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

package docsharepoint.lib.pastry.scribe;

import docsharepoint.lib.Messaging;
import java.util.Collection;
import rice.p2p.commonapi.Application;
import rice.p2p.commonapi.CancellableTask;
import rice.p2p.commonapi.Endpoint;
import rice.p2p.commonapi.Id;
import rice.p2p.commonapi.Message;
import rice.p2p.commonapi.Node;
import rice.p2p.commonapi.NodeHandle;
import rice.p2p.commonapi.RouteMessage;
import rice.p2p.scribe.Scribe;
import rice.p2p.scribe.ScribeClient;
import rice.p2p.scribe.ScribeContent;
import rice.p2p.scribe.ScribeImpl;
import rice.p2p.scribe.Topic;
import rice.pastry.commonapi.PastryIdFactory;

/**
 * scribe client implementation
 * @author George Karpouzas <gkarpouzas@webnetsoft.gr>
 */
public class dspScribeClient implements ScribeClient, Application {
    private int sequenceNumber;
    private CancellableTask publishTask;
    private Scribe dspScribe;
    private Topic dspTopic;
    private Endpoint endpoint;
    private Node pnode;

    /**
     * constructor specifying node and topic title
     * @param node
     * @param TopicTitle
     */
    public dspScribeClient(Node node, String TopicTitle){
        sequenceNumber = 0;
        //one app instance for the node
        endpoint = node.buildEndpoint(this, "dspinstance");
        dspScribe = new ScribeImpl(node,"dspScribeInstance");
        //topic initialization and construction
        dspTopic = new Topic(new PastryIdFactory(node.getEnvironment()), TopicTitle);
        pnode = node;
        // now we can receive messages
        endpoint.register();
    }

    /**
     * is root?
     * @return boolean
     */
    public boolean isRoot() { return dspScribe.isRoot(dspTopic); }

    /**
     * get parent nodehandle handler
     * @return NodeHandle
     */
    public NodeHandle getParent() {
        //return ((ScribeImpl)dspScribe).getParent(dspTopic);
        return dspScribe.getParent(dspTopic);
    }

    /**
     * get children collection
     * @return NodeHandle[]
     */
    public Collection<NodeHandle> getChildren() {
        return dspScribe.getChildrenOfTopic(dspTopic);
    }

    /**
     * subscribe to topic
     */
    public void subscribe() {
        dspScribe.subscribe(dspTopic, this);
    }

    /**
     * start the publishtask
     */
    public void startPublishTask() {
        publishTask = endpoint.scheduleMessage(new dspScribeMessage(), 5000, 5000);
    }

    /**
     * called when an anycast message is received
     * @param topic
     * @param sc
     * @return boolean, true to stop message here else deliver message elsewhere
     */
    public boolean anycast(Topic topic, ScribeContent sc) {
        boolean returnValue = dspScribe.getEnvironment().getRandomSource().nextInt(3) == 0;
        Messaging.Print2Console("MyScribeClient.anycast("+topic+","+sc+"):"+returnValue);
        return returnValue;
    }

    /**
     * send multicast message
     */
    public void sendMulticast() {
        Messaging.Print2Console("Node "+endpoint.getLocalNodeHandle()+" broadcasting "+sequenceNumber);
        dspScribeContent myMessage = new dspScribeContent(endpoint.getLocalNodeHandle(), sequenceNumber);
        dspScribe.publish(dspTopic, myMessage);
        sequenceNumber++;
    }

    /**
     * send anycast message
     */
    public void sendAnycast() {
        Messaging.Print2Console("Node "+endpoint.getLocalNodeHandle()+" anycasting "+sequenceNumber);
        dspScribeContent myMessage = new dspScribeContent(endpoint.getLocalNodeHandle(), sequenceNumber);
        dspScribe.anycast(dspTopic, myMessage);
        sequenceNumber++;
    }

    /**
     * deliver a received published message
     * @param topic
     * @param sc
     */
    public void deliver(Topic topic, ScribeContent sc) {
        Messaging.Print2Console("dspScribeClient.deliver("+topic+","+sc+")");
        if (((dspScribeContent)sc).getNodeHandle() == null)
            Messaging.Print2Console(new Exception("Stack Trace").toString());
    }

    /**
     *
     * @param topic
     * @param nh
     */
    public void childAdded(Topic topic, NodeHandle nh) {}

    /**
     *
     * @param topic
     * @param nh
     */
    public void childRemoved(Topic topic, NodeHandle nh) {}

    /**
     * 
     * @param topic
     */
    public void subscribeFailed(Topic topic) {}

    /**
     * 
     * @param rm
     * @return boolean
     */
    public boolean forward(RouteMessage rm) {
        return true;
    }

    /**
     * 
     * @param id
     * @param msg
     */
    public void deliver(Id id, Message msg) {
        if (msg instanceof dspScribeMessage) {
            sendMulticast();
            sendAnycast();
        }
    }

    /**
     * 
     * @param nh
     * @param bln
     */
    public void update(NodeHandle nh, boolean bln) {}
}
