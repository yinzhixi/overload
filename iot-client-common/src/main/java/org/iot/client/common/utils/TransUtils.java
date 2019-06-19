package org.iot.client.common.utils;

public class TransUtils {
    public static long byteToLong(byte b1,byte b2,byte b3,byte b4) {
        int i1,i2,i3,i4;
        if(b1 < 0) {
            i1 = (256+b1);
        }else {
            i1 = b1;
        }
        
        if(b2 < 0) {
            i2 = (256+b2)*256;
        }else {
            i2 = b2*256;
        }
        
        if(b3 < 0) {
            i3 = (256+b3)*65536;
        }else {
            i3 = b3*65536;
        }
        
        if(b4 < 0) {
            i4 = (256+b4)*65536*256;
        }else {
            i4 = b4*65536*256;
        }
        return i1+i2+i3+i4;
    }
    
    public static long bytesToLong(byte b1,byte b2,byte b3,byte b4) {
        long i1,i2,i3,i4;
        i1 = Byte.toUnsignedInt(b1);
        i2 = Byte.toUnsignedInt(b2);
        i3 = Byte.toUnsignedInt(b3);
        i4 = Byte.toUnsignedInt(b4);
        return i1 + 
                (i2 << 8 )+ 
                (i3 << 16) +
                (i4 << 24);
    }
    
    public static long bytesToLongLH(byte[] bs) {
        long[] ls = new long[bs.length];
        long total = 0;
        for(int i = 0;i < bs.length;i++) {
            ls[i] = Byte.toUnsignedInt(bs[i]);
            total = total + (ls[i] << (i * 8));
        }
        return total;
    }

    public static long bytesToLongHL(byte[] bs) {
        long[] ls = new long[bs.length];
        long total = 0;
        for(int i = bs.length - 1,j=0;i >= 0;i--,j++) {
            ls[i] = Byte.toUnsignedInt(bs[i]);
            total = total + (ls[i] << (j * 8));
        }
        return total;
    }
    
    /**
     * 小端：[0] 低位 ，[3] 高位
     * @param value
     * @return
     */
    public static byte[] longToBytes(long value){
        byte[] bs = new byte[4];
        bs[0] = (byte)(value % (0x100l));
        bs[1] = (byte)((value % 0x10000l) / 0x100l);
        bs[2] = (byte)((value % 0x1000000l) / 0x10000l);
        bs[3] = (byte)((value % 0x100000000l) / 0x1000000l);
        return bs;
    }
    
    public static byte[] longToBytes2(long value) {
        String hexStr = Long.toHexString(value);
        return TransUtils.hexStringToBytesLH(hexStr);
    }
    
    public static void main(String[] args) {
        /*long num = 0;
        byte[] bs = new byte[4];
        bs[0] = 0;
        bs[1] = (byte)0xc0;
        bs[2] = 0;
        bs[3] =0;
        System.out.println(TransUtils.bytesToLong(bs[0],bs[1],bs[2],bs[3]));
        bs = TransUtils.longToBytes2(148603693);
        System.out.println(String.format("%x%x%x%x", bs[0],bs[1],bs[2],bs[3]));
         */
        
        byte[] bs = TransUtils.longToBytes(258);
        System.out.println(String.format("%x,%x,%x,%x", bs[0],bs[1],bs[2],bs[3]));
        
        
        /*
        System.out.println(Long.MAX_VALUE);
        for (;num < Long.MAX_VALUE;num+=1) {
            byte[] bs = TransUtils.longToBytes(num);
            long v = TransUtils.bytesToLong(bs[0],bs[1],bs[2],bs[3]);
            long v1 = TransUtils.byteToLong(bs[0],bs[1],bs[2],bs[3]);
            System.out.println(num+","+v+","+v1);
        }*/
    }
    
    /**
    * Convert byte[] to hex string.这里我们可以将byte转换成int，然后利用Integer.toHexString(int)来转换成16进制字符串。  
    * @param src byte[] data  
    * @return hex string  
    */
   public static String bytesToHexString(byte[] src){  
       StringBuilder stringBuilder = new StringBuilder("");
       if (src == null || src.length <= 0) {
           return null;
       }
       for (int i = 0; i < src.length; i++) {  
           int v = src[i] & 0xFF;
           String hv = Integer.toHexString(v);
           if (hv.length() < 2) {
               stringBuilder.append(0);
           }
           stringBuilder.append(hv);
       }  
       return stringBuilder.toString();
   }  
   
   /** 
    * Convert hex string to byte[] 
    * @param hexString the hex string 
    * @return byte[] 高位在前
    */  
   public static byte[] hexStringToBytesHL(String hexString) {  
       if (hexString == null || hexString.equals("")) {  
           return null;
       }  
       if(hexString.length() % 2 == 1) {
           hexString = "0" + hexString;
       }
       hexString = hexString.toUpperCase();
       int length = hexString.length() / 2;
       char[] hexChars = hexString.toCharArray();
       
       byte[] d = new byte[length];
       for (int i = 0; i < length; i++) {  
           int pos = i * 2;
           d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
       }  
       return d;
   }

   /** 
    * Convert hex string to byte[] 
    * @param hexString the hex string 
    * @return byte[] 低位在前
    */  
   public static byte[] hexStringToBytesLH(String hexString) {  
       if (hexString == null || hexString.equals("")) {  
           return null;
       }  
       if(hexString.length() % 2 == 1) {
           hexString = "0" + hexString;
       }
       hexString = hexString.toUpperCase();
       int length = hexString.length() / 2;
       char[] hexChars = hexString.toCharArray();
       
       byte[] d = new byte[length];
       for (int i = 0; i < length; i++) {  
           int pos = i * 2;
           d[length -1 -i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
       }
       return d;
   }
   /** 
    * Convert char to byte 
    * @param c char 
    * @return byte 
    */  
    public static byte charToByte(char c) {  
       return (byte) "0123456789ABCDEF".indexOf(c);
   }
    
}
