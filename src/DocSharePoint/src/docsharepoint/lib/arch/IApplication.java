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

import rice.p2p.commonapi.Application;
import rice.p2p.commonapi.Endpoint;
import rice.p2p.commonapi.Id;
import rice.p2p.commonapi.Node;
import rice.p2p.commonapi.NodeHandle;

/**.
 * application based on pastry network
 * @author George Karpouzas
 */
public interface IApplication extends Application {

    /**.
     * directly send a message to the Node Handle
     * @param nh node handler
     */
    void routeMyMsgDirect(NodeHandle nh);

    /**.
     * route a message to the id
     * @param id node id
     */
    void routeMyMsg(Id id);
    /**.
     * get handle to endpoint
     * @return Endpoint
     */
    Endpoint getEndpoint();
    /**
     * our node
     * @return Node
     */
    Node getNode();
}

    /**.
     * send object to nearest k-1 pastry nodes
     * @param object
     * @param id
     * @return boolean
     */
    //public boolean InsertObject(iTransactionObject object, iNodeID id);

    /**
     * send object to specific ip address
     * @param object
     * @param IPAddress
     * @return boolean
     */
    //public boolean SendObject(iTransactionObject object, String IPAddress);

    /**
     * delete object
     * @param id
     * @return boolean
     */
    //public boolean DeleteObject(iObjectID id);

    /**
     * search for an object
     * @param id
     * @return iTransactionObject
     */
    //public iTransactionObject SearchObject(iObjectID id);
