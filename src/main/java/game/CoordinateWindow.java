package game;

import storage.Savable;
import storage.Storage;
import storage.WindowState;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;


public class CoordinateWindow extends JDialog implements Observer, Savable {

    private final RobotModel m_robotModel;
    private final TextArea m_coordinateContent;

    public CoordinateWindow(JDesktopPane desktopPane, RobotModel robotModel) {
        super((JFrame) SwingUtilities.getWindowAncestor(desktopPane), "Координаты");
        m_robotModel = robotModel;
        robotModel.addObserver(this);

        m_coordinateContent = new TextArea("");

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_coordinateContent, BorderLayout.CENTER);

        getContentPane().add(panel);
    }

    @Override
    public void saveState(Storage storage) {
        storage.setState("coordinateWindow", new WindowState(this));
    }

    @Override
    public void loadState(Storage storage) {
        CoordinateWindow dialog = this;
        WindowState coordinateState = storage.getState("coordinateWindow");
        int height, width, y, x;
        // default settings
        if (coordinateState == null) {
            x = 10;
            y = 10;
            width = 210;
            height = 600;
        } else {
            x = coordinateState.getX();
            y = coordinateState.getY();
            width = coordinateState.getWidth();
            height = coordinateState.getHeight();
        }
        dialog.setSize(width, height);
        dialog.setLocation(x, y);
    }

    @Override
    public void update(Observable o, Object arg) {
        m_coordinateContent.setText(String.format("x = %.1f\ny = %.1f", m_robotModel.getRobotPositionX(),
                m_robotModel.getRobotPositionY()));
        m_coordinateContent.invalidate();
    }
}