package storage;

public interface IWriteLog<T> {
    void WriteLogFile(T log);
}
