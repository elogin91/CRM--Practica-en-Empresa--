package CRM.componentes;

import javax.swing.*;
import java.awt.*;

public class LogoFinse extends JLabel {
    private static final long serialVersionUID = 1L;
    public LogoFinse(String name, int positionX, int positionY, int width, int height) {
        ImageIcon imagen = new ImageIcon("Imagenes\\fondo_pantalla.png");
        setBounds(positionX,positionY,width,height);
        ImageIcon icono = new ImageIcon(imagen.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_DEFAULT));
        setIcon(icono);
    }
}
