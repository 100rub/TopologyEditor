package TopologyEditor.EventsHandling;

/**
 * Created by VEF on 23.04.2015.
 */
public final class ActionInfo
{
    private Action _action;
    private Action _opposite;
    private ActionParameters _parameters;



    public ActionInfo(Action opposite, ActionParameters parameters)
    {
        _opposite = opposite;
        _parameters = parameters;
    }


    public Action getAction()
    {
        return _action;
    }
    protected void setAction(Action value)
    {
        _action = value;
    }

    public ActionParameters getParameters()
    {
        return _parameters;
    }

    public Action getOpposite()
    {
        return _opposite;
    }
}
