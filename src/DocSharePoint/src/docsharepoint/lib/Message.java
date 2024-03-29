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
package docsharepoint.lib;

/**
 * represents a message
 * @author George Karpouzas
 */
public class Message {
    private String _message;
    
    /**
     * constructor specifying message
     * @param value 
     */
    public Message(String value){
        this._message = value;
    }
    
    /**
     * get message
     */
    public String getMessage(){
        return this._message;
    }
    
    /**
     * get Message Type
     * @return String
     */
    public String getMessageType(){
        return this._message.split(":")[0];
    }
    
    /**
     * get Node ID
     */
    public String getNodeId(){
        return this._message.split(":")[1];
    }
}
