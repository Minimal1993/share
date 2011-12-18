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

package docsharepoint.ui.utils;

import docsharepoint.ui.Exceptions.PositionInvalidException;

/**.
 * represents component position
 * @author George Karpouzas
 */
public class Position {
    /**.
     * x value
     */
    private int x;
    /**.
     * y value
     */
    private int y;
    /**.
     * constructor specifying x and y
     * @param xx x coordinate
     * @param yy y coordinate
     * @throws PositionInvalidException
     */
    public Position(final int xx, final int yy)
            throws PositionInvalidException {
        if(xx<0 || yy<0) throw new PositionInvalidException();
        x = xx;
        y = yy;
    }
    /**.
     * set x
     * @param value x value
     */
    public void setX(int value) throws PositionInvalidException {
        if(value<0)throw new PositionInvalidException();
        x = value;
    }
    /**.
     * set y
     * @param value y value
     */
    public void setY(int value) throws PositionInvalidException {
        if(value<0)throw new PositionInvalidException();
        y = value;
    }
    /**.
     * get x
     * @return int
     */
    public int getX() { return x; }
    /**.
     * get y
     * @return int
     */
    public int getY() {return y; }
}
