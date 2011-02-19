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

package docsharepoint.ui.utils;

/**.
 * represents a component's bounds
 * @author George Karpouzas
 */
public class Bounds {
    /**.
     * size
     */
    private Size size;
    /**.
     * position
     */
    private Position position;
    /**.
     * constructor specifying position and size
     * @param pos position
     * @param siz size
     */
    public Bounds(final Position pos, final Size siz) {
        size = siz;
        position = pos;
    }
    /**.
     * set size
     * @param value size
     */
    public final void setSize(final Size value) { size = value; }
    /**.
     *  set position
     * @param value position
     */
    public final void setPosition(final Position value) { position = value; }
    /**.
     * get size
     * @return Size
     */
    public final Size getSize() { return size; }
    /**.
     * get position
     * @return Position
     */
    public final Position getPosition() { return position; }
}
