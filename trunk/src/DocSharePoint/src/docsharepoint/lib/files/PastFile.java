/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package docsharepoint.lib.files;

/**
 * represents a file to be stored in pastry network
 * @author George Karpouzas
 */
public class PastFile {
    private String _contents;

    /**
     * constructor specifying file contents
     * @param contents
     */
    public PastFile(String contents){
        _contents = contents;
    }

    /**
     * get file contents
     * @return String
     */
    public String getContents(){
        return _contents;
    }
}
