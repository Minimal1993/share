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

import docsharepoint.lib.network.FileMessenger;
import docsharepoint.ui.panels.SharedPanel;
import java.awt.event.ActionEvent;

/**.
 * represents the insert file click event
 * @author George Karpouzas
 */
public class InsertFileEvent implements ClickEvent {
    private SharedPanel _panel;
    private int fileKey;
    
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
    @Override
    public final void actionPerformed(final ActionEvent e) {
        //FileMessenger fmessenger = new FileMessenger(host, port, filename);
    }
}
