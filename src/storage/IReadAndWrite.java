package storage;

public interface IReadAndWrite<T> {
    void writeFile(T file, String path);
    T readFile(String path);
}
