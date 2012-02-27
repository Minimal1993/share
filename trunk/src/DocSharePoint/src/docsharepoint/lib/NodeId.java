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
package docsharepoint.lib;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * represents the nodeid
 * @author George Karpouzas
 */
public class NodeId implements Comparable<NodeId>{
    private byte[] _key;
    private String _keystr;
    
    /**
     * default constructor
     */
    public NodeId(){
        this._key = new byte[16];
        this._keystr = "";
    }
    
    /**
     * constructor specifying ip:port or object name
     * @param plainText 
     */
    public NodeId(String plainText){
        try {
            //------------------------------------------------------
            //hash ip:port
            //------------------------------------------------------
            MessageDigest md = MessageDigest.getInstance("SHA1");
            md.update(plainText.getBytes()); 
            byte[] output = md.digest();
            this._key = new byte[16];
            
            //------------------------------------------------------
            //keep only 16 bytes
            //------------------------------------------------------
            System.arraycopy(output, 0, this._key, 0, 16);
            
            //------------------------------------------------------
            //convert byte array to a positive big integer
            //------------------------------------------------------
            BigInteger ret = new BigInteger(1, this._key);
            
            //------------------------------------------------------
            //convert biginteger into chars 0-3
            char[] ch = ret.toString(4).toCharArray();
            
            //------------------------------------------------------
            //get string representation of the key
            //------------------------------------------------------
            this._keystr=String.valueOf(ch);
        } catch (NoSuchAlgorithmException ex) {
            this._keystr = "";
        }
    }
    
    /**
     * get key
     * @return byte[]
     */
    public byte[] getKey(){
        return this._key;
    }
    
    /**
     * set key
     * @param value 
     */
    public void setKey(String value){
        this._keystr = value;
        BigInteger big = new BigInteger(value);
        this._key = big.toByteArray();
    }
    
    /**
     * get biginteger representation of the key
     * @return 
     */
    public BigInteger toBigInteger(){
        return new BigInteger(1, this._key);
    }
    
    /**
     * get string representation of the key
     * @return String
     */
    @Override
    public String toString(){
        return this._keystr;
    }

    /**
     * 
     * @param arg0
     * @return 
     */
    @Override
    public int compareTo(NodeId arg0) {
        return this.toBigInteger().compareTo(arg0.toBigInteger());
    }
}
