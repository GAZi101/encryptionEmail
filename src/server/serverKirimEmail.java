/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import enkripsi.Enkrip;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 *
 * @author Resa S.A
 */
public class serverKirimEmail {
private final Enkrip enkrip = new Enkrip();    
private String pesanKesalahan="";
private String user,password;
    public String getPesanKesalahan() {
        return pesanKesalahan;
    }
private void loadAkun() throws IOException {
String direktori = System.getProperty("user.dir");
Properties props = new Properties();
File configFile = new File(direktori+"/src/seting/akunProperties.properties");    
		if (configFile.exists()) {
			InputStream inputStream = new FileInputStream(configFile);
			props.load(inputStream);
			inputStream.close();
                        
		}
		user=enkrip.deskripText2(props.getProperty("mail.user"), "budiluhur");
                password=enkrip.deskripText2(props.getProperty("mail.password"), "budiluhur");
	}
private Properties loadPropertiesSMTP() throws IOException {
String direktori = System.getProperty("user.dir");
Properties props = new Properties();
File configFile = new File(direktori+"/src/seting/SMTPProperties.properties");    
		if (configFile.exists()) {
			InputStream inputStream = new FileInputStream(configFile);
			props.load(inputStream);
			inputStream.close();
                        
		}
		return props;
	}


public boolean sendemailcompose(String toAddress,String subject, String message, File[] attachFiles){
  boolean valid = true;  
  Authenticator auth = new Authenticator() {
    public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user, password);
   
    }
   };  
  try{
        loadAkun();
        System.out.println(password);
       Session session = Session.getInstance(loadPropertiesSMTP(), auth);
                Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(user));
		InternetAddress[] toAddresses = { new InternetAddress(toAddress) };
		msg.setRecipients(Message.RecipientType.TO, toAddresses);
		msg.setSubject(subject);
		msg.setSentDate(new Date());
		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setContent(message, "text/html");
		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);
                
                if (attachFiles != null ) {
            for (File attachFile : attachFiles) {
                MimeBodyPart attachPart = new MimeBodyPart();
                attachPart.attachFile(attachFile);
                multipart.addBodyPart(attachPart);
            }
           }
		
                
                
		msg.setContent(multipart);
		Transport.send(msg);

  }catch(IOException e){
  pesanKesalahan="Error Load Protocol SMTP\n"+e.getMessage();
  valid = false;
  }catch(AddressException e){
   pesanKesalahan="Kesalahan Penerima\n"+e.getMessage();
  valid = false;
  }catch(MessagingException e){
   pesanKesalahan="Pengiriman Email Gagal\n"+e.getMessage();
  valid = false;
  }catch(Exception e){
    pesanKesalahan="Pengiriman Email Gagal\n"+e.getMessage();
  valid = false;
  }
  return valid;
}
  
    public void sendEmail(Properties smtpProperties, String toAddress,
			String subject, String message, File attachFiles,Message msgForward)
			throws AddressException, MessagingException, IOException {

		final String userName = smtpProperties.getProperty("mail.user");
		final String password = smtpProperties.getProperty("mail.password");
		
		// buat Sesion baru untuk autentikator
		Authenticator auth = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName, password);
			}
		};
		Session session = Session.getInstance(smtpProperties, auth);
	
		Message msg = new MimeMessage(session);

		msg.setFrom(new InternetAddress(userName));
		InternetAddress[] toAddresses = { new InternetAddress(toAddress) };
		msg.setRecipients(Message.RecipientType.TO, toAddresses);
		msg.setSubject(subject);
		msg.setSentDate(new Date());


		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setContent(message, "text/html");

		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);
            

		if (attachFiles != null ) {
	
				MimeBodyPart attachPart = new MimeBodyPart();
                    attachPart.attachFile(attachFiles);

				multipart.addBodyPart(attachPart);
			}
		
                    if(msgForward !=null){
                MimeBodyPart forwarBodyPart = new MimeBodyPart();
                forwarBodyPart.setDataHandler(msgForward.getDataHandler());
                multipart.addBodyPart(forwarBodyPart);
                }
                
		msg.setContent(multipart);

		Transport.send(msg);

	}
}
