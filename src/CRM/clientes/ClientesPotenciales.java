package CRM.clientes;

/**
 * @author Ainhoa Lopez Bleda
 * * @refactor Cristina Álvarez
 */

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;

import javax.swing.JOptionPane;

import CRM.componentes.*;

import Conexion.Conexion;
import Rutinas.esNumerico;


import static CRM.clientes.Cliente.getIdClient;
import static CRM.servicios.CleanForm.cleanForm;
import static CRM.servicios.Eventos.controlCaracteres;
import static CRM.servicios.Eventos.validationPhoneNumber;
import static CRM.servicios.ValidateText.validatingNuevoContacto;

public class ClientesPotenciales extends JFrame {

	private static final long serialVersionUID = 1L;
	private Connection conexion;
	private Conexion con;
	private Date fec;
	private String fechaFormateada;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	String[] opcionesDesplegables;

	public ClientesPotenciales() {
		setTitle("C L I E N T E S  P O T E N C I A L E S");
		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(ClientesPotenciales.class.getResource("/Imagenes/c-potenciales.png")));
		setBounds(642, 10, 470, 472);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);

		//Contenedor de elementos.
		Container container=new Container();
		setContentPane(container);

		// FORMULARIO

		//FECHA DEL CONTACTO
		Etiqueta etiquetaFechaContacto = new Etiqueta("Fecha Contacto",20, 33, 107, 14, 2);
		getContentPane().add(etiquetaFechaContacto);

		DateChooser fechaContacto = new DateChooser(150, 30, 90, 20);
		getContentPane().add(fechaContacto);

		//NOMBRE Y APELLIDOS
		Etiqueta etiquetaNombreApellidos = new Etiqueta("Nombre y Apellidos",20, 63, 119, 14, 2);
		getContentPane().add(etiquetaNombreApellidos);

		//ENTRADA DE NOMBRE Y APELLIDOS
		InputText nombreApellidos= new InputText(150, 60, 170, 20, 10);
		getContentPane().add(nombreApellidos);

		//EVENTO EVITANDO CARACTERES ESPECIALES  EN NOMBRE Y APELLIDOS
		controlCaracteres(nombreApellidos);

		//TIPO DOCUMENTO IDENTIFICATIVO
		Etiqueta etiquetaTipoDocIdentidad = new Etiqueta("Tipo doc. identidad",20, 93, 119, 14, 2);
		getContentPane().add(etiquetaTipoDocIdentidad);

		opcionesDesplegables = new String[]{ "", "DNI", "NIE", "En tramite", "Pasaporte" };
		ComboBox tipoDocIdentificativo =new ComboBox(opcionesDesplegables,150, 90, 90, 20);
		getContentPane().add(tipoDocIdentificativo);

		//ETAPAS DE SEGUIMIENTO DEL CLIENTE
		Etiqueta etiquetaSeguimiento = new Etiqueta("Seguimiento",20, 123, 119, 14,2);
		getContentPane().add(etiquetaSeguimiento);

		opcionesDesplegables = new String[]{ "", "Insistir comunicación", "Descartado", "Reprogramar cita", "Tiene cita", "Matriculado", "Seguimiento" };
		ComboBox seguimiento =new ComboBox(opcionesDesplegables,150, 120, 170, 20);
		getContentPane().add(seguimiento);

		//TELEFONO
		Etiqueta etiquetaTelefono = new Etiqueta("Telefono",20, 153, 56, 14,2);
		getContentPane().add(etiquetaTelefono);

		InputText telefono = new InputText(150, 150, 90, 20, 10);
		getContentPane().add(telefono);

		//Evento ingresar solo números en el telefono
		validationPhoneNumber(telefono);

		//CURSO EN EL QUE TIENE INTERES
		Etiqueta etiquetaInteres = new Etiqueta("Curso Interes", 20, 183, 89, 14,2);
		getContentPane().add(etiquetaInteres);

		opcionesDesplegables=new String[]{ };
		ComboBox seleccionarInteres = new ComboBox(opcionesDesplegables,150, 180, 170, 20);
		getContentPane().add(seleccionarInteres);

		seleccionarInteres.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String completo = (String) seleccionarInteres.getSelectedItem();
				String[] completoSeparado = completo.split(" ");
			}
		});
		// CARGAMOS EL COMBO
		try {
			con = new Conexion();
			conexion = con.getConnection();

			PreparedStatement inte = conexion.prepareStatement("SELECT * from cursos");
			ResultSet res = inte.executeQuery();
			seleccionarInteres.addItem("");

			while (res.next()) {
				seleccionarInteres.addItem(res.getString("Nombre_Curso"));
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

		// Canal de conocimiento de la academia
		Etiqueta etiquetaCanal = new Etiqueta("Canal",20, 213, 56, 14, 2);
		getContentPane().add(etiquetaCanal);

		opcionesDesplegables=new String[]{ };
		ComboBox canal = new ComboBox(opcionesDesplegables,150, 215, 170, 20);
		getContentPane().add(canal);

		canal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String completo = (String) canal.getSelectedItem();
				String[] completoSeparado = completo.split(" ");
			}
		});

		// CARGAMOS EL COMBO
		try {
			con = new Conexion();
			conexion = con.getConnection();

			PreparedStatement inte = conexion.prepareStatement("SELECT * from canales");
			ResultSet res = inte.executeQuery();
			canal.addItem("");

			while (res.next()) {
				canal.addItem(res.getString("Nombre_Canal"));
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

		// COMBO RESPONSABLE
		Etiqueta etiquetaResponsable = new Etiqueta("Responsable",20, 243, 89, 14, 2);
		getContentPane().add(etiquetaResponsable);

		opcionesDesplegables=new String[]{ };
		ComboBox responsable = new ComboBox(opcionesDesplegables,150, 240, 138, 20);
		getContentPane().add(responsable);

		responsable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String completo = (String) responsable.getSelectedItem();
				String[] completoSeparado = completo.split(" ");
			}
		});

		// CARGAMOS EL COMBO
		try {
			con = new Conexion();
			conexion = con.getConnection();

			PreparedStatement respon = conexion.prepareStatement("SELECT * from usuarios");
			ResultSet res = respon.executeQuery();
			responsable.addItem("");

			while (res.next()) {
				responsable.addItem(res.getString("Responsable"));
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

		//Concretar Cita
		Etiqueta etiquetaConcretarCita = new Etiqueta("Concreta_Cita",20, 273, 89, 14,2);
		getContentPane().add(etiquetaConcretarCita);

		opcionesDesplegables = new String[]{ "", "Si", "No"  };
		ComboBox concretarCita =new ComboBox(opcionesDesplegables,150, 270, 56, 20);
		getContentPane().add(concretarCita);

		//Detalles
		Etiqueta etiquetaDetalles = new Etiqueta("Detalles",20, 303, 56, 34,2);
		getContentPane().add(etiquetaDetalles);

		InputText detalles = new InputText(150, 300, 215, 50, 10);
		getContentPane().add(detalles);

		// BOTON AÑADIR
		Btn añadirContacto=new Btn("AÑADIR",45, 365, 95, 23);
		getContentPane().add(añadirContacto);

		// VALIDAMOS CAMPOS VACIOS
		añadirContacto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				validatingNuevoContacto(nombreApellidos, seguimiento, canal, responsable, concretarCita);

				// CAMBIAMOS DE FORMATO LA FECHA
				fec = fechaContacto.getDate();
				if (fec != null) {
					fechaFormateada = sdf.format(fec);
				} else {
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
						PreparedStatement comando = conexion.prepareStatement("insert into clientespotenciales "
								+ "(Fecha_Contacto, NombreApellidos, Tipo_Documento, Resumen, Telefono, Curso_Interes, Canal, Responsable, Concretar_Cita, Detalles) "
								+ "values (?,?,?,?,?,?,?,?,?,?)");

						comando.setString(1, fechaFormateada);
						comando.setString(2, nombreApellidos.getText());
						comando.setString(3, (String) tipoDocIdentificativo.getSelectedItem());
						comando.setString(4, (String) seguimiento.getSelectedItem());
						comando.setString(5, telefono.getText());
						comando.setString(6, (String) seleccionarInteres.getSelectedItem());
						comando.setString(7, (String) canal.getSelectedItem());
						comando.setString(8, (String) responsable.getSelectedItem());
						comando.setString(9, (String) concretarCita.getSelectedItem());
						comando.setString(10, detalles.getText());
						comando.executeUpdate();

						JOptionPane.showMessageDialog(null, "Cliente insertado", "INFORMACION",
								JOptionPane.INFORMATION_MESSAGE);

						// Si se selecciona el valor si en citar-> se abre ventana crear cita.
						String comprobarValor="Si";
						if(comprobarValor.equals(concretarCita.getSelectedItem().toString())) {
							CitaClientes citar = new CitaClientes(nombreApellidos.getText(),getIdClient(telefono.getText()));
							citar.setVisible(true);
						}
					} else {
						JOptionPane.showMessageDialog(null, "Telefono incorrecto");
					}

				} catch (SQLException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Cliente no insertado", "INFORMACION",
							JOptionPane.INFORMATION_MESSAGE);
				} finally {
					try {
						conexion.close();
						dispose();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});

		// BOTON BUSCAR
		Btn listado = new Btn("BUSCAR", 260, 365, 100, 23);
		getContentPane().add(listado);

		//Evento abrir un listado.
		listado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listadoClientes lista = new listadoClientes();
				lista.setVisible(true);
			}
		});

		// BOTON LIMPIAR
		Btn limpiar = new Btn("LIMPIAR",150, 365, 95, 23 );
		getContentPane().add(limpiar);

		// EVENTO VACIAR EL FORMULARIO
		limpiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cleanForm(fechaContacto, nombreApellidos, tipoDocIdentificativo,seguimiento,telefono, seleccionarInteres,canal, concretarCita,responsable,detalles);
			}
		});

		// LOGO FINSE
		LogoFinse logoFinse= new LogoFinse("foto",230, 10, 180, 389);
		getContentPane().add(logoFinse);
	}
}
