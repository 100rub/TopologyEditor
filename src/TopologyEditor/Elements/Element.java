package TopologyEditor.Elements;

import TopologyEditor.PrecisePoint;

/**
 * Created by VEF on 23.04.2015.
 */
public abstract class Element
{
    private PrecisePoint _position;



    public PrecisePoint getPosition()
    {
        return _position;
    }

    public void setPosition(PrecisePoint value)
    {
        _position = value;
    }

    public abstract double getSize();
}
