/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;
import javax.mail.Message;
import javax.swing.JEditorPane;
import view.viewBalasEmail;

/**
 *
 * @author Resa S.A
 */
public class controllerTeruskan {
  
viewBalasEmail viewbalasEmail;
private final JEditorPane editorPane = new JEditorPane();

    public JEditorPane getEditorPane() {
        return editorPane;
    }

private static String content,from,file;
private static Message msg;
public Message msg(){
return msg;
}

    public  String getFrom() {
        return from;
    }
   public  String getFile() {
        return file;
    }
    public  void setFrom(String from) {
        controllerTeruskan.from = from;
    }

    public String getContent() {
    
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }





public void balasEmail(String from){
this.from =from; 
viewbalasEmail = new viewBalasEmail(null,true);
viewbalasEmail.setVisible(true);
}
}
