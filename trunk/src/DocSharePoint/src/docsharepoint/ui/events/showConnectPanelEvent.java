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
import docsharepoint.ui.panels.ConnectPanel;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;

/**.
 * connection event - click
 * @author George Karpouzas
 */
public class showConnectPanelEvent implements ClickEvent {
    /**.
     * array of images
     */
    private ImageIcon[] images;
    /**.
     * constructor specifying connect and disconnect icons
     * @param imgs array of images
     */
    public showConnectPanelEvent(final ImageIcon[] imgs) {
        this.images = imgs;
    }

    /**.
     * do something!
     * @param e action event
     */
    public final void actionPerformed(final ActionEvent e) {
        ConnectPanel cp = new ConnectPanel();
        cp.init(AppConfig.getInstance().
                getMainWindow().getMainPanel().getPanelSize());
        AppConfig.getInstance().getMainWindow().getMainPanel().addPanel(cp);
        cp.revalidate();
        cp.repaint();
    }
}
