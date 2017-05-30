/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alquilervehiculos;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTable;

/**
 *
 * @author Alberto
 */
public class TableMouseListener extends MouseAdapter{
    
    private JTable table;
     
    public TableMouseListener(JTable table) {
        this.table = table;
    }
        
    @Override
    public void mousePressed(MouseEvent event) {
        // Selecciona la fila en la que se pulsa el ratón
        // Se implementa para que funcione al botón derecho sobre una fila
        Point point = event.getPoint();
        int currentRow = table.rowAtPoint(point);
        table.setRowSelectionInterval(currentRow, currentRow);
    }
}
