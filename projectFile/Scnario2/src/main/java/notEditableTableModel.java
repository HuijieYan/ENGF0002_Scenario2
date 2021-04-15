
import javax.swing.table.DefaultTableModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author yadongliu
 */
public class notEditableTableModel extends DefaultTableModel {
    
    @Override
    public boolean isCellEditable(int row, int column){  
       return false;  
    }   
}
