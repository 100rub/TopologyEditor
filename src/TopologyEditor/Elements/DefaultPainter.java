package TopologyEditor.Elements;

import TopologyEditor.Utilities.PrecisePoint;
import TopologyEditor.Utilities.ICoordinateTranslator;

import java.awt.*;

/**
 * Created by VEF on 5/1/2015.
 */
public class DefaultPainter implements IPainter
{
    public void Draw(Element element, Graphics2D graphics, ICoordinateTranslator translator)
    {
        PrecisePoint pos = translator.TranslatePointOut(element.getPosition());
        graphics.setColor(Color.blue);
        graphics.drawLine((int)pos.getX()-5, (int)pos.getY()-5, (int)pos.getX()+5, (int)pos.getY()+5);
        graphics.drawLine((int)pos.getX()+5, (int)pos.getY()-5, (int)pos.getX()-5, (int)pos.getY()+5);
        graphics.drawString(element.GetName(), (int)pos.getX()-5, (int)pos.getY()+15);
    }

    public boolean IsOnScreen(Element element, PrecisePoint leftTop, PrecisePoint rightBottom)
    {
        PrecisePoint pos = element.getPosition();

        return  pos.getX() - 5 > leftTop.getX() &&
                pos.getY() + 5 < leftTop.getY() &&
                pos.getX() + 5 < rightBottom.getX() &&
                pos.getY() - 5 > rightBottom.getY();
    }

    public boolean IsClicked(Element element, PrecisePoint point)
    {
        return IsOnScreen(element, point, point);
    }
}
