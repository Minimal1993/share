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
package docsharepoint.chat.client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;

/**
 * represents the peer server
 * this server is running on each peer and is listening
 * for incoming connections to the peer for chating
 * @author George Karpouzas
 */
public class PeerServer {
    private int _port;
    private ServerSocket _socket;
    private HashMap<Socket, DataOutputStream> _outputs;
    
    /**
     * constructor specifying server's listening port
     * @param port 
     */
    public PeerServer(int port){
        this._port = port;
        this._outputs = new HashMap<Socket, DataOutputStream>();
    }
    
    /**
     * get server port
     * @return int
     */
    public int getPort(){
        return this._port;
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
                System.out.println("Connection accepted from " + client + ".");
                
                // -----------------------------------------------------------
                // remember socket client outputstream
                // -----------------------------------------------------------
                this._outputs.put(client, new DataOutputStream(client.getOutputStream()));
                
                
                // -----------------------------------------------------------
                // start client thread
                // -----------------------------------------------------------
                new PeerServerThread(this, client).start();
            }
            
            
        } catch (IOException ex) {
            System.err.println("Error: Unable to start server on port " + this._port + ". Maybe port is in use.");
        }
    }    
    
    /**
     * send message to client
     * @param message
     * @param client 
     */
    public void sendmessagetoall(String message){
        // -----------------------------------------------------------
        // syncronize it in case another thread is using it
        synchronized(_outputs){
            try {
                for(Enumeration<DataOutputStream> en = Collections.enumeration(this._outputs.values()); en.hasMoreElements();){
                    
                    // -----------------------------------------------------------
                    // send message
                    // -----------------------------------------------------------
                    en.nextElement().writeUTF(message);
                
                }

            } catch (IOException ex) {
                System.err.println("Error: sending message to clients.");
            }
        }
    }
    
    /**
     * close connection
     * @param client
     * @throws IOException 
     */
    public void removeConnection(Socket client){
        
        // -----------------------------------------------------------
        // synchronize peers list because another thread might be using it
        // -----------------------------------------------------------
        synchronized(_outputs){
            try {

                // -----------------------------------------------------------
                // close connection with client
                // -----------------------------------------------------------
                client.close();

            } catch (IOException ex) {}


            // -----------------------------------------------------------
            // remove client from peers list
            // -----------------------------------------------------------
            this._outputs.remove(client);
        }
    }
}

