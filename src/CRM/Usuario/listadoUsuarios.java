package CRM.Usuario;

/**
 * @author Ainhoa Lopez Bleda
 */

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Conexion.Conexion;

public class listadoUsuarios extends JDialog {
	private static final long serialVersionUID = 1L;
	private JButton cerrar;
	private JTable table;
	private Conexion con;
	private Connection conexion;
	public static Boolean cerrarpan = false;
	

	public listadoUsuarios() {
		setResizable(false);
		cerrarpan = true;
		setIconImage(Toolkit.getDefaultToolkit().getImage("Imagenes\\logo.png"));
		setTitle("CATALOGO DE USUARIOS");
		setBounds(100, 100, 800, 318);
		getContentPane().setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 764, 216);
		getContentPane().add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);
		DefaultTableModel modelo = new DefaultTableModel();

		try {
			Object[] columnas = new Object[4];
			modelo.setColumnIdentifiers(columnas);

			con = new Conexion();
			conexion = con.getConnection();
			PreparedStatement comando = conexion.prepareStatement("Select * from usuarios");

			ResultSet res = comando.executeQuery();

			while (res.next()) {
				Object[] datos = new Object[4];
				datos[0] = res.getString("Responsable");
				datos[1] = res.getString("Usuario");
				datos[2] = res.getString("Password");
				datos[3] = res.getString("Rol");

				modelo.addRow(datos);
			}
			table.setModel(modelo);

			// AUTO_RESIZE_OFF PARA QUE FUNCIONE EL SCROLL
			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

			table.getTableHeader().setReorderingAllowed(false);
			table.getColumnModel().getColumn(0).setResizable(false);
			table.getColumnModel().getColumn(0).setHeaderValue("Responsable");
			table.getColumnModel().getColumn(0).setPreferredWidth(120);

			table.getColumnModel().getColumn(1).setResizable(false);
			table.getColumnModel().getColumn(1).setHeaderValue("Usuario");
			table.getColumnModel().getColumn(1).setPreferredWidth(120);

			table.getColumnModel().getColumn(2).setResizable(false);
			table.getColumnModel().getColumn(2).setHeaderValue("Password");
			table.getColumnModel().getColumn(2).setPreferredWidth(480);

			table.getColumnModel().getColumn(3).setResizable(false);
			table.getColumnModel().getColumn(3).setHeaderValue("Rol");
			table.getColumnModel().getColumn(3).setPreferredWidth(100);

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			try {
				conexion.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		cerrar = new JButton("Cerrar");
		cerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cerrarpan = false;
				dispose();
			}
		});
		cerrar.setBounds(350, 238, 90, 23);
		getContentPane().add(cerrar);

	}
}
