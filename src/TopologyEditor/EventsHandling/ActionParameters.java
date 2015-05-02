package TopologyEditor.EventsHandling;

import TopologyEditor.Elements.Element;

/**
 * Created by VEF on 23.04.2015.
 */
public class ActionParameters
{
    private Element _target;



    public ActionParameters(Element target)
    {
        _target = target;
    }



    public Element GetTarget()
    {
        return _target;
    }
}
