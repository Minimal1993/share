/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package docsharepoint.lib.pastry.past;

import docsharepoint.lib.Messaging;
import docsharepoint.lib.files.FileManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import rice.p2p.commonapi.Id;

/**
 *
 * @author George Karpouzas
 */
public class dspIdManager {
    private HashMap<String, Id> fileNamesKeys;
    private String keysfolder;
    /**
     * default constructor
     */
    public dspIdManager(){
        fileNamesKeys = new HashMap<String, Id>();
        keysfolder = System.getProperty("user.home")+
                System.getProperty("file.separator")+"dsp"+
                System.getProperty("file.separator")+"saved_keys"+
                System.getProperty("file.separator");
        try{
            boolean success = (new File(keysfolder)).mkdirs();
        }
        catch(Exception ex){
            Messaging.PrintError2Console(ex.toString());
        }
    }

    /**
     * initialize keys
     */
    public void init(){
        loadStoredKeysFromFiles();
    }

    private void loadStoredKeysFromFiles(){
        List<File> datFiles = FileManager.getDirFiles(keysfolder, "dat");
        if(datFiles==null) return;
        for(File f : datFiles){
            String Key = f.getName().replace(".dat", "");
            Id val = null;
            try {
                FileInputStream fin = new FileInputStream(keysfolder+f.getName());
                ObjectInputStream ois = new ObjectInputStream(fin);
                val = (Id) ois.readObject();
                ois.close();
            }
            catch (Exception e) { System.out.println(e.toString());}
            fileNamesKeys.put(Key, val);
        }
    }

    /**
     * add a pair
     * @param Key
     * @param value
     */
    public void add(String Key, Id value){
        fileNamesKeys.put(Key, value);
        dump2Files();
    }

    /**
     * retrieve value
     * @param Key
     * @return Id
     */
    public Id get(String Key){
        return fileNamesKeys.get(Key);
    }

    /**
     * check if key already stored
     * @param Key
     * @return boolean
     */
    public boolean exists(String Key){
        return fileNamesKeys.containsKey(Key);
    }

    private void dump2Files(){
        Iterator it = fileNamesKeys.keySet().iterator();
        while(it.hasNext()) {
            String key = (String) it.next();
            Id val = fileNamesKeys.get(key);
            try {
              File f = new File(System.getProperty("user.home")+System.getProperty("file.separator")+
                      "dsp"+System.getProperty("file.separator")+
                      "saved_keys"+System.getProperty("file.separator")
                      + key +".dat");
              if(!f.exists()){
                    FileOutputStream fout = new FileOutputStream(f);
                    ObjectOutputStream oos = new ObjectOutputStream(fout);
                    oos.writeObject(val);
                    oos.close();
                }
              }
            catch (Exception e) {}
        }
    }
}
