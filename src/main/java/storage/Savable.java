package storage;


import java.beans.PropertyVetoException;
import java.util.Map;

/**
 * Интерфейс сохранения и загрузки состояния окон.
 */
public interface Savable {

    /**
     * Метод сохранения состояния.
     * @param states - словарь состояний состояние
     */
    void saveState(Map<String, WindowState> states);

    /**
     * Метод восстановления сохраненного состояния, при предыдущем выходе.
     * @param states - словарь состояний состояние
     */
    void recoverState(Map<String, WindowState> states) throws PropertyVetoException;
}
