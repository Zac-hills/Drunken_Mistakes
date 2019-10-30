package Events;

import MouseUtility.MouseWrap;
import Windows.Box;

public class LeftClickBox implements Runnable {
    Box m_Box;
    Runnable m_Callback;
    LeftClickBox(Box a_Box, Runnable a_Callback)
    {
        m_Box = a_Box;
        m_Callback = a_Callback;
    }
    @Override
    public void run()
    {
        MouseWrap.RightClickBox(m_Box);
        if(m_Callback!=null)
        {
            m_Callback.run();
        }
    }

}
