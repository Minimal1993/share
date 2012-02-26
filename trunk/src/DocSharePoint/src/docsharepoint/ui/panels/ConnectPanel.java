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

import docsharepoint.AppConfig;
import docsharepoint.lib.helpers.MessagingHelper;
import docsharepoint.ui.components.Button;
import docsharepoint.ui.components.CheckBox;
import docsharepoint.ui.components.Label;
import docsharepoint.ui.components.TextField;
import docsharepoint.ui.events.Browse4FolderEvent;
import docsharepoint.ui.events.ConnectEvent;
import docsharepoint.ui.utils.Size;
import java.io.File;

/**.
 * connection panel
 * @author George Karpouzas
 */
public class ConnectPanel extends AbstractPanel {
    /**.
     * boot host, boot port and bind port labels
     */
    private Label boothost, bootport, remotehost, remoteport, path2saveLabel;
    /**.
     * boot host, boot port, bind port
     */
    private TextField bootHost, bootPort, remoteHost, remotePort, path2save;
    /**.
     * connect button
     */
    private Button connect, browse;

    private CheckBox newnetwork;
    
    /**.
     * default constructor
     */
    public ConnectPanel() {
        super(null);
    }

    /**.
     * initialize connect panel
     * @param parentsize parent size
     * @return boolean, true on success
     */
    @Override
    public final boolean init(final Size parentsize) {
        final int second_column_comp_y = 190;
        final int text_fields_width = 180;
        
        newnetwork = new CheckBox("Create new Pastry Network?");
        add(newnetwork);
        newnetwork.setBounds(10, 10, 260, 20);
        
        connect = new Button("Connect", "Connect with pastry network",
                new ConnectEvent(this));
        if (AppConfig.Instance().isConnected()) {
            connect.setText("Disconnect");
        }
        add(connect);
        connect.setBounds(380, 160, 120, 20);

        browse = new Button("...", "Browse for folder",
                new Browse4FolderEvent());
        add(browse);
        browse.setBounds(second_column_comp_y-45, 160, 30, 20);
        
        boothost = new Label("Boot Host");
        add(boothost);
        boothost.setBounds(10, 40, 160, 20);

        bootHost = new TextField("localhost");
        add(bootHost);
        bootHost.setBounds(second_column_comp_y, 40, text_fields_width, 20);

        bootport = new Label("Boot Port");
        add(bootport);
        bootport.setBounds(10, 70, 100, 20);

        bootPort = new TextField("9090");
        add(bootPort);
        bootPort.setBounds(second_column_comp_y, 70, text_fields_width, 20);
        
        remotehost = new Label("Remote Host");
        add(remotehost);
        remotehost.setBounds(10, 100, 160, 20);

        remoteHost = new TextField("localhost");
        add(remoteHost);
        remoteHost.setBounds(second_column_comp_y, 100, text_fields_width, 20);

        remoteport = new Label("Remote Port");
        add(remoteport);
        remoteport.setBounds(10, 130, 100, 20);

        remotePort = new TextField("9090");
        add(remotePort);
        remotePort.setBounds(second_column_comp_y, 130, text_fields_width, 20);

        path2saveLabel = new Label("Storage directory");
        add(path2saveLabel);
        path2saveLabel.setBounds(10, 160, text_fields_width, 20);

        path2save = new TextField(System.getProperty("user.home")+
                System.getProperty("file.separator")+
                "dsp"+System.getProperty("file.separator"));
        try{
            boolean success = (new File(path2save.getText())).mkdirs();
            if(success) MessagingHelper.Print2Console(path2save.getText()+" succesfully created.");
        }
        catch(Exception ex){
            MessagingHelper.PrintError2Console(ex.toString());
        }
        add(path2save);
        path2save.setBounds(second_column_comp_y, 160, text_fields_width, 20);
        
        return true;
    }

    /**
     * get boot host value
     * @return String
     */
    public String getBootHost() {
        return bootHost.getText();
    }

    /**
     * get boot port value
     * @return int
     */
    public int getBootPort(){
        try{
            return Integer.parseInt(bootPort.getText());
        }
        catch(Exception ex){
            return -1;
        }
    }
    
    /**
     * get remote host
     * @return 
     */
    public String getRemoteHost(){
        return remoteHost.getText();
    }
    
    /**
     * get remote port
     * @return 
     */
    public int getRemotePort(){
        try{
            return Integer.parseInt(remotePort.getText());
        }
        catch(Exception ex){
            return -1;
        }
    }

    /**
     * set boot host value
     * @param value
     */
    public void setBootHost(String value){
        bootHost.setText(value);
    }

    /**
     * set boot port value
     * @param value
     */
    public void setBootPort(int value){
        bootPort.setText(value+"");
    }

    /**
     * create new pastry network?
     */
    public Boolean createNewNetwork(){
        return newnetwork.isSelected();
    }
    
    /**
     * get path
     * @return String
     */
    public String getPath(){
        return path2save.getText();
    }
}
