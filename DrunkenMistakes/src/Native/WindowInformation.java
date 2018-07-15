package Native;

public class WindowInformation {
    //need either JNA or JNI fix
    static
    {
        System.load("C:\\Users\\zackh\\IdeaProjects\\Drunken_Mistakes\\DrunkenMistakes\\NativeSrc\\WindowInfo.dll");
    }

    static public native int[] GetNativeWindowBounds(int PID);
}
