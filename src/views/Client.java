package views;

import controller.QuanLy;
import model.NhanVien;
import model.NhanVienChinhThuc;
import model.NhanVienParttime;
import storage.DocGhiFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Client {
    public static final String REGEX = "^[a-z]\\.[0-9]{8}$"; //regex account dinh dang ten
    public static Scanner scanner = new Scanner(System.in);
    public static List<NhanVien> nhanVien = DocGhiFile.docFile("manager.bin");
    public static QuanLy quanLy = new QuanLy(nhanVien);

    public static void main(String[] args) {
        File file = new File("log.txt");
        OutputStream os = null;
        try {
            os = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            ObjectOutputStream oos = new ObjectOutputStream(os);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        NhanVien test1 = new NhanVienChinhThuc("1", "test", "0xxxxxxxx", "test",
                "email@gmail.com", 10000000, 1000000, 500000);
        NhanVien test2 = new NhanVienParttime("2", "test2", "012345xx", "test2",
                "email2@gmail.com", 15.5);
        quanLy.themNhanViens(test1);
        quanLy.themNhanViens(test2);

        int choice = 0;
        do {
            System.out.println("--------------------------------");
            System.out.print("""
                    1. Dang Nhap Admin
                    2. Dang Nhap Nhan Vien
                    0. Thoat
                    --------------------------------
                    """);
            System.out.println("Moi nhap lua chon.");
            choice = scanner.nextInt();
            switch (choice) {
                case 0:
                    try {
                        os.write("exit".getBytes());
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                    System.exit(0);
                    break;
                case 1:
            }
        } while (choice != 0);
    }
}
