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
package docsharepoint.lib.helpers;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * sha1 class helper
 * @author Karpouzas George
 */
public class SHA1Helper {
    private static SHA1Helper _instance;
    
    private SHA1Helper(){}
    
    /**
     * calculate sha1 hash
     * @param plaintext
     * @return String
     */
    public BigInteger hash(String plaintext){
        try {
            MessageDigest md = MessageDigest.getInstance("SHA1");
            md.update(plaintext.getBytes()); 
            byte[] output = md.digest();
            BigInteger ret = new BigInteger(1, output);
            
            return ret;
        } catch (NoSuchAlgorithmException ex) {
            return null;
        }
    }
    
    /**
     * get class instance create an internal SHA1Helper object
     * @return SHA1Helper
     */
    public static SHA1Helper Instance() {
        if (_instance == null) {
            _instance = new SHA1Helper();
        }
        return _instance;
    }
    
    private String bytesToHex(byte[] b) {
      char hexDigit[] = {'0', '1', '2', '3', '4', '5', '6', '7',
                         '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
      StringBuilder buf = new StringBuilder();
      for (int j=0; j<b.length; j++) {
         buf.append(hexDigit[(b[j] >> 4) & 0x0f]);
         buf.append(hexDigit[b[j] & 0x0f]);
      }
      return buf.toString();
   }
}