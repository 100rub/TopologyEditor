package TopologyEditor.Elements;

import TopologyEditor.Utilities.PrecisePoint;
import TopologyEditor.Utilities.ICoordinateTranslator;

import java.awt.*;

/**
 * Created by VEF on 5/1/2015.
 */
public interface IPainter
{
    public void Draw(Element element, Graphics2D graphics, ICoordinateTranslator translator);

    public boolean IsOnScreen(Element element, PrecisePoint leftTop, PrecisePoint rightBottom);

    public boolean IsClicked(Element element, PrecisePoint point);
}
