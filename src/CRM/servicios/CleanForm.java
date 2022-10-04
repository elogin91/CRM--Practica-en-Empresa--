package CRM.servicios;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.util.Date;

public class CleanForm {
    public static void cleanForm(JDateChooser fecha, JTextField nombreApellidos, JComboBox comboDocumento, JComboBox comboResumen, JTextField telefono, JComboBox comboInteres, JComboBox comboCanal, JComboBox comboResponsable, JComboBox comboCita, JTextField detalles){
        fecha.setDateFormatString(null);
        nombreApellidos.setText("");
        comboDocumento.setSelectedItem("");
        comboResumen.setSelectedItem("");
        telefono.setText("");
        comboInteres.setSelectedItem("");
        comboCanal.setSelectedItem("");
        comboResponsable.setSelectedItem("");
        comboCita.setSelectedItem("");
        detalles.setText("");
    }
}
