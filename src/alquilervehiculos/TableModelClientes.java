/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alquilervehiculos;

import com.mysql.fabric.xmlrpc.Client;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.Cliente;

/**
 *
 * @author daw120
 */
public class TableModelClientes extends AbstractTableModel {

    private String[] columnNames = {"Nombre", "Apellidos", "DNI", "Teléfono", "Dirección"};
    List<Cliente> listaClientes;

    public TableModelClientes(List<Cliente> listaClientes) {
        this.listaClientes = listaClientes;
    }

    public void setListaClientes(List<Cliente> listaClientes) {
        this.listaClientes = listaClientes;
    }

    @Override
    public int getRowCount() {
        return listaClientes.size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Cliente c = listaClientes.get(rowIndex);
        switch (columnIndex) {
            case (0):
                return c.getNombre();
            case (1):
                return c.getApellidos();
            case (2):
                return c.getDni();
            case (3):
                return c.getTelefono();
            case (4):
                return c.getDireccion();
            default:
                return null;
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (listaClientes.isEmpty()) {
            return Object.class;
        }
        return getValueAt(0, columnIndex).getClass();
    }
}
