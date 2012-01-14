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

package docsharepoint.lib.pastry;

import docsharepoint.lib.arch.IMessage;
import rice.p2p.commonapi.Id;
import rice.p2p.commonapi.Message;

/**.
 * represents a pastry message
 * @author George Karpouzas <gkarpouzas@webnetsoft.gr>
 */
public class PastryMessage implements IMessage {
    /**.
     * sender
     */
    private Id from;
    /**.
     * receiver
     */
    private Id to;

    /**.
     * constructor specifying receiver and sender id's
     * @param sender sender
     * @param receiver receiver
     */
    public PastryMessage(final Id sender, final Id receiver) {
        this.from = sender;
        this.to = receiver;
    }

    /**.
     * message priority
     * @return int
     */
    public final int getPriority() {
        return Message.LOW_PRIORITY;
    }
    /**.
     * get sender Id
     * @return Id node id
     */
    public final Id getFrom() {
        return from;
    }
    /**.
     * get receiver Id
     * @return Id
     */
    public final Id getTo() {
        return to;
    }
    /**.
     * set sender
     * @param value node id
     */
    public final void setFrom(final Id value) {
        from = value;
    }
    /**.
     * set receiver
     * @param value node id
     */
    public final void setTo(final Id value) {
        to = value;
    }
}
