package TopEd.EventsHandling;

/**
 * Created by VEF on 23.04.2015.
 */
public abstract class Action
{
    public String ID;
    public Action OppositeAction;



    public abstract void Apply();
}
