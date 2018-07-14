
import MouseUtility.MouseWindow;
import MouseUtility.MouseWrap;
import Windows.Box;

import java.awt.AWTException;

public class Main {

public static void main(String[] args) throws AWTException {
// TODO Auto-generated method stub

MouseWindow lmw = new MouseWindow();
lmw.setVisible(true);
Box ConfirmButton = new Box(-670, 355, 15, 56);
Thread t1 = new Thread(new Runnable(){public void run(){MouseWrap.MoveMouseLeftClick(ConfirmButton, 1.0f); }});
t1.start();
//mouseWrap.MoveMouseLeftClick(ConfirmButton, 1.0f);
//mouseWrap.LeftClick();
//RectAlphaWindow RW = new RectAlphaWindow(ConfirmButton);
//RW.setVisible(true);
while(true)
{
MouseWrap.Sleep(1338.0f, 1998.0f);
MouseWrap.MoveMouseLeftClick(ConfirmButton, 1.0f);
lmw.Update();
//System.out.println(MouseInfo.getPointerInfo().getLocation());
}
}

}