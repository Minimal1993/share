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

package docsharepoint;

import docsharepoint.app.DocSharePointApp;
import docsharepoint.ui.dialogs.ReportDialog;
import docsharepoint.ui.windows.MainWindow;

/**.
 * keep in this class all global configuration variables
 * @author George Karpouzas
 */
public final class AppConfig {
    /**
     * application instance
     */
    private static AppConfig _instance;
    /**
     * application version
     */
    private static String _version;
    /**
     * connection status
     */
    private static boolean _connected;
    /**
     * dsp application
     */
    private static DocSharePointApp _dspApplication;
    /**.
     * main window handle
     */
    private static MainWindow _mw;
    /**
     * report dialog
     */
    private static ReportDialog _reportArea;
    /*
     * print to console or not?
     */
    private static boolean _print2Console;
    /**
     * number of leaf set nodes
     */
    private static int L = 16;
    /**
     * number of neighborhood set nodes
     */
    private static int M = 16;
    /**
     * 2^b-1  2^b
     */
    private static int b = 4;
    /**
     * number of nodes - this number must change accordingly
     */
    private static int N = 32;
    /**
     * private constructor
     */
    private AppConfig() {
        initGlobals();
    }

    /**.
     * init variables
     */
     private void initGlobals() {
         _version = "0.9a";
         _connected = false;
         _dspApplication = new DocSharePointApp();
         _print2Console = false;
     }

    /**.
     * get app_config class instance
     * @return APP_CONFIG
     */
    public static AppConfig getInstance() {
        if (_instance == null) {
            _instance = new AppConfig();
        }
        return _instance;
    }

    /**
     * leaf set size - L in L/2
     * @return int
     */
    public int getL(){
        return L;
    }
    
    /**
     * neighborhood set size - M
     * @return int
     */
    public int getM(){
        return M;
    }
    
    /**
     * 2^b-1  2^b
     */
    public int getB(){
        return b;
    }
    /**
     * number of nodes in network;
     * @return int
     */
    public int getN(){
        return N;
    }
    /**.
     * get application version
     * @return String
     */
    public String getVersion() { return _version; }
    /**.
     * check if application is connected with p2p network
     * @return boolean, true if connected
     */
    public boolean isConnected() { return _connected; }
    /**.
     * set connected
     * @param value connected or not?
     */
    public void setConnected(final boolean value) { _connected = value; }
    /**.
     * get application handler
     * @return DocSharePointApp
     */
    public DocSharePointApp getApplication() { return _dspApplication; }
    /**.
     * get main window handle
     * @return MainWindow
     */
    public MainWindow getMainWindow() { return _mw; }
    /**
     * set main window
     * @param main
     */
    public void setMainWindow(MainWindow main) { _mw = main; }

    /**
     * get handle to report dialog
     * @return ReportDialog
     */
    public ReportDialog getReportDialog() { return _reportArea; }

    /**
     * set report dialog
     * @param rd
     */
    public void setReportDialog(ReportDialog rd) { _reportArea = rd; }
    /**
     * get status for printing to console
     * @return boolean
     */
    public boolean print2Console(){ return _print2Console; }
}
