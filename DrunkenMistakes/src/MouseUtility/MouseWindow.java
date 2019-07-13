package MouseUtility;

import java.awt.*;
import java.awt.event.*;

import Windows.RectAlphaWindow;
import Windows.Box;

import javax.swing.*;

import static java.lang.Math.abs;

public class MouseWindow extends RectAlphaWindow implements MouseListener, KeyListener {

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_ESCAPE)
        {
            completed=true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public interface CallBack
{

    void twoArgs(Box a_box, String a_string);
}

public MouseWindow(CallBack a_CallBack) throws AWTException
{
super();
callBack = a_CallBack;
addMouseListener(this);
addKeyListener(this);
this.toFront();
this.setAlwaysOnTop(true);
this.getRootPane().setOpaque(true);
this.setBackground(new Color(0,0,0,0.5f));
    this.setOpacity(0.5f);
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

@Override
public void mouseClicked(MouseEvent e)
{
    startPoint = e.getLocationOnScreen();
}

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
    endPoint = e.getLocationOnScreen();
    setVisible(false);
    windowName = JOptionPane.showInputDialog("Enter Window Name");
    box.m_pos = new Point((endPoint.x + startPoint.x) / 2, (endPoint.y + startPoint.y)/2);
    box.m_Size = new Point(abs(endPoint.x - startPoint.x), abs(endPoint.y - startPoint.y));
    callBack.twoArgs(box, windowName);
        completed = true;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    Point startPoint = new Point();
    Point endPoint = new Point();
    MouseMotionListener ml;
    public boolean completed=false;
    String windowName = new String();
    Box box = new Box(new Point(), new Point());
    CallBack callBack;
}
