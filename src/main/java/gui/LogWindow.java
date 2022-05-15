package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.TextArea;
import java.beans.PropertyVetoException;
import java.util.HashMap;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import log.LogChangeListener;
import log.LogEntry;
import log.LogWindowSource;
import storage.Savable;
import storage.Storage;
import storage.WindowState;

public class LogWindow extends JInternalFrame implements LogChangeListener, Savable
{
    private final LogWindowSource m_logSource;
    private final TextArea m_logContent;

    public LogWindow(LogWindowSource logSource)
    {
        super("Протокол работы", true, true, true, true);
        m_logSource = logSource;
        m_logSource.registerListener(this);
        m_logContent = new TextArea("");
        m_logContent.setSize(200, 500);
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_logContent, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
        updateLogContent();
    }

    private void updateLogContent()
    {
        StringBuilder content = new StringBuilder();
        for (LogEntry entry : m_logSource.all())
        {
            content.append(entry.getMessage()).append("\n");
        }
        m_logContent.setText(content.toString());
        m_logContent.invalidate();
    }
    
    @Override
    public void onLogChanged()
    {
        EventQueue.invokeLater(this::updateLogContent);
    }

    @Override
    public void saveState(Storage storage) {
        storage.setState("logWindow", new WindowState(this));
    }

    /**
     * Метод применения сохраненного состояния, при предыдущем выходе.
     * @param storage - класс хранящий состояние
     */
    @Override
    public void loadState(Storage storage) {
        LogWindow frame = this;
        WindowState logState = storage.getState("logWindow");
        int height, width, y, x;
        if (logState == null){
            x = 10;
            y = 10;
            width = 210;
            height = 600;
        }
        else {
            x = logState.getX();
            y = logState.getY();
            width = logState.getWidth();
            height = logState.getHeight();
        }
        frame.setSize(width, height);
        frame.setLocation(x, y);
    }
}
