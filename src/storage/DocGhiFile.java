package storage;

import model.NhanVien;

import java.io.*;
import java.util.*;
public class DocGhiFile {
    public static void ghiFile(List<NhanVien> nhanViens, String path) {

        File file = new File(path);
        OutputStream os = null;
        try {
            os = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(os);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            oos.writeObject(nhanViens);
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


    public static List<NhanVien> docFile(String path) {
        File docFile = new File(path);

        InputStream in = null;
        try {
            in = new FileInputStream(docFile);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(in);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            if (ois != null) {
                List<NhanVien> nhanViens;
                try {
                    nhanViens = (List<NhanVien>) ois.readObject();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                return nhanViens;
            } else {
                return new ArrayList<>();
            }
        }
    }
}
