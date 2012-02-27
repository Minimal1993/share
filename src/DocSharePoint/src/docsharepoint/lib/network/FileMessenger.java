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
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * sends files
 * @author George Karpouzas
 */
public class FileMessenger implements Runnable{
    private  File _file;
    private String _host;
    private int _port;
    private Socket _socket;
    
    /**
     * 
     * @param host
     * @param port
     * @param file 
     */
    public FileMessenger(String host, int port, File file){
        this._file = file;
        this._host = host;
        this._port = port;
    }
    
    @Override
    public void run() {
        try {
            
            this._socket = new Socket(this._host, this._port);            
            
            byte [] mybytearray  = new byte [(int)this._file.length()];
            FileInputStream fis = new FileInputStream(this._file);
            BufferedInputStream bis = new BufferedInputStream(fis);
            
            bis.read(mybytearray,0,mybytearray.length);
            OutputStream os = this._socket.getOutputStream();
            AppConfig.Instance().getReportDialog().LogMessage("Sending file..");
            os.write(mybytearray,0,mybytearray.length);
            os.flush();
            this._socket.close();
            
        } catch (UnknownHostException ex) {
            AppConfig.Instance().getReportDialog().LogMessage("Error: Unable to connect to the server.");
        } catch (IOException exe) {
            AppConfig.Instance().getReportDialog().LogMessage("Error: Unable to connect to the server.");
        }
    }
    
}
