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

import docsharepoint.ui.components.Button;
import docsharepoint.ui.components.Label;
import docsharepoint.ui.components.TextField;
import docsharepoint.ui.events.Browse4FileEvent;
import docsharepoint.ui.events.InsertFileEvent;
import docsharepoint.ui.utils.Size;

/**.
 * shared-insert panel
 * @author George Karpouzas
 */
public class SharedPanel extends AbstractPanel {
    private Label insFileLabel, fileNameLabel, replicationLabel;
    private TextField insFile, fileName, replication;
    private Button browse;
    private Button insert;
    
    /**.
     * default constructor
     */
    public SharedPanel() {
        super(null);
    }

    /**.
     * initialize shared panel
     * @param parentsize parent size
     * @return boolean, true on success
     */
    @Override
    public final boolean init(final Size parentsize) {

        fileNameLabel = new Label("File Name");
        add(fileNameLabel);
        fileNameLabel.setBounds(10, 10, 130, 20);

        fileName = new TextField();
        add(fileName);
        fileName.setBounds(180, 10, 160, 20);

        replicationLabel = new Label("Replications");
        add(replicationLabel);
        replicationLabel.setBounds(10, 40, 130, 20);

        replication = new TextField("10");
        add(replication);
        replication.setBounds(180, 40, 160, 20);
        
        insFileLabel = new Label("Choose a file to insert");
        add(insFileLabel);
        insFileLabel.setBounds(10, 70, 160, 20);

        insFile = new TextField();
        add(insFile);
        insFile.setBounds(180, 70, 200, 20);

        browse = new Button("...", "Browse for file",
                new Browse4FileEvent());
        add(browse);
        browse.setBounds(400, 70, 40, 20);

        insert = new Button("Upload File", "Upload File to Pastry Nework",
                new InsertFileEvent(this));
        add(insert);
        insert.setBounds(450, 70, 120, 20);
        
        return true;
    }

    /**
     * get file name to produce key-id
     * @return String
     */
    public String getFileNameIdentifier(){
        return fileName.getText();
    }

    /**
     * get number of replications
     * @return int
     */
    public int getNumOfReplications(){
        try{
            return Integer.parseInt(replication.getText());
        }
        catch(Exception ex){
            return 10;
        }
    }

    /**
     * get file path
     * @return String
     */
    public String getFilePath(){
        return insFile.getText();
    }
}
