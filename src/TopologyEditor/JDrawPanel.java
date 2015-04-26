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
    private double _zoomCoefficient;        //1.0 is default, closer=bigger
    private PrecisePoint _viewPointCoordsInside;
    private PrecisePoint _viewPortCenter;
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
        _zoomCoefficient = 1;
        _viewPointCoordsInside = new PrecisePoint();
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



    public PrecisePoint get_viewPointCoordsInside()
    {
        return _viewPointCoordsInside;
    }



    public void set_viewPointCoordsInside(PrecisePoint pnt)
    {
        _viewPointCoordsInside = pnt;
    }



    public PrecisePoint get_viewPortCenter()
    {
        return _viewPortCenter;
    }



    public void set_viewPortCenter(PrecisePoint pnt)
    {
        _viewPortCenter = pnt;
    }



    private PrecisePoint translateIn(PrecisePoint pnt)      //translates position from DrawPanel into position on VirtualPane
    {
        PrecisePoint res = new PrecisePoint();

        _viewPortCenter = new PrecisePoint(this.getWidth()/2, this.getHeight()/2);

        PrecisePoint d = new PrecisePoint(pnt.getX() - _viewPortCenter.getX(), pnt.getY() - _viewPortCenter.getY());
        res.setX(_viewPointCoordsInside.getX() + d.getX()/_zoomCoefficient);
        res.setY(_viewPointCoordsInside.getY() + d.getY() / _zoomCoefficient);

        return res;
    }



    private PrecisePoint translateOut(PrecisePoint pnt)     //translates position from VirtualPane into position on DrawPanel
    {
        PrecisePoint res = new PrecisePoint();

        _viewPortCenter = new PrecisePoint(this.getWidth()/2, this.getHeight()/2);

        PrecisePoint d = new PrecisePoint(pnt.getX() - _viewPortCenter.getX(), pnt.getY() - _viewPortCenter.getY());
        res.setX(_viewPointCoordsInside.getX() + d.getX()*_zoomCoefficient);
        res.setY(_viewPointCoordsInside.getY() + d.getY()*_zoomCoefficient);

        return res;
    }



    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        _viewPortCenter = new PrecisePoint(this.getWidth()/2, this.getHeight()/2);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        //painting grid
        g2d.setColor(new Color(0, 0, 0));
        PrecisePoint leftTopMostInside1 = new PrecisePoint();
        leftTopMostInside1.setX(_viewPointCoordsInside.getX() - this.getWidth() / 2);
        leftTopMostInside1.setY(_viewPointCoordsInside.getY() - this.getHeight() / 2);

        PrecisePoint leftTopMostInside2 = new PrecisePoint();
        leftTopMostInside2.setX( Math.floor(leftTopMostInside1.getX()/gridPointsDistance*_zoomCoefficient) * (gridPointsDistance*_zoomCoefficient) );
        leftTopMostInside2.setY(Math.floor(leftTopMostInside1.getY() / gridPointsDistance * _zoomCoefficient) * (gridPointsDistance * _zoomCoefficient));

        PrecisePoint d = new PrecisePoint(leftTopMostInside2.getX() - leftTopMostInside1.getX(), leftTopMostInside2.getY() - leftTopMostInside1.getY());

        int x_count = (int)Math.floor(this.getWidth()/(gridPointsDistance*_zoomCoefficient)) + 1;
        int y_count = (int)Math.floor(this.getHeight()/(gridPointsDistance*_zoomCoefficient)) + 1;

        for(int i=0; i<x_count; i++)
        {
            for(int j=0; j<y_count; j++)
            {
                g2d.drawLine((int)(d.getX()+gridPointsDistance*_zoomCoefficient*i), (int)(d.getY()+gridPointsDistance*_zoomCoefficient*j),
                        (int)(d.getX()+gridPointsDistance*_zoomCoefficient*i), (int)(d.getY()+gridPointsDistance*_zoomCoefficient*j));
            }
        }

        PrecisePoint p1 = new PrecisePoint(0,0);
        PrecisePoint p2 = new PrecisePoint(10,10);

        p1 = translateOut(p1);
        p2 = translateOut(p2);

        g2d.drawLine((int)p1.getX(), (int)p1.getY(), (int)p2.getX(), (int)p2.getY());

        for(int i=0; i<_assignedPane.getElements().size(); i++)
        {
            //TODO paint elements properly
            Element tmp = _assignedPane.getElements().get(i);
            int x = (int)(tmp.getPosition().getX() - tmp.getSize()/2*_zoomCoefficient);
            int y = (int)(tmp.getPosition().getY() - tmp.getSize()/2*_zoomCoefficient);
            PrecisePoint pnttmp = new PrecisePoint(x,y);
            pnttmp = translateOut(pnttmp);
            g2d.drawRect((int)pnttmp.getX(), (int)pnttmp.getY(), (int)(tmp.getSize()*_zoomCoefficient), (int)(tmp.getSize()*_zoomCoefficient));
            //System.out.println(pnttmp.getX() + " " + pnttmp.getY());
        }
        //g2d.fill(myRect);
    }
}
