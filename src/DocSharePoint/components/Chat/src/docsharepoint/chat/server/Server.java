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
package docsharepoint.chat.server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * represents the decentralized server
 * peers connect to this server to get a unique id
 * and get a list of available peers on the network to
 * communicate with.
 * 
 * @author developer
 */
public class Server {
    private int _port;
    private ServerSocket _socket;
    
    /**
     * constructor specifying server's listening port
     * @param port 
     */
    public Server(int port){
        this._port = port;
    }
    
    /**
     * start the server
     */
    public void start(){
        try {
            
            // -----------------------------------------------------------
            // start listening
            // -----------------------------------------------------------
            this._socket = new ServerSocket(this._port);
            System.out.println("Listening on " + this._port);
            
            
            // -----------------------------------------------------------
            // accepting connections
            // -----------------------------------------------------------
            while(true){
                
                // -----------------------------------------------------------
                // accept connection
                // -----------------------------------------------------------
                Socket client = this._socket.accept();
                System.out.println("Connection accepted from " + client);
                
                // -----------------------------------------------------------
                // start client thread
                // -----------------------------------------------------------
                new ServerThread(this, client).start();
            }
            
            
        } catch (IOException ex) {
            System.err.println("Error: Unable to start server on port " + this._port + ". Maybe port is in use.");
        }
    }
    
    /**
     * get the listening port
     * @return int
     */
    public int getListeningPort(){
        return this._port;
    }
    
    /**
     * send message to client
     * @param message
     * @param client 
     */
    public void sendmessage(String message, Socket client){
        try {
            
            // -----------------------------------------------------------
            // send message
            // -----------------------------------------------------------
            DataOutputStream outputtoclient = new DataOutputStream(client.getOutputStream());
            outputtoclient.writeUTF(message);
            
        } catch (IOException ex) {
            System.err.println("Error: sending message to client.");
        }
    }
}
