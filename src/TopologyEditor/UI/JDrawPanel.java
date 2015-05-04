package TopologyEditor.UI;

import TopologyEditor.Elements.DefaultPainter;
import TopologyEditor.Elements.Element;
import TopologyEditor.Elements.IPainter;
import TopologyEditor.Elements.VirtualPane;
import TopologyEditor.Utilities.ICoordinateTranslator;
import TopologyEditor.Utilities.PainterLink;
import TopologyEditor.Utilities.PrecisePoint;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by 100rub on 14.04.2015.
 */
public class JDrawPanel extends JPanel
{
    private HashMap<PainterLink, IPainter> PainterMap;

    private VirtualPane _assignedPane;
    private int _zoomLevel;                 //0 is default
    private double _zoomCoefficient;        //1.0 is default, closer=bigger
    private PrecisePoint _viewPoint;
    private PrecisePoint _viewPanelCenter;
    private int gridPointsDistance = 20;
    private ICoordinateTranslator _coordTranslator;
    private String _drawMode;

    private boolean _hasUnsavedChanges;

    private final static BasicStroke dashedStroke = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, new float[] {10.0f}, 0.0f);



    public JDrawPanel(VirtualPane pane, HashMap<PainterLink, IPainter> painterMap)
    {
        this.PainterMap = painterMap;

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
        _hasUnsavedChanges = false;
    }



    public void AssignPane(VirtualPane pane)
    {
        _assignedPane = pane;
    }



    public void SetDrawMode(String value)
    {
        _drawMode = value;
    }



    public Element SelectElement(PrecisePoint point)
    {
        for(Element element : _assignedPane.getElements())
        {
            IPainter painter = PainterMap.get(new PainterLink("test", element.getClass()));

            if(painter == null)
            {
                painter = new DefaultPainter();
            }

            if(painter.IsClicked(element, point))
            {
                return element;
            }
        }
        return null;
    }



    public VirtualPane getPane()
    {
        return _assignedPane;
    }



    public PrecisePoint getViewPoint()
    {
        return _viewPoint;
    }



    public void setViewPoint(PrecisePoint pnt)
    {
        _viewPoint = pnt;
    }



    public PrecisePoint getViewPanelCenter()
    {
        return _viewPanelCenter;
    }



    public void setViewPanelCenter(PrecisePoint pnt)
    {
        _viewPanelCenter = pnt;
    }



    public int getZoomLevel()
    {
        return _zoomLevel;
    }



    public void setZoomLevel(int lvl)
    {
        _zoomLevel = lvl;
    }



    public boolean getHasUnsavedChanges()
    {
        return _hasUnsavedChanges;
    }



    public void setHasUnsavedChanges(boolean b)
    {
        _hasUnsavedChanges = b;
    }



    public void setZoomCoefficient(double d)
    {
        _zoomCoefficient = d;
    }



    public double getZoomCoefficient()
    {
        return _zoomCoefficient;
    }



    public void MoveViewPoint(double x, double y)
    {
        _viewPoint.setX(_viewPoint.getX() + x / _zoomCoefficient);
        _viewPoint.setY(_viewPoint.getY() + y / _zoomCoefficient);
    }



    public ICoordinateTranslator GetTranslator()
    {
        if (_coordTranslator == null)
        {
            final JDrawPanel jdp = this;
            _coordTranslator = new ICoordinateTranslator()
            {
                public PrecisePoint TranslatePointIn(PrecisePoint point) {
                    return jdp.TranslatePointIn(point);
                }

                public PrecisePoint TranslatePointOut(PrecisePoint point) {
                    return jdp.TranslatePointOut(point);
                }

                public double TranslateValueIn(double value) {
                    return jdp.TranslateValueIn(value);
                }

                public double TranslateValueOut(double value) {
                    return jdp.TranslateValueOut(value);
                }
            };
        }
        return _coordTranslator;
    }



    private PrecisePoint TranslatePointIn(PrecisePoint pnt)      //translates position from DrawPanel into position on VirtualPane
    {
        PrecisePoint res = new PrecisePoint();

        _viewPanelCenter = new PrecisePoint(this.getWidth()/2, this.getHeight()/2);

        PrecisePoint d = new PrecisePoint(pnt.getX() - _viewPanelCenter.getX(), pnt.getY() - _viewPanelCenter.getY());
        res.setX(_viewPoint.getX() + d.getX() / _zoomCoefficient);
        res.setY(_viewPoint.getY() - d.getY() / _zoomCoefficient);

        return res;
    }



    private PrecisePoint TranslatePointOut(PrecisePoint pnt)     //translates position from VirtualPane into position on DrawPanel
    {
        PrecisePoint res = new PrecisePoint();

        _viewPanelCenter = new PrecisePoint(this.getWidth()/2, this.getHeight()/2);

        PrecisePoint d = new PrecisePoint(pnt.getX() - _viewPoint.getX(), pnt.getY() - _viewPoint.getY());
        res.setX(_viewPanelCenter.getX() + d.getX()*_zoomCoefficient);
        res.setY(_viewPanelCenter.getY() - d.getY()*_zoomCoefficient);

        return res;
    }



    private double TranslateValueIn(double val)
    {
        return val / _zoomCoefficient;
    }



    private double TranslateValueOut(double val)
    {
        return val * _zoomCoefficient;
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


    private void paintFigure(Graphics2D g2d, ArrayList<PrecisePoint> list)
    {
        //TODO
        for(int i=0; i<list.size()-1; i++)
        {
            PrecisePoint a = list.get(i);
            PrecisePoint b = list.get(i+1);
            //g2d.drawLine((int)a.getX(), (int)a.getY(), (int)b.getX(), (int)b.getY());
        }
    }


    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        _viewPanelCenter = new PrecisePoint(this.getWidth()/2, this.getHeight()/2);

        Graphics2D g2d = (Graphics2D) g.create();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        //painting grid
        g2d.setColor(new Color(0, 0, 0));
        PrecisePoint leftTopMostScreenPoint = TranslatePointIn(new PrecisePoint());

        //System.out.println("leftTopMostScreenPoint=" + leftTopMostScreenPoint.ToString());

        PrecisePoint leftTopMostGridPoint = new PrecisePoint();
        leftTopMostGridPoint.setX( Math.floor(leftTopMostScreenPoint.getX() / gridPointsDistance) * gridPointsDistance);
        leftTopMostGridPoint.setY( Math.floor(leftTopMostScreenPoint.getY() / gridPointsDistance) * gridPointsDistance);

        //System.out.println("leftTopMostGridPoint=" + leftTopMostGridPoint.ToString());

        double curr_grid_dist = gridPointsDistance;

        if(_zoomLevel >= 13)        //TODO upgrade zooming
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
                paintPoint(g2d, TranslatePointOut(pnt));
            }
        }

        //--DrawCenter marker

        PrecisePoint zero = TranslatePointOut(new PrecisePoint());
        if (zero.getX() > 0 && zero.getX() < this.getWidth())
        {
            g2d.setColor(Color.blue);
            paintLine(g2d, new PrecisePoint(zero.getX(), 0), new PrecisePoint(zero.getX(), this.getHeight()));
        }
        if (zero.getY() > 0 && zero.getY() < this.getHeight())
        {
            g2d.setColor(Color.red);
            paintLine(g2d, new PrecisePoint(0, zero.getY()), new PrecisePoint(this.getWidth(), zero.getY()));
        }

        PrecisePoint lt = TranslatePointIn(new PrecisePoint());
        PrecisePoint rt = TranslatePointIn(new PrecisePoint(this.getWidth(), this.getHeight()));

        //System.out.println("Element count: " + _assignedPane.getElements().size());

        for(Element element : _assignedPane.getElements())
        {
            IPainter painter = PainterMap.get(new PainterLink(_drawMode, element.getClass()));

            if(painter == null)
            {
                painter = new DefaultPainter();
            }

            if(painter.IsOnScreen(element, lt, rt))
            {
                painter.Draw(element, g2d, GetTranslator());
            }
        }

        if (zero.getX() > 0 && zero.getX() < this.getWidth())
        {
            g2d.setColor(Color.blue);
            Stroke stroke = g2d.getStroke();
            g2d.setStroke(dashedStroke);
            paintLine(g2d, new PrecisePoint(zero.getX(), zero.getY() % 20), new PrecisePoint(zero.getX(), this.getHeight()));
            g2d.setStroke(stroke);
        }
        if (zero.getY() > 0 && zero.getY() < this.getHeight())
        {
            g2d.setColor(Color.red);
            Stroke stroke = g2d.getStroke();
            g2d.setStroke(dashedStroke);
            paintLine(g2d, new PrecisePoint(zero.getX() % 20, zero.getY()), new PrecisePoint(this.getWidth(), zero.getY()));
            g2d.setStroke(stroke);
        }

        PrecisePoint p1 = new PrecisePoint(this.getWidth()/2, this.getHeight()/2 - 5);
        PrecisePoint p2 = new PrecisePoint(this.getWidth()/2, this.getHeight()/2 + 5);
        PrecisePoint p3 = new PrecisePoint(this.getWidth()/2 + 5, this.getHeight()/2);
        PrecisePoint p4 = new PrecisePoint(this.getWidth()/2 - 5, this.getHeight()/2);
        g2d.setColor(Color.black);
        paintLine(g2d, p1, p2);
        paintLine(g2d, p3, p4);
    }
}
