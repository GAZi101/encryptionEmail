/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enkripsi;


//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import algo.RC4;
import algo.Des;
import algo.RC4;
import algo.RC6;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
//import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
//import java.io.File;
//import java.util.Arrays;
/**
 *
 * @author rockiericker
 */
public class Enkrip {
private final RC4 rc4 =new RC4();
private final RC6 rc6 =new RC6();
private final Des des = new Des();
private String pesanKesalahan="";
private String namaFile;

    public String getNamaFile() {
        return namaFile;
    }

    public String getPesanKesalahan() {
        return pesanKesalahan;
    }

public String enkripText2(String plaintext,String password){
String enkrip="";
System.out.print(enkrip);
Enkrip enk =new Enkrip();
byte[] plain = enk.STB2(plaintext);
byte[] pass = enk.STB2(password);

byte [] en = rc6.encrypt(plain , pass);
String bts = enk.byte2String2(en);
enkrip = new String(bts);
//return enkrip;
System.out.println("Cipher :"+enkrip);
    return enkrip;
}  

public String deskripText2(String chipertext,String password){
String deskrip ="";
System.out.print(deskrip);
Enkrip dek =new Enkrip();
byte[] cip = dek.STB2(chipertext);
byte[] pass = dek.STB2(password);

byte [] de = rc6.decrypt(cip, pass);
String bts2 = dek.byte2String2(de);
deskrip = new String(bts2);
System.out.println("Plain :"+deskrip);
return deskrip;
}

String byte2String2(byte[]data){
    String gab="";
        for(int i=0; i < data.length; i++) {
              char c=(char)(int)data[i];
              gab=gab+c;
         }
        return gab;
}
byte [] STB2(String s){
            char [] x = s.toCharArray();
            byte [] hs = new byte[x.length];
            for (int i = 0;i<x.length;i++){
                hs [i] = (byte) (int)x[i];
            }
            return hs;
 }
}