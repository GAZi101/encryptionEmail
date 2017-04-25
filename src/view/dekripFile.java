/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.File;
import javax.swing.JFileChooser;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JOptionPane;
import enkripsi.Enkrip;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import server.serverGmail;
import algo.RC6;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author rockiericker
 */
public class dekripFile extends javax.swing.JDialog {
private final RC6 rc6 = new RC6();
private final Enkrip enkripsi = new Enkrip();
private JFileChooser jfc;
private static File file;
private final serverGmail server = new serverGmail();
    /**
     * Creates new form viewEnkripsiPesan
     */
    public dekripFile(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        aturFrame(0.85,true);
        fileLabel.setText(server.getFiles());
        if(!fileLabel.getText().equals("No File")){
        file=new File(fileLabel.getText());
        }
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

        jPanel1 = new javax.swing.JPanel();
        passwordField = new javax.swing.JPasswordField();
        jLabel2 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        fileLabel = new javax.swing.JLabel();
        deskripButton = new javax.swing.JButton();
        okButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Deskripsi");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setText("Password :");

        jButton4.setText("Choose File");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        fileLabel.setBackground(new java.awt.Color(204, 204, 204));
        fileLabel.setText("No File");
        fileLabel.setOpaque(true);

        deskripButton.setText("Deskripsi");
        deskripButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deskripButtonActionPerformed(evt);
            }
        });

        okButton.setText("Ok");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(fileLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(deskripButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(okButton))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(29, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(fileLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(deskripButton)
                    .addComponent(okButton))
                .addContainerGap(40, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
            String current = "";
		JFileChooser jfc = null; 
		LookAndFeel previousLF = UIManager.getLookAndFeel();
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			jfc = new JFileChooser(current);
			jfc.setDialogTitle("Pilih File");
			UIManager.setLookAndFeel(previousLF);
		}catch (IllegalAccessException | UnsupportedLookAndFeelException | InstantiationException | ClassNotFoundException e) {}
		FileFilter docxFilter, docFilter, xlsFilter, xlsxFilter, txtFilter,pdfFilter, allFilter;
		docFilter = new FileNameExtensionFilter("Word 2007/2010", "docx");
		docxFilter = new FileNameExtensionFilter("Word 2003", "doc");
		xlsFilter = new FileNameExtensionFilter("Excel 2003", "xls");
		xlsxFilter = new FileNameExtensionFilter("Excel 2007/2010", "xlsx");
		txtFilter = new FileNameExtensionFilter("Text Document File","txt");
		pdfFilter = new FileNameExtensionFilter("PDF Document File", "pdf");
		allFilter = new FileNameExtensionFilter("All File", "doc", "docx", "xls", "xlsx", "txt");
		jfc.setAcceptAllFileFilterUsed(false);
		jfc.addChoosableFileFilter(docxFilter);
                jfc.addChoosableFileFilter(docFilter);
		jfc.addChoosableFileFilter(xlsFilter);
		jfc.addChoosableFileFilter(xlsxFilter);
		jfc.addChoosableFileFilter(txtFilter);
		jfc.addChoosableFileFilter(pdfFilter);
		jfc.addChoosableFileFilter(allFilter);
		if (jfc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			String f = jfc.getSelectedFile().toString();
			fileLabel.setText(f);
		}
		else {
			jfc.hide();
		}
    }//GEN-LAST:event_jButton4ActionPerformed
public void setFile(String namaFile){
fileLabel.setText(namaFile);
}
    private void deskripButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deskripButtonActionPerformed
        // TODO add your handling code here:
      try {
            
			if (fileLabel.getText().equals("")){
				JOptionPane.showMessageDialog(this,"File Belum Di Pilih");
				fileLabel.requestFocus();
			}
			else if (passwordField.getText().equals("")){
				JOptionPane.showMessageDialog(this,"Password Belum Di Masukan");
				passwordField.requestFocus();
			}
//                        else if(!miripLabel.getText().equals("Valid")){
//                        JOptionPane.showMessageDialog(null,"Pasword Tidak Valid","Informasi",JOptionPane.INFORMATION_MESSAGE);
                        
//        }
                        else {
				//
				long start = System.currentTimeMillis();
				File file = new File(fileLabel.getText());
				FileInputStream fis = new FileInputStream(file);
				byte[] b = new byte[(int) file.length()];
				fis.read(b);
			        
                                //byte[]passByte= STB2(password);
				//Encryption rc4 = new Encryption(jTextField1.getText().getBytes());
				
                                byte[] de = rc6.decrypt(b, passwordField.getText().getBytes());
				
				String f = file.getName().substring(3, file.getName().length());
				
				File fout = new File("de_" + file.getName());
				
				long now = System.currentTimeMillis();
				
				JFileChooser fileChooser = null;
				LookAndFeel previousLF = UIManager.getLookAndFeel();
				
				try{
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					fileChooser = new JFileChooser();
					fileChooser.setSelectedFile(fout);
					fileChooser.setDialogTitle("Save File Location");
					UIManager.setLookAndFeel(previousLF);
					
					if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
			    		boolean doExport = true;
			    		boolean overrideExistingFile = false;

			    		String dir = fileChooser.getCurrentDirectory().toString();
			    		File destinationFile = new File(fileChooser.getSelectedFile().getName());
			    		DataOutputStream out = new DataOutputStream(new FileOutputStream(dir + "/" + destinationFile));
			    
			 			ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
						byteOut.write(de);
						byteOut.writeTo(out);
						out.close();
			    

					if (doExport) {
			    	}
				
					JOptionPane.showMessageDialog(this, "File " + file.getName() + " Berhasil Di Enkrip\nWaktu Enkrip : " + (now-start)/1000.0 + " Detik");
			            
					fileLabel.setText("");
					passwordField.setText("");
				
					}
				
				}
				catch(IllegalAccessException | UnsupportedLookAndFeelException | InstantiationException | ClassNotFoundException e){}	 
			}
			fileLabel.setText("");
			passwordField.setText("");
				} 
		catch(IOException ioe) {
			JOptionPane.showMessageDialog(this,"File Yang Dipilih Bukan File Doc, Docx, Xls atau Xlsx");
			fileLabel.setText("");
                	passwordField.setText("");
				
                }
    }//GEN-LAST:event_deskripButtonActionPerformed

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        // TODO add your handling code here:
dispose();
    }//GEN-LAST:event_okButtonActionPerformed

    private String path="";

    public String getPath() {
        return path;
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
            java.util.logging.Logger.getLogger(dekripFile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(dekripFile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(dekripFile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(dekripFile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                dekripFile dialog = new dekripFile(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton deskripButton;
    private javax.swing.JLabel fileLabel;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton okButton;
    private javax.swing.JPasswordField passwordField;
    // End of variables declaration//GEN-END:variables
}