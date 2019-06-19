package org.iot.client.common.utils;

import java.nio.ByteBuffer;

import org.apache.shiro.crypto.hash.Md2Hash;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.Sha512Hash;

public class MD5Utils {
    public static String encode(byte[] source,String salt,int hashIterations) {
        Md5Hash md = new Md5Hash(source, salt, hashIterations);
        return md.toHex();
    }
    
    public static String encode(byte[] source) {
        return MD5Utils.encode(source, null, 1);
    }
    
    public static String encode(byte[] source,byte[] key) {
        ByteBuffer buffer = ByteBuffer.allocate(source.length+key.length);
        buffer.put(source);
        buffer.put(key);
        return MD5Utils.encode(buffer.array());
    }
    
    public static void main(String[] args) {
        byte[] src = new byte[] {-86, 1, 31, 123, 34, 102, 114, 111, 109, 34, 58, 34, 81, 83, 89, 95, 48, 48, 48, 
                48, 49, 34, 44, 34, 100, 101, 115, 116, 34, 58, 34, 42, 34, 125, -109, 0, 0, 0, 2, 123, 34, 109, 97, 
                114, 107, 49, 34, 58, 50, 44, 34, 116, 105, 109, 101, 70, 108, 111, 119, 78, 111, 34, 58, 34, 50, 48, 
                49, 57, 48, 54, 49, 51, 49, 52, 49, 56, 50, 56, 49, 48, 34, 44, 34, 102, 105, 108, 101, 78, 97, 109, 
                101, 34, 58, 34, 50, 48, 49, 57, 48, 54, 49, 51, 49, 52, 49, 56, 50, 56, 49, 48, 95, 49, 46, 106, 112, 
                103, 34, 44, 34, 109, 101, 100, 105, 97, 84, 105, 109, 101, 34, 58, 34, 50, 48, 49, 57, 45, 48, 54, 45, 49, 
                50, 32, 49, 54, 58, 48, 57, 58, 51, 48, 46, 49, 50, 51, 34, 44, 34, 100, 105, 114, 101, 99, 116, 105, 111, 
                110, 34, 58, 49, 44, 34, 115, 105, 122, 101, 34, 58, 49, 48, 56, 55, 53, 50, 57, 125, 50, 97, 48, 49, 52, 
                50, 102, 48, 54, 54, 97, 56, 49, 56, 50, 51, 56, 54, 54, 48, 50, 55, 55, 53, 98, 49, 52,
                101, 98, 54, 99, 53, 51, 52, 100, 100, 48, 101, 101, 52, 101, 56, 102, 52, 97, 55, 57, 97, 98,
                102, 57, 55, 57, 55, 54, 97, 52, 56, 56, 100, 54, 54, 100, 97, 52, 100, 50, 98, 54, 98, 55, 51, 
                52, 100, 52, 51, 101, 55, 50, 56, 50, 102, 57, 98, 100, 99, 55, 101, 57, 101, 99, 52, 54, 101, 101, 51, 98, 
                57, 102, 52, 49, 50, 54, 48, 50, 99, 54, 53, 53, 102, 101, 53, 51, 50, 53, 48, 99, 
                101, 48, 98, 98, 101, 98, 53, 51, 54, 52, 51
        };
        
        System.out.println(MD5Utils.encode(src,null,1));

/*
        System.out.println(MD5Utils.encode(("abcdefghijklmnopqrstuvwxyz").getBytes(),"123",1));
        System.out.println(MD5Utils.encode(("abcdefghijklmnopqrstuvwxyz123").getBytes(),"",1));
        //21232f297a57a5a743894a0e4a801fc3
        //21232f297a57a5a743894a0e4a801fc3
        
        //a66abb5684c45962d887564f08346e8d
        //a66abb5684c45962d887564f08346e8d
        Sha512Hash sha = new Sha512Hash(("assdfffffffssssssssssssssssffffffffffffffffffsffffffdmin"+"123456").getBytes(), null);
        System.out.println(sha.toHex());
        
        Md2Hash hash = new Md2Hash("assdfffffffssssssssssssssssffffffffffffffffffsffffffdmin"+"123456",null,1);
        System.out.println(hash.toHex());*/
    }
}
