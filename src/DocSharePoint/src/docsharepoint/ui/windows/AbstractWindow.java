/*
 *  DocSharePoint
 *  Open Source Distributed p2p system based on pastry
 *  Copyright (C) 2010 DocSharePoint KARPOUZAS GEORGE
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

package docsharepoint.ui.windows;

import docsharepoint.ui.arch.IWindow;
import javax.swing.JFrame;

/**.
 * abstract window
 * @author George Karpouzas
 */
public abstract class AbstractWindow extends JFrame implements IWindow {

    /**.
     * constructor specifying window title
     * @param title window title
     */
    public AbstractWindow(final String title) {
        setTitle(title);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    /**.
     * show window
     */
    public final void showWindow() {
        setVisible(true);
    }

    /**.
     * close window
     */
    public final void close() {
        setVisible(false);
        dispose();
    }
}
