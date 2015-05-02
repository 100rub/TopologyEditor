package TopologyEditor.TestContent.Actions;

import TopologyEditor.Elements.Element;
import TopologyEditor.EventsHandling.Action;
import TopologyEditor.EventsHandling.ActionInfo;
import TopologyEditor.EventsHandling.ActionParameters;
import TopologyEditor.VirtualPane;

/**
 * Created by VEF on 5/3/2015.
 */
public class RemoveElementAction extends Action
{
    protected ActionInfo ApplyMain(ActionParameters parameters)
    {
        Element target = parameters.GetTarget();
        VirtualPane pane = target.GetParent();
        ActionParameters revertParams = new AddElementParameters(target, target.getPosition(), pane);
        ActionInfo revertInfo = new ActionInfo(new AddElementAction(), revertParams);

        pane.getElements().remove(target);

        return revertInfo;
    }
}
