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

import docsharepoint.ui.Exceptions.SizeInvalidException;

/**.
 * represents component size
 * @author George Karpouzas
 */
public class Size {
    /**.
     * width
     */
    private int width;
    /**.
     * height
     */
    private int height;

    /**.
     * constructor specifying width and height
     * @param w width value
     * @param h height value
     * @throws SizeInvalidException if size is invalid
     */
    public Size(final int w, final int h) throws SizeInvalidException {
        if(w<0 || h<0) throw new SizeInvalidException();
        width = w;
        height = h;
    }

    /**.
     * set width
     * @param value width
     * @throws SizeInvalidException
     */
    public void setWidth(int value) throws SizeInvalidException{
        if(value<0)throw new SizeInvalidException();
        width = value;
    }

    /**.
     * set height
     * @param value height
     * @throws SizeInvalidException
     */
    public void setheight(int value) throws SizeInvalidException{
        if(value<0)throw new SizeInvalidException();
        height = value;
    }
    /**.
     * get width
     * @return int
     */
    public int getWidth() { return width; }
    /**.
     * get height
     * @return int
     */
    public int getHeight() {return height; }
}
