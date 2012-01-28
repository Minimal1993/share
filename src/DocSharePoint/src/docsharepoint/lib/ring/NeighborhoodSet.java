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
package docsharepoint.lib.ring;

import docsharepoint.AppConfig;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * represents node's neighborhood
 * @author Karpouzas George
 */
public class NeighborhoodSet {
    private ArrayList<Node> _list;
    
    /**
     * constructor
     * @param main 
     */
    public NeighborhoodSet(){
        this._list = new ArrayList<Node>();
    }
    
    /**
     * add new node
     * @param n 
     */
    public void add(Node n){
        if(this._list.size() < AppConfig.getInstance().getM()) {
            this._list.add(n);
            Collections.sort(this._list);
        }
    }
    
    /**
     * remove a node
     * @param n 
     */
    public void remove(Node n){
        this._list.remove(n);
    }
    
    /**
     * search for give nodeid
     * @param nodeid
     * @return 
     */
    public Node search(String nodeid){
        Node found = null;
        Iterator<Node> iter = this._list.iterator();
        while(iter.hasNext()){
            Node item = iter.next();
            if(item.getID().compareTo(nodeid)==0){
                found = item;
                break;
            }
        }
        return found;
    }
}
