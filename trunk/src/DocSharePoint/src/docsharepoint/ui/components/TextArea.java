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

package docsharepoint.ui.components;

import docsharepoint.ui.arch.IComponent;
import javax.swing.JTextArea;

/**.
 * represents a textarea
 * @author George Karpouzas
 */
public class TextArea extends JTextArea implements IComponent {

    /**
     * default constructor
     */
    public TextArea(){
        super();
    }
    
    /**.
     * constructor specifying text area text
     * @param text text
     */
    public TextArea(String text) {
        super(text);
    }
}
