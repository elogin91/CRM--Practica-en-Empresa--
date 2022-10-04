package CRM.calendario;

/**
 * @author Cristina Álvarez López
 */

import CRM.clientes.ResulBusqueda;
import CRM.componentes.*;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

public class Calendario extends JFrame {

    private static final long serialVersionUID = 1L;
    private String fechaBusqueda;
    private ResulBusqueda resulBusqueda = null;
    public Calendario() {

        setTitle("C A L E N D A R I O");
        setBounds(723, 100, 450, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setIconImage(Toolkit.getDefaultToolkit().getImage("Imagenes\\logo.png"));
        getContentPane().setLayout(null);

        //ETIQUETA DEL CALENDARIO
        Etiqueta etiquetaCalendar = new Etiqueta("Seleccione el día a consultar:",10, 10, 300, 20, 2);
        getContentPane().add(etiquetaCalendar);

        //PINTAR EL OBJETO CALENDARIO
        PaintCalendar fecha = new PaintCalendar(10, 40, 200, 200);
        getContentPane().add(fecha);

        //BOTÓN BUSCAR
        Btn searchButton = new Btn("BUSCAR", 290, 208, 110, 23);
        getContentPane().add(searchButton);

        //FONDO DE LOGO FINSE
        LogoFinse logoFinse = new LogoFinse("foto", 246, 10, 180, 222);
        getContentPane().add(logoFinse);

        //Evento introducir fecha y mostrar un listado de los clientes en esa fecha
        searchButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                if (resulBusqueda == null) {
                    //OBTENGO LA FECHA SELECCIONADA
                    SimpleDateFormat fechaSeleccionada = new SimpleDateFormat("yyyy-MM-dd");
                    fechaBusqueda = fechaSeleccionada.format(fecha.getCalendar().getTime());

                    // ABRE VENTANA CON RESULTADO DE LA PRIMERA BUSQUEDA
                    resulBusqueda =new ResulBusqueda(fechaBusqueda, "Fecha_Contacto");
                    resulBusqueda.setVisible(true);
                } else {
                    //OBTENGO LA FECHA SELECCIONADA
                    SimpleDateFormat fechaSeleccionada = new SimpleDateFormat("yyyy-MM-dd");
                    fechaBusqueda = fechaSeleccionada.format(fecha.getCalendar().getTime());

                    //CIERRO LA VENTANA DE LA BUSQUEDA ANTERIOR
                    resulBusqueda.dispose();

                    //ABRO UNA NUEVA VENTANA
                    resulBusqueda =new ResulBusqueda(fechaBusqueda, "Fecha_Contacto");
                    resulBusqueda.setVisible(true);
                }
            }
        });
    }
}
