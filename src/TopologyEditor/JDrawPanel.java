package TopologyEditor;

import TopologyEditor.Elements.Element;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

/**
 * Created by 100rub on 14.04.2015.
 */
public class JDrawPanel extends JPanel
{
    private VirtualPane _assignedPane;
    private int _zoomLevel;                 //0 is default
    private double _zoomCoefficient;        //1.0 is default, closer=bigger
    private PrecisePoint _viewPoint;
    private PrecisePoint _viewPanelCenter;
    private int gridPointsDistance = 20;


    public JDrawPanel(VirtualPane pane)
    {
        if(pane == null)
        {
            _assignedPane = new VirtualPane();
        }
        else
        {
            _assignedPane = pane;
        }
        _zoomLevel = 0;
        _zoomCoefficient = 1;
        _viewPoint = new PrecisePoint();
    }



    public void assignPane(VirtualPane pane)
    {
        if(_assignedPane != null)
        {
            //TODO free memory
        }

        _assignedPane = pane;
    }



    public VirtualPane getPane()
    {
        return _assignedPane;
    }



    public PrecisePoint get_viewPoint()
    {
        return _viewPoint;
    }



    public void set_viewPoint(PrecisePoint pnt)
    {
        _viewPoint = pnt;
    }



    public PrecisePoint get_viewPanelCenter()
    {
        return _viewPanelCenter;
    }



    public void set_viewPanelCenter(PrecisePoint pnt)
    {
        _viewPanelCenter = pnt;
    }



    public int get_zoomLevel()
    {
        return _zoomLevel;
    }



    public void set_zoomLevel(int lvl)
    {
        _zoomLevel = lvl;
    }



    public void set_zoomCoefficient(double d)
    {
        _zoomCoefficient = d;
    }



    public double get_zoomCoefficient()
    {
        return _zoomCoefficient;
    }



    public void moveViewPoint(double x, double y)
    {
        _viewPoint.setX(_viewPoint.getX() + x / _zoomCoefficient);
        _viewPoint.setY(_viewPoint.getY() - y / _zoomCoefficient);
    }



    public PrecisePoint translateIn(PrecisePoint pnt)      //translates position from DrawPanel into position on VirtualPane
    {
        PrecisePoint res = new PrecisePoint();

        _viewPanelCenter = new PrecisePoint(this.getWidth()/2, this.getHeight()/2);

        PrecisePoint d = new PrecisePoint(pnt.getX() - _viewPanelCenter.getX(), pnt.getY() - _viewPanelCenter.getY());
        res.setX(_viewPoint.getX() + d.getX() / _zoomCoefficient);
        res.setY(_viewPoint.getY() - d.getY() / _zoomCoefficient);

        return res;
    }



    public PrecisePoint translateOut(PrecisePoint pnt)     //translates position from VirtualPane into position on DrawPanel
    {
        PrecisePoint res = new PrecisePoint();

        _viewPanelCenter = new PrecisePoint(this.getWidth()/2, this.getHeight()/2);

        PrecisePoint d = new PrecisePoint(pnt.getX() - _viewPoint.getX(), pnt.getY() - _viewPoint.getY());
        res.setX(_viewPanelCenter.getX() + d.getX()*_zoomCoefficient);
        res.setY(_viewPanelCenter.getY() - d.getY()*_zoomCoefficient);

        return res;
    }



    private void paintPoint(Graphics2D g2d, PrecisePoint a)
    {
        g2d.drawLine((int)a.getX(), (int)a.getY(), (int)a.getX(), (int)a.getY());
    }



    private void paintLine(Graphics2D g2d, PrecisePoint a, PrecisePoint b)
    {
        g2d.drawLine((int)a.getX(), (int)a.getY(), (int)b.getX(), (int)b.getY());
    }



    private void paintMarker(Graphics2D g2d, PrecisePoint a)
    {
        g2d.drawLine((int)a.getX()-5, (int)a.getY()-5, (int)a.getX()+5, (int)a.getY()+5);
        g2d.drawLine((int)a.getX()+5, (int)a.getY()-5, (int)a.getX()-5, (int)a.getY()+5);
    }


    private void paintElement(Graphics2D g2d, Element elem)
    {

    }


    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        _viewPanelCenter = new PrecisePoint(this.getWidth()/2, this.getHeight()/2);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        //painting grid
        g2d.setColor(new Color(0, 0, 0));
        PrecisePoint leftTopMostScreenPoint = translateIn(new PrecisePoint());

        //System.out.println("leftTopMostScreenPoint=" + leftTopMostScreenPoint.ToString());

        PrecisePoint leftTopMostGridPoint = new PrecisePoint();
        leftTopMostGridPoint.setX( Math.floor(leftTopMostScreenPoint.getX() / gridPointsDistance) * gridPointsDistance);
        leftTopMostGridPoint.setY( Math.floor(leftTopMostScreenPoint.getY() / gridPointsDistance) * gridPointsDistance);

        //System.out.println("leftTopMostGridPoint=" + leftTopMostGridPoint.ToString());

        double curr_grid_dist = gridPointsDistance;

        if(_zoomLevel >= 13)
        {
            curr_grid_dist = curr_grid_dist * 2;
        }

        if(_zoomLevel <= -13)
        {
            curr_grid_dist = curr_grid_dist / 2;
        }

        int x_count = (int)Math.floor(this.getWidth()/(curr_grid_dist*_zoomCoefficient)) + 2;
        int y_count = (int)Math.floor(this.getHeight()/(curr_grid_dist*_zoomCoefficient)) + 2;

        for(int i=0; i<x_count; i++)
        {
            for(int j=0; j<y_count; j++)
            {
                PrecisePoint pnt = new PrecisePoint(leftTopMostGridPoint.getX() + curr_grid_dist*i, leftTopMostGridPoint.getY() - curr_grid_dist*j);
                paintPoint(g2d, translateOut(pnt));
            }
        }

        PrecisePoint zero = translateOut(new PrecisePoint());

        //System.out.println(zero.ToString());

        paintMarker(g2d, zero);

        //--DrawCenter marker
        PrecisePoint p1 = new PrecisePoint(this.getWidth()/2, this.getHeight()/2 - 5);
        PrecisePoint p2 = new PrecisePoint(this.getWidth()/2, this.getHeight()/2 + 5);
        PrecisePoint p3 = new PrecisePoint(this.getWidth()/2 + 5, this.getHeight()/2);
        PrecisePoint p4 = new PrecisePoint(this.getWidth()/2 - 5, this.getHeight()/2);
        paintLine(g2d, p1, p2);
        paintLine(g2d, p3, p4);

        for(int i=0; i<_assignedPane.getElements().size(); i++)
        {
            //TODO paint elements properly
            Element tmp = _assignedPane.getElements().get(i);
            int x = (int)(tmp.getPosition().getX() - tmp.getSize()/2*_zoomCoefficient);
            int y = (int)(tmp.getPosition().getY() - tmp.getSize()/2*_zoomCoefficient);
            PrecisePoint pnttmp = new PrecisePoint(x,y);
            pnttmp = translateOut(pnttmp);
            g2d.drawRect((int)pnttmp.getX(), (int)pnttmp.getY(), (int)(tmp.getSize()*_zoomCoefficient), (int)(tmp.getSize()*_zoomCoefficient));
        }
    }
}
