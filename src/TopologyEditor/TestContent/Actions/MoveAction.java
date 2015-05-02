package TopologyEditor.TestContent.Actions;

import TopologyEditor.Elements.Element;
import TopologyEditor.EventsHandling.Action;
import TopologyEditor.EventsHandling.ActionInfo;
import TopologyEditor.EventsHandling.ActionParameters;
import TopologyEditor.EventsHandling.PointTargetedActionParameters;
import TopologyEditor.PrecisePoint;

/**
 * Created by VEF on 24.04.2015.
 */
public class MoveAction extends Action
{
    public ActionInfo ApplyMain(ActionParameters parameters)
    {
        if (!PointTargetedActionParameters.class.isInstance(parameters))
            throw new IllegalArgumentException("MoveAction accepts only PointTargetedActionParameters as parameters");

        PointTargetedActionParameters params = (PointTargetedActionParameters)parameters;
        Element target = params.GetTarget();

        target.getPosition().Shift(params.GetPoint());

        PrecisePoint newPoint = new PrecisePoint(-params.GetPoint().getX(), -params.GetPoint().getY());
        PointTargetedActionParameters newParams = new PointTargetedActionParameters(parameters.GetTarget(), newPoint);
        return new ActionInfo(this, newParams);
    }
}
