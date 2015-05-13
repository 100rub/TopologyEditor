package TopologyEditor.Elements;

import TopologyEditor.Utilities.PrecisePoint;
import TopologyEditor.Utilities.ICoordinateTranslator;

import java.awt.*;

/**
 * Created by VEF on 5/1/2015.
 */
public interface IPainter
{
    void Draw(Element element, Graphics2D graphics, ICoordinateTranslator translator);

    void DrawBorder(Element element, Graphics2D graphics, ICoordinateTranslator translator);

    boolean IsOnScreen(Element element, PrecisePoint leftTop, PrecisePoint rightBottom);

    boolean IsClicked(Element element, PrecisePoint point);

    boolean IsSelected(Element element, PrecisePoint leftTop, PrecisePoint rightBottom);
}
