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

package docsharepoint.ui.dialogs;

import docsharepoint.ui.arch.IWindow;
import javax.swing.JDialog;

/**.
 * abstract dialog
 * @author George Karpouzas
 */
public abstract class AbstractDialog extends JDialog implements IWindow {
    /**.
     * constructor specifying dialog title
     * @param title dialog title
     */
    public AbstractDialog(final String title) {
        setTitle(title);
        setResizable(false);
        setModal(true);
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
