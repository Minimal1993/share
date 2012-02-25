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

/**
 * represents node's routing table
 * @author Karpouzas George
 */
public class RoutingTable implements iSet{
    private Node[][] _rtable;
    /**
     * default constructor
     */
    public RoutingTable(){
        AppConfig.Instance().getN();
        //rows = log(N)/log(2^b)
        int rows = (int) (Math.log(AppConfig.Instance().getN())/Math.log(4));
        //cols = 2^b-1
        int cols = (int) (Math.pow(2, AppConfig.Instance().getB()) - 1);
        this._rtable = new Node[rows][cols];
    }
    
    /**
     * add node
     * @param n 
     */
    public void add(Node n){
        
    }
    
    /**
     * remove node
     * @param n 
     */
    public void remove(Node n){
        
    }
    
    /**
     * search for a nodeid
     * @param nodeid
     * @return 
     */
    public Node search(String nodeid){
        Node found = null;
        return found;
    }
}
