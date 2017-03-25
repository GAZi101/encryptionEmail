/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editSwing;


import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
/**
 *
 * @author ikhsanfadly
 */
public class panelGmail extends JPanel{
  
       private Image image;  
  
    public panelGmail() {  
image = new ImageIcon(getClass().getResource("/image/gmail-logo.png")).getImage();  
    }  
  
    @Override  
    protected void paintComponent(Graphics grphcs) {  
        super.paintComponent(grphcs);  
  
        Graphics2D gd = (Graphics2D) grphcs.create();  
        gd.drawImage(image, 0, 0, getWidth(), getHeight(), null);  
        gd.dispose();  
    }    
}
