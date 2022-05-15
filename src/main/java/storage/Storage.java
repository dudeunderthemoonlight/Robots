package storage;

import java.util.*;

/**
 * Класс хранящий состояния окон приложения.
 */
public class Storage {
    public static HashMap<String, WindowState> statesStorage = new HashMap<>();

    /**
     * Добавляет сформированное состояние окна в словарь состояний.
     * @param window название окна
     * @param state его состояние
     */
    public void setState(String window, WindowState state) {
        statesStorage.put(window, state);
    }

    /**
     * Достает состояние окна из словаря по ключу.
     * @param key имя окна
     * @return состояние окна с именем key
     */
    public WindowState getState(String key){
        return statesStorage.get(key);
    }

    public static void setStateStorage(HashMap<String, WindowState> stateStorage) {
        Storage.statesStorage = stateStorage;
    }

    public static HashMap<String, WindowState> getStateStorage() {
        return statesStorage;
    }
}
