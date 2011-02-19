/*
 *  DocSharePoint
 *  Open Source Distributed p2p system based on pastry
 *  Copyright (C) 2010 DocSharePoint KARPOUZAS GEORGE
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

import docsharepoint.ui.Exceptions.SizeInvalidException;
import docsharepoint.ui.components.Button;
import docsharepoint.ui.events.showConnectPanelEvent;
import docsharepoint.ui.events.showPreferencesPanelEvent;
import docsharepoint.ui.events.showSearchPanelEvent;
import docsharepoint.ui.events.showSharedPanelEvent;
import docsharepoint.ui.events.showTransfersPanelEvent;
import docsharepoint.ui.utils.Size;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JToolBar;

/**.
 * represents the main window panel
 * @author George Karpouzas
 */
public class MainWindowPanel extends AbstractPanel {
    private AbstractPanel _activePanel;
    /**.
     * toolbar
     */
    private JToolBar toolbar;
    /**.
     * all buttons
     */
    private Button connect, search, transfers,
            shared, preferences;
    /**.
     * image icons
     */
    private ImageIcon connecticon, disconnecticon,
            searchicon, transfersicon, sharedicon, preferencesicon;

    /**.
     * default constructor
     */
    public MainWindowPanel() {
        super(null);
        toolbar = new JToolBar("Toolbar", JToolBar.HORIZONTAL);
        connecticon = new ImageIcon(
                Toolkit.getDefaultToolkit().getImage(
                this.getClass().getResource(
                "/docsharepoint/ui/images/connect.jpg")));
        disconnecticon = new ImageIcon(
                Toolkit.getDefaultToolkit().getImage(
                this.getClass().getResource(
                "/docsharepoint/ui/images/disconnect.jpg")));
        searchicon = new ImageIcon(
                Toolkit.getDefaultToolkit().getImage(
                this.getClass().getResource(
                "/docsharepoint/ui/images/search.jpeg")));
        transfersicon = new ImageIcon(
                Toolkit.getDefaultToolkit().getImage(
                this.getClass().getResource(
                "/docsharepoint/ui/images/transfer.jpeg")));
        sharedicon = new ImageIcon(
                Toolkit.getDefaultToolkit().getImage(
                this.getClass().getResource(
                "/docsharepoint/ui/images/shared.jpeg")));
        preferencesicon = new ImageIcon(
                Toolkit.getDefaultToolkit().getImage(
                this.getClass().getResource(
                "/docsharepoint/ui/images/preferences.jpeg")));
    }

    /**.
     * get connect and disconnect icons
     * @return ImageIcon[]
     */
    public final ImageIcon[] getConnectionImages() {
        return new ImageIcon[]{
            connecticon, disconnecticon
        };
    }

    /**.
     * initialize panel
     * @param parentsize parent size
     * @return boolean
     */
    public final boolean init(final Size parentsize) {
        connect = new Button("Connect", connecticon,
                "Connect to Network",
                new showConnectPanelEvent(this.getConnectionImages()));
        search = new Button("Search", searchicon,
                "Search for File and Download it",
                new showSearchPanelEvent());
        transfers = new Button("Transfers", transfersicon,
                "See all active transfers",
                new showTransfersPanelEvent());
        shared = new Button("Shared files", sharedicon,
                "See all your shared files - Upload File",
                new showSharedPanelEvent());
        preferences = new Button("Preferences", preferencesicon,
                "Application preferences",
                new showPreferencesPanelEvent());

        final int height = 30;
        toolbar.setPreferredSize(new Dimension(parentsize.getWidth(), height));

        toolbar.add(connect);
        toolbar.addSeparator();
        toolbar.add(search);
        toolbar.add(transfers);
        toolbar.add(shared);
        toolbar.addSeparator();
        toolbar.add(preferences);

        add(toolbar);
        toolbar.setBounds(0, 0, parentsize.getWidth(), 80);

        return true;
    }

    /**.
     * add panel in mainpanel
     * @param panel panel to add
     */
    public final void addPanel(final AbstractPanel panel) {
        if(_activePanel!=null)remove(_activePanel);
        _activePanel = panel;
        add(panel);
        panel.setBounds(10, 90, this.getWidth(), this.getHeight());
    }

    /**
     * get panel size
     * @return Size
     */
    public Size getPanelSize(){
        try {
            return new Size(this.getWidth(), this.getHeight());
        } catch (SizeInvalidException ex) {
            return null;
        }
    }
}
