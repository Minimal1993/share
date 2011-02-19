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

package docsharepoint.lib;

import docsharepoint.AppConfig;
import javax.swing.JOptionPane;

/**
 * represents the messaging manager
 * print to console
 * print to textbox
 * popup messagebox
 * etc..
 * @author George Karpouzas
 */
public class Messaging {
    
    /**
     * print to command line - console
     * @param message
     */
    public static void Print2Console(String message){
        if(AppConfig.getInstance().print2Console())System.out.println(message);
        if(AppConfig.getInstance().getReportDialog()!=null)AppConfig.getInstance().getReportDialog().LogMessage(message);
    }

    /**
     * print error message to command line - console
     * @param message
     */
    public static void PrintError2Console(String message){
        if(AppConfig.getInstance().print2Console())System.err.println(message);
        if(AppConfig.getInstance().getReportDialog()!=null)AppConfig.getInstance().getReportDialog().LogMessage(message);
    }

    /**
     * popup messagebox
     * @param message
     * @param title
     */
    public static void ShowMessage(String message, String title){
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
        if(AppConfig.getInstance().getReportDialog()!=null)AppConfig.getInstance().getReportDialog().LogMessage(message);
    }

    /**
     * popup messagebox error
     * @param message
     * @param title
     */
    public static void ShowErrorMessage(String message, String title){
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
        if(AppConfig.getInstance().getReportDialog()!=null)AppConfig.getInstance().getReportDialog().LogMessage(message);
    }
}
