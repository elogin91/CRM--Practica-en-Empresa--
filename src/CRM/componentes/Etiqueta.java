package CRM.componentes;

import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.SwingConstants;


public class Etiqueta extends JLabel {
    private static final long serialVersionUID = 1L;
    public Etiqueta(String name, int positionX, int positionY, int width, int height, int align) {
        super(name);
        setHorizontalAlignment(align);
        setBounds(positionX, positionY, width, height);
        setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));

    }
}
