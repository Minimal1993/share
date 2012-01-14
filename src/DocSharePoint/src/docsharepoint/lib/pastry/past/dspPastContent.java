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

package docsharepoint.lib.pastry.past;

import docsharepoint.lib.files.PastFile;
import rice.p2p.commonapi.Id;
import rice.p2p.past.ContentHashPastContent;
import rice.p2p.past.PastContent;
import rice.p2p.past.PastException;

/**
 * represents a content object to put/get in pastry network
 * @author George Karpouzas <gkarpouzas@webnetsoft.gr>
 */
public class dspPastContent extends ContentHashPastContent{
    private String _fileContents;
    private String _fileName;
    private String _extension;
    
    /**
     * An Id to generate the hash
     * The content to be stored.
     *
     * @param id to generate a hash of the content
     * @param content to be stored
    */
    public dspPastContent(Id id, String name, String extension, PastFile content){
        super(id);
        this._fileContents = content.getContents();
        this._fileName = name;
        this._extension = extension;
    }

    /**
     * get stored content
     * @return String
     */
    public String getContent(){
        return _fileContents;
    }

    /**
     * get file name
     * @return String
     */
    public String getName(){
        return _fileName;
    }

    /**
     * get file extension
     * @return String
     */
    public String getExtension(){
        return _extension;
    }

    /**
     * check if already inserted
     * @param id
     * @param existingContent
     * @return PastContent
     */
    @Override
    public PastContent checkInsert(Id id, PastContent existingContent) throws PastException { return this; }


    /**
     * mot changeable - cannot be modified
     * @return boolean
     */
    @Override
    public boolean isMutable() { return false; }

}
