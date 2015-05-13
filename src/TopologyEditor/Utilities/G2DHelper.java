package TopologyEditor.Utilities;

import java.awt.*;

/**
 * Created by VEF on 5/13/2015.
 */
public class G2DHelper
{
    private final static Stroke _dashedStroke = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, new float[] {10.0f}, 0.0f);

    public static Stroke GetDashedStroke()
    {
        return _dashedStroke;
    }

    public static void DrawLine(Graphics2D graphics, PrecisePoint a, PrecisePoint b)
    {
        graphics.drawLine((int)a.getX(), (int)a.getY(), (int)b.getX(), (int)b.getY());
    }

    public static void DrawRect(Graphics2D graphics, PrecisePoint center, double halfSide)
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

}
