package views;

import controller.Management;

import controller.AdminAccountManagement;
import model.AdminAccount;
import model.Employee;
import model.FullTimeEmployee;
import model.PartTimeEmployee;
import storage.ReadAndWrite;

import java.util.List;
import java.util.Scanner;

public class Client {
    public static Scanner scanner = new Scanner(System.in);
    public static List<Employee> employees = (List<Employee>) ReadAndWrite.getInstance().readFile("management.bin");
    public static Management management = new Management(employees);

    public static List<AdminAccount> accounts = (List<AdminAccount>) ReadAndWrite.getInstance().readFile("admin.bin");
    public static AdminAccountManagement admin = new AdminAccountManagement(accounts);




    public static void main(String[] args) {
        AdminAccount adminAccount= new AdminAccount("admin","admin");
        admin.add(adminAccount);
        System.out.println(adminAccount.getUsername());
        System.out.println(adminAccount.getPassword());
        System.out.println(adminAccount);
        management.display();

        Employee test1 = new FullTimeEmployee("1", "test", "0xxxxxxxx", "test",
                "email@gmail.com", 10000000, 1000000, 500000);
        Employee test2 = new PartTimeEmployee("2", "test2", "012345xx", "test2",
                "email2@gmail.com", 15.5);
//        management.addEmployee(test1);
//        management.addEmployee(test2);

        int rollChoice = 0;
        int loginFailCount = 0;
        do {
            System.out.print("""
                    -------------------------------------
                    |  1. Admin                         |
                    |  2. Nhan Vien                     |
                    |  0. Thoat                         |
                    -------------------------------------
                    """);
            System.out.print("Moi nhap lua chon:");
            rollChoice = scanner.nextInt();
            switch (rollChoice) {
                case 0 -> System.exit(0);
                case 1 -> {
                    int adminChoice;
                    do {
                        System.out.print("""
                                -------------------------------------
                                |   1. Dang nhap                    |
                                |   2. Thoat                        |
                                -------------------------------------
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
                                if (true) {
                                    System.out.println("Dang nhap thanh cong!");
                                    int success = 0;
                                    do {
                                        System.out.print("""
                                                -------------------------------------
                                                |   1. Hien thi danh sach nhan vien |
                                                |   2. Them nhan vien               |
                                                |   3. Sua nhan vien theo id        |
                                                |   4. Xoa nhan vien theo id        |
                                                |   5. Tim nhan vien theo id        |
                                                |   99. Clear toan bo nhan vien     |
                                                |   0. Thoat                        |
                                                -------------------------------------
                                                                """);
                                        System.out.print("Moi nhap lua chon: ");
                                        success = scanner.nextInt();
                                        switch (success) {
                                            case 0 -> System.exit(0);
                                            case 1 -> management.display();
                                            case 2 -> {
                                                System.out.print("Nhap ma nhan vien: ");
                                                String maNv = scanner.nextLine();
                                            }
                                            case 3 -> {
                                                System.out.println("Sua nhan vien theo id");
                                            }
                                            case 4 -> {
                                                System.out.println("Xoa nhan vien theo id");
                                            }
                                            case 5 -> {
                                                System.out.println("Tim nhan vien theo id");
                                            }
                                            case 99 -> management.xoaTatCaNhanVien();
                                        }
                                    } while (success != 0);

                                } else {
                                    loginFailCount++;
                                    System.out.println(loginFailCount);
                                    System.out.println("Dang nhap that bai, moi nhap lai!");
                                    if (loginFailCount > 2) {
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
