/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package docsharepoint.lib.pastry.appsocket;

import docsharepoint.lib.Messaging;
import org.mpisws.p2p.filetransfer.BBReceipt;
import org.mpisws.p2p.filetransfer.FileReceipt;
import org.mpisws.p2p.filetransfer.FileTransferListener;
import org.mpisws.p2p.filetransfer.Receipt;

/**
 *
 * @author George Karpouzas
 */
public class dspFileTransferListener implements FileTransferListener{
    private dspSocketApplication _dspSA;

    /**
     * constructor specifying socket application
     * @param dspSA
     */
    public dspFileTransferListener(dspSocketApplication dspSA){
        _dspSA = dspSA;
    }

    /**
     * file transfer
     * @param receipt
     * @param bytesTransferred
     * @param total
     * @param incoming
     */
    public void fileTransferred(FileReceipt receipt,
        long bytesTransferred, long total, boolean incoming) {
        String s;
        if (incoming) {
            s = " Downloaded ";
        } else {
            s = " Uploaded ";
        }
        double percent = 100.0*bytesTransferred/total;
        Messaging.Print2Console(_dspSA+s+percent+"% of "+receipt);
    }

    /**
     * message transfer
     * @param receipt
     * @param bytesTransferred
     * @param total
     * @param incoming
     */
    public void msgTransferred(BBReceipt receipt, int bytesTransferred,
        int total, boolean incoming) {
        String s;
        if (incoming) {
            s = " Downloaded ";
        } else {
            s = " Uploaded ";
        }
        double percent = 100.0*bytesTransferred/total;
        Messaging.Print2Console(_dspSA+s+percent+"% of "+receipt);
    }

    /**
     * transfer cancelled
     * @param receipt
     * @param incoming
     */
    public void transferCancelled(Receipt receipt, boolean incoming) {
        String s;
        if (incoming) {
            s = "download";
        } else {
            s = "upload";
        }
        Messaging.Print2Console(_dspSA+": Cancelled "+s+" of "+receipt);
    }

    /**
     * transfer failed
     * @param receipt
     * @param incoming
     */
    public void transferFailed(Receipt receipt, boolean incoming) {
        String s;
        if (incoming) {
            s = "download";
        } else {
            s = "upload";
        }
        Messaging.Print2Console(_dspSA+": Transfer Failed "+s+" of "+receipt);
    }

}
