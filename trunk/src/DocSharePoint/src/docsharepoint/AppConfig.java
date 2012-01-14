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
import docsharepoint.lib.pastry.past.dspPastManager;
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
    private static AppConfig instance;
    /**
     * application version
     */
    private static String version;
    /**
     *
     */
    private static boolean connected;
    /**
     * dsp application
     */
    private static DocSharePointApp dspApplication;
    /**.
     * main window handle
     */
    private static MainWindow mw;
    /**
     * report dialog
     */
    private static ReportDialog reportArea;
    /*
     * print to console or not?
     */
    private static boolean print2Console;
    /**
     * socketapp manager
     */
    private static dspPastManager manager;
    
    /**.
     * private constructor
     */
    private AppConfig() {
        initGlobals();
    }

    /**.
     * init variables
     */
     private void initGlobals() {
         version = "0.7.30.15a";
         connected = false;
         dspApplication = new DocSharePointApp();
         print2Console = false;
         manager = new dspPastManager();
     }

    /**.
     * get app_config class instance
     * @return APP_CONFIG
     */
    public static AppConfig getInstance() {
        if (instance == null) {
            instance = new AppConfig();
        }
        return instance;
    }

    /**.
     * get application version
     * @return String
     */
    public String getVersion() { return version; }
    /**.
     * check if application is connected with p2p network
     * @return boolean, true if connected
     */
    public boolean isConnected() { return connected; }
    /**.
     * set connected
     * @param value connected or not?
     */
    public void setConnected(final boolean value) { connected = value; }
    /**.
     * get application handler
     * @return DocSharePointApp
     */
    public DocSharePointApp getApplication() { return dspApplication; }
    /**.
     * get main window handle
     * @return MainWindow
     */
    public MainWindow getMainWindow() { return mw; }
    /**
     * set main window
     * @param main
     */
    public void setMainWindow(MainWindow main) { mw = main; }

    /**
     * get handle to report dialog
     * @return ReportDialog
     */
    public ReportDialog getReportDialog() { return reportArea; }

    /**
     * set report dialog
     * @param rd
     */
    public void setReportDialog(ReportDialog rd) { reportArea = rd; }
    /**
     * get status for printing to console
     * @return boolean
     */
    public boolean print2Console(){ return print2Console; }
    /**
     * get reference to pastry application manager
     * @return dspSocketAppManager
     */
    public dspPastManager getPastryApp(){ return manager; }
}
