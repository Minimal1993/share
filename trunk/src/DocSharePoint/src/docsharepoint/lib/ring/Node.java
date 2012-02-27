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
    private NodeInfo _nodeinfo;
    public LeafSet L;
    public RoutingTable R;
    public NeighborhoodSet M;
    
    /**
     * constructor specifying port
     * @param port 
     */
    public Node(int port) throws InvalidIPAddressException{
        String ip = this._getipaddress();
        this._nodeinfo = new NodeInfo(
                new NodeId(ip+":"+port), ip, port);
        
        this.L = new LeafSet(this);
        this.M = new NeighborhoodSet(this);
        this.R = new RoutingTable(this);
    }
    
    /**
     * get node id
     * @return String
     */
    public NodeId getID(){
        return this._nodeinfo.getID();
    }
    
    /**
     * get node ip
     * @return String
     */
    public String getIP(){
        return this._nodeinfo.getIP();
    }
    
    /**
     * get node info
     * @return NoeInfo
     */
    public NodeInfo getNodeInfo(){
        return this._nodeinfo;
    }
    
    /**
     * get port number
     * @return int
     */
    public int getPort(){
        return this._nodeinfo.getPort();
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
     * @param IP
     * @return int hops
     */
    public int distance(String IP){
        try {
            String[] cmd = {
            "/bin/sh",
            "-c",
            "traceroute " + IP + " | tail -1"
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
     * @return int
     */
    @Override
    public int compareTo(Node t) {
        return t.getID().compareTo(this._nodeinfo.getID());
    }

    /**
     * initialize pastry new or join
     * @param IP
     * @param Port
     * @return NodeId
     */
    @Override
    public NodeId pastryInit(String IP, int Port, 
        String RemoteIP, int RemotePort, Boolean newNet) {
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
            
            //--------------------------------------------
            //send join request
            //--------------------------------------------
            this.send(new Message("MSG_JOIN:"+this._nodeinfo.getID()), RemoteIP, RemotePort);
        }
        return this._nodeinfo.getID();
    }

    /**
     * 
     * @param msg
     * @param key 
     */
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
