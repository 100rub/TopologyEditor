package TopologyEditor.TestContent.Elements;

import TopologyEditor.Elements.Element;
import TopologyEditor.Elements.IPainter;
import TopologyEditor.Utilities.G2DHelper;
import TopologyEditor.Utilities.ICoordinateTranslator;
import TopologyEditor.Utilities.PrecisePoint;

import java.awt.*;

/**
 * Created by VEF on 5/4/2015.
 */
public class ContactRealPainter implements IPainter
{
    public void Draw(Element element, Graphics2D graphics, ICoordinateTranslator translator)
    {
        Contact c = (Contact)element;
        PrecisePoint pos = translator.TranslatePointOut(c.getPosition());
        double size = translator.TranslateValueOut(c.getSize()) / 2;
        double innerSize = translator.TranslateValueOut(c.getInnerSize()) / 2;

        graphics.setColor(Color.black);
        G2DHelper.DrawRect(graphics, pos, size);

        graphics.setColor(Color.pink);
        graphics.setStroke(G2DHelper.GetDashedStroke());
        G2DHelper.DrawRect(graphics, pos, innerSize);
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
        double size = ((Contact)element).getSize() / 2;

        return  x + size > leftTop.getX() &&
                x - size < rightBottom.getX() &&
                y + size > rightBottom.getY() &&
                y - size < leftTop.getY();
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
        double size = ((Contact)element).getSize() / 2;

        return  x - size > leftTop.getX() &&
                x + size < rightBottom.getX() &&
                y - size > rightBottom.getY() &&
                y + size < leftTop.getY();
    }
}
