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

package docsharepoint.system;

import docsharepoint.ui.Exceptions.PositionInvalidException;
import docsharepoint.ui.utils.Position;
import docsharepoint.ui.utils.Size;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

/**.
 * represents a monitor
 * @author George Karpouzas <gkarpouzas@webnetsoft.gr>
 */
public class Monitor {

    /**.
     * private constructor
     */
    private Monitor() { }
    /**.
     * get center of the screen coordinates
     * @param size size of window
     * @return Position
     * @throws PositionInvalidException thrown on invalid position values
     */
    public static Position getCenter(Size size)
            throws PositionInvalidException {
        GraphicsEnvironment ge =
                GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] gd = ge.getScreenDevices();

        //find biggest screen
        int maxWidth = gd[0].getDisplayMode().getWidth();
        int maxHeight = gd[0].getDisplayMode().getHeight();

        for (int i=0; i<gd.length; i++) {
            DisplayMode dm = gd[i].getDisplayMode();
            if(dm.getWidth()>maxWidth){
                maxWidth = dm.getWidth();
                maxHeight = dm.getHeight();
            }
        }
        return new Position((int)
                (maxWidth - size.getWidth()) / 2,
                (int) (maxHeight - size.getHeight() ) / 2);
    }
}
