package CRM.componentes;

import javax.swing.*;
import java.awt.*;

public class InputText extends JTextField {

    public InputText(int positionX, int positionY, int width, int height, int columns) {
        setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
        setBounds(positionX, positionY, width, height);
        setColumns(columns);
    }
}
