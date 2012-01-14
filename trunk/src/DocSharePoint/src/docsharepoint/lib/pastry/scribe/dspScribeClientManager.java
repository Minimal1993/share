/*
 *  DocSharePoint
 *  Open Source Distributed p2p system based on pastry
 *  Copyright (C) 2010-2012 DocSharePoint KARPOUZAS GEORGE
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

package docsharepoint.lib.pastry.scribe;

import docsharepoint.lib.Messaging;
import docsharepoint.lib.pastry.core.iPastryManager;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import rice.environment.Environment;
import rice.pastry.NodeIdFactory;
import rice.pastry.PastryNode;
import rice.pastry.PastryNodeFactory;
import rice.pastry.socket.SocketPastryNodeFactory;
import rice.pastry.standard.RandomNodeIdFactory;

/**
 * represents a scribe client-application
 * @author George Karpouzas <gkarpouzas@webnetsoft.gr>
 */
public class dspScribeClientManager implements iPastryManager{
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
     * default constructor
     */
    public dspScribeClientManager(){
        connected = false;
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

        Environment env = new Environment();
        env.getParameters().setString("firewall_test_policy", "never");
        env.getParameters().setString("nat_search_policy", "never");
        env.getParameters().setString("pastry_socket_scm_ping_delay", "30");
        env.getParameters().setString("probe_for_external_address", "false");
        //800 - detailed, 900 - less detailed
        env.getParameters().setInt("loglevel", loglevel);

        try {
            local = InetAddress.getByName(bootIP);
        } catch (Exception ex) { 
            return false;
        }

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

        //new scribe app
        dspScribeClient app = new dspScribeClient(node, "DocSharePoint - Share Files");

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

        //subscribe and start publishtask
        app.subscribe();
        app.startPublishTask();
        
        try {
            env.getTimeSource().sleep(5000);
        } catch (InterruptedException ex) { }

        return true;
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
}
