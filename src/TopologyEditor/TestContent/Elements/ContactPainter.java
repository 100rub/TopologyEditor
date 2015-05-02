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
        return  pos.getX() + ((Contact)element).getSize() > leftTop.getX() &&
                pos.getY() - ((Contact)element).getSize() < leftTop.getY() &&
                pos.getX() - ((Contact)element).getSize() < rightBottom.getX() &&
                pos.getY() + ((Contact)element).getSize() > rightBottom.getY();
    }

    public boolean IsClicked(Element element, PrecisePoint point)
    {
        return IsOnScreen(element, point, point);
    }
}
