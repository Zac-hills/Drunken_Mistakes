package Windows;

import java.awt.Point;
import java.util.concurrent.ThreadLocalRandom;

public class Box
{
public Point m_pos;
public Point m_Size;
//creates a rectangle
public Box(int a_x,int a_y,int a_height,int a_width)
{
m_pos = new Point();
m_Size = new Point();
m_pos.x = a_x;
m_pos.y = a_y;
m_Size.y = a_height;
m_Size.x = a_width;
}
//creates square at location given size
public Box(Point a_Location, Point a_Size)
{
    m_pos = a_Location;
    m_Size = a_Size;
}
//gets random point inside of box
public Point getRandomPoint()
{
Point l_point = new Point();

l_point.x = ThreadLocalRandom.current().nextInt(m_pos.x, m_pos.x + m_Size.x + 1);
l_point.y = ThreadLocalRandom.current().nextInt(m_pos.y, m_pos.y + m_Size.y + 1);

return l_point;
}

}