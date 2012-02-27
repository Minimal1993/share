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
import docsharepoint.lib.NodeId;
import docsharepoint.lib.ring.NodeInfo;
import java.io.*;
import java.net.Socket;

/**
 * represents the server thread
 * this thread is created for every accepted client connection
 * @author George Karpouzas
 */
public class MessageListener extends Thread{
    private Socket _clientsocket;
    private PeerServer _server;
    
    /**
     * contractor specifying server and client socket
     * @param server
     * @param client 
     */
    public MessageListener(PeerServer server, Socket client){
        this._clientsocket = client;
        this._server = server;
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
                Message msg = new Message(inputfromclient.readUTF());
                
                switch(msg.getMessageType()){
                    case "MSG_JOIN":
                        NodeId n = new NodeId();
                        n.setKey(msg.getNodeId());
                        NodeInfo ni = new NodeInfo(n, 
                                this._clientsocket.getInetAddress().toString().replace("/", ""), 
                                this._clientsocket.getPort());

                        AppConfig.Instance().localNode.L.add(ni);
                        AppConfig.Instance().localNode.M.add(ni);
                        AppConfig.Instance().localNode.R.add(ni);
                        
                        this._server.sendmessage(AppConfig.Instance().localNode.getID().toString(), _clientsocket);
                        AppConfig.Instance().getReportDialog().LogMessage("JOIN MESSAGE received.");
                        
                        break;
                    case "MSG_FILE":
                        // receive file
                        byte [] mybytearray  = new byte [1024];
                        InputStream is = this._clientsocket.getInputStream();
                        FileOutputStream fos = new FileOutputStream("source-copy.pdf");
                        BufferedOutputStream bos = new BufferedOutputStream(fos);
                        int bytesRead = is.read(mybytearray,0,mybytearray.length);
                        int current = 0;
                        current = bytesRead;
                        do {
                            bytesRead = is.read(mybytearray, current, (mybytearray.length-current));
                        if(bytesRead >= 0) current += bytesRead;
                        } while(bytesRead > -1);

                        bos.write(mybytearray, 0 , current);
                        bos.flush();
                        long end = System.currentTimeMillis();
                        
                        bos.close();
                        this._clientsocket.close();
                        break;
                    default:
                        AppConfig.Instance().getReportDialog().LogMessage("Message received: "+msg.getMessage());
                }
                
            }
            
        } catch (IOException ex) {}
    }
    
    /**
     * deliver message
     * @param msg
     * @param key 
     */
    public void deliver(Message msg, NodeId key){
        
    }
}
