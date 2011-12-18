/*
 *  DocSharePoint
 *  Open Source Distributed p2p system based on pastry
 *  Copyright (C) 2010-2011 DocSharePoint KARPOUZAS GEORGE
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

package docsharepoint.ui.events;

import docsharepoint.AppConfig;
import docsharepoint.lib.Messaging;
import docsharepoint.ui.panels.SharedPanel;
import java.awt.event.ActionEvent;
import java.io.File;
import rice.p2p.commonapi.Id;

/**.
 * represents the insert file click event
 * @author George Karpouzas
 */
public class InsertFileEvent implements ClickEvent {
    private SharedPanel _panel;
    private Id fileKey;
    
    /**
     * constructor specifying shared panel
     * @param panel
     */
    public InsertFileEvent(SharedPanel panel){
        _panel = panel;
    }

    /**.
     * do something
     * @param e action event
     */
    public final void actionPerformed(final ActionEvent e) {
        if(AppConfig.getInstance().isConnected()){
            fileKey = AppConfig.getInstance().getPastryApp().insert(
                _panel.getFileNameIdentifier(),
                _panel.getNumOfReplications(),
                new File(_panel.getFilePath()));
            if(fileKey!=null)
                Messaging.ShowMessage("File has been uploaded succesfully", "Upload File");
            else
                Messaging.ShowErrorMessage("File hasn't been uploaded.", "Upload File");
        }
        else
            Messaging.Print2Console("Not connected to Pastry Network");
    }
}
