package CRM.empresa;

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

public class listadoEmpresas extends JDialog {

	private static final long serialVersionUID = 1L;
	public static Boolean cerrarpan = false;
	private JButton cerrar;
	private JTable table;
	private Conexion con;
	private Connection conexion;

	public listadoEmpresas() {
		setResizable(false);
		setTitle("L I S T A D O   D E   E M P R E S A S");
		cerrarpan = true;
		setIconImage(Toolkit.getDefaultToolkit().getImage("Imagenes\\logo.png"));
		setBounds(100, 100, 770, 307);
		getContentPane().setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 733, 216);
		getContentPane().add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);

		cerrar = new JButton("CERRAR");
		cerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		cerrar.setBounds(350, 238, 89, 23);
		getContentPane().add(cerrar);
		DefaultTableModel modelo = new DefaultTableModel();

		try {
			Object[] columnas = new Object[6];
			modelo.setColumnIdentifiers(columnas);

			con = new Conexion();
			conexion = con.getConnection();
			PreparedStatement comando = conexion.prepareStatement("Select * from empresas");

			ResultSet res = comando.executeQuery();

			while (res.next()) {
				Object[] datos = new Object[6];
				datos[0] = res.getString("idEmpresas");
				datos[1] = res.getString("nombreEmpresas");
				datos[2] = res.getString("CIF");
				datos[3] = res.getString("Telefono");
				datos[4] = res.getString("domicilioFiscal");
				datos[5] = res.getString("Empleados");

				modelo.addRow(datos);
			}
			table.setModel(modelo);

			// AUTO_RESIZE_OFF PARA QUE FUNCIONE EL SCROOL

			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			table.getTableHeader().setReorderingAllowed(false);

			table.getColumnModel().getColumn(0).setResizable(false);
			table.getColumnModel().getColumn(0).setHeaderValue("iD");
			table.getColumnModel().getColumn(0).setPreferredWidth(30);

			table.getColumnModel().getColumn(1).setResizable(false);
			table.getColumnModel().getColumn(1).setHeaderValue("Nombre");
			table.getColumnModel().getColumn(1).setPreferredWidth(150);

			table.getColumnModel().getColumn(2).setResizable(false);
			table.getColumnModel().getColumn(2).setHeaderValue("CIF");
			table.getColumnModel().getColumn(2).setPreferredWidth(100);

			table.getColumnModel().getColumn(3).setResizable(false);
			table.getColumnModel().getColumn(3).setHeaderValue("Telefono");
			table.getColumnModel().getColumn(3).setPreferredWidth(100);

			table.getColumnModel().getColumn(4).setResizable(false);
			table.getColumnModel().getColumn(4).setHeaderValue("Domicilio Fiscal");
			table.getColumnModel().getColumn(4).setPreferredWidth(200);

			table.getColumnModel().getColumn(5).setResizable(false);
			table.getColumnModel().getColumn(5).setHeaderValue("Nº Empleados");
			table.getColumnModel().getColumn(5).setPreferredWidth(150);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conexion.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

	}
}
