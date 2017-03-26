/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enkripsi;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import algo.RC4;
import algo.Des;
import algo.RC6;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import java.io.File;
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

public String enkripText(String plaintext,String password){
String enkrip="";
System.out.print(enkrip);
String EnkripFile = des.enkrip(plaintext+" Key : "+password, password,null);
        rc4.keyString(password);
        char []plaintextChar= rc4.encrypt(EnkripFile.toCharArray(),null);
        String encodeString =Base64.encode(new String(plaintextChar).getBytes());
        plaintextChar =encodeString.toCharArray();
        enkrip = new String(plaintextChar);
return enkrip;
}  

public String deskripText(String chipertext,String password){
String deskrip ="";
rc4.keyString(password);
try{
byte [] decodeByte=Base64.decode(chipertext);
char[] chperByte =new String(decodeByte).toCharArray();
deskrip = new String(rc4.encrypt(chperByte, null));
deskrip=des.deskrip(deskrip, password,null);
if(!deskrip.substring(deskrip.lastIndexOf("Key") + 6).equals(password)){
           pesanKesalahan ="Password Salah";
           return ""; 
        }
deskrip = deskrip.substring(0, deskrip.lastIndexOf("Key"));
}catch(Exception e){
pesanKesalahan ="Password Salah";
return ""; 
}
return deskrip;
}




public byte[] enkripFile(File file,String password,javax.swing.JProgressBar persenProgessbar){
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
        //fileString="";
//Base64.encode(byteFile);
        fileString = Base64.encode(byteFile);
        String EnkripFile = des.enkrip(fileString, password, persenProgessbar);
        //byte[] EnkripFile = rc6.encrypt(byteFile, password.getBytes());
        
        String enkrip =EnkripFile+"namaFile :"+file.getName()+"Key : "+password;
        
        String encodeString =Base64.encode(new String(enkrip).getBytes());//+status;
        encodeString+="jeda : "+status;
        enkripFile =encodeString.getBytes();
//return  enkripFile;   
return  enkripFile;   
}



public byte[] deskripFile(File file,String password,javax.swing.JProgressBar persenProgessbar){
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
    System.out.print(pass);*/
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
    String DeskripFile = des.deskrip(fileString, password,persenProgessbar);
    byte[] DeskripBytes =  Base64.decode(DeskripFile);
    enkripFile =DeskripBytes;
    
    return enkripFile;
}
byte [] STB2(String s){
            char [] x = s.toCharArray();
            byte [] hs = new byte[x.length];
            for (int i = 0;i<x.length;i++){
                hs [i] = (byte) (int)x[i];
            }
            return hs;
 }
/*
*/
}
