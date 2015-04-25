package TopologyEditor.DataStoring;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by VEF on 23.04.2015.
 */
public final class XMLHelper
{
    public static void Write(String filename, Object obj) throws FileNotFoundException
    {
        XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(filename)));
        encoder.writeObject(obj);
        encoder.close();
    }



    public static Object Read(String filename) throws FileNotFoundException
    {
        XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(filename)));
        Object obj = decoder.readObject();
        decoder.close();
        return obj;
    }
}
