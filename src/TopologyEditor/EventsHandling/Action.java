package TopologyEditor.EventsHandling;

/**
 * Created by VEF on 23.04.2015.
 */
public abstract class Action
{
    protected final ActionInfo ApplyMain(ActionParameters parameters)
    {
        ActionInfo info = Apply(parameters);
        info.setAction(this);
        return info;
    }



    public abstract ActionInfo Apply(ActionParameters parameters);
}
