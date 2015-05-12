package TopologyEditor.TestContent.Elements;

import TopologyEditor.Elements.Element;
import TopologyEditor.Elements.IPainter;
import TopologyEditor.Utilities.PrecisePoint;
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
        int x = (int)(pos.getX() - diameter / 2);
        int y = (int)(pos.getY() - diameter / 2);

        graphics.setColor(Color.pink);
        graphics.fillOval(x, y, (int)diameter, (int)diameter);
        graphics.setColor(Color.black);
        graphics.drawOval(x, y, (int)diameter, (int)diameter);
    }

    public boolean IsOnScreen(Element element, PrecisePoint leftTop, PrecisePoint rightBottom)
    {
        PrecisePoint pos = element.getPosition();
        double x = pos.getX();
        double y = pos.getY();
        double size = ((Contact)element).getSize();

        return  x + size > leftTop.getX() &&
                x - size < rightBottom.getX() &&
                y + size > rightBottom.getY() &&
                y - size < leftTop.getY();
    }

    public boolean IsClicked(Element element, PrecisePoint point)
    {
        PrecisePoint pos = element.getPosition();
        double x = pos.getX() - point.getX();
        double y = pos.getY() - point.getY();
        double size = ((Contact)element).getSize();

        return  x*x + y*y <= size*size/4;
    }

    public boolean IsSelected(Element element, PrecisePoint leftTop, PrecisePoint rightBottom)
    {
        PrecisePoint pos = element.getPosition();
        double x = pos.getX();
        double y = pos.getY();
        double size = ((Contact)element).getSize();

        return  x - size > leftTop.getX() &&
                x + size < rightBottom.getX() &&
                y - size > rightBottom.getY() &&
                y + size < leftTop.getY();
    }
}
