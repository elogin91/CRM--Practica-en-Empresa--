package CRM.clientes;

/**
 * @author Ainhoa Lopez Bleda
 * @re-factor Cristina Álvarez López
 */

import java.awt.Toolkit;

import javax.swing.JDialog;

import CRM.componentes.*;

public class CitaClientes extends JDialog {

	private static final long serialVersionUID = 1L;
	private String [] opcionesSelecionables;
	public CitaClientes() {
		setResizable(false);
		setTitle("C I T A R   C L I E N T E S");
		setIconImage(Toolkit.getDefaultToolkit().getImage("Imagenes\\logo.png"));
		setBounds(425, 130, 350, 300);
		getContentPane().setLayout(null);

		//Seleccionar la fecha de la cita
		Etiqueta cita = new Etiqueta("Cita",20, 60, 46, 20, 2  );
		getContentPane().add(cita);

		DateChooser fecha = new DateChooser(100, 60, 90, 20);
		getContentPane().add(fecha);

		//Nombre Seleccionable para la Cita
		Etiqueta etiquetaNombre = new Etiqueta("Nombre", 20, 130, 60, 20, 2 );
		getContentPane().add(etiquetaNombre);

		InputText nombreApellidos = new InputText(100, 130, 160, 20,10);
		getContentPane().add(nombreApellidos);

		//Concretar matricula
		Etiqueta etiquetaConcretarMatricula = new Etiqueta("Concretar Matricula",20, 165, 123, 20,2);
		getContentPane().add(etiquetaConcretarMatricula);

		opcionesSelecionables=new String[]{ "", "Si", "No" };
		ComboBox concretarMatricula = new ComboBox(opcionesSelecionables, 150, 165, 78, 20);
		getContentPane().add(concretarMatricula);

		//Hora de la cita
		Etiqueta etiquetaHora = new Etiqueta("Hora",20, 95, 49, 20,2 );
		getContentPane().add(etiquetaHora);

		InputText hora= new InputText(100, 95, 86, 20, 10);
		hora.setToolTipText("hh:MM");
		getContentPane().add(hora);

		//Seguimiento de la asistencia de la cita
		Etiqueta etiquetaAsistencia = new Etiqueta("Asistencia", 20, 25, 65, 20, 2);
		getContentPane().add(etiquetaAsistencia);

		opcionesSelecionables=new String[] { "","Pendiente", "Si", "No","Cancelación"};
		ComboBox asistencia = new ComboBox(opcionesSelecionables,100, 25, 80, 20 );
		getContentPane().add(asistencia);

		//Boton Añadir Cita
		Btn añadirCita = new Btn("AÑADIR",10, 208, 110, 23);
		getContentPane().add(añadirCita);

		//Boton Modificar Cita
		Btn modificar = new Btn("MODIFICAR", 145, 210, 110, 23);
		getContentPane().add(modificar);

		// LOGO FINSE
		LogoFinse logoFinse= new LogoFinse("foto",150, -26, 180, 389);
		getContentPane().add(logoFinse);

	}
}
