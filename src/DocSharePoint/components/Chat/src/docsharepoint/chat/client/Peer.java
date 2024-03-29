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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * represents a peer
 * @author George Karpouzas
 */
public class Peer {
    private Socket _socket;
    private PeerServer _server;
    private int _port;
    
    /**
     * constructor specifying socket
     * @param client 
     */
    public Peer(Socket client){
        this._socket = client;
        this._port = 11888;
        findport();
    }
    
    /**
     * get socket
     * @return Socket
     */
    public Socket getSocket(){
        return this._socket;
    }
    
    /**
     * get peer listening port
     * @return int
     */
    public int getListeningPort(){
        return this._port;
    }
    
    /**
     * get peer host-ip
     * @return String
     */
    public String getIP(){
        return this._socket.getRemoteSocketAddress().toString();
    }
    
    /**
     * close connection-socket
     * @throws IOException 
     */
    public void close() throws IOException{
        this._socket.close();
    }
    
    /**
     * get socket input stream
     * @return InputStream
     * @throws IOException 
     */
    public InputStream getInputStream() throws IOException{
        return this._socket.getInputStream();
    }
    
    /**
     * get socket output stream
     * @return OutputStream
     * @throws IOException 
     */
    public OutputStream getOutputStream() throws IOException{
        return this._socket.getOutputStream();
    }
    
    
    /**
     * object to string
     * @return String
     */
    @Override
    public String toString(){
        return this._socket.toString();
    }
    
    /**
     * start local peer chat server
     */
    public void startChatServer(){
        this._server = new PeerServer(this._port);
        this._server.start();
    }
    
    private void findport(){
        for(int i=this._port;i<65535;i++){
            try {
                ServerSocket server = new ServerSocket(i);
                server.close();
                this._port = i;
                return;
            } catch (IOException ex) {
                continue;
            }
        }
    }
}
