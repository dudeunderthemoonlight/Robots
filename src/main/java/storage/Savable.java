package storage;


/**
 * Интерфейс сохранения и загрузки состояния окон.
 */
public interface Savable {

    void saveState(Storage storage);

    void loadState(Storage storage);
}
