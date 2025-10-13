/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import controller.ClienteController;
import controller.EquipoController;
import model.Cliente;
import model.EquipoListado;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class Proyecto extends javax.swing.JFrame {

    // --- Controladores ---
    private ClienteController clienteCtl;
    private EquipoController  equipoCtl;

    // --- Modelos de tabla ---
    private DefaultTableModel dtm;    // Clientes
    private DefaultTableModel dtmEq;  // Equipos

    public Proyecto() {
        initComponents();
        setLocationRelativeTo(null);
        setTitle("Computec - Clientes / Equipos");

        // --- Controladores ---
        clienteCtl = new ClienteController();
        equipoCtl  = new EquipoController();

        // --- Tabla Clientes ---
        dtm = new DefaultTableModel(
                new Object[]{"RUT","Nombre","Dirección","Comuna","Teléfono","Correo"}, 0) {
            @Override public boolean isCellEditable(int r,int c){ return false; }
        };
        table.setModel(dtm);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Selección de fila → formulario cliente
        table.getSelectionModel().addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) return;
            int r = table.getSelectedRow();
            if (r >= 0) {
                txtRut.setText(      String.valueOf(dtm.getValueAt(r,0)));
                txtNombre.setText(   String.valueOf(dtm.getValueAt(r,1)));
                txtDireccion.setText(String.valueOf(dtm.getValueAt(r,2)));
                txtComuna.setText(   String.valueOf(dtm.getValueAt(r,3)));
                txtTelefono.setText( String.valueOf(dtm.getValueAt(r,4)));
                txtCorreo.setText(   String.valueOf(dtm.getValueAt(r,5)));
            }
        });

        // Acciones Clientes
        btnNuevo.addActionListener(e -> limpiarForm());
        btnLimpiar.addActionListener(e -> { txtBuscar.setText(""); limpiarForm(); cargarTablaClientes(); });
        btnRefrescar.addActionListener(e -> cargarTablaClientes());
        btnGuardar.addActionListener(e -> onCliGuardar());
        btnActualizar.addActionListener(e -> onCliActualizar());
        btnEliminar.addActionListener(e -> onCliEliminar());
        btnBuscar.addActionListener(e -> onCliBuscar());
        txtBuscar.addActionListener(e -> onCliBuscar());

        // --- Tabla Equipos ---
        dtmEq = new DefaultTableModel(
                new Object[]{"ID","Tipo","Modelo","CPU","Disco(MB)","RAM(GB)","Precio"}, 0) {
            @Override public boolean isCellEditable(int r,int c){ return false; }
        };
        table2.setModel(dtmEq);
        table2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Selección de fila → mini form equipo (solo lo que existe en DTO)
        table2.getSelectionModel().addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) return;
            int r = table2.getSelectedRow();
            if (r >= 0) {
                txtEqID.setText(String.valueOf(dtmEq.getValueAt(r,0)));
                String tipo = String.valueOf(dtmEq.getValueAt(r,1));
                txtEqEquipo.setText(tipo != null ? tipo : "");
                txtEqModelo.setText(String.valueOf(dtmEq.getValueAt(r,2)));
                txtEqPrecio.setText(String.valueOf(dtmEq.getValueAt(r,6)));
                // Estos campos no vienen en el listado:
                txtEqMarca.setText("");
                txtEqStock.setText("");
            }
        });

        // Acciones Equipos
        btnEqRefrescar.addActionListener(e -> cargarTablaEquipos());
        btnEqBuscar.addActionListener(e -> onEqBuscar());
        txtEqBuscar.addActionListener(e -> onEqBuscar());
        btnEqNuevo.addActionListener(e -> limpiarEqForm());
        btnEqEliminar.addActionListener(e -> onEqEliminar());

        // Aviso: Guardar/Actualizar de equipos requiere campos técnicos (CPU, Disco, RAM, etc.)
        btnEqGuardar.addActionListener(e -> info("Para Guardar/Actualizar equipo necesitas los campos técnicos (Desktop/Laptop)."));
        btnEqActualizar.addActionListener(e -> info("Para Actualizar equipo necesitas los campos técnicos (Desktop/Laptop)."));

        // Cargas iniciales
        cargarTablaClientes();
        cargarTablaEquipos();
    }

    // ================== CLIENTES ==================
    private void onCliGuardar() {
        try {
            Cliente c = getFormCliente(true);
            if (clienteCtl.crear(c)) {
                info("Cliente creado.");
                cargarTablaClientes();
                limpiarForm();
            }
        } catch (IllegalArgumentException ex) {
            error("Validación: " + ex.getMessage());
        } catch (Exception ex) {
            error("Error al guardar: " + ex.getMessage());
        }
    }

    private void onCliActualizar() {
        try {
            Cliente c = getFormCliente(false);
            if (clienteCtl.actualizar(c)) {
                info("Cliente actualizado.");
                cargarTablaClientes();
            } else {
                error("No se actualizó ningún registro.");
            }
        } catch (IllegalArgumentException ex) {
            error("Validación: " + ex.getMessage());
        } catch (Exception ex) {
            error("Error al actualizar: " + ex.getMessage());
        }
    }

    private void onCliEliminar() {
        String rut = txtRut.getText().trim();
        if (rut.isEmpty()) { error("Ingrese o seleccione RUT para eliminar."); return; }
        int opt = JOptionPane.showConfirmDialog(this,
                "¿Eliminar cliente RUT " + rut + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (opt != JOptionPane.YES_OPTION) return;

        try {
            if (clienteCtl.eliminar(rut)) {
                info("Cliente eliminado.");
                cargarTablaClientes();
                limpiarForm();
            } else {
                error("No se eliminó ningún registro.");
            }
        } catch (Exception ex) {
            error("Error al eliminar: " + ex.getMessage());
        }
    }

    private void onCliBuscar() {
        String q = txtBuscar.getText().trim().toLowerCase();
        try {
            List<Cliente> all = clienteCtl.listar();
            List<Cliente> res = q.isEmpty()
                    ? all
                    : all.stream().filter(c ->
                       c.getRut().toLowerCase().contains(q)
                    || (c.getNombre()!=null && c.getNombre().toLowerCase().contains(q))
                    || (c.getCorreo()!=null && c.getCorreo().toLowerCase().contains(q))
            ).toList();
            fillTablaClientes(res);
        } catch (Exception ex) { error("Error al buscar: " + ex.getMessage()); }
    }

    private void cargarTablaClientes() {
        try { fillTablaClientes(clienteCtl.listar());
        } catch (Exception ex) { error("Error al listar: " + ex.getMessage()); }
    }

    private void fillTablaClientes(List<Cliente> data) {
        dtm.setRowCount(0);
        for (Cliente c : data) {
            dtm.addRow(new Object[]{
                c.getRut(), c.getNombre(), c.getDireccion(),
                c.getComuna(), c.getTelefono(), c.getCorreo()
            });
        }
    }

    private void limpiarForm() {
        txtId.setText("");
        txtRut.setText("");
        txtNombre.setText("");
        txtDireccion.setText("");
        txtComuna.setText("");
        txtTelefono.setText("");
        txtCorreo.setText("");
        table.clearSelection();
        txtRut.requestFocus();
    }

    /** crear=true exige RUT y Nombre; en actualizar también */
    private Cliente getFormCliente(boolean crear) {
        String rut = txtRut.getText().trim();
        String nombre = txtNombre.getText().trim();
        if (rut.isEmpty())    throw new IllegalArgumentException("RUT obligatorio");
        if (nombre.isEmpty()) throw new IllegalArgumentException("Nombre obligatorio");

        Cliente c = new Cliente();
        c.setRut(rut);
        c.setNombre(nombre);
        c.setDireccion(txtDireccion.getText().trim());
        c.setComuna(txtComuna.getText().trim());
        c.setTelefono(txtTelefono.getText().trim());
        c.setCorreo(txtCorreo.getText().trim());
        return c;
    }

    // ================== EQUIPOS ==================
    private void cargarTablaEquipos() {
        try {
            List<EquipoListado> all = equipoCtl.listarTodos();
            fillTablaEquipos(all);
        } catch (Exception ex) {
            error("Error al listar equipos: " + ex.getMessage());
        }
    }

    private void onEqBuscar() {
        String q = txtEqBuscar.getText().trim().toLowerCase();
        try {
            List<EquipoListado> all = equipoCtl.listarTodos();
            List<EquipoListado> res = q.isEmpty()
                    ? all
                    : all.stream().filter(e ->
                           (e.modelo!=null && e.modelo.toLowerCase().contains(q))
                        || (e.cpu!=null    && e.cpu.toLowerCase().contains(q))
                        || (e.tipo!=null   && e.tipo.toLowerCase().contains(q))
                    ).toList();
            fillTablaEquipos(res);
        } catch (Exception ex) {
            error("Error al buscar equipos: " + ex.getMessage());
        }
    }

    private void onEqEliminar() {
        int r = table2.getSelectedRow();
        if (r < 0) { error("Selecciona un equipo en la tabla."); return; }
        try {
            int id = Integer.parseInt(String.valueOf(dtmEq.getValueAt(r, 0)));
            int opt = JOptionPane.showConfirmDialog(this,
                    "¿Eliminar equipo ID " + id + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (opt != JOptionPane.YES_OPTION) return;

            if (equipoCtl.eliminar(id)) {
                info("Equipo eliminado.");
                cargarTablaEquipos();
                limpiarEqForm();
            } else {
                error("No se eliminó ningún registro.");
            }
        } catch (NumberFormatException nfe) {
            error("ID inválido para eliminar.");
        } catch (Exception ex) {
            error("Error al eliminar equipo: " + ex.getMessage());
        }
    }

    private void fillTablaEquipos(List<EquipoListado> data) {
        dtmEq.setRowCount(0);
        for (EquipoListado e : data) {
            dtmEq.addRow(new Object[]{
                e.id, e.tipo, e.modelo, e.cpu, e.discoMb, e.ramGb, e.precio
            });
        }
    }

    private void limpiarEqForm() {
        txtEqID.setText("");
        txtEqEquipo.setText("");
        txtEqMarca.setText("");
        txtEqModelo.setText("");
        txtEqPrecio.setText("");
        txtEqStock.setText("");
        table2.clearSelection();
        txtEqBuscar.setText("");
        txtEqEquipo.requestFocus();
    }

    // ================== Utils ==================
    private void info(String m){ JOptionPane.showMessageDialog(this,m,"Info",JOptionPane.INFORMATION_MESSAGE); }
    private void error(String m){ JOptionPane.showMessageDialog(this,m,"Error",JOptionPane.ERROR_MESSAGE); }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLayeredPane1 = new javax.swing.JLayeredPane();
        Cliente = new javax.swing.JPanel();
        EquipoListado = new javax.swing.JPanel();
        ID = new javax.swing.JLabel();
        Tipo = new javax.swing.JLabel();
        Marca = new javax.swing.JLabel();
        Modelo = new javax.swing.JLabel();
        Precio = new javax.swing.JLabel();
        Stock = new javax.swing.JLabel();
        txtEqID = new javax.swing.JTextField();
        txtEqMarca = new javax.swing.JTextField();
        txtEqModelo = new javax.swing.JTextField();
        txtEqPrecio = new javax.swing.JTextField();
        txtEqStock = new javax.swing.JTextField();
        btnEqBuscar = new javax.swing.JButton();
        txtEqBuscar = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        table2 = new javax.swing.JTable();
        jToolBar2 = new javax.swing.JToolBar();
        btnEqNuevo = new javax.swing.JButton();
        jSeparator6 = new javax.swing.JToolBar.Separator();
        btnEqGuardar = new javax.swing.JButton();
        jSeparator7 = new javax.swing.JToolBar.Separator();
        btnEqActualizar = new javax.swing.JButton();
        jSeparator8 = new javax.swing.JToolBar.Separator();
        btnEqEliminar = new javax.swing.JButton();
        jSeparator9 = new javax.swing.JToolBar.Separator();
        btnEqRefrescar = new javax.swing.JButton();
        txtEqEquipo = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        txtRut = new javax.swing.JTextField();
        txtDireccion = new javax.swing.JTextField();
        txtComuna = new javax.swing.JTextField();
        txtTelefono = new javax.swing.JTextField();
        txtCorreo = new javax.swing.JTextField();
        txtBuscar = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        jToolBar1 = new javax.swing.JToolBar();
        btnNuevo = new javax.swing.JButton();
        jSeparator5 = new javax.swing.JToolBar.Separator();
        btnGuardar = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        btnActualizar = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        btnEliminar = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        btnRefrescar = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        btnLimpiar = new javax.swing.JButton();
        btnBuscar = new javax.swing.JButton();

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Computec");

        Cliente.setLayout(new java.awt.BorderLayout());

        ID.setText("IdEq");

        Tipo.setText("Tipo");

        Marca.setText("Marca");

        Modelo.setText("Modelo");

        Precio.setText("Precio");

        Stock.setText("Stock");

        txtEqID.setEditable(false);

        btnEqBuscar.setText("Buscar");

        table2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(table2);

        jToolBar2.setRollover(true);

        btnEqNuevo.setText("Nuevo");
        jToolBar2.add(btnEqNuevo);
        jToolBar2.add(jSeparator6);

        btnEqGuardar.setText("Guardar");
        jToolBar2.add(btnEqGuardar);
        jToolBar2.add(jSeparator7);

        btnEqActualizar.setText("Actualizar");
        jToolBar2.add(btnEqActualizar);
        jToolBar2.add(jSeparator8);

        btnEqEliminar.setText("Eliminar");
        jToolBar2.add(btnEqEliminar);
        jToolBar2.add(jSeparator9);

        btnEqRefrescar.setText("Refrescar");
        jToolBar2.add(btnEqRefrescar);

        txtEqEquipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEqEquipoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout EquipoListadoLayout = new javax.swing.GroupLayout(EquipoListado);
        EquipoListado.setLayout(EquipoListadoLayout);
        EquipoListadoLayout.setHorizontalGroup(
            EquipoListadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(EquipoListadoLayout.createSequentialGroup()
                .addGroup(EquipoListadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(EquipoListadoLayout.createSequentialGroup()
                        .addGroup(EquipoListadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(EquipoListadoLayout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addComponent(ID, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, EquipoListadoLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(EquipoListadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(Tipo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(Marca, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(Modelo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(Precio, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(Stock, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(35, 35, 35)
                        .addGroup(EquipoListadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(EquipoListadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtEqID)
                                .addComponent(txtEqStock, javax.swing.GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE)
                                .addComponent(txtEqPrecio)
                                .addComponent(txtEqMarca, javax.swing.GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE)
                                .addComponent(txtEqModelo))
                            .addComponent(txtEqEquipo, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(156, 156, 156)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 416, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(EquipoListadoLayout.createSequentialGroup()
                        .addGap(170, 170, 170)
                        .addComponent(btnEqBuscar)
                        .addGap(18, 18, 18)
                        .addComponent(txtEqBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(EquipoListadoLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, 416, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(1176, Short.MAX_VALUE))
        );
        EquipoListadoLayout.setVerticalGroup(
            EquipoListadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(EquipoListadoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(EquipoListadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(EquipoListadoLayout.createSequentialGroup()
                        .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addGroup(EquipoListadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ID)
                            .addComponent(txtEqID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(EquipoListadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Tipo)
                            .addComponent(txtEqEquipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(EquipoListadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Marca)
                            .addComponent(txtEqMarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(EquipoListadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Modelo)
                            .addComponent(txtEqModelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(EquipoListadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Precio)
                            .addComponent(txtEqPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(EquipoListadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Stock)
                            .addComponent(txtEqStock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(EquipoListadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEqBuscar)
                    .addComponent(txtEqBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel1.setText("ID");

        txtId.setEditable(false);

        jLabel2.setText("Nombre");

        jLabel3.setText("Rut");

        jLabel4.setText("Direccion");

        jLabel5.setText("Comuna");

        jLabel6.setText("Telefono");

        jLabel7.setText("Correo");

        txtRut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRutActionPerformed(evt);
            }
        });

        txtBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarActionPerformed(evt);
            }
        });

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(table);

        jToolBar1.setRollover(true);

        btnNuevo.setText("Nuevo");
        btnNuevo.setFocusable(false);
        btnNuevo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnNuevo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnNuevo);
        jToolBar1.add(jSeparator5);

        btnGuardar.setText("Guardar");
        btnGuardar.setFocusable(false);
        btnGuardar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnGuardar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnGuardar);
        jToolBar1.add(jSeparator4);

        btnActualizar.setText("Actualizar");
        btnActualizar.setFocusable(false);
        btnActualizar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnActualizar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnActualizar);
        jToolBar1.add(jSeparator3);

        btnEliminar.setText("Eliminar");
        btnEliminar.setFocusable(false);
        btnEliminar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnEliminar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnEliminar);
        jToolBar1.add(jSeparator2);

        btnRefrescar.setText("Refrescar");
        btnRefrescar.setFocusable(false);
        btnRefrescar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRefrescar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnRefrescar);
        jToolBar1.add(jSeparator1);

        btnLimpiar.setText("Limpiar");
        btnLimpiar.setFocusable(false);
        btnLimpiar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnLimpiar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnLimpiar);

        btnBuscar.setText("Buscar");
        btnBuscar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnBuscar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel4))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtDireccion)
                                .addComponent(txtComuna)
                                .addComponent(txtTelefono)
                                .addComponent(txtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtRut, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnBuscar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 3, Short.MAX_VALUE)))
                .addGap(55, 55, 55)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 402, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 885, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnBuscar))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtRut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtComuna, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(21, 21, 21))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Cliente, javax.swing.GroupLayout.DEFAULT_SIZE, 458, Short.MAX_VALUE)
                .addGap(756, 756, 756))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(EquipoListado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(89, 89, 89))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Cliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(EquipoListado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(22, 22, 22))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtRutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRutActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRutActionPerformed

    private void txtBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarActionPerformed

    private void txtEqEquipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEqEquipoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEqEquipoActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Proyecto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Proyecto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Proyecto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Proyecto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Proyecto().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Cliente;
    private javax.swing.JPanel EquipoListado;
    private javax.swing.JLabel ID;
    private javax.swing.JLabel Marca;
    private javax.swing.JLabel Modelo;
    private javax.swing.JLabel Precio;
    private javax.swing.JLabel Stock;
    private javax.swing.JLabel Tipo;
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnEqActualizar;
    private javax.swing.JButton btnEqBuscar;
    private javax.swing.JButton btnEqEliminar;
    private javax.swing.JButton btnEqGuardar;
    private javax.swing.JButton btnEqNuevo;
    private javax.swing.JButton btnEqRefrescar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnRefrescar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JToolBar.Separator jSeparator6;
    private javax.swing.JToolBar.Separator jSeparator7;
    private javax.swing.JToolBar.Separator jSeparator8;
    private javax.swing.JToolBar.Separator jSeparator9;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JTable table;
    private javax.swing.JTable table2;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtComuna;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtEqBuscar;
    private javax.swing.JTextField txtEqEquipo;
    private javax.swing.JTextField txtEqID;
    private javax.swing.JTextField txtEqMarca;
    private javax.swing.JTextField txtEqModelo;
    private javax.swing.JTextField txtEqPrecio;
    private javax.swing.JTextField txtEqStock;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtRut;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
