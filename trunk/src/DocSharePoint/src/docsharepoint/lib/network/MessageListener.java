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
import docsharepoint.lib.Message;
import java.io.DataInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.Socket;

/**
 * represents the server thread
 * this thread is created for every accepted client connection
 * @author George Karpouzas
 */
public class MessageListener extends Thread{
    private PeerServer _server;
    private Socket _clientsocket;
    
    /**
     * contractor specifying server and client socket
     * @param server
     * @param client 
     */
    public MessageListener(PeerServer server, Socket client){
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
                //deliver(new Message(inputfromclient.readUTF()), 
                //        SHA1Helper.Instance().hash(this._clientsocket.getInetAddress().toString().replace("/", "")));
                AppConfig.Instance().getReportDialog().LogMessage(inputfromclient.readUTF());
            }
            
        } catch (IOException ex) {}
    }
    
    /**
     * deliver message
     * @param msg
     * @param key 
     */
    public void deliver(Message msg, BigInteger key){
        
    }
}
