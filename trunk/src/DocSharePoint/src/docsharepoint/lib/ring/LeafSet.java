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
import java.util.List;

/**
 * represents node's leaf set
 * @author Karpouzas George
 */
public class LeafSet implements iSet{
    private List<Node> _smaller, _bigger;
    private Node _owner;
    
    /**
     * constructor specifying main leafset node
     * @param main 
     */
    public LeafSet(Node main){
        this._smaller = new ArrayList<Node>();
        this._bigger = new ArrayList<Node>();
        this._owner = main;
    }
    
    /**
     * add new node
     * @param n 
     */
    public void add(Node n){
        if(n.compareTo(this._owner)==0) return;
        if(n.compareTo(this._owner)<0 && this._smaller.size() < 
                AppConfig.Instance().getL()/2) {
            this._smaller.add(n);
            Collections.sort(this._smaller);
        }
        else{
            if(this._bigger.size() < AppConfig.Instance().getL()/2){
                this._bigger.add(n);
                Collections.sort(this._bigger);
            }
        }
    }
    
    /**
     * remove a node
     * @param n 
     */
    public void remove(Node n){
        this._smaller.remove(n);
        this._bigger.remove(n);
    }
    
    /**
     * search for give nodeid
     * @param nodeid
     * @return 
     */
    public Node search(String nodeid){
        Node found = null;
        if(nodeid.compareTo(this._owner.getID())==0)return this._owner;
        if(nodeid.compareTo(this._owner.getID())<0){
            Iterator<Node> iter = this._smaller.iterator();
            while(iter.hasNext()){
                Node item = iter.next();
                if(item.getID().compareTo(nodeid)==0){
                    found = item;
                    break;
                }
            }
        }
        else{
            Iterator<Node> iter = this._bigger.iterator();
            while(iter.hasNext()){
                Node item = iter.next();
                if(item.getID().compareTo(nodeid)==0){
                    found = item;
                    break;
                }
            }
        }
        return found;
    }
}
