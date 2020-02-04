import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class SQLQueries {


	private Connection con;
	String connectionString = "jdbc:mysql://localhost:3306/integracion?serverTimezone=UTC";

	public SQLQueries() {
		try {
			con=DriverManager.getConnection(  
			connectionString,"root","");
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}

public void crearConexion(String consulta) {
	try{  
		Class.forName("com.mysql.cj.jdbc.Driver");  
		verListas(consulta);
		}catch(Exception e){ System.out.println(e);} 
}

public void verListas(String tabla) {
	try{  
		Statement stmt=con.createStatement();  
		ResultSet rs=stmt.executeQuery("SELECT * FROM " + tabla);
		ResultSetMetaData rsmd = rs.getMetaData();
		
		System.out.print(rsmd.getColumnName(1));
		for (int i=2; i<=rsmd.getColumnCount(); i++) System.out.format("\t\t %10s", rsmd.getColumnName(i));
		System.out.println("");
		while(rs.next())  {
			for (int i=1; i<=rsmd.getColumnCount(); i ++){
				if (rsmd.getColumnTypeName(i).equals("VARCHAR")) System.out.format("%20s \t\t", rs.getString(i));
				else if (rsmd.getColumnTypeName(i).equals("INT")) System.out.format("%5s \t\t", rs.getInt(i));
				else if (rsmd.getColumnTypeName(i).equals("FLOAT")) System.out.format("%5s \t\t", rs.getFloat(i));
				else if (rsmd.getColumnTypeName(i).equals("DATE")) System.out.format("%5s \t\t", rs.getDate(i));
			}
			System.out.println("");

		}

		con.close();
		}catch(Exception e){ System.out.println(e);} 

	}

	public void cargarComboBoxProveedor (JComboBox<Integer> cmbIdProveedor) {
		try{  
			con=DriverManager.getConnection(connectionString,"root","");
			Statement stmt=con.createStatement();  
			ResultSet rs=stmt.executeQuery("SELECT idProveedor FROM Proveedores");
			
			while(rs.next())  {
			    int idProveedor = rs.getInt(1);
			    cmbIdProveedor.addItem(idProveedor);
			}

			con.close();
			}catch(Exception e){ System.out.println(e);} 
	}

	public void cargarDatosProductos (Object[] data, String condicion, DefaultTableModel tableModel) {
		try{  
			con=DriverManager.getConnection(connectionString,"root","");
			Statement stmt=con.createStatement(); 
			String consultaPrincipal = "SELECT * FROM Productos";
			ResultSet rs=stmt.executeQuery(consultaPrincipal + condicion);
			if (tableModel.getRowCount() > 0) {
			    for (int i = tableModel.getRowCount() - 1; i > -1; i--) {
			        tableModel.removeRow(i);
			    }
			}
			while(rs.next())  {
			    data[0] = rs.getInt(1);
			    data[1] = rs.getString(2);
			    data[2] = rs.getFloat(3);
			    data[3] = rs.getInt(4);
			    tableModel.addRow(data);
			}

			con.close();
			}catch(Exception e){ System.out.println(e);}
	}
	
	public void cargarDatosProductos (Object[] data, String condicion) {
		DefaultTableModel tempModel = new DefaultTableModel();
		cargarDatosProductos(data, condicion, tempModel);
	}
	
	public void cargarIDsProductos (ArrayList<Integer> listaIds, String tabla, String condicion) {
		try{  
			con=DriverManager.getConnection(connectionString,"root","");
			Statement stmt=con.createStatement();  
			String consultaPrincipal="SELECT DISTINCT idProducto FROM " + tabla;
			ResultSet rs=stmt.executeQuery(consultaPrincipal + condicion + " ORDER BY idProducto ASC");
			
			while(rs.next())  {
			    int idProducto = rs.getInt(1);
			    listaIds.add(idProducto);
			}

			con.close();
			}catch(Exception e){ System.out.println(e);} 
	}
	
	public void cargarNombresProductos (ArrayList<String> listaNombres) {
		try{  
			con=DriverManager.getConnection(connectionString,"root","");
			Statement stmt=con.createStatement();  
			ResultSet rs=stmt.executeQuery("SELECT Nombre_Producto FROM Productos ORDER BY idProducto ASC");
			
			while(rs.next())  {
			    String nombreProducto = rs.getString(1);
			    listaNombres.add(nombreProducto);
			}

			con.close();
			}catch(Exception e){ System.out.println(e);} 
	}
	
	public void cargarNombresProveedores (ArrayList<String> listaNombres) {
		try{  
			con=DriverManager.getConnection(connectionString,"root","");
			Statement stmt=con.createStatement();  
			ResultSet rs=stmt.executeQuery("SELECT Nombre_Proveedor FROM Proveedores ORDER BY idProveedor ASC");
			
			while(rs.next())  {
			    String nombreProveedor = rs.getString(1);
			    listaNombres.add(nombreProveedor);
			}

			con.close();
			}catch(Exception e){ System.out.println(e);} 
	}	

	public void cargarFechas(ArrayList<String> listaFechas) {
		try{  
			con=DriverManager.getConnection(connectionString,"root","");
			Statement stmt=con.createStatement();  
			ResultSet rs=stmt.executeQuery("SELECT DISTINCT Fecha FROM Ventas order by Fecha ASC");
			
			while(rs.next())  {
			    String fecha = rs.getString(1);
			    listaFechas.add(fecha);
			}

			con.close();
			}catch(Exception e){ System.out.println(e);}
	}	
	
	public void cargarDatosVentas (Object[] data, String condicion, DefaultTableModel tableModel) {
		try{  
			con=DriverManager.getConnection(connectionString,"root","");
			Statement stmt=con.createStatement();  
			String consultaPrincipal ="SELECT * FROM Ventas ";
			ResultSet rs=stmt.executeQuery(consultaPrincipal + condicion);
			
			if (tableModel.getRowCount() > 0) {
			    for (int i = tableModel.getRowCount() - 1; i > -1; i--) {
			        tableModel.removeRow(i);
			    }
			}
			
			while(rs.next())  {
			    data[0] = rs.getString(1);
			    data[1] = rs.getInt(2);
			    data[2] = rs.getInt(3);
			    data[3] = rs.getInt(4);
			    tableModel.addRow(data);
			}
			con.close();
			}catch(Exception e){ System.out.println(e);} 		
	}	
	
	public void cargarDatosVentas (Object[] data, String condicion) {
		DefaultTableModel tempModel = new DefaultTableModel();
		cargarDatosVentas(data, condicion, tempModel);
	}
	
	public void cargarDatosProveedores (Object[] data, String condicion, DefaultTableModel tableModel) {
		try{  
			con=DriverManager.getConnection(connectionString,"root","");
			Statement stmt=con.createStatement();
			String consultaPrincipal ="SELECT * FROM Proveedores";
			String condicionOpcional = condicion;
			ResultSet rs=stmt.executeQuery(consultaPrincipal + condicion);
			
			if (tableModel.getRowCount() > 0) {
			    for (int i = tableModel.getRowCount() - 1; i > -1; i--) {
			        tableModel.removeRow(i);
			    }
			}
			
			while(rs.next())  {
			    data[0] = rs.getInt(1);
			    data[1] = rs.getString(2);
			    data[2] = rs.getString(3);
			    data[3] = rs.getString(4);
			    data[4] = rs.getInt(5);
			    tableModel.addRow(data);
			}
			con.close();
			}catch(Exception e){ System.out.println(e);} 		
				
	}	
	
	public void cargarDatosProveedores (Object[] data, String condicion) {
		DefaultTableModel tempModel = new DefaultTableModel();
		cargarDatosProveedores(data, condicion, tempModel);
	}
	
	public void insertarProducto(int idProducto, String nombre, float precio, int idProveedor) {
		try{  
			con=DriverManager.getConnection(connectionString,"root","");
			Statement stmt=con.createStatement();  
		    stmt.executeUpdate("INSERT INTO Productos (idProducto, Nombre_Producto, Precio, idProveedor) "
		              + "VALUES (" + idProducto + ", '" + nombre + "', " + precio + ", " + idProveedor + ")");
			con.close();
			}catch(Exception e){ System.out.println(e);}		
	}
	
	public void modificarProducto(int idProducto, String columna, String atributo) {
		try{  
			con=DriverManager.getConnection(connectionString,"root","");
			Statement stmt=con.createStatement();  
		    stmt.executeUpdate("UPDATE Productos SET " + columna + "= '" + atributo + "' WHERE idProducto=" + idProducto);
			con.close();
			}catch(Exception e){ System.out.println(e);}		
	}

	public void eliminarProducto(int idProducto) {
		try{  
			con=DriverManager.getConnection(connectionString,"root","");
			Statement stmt=con.createStatement();  
		    stmt.executeUpdate("DELETE FROM Productos WHERE idProducto=" + idProducto);
			con.close();
			}catch(Exception e){ System.out.println(e);}	
	}

	public void insertarProveedor(int idProveedor, String nombreProveedor, String cuilProveedor,
			String domicilioProveedor, int telefonoProveedor) {
		try{  
			con=DriverManager.getConnection(connectionString,"root","");
			Statement stmt=con.createStatement();  
		    stmt.executeUpdate("INSERT INTO Proveedores (idProveedor, Nombre_Proveedor, CUIL, Domicilio, Telefono) "
		              + "VALUES (" + idProveedor + ", '" + nombreProveedor + "', '" + cuilProveedor + "', '" + domicilioProveedor + "', " + telefonoProveedor + ")");
			con.close();
			}catch(Exception e){ System.out.println(e);}	
		
	}

	public void modificarProveedor(int idProveedor, String columna, String atributo) {
		try{  
			con=DriverManager.getConnection(connectionString,"root","");
			Statement stmt=con.createStatement();  
		    stmt.executeUpdate("UPDATE Proveedores SET " + columna + "= '" + atributo + "' WHERE idProveedor=" + idProveedor);
			con.close();
			}catch(Exception e){ System.out.println(e);}	
	}

	public void eliminarProvedor(int idProveedor) {
		try{  
			con=DriverManager.getConnection(connectionString,"root","");
			Statement stmt=con.createStatement();  
		    stmt.executeUpdate("DELETE FROM Proveedores WHERE idProveedor=" + idProveedor);
			con.close();
			}catch(Exception e){ System.out.println(e);}	
	}

	public void insertarVenta(String fecha, String idProducto, int ingresado, int vendido) {
		try{  
			con=DriverManager.getConnection(connectionString,"root","");
			Statement stmt=con.createStatement();  
		    stmt.executeUpdate("INSERT INTO Ventas (Fecha, idProducto, Ingresado, Vendido) "
		              + "VALUES ('" + fecha + "', " + idProducto + ", " + ingresado + ", " + vendido + ")");
			con.close();
			}catch(Exception e){ System.out.println(e);}	
	}

	public void modificarVenta(String fecha, String idProducto, String columna, String atributo) {
		try{  
			con=DriverManager.getConnection(connectionString,"root","");
			Statement stmt=con.createStatement();  
		    stmt.executeUpdate("UPDATE Ventas SET " + columna + "= '" + atributo + "' WHERE Fecha='" + fecha + "' AND idProducto = " + idProducto);
			con.close();
			}catch(Exception e){ System.out.println(e);}
	}

	public void eliminarventa(String fecha, String idProducto) {
		try{  
			con=DriverManager.getConnection(connectionString,"root","");
			Statement stmt=con.createStatement();  
		    stmt.executeUpdate("DELETE FROM Ventas WHERE Fecha='" + fecha + "' AND idProducto = " + idProducto);
			con.close();
			}catch(Exception e){ System.out.println(e);}	
		}

	public void cargarDatosIniciales(Object[] dataInicio, DefaultTableModel tableModelInicio, String fecha) {
		
		try{  
			con=DriverManager.getConnection(connectionString,"root","");
			Statement stmt=con.createStatement(); 
			String consultaPrincipal = "SELECT Productos.idProducto, Productos.Nombre_Producto, Productos.Precio, "
					+ "Ventas.vendido*Productos.Precio AS Venta,Proveedores.Nombre_Proveedor"
					+ " FROM Productos"
					+ " INNER JOIN Proveedores ON Productos.idProveedor=Proveedores.idProveedor"
					+ " LEFT JOIN Ventas ON Productos.idProducto=Ventas.idProducto WHERE Fecha= '" + fecha + "' ORDER BY idProducto ASC";
			ResultSet rs=stmt.executeQuery(consultaPrincipal);
			if (tableModelInicio.getRowCount() > 0) {
			    for (int i = tableModelInicio.getRowCount() - 1; i > -1; i--) {
			        tableModelInicio.removeRow(i);
			    }
			}
			while(rs.next())  {
			    dataInicio[0] = rs.getInt(1);
			    dataInicio[1] = rs.getString(2);
			    dataInicio[2] = rs.getFloat(3);
			    dataInicio[3] = rs.getFloat(4);
			    dataInicio[4] = rs.getString(5);
			    tableModelInicio.addRow(dataInicio);
			}

			con.close();
			}catch(Exception e){ System.out.println(e);}
		
		
	}
	
	
}
