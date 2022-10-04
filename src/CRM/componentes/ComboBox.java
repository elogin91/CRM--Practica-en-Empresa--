package CRM.componentes;

import javax.swing.*;
import java.awt.*;

public class ComboBox extends JComboBox {
    public ComboBox(String[] mia, int positionX, int positionY, int width, int height) {
        setModel(new DefaultComboBoxModel<String>(mia));
        setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
        setBounds(positionX, positionY, width, height);

    }
}
