package Windows;

import MouseUtility.MouseWindow;
import Windows.Box;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class WindowControls extends JFrame implements MouseWindow.CallBack{
public WindowControls()
{
setTitle("Control Window");
setSize(400,400);
JPanel panel = new JPanel();
JScrollPane scrollPane = new JScrollPane();
JList listView = new JList(listModel);
JButton button1 = new JButton("Show Boxes");
JButton button2 = new JButton("Execute");
JButton button3 = new JButton("Add Window");
listView.addListSelectionListener(new ListSelectionListener() {
    @Override
    public void valueChanged(ListSelectionEvent e) {

    }
});
button3.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        //check if thread is running
        //if thread is not running start task
        if(!boxCreationThread.isAlive()) {
            //refactor to callback function
            boxCreationThread = new Thread(() ->
            {
                try {
                    //pass callback
                    MouseWindow tmp = new MouseWindow(WindowControls.this::twoArgs);
                    tmp.setVisible(true);
                    tmp.toFront();
                    while (!tmp.completed) {
                        tmp.Update();
                    }
                    tmp.dispose();
                } catch (java.awt.AWTException error) {

                }
            });
            boxCreationThread.start();
        }
    }
});
button1.addActionListener(new ActionListener()
{
@Override
public void actionPerformed(ActionEvent e)
{
if(!pressed)
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
panel.add(button3);
panel.add(scrollPane);
scrollPane.setViewportView(listView);
this.getContentPane().add(panel);
setVisible(true);
setDefaultCloseOperation(EXIT_ON_CLOSE);
}

public void twoArgs(Box b, String a_WindowName)
{
    if(!listModel.contains(a_WindowName))
    {
        listModel.add(listModel.size(), a_WindowName);
        boxes.put(a_WindowName, b);
    }
}

Thread boxCreationThread = new Thread();
DefaultListModel listModel = new DefaultListModel();
Map<String, Box> boxes = new HashMap<>();
boolean pressed = false;
}
