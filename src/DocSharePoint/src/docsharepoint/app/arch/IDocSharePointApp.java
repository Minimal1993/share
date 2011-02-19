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

package docsharepoint.app.arch;

import docsharepoint.app.Exceptions.ApplicationStartupException;

/**.
 * represents a docsharepoint application
 * @author George Karpouzas
 */
public interface IDocSharePointApp {
    /**.
     * start docsharepoint application - open ui init pastry etc
     * @throws ApplicationStartupException
     */
    public void start() throws ApplicationStartupException;

    /**
     * stop all and exit
     */
    public void end();
}
