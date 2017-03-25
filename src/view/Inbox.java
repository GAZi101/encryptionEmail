/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.SaveOfflineController;
import java.awt.Cursor;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import server.serverGmail;

/**
 *
 * @author Destar
 */
public class Inbox extends javax.swing.JFrame {
    private final serverGmail servergmail = new serverGmail();
    viewEnkripsiFile viewenkripsiFile; 
    viewDeskripsiFile viewdeskripsiFile;
    viewEnkripsiPesan viewenkripsiPesan;
    viewDeskripsiPesan viewdeskripsiPesan;
    viewCompose viewcompose;
    DefaultTableModel inboxTableModel;
    DefaultTableModel sentTableModel;
    SaveOfflineController soc = new SaveOfflineController();
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date date = null;

    /**
     * Creates new form Inbox
     */
    public Inbox() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane7 = new javax.swing.JTabbedPane();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        inboxTable = new javax.swing.JTable();
        hapusButton = new javax.swing.JButton();
        jCheckBox1 = new javax.swing.JCheckBox();
        hapusLabel = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane11 = new javax.swing.JScrollPane();
        sentTable = new javax.swing.JTable();
        jButton4 = new javax.swing.JButton();
        jCheckBox2 = new javax.swing.JCheckBox();
        hapusLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTabbedPane7.setBackground(new java.awt.Color(178, 255, 161));
        jTabbedPane7.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jTabbedPane7.setForeground(new java.awt.Color(255, 255, 255));
        jTabbedPane7.setFont(new java.awt.Font("Lucida Sans", 1, 14)); // NOI18N

        jPanel7.setBackground(new java.awt.Color(204, 204, 204));

        inboxTable.setBackground(new java.awt.Color(204, 255, 191));
        inboxTable.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        inboxTable.setForeground(new java.awt.Color(226, 29, 29));
        inboxTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "", "From", "Subject", "Messages", "Date"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        inboxTable.setSelectionBackground(new java.awt.Color(204, 204, 204));
        inboxTable.getTableHeader().setReorderingAllowed(false);
        inboxTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                inboxTableMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(inboxTable);

        hapusButton.setBackground(new java.awt.Color(51, 51, 51));
        hapusButton.setForeground(new java.awt.Color(255, 255, 255));
        hapusButton.setText("Hapus");
        hapusButton.setOpaque(false);
        hapusButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hapusButtonActionPerformed(evt);
            }
        });

        jCheckBox1.setText("Pilih Semua");
        jCheckBox1.setOpaque(false);
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jCheckBox1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(hapusButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(hapusLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane7))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(hapusLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(hapusButton)
                        .addComponent(jCheckBox1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane7.addTab("Inbox", jPanel7);

        jPanel8.setBackground(new java.awt.Color(204, 204, 204));

        sentTable.setBackground(new java.awt.Color(204, 204, 204));
        sentTable.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        sentTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "", "From", "Subject", "Messages", "Date"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        sentTable.getTableHeader().setReorderingAllowed(false);
        sentTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sentTableMouseClicked(evt);
            }
        });
        jScrollPane11.setViewportView(sentTable);

        jButton4.setBackground(new java.awt.Color(51, 51, 51));
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Hapus");
        jButton4.setOpaque(false);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jCheckBox2.setText("Pilih Semua");
        jCheckBox2.setOpaque(false);
        jCheckBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 889, Short.MAX_VALUE)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jCheckBox2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(hapusLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton4)
                        .addComponent(jCheckBox2))
                    .addComponent(hapusLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane7.addTab("Sent", jPanel8);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 938, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jTabbedPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 918, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 395, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jTabbedPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 373, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void inboxTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inboxTableMouseClicked
        // TODO add your handling code here:
        if(evt.getClickCount()==2){
            int i=inboxTable.getSelectedRow();
            if(servergmail.cekKoneksi()){
                try{
                    if(servergmail.readEmail(i,"inbox")){
                        inboxTable.setValueAt("R", i, 0);
                    }
                }catch(IOException e){
                    System.out.print(e);
                }
            }else{
                if(servergmail.readEmailOffline(i,"inbox",inboxTable)){
                    inboxTable.setValueAt("R", i, 0);
                }
            }
        }
    }//GEN-LAST:event_inboxTableMouseClicked
