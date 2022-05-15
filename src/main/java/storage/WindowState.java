package storage;

import javax.swing.*;
import java.io.Serializable;

/**
 * Класс, хранящий состояние окна.
 */
public class WindowState implements Serializable {
    private final int x;
    private final int y;
    private final int height;
    private final int width;

    public WindowState(JInternalFrame frame) {
        this.x = frame.getX();
        this.y = frame.getY();
        this.width = frame.getWidth();
        this.height = frame.getHeight();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}