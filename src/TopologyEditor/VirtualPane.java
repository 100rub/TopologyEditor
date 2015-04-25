package TopologyEditor;

import TopologyEditor.Elements.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 100rub on 14.04.2015.
 */
public class VirtualPane
{
    private List<Element> _elements;

    private PrecisePoint _viewPoint;

    public  VirtualPane()
    {
        _viewPoint = new PrecisePoint();
        _elements = new ArrayList<Element>();
    }



    public PrecisePoint getViewPoint() {
        return _viewPoint;
    }

    public void setViewPoint(PrecisePoint value) {
        _viewPoint = value;
    }

    public List<Element> getElements() {
        return _elements;
    }

    public void setElements(List<Element> value) {
         _elements = value;
    }
}
