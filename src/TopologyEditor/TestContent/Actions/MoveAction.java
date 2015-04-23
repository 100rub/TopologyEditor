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
    @Override
    public ActionInfo Apply(ActionParameters parameters)
    {
        if (!PointTargetedActionParameters.class.isInstance(parameters))
            throw new IllegalArgumentException("MoveAction accepts only PointTargetedActionParameters as parameters");

        PointTargetedActionParameters params = (PointTargetedActionParameters)parameters;
        Element target = params.getTarget();

        target.getPosition().Shift(params.getPoint());

        PrecisePoint newPoint = new PrecisePoint(-params.getPoint().getX(), -params.getPoint().getY());
        PointTargetedActionParameters newParams = new PointTargetedActionParameters(parameters.getTarget(), newPoint);
        return new ActionInfo(this, newParams);
    }
}
