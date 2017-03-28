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
import algo.RC6;
//import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
//import java.io.File;
//import java.util.Arrays;
/**
 *
 * @author rockiericker
 */
public class Enkrip {
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

public String enkripText(String plaintext,String password){
String enkrip="";
System.out.print(enkrip);
Enkrip enk =new Enkrip();
byte[] plain = enk.STB2(plaintext);
byte[] pass = enk.STB2(password);

byte [] en = rc6.encrypt(plain , pass);
String bts = enk.byte2String2(en);
enkrip = new String(bts);
return enkrip;
}  

public String deskripText(String chipertext,String password){
String deskrip ="";
System.out.println(deskrip);
Enkrip dek =new Enkrip();
byte[] cip = dek.STB2(chipertext);
byte[] pass = dek.STB2(password);

byte [] de = rc6.decrypt(cip, pass);
String bts2 = dek.byte2String2(de);
deskrip = new String(bts2);
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

/*public byte[] enkripFile(File file,String password,javax.swing.JProgressBar persenProgessbar){
        FileInputStream inputStream;
        FileOutputStream outputStream;    
        byte [] enkripFile =null;
        int persen =0;
        byte[] byteFile = new byte[(int)file.length()];
        try{
            inputStream = new FileInputStream(file);
            inputStream.read(byteFile);
            inputStream.close();
        }catch(Exception e){
        }
        String fileString ="";
        fileString = new String(byteFile);
        String status =enkripText("Sudah","Di isi");
        if(fileString.contains(status)){
            pesanKesalahan="File Sudah Di Enkripsi";
            return null;
        }     
        
        String extension = "";
        //fileString = Base64.encode(byteFile);
//        byte[] EnkripFile = rc6.encrypt(byteFile, password.getBytes(), persenProgessbar);
        //byte[] EnkripFile = rc6.encrypt(byteFile, password.getBytes());
        
        String enkrip =Arrays.toString(EnkripFile)+"namaFile :"+file.getName()+"Key : "+password;
        
        String encodeString =Base64.encode(new String(EnkripFile).getBytes());//+status;
        encodeString+="jeda : "+status;
        enkripFile =encodeString.getBytes();
//return  enkripFile;   
return  enkripFile;   
}


*/
/*public byte[] deskripFile(File file,String password){
String extension="";   
FileInputStream inputStream;
FileOutputStream outputStream;    
byte[] enkripFile =null;
int persen =0;
byte[] byteFile = new byte[(int)file.length()];
try{
    inputStream= new FileInputStream(file);
    inputStream.read(byteFile);
    inputStream.close();                   
}catch(Exception exc){}
    String fileString =new String(byteFile);
    String status =enkripText("Sudah","Di isi");
    if(!fileString.contains(status)){
         pesanKesalahan="File Belum Terenkripsi";
         return null;
    }
    /*fileString=fileString.replaceAll("jeda : "+status,"");
    byte [] decodeByte=Base64.decode(fileString);
    char[] fileByte =new String(decodeByte).toCharArray();
    rc4.keyString(password);
    char fileChar[]= rc4.decrypt(fileByte,persenProgessbar);
    fileString = new String(fileChar);
    String pass =fileString.substring(fileString.lastIndexOf("Key : ") + 6);
    System.out.print(pass);
    fileString=fileString.replaceAll("jeda : "+status,"");
    byte [] decodeByte=Base64.decode(fileString);
    char[] fileByte =new String(decodeByte).toCharArray();
    
    fileString = new String(fileByte);
    String pass =fileString.substring(fileString.lastIndexOf("Key : ") + 6);
    System.out.print(pass);
    if(!pass.equals(password)){
        pesanKesalahan ="Password Salah";
        return null; 
    }
    fileString = fileString.replaceAll("Key : "+pass,"");
    namaFile= fileString.substring(fileString.lastIndexOf("namaFile :") + 10);
    fileString = fileString.replaceAll("namaFile :"+namaFile,"");
    byte[] DeskripFile = rc6.decrypt(fileString.getBytes());
    byte[] DeskripBytes =  Base64.decode(DeskripFile.toString());
    enkripFile =DeskripBytes;
    
    return enkripFile;
}
/*
*/
}
