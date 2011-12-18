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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private HashMap<Long, Socket> _peers;
    private static long _peerscounter = 0;
    
    /**
     * constructor specifying server's listening port
     * @param port 
     */
    public Server(int port){
        this._port = port;
        this._peers = new HashMap<Long, Socket>();
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
                
                
                // -----------------------------------------------------------
                // add client to peers list
                // -----------------------------------------------------------
                long id = this.generateID();
                this._peers.put(id, client);
                System.out.println("Connection accepted from " + client + ". Peer ID " + id);
                this.sendmessage("#Your ID is " + id, client);
                
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
     * get peers list to send it to the client which requested it
     * @return String
     */
    public String getPeersList(){
        String list = "#\r\n";
        for(Long key : this._peers.keySet()){
            list += "Peer - " + key + "\r\n";
        }
        return list;
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
    
    public void removeConnection(Socket client){
        
        // -----------------------------------------------------------
        // synchronize peers list because another thread might be using it
        // -----------------------------------------------------------
        synchronized(_peers){
            try {

                // -----------------------------------------------------------
                // close connection with client
                // -----------------------------------------------------------
                client.close();

            } catch (IOException ex) {}


            // -----------------------------------------------------------
            // remove client from peers list
            // -----------------------------------------------------------
            this._peers.values().remove(client);
        }
    }

    //generate unique id for each connected peer
    private long generateID(){
        
        // -----------------------------------------------------------
        // get current date
        // -----------------------------------------------------------
        Calendar currentdate = Calendar.getInstance();
        SimpleDateFormat dformatter = new SimpleDateFormat("ddmmyyyy");
        String dateNow = dformatter.format(currentdate.getTime());
        
        
        // -----------------------------------------------------------
        // add one to the current date and return
        // -----------------------------------------------------------
        return Long.parseLong(dateNow) + Server._peerscounter++;
        
    }
}
