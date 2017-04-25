/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*
 *  copyright 2008 echo.khannedy@gmail.com
 */
package editSwing;
 
import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
 
/**
 *
 * @author echo
 */
public class RenderWarnaWarni extends DefaultTableCellRenderer {
 
    private static final long serialVersionUID = 47612794125L;
 
    // warna background untuk baris ganjil
    private Color ganjil;
 
    // warna background untuk baris genap
    private Color genap;
 
    /**
     * membuat RenderWarnaWarni baru dengan menantukan warna
     * backgroundnya
     * @param ganjil warna ganjil
     * @param genap warna genap
     */
    public RenderWarnaWarni(int rows) {
        this.rows = rows;
    }
    
    private int rows;
int[] a = {1,4,6};
   

   
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        // mendapatkan component superclass
        Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
 
   if (row % 2 == 1) {
            // ganjil
            component.setBackground(new Color(204,255,204));
        } else {
            // genap
            component.setBackground(Color.white);
        }
 
        // cek ganjil ato genap

 
        // mengembalikan komponen
        return component;
    }
}