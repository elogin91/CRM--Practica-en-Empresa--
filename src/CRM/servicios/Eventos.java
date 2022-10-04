package CRM.servicios;

import CRM.componentes.InputText;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Eventos {

    //Eventos de InputText

    //No permitir caracteres especiales -> previene la injeccion de SQL
    public static void controlCaracteres (InputText text) {
        text.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (text.getText().length() == 150) {
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
    }

    public static void validationPhoneNumber(InputText phoneNumber) {
        phoneNumber.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (phoneNumber.getText().length() == 9) {
                    e.consume();
                }
                char validar = e.getKeyChar();
                if (Character.isLetter(validar)) {
                    e.consume();
                    JOptionPane.showMessageDialog(null, "Ingresa solo números");
                }
            }
        });
    }

    //EVENTOS DE ComboBOX


}
