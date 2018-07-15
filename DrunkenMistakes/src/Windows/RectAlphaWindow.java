package Windows;

import Windows.Box;
import com.sun.awt.AWTUtilities;
import com.sun.java.swing.plaf.windows.WindowsGraphicsUtils;
import sun.awt.image.OffScreenImage;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class RectAlphaWindow extends JFrame{
  BufferedImage m_BufferedImage;
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
  setBackground(new Color(0,0,0,0));
  getRootPane().setOpaque(false);

  getRootPane().setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.RED));
m_BufferedImage = new BufferedImage(getWidth(),getHeight(), BufferedImage.TYPE_INT_ARGB);

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
//setOpacity(.10f);
getRootPane().setOpaque(false);
getRootPane().setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.RED));
  m_BufferedImage = new BufferedImage(getWidth(),getHeight(), BufferedImage.TYPE_INT_ARGB);
}
public byte[] GetPixels() throws IOException {
  //paintAll(m_BufferedImage.createGraphics());
  getRootPane().paintAll(m_BufferedImage.createGraphics());
  ImageIO.write(m_BufferedImage, "PNG", new File("filename.png"));
  return ((DataBufferByte) m_BufferedImage.getRaster().getDataBuffer()).getData();
}
public Box GetBounds()
{
  return new Box(getLocation(), new Point(getSize().width, getSize().height));
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
