package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

	private static Connection con;
	private static final String driver = "com.mysql.cj.jdbc.Driver";
	private static final String user = "root";
	private static final String password="12345";
	private static final String url="jdbc:mysql://localhost:3306/crm";
	
	public Conexion() {
		con = null;
		
		try {
			Class.forName (driver);
			con=DriverManager.getConnection(url,user,password);
			if (con != null) {
				System.out.println("Conexion establecida");
			}	
		} catch(ClassNotFoundException | SQLException e){
			System.out.println("Error en la conexion");
		}
	}
	
// Este metodo realiza la conexion a la base de datos	
	
	public Connection getConnection() {
		return con;
	}
	
// Con este metodo nos desconectamos de la base de datos	
	
	public void Desconectar() {
		con = null;
		if(con == null) {
			System.out.println("Conexion terminada");
		}
	}
}