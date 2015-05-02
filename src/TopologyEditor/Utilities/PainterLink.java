package TopologyEditor.Utilities;

/**
 * Created by 100rub on 03.05.2015.
 */
public class PainterLink
{
    private String DrawMode;
    private Class Painter;

    public PainterLink()
    {
        this.DrawMode = null;
        this.Painter = null;
    }

    public PainterLink(String str, Class pntr)
    {
        this.DrawMode = str;
        this.Painter = pntr;
    }

    public boolean Equals(Object other)
    {
        if (other instanceof PainterLink)
        {
            PainterLink otherPair = (PainterLink) other;
            return
                    ((  this.DrawMode == otherPair.DrawMode ||
                            ( this.DrawMode != null && otherPair.DrawMode != null &&
                                    this.DrawMode.equals(otherPair.DrawMode))) &&
                            ( this.Painter == otherPair.Painter ||
                                    ( this.Painter != null && otherPair.Painter != null &&
                                            this.Painter.equals(otherPair.Painter))) );
        }

        return false;
    }

    public int HashCode()
    {
        int hashFirst = DrawMode != null ? DrawMode.hashCode() : 0;
        int hashSecond = Painter != null ? Painter.hashCode() : 0;

        return (hashFirst *65497)^hashSecond;
    }
}
