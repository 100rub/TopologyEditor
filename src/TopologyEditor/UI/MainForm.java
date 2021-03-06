package TopologyEditor.UI;

import TopologyEditor.Elements.*;
import TopologyEditor.EventsHandling.*;
import TopologyEditor.EventsHandling.Action;
import TopologyEditor.EventsHandling.Listeners.IDoListener;
import TopologyEditor.TestContent.Actions.MoveAction;
import TopologyEditor.UI.JDrawPanel;
import TopologyEditor.Utilities.MouseState;
import TopologyEditor.Utilities.PrecisePoint;
import TopologyEditor.TestContent.Actions.AddElementAction;
import TopologyEditor.TestContent.Actions.AddElementParameters;
import TopologyEditor.TestContent.Actions.RemoveElementAction;
import TopologyEditor.TestContent.Elements.Contact;
import TopologyEditor.TestContent.Elements.ContactPainter;
import TopologyEditor.Utilities.PainterLink;
import TopologyEditor.Elements.VirtualPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by 100rub on 09.04.2015.
 */
public class MainForm extends JFrame
{
    /**
     * список панелей
     */
    private ArrayList<JDrawPanel> Panels;

    /**
     * обработчик событий
     */
    //private ActionHandler handler;

    private ArrayList<String> drawModes;
    private HashMap<PainterLink, IPainter> PainterMap;

    //--Gui elements
    private JPanel rootPanel;
    private JMenuBar MenuBar;
    private JTabbedPane tabbedPane;
    private JPanel PropertiesPanel;
    private JComboBox cbDrawMode;

    private JMenu FileMenu;
    private JMenu EditMenu;
    private JMenu ViewMenu;

    private JMenuItem NewMenuItem;
    private JMenuItem OpenMenuItem;
    private JMenuItem SaveMenuItem;
    private JMenuItem SaveAsMenuItem;
    private JMenuItem ExitMenuItem;

    private JMenuItem UndoMenuItem;
    private JMenuItem RedoMenuItem;
    private JMenuItem SettingsMenuItem;
    //--------------

    private JDrawPanel CurrentPanel;    //pointer to currently selected JDrawPanel

    private String currDir;

    public void loadTestData()      //TODO remove
    {
        Random r = new Random();
        for(int i=0; i<100; i++)
        {
            Contact c = new Contact();
            c.setSize(10);
            c.setPosition(new PrecisePoint(r.nextDouble()*1000-500, r.nextDouble()*1000-500));
            CurrentPanel.getPane().getElements().add(c);
        }
    }



    private void InitHandler(JDrawPanel panel)
    {
        ActionHandler hndlr = panel.getHandler();

        IDoListener listener = new IDoListener()
        {
            public void OnDo(ActionHandler handler, TopologyEditor.EventsHandling.Action action, ActionParameters params)
            {
                int id = tabbedPane.getSelectedIndex();
                String title = tabbedPane.getTitleAt(id);
                if (!title.endsWith("*"))
                    tabbedPane.setTitleAt(id, title + "*");
                CurrentPanel.setHasUnsavedChanges(true);
                UpdateUI();
            }
        };

        hndlr.RegisterDoListener(listener);
        hndlr.RegisterReDoListener(listener);
        hndlr.RegisterUnDoListener(listener);
    }



