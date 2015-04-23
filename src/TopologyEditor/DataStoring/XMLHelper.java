package TopologyEditor.DataStoring;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

/**
 * Created by VEF on 23.04.2015.
 */
public final class XMLHelper
{
    public static void Write(Iterable<Object> list, String filename) throws Exception
    {
        XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(filename)));
        for (Object o : list)
            encoder.writeObject(o);
        encoder.close();
    }



    public static Iterable<Object> Read(String filename) throws Exception
    {
        XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(filename)));
        ArrayList<Object> list = new ArrayList<Object>();
        Object o = decoder.readObject();

        while (o != null)
        {
            list.add(o);
            o = decoder.readObject();
        }

        decoder.close();
        return list;
    }
}
