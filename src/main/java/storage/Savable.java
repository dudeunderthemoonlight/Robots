package storage;


import java.util.HashMap;
import java.util.Map;

/**
 * Интерфейс сохранения и загрузки состояния окон.
 */
public interface Savable {

    /**
     * Метод сохранения состояния.
     * @param states - словарь состояний состояние
     */
    void saveState(HashMap<String, WindowState> states);

    /**
     * Метод восстановления сохраненного состояния, при предыдущем выходе.
     * @param states - словарь состояний состояние
     */
    void recoverState(HashMap<String, WindowState> states);
}
