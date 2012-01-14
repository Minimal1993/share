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

package docsharepoint.ui.windows;

import docsharepoint.AppConfig;
import docsharepoint.lib.Messaging;
import docsharepoint.system.Monitor;
import docsharepoint.ui.Exceptions.WindowInitializationException;
import docsharepoint.ui.events.AboutMenuEvent;
import docsharepoint.ui.events.ExitEvent;
import docsharepoint.ui.panels.MainWindowPanel;
import docsharepoint.ui.utils.Bounds;
import docsharepoint.ui.utils.Position;
import docsharepoint.ui.utils.Size;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**.
 * main application window
 * @author George Karpouzas
 */
public class MainWindow extends AbstractWindow {
    /**.
     * menubar
     */
    private JMenuBar menubar;
    /**.
     * menus
     */
    private JMenu menuFile, menuHelp;
    /**.
     * menu items
     */
    private JMenuItem menuExit, menuAbout;
    /**.
     * panel
     */
    private MainWindowPanel mainPanel;

    /**.
     * default constructor
     */
    public MainWindow() {
        super("DocSharePoint - " + AppConfig.getInstance().getVersion());
        setResizable(false);
        menubar = new JMenuBar();
        mainPanel = new MainWindowPanel();
    }

    /**.
     * get handle to main panel
     * @return MainWindowPanel
     */
    public final MainWindowPanel getMainPanel() {
        return mainPanel;
    }

     /**.
      * initialize window, providing window size and listeners
      * @param size size
      * @return boolean
      * @throws WindowInitializationException thrown on failure
      */
     public final boolean init(final Size size)
             throws WindowInitializationException {
        try{
            setSize(size.getWidth(), size.getHeight());
            Position position = Monitor.getCenter(size);
            setLocation(position.getX(), position.getY());

            initialize(size);
            return true;
        }
        catch(Exception ex){ Messaging.PrintError2Console(ex.toString()); return false; }
    }

    /**.
     * initialize window, providing window bounds and listeners
     * @param bounds window bounds
     * @return boolean
     * @throws WindowInitializationException thrown on failure
     */
    public boolean init(Bounds bounds)
            throws WindowInitializationException {
        try{
            //set window size and position
            setBounds(bounds.getPosition().getX(), bounds.getPosition().getY(),
                bounds.getSize().getWidth(), bounds.getSize().getHeight());

            initialize(bounds.getSize());
            return true;
        }
        catch(Exception ex){ return false; }
    }

    /**.
     * initialize
     * @param size window size
     */
    private void initialize(Size size) {
        //add menubar
        buildMenuBar();
        //init main panel
        mainPanel.init(size);
        //set main panel
        setContentPane(mainPanel);
    }

    /**
     * build menu
     * @param listeners action listeners
     */
    private void buildMenuBar() {
        //menu exit option
        menuFile = new JMenu("File");
        menuExit = new JMenuItem("Exit");
        menuExit.addActionListener(new ExitEvent());
        menuFile.add(menuExit);
        menubar.add(menuFile);

        //menu about option
        menuHelp = new JMenu("Help");
        menuAbout = new JMenuItem("About");
        menuAbout.addActionListener(new AboutMenuEvent());
        menuHelp.add(menuAbout);
        menubar.add(menuHelp);

        //set menu bar
        setJMenuBar(menubar);
    }
}
