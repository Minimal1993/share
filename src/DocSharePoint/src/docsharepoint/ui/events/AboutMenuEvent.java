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

import docsharepoint.lib.Messaging;
import docsharepoint.ui.Exceptions.SizeInvalidException;
import docsharepoint.ui.Exceptions.WindowInitializationException;
import docsharepoint.ui.dialogs.AboutDialog;

import docsharepoint.ui.utils.Size;
import java.awt.event.ActionEvent;

/**.
 * about menu click event
 * @author George Karpouzas
 */
public class AboutMenuEvent implements ClickEvent {

    /**.
     * do something
     * @param e actionevent
     */
    public final void actionPerformed(final ActionEvent e) {
        final AboutDialog about = new AboutDialog();
        final int width = 400;
        final int height = 360;
        try {
            ClickEvent clickevent = new ClickEvent() {
                public void actionPerformed(final ActionEvent e) {
                    about.close(); } };
            if (about.init(new Size(width, height))) {
                about.show();
            }
        } catch (SizeInvalidException pie) {
            Messaging.PrintError2Console(pie.toString());
            System.exit(1);
        } catch (WindowInitializationException wie) {
            Messaging.PrintError2Console(wie.toString());
            System.exit(1);
        }
    }

}
