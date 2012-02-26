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
import docsharepoint.lib.NodeId;
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
    private Node _main;
    
    /**
     * constructor specifying main leafset node
     * @param main 
     */
    public LeafSet(Node main){
        this._smaller = new ArrayList<>();
        this._bigger = new ArrayList<>();
        this._main = main;
    }
    
    /**
     * add new node
     * @param n 
     */
    @Override
    public void add(Node n){
        if(n.compareTo(this._main)==0) return;
        if(n.compareTo(this._main)<0){
            if(this._smaller.size() == AppConfig.Instance().getL()/2)
                this._replaceSmallest(n);
            else
                this._smaller.add(n);
            Collections.sort(this._smaller);
        }
        else{
            if(this._bigger.size() < AppConfig.Instance().getL()/2)
                this._replaceBiggest(n);
            else
                this._bigger.add(n);
            Collections.sort(this._bigger);
        }
    }
    
    /**
     * remove a node
     * @param n 
     */
    @Override
    public void remove(Node n){
        //try to remove from list of smaller nodes
        this._smaller.remove(n);
        //try to remove from list of bigger nodes
        this._bigger.remove(n);
    }
    
    /**
     * search for given nodeid
     * @param nodeid
     * @return 
     */
    @Override
    public Node search(NodeId nodeid){
        Node found = null;
        if(nodeid.compareTo(this._main.getID())==0)return this._main;
        if(nodeid.compareTo(this._main.getID())<0){
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
    
    private void _replaceSmallest(Node n){
        //check if n has bigger key
        //smaller list is sorted in ascending order 
        //so the item(0) has the smallest key
        if(n.compareTo(this._smaller.get(0))>0){
            this._smaller.remove(0);
            this._smaller.add(n);
        }
    }
    
    private void _replaceBiggest(Node n){
        //check if n has smaller key
        if(n.compareTo(this._bigger.get(this._bigger.size()-1))<0){
            this._bigger.remove(this._bigger.size()-1);
            this._bigger.add(n);
        }
    }
}