    public MainForm()
    {
        super("Topology Editor");

        currDir = Paths.get(".").toAbsolutePath().normalize().toString();

        Panels = new ArrayList<JDrawPanel>();
        drawModes = new ArrayList<String>();
        PainterMap = new HashMap<PainterLink, IPainter>();
        drawModes.add("Debug");
        cbDrawMode.addItem("Debug");

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
            NewMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
            NewMenuItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    //System.out.println("NewMenuItem Clicked");
                    New(e);
                }
            });

            OpenMenuItem = new JMenuItem("Open");
            FileMenu.add(OpenMenuItem);
            OpenMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
            OpenMenuItem.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    //System.out.println("OpenMenuItem Clicked");
                    Open(e);
                }
            });

            SaveMenuItem = new JMenuItem("Save");
            FileMenu.add(SaveMenuItem);
            SaveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
            SaveMenuItem.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    //System.out.println("SaveMenuItem Clicked");
                    Save(e);
                }
            });

            SaveAsMenuItem = new JMenuItem("Save As");
            FileMenu.add(SaveAsMenuItem);
            SaveAsMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.SHIFT_MASK | Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
            SaveAsMenuItem.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    //System.out.println("SaveAsMenuItem Clicked");
                    SaveAs(e);
                }
            });

            ExitMenuItem = new JMenuItem("Exit");
            FileMenu.add(ExitMenuItem);
            ExitMenuItem.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    //System.out.println("SaveAsMenuItem Clicked");
                    Exit(e);
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
            UndoMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
            UndoMenuItem.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    //System.out.println("UndoMenuItem Clicked");
                    Undo(e);
                }
            });

            RedoMenuItem = new JMenuItem("Redo");
            EditMenu.add(RedoMenuItem);
            RedoMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
            RedoMenuItem.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    //System.out.println("RedoMenuItem Clicked");
                    Redo(e);
                }
            });

            SettingsMenuItem = new JMenuItem("Settings");
            EditMenu.add(SettingsMenuItem);
            SettingsMenuItem.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    //System.out.println("SettingsMenuItem Clicked");
                    Settings(e);
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

        CurrentPanel = new JDrawPanel(null, PainterMap);
        Panels.add(CurrentPanel);
        InitHandler(CurrentPanel);

        cbDrawMode.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e)
            {
                if (e.getStateChange() == ItemEvent.SELECTED)
                {
                    for (JDrawPanel jdp : Panels)
                        jdp.SetDrawMode(drawModes.get(cbDrawMode.getSelectedIndex()));
                    CurrentPanel.repaint();
                }
            }
        });

        tabbedPane.add(CurrentPanel, BorderLayout.CENTER);
        tabbedPane.removeTabAt(0);
        tabbedPane.setTitleAt(0, "Untitled");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }



    private void New(ActionEvent e)
    {
        if(CurrentPanel.getHasUnsavedChanges())
        {
            Object[] options = {"Yes", "No"};
            int returnVal = JOptionPane.showOptionDialog(this,
                    "Your current project has unsaved changes that will be lost if you continue. Do you want to continue anyway?",
                    "Warning",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE,
                    null,
                    options,
                    options[1]);
            if(returnVal == JOptionPane.YES_OPTION)
            {
                CurrentPanel.AssignPane(new VirtualPane());
                //System.out.println("YES_OPTION");
            }
        }
        else
        {
            CurrentPanel.AssignPane(new VirtualPane());
        }
        UpdateUI();
    }



    private void Open(ActionEvent e)
    {
        if(CurrentPanel.getHasUnsavedChanges())
        {
            Object[] options = {"Yes", "No"};
            int returnVal = JOptionPane.showOptionDialog(this,
                    "Your current project has unsaved changes that will be lost if you continue. Do you want to continue anyway?",
                    "Warning",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE,
                    null,
                    options,
                    options[1]);
            if(returnVal == JOptionPane.YES_OPTION)
            {
                final JFileChooser fc = new JFileChooser();
                fc.setCurrentDirectory(new File(currDir));
                int result = fc.showOpenDialog(rootPanel);
                if (result == JFileChooser.APPROVE_OPTION)
                {
                    CurrentPanel.AssignPane(VirtualPane.Load(fc.getSelectedFile().getAbsolutePath()));
                    tabbedPane.setTitleAt(0, fc.getSelectedFile().getName());
                }
            }
        }
        else
        {
            final JFileChooser fc = new JFileChooser();
            fc.setCurrentDirectory(new File(currDir));
            int result = fc.showOpenDialog(rootPanel);
            if (result == JFileChooser.APPROVE_OPTION)
            {
                CurrentPanel.AssignPane(VirtualPane.Load(fc.getSelectedFile().getAbsolutePath()));
                tabbedPane.setTitleAt(0, fc.getSelectedFile().getName());
            }
        }
        UpdateUI();
    }



    private void Save(ActionEvent e)
    {
        if(!CurrentPanel.getPane().Save())
        {
            final JFileChooser fc = new JFileChooser();
            fc.setCurrentDirectory(new File(currDir));
            int returnVal = fc.showSaveDialog(rootPanel);
            if (returnVal == JFileChooser.APPROVE_OPTION)
            {
                CurrentPanel.getPane().SaveAs(fc.getSelectedFile().getAbsolutePath());
                tabbedPane.setTitleAt(0, fc.getSelectedFile().getName());
                int id = tabbedPane.getSelectedIndex();
                String title = tabbedPane.getTitleAt(id);
                if (title.endsWith("*"))
                    tabbedPane.setTitleAt(id, title.substring(0, title.length() - 1));
                CurrentPanel.setHasUnsavedChanges(false);
            }
        }
        else
        {
            int id = tabbedPane.getSelectedIndex();
            String title = tabbedPane.getTitleAt(id);
            if (title.endsWith("*"))
                tabbedPane.setTitleAt(id, title.substring(0, title.length() - 1));
            CurrentPanel.setHasUnsavedChanges(false);
        }
        UpdateUI();
    }



    private void SaveAs(ActionEvent e)
    {
        final JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new File(currDir));
        int returnVal = fc.showSaveDialog(rootPanel);
        if (returnVal == JFileChooser.APPROVE_OPTION)
        {
            CurrentPanel.getPane().SaveAs(fc.getSelectedFile().getAbsolutePath());
            tabbedPane.setTitleAt(0, fc.getSelectedFile().getName());
            int id = tabbedPane.getSelectedIndex();
            String title = tabbedPane.getTitleAt(id);
            if (title.endsWith("*"))
                tabbedPane.setTitleAt(id, title.substring(0, title.length() - 1));
            CurrentPanel.setHasUnsavedChanges(false);
        }
        UpdateUI();
    }



    private void Undo(ActionEvent e)
    {
        CurrentPanel.getHandler().Undo();
    }



    private void Redo(ActionEvent e)
    {
        CurrentPanel.getHandler().Redo();
    }



    private void Settings(ActionEvent e)
    {
        //TODO
    }



    private void Exit(ActionEvent e)
    {
        if(CurrentPanel.getHasUnsavedChanges())
        {
            Object[] options = {"Yes", "No"};
            int returnVal = JOptionPane.showOptionDialog(this,
                    "Your current project has unsaved changes that will be lost if you continue. Do you want to continue anyway?",
                    "Warning",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE,
                    null,
                    options,
                    options[1]);
            if(returnVal == JOptionPane.YES_OPTION)
            {
                System.exit(0);
            }
        }
        else
        {
            System.exit(0);
        }
    }



    private void UpdateUI()
    {
        ActionInfo info = CurrentPanel.getHandler().GetLastAction();
        UndoMenuItem.setEnabled(info != null);
        if (info != null)
            UndoMenuItem.setText("Undo " + info.GetAction().GetName());
        else
            UndoMenuItem.setText("Undo");

        info = CurrentPanel.getHandler().GetLastUndo();
        RedoMenuItem.setEnabled(info != null);
        if (info != null)
            RedoMenuItem.setText("Redo " + info.GetAction().GetName());
        else
            RedoMenuItem.setText("Redo");

        CurrentPanel.repaint();
    }



    public void Show()
    {
        //CurrentPanel.getHandler() = new ActionHandler();
        //InitHandler();

        UpdateUI();

        pack();
        setVisible(true);
    }



    public void AddPainter(String drawMode, Class elementType, IPainter painter)
    {
        if (!drawModes.contains(drawMode))
        {
            drawModes.add(drawMode);
            cbDrawMode.addItem(drawMode);
        }
        PainterMap.put(new PainterLink(drawMode, elementType), painter);
    }



    public void SetDrawMode(String value)
    {
        if (drawModes.contains(value))
            cbDrawMode.setSelectedItem(value);
    }
}
