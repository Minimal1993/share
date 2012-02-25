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
public class Node implements Comparable<Node>{
    private String _nodeID;
    private String _ip;
    
    public LeafSet L;
    public RoutingTable R;
    public NeighborhoodSet M;
    
    /**
     * default constructor listening on port 9090
     */
    public Node() throws InvalidIPAddressException{
        this._ip = this._getipaddress();
        this._nodeID = this._generateID();
        
        this.L = new LeafSet(this);
        this.R = new RoutingTable();
        this.M = new NeighborhoodSet();
    }
    
    /**
     * constructor specifying port
     * @param port 
     */
    public Node(int port) throws InvalidIPAddressException{
        this._nodeID = this._generateID();
        this._ip = this._getipaddress();

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
    
    private String _generateID() throws InvalidIPAddressException{
        if(this._ip.isEmpty())
            throw new InvalidIPAddressException();
        return SHA1Helper.getInstance().hash(this._ip);
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
}
