package TopologyEditor.EventsHandling.Listeners;

import TopologyEditor.EventsHandling.Action;
import TopologyEditor.EventsHandling.ActionHandler;
import TopologyEditor.EventsHandling.ActionParameters;

/**
 * Created by VEF on 5/3/2015.
 */
public interface IDoListener
{
    void OnDo(ActionHandler handler, Action action, ActionParameters params);
}
