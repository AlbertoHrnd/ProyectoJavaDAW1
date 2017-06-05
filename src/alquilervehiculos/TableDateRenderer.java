/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alquilervehiculos;

import java.text.DateFormat;
import java.util.Date;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Alberto
 */
class TableDateRenderer extends DefaultTableCellRenderer {

    public TableDateRenderer() {
        super();
    }

    @Override
    public void setValue(Object value) {
        setText(Utils.formateaFecha((Date) value));
    }
}
