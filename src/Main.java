import TopologyEditor.DataStoring.XMLHelper;
import TopologyEditor.EventsHandling.ActionHandler;
import TopologyEditor.EventsHandling.PointTargetedActionParameters;
import TopologyEditor.TestContent.Actions.MoveAction;
import TopologyEditor.TestContent.Elements.Contact;
import TopologyEditor.PrecisePoint;

import java.util.ArrayList;

/**
 * Created by 100rub on 09.04.2015.
 */
public class Main
{
    public static void main(String[] args)
    {
        ArrayList<Object> list = new ArrayList<Object>();
        Contact c;
        ActionHandler handler = new ActionHandler();

        c = new Contact();
        c.setSize(10);
        c.setPosition(new PrecisePoint(1.5, -1.5));
        list.add(c);

        c = new Contact();
        c.setSize(5);
        c.setPosition(new PrecisePoint(-1.5, 1.5));
        list.add(c);

        try
        {
            XMLHelper.Write(list, "1.xml");
            list = (ArrayList<Object>)XMLHelper.Read("1.xml");
        }
        catch (Exception e)
        {

        }

        MoveAction moveAction = new MoveAction();

        c = (Contact)list.get(0);
        handler.Do(moveAction, new PointTargetedActionParameters(c, new PrecisePoint(1, 1)));

        try
        {
            handler.Undo();
            handler.Redo();
        }
        catch (Exception e)
        {

        }

        MainForm mainForm = new MainForm();
    }
}
