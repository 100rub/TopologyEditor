import TopologyEditor.PrecisePoint;
import TopologyEditor.VirtualPane;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

/**
 * Created by 100rub on 09.04.2015.
 */
public class MainForm extends JFrame
{
    private JPanel rootPanel;
    private JScrollBar scrollBarVer;
    private JScrollBar scrollBarHor;
    private JToolBar toolBar;
    private JPanel drawPanel;

    private VirtualPane CurrentPane;

    private PrecisePoint mousePosition;

    public MainForm()
    {
        super("Topology Editor");
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
                if(mousePosition == null)
                {
                    mousePosition = new PrecisePoint();
                }
                mousePosition.setX(e.getX());
                mousePosition.setY(e.getY());
            }
            public void mouseReleased(MouseEvent e)
            {
                //System.out.println("mouseReleased");
            }
        });

        drawPanel.addMouseMotionListener(new MouseMotionListener()
        {
            public void mouseDragged(MouseEvent e)
            {
                //System.out.println("mouseDragged");
                double dx = e.getX() - mousePosition.getX();
                double dy = e.getY() - mousePosition.getY();
                CurrentPane.getViewPoint().setX(CurrentPane.getViewPoint().getX() - dx);
                CurrentPane.getViewPoint().setY(CurrentPane.getViewPoint().getY() - dy);

                System.out.println("mouseDragged dx=" + dx + " dy=" + dy);

                RedrawPane();

                mousePosition.setX(e.getX());
                mousePosition.setY(e.getY());
            }

            public void mouseMoved(MouseEvent e)
            {
                //System.out.println("mouseMoved");
            }
        });



        CurrentPane = new VirtualPane();

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void RedrawPane()
    {

    }
}
