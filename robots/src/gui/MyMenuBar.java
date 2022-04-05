package gui;

import log.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

public class MyMenuBar extends JMenuBar {
    public MainApplicationFrame mainFrame;
    public final static JMenuBar menuBar = new JMenuBar();

    public MyMenuBar(MainApplicationFrame frame) {
        mainFrame = frame;
        createMenuBar();
    }

    protected void addMenu(JMenu menu) {
        menuBar.add(menu);
    }

    private void createMenuBar() {
        addMenu(createOptionMenu());
        addMenu(createLookAndFeelMenu());
        addMenu(createTestMenu());
    }

    private JMenu createLookAndFeelMenu() {
        JMenu lookAndFeelMenu = new JMenu("Режим отображения");
        lookAndFeelMenu.setMnemonic(KeyEvent.VK_V);
        lookAndFeelMenu.getAccessibleContext().setAccessibleDescription(
                "Управление режимом отображения приложения");

        {
            JMenuItem systemLookAndFeel = new JMenuItem("Системная схема", KeyEvent.VK_S);
            systemLookAndFeel.addActionListener((event) -> {
                mainFrame.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                mainFrame.invalidate();
            });
            lookAndFeelMenu.add(systemLookAndFeel);
        }

        {
            JMenuItem crossplatformLookAndFeel = new JMenuItem("Универсальная схема", KeyEvent.VK_S);
            crossplatformLookAndFeel.addActionListener((event) -> {
                mainFrame.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
                mainFrame.invalidate();
            });
            lookAndFeelMenu.add(crossplatformLookAndFeel);
        }

        return lookAndFeelMenu;
    }

    private JMenu createTestMenu() {
        JMenu testMenu = new JMenu("Тесты");
        testMenu.setMnemonic(KeyEvent.VK_T);
        testMenu.getAccessibleContext().

                setAccessibleDescription(
                        "Тестовые команды");

        {
            JMenuItem addLogMessageItem = new JMenuItem("Сообщение в лог", KeyEvent.VK_S);
            addLogMessageItem.addActionListener((event) -> {
                Logger.debug("Новая строка");
            });
            testMenu.add(addLogMessageItem);
        }

        return testMenu;
    }

    private JMenu createOptionMenu() {
        JMenu optionMenu = new JMenu("Опции");
        optionMenu.setMnemonic(KeyEvent.VK_O);
        optionMenu.getAccessibleContext().

                setAccessibleDescription(
                        "Управление приложением");

        {
            JMenuItem exitItem = new JMenuItem("Выход", KeyEvent.VK_X | KeyEvent.VK_ALT);
            exitItem.addActionListener((event) -> {
                Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(
                        new WindowEvent(mainFrame, WindowEvent.WINDOW_CLOSING));
            });
            optionMenu.add(exitItem);
        }

        return optionMenu;
    }
}
