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
    private final boolean isIcon;

    public WindowState(JInternalFrame frame) {
        this.x = frame.getX();
        this.y = frame.getY();
        this.width = frame.getWidth();
        this.height = frame.getHeight();
        this.isIcon = frame.isIcon();
    }

    public WindowState(JDialog dialog) {
        this.x = dialog.getX();
        this.y = dialog.getY();
        this.width = dialog.getWidth();
        this.height = dialog.getHeight();
        this.isIcon = dialog.isActive();
    }

    public WindowState(int x, int y, int height, int width, boolean isIcon) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        this.isIcon = isIcon;
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

    public boolean getIsIcon() {
        return isIcon;
    }
}