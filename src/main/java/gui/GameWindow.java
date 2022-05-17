package gui;

import game.GameVisualizer;
import game.RobotModel;
import storage.Savable;
import storage.Storage;
import storage.WindowState;

import java.awt.BorderLayout;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

public class GameWindow extends JInternalFrame implements Savable
{

    public GameWindow(RobotModel robotModel)
    {
        super("Игровое поле", true, true, true, true);

        JPanel panel = new JPanel(new BorderLayout());
        GameVisualizer m_visualizer = new GameVisualizer(robotModel);
        panel.add(m_visualizer, BorderLayout.CENTER);

        getContentPane().add(panel);
        pack();
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
        if (gameState == null){
            x = 230;
            y = 10;
            width = 1000;
            height = 600;
        }
        else {
            x = gameState.getX();
            y = gameState.getY();
            width = gameState.getWidth();
            height = gameState.getHeight();
        }
        frame.setSize(width, height);
        frame.setLocation(x, y);
    }
}
