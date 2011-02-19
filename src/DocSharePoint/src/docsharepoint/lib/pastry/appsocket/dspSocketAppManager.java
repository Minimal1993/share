/*
 *  Copyright (c) 2010 WEBNETSOFT
 *  http://www.webnetsoft.gr/
 */

package docsharepoint.lib.pastry.appsocket;

import docsharepoint.lib.Messaging;
import docsharepoint.lib.pastry.core.iPastryManager;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import rice.environment.Environment;
import rice.p2p.commonapi.IdFactory;
import rice.p2p.commonapi.NodeHandle;
import rice.p2p.commonapi.rawserialization.RawMessage;
import rice.pastry.NodeIdFactory;
import rice.pastry.PastryNode;
import rice.pastry.PastryNodeFactory;
import rice.pastry.commonapi.PastryIdFactory;
import rice.pastry.direct.DirectNodeHandle;
import rice.pastry.direct.DirectPastryNodeFactory;
import rice.pastry.direct.EuclideanNetwork;
import rice.pastry.direct.NetworkSimulator;
import rice.pastry.leafset.LeafSet;
import rice.pastry.socket.SocketPastryNodeFactory;
import rice.pastry.standard.RandomNodeIdFactory;

/**
 *
 * @author George Karpouzas <gkarpouzas@webnetsoft.gr>
 */
public class dspSocketAppManager implements iPastryManager{
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
    private int _loglevel;

    /**.
     * wait before join
     */
    private final int nodeWaitTime = 500;

    /**
     * if true create ring and boot else connect to existent ring
     */
    private boolean _useDirect;

    private int _numNodes;

    private Environment env;

    private dspSocketApplication app;
    private String _firewallPolicy, _natPolicy;
    private int _pingDelay;
    private boolean _probe4ExtAddr;
    /**
     * default constructor
     */
    public dspSocketAppManager(){
        connected = false;
    }

    /**
     * initialize socketapp properties
     * @param numNodes
     * @param useDirect
     */
    public void init(int numNodes, boolean useDirect, int logLevel,
            String firewallPolicy, String natPolicy,
            int pingDelay, boolean probe4ExternalAddress){
        _useDirect = useDirect;
        _numNodes = numNodes;
        _loglevel = logLevel;
        _firewallPolicy = firewallPolicy;
        _natPolicy = natPolicy;
        _pingDelay = pingDelay;
        _probe4ExtAddr = probe4ExternalAddress;
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

        //generate random nodeids
        NodeIdFactory nodeidFactory = new RandomNodeIdFactory(env);
        PastryNodeFactory factory = null;
        node = null;

        if (_useDirect) {
            NetworkSimulator<DirectNodeHandle,RawMessage> sim =
                    new EuclideanNetwork<DirectNodeHandle,RawMessage>(env);
            factory = new DirectPastryNodeFactory(nodeidFactory, sim, env);
        } else {
            //factory = new SocketPastryNodeFactory(nodeidFactory, bindport, env);
            try {
                factory = new SocketPastryNodeFactory(
                    nodeidFactory, bindPort, env);
                node = factory.newNode();
            } catch (IOException ex) { return false; }
        }

        try {
            local = InetAddress.getByName(bootIP);
        } catch (Exception ex) {
            return false;
        }
        
        InetSocketAddress bootaddress = new InetSocketAddress(local, bootPort);
        
        IdFactory idFactory = new PastryIdFactory(env);
        Object bootHandle = null;

        // loop to construct the nodes/apps
        //for (int curNode = 0; curNode < _numNodes; curNode++) {

            try {
                node = factory.newNode();
            } catch (IOException ex) {
            }

            // construct a new MyApp
            app = new dspSocketApplication(node, idFactory);

            node.boot(bootHandle);

            if (bootHandle == null) {
                if (_useDirect) {
                    bootHandle = node.getLocalHandle();
                } else {
                    bootHandle = bootaddress;
                }
            }
        //}

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
            env.getTimeSource().sleep(1000);
        } catch (InterruptedException ex) { }

        return true;
    }
    
    public void insert(String filename){
        // wait 1 second
        try {
            env.getTimeSource().sleep(1000);
        } catch (InterruptedException ex) { }

        // pick node
        PastryNode mnode = (PastryNode)app.getNode();

        // send directly to my leafset (including myself)
        LeafSet leafSet = node.getLeafSet();

        // pick some node in the leafset
        int i=-leafSet.ccwSize();

        // select the item
        NodeHandle nh = leafSet.get(i);

        // send the message directly to the node
        app.sendMyMsgDirect(nh, new File(filename));
        
        // wait a bit
        try {
            env.getTimeSource().sleep(100);
        } catch (InterruptedException ex) { }
    }

    /**
     * disconnect from pastry network
     */
    public void disconnect(){
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

    /**
     * testing
     * @param args
     */
    /*public static void main(String args[]){
        dspSocketAppManager manager = new dspSocketAppManager(10, true);
        manager.connect("localhost", 9090, 9090);
        manager.insert("test.txt");
        //manager.disconnect();
        //System.exit(0);
    }*/
}
