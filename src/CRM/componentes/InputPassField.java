package CRM.componentes;

import javax.swing.*;
import java.awt.*;

public class InputPassField extends JPasswordField {
    private static final long serialVersionUID = 1L;

    public InputPassField(int positionX, int positionY, int width, int height) {
        setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
        setBounds(positionX, positionY, width, height);
    }
}
