/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import view.pesanDialog;
import enkripsi.Enkrip;
import java.awt.Cursor;
import javax.swing.JOptionPane;
import server.serverGmail;
import view.viewEmail;

/**
 *
 * @author rockiericker
 */
public class loginController {
  
private final Enkrip enkrip = new Enkrip();    
serverGmail servergmail = new serverGmail();
private final pesanDialog pesan = new pesanDialog();
private boolean loadingAnimasi=true;   
SaveOfflineController soc = new SaveOfflineController();

public void login(final javax.swing.JPasswordField passwordField,final javax.swing.JTextField alamatEmailTextField,final javax.swing.JLabel loadingLabel,final javax.swing.JFrame frame){
boolean valid = true;

if(alamatEmailTextField.getText().equals("")&&passwordField.getText().equals("")){
    valid = false;
    JOptionPane.showMessageDialog(null, "Alamat Email dan Password Harus Di Isi", "Informasi",JOptionPane.INFORMATION_MESSAGE);
    return;
}else{
    if(alamatEmailTextField.getText().equals("")){
        valid = false;
        JOptionPane.showMessageDialog(null, "Alamat Email Harus Di Isi", "Informasi",JOptionPane.INFORMATION_MESSAGE);
        return;    
    }
    if(passwordField.getText().equals("")){
        valid = false;
        JOptionPane.showMessageDialog(null, "Password Harus Di Isi", "Informasi",  JOptionPane.INFORMATION_MESSAGE);
        return;
    }
}
if(!alamatEmailTextField.getText().toLowerCase().contains("gmail.com")||alamatEmailTextField.getText().toLowerCase().contains("yahoo.com")||
        alamatEmailTextField.getText().toLowerCase().contains("yahoo.co.id")){
    valid = false;
    JOptionPane.showMessageDialog(null, "Email Tidak Valid", "Informasi",JOptionPane.INFORMATION_MESSAGE);
    return;
}
if(!servergmail.cekKoneksi()){
    viewEmail viewemail = new viewEmail();
    viewemail.setCursor(new Cursor(Cursor.WAIT_CURSOR));
    if(soc.bacaUser(alamatEmailTextField.getText().toLowerCase())){  
        if(soc.password.equals(passwordField.getText())){
            Thread treet = new Thread(){
            public void run(){
            Properties propsImap =new Properties();    
            Properties propsSMTP =new Properties();  
            Properties propsAkun =new Properties();       
            String host=alamatEmailTextField.getText().toLowerCase();
            
            if(host.contains("@gmail")){
                propsImap.put(String.format("mail.%s.host","imap"),"imap.gmail.com");
                propsImap.put(String.format("mail.%s.port","imap"), "993");
                propsImap.setProperty(String.format("mail.%s.socketFactory.class","imap"), "javax.net.ssl.SSLSocketFactory");
                propsImap.setProperty(String.format("mail.%s.socketFactory.fallback","imap"), "false");
                propsImap.setProperty(String.format("mail.%s.socketFactory.port","imap"), String.valueOf("993"));
                propsSMTP.setProperty("mail.smtp.host","smtp.gmail.com");
                propsSMTP.setProperty("mail.smtp.port", "587");
                propsSMTP.setProperty("mail.smtp.starttls.enable", "true");
                propsSMTP.setProperty("mail.smtp.auth", "true");
            }else if(host.contains("@yahoo.com")){
                propsImap.put(String.format("mail.%s.host","imap"),"imap.mail.yahoo.com");
                propsImap.put(String.format("mail.%s.port","imap"), "993");
                propsImap.setProperty(String.format("mail.%s.socketFactory.class","imap"), "javax.net.ssl.SSLSocketFactory");
                propsImap.setProperty(String.format("mail.%s.socketFactory.fallback","imap"), "false");
                propsImap.setProperty(String.format("mail.%s.socketFactory.port","imap"), String.valueOf("993"));
                propsSMTP.setProperty("mail.smtp.host","smtp.mail.yahoo.com");
                propsSMTP.setProperty("mail.smtp.port", "587");
                propsSMTP.setProperty("mail.smtp.starttls.enable", "true");
                propsSMTP.setProperty("mail.smtp.auth", "true");
                
            }else{
                propsImap.put(String.format("mail.%s.host","imap"),"imap.mail.yahoo.co.id");
                propsImap.put(String.format("mail.%s.port","imap"), "993");
                propsImap.setProperty(String.format("mail.%s.socketFactory.class","imap"), "javax.net.ssl.SSLSocketFactory");
                propsImap.setProperty(String.format("mail.%s.socketFactory.fallback","imap"), "false");
                propsImap.setProperty(String.format("mail.%s.socketFactory.port","imap"), String.valueOf("993"));
                propsSMTP.setProperty("mail.smtp.host","smtp.mail.yahoo.co.id");
                propsSMTP.setProperty("mail.smtp.port", "587");
                propsSMTP.setProperty("mail.smtp.starttls.enable", "true");
                propsSMTP.setProperty("mail.smtp.auth", "true");
            }
                propsAkun.setProperty("mail.user",enkrip.enkripText(alamatEmailTextField.getText(),"budiluhur"));
                propsAkun.setProperty("mail.password",enkrip.enkripText(passwordField.getText(),"budiluhur"));
                loadingAnimasi=false;
                String direktori= System.getProperty("user.dir");
                File setingImap = new File(direktori+"/src/seting/imapProperties.properties");
                File setingSMTP = new File(direktori+"/src/seting/SMTPProperties.properties");
                File setingAkun = new File(direktori+"/src/seting/akunProperties.properties");
                try{
                    OutputStream outputStream = new FileOutputStream(setingImap);
                    propsImap.store(outputStream, "imap setings");
                    outputStream.close();
                    outputStream = new FileOutputStream(setingSMTP);
                    propsSMTP.store(outputStream, "smtp setings");
                    outputStream.close();
                    outputStream = new FileOutputStream(setingAkun);
                    propsAkun.store(outputStream, "akun setings");
                    JOptionPane.showMessageDialog(null, "BERHASIL", "Informasi",JOptionPane.INFORMATION_MESSAGE);
                    viewEmail viewemail= new viewEmail();
                    frame.dispose();  
                    viewemail.setVisible(true);
                    outputStream.close();
                }catch(IOException e){
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                }


            }
        };treet.start();
        }else{
            JOptionPane.showMessageDialog(null, "Password salah", "Informasi",JOptionPane.INFORMATION_MESSAGE);
        }
    }else{
        if(!soc.pesan.equals("")){
           JOptionPane.showMessageDialog(null, "Alamat email tidak ditemukan\n"+soc.pesan, "Informasi",JOptionPane.INFORMATION_MESSAGE);
           soc.pesan="";
           System.out.println(soc.pesan);
        }else
        JOptionPane.showMessageDialog(null, "Akun tidak memiliki data offline", "Informasi",JOptionPane.INFORMATION_MESSAGE);        
    }
    viewemail.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
}else
if(valid){
Thread treet3 = new Thread() {
public void run() {
do{
          for(int i=1;i<8;i++){
    loadingLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/loading_acount_"+i+".png")));
    try{
    Thread.sleep(150);
    }catch(InterruptedException  e){
    }
    }
}    
while(loadingAnimasi);
loadingLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/loading_acount_"+0+".png")));
}
};treet3.start();    
Thread treet = new Thread(){
public void run(){
Properties propsImap =new Properties();    
Properties propsSMTP =new Properties();  
Properties propsAkun =new Properties();       
String host=alamatEmailTextField.getText().toLowerCase();
    try{
       if(host.contains("@gmail")){
            propsImap.put(String.format("mail.%s.host","imap"),"imap.gmail.com");
            propsImap.put(String.format("mail.%s.port","imap"), "993");
            propsImap.setProperty(String.format("mail.%s.socketFactory.class","imap"), "javax.net.ssl.SSLSocketFactory");
            propsImap.setProperty(String.format("mail.%s.socketFactory.fallback","imap"), "false");
            propsImap.setProperty(String.format("mail.%s.socketFactory.port","imap"), String.valueOf("993"));
            propsSMTP.setProperty("mail.smtp.host","smtp.gmail.com");
            propsSMTP.setProperty("mail.smtp.port", "587");
            propsSMTP.setProperty("mail.smtp.starttls.enable", "true");
            propsSMTP.setProperty("mail.smtp.auth", "true");
        }else if(host.contains("@yahoo.com")){
            propsImap.put(String.format("mail.%s.host","imap"),"imap.mail.yahoo.com");
            propsImap.put(String.format("mail.%s.port","imap"), "993");
            propsImap.setProperty(String.format("mail.%s.socketFactory.class","imap"), "javax.net.ssl.SSLSocketFactory");
            propsImap.setProperty(String.format("mail.%s.socketFactory.fallback","imap"), "false");
            propsImap.setProperty(String.format("mail.%s.socketFactory.port","imap"), String.valueOf("993"));
            propsSMTP.setProperty("mail.smtp.host","smtp.mail.yahoo.com");
            propsSMTP.setProperty("mail.smtp.port", "587");
            propsSMTP.setProperty("mail.smtp.starttls.enable", "true");
            propsSMTP.setProperty("mail.smtp.auth", "true");

        }else{
            propsImap.put(String.format("mail.%s.host","imap"),"imap.mail.yahoo.co.id");
            propsImap.put(String.format("mail.%s.port","imap"), "993");
            propsImap.setProperty(String.format("mail.%s.socketFactory.class","imap"), "javax.net.ssl.SSLSocketFactory");
            propsImap.setProperty(String.format("mail.%s.socketFactory.fallback","imap"), "false");
            propsImap.setProperty(String.format("mail.%s.socketFactory.port","imap"), String.valueOf("993"));
            propsSMTP.setProperty("mail.smtp.host","smtp.mail.yahoo.co.id");
            propsSMTP.setProperty("mail.smtp.port", "587");
            propsSMTP.setProperty("mail.smtp.starttls.enable", "true");
            propsSMTP.setProperty("mail.smtp.auth", "true");
        }

        propsAkun.setProperty("mail.user",enkrip.enkripText(alamatEmailTextField.getText(),"budiluhur"));//enkrip.enkripText(alamatEmailTextField.getText(),"budiluhur"));
        propsAkun.setProperty("mail.password",enkrip.enkripText(passwordField.getText(),"budiluhur"));//enkrip.enkripText(passwordField.getText(),"budiluhur"));
        Session session = Session.getDefaultInstance(propsImap);  
        Store store = session.getStore("imap");
        store.connect(alamatEmailTextField.getText().toLowerCase(), passwordField.getText());
        loadingAnimasi=false;
        String direktori= System.getProperty("user.dir");
        File setingImap = new File(direktori+"/src/seting/imapProperties.properties"); 
        File setingSMTP = new File(direktori+"/src/seting/SMTPProperties.properties"); 
        File setingAkun = new File(direktori+"/src/seting/akunProperties.properties"); 
    try{
        OutputStream outputStream = new FileOutputStream(setingImap);
        propsImap.store(outputStream, "imap setings");
        outputStream.close();
        outputStream = new FileOutputStream(setingSMTP);
        propsSMTP.store(outputStream, "smtp setings");
        outputStream.close();
        outputStream = new FileOutputStream(setingAkun);
        propsAkun.store(outputStream, "akun setings");   
        viewEmail viewemail = new viewEmail();
        soc.userID = alamatEmailTextField.getText().toLowerCase();
        soc.password = passwordField.getText();
        if(soc.simpanUser()){               
            frame.dispose();  
            viewemail.setVisible(true);
        }else{
            System.out.println("gagal menyimpan akun /n"+soc.pesan);
            JOptionPane.showMessageDialog(null,soc.pesan);
            Thread.currentThread().interrupt();
        } 
            outputStream.close();
    }catch(IOException e){
         e.printStackTrace();
         System.out.println(e.getMessage());
    }
    } catch (MessagingException ex) {  
        pesan.setPesan("Gagal Koneksi Ke Akun\n"+ex.getMessage());
        pesan.setJudul("Informasi");
        pesan.pesan();
        loadingAnimasi=false;
    }

      
}
};treet.start();
}
}
}
