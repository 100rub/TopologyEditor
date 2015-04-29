import TopologyEditor.DataStoring.XMLHelper;
import TopologyEditor.Elements.Element;
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
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

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
    private JMenuBar MenuBar;
    private JTabbedPane tabbedPane;
    private JPanel ProtertiesPanel;

    private JMenu FileMenu;
    private JMenu EditMenu;
    private JMenu ViewMenu;

    private JMenuItem NewMenuItem;
    private JMenuItem OpenMenuItem;
    private JMenuItem SaveMenuItem;

    private JMenuItem UndoMenuItem;
    private JMenuItem RedoMenuItem;
    private JMenuItem SettingsMenuItem;
    //--------------

    private boolean leftMousePressed = false;
    private boolean rightMousePressed = false;

    private JDrawPanel CurrentPanel;    //pointer to currently selected JDrawPanel
    private PrecisePoint mouseDownPosition;
    private PrecisePoint mouseUpPosition;
    private PrecisePoint previousMousePosition;

    private Element SelectedElement;

    public void loadTestData()      //TODO load test file properly
    {
        Random r = new Random();
        for(int i=0; i<100; i++)
        {
            Contact c = new Contact();
            c.setSize(10);
            c.setPosition(new PrecisePoint(r.nextDouble()*1000-500, r.nextDouble()*1000-500));
            CurrentPanel.getPane().getElements().add(c);
        }

        //PrecisePoint tmp = new PrecisePoint();
        //System.out.println(tmp.ToString());
        //System.out.println(CurrentPanel.translateOut(CurrentPanel.translateIn(tmp)).ToString());

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
        rootPanel.setPreferredSize(new Dimension(1024, 768));

        MenuBar = new JMenuBar();
        MenuBar.setPreferredSize(new Dimension(-1, 20));
        this.setJMenuBar(MenuBar);

        //------------------
        {
            FileMenu = new JMenu("File");
            MenuBar.add(FileMenu);

            NewMenuItem = new JMenuItem("New");
            FileMenu.add(NewMenuItem);
            NewMenuItem.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    System.out.println("NewMenuItem Clicked");
                }
            });

            OpenMenuItem = new JMenuItem("Open");
            FileMenu.add(OpenMenuItem);
            OpenMenuItem.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    System.out.println("OpenMenuItem Clicked");
                }
            });

            SaveMenuItem = new JMenuItem("Save");
            FileMenu.add(SaveMenuItem);
            SaveMenuItem.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    System.out.println("SaveMenuItem Clicked");
                }
            });
        }
        //--------------------

        //--------------------
        {
            EditMenu = new JMenu("Edit");
            MenuBar.add(EditMenu);

            UndoMenuItem = new JMenuItem("Undo");
            EditMenu.add(UndoMenuItem);
            UndoMenuItem.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    System.out.println("UndoMenuItem Clicked");
                }
            });

            RedoMenuItem = new JMenuItem("Redo");
            EditMenu.add(RedoMenuItem);
            RedoMenuItem.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    System.out.println("RedoMenuItem Clicked");
                }
            });

            SettingsMenuItem = new JMenuItem("Settings");
            EditMenu.add(SettingsMenuItem);
            SettingsMenuItem.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    System.out.println("SettingsMenuItem Clicked");
                }
            });
        }
        //--------------------

        //--------------------
        {
            //TODO add menus
            //ViewMenu = new JMenu("View");
            //MenuBar.add(ViewMenu);
        }
        //-----------------

        Panels.add(new JDrawPanel(null));               //creating empty pane for default
        CurrentPanel = Panels.get(Panels.size()-1);     //setting it as current panel

        initializeDrawPanelEvents(CurrentPanel);

        tabbedPane.add(CurrentPanel, BorderLayout.CENTER);
        tabbedPane.removeTabAt(0);
        tabbedPane.setTitleAt(0, "Untitled");

        SelectedElement = null;

        loadTestData();     //temporary for test

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }



    private void initializeDrawPanelEvents(JDrawPanel panel)
    {
        panel.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                if(e.getButton() == MouseEvent.BUTTON1)     //left mouse button
                {
                    System.out.println("LeftMouseClicked");
                    if(!rightMousePressed)
                    {
                        SelectedElement = CurrentPanel.SelectElement(CurrentPanel.TranslateIn(new PrecisePoint(e.getX(), e.getY())));
                    }
                }

                if(e.getButton() == MouseEvent.BUTTON3)     //right mouse button
                {
                    System.out.println("RightMouseClicked");
                    if(!leftMousePressed)
                    {

                    }
                }
            }

            public void mouseEntered(MouseEvent e)
            {
                //System.out.println("mouseEntered");
            }

            public void mouseExited(MouseEvent e)
            {
                //System.out.println("mouseExited");
            }

            public void mousePressed(MouseEvent e)
            {
                if(e.getButton() == MouseEvent.BUTTON1)     //left mouse button
                {
                    leftMousePressed = true;
                    //System.out.println("LeftMousePressed");
                }

                if(e.getButton() == MouseEvent.BUTTON3)     //right mouse button
                {
                    rightMousePressed = true;
                    previousMousePosition = new PrecisePoint(e.getX(), e.getY());
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
                    if(rightMousePressed)
                    {
                        previousMousePosition = new PrecisePoint(e.getX(), e.getY());
                    }
                }

                if(e.getButton() == MouseEvent.BUTTON3)
                {
                    rightMousePressed = false;
                    //System.out.println("RightMouseReleased");
                }

                mouseUpPosition = new PrecisePoint(e.getX(), e.getY());
                //System.out.println("viewPoint x=" + CurrentPanel.get_viewPointCoordsInside().getX() + " y=" + CurrentPanel.get_viewPointCoordsInside().getY());
            }
        });

        panel.addMouseMotionListener(new MouseMotionListener()
        {
            public void mouseDragged(MouseEvent e)
            {
                if(previousMousePosition == null)
                {
                    previousMousePosition = mouseDownPosition;
                }

                if(!leftMousePressed && rightMousePressed)
                {
                    double dx = e.getX() - previousMousePosition.getX();
                    double dy = e.getY() - previousMousePosition.getY();

                    CurrentPanel.MoveViewPoint(-dx, dy);

                    previousMousePosition.setX(e.getX());
                    previousMousePosition.setY(e.getY());
                }

                CurrentPanel.repaint();
            }

            public void mouseMoved(MouseEvent e)
            {
                //System.out.println("mouseMoved");
            }
        });

        panel.addMouseWheelListener(new MouseWheelListener()
        {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e)
            {
                int wheel_d = e.getWheelRotation();
                //System.out.println("mouseWheelMoved " + wheel_d);

                if(Math.abs(CurrentPanel.getZoomLevel() + wheel_d) <= 20)
                {
                    CurrentPanel.setZoomLevel(CurrentPanel.getZoomLevel() + wheel_d);
                }

                int zoom_lvl = CurrentPanel.getZoomLevel();
                double res_zoom_coef = 1;
                for(int i=0; i<Math.abs(zoom_lvl); i++)
                {
                    if(zoom_lvl < 0)
                    {
                        res_zoom_coef = res_zoom_coef * 1.1;
                        //CurrentPanel.set_zoomCoefficient(CurrentPanel.get_zoomCoefficient() * 1.1);
                    }
                    if(zoom_lvl > 0)
                    {
                        res_zoom_coef = res_zoom_coef / 1.1;
                        //CurrentPanel.set_zoomCoefficient(CurrentPanel.get_zoomCoefficient() / 1.1);
                    }
                }

                PrecisePoint mousePosInsideBefore = CurrentPanel.TranslateIn(new PrecisePoint(e.getX(), e.getY()));

                CurrentPanel.setZoomCoefficient(res_zoom_coef);

                PrecisePoint mousePosInsideAfter = CurrentPanel.TranslateIn(new PrecisePoint(e.getX(), e.getY()));

                PrecisePoint d = new PrecisePoint(mousePosInsideBefore.getX()-mousePosInsideAfter.getX(), mousePosInsideBefore.getY()-mousePosInsideAfter.getY());

                if(wheel_d < 0)
                {
                    CurrentPanel.MoveViewPoint(d.getX() * CurrentPanel.getZoomCoefficient(), d.getY() * CurrentPanel.getZoomCoefficient());
                }

                if(wheel_d > 0)
                {
                    CurrentPanel.MoveViewPoint(d.getX() * CurrentPanel.getZoomCoefficient(), d.getY() * CurrentPanel.getZoomCoefficient());
                }

                //System.out.println("zoom_lvl " + CurrentPanel.get_zoomLevel());
                //System.out.println("zoom_coef " + CurrentPanel.get_zoomCoefficient());
                CurrentPanel.repaint();
            }
        });
    }



    private void initializeScrollBarsEvents()
    {

    }



    private VirtualPane loadPaneFromFile(String filename)
    {
        VirtualPane res;
        try
        {
            res = (VirtualPane)XMLHelper.Read(filename);
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



    private void savePaneToFile(VirtualPane pane, String filename)
    {
        try
        {
            XMLHelper.Write(filename, pane);
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
}
