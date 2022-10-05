package CRM.clientes;

/**
 * @author Ainhoa Lopez Bleda
 * @refactor Cristina Álvarez
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
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import CRM.componentes.Btn;
import CRM.componentes.ComboBox;
import CRM.componentes.InputText;
import Conexion.Conexion;

public class listadoClientes extends JDialog {

	private static final long serialVersionUID = 1L;
	public static Boolean cerrarpan = false;
	private JButton cerrar,eliminar,modificar,nuevo;
	private JTable table;
	private Conexion con;
	private Connection conexion;
	private ClientesPotenciales mod = null;

	public listadoClientes() 
	{
		setResizable(false);
		setTitle("L I S T A D O    C L I E N T E S    P O T E N C I A L E S");
		cerrarpan = true;
		setIconImage(Toolkit.getDefaultToolkit().getImage("Imagenes\\logo.png"));
		setBounds(10, 495, 1102, 309);
		getContentPane().setLayout(null);
		
		//DIBUJAR TABLA
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 1062, 216);
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
			PreparedStatement comando = conexion.prepareStatement("Select * from clientespotenciales");

			ResultSet res = comando.executeQuery();

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
		//BOTON BUSCAR

        Btn buscar = new Btn("BUSCAR",230, 238, 100, 23 );
        getContentPane().add(buscar);

		InputText valueToSearch = new InputText(120,238,100,23,10);
		getContentPane().add(valueToSearch);

        String[] opcionesDesplegables = new String[]{"", "NombreApellidos", "Telefono", "idCliente","Curso_Interes","Resumen"};
        ComboBox criteriaToSearch = new ComboBox(opcionesDesplegables, 10,238,100,23);
        getContentPane().add(criteriaToSearch);

		//Realiza la busqueda y devuelve el listado resultante en otra ventana
		buscar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ResulBusqueda resulBusqueda= new ResulBusqueda(valueToSearch.getText(), (String) criteriaToSearch.getSelectedItem());
				resulBusqueda.setVisible(true);

			}
		});

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
		eliminar.setBounds(582, 238, 100, 23);
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
		modificar.setBounds(712, 238, 100, 23);
		getContentPane().add(modificar);
		
		//BOTON NUEVO
		Btn nuevo = new Btn("NUEVO",842, 238, 100, 23);
		getContentPane().add(nuevo);
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

		//Botón citar
		Btn citar = new Btn("CITAR",972, 238, 100, 23 );
		getContentPane().add(citar);
		
	}
			
}
