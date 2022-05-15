package gui;

import storage.Savable;
import storage.Storage;
import storage.WindowState;

import java.awt.BorderLayout;
import java.beans.PropertyVetoException;
import java.util.HashMap;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

public class GameWindow extends JInternalFrame implements Savable
{
    public GameWindow()
    {
        super("Игровое поле", true, true, true, true);
        GameVisualizer m_visualizer = new GameVisualizer();
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
    }

    @Override
    public void saveState(Storage storage) {
        storage.setState("gameWindow", new WindowState(this));
    }

    /**
     * Метод применения сохраненного состояния, при предыдущем выходе.
     * @param storage - класс хранящий состояние
     */
    @Override
    public void loadState(Storage storage) {
        GameWindow frame = this;
        WindowState gameState = storage.getState("gameWindow");
        int height, width, y, x;
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
