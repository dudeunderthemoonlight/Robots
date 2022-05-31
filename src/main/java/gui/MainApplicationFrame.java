package gui;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

import game.RobotModel;
import log.Logger;
import storage.Savable;
import storage.StateHandler;
import storage.WindowState;
import java.util.List;
import java.util.Map;


/**
 * Класс создания и обработки главного окна приложения.
 */
public class MainApplicationFrame extends JFrame {

    /**
     * Поле главного окна приложения.
     */
    private static final MainApplicationFrame frame = new MainApplicationFrame();

    /**
     * Поле рабочей области frame.
     */
    private static final JDesktopPane desktopPane = new JDesktopPane();

    private final StateHandler stateHandler = new StateHandler();
    private final RobotModel robotModel = new RobotModel();

    private final List<Savable> windows = new ArrayList<>();


    /**
     * Метод создания, отрисовки и обработки нажатий на главное окно.
     */
    public static void runFrame() {
        frame.pack();
        frame.setVisible(true);
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        frame.setScreenMargin(40);
        frame.setContentPane(desktopPane);

        frame.stateRecovery();
        frame.generateMenuBar();

        frame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        frame.addQuitListener();
    }

    /**
     * Метод добавляет прослушивание на выход из приложения.
     */
    private void addQuitListener() {
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent event) {
                Object[] options = {"Да", "Нет!"};
                int answer = JOptionPane
                        .showOptionDialog(event.getWindow(), "Выйти из игры?", "Подтверждение",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                if (answer == 0) {
                    stateHandler.saveStates(windows);
                    event.getWindow().setVisible(false);
                    System.exit(0);
                }
            }
        });
    }

    /**
     * Генерауия окон + восстановление состояния состояний окон.
     */
    private void stateRecovery() {
        Map<String, WindowState> states = stateHandler.readStates();
        generateLogWindow(states);
        generateGameWindow(states);
        generateCoordinateWindow(states);
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
     * Генерирует игровое окно по словарю состояний.
     */
    protected void generateGameWindow(Map<String, WindowState> states) {
        GameWindow gameWindow = new GameWindow(robotModel);

        gameWindow.recoverState(states);
        addWindow(gameWindow);
        windows.add(gameWindow);
    }

    /**
     * Генерирует окно логирования по словарю состояний.
     */
    protected void generateLogWindow(Map<String, WindowState> states) {
        LogWindow logWindow = new LogWindow(Logger.getDefaultLogSource());

        logWindow.recoverState(states);
        Logger.debug("Протокол работает");
        addWindow(logWindow);
        windows.add(logWindow);
    }

    /**
     * Генерирует окно координат робота по словарю состояний.
     */
    protected void generateCoordinateWindow(Map<String, WindowState> states) {
        CoordinateWindow coordinateWindow = new CoordinateWindow(desktopPane, robotModel);

        coordinateWindow.recoverState(states);
        coordinateWindow.setVisible(true);
        windows.add(coordinateWindow);
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
