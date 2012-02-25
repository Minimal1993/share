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

package docsharepoint.ui.dialogs;

import docsharepoint.AppConfig;
import docsharepoint.system.Monitor;
import docsharepoint.ui.Exceptions.WindowInitializationException;
import docsharepoint.ui.panels.AboutDialogPanel;
import docsharepoint.ui.utils.Bounds;
import docsharepoint.ui.utils.Position;
import docsharepoint.ui.utils.Size;

/**.
 * represents the about dialog
 * @author George Karpouzas
 */
public class AboutDialog extends AbstractDialog {
    /**.
     * Aboutdialog panel
     */
    private AboutDialogPanel adp;

    /**.
     * default constructor
     */
    public AboutDialog() {
        super("About DocSharePoint - " + AppConfig.Instance().getVersion());
        adp = new AboutDialogPanel();
    }

    /**.
     * initialize window, providing window size and listeners
     * @param size dialog size
     * @return boolean true on successful initialization
     * @throws WindowInitializationException thrown on failure
     */
    public final boolean init(final Size size)
            throws WindowInitializationException {
        try{
            setSize(size.getWidth(), size.getHeight());
            Position position = Monitor.getCenter(size);
            setLocation(position.getX(), position.getY());

            myinit(size);
            return true;
        }
        catch(Exception ex){ return false; }
    }

    /**.
     * initialize window, providing window bounds and listenerss
     * @param bounds
     * @return boolean
     * @throws WindowInitializationException
     */
    public boolean init(Bounds bounds) throws WindowInitializationException {
        setBounds(bounds.getPosition().getX(), bounds.getPosition().getY(),
                    bounds.getSize().getWidth(), bounds.getSize().getHeight());

        myinit(bounds.getSize());
        return true;
    }

    /**
     * initialize
     * @param parentsize
     * @param listeners
     */
    private void myinit(Size parentsize){
        adp.init(parentsize);
        setContentPane(adp);
    }
}
