import Windows.WindowControls;

import java.awt.AWTException;
import java.io.IOException;

public class Main {

public static void main(String[] args) throws AWTException, IOException {
// TODO Auto-generated method stub
   //int[] tmp= WindowInformation.GetNativeWindowBounds(16260);
//MouseWindow lmw = new MouseWindow();
//lmw.setVisible(true);
//Box ConfirmButton = new Box(-670, 355, 15, 56);

//Thread t1 = new Thread(new Runnable(){public void run(){MouseWrap.MoveMouseLeftClick(ConfirmButton, 1.0f); }});
//t1.start();

//lmw.GetPixels();
//mouseWrap.MoveMouseLeftClick(ConfirmButton, 1.0f);
//mouseWrap.LeftClick();
//RectAlphaWindow RW = new RectAlphaWindow(ConfirmButton);
//RW.setVisible(true);
//while(true)
//{
//MouseWrap.Sleep(1338.0f, 1998.0f);
//MouseWrap.MoveMouseLeftClick(ConfirmButton, 1.0f);
//lmw.Update();
//System.out.println(MouseInfo.getPointerInfo().getLocation());
//}
   WindowControls myWindow = new WindowControls();
   myWindow.show();
}

}