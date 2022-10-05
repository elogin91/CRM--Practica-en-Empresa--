package CRM.clientes;

/**
 * @author Saulo Alcaide Plou
 */

import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

import Conexion.Conexion;
import Rutinas.esNumerico;

import static CRM.clientes.Cliente.getIdClient;

public class ModificarCliente extends JFrame 
{

	private static final long serialVersionUID = 1L;
	private JTextField nombreApellidos;
	private JTextField telefono;
	private JComboBox<String> comboResumen;
	private JComboBox<String> comboCanal;
	private JComboBox<String> comboInteres;
	private JComboBox<String> comboResponsable;
	private JTextField codResponsable;
	private JTextField codInteres;
	private JTextField codCanal;
	private JComboBox<String> comboCita;
	private Connection conexion;
	private Conexion con;
	private JDateChooser fecha;
	private Date fec;
	private String fechaFormateada;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private JTextField detalles;
	private JComboBox<String> comboDocumento;

	public ModificarCliente(String idCliente) 
	{
		setTitle("MODIFICAR DATOS DEL CLIENTE");
		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(ModificarCliente.class.getResource("/Imagenes/c-potenciales.png")));
		setBounds(100, 100, 521, 472);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
				
		
		// BOTON MODIFICAR
		JButton Modificar = new JButton("MODIFICAR");
		Modificar.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				// VALIDAMOS CAMPOS VACIOS
				if (!nombreApellidos.getText().trim().isEmpty()) 
				{
					if (!comboResumen.getSelectedItem().equals("")) 
					{
						if (!comboInteres.getSelectedItem().equals("")) 
						{
							if (!comboResponsable.getSelectedItem().equals("")) 
							{
								if (!comboCita.getSelectedItem().equals("")) 
								{

								} 
								else 
								{
									JOptionPane.showMessageDialog(null, "¿Ha concertado cita?");
								}
							}
							else 
							{
								JOptionPane.showMessageDialog(null, "Seleccione un Responsable");
							}
						} 
						else 
						{
							JOptionPane.showMessageDialog(null, "Seleccione el curso de interes");
						}
					} 
					else
					{
						JOptionPane.showMessageDialog(null, "Seleccione una opcion de resumen");
					}
				} 
				else 
				{
					JOptionPane.showMessageDialog(null, "El nombre no puede estar vacio");
				}

				// CAMBIAMOS DE FORMATO LA FECHA
				fec = fecha.getDate();
				if (fec != null) 
				{
					fechaFormateada = sdf.format(fec);
				}
				else
				{
					fechaFormateada = null;
				}

