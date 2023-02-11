package views;

import controller.QuanLy;

import controller.QuanLyAdminAcc;
import model.Admin;
import model.NhanVien;
import model.NhanVienChinhThuc;
import model.NhanVienParttime;
import storage.DocGhiAdmin;
import storage.DocGhiObj;

import java.io.*;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Client {
    public static Scanner scanner = new Scanner(System.in);
    public static List<NhanVien> nhanVien = new DocGhiObj().readFile("manager.bin");
    public static QuanLy quanLy = new QuanLy(nhanVien);

    public static List<String> str = new DocGhiAdmin().readFile("admin.bin");
    public static QuanLyAdminAcc quanLyAdminAcc = new QuanLyAdminAcc(str);

    public static void main(String[] args) {

        QuanLyAdminAcc.defaultAdmin();

        NhanVien test1 = new NhanVienChinhThuc("1", "test", "0xxxxxxxx", "test",
                "email@gmail.com", 10000000, 1000000, 500000);
        NhanVien test2 = new NhanVienParttime("2", "test2", "012345xx", "test2",
                "email2@gmail.com", 15.5);
        quanLy.themNhanViens(test1);
        quanLy.themNhanViens(test2);

        int rollChoice = 0;
        int count = 0;
        do {
            System.out.print("""
                    --------------------------------
                    |  1. Admin                    |
                    |  2. Nhan Vien                |
                    |  0. Thoat                    |
                    --------------------------------
                    """);
            System.out.print("Moi nhap lua chon:");
            rollChoice = scanner.nextInt();
            switch (rollChoice) {
                case 0 -> System.exit(0);
                case 1 -> {
                    int adminChoice;
                    do {
                        System.out.print("""
                                --------------------------------
                                |   1. Dang nhap               |
                                |   2. Thoat                   |
                                --------------------------------
                                """);
                        System.out.print("Moi nhap lua chon:");
                        adminChoice = scanner.nextInt();

                        switch (adminChoice) {
                            case 1 -> {
                                System.out.print("Tai khoan:");
                                scanner.nextLine();
                                String account = scanner.nextLine();
                                System.out.print("Mat khau:");
                                String password = scanner.nextLine();
                                String admin = "[" + account + "|" + password + "]";

                                if (admin.equals(quanLyAdminAcc.getAdminAcc().toString())) {
                                    System.out.println("Dang nhap thanh cong!");
                                } else {
                                    count++;
                                    System.out.println(count);
                                    System.out.println("Dang nhap that bai, moi nhap lai!");
                                    if (count > 2) {
                                        System.out.println("Ban nhap sai qua nhieu, chuong trinh se tu dong thoat sau 5s");
                                        try {
                                            Thread.sleep(5000);
                                        } catch (InterruptedException e) {
                                            throw new RuntimeException(e);
                                        }
                                        System.exit(0);
                                    }
                                }


                            }
                            case 2 -> System.exit(adminChoice);
                        }
                    } while (adminChoice != 0);
                }
                case 2 -> System.out.println("Nhanvien");

                default -> System.out.println("Ban da nhap sai, moi nhap lai.");
            }
        } while (rollChoice != 0);
    }
}
