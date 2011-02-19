/*
 *  DocSharePoint
 *  Open Source Distributed p2p system based on pastry
 *  Copyright (C) 2010 DocSharePoint KARPOUZAS GEORGE
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

package docsharepoint.lib.pastry.appsocket;

import docsharepoint.lib.Messaging;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import org.mpisws.p2p.filetransfer.FileReceipt;
import org.mpisws.p2p.filetransfer.FileTransfer;
import org.mpisws.p2p.filetransfer.FileTransferCallback;
import org.mpisws.p2p.filetransfer.FileTransferImpl;
import rice.Continuation;
import rice.p2p.commonapi.Application;
import rice.p2p.commonapi.Endpoint;
import rice.p2p.commonapi.Id;
import rice.p2p.commonapi.IdFactory;
import rice.p2p.commonapi.Message;
import rice.p2p.commonapi.Node;
import rice.p2p.commonapi.NodeHandle;
import rice.p2p.commonapi.RouteMessage;
import rice.p2p.commonapi.appsocket.AppSocket;
import rice.p2p.commonapi.appsocket.AppSocketReceiver;
import rice.p2p.util.rawserialization.SimpleInputBuffer;
import rice.p2p.util.rawserialization.SimpleOutputBuffer;

/**
 * represents an appsocket application
 * @author George Karpouzas <gkarpouzas@webnetsoft.gr>
 */
public class dspSocketApplication implements Application{

    /**.
     * endpoint handler
     */
    private Endpoint endpoint;
    /**
     * our app node
     */
    private Node node;
    /**
     * file transferer
     */
    private FileTransfer fileTransfer;

    public dspSocketApplication(Node pnode, final IdFactory factory){
        //one app instance for the node
        endpoint = pnode.buildEndpoint(this, "dspinstance");
        node = pnode;
        
        // receiver interface
        endpoint.accept(new AppSocketReceiver() {
            public void receiveSocket(AppSocket socket) {
                fileTransfer = new FileTransferImpl(socket, new FileTransferCallback() {
                    
                    public void messageReceived(ByteBuffer bb) {
                        Messaging.Print2Console("Message received: "+bb);
                    }

                    public void fileReceived(File f, ByteBuffer metadata) {
                        try {
                            String originalFileName = new
                                    SimpleInputBuffer(metadata).readUTF();
                            File dest = new File("downloaded_"+originalFileName);

                            Messaging.Print2Console("Moving " + f + " to " +
                                    dest + " original:" + originalFileName);
                            f.renameTo(dest);
                            
                        } catch (IOException ioe) {
                            Messaging.Print2Console("Error deserializing file name. " + ioe);
                        }
                    }

                    public void receiveException(Exception ioe) {
                        Messaging.Print2Console("FTC.receiveException() "+ioe);
                    }

                }, dspSocketApplication.this.node.getEnvironment());

                fileTransfer.addListener(new dspFileTransferListener(dspSocketApplication.this));
                
                endpoint.accept(this);
            }

            public void receiveSelectResult(AppSocket socket, boolean canRead, boolean canWrite) {
                throw new RuntimeException("Shouldn't be called.");
            }

            public void receiveException(AppSocket as, Exception excptn) {
                Messaging.PrintError2Console(excptn.toString());
            }
            
        });

        // register after we have set the AppSocketReceiver
        endpoint.register();
    }
    
    /**
     * forward message
     * @param rm
     * @return boolean
     */
    public boolean forward(RouteMessage rm) {
        return true;
    }

    /**
     * deliver message
     * @param id
     * @param msg
     */
    public void deliver(Id id, Message msg) {
        Messaging.Print2Console(this+" received "+msg);
    }

    /**
     * update node
     * @param nh
     * @param bln
     */
    public void update(NodeHandle nh, boolean bln) {
    }

    /**.
     * get endpoint handle
     * @return Endpoint
     */
    public final Endpoint getEndpoint() {
        return endpoint;
    }

    /**
     * get node handle
     * @return Node
     */
    public final Node getNode() {
        return node;
    }

    /**
     * send message directly
     * @param nh
     */
    public void sendMyMsgDirect(NodeHandle nh, final File file2insert) {
        endpoint.connect(nh, new AppSocketReceiver() {

          /**
           * Called when the socket comes available.
           */
          public void receiveSocket(AppSocket socket) {
            // create the FileTransfer object
            FileTransfer sender = new FileTransferImpl(socket, null, node.getEnvironment());

            // add the listener
            sender.addListener(new dspFileTransferListener(dspSocketApplication.this));

            // Create a simple 4 byte message
            //ByteBuffer sendMe = ByteBuffer.allocate(4);
            //sendMe.put((byte)1);
            //sendMe.put((byte)2);
            //sendMe.put((byte)3);
            //sendMe.put((byte)4);

            // required when using a byteBuffer to both read and write
            //sendMe.flip();

            // Send the message
            //Messaging.Print2Console("Sending "+sendMe);
            //sender.sendMsg(sendMe, (byte)1, null);

            try {

              // make sure it exists
              if (!file2insert.exists()) {
                Messaging.PrintError2Console("File "+file2insert+" does not exist.  Please create a file called "+file2insert+".");
                System.exit(1);
              }

              // serialize the filename
              SimpleOutputBuffer sob = new SimpleOutputBuffer();
              sob.writeUTF(file2insert.getName());

              // request transfer of the file with priority 2
              sender.sendFile(file2insert, sob.getByteBuffer(), (byte)2, new Continuation<FileReceipt, Exception>() {

                public void receiveException(Exception exception) {
                  Messaging.Print2Console("Error sending: "+file2insert+" "+exception);
                }

                public void receiveResult(FileReceipt result) {
                  Messaging.Print2Console("Send complete: "+result);
                }
              });

            } catch (IOException ioe) {
              Messaging.Print2Console(ioe.toString());
            }
          }

          /**
           * Called if there is a problem.
           */
          public void receiveException(AppSocket socket, Exception e) {
            Messaging.Print2Console(e.toString());
          }

          /**
           * Example of how to write some bytes
           */
          public void receiveSelectResult(AppSocket socket, boolean canRead, boolean canWrite) {
            throw new RuntimeException("Shouldn't be called.");
          }
        }, 30000);
      }
}
