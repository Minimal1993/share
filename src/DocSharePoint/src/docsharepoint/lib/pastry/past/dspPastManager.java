/*
 *  DocSharePoint
 *  Open Source Distributed p2p system based on pastry
 *  Copyright (C) 2010-2011 DocSharePoint KARPOUZAS GEORGE
 *
 *  http://docsharepoint.sourceforge.net/
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package docsharepoint.lib.pastry.past;

import docsharepoint.lib.Messaging;
import docsharepoint.lib.files.FileManager;
import docsharepoint.lib.files.PastFile;
import docsharepoint.lib.pastry.core.iPastryManager;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import rice.Continuation;
import rice.environment.Environment;
import rice.p2p.commonapi.Id;
import rice.p2p.past.Past;
import rice.p2p.past.PastContent;
import rice.p2p.past.PastImpl;
import rice.pastry.NodeIdFactory;
import rice.pastry.PastryNode;
import rice.pastry.PastryNodeFactory;
import rice.pastry.commonapi.PastryIdFactory;
import rice.pastry.socket.SocketPastryNodeFactory;
import rice.pastry.standard.RandomNodeIdFactory;
import rice.persistence.LRUCache;
import rice.persistence.PersistentStorage;
import rice.persistence.Storage;
import rice.persistence.StorageManagerImpl;

/**
 *
 * @author George Karpouzas <gkarpouzas@webnetsoft.gr>
 */
public class dspPastManager implements iPastryManager{
    private dspIdManager keyManager;
    /**.
     * boot hostname
     */
    private String bootIP;

    /**.
     * boot port
     */
    private int bootPort;

    /**.
     * bind port
     */
    private int bindPort;

    /**.
     * connected?
     */
    private boolean connected;

    /**.
     * pastry node created
     */
    private PastryNode node;

    /**.
     * log level - default
     */
    private final int loglevel = 1000;

    /**.
     * wait before join
     */
    private final int nodeWaitTime = 500;
    /**
     * path to store content
     */
    private String _path;
    /**
     * environment
     */
    private Environment env;
    /**
     * past application
     */
    private Past app;
    
    /**
     * local factory - produce ids-keys for content
     */
    private PastryIdFactory localFactory;
    private String _firewallPolicy, _natPolicy;
    private int _pingDelay;
    private boolean _probe4ExtAddr;
    private boolean _useDirect;
    private int _numNodes;
    private int _loglevel;
    private dspPastContent contentResult;
    
    /**
     * default constructor
     */
    public dspPastManager(){
        connected = false;
        contentResult = null;
        keyManager = new dspIdManager();
        keyManager.init();
    }

    /**
     * initialize past app
     * @param Path
     */
    public void init(int numNodes, boolean useDirect, int logLevel,
            String firewallPolicy, String natPolicy,
            int pingDelay, boolean probe4ExternalAddress, String Path){
        _useDirect = useDirect;
        _numNodes = numNodes;
        _loglevel = logLevel;
        _firewallPolicy = firewallPolicy;
        _natPolicy = natPolicy;
        _pingDelay = pingDelay;
        _probe4ExtAddr = probe4ExternalAddress;
        _path = Path;
    }
    /**
     * connect to pastry network
     * @param boothostip
     * @param bootport
     * @param bindport
     * @return boolean
     */
    public boolean connect(String boothostip, int bootport, int bindport) {
        bootIP = boothostip;
        bootPort = bootport;
        bindPort = bindport;
        InetAddress local = null;

        env = new Environment();
        env.getParameters().setString("firewall_test_policy", _firewallPolicy);
        env.getParameters().setString("nat_search_policy", _natPolicy);
        env.getParameters().setString("pastry_socket_scm_ping_delay", _pingDelay+"");
        env.getParameters().setString("probe_for_external_address", _probe4ExtAddr+"");
        //800 - detailed, 900 - less detailed
        env.getParameters().setInt("loglevel", _loglevel);

        try {
            local = InetAddress.getByName(bootIP);
        } catch (Exception ex) { return false; }
        InetSocketAddress bootaddress = new InetSocketAddress(local, bootPort);

        //generate random nodeids
        NodeIdFactory nodeidFactory = new RandomNodeIdFactory(env);
        PastryNodeFactory factory = null;
        node = null;

        try {
            factory = new SocketPastryNodeFactory(
                    nodeidFactory, local, bindPort, env);

            node = factory.newNode();
        } catch (IOException ex) { return false; }


        PastryIdFactory idf = new rice.pastry.commonapi.PastryIdFactory(env);
        String storageDirectory = _path+"storage"+node.getId().hashCode();

        // the persistent part
        Storage stor = null;
        try {
            stor = new PersistentStorage(idf, storageDirectory, 4 * 1024 * 1024, node.getEnvironment());
        } catch (IOException ex) {
            Messaging.PrintError2Console(ex.toString());
        }

        app = new PastImpl(node, new StorageManagerImpl(idf, stor, new LRUCache(
 	          stor, 512 * 1024, node.getEnvironment())), 0, "");
        
        //boot node
        node.boot(bootaddress);

        // the node may require sending several messages to fully boot into the ring
        synchronized (node) {
            while (!node.isReady() && !node.joinFailed()) {
                try {
                    //delay
                    node.wait(nodeWaitTime);
                } catch (Exception ex) { Messaging.PrintError2Console(ex.toString()); }

                // abort if can't join
                if (node.joinFailed()) {
                  Messaging.PrintError2Console(
                    "Could not join the FreePastry ring.  Reason:"
                    + node.joinFailedReason());
                  System.exit(1);
                }
            }
        }

        connected = true;
        Messaging.Print2Console("Finished creating new node " + node.toString());
        
        try {
            env.getTimeSource().sleep(5000);
        } catch (InterruptedException ex) { }
        
        return true;
    }

