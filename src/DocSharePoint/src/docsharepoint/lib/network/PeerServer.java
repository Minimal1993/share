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
package docsharepoint.lib.network;

import docsharepoint.AppConfig;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * represents the peer server
 * this server is running on each peer and is listening
 * for incoming connections to the peer
 * @author George Karpouzas
 */
public class PeerServer extends Thread{
    private int _port;
    private ServerSocket _socket;
    
    /**
     * constructor specifying server's listening port
     * @param port 
     */
    public PeerServer(int port){
        this._port = port;
    }
    
    /**
     * get server port
     * @return int
     */
    public int getPort(){
        return this._port;
    }

    @Override
    public void run() {
        try {
            
            // -----------------------------------------------------------
            // start listening
            // -----------------------------------------------------------
            this._socket = new ServerSocket(this._port);
            AppConfig.Instance().getReportDialog().LogMessage("Listening on " 
                    + this._port);
            
            
            // -----------------------------------------------------------
            // accepting connections
            // -----------------------------------------------------------
            while(true){
                
                // -----------------------------------------------------------
                // accept connection
                // -----------------------------------------------------------
                Socket client = this._socket.accept();
                AppConfig.Instance().getReportDialog().LogMessage("Connection accepted from " 
                        + client + ".");
                
                // -----------------------------------------------------------
                // start client thread
                // -----------------------------------------------------------
                new PeerServerThread(this, client).start();
            }
            
            
        } catch (IOException ex) {
            AppConfig.Instance().getReportDialog().LogMessage("Error: Unable to start server on port " 
                    + this._port + 
                    ". Maybe port is in use.");
        }
    }
}

