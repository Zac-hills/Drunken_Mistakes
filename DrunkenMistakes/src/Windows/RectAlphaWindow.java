package Windows;

import Windows.Box;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class RectAlphaWindow extends JFrame{
public RectAlphaWindow()
{
super("");
addComponentListener(new ComponentAdapter()
{
// Give the window an elliptical shape.
// If the window is resized, the shape is recalculated here.
@Override
public void componentResized(ComponentEvent e) {
setShape(new Rectangle(0, 0, getWidth(), getHeight()));
}

});
setUndecorated(true);
setSize(300,200);
setLocationRelativeTo(null);
setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
setOpacity(.50f);
}

public RectAlphaWindow(Box b)
{
super("");
addComponentListener(new ComponentAdapter()
{
// Give the window an elliptical shape.
// If the window is resized, the shape is recalculated here.
@Override
public void componentResized(ComponentEvent e) {
setShape(new Rectangle(0, 0, getWidth(), getHeight()));
}

});
setUndecorated(true);
setSize(b.m_Size.x, b.m_Size.y);
setLocationRelativeTo(null);
setLocation(b.m_pos.x, b.m_pos.y);
setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
setOpacity(.50f);
}

public void ColorBoarder(boolean b)
{
if(b)
{
getRootPane().setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.RED));
}
else
{
getRootPane().setBorder(null);
}
}


}