    /**
     * insert file
     * @param name
     * @param k
     * @param file
     * @return Id
     */
    public Id insert(String name, int k, File file){
        if(keyManager.exists(name))Messaging.ShowMessage("This name has already been taken. Please choose another one.", "Upload File");
        if(localFactory==null)localFactory = new rice.pastry.commonapi.PastryIdFactory(env);
        Id contentKey = null;
        
        for(int ctr = 0; ctr < k; ctr++) {
          String extension = FileManager.getExtension(file);
          //store content
          final PastContent content = new dspPastContent(
                  localFactory.buildId(name),
                  name,
                  extension,
                  new PastFile(FileManager.getContents(file)));
          contentKey = content.getId();
          keyManager.add(name, contentKey);
          
          // insert data
          app.insert(content, new Continuation(){
              
            // the result is an Array of Booleans for each insert
            public void receiveResult(Object result) {
              Boolean[] results = ((Boolean[]) result);
              int numSuccessfulStores = 0;
              for (int ctr = 0; ctr < results.length; ctr++) {
                if (results[ctr].booleanValue())
                  numSuccessfulStores++;
              }
              Messaging.Print2Console(content + " successfully stored at " +
                  numSuccessfulStores + " locations.");
            }
            public void receiveException(Exception result) { }
          });
        }
        try {
            // wait 5 seconds
            env.getTimeSource().sleep(5000);
        } catch (InterruptedException ex) { }
        return contentKey;
    }

    /**
     * build id for name
     * @param name
     * @return Id
     */
    public Id buildKey(String name){
        Messaging.Print2Console("Producing key for " + name);
        if(localFactory==null)localFactory = new rice.pastry.commonapi.PastryIdFactory(env);
        return localFactory.buildId(name);
    }

    /**
     * get filename key
     * @param name
     * @return Id
     */
    public Id getKey(String name){
        return keyManager.get(name);
    }

    /**
     * search for file
     * @param lookupKey
     * @param path2download
     */
    public void search(final Id lookupKey, final String path2download){
          Messaging.Print2Console("Looking up " + lookupKey + " at node "+app.getLocalNodeHandle());

          app.lookup(lookupKey, new Continuation<PastContent, Exception>() {
            
            public void receiveResult(PastContent result) {
                contentResult = (dspPastContent) result;
                if(contentResult!=null){
                    File downloaded = new File(path2download+contentResult.getName()+"."+
                        contentResult.getExtension());
                    try {
                        downloaded.createNewFile();
                        FileManager.setContents(downloaded, contentResult.getContent());
                        Messaging.Print2Console("File downloaded to "+downloaded.getPath());
                    } catch (IOException ex) {
                        Messaging.Print2Console("Error occured while downloading file.");
                    }
                }
            }
            
            public void receiveException(Exception result) {
              Messaging.Print2Console("Error looking up "+lookupKey);
              Messaging.Print2Console(result.toString());
              env.destroy();
            }
          });
    }

    /**.
     * disconnect from pastry network
     */
    public final void disconnect() {
        if (node != null) {
            node.destroy();
            connected = false;
            Messaging.Print2Console("disconnected..");
        }
    }

    /**.
     * returns true if current machine is connected to pastry network
     * @return boolean, true if connected
     */
    public final boolean isConnected() {
        return connected;
    }

    /**.
     * get bootstrap ip
     * @return String
     */
    public final String getBootIP() {
        return bootIP;
    }

    /**.
     * get bootstrap port
     * @return int
     */
    public final int getBootPort() {
        return bootPort;
    }

    /**.
     * set bootstrap ip
     * @param value hostname
     */
    public final void setBootIP(final String value) {
        bootIP = value;
    }

    /**.
     * set boot port
     * @param value boot port
     */
    public final void setBootPort(final int value) {
        bootPort = value;
    }

    /**.
     * get binded port
     * @return int
     */
    public final int getBindPort() {
        return bindPort;
    }

    /**.
     * set binded port
     * @param value bind port
     */
    public final void setBindPort(final int value) {
        bindPort = value;
    }

    /**.
     * get handler to newly created pastry node
     * @return PastryNode
     */
    public final PastryNode getNode() {
        return node;
    }
}
