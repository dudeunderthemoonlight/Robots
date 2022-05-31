package storage;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс записи и чтения состояний окон в/из файла.
 */
public class StateHandler {
    private final String PATH = System.getProperty("user.home") + File.separator + "robotsState.txt";
    public final Map<String, WindowState> tempStorage = new HashMap<>();

    /**
     * Запись словаря в файл.
     */
    public void saveToFile(Map<String, WindowState> states) {
        try {
            FileOutputStream fileOut = new FileOutputStream(PATH);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(states);
            objectOut.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Чтение файла.
     */
    public Object readFile() {
        if (new File(PATH).exists()) {
            try {
                FileInputStream inputFile = new FileInputStream(PATH);
                ObjectInputStream inputObject = new ObjectInputStream(inputFile);
                var result = inputObject.readObject();
                inputObject.close();
                return result;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Десериализация.
     *
     * @return словарь состояний
     */
    @SuppressWarnings("unchecked")
    public Map<String, WindowState> readStates() {
        var dataObject = readFile();
        if (dataObject == null) {
            return new HashMap<>();
        } else {
            return (Map<String, WindowState>) dataObject;
        }
    }

    /**
     * Сохранение состояний всех окон в tempStorage
     */
    public void saveStates(List<Savable> windows){
        for (Savable window : windows) {
            window.saveState(tempStorage);
        }
        saveToFile(tempStorage);
    }
}