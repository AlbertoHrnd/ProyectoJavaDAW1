/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alquilervehiculos;

import java.awt.Frame;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Query;
import model.Agencia;
import model.Cliente;
import model.Reserva;
import model.Vehiculo;

/**
 *
 * @author Alberto
 */
public class DiaCrearModificarReserva extends javax.swing.JDialog {

    VentanaPrincipal padre;
    private Reserva reserva;
    private Cliente cliente;
    private Vehiculo vehiculo;
    private Agencia agencia;

    /**
     * Creates new form diaCrearModificarReserva
     */
    public DiaCrearModificarReserva(Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        padre = (VentanaPrincipal) parent;
        reserva = new Reserva();

        for (Cliente c : padre.listaClientesVistaClientes) {
            cmbClientes.addItem(c);
        }

//        for (Vehiculo v : padre.listaVehiculosVistaVehiculos) {
//            cmbVehiculos.addItem(v);
//        }
    }

    /**
     * Creates new form diaCrearModificarReserva pasando un cliente seleccionado
     */
    public DiaCrearModificarReserva(Frame parent, boolean modal, Cliente cliente) {
        super(parent, modal);
        initComponents();

        padre = (VentanaPrincipal) parent;
        reserva = new Reserva();

        cmbClientes.addItem(cliente);

//        for (Vehiculo v : padre.listaVehiculosVistaVehiculos) {
//            cmbVehiculos.addItem(v);
//        }
    }

    /**
     * Creates new form diaCrearModificarReserva pasando un vehículo
     * seleccionado
     */
    public DiaCrearModificarReserva(Frame parent, boolean modal, Vehiculo vehiculo) {
        super(parent, modal);
        initComponents();

        padre = (VentanaPrincipal) parent;
        reserva = new Reserva();

        cmbVehiculos.addItem(vehiculo);

        for (Cliente c : padre.listaClientesVistaClientes) {
            cmbClientes.addItem(c);
        }
    }

