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

import docsharepoint.AppConfig;
import docsharepoint.ui.components.Button;
import docsharepoint.ui.components.Label;
import docsharepoint.ui.components.TextArea;
import docsharepoint.ui.events.ExitEvent;
import docsharepoint.ui.utils.Size;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

/**.
 * represents the aboutdialog's dialog main panel
 * @author George Karpouzas
 */
public class AboutDialogPanel extends AbstractPanel {
    /**.
     * ok button
     */
    private Button okbutton;
    /**.
     * labels
     */
    private Label product, copyright, license, url;
    /**.
     * text area - license
     */
    private TextArea textarea;

    /**.
     * default constructor
     */
    public AboutDialogPanel() {
        super(null);
    }

    /**.
     * initialize panel
     * @param parentsize parent size
     * @return boolean
     */
    public final boolean init(final Size parentsize) {
        okbutton = new Button("OK", "Close window",
                new ExitEvent());
        add(okbutton);
        okbutton.setBounds(145, 300, 100, 20);

        product = new Label("DocSharePoint "
                + AppConfig.getInstance().getVersion());
        add(product);
        product.setBounds(10, 10, 180, 20);

        copyright = new Label("Copyright (c) 2011 Karpouzas George");
        add(copyright);
        copyright.setBounds(10, 30, 280, 20);

        license = new Label("DocSharePoint is Licensed under GPLv3");
        add(license);
        license.setBounds(10, 50, 280, 20);

        url = new Label("http://docsharepoint.sourceforge.net/");
        add(url);
        url.setBounds(10,70,280,20);

        textarea = new TextArea("DocSharePoint - "
    + "Open Source Distributed p2p application based on pastry\r\n"
   + "Copyright (C) 2010 DocSharePoint KARPOUZAS GEORGE\r\n\r\n"
   + "http://docsharepoint.sourceforge.net/\r\n\r\n"
    + "This program is free software: you can redistribute it and/or modify\r\n"
    + "it under the terms of the GNU General Public License as published by\r\n"
    + "the Free Software Foundation, either version 3 of the License, or\r\n"
   + "(at your option) any later version.\r\n\r\n"
   + "This program is distributed in the hope that it will be useful,\r\n"
   + "but WITHOUT ANY WARRANTY; without even the implied warranty of\r\n"
   + "MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the\r\n"
   + "GNU General Public License for more details.\r\n\r\n"
   + "You should have received a copy of the GNU General Public License\r\n"
+ "along with this program.  If not, see <http://www.gnu.org/licenses/>.\r\n");

        JScrollPane scroll = new JScrollPane(textarea);
        scroll.setHorizontalScrollBarPolicy(
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scroll);
        scroll.setBounds(10, 100, 380, 180);

        return true;
    }
}
