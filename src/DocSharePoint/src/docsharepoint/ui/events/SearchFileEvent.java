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

package docsharepoint.ui.events;

import docsharepoint.AppConfig;
import docsharepoint.lib.Messaging;
import docsharepoint.ui.panels.SearchPanel;
import java.awt.event.ActionEvent;
import rice.p2p.commonapi.Id;

/**.
 * represents the search file click event
 * @author George Karpouzas
 */
public class SearchFileEvent implements ClickEvent {
    private SearchPanel _panel;

    /**
     * constructor specifying shared panel
     * @param panel
     */
    public SearchFileEvent(SearchPanel panel){
        _panel = panel;
    }

    /**.
     * do something
     * @param e action event
     */
    public final void actionPerformed(final ActionEvent e) {
        if(AppConfig.getInstance().isConnected()){
            Id val = AppConfig.getInstance().getPastryApp().getKey(_panel.getFileName());
            if(val!=null){
                AppConfig.getInstance().getPastryApp().search(
                    val,
                    _panel.getPath());
                Messaging.ShowMessage("File has been downloaded succesfully", "Download File");
            }
            else Messaging.ShowMessage("File has not been found", "Download File");
        }
        else
            Messaging.Print2Console("Not connected to Pastry Network");
    }
}
