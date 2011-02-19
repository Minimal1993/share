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

package docsharepoint.ui.panels;

import docsharepoint.ui.arch.IPanel;
import java.awt.LayoutManager;
import javax.swing.JPanel;

/**.
 * abstract panel
 * @author George Karpouzas
 */
public abstract class AbstractPanel extends JPanel implements IPanel {
    /**.
     * constructor specifying layout manager
     * @param layoutmanager layout manager
     */
    public AbstractPanel(final LayoutManager layoutmanager) {
        this.setLayout(layoutmanager);
    }
}
