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
package docsharepoint.chat.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * represents a peer on the network
 * 
 * @author developer
 */
public class Client implements Runnable{
    private Socket _socket;
    private String _host;
    private int _port;
    private DataOutputStream _output;
    private DataInputStream _input;
    
    /**
     * constructor providing server-host and server listening port
     * @param host
     * @param port 
     */
    public Client(String host, int port){
        this._host = host;
        this._port = port;
    }
    
    /**
     * connect to the server
     */
    public void connect(){
        try {
            
            // -----------------------------------------------------------
            // connect to server
            // -----------------------------------------------------------
            this._socket = new Socket(this._host, this._port);
            System.out.println("Connected to server.");
            
            
            // -----------------------------------------------------------
            // get output stream
            // -----------------------------------------------------------
            this._output = new DataOutputStream(this._socket.getOutputStream());
            
            
            // -----------------------------------------------------------
            // get input stream
            // -----------------------------------------------------------
            this._input = new DataInputStream(this._socket.getInputStream());
            
            
            // -----------------------------------------------------------
            // start the thread to handle communication
            // -----------------------------------------------------------
            new Thread(this).start();
            
        } catch (UnknownHostException ex) {
            System.err.println("Error: Unable to connect to the server.");
        } catch (IOException ex) {
            System.err.println("Error: Unable to connect to the server.");
        }
    }

    /**
     * handle communication on separated thread
     */
    @Override
    public void run() {
        while(true){
            
        }
    }
}
