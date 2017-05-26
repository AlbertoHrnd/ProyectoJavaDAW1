/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alquilervehiculos;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.swing.DefaultListModel;
import model.Cliente;
import model.Reserva;
import model.Vehiculo;

/**
 *
 * @author daw120
 */
public class VentanaPrincipal extends javax.swing.JFrame {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("AlquilerVehiculosPU");
    EntityManager em = emf.createEntityManager();

    ArrayList<Cliente> listaClientesVistaClientes;
    ArrayList<Vehiculo> listaVehiculosVistaVehiculos;
    ArrayList<Reserva> listaReservasVistaReserva;

    private DefaultListModel<Reserva> listaReservasVistaClientes;
    private DefaultListModel<Reserva> listaReservasVistaVehiculos;

    /**
     * Creates new form VentanaPrincipal
     */
    public VentanaPrincipal() {
        initComponents();

        pnlVehiculos.setVisible(false);
        pnlReservas.setVisible(false);

        listaClientesVistaClientes = new ArrayList<>();
        listaReservasVistaClientes = new DefaultListModel<>();
        listaReservasVistaReserva = new ArrayList<>();
        listaReservasVistaVehiculos = new DefaultListModel<>();
        listaVehiculosVistaVehiculos = new ArrayList<>();

        tblClientes.setAutoCreateRowSorter(true);
        tblClientes.setComponentPopupMenu(pMenuTablaCLientes);

        tblReservas.setAutoCreateRowSorter(true);

        // Listener para seleccionar la fila de la tabla cuando botón derecho
        tblClientes.addMouseListener(new TableMouseListener(tblClientes));
        tblReservas.addMouseListener(new TableMouseListener(tblReservas));

        cargarDatos();
    }

    public void cargarDatos() {
        cargarClientes();
        cargarVehiculos();
        cargarReservas();
    }

    public void cargarClientes() {
        listaClientesVistaClientes.clear();

        TypedQuery<Cliente> queryClientes = em.createNamedQuery("Cliente.findAll", Cliente.class);
        List<Cliente> clientes = queryClientes.getResultList();

        // Ordenamos los clientes por apellidos
        clientes.sort(new Comparator<Cliente>() {
            @Override
            public int compare(Cliente o1, Cliente o2) {
                return o1.getApellidos().compareTo(o2.getApellidos());
            }
        });
        
        for (Cliente c : clientes) {
            listaClientesVistaClientes.add(c);
        }
        
        tblClientes.setModel(new TableModelClientes(listaClientesVistaClientes));

        listaReservasVistaClientes.clear();
    }

    public void cargarReservas() {
        listaReservasVistaReserva.clear();

        TypedQuery<Reserva> queryReservas = em.createNamedQuery("Reserva.findAll", Reserva.class);
        List<Reserva> reservas = queryReservas.getResultList();

        // Ordenamos las reservas por fecha de inicio descendiente
        reservas.sort(new Comparator<Reserva>() {
            @Override
            public int compare(Reserva o1, Reserva o2) {
                if (o1.getFechaInicio().before(o2.getFechaInicio())) {
                    return 1;
                }
                return -1;
            }
        });

        for (Reserva r : reservas) {
            listaReservasVistaReserva.add(r);
        }
        
        tblReservas.setModel(new TableModelReservas(listaReservasVistaReserva));
    }

