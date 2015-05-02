package TopologyEditor.Elements;

import TopologyEditor.PrecisePoint;
import TopologyEditor.TestContent.Elements.Contact;
import TopologyEditor.Utilities.CoordinateTranslator;

import java.awt.*;

/**
 * Created by VEF on 5/1/2015.
 */
public class DefaultPainter implements Painter
{
    public void Draw(Element element, Graphics2D graphics, CoordinateTranslator translator)
    {
        PrecisePoint pos = translator.TranslatePointOut(element.getPosition());
        graphics.setColor(Color.blue);
        graphics.drawLine((int)pos.getX()-5, (int)pos.getY()-5, (int)pos.getX()+5, (int)pos.getY()+5);
        graphics.drawLine((int)pos.getX()+5, (int)pos.getY()-5, (int)pos.getX()-5, (int)pos.getY()+5);
    }

    public boolean IsOnScreen(Element element, PrecisePoint leftTop, PrecisePoint rightBottom)
    {
        PrecisePoint pos = element.getPosition();
        return  pos.getX() > leftTop.getX() &&
                pos.getY() < leftTop.getY() &&
                pos.getX() < rightBottom.getX() &&
                pos.getY() > rightBottom.getY();
    }

    public boolean IsClicked(Element element, PrecisePoint point)
    {
        return IsOnScreen(element, point, point);
    }
}
