package CRM.Usuario;

/**
 * @author Ainhoa Lopez Bleda
 */

import java.awt.EventQueue;
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
import javax.swing.JOptionPane;

import CRM.MenuPrincipal;
import CRM.componentes.*;
import Conexion.Conexion;
import Conexion.EncriptarSHA256;

public class Login extends JFrame {
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public Login() {

		//Ventana de Login
		setTitle("CRM - FINSE");
		setIconImage(Toolkit.getDefaultToolkit().getImage("Imagenes\\user.png"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(10, 10, 331, 304);
		setResizable(false);

		// Bloque Contenedor
		Container container= new Container();
		setContentPane(container);

		//Etiqueta USUARIO
		Etiqueta usuarioEtiqueta= new Etiqueta("Usuario:", 36, 100, 80, 14, 0);
		container.add(usuarioEtiqueta);

		//Entrada de texto USUARIO
		InputText inputUsuario= new InputText(116, 97, 130, 20,10);
		container.add(inputUsuario);

		//Etiqueta CONTRASEÑA
		Etiqueta contraseñaEtiqueta= new Etiqueta("Password:", 36, 140, 80, 14, 0);
		container.add(contraseñaEtiqueta);

		//Entrada de texto CONTRASEÑA
		InputPassField inputPass=new InputPassField(116, 137, 130, 20);
		container.add(inputPass);

		//Botón CANCELAR
		Btn cancelar =new Btn("Cancelar", 175, 182, 96, 23);
		container.add(cancelar);

		//Evento cerrar ventana al pulsar BOTON CANCELAR
		cancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		//Botón ENTRAR
		Btn entrar=new Btn("ENTRAR",36, 182, 96, 23 );
		container.add(entrar);

		//EVENTO PULSAR ENTER = CLICK en Botón Entrar
		inputPass.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char cTeclaPresionada = e.getKeyChar(); // DA CLICK AL BOTON ENTRAR AL DETECTAR EL ENTER
				if (cTeclaPresionada == KeyEvent.VK_ENTER) {
					entrar.doClick();	// EJECUTA EL BOTON ENTRAR COMO SI HICIERAMOS CLICK
				}
			}
		});

		//EVENTO ENVIAR EL USUARIO Y CONTRASEÑA
		entrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Conexion con = new Conexion();
					Connection conexion = con.getConnection();

					String passEncriptada = new EncriptarSHA256().Convertir(String.valueOf(inputPass.getPassword()));

					PreparedStatement comando = conexion
							.prepareStatement("SELECT * FROM crm.usuarios where Usuario = ? and Password = ?");

					comando.setString(1, inputUsuario.getText());
					comando.setString(2, String.valueOf(passEncriptada.toString()));

					ResultSet registro = comando.executeQuery();

					if (registro.next()) {
						MenuPrincipal menu = new MenuPrincipal();
						menu.setVisible(true);
						dispose();
					} else {
						JOptionPane.showMessageDialog(((JButton) e.getSource()).getParent(),
								"Contraseña y/o usuario son incorrectos", "ERROR EN EL LOGIN",
								JOptionPane.ERROR_MESSAGE);
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});

		//LOGO FINSE
		LogoFinse logoFinse=new LogoFinse("foto",106, 0, 96, 113);
		container.add(logoFinse);

		//Etiqueta Nuevo usuario
		Etiqueta nuevoUsuario=new Etiqueta("¿Eres nuevo?",125, 245, 80, 16,0);
		container.add(nuevoUsuario);

		//Boton Nuevo Usuario
		Btn crearUsuario=new Btn("Registrate",205, 245, 89, 16);
		container.add(crearUsuario);

		crearUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NuevoUsuario nuevo = new NuevoUsuario();
				nuevo.setVisible(true);
			}
		});
	}
}