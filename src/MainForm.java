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
import java.awt.*;
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
    private JTabbedPane tabbedPane;
    //--------------

    private boolean leftMousePressed = false;
    private boolean rightMousePressed = false;

    private JDrawPanel CurrentPanel;    //pointer to currently selected JDrawPanel
    private PrecisePoint mouseDownPosition;
    private PrecisePoint mouseUpPosition;
    private PrecisePoint previousMousePosition;



    public void loadTestData()      //TODO load test file properly
    {
        Contact c = new Contact();
        c.setSize(10);
        c.setPosition(new PrecisePoint(0, 0));
        CurrentPanel.getPane().getElements().add(c);

        /*c = new Contact();
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

        Panels.add(new JDrawPanel(null));       //creating empty pane for default
        CurrentPanel = Panels.get(Panels.size()-1);     //setting it as current panel

        initializeDrawPanelEvents(CurrentPanel);

        tabbedPane.add(CurrentPanel, BorderLayout.CENTER);
        tabbedPane.removeTabAt(0);

        loadTestData();     //temporary for test

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }



    private void initializeDrawPanelEvents(JDrawPanel panel)
    {
        panel.addMouseListener(new MouseAdapter()
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
                if(e.getButton() == MouseEvent.BUTTON1)
                {
                    leftMousePressed = true;
                    //System.out.println("LeftMousePressed");
                }

                if(e.getButton() == MouseEvent.BUTTON3)
                {
                    rightMousePressed = true;
                    //System.out.println("RightMousePressed");
                }

                mouseDownPosition = new PrecisePoint(e.getX(), e.getY());
            }

            public void mouseReleased(MouseEvent e)
            {
                if(e.getButton() == MouseEvent.BUTTON1)
                {
                    leftMousePressed = false;
                    //System.out.println("LeftMouseReleased");
                }

                if(e.getButton() == MouseEvent.BUTTON3)
                {
                    rightMousePressed = false;
                    //System.out.println("RightMouseReleased");
                }

                mouseUpPosition = new PrecisePoint(e.getX(), e.getY());
            }
        });

        panel.addMouseMotionListener(new MouseMotionListener()
        {
            public void mouseDragged(MouseEvent e)
            {
                if(previousMousePosition == null)
                {
                    previousMousePosition = new PrecisePoint(e.getX(), e.getY());
                }

                if(!leftMousePressed && rightMousePressed)
                {
                    double dx = e.getX() - previousMousePosition.getX();
                    double dy = e.getY() - previousMousePosition.getY();

                    CurrentPanel.get_viewPointCoordsInside().setX(CurrentPanel.get_viewPointCoordsInside().getX() - dx);        //TODO fix this
                    CurrentPanel.get_viewPointCoordsInside().setY(CurrentPanel.get_viewPointCoordsInside().getY() - dy);

                    System.out.println("viewPoint x=" + CurrentPanel.get_viewPointCoordsInside().getX() + " y=" + CurrentPanel.get_viewPointCoordsInside().getY());
                }

                previousMousePosition.setX(e.getX());
                previousMousePosition.setY(e.getY());

                CurrentPanel.repaint();
            }

            public void mouseMoved(MouseEvent e)
            {
                //System.out.println("mouseMoved");
            }
        });
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
