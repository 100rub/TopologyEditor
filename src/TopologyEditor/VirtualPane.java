package TopologyEditor;

import TopologyEditor.DataStoring.XMLHelper;
import TopologyEditor.Elements.Element;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 100rub on 14.04.2015.
 */
public class VirtualPane
{
    private List<Element> _elements;
    private PrecisePoint _viewPoint;
    private String _fileName;



    public  VirtualPane()
    {
        _viewPoint = new PrecisePoint();
        _elements = new ArrayList<Element>();
        _fileName = null;
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



    public boolean Save()
    {
        if (_fileName == null)
            return false;
        try
        {
            XMLHelper.Write(_fileName, this);
        }
        catch (FileNotFoundException e)
        {
            return false;
        }
        return true;
    }



    public boolean SaveAs(String path)
    {
        _fileName = path;
        try
        {
            XMLHelper.Write(_fileName, this);
        }
        catch (FileNotFoundException e)
        {
            return false;
        }
        return true;
    }



    public static VirtualPane Load(String path)
    {
        try
        {
            VirtualPane pane = (VirtualPane)XMLHelper.Read(path);
            pane._fileName = path;
            return pane;
        }
        catch (FileNotFoundException e)
        {
            return null;
        }
    }
}
