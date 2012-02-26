
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
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * represents the peer client
 * this client is running on each peer when the peer want to
 * connect to the chat server of another peer
 * @author developer
 */
public class Messenger implements Runnable{
    private Socket _socket;
    private String _host;
    private int _port;
    private DataOutputStream _output;
    private Message _msg;
    
    /**
     * constructor providing server-host and server listening port
     * @param host
     * @param port 
     */
    public Messenger(String host, int port, Message msg){
        
        // -----------------------------------------------------------
        // init
        // -----------------------------------------------------------
        this._host = host;
        this._port = port;
        this._msg = msg;
    }
    
    /**
     * connect to the server
     */
    @Override
    public void run(){
        try {
            
            // -----------------------------------------------------------
            // connect to server
            // -----------------------------------------------------------
            this._socket = new Socket(this._host, this._port);            
            AppConfig.Instance().getReportDialog().LogMessage("Connected to peer..");
            
            // -----------------------------------------------------------
            // get output stream
            // -----------------------------------------------------------
            this._output = new DataOutputStream(this._socket.getOutputStream());
            
            try {
                this._output.writeUTF(this._msg.getMessage());
            
            } catch (IOException ex) {
                AppConfig.Instance().getReportDialog().LogMessage("Error: Sending message to the server.");
            }
            
            this._socket.close();
            
        } catch (UnknownHostException ex) {
            AppConfig.Instance().getReportDialog().LogMessage("Error: Unable to connect to the server.");
        } catch (IOException ex) {
            AppConfig.Instance().getReportDialog().LogMessage("Error: Unable to connect to the server.");
        }
    }
}
