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

package docsharepoint.lib.pastry;

import docsharepoint.lib.Messaging;
import docsharepoint.lib.arch.IApplication;
import rice.p2p.commonapi.Endpoint;
import rice.p2p.commonapi.Id;
import rice.p2p.commonapi.Message;
import rice.p2p.commonapi.Node;
import rice.p2p.commonapi.NodeHandle;
import rice.p2p.commonapi.RouteMessage;
import rice.pastry.PastryNode;

/**.
 * represents a pastry application
 * @author George Karpouzas <gkarpouzas@webnetsoft.gr>
 */
public class PastryApplication implements IApplication {
    /**.
     * endpoint handler
     */
    private Endpoint endpoint;
    /**
     * our app node
     */
    private Node node;
    
    /**.
     * constructor specifying active node
     * @param pnode pastry node handle
     */
    public PastryApplication(final PastryNode pnode) {
        //one app instance for the node
        endpoint = pnode.buildEndpoint(this, "dspinstance");

        //node pointer
        node = pnode;
        
        //start receiving messages
        endpoint.register();
    }

    /**.
     * forward message
     * @param rm message
     * @return boolean, true if message forwarded successfully
     */
    public final boolean forward(final RouteMessage rm) { return true; }

    /**.
     * called when a message is received
     * @param id node id
     * @param msg message
     */
    public final void deliver(final Id id, final Message msg) {
        Messaging.Print2Console("Receiving message " + msg + " from " + id);
    }

    /**.
     * Called when a new neighbor arrives
     * @param nh node handle
     * @param bln true if
     */
    public void update(final NodeHandle nh, final boolean bln) { }

    /**.
     * get endpoint handle
     * @return Endpoint
     */
    public final Endpoint getEndpoint() {
        return endpoint;
    }

    /**.
     * directly send a message to the Node Handle
     * @param nh node handle
     */
    public final void routeMyMsgDirect(final NodeHandle nh) {
        Messaging.Print2Console(this + " sending direct to " + nh);
        endpoint.route(null,
                new PastryMessage(endpoint.getId(), nh.getId()), nh);
    }

    /**.
     * route a message to the id
     * @param id node id
     */
    public final void routeMyMsg(final Id id) {
        Messaging.Print2Console(this + " sending to " + id);
        endpoint.route(id,
                new PastryMessage(endpoint.getId(), id), null);
    }

    /**
     * get node handle
     * @return Node
     */
    public final Node getNode() {
        return node;
    }
}
