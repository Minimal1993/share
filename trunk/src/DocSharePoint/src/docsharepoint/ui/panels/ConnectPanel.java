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
import docsharepoint.ui.components.ComboBox;
import docsharepoint.ui.components.Label;
import docsharepoint.ui.components.TextField;
import docsharepoint.ui.events.Browse4FolderEvent;
import docsharepoint.ui.events.ServerConnectEvent;
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
    private Label boothost, bootport, bindport, logLevelLabel,
            firewallPolicyLabel, natSearchPolicyLabel, pingDelayLabel,
            path2saveLabel;
    /**.
     * boot host, boot port, bind port
     */
    private TextField bootHost, bootPort,
            bindPort, logLevel, pingDelay, path2save;
    /**.
     * connect button
     */
    private Button connect, browse;
    /**
     * direct connect?
     */
    private CheckBox direct, probeForExternalAddress;
    /**
     * policies
     */
    private ComboBox firewallPolicy, natSearchPolicy;

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
    public final boolean init(final Size parentsize) {
        final int second_column_comp_y = 190;
        final int text_fields_width = 180;
        
        connect = new Button("Connect", "Connect with pastry network",
                new ServerConnectEvent(this));
        if (AppConfig.getInstance().isConnected()) {
            connect.setText("Disconnect");
        }
        add(connect);
        connect.setBounds(380, 250, 120, 20);

        browse = new Button("...", "Browse for folder",
                new Browse4FolderEvent());
        add(browse);
        browse.setBounds(second_column_comp_y-45, 250, 30, 20);

        direct = new CheckBox("Direct Connection");
        direct.setSelected(true);
        add(direct);
        direct.setBounds(10, 10, 130, 20);

        probeForExternalAddress = new CheckBox("Probe for external address");
        add(probeForExternalAddress);
        probeForExternalAddress.setBounds(second_column_comp_y, 10, 250, 20);
        
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

        bindport = new Label("Bind Port");
        add(bindport);
        bindport.setBounds(10, 100, 100, 20);

        bindPort = new TextField("9090");
        add(bindPort);
        bindPort.setBounds(second_column_comp_y, 100, text_fields_width, 20);

        logLevelLabel = new Label("Log Level");
        add(logLevelLabel);
        logLevelLabel.setBounds(10, 130, 100, 20);
        
        logLevel = new TextField("1000");
        add(logLevel);
        logLevel.setBounds(second_column_comp_y, 130, text_fields_width, 20);

        firewallPolicyLabel = new Label("Firewall Policy");
        add(firewallPolicyLabel);
        firewallPolicyLabel.setBounds(10, 160, 160, 20);

        firewallPolicy = new ComboBox(new String[] {"always", "never"});
        firewallPolicy.setSelectedIndex(1);
        add(firewallPolicy);
        firewallPolicy.setBounds(second_column_comp_y, 160, text_fields_width, 20);

        natSearchPolicyLabel = new Label("NAT Policy");
        add(natSearchPolicyLabel);
        natSearchPolicyLabel.setBounds(10, 190, 160, 20);

        natSearchPolicy = new ComboBox(new String[] {"always", "never"});
        natSearchPolicy.setSelectedIndex(1);
        add(natSearchPolicy);
        natSearchPolicy.setBounds(second_column_comp_y, 190, text_fields_width, 20);

        pingDelayLabel = new Label("Ping Delay");
        add(pingDelayLabel);
        pingDelayLabel.setBounds(10, 220, 160, 20);

        pingDelay = new TextField("30");
        add(pingDelay);
        pingDelay.setBounds(second_column_comp_y, 220, text_fields_width, 20);

        path2saveLabel = new Label("Storage directory");
        add(path2saveLabel);
        path2saveLabel.setBounds(10, 250, text_fields_width, 20);

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
        path2save.setBounds(second_column_comp_y, 250, text_fields_width, 20);
        
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
     * get bind port value
     * @return int
     */
    public int getBindPort(){
        try{
            return Integer.parseInt(bindPort.getText());
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
     * set bind port value
     * @param value
     */
    public void setBindPort(int value){
        bindPort.setText(value+"");
    }

    /**
     * connect direct?
     * @return boolean
     */
    public boolean useDirectConnection(){
        return direct.isSelected();
    }

    /**
     * get log level value
     * @return int
     */
    public int getLogLevel(){
        try{
            return Integer.parseInt(logLevel.getText());
        }
        catch(Exception ex){
         return 1000;
        }
    }

    /**
     * firewall policy
     * @return String
     */
    public String getFirewallPolicy(){
        return firewallPolicy.getSelectedItem().toString();
    }

    /**
     * nat search policy
     * @return String
     */
    public String getNATPolicy(){
        return natSearchPolicy.getSelectedItem().toString();
    }

    /**
     * get pastry ring ping delay
     * @return int
     */
    public int getPingDelay(){
        try{
            return Integer.parseInt(pingDelay.getText());
        }
        catch(Exception ex){
         return 30;
        }
    }

    /**
     * probe for external address?
     * @return boolean
     */
    public boolean probe4ExternalAddress(){
        return probeForExternalAddress.isSelected();
    }

    /**
     * get path
     * @return String
     */
    public String getPath(){
        return path2save.getText();
    }
}
