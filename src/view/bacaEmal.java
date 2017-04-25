/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;
import javax.swing.JOptionPane;
import server.serverGmail;
import controller.controllerReadEmail;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.Element;
import javax.swing.text.StyleConstants;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;
import javax.swing.text.html.FormSubmitEvent;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.ObjectView;
import controller.controllerTeruskan;
import enkripsi.Enkrip;
/**
 *
 * @author Resa S.A
 */
public final class bacaEmal extends javax.swing.JDialog {
private final Enkrip enkripsi = new Enkrip();    
private final controllerTeruskan teruskan = new controllerTeruskan();    
private final serverGmail servergmail = new serverGmail();
private final controllerReadEmail controllerreadEmail = new controllerReadEmail();
private static int pilih;
public String folder;

    /**
     * Creates new form viewBacaEmail
     */
    public bacaEmal(java.awt.Frame parent, boolean modal) {
        
        
        super(parent, modal);
        initComponents();
        tampungContent="";
        tampungHtml="";
        if(servergmail.cekKoneksi()){
            emailEditorPane.setEditorKit(new bacaEmal.CompEditorKit()); // install our hook
            HTMLEditorKit kit = (HTMLEditorKit)emailEditorPane.getEditorKit();
                        kit.setAutoFormSubmission(false);
                        emailEditorPane.addHyperlinkListener(new HyperlinkListener()
                        {                           
                            @Override
                            public void hyperlinkUpdate(HyperlinkEvent e)
                            {
                                if (e instanceof FormSubmitEvent)
                                {
                                    System.out.println(((FormSubmitEvent)e).getData().toString());
                                                         String splitPesan[]=((FormSubmitEvent)e).getData().toString().split("=");
                                     // if(host.equals("imap.gmail.com")){
                                          String replace=splitPesan[0].replace("+"," ");               
                                          try{
                                          int indexSelect= pilih;   
                                          servergmail.saveFile(indexSelect,replace,folder);}catch(Exception a){
                                          }
                                //      }
                                }
                            }
                        });
                         emailEditorPane.setText(servergmail.getHtml());
                         tampungHtml=servergmail.getHtml();
            pilih=servergmail.getIndex();
            folder = servergmail.getFolder();
            if(folder.equals("inbox")){
                jButton1.setEnabled(true);
                System.out.println("folder="+folder);
            }else{
                jButton1.setEnabled(false);
                System.out.println("folder="+folder);
            }
        }else{
           
        }
       aturFrame(0.85,true);
    }

           private void aturFrame(double skala, boolean tengah){
        Dimension dimensi = Toolkit.getDefaultToolkit().getScreenSize();
 
    
    if (tengah){
    setLocation ((int) ((dimensi.getWidth()-getWidth())/2),
    (int)((dimensi.getHeight()-getHeight())/2));
    }
}



    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel1 = new editSwing.Panel();
        jButton1 = new javax.swing.JButton();
        passwordField = new javax.swing.JPasswordField();
        jLabel4 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        emailEditorPane = new javax.swing.JEditorPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Baca Email");

        panel1.setMaximumSize(new java.awt.Dimension(898, 516));
        panel1.setMinimumSize(new java.awt.Dimension(898, 516));

        jButton1.setText("Balas Email");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel4.setText("Password :");

        jButton5.setText("Deskripsi");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        emailEditorPane.setEditable(false);
        emailEditorPane.setContentType("text/html"); // NOI18N
        jScrollPane1.setViewportView(emailEditorPane);

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 656, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panel1Layout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton5)
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 384, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton5)
                .addContainerGap(63, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, 679, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        if(servergmail.cekKoneksi()){
            String splitPesan[]=servergmail.getHtml().split("<strong>");
            String splitPesan1[]=splitPesan[1].split("</strong>");
            System.out.println(splitPesan1[0]);
            teruskan.balasEmail(splitPesan1[0]);
        }else{
            JOptionPane.showMessageDialog(null, "Tidak dapat membalas email saat mode offline");
        }
    }//GEN-LAST:event_jButton1ActionPerformed
private static String tampungContent,tampungHtml; 
    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        if(jButton5.getText().equals("Deskripsi")){
            tampungContent=emailEditorPane.getText();
            if(!passwordField.getText().equals("")){
                if(passwordField.getText().length()>=8){
                    String splitPesan[]=emailEditorPane.getText().split("</table>");
                    String content="";
                    System.out.println( splitPesan.length);
                    if(splitPesan.length>0){
                        for(int i=1;i<splitPesan.length;i++){
                            int a=i+1;
                            if(a<splitPesan.length){
                                content+=splitPesan[a];
                            }
                        }
                        String Text =content.replaceAll("\\<[^>]*>","");
                        String conten=Text.replace(" ","");
                        //String tex =conten.replace("\n", "").replace("\r", "");
                        System.out.println("pesan "+conten);
                        String password=passwordField.getText();
                        String cont=enkripsi.deskripText2(conten, password);

                        if(!cont.equals("")){
                            jButton5.setText("Kembali");
                            emailEditorPane.setText(cont);
                        }else{ 
                            JOptionPane.showMessageDialog(null,enkripsi.getPesanKesalahan(),"Informasi",JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                }else{
                    JOptionPane.showMessageDialog(null,"Password Harus 8 Karakter Atau Lebih","Informasi",JOptionPane.INFORMATION_MESSAGE);
                }
            }else{
                JOptionPane.showMessageDialog(null,"Password Harus Di Isi","Informasi",JOptionPane.INFORMATION_MESSAGE);
            }
        }else{
            emailEditorPane.setText(tampungContent);
            tampungContent="";
            jButton5.setText("Deskripsi");
        }
    }//GEN-LAST:event_jButton5ActionPerformed

protected class CompEditorKit extends HTMLEditorKit {
    @Override
    public ViewFactory getViewFactory() {
        return new CompFactory();
    }       
}   
protected class CompFactory extends HTMLEditorKit.HTMLFactory {
    public CompFactory() {
        super();
    }
    @Override
    public View create(Element element) {
        AttributeSet attrs = element.getAttributes();
    Object elementName = attrs.getAttribute(AbstractDocument.ElementNameAttribute);
    Object o = (elementName != null) ? null : attrs.getAttribute(StyleConstants.NameAttribute);
    if (o instanceof HTML.Tag) {       
            if ((HTML.Tag) o == HTML.Tag.OBJECT) {
             //   JOptionPane.showMessageDialog(null, "asd", "asd", JOptionPane.INFORMATION_MESSAGE);
                return new CompView(element); 
                
            }
        }
        return super.create(element);
    }
}   
protected class CompView extends ObjectView {
    public CompView(Element element) {
        super(element);
    }
    @Override
    protected Component createComponent() {
        Component component = super.createComponent();  // COMPONENT IS CREATED HERE
    System.out.println(component);
   // JOptionPane.showMessageDialog(null, "asd", "asd", JOptionPane.INFORMATION_MESSAGE);
  //  JLabel layeredPane = new JLabel();
    //layeredPane.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("images/broadcast.png"))));
    return component;   
    }
}
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(bacaEmal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(bacaEmal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(bacaEmal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(bacaEmal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                bacaEmal dialog = new bacaEmal(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JEditorPane emailEditorPane;
    public javax.swing.JButton jButton1;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private editSwing.Panel panel1;
    private javax.swing.JPasswordField passwordField;
    // End of variables declaration//GEN-END:variables

}
