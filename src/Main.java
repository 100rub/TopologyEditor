import TopologyEditor.DataStoring.XMLHelper;
import TopologyEditor.EventsHandling.ActionHandler;
import TopologyEditor.EventsHandling.PointTargetedActionParameters;
import TopologyEditor.TestContent.Actions.MoveAction;
import TopologyEditor.TestContent.Elements.Contact;
import TopologyEditor.PrecisePoint;
import TopologyEditor.VirtualPane;

import javax.swing.*;

/**
 * Created by 100rub on 09.04.2015.
 */
public class Main
{
    public static void main(String[] args)
    {
        VirtualPane vp = new VirtualPane();
        Contact c;
        ActionHandler handler = new ActionHandler();

        c = new Contact();
        c.setSize(10);
        c.setPosition(new PrecisePoint(1.5, -1.5));
        vp.getElements().add(c);

        c = new Contact();
        c.setSize(5);
        c.setPosition(new PrecisePoint(-1.5, 1.5));
        vp.getElements().add(c);

        try
        {
            XMLHelper.Write("1.xml", vp);
            vp = (VirtualPane)XMLHelper.Read("1.xml");
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null,
                    "Error while trying to read/write file.\r\n" +
                    "Exception: " + e.getClass().getName() + "\r\n" +
                    "Message: " + e.getMessage() + ".",
                "Error", JOptionPane.ERROR_MESSAGE);
        }

        MoveAction moveAction = new MoveAction();

        c = (Contact)vp.getElements().get(0);
        handler.Do(moveAction, new PointTargetedActionParameters(c, new PrecisePoint(1, 1)));

        handler.Undo();
        handler.Redo();

        MainForm mainForm = new MainForm();
    }
}
