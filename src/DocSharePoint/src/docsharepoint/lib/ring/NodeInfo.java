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

import docsharepoint.lib.NodeId;

/**
 * represents a set item (nodeid, ip, port)
 * @author developer
 */
public class NodeInfo implements Comparable<NodeInfo>{
    private NodeId _nodeid;
    private String _ip;
    private int _port;
    
    /**
     * constructor specifying nodeid, ip and port
     * @param nodeid
     * @param IP
     * @param Port 
     */
    public NodeInfo(NodeId nodeid, String IP, int Port){
        this._nodeid = nodeid;
        this._ip = IP;
        this._port = Port;
    }
    
    /**
     * get ip
     * @return 
     */
    public String getIP(){
        return this._ip;
    }
    
    /**
     * get port
     */
    public int getPort(){
        return this._port;
    }
    
    /**
     * get node id
     */
    public NodeId getID(){
        return this._nodeid;
    }

    /**
     * nodeinfo's nodeids
     * @param arg0
     * @return 
     */
    @Override
    public int compareTo(NodeInfo arg0) {
        return this._nodeid.compareTo(arg0.getID());
    }
}
