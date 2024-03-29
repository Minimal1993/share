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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.*;

/**
 * represents a peer on the network
 * 
 * @author George Karpouzas
 */
public class Client extends JFrame implements Runnable{
    private Socket _socket;
    private Peer _peer;
    private String _host;
    private int _port;
    private DataOutputStream _output;
    private DataInputStream _input;
    
    private JPanel _panel;
    private JTextArea _area;
    private JTextField _text;
    private JButton _button;
    
    private PeerClient _client;
    
    /**
     * constructor providing server-host and server listening port
     * @param host
     * @param port 
     */
    public Client(String host, int port){
        
        // -----------------------------------------------------------
        // interface
        // -----------------------------------------------------------
        this.setTitle("P2P Chat");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this._panel = new JPanel();
        this._panel.setLayout(null);
        
        this._area = new JTextArea();
        JScrollPane scroll = new JScrollPane(this._area);
        this._area.setEditable(false);
        this._text = new JTextField();
        this._button = new JButton("Send");
        this.getRootPane().setDefaultButton(this._button);
        
        this._panel.add(scroll);
        this._panel.add(this._text);
        this._panel.add(this._button);
        
        this.addWindowFocusListener(new WindowAdapter() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                _text.requestFocusInWindow();
            }
        });

        
        scroll.setBounds(5, 5, 300, 200);
        this._text.setBounds(5, 210, 300, 20);
        this._button.setBounds(315, 210, 80, 20);
        
        this._button.addActionListener(new ActionListener(){

            /**
             * do something!
             */
            @Override
            public void actionPerformed(ActionEvent ae) {
                sendMessage(_text.getText());
            }
            
        });
        
        this.setBounds(200, 200, 430, 280);
        this.setContentPane(this._panel);
        this.setVisible(true);
        
        
        // -----------------------------------------------------------
        // init
        // -----------------------------------------------------------
        this._host = host;
        this._port = port;
        this._client = null;
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
            
            
            // -----------------------------------------------------------
            // create a local peer
            // -----------------------------------------------------------
            this._peer = new Peer(this._socket);
            
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
            
            
            // -----------------------------------------------------------
            // start peers server for chating
            // -----------------------------------------------------------
            this._peer.startChatServer();
                        
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
            String message = "";
            try {
                
                // -----------------------------------------------------------
                // get message
                // -----------------------------------------------------------
                message = this._input.readUTF();
                if(message.matches("#connect [0-9]{8,}:[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}:[0-9]{1,5}") && 
                    this._client==null){
                    
                    // -----------------------------------------------------------
                    // get requested ip and port
                    // -----------------------------------------------------------
                    message = message.replace("#connect ", "");
                    String host = message.split(":\\s*")[1];
                    int port = Integer.parseInt(message.split(":\\s*")[2]);
                
                    this.connect2chatserver(host, port);
                }
                else
                    this._area.append(message + "\r\n");
                
            } catch (Exception ex) {
                System.exit(0);
            }
        }
    }
    
    private void sendMessage(String message){
        try {
            // -----------------------------------------------------------
            // send message to the server only if message is prefixed with #
            // -----------------------------------------------------------
            if(message.startsWith("#"))
                
                // -----------------------------------------------------------
                //send message to decentralized server
                // -----------------------------------------------------------
                this._output.writeUTF(message);
            else if(message.matches("!connect [0-9]{8,}:[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}:[0-9]{1,5}") && 
                    this._client==null){
                
                // -----------------------------------------------------------
                // get requested ip and port
                // -----------------------------------------------------------
                message = message.replace("!connect ", "");
                long id = Long.parseLong(message.split(":\\s*")[0]);
                String host = message.split(":\\s*")[1];
                int port = Integer.parseInt(message.split(":\\s*")[2]);
        
                if(this.connect2chatserver(host, port)){
                    
                    // -----------------------------------------------------------
                    // request from server to inform the other peer (chat server)
                    // for the connection and force it to connect its client to the
                    // server too in order to chat.
                    // -----------------------------------------------------------
                    this._output.writeUTF("#connect "+id+":"+host+":"+port);
                
                }
            }
            else if(message.compareTo("!disconnect")==0){
                this._client.disconnect();
                this._client = null;
            }
            else{
                if(this._client!=null) 
                    this._client.sendMessage(message);
            }
            
            
            // -----------------------------------------------------------
            // clear text field
            // -----------------------------------------------------------
            this._text.setText("");
            
        } catch (IOException ex) {
            System.err.println("Error: Sending message to the server.");
        }
    }
    
    private boolean connect2chatserver(String host, int port){

        // -----------------------------------------------------------
        //connect to peer's chat server
        // -----------------------------------------------------------
        this._area.append("connecting to " + host + ", port " + port + "..\r\n");
        this._client = new PeerClient(host, port, this._area, this._text);
        if(this._client.connect()){
            this._area.append("connected!\r\n");
            return true;
        }
        else{
            this._area.append("Unable to connect.\r\n");
            return false;
        }
    }
}
