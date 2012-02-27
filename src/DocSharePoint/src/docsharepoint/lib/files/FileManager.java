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

package docsharepoint.lib.files;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * file manager
 * @author George Karpouzas
 */
public class FileManager {
    /**
     * get file contents
     * @param f
     * @return String
     */
    public static String getContents(File f) {
    StringBuilder contents = new StringBuilder();

    try {
      try ( BufferedReader input = new BufferedReader(new FileReader(f))) {
        String line;

        while (( line = input.readLine()) != null){
          contents.append(line);
          contents.append(System.getProperty("line.separator"));
        }
      }
    }
    catch (IOException ex){
      return null;
    }

    return contents.toString();
  }

    /**
     * write file contents
     * @param f
     * @param contents
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void setContents(File f, String contents)
                                 throws FileNotFoundException, IOException {
        if (f == null) {
          throw new IllegalArgumentException("File should not be null.");
        }
        if (!f.exists()) {
          throw new FileNotFoundException ("File does not exist: " + f);
        }
        if (!f.isFile()) {
          throw new IllegalArgumentException("Should not be a directory: " + f);
        }
        if (!f.canWrite()) {
          throw new IllegalArgumentException("File cannot be written: " + f);
        }
        try (Writer output = new BufferedWriter(new FileWriter(f))) {
          //FileWriter always assumes default encoding is OK!
          output.write( contents );
        }
    }

    /**
     * get file extension
     * @param file
     * @return String
     */
      public static String getExtension(File file){
          int dot = file.getPath().lastIndexOf(".");
          return file.getPath().substring(dot + 1);
      }

      /**
       * get list of files with specific extensions
       * @param folder
       * @param extension
       * @return List<File>
       */
      public static List<File> getDirFiles(String folder, String extension){
        File directory = new File(folder);
        File[] listOfFiles = directory.listFiles();
        List<File> files = new ArrayList<>();
        if(listOfFiles==null)return null;
        for (int i = 0; i < listOfFiles.length; i++) {
            File f = listOfFiles[i];
            int dot = f.getPath().lastIndexOf(".");
            String ext = f.getPath().substring(dot + 1);
            if (f.isFile() && ext.compareTo(extension)==0)
                files.add(f);
        }

        return files;
      }
}
