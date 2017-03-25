/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algo;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rockiericker
 */
public class RC4 {
    
    private char[] key;
    private int[] sbox;
    private static final int SBOX_LENGTH = 256;
    public void keyString(String key){
        this.key = key.toCharArray();
    }

 
    public char[] encrypt(final char[] msg,javax.swing.JProgressBar persenProgessBar) {
        int persen=0;
        
        
            sbox = initSBox(key ,persenProgessBar);
    
        char[] code = new char[msg.length];
        int i = 0;
        int j = 0;
        for (int n = 0; n < msg.length; n++) {
            i = (i + 1) % SBOX_LENGTH;
            persen++;
            j = (j + sbox[i]) % SBOX_LENGTH;
            swap(i, j, sbox);
            int rand = sbox[(sbox[i] + sbox[j]) % SBOX_LENGTH];
            code[n] = (char) (rand ^ (int) msg[n]);
             if(persenProgessBar!=null){
            double totalPersen= ((double)persen/(double)msg.length)*15.0;
            persenProgessBar.setString(Double.toString((int)totalPersen+85.0)+"%");
            persenProgessBar.setValue(((int)totalPersen)+85);}
        
        }
        return code;
    }
    
     public char[] decrypt(final char[] msg,javax.swing.JProgressBar persenProgessBar) {
        int persen=0;
        sbox = initSBox(key ,persenProgessBar);
        char[] code = new char[msg.length];
        int i = 0;
        int j = 0;
        for (int n = 0; n < msg.length; n++) {
            i = (i + 1) % SBOX_LENGTH;
            persen++;
            j = (j + sbox[i]) % SBOX_LENGTH;
            swap(i, j, sbox);
            int rand = sbox[(sbox[i] + sbox[j]) % SBOX_LENGTH];
            code[n] = (char) (rand ^ (int) msg[n]);
            
            double totalPersen= ((double)persen/(double)msg.length)*5.0;
            if(persenProgessBar!=null){
            persenProgessBar.setString(Double.toString((int)totalPersen+25.0)+"%");
            persenProgessBar.setValue(((int)totalPersen)+20);}
        
        }
        return code;
    }
 
    private int[] initSBox(char[] key,javax.swing.JProgressBar persenProgessBar)  {
        int[] sbox = new int[SBOX_LENGTH];
        int j = 0;
        int persen =0;
        for (int i = 0; i < SBOX_LENGTH; i++) {
            sbox[i] = i;
            persen++;
            double totalPersen= ((double)persen/(double)SBOX_LENGTH)*2.0;
            if(persenProgessBar!=null){
            persenProgessBar.setString(Double.toString((int)totalPersen+20.0)+"%");
            persenProgessBar.setValue(((int)totalPersen)+80);}
           
        }
        persen=0;
        for (int i = 0; i < SBOX_LENGTH; i++) {
            
            j = (j + sbox[i] + key[i % key.length]) % SBOX_LENGTH;
            swap(i, j, sbox);
            persen++;
            double totalPersen= ((double)persen/(double)SBOX_LENGTH)*3.0;
            if(persenProgessBar!=null){
            persenProgessBar.setString(Double.toString((int)totalPersen+22.0)+"%");
            persenProgessBar.setValue(((int)totalPersen)+22);}
           
        }
        return sbox;
    }
 
    private void swap(int i, int j, int[] sbox) {
        int temp = sbox[i];
        sbox[i] = sbox[j];
        sbox[j] = temp;
    }
 

 
}


