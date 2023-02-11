package storage;

import model.Admin;
import model.NhanVien;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DocGhiAdmin implements DocGhiFile<List<StringBuilder>> {


    @Override
    public void writeFile(List<StringBuilder> admin, String path) {
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
            oos.writeObject(admin);
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
    public List<StringBuilder> readFile(String path) {
        File file = new File(path);
        InputStream is = null;
        try {
            is = new FileInputStream(file);
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

                List<StringBuilder> admin;
                try {
                    admin = (List<StringBuilder>) ois.readObject();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                return admin;
            } else {
                return new ArrayList<>();
            }
        }
    }


}
