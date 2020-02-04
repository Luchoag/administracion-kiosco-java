import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.mysql.cj.MessageBuilder;

import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.Component;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.event.ItemListener;
import java.io.LineNumberInputStream;
import java.awt.event.ItemEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AdminGUI {

	private JFrame frmAdministracinDeKiosco;
	private JTextField txtIdProducto;
	private JTextField txtNombreProducto;
	private JTextField txtPrecioProducto;
	private JTable tblProductos;
	private JTextField txtFecha;
	private JTextField txtVendido;
	private JTextField txtIngresado;
	private JTable tblVentas;
	private JTextField txtIdProveedor;
	private JTextField txtNombreProveedor;
	private JTextField txtCUIL;
	private JTextField txtTelefono;
	private JTextField txtDomicilio;
	private JTable tblProveedores;
	private JTable tblGeneral;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminGUI window = new AdminGUI();
					window.frmAdministracinDeKiosco.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AdminGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		SQLQueries sql2 = new SQLQueries();	
		
		frmAdministracinDeKiosco = new JFrame();
		frmAdministracinDeKiosco.setTitle("Administración de Kiosco");
		frmAdministracinDeKiosco.setBounds(100, 100, 607, 698);
		frmAdministracinDeKiosco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frmAdministracinDeKiosco.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		JPanel pnlInicio = new JPanel();
		tabbedPane.addTab("Inicio", null, pnlInicio, null);
		pnlInicio.setLayout(null);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(12, 75, 560, 425);
		pnlInicio.add(scrollPane_3);
		
		tblGeneral = new JTable();
		String[] columnNamesInicio = {"ID Producto", "Nombre Producto", "Ventas", "Facturado", "Proveedor"};
		DefaultTableModel tableModelInicio = new DefaultTableModel(columnNamesInicio, 0);		
		scrollPane_3.setViewportView(tblGeneral);
		
		JLabel lblElijaUnaFecha = new JLabel("Elija una fecha:");
		lblElijaUnaFecha.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblElijaUnaFecha.setBounds(12, 13, 132, 30);
		pnlInicio.add(lblElijaUnaFecha);
		
		JComboBox cmbFechasInicio = new JComboBox();
		cmbFechasInicio.setFont(new Font("Tahoma", Font.PLAIN, 16));
		cmbFechasInicio.setBounds(140, 17, 132, 22);
		pnlInicio.add(cmbFechasInicio);
		ArrayList<String> listaFechasInicio = new ArrayList <String>();
		sql2.cargarFechas(listaFechasInicio);
		for (int i = 0; i<listaFechasInicio.size(); i++) cmbFechasInicio.addItem(listaFechasInicio.get(i));
		
		Object [] dataInicio = new Object [5];
		sql2.cargarDatosIniciales(dataInicio, tableModelInicio, cmbFechasInicio.getSelectedItem().toString());
		tblGeneral.setModel(tableModelInicio);
				
		JLabel lblTotalFacturado = new JLabel("Total Facturado:");
		lblTotalFacturado.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblTotalFacturado.setBounds(286, 21, 116, 16);
		pnlInicio.add(lblTotalFacturado);
		
		JLabel lblFacturado = new JLabel("");
		lblFacturado.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblFacturado.setBounds(414, 21, 116, 16);
		pnlInicio.add(lblFacturado);
		
		Float suma=0.0f;
		for (int i=0; i<tblGeneral.getRowCount(); i++) {
				suma += (Float)tblGeneral.getModel().getValueAt(i, 3);
		}
		lblFacturado.setText("$" + suma);
	
		cmbFechasInicio.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				Object [] dataInicio = new Object [5];
				sql2.cargarDatosIniciales(dataInicio, tableModelInicio, cmbFechasInicio.getSelectedItem().toString());
				tblGeneral.setModel(tableModelInicio);
				Float suma=0.0f;
				for (int i=0; i<tblGeneral.getRowCount(); i++) {
						suma += (Float)tblGeneral.getModel().getValueAt(i, 3);
				}
				lblFacturado.setText("$" + suma);
			}
		});
		
		JPanel pnlProductos = new JPanel();
		tabbedPane.addTab("Productos", null, pnlProductos, null);
		pnlProductos.setLayout(null);
		
		JLabel lblElijaUnaAccin = new JLabel("Elija una acción:");
		lblElijaUnaAccin.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblElijaUnaAccin.setBounds(12, 29, 125, 16);
		pnlProductos.add(lblElijaUnaAccin);
		
		JRadioButton rbtnInsertarProducto = new JRadioButton("Insertar");

		rbtnInsertarProducto.setSelected(true);
		rbtnInsertarProducto.setFont(new Font("Tahoma", Font.PLAIN, 16));
		rbtnInsertarProducto.setBounds(137, 26, 130, 25);
		pnlProductos.add(rbtnInsertarProducto);
		
		JRadioButton rbtnModificarProducto = new JRadioButton("Modificar");
		rbtnModificarProducto.setFont(new Font("Tahoma", Font.PLAIN, 16));
		rbtnModificarProducto.setBounds(271, 26, 127, 25);
		pnlProductos.add(rbtnModificarProducto);
		
		JRadioButton rbtnEliminarProducto = new JRadioButton("Eliminar");
		rbtnEliminarProducto.setFont(new Font("Tahoma", Font.PLAIN, 16));
		rbtnEliminarProducto.setBounds(409, 26, 127, 25);
		pnlProductos.add(rbtnEliminarProducto);
		
		ButtonGroup gProductos = new ButtonGroup();
		gProductos.add(rbtnInsertarProducto);
		gProductos.add(rbtnModificarProducto);
		gProductos.add(rbtnEliminarProducto);
		
		JLabel lblIdProducto = new JLabel("ID Producto:");
		lblIdProducto.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblIdProducto.setBounds(12, 121, 109, 25);
		pnlProductos.add(lblIdProducto);
		
		txtIdProducto = new JTextField();
		txtIdProducto.setBounds(128, 123, 116, 22);
		pnlProductos.add(txtIdProducto);
		txtIdProducto.setColumns(10);
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNombre.setBounds(12, 159, 83, 25);
		pnlProductos.add(lblNombre);
		
		txtNombreProducto = new JTextField();
		txtNombreProducto.setBounds(128, 161, 116, 22);
		pnlProductos.add(txtNombreProducto);
		txtNombreProducto.setColumns(10);
		
		JLabel lblPrecioProducto = new JLabel("Precio:");
		lblPrecioProducto.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblPrecioProducto.setBounds(304, 121, 109, 25);
		pnlProductos.add(lblPrecioProducto);
		
		txtPrecioProducto = new JTextField();
		txtPrecioProducto.setColumns(10);
		txtPrecioProducto.setBounds(420, 123, 116, 22);
		pnlProductos.add(txtPrecioProducto);
		
		JLabel lblIdProveedor = new JLabel("ID Proveedor:");
		lblIdProveedor.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblIdProveedor.setBounds(304, 164, 109, 20);
		pnlProductos.add(lblIdProveedor);
		
		JComboBox cmbIdProveedor = new JComboBox();
		cmbIdProveedor.setBounds(420, 164, 116, 22);
		pnlProductos.add(cmbIdProveedor);
		
		String[] columnNamesProductos = {"ID Producto", "Nombre Producto", "Precio", "Id Proveedor"};
		DefaultTableModel tableModel = new DefaultTableModel(columnNamesProductos, 0);
		
		sql2.cargarComboBoxProveedor(cmbIdProveedor);
		Object [] data = new Object [4];
		sql2.cargarDatosProductos(data, "", tableModel);
		
		tblProductos = new JTable();
		tblProductos.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tblProductos.setBackground(Color.WHITE);
		
		
		tblProductos.setModel(tableModel);
		tblProductos.getColumnModel().getColumn(0).setPreferredWidth(84);
		tblProductos.getColumnModel().getColumn(1).setPreferredWidth(107);
		tblProductos.getColumnModel().getColumn(2).setPreferredWidth(100);
		tblProductos.getColumnModel().getColumn(3).setPreferredWidth(95);
		
		JScrollPane scrollPane = new JScrollPane(tblProductos);
		scrollPane.setBounds(12, 261, 549, 237);
		pnlProductos.add(scrollPane);
		tblProductos.setBounds(539, 478, -512, -208);
		
		JButton btnImprimirReporte = new JButton("Imprimir Reporte");
		btnImprimirReporte.setBounds(420, 511, 141, 25);
		pnlProductos.add(btnImprimirReporte);
		
		JButton btnAplicarProducto = new JButton("Aplicar");
		btnAplicarProducto.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnAplicarProducto.setBounds(420, 212, 116, 36);
		pnlProductos.add(btnAplicarProducto);
		
		JComboBox<String> cmbProductos = new JComboBox<String>();
		cmbProductos.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (rbtnModificarProducto.isSelected() || rbtnEliminarProducto.isSelected()) {
					Object [] data = new Object[4];
					SQLQueries sql1 = new SQLQueries();
					String condicion = " WHERE Nombre_Producto= '" + cmbProductos.getSelectedItem().toString() + "'";
					sql1.cargarDatosProductos(data,condicion);
					txtIdProducto.setText(data[0].toString());
					txtNombreProducto.setText(data[1].toString());
					txtPrecioProducto.setText(data[2].toString());
					cmbIdProveedor.setSelectedIndex(0);
					for (int i=0; i<cmbIdProveedor.getItemCount(); i++) {
						if (data[3]==cmbIdProveedor.getItemAt(i)) cmbIdProveedor.setSelectedItem(data[3]);
					}
				}
			}
		});
		cmbProductos.setEnabled(false);
		cmbProductos.setFont(new Font("Tahoma", Font.PLAIN, 16));
		cmbProductos.setBounds(128, 71, 233, 22);
		pnlProductos.add(cmbProductos);
		ArrayList<String> listaNombresProductos = new ArrayList<String>();
		sql2.cargarNombresProductos(listaNombresProductos);
		for (int i=0; i<listaNombresProductos.size(); i++) cmbProductos.addItem(listaNombresProductos.get(i));
		
		JLabel lblProductos = new JLabel("Productos:");
		lblProductos.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblProductos.setBounds(12, 73, 83, 16);
		pnlProductos.add(lblProductos);
		
		JPanel pnlVentas = new JPanel();
		tabbedPane.addTab("Ventas", null, pnlVentas, null);
		pnlVentas.setLayout(null);
		
		JPanel panel_4 = new JPanel();
		panel_4.setLayout(null);
		panel_4.setBounds(0, 0, 584, 549);
		pnlVentas.add(panel_4);
		
		JLabel label = new JLabel("Elija una acción:");
		label.setFont(new Font("Tahoma", Font.PLAIN, 16));
		label.setBounds(12, 29, 125, 16);
		panel_4.add(label);
		
		JRadioButton rbtnInsertarVenta = new JRadioButton("Insertar");
		rbtnInsertarVenta.setSelected(true);
		rbtnInsertarVenta.setFont(new Font("Tahoma", Font.PLAIN, 16));
		rbtnInsertarVenta.setBounds(137, 26, 130, 25);
		panel_4.add(rbtnInsertarVenta);
		
		JRadioButton rbtnModificarVenta = new JRadioButton("Modificar");
		rbtnModificarVenta.setFont(new Font("Tahoma", Font.PLAIN, 16));
		rbtnModificarVenta.setBounds(271, 26, 127, 25);
		panel_4.add(rbtnModificarVenta);
		
		JRadioButton rbtnEliminarVenta = new JRadioButton("Eliminar");
		rbtnEliminarVenta.setFont(new Font("Tahoma", Font.PLAIN, 16));
		rbtnEliminarVenta.setBounds(409, 26, 127, 25);
		panel_4.add(rbtnEliminarVenta);
		
		ButtonGroup gVentas = new ButtonGroup();
		gVentas.add(rbtnInsertarVenta);
		gVentas.add(rbtnModificarVenta);
		gVentas.add(rbtnEliminarVenta);
		
		JLabel lblFecha = new JLabel("Fecha:");
		lblFecha.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblFecha.setBounds(12, 117, 109, 25);
		panel_4.add(lblFecha);
		
		txtFecha = new JTextField();
		txtFecha.setColumns(10);
		txtFecha.setBounds(128, 119, 116, 22);
		panel_4.add(txtFecha);
		
		JLabel lblIdProducto_1 = new JLabel("ID Producto:");
		lblIdProducto_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblIdProducto_1.setBounds(12, 159, 109, 25);
		panel_4.add(lblIdProducto_1);
		
		txtVendido = new JTextField();
		txtVendido.setColumns(10);
		txtVendido.setBounds(420, 161, 116, 22);
		panel_4.add(txtVendido);
		
		JLabel lblInresado = new JLabel("Ingresado:");
		lblInresado.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblInresado.setBounds(304, 117, 109, 25);
		panel_4.add(lblInresado);
		
		txtIngresado = new JTextField();
		txtIngresado.setColumns(10);
		txtIngresado.setBounds(420, 119, 116, 22);
		panel_4.add(txtIngresado);
		
		JLabel lblVendido = new JLabel("Vendido:");
		lblVendido.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblVendido.setBounds(304, 164, 109, 20);
		panel_4.add(lblVendido);
		
		JComboBox cmbIdProductos = new JComboBox();
		cmbIdProductos.setBounds(128, 161, 116, 22);
		panel_4.add(cmbIdProductos);
		
		JScrollPane scrollPane_1 = new JScrollPane((Component) null);
		scrollPane_1.setBounds(12, 261, 549, 237);
		panel_4.add(scrollPane_1);
		
		tblVentas = new JTable();
		tblVentas.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		String[] columnNamesVentas = {"Fecha", "ID Producto", "Ingresado", "Vendido"};
		DefaultTableModel tableModel2 = new DefaultTableModel(columnNamesVentas, 0);
			
		ArrayList<Integer> listaIds = new ArrayList<Integer>();
		sql2.cargarIDsProductos(listaIds, "Productos", "");
		for (int i=0; i<listaIds.size(); i++) cmbIdProductos.addItem(listaIds.get(i));
		Object [] dataVentas = new Object[4];
		sql2.cargarDatosVentas(dataVentas, "", tableModel2);		
		
		tblVentas.setModel(tableModel2);
		tblVentas.getColumnModel().getColumn(0).setPreferredWidth(94);
		tblVentas.getColumnModel().getColumn(1).setPreferredWidth(104);
		tblVentas.getColumnModel().getColumn(2).setPreferredWidth(88);
		tblVentas.getColumnModel().getColumn(3).setPreferredWidth(95);
		scrollPane_1.setViewportView(tblVentas);
		
		JButton button = new JButton("Imprimir Reporte");
		button.setBounds(420, 514, 141, 25);
		panel_4.add(button);
		
		JButton btnAplicarVenta = new JButton("Aplicar");
		btnAplicarVenta.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnAplicarVenta.setBounds(420, 212, 116, 36);
		panel_4.add(btnAplicarVenta);
		
		JComboBox cmbFechas = new JComboBox();
		cmbFechas.setEnabled(false);
		cmbFechas.setFont(new Font("Tahoma", Font.PLAIN, 16));
		cmbFechas.setBounds(128, 75, 117, 22);
		panel_4.add(cmbFechas);
		ArrayList<String> listaFechas = new ArrayList<String>();
		sql2.cargarFechas(listaFechas);
		for (int i=0; i<listaFechas.size(); i++) cmbFechas.addItem(listaFechas.get(i));
		
		JComboBox cmbIDs = new JComboBox();
		cmbIDs.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (rbtnModificarVenta.isSelected() || rbtnEliminarVenta.isSelected()) {
					Object [] data = new Object[4];
					String condicion = " WHERE Fecha= '" + cmbFechas.getSelectedItem().toString() + "' AND idProducto = " + cmbIDs.getSelectedItem().toString();
					sql2.cargarDatosVentas(data,condicion);
					txtFecha.setText(data[0].toString());
					txtIngresado.setText(data[2].toString());
					txtVendido.setText(data[3].toString());
					for (int i=0; i<cmbIdProductos.getItemCount(); i++) {
						if (data[1]==cmbIdProductos.getItemAt(i)) cmbIdProductos.setSelectedItem(data[1]);
					}
				}

			}
		});
		cmbIDs.setEnabled(false);
		cmbIDs.setFont(new Font("Tahoma", Font.PLAIN, 16));
		cmbIDs.setBounds(419, 75, 117, 22);
		panel_4.add(cmbIDs);
		listaIds.clear();
		sql2.cargarIDsProductos(listaIds, "Ventas", " WHERE Fecha= '"  + cmbFechas.getSelectedItem().toString() + "'");
		for (int i=0; i<listaIds.size(); i++) cmbIDs.addItem(listaIds.get(i));
		
		
		cmbFechas.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (rbtnModificarVenta.isSelected() || rbtnEliminarVenta.isSelected()) {
					Object [] data = new Object[4];
					String condicion = " WHERE Fecha= '" + cmbFechas.getSelectedItem().toString() + "' AND idProducto= " + cmbIDs.getSelectedItem().toString();
					sql2.cargarDatosVentas(data,condicion);
					txtFecha.setText(data[0].toString());
					txtIngresado.setText(data[2].toString());
					txtVendido.setText(data[3].toString());
					for (int i=0; i<cmbIdProductos.getItemCount(); i++) {
						if (data[3]==cmbIdProductos.getItemAt(i)) cmbIdProductos.setSelectedItem(data[1]);
					}
				}
			}
		});
		
		JLabel lblFechas = new JLabel("Fechas:");
		lblFechas.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblFechas.setBounds(12, 74, 109, 25);
		panel_4.add(lblFechas);
		
		JLabel lblIdProductos = new JLabel("ID Productos:");
		lblIdProductos.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblIdProductos.setBounds(304, 74, 109, 25);
		panel_4.add(lblIdProductos);
		
		JPanel pnlProveedores = new JPanel();
		tabbedPane.addTab("Proveedores", null, pnlProveedores, null);
		pnlProveedores.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(0, 0, 584, 549);
		pnlProveedores.add(panel);
		
		JLabel label_1 = new JLabel("Elija una acción:");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		label_1.setBounds(12, 29, 125, 16);
		panel.add(label_1);
		
		JRadioButton rbtnInsertarProveedor = new JRadioButton("Insertar");
		rbtnInsertarProveedor.setSelected(true);
		rbtnInsertarProveedor.setFont(new Font("Tahoma", Font.PLAIN, 16));
		rbtnInsertarProveedor.setBounds(137, 26, 130, 25);
		panel.add(rbtnInsertarProveedor);
		
		JRadioButton rbtnModificarProveedor = new JRadioButton("Modificar");
		rbtnModificarProveedor.setFont(new Font("Tahoma", Font.PLAIN, 16));
		rbtnModificarProveedor.setBounds(271, 26, 127, 25);
		panel.add(rbtnModificarProveedor);
		
		JRadioButton rbtnEliminarProveedor = new JRadioButton("Eliminar");
		rbtnEliminarProveedor.setFont(new Font("Tahoma", Font.PLAIN, 16));
		rbtnEliminarProveedor.setBounds(409, 26, 127, 25);
		panel.add(rbtnEliminarProveedor);
		
		ButtonGroup gProveedores = new ButtonGroup();
		gProveedores.add(rbtnInsertarProveedor);
		gProveedores.add(rbtnModificarProveedor);
		gProveedores.add(rbtnEliminarProveedor);		
		
		JLabel lblTelfono = new JLabel("Teléfono:");
		lblTelfono.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblTelfono.setBounds(304, 159, 109, 25);
		panel.add(lblTelfono);
		
		txtIdProveedor = new JTextField();
		txtIdProveedor.setColumns(10);
		txtIdProveedor.setBounds(128, 107, 116, 22);
		panel.add(txtIdProveedor);
		
		JLabel lblNombreProveedor = new JLabel("Nombre:");
		lblNombreProveedor.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNombreProveedor.setBounds(12, 159, 83, 25);
		panel.add(lblNombreProveedor);
		
		txtNombreProveedor = new JTextField();
		txtNombreProveedor.setColumns(10);
		txtNombreProveedor.setBounds(128, 161, 116, 22);
		panel.add(txtNombreProveedor);
		
		JLabel lblCuil = new JLabel("CUIL:");
		lblCuil.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblCuil.setBounds(304, 89, 109, 25);
		panel.add(lblCuil);
		
		txtCUIL = new JTextField();
		txtCUIL.setColumns(10);
		txtCUIL.setBounds(420, 91, 116, 22);
		panel.add(txtCUIL);
		
		JLabel lblIdProveedor2 = new JLabel("ID Proveedor:");
		lblIdProveedor2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblIdProveedor2.setBounds(12, 107, 109, 20);
		panel.add(lblIdProveedor2);
		
		JScrollPane scrollPane_2 = new JScrollPane((Component) null);
		scrollPane_2.setBounds(12, 261, 549, 237);
		panel.add(scrollPane_2);
		
		String[] columnNamesProveedores = {"ID Proveedor", "Nombre Producto", "CUIL", "Domicilio", "Teléfono"};
		DefaultTableModel tableModel3 = new DefaultTableModel(columnNamesProveedores, 0);
		
		tblProveedores = new JTable();
		tblProveedores.setFont(new Font("Tahoma", Font.PLAIN, 14));
		scrollPane_2.setViewportView(tblProveedores);
		tblProveedores.setModel(tableModel3);
		Object [] dataProveedores = new Object [5];
		sql2.cargarDatosProveedores(dataProveedores, "", tableModel3);
		
		JButton button_1 = new JButton("Imprimir Reporte");
		button_1.setBounds(420, 506, 141, 25);
		panel.add(button_1);
		
		txtTelefono = new JTextField();
		txtTelefono.setBounds(420, 161, 116, 22);
		panel.add(txtTelefono);
		txtTelefono.setColumns(10);
		
		JLabel lblDomicilio = new JLabel("Domicilio:");
		lblDomicilio.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblDomicilio.setBounds(304, 127, 109, 25);
		panel.add(lblDomicilio);
		
		txtDomicilio = new JTextField();
		txtDomicilio.setColumns(10);
		txtDomicilio.setBounds(420, 126, 116, 22);
		panel.add(txtDomicilio);
		
		JButton btnAplicarProveedor = new JButton("Aplicar");
		btnAplicarProveedor.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnAplicarProveedor.setBounds(420, 212, 116, 36);
		panel.add(btnAplicarProveedor);
		
		JLabel lblProveedores = new JLabel("Proveedores");
		lblProveedores.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblProveedores.setBounds(12, 58, 109, 20);
		panel.add(lblProveedores);
		
		JComboBox cmbProveedores = new JComboBox();
		cmbProveedores.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (rbtnModificarProveedor.isSelected() || rbtnEliminarProveedor.isSelected()) {
					Object [] data = new Object[5];
					String condicion = " WHERE Nombre_Proveedor= '" + cmbProveedores.getSelectedItem().toString() + "'";
					sql2.cargarDatosProveedores(data,condicion);
					txtIdProveedor.setText(data[0].toString());
					txtNombreProveedor.setText(data[1].toString());
					txtCUIL.setText(data[2].toString());
					txtDomicilio.setText(data[3].toString());
					txtTelefono.setText(data[4].toString());					
				}
			}
		});
		cmbProveedores.setBounds(137, 58, 222, 22);
		panel.add(cmbProveedores);
		ArrayList<String> listaNombresProveedores = new ArrayList<String>();
		sql2.cargarNombresProveedores(listaNombresProveedores);
		for (int i=0; i<listaNombresProveedores.size(); i++) cmbProveedores.addItem(listaNombresProveedores.get(i));
		
		JPanel pnlTop = new JPanel();
		frmAdministracinDeKiosco.getContentPane().add(pnlTop, BorderLayout.NORTH);
		
		JLabel lblAdministracinDeKioco = new JLabel("Administración de Kiosco");
		lblAdministracinDeKioco.setFont(new Font("Tahoma", Font.PLAIN, 20));
		pnlTop.add(lblAdministracinDeKioco);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("C:\\Mis documentos\\Luciano\\Facultad\\IRSO\\2do Año\\Integración de Programación\\Unidad 3\\Kiosco2.png"));
		pnlTop.add(lblNewLabel);
		
		rbtnInsertarProducto.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (arg0.getStateChange() == ItemEvent.SELECTED)
					cmbProductos.setEnabled(false);
					txtIdProducto.setEditable(true);
					txtNombreProducto.setEditable(true);
					txtPrecioProducto.setEditable(true);
					cmbIdProveedor.setEnabled(true);
					txtIdProducto.setText("");
					txtNombreProducto.setText("");
					txtPrecioProducto.setText("");
					cmbIdProveedor.setSelectedIndex(0);
				}
		});
		
		rbtnModificarProducto.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {			
					cmbProductos.setEnabled(true);
					txtIdProducto.setEditable(false);
					txtNombreProducto.setEditable(true);
					txtPrecioProducto.setEditable(true);
					cmbIdProveedor.setEnabled(true);
					Object [] data = new Object[4];
					SQLQueries sql1 = new SQLQueries();
					String condicion = " WHERE Nombre_Producto= '" + cmbProductos.getSelectedItem().toString() + "'";
					sql1.cargarDatosProductos(data,condicion);
					txtIdProducto.setText(data[0].toString());
					txtNombreProducto.setText(data[1].toString());
					txtPrecioProducto.setText(data[2].toString());
				}
			}
		});
		
		rbtnEliminarProducto.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {				
					cmbProductos.setEnabled(true);
					txtIdProducto.setEditable(false);
					txtNombreProducto.setEditable(false);
					txtPrecioProducto.setEditable(false);
					cmbIdProveedor.setEnabled(false);
				}
			}
		});
		
		rbtnInsertarVenta.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (arg0.getStateChange() == ItemEvent.SELECTED) {
					cmbFechas.setEnabled(false);
					cmbIDs.setEnabled(false);
				}
			}
		});
		
		rbtnModificarVenta.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED)
					cmbFechas.setEnabled(true);
					cmbIDs.setEnabled(true);
			}
		});
		
		rbtnEliminarVenta.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED)
					cmbFechas.setEnabled(true);
					cmbIDs.setEnabled(true);
			}
		});
		
		btnAplicarProducto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int idProducto = Integer.parseInt(txtIdProducto.getText());
				String nombreProducto = txtNombreProducto.getText();
				float precioProducto = Float.parseFloat(txtPrecioProducto.getText());
				int idProveedor = Integer.parseInt(cmbIdProveedor.getSelectedItem().toString());
				if (rbtnInsertarProducto.isSelected()){
					sql2.insertarProducto(idProducto, nombreProducto, precioProducto, idProveedor);
					listaNombresProductos.add(nombreProducto);
					cmbProductos.addItem(listaNombresProductos.get(listaNombresProductos.size()-1));
				}
				else if (rbtnModificarProducto.isSelected()) {
					sql2.modificarProducto(idProducto, "Nombre_Producto", nombreProducto);
					sql2.modificarProducto(idProducto, "Precio", String.valueOf(precioProducto));
					sql2.modificarProducto(idProducto, "idProveedor", String.valueOf(idProveedor));
					for (int i = 0; i<listaNombresProductos.size(); i++) {
						if (listaNombresProductos.get(i).toString().equals(cmbProductos.getSelectedItem().toString())) {
							listaNombresProductos.set(i, nombreProducto);
							cmbProductos.removeItemAt(i);
							cmbProductos.insertItemAt(nombreProducto, i);
							cmbProductos.setSelectedIndex(i);
						}
					}
					
					
				}
				else if (rbtnEliminarProducto.isSelected()) {
					int confirmar = JOptionPane.showConfirmDialog(null, "¿Está seguro de que desea eliminar el producto seleccionado?", "Eliminar Producto", JOptionPane.YES_NO_OPTION);
			        if (confirmar == JOptionPane.YES_OPTION) {
			        	sql2.eliminarProducto(idProducto);
			            JOptionPane.showMessageDialog(null, "Producto eliminado.");
						for (int i = 0; i<listaNombresProductos.size(); i++) {
							if (listaNombresProductos.get(i).toString().equals(cmbProductos.getSelectedItem().toString())) {
								listaNombresProductos.remove(i);
								cmbProductos.removeItemAt(i);
								cmbProductos.setSelectedIndex(i-1);
							}
						}
			          }
			          else {
			             JOptionPane.showMessageDialog(null, "Operación cancelada.");
			          }
				}
				sql2.cargarDatosProductos(data, "", tableModel);
				listaNombresProductos.clear();
				sql2.cargarNombresProductos(listaNombresProductos);
				sql2.cargarDatosIniciales(dataInicio, tableModelInicio, cmbFechasInicio.getSelectedItem().toString());
				tblGeneral.setModel(tableModelInicio);
			}
		});
		
		rbtnInsertarProveedor.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (arg0.getStateChange() == ItemEvent.SELECTED)
					cmbProveedores.setSelectedIndex(0);
					cmbProveedores.setEnabled(false);
					txtIdProveedor.setEditable(true);
					txtNombreProveedor.setEditable(true);
					txtCUIL.setEditable(true);
					txtDomicilio.setEditable(true);
					txtTelefono.setEditable(true);
					txtIdProveedor.setText("");
					txtNombreProveedor.setText("");
					txtCUIL.setText("");
					txtDomicilio.setText("");
					txtTelefono.setText("");
				}
		});

		 
		rbtnModificarProveedor.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {			
					cmbProveedores.setEnabled(true);
					txtIdProveedor.setEditable(false);
					txtNombreProveedor.setEditable(true);
					txtCUIL.setEditable(true);
					txtDomicilio.setEditable(true);
					txtTelefono.setEditable(true);
					String condicion = " WHERE Nombre_Proveedor= '" + cmbProveedores.getSelectedItem().toString() + "'";
					sql2.cargarDatosProveedores(dataProveedores,condicion);
					txtIdProveedor.setText(dataProveedores[0].toString());
					txtNombreProveedor.setText(dataProveedores[1].toString());
					txtCUIL.setText(dataProveedores[2].toString());
					txtDomicilio.setText(dataProveedores[3].toString());
					txtTelefono.setText(dataProveedores[4].toString());
				}
			}
		});
		
		rbtnEliminarProveedor.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {				
					cmbProveedores.setEnabled(true);
					txtIdProveedor.setEditable(false);
					txtNombreProveedor.setEditable(false);
					txtCUIL.setEditable(false);
					txtDomicilio.setEditable(false);
					txtTelefono.setEditable(false);
					String condicion = " WHERE Nombre_Proveedor= '" + cmbProveedores.getSelectedItem().toString() + "'";
					sql2.cargarDatosProveedores(dataProveedores,condicion);
					txtIdProveedor.setText(dataProveedores[0].toString());
					txtNombreProveedor.setText(dataProveedores[1].toString());
					txtCUIL.setText(dataProveedores[2].toString());
					txtDomicilio.setText(dataProveedores[3].toString());
					txtTelefono.setText(dataProveedores[4].toString());
				}
			}
		});
		
		btnAplicarProveedor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int idProveedor = Integer.parseInt(txtIdProveedor.getText());
				String nombreProveedor = txtNombreProveedor.getText();
				String cuilProveedor = txtCUIL.getText();
				String domicilioProveedor = txtDomicilio.getText();
				int telefonoProveedor = Integer.parseInt(txtTelefono.getText());
				if (rbtnInsertarProveedor.isSelected()){
					sql2.insertarProveedor(idProveedor, nombreProveedor, cuilProveedor, domicilioProveedor, telefonoProveedor);
					listaNombresProveedores.add(nombreProveedor);
					cmbProveedores.addItem(listaNombresProveedores.get(listaNombresProveedores.size()-1));
				}
				else if (rbtnModificarProveedor.isSelected()) {
					sql2.modificarProveedor(idProveedor, "Nombre_Proveedor", nombreProveedor);
					sql2.modificarProveedor(idProveedor, "CUIL", cuilProveedor);
					sql2.modificarProveedor(idProveedor, "Domicilio", domicilioProveedor);
					sql2.modificarProveedor(idProveedor, "Telefono", String.valueOf(telefonoProveedor));
					for (int i = 0; i<listaNombresProveedores.size(); i++) {
						if (listaNombresProveedores.get(i).toString().equals(cmbProveedores.getSelectedItem().toString())) {
							listaNombresProveedores.set(i, nombreProveedor);
							cmbProveedores.removeItemAt(i);
							cmbProveedores.insertItemAt(nombreProveedor, i);
							cmbProveedores.setSelectedIndex(i);
						}
					}
				}
				else if (rbtnEliminarProveedor.isSelected()) {
					int confirmar = JOptionPane.showConfirmDialog(null, "¿Está seguro de que desea eliminar el proveedor seleccionado?", "Eliminar Proveedor", JOptionPane.YES_NO_OPTION);
			        if (confirmar == JOptionPane.YES_OPTION) {
			        	sql2.eliminarProvedor(idProveedor);
			            JOptionPane.showMessageDialog(null, "Proveedor eliminado.");
			            for (int i = 0; i<listaNombresProveedores.size(); i++) {
							if (listaNombresProveedores.get(i).toString().equals(cmbProveedores.getSelectedItem().toString())) {
								listaNombresProveedores.remove(i);
								cmbProveedores.removeItemAt(i);
								cmbProveedores.setSelectedIndex(i-1);
							}
						}
			          }
			          else {
			             JOptionPane.showMessageDialog(null, "Operación cancelada.");
			          }
				}
				sql2.cargarDatosProveedores(dataProveedores, "", tableModel3);
				listaNombresProveedores.clear();
				sql2.cargarNombresProveedores(listaNombresProveedores);
				sql2.cargarDatosIniciales(dataInicio, tableModelInicio, cmbFechasInicio.getSelectedItem().toString());
				tblGeneral.setModel(tableModelInicio);
			}
		});
		
		
		rbtnInsertarVenta.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (arg0.getStateChange() == ItemEvent.SELECTED)
					cmbFechas.setSelectedIndex(0);
					cmbIDs.setSelectedIndex(0);
					cmbFechas.setEnabled(false);
					cmbIDs.setEnabled(false);
					cmbIdProductos.setEnabled(true);
					txtFecha.setEditable(true);
					txtIngresado.setEditable(true);
					txtVendido.setEditable(true);
					txtFecha.setText("");
					txtIngresado.setText("");
					txtVendido.setText("");
				}
		});

		 
		rbtnModificarVenta.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					cmbFechas.setSelectedIndex(0);
					cmbIDs.setSelectedIndex(0);
					cmbFechas.setEnabled(true);
					cmbIDs.setEnabled(true);
					cmbIdProductos.setEnabled(true);
					txtFecha.setEditable(true);
					txtIngresado.setEditable(true);
					txtVendido.setEditable(true);
					String condicion = " WHERE Fecha= '" + cmbFechas.getSelectedItem().toString() + "' AND idProducto = " + cmbIDs.getSelectedItem().toString();
					sql2.cargarDatosVentas(dataVentas,condicion);
					txtFecha.setText(dataVentas[0].toString());
					txtIngresado.setText(dataVentas[2].toString());
					txtVendido.setText(dataVentas[3].toString());
					
				}
			}
		});
		
		rbtnEliminarVenta.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					cmbFechas.setSelectedIndex(0);
					cmbIDs.setSelectedIndex(0);
					cmbFechas.setEnabled(true);
					cmbIDs.setEnabled(true);
					cmbIdProductos.setEnabled(false);
					txtFecha.setEditable(false);
					txtIngresado.setEditable(false);
					txtVendido.setEditable(false);
					String condicion = " WHERE Fecha= '" + cmbFechas.getSelectedItem().toString() + "' AND idProducto = " + cmbIDs.getSelectedItem().toString();
					sql2.cargarDatosVentas(dataVentas,condicion);
					txtFecha.setText(dataVentas[0].toString());
					txtIngresado.setText(dataVentas[2].toString());
					txtVendido.setText(dataVentas[3].toString());
				}
			}
		});
		
		btnAplicarVenta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String fechaNueva = txtFecha.getText();
				String idProductoNuevo = cmbIdProductos.getSelectedItem().toString();
				int ingresado = Integer.parseInt(txtIngresado.getText());
				int vendido = Integer.parseInt(txtVendido.getText());
				if (rbtnInsertarVenta.isSelected()){
					sql2.insertarVenta(fechaNueva, idProductoNuevo, ingresado, vendido);
					listaFechas.clear();
					sql2.cargarFechas(listaFechas);
					boolean existeFecha = false;
					for (int i=0; i<listaFechas.size(); i++) {
						if (listaFechas.get(i).toString().equals(cmbFechas.getItemAt(i))) existeFecha=true;
					}
					if (!existeFecha) cmbFechas.addItem(listaFechas.get(listaFechas.size()-1));
					
					listaIds.add(Integer.parseInt(idProductoNuevo));
					cmbIDs.addItem(listaIds.get(listaIds.size()-1));
					
				}
				else if (rbtnModificarVenta.isSelected()) {
					sql2.modificarVenta(cmbFechas.getSelectedItem().toString(), cmbIDs.getSelectedItem().toString(),  "Fecha", fechaNueva);
					sql2.modificarVenta(cmbFechas.getSelectedItem().toString(), cmbIDs.getSelectedItem().toString(),  "idProducto", idProductoNuevo);
					sql2.modificarVenta(cmbFechas.getSelectedItem().toString(), cmbIDs.getSelectedItem().toString(),  "Ingresado", String.valueOf(ingresado));
					sql2.modificarVenta(cmbFechas.getSelectedItem().toString(), cmbIDs.getSelectedItem().toString(),  "Vendido", String.valueOf(vendido));				
				}
				else if (rbtnEliminarVenta.isSelected()) {
					int confirmar = JOptionPane.showConfirmDialog(null, "¿Está seguro de que desea eliminar la venta seleccionada?", "Eliminar Venta", JOptionPane.YES_NO_OPTION);
			        if (confirmar == JOptionPane.YES_OPTION) {
			        	sql2.eliminarventa(cmbFechas.getSelectedItem().toString(), cmbIDs.getSelectedItem().toString());
			            JOptionPane.showMessageDialog(null, "Venta eliminada.");
			          }
			          else {
			             JOptionPane.showMessageDialog(null, "Operación cancelada.");
			          }
				}
				sql2.cargarDatosVentas(dataVentas, "", tableModel2);
				listaFechas.clear();
				sql2.cargarFechas(listaFechas);
				for (int i=0; i<listaFechas.size(); i++) cmbFechas.addItem(listaFechas.get(i));
				listaIds.clear();
				sql2.cargarIDsProductos(listaIds, "Ventas", " WHERE Fecha= '"  + cmbFechas.getSelectedItem().toString() + "'");
				for (int i=0; i<listaIds.size(); i++) cmbIDs.addItem(listaIds.get(i));
				sql2.cargarDatosIniciales(dataInicio, tableModelInicio, cmbFechasInicio.getSelectedItem().toString());
				tblGeneral.setModel(tableModelInicio);
			}
		});
		
	}
}
