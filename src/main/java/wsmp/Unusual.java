package wsmp;

public class Unusual {

    public native static Object createObj(byte[] res);

    public native static int calcUnusualLevel(Object obj, short[] levels, short[] variance, int count);

    public native static int freeOccObj(Object obj);

}
