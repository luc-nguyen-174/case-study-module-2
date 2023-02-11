package storage;

public interface DocGhiFile<G> {
    void writeFile(G file,String path);
    G readFile(String path);
}
