package TopologyEditor.Elements;

import TopologyEditor.PrecisePoint;
import TopologyEditor.Utilities.CoordinateTranslator;

import java.awt.*;

/**
 * Created by VEF on 5/1/2015.
 */
public interface Painter
{
    public void Draw(Element element, Graphics graphics, CoordinateTranslator translator);

    public boolean IsOnScreen(Element element, PrecisePoint leftTop, PrecisePoint rightBottom);

    public boolean IsClicked(Element element, PrecisePoint point);
}
