package TopologyEditor.Utilities;

import TopologyEditor.PrecisePoint;

/**
 * Created by VEF on 5/2/2015.
 */
public interface ICoordinateTranslator
{
    // From graphics to real
    public PrecisePoint TranslatePointIn(PrecisePoint point);

    // From real to graphics
    public PrecisePoint TranslatePointOut(PrecisePoint point);

    // From real to graphics
    public double TranslateValueIn(double value);

    // From real to graphics
    public double TranslateValueOut(double value);
}
