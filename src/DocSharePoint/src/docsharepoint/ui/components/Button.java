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

package docsharepoint.ui.components;

import docsharepoint.ui.arch.IComponent;
import docsharepoint.ui.events.ClickEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

/**.
 * represents a button
 * @author George Karpouzas
 */
public class Button extends JButton implements IComponent {

    /**.
     * constructor specifying title, tooltip and click event
     * @param title component title
     * @param tooltip component tooltip text
     * @param clickevent action
     */
    public Button(final String title,
            final String tooltip, final ClickEvent clickevent) {
        super(title);
        setToolTipText(tooltip);
        addActionListener(clickevent);
    }

    /**.
     * constructor specifying icon, tooltip and click event
     * @param icon button icon
     * @param tooltip button tooltip text
     * @param clickevent action
     */
    public Button(final ImageIcon icon,
            final String tooltip, final ClickEvent clickevent) {
        super(icon);
        setToolTipText(tooltip);
        addActionListener(clickevent);
    }

    /**.
     * constructor specifying title, icon, tooltip and click event
     * @param title button title
     * @param icon button icon
     * @param tooltip tooltip text
     * @param clickevent action
     */
    public Button(final String title, final ImageIcon icon,
            final String tooltip, final ClickEvent clickevent) {
        super(title, icon);
        setVerticalTextPosition(SwingConstants.BOTTOM);
        setHorizontalTextPosition(SwingConstants.CENTER);
        setToolTipText(tooltip);
        addActionListener(clickevent);
    }
}
