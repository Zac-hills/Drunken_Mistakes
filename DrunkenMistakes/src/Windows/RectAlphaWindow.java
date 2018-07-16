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

public class RectAlphaWindow extends JFrame
{
		BufferedImage m_BufferedImage;
		Robot m_Robot;

		public RectAlphaWindow() throws AWTException
		{
				super("");
				addComponentListener(new ComponentAdapter()
				{
						// Give the window an elliptical shape.
// If the window is resized, the shape is recalculated here.
						@Override
						public void componentResized(ComponentEvent e)
						{
								setShape(new Rectangle(0, 0, getWidth(), getHeight()));
						}

				});
				GraphicsEnvironment l_GraphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
				GraphicsDevice l_GraphicDevice = l_GraphicsEnvironment.getDefaultScreenDevice();
				m_Robot = new Robot(l_GraphicDevice);
				setUndecorated(true);
				setSize(300, 200);
				setLocationRelativeTo(null);
				setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				setBackground(new Color(0, 0, 0, 0));
				getRootPane().setOpaque(false);

				getRootPane().setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.RED));
				m_BufferedImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);

		}

		public RectAlphaWindow(Box b) throws AWTException
		{
				super("");
				addComponentListener(new ComponentAdapter()
				{
						// Give the window an elliptical shape.
// If the window is resized, the shape is recalculated here.
						@Override
						public void componentResized(ComponentEvent e)
						{
								setShape(new Rectangle(0, 0, getWidth(), getHeight()));
						}

				});
				GraphicsEnvironment l_GraphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
				GraphicsDevice l_GraphicDevice = l_GraphicsEnvironment.getDefaultScreenDevice();
				m_Robot = new Robot(l_GraphicDevice);
				setUndecorated(true);
				setSize(b.m_Size.x, b.m_Size.y);
				setLocationRelativeTo(null);
				setLocation(b.m_pos.x, b.m_pos.y);
				setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				getRootPane().setOpaque(false);
				getRootPane().setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.RED));
				m_BufferedImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
		}

		public byte[] GetPixels() throws IOException
		{
				m_BufferedImage = m_Robot.createScreenCapture(new Rectangle(getLocation().x, getLocation().y, getWidth(), getHeight()));
				return ((DataBufferByte) m_BufferedImage.getRaster().getDataBuffer()).getData();
		}

		public void WindowCapture(String a_FileName) throws IOException
		{
				m_BufferedImage = m_Robot.createScreenCapture(new Rectangle(getLocation().x, getLocation().y, getWidth(), getHeight()));
				ImageIO.write(m_BufferedImage, "PNG", new File("./Images"+a_FileName));
		}
		public Box GetBounds()
		{
				return new Box(getLocation(), new Point(getSize().width, getSize().height));
		}

		public void ColorBoarder(boolean b)
		{
				if (b)
				{
						getRootPane().setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.RED));
				}	else
				{
						getRootPane().setBorder(null);
				}
		}


}
