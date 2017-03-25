/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import javax.swing.JOptionPane;

/**
 *
 * @author ikhsanfadly
 */
public class pesanDialog {
    private String pesan;
    private String judul;
    int input =0;
    public void setPesan(String pesan) {
        this.pesan = pesan;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }
    
    public String pesanInput(){    
        try{
       String input= JOptionPane.showInputDialog(null, pesan, judul,
        JOptionPane.QUESTION_MESSAGE);
       return input;
        }catch(Exception e){
            return "cancel"; 
        }
       
   }
    
    
    public int pesanYesOrNo(){
  int appr=JOptionPane.showOptionDialog(null, pesan, judul, JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE, null, null, null);
        return appr;
    }
    
    public void pesan(){
    if(judul.equals("Informasi")){
    JOptionPane.showMessageDialog(null, pesan,judul,JOptionPane.INFORMATION_MESSAGE);
    
    }else{
     JOptionPane.showMessageDialog(null, pesan,judul,JOptionPane.ERROR_MESSAGE);
    }
    
    }
    
    }
    
    
    
    

