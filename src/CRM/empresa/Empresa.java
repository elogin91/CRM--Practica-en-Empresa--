package CRM.empresa;

/**
 * @author Ainhoa Lopez Bleda
 */

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import CRM.clientes.ClientesPotenciales;
import CRM.componentes.LogoFinse;
import Conexion.Conexion;
import Rutinas.esNumerico;

public class Empresa extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextField nombreEmpresa;
	private JTextField cif;
	private JTextField direccion;
	private JTextField empleados;

	private Connection conexion;
	private Conexion con;
	private JTextField telefono;

	public Empresa() {
		setResizable(false);
		setTitle("E M P R E S A");
		setIconImage(Toolkit.getDefaultToolkit().getImage(ClientesPotenciales.class.getResource("/Imagenes/empresa.png")));
		setBounds(100, 100, 450, 328);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);

		// BOTON INSERTAR
		JButton Insertar = new JButton("AÑADIR");
		Insertar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// VALIDAMOS CAMPOS VACIOS
				if (!nombreEmpresa.getText().trim().isEmpty()) {
					if (!cif.getText().trim().isEmpty()) {
						if (!direccion.getText().trim().isEmpty()) {
							if (!empleados.getText().trim().isEmpty()) {
							} else {
								JOptionPane.showMessageDialog(null, "El numero de empleados es obligatorio");
							}
						} else {
							JOptionPane.showMessageDialog(null, "La direccion es obligatoria");
						}
					} else {
						JOptionPane.showMessageDialog(null, "El CIF es obligatorio");
					}
				} else {
					JOptionPane.showMessageDialog(null, "El nombre de la empresa no puede estar vacio");
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
						PreparedStatement comando = conexion.prepareStatement("insert into empresas "
								+ "(nombreEmpresas, CIF, Telefono, domicilioFiscal, Empleados) values (?,?,?,?,?)");

						comando.setString(1, nombreEmpresa.getText());
						comando.setString(2, cif.getText());
						comando.setString(3, telefono.getText());
						comando.setString(4, direccion.getText());
						comando.setString(5, empleados.getText());

						comando.executeUpdate();

						JOptionPane.showMessageDialog(null, "Empresa insertada", "INFORMACION",
								JOptionPane.INFORMATION_MESSAGE);

					} else {
						JOptionPane.showMessageDialog(null, "Telefono incorrecto");
					}

				} catch (SQLException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Empresa no insertada", "INFORMACION",
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
		Insertar.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 12));
		Insertar.setBounds(20, 208, 95, 23);
		getContentPane().add(Insertar);

		// BOTON CITA
		JButton Cita = new JButton("CITA");
		Cita.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CitaEmpresas citar = new CitaEmpresas();
				citar.setVisible(true);
			}
		});
		Cita.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 12));
		Cita.setBounds(20, 243, 95, 23);
		getContentPane().add(Cita);

		// BOTON MODIFICAR
		JButton Modificar = new JButton("MODIFICAR");
		Modificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// VALIDAMOS CAMPOS VACIOS
				if (!nombreEmpresa.getText().trim().isEmpty()) {
					if (!cif.getText().trim().isEmpty()) {
						if (!direccion.getText().trim().isEmpty()) {
							if (!empleados.getText().trim().isEmpty()) {
							} else {
								JOptionPane.showMessageDialog(null, "El numero de empleados es obligatorio");
							}
						} else {
							JOptionPane.showMessageDialog(null, "La direccion es obligatoria");
						}
					} else {
						JOptionPane.showMessageDialog(null, "El CIF es obligatorio");
					}
				} else {
					JOptionPane.showMessageDialog(null, "El nombre de la empresa no puede estar vacio");
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
						PreparedStatement comando = conexion.prepareStatement("update empresas set "
								+ "CIF = ?, Telefono = ?, domicilioFiscal = ?, Empleados = ? where nombreEmpresas = ?");

						comando.setString(1, cif.getText());
						comando.setString(2, telefono.getText());
						comando.setString(3, direccion.getText());
						comando.setString(4, empleados.getText());
						comando.setString(5, nombreEmpresa.getText());

						int cantidad = comando.executeUpdate();

						if (cantidad == 1) {
							JOptionPane.showMessageDialog(null, "Empresa modificada");
						} else {
							JOptionPane.showMessageDialog(null, "Empresa no modificado");

							nombreEmpresa.setText("");
							cif.setText("");
							telefono.setText("");
							direccion.setText("");
							empleados.setText("");
						}

					} else {
						JOptionPane.showMessageDialog(null, "Telefono incorrecto");
					}

				} catch (SQLException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Empresa no insertada", "INFORMACION",
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
		Modificar.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 12));
		Modificar.setBounds(135, 208, 100, 23);
		getContentPane().add(Modificar);

		// BOTON LISTADO
		JButton Listado = new JButton("LISTADO");
		Listado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listadoEmpresas lem = new listadoEmpresas();
				lem.setVisible(true);
			}
		});
		Listado.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 12));
		Listado.setBounds(135, 243, 100, 23);
		getContentPane().add(Listado);

		// BOTON BUSCAR
		JButton Buscar = new JButton("BUSCAR");
		Buscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					con = new Conexion();
					conexion = con.getConnection();

					// COMPROBAMOS EL TELEFONO CON LA RUTINA ESNUMERICO Y LA PRIMERA POSICION
					String tf1 = (telefono.getText()).substring(0, 1);

					if (esNumerico.VerificarNumerico(telefono.getText())
							&& (tf1.equals("8") || tf1.equals("9") || tf1.equals("6") || tf1.equals("7"))
							&& (telefono.getText().length() == 9)) {

						// PROCEDEMOS A LA INSERCCION EN LA BBDD
						PreparedStatement comando = conexion
								.prepareStatement("select * from empresas where nombreEmpresas = ?");

						comando.setString(1, nombreEmpresa.getText());

						ResultSet res = comando.executeQuery();

						if (res.next()) {
							nombreEmpresa.setText(res.getString("nombreEmpresas"));
							cif.setText(res.getString("CIF"));
							telefono.setText(res.getString("Telefono"));
							direccion.setText(res.getString("domicilioFiscal"));
							empleados.setText(res.getString("Empleados"));

						} else {
							JOptionPane.showMessageDialog(null, "Empresa no encontrada");

							nombreEmpresa.setText("");
							cif.setText("");
							telefono.setText("");
							direccion.setText("");
							empleados.setText("");
						}

					} else {
						JOptionPane.showMessageDialog(null, "Telefono incorrecto");
					}

				} catch (SQLException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Empresa no insertada", "INFORMACION",
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
		Buscar.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 12));
		Buscar.setBounds(255, 208, 95, 23);
		getContentPane().add(Buscar);

		// BOTON LIMPIAR
		JButton Limpiar = new JButton("LIMPIAR");
		Limpiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nombreEmpresa.setText("");
				cif.setText("");
				telefono.setText("");
				direccion.setText("");
				empleados.setText("");
			}
		});
		Limpiar.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 12));
		Limpiar.setBounds(255, 243, 95, 23);
		getContentPane().add(Limpiar);

		// ESTRUCTURA
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
		lblNombre.setBounds(30, 43, 68, 14);
		getContentPane().add(lblNombre);

		nombreEmpresa = new JTextField();
		nombreEmpresa.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (nombreEmpresa.getText().length() == 100) {
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
		nombreEmpresa.setBounds(100, 40, 150, 22);
		getContentPane().add(nombreEmpresa);
		nombreEmpresa.setColumns(10);

		JLabel lblCif = new JLabel("CIF");
		lblCif.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
		lblCif.setBounds(30, 73, 46, 14);
		getContentPane().add(lblCif);

		cif = new JTextField();
		cif.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (cif.getText().length() == 9) {
					e.consume();
					JOptionPane.showMessageDialog(null, "Maximo 9 caracteres");
				}
			}
		});
		cif.setColumns(10);
		cif.setBounds(100, 70, 100, 22);
		getContentPane().add(cif);

		JLabel lblTelefono = new JLabel("Telefono");
		lblTelefono.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
		lblTelefono.setBounds(30, 103, 68, 14);
		getContentPane().add(lblTelefono);

		telefono = new JTextField();
		telefono.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (telefono.getText().length() == 9) {
					e.consume();
					JOptionPane.showMessageDialog(null, "Maximo 9 caracteres");
				}
				char validar = e.getKeyChar();
				if (Character.isLetter(validar)) {
					getToolkit().beep();
					e.consume();
					JOptionPane.showMessageDialog(null, "Ingresa solo números");
				}
			}
		});
		telefono.setColumns(10);
		telefono.setBounds(100, 100, 100, 22);
		getContentPane().add(telefono);

		JLabel lblDireccion = new JLabel("Direccion");
		lblDireccion.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
		lblDireccion.setBounds(30, 133, 68, 14);
		getContentPane().add(lblDireccion);

		direccion = new JTextField();
		direccion.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (direccion.getText().length() == 150) {
					e.consume();
					JOptionPane.showMessageDialog(null, "Maximo 150 caracteres");
				}
			}
		});
		direccion.setColumns(10);
		direccion.setBounds(100, 130, 180, 22);
		getContentPane().add(direccion);

		JLabel lblNumeroDeEmpleados = new JLabel("Numero de empleados");
		lblNumeroDeEmpleados.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
		lblNumeroDeEmpleados.setBounds(32, 168, 147, 14);
		getContentPane().add(lblNumeroDeEmpleados);

		empleados = new JTextField();
		empleados.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (empleados.getText().length() == 10) {
					e.consume();
				}
				e.getKeyChar();
				if (e.getKeyChar() >= 33 && e.getKeyChar() <= 42 || e.getKeyChar() >= 45 && e.getKeyChar() <= 47
						|| e.getKeyChar() >= 58 && e.getKeyChar() <= 61
						|| e.getKeyChar() >= 63 && e.getKeyChar() <= 255) {
					e.consume();
					JOptionPane.showMessageDialog(null, "No se permiten caracteres especiales");
				}
			}
		});
		empleados.setColumns(10);
		empleados.setBounds(192, 165, 80, 22);
		getContentPane().add(empleados);

		// LOGO FINSE
		LogoFinse logoFinse= new LogoFinse("foto",325, -26, 180, 389);
		getContentPane().add(logoFinse);
	}
}
