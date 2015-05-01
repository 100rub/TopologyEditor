package TopologyEditor.Elements;

import TopologyEditor.PrecisePoint;
import TopologyEditor.VirtualPane;

/**
 * Created by VEF on 23.04.2015.
 */
public abstract class Element
{
    private PrecisePoint _position;
    private VirtualPane _parent;



    public abstract String GetName();

    public PrecisePoint getPosition()
    {
        return _position;
    }

    public void setPosition(PrecisePoint value)
    {
        _position = value;
    }

    public VirtualPane GetParent()
    {
        return _parent;
    }

    public void SetParent(VirtualPane _parent)
    {
        this._parent = _parent;
    }
}
