package GUI;


import javax.swing.*;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class OutConsole extends OutputStream
{
    private JTextArea area;
    OutConsole(JTextArea a_area)
    {
        area = a_area;
    }

    @Override
    public void write(int b) throws IOException
    {
        Lock lock = new ReentrantLock();
        lock.lock();
        try
        {
            area.append(String.valueOf((char) b));
            area.setCaretPosition(area.getDocument().getLength());
        }
        finally
        {
            lock.unlock();
        }
    }
}
