/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alquilervehiculos;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.Vehiculo;

/**
 *
 * @author Alberto
 */
public class TableModelVehiculos extends AbstractTableModel {

    private String[] columnNames = {"Marca", "Modelo", "Matrícula", "Color", "Garaje"};
    List<Vehiculo> listaVehiculos;

    public TableModelVehiculos(List<Vehiculo> listaVehiculos) {
        this.listaVehiculos = listaVehiculos;
    }

    public void setListaVehiculos(List<Vehiculo> listaVehiculos) {
        this.listaVehiculos = listaVehiculos;
    }

    @Override
    public int getRowCount() {
        return listaVehiculos.size();
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
        Vehiculo v = listaVehiculos.get(rowIndex);
        switch (columnIndex) {
            case (0):
                return v.getMarca();
            case (1):
                return v.getModelo();
            case (2):
                return v.getMatricula();
            case (3):
                return v.getColor();
            case (4):
                return v.getGarajeId();
            default:
                return null;
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (listaVehiculos.isEmpty()) {
            return Object.class;
        }
        return getValueAt(0, columnIndex).getClass();
    }
}
