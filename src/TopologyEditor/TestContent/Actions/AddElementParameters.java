package TopologyEditor.TestContent.Actions;

import TopologyEditor.Elements.Element;
import TopologyEditor.EventsHandling.PointTargetedActionParameters;
import TopologyEditor.Utilities.PrecisePoint;
import TopologyEditor.Elements.VirtualPane;

/**
 * Created by VEF on 5/3/2015.
 */
public class AddElementParameters extends PointTargetedActionParameters
{
    private VirtualPane _targetPane;

    public AddElementParameters(Element target, PrecisePoint point, VirtualPane targetPane)
    {
        super(target, point);
        _targetPane = targetPane;
    }

    public VirtualPane GetTargetPane()
    {
        return _targetPane;
    }
}
