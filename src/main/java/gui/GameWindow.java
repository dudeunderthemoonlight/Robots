package gui;

import game.CoordinateWindow;
import game.GameVisualizer;
import game.RobotModel;
import storage.Savable;
import storage.Storage;
import storage.WindowState;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

public class GameWindow extends JInternalFrame implements Savable {

    private final RobotModel m_robotModel;

    public GameWindow(RobotModel robotModel) {
        super("Игровое поле", true, true, true, true);

        JPanel panel = new JPanel(new BorderLayout());

        m_robotModel = robotModel;
        GameVisualizer m_visualizer = new GameVisualizer(robotModel);

        panel.add(m_visualizer, BorderLayout.CENTER);

        getContentPane().add(panel);
        pack();

        quitListener();
    }

    /**
     * Прослушивание закрытия игрового окна.
     */
    public void quitListener(){
        addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosing(InternalFrameEvent e) {
                m_robotModel.deleteObservers();
                System.out.println("game closed");
            }
        });
    }

    @Override
    public void saveState(Storage storage) {
        storage.setState("gameWindow", new WindowState(this));
    }

    @Override
    public void loadState(Storage storage) {
        GameWindow frame = this;
        WindowState gameState = storage.getState("gameWindow");
        int height, width, y, x;
        //default settings
        if (gameState == null) {
            x = 230;
            y = 10;
            width = 1000;
            height = 600;
        } else {
            x = gameState.getX();
            y = gameState.getY();
            width = gameState.getWidth();
            height = gameState.getHeight();
        }
        frame.setSize(width, height);
        frame.setLocation(x, y);
    }
}
