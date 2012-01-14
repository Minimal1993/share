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

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * represents the peer client
 * this client is running on each peer when the peer want to
 * connect to the chat server of another peer
 * @author developer
 */
public class PeerClient implements Runnable{
    private Socket _socket;
    private String _host;
    private int _port;
    private DataOutputStream _output;
    private DataInputStream _input;
    private JTextField _text;
    private JTextArea _area;
    
    /**
     * constructor providing server-host and server listening port
     * @param host
     * @param port 
     */
    public PeerClient(String host, int port, JTextArea area, JTextField text){
        
        // -----------------------------------------------------------
        // init
        // -----------------------------------------------------------
        this._host = host;
        this._port = port;
        this._area = area;
        this._text = text;
    }
    
    /**
     * connect to the server
     */
    public boolean connect(){
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
            return true;          
        } catch (UnknownHostException ex) {
            System.err.println("Error: Unable to connect to the server.");
            return false;
        } catch (IOException ex) {
            System.err.println("Error: Unable to connect to the server.");
            return false;
        }
    }

    /**
     * disconnect from server
     */
    public void disconnect(){
        try {
            this._socket.close();
        } catch (IOException ex) {}
    }
    
    /**
     * handle communication on separated thread
     */
    @Override
    public void run() {
        
        while(true){
            String message = "";
            try {
                
                // -----------------------------------------------------------
                // get message
                // -----------------------------------------------------------
                message = this._input.readUTF();
                this._area.append(":" + message + "\r\n");
                
            } catch (Exception ex) {
                System.exit(0);
            }
        }
    }
    
    public void sendMessage(String message){
        try {
            
            // -----------------------------------------------------------
            // send message to the server
            // -----------------------------------------------------------
            this._output.writeUTF(message);
            
            // -----------------------------------------------------------
            // clear text field
            // -----------------------------------------------------------
            this._text.setText("");
            
        } catch (IOException ex) {
            System.err.println("Error: Sending message to the server.");
        }
    }
}
