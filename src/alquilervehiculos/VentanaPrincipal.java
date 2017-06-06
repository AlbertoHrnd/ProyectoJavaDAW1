/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alquilervehiculos;

import almacenamiento.ExportarClientes;
import almacenamiento.ExportarReservas;
import almacenamiento.ExportarVehiculos;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import model.Agencia;
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

        CargarIcono();

        pnlVehiculos.setVisible(false);
        pnlClientes.setVisible(false);

        inicializarListas();

        inicializarTablas();

        cargarDatos();
    }

    private void CargarIcono() {
        URL iconURL = getClass().getResource("/img/icono.png");
        Image im = Toolkit.getDefaultToolkit().getImage(iconURL);
        this.setIconImage(im);
    }

    private void inicializarListas() {
        listaClientesVistaClientes = new ArrayList<>();
        listaReservasVistaClientes = new DefaultListModel<>();

        listaReservasVistaReserva = new ArrayList<>();

        listaVehiculosVistaVehiculos = new ArrayList<>();
        listaReservasVistaVehiculos = new DefaultListModel<>();

        // Listeners para seleccionar la fila de la lista cuando botón derecho
        lstReservasVistaClientes.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent event) {
                // Selecciona la fila en la que se pulsa el ratón
                // Se implementa para que marque el elemento seleccionado al
                // pulsar botón derecho sobre una fila
                int row = lstReservasVistaClientes.locationToIndex(event.getPoint());
                lstReservasVistaClientes.setSelectedIndex(row);
            }
        });

        lstReservasVistaVehiculos.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent event) {
                // Selecciona la fila en la que se pulsa el ratón
                // Se implementa para que marque el elemento seleccionado al
                // pulsar botón derecho sobre una fila
                int row = lstReservasVistaVehiculos.locationToIndex(event.getPoint());
                lstReservasVistaVehiculos.setSelectedIndex(row);
            }
        });
    }

    private void inicializarTablas() {
        tblClientes.setAutoCreateRowSorter(true);
        tblClientes.setComponentPopupMenu(pMenuTabla);

        tblReservas.setAutoCreateRowSorter(true);

        tblVehiculos.setAutoCreateRowSorter(true);
        tblVehiculos.setComponentPopupMenu(pMenuTabla);

        // Listeners para seleccionar la fila de la tabla cuando botón derecho
        tblClientes.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent event) {
                // Selecciona la fila en la que se pulsa el ratón
                // Se implementa para que marque el elemento seleccionado al
                // pulsar botón derecho sobre una fila
                Point point = event.getPoint();
                int currentRow = tblClientes.rowAtPoint(point);
                tblClientes.setRowSelectionInterval(currentRow, currentRow);
                cargarListaReservasDeClienteSeleccionado();
            }
        });

        tblVehiculos.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent event) {
                // Selecciona la fila en la que se pulsa el ratón
                // Se implementa para que marque el elemento seleccionado al
                // pulsar botón derecho sobre una fila
                Point point = event.getPoint();
                int currentRow = tblVehiculos.rowAtPoint(point);
                tblVehiculos.setRowSelectionInterval(currentRow, currentRow);
                cargarListaReservasDeVehiculoSeleccionado();
            }
        });
    }

    public void cargarDatos() {
        // Si no hay Agencia (Acabamos de crear las tablas)
        // Creamos la agencia por defecto
        if (em.find(Agencia.class, 1) == null) {
            em.getTransaction().begin();
            Agencia a = new Agencia("Agencia Principal", "C/ Correos, 1 ");
            em.persist(a);
            em.getTransaction().commit();
        }
        cargarClientes();
        cargarVehiculos();
        cargarReservas();
    }

    public void cargarClientes() {
        listaClientesVistaClientes.clear();

        TypedQuery<Cliente> queryClientes = em.createNamedQuery("Cliente.findAll", Cliente.class);
        List<Cliente> clientes = queryClientes.getResultList();

        cargarListaTablaClientes(clientes);

        tblClientes.setModel(new TableModelClientes(listaClientesVistaClientes));

        listaReservasVistaClientes.clear();
    }

    public void cargarReservas() {
        listaReservasVistaReserva.clear();

        TypedQuery<Reserva> queryReservas = em.createNamedQuery("Reserva.findAll", Reserva.class);
        List<Reserva> reservas = queryReservas.getResultList();

        cargarListaTablaReservas(reservas);

        tblReservas.setModel(new TableModelReservas(listaReservasVistaReserva));

        // Añadimos los CellRenderers para que nos muestre la fecha en el formato deseado
        tblReservas.setDefaultRenderer(Date.class, new TableDateRenderer());
    }

    public void cargarVehiculos() {
        listaVehiculosVistaVehiculos.clear();

        TypedQuery<Vehiculo> queryVehiculos = em.createNamedQuery("Vehiculo.findAll", Vehiculo.class);
        List<Vehiculo> vehiculos = queryVehiculos.getResultList();

        cargarListaTablaVehiculos(vehiculos);

        tblVehiculos.setModel(new TableModelVehiculos(listaVehiculosVistaVehiculos));
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
        pMenuTabla = new javax.swing.JPopupMenu();
        pMenuItemNuevaReserva = new javax.swing.JMenuItem();
        pMenuLista = new javax.swing.JPopupMenu();
        pMenuItemModificarReserva = new javax.swing.JMenuItem();
        pnlContenedor = new javax.swing.JPanel();
        pnlClientes = new javax.swing.JPanel();
        lblTituloPanelClientes = new javax.swing.JLabel();
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
        lblTituloListaReservasVistaClientes = new javax.swing.JLabel();
        btnGuardarClientesXml = new javax.swing.JButton();
        btnGuardarClientesExcel = new javax.swing.JButton();
        pnlVehiculos = new javax.swing.JPanel();
        lblTituloPanelVehiculos = new javax.swing.JLabel();
        pnlPrincipalVehiculos = new javax.swing.JPanel();
        pnlCabeceraVehiculos = new javax.swing.JPanel();
        btnNuevoVehiculo = new javax.swing.JButton();
        txtBuscarVehiculoMarca = new javax.swing.JTextField();
        txtBuscarVehiculoModelo = new javax.swing.JTextField();
        btnBuscarVehiculo = new javax.swing.JButton();
        lblBuscarVehiculoMarca = new javax.swing.JLabel();
        lblBuscarVehiculoModelo = new javax.swing.JLabel();
        btnModificarVehiculo = new javax.swing.JButton();
        btnEliminarVehiculo = new javax.swing.JButton();
        pnlCuerpoVehiculos = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tblVehiculos = new javax.swing.JTable();
        jScrollPane8 = new javax.swing.JScrollPane();
        lstReservasVistaVehiculos = new javax.swing.JList();
        lblTituloListaReservasVistaVehiculos = new javax.swing.JLabel();
        btnGuardarVehiculosXml = new javax.swing.JButton();
        btnGuardarVehiculosExcel = new javax.swing.JButton();
        pnlReservas = new javax.swing.JPanel();
        lblTituloPnlReservas = new javax.swing.JLabel();
        pnlPrincipaReservas = new javax.swing.JPanel();
        pnlCabeceraReservas = new javax.swing.JPanel();
        btnNuevaReserva = new javax.swing.JButton();
        btnModificarReserva = new javax.swing.JButton();
        btnEliminarReserva = new javax.swing.JButton();
        tbtnVerPendientesDevolucion = new javax.swing.JToggleButton();
        tbtnVerPendientesEntrega = new javax.swing.JToggleButton();
        dtpBuscarReservaDeFecha = new org.jdesktop.swingx.JXDatePicker();
        dtpBuscarReservasAFecha = new org.jdesktop.swingx.JXDatePicker();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblErrorBuscarReservas = new javax.swing.JLabel();
        tbtnFiltrarPorFechaInicio = new javax.swing.JToggleButton();
        tbtnFiltrarPorFechaFin = new javax.swing.JToggleButton();
        pnlCuerpoClientes1 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblReservas = new javax.swing.JTable();
        btnGuardarReservasXml = new javax.swing.JButton();
        btnGuardarReservasExcel = new javax.swing.JButton();
        jMenuBar = new javax.swing.JMenuBar();
        menuArchivo = new javax.swing.JMenu();
        menuItemNuevoCliente = new javax.swing.JMenuItem();
        menuItemNuevaReserva = new javax.swing.JMenuItem();
        menuItemNuevoVehiculo = new javax.swing.JMenuItem();
        menuItemNuevoGaraje = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        menuItemCerrar = new javax.swing.JMenuItem();
        menuVer = new javax.swing.JMenu();
        menuItemVerClientes = new javax.swing.JMenuItem();
        menuItemVerReservas = new javax.swing.JMenuItem();
        menuItemVerVehiculos = new javax.swing.JMenuItem();

        pMenuItemNuevaReserva.setText("Nueva Reserva");
        pMenuItemNuevaReserva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pMenuItemNuevaReservaActionPerformed(evt);
            }
        });
        pMenuTabla.add(pMenuItemNuevaReserva);

        pMenuItemModificarReserva.setText("Modificar Reserva");
        pMenuItemModificarReserva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pMenuItemModificarReservaActionPerformed(evt);
            }
        });
        pMenuLista.add(pMenuItemModificarReserva);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblTituloPanelClientes.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTituloPanelClientes.setText("CLIENTES");

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

        jScrollPane3.setViewportView(tblClientes);

        jScrollPane2.setViewportView(lstReservasVistaClientes);

        lblTituloListaReservasVistaClientes.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTituloListaReservasVistaClientes.setText("RESERVAS");

        btnGuardarClientesXml.setText("Guardar XML");
        btnGuardarClientesXml.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarClientesXmlActionPerformed(evt);
            }
        });

        btnGuardarClientesExcel.setText("Guardar Excel");
        btnGuardarClientesExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarClientesExcelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlCuerpoClientesLayout = new javax.swing.GroupLayout(pnlCuerpoClientes);
        pnlCuerpoClientes.setLayout(pnlCuerpoClientesLayout);
        pnlCuerpoClientesLayout.setHorizontalGroup(
            pnlCuerpoClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCuerpoClientesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlCuerpoClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 560, Short.MAX_VALUE)
                    .addGroup(pnlCuerpoClientesLayout.createSequentialGroup()
                        .addComponent(btnGuardarClientesXml, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnGuardarClientesExcel, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlCuerpoClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTituloListaReservasVistaClientes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 487, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlCuerpoClientesLayout.setVerticalGroup(
            pnlCuerpoClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCuerpoClientesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlCuerpoClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlCuerpoClientesLayout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlCuerpoClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnGuardarClientesXml)
                            .addComponent(btnGuardarClientesExcel))
                        .addGap(5, 5, 5))
                    .addGroup(pnlCuerpoClientesLayout.createSequentialGroup()
                        .addComponent(lblTituloListaReservasVistaClientes)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 399, Short.MAX_VALUE)))
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
                    .addComponent(lblTituloPanelClientes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlClientesLayout.setVerticalGroup(
            pnlClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlClientesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTituloPanelClientes)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlPrincipalClientes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        lblTituloPanelVehiculos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTituloPanelVehiculos.setText("VEHÍCULOS");

        btnNuevoVehiculo.setText("Nuevo Vehículo");
        btnNuevoVehiculo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoVehiculoActionPerformed(evt);
            }
        });

        btnBuscarVehiculo.setText("Buscar Vehículo");
        btnBuscarVehiculo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarVehiculoActionPerformed(evt);
            }
        });

        lblBuscarVehiculoMarca.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblBuscarVehiculoMarca.setText("Marca");

        lblBuscarVehiculoModelo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblBuscarVehiculoModelo.setText("Modelo");

        btnModificarVehiculo.setText("Modificar Vehículo");
        btnModificarVehiculo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarVehiculoActionPerformed(evt);
            }
        });

        btnEliminarVehiculo.setBackground(new java.awt.Color(240, 0, 0));
        btnEliminarVehiculo.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminarVehiculo.setText("Eliminar Vehículo");
        btnEliminarVehiculo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarVehiculoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlCabeceraVehiculosLayout = new javax.swing.GroupLayout(pnlCabeceraVehiculos);
        pnlCabeceraVehiculos.setLayout(pnlCabeceraVehiculosLayout);
        pnlCabeceraVehiculosLayout.setHorizontalGroup(
            pnlCabeceraVehiculosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCabeceraVehiculosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlCabeceraVehiculosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlCabeceraVehiculosLayout.createSequentialGroup()
                        .addGroup(pnlCabeceraVehiculosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnNuevoVehiculo, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnModificarVehiculo, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pnlCabeceraVehiculosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblBuscarVehiculoMarca, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblBuscarVehiculoModelo, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)))
                    .addGroup(pnlCabeceraVehiculosLayout.createSequentialGroup()
                        .addComponent(btnEliminarVehiculo, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlCabeceraVehiculosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtBuscarVehiculoMarca)
                    .addComponent(txtBuscarVehiculoModelo)
                    .addComponent(btnBuscarVehiculo, javax.swing.GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlCabeceraVehiculosLayout.setVerticalGroup(
            pnlCabeceraVehiculosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCabeceraVehiculosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlCabeceraVehiculosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscarVehiculoMarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblBuscarVehiculoMarca)
                    .addComponent(btnNuevoVehiculo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlCabeceraVehiculosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscarVehiculoModelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblBuscarVehiculoModelo)
                    .addComponent(btnModificarVehiculo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addGroup(pnlCabeceraVehiculosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBuscarVehiculo)
                    .addComponent(btnEliminarVehiculo))
                .addContainerGap())
        );

        jScrollPane7.setViewportView(tblVehiculos);

        jScrollPane8.setViewportView(lstReservasVistaVehiculos);

        lblTituloListaReservasVistaVehiculos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTituloListaReservasVistaVehiculos.setText("RESERVAS");

        btnGuardarVehiculosXml.setText("Guardar XML");
        btnGuardarVehiculosXml.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarVehiculosXmlActionPerformed(evt);
            }
        });

        btnGuardarVehiculosExcel.setText("Guardar Excel");
        btnGuardarVehiculosExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarVehiculosExcelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlCuerpoVehiculosLayout = new javax.swing.GroupLayout(pnlCuerpoVehiculos);
        pnlCuerpoVehiculos.setLayout(pnlCuerpoVehiculosLayout);
        pnlCuerpoVehiculosLayout.setHorizontalGroup(
            pnlCuerpoVehiculosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCuerpoVehiculosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlCuerpoVehiculosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 560, Short.MAX_VALUE)
                    .addGroup(pnlCuerpoVehiculosLayout.createSequentialGroup()
                        .addComponent(btnGuardarVehiculosXml, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnGuardarVehiculosExcel, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlCuerpoVehiculosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTituloListaReservasVistaVehiculos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 487, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlCuerpoVehiculosLayout.setVerticalGroup(
            pnlCuerpoVehiculosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCuerpoVehiculosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlCuerpoVehiculosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlCuerpoVehiculosLayout.createSequentialGroup()
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlCuerpoVehiculosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnGuardarVehiculosXml)
                            .addComponent(btnGuardarVehiculosExcel))
                        .addGap(5, 5, 5))
                    .addGroup(pnlCuerpoVehiculosLayout.createSequentialGroup()
                        .addComponent(lblTituloListaReservasVistaVehiculos)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 399, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout pnlPrincipalVehiculosLayout = new javax.swing.GroupLayout(pnlPrincipalVehiculos);
        pnlPrincipalVehiculos.setLayout(pnlPrincipalVehiculosLayout);
        pnlPrincipalVehiculosLayout.setHorizontalGroup(
            pnlPrincipalVehiculosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlCabeceraVehiculos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlCuerpoVehiculos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlPrincipalVehiculosLayout.setVerticalGroup(
            pnlPrincipalVehiculosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPrincipalVehiculosLayout.createSequentialGroup()
                .addComponent(pnlCabeceraVehiculos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlCuerpoVehiculos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlVehiculosLayout = new javax.swing.GroupLayout(pnlVehiculos);
        pnlVehiculos.setLayout(pnlVehiculosLayout);
        pnlVehiculosLayout.setHorizontalGroup(
            pnlVehiculosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlVehiculosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlVehiculosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pnlPrincipalVehiculos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTituloPanelVehiculos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlVehiculosLayout.setVerticalGroup(
            pnlVehiculosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlVehiculosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTituloPanelVehiculos)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlPrincipalVehiculos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
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

        tbtnVerPendientesDevolucion.setText("Pendientes de devolución");
        tbtnVerPendientesDevolucion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbtnVerPendientesDevolucionActionPerformed(evt);
            }
        });

        tbtnVerPendientesEntrega.setText("Pendientes de entrega");
        tbtnVerPendientesEntrega.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbtnVerPendientesEntregaActionPerformed(evt);
            }
        });

        dtpBuscarReservaDeFecha.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dtpBuscarReservaDeFechaPropertyChange(evt);
            }
        });

        dtpBuscarReservasAFecha.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dtpBuscarReservasAFechaPropertyChange(evt);
            }
        });

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("De:");

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("A:");

        lblErrorBuscarReservas.setForeground(new java.awt.Color(255, 0, 0));

        tbtnFiltrarPorFechaInicio.setText("Fecha de inicio");
        tbtnFiltrarPorFechaInicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbtnFiltrarPorFechaInicioActionPerformed(evt);
            }
        });

        tbtnFiltrarPorFechaFin.setText("Fecha de fin");
        tbtnFiltrarPorFechaFin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbtnFiltrarPorFechaFinActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlCabeceraReservasLayout = new javax.swing.GroupLayout(pnlCabeceraReservas);
        pnlCabeceraReservas.setLayout(pnlCabeceraReservasLayout);
        pnlCabeceraReservasLayout.setHorizontalGroup(
            pnlCabeceraReservasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCabeceraReservasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlCabeceraReservasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlCabeceraReservasLayout.createSequentialGroup()
                        .addComponent(btnNuevaReserva, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 444, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlCabeceraReservasLayout.createSequentialGroup()
                        .addComponent(btnEliminarReserva, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(pnlCabeceraReservasLayout.createSequentialGroup()
                        .addComponent(btnModificarReserva, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlCabeceraReservasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(dtpBuscarReservasAFecha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dtpBuscarReservaDeFecha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tbtnFiltrarPorFechaInicio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tbtnFiltrarPorFechaFin, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE))
                .addGap(10, 10, 10)
                .addGroup(pnlCabeceraReservasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCabeceraReservasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(lblErrorBuscarReservas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(tbtnVerPendientesEntrega, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
                    .addComponent(tbtnVerPendientesDevolucion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        pnlCabeceraReservasLayout.setVerticalGroup(
            pnlCabeceraReservasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCabeceraReservasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlCabeceraReservasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNuevaReserva)
                    .addComponent(tbtnVerPendientesDevolucion)
                    .addComponent(dtpBuscarReservaDeFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlCabeceraReservasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnModificarReserva)
                    .addComponent(tbtnVerPendientesEntrega)
                    .addComponent(dtpBuscarReservasAFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tbtnFiltrarPorFechaInicio)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlCabeceraReservasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEliminarReserva)
                    .addComponent(lblErrorBuscarReservas, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tbtnFiltrarPorFechaFin))
                .addContainerGap())
        );

        jScrollPane4.setViewportView(tblReservas);

        btnGuardarReservasXml.setText("Guardar XML");
        btnGuardarReservasXml.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarReservasXmlActionPerformed(evt);
            }
        });

        btnGuardarReservasExcel.setText("Guardar Excel");
        btnGuardarReservasExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarReservasExcelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlCuerpoClientes1Layout = new javax.swing.GroupLayout(pnlCuerpoClientes1);
        pnlCuerpoClientes1.setLayout(pnlCuerpoClientes1Layout);
        pnlCuerpoClientes1Layout.setHorizontalGroup(
            pnlCuerpoClientes1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCuerpoClientes1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlCuerpoClientes1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 1057, Short.MAX_VALUE)
                    .addGroup(pnlCuerpoClientes1Layout.createSequentialGroup()
                        .addComponent(btnGuardarReservasXml, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnGuardarReservasExcel, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlCuerpoClientes1Layout.setVerticalGroup(
            pnlCuerpoClientes1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCuerpoClientes1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 371, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlCuerpoClientes1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardarReservasXml)
                    .addComponent(btnGuardarReservasExcel))
                .addGap(12, 12, 12))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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

        menuItemNuevoCliente.setText("Nuevo cliente");
        menuItemNuevoCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemNuevoClienteActionPerformed(evt);
            }
        });
        menuArchivo.add(menuItemNuevoCliente);

        menuItemNuevaReserva.setText("Nueva reserva");
        menuItemNuevaReserva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemNuevaReservaActionPerformed(evt);
            }
        });
        menuArchivo.add(menuItemNuevaReserva);

        menuItemNuevoVehiculo.setText("Nuevo vehículo");
        menuItemNuevoVehiculo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemNuevoVehiculoActionPerformed(evt);
            }
        });
        menuArchivo.add(menuItemNuevoVehiculo);

        menuItemNuevoGaraje.setText("Nuevo Garaje");
        menuItemNuevoGaraje.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemNuevoGarajeActionPerformed(evt);
            }
        });
        menuArchivo.add(menuItemNuevoGaraje);
        menuArchivo.add(jSeparator1);

        menuItemCerrar.setText("Salir");
        menuItemCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemCerrarActionPerformed(evt);
            }
        });
        menuArchivo.add(menuItemCerrar);

        jMenuBar.add(menuArchivo);

        menuVer.setText("Ver");

        menuItemVerClientes.setText("Clientes");
        menuItemVerClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemVerClientesActionPerformed(evt);
            }
        });
        menuVer.add(menuItemVerClientes);

        menuItemVerReservas.setText("Reservas");
        menuItemVerReservas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemVerReservasActionPerformed(evt);
            }
        });
        menuVer.add(menuItemVerReservas);

        menuItemVerVehiculos.setText("Vehículos");
        menuItemVerVehiculos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemVerVehiculosActionPerformed(evt);
            }
        });
        menuVer.add(menuItemVerVehiculos);

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
        listaClientesVistaClientes.clear();

        TypedQuery<Cliente> query = em.createNamedQuery("Cliente.findByNombreOrApellidos", Cliente.class);

        query.setParameter("nombre", "%" + txtBuscarClienteNombre.getText() + "%");
        query.setParameter("apellidos", "%" + txtBuscarClienteApellidos.getText() + "%");

        List<Cliente> results = query.getResultList();

        cargarListaTablaClientes(results);

        tblClientes.setModel(new TableModelClientes(listaClientesVistaClientes));

        listaReservasVistaClientes.clear();
    }//GEN-LAST:event_btnBuscarClienteActionPerformed

    private void btnNuevoClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoClienteActionPerformed
        nuevoCliente();
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
        if (tblClientes.getSelectedRow() != -1) {
            int i = tblClientes.convertRowIndexToModel(tblClientes.getSelectedRow());
            Cliente c = listaClientesVistaClientes.get(i);

            int dialogResult = JOptionPane.showConfirmDialog(
                    null,
                    "Vas a eliminar al cliente seleccionado!!\n"
                    + "También eliminarás todas las reservas de ese cliente!!\n"
                    + "¿Estás seguro?",
                    "Eliminar cliente",
                    JOptionPane.YES_NO_OPTION);

            if (dialogResult == JOptionPane.YES_OPTION) {

                em.getTransaction().begin();
                em.remove(c);
                em.getTransaction().commit();

                cargarDatos();
            }

            lstReservasVistaClientes.removeAll();
        }
    }//GEN-LAST:event_btnEliminarClienteActionPerformed

    private void pMenuItemNuevaReservaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pMenuItemNuevaReservaActionPerformed
        if (pnlClientes.isVisible()) {
            if (tblClientes.getSelectedRow() != -1) {
                int i = tblClientes.convertRowIndexToModel(tblClientes.getSelectedRow());
                Cliente c = listaClientesVistaClientes.get(i);

                DiaCrearModificarReserva diaCrearReserva = new DiaCrearModificarReserva(this, true, c);
                diaCrearReserva.setLocationRelativeTo(this);
                diaCrearReserva.setVisible(true);
            }
        } else if (pnlVehiculos.isVisible()) {
            if (tblVehiculos.getSelectedRow() != -1) {
                int i = tblVehiculos.convertRowIndexToModel(tblVehiculos.getSelectedRow());
                Vehiculo v = listaVehiculosVistaVehiculos.get(i);

                DiaCrearModificarReserva diaCrearReserva = new DiaCrearModificarReserva(this, true, v);
                diaCrearReserva.setLocationRelativeTo(this);
                diaCrearReserva.setVisible(true);
            }
        }

    }//GEN-LAST:event_pMenuItemNuevaReservaActionPerformed

    private void menuItemNuevoClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemNuevoClienteActionPerformed
        nuevoCliente();
    }//GEN-LAST:event_menuItemNuevoClienteActionPerformed

    private void menuItemCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemCerrarActionPerformed
        em.close();
        emf.close();
        System.exit(0);
    }//GEN-LAST:event_menuItemCerrarActionPerformed

    private void menuItemNuevaReservaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemNuevaReservaActionPerformed
        nuevaReserva();
    }//GEN-LAST:event_menuItemNuevaReservaActionPerformed

    private void btnNuevoVehiculoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoVehiculoActionPerformed
        nuevoVehiculo();
    }//GEN-LAST:event_btnNuevoVehiculoActionPerformed

    private void btnBuscarVehiculoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarVehiculoActionPerformed
        listaVehiculosVistaVehiculos.clear();

        TypedQuery<Vehiculo> query = em.createNamedQuery("Vehiculo.findByMarcaOrModelo", Vehiculo.class);

        query.setParameter("marca", "%" + txtBuscarVehiculoMarca.getText() + "%");
        query.setParameter("modelo", "%" + txtBuscarVehiculoModelo.getText() + "%");

        List<Vehiculo> results = query.getResultList();

        cargarListaTablaVehiculos(results);

        tblVehiculos.setModel(new TableModelVehiculos(listaVehiculosVistaVehiculos));

        listaReservasVistaVehiculos.clear();
    }//GEN-LAST:event_btnBuscarVehiculoActionPerformed

    private void btnModificarVehiculoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarVehiculoActionPerformed
        if (tblVehiculos.getSelectedRow() != -1) {
            int i = tblVehiculos.convertRowIndexToModel(tblVehiculos.getSelectedRow());
            Vehiculo v = listaVehiculosVistaVehiculos.get(i);

            DiaCrearModificarVehiculo diaCrearVehiculo = new DiaCrearModificarVehiculo(this, true, v);
            diaCrearVehiculo.setLocationRelativeTo(this);
            diaCrearVehiculo.setVisible(true);
        }
    }//GEN-LAST:event_btnModificarVehiculoActionPerformed

    private void btnEliminarVehiculoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarVehiculoActionPerformed
        if (tblVehiculos.getSelectedRow() != -1) {
            int i = tblVehiculos.convertRowIndexToModel(tblVehiculos.getSelectedRow());
            Vehiculo c = listaVehiculosVistaVehiculos.get(i);

            int dialogResult = JOptionPane.showConfirmDialog(
                    null,
                    "Vas a eliminar el vehículo seleccionado!!\n"
                    + "También eliminarás todas las reservas de ese vehículo!!\n"
                    + "¿Estás seguro?",
                    "Eliminar vehículo",
                    JOptionPane.YES_NO_OPTION);

            if (dialogResult == JOptionPane.YES_OPTION) {

                em.getTransaction().begin();
                em.remove(c);
                em.getTransaction().commit();

                cargarDatos();
            }

            lstReservasVistaVehiculos.removeAll();
        }
    }//GEN-LAST:event_btnEliminarVehiculoActionPerformed

    private void menuItemNuevoVehiculoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemNuevoVehiculoActionPerformed
        nuevoVehiculo();
    }//GEN-LAST:event_menuItemNuevoVehiculoActionPerformed

    private void btnGuardarClientesXmlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarClientesXmlActionPerformed
        File archivo = seleccionarArchivoXml();
        if (archivo != null) {
            ExportarClientes gc = new ExportarClientes();
            gc.guardarXml(tblClientes, archivo);
        }
    }//GEN-LAST:event_btnGuardarClientesXmlActionPerformed

    private void btnGuardarReservasXmlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarReservasXmlActionPerformed
        File archivo = seleccionarArchivoXml();
        if (archivo != null) {
            ExportarReservas gr = new ExportarReservas();
            gr.guardarXml(tblReservas, archivo);
        }
    }//GEN-LAST:event_btnGuardarReservasXmlActionPerformed

    private void btnGuardarVehiculosXmlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarVehiculosXmlActionPerformed
        File archivo = seleccionarArchivoXml();
        if (archivo != null) {
            ExportarVehiculos gv = new ExportarVehiculos();
            gv.guardarXml(tblVehiculos, archivo);
        }
    }//GEN-LAST:event_btnGuardarVehiculosXmlActionPerformed

    private void btnGuardarVehiculosExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarVehiculosExcelActionPerformed
        File archivo = seleccionarArchivoExcel();
        if (archivo != null) {
            ExportarClientes gc = new ExportarClientes();
            gc.guardarExcel(tblVehiculos, archivo);
        }
    }//GEN-LAST:event_btnGuardarVehiculosExcelActionPerformed

    private void btnGuardarClientesExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarClientesExcelActionPerformed
        File archivo = seleccionarArchivoExcel();
        if (archivo != null) {
            ExportarClientes gc = new ExportarClientes();
            gc.guardarExcel(tblClientes, archivo);
        }
    }//GEN-LAST:event_btnGuardarClientesExcelActionPerformed

    private void btnGuardarReservasExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarReservasExcelActionPerformed
        File archivo = seleccionarArchivoExcel();
        if (archivo != null) {
            ExportarClientes gc = new ExportarClientes();
            gc.guardarExcel(tblReservas, archivo);
        }
    }//GEN-LAST:event_btnGuardarReservasExcelActionPerformed

    private void tbtnFiltrarPorFechaInicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbtnFiltrarPorFechaInicioActionPerformed
        if (tbtnFiltrarPorFechaInicio.isSelected()) {
            if (dtpBuscarReservaDeFecha.getDate() != null && dtpBuscarReservasAFecha.getDate() != null) {
                if (dtpBuscarReservaDeFecha.getDate().before(dtpBuscarReservasAFecha.getDate())) {
                    List<Reserva> results = new ArrayList<>();
                    listaReservasVistaReserva.clear();

                    TypedQuery<Reserva> query = em.createNamedQuery("Reserva.findByFechaInicio", Reserva.class);

                    query.setParameter("deFecha", dtpBuscarReservaDeFecha.getDate());
                    query.setParameter("aFecha", dtpBuscarReservasAFecha.getDate());

                    results = query.getResultList();

                    cargarListaTablaReservas(results);

                    tbtnVerPendientesEntrega.setSelected(false);
                    tbtnVerPendientesDevolucion.setSelected(false);
                    tbtnFiltrarPorFechaFin.setSelected(false);
                } else {
                    lblErrorBuscarReservas.setText("Las fechas no son correctas.");
                    tbtnFiltrarPorFechaInicio.setSelected(false);
                }
            } else {
                lblErrorBuscarReservas.setText("Debes seleccionar las fechas.");
                tbtnFiltrarPorFechaInicio.setSelected(false);
            }
        } else {
            cargarReservas();
        }
    }//GEN-LAST:event_tbtnFiltrarPorFechaInicioActionPerformed

    private void dtpBuscarReservasAFechaPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dtpBuscarReservasAFechaPropertyChange
        tbtnFiltrarPorFechaInicio.setSelected(false);
    }//GEN-LAST:event_dtpBuscarReservasAFechaPropertyChange

    private void dtpBuscarReservaDeFechaPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dtpBuscarReservaDeFechaPropertyChange
        tbtnFiltrarPorFechaInicio.setSelected(false);
    }//GEN-LAST:event_dtpBuscarReservaDeFechaPropertyChange

    private void tbtnVerPendientesEntregaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbtnVerPendientesEntregaActionPerformed
        if (tbtnVerPendientesEntrega.isSelected()) {

            List<Reserva> results = new ArrayList<>();
            listaReservasVistaReserva.clear();

            TypedQuery<Reserva> query = em.createNamedQuery("Reserva.findByEntregado", Reserva.class);

            query.setParameter("entregado", false);

            results = query.getResultList();

            cargarListaTablaReservas(results);

            tbtnVerPendientesDevolucion.setSelected(false);
            tbtnFiltrarPorFechaInicio.setSelected(false);
            tbtnFiltrarPorFechaFin.setSelected(false);

        } else {
            cargarReservas();
        }
    }//GEN-LAST:event_tbtnVerPendientesEntregaActionPerformed

    private void tbtnVerPendientesDevolucionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbtnVerPendientesDevolucionActionPerformed
        if (tbtnVerPendientesDevolucion.isSelected()) {

            List<Reserva> results = new ArrayList<>();
            listaReservasVistaReserva.clear();

            TypedQuery<Reserva> query = em.createNamedQuery("Reserva.findByDevuelto", Reserva.class);

            query.setParameter("devuelto", false);

            results = query.getResultList();

            cargarListaTablaReservas(results);

            tbtnVerPendientesEntrega.setSelected(false);
            tbtnFiltrarPorFechaInicio.setSelected(false);
            tbtnFiltrarPorFechaFin.setSelected(false);

        } else {
            cargarReservas();
        }
    }//GEN-LAST:event_tbtnVerPendientesDevolucionActionPerformed

    private void btnEliminarReservaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarReservaActionPerformed
        if (tblReservas.getSelectedRow() != -1) {
            int i = tblReservas.convertRowIndexToModel(tblReservas.getSelectedRow());
            Reserva r = listaReservasVistaReserva.get(i);

            int dialogResult = JOptionPane.showConfirmDialog(
                    null,
                    "Vas a eliminar la reserva seleccionada\n¿Estás seguro?",
                    "Eliminar reserva",
                    JOptionPane.YES_NO_OPTION);

            if (dialogResult == JOptionPane.YES_OPTION) {

                em.getTransaction().begin();
                em.remove(r);
                em.getTransaction().commit();

                cargarDatos();
            }
        }
    }//GEN-LAST:event_btnEliminarReservaActionPerformed

    private void btnModificarReservaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarReservaActionPerformed
        if (tblReservas.getSelectedRow() != -1) {
            int i = tblReservas.convertRowIndexToModel(tblReservas.getSelectedRow());
            Reserva r = listaReservasVistaReserva.get(i);

            DiaCrearModificarReserva diaModificarReserva = new DiaCrearModificarReserva(this, true, r);
            diaModificarReserva.setLocationRelativeTo(this);
            diaModificarReserva.setVisible(true);
        }
    }//GEN-LAST:event_btnModificarReservaActionPerformed

    private void btnNuevaReservaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevaReservaActionPerformed
        nuevaReserva();
    }//GEN-LAST:event_btnNuevaReservaActionPerformed

    private void tbtnFiltrarPorFechaFinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbtnFiltrarPorFechaFinActionPerformed
        if (tbtnFiltrarPorFechaFin.isSelected()) {
            if (dtpBuscarReservaDeFecha.getDate() != null && dtpBuscarReservasAFecha.getDate() != null) {
                if (dtpBuscarReservaDeFecha.getDate().before(dtpBuscarReservasAFecha.getDate())) {
                    List<Reserva> results = new ArrayList<>();
                    listaReservasVistaReserva.clear();

                    TypedQuery<Reserva> query = em.createNamedQuery("Reserva.findByFechaFin", Reserva.class);

                    query.setParameter("deFecha", dtpBuscarReservaDeFecha.getDate());
                    query.setParameter("aFecha", dtpBuscarReservasAFecha.getDate());

                    results = query.getResultList();

                    cargarListaTablaReservas(results);

                    tbtnVerPendientesEntrega.setSelected(false);
                    tbtnVerPendientesDevolucion.setSelected(false);
                    tbtnFiltrarPorFechaInicio.setSelected(false);
                } else {
                    lblErrorBuscarReservas.setText("Las fechas no son correctas.");
                    tbtnFiltrarPorFechaFin.setSelected(false);
                }
            } else {
                lblErrorBuscarReservas.setText("Debes seleccionar las fechas.");
                tbtnFiltrarPorFechaFin.setSelected(false);
            }
        } else {
            cargarReservas();
        }
    }//GEN-LAST:event_tbtnFiltrarPorFechaFinActionPerformed

    private void pMenuItemModificarReservaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pMenuItemModificarReservaActionPerformed
        if (pnlClientes.isVisible()) {
            if (lstReservasVistaClientes.getSelectedIndex() != -1) {
                Reserva r = (Reserva) lstReservasVistaClientes.getSelectedValue();

                DiaCrearModificarReserva diaCrearReserva = new DiaCrearModificarReserva(this, true, r);
                diaCrearReserva.setLocationRelativeTo(this);
                diaCrearReserva.setVisible(true);
            }
        } else if (pnlVehiculos.isVisible()) {
            if (lstReservasVistaVehiculos.getSelectedIndex() != -1) {
                Reserva r = (Reserva) lstReservasVistaVehiculos.getSelectedValue();

                DiaCrearModificarReserva diaCrearReserva = new DiaCrearModificarReserva(this, true, r);
                diaCrearReserva.setLocationRelativeTo(this);
                diaCrearReserva.setVisible(true);
            }
        }
    }//GEN-LAST:event_pMenuItemModificarReservaActionPerformed

    private void menuItemNuevoGarajeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemNuevoGarajeActionPerformed
        nuevoGaraje();
    }//GEN-LAST:event_menuItemNuevoGarajeActionPerformed

    private void cargarListaReservasDeClienteSeleccionado() {
        if (tblClientes.getSelectedRow() != -1) {
            int i = tblClientes.convertRowIndexToModel(tblClientes.getSelectedRow());
            Cliente c = listaClientesVistaClientes.get(i);

            listaReservasVistaClientes.clear();
            List<Reserva> listaReservasCLiente = c.getReservaList();

            if (!listaReservasCLiente.isEmpty()) {
                ordenaPorFechaInicio(listaReservasCLiente);

                for (Reserva r : listaReservasCLiente) {
                    listaReservasVistaClientes.addElement(r);
                }

                lstReservasVistaClientes.setComponentPopupMenu(pMenuLista);

            } else {
                lstReservasVistaClientes.setComponentPopupMenu(null);
            }
            lstReservasVistaClientes.setModel(listaReservasVistaClientes);
        }
    }

    private void cargarListaReservasDeVehiculoSeleccionado() {
        if (tblVehiculos.getSelectedRow() != -1) {
            int i = tblVehiculos.convertRowIndexToModel(tblVehiculos.getSelectedRow());
            Vehiculo v = listaVehiculosVistaVehiculos.get(i);

            listaReservasVistaVehiculos.clear();
            List<Reserva> listaReservasVehiculo = v.getReservaList();

            if (!listaReservasVehiculo.isEmpty()) {
                ordenaPorFechaInicio(listaReservasVehiculo);

                for (Reserva r : listaReservasVehiculo) {
                    listaReservasVistaVehiculos.addElement(r);
                }

                lstReservasVistaVehiculos.setComponentPopupMenu(pMenuLista);

            } else {
                lstReservasVistaVehiculos.setComponentPopupMenu(null);
            }
            lstReservasVistaVehiculos.setModel(listaReservasVistaVehiculos);
        }
    }

    private void cargarListaTablaReservas(List<Reserva> results) {
        ordenaPorFechaInicio(results);

        for (Reserva r : results) {
            listaReservasVistaReserva.add(r);
        }

        tblReservas.setModel(new TableModelReservas(listaReservasVistaReserva));

        lblErrorBuscarReservas.setText("");
    }

    private void ordenaPorFechaInicio(List<Reserva> listaReservas) {
        listaReservas.sort(new Comparator<Reserva>() {
            @Override
            public int compare(Reserva r1, Reserva r2) {
                return r2.getFechaInicio().compareTo(r1.getFechaInicio());
            }
        });
    }

    private void cargarListaTablaVehiculos(List<Vehiculo> vehiculos) {
        // Ordenamos los vehículos por marca
        vehiculos.sort(new Comparator<Vehiculo>() {
            @Override
            public int compare(Vehiculo o1, Vehiculo o2) {
                return o1.getMarca().compareTo(o2.getMarca());
            }
        });

        for (Vehiculo v : vehiculos) {
            listaVehiculosVistaVehiculos.add(v);
        }
    }

    private void cargarListaTablaClientes(List<Cliente> clientes) {
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
    }

    private File seleccionarArchivoXml() {
        File archivo = null;

        JFileChooser fc = new JFileChooser(new File("."));

        fc.setFileFilter(new FileNameExtensionFilter("xml", "xml"));
        int returnVal = fc.showSaveDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            archivo = fc.getSelectedFile();

            // Forzamos la extensión a xml
            if (!archivo.toString().endsWith(".xml")) {
                archivo = new File(archivo.toString() + ".xml");
            }
        }
        return archivo;
    }

    private File seleccionarArchivoExcel() {
        File archivo = null;

        JFileChooser fc = new JFileChooser(new File("."));

        fc.setFileFilter(new FileNameExtensionFilter("xls", "xls"));
        int returnVal = fc.showSaveDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            archivo = fc.getSelectedFile();

            // Forzamos la extensión a xml
            if (!archivo.toString().endsWith(".xls")) {
                archivo = new File(archivo.toString() + ".xls");
            }
        }
        return archivo;
    }

    private void nuevoCliente() {
        DiaCrearModificarCliente diaCrearCliente = new DiaCrearModificarCliente(this, true);
        diaCrearCliente.setLocationRelativeTo(this);
        diaCrearCliente.setVisible(true);
    }

    private void nuevaReserva() {
        DiaCrearModificarReserva diaCrearReserva = new DiaCrearModificarReserva(this, true);
        diaCrearReserva.setLocationRelativeTo(this);
        diaCrearReserva.setVisible(true);
    }

    private void nuevoVehiculo() {
        DiaCrearModificarVehiculo diaCrearVehiculo = new DiaCrearModificarVehiculo(this, true);
        diaCrearVehiculo.setLocationRelativeTo(this);
        diaCrearVehiculo.setVisible(true);
    }

    private void nuevoGaraje() {
        DiaCrearGaraje diaCrearGaraje = new DiaCrearGaraje(this, true);
        diaCrearGaraje.setLocationRelativeTo(this);
        diaCrearGaraje.setVisible(true);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.persistence.EntityManager AlquilerVehiculosPUEntityManager;
    private javax.swing.JButton btnBuscarCliente;
    private javax.swing.JButton btnBuscarVehiculo;
    private javax.swing.JButton btnEliminarCliente;
    private javax.swing.JButton btnEliminarReserva;
    private javax.swing.JButton btnEliminarVehiculo;
    private javax.swing.JButton btnGuardarClientesExcel;
    private javax.swing.JButton btnGuardarClientesXml;
    private javax.swing.JButton btnGuardarReservasExcel;
    private javax.swing.JButton btnGuardarReservasXml;
    private javax.swing.JButton btnGuardarVehiculosExcel;
    private javax.swing.JButton btnGuardarVehiculosXml;
    private javax.swing.JButton btnModificarCliente;
    private javax.swing.JButton btnModificarReserva;
    private javax.swing.JButton btnModificarVehiculo;
    private javax.swing.JButton btnNuevaReserva;
    private javax.swing.JButton btnNuevoCliente;
    private javax.swing.JButton btnNuevoVehiculo;
    private java.util.List<model.Cliente> clienteList;
    private javax.persistence.Query clienteQuery;
    private org.jdesktop.swingx.JXDatePicker dtpBuscarReservaDeFecha;
    private org.jdesktop.swingx.JXDatePicker dtpBuscarReservasAFecha;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenuBar jMenuBar;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JLabel lblBuscarClienteApellidos;
    private javax.swing.JLabel lblBuscarClienteNombre;
    private javax.swing.JLabel lblBuscarVehiculoMarca;
    private javax.swing.JLabel lblBuscarVehiculoModelo;
    private javax.swing.JLabel lblErrorBuscarReservas;
    private javax.swing.JLabel lblTituloListaReservasVistaClientes;
    private javax.swing.JLabel lblTituloListaReservasVistaVehiculos;
    private javax.swing.JLabel lblTituloPanelClientes;
    private javax.swing.JLabel lblTituloPanelVehiculos;
    private javax.swing.JLabel lblTituloPnlReservas;
    private javax.swing.JList lstReservasVistaClientes;
    private javax.swing.JList lstReservasVistaVehiculos;
    private javax.swing.JMenu menuArchivo;
    private javax.swing.JMenuItem menuItemCerrar;
    private javax.swing.JMenuItem menuItemNuevaReserva;
    private javax.swing.JMenuItem menuItemNuevoCliente;
    private javax.swing.JMenuItem menuItemNuevoGaraje;
    private javax.swing.JMenuItem menuItemNuevoVehiculo;
    private javax.swing.JMenuItem menuItemVerClientes;
    private javax.swing.JMenuItem menuItemVerReservas;
    private javax.swing.JMenuItem menuItemVerVehiculos;
    private javax.swing.JMenu menuVer;
    private javax.swing.JMenuItem pMenuItemModificarReserva;
    private javax.swing.JMenuItem pMenuItemNuevaReserva;
    private javax.swing.JPopupMenu pMenuLista;
    private javax.swing.JPopupMenu pMenuTabla;
    private javax.swing.JPanel pnlCabeceraClientes;
    private javax.swing.JPanel pnlCabeceraReservas;
    private javax.swing.JPanel pnlCabeceraVehiculos;
    private javax.swing.JPanel pnlClientes;
    private javax.swing.JPanel pnlContenedor;
    private javax.swing.JPanel pnlCuerpoClientes;
    private javax.swing.JPanel pnlCuerpoClientes1;
    private javax.swing.JPanel pnlCuerpoVehiculos;
    private javax.swing.JPanel pnlPrincipaReservas;
    private javax.swing.JPanel pnlPrincipalClientes;
    private javax.swing.JPanel pnlPrincipalVehiculos;
    private javax.swing.JPanel pnlReservas;
    private javax.swing.JPanel pnlVehiculos;
    private javax.swing.JTable tblClientes;
    private javax.swing.JTable tblReservas;
    private javax.swing.JTable tblVehiculos;
    private javax.swing.JToggleButton tbtnFiltrarPorFechaFin;
    private javax.swing.JToggleButton tbtnFiltrarPorFechaInicio;
    private javax.swing.JToggleButton tbtnVerPendientesDevolucion;
    private javax.swing.JToggleButton tbtnVerPendientesEntrega;
    private javax.swing.JTextField txtBuscarClienteApellidos;
    private javax.swing.JTextField txtBuscarClienteNombre;
    private javax.swing.JTextField txtBuscarVehiculoMarca;
    private javax.swing.JTextField txtBuscarVehiculoModelo;
    // End of variables declaration//GEN-END:variables
}