    /**
     * Creates new form diaCrearModificarReserva pasando una reserva
     * seleccionada a modificar
     */
    public DiaCrearModificarReserva(Frame parent, boolean modal, Reserva reserva) {
        super(parent, modal);
        initComponents();

        padre = (VentanaPrincipal) parent;
        this.reserva = reserva;

        for (Cliente c : padre.listaClientesVistaClientes) {
            cmbClientes.addItem(c);
        }

        for (Vehiculo v : padre.listaVehiculosVistaVehiculos) {
            cmbVehiculos.addItem(v);
        }

        lblTitulo.setText("ACTUALIZAR RESERVA");

        cmbClientes.setSelectedItem(reserva.getClienteId());
        cmbVehiculos.setSelectedItem(reserva.getVehiculoId());

        dtpFechaInicio.setDate(reserva.getFechaInicio());
        dtpFechaFin.setDate(reserva.getFechaFin());

        txtLitrosGasolina.setText(String.valueOf(reserva.getLitrosGasolina()));
        txtPrecio.setText(String.valueOf(reserva.getPrecioDia()));

        chkDevuelto.setSelected(reserva.getDevuelto());
        chkEntregado.setSelected(reserva.getEntregado());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        lblCliente = new javax.swing.JLabel();
        lblFechaInicio = new javax.swing.JLabel();
        lblFechaFin = new javax.swing.JLabel();
        lblVehiculo = new javax.swing.JLabel();
        lblPrecio = new javax.swing.JLabel();
        txtPrecio = new javax.swing.JTextField();
        btnGuardar = new javax.swing.JButton();
        lblError = new javax.swing.JLabel();
        txtLitrosGasolina = new javax.swing.JTextField();
        lblLitroGasolina = new javax.swing.JLabel();
        chkEntregado = new javax.swing.JCheckBox();
        chkDevuelto = new javax.swing.JCheckBox();
        dtpFechaInicio = new org.jdesktop.swingx.JXDatePicker();
        dtpFechaFin = new org.jdesktop.swingx.JXDatePicker();
        cmbClientes = new javax.swing.JComboBox();
        cmbVehiculos = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        lblTitulo.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("NUEVA RESERVA");

        lblCliente.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCliente.setText("Cliente");

        lblFechaInicio.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblFechaInicio.setText("Fecha Inicio");

        lblFechaFin.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblFechaFin.setText("Fecha Fin");

        lblVehiculo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblVehiculo.setText("Vehículo");

        lblPrecio.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblPrecio.setText("Precio / día");

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        lblError.setForeground(new java.awt.Color(255, 0, 0));

        lblLitroGasolina.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblLitroGasolina.setText("Litros Gasolina");

        chkEntregado.setText("Entregado");

        chkDevuelto.setText("Devuelto");

        cmbVehiculos.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cmbVehiculosFocusGained(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblVehiculo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblFechaFin, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblFechaInicio, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblCliente, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblPrecio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblLitroGasolina, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbClientes, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(chkDevuelto)
                                    .addComponent(chkEntregado))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(cmbVehiculos, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(dtpFechaFin, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
                                    .addComponent(dtpFechaInicio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtPrecio, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtLitrosGasolina, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(139, 139, 139))
                            .addComponent(lblError, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitulo)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCliente)
                    .addComponent(cmbClientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFechaInicio)
                    .addComponent(dtpFechaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFechaFin)
                    .addComponent(dtpFechaFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblVehiculo)
                    .addComponent(cmbVehiculos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPrecio)
                    .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtLitrosGasolina, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblLitroGasolina))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chkEntregado)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chkDevuelto)
                .addGap(8, 8, 8)
                .addComponent(btnGuardar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblError, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(39, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed

        if (!txtLitrosGasolina.getText().trim().equals("")
                && !txtPrecio.getText().trim().equals("")) {

            // Comprobamos litros válidos
            try {
                int litros = Integer.parseInt(txtLitrosGasolina.getText());
                reserva.setLitrosGasolina(litros);
            } catch (Exception e) {
                lblError.setText("El número de litros no es válido.");
                return;
            }

            // Comprobamos precio válido
            try {
                double precioDia = Double.parseDouble(txtPrecio.getText());
                reserva.setPrecioDia(BigDecimal.valueOf(precioDia));
            } catch (Exception e) {
                lblError.setText("El precio por día no es válido.");
                return;
            }

            cliente = (Cliente) cmbClientes.getSelectedItem();
            vehiculo = (Vehiculo) cmbVehiculos.getSelectedItem();

            // Comprobamos selección vehículo
            if (vehiculo == null) {
                lblError.setText("Debes seleccionar un vehículo.");
                return;
            }

            reserva.setClienteId(cliente);
            reserva.setVehiculoId(vehiculo);

            // Cargamos la agencia 1 por defecto
            agencia = padre.em.find(Agencia.class, 1);
            reserva.setAgenciaId(agencia);

            reserva.setDevuelto(chkDevuelto.isSelected());
            reserva.setEntregado(chkEntregado.isSelected());

            reserva.setFechaInicio(dtpFechaInicio.getDate());
            reserva.setFechaFin(dtpFechaFin.getDate());

            System.out.println(reserva.toString());

            padre.em.getTransaction().begin();
            padre.em.persist(reserva);
            padre.em.getTransaction().commit();

            // Refrescamos el cliente y el vehículo involucrados
            // para que muestre bien las listas
            padre.em.refresh(cliente);
            padre.em.refresh(vehiculo);

            padre.cargarDatos();
            padre.repaint();

            this.dispose();
        } else {
            lblError.setText("Rellene todos los campos");
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void cmbVehiculosFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cmbVehiculosFocusGained
        Date ini = dtpFechaInicio.getDate();
        Date fin = dtpFechaFin.getDate();

        if (ini != null && fin != null) {
            if (fin.after(ini)) {
                compruebaDisponibilidad(dtpFechaInicio.getDate(), dtpFechaFin.getDate());
            } else {
                lblError.setText("<html>La fecha de inicio debe ser anterior a<br>la fecha de fin.</html>");
            }
        } else {
            lblError.setText("<html>Debes seleccionar un rango de fechas<br>para comprobar la disponibilidad.</html>");
        }
    }//GEN-LAST:event_cmbVehiculosFocusGained

    private void compruebaDisponibilidad(Date fechaInicio, Date fechaFin) {

        Query q = padre.em.createNativeQuery("SELECT * from vehiculo where id NOT IN "
                + "(SELECT vehiculo_id from reserva "
                + "where (fecha_inicio between :inicio and :fin "
                + "OR fecha_fin between :inicio and :fin) "
                + "OR (fecha_inicio < :inicio AND fecha_fin > :fin))", Vehiculo.class);

        q.setParameter("inicio", fechaInicio);
        q.setParameter("fin", fechaFin);

        List<Vehiculo> listaVehiculos = q.getResultList();

        cmbVehiculos.removeAllItems();

        if (listaVehiculos == null) {
            cmbVehiculos.setEnabled(false);
        } else {
            for (Vehiculo v : listaVehiculos) {
                cmbVehiculos.addItem(v);
            }
            cmbVehiculos.setEnabled(true);
            cmbVehiculos.showPopup();
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGuardar;
    private javax.swing.JCheckBox chkDevuelto;
    private javax.swing.JCheckBox chkEntregado;
    private javax.swing.JComboBox cmbClientes;
    private javax.swing.JComboBox cmbVehiculos;
    private org.jdesktop.swingx.JXDatePicker dtpFechaFin;
    private org.jdesktop.swingx.JXDatePicker dtpFechaInicio;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblCliente;
    private javax.swing.JLabel lblError;
    private javax.swing.JLabel lblFechaFin;
    private javax.swing.JLabel lblFechaInicio;
    private javax.swing.JLabel lblLitroGasolina;
    private javax.swing.JLabel lblPrecio;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblVehiculo;
    private javax.swing.JTextField txtLitrosGasolina;
    private javax.swing.JTextField txtPrecio;
    // End of variables declaration//GEN-END:variables
}
