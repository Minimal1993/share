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

package docsharepoint.ui.events;

import docsharepoint.AppConfig;
import docsharepoint.lib.NodeId;
import docsharepoint.lib.ring.Node;
import docsharepoint.ui.Exceptions.InvalidIPAddressException;
import docsharepoint.ui.components.Button;
import docsharepoint.ui.panels.ConnectPanel;
import java.awt.event.ActionEvent;

/**
 * connection event
 * @author George Karpouzas <gkarpouzas@webnetsoft.gr>
 */
public class ConnectEvent implements ClickEvent {
    private ConnectPanel cp;
    
    /**
     * constructor specifying connect panel
     * @param connectpanel
     */
    public ConnectEvent(ConnectPanel connectpanel){
        cp = connectpanel;
    }
    
    /**
     * action
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        Button but = (Button) obj;
        if(but.getText().compareTo("Connect")==0) {
            if(!cp.getBootHost().isEmpty() && cp.getBootPort()!=-1){
                Node n;
                NodeId nodeid = null;
                
                //-------------------------------------------------------
                // create new node locally to join a pastry network or
                // create a new pastry network
                //-------------------------------------------------------
                try{
                    n = new Node(cp.getBootPort());
                    nodeid = n.pastryInit(cp.getBootHost(), cp.getBootPort(), cp.createNewNetwork());
                }
                catch(InvalidIPAddressException ipac){
                    AppConfig.Instance().getReportDialog().LogMessage("Node has been initialized");
                }
                
                AppConfig.Instance().getReportDialog().LogMessage("Node ID + " + nodeid);
                AppConfig.Instance().setConnected(true);
                but.setText("Disconnect");
            }
        }
        else{
            //AppConfig.getInstance().getPastryApp().disconnect();
            AppConfig.Instance().setConnected(false);
            but.setText("Connect");
        }
        
    }

}
