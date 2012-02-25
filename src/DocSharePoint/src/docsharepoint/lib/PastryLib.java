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
package docsharepoint.lib;

import docsharepoint.AppConfig;
import docsharepoint.lib.network.PeerClient;
import docsharepoint.lib.network.PeerServer;
import docsharepoint.lib.ring.Node;
import docsharepoint.ui.Exceptions.InvalidIPAddressException;
import java.io.IOException;

/**
 * represents the pastry lib
 * @author Karpouzas George
 */
public class PastryLib {
    private int _port;
    
    /**
     * initialize local node
     * @return nodeid
     */
    public String pastryInit(String IP, int Port){
        try {
            this._port = Port;
            AppConfig.Instance().localNode = new Node();
            //-----------------------------------------------------
            // start listening on port for incoming connections
            //-----------------------------------------------------
            new Thread(new PeerServer(Port)).start();
            
            send(new Message("join"), IP);
            
            //-----------------------------------------------------
            // return node id
            //-----------------------------------------------------
            return AppConfig.Instance().localNode.getID();
        } catch (InvalidIPAddressException ex) {
            return null;
        }
    }
    
    /**
     * send message to specific ip address
     * @param msg
     * @param IP 
     */
    public void send(Message msg, String IP){
        new Thread(new PeerClient(IP, this._port, msg)).start();
    }
}
