package Windows;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

//This class is an alpha window class. Think of it as a viewport on screen. It constantly can take screenshots of the window and us it for
//machine learning
//for example if you want to click a button and that button changes color and you only want to click the button when
//the button is red you can have an alpha window on top and keep checking the pixels to see if the button is red
public class RectAlphaWindow extends JFrame
{
    BufferedImage m_BufferedImage;
    Robot m_Robot;
    public String WindowName = new String();

    public void SetWindowName(String a_WindowName)
    {
        WindowName = a_WindowName;
    }

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
        setLocation(b.m_pos.x - (b.m_Size.x / 2), b.m_pos.y - (b.m_Size.y / 2));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(new Color(0, 0, 0, 0));

        getRootPane().setOpaque(false);
        getRootPane().setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.RED));
        m_BufferedImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
    }

    //returns the windows pixels this is where one can use machine learning or opencv
    public byte[] GetPixels() throws IOException
    {
        m_BufferedImage = m_Robot.createScreenCapture(new Rectangle(getLocation().x, getLocation().y, getWidth(), getHeight()));
        return ((DataBufferByte) m_BufferedImage.getRaster().getDataBuffer()).getData();
    }

    //saves screen capture to file, extremely useful for building a dataset for machine learning model
    public void WindowCapture(String a_FileName) throws IOException
    {
        m_BufferedImage = m_Robot.createScreenCapture(new Rectangle(getLocation().x, getLocation().y, getWidth(), getHeight()));
        ImageIO.write(m_BufferedImage, "PNG", new File("./Images" + a_FileName));
    }

    public Box GetBounds()
    {
        return new Box(getLocation(), new Point(getSize().width, getSize().height));
    }

    //The windows are transparent so one can enable a boarder to check to see if the window is in the correct spot
    public void ColorBoarder(boolean b)
    {
        if (b)
        {
            getRootPane().setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.RED));
        } else
        {
            getRootPane().setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.black));
        }
    }


}
