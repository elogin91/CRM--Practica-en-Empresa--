package CRM.clientes;

/**
 * @author Ainhoa Lopez Bleda
 * @re-factor Cristina Álvarez López
 */

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import javax.swing.*;

import CRM.componentes.*;
import Conexion.Conexion;

public class CitaClientes extends JDialog {

	private static final long serialVersionUID = 1L;
	private String [] opcionesSelecionables;
	private Conexion con;
	private Connection conexion;
	private String fechaCita;
	public CitaClientes(String name, String id) {
		setResizable(false);
		setTitle("C I T A R   C L I E N T E S");
		setIconImage(Toolkit.getDefaultToolkit().getImage("Imagenes\\logo.png"));
		setBounds(1122, 10, 470, 270);
		getContentPane().setLayout(null);

		//Seleccionar la fecha de la cita
		Etiqueta cita = new Etiqueta("Cita",20, 25, 46, 20, 2  );
		getContentPane().add(cita);

		DateChooser fecha = new DateChooser(100, 25, 90, 20);
		getContentPane().add(fecha);

		//Nombre Seleccionable para la Cita
		Etiqueta etiquetaNombre = new Etiqueta("Nombre", 220, 95, 60, 20, 2 );
		getContentPane().add(etiquetaNombre);

		InputText nombreApellidos = new InputText(300, 95, 120, 20,10);
		nombreApellidos.setText(name);
		getContentPane().add(nombreApellidos);

		//IdCliente para la Cita
		Etiqueta etiquetaIdCliente = new Etiqueta("Id Cliente", 20, 95, 60, 20, 2 );
		getContentPane().add(etiquetaIdCliente);

		InputText idCliente = new InputText(100, 95, 50, 20,10);
		idCliente.setText(id);
		getContentPane().add(idCliente);

		//Concretar matricula
		Etiqueta etiquetaConcretarMatricula = new Etiqueta("Concreta Matricula",220, 130, 123, 20,2);
		getContentPane().add(etiquetaConcretarMatricula);

		opcionesSelecionables=new String[]{ "", "Si", "No" };
		ComboBox concretarMatricula = new ComboBox(opcionesSelecionables, 340, 130, 78, 20);
		getContentPane().add(concretarMatricula);

		//Hora de la cita
		Etiqueta etiquetaHora = new Etiqueta("Hora",20, 60, 49, 20,2 );
		getContentPane().add(etiquetaHora);

		InputText hora= new InputText(100, 60, 50, 20, 10);
		hora.setToolTipText("hh:MM");
		getContentPane().add(hora);

		//Seguimiento de la asistencia de la cita
		Etiqueta etiquetaAsistencia = new Etiqueta("Asistencia", 20, 130, 65, 20, 2);
		getContentPane().add(etiquetaAsistencia);

		opcionesSelecionables=new String[] { "","Pendiente", "Si", "No","Cancelación"};
		ComboBox asistencia = new ComboBox(opcionesSelecionables,100, 130, 80, 20 );
		getContentPane().add(asistencia);

		//Boton Añadir Cita
		Btn añadirCita = new Btn("AÑADIR",220, 180, 110, 23);
		getContentPane().add(añadirCita);

		añadirCita.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// CAMBIAMOS DE FORMATO LA FECHA
				SimpleDateFormat fechaSeleccionada = new SimpleDateFormat("yyyy-MM-dd");
				fechaCita = fechaSeleccionada.format(fecha.getCalendar().getTime());

				try {
					con = new Conexion();
					conexion = con.getConnection();

						// PROCEDEMOS A LA INSERCCION EN LA BBDD
						PreparedStatement comando = conexion.prepareStatement("insert into cita "
								+ "(Asiste_Cita, NombreApellidos, Fecha, Hora, Concretar_Matricula, idClientes ) "
								+ "values (?,?,?,?,?,?)");

						comando.setString(1, (String) asistencia.getSelectedItem());
						comando.setString(2, nombreApellidos.getText());
						comando.setString(3, fechaCita);
						comando.setString(4, hora.getText());
						comando.setString(5, (String) concretarMatricula.getSelectedItem());
						comando.setString(6, idCliente.getText());
						comando.executeUpdate();

						JOptionPane.showMessageDialog(null, "Cita creada", "INFORMACION",
								JOptionPane.INFORMATION_MESSAGE);
				}
				catch (SQLException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Cita no creada", "INFORMACION",
							JOptionPane.INFORMATION_MESSAGE);
				}
				finally {
					try {
						conexion.close();
						dispose();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});

		//Boton Modificar Cita
		Btn modificar = new Btn("MODIFICAR", 90, 180, 110, 23);
		getContentPane().add(modificar);

		// LOGO FINSE
		LogoFinse logoFinse= new LogoFinse("foto",120, -26, 180, 250);
		getContentPane().add(logoFinse);

	}
}
