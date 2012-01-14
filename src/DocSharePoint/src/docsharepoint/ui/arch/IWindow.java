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

package docsharepoint.ui.arch;

import docsharepoint.ui.Exceptions.WindowInitializationException;
import docsharepoint.ui.utils.Bounds;
import docsharepoint.ui.utils.Size;

/**.
 * interface that windows and dialogs must implement
 * @author George Karpouzas
 */
public interface IWindow {

    /**.
     * initialize window specifying bounds and listeners
     * @param bounds bounds
     * @return boolean true on success
     * @throws WindowInitializationException on failure
     */
    boolean init(Bounds bounds) throws WindowInitializationException;
    /**.
     * initialize window specifying only the size and listeners
     * @param size size
     * @return boolean true on successful initialization
     * @throws WindowInitializationException
     */
    boolean init(Size size) throws WindowInitializationException;

    /**.
     * open window - make visible
     */
    void showWindow();

    /**.
     * close window
     */
    void close();
}
