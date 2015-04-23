import javax.swing.*;
import java.awt.*;

/**
 * Created by 100rub on 14.04.2015.
 */
public class JDrawPanel extends JPanel
{


    public void paint(Graphics g)
    {
        super.paint(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        //g2d.setColor(new Color(0, 0, 200));
        //g2d.fill(myRect);
    }
}
