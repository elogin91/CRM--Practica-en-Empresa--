package CRM.Usuario;

/**
 * @author Ainhoa Lopez Bleda
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
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Conexion.Conexion;
import Conexion.EncriptarSHA256;

public class NuevoUsuario extends JDialog {
	private static final long serialVersionUID = 1L;
	private JTextField nombre;
	public JTextField usuario;
	private JPasswordField password;
	private JComboBox<String> comborol;
	private JTextField codRol;

	private Connection conexion;
	private Conexion con;
	private JButton registrar;
	private JButton cancelar;
	private JPanel contentPane;

	public NuevoUsuario() {
		setTitle("CREAR NUEVO USUARIO");
		setIconImage(Toolkit.getDefaultToolkit().getImage("Imagenes\\user.png"));
		setBounds(100, 100, 592, 380);
		setResizable(false);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNombre = new JLabel("Nombre del responsable");
		lblNombre.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
		lblNombre.setBounds(40, 45, 160, 22);
		getContentPane().add(lblNombre);

		nombre = new JTextField();
		nombre.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (nombre.getText().length() == 50) {
					e.consume();
				}

				char validar = e.getKeyChar();
				if (Character.isDigit(validar)) {
					getToolkit().beep();
					e.consume();
					JOptionPane.showMessageDialog(rootPane, "El nombre solo puede contener letras.");
				}
			}
		});
		nombre.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
		nombre.setBounds(40, 75, 180, 30);
		getContentPane().add(nombre);
		nombre.setColumns(10);

		JLabel lblUsuario = new JLabel("Usuario");
		lblUsuario.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
		lblUsuario.setBounds(40, 120, 150, 22);
		getContentPane().add(lblUsuario);

		usuario = new JTextField();
		usuario.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (nombre.getText().length() == 50) {
					e.consume();
				}

				usuario.setText(usuario.getText().trim());
			}
		});
		usuario.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
		usuario.setColumns(10);
		usuario.setBounds(40, 150, 180, 29);
		getContentPane().add(usuario);

		JLabel lblContrasea = new JLabel("Contraseña");
		lblContrasea.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
		lblContrasea.setBounds(40, 195, 161, 22);
		getContentPane().add(lblContrasea);

		password = new JPasswordField();
		password.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				password.setText(password.getText().trim());

			}
		});
		password.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
		password.setBounds(40, 225, 237, 29);
		getContentPane().add(password);

		JLabel lblRol = new JLabel("Rol");
		lblRol.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
		lblRol.setBounds(40, 270, 80, 22);
		getContentPane().add(lblRol);

		codRol = new JTextField();
		codRol.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
		codRol.setColumns(10);
		codRol.setBounds(290, 186, -10, 22);
		getContentPane().add(codRol);

		comborol = new JComboBox<String>();
		comborol.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String completo = (String) comborol.getSelectedItem();
				String[] completoSeparado = completo.split(" ");
				String codigo = completoSeparado[0];
				codRol.setText(codigo);
			}
		});
		// CARGAMOS EL COMBO
		try {
			con = new Conexion();
			conexion = con.getConnection();

			PreparedStatement inte = conexion.prepareStatement("SELECT * from rol");
			ResultSet res = inte.executeQuery();
			comborol.addItem("");

			while (res.next()) {
				comborol.addItem(res.getString("Puesto"));
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
		comborol.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 12));
		comborol.setBounds(130, 271, 147, 21);
		getContentPane().add(comborol);

		registrar = new JButton("REGISTRAR");
		registrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (!nombre.getText().trim().isEmpty()) {
						if (!usuario.getText().trim().isEmpty()) {
							if (!password.getText().trim().isEmpty()) {

							} else {
								JOptionPane.showMessageDialog(rootPane, "La contraseña no puede estar vacia");
							}
						} else {
							JOptionPane.showMessageDialog(rootPane, "El usuario no puede estar vacio");
						}
					} else {
						JOptionPane.showMessageDialog(rootPane, "El nombre no puede estar vacio");
					}
				} finally {
				}

				try {
					Conexion con = new Conexion();
					Connection conexion = con.getConnection();

					String pwdconvertir = new EncriptarSHA256().Convertir(String.valueOf(password.getPassword()));

					PreparedStatement comando = conexion.prepareStatement(
							"insert into usuarios (Responsable, Usuario, Password, Rol) value (?,?,?,?)",
							Statement.RETURN_GENERATED_KEYS);
					comando.setString(1, nombre.getText());
					comando.setString(2, usuario.getText());
					comando.setString(3, String.valueOf(pwdconvertir.toString()));
					comando.setString(4, (String) comborol.getSelectedItem());

					comando.executeUpdate();

					JOptionPane.showMessageDialog(rootPane, "B I E N V E N I D O !! Ahora puedes iniciar sesion!!");
					dispose();
					Login on = new Login();
					on.setVisible(true);

				} catch (SQLException e1) {
					e1.printStackTrace();
				} finally {
					try {
						conexion.close();
					} catch (SQLException e1) {
						System.out.print(e1.getErrorCode());
						if (e1.getErrorCode() == 1062) {
							JOptionPane.showMessageDialog(null, "Esta persona ya está registrada", "ERROR",
									JOptionPane.ERROR_MESSAGE);
						} else {
							if (e1.getErrorCode() == 1452) {
								JOptionPane.showMessageDialog(null, "Es obligatorio rellenar todos los campos", "ERROR",
										JOptionPane.ERROR_MESSAGE);
							} else {
								e1.getErrorCode();
							}
						}
					}

				}
			}
		});
		registrar.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 12));
		registrar.setBounds(460, 215, 105, 25);
		getContentPane().add(registrar);

		cancelar = new JButton("CANCELAR");
		cancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		cancelar.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 12));
		cancelar.setBounds(460, 275, 105, 25);
		getContentPane().add(cancelar);

		JLabel foto = new JLabel("foto");
		ImageIcon Imagen = new ImageIcon("Imagenes\\fondo_pantalla.png");
		foto.setBounds(167, 0, 411, 261);
		ImageIcon icono = new ImageIcon(
				Imagen.getImage().getScaledInstance(foto.getWidth(), foto.getHeight(), Image.SCALE_DEFAULT));
		foto.setIcon(icono);
		contentPane.add(foto);

		JButton visualizar = new JButton("VISUALIZAR");
		visualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listadoUsuarios users = new listadoUsuarios();
				users.setVisible(true);
			}
		});
		visualizar.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 12));
		visualizar.setBounds(460, 245, 105, 25);
		contentPane.add(visualizar);
	}
}
