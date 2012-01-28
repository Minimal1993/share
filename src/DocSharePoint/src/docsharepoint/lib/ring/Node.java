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

import docsharepoint.lib.helpers.SHA1Helper;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * represents a ring node
 * @author Karpouzas George
 */
public class Node implements Comparable{
    private String _nodeID;
    private String _ip;
    private int _port;
    public LeafSet L;
    public RoutingTable R;
    public NeighborhoodSet M;
    
    /**
     * default constructor listening on port 9090
     */
    public Node(){
        this._nodeID = this._generateID();
        this._ip = this._getipaddress();
        this._port = 9090;

        this.L = new LeafSet(this);
        this.R = new RoutingTable();
        this.M = new NeighborhoodSet();
    }
    
    /**
     * constructor specifying port
     * @param port 
     */
    public Node(int port){
        this._nodeID = this._generateID();
        this._ip = this._getipaddress();
        this._port = port;

        this.L = new LeafSet(this);
        this.R = new RoutingTable();
        this.M = new NeighborhoodSet();
    }
    
    /**
     * get node id
     * @return String
     */
    public String getID(){
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
     * get node port
     * @return int
     */
    public int getPort(){
        return this._port;
    }
    
    private String _getipaddress(){
        String ipaddress = "0.0.0.0";
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
    
    private String _generateID(){
        return SHA1Helper.getInstance().hash(this._ip + ":" + this._port);
    }
    
    public static void main(String args[]){
        Node n = new Node();
        System.out.println(n.getID());
    }

    /**
     * compare nodes by comparing their ids
     * @param t
     * @return int
     */
    public int compareTo(Object t) {
        return ((Node) t).getID().compareTo(this._nodeID);
    }
    
    public int distance(Node n){
        return -1;
    }
}
