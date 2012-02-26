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
package docsharepoint.lib.ring;

import docsharepoint.lib.LibPastry;
import docsharepoint.lib.Message;
import docsharepoint.lib.NodeId;
import docsharepoint.lib.network.Messenger;
import docsharepoint.lib.network.PeerServer;
import docsharepoint.ui.Exceptions.InvalidIPAddressException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * represents a ring node
 * @author Karpouzas George
 */
public class Node implements Comparable<Node>, LibPastry{
    private NodeId _nodeID;
    private String _ip;
    private int _port;
    public LeafSet L;
    public RoutingTable R;
    public NeighborhoodSet M;
    
    /**
     * constructor specifying port
     * @param port 
     */
    public Node(int port) throws InvalidIPAddressException{
        this._ip = this._getipaddress();
        this._port = port;
        this._nodeID = new NodeId(this._ip+":"+this._port);
        
        this.L = new LeafSet(this);
        this.M = new NeighborhoodSet(this);
        this.R = new RoutingTable(this);
    }
    
    /**
     * get node id
     * @return String
     */
    public NodeId getID(){
        return this._nodeID;
    }
    
    /**
     * get node ip
     * @return String
     */
    public String getIP(){
        return this._ip;
    }
    
    /**
     * get port number
     * @return 
     */
    public int getPort(){
        return this._port;
    }
    
    private String _getipaddress(){
        String ipaddress = "";
        try {
            for (Enumeration<NetworkInterface> eni = 
                NetworkInterface.getNetworkInterfaces();
                eni.hasMoreElements(); )
                {
                    NetworkInterface ni = eni.nextElement();
                    if(ni.getName().contains("eth")){
                        for (Enumeration<InetAddress> addresses =
                           ni.getInetAddresses();
                         addresses.hasMoreElements(); )
                        {
                            InetAddress address = addresses.nextElement();
                            ipaddress = address.toString().replace("/", "");
                        }
                    }
                }
            return ipaddress;
        } catch (SocketException ex) {
            return ipaddress;
        }
    }
    
    /**
     * calculate distance between nodes
     * @param n
     * @return int hops
     */
    public int distance(Node n){
        try {
            String[] cmd = {
            "/bin/sh",
            "-c",
            "traceroute " + n.getIP() + " | tail -1"
            };
            Process proc = Runtime.getRuntime().exec(cmd);
            InputStream in = proc.getInputStream();
            StringWriter writer = new StringWriter();
            int c;
            while((c = in.read()) > 0) {
                writer.write(c);
            }
            writer.flush();
            return Integer.parseInt(writer.toString().trim().split(" ")[0]);
        } catch (IOException ex) {
            return -1;
        }
    }

    /**
     * compare nodes
     * @param t
     * @return 
     */
    @Override
    public int compareTo(Node t) {
        return t.getID().compareTo(this._nodeID);
    }

    /**
     * initialize pastry new or join
     * @param IP
     * @param Port
     * @return 
     */
    @Override
    public NodeId pastryInit(String IP, int Port, Boolean newNet) {
        if(newNet){
            //--------------------------------------------
            //start listening on a port
            //--------------------------------------------
            new Thread(new PeerServer(Port)).start();
        }
        else{
            //--------------------------------------------
            //start listening on a port
            //--------------------------------------------
            new Thread(new PeerServer(Port)).start();
            this.send(new Message("MSG_JOIN"), IP, Port);
        }
        return this._nodeID;
    }

    @Override
    public void route(Message msg, NodeId key) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * send message to specific node
     * @param msg
     * @param IP
     * @param Port 
     */
    @Override
    public void send(Message msg, String IP, int Port) {
        new Thread(new Messenger(IP, Port, msg)).start();
    }
}
