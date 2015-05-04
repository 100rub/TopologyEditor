package TopologyEditor.TestContent.Actions;

import TopologyEditor.Elements.Element;
import TopologyEditor.EventsHandling.Action;
import TopologyEditor.EventsHandling.ActionInfo;
import TopologyEditor.EventsHandling.ActionParameters;
import TopologyEditor.EventsHandling.PointTargetedActionParameters;
import TopologyEditor.Utilities.PrecisePoint;

/**
 * Created by VEF on 24.04.2015.
 */
public class MoveAction extends Action
{
    private String _elementName;

    public ActionInfo ApplyMain(ActionParameters parameters)
    {
        PointTargetedActionParameters params = (PointTargetedActionParameters)parameters;
        Element target = params.GetTarget();

        target.getPosition().Shift(params.GetPoint());

        _elementName = target.GetName();
        PrecisePoint newPoint = new PrecisePoint(-params.GetPoint().getX(), -params.GetPoint().getY());
        PointTargetedActionParameters newParams = new PointTargetedActionParameters(parameters.GetTarget(), newPoint);
        return new ActionInfo(this, newParams);
    }

    public String GetName() {
        return " moving " + _elementName;
    }
}
