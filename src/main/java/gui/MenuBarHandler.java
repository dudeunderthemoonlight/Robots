package gui;

import log.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

/**
 * Класс создания и обработки главного меню-бара приложения.
 */
public class MenuBarHandler {

    /**
     * Поле главного окна, которому принадлежит меню-бар.
     */
    public MainApplicationFrame mainFrame;

    /**
     * Поле меню-бара.
     */
    public final static JMenuBar menuBar = new JMenuBar();

    public MenuBarHandler(MainApplicationFrame frame) {
        mainFrame = frame;
        createMenuBar();
    }

    /**
     * Добавление элементов меню к бару.
     *
     * @param menu - элемент
     */
    protected void addMenu(JMenu menu) {
        menuBar.add(menu);
    }

    /**
     * Метод объединения элементов меню в меню-бар.
     */
    private void createMenuBar() {
        addMenu(createOptionMenu());
        addMenu(createLookAndFeelMenu());
        addMenu(createTestMenu());
    }

    /**
     * Метод создания элемента меню, меняющего внешний вид приложения (режим отображения).
     *
     * @return lookAndFeelMenu - элемент меню
     */
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

    /**
     * Метод создания элемента меню - взаимодействие с тестами.
     *
     * @return testMenu - элемент меню
     */
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

    /**
     * Метод создания элемента меню - опции.
     *
     * @return optionMenu - элемент меню
     */
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