    public void cargarVehiculos() {
        listaVehiculosVistaVehiculos.clear();

        TypedQuery<Vehiculo> queryVehiculos = em.createNamedQuery("Vehiculo.findAll", Vehiculo.class);
        List<Vehiculo> vehiculos = queryVehiculos.getResultList();

        for (Vehiculo v : vehiculos) {
            listaVehiculosVistaVehiculos.add(v);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        AlquilerVehiculosPUEntityManager = java.beans.Beans.isDesignTime() ? null : javax.persistence.Persistence.createEntityManagerFactory("AlquilerVehiculosPU").createEntityManager();
        clienteQuery = java.beans.Beans.isDesignTime() ? null : AlquilerVehiculosPUEntityManager.createQuery("SELECT c FROM Cliente c");
        clienteList = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : clienteQuery.getResultList();
        pMenuTablaCLientes = new javax.swing.JPopupMenu();
        pMenuItemNuevaReserva = new javax.swing.JMenuItem();
        pnlContenedor = new javax.swing.JPanel();
        pnlClientes = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        pnlPrincipalClientes = new javax.swing.JPanel();
        pnlCabeceraClientes = new javax.swing.JPanel();
        btnNuevoCliente = new javax.swing.JButton();
        txtBuscarClienteNombre = new javax.swing.JTextField();
        txtBuscarClienteApellidos = new javax.swing.JTextField();
        btnBuscarCliente = new javax.swing.JButton();
        lblBuscarClienteNombre = new javax.swing.JLabel();
        lblBuscarClienteApellidos = new javax.swing.JLabel();
        btnModificarCliente = new javax.swing.JButton();
        btnEliminarCliente = new javax.swing.JButton();
        pnlCuerpoClientes = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblClientes = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        lstReservasVistaClientes = new javax.swing.JList();
        lblTituloListaReservas = new javax.swing.JLabel();
        pnlVehiculos = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        pnlReservas = new javax.swing.JPanel();
        lblTituloPnlReservas = new javax.swing.JLabel();
        pnlPrincipaReservas = new javax.swing.JPanel();
        pnlCabeceraReservas = new javax.swing.JPanel();
        btnNuevaReserva = new javax.swing.JButton();
        btnModificarReserva = new javax.swing.JButton();
        btnEliminarReserva = new javax.swing.JButton();
        pnlCuerpoClientes1 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblReservas = new javax.swing.JTable();
        jMenuBar = new javax.swing.JMenuBar();
        menuArchivo = new javax.swing.JMenu();
        menuVer = new javax.swing.JMenu();
        menuItemVerClientes = new javax.swing.JMenuItem();
        menuItemVerVehiculos = new javax.swing.JMenuItem();
        menuItemVerReservas = new javax.swing.JMenuItem();

        pMenuItemNuevaReserva.setText("Nueva Reserva");
        pMenuItemNuevaReserva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pMenuItemNuevaReservaActionPerformed(evt);
            }
        });
        pMenuTablaCLientes.add(pMenuItemNuevaReserva);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("CLIENTES");

        btnNuevoCliente.setText("Nuevo Cliente");
        btnNuevoCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoClienteActionPerformed(evt);
            }
        });

        btnBuscarCliente.setText("Buscar Cliente");
        btnBuscarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarClienteActionPerformed(evt);
            }
        });

        lblBuscarClienteNombre.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblBuscarClienteNombre.setText("Nombre");

        lblBuscarClienteApellidos.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblBuscarClienteApellidos.setText("Apellidos");

        btnModificarCliente.setText("Modificar Cliente");
        btnModificarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarClienteActionPerformed(evt);
            }
        });

        btnEliminarCliente.setBackground(new java.awt.Color(240, 0, 0));
        btnEliminarCliente.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminarCliente.setText("Eliminar Cliente");
        btnEliminarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarClienteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlCabeceraClientesLayout = new javax.swing.GroupLayout(pnlCabeceraClientes);
        pnlCabeceraClientes.setLayout(pnlCabeceraClientesLayout);
        pnlCabeceraClientesLayout.setHorizontalGroup(
            pnlCabeceraClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCabeceraClientesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlCabeceraClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlCabeceraClientesLayout.createSequentialGroup()
                        .addGroup(pnlCabeceraClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnNuevoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnModificarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pnlCabeceraClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblBuscarClienteNombre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblBuscarClienteApellidos, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)))
                    .addGroup(pnlCabeceraClientesLayout.createSequentialGroup()
                        .addComponent(btnEliminarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlCabeceraClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtBuscarClienteNombre)
                    .addComponent(txtBuscarClienteApellidos)
                    .addComponent(btnBuscarCliente, javax.swing.GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlCabeceraClientesLayout.setVerticalGroup(
            pnlCabeceraClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCabeceraClientesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlCabeceraClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscarClienteNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblBuscarClienteNombre)
                    .addComponent(btnNuevoCliente))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlCabeceraClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscarClienteApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblBuscarClienteApellidos)
                    .addComponent(btnModificarCliente))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addGroup(pnlCabeceraClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBuscarCliente)
                    .addComponent(btnEliminarCliente))
                .addContainerGap())
        );

        tblClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblClientesMousePressed(evt);
            }
        });
        jScrollPane3.setViewportView(tblClientes);

        jScrollPane2.setViewportView(lstReservasVistaClientes);

        lblTituloListaReservas.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTituloListaReservas.setText("RESERVAS");

        javax.swing.GroupLayout pnlCuerpoClientesLayout = new javax.swing.GroupLayout(pnlCuerpoClientes);
        pnlCuerpoClientes.setLayout(pnlCuerpoClientesLayout);
        pnlCuerpoClientesLayout.setHorizontalGroup(
            pnlCuerpoClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCuerpoClientesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 560, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlCuerpoClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTituloListaReservas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 487, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlCuerpoClientesLayout.setVerticalGroup(
            pnlCuerpoClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCuerpoClientesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlCuerpoClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 419, Short.MAX_VALUE)
                    .addGroup(pnlCuerpoClientesLayout.createSequentialGroup()
                        .addComponent(lblTituloListaReservas)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2)))
                .addContainerGap())
        );

        javax.swing.GroupLayout pnlPrincipalClientesLayout = new javax.swing.GroupLayout(pnlPrincipalClientes);
        pnlPrincipalClientes.setLayout(pnlPrincipalClientesLayout);
        pnlPrincipalClientesLayout.setHorizontalGroup(
            pnlPrincipalClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlCabeceraClientes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlCuerpoClientes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlPrincipalClientesLayout.setVerticalGroup(
            pnlPrincipalClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPrincipalClientesLayout.createSequentialGroup()
                .addComponent(pnlCabeceraClientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlCuerpoClientes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlClientesLayout = new javax.swing.GroupLayout(pnlClientes);
        pnlClientes.setLayout(pnlClientesLayout);
        pnlClientesLayout.setHorizontalGroup(
            pnlClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlClientesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pnlPrincipalClientes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlClientesLayout.setVerticalGroup(
            pnlClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlClientesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlPrincipalClientes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("VEHÍCULOS");

        javax.swing.GroupLayout pnlVehiculosLayout = new javax.swing.GroupLayout(pnlVehiculos);
        pnlVehiculos.setLayout(pnlVehiculosLayout);
        pnlVehiculosLayout.setHorizontalGroup(
            pnlVehiculosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlVehiculosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 852, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlVehiculosLayout.setVerticalGroup(
            pnlVehiculosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlVehiculosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addContainerGap(597, Short.MAX_VALUE))
        );

        lblTituloPnlReservas.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTituloPnlReservas.setText("RESERVAS");

        btnNuevaReserva.setText("Nueva Reserva");
        btnNuevaReserva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevaReservaActionPerformed(evt);
            }
        });

        btnModificarReserva.setText("Modificar Reserva");
        btnModificarReserva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarReservaActionPerformed(evt);
            }
        });

        btnEliminarReserva.setBackground(new java.awt.Color(240, 0, 0));
        btnEliminarReserva.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminarReserva.setText("Eliminar Reserva");
        btnEliminarReserva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarReservaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlCabeceraReservasLayout = new javax.swing.GroupLayout(pnlCabeceraReservas);
        pnlCabeceraReservas.setLayout(pnlCabeceraReservasLayout);
        pnlCabeceraReservasLayout.setHorizontalGroup(
            pnlCabeceraReservasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCabeceraReservasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlCabeceraReservasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnNuevaReserva, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnModificarReserva, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminarReserva, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(873, Short.MAX_VALUE))
        );
        pnlCabeceraReservasLayout.setVerticalGroup(
            pnlCabeceraReservasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCabeceraReservasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnNuevaReserva)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnModificarReserva)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addComponent(btnEliminarReserva)
                .addContainerGap())
        );

        tblReservas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblReservasMousePressed(evt);
            }
        });
        jScrollPane4.setViewportView(tblReservas);

        javax.swing.GroupLayout pnlCuerpoClientes1Layout = new javax.swing.GroupLayout(pnlCuerpoClientes1);
        pnlCuerpoClientes1.setLayout(pnlCuerpoClientes1Layout);
        pnlCuerpoClientes1Layout.setHorizontalGroup(
            pnlCuerpoClientes1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCuerpoClientes1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 1057, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlCuerpoClientes1Layout.setVerticalGroup(
            pnlCuerpoClientes1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCuerpoClientes1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 415, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout pnlPrincipaReservasLayout = new javax.swing.GroupLayout(pnlPrincipaReservas);
        pnlPrincipaReservas.setLayout(pnlPrincipaReservasLayout);
        pnlPrincipaReservasLayout.setHorizontalGroup(
            pnlPrincipaReservasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlCabeceraReservas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlCuerpoClientes1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlPrincipaReservasLayout.setVerticalGroup(
            pnlPrincipaReservasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPrincipaReservasLayout.createSequentialGroup()
                .addComponent(pnlCabeceraReservas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlCuerpoClientes1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlReservasLayout = new javax.swing.GroupLayout(pnlReservas);
        pnlReservas.setLayout(pnlReservasLayout);
        pnlReservasLayout.setHorizontalGroup(
            pnlReservasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlReservasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTituloPnlReservas, javax.swing.GroupLayout.DEFAULT_SIZE, 1077, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(pnlReservasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnlReservasLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(pnlPrincipaReservas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        pnlReservasLayout.setVerticalGroup(
            pnlReservasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlReservasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTituloPnlReservas)
                .addContainerGap(597, Short.MAX_VALUE))
            .addGroup(pnlReservasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnlReservasLayout.createSequentialGroup()
                    .addGap(40, 40, 40)
                    .addComponent(pnlPrincipaReservas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        javax.swing.GroupLayout pnlContenedorLayout = new javax.swing.GroupLayout(pnlContenedor);
        pnlContenedor.setLayout(pnlContenedorLayout);
        pnlContenedorLayout.setHorizontalGroup(
            pnlContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlClientes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(pnlVehiculos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(pnlContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(pnlReservas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlContenedorLayout.setVerticalGroup(
            pnlContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlClientes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(pnlVehiculos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(pnlContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(pnlReservas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        menuArchivo.setText("Archivo");
        jMenuBar.add(menuArchivo);

        menuVer.setText("Ver");

        menuItemVerClientes.setText("Clientes");
        menuItemVerClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemVerClientesActionPerformed(evt);
            }
        });
        menuVer.add(menuItemVerClientes);

        menuItemVerVehiculos.setText("Vehículos");
        menuItemVerVehiculos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemVerVehiculosActionPerformed(evt);
            }
        });
        menuVer.add(menuItemVerVehiculos);

        menuItemVerReservas.setText("Reservas");
        menuItemVerReservas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemVerReservasActionPerformed(evt);
            }
        });
        menuVer.add(menuItemVerReservas);

        jMenuBar.add(menuVer);

        setJMenuBar(jMenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlContenedor, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlContenedor, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menuItemVerClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemVerClientesActionPerformed
        pnlVehiculos.setVisible(false);
        pnlReservas.setVisible(false);
        pnlClientes.setVisible(true);
    }//GEN-LAST:event_menuItemVerClientesActionPerformed

    private void menuItemVerVehiculosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemVerVehiculosActionPerformed
        pnlReservas.setVisible(false);
        pnlClientes.setVisible(false);
        pnlVehiculos.setVisible(true);
    }//GEN-LAST:event_menuItemVerVehiculosActionPerformed

    private void menuItemVerReservasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemVerReservasActionPerformed
        pnlVehiculos.setVisible(false);
        pnlClientes.setVisible(false);
        pnlReservas.setVisible(true);
    }//GEN-LAST:event_menuItemVerReservasActionPerformed

    private void btnBuscarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarClienteActionPerformed

        List<Cliente> results = new ArrayList<>();
        listaClientesVistaClientes.clear();

        TypedQuery<Cliente> query = null;

        query = em.createNamedQuery("Cliente.findByNombreOrApellidos", Cliente.class);

        query.setParameter("nombre", "%" + txtBuscarClienteNombre.getText() + "%");
        query.setParameter("apellidos", "%" + txtBuscarClienteApellidos.getText() + "%");

        results = query.getResultList();

        for (Cliente c : results) {
            listaClientesVistaClientes.add(c);
        }

        tblClientes.setModel(new TableModelClientes(listaClientesVistaClientes));

        listaReservasVistaClientes.clear();
    }//GEN-LAST:event_btnBuscarClienteActionPerformed

    private void btnNuevoClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoClienteActionPerformed
        DiaCrearModificarCliente diaCrearCliente = new DiaCrearModificarCliente(this, true);

        diaCrearCliente.setLocationRelativeTo(this);
        diaCrearCliente.setVisible(true);
    }//GEN-LAST:event_btnNuevoClienteActionPerformed

    private void btnModificarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarClienteActionPerformed
        if (tblClientes.getSelectedRow() != -1) {
            int i = tblClientes.convertRowIndexToModel(tblClientes.getSelectedRow());
            Cliente c = listaClientesVistaClientes.get(i);

            DiaCrearModificarCliente diaCrearCliente = new DiaCrearModificarCliente(this, true, c);
            diaCrearCliente.setLocationRelativeTo(this);
            diaCrearCliente.setVisible(true);
        }
    }//GEN-LAST:event_btnModificarClienteActionPerformed

    private void btnEliminarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEliminarClienteActionPerformed

    private void pMenuItemNuevaReservaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pMenuItemNuevaReservaActionPerformed
        if (tblClientes.getSelectedRow() != -1) {
            int i = tblClientes.convertRowIndexToModel(tblClientes.getSelectedRow());
            Cliente c = listaClientesVistaClientes.get(i);

            DiaCrearModificarReserva diaCrearReserva = new DiaCrearModificarReserva(this, true, c);
//            DiaCrearModificarReserva diaCrearReserva = new DiaCrearModificarReserva(this, true);
            diaCrearReserva.setLocationRelativeTo(this);
            diaCrearReserva.setVisible(true);
        }
    }//GEN-LAST:event_pMenuItemNuevaReservaActionPerformed

    private void tblClientesMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblClientesMousePressed
        if (tblClientes.getSelectedRow() != -1) {
            int i = tblClientes.convertRowIndexToModel(tblClientes.getSelectedRow());
            Cliente c = listaClientesVistaClientes.get(i);

            listaReservasVistaClientes.clear();
            List<Reserva> listaReservasCLiente = c.getReservaList();

            for (Reserva r : listaReservasCLiente) {
                listaReservasVistaClientes.addElement(r);
            }

            lstReservasVistaClientes.setModel(listaReservasVistaClientes);
        }
    }//GEN-LAST:event_tblClientesMousePressed

    private void btnNuevaReservaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevaReservaActionPerformed
        DiaCrearModificarReserva diaCrearReserva = new DiaCrearModificarReserva(this, true);
        diaCrearReserva.setLocationRelativeTo(this);
        diaCrearReserva.setVisible(true);
    }//GEN-LAST:event_btnNuevaReservaActionPerformed

    private void btnModificarReservaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarReservaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnModificarReservaActionPerformed

    private void btnEliminarReservaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarReservaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEliminarReservaActionPerformed

    private void tblReservasMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblReservasMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tblReservasMousePressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.persistence.EntityManager AlquilerVehiculosPUEntityManager;
    private javax.swing.JButton btnBuscarCliente;
    private javax.swing.JButton btnEliminarCliente;
    private javax.swing.JButton btnEliminarReserva;
    private javax.swing.JButton btnModificarCliente;
    private javax.swing.JButton btnModificarReserva;
    private javax.swing.JButton btnNuevaReserva;
    private javax.swing.JButton btnNuevoCliente;
    private java.util.List<model.Cliente> clienteList;
    private javax.persistence.Query clienteQuery;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenuBar jMenuBar;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lblBuscarClienteApellidos;
    private javax.swing.JLabel lblBuscarClienteNombre;
    private javax.swing.JLabel lblTituloListaReservas;
    private javax.swing.JLabel lblTituloPnlReservas;
    private javax.swing.JList lstReservasVistaClientes;
    private javax.swing.JMenu menuArchivo;
    private javax.swing.JMenuItem menuItemVerClientes;
    private javax.swing.JMenuItem menuItemVerReservas;
    private javax.swing.JMenuItem menuItemVerVehiculos;
    private javax.swing.JMenu menuVer;
    private javax.swing.JMenuItem pMenuItemNuevaReserva;
    private javax.swing.JPopupMenu pMenuTablaCLientes;
    private javax.swing.JPanel pnlCabeceraClientes;
    private javax.swing.JPanel pnlCabeceraReservas;
    private javax.swing.JPanel pnlClientes;
    private javax.swing.JPanel pnlContenedor;
    private javax.swing.JPanel pnlCuerpoClientes;
    private javax.swing.JPanel pnlCuerpoClientes1;
    private javax.swing.JPanel pnlPrincipaReservas;
    private javax.swing.JPanel pnlPrincipalClientes;
    private javax.swing.JPanel pnlReservas;
    private javax.swing.JPanel pnlVehiculos;
    private javax.swing.JTable tblClientes;
    private javax.swing.JTable tblReservas;
    private javax.swing.JTextField txtBuscarClienteApellidos;
    private javax.swing.JTextField txtBuscarClienteNombre;
    // End of variables declaration//GEN-END:variables
}
