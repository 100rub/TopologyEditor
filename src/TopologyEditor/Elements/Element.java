package TopologyEditor.Elements;

import TopologyEditor.PrecisePoint;
import TopologyEditor.VirtualPane;

import java.awt.*;

/**
 * Created by VEF on 23.04.2015.
 */
public abstract class Element
{
    private PrecisePoint _position;
    private VirtualPane _parent;



    public PrecisePoint getPosition()
    {
        return _position;
    }

    public void setPosition(PrecisePoint value)
    {
        _position = value;
    }

    public abstract boolean IsOnScreen(PrecisePoint leftTop, PrecisePoint rightBottom);

    public abstract boolean IsClicked(PrecisePoint point);

    public abstract void Draw(Graphics graphics, int layer);
}
