package CRM.clientes;

/**
 * @author Ainhoa Lopez Bleda
 */

import Conexion.Conexion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static CRM.servicios.QuerysBuilding.searchQuery;


public class ResulBusqueda extends JDialog {

	private static final long serialVersionUID = 1L;
	public static Boolean cerrarpan = false;
	private JButton cerrar,eliminar,modificar,nuevo;
	private JTable table;
	private Conexion con;
	private Connection conexion;
	private ClientesPotenciales mod = null;

	public ResulBusqueda(String value, String kindOfValue)
	{
		setResizable(false);
		setTitle("R E S U L T A D O  D E  L A  B U S Q U E D A.");
		cerrarpan = true;
		setIconImage(Toolkit.getDefaultToolkit().getImage("Imagenes\\logo.png"));
		setBounds(100, 576, 800, 309);
		getContentPane().setLayout(null);
		
		//DIBUJAR TABLA
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 764, 216);
		getContentPane().add(scrollPane);
		table = new JTable();
		scrollPane.setViewportView(table);
		DefaultTableModel modelo = new DefaultTableModel();
		try 
		{
			Object[] columnas = new Object[11];
			modelo.setColumnIdentifiers(columnas);

			con = new Conexion();
			conexion = con.getConnection();
			PreparedStatement sentencia = conexion.prepareStatement(searchQuery(value, kindOfValue));
			ResultSet res = sentencia.executeQuery();

			while (res.next()) 
			{
				
				Object[] datos = new Object[11];
				datos[0] = res.getString("idCliente");				
				datos[1] = res.getString("Fecha_Contacto");
				datos[2] = res.getString("NombreApellidos");
				datos[3] = res.getString("Tipo_Documento");
				datos[4] = res.getString("Resumen");
				datos[5] = res.getString("Telefono");
				datos[6] = res.getString("Curso_Interes");
				datos[7] = res.getString("Canal");
				datos[8] = res.getString("Responsable");
				datos[9] = res.getString("Concretar_Cita");
				datos[10] = res.getString("Detalles");
				
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
			table.getColumnModel().getColumn(1).setHeaderValue("Fecha contacto");
			table.getColumnModel().getColumn(1).setPreferredWidth(90);

			table.getColumnModel().getColumn(2).setResizable(false);
			table.getColumnModel().getColumn(2).setHeaderValue("Nombre Completo");
			table.getColumnModel().getColumn(2).setPreferredWidth(120);

			table.getColumnModel().getColumn(3).setResizable(false);
			table.getColumnModel().getColumn(3).setHeaderValue("Documento");
			table.getColumnModel().getColumn(3).setPreferredWidth(80);

			table.getColumnModel().getColumn(4).setResizable(false);
			table.getColumnModel().getColumn(4).setHeaderValue("Resumen");
			table.getColumnModel().getColumn(4).setPreferredWidth(130);

			table.getColumnModel().getColumn(5).setResizable(false);
			table.getColumnModel().getColumn(5).setHeaderValue("Telefono");
			table.getColumnModel().getColumn(5).setPreferredWidth(80);

			table.getColumnModel().getColumn(6).setResizable(false);
			table.getColumnModel().getColumn(6).setHeaderValue("Curso de interes");
			table.getColumnModel().getColumn(6).setPreferredWidth(160);

			table.getColumnModel().getColumn(7).setResizable(false);
			table.getColumnModel().getColumn(7).setHeaderValue("Canal");
			table.getColumnModel().getColumn(7).setPreferredWidth(100);

			table.getColumnModel().getColumn(8).setResizable(false);
			table.getColumnModel().getColumn(8).setHeaderValue("Responsable");
			table.getColumnModel().getColumn(8).setPreferredWidth(120);

			table.getColumnModel().getColumn(9).setResizable(false);
			table.getColumnModel().getColumn(9).setHeaderValue("¿Cita?");
			table.getColumnModel().getColumn(9).setPreferredWidth(50);

			table.getColumnModel().getColumn(10).setResizable(false);
			table.getColumnModel().getColumn(10).setHeaderValue("Detalles");
			table.getColumnModel().getColumn(10).setPreferredWidth(120);

			
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			try 
			{
				conexion.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

		
		//BOTONES:
		
		//BOTON CERRAR
		cerrar = new JButton("CERRAR");
		cerrar.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				dispose();
			}
		});
			
		cerrar.setBounds(600, 238, 100, 23);
		getContentPane().add(cerrar);
									
		//BOTON ELIMINAR
		eliminar = new JButton("ELIMINAR");
		eliminar.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				int fila=table.getSelectedRow();				
				if(fila>=0)
				{
					String idc=table.getValueAt(fila,0).toString();
					String sql = "delete from clientespotenciales where idCliente='"+idc+"'";
					try 
					{
						con = new Conexion();
						conexion = con.getConnection();
						PreparedStatement elim = conexion.prepareStatement(sql);
						elim.executeUpdate();
						modelo.removeRow(fila);
					} 
					catch (SQLException e1) 
					{
						
						e1.printStackTrace();
					}
					
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Selecione Cliente");
				}
			}
		});
		eliminar.setBounds(450, 238, 100, 23);
		getContentPane().add(eliminar);

		//BOTON MODIFICAR
		modificar = new JButton("MODIFICAR");
		modificar.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				int fila=table.getSelectedRow();
				if (fila>=0)
				{
				String idc=table.getValueAt(fila,0).toString();
				ModificarCliente mod = new ModificarCliente(idc);
				mod.setVisible(true);
				dispose();
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Selecione Cliente");
				}
				dispose();
			}
		});
		modificar.setBounds(300, 238, 100, 23);
		getContentPane().add(modificar);
		
		//BOTON NUEVO
		nuevo = new JButton("NUEVO");
		nuevo.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if (mod==null)
				{
					mod = new ClientesPotenciales();
					mod.setVisible(true);
					dispose();
				}
				else
				{
					mod.setVisible(true);
				}
				
			}
		});
		nuevo.setBounds(150, 238, 100, 23);
		getContentPane().add(nuevo);
		
	}
			
}
