package storage;

public interface IReadAndWrite<T> {
    boolean writeFile(T file, String path);
    T readFile(String path);
}
