package Windows;

import Windows.Box;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class WindowControls extends JFrame {
public WindowControls()
{
setTitle("Control Window");
setSize(400,400);
JPanel panel = new JPanel();
JButton button1 = new JButton("Show Boxes");
JButton button2 = new JButton("Execute");
button1.addActionListener(new ActionListener()
{
@Override
public void actionPerformed(ActionEvent e)
{
if(!Pressed)
{
button1.setText("Hide Boxes");
}
else
{
button1.setText("Show Boxes");
}
}
});
panel.add(button1);
panel.add(button2);
this.getContentPane().add(panel);
setVisible(true);
setDefaultCloseOperation(EXIT_ON_CLOSE);
}

public void addBox(Box b)
{
Boxes.add(b);
}

ArrayList <Box> Boxes;
boolean Pressed = false;
}
