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

package docsharepoint.app;

import docsharepoint.AppConfig;
import docsharepoint.app.Exceptions.ApplicationStartupException;
import docsharepoint.system.Monitor;
import docsharepoint.ui.Exceptions.PositionInvalidException;
import docsharepoint.ui.Exceptions.SizeInvalidException;
import docsharepoint.ui.Exceptions.WindowInitializationException;
import docsharepoint.ui.dialogs.ReportDialog;
import docsharepoint.ui.utils.Bounds;
import docsharepoint.ui.utils.Position;
import docsharepoint.ui.utils.Size;
import docsharepoint.ui.windows.MainWindow;

/**
 * represents the pastry application
 * @author George Karpouzas <gkarpouzas@webnetsoft.gr>
 */
public class DocSharePointApp{

    /**.
     * start application
     * @throws ApplicationStartupException thrown on failure
     */
    public final void start() throws ApplicationStartupException {
        AppConfig.Instance().setMainWindow(new MainWindow());
        AppConfig.Instance().setReportDialog(new ReportDialog());
        int initial_width = 600;
        int initial_height = 400;
        
        try {
            int x = Monitor.getCenter(new Size(initial_width, initial_height)).getX() 
                    + initial_width;
            int y = Monitor.getCenter(new Size(initial_width, initial_height)).getY();
            if(AppConfig.Instance().getMainWindow().init(
                    new Bounds(new Position(x - initial_width - 200, y), 
                    new Size(initial_width, initial_height))) &&
                    AppConfig.Instance().getReportDialog().init(
                    new Bounds(new Position(x-180, y),
                    new Size(410, initial_height)))){
                AppConfig.Instance().getReportDialog().showWindow();
                AppConfig.Instance().getMainWindow().showWindow();
            }
            else end();
        } catch (SizeInvalidException | PositionInvalidException | WindowInitializationException pie) {
            end();
        }
    }

    /**
     * shutdown application
     */
    public void end() {
        System.exit(0);
    }
}
