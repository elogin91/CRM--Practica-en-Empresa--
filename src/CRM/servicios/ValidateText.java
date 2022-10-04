package CRM.servicios;

import CRM.componentes.InputText;

import javax.swing.*;
import java.lang.reflect.Array;

public class ValidateText {
    public static void validatingNuevoContacto(JTextField apellido, JComboBox seguimiento, JComboBox interes, JComboBox responsable, JComboBox cita) {
        if (!apellido.getText().trim().isEmpty()) {
            if (!seguimiento.getSelectedItem().equals("")) {
                if (!interes.getSelectedItem().equals("")) {
                    if (!responsable.getSelectedItem().equals("")) {
                        if (!cita.getSelectedItem().equals("")) {

                        } else {
                            JOptionPane.showMessageDialog(null, "¿Ha concertado cita?");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Seleccione un Responsable");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione el curso de interes");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Seleccione una opcion de resumen");
            }
        } else {
            JOptionPane.showMessageDialog(null, "El nombre no puede estar vacio");
        }
    }
}