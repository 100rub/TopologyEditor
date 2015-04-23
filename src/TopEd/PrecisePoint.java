package TopEd;

import java.awt.*;

/**
 * Created by 100rub on 11.04.2015.
 */
public class PrecisePoint
{
    public double x;
    public double y;

    public PrecisePoint()
    {
        x = 0;
        y = 0;
    }

    public PrecisePoint(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    public PrecisePoint(Point p)
    {
        this.x = (double)p.x;
        this.y = (double)p.y;
    }

    public Point ToPoint()
    {
        return (new Point( (int)x, (int)y ));
    }

    public PrecisePoint Copy()
    {
        PrecisePoint res = new PrecisePoint(this.x, this.y);
        return res;
    }

    public PrecisePoint CopyShift(double x, double y)
    {
        PrecisePoint res = new PrecisePoint(this.x + x, this.y + y);
        return res;
    }

    public boolean Equals(PrecisePoint pnt)
    {
        if(pnt == null)
            return false;

        if (this.x == pnt.x && this.y == pnt.y)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
