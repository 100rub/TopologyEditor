import TopologyEditor.DataStoring.XMLHelper;
import TopologyEditor.EventsHandling.ActionHandler;
import TopologyEditor.EventsHandling.PointTargetedActionParameters;
import TopologyEditor.JDrawPanel;
import TopologyEditor.PrecisePoint;
import TopologyEditor.TestContent.Actions.MoveAction;
import TopologyEditor.TestContent.Elements.Contact;
import TopologyEditor.VirtualPane;
import javafx.scene.layout.Pane;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

/**
 * Created by 100rub on 09.04.2015.
 */
public class MainForm extends JFrame
{
    private ArrayList<JDrawPanel> Panels;
    private ActionHandler handler;

    //--Gui elements
    private JPanel rootPanel;
    private JScrollBar scrollBarVer;
    private JScrollBar scrollBarHor;
    private JToolBar toolBar;
    private JPanel drawPanel;
    //--------------

    private boolean leftMousePressed = false;
    private boolean rightMousePressed = false;

    private JDrawPanel CurrentPanel;    //pointer to currently selected JDrawPanel

    private PrecisePoint mouseClickPosition;     //mouse position inside JDrawPanel

    public void loadTestData()      //TODO load test file properly
    {
        /*Contact c = new Contact();
        c.setSize(10);
        c.setPosition(new PrecisePoint(1.5, -1.5));
        CurrentPanel.getPane().getElements().add(c);

        c = new Contact();
        c.setSize(5);
        c.setPosition(new PrecisePoint(-1.5, 1.5));
        CurrentPanel.getPane().getElements().add(c);

        MoveAction moveAction = new MoveAction();

        c = (Contact)CurrentPanel.getPane().getElements().get(0);
        handler.Do(moveAction, new PointTargetedActionParameters(c, new PrecisePoint(1, 1)));

        handler.Undo();
        handler.Redo();*/
    }

    public MainForm()
    {
        super("Topology Editor");

        Panels = new ArrayList<JDrawPanel>();
        handler = new ActionHandler();

        setContentPane(rootPanel);
        rootPanel.setOpaque(true);
        toolBar.setFloatable(false);

        drawPanel.addMouseListener(new MouseAdapter()
        {
            /*public void mouseClicked(MouseEvent e)
            {
                System.out.println("mouseClicked");
            }
            public void mouseEntered(MouseEvent e)
            {
                System.out.println("mouseEntered");
            }
            public void mouseExited(MouseEvent e)
            {
                System.out.println("mouseExited");
            }*/
            public void mousePressed(MouseEvent e)
            {
                //System.out.println("mousePressed");
                if(e.getButton() == MouseEvent.BUTTON1)
                {
                    leftMousePressed = true;
                    System.out.println("LeftMousePressed");
                }

                if(e.getButton() == MouseEvent.BUTTON3)
                {
                    rightMousePressed = true;
                    System.out.println("RightMousePressed");
                }

                if(mouseClickPosition == null)
                {
                    mouseClickPosition = new PrecisePoint();
                }
                mouseClickPosition.setX(e.getX());
                mouseClickPosition.setY(e.getY());
            }
            public void mouseReleased(MouseEvent e)
            {
                //System.out.println("mouseReleased");
                if(e.getButton() == MouseEvent.BUTTON1)
                {
                    leftMousePressed = false;
                    System.out.println("LeftMouseReleased");
                }

                if(e.getButton() == MouseEvent.BUTTON3)
                {
                    rightMousePressed = false;
                    System.out.println("RightMouseReleased");
                }
            }
        });

        drawPanel.addMouseMotionListener(new MouseMotionListener()
        {
            public void mouseDragged(MouseEvent e)
            {
                //System.out.println("mouseDragged");
                if(!leftMousePressed && rightMousePressed)
                {

                }
                /*double dx = e.getX() - mousePosition.getX();
                double dy = e.getY() - mousePosition.getY();
                CurrentPanel.getViewPoint().setX(CurrentPanel.getViewPoint().getX() - dx);
                CurrentPanel.getViewPoint().setY(CurrentPanel.getViewPoint().getY() - dy);

                System.out.println("mouseDragged dx=" + dx + " dy=" + dy);

                RedrawPane();

                mousePosition.setX(e.getX());
                mousePosition.setY(e.getY());*/
            }

            public void mouseMoved(MouseEvent e)
            {
                //System.out.println("mouseMoved");
            }
        });

        Panels.add(new JDrawPanel());       //creating empty pane for default
        CurrentPanel = Panels.get(Panels.size()-1);     //setting it as current panel

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private VirtualPane loadPaneFromFile(String path)
    {
        VirtualPane res;
        try
        {
            res = (VirtualPane)XMLHelper.Read(path);
            return res;
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null,
                    "Error while trying to read/write file.\r\n" +
                            "Exception: " + e.getClass().getName() + "\r\n" +
                            "Message: " + e.getMessage() + ".",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    private void savePaneToFile(VirtualPane pane)
    {
        try
        {
            XMLHelper.Write("1.xml", pane);
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null,
                    "Error while trying to read/write file.\r\n" +
                            "Exception: " + e.getClass().getName() + "\r\n" +
                            "Message: " + e.getMessage() + ".",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void RedrawPane()
    {

    }
}
