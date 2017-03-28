/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.io.File;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import view.akun;
import view.lihatEmail;

/**
 *
 * @author JTEK
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
                  java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                 try
            {
                
                
             // com.jtattoo.plaf.fast.FastLookAndFeel
                
  
                UIManager.setLookAndFeel("com.jtattoo.plaf.fast.FastLookAndFeel");
        String direktori=System.getProperty("user.dir");
        File file = new File(direktori+"/src/seting/imapProperties.properties");
        if(file.exists()){
        new lihatEmail().setVisible(true);
        }else{
        new akun().setVisible(true);
        JOptionPane.showMessageDialog(null, "Alamat Email dan Password Harus Di Isi", "Informasi",JOptionPane.INFORMATION_MESSAGE);
        }
        
        
    }catch(Exception e){}}});
    
}
}
