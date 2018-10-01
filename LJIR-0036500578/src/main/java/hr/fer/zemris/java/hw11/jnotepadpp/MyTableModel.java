package hr.fer.zemris.java.hw11.jnotepadpp;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

public class MyTableModel extends DefaultTableModel {

    public boolean isCellEditable(int row, int column){  
        return false;  
    }

    
}