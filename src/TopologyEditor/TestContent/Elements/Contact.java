package TopologyEditor.TestContent.Elements;

import TopologyEditor.Elements.Element;

/**
 * Created by VEF on 23.04.2015.
 */
public class Contact extends Element
{
    private double _size;
    private double _innerSize;



    public double getSize()
    {
        return _size;
    }

    public void setSize(double value)
    {
        _size = value;
    }

    public double getInnerSize()
    {
        return _innerSize;
    }

    public void setInnerSize(double value)
    {
        _innerSize = value;
    }

    public String GetName()
    {
        return "contact";
    }
}
