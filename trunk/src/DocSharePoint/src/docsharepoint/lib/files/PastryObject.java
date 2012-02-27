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

package docsharepoint.lib.files;

import docsharepoint.lib.ObjectId;

/**
 * represents a file to be stored in pastry network
 * @author George Karpouzas
 */
public class PastryObject {
    private String _contents;
    private ObjectId _objectid;
    private String _name;
    
    /**
     * constructor specifying file contents
     * @param contents
     */
    public PastryObject(String name, String contents){
        this._contents = contents;
        this._name = name;
        this._objectid = new ObjectId(this._name);
    }

    /**
     * get file contents
     * @return String
     */
    public String getContents(){
        return this._contents;
    }
}
