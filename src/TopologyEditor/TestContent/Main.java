package TopologyEditor.TestContent;

import TopologyEditor.TestContent.Elements.Contact;
import TopologyEditor.TestContent.Elements.ContactPainter;
import TopologyEditor.TestContent.Elements.ContactRealPainter;
import TopologyEditor.UI.MainForm;

/**
 * Created by 100rub on 09.04.2015.
 */
public class Main
{
    public static void main(String[] args)
    {
        MainForm mainForm = new MainForm();
        mainForm.AddPainter("Scetch", Contact.class, new ContactPainter());
        mainForm.AddPainter("Topology", Contact.class, new ContactRealPainter());
        mainForm.SetDrawMode("Scetch");
        mainForm.Show();
    }
}
