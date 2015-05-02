package TopologyEditor.TestContent.Elements;

import TopologyEditor.Elements.Element;
import TopologyEditor.Elements.IPainter;
import TopologyEditor.PrecisePoint;
import TopologyEditor.Utilities.ICoordinateTranslator;

import java.awt.*;

/**
 * Created by VEF on 5/1/2015.
 */
public class ContactPainter implements IPainter
{
    public void Draw(Element element, Graphics2D graphics, ICoordinateTranslator translator)
    {
        PrecisePoint pos = translator.TranslatePointOut(element.getPosition());
        double diameter = translator.TranslateValueOut(((Contact)element).getSize());

        graphics.setColor(Color.pink);
        graphics.fillOval((int) pos.getX(), (int) pos.getY(), (int) diameter, (int) diameter);
        graphics.setColor(Color.black);
        graphics.drawOval((int) pos.getX(), (int) pos.getY(), (int) diameter, (int) diameter);
    }

    public boolean IsOnScreen(Element element, PrecisePoint leftTop, PrecisePoint rightBottom)
    {
        PrecisePoint pos = element.getPosition();
        double x = pos.getX();
        double y = pos.getY();
        double size = ((Contact)element).getSize();

        return  x * x + y * y <= size * size;
    }

    public boolean IsClicked(Element element, PrecisePoint point)
    {
        return IsOnScreen(element, point, point);
    }
}
