package TopologyEditor.Utilities;

import TopologyEditor.PrecisePoint;

/**
 * Created by VEF on 5/2/2015.
 */
public interface CoordinateTranslator
{
    // From graphics to real
    public PrecisePoint TranslateIn(PrecisePoint pnt);

    // From real to graphics
    public PrecisePoint TranslateOut(PrecisePoint pnt);
}
