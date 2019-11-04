package GUI;

import MouseUtility.MouseWindow;
import Windows.Box;
import Windows.RectAlphaWindow;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ControlWindow implements MouseWindow.CallBack
{
    static
    {
        try
        {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        } catch (InstantiationException e)
        {
            e.printStackTrace();
        } catch (IllegalAccessException e)
        {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e)
        {
            e.printStackTrace();
        }
    }
    public OutConsole console;
    private JFrame mainWindow = new JFrame();
    private JPanel mainPanel;
    private JScrollPane windowListScrollPane;
    private DefaultListModel listModel = new DefaultListModel();
    private JList windowList;
    private JCheckBox showWindows;
    private JButton saveButton;
    private JButton loadButton;
    private JTabbedPane windowSpawnPane;
    private JButton addWindowsButton;
    private JPanel windowDesign;
    private JTextArea consoleText;
    private JButton loadMainEntry;
    private JPanel scriptPanel;
    private Thread boxCreationThread = new Thread();
    private Map<String, RectAlphaWindow> boxes = new HashMap<>();

    public ControlWindow()
    {
        console = new OutConsole(consoleText);
        PrintStream printStream = new PrintStream(console);
        System.setOut(printStream);
        System.setErr(printStream);
        windowList.setModel(listModel);
        mainWindow.add($$$getRootComponent$$$());
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.pack();
        initColor();
        mainWindow.setVisible(true);
        loadMainEntry.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {

            }
        });
        saveButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.showSaveDialog(null);
                try
                {
                    FileWriter fileWriter = new FileWriter(fileChooser.getSelectedFile() + ".json");
                    for (Object key : boxes.keySet())
                    {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("windowName", boxes.get(key).WindowName);
                        JSONArray position = new JSONArray();
                        position.add(boxes.get(key).GetBounds().m_pos.x);
                        position.add(boxes.get(key).GetBounds().m_pos.y);
                        jsonObject.put("position", position);
                        JSONArray size = new JSONArray();
                        size.add(boxes.get(key).GetBounds().m_Size.x);
                        size.add(boxes.get(key).GetBounds().m_Size.y);
                        jsonObject.put("size", size);
                        fileWriter.write(jsonObject.toJSONString());
                        fileWriter.write("\n");
                    }
                    fileWriter.close();
                } catch (IOException error)
                {
                    System.out.println(error.getMessage());
                }
            }
        });

        loadButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                JFileChooser fileChooser = new JFileChooser();
                JSONParser parser = new JSONParser();
                fileChooser.showOpenDialog(null);
                if (fileChooser.getSelectedFile() != null)
                    try
                    {
                        BufferedReader fileReader = new BufferedReader(new FileReader(fileChooser.getSelectedFile()));
                        String line = fileReader.readLine();
                        line.replaceAll("\n", "");
                        if (!showWindows.isSelected())
                        {
                            showWindows.setSelected(true);
                        }
                        listModel.clear();
                        for (Object key : boxes.keySet())
                        {
                            boxes.get(key).dispose();
                        }
                        boxes.clear();
                        while (line != null)
                        {
                            JSONObject json = (JSONObject) parser.parse(line);
                            JSONArray position = (JSONArray) json.get("position");
                            JSONArray size = (JSONArray) json.get("size");

                            addWindow(new Box(new Point(Math.toIntExact((long) position.get(0)), Math.toIntExact((long) position.get(1))), new Point(Math.toIntExact((long) size.get(0)), Math.toIntExact((long) size.get(1)))), (String) json.get("windowName"));
                            line = fileReader.readLine();
                        }
                    } catch (IOException | ParseException error)
                    {
                        System.out.println(error.toString());
                    }
            }
        });
        addWindowsButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //check if thread is running
                //if thread is not running start task
                if (!boxCreationThread.isAlive())
                {
                    mainWindow.getContentPane().setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
                    //refactor to callback function
                    boxCreationThread = new Thread(() ->
                    {
                        try
                        {
                            //pass callback
                            MouseWindow tmp = new MouseWindow(ControlWindow.this::addWindow);
                            tmp.setVisible(true);
                            tmp.toFront();
                            while (!tmp.completed)
                            {
                                tmp.Update();
                            }
                            tmp.dispose();
                        } catch (AWTException error)
                        {

                        }
                    });
                    boxCreationThread.start();
                }
            }
        });
        showWindows.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (showWindows.isSelected())
                {
                    for (Object key : boxes.keySet())
                    {
                        boxes.get(key).ColorBoarder(true);
                    }
                } else
                {
                    for (Object key : boxes.keySet())
                    {
                        boxes.get(key).ColorBoarder(false);
                    }
                }
            }
        });
    }

    public void addWindow(Box b, String a_WindowName)
    {
        if (!listModel.contains(a_WindowName))
        {
            try
            {
                listModel.add(listModel.size(), a_WindowName);
                RectAlphaWindow tmp = new RectAlphaWindow(b);
                tmp.ColorBoarder(true);
                tmp.setVisible(true);
                tmp.SetWindowName(a_WindowName);
                boxes.put(a_WindowName, tmp);
            } catch (AWTException error)
            {
                System.out.println(error.getMessage());
            }

        }
    }

    public void initColor()
    {

        Color blackUI = new Color(60, 63, 65);
        Color whiteForeground = new Color(187, 187, 187);
        for (Component c : mainWindow.getContentPane().getComponents())
        {
            c.setBackground(blackUI);
            c.setForeground(whiteForeground);
        }
        windowSpawnPane.setBackground(blackUI);
        windowSpawnPane.setForeground(whiteForeground);
        windowList.setBackground(blackUI);
        windowList.setForeground(whiteForeground);
        for (Component c : windowSpawnPane.getComponents())
        {
            c.setBackground(blackUI);
            c.setForeground(whiteForeground);
        }
        windowDesign.setBackground(blackUI);
        windowDesign.setForeground(whiteForeground);
        for (Component c : windowDesign.getComponents())
        {
            c.setBackground(blackUI);
            c.setForeground(whiteForeground);
        }
        scriptPanel.setBackground(blackUI);
        scriptPanel.setForeground(whiteForeground);
        for (Component c : scriptPanel.getComponents())
        {
            c.setBackground(blackUI);
            c.setForeground(whiteForeground);
        }
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$()
    {
        mainPanel = new JPanel();
        mainPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 3, new Insets(0, 0, 0, 0), -1, -1));
        windowSpawnPane = new JTabbedPane();
        windowSpawnPane.setName("Window Designer");
        windowSpawnPane.setToolTipText("Creating view ports for scripts.");
        mainPanel.add(windowSpawnPane, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
        windowDesign = new JPanel();
        windowDesign.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(3, 2, new Insets(0, 0, 0, 0), -1, -1));
        windowSpawnPane.addTab("Window", windowDesign);
        windowListScrollPane = new JScrollPane();
        windowDesign.add(windowListScrollPane, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        windowList = new JList();
        windowListScrollPane.setViewportView(windowList);
        loadButton = new JButton();
        loadButton.setText("Load");
        windowDesign.add(loadButton, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        showWindows = new JCheckBox();
        showWindows.setSelected(true);
        showWindows.setText("Show Windows");
        windowDesign.add(showWindows, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        saveButton = new JButton();
        saveButton.setText("Save");
        windowDesign.add(saveButton, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addWindowsButton = new JButton();
        addWindowsButton.setForeground(new Color(-13027015));
        addWindowsButton.setText("Draw Viewport");
        windowDesign.add(addWindowsButton, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        scriptPanel = new JPanel();
        scriptPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        windowSpawnPane.addTab("Script", scriptPanel);
        consoleText = new JTextArea();
        consoleText.setText("");
        scriptPanel.add(consoleText, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
        scriptPanel.add(spacer1, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        loadMainEntry = new JButton();
        loadMainEntry.setText("Load Main Entry");
        scriptPanel.add(loadMainEntry, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        windowSpawnPane.addTab("ML", panel1);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$()
    {
        return mainPanel;
    }

}
