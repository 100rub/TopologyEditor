package TopologyEditor.EventsHandling;

/**
 * Created by VEF on 23.04.2015.
 */
public abstract class Action
{
    protected final ActionInfo Apply(ActionParameters parameters)
    {
        ActionInfo info = ApplyMain(parameters);
        info.setAction(this);
        return info;
    }



    protected abstract ActionInfo ApplyMain(ActionParameters parameters);
}
