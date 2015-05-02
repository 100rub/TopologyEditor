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


    public Action GetAction()
    {
        return _action;
    }
    
    protected void SetAction(Action value)
    {
        _action = value;
    }

    public ActionParameters GetParameters()
    {
        return _parameters;
    }

    public Action GetOpposite()
    {
        return _opposite;
    }
}
