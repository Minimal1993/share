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

package docsharepoint.lib.arch;

import rice.p2p.commonapi.Id;
import rice.p2p.commonapi.Message;

/**.
 * represents a pastry message
 * @author George Karpouzas <gkarpouzas@webnetsoft.gr>
 */
public interface IMessage extends Message {
    /**.
     * message priority
     * @return int
     */
    int getPriority();
    /**.
     * get sender Id
     * @return Id
     */
    Id getFrom();
    /**.
     * get receiver Id
     * @return Id
     */
    Id getTo();
    /**.
     * set sender
     * @param value node id
     */
    void setFrom(Id value);
    /**.
     * set receiver
     * @param value node id
     */
    void setTo(Id value);
}
