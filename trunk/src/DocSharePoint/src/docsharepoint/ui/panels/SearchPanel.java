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

package docsharepoint.ui.panels;

import docsharepoint.lib.Messaging;
import docsharepoint.ui.components.Button;
import docsharepoint.ui.components.Label;
import docsharepoint.ui.components.TextField;
import docsharepoint.ui.events.Browse4FileEvent;
import docsharepoint.ui.events.SearchFileEvent;
import docsharepoint.ui.utils.Size;
import java.io.File;

/**.
 * search panel
 * @author George Karpouzas
 */
public class SearchPanel extends AbstractPanel {
    private Label folderLabel, fileNameLabel;
    private TextField folder, fileName;
    private Button browse;
    private Button search;

    /**.
     * default constructor
     */
    public SearchPanel() {
        super(null);
    }

    /**.
     * initialize shared panel
     * @param parentsize parent size
     * @return boolean, true on success
     */
    public final boolean init(final Size parentsize) {

        fileNameLabel = new Label("Name");
        add(fileNameLabel);
        fileNameLabel.setBounds(10, 10, 130, 20);

        fileName = new TextField();
        add(fileName);
        fileName.setBounds(100, 10, 160, 20);

        folderLabel = new Label("Downloads");
        add(folderLabel);
        folderLabel.setBounds(10, 40, 160, 20);

        folder = new TextField(System.getProperty("user.home")+System.getProperty("file.separator")+
                "dsp"+System.getProperty("file.separator")+
                "downloads"+
                System.getProperty("file.separator"));
        try{
            boolean success = (new File(folder.getText())).mkdirs();
            if(success) Messaging.Print2Console(folder.getText()+" succesfully created.");
        }
        catch(Exception ex){
            Messaging.PrintError2Console(ex.toString());
        }
        add(folder);
        folder.setBounds(100, 40, 300, 20);

        browse = new Button("...", "Browse for Folder",
                new Browse4FileEvent());
        add(browse);
        browse.setBounds(410, 40, 40, 20);

        search = new Button("Search..", "Search for file and download it",
                new SearchFileEvent(this));
        add(search);
        search.setBounds(500, 40, 100, 20);

        return true;
    }

    /**
     * get file name to produce key-id
     * @return String
     */
    public String getFileName(){
        return fileName.getText();
    }

    /**
     * get file path
     * @return String
     */
    public String getPath(){
        return folder.getText();
    }
}
