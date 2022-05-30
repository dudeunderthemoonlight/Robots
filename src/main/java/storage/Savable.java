package storage;


/**
 * Интерфейс сохранения и загрузки состояния окон.
 */
public interface Savable {

    /**
     * Метод сохранения состояния.
     * @param storage - класс хранящий состояние
     */
    void saveState(Storage storage);

    /**
     * Метод применения сохраненного состояния, при предыдущем выходе.
     * @param storage - класс хранящий состояние
     */
    void loadState(Storage storage);
}
