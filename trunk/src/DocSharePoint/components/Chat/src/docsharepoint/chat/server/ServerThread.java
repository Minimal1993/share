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

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * represents the server thread
 * this thread is created for every accepted client connection
 * @author developer
 */
public class ServerThread extends Thread{
    private Server _server;
    private Socket _clientsocket;
    
    /**
     * contractor specifying server and client socket
     * @param server
     * @param client 
     */
    public ServerThread(Server server, Socket client){
        this._server = server;
        this._clientsocket = client;
    }
    
    /**
     * run the thread
     */
    @Override
    public void run(){
        try {
            
            // -----------------------------------------------------------
            // read from client
            // -----------------------------------------------------------
            DataInputStream inputfromclient = new DataInputStream(this._clientsocket.getInputStream());
            
            while(true){
                
                // -----------------------------------------------------------
                // read client's message
                // -----------------------------------------------------------
                String message = inputfromclient.readUTF();
                
                // -----------------------------------------------------------
                // respond to the client
                // -----------------------------------------------------------
                this._server.sendmessage(message, this._clientsocket);
            }
            
        } catch (IOException ex) {
        }
        finally{
            try {
                
                // -----------------------------------------------------------
                // close client connection if was terminated for some reason
                // -----------------------------------------------------------
                this._clientsocket.close();
            } catch (IOException ex) {}
        }
    }
}
