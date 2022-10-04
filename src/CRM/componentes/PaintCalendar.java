package CRM.componentes;

import com.toedter.calendar.JCalendar;

public class PaintCalendar extends JCalendar {

    public PaintCalendar(int positionX, int positionY, int width, int height) {
        super();
        setWeekOfYearVisible(false);
        setMaxDayCharacters(1);
        setBounds(positionX, positionY, width, height);
    }
}
