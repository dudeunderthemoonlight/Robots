package gui;

import java.awt.*;
import java.awt.event.*;
import java.io.File;

import javax.swing.*;

import log.Logger;
import storage.StateHandler;
import storage.Storage;

import static storage.Storage.getStateStorage;

/**
 * Класс создания и обработки главного окна приложения.
 */
public class MainApplicationFrame extends JFrame {

    private final Storage storage = new Storage();
    private final GameWindow gameWindow = new GameWindow();
    private final LogWindow logWindow = new LogWindow(Logger.getDefaultLogSource());
    private static final StateHandler stateHandler = new StateHandler();

    /**
     * Поле главного окна приложения.
     */
    private static final MainApplicationFrame frame = new MainApplicationFrame();

    /**
     * Поле рабочей области frame.
     */
    private static final JDesktopPane desktopPane = new JDesktopPane();


    /**
     * Метод создания, отрисовки и обработки нажатий на главное окно.
     */
    public static void runFrame() {
        frame.pack();
        frame.setVisible(true);
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        frame.setScreenMargin(40);
        frame.setContentPane(desktopPane);

        Storage.setStateStorage(stateHandler.readStates());

        frame.generateMenuBar();
        frame.generateLogWindow();
        frame.generateGameWindow();

        frame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        frame.quitListener();
    }

    /**
     * Метод добавляет прослушивание на выход их приложения.
     */
    private void quitListener() {
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent event) {
                Object[] options = {"Да", "Нет!"};
                int answer = JOptionPane
                        .showOptionDialog(event.getWindow(), "Выйти из игры?", "Подтверждение",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                if (answer == 0) {
                    gameWindow.saveState(storage);
                    logWindow.saveState(storage);
                    stateHandler.saveToFile(getStateStorage());
                    event.getWindow().setVisible(false);
                    System.exit(0);
                }
            }
        });
    }

    /**
     * Создает внешний отступ для frame.
     *
     * @param inset - число пикселей.
     */
    private void setScreenMargin(int inset) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset,
                screenSize.width - inset * 2,
                screenSize.height - inset * 2);
    }

    /**
     * Добавляет внутренние окна на рабочую область окна frame.
     *
     * @param frame - главное окно.
     */
    protected void addWindow(JInternalFrame frame) {
        desktopPane.add(frame);
        frame.setVisible(true);
    }

    /**
     * Генерирует меню-бар.
     */
    protected void generateMenuBar() {
        new MenuBarHandler(frame);
        JMenuBar menuBar = MenuBarHandler.menuBar;
        setJMenuBar(menuBar);
    }

    /**
     * Генерирует игровое окно.
     */
    protected void generateGameWindow() {
        gameWindow.loadState(storage);
        addWindow(gameWindow);
    }

    /**
     * Генерирует окно логирования.
     */
    protected void generateLogWindow() {
        logWindow.loadState(storage);
        Logger.debug("Протокол работает");
        addWindow(logWindow);
    }

    /**
     * Меняет внешний вид (режим отображения) frame.
     *
     * @param className - название режима отображения.
     */
    public void setLookAndFeel(String className) {
        try {
            UIManager.setLookAndFeel(className);
            SwingUtilities.updateComponentTreeUI(this);
        } catch (ClassNotFoundException | InstantiationException
                | IllegalAccessException | UnsupportedLookAndFeelException e) {
            // just ignore
        }
    }
}
