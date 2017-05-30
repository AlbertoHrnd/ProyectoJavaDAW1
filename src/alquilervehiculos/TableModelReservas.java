/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alquilervehiculos;

import java.math.BigDecimal;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.Reserva;

/**
 *
 * @author daw120
 */
public class TableModelReservas extends AbstractTableModel {

    private String[] columnNames = {"Cliente", "Fecha Inicio", "Fecha Fin", "Entregado", "Devuelto", "Vehículo", "Precio total"};
    List<Reserva> listaReservas;

    public TableModelReservas(List<Reserva> listaReservas) {
        this.listaReservas = listaReservas;
    }

    public void setListaClientes(List<Reserva> listaReservas) {
        this.listaReservas = listaReservas;
    }

    @Override
    public int getRowCount() {
        return listaReservas.size();
    }

    @Override
    public int getColumnCount() {
        return 7;
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Reserva r = listaReservas.get(rowIndex);
        
        switch (columnIndex) {
            case (0):
                return r.getClienteId().getNombre() + " " + r.getClienteId().getApellidos();
            case (1):
                return Utils.formateaFecha(r.getFechaInicio());
            case (2):
                return Utils.formateaFecha(r.getFechaFin());
            case (3):
                return r.getEntregado();
            case (4):
                return r.getDevuelto();
            case (5):
                return r.getVehiculoId().getMarca() + " " + r.getVehiculoId().getModelo();
            case (6):
                return r.getPrecioTotal();
            default:
                return null;
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (listaReservas.isEmpty()) {
            return Object.class;
        }
        return getValueAt(0, columnIndex).getClass();
    }
}
