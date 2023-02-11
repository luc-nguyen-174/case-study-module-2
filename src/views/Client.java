package views;

import controller.QuanLy;

import model.Admin;
import model.NhanVien;
import model.NhanVienChinhThuc;
import model.NhanVienParttime;
import storage.DocGhiObj;

import java.io.*;
import java.util.List;
import java.util.Scanner;

public class Client {
    public static Scanner scanner = new Scanner(System.in);
    public static List<NhanVien> nhanVien = new DocGhiObj().readFile("manager.bin");
    public static QuanLy quanLy = new QuanLy(nhanVien);

    public static void main(String[] args) {
        Admin.createAdmin();
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
                    Moi nhap lua chon:
                    1. Admin
                    2. Nhan Vien
                    0. Thoat
                    --------------------------------
                    """);
            choice = scanner.nextInt();
            switch (choice) {
                case 0 -> System.exit(0);
                case 1 -> {
                    int choiceAdmin;
                    do {
                        System.out.println("1. Dang nhap.");
                        System.out.println("2. Thoat.");
                        System.out.print("Moi nhap lua chon:");
                        choiceAdmin = scanner.nextInt();
                        switch (choiceAdmin) {
                            case 1 -> {
                                System.out.println("Trang dang nhap admin, moi nhap tai khoan va mat khau:");
                                System.out.print("Tai khoan:");
                                String account = scanner.nextLine();
                                System.out.print("Mat khau:");
                                String password = scanner.nextLine();
                                StringBuilder adminAccInp = new StringBuilder();
                                adminAccInp.append(account).append("|").append(password);
                                System.out.println(adminAccInp.toString());
                            }
                            case 2 -> System.exit(choiceAdmin);
                        }
                    } while (choiceAdmin != 0);
                }

                default -> System.out.println("Ban da nhap sai, moi nhap lai.");
            }
        } while (choice != 0);
    }
}
