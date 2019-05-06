package cjt.androiddisk.Model.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by CJT on 2018/12/4.
 */

public class MD5 {
    public static String msgToMD5L32(String msg){
        MessageDigest m = null;
        StringBuffer result = new StringBuffer();
        try {
            m = MessageDigest.getInstance("MD5");
            m.update(msg.getBytes("UTF8"));
            byte s[]=m.digest();
            for (int i=0; i<s.length;i++){
                result.append(Integer.toHexString((0x000000ff & s[i]) | 0xffffff00).substring(6));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

//        System.out.println(result);
        return String.valueOf(result);
    }


}
