package MouseUtility;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseMotionListener;
import Windows.RectAlphaWindow;

public class MouseWindow extends RectAlphaWindow
{

public MouseWindow()
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
