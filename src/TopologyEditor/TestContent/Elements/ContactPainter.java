package TopologyEditor.TestContent.Elements;

import TopologyEditor.Elements.Element;
import TopologyEditor.Elements.IPainter;
import TopologyEditor.Utilities.G2DHelper;
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
        double diameter = translator.TranslateValueOut(((Contact) element).getSize());
        int x = (int)(pos.getX() - diameter / 2);
        int y = (int)(pos.getY() - diameter / 2);

        graphics.setColor(Color.pink);
        graphics.fillOval(x, y, (int)diameter, (int)diameter);
        graphics.setColor(Color.black);
        graphics.drawOval(x, y, (int) diameter, (int) diameter);
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
        double size = ((Contact)element).getSize() / 2;

        return  x*x + y*y <= size*size;
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
