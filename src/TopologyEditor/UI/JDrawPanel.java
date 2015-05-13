package TopologyEditor.UI;

import TopologyEditor.Elements.DefaultPainter;
import TopologyEditor.Elements.Element;
import TopologyEditor.Elements.IPainter;
import TopologyEditor.Elements.VirtualPane;
import TopologyEditor.EventsHandling.*;
import TopologyEditor.EventsHandling.Listeners.IDoListener;
import TopologyEditor.TestContent.Actions.MoveAction;
import TopologyEditor.Utilities.ICoordinateTranslator;
import TopologyEditor.Utilities.MouseState;
import TopologyEditor.Utilities.PainterLink;
import TopologyEditor.Utilities.PrecisePoint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by 100rub on 14.04.2015.
 */
public class JDrawPanel extends JPanel
{
    private HashMap<PainterLink, IPainter> PainterMap;

    private ActionHandler handler;

    private VirtualPane _assignedPane;
    private int _zoomLevel;                 //0 is default
    private double _zoomCoefficient;        //1.0 is default, closer=bigger
    private PrecisePoint _viewPoint;
    private PrecisePoint _viewPanelCenter;
    private int gridPointsDistance = 20;
    private ICoordinateTranslator _coordTranslator;
    private String _drawMode;

    private ArrayList<Element> SelectedElements;

    private MouseState mouseState;

    private boolean leftMousePressed = false;
    private boolean rightMousePressed = false;

    private PrecisePoint mouseDownPosition;         //on-screen coordinates
    private PrecisePoint mouseUpPosition;           //on-screen coordinates
    private PrecisePoint previousMousePosition;     //on-screen coordinates
    private PrecisePoint currentMousePosition;      //on-screen coordinates

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

        mouseState = MouseState.NONE;

        SelectedElements = new ArrayList<Element>();

        handler = new ActionHandler();

