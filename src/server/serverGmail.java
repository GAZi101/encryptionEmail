/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import controller.SaveOfflineController;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Store;
import javax.mail.event.MessageCountAdapter;
import javax.mail.event.MessageCountEvent;
import javax.mail.internet.MimeBodyPart;
import javax.swing.JFileChooser;
import java.text.DecimalFormat;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import editSwing.RenderWarnaWarni;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Properties;
import javax.mail.Session;
import view.bacaEmal;
import view.pesanDialog;
import view.dekripFile;
import view.lihatEmail;
import enkripsi.Enkrip;
import java.net.URL;
import java.net.URLConnection;

/**
 *
 * @author Resa S.A
 */
public class serverGmail {  
private final Enkrip enkrip = new Enkrip();    
private String user,password;
dekripFile viewDeskrip;    
bacaEmal viewbacaEmail;
private final pesanDialog pesandialog= new pesanDialog();  
DefaultTableModel inboxTableModel,sentTableModel,draftTableModel;  
private static FileInputStream inputStream;
private static FileOutputStream outputStream;
   private JFileChooser jfc;    
JFrame parentFrame = new JFrame(); 
private static boolean loadingInbox=true,loadingSent=true,startInbox=false,ready=true,startSent=false,monitoringReady=true;
int load=0,sleep=0;    
SaveOfflineController soc = new SaveOfflineController();
public String dataOffline="";

