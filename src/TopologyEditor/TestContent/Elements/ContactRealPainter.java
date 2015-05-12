package TopologyEditor.TestContent.Elements;

import TopologyEditor.Elements.Element;
import TopologyEditor.Elements.IPainter;
import TopologyEditor.Utilities.ICoordinateTranslator;
import TopologyEditor.Utilities.PrecisePoint;

import java.awt.*;

/**
 * Created by VEF on 5/4/2015.
 */
public class ContactRealPainter implements IPainter
{
    private final static BasicStroke dashedStroke = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, new float[] {10.0f}, 0.0f);

    private void DrawLine(Graphics2D graphics, PrecisePoint a, PrecisePoint b)
    {
        graphics.drawLine((int)a.getX(), (int)a.getY(), (int)b.getX(), (int)b.getY());
    }

    private void DrawRect(Graphics2D graphics, PrecisePoint center, double halfSide)
    {
        PrecisePoint a = center.CopyShift(-halfSide, -halfSide);
        PrecisePoint b = center.CopyShift(-halfSide, halfSide);
        PrecisePoint c = center.CopyShift(halfSide, halfSide);
        PrecisePoint d = center.CopyShift(halfSide, -halfSide);

        DrawLine(graphics, a, b);
        DrawLine(graphics, b, c);
        DrawLine(graphics, c, d);
        DrawLine(graphics, d, a);
    }

    public void Draw(Element element, Graphics2D graphics, ICoordinateTranslator translator)
    {
        Contact c = (Contact)element;
        PrecisePoint pos = translator.TranslatePointOut(c.getPosition());
        double size = translator.TranslateValueOut(c.getSize()) / 2;
        double innerSize = translator.TranslateValueOut(c.getInnerSize()) / 2;

        graphics.setColor(Color.black);
        DrawRect(graphics, pos, size);

        graphics.setColor(Color.pink);
        graphics.setStroke(dashedStroke);
        DrawRect(graphics, pos, innerSize);
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
        return IsOnScreen(element, point, point);
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
