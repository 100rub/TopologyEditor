package TopologyEditor.TestContent.Elements;

import TopologyEditor.Elements.Element;
import TopologyEditor.PrecisePoint;

import java.awt.*;

/**
 * Created by VEF on 23.04.2015.
 */
public class Contact extends Element
{
    private double _size;



    public double getSize()
    {
        return _size;
    }

    public void setSize(double value)
    {
        _size = value;
    }

    public void Draw(Graphics graphics, int layer)
    {

    }

    public boolean IsOnScreen(PrecisePoint leftTop, PrecisePoint rightBottom)
    {
        PrecisePoint pos = getPosition();
        return  pos.getX() + _size > leftTop.getX() &&
                pos.getY() - _size < leftTop.getY() &&
                pos.getX() - _size < rightBottom.getX() &&
                pos.getY() + _size > rightBottom.getY();
    }

    public boolean IsClicked(PrecisePoint point) {
        return IsOnScreen(point, point);
    }
}