    public String getUser() {
        return user;
    }
    
private static Folder inbox,sent;
private String pesan;
private static Message[] arrayMessagesInbox,arrayMessagesSent,arrayMessagesDraft;
private static  MimeBodyPart part[]= new MimeBodyPart[100];
private static boolean refresh=true;

public boolean cekKoneksi(){
    boolean konektivitas;
    try{
        URL url = new URL("http://accounts.google.com");
        URLConnection conn = url.openConnection();
        conn.connect();
        konektivitas = true;
        System.out.println("Connected");
    }catch(IOException e){
        konektivitas = false;
        System.out.println("Not Connected");
    }
    return konektivitas;
}
public void loadAkun() throws IOException {
String direktori = System.getProperty("user.dir");
Properties props = new Properties();
File configFile = new File(direktori+"/src/seting/akunProperties.properties");    
		if (configFile.exists()) {
			InputStream inputStream = new FileInputStream(configFile);
			props.load(inputStream);
			inputStream.close();
                        
		}
		user=enkrip.deskripText(props.getProperty("mail.user"), "budiluhur");//props.getProperty("mail.user");//enkrip.deskripText(props.getProperty("mail.user"), "budiluhur");
                password=enkrip.deskripText(props.getProperty("mail.password"), "budiluhur");//props.getProperty("mail.password");//enkrip.deskripText(props.getProperty("mail.password"), "budiluhur");
                //System.out.println(password);
}
public void closed(){    
loadingInbox=false;loadingSent=false;startInbox=false;ready=true;startSent=false;
refresh=false;try{
 inbox.close(true);
 sent.close(true);
 Thread t = Thread.currentThread();
 System.out.println("ThreadName"+t.getName());
}catch(Exception e){
}
}

private Properties loadPropertiesIMAP() throws IOException {
String direktori = System.getProperty("user.dir");
Properties props = new Properties();
File configFile = new File(direktori+"/src/seting/imapProperties.properties");    
		if (configFile.exists()) {
			InputStream inputStream = new FileInputStream(configFile);
			props.load(inputStream);
			inputStream.close();
                        
		}
		return props;
	}



private static Session session =null;
private static Store store =null;
public void readEmail(final javax.swing.JTable inboxTable ,final javax.swing.JTable sentTable){ 
  
 inboxTable.setDefaultRenderer(Object.class, new RenderWarnaWarni(3));
 sentTable.setDefaultRenderer(Object.class, new RenderWarnaWarni(3));
boolean valid =true; 
try{
  loadAkun();    
session= Session.getDefaultInstance(loadPropertiesIMAP());
}catch(IOException e){
valid = false;
}
try{
store =session.getStore("imap");
store.connect(user,password);
//Chek Folder Yang Tersedia Pada Store Gmail    
Folder[] folderList = store.getFolder("[Gmail]").list();         
 for (int i = 0; i < folderList.length; i++) {
            System.out.println(folderList[i].getFullName());
        }   
 inbox = store.getFolder("INBOX");
 sent =store.getFolder(folderList[5].getFullName());

//Open Folder Inbox Jenis Read dan Write
 inbox.open(Folder.READ_WRITE);
 //Open Folder Sent Jenis Read dan Write
 sent.open(Folder.READ_WRITE);

//Tampung Email Pada ArrayMessages 
 arrayMessagesInbox = inbox.getMessages();
 arrayMessagesSent = sent.getMessages();

//Inisialisasi awal Model JTable inboxTable dan sentTable
inboxTableModel = (DefaultTableModel) inboxTable.getModel();
sentTableModel = (DefaultTableModel) sentTable.getModel();  

//jika refresh true maka akan dihapus semua email pada table(Hanya Ketika dilakukan refreshs)

while(inboxTableModel.getRowCount()>0){
    for(int i=0;i<inboxTableModel.getRowCount();i++){
    inboxTableModel.removeRow(i);
    }
}
while(sentTableModel.getRowCount()>0){
    for(int i=0;i<sentTableModel.getRowCount();i++){
   sentTableModel.removeRow(i);
    }
}



//Buat Thread pada pembacaan inbox agar program tidak lag karna proses pembacaan yang lama
//Thread juga bisa dikatakan melempar suati proses ke alokasi memori lain sehingga program induk dapat menskipnya 
//Tetapi secara teori tidak seperti itu (hanya untuk pemahaman saja )

Thread treetInbox = new Thread() {
 public void run() {
inbox.addMessageCountListener(new MessageCountAdapter(){
public void messagesAdded(MessageCountEvent ev) {
System.out.println("message is ok.");
Message[] msgs = ev.getMessages();
System.out.println("Get " + msgs.length + " new messages");
for (int i = 0; i < msgs.length; i++) {
try {
ready=false;    
Message message = msgs[i];
String contentType = message.getContentType();
Address[] fromAddress = message.getFrom();
String tanggal = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").format(message.getSentDate());
String form1[] = new String[inboxTableModel.getRowCount()];
String subject1[] = new String[inboxTableModel.getRowCount()];
String conten1[] = new String[inboxTableModel.getRowCount()];
String date1[]= new String[inboxTableModel.getRowCount()];
String status1[]=new String[inboxTableModel.getRowCount()];
for(int count=0;count<inboxTableModel.getRowCount();count++){
    try{    
status1[count]=inboxTable.getValueAt(count, 0).toString();
}catch(Exception e){
    status1[count]="";
}
try{    
form1[count]=inboxTable.getValueAt(count, 2).toString();
}catch(Exception e){
    form1[count]="";
}
try{
subject1[count]=inboxTable.getValueAt(count, 3).toString();
}catch(Exception e){
    subject1[count]="";
}
try{
conten1[count]=inboxTable.getValueAt(count, 4).toString();
}catch(Exception e){
conten1[count]="";
}
try{
date1[count]=inboxTable.getValueAt(count, 5).toString();
}catch(Exception e){
    date1[count]="";
}
}
String messageContent="";
      if (contentType.contains("multipart")) {
                    Multipart multiPart = (Multipart) message.getContent();
                    int numberOfParts = multiPart.getCount();
                    for (int partCount = 0; partCount < numberOfParts; partCount++) {
                    part[partCount] = (MimeBodyPart) multiPart.getBodyPart(partCount);
                        if (Part.ATTACHMENT.equalsIgnoreCase(part[partCount].getDisposition())) {
                           //part.saveFile("D:/Attachment"+ File.separator + fileName);
                        } else {
                            // this part may be the message content
                            messageContent = part[partCount].getContent().toString();
                           
                        }
                    }
                } else if (contentType.contains("text/plain")
                        || contentType.contains("text/html")) {
                    Object contents = message.getContent();
                    if (contents != null) {
                      //   JOptionPane.showMessageDialog(null, "adsa", "asd",JOptionPane.INFORMATION_MESSAGE);
                        messageContent = contents.toString();
                    }
                }
String splitPesan[]=messageContent.split("</style>");
String hasilSplit="";
 if(splitPesan.length>1){

 hasilSplit=splitPesan[1];
  
 }else{
 hasilSplit=splitPesan[0];
 }     

String content =hasilSplit.replaceAll("\\<[^>]*>","");  
String replacnbsp=content.replaceAll("&nbsp;","");

while(inboxTableModel.getRowCount()>0){
    for(int a=0;a<inboxTableModel.getRowCount();a++){
    inboxTableModel.removeRow(a);
    }
}
inboxTableModel.addRow(new Object[]{"D",Boolean.FALSE,fromAddress[0].toString(), message.getSubject(),replacnbsp,tanggal});
//Object[][] listInbox = new Object[form1.length][5];
for(int add=0;add<form1.length;add++){
inboxTableModel.addRow(new Object[]{status1[add],Boolean.FALSE,form1[add], subject1[add],conten1[add],date1[add]});
/*System.out.println("OKAY1");
soc.userID = getUser();
soc.date = date1[add].toString();
listInbox[add][0] = status1[add];
listInbox[add][1] = form1[add];
listInbox[add][2] = subject1[add];
listInbox[add][3] = conten1[add];
listInbox[add][3] = date1[add];
soc.listInbox = listInbox;
if(soc.simpanInbox()){
    System.out.println("TERSIMPAN1");
}else{
    JOptionPane.showMessageDialog(null, soc.pesan);
} */
}
ready=true;
System.out.println("-----");
if(msgs[i].getSubject().toLowerCase().startsWith("effort sheet details")){
 System.out.println("Message "
                                + msgs[i].getMessageNumber() + ":");
                        msgs[i].writeTo(System.out);
                         
                    }
                } catch (IOException | MessagingException ex) {
                    ex.printStackTrace();
                }
            }
ready=true;
try{
 arrayMessagesInbox = inbox.getMessages();

}catch(MessagingException ex){
    ex.printStackTrace();
}

        }
 
        /* (non-Javadoc)
         * @see javax.mail.event.MessageCountListener#messagesRemoved(javax.mail.event.MessageCountEvent)
         */
        public void messagesRemoved(MessageCountEvent arg0) {
            // TODO Auto-generated method stub    
                try{
                    inbox.expunge();//expunge adalah proses cek email yang terhapus lalu menghapusnya pada variable inbox
 arrayMessagesInbox = inbox.getMessages();
 ready=true;
System.out.println(arrayMessagesInbox.length);
}catch(Exception e){

}
         
        }
    });
int indexInbox=0;
while(inbox.isOpen()){
      indexInbox=(arrayMessagesInbox.length-1)-inboxTable.getRowCount();
    if(inboxTable.getRowCount()<=100&&ready&&indexInbox>=0){
        loadingInbox=true;
    startInbox=true;
try{  
Message message = arrayMessagesInbox[indexInbox];
String Status;
if(message.getFlags().toString().equals("com.sun.mail.imap.protocol.FLAGS@20")){
Status="R";
}else{
Status="D";
}
String contentType = message.getContentType();
System.out.println(message.getMessageNumber());
Address[] fromAddress = message.getFrom();
String tanggal = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").format(message.getSentDate());
String messageContent="";
//Jika contenType Bertipe Multipart Atau Dalam Artian Conten Tersebut Berisi File Dana Email
      if (contentType.contains("multipart")) {
                    Multipart multiPart = (Multipart) message.getContent();
                    int numberOfParts = multiPart.getCount();
                    for (int partCount = 0; partCount < numberOfParts; partCount++) {
                    part[partCount] = (MimeBodyPart) multiPart.getBodyPart(partCount);
                        if (Part.ATTACHMENT.equalsIgnoreCase(part[partCount].getDisposition())) {
                        } else {
                            messageContent = part[partCount].getContent().toString();
                           
                        }
                    }
                } else if (contentType.contains("text/plain")
                        || contentType.contains("text/html")) {
                    Object contents = message.getContent();
                    if (contents != null) {
                   
                        messageContent = contents.toString();
                    }
                }

String splitPesan[]=messageContent.split("</style>");
String hasilSplit="";
 if(splitPesan.length>1){

 hasilSplit=splitPesan[1];
  
 }else{
 hasilSplit=splitPesan[0];
 }     
      
String content =hasilSplit.replaceAll("\\<[^>]*>","");  
String replacnbsp=content.replaceAll("&nbsp;","");
inboxTableModel.addRow(new Object[]{Status,Boolean.FALSE,fromAddress[0].toString(), message.getSubject(),replacnbsp,tanggal});


}catch(Exception e){}

}else{
loadingInbox=false;
    }
        try{
            //Proses Monitoring Inbox Dengan Durasi 3 detik Sekali
    int freq = Integer.parseInt("100");
        System.out.println("Thread sleep selama 0,1 detik");
        Thread.sleep(freq);  // sleep for freq milliseconds
        System.out.println("Ceking Folder Inbox");
        System.out.println("message count in folder is Inbox"+inbox.getMessageCount());
        System.out.println();
        System.out.println();
} catch (Exception ex) {
    ex.printStackTrace();
}
}
}               };treetInbox.start();






Thread treetSent = new Thread() {
 public void run() {
sent.addMessageCountListener(new MessageCountAdapter(){
public void messagesAdded(MessageCountEvent ev) {
System.out.println("message is ok.");
Message[] msgs = ev.getMessages();
System.out.println("Get " + msgs.length + " new messages");
for (int i = 0; i < msgs.length; i++) {
try {
ready=false;     
Message message = msgs[i];
String contentType = message.getContentType();
Address[] fromAddress = message.getRecipients(Message.RecipientType.TO);
String tanggal = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").format(message.getSentDate());
String form1[] = new String[sentTableModel.getRowCount()];
String subject1[] = new String[sentTableModel.getRowCount()];
String conten1[] = new String[sentTableModel.getRowCount()];
String date1[]= new String[sentTableModel.getRowCount()];
String status1[]=new String[sentTableModel.getRowCount()];
//JOptionPane.showMessageDialog(null,"re", "as",JOptionPane.INFORMATION_MESSAGE);
for(int count=0;count<sentTableModel.getRowCount();count++){
    try{    
status1[count]=sentTable.getValueAt(count, 0).toString();
}catch(Exception e){
    status1[count]="";
}
try{    
form1[count]=sentTable.getValueAt(count, 2).toString();
}catch(Exception e){
    form1[count]="";
}
try{
subject1[count]=sentTable.getValueAt(count, 3).toString();
}catch(Exception e){
    subject1[count]="";
}
try{
conten1[count]=sentTable.getValueAt(count, 4).toString();
}catch(Exception e){
conten1[count]="";
}
try{
date1[count]=sentTable.getValueAt(count, 5).toString();
}catch(Exception e){
    date1[count]="";
}
}

String messageContent="";
      if (contentType.contains("multipart")) {
                    Multipart multiPart = (Multipart) message.getContent();
                    int numberOfParts = multiPart.getCount();
                    for (int partCount = 0; partCount < numberOfParts; partCount++) {
                    part[partCount] = (MimeBodyPart) multiPart.getBodyPart(partCount);
                        if (Part.ATTACHMENT.equalsIgnoreCase(part[partCount].getDisposition())) {
                         
                        } else {
         
                            messageContent = part[partCount].getContent().toString();
                           
                        }
                    }
                } else if (contentType.contains("text/plain")
                        || contentType.contains("text/html")) {
                    Object contents = message.getContent();
                    if (contents != null) {
                      //   JOptionPane.showMessageDialog(null, "adsa", "asd",JOptionPane.INFORMATION_MESSAGE);
                        messageContent = contents.toString();
                    }
                }
String splitPesan[]=messageContent.split("</style>");
String hasilSplit="";
 if(splitPesan.length>1){

 hasilSplit=splitPesan[1];
  
 }else{
 hasilSplit=splitPesan[0];
 }     
      
String content =hasilSplit.replaceAll("\\<[^>]*>","");  
String replacnbsp=content.replaceAll("&nbsp;","");

while(sentTableModel.getRowCount()>0){
    for(int a=0;a<sentTableModel.getRowCount();a++){
    sentTableModel.removeRow(a);
    }
}
sentTableModel.addRow(new Object[]{"D",Boolean.FALSE,fromAddress[0].toString(), message.getSubject(),replacnbsp,tanggal});
for(int add=0;add<form1.length;add++){    
sentTableModel.addRow(new Object[]{status1[add],Boolean.FALSE,form1[add], subject1[add],conten1[add],date1[add]});
}
ready=true; 
System.out.println("-----");
if(msgs[i].getSubject().toLowerCase().startsWith("effort sheet details")){
 System.out.println("Message "
                                + msgs[i].getMessageNumber() + ":");
                        msgs[i].writeTo(System.out);
                         
                    }
                } catch (IOException ioex) {
                    ioex.printStackTrace();
                } catch (MessagingException mex) {
                    mex.printStackTrace();
                }
            }
try{
 arrayMessagesSent = sent.getMessages();

}catch(Exception e){

}
monitoringReady=true;
        }
 
      
        public void messagesRemoved(MessageCountEvent arg0) {
            // TODO Auto-generated method stub    
                try{
                    sent.expunge();//expunge adalah proses cek email yang terhapus lalu menghapusnya pada variable sent
 arrayMessagesSent = sent.getMessages();
System.out.println(arrayMessagesSent.length);
 ready=true;
}catch(Exception e){

}
         
        }
    });
int indexSent=0;
while(sent.isOpen()){
     indexSent=(arrayMessagesSent.length-1)-sentTable.getRowCount();
    if(sentTable.getRowCount()<=100&&ready&&indexSent>=0){
       loadingSent=true;
    startSent=true;
    
try{  
Message message = arrayMessagesSent[indexSent];
String Status;
if(message.getFlags().toString().equals("com.sun.mail.imap.protocol.FLAGS@20")){
Status="R";
}else{
Status="D";
}
String contentType = message.getContentType();
System.out.println(message.getMessageNumber());
Address[] fromAddress = message.getRecipients(Message.RecipientType.TO);
String tanggal = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").format(message.getSentDate());
String messageContent="";
      if (contentType.contains("multipart")) {
                    Multipart multiPart = (Multipart) message.getContent();
                    int numberOfParts = multiPart.getCount();
                    for (int partCount = 0; partCount < numberOfParts; partCount++) {
                    part[partCount] = (MimeBodyPart) multiPart.getBodyPart(partCount);
                        if (Part.ATTACHMENT.equalsIgnoreCase(part[partCount].getDisposition())) {
                        } else {
                            messageContent = part[partCount].getContent().toString();
                           
                        }
                    }
                } else if (contentType.contains("text/plain")
                        || contentType.contains("text/html")) {
                    Object contents = message.getContent();
                    if (contents != null) {
 
                        messageContent = contents.toString();
                    }
                }

String splitPesan[]=messageContent.split("</style>");
String hasilSplit="";
 if(splitPesan.length>1){

 hasilSplit=splitPesan[1];
  
 }else{
 hasilSplit=splitPesan[0];
 }     
      
String content =hasilSplit.replaceAll("\\<[^>]*>","");  
String replacnbsp=content.replaceAll("&nbsp;","");
sentTableModel.addRow(new Object[]{Status,Boolean.FALSE,fromAddress[0].toString(), message.getSubject(),replacnbsp,tanggal});
}catch(Exception e){}

}else{
  loadingSent=false;
    }
       try{
    int freq = Integer.parseInt("1000");
        Thread.sleep(freq); 
        System.out.println("Ceking Folder Sent");
        System.out.println("message count in folder is Sent"+sent.getMessageCount());
        System.out.println();
        System.out.println();
        System.out.println("Thread sleep selama 1 detik");
} catch (NumberFormatException | InterruptedException | MessagingException ex) {
    ex.printStackTrace();
}  
    
}
}               };treetSent.start();
     
}  catch (NoSuchProviderException ex) {
     JOptionPane.showMessageDialog(null,"Kesalahan Konfigurasi Protocol \n"+ex, "Informasi",JOptionPane.INFORMATION_MESSAGE);
      ex.printStackTrace();
      } catch (MessagingException ex) {
         System.out.println("Could not connect to  the message store");
         JOptionPane.showMessageDialog(null,"Tidak Bisa Terkoneksi Dengan Email Store\n"+ex, "Informasi",JOptionPane.INFORMATION_MESSAGE);
        ex.printStackTrace();
      }  


    
}
public boolean readEmail(int select,String folder) throws IOException{
                   Message arrayMessage[]=null;
                    try{
                     switch(folder){
                         case "inbox":
                         arrayMessage=inbox.getMessages();break;
                             case "sent":
                         arrayMessage=sent.getMessages();break;
                      
                     
                                 
                    }   
                   
                       
                        try{
                            int pilih =(arrayMessage.length-1)- select;
                            this.folder =folder;
                    String fromRead ="",subjectRead="",sentDateRead="",htmlRead="<table>";
                    String fileName="",Attch="",head="";
                    String messageContent = "";
                    String from="",subject="",sentDate="",html=""; 
                    Message message= arrayMessage[pilih];
                    messagess =message;
                    message.setFlag(Flags.Flag.SEEN,true);
                    index =pilih;
                    Address[] fromAddress =null;
                    if(!folder.equals("sent")){
                    fromAddress = message.getFrom();
                    }else{
                            fromAddress = message.getRecipients(Message.RecipientType.TO);
                    }
                
                    from = fromAddress[0].toString();
                    subject = message.getSubject();
                    sentDate = message.getSentDate().toString();
                    html="<html><body><strong> "+from+" </strong><br>"+subject+"<br><i>"+sentDate+"</i>";
                    String contentType = message.getContentType();
                    String attachFiles = "";
                    if (contentType.contains("multipart")) {
                    Multipart multiPart = (Multipart) message.getContent();
                    int numberOfParts = multiPart.getCount();
                    for (int partCount = 0; partCount < numberOfParts; partCount++) {
                    part[partCount] = (MimeBodyPart) multiPart.getBodyPart(partCount);
                        if (Part.ATTACHMENT.equalsIgnoreCase(part[partCount].getDisposition())) {
                            // this part is attachment
                         // JOptionPane.showMessageDialog(null,"das", "adsa", JOptionPane.INFORMATION_MESSAGE);
                            fileName = part[partCount].getFileName();
                            attachFiles += fileName + ", ";
                            
                    
                            htmlRead+="<tr><td>"+fileName+"</td><td><form method='post' action='noAction.html'><input type='submit' name='"+fileName+"' value='Save'></input></form></td></tr>";
                           //part.saveFile("D:/Attachment"+ File.separator + fileName);
                        } else {
                            // this part may be the message content
                            messageContent = part[partCount].getContent().toString();
                           
                        }
                    }
                    
                    htmlRead+="<object classid=\"javax.swing.JLabel\" label=just do it></object>"+"</table><hr><table></table>";
                    html+=htmlRead;
                    if (!messageContent.equals("")){
                                String splitPesan[]=messageContent.split("</style>");
String hasilSplit="";
 if(splitPesan.length>1){

 hasilSplit=splitPesan[1];
  
 }else{
 hasilSplit=splitPesan[0];
 }     
      
String replacnbsp=hasilSplit.replaceAll("&nbsp;","");   
 html+=replacnbsp.replaceAll("<style>([^<]*)</style>", "");
             
                    messageContent="";
                    }
                    
                    if (attachFiles.length() > 1) {
                        attachFiles = attachFiles.substring(0, attachFiles.length() - 2);
                    }
                    
                    namaFile =attachFiles;
                    
                    
                    
                } else if (contentType.contains("text/plain")
                        || contentType.contains("text/html")) {
                    Object content = message.getContent();
                    if (content != null) {
                      //   JOptionPane.showMessageDialog(null, "adsa", "asd",JOptionPane.INFORMATION_MESSAGE);
                        messageContent = content.toString();
                    }
                }
                if (!messageContent.equals("")){
                 
                    }
               
    
               String splitPesan[]=messageContent.split("</style>");
String hasilSplit="";
 if(splitPesan.length>1){

 hasilSplit=splitPesan[1];
  
 }else{
 hasilSplit=splitPesan[0];
 }     
      
String replacnbsp=hasilSplit.replaceAll("&nbsp;","");   
 html+=replacnbsp.replaceAll("<style>([^<]*)</style>", "");
 System.out.println(html);
               Html=html;
                   viewbacaEmail = new bacaEmal(null,true); 
                    viewbacaEmail.setVisible(true);
                        }catch(MessagingException | IOException e){
                            System.out.println(e.getMessage());
                        }    
                 
               
                    } catch (NoSuchProviderException ex) {
                        System.out.println(ex.getMessage());
        // System.out.println("No provider for protocol: "
          //  + protocol);
        // ex.printStackTrace();
      } catch (MessagingException ex) {
           System.out.println(ex.getMessage());
         System.out.println("Could not connect to  the message store");
         //ex.printStackTrace();
      }          
                 
boolean valid = true;

return valid;
}


public boolean saveFile(final int select,final String file,final String folder) throws IOException{
    Thread treet = new Thread(){
        public void run(){
             try{
                 MimeBodyPart PArt[]= new MimeBodyPart[100];
                 Message arrayMessage[]=null;
                 switch(folder){
                     case "inbox":
                         arrayMessage=inbox.getMessages();break;
                     case "sent":
                         arrayMessage=sent.getMessages();break;    
                 }
                 Message message=arrayMessage[select];
                 String contentType = message.getContentType();
                 String attachFiles = "";
                 if (contentType.contains("multipart")) {
                     Multipart multiPart = (Multipart) message.getContent();
                     int numberOfParts = multiPart.getCount();
                     for (int partCount = 0; partCount < numberOfParts; partCount++) {
                         PArt[partCount] = (MimeBodyPart) multiPart.getBodyPart(partCount);
                         if (Part.ATTACHMENT.equalsIgnoreCase(PArt[partCount].getDisposition())) {
                             // this part is attachment
                             //    JOptionPane.showMessageDialog(null, "as", "as", JOptionPane.INFORMATION_MESSAGE);
                             // JOptionPane.showMessageDialog(null,"das", "adsa", JOptionPane.INFORMATION_MESSAGE);
                             
                             if( file.equals(PArt[partCount].getFileName())){
                                 jfc =new JFileChooser();
                                 final double size =PArt[partCount].getSize()/1024.0;
                                 jfc.setDialogTitle("Save File");
                                 jfc.setSelectedFile(new File(file));
                                 int userSelection = jfc.showSaveDialog(parentFrame);
                                 if (userSelection == JFileChooser.APPROVE_OPTION) {
                                     
                                     final File fileToSave = jfc.getSelectedFile();
     
                                     System.out.println("Saving as file: " + fileToSave.getAbsolutePath());
//                                     lihatEmail.statusLabel.setText("Saving File");
//                                     lihatEmail.statusProgressBar.setStringPainted(true);
                                     Thread treetSave = new Thread(){
                                         public void run(){
                                             for(;;){
                                                 String titik="..";
                                                 for(int i=0;i<3;i++){
                                                     try{
                                                     Thread.sleep(100);
//                                                      lihatEmail.statusLabel.setText("Save File"+titik);
                                                      titik+=titik;
                                                     }catch(Exception e){
                                                     }
                                                 }
                                                  DecimalFormat sdf = new DecimalFormat("#,###,###,###.##");
                                                  double curentSize =fileToSave.length()/1024.0;
                                                  double totalPersen=(curentSize/size)*100;
//                                                  lihatEmail.statusProgressBar.setString(sdf.format(curentSize)+"KB/"+sdf.format(size)+"KB "+Integer.toString((int)totalPersen)+"%");
//                                                  lihatEmail.statusProgressBar.setValue((int)totalPersen);
                                             }
                                         }
                                     };treetSave.start();
                                     PArt[partCount].saveFile( fileToSave.getAbsolutePath());
                                     treetSave.stop();
//                                      lihatEmail.statusLabel.setText("");
            File file1= jfc.getSelectedFile();
                                     byte[] byteFile = new byte[(int)file1.length()];
                                       try{
            FileInputStream in = new FileInputStream(file1);
            in.read(byteFile);
            in.close();        
                                           
        }catch(Exception exc){}
        String fileString = new String(byteFile);

        if(fileString.contains("jeda")){
         pesandialog.setPesan("File Terenkripsi\nApakah Anda Ingin Deskripsi File Ini ?");
         pesandialog.setJudul("Informasi");
         try{
         if(pesandialog.pesanYesOrNo()==JOptionPane.YES_OPTION){
         files=fileToSave.getAbsolutePath();
             viewDeskrip = new dekripFile(null,true);
             viewDeskrip.setVisible(true);
             files="";
         }}catch(Exception e){}
        }
                                 }
                                 
                             }
                             
                             
                             
                             
                             
                             //part.saveFile("D:/Attachment"+ File.separator + fileName);
                         }             
                     }
                     
                     
                     
                     
                     
                 }
                 
             }catch(Exception e){
             }
//viewEmail.statusLabel.setText("");
// lihatEmail.statusProgressBar.setValue(0);
//        lihatEmail.statusProgressBar.setStringPainted(false);             
}       };treet.start();
boolean valid = true;

return valid;
} 
DefaultTableModel tableModelFolder;
public boolean hapus(final javax.swing.JTable tableFolder, String folder) throws InterruptedException{
boolean valid = true;
tableModelFolder =(DefaultTableModel)tableFolder.getModel();
int banyak=0,panjang=0;
switch(folder){
    case "inbox":
            
            
            try{
                Message[] arrayMesages;
                arrayMesages = inbox.getMessages();
                for(int i=0;i<tableFolder.getRowCount();i++){
                    sleep=5000;
                    if(tableFolder.getValueAt(i, 1).toString().equals("true")){
                        int pilih =(inbox.getMessages().length-1)-i;
                        inbox.getMessages()[pilih].setFlag(Flags.Flag.DELETED, true);
                        banyak++;
                        arrayMesages[pilih]=null;
                    }
                }
                panjang= banyak;
                while(banyak>0){
                    for(int i=0;i<tableModelFolder.getRowCount();i++){
                        if(tableFolder.getValueAt(i, 1).toString().equals("true")){
                            tableModelFolder.removeRow(i);ready=false;
                        }
                    }
                    banyak--;
                }
inbox.expunge();
ready=true;
arrayMessagesInbox= inbox.getMessages();
                
                
                
            }catch(Exception e){
                valid = false;
            }
        
        break;
    case "sent":  
            try{
              
          
                for(int i=0;i<tableFolder.getRowCount();i++){
                    sleep=5000;
                    if(tableFolder.getValueAt(i, 1).toString().equals("true")){
                        int pilih =(sent.getMessages().length-1)-i;
                        sent.getMessages()[pilih].setFlag(Flags.Flag.DELETED, true);
                        banyak++;
                       
                    }
                }
                panjang= banyak;
                while(banyak>0){
                    for(int i=0;i<tableModelFolder.getRowCount();i++){
                        if(tableFolder.getValueAt(i, 1).toString().equals("true")){
                            tableModelFolder.removeRow(i);ready=false;
                        }
                    }
                    banyak--;
                }
                sent.expunge();
                ready =true;
                arrayMessagesSent =sent.getMessages();
                
                
                
            }catch(Exception e){
                valid = false;
            }
        
        break;
    
        
}             


return valid;
}


private static String files="";
public  String getFiles() {
        return files;
    }
    
private static String Html="";
public String getHtml() {
        return Html;
    }

private static int index=0;
public int getIndex(){
return index;
}

public static String folder="";
public String getFolder(){
return folder;
}
private static Message messagess;
public Message getMsg(){
return messagess;
}
private static String namaFile="";
public String file(){
return namaFile;
}
public String setfile(){
return namaFile="";
}

public void tampilkanDataTable(javax.swing.JTable table, DefaultTableModel tableModel,String folder){ 
  
    table.setDefaultRenderer(Object.class, new RenderWarnaWarni(3));
    //sentTable.setDefaultRenderer(Object.class, new RenderWarnaWarni(3));
    if(folder.equals("inbox")){
        if(soc.bacaDataInbox(this.getUser())){
            tableModel.setRowCount(0);
            if(soc.listInbox != null && soc.listInbox.length>0){
                for(int i=0; i<soc.listInbox.length; i++){
                    tableModel.addRow(soc.listInbox[i]);
                }
            }
        }
    }else if(folder.equals("sent")){
        if(soc.bacaDataSent(this.getUser())){
            tableModel.setRowCount(0);
            if(soc.listSent != null && soc.listSent.length>0){
                for(int i=0; i<soc.listSent.length; i++){
                    tableModel.addRow(soc.listSent[i]);
                }
            }
        }
    }
}  

public boolean readEmailOffline(int row, String folder, javax.swing.JTable table ){    
    viewbacaEmail = new bacaEmal(null,true);
    String subject="",content="";
    if(table.getValueAt(row, 3).toString()!= null){
        subject = table.getValueAt(row, 3).toString();
    }
    if(table.getValueAt(row, 4).toString()!= null){
        content = table.getValueAt(row, 4).toString();
    }
    String html="<html><body><strong> "+table.getValueAt(row, 2).toString()+" </strong><br>"+
            subject +"<br><i>"+table.getValueAt(row, 5).toString()+"</i>";
            
    dataOffline = html+"<br>"+content+"<br>";
    if(folder.equals("inbox")){
        viewbacaEmail.jButton1.setEnabled(true);
        System.out.println("folder="+folder);
    }else{
        viewbacaEmail.jButton1.setEnabled(false);  
        System.out.println("folder="+folder);             
    }
    viewbacaEmail.emailEditorPane.setText(dataOffline);
    viewbacaEmail.setVisible(true);
    return true;
}

public String getDataOffline(){
    return dataOffline;
}
DefaultTableModel tableModel;
public boolean hapusDataFolderOffline( javax.swing.JTable table, String folder){
    tableModel = (DefaultTableModel) table.getModel();
    if(table.getRowCount()>0){
         for(int j=0;j<table.getRowCount();j++){
            for(int i=0;i<table.getRowCount();i++){
                if(table.getValueAt(i, 1).toString().equals("true")){                
                    //System.out.println(i+" TRUE");
                    if(soc.hapus(folder, table.getValueAt(i, 5).toString())){
                        tableModel.removeRow(i);
                    }else{
                        System.out.println(soc.pesan);
                    }
                }else{
                   // System.out.println(i+" FALSE - "+table.getValueAt(i, 1).toString()+" = "+Boolean.TRUE);
                }
            }
         }
    }
    return true;
}

}
