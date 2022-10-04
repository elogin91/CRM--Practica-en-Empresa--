package CRM.componentes;

import javax.swing.*;
import java.awt.Font;
public class Btn extends JButton {
    private static final long serialVersionUID = 1L;

    public Btn(String name, int positionX, int positionY, int width, int height) {
        super(name);
        setFont(new Font("Segoe UI Semibold", Font.PLAIN, 12));
        setBounds(positionX, positionY, width, height);
    }
}
