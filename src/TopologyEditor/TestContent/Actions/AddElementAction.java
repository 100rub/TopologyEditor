package TopologyEditor.TestContent.Actions;

import TopologyEditor.Elements.Element;
import TopologyEditor.EventsHandling.Action;
import TopologyEditor.EventsHandling.ActionInfo;
import TopologyEditor.EventsHandling.ActionParameters;

/**
 * Created by VEF on 5/3/2015.
 */
public class AddElementAction extends Action
{
    private String _elementName;

    protected ActionInfo ApplyMain(ActionParameters parameters)
    {
        AddElementParameters aeParams = (AddElementParameters)parameters;
        Element target = aeParams.GetTarget();
        ActionParameters revertParams = new ActionParameters(target);
        ActionInfo revertInfo = new ActionInfo(new RemoveElementAction(), revertParams);

        _elementName = target.GetName();
        aeParams.GetTargetPane().getElements().add(target);
        target.setPosition(aeParams.GetPoint());

        return revertInfo;
    }

    public String GetName() {
        return " adding " + _elementName;
    }
}
