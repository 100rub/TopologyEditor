package TopologyEditor;

import java.awt.*;

/**
 * Created by 100rub on 11.04.2015.
 */
public class PrecisePoint
{
    private double _x;
    private double _y;

    public PrecisePoint()
    {
        _x = 0;
        _y = 0;
    }

    public PrecisePoint(double x, double y)
    {
        this._x = x;
        this._y = y;
    }

    public PrecisePoint(Point p)
    {
        this._x = (double)p.x;
        this._y = (double)p.y;
    }


    public void Shift(double x, double y)
    {
        _x += x;
        _y += y;
    }


    public void Shift(PrecisePoint shift)
    {
        _x += shift.getX();
        _y += shift.getY();
    }

    public Point ToPoint()
    {
        return (new Point( (int)_x, (int)_y ));
    }


    public String ToString()
    {
        return ("[x=" + (float)this.getX() + ", y=" + (float)this.getY() + "]");
    }

    public PrecisePoint Copy()
    {
        PrecisePoint res = new PrecisePoint(this._x, this._y);
        return res;
    }

    public PrecisePoint CopyShift(double x, double y)
    {
        PrecisePoint res = new PrecisePoint(this._x + x, this._y + y);
        return res;
    }

    public boolean Equals(PrecisePoint pnt)
    {
        if(pnt == null)
            return false;

        if (this._x == pnt._x && this._y == pnt._y)
        {
            return true;
        }
        else
        {
            return false;
        }
    }



    public double getX()
    {
        return _x;
    }

    public void setX(double value)
    {
         _x = value;
    }

    public double getY()
    {
        return _y;
    }

    public void setY(double value)
    {
        _y = value;
    }
}