        initializeEvents();
    }



    private void initializeEvents()
    {
        this.addMouseListener(new MouseAdapter()
        {
            /*public void mouseClicked(MouseEvent e)
            {
                if(e.getButton() == MouseEvent.BUTTON1)     //left mouse button
                {

                    //System.out.println("LeftMouseClicked");
                    if(!rightMousePressed)
                    {
                        PrecisePoint clickPosition = CurrentPanel.GetTranslator().TranslatePointIn(new PrecisePoint(e.getX(), e.getY()));
                        SelectedElements.get(0) = CurrentPanel.SelectElement(clickPosition);

                        if (SelectedElement == null)
                        {
                            Contact c = new Contact();
                            c.setSize(Math.random() * 15 + 5);
                            c.setInnerSize(Math.random() * ((c.getSize() - 4) * .8) + 4);
                            handler.Do(new AddElementAction(), new AddElementParameters(c, clickPosition, CurrentPanel.getPane()));
                            CurrentPanel.repaint();
                        }
                        else
                        {
                            handler.Do(new RemoveElementAction(), new ActionParameters(SelectedElement));
                            CurrentPanel.repaint();
                        }
                    }
                }

                if(e.getButton() == MouseEvent.BUTTON3)     //right mouse button
                {
                    //System.out.println("RightMouseClicked");
                    if (!leftMousePressed)
                    {

                    }
                }
            }*/

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
                mouseDownPosition = new PrecisePoint(e.getX(), e.getY());
                previousMousePosition = null;

                if(e.getButton() == MouseEvent.BUTTON1)     //left mouse button pressed
                {
                    leftMousePressed = true;

                    switch (mouseState)
                    {
                        case NONE:
                        {
                            if(!SelectedElements.isEmpty())
                            {
                                for(Element elem : SelectedElements)
                                {
                                    IPainter painter = PainterMap.get(new PainterLink(_drawMode, elem.getClass()));

                                    if(painter == null)
                                    {
                                        painter = new DefaultPainter();
                                    }

                                    if(painter.IsClicked(elem, GetTranslator().TranslatePointIn(mouseDownPosition)))
                                    {
                                        mouseState = MouseState.MOVING_ELEMENT;
                                        break;
                                    }
                                }
                            }
                            if(mouseState == MouseState.NONE)
                                mouseState = MouseState.SELECTING_ELEMENTS;
                            break;
                        }
                        case SELECTING_ELEMENTS:
                        {
                            break;
                        }
                        case MOVING_ELEMENT:
                        {
                            break;
                        }
                        case MOVING_CAMERA:
                        {
                            break;
                        }
                    }
                    //System.out.println("LeftMousePressed");
                }

                if(e.getButton() == MouseEvent.BUTTON3)     //right mouse button pressed
                {
                    rightMousePressed = true;

                    switch (mouseState)
                    {
                        case NONE:
                        {
                            mouseState = MouseState.MOVING_CAMERA;
                            break;
                        }
                        case SELECTING_ELEMENTS:
                        {
                            break;
                        }
                        case MOVING_ELEMENT:
                        {
                            break;
                        }
                        case MOVING_CAMERA:
                        {
                            break;
                        }
                    }
                    //System.out.println("RightMousePressed");
                }
            }

            public void mouseReleased(MouseEvent e)
            {
                mouseUpPosition = new PrecisePoint(e.getX(), e.getY());

                if(e.getButton() == MouseEvent.BUTTON1)     //left mouse button released
                {
                    leftMousePressed = false;

                    switch (mouseState)
                    {
                        case NONE:
                        {
                            break;
                        }
                        case SELECTING_ELEMENTS:
                        {
                            SelectedElements.clear();
                            if(mouseUpPosition != null && mouseDownPosition != null)
                            {
                                PrecisePoint lt = new PrecisePoint();
                                PrecisePoint rb = new PrecisePoint();

                                if(mouseDownPosition.getX() > mouseUpPosition.getX())
                                {
                                    rb.setX(mouseDownPosition.getX());
                                    lt.setX(mouseUpPosition.getX());
                                }
                                else
                                {
                                    rb.setX(mouseUpPosition.getX());
                                    lt.setX(mouseDownPosition.getX());
                                }

                                if(mouseDownPosition.getY() > mouseUpPosition.getY())
                                {
                                    rb.setY(mouseDownPosition.getY());
                                    lt.setY(mouseUpPosition.getY());
                                }
                                else
                                {
                                    rb.setY(mouseUpPosition.getY());
                                    lt.setY(mouseDownPosition.getY());
                                }

                                for(Element elem : getPane().getElements())
                                {
                                    IPainter painter = PainterMap.get(new PainterLink(_drawMode, elem.getClass()));

                                    if(painter == null)
                                    {
                                        painter = new DefaultPainter();
                                    }

                                    if(painter.IsSelected(elem, GetTranslator().TranslatePointIn(lt), GetTranslator().TranslatePointIn(rb)))
                                    {
                                        SelectedElements.add(elem);
                                    }
                                }
                            }
                            System.out.println("Selected elements: " + SelectedElements.size());
                            repaint();

                            mouseUpPosition = null;
                            mouseDownPosition = null;
                            currentMousePosition = null;
                            mouseState = MouseState.NONE;
                            break;
                        }
                        case MOVING_ELEMENT:
                        {
                            if(!SelectedElements.isEmpty())
                            {
                                PrecisePoint clickPosition = GetTranslator().TranslatePointIn(new PrecisePoint(e.getX(), e.getY()));
                                PrecisePoint elementPosition = GetTranslator().TranslatePointIn(mouseDownPosition);

                                for(Element elem : SelectedElements)
                                {
                                    if (elem != null)
                                    {
                                        handler.Do(new MoveAction(), new PointTargetedActionParameters(elem, clickPosition.CopyShift(elementPosition.Negatite())));
                                    }
                                }
                            }

                            repaint();

                            mouseState = MouseState.NONE;
                            break;
                        }
                        case MOVING_CAMERA:
                        {
                            break;
                        }
                    }
                    //System.out.println("LeftMouseReleased");
                }

                if(e.getButton() == MouseEvent.BUTTON3)     //right mouse button released
                {
                    rightMousePressed = false;

                    switch (mouseState)
                    {
                        case NONE:
                        {
                            break;
                        }
                        case SELECTING_ELEMENTS:
                        {
                            break;
                        }
                        case MOVING_ELEMENT:
                        {
                            break;
                        }
                        case MOVING_CAMERA:
                        {
                            repaint();
                            mouseState = MouseState.NONE;
                            break;
                        }
                    }
                    //System.out.println("RightMouseReleased");
                }
            }
        });

        this.addMouseMotionListener(new MouseMotionListener() {
            public void mouseDragged(MouseEvent e) {
                if (previousMousePosition == null) {
                    previousMousePosition = mouseDownPosition;
                }

                currentMousePosition = new PrecisePoint(e.getX(), e.getY());

                switch (mouseState) {
                    case NONE: {
                        break;
                    }
                    case SELECTING_ELEMENTS: {
                        break;
                    }
                    case MOVING_ELEMENT: {
                        break;
                    }
                    case MOVING_CAMERA: {
                        double dx = e.getX() - previousMousePosition.getX();
                        double dy = e.getY() - previousMousePosition.getY();

                        MoveViewPoint(-dx, dy);

                        previousMousePosition.setX(e.getX());
                        previousMousePosition.setY(e.getY());
                        break;
                    }
                }

                repaint();
            }

            public void mouseMoved(MouseEvent e)
            {
                currentMousePosition = new PrecisePoint(e.getX(), e.getY());
                //System.out.println("mouseMoved");
            }
        });

        this.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                int wheel_d = e.getWheelRotation();
                //System.out.println("mouseWheelMoved " + wheel_d);

                if (Math.abs(getZoomLevel() + wheel_d) <= 20) {
                    setZoomLevel(getZoomLevel() + wheel_d);
                }

                int zoom_lvl = getZoomLevel();
                double res_zoom_coef = 1;
                for (int i = 0; i < Math.abs(zoom_lvl); i++) {
                    if (zoom_lvl < 0) {
                        res_zoom_coef = res_zoom_coef * 1.1;
                        //CurrentPanel.set_zoomCoefficient(CurrentPanel.get_zoomCoefficient() * 1.1);
                    }
                    if (zoom_lvl > 0) {
                        res_zoom_coef = res_zoom_coef / 1.1;
                        //CurrentPanel.set_zoomCoefficient(CurrentPanel.get_zoomCoefficient() / 1.1);
                    }
                }

                PrecisePoint mousePosInsideBefore = GetTranslator().TranslatePointIn(new PrecisePoint(e.getX(), e.getY()));

                setZoomCoefficient(res_zoom_coef);

                PrecisePoint mousePosInsideAfter = GetTranslator().TranslatePointIn(new PrecisePoint(e.getX(), e.getY()));

                PrecisePoint d = new PrecisePoint(mousePosInsideBefore.getX() - mousePosInsideAfter.getX(), mousePosInsideBefore.getY() - mousePosInsideAfter.getY());

                if (wheel_d < 0)
                {
                    MoveViewPoint(d.getX() * getZoomCoefficient(), d.getY() * getZoomCoefficient());
                }

                if (wheel_d > 0)
                {
                    MoveViewPoint(d.getX() * getZoomCoefficient(), d.getY() * getZoomCoefficient());
                }

                //System.out.println("zoom_lvl " + CurrentPanel.get_zoomLevel());
                //System.out.println("zoom_coef " + CurrentPanel.get_zoomCoefficient());
                repaint();
            }
        });
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



    public ActionHandler getHandler()
    {
        return handler;
    }



    public void setHandler(ActionHandler hndlr)
    {
        handler = hndlr;
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



    public void setMouseDownPosition(PrecisePoint pnt)
    {
        mouseDownPosition = pnt.Copy();
    }



    public PrecisePoint getMouseDownPosition()
    {
        return mouseDownPosition;
    }



    public void setMouseUpPosition(PrecisePoint pnt)
    {
        mouseUpPosition = pnt.Copy();
    }



    public PrecisePoint getMouseUpPosition()
    {
        return mouseUpPosition;
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



    private void paintSelectionRect(Graphics2D g2d)
    {
        if(currentMousePosition != null && mouseDownPosition != null && mouseState == MouseState.SELECTING_ELEMENTS)
        {
            PrecisePoint lt = new PrecisePoint();
            PrecisePoint rt = new PrecisePoint();
            PrecisePoint lb = new PrecisePoint();
            PrecisePoint rb = new PrecisePoint();

            if(mouseDownPosition.getX() > currentMousePosition.getX())
            {
                rt.setX(mouseDownPosition.getX());
                rb.setX(mouseDownPosition.getX());

                lt.setX(currentMousePosition.getX());
                lb.setX(currentMousePosition.getX());
            }
            else
            {
                rt.setX(currentMousePosition.getX());
                rb.setX(currentMousePosition.getX());

                lt.setX(mouseDownPosition.getX());
                lb.setX(mouseDownPosition.getX());
            }

            if(mouseDownPosition.getY() > currentMousePosition.getY())
            {
                lb.setY(mouseDownPosition.getY());
                rb.setY(mouseDownPosition.getY());

                lt.setY(currentMousePosition.getY());
                rt.setY(currentMousePosition.getY());
            }
            else
            {
                lb.setY(currentMousePosition.getY());
                rb.setY(currentMousePosition.getY());

                lt.setY(mouseDownPosition.getY());
                rt.setY(mouseDownPosition.getY());
            }

            paintLine(g2d, lt, rt);
            paintLine(g2d, lb, rb);
            paintLine(g2d, lt, lb);
            paintLine(g2d, rt, rb);
        }
    }



    private void paintFigure(Graphics2D g2d, ArrayList<PrecisePoint> list)
    {
        //TODO complete
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

        //--Draw all elements

        g2d.setColor(Color.black);
        for(Element element : _assignedPane.getElements())
        {
            IPainter painter = PainterMap.get(new PainterLink(_drawMode, element.getClass()));

            if(painter == null)
            {
                painter = new DefaultPainter();
            }

            if(painter.IsOnScreen(element, lt, rt))
            {
                painter.Draw(element, (Graphics2D)g2d.create(), GetTranslator());
            }
        }

        //--Draw selected elements

        g2d.setColor(Color.red);
        for(Element element : SelectedElements)
        {
            IPainter painter = PainterMap.get(new PainterLink(_drawMode, element.getClass()));

            if(painter == null)
            {
                painter = new DefaultPainter();
            }

            if(painter.IsOnScreen(element, lt, rt))
            {
                painter.DrawBorder(element,(Graphics2D) g2d.create(), GetTranslator());
            }
        }

        Stroke stroke = g2d.getStroke();
        g2d.setStroke(dashedStroke);
        if (zero.getX() > 0 && zero.getX() < this.getWidth())
        {
            g2d.setColor(Color.blue);
            paintLine(g2d, new PrecisePoint(zero.getX(), zero.getY() % 20), new PrecisePoint(zero.getX(), this.getHeight()));
        }
        if (zero.getY() > 0 && zero.getY() < this.getHeight())
        {
            g2d.setColor(Color.red);
            paintLine(g2d, new PrecisePoint(zero.getX() % 20, zero.getY()), new PrecisePoint(this.getWidth(), zero.getY()));
        }
        g2d.setStroke(stroke);

        PrecisePoint p1 = new PrecisePoint(this.getWidth()/2, this.getHeight()/2 - 5);
        PrecisePoint p2 = new PrecisePoint(this.getWidth()/2, this.getHeight()/2 + 5);
        PrecisePoint p3 = new PrecisePoint(this.getWidth()/2 + 5, this.getHeight()/2);
        PrecisePoint p4 = new PrecisePoint(this.getWidth()/2 - 5, this.getHeight()/2);
        g2d.setColor(Color.black);
        paintLine(g2d, p1, p2);
        paintLine(g2d, p3, p4);

        g2d.setColor(new Color(0, 0, 255));
        paintSelectionRect((Graphics2D) g2d.create());
    }
}
