package TopologyEditor.Elements;

import TopologyEditor.TestContent.Elements.Contact;
import TopologyEditor.Utilities.G2DHelper;
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

    public void DrawBorder(Element element, Graphics2D graphics, ICoordinateTranslator translator)
    {
        Contact c = (Contact)element;
        PrecisePoint pos = translator.TranslatePointOut(c.getPosition());
        double size = translator.TranslateValueOut(c.getSize()) / 2 * 1.2;

        graphics.setStroke(G2DHelper.GetDashedStroke());
        G2DHelper.DrawRect(graphics, pos, size);
    }

    public boolean IsOnScreen(Element element, PrecisePoint leftTop, PrecisePoint rightBottom)
    {
        PrecisePoint pos = element.getPosition();
        double x = pos.getX();
        double y = pos.getY();

        return  x + 5 > leftTop.getX() &&
                x - 5 < rightBottom.getX() &&
                y + 5 > rightBottom.getY() &&
                y - 5 < leftTop.getY();
    }

    public boolean IsClicked(Element element, PrecisePoint point)
    {
        return IsOnScreen(element, point, point);
    }

    public boolean IsSelected(Element element, PrecisePoint leftTop, PrecisePoint rightBottom)
    {
        PrecisePoint pos = element.getPosition();
        double x = pos.getX();
        double y = pos.getY();

        return  x - 5 > leftTop.getX() &&
                x + 5 < rightBottom.getX() &&
                y - 5 > rightBottom.getY() &&
                y + 5 < leftTop.getY();
    }
}
