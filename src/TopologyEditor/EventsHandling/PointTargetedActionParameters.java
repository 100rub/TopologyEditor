package TopologyEditor.EventsHandling;

import TopologyEditor.Elements.Element;
import TopologyEditor.PrecisePoint;

/**
 * Created by VEF on 24.04.2015.
 */
public class PointTargetedActionParameters extends ActionParameters
{
    private PrecisePoint _point;



    public PointTargetedActionParameters(Element target, PrecisePoint point)
    {
        super(target);
        _point = point;
    }



    public PrecisePoint GetPoint() {
        return _point;
    }
}
