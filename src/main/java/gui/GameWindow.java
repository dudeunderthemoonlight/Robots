package gui;

import game.GameVisualizer;
import game.RobotModel;
import storage.Savable;
import storage.WindowState;

import java.awt.*;
import java.util.HashMap;

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

        addQuitListener();
    }

    /**
     * Добавляет слушателя события закрытия окна.
     */
    public void addQuitListener(){
        addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosing(InternalFrameEvent e) {
                m_robotModel.deleteObservers();
                System.out.println("game closed");
            }
        });
    }

    @Override
    public void saveState(HashMap<String, WindowState> states) {
        states.put("gameWindow", new WindowState(this));
    }

    @Override
    public void recoverState(HashMap<String, WindowState> states) {
        GameWindow frame = this;
        WindowState gameState = states.get("gameWindow");
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