boolean loading;
    private void hapusButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hapusButtonActionPerformed
        // TODO add your handling code here:
        setCursor(new Cursor(Cursor.WAIT_CURSOR));
        if(servergmail.cekKoneksi()){
            loading=true;
            Thread treet3 = new Thread() {
                public void run() {
                    String titik=".";
                    do{
                        for(int i=0;i<5;i++){
                            hapusLabel.setText("Hapus Email."+titik);
                            try{
                                Thread.sleep(500);
                            }catch(InterruptedException  e){
                            }
                            titik+=titik;
                        }
                        titik=".";
                    }
                    while(loading);{
                        hapusLabel.setText("");
                    }

                }
            };treet3.start();
            Thread  treetHapus = new Thread(){
                public void run(){
                    try {
                        hapusButton.setEnabled(false);
                        if(servergmail.hapus(inboxTable, "inbox")){
                            JOptionPane.showMessageDialog(null,"Penghapusan Pesan Berhasil","Informasi",JOptionPane.INFORMATION_MESSAGE);
                            jCheckBox1.setSelected(false);
                            hapusButton.setEnabled(true);
                        }   } catch (InterruptedException ex) {
                            Logger.getLogger(viewEmail.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        jCheckBox1.setEnabled(true);
                        loading=false;
                    }};treetHapus.start();
                }else{
                    servergmail.hapusDataFolderOffline(inboxTable, "inbox");
                }
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_hapusButtonActionPerformed

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        // TODO add your handling code here:
        if(!loading){
            if(!jCheckBox1.isSelected()){
                for(int i=0;i<inboxTable.getRowCount();i++){
                    inboxTable.setValueAt(Boolean.FALSE, i, 1);
                    jCheckBox1.setSelected(false);
                }
            }else{
                for(int i=0;i<inboxTable.getRowCount();i++){
                    inboxTable.setValueAt(Boolean.TRUE, i, 1);
                    jCheckBox1.setSelected(true);
                }
            }}
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void sentTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sentTableMouseClicked
        // TODO add your handling code here
        if(!loading){
            if(evt.getClickCount()==2){
                int i=sentTable.getSelectedRow();
                if(servergmail.cekKoneksi()){
                    try {
                        if(servergmail.readEmail(i,"sent")){
                            sentTable.setValueAt("R", i, 0);
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(viewEmail.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }else{
                    if(servergmail.readEmailOffline(i,"sent",sentTable)){
                        sentTable.setValueAt("R", i, 0);
                    }
                }
            }
        }
    }//GEN-LAST:event_sentTableMouseClicked

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        setCursor(new Cursor(Cursor.WAIT_CURSOR));
        if(servergmail.cekKoneksi()){
            loading=true;
            Thread treet3 = new Thread() {
                public void run() {
                    String titik=".";
                    do{
                        for(int i=0;i<5;i++){
                            hapusLabel1.setText("Hapus Email."+titik);
                            try{
                                Thread.sleep(500);
                            }catch(InterruptedException  e){
                            }
                            titik+=titik;
                        }
                        titik=".";
                    }
                    while(loading);{
                        hapusLabel1.setText("");
                    }

                }
            };treet3.start();
            Thread treetHapus = new Thread(){
                public void run(){
                    jButton4.setEnabled(false);
                    try {
                        if(servergmail.hapus(sentTable, "sent")){
                            JOptionPane.showMessageDialog(null,"Penghapusan Pesan Berhasil","Informasi",JOptionPane.INFORMATION_MESSAGE);
                            jCheckBox2.setSelected(false);
                            jButton4.setEnabled(true);
                        }   } catch (InterruptedException ex) {
                            Logger.getLogger(viewEmail.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        jCheckBox2.setEnabled(true);
                        loading=false;
                    }};treetHapus.start();
                }else{
                    servergmail.hapusDataFolderOffline(sentTable, "sent");
                }
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jCheckBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox2ActionPerformed
        // TODO add your handling code here:
        if(!jCheckBox2.isSelected()){
            for(int i=0;i<sentTable.getRowCount();i++){
                sentTable.setValueAt(Boolean.FALSE, i, 1);
                jCheckBox2.setSelected(false);
            }
        }else{
            for(int i=0;i<sentTable.getRowCount();i++){
                sentTable.setValueAt(Boolean.TRUE, i, 1);
                jCheckBox2.setSelected(true);
            }
        }

    }//GEN-LAST:event_jCheckBox2ActionPerformed

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
            java.util.logging.Logger.getLogger(Inbox.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Inbox.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Inbox.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Inbox.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Inbox().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton hapusButton;
    private javax.swing.JLabel hapusLabel;
    private javax.swing.JLabel hapusLabel1;
    private javax.swing.JTable inboxTable;
    private javax.swing.JButton jButton4;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTabbedPane jTabbedPane7;
    private javax.swing.JTable sentTable;
    // End of variables declaration//GEN-END:variables
}
