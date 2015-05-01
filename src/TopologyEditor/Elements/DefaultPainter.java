package TopologyEditor.Elements;

import TopologyEditor.PrecisePoint;
import TopologyEditor.TestContent.Elements.Contact;

import java.awt.*;

/**
 * Created by VEF on 5/1/2015.
 */
public class DefaultPainter implements Painter
{
    public void Draw(Element element, Graphics graphics)
    {

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
