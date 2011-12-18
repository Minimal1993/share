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

package docsharepoint.lib.arch;

/**.
 * represents a pastry network-ring
 * @author George Karpouzas
 */
public interface IPastryRing {
    /**.
     * pastry ring initialization
     * @param boothostip hostname
     * @param bootport boot port value
     * @param bindport bind port value
     * @return boolean
     */
    boolean connect(String boothostip, int bootport, int bindport);

    /**.
     * disconnect from pastry network
     */
    void disconnect();

    /**.
     * check if current machine is connected to pastry network
     * @return boolean, true if connected
     */
    boolean isConnected();

    /**.
     * get hostname or ip
     * @return String
     */
    String getBootIP();
    /**.
     * set hostname or ip
     * @param value hostname
     */
    void setBootIP(String value);

    /**.
     * get bind port
     * @return integer
     */
    int getBootPort();
    /**.
     * set boot port
     * @param value port value
     */
    void setBootPort(int value);

    /**.
     * get bind port
     * @return int
     */
    int getBindPort();
    /**.
     * set bind port
     * @param value port value
     */
    void setBindPort(int value);
}
