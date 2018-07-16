package MouseUtility;

import java.awt.*;
import java.awt.event.MouseMotionListener;
import Windows.RectAlphaWindow;

public class MouseWindow extends RectAlphaWindow
{

public MouseWindow() throws AWTException
{
super();
}

public void Update()
{
int tempWidth = getWidth() / 2;
int tempHeight = getHeight() / 2;
Point p = MouseInfo.getPointerInfo().getLocation();
p.x -= tempWidth;
p.y -= tempHeight;
setLocation(p);
}

MouseMotionListener ml;

}
