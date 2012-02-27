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

/**
 * represents node's routing table
 * @author Karpouzas George
 */
public class RoutingTable implements iSet{
    private NodeInfo[][] _rtable;
    private Node _main;
    
    /**
     * default constructor
     */
    public RoutingTable(Node main){
        this._main = main;
        //rows = log(N)/log(2^b)
        //int rows = (int) (Math.log(AppConfig.Instance().getN())/Math.log(4));
        //cols = 2^b-1
        //int cols = (int) (Math.pow(2, AppConfig.Instance().getB()) - 1);
        this._rtable = new NodeInfo[AppConfig.Instance().getN()][AppConfig.Instance().getM()];
    }
    
    /**
     * add node
     * @param n 
     */
    @Override
    public void add(NodeInfo n){
        int row = this._shared_prefix_length(n)-1;
        int column = (Integer.parseInt(Character.toString(
                this._main.getID().toString().charAt(row))));
        if (this._rtable[row][column]==null){
            this._rtable[row][column] = n;
        }
        else{
            if ( this._main.distance(n.getIP()) < 
                    this._main.distance(this._rtable[row][column].getIP()))
                this._rtable[row][column] = n;
        }
    }
    
    /**
     * remove node
     * @param n 
     */
    @Override
    public void remove(NodeInfo n){
        
    }
    
    /**
     * search for a nodeid
     * @param nodeid
     * @return 
     */
    @Override
    public NodeInfo search(NodeId nodeid){
        NodeInfo found = null;
        return found;
    }
    
    private int _shared_prefix_length(NodeInfo n){
        String n1 = this._main.getID().toString();
        String n2 = n.getID().toString();
        int length = 0;
              
        for (int i=0 ; i<32 ; i++){
            if ( n1.charAt(i) == n2.charAt(i) )
                length++;
            else break;
        }
        return length;
    }
}
