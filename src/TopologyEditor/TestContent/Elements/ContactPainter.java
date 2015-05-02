package TopologyEditor.TestContent.Elements;

import TopologyEditor.Elements.Element;
import TopologyEditor.Elements.Painter;
import TopologyEditor.PrecisePoint;
import TopologyEditor.Utilities.CoordinateTranslator;

import java.awt.*;

/**
 * Created by VEF on 5/1/2015.
 */
public class ContactPainter implements Painter
{
    public void Draw(Element element, Graphics graphics, CoordinateTranslator translator)
    {
        PrecisePoint pos = translator.TranslateOut(element.getPosition());
        graphics.drawLine((int)pos.getX()-5, (int)pos.getY()-5, (int)pos.getX()+5, (int)pos.getY()+5);
        graphics.drawLine((int)pos.getX()+5, (int)pos.getY()-5, (int)pos.getX()-5, (int)pos.getY()+5);
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
