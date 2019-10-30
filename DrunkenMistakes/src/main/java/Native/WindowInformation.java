package Native;

public class WindowInformation {
    //need either JNA or JNI fix
    static
    {
        System.load(System.getProperty("user.dir")+"\\DrunkenMistakes\\NativeSrc\\WindowInfo.dll");
    }

    static public native int[] GetNativeWindowBounds(int PID);
}
