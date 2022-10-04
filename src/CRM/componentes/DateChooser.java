package CRM.componentes;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class DateChooser extends JDateChooser {

    public DateChooser(int positionX, int positionY, int width, int height) {
        super("yyyy-MM-dd", " ####-##-##", '_');
        setBounds(positionX, positionY, width, height);
    }

}
