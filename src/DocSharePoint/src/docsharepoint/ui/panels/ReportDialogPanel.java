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

import docsharepoint.ui.components.TextArea;
import docsharepoint.ui.utils.Size;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

/**.
 * represents the report dialog
 * @author George Karpouzas
 */
public class ReportDialogPanel extends AbstractPanel {
    /**.
     * text area - license
     */
    private TextArea textarea;

    /**.
     * default constructor
     */
    public ReportDialogPanel() {
        super(null);
    }

    /**.
     * initialize panel
     * @param parentsize parent size
     * @return boolean
     */
    public final boolean init(final Size parentsize) {
        textarea = new TextArea("");
        textarea.setEditable(false);
        textarea.setWrapStyleWord(true);
        
        JScrollPane scroll = new JScrollPane(textarea);
        scroll.setHorizontalScrollBarPolicy(
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scroll);
        scroll.setBounds(10, 10, 380, parentsize.getHeight()-50);

        return true;
    }

    /**
     * append text to textarea
     * @param message
     */
    public void append2TextArea(String message){
        textarea.setText(textarea.getText() + "\r\n"+message);
    }
}
