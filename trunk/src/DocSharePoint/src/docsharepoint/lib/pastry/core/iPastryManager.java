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

package docsharepoint.lib.pastry.core;

import rice.pastry.PastryNode;

/**
 * all managers (Scribe, PastryRing, Past) must implement this interface
 * @author George Karpouzas <gkarpouzas@webnetsoft.gr>
 */
public interface iPastryManager {
    /**
     * connect to pastry network
     * @param boothostip
     * @param bootport
     * @param bindport
     * @return boolean
     */
    public boolean connect(String boothostip, int bootport, int bindport);
    
    /**
     * disconnect from pastry network
     */
    public void disconnect();

    /**.
     * returns true if current machine is connected to pastry network
     * @return boolean, true if connected
     */
    public boolean isConnected();

    /**.
     * get bootstrap ip
     * @return String
     */
    public String getBootIP();

    /**.
     * get bootstrap port
     * @return int
     */
    public int getBootPort();

    /**.
     * set bootstrap ip
     * @param value hostname
     */
    public void setBootIP(final String value);

    /**.
     * set boot port
     * @param value boot port
     */
    public void setBootPort(final int value);

    /**.
     * get binded port
     * @return int
     */
    public int getBindPort();

    /**.
     * set binded port
     * @param value bind port
     */
    public void setBindPort(final int value);

    /**.
     * get handler to newly created pastry node
     * @return PastryNode
     */
    public PastryNode getNode();
}