				try {
					con = new Conexion();
					conexion = con.getConnection();

					// COMPROBAMOS EL TELEFONO CON LA RUTINA ESNUMERICO Y LA PRIMERA POSICION
					String tf1 = (telefono.getText()).substring(0, 1);

					if (esNumerico.VerificarNumerico(telefono.getText())
							&& (tf1.equals("8") || tf1.equals("9") || tf1.equals("6") || tf1.equals("7"))
							&& (telefono.getText().length() == 9)) {

						// PROCEDEMOS A LA INSERCCION EN LA BBDD
						PreparedStatement comando = conexion.prepareStatement("update clientespotenciales set "
								+ "Fecha_Contacto = ?, NombreApellidos = ?, Tipo_Documento = ?, Resumen = ?, Telefono =?,"
								+ "Curso_Interes = ?, Canal = ?, Responsable = ?, Concretar_Cita = ?, Detalles = ? where idCliente ='"+idCliente+"'");
						
						comando.setString(1, fechaFormateada);
						comando.setString(2, nombreApellidos.getText());
						comando.setString(3, (String) comboDocumento.getSelectedItem());
						comando.setString(4, (String) comboResumen.getSelectedItem());
						comando.setString(5, telefono.getText());
						comando.setString(6, (String) comboInteres.getSelectedItem());
						comando.setString(7, (String) comboCanal.getSelectedItem());
						comando.setString(8, (String) comboResponsable.getSelectedItem());
						comando.setString(9, (String) comboCita.getSelectedItem());
						comando.setString(10, detalles.getText());

						comando.executeUpdate();

						JOptionPane.showMessageDialog(null, "Cliente modificado", "INFORMACION",
								JOptionPane.INFORMATION_MESSAGE);

						//Abre la ventana Citar cuando cita = 'Sí'
						String comprobarValor="Si";
						if(comprobarValor.equals(comboCita.getSelectedItem().toString())) {
							CitaClientes citar = new CitaClientes(nombreApellidos.getText(),getIdClient(telefono.getText()));
							citar.setVisible(true);
						}

						dispose();
						listadoClientes lista = new listadoClientes();
						lista.setVisible(true);
						
					} else {
						JOptionPane.showMessageDialog(null, "Telefono incorrecto");
					}

				} catch (SQLException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Cliente no modificado", "INFORMACION",
							JOptionPane.INFORMATION_MESSAGE);
				} finally {
					try {
						conexion.close();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		Modificar.setBounds(150, 385, 100, 23);
		Modificar.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 12));
		getContentPane().add(Modificar);

		
		// BOTON CANCELAR
		JButton Cancelar = new JButton("CANCELAR");
		Cancelar.setBounds(300, 385, 95, 23);
		Cancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				listadoClientes lista = new listadoClientes();
				lista.setVisible(true);
			}
		});
		Cancelar.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 12));
		getContentPane().add(Cancelar);

		// ESTRUCTURA
		JLabel lblFecha = new JLabel("Fecha Contacto");
		lblFecha.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
		lblFecha.setBounds(20, 33, 107, 14);
		getContentPane().add(lblFecha);

		fecha = new JDateChooser("yyyy-MM-dd", " ####-##-##", '_');
		fecha.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (fecha.getDate() == null) {
					e.consume();
					JOptionPane.showMessageDialog(null, "Obligatoria la fecha");
				}
			}
		});
		fecha.setBounds(150, 30, 90, 20);
		getContentPane().add(fecha);

		JLabel lblnombre = new JLabel("Nombre y Apellidos");
		lblnombre.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
		lblnombre.setBounds(20, 63, 119, 14);
		getContentPane().add(lblnombre);

		nombreApellidos = new JTextField();
		nombreApellidos.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (nombreApellidos.getText().length() == 150) {
					e.consume();
					JOptionPane.showMessageDialog(null, "Maximo 150 caracteres");
				}
				e.getKeyChar();
				if (e.getKeyChar() >= 33 && e.getKeyChar() <= 64 || e.getKeyChar() >= 91 && e.getKeyChar() <= 96
						|| e.getKeyChar() >= 123 && e.getKeyChar() <= 208
						|| e.getKeyChar() >= 210 && e.getKeyChar() <= 240
						|| e.getKeyChar() >= 242 && e.getKeyChar() <= 255) {

					e.consume();
					JOptionPane.showMessageDialog(null, "No se permiten caracteres especiales");
				}
			}
		});
		nombreApellidos.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
		nombreApellidos.setBounds(150, 60, 165, 20);
		getContentPane().add(nombreApellidos);
		nombreApellidos.setColumns(10);

		JLabel lblTipoDocumento = new JLabel("Tipo documento");
		lblTipoDocumento.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
		lblTipoDocumento.setBounds(20, 93, 119, 14);
		getContentPane().add(lblTipoDocumento);

		comboDocumento = new JComboBox<String>();
		comboDocumento.setModel(
				new DefaultComboBoxModel<String>(new String[] { "", "DNI", "NIE", "En tramite", "Pasaporte" }));
		comboDocumento.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
		comboDocumento.setBounds(150, 90, 90, 20);
		getContentPane().add(comboDocumento);

		JLabel lblResumen = new JLabel("Resumen");
		lblResumen.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
		lblResumen.setBounds(20, 123, 56, 14);
		getContentPane().add(lblResumen);

		comboResumen = new JComboBox<String>();
		comboResumen.setModel(new DefaultComboBoxModel<String>(new String[] { "", "Insistir comunicación", "Descartado",
				"Reprogramar cita", "Tiene cita", "Matriculado", "Seguimiento" }));
		comboResumen.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
		comboResumen.setBounds(150, 120, 165, 20);
		getContentPane().add(comboResumen);

		JLabel lblTelefono = new JLabel("Telefono");
		lblTelefono.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
		lblTelefono.setBounds(20, 153, 56, 14);
		getContentPane().add(lblTelefono);

		telefono = new JTextField();
		telefono.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (telefono.getText().length() == 9) {
					e.consume();
				}
				char validar = e.getKeyChar();
				if (Character.isLetter(validar)) {
					getToolkit().beep();
					e.consume();
					JOptionPane.showMessageDialog(null, "Ingresa solo números");
				}
			}
		});
		telefono.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
		telefono.setColumns(10);
		telefono.setBounds(150, 150, 90, 20);
		getContentPane().add(telefono);

		// COMBO INTERES
		JLabel lblCursoInteres = new JLabel("Curso Interes");
		lblCursoInteres.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
		lblCursoInteres.setBounds(20, 183, 89, 14);
		getContentPane().add(lblCursoInteres);

		codInteres = new JTextField();
		codInteres.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
		codInteres.setColumns(10);
		codInteres.setBounds(440, 106, -10, 20);
		getContentPane().add(codInteres);

		comboInteres = new JComboBox<String>();
		comboInteres.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String completo = (String) comboInteres.getSelectedItem();
				String[] completoSeparado = completo.split(" ");
				String codigo = completoSeparado[0];
				codInteres.setText(codigo);
			}
		});
		// CARGAMOS EL COMBO
		try {
			con = new Conexion();
			conexion = con.getConnection();

			PreparedStatement inte = conexion.prepareStatement("SELECT * from cursos");
			ResultSet res = inte.executeQuery();
			comboInteres.addItem("");

			while (res.next()) {
				comboInteres.addItem(res.getString("Nombre_Curso"));
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				conexion.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

		comboInteres.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
		comboInteres.setBounds(150, 180, 180, 20);
		getContentPane().add(comboInteres);

		// COMBO CANAL
		JLabel lblCanal = new JLabel("Canal");
		lblCanal.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
		lblCanal.setBounds(20, 213, 56, 14);
		getContentPane().add(lblCanal);

		codCanal = new JTextField();
		codCanal.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
		codCanal.setColumns(10);
		codCanal.setBounds(290, 132, -10, 22);
		getContentPane().add(codCanal);

		comboCanal = new JComboBox<String>();
		comboCanal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String completo = (String) comboCanal.getSelectedItem();
				String[] completoSeparado = completo.split(" ");
				String codigo = completoSeparado[0];
				codCanal.setText(codigo);
			}
		});
		// CARGAMOS EL COMBO
		try {
			con = new Conexion();
			conexion = con.getConnection();

			PreparedStatement inte = conexion.prepareStatement("SELECT * from canales");
			ResultSet res = inte.executeQuery();
			comboCanal.addItem("");

			while (res.next()) {
				comboCanal.addItem(res.getString("Nombre_Canal"));
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				conexion.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		comboCanal.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
		comboCanal.setBounds(150, 210, 165, 20);
		getContentPane().add(comboCanal);

		// COMBO RESPONSABLE
		JLabel lblResponsable = new JLabel("Responsable");
		lblResponsable.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
		lblResponsable.setBounds(20, 243, 89, 14);
		getContentPane().add(lblResponsable);

		codResponsable = new JTextField();
		codResponsable.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
		codResponsable.setColumns(10);
		codResponsable.setBounds(290, 186, -10, 22);
		getContentPane().add(codResponsable);

		comboResponsable = new JComboBox<String>();
		comboResponsable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String completo = (String) comboResponsable.getSelectedItem();
				String[] completoSeparado = completo.split(" ");
				String codigo = completoSeparado[0];
				codResponsable.setText(codigo);
			}
		});

		// CARGAMOS EL COMBO
		try {
			con = new Conexion();
			conexion = con.getConnection();

			PreparedStatement respon = conexion.prepareStatement("SELECT * from usuarios");
			ResultSet res = respon.executeQuery();
			comboResponsable.addItem("");

			while (res.next()) {
				comboResponsable.addItem(res.getString("Responsable"));
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				conexion.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

		comboResponsable.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
		comboResponsable.setBounds(150, 240, 138, 20);
		getContentPane().add(comboResponsable);

		JLabel lblConcretacita = new JLabel("Concreta_Cita");
		lblConcretacita.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
		lblConcretacita.setBounds(20, 273, 89, 14);
		getContentPane().add(lblConcretacita);

		comboCita = new JComboBox<String>();
		comboCita.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		comboCita.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
		comboCita.setModel(new DefaultComboBoxModel<String>(new String[] { "", "Si", "No" }));
		comboCita.setBounds(150, 270, 56, 20);
		getContentPane().add(comboCita);

		JLabel lblDetalles = new JLabel("Detalles");
		lblDetalles.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
		lblDetalles.setBounds(20, 303, 56, 14);
		getContentPane().add(lblDetalles);

		detalles = new JTextField();
		detalles.setFont(new Font("Microsoft YaHei Light", Font.PLAIN, 12));
		detalles.setBounds(150, 300, 211, 30);
		getContentPane().add(detalles);
		detalles.setColumns(10);

		// foto imagen
		JLabel foto = new JLabel("foto");
		ImageIcon Imagen = new ImageIcon("Imagenes\\fondo_pantalla.png");
		foto.setBounds(325, -26, 180, 389);
		ImageIcon icono = new ImageIcon(
				Imagen.getImage().getScaledInstance(foto.getWidth(), foto.getHeight(), Image.SCALE_DEFAULT));
		foto.setIcon(icono);
		getContentPane().add(foto);
		
		//AUTO-RELLENO DE DATOS
		
				try 
				{
					con = new Conexion();
					conexion = con.getConnection();
					PreparedStatement comando;
					comando = conexion.prepareStatement("Select * from clientespotenciales");
					ResultSet dat = comando.executeQuery();
					
					while (dat.next()) 
					{
						String wid = dat.getString("idCliente");						
						if (wid.equals(idCliente))
						{							
							fechaFormateada = dat.getString("Fecha_Contacto");
							fec = sdf.parse(fechaFormateada);									
							fecha.setDate(fec);
							nombreApellidos.setText(dat.getString("NombreApellidos"));
							comboDocumento.setSelectedItem(dat.getString("Tipo_Documento"));
							comboResumen.setSelectedItem(dat.getString("Resumen"));
							telefono.setText(dat.getString("Telefono"));
							comboInteres.setSelectedItem(dat.getString("Curso_Interes"));
							comboCanal.setSelectedItem(dat.getString("Canal"));
							comboResponsable.setSelectedItem(dat.getString("Responsable"));
							comboCita.setSelectedItem(dat.getString("Concretar_Cita"));
							detalles.setText(dat.getString("Detalles"));					
						}
						
						
					}
					
					
				} 
				catch (SQLException | ParseException e2) 
				{
					
					e2.printStackTrace();
				}
	}
	
}
