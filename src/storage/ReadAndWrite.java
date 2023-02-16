package storage;

import java.io.*;
import java.util.ArrayList;

public class ReadAndWrite<T> implements IReadAndWrite {
    private ReadAndWrite() {
    }

    private static ReadAndWrite instance;

    public static ReadAndWrite getInstance() {
        if (instance == null) {
            instance = new ReadAndWrite();
        }
        return instance;
    }

    @Override
    public void writeFile(Object file, String path) {
        File f = new File(path);
        OutputStream os = null;
        try {
            os = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(os);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            oos.writeObject(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            oos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            os.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public T readFile(String path) {
        InputStream is = null;
        try {
            is = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(is);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            if (ois != null) {
                T writeObj = null;
                try {
                    writeObj = (T) ois.readObject();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                return writeObj;
            } else {
                return (T) new ArrayList<>();
            }
        }
    }
}
