package views;

import controller.Management;

import controller.AdminAccountManagement;
import controller.UsersAccountManagement;
import model.*;
import storage.IReadAndWrite;
import storage.IWriteLog;
import storage.ReadAndWrite;
import storage.WriteLogFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class Client {
    public static Scanner scanner = new Scanner(System.in);
    public static IWriteLog logWrite = WriteLogFile.getInstance();
    public static IReadAndWrite<List<Employee>> employeeIReadAndWrite = ReadAndWrite.getInstance();
    public static List<Employee> employees = employeeIReadAndWrite.readFile("management.bin");
    public static IReadAndWrite<List<AdminAccount>> adminAccountIReadAndWrite = ReadAndWrite.getInstance();
    public static List<AdminAccount> accounts = adminAccountIReadAndWrite.readFile("admin.bin");
    public static IReadAndWrite<List<UsersAccount>> usersIReadAndWrite = ReadAndWrite.getInstance();
    public static List<UsersAccount> users = usersIReadAndWrite.readFile("users.bin");
    public static Management management = new Management(employees);
    public static AdminAccountManagement admin = new AdminAccountManagement(accounts);
    public static UsersAccountManagement user = new UsersAccountManagement(users);
    public static LocalDateTime now = LocalDateTime.now();

    public static void main(String[] args) {
//        management.display();
//        defaultData();
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
            System.out.print("Moi nhap lua chon: ");
            rollChoice = scanner.nextInt();
            switch (rollChoice) {
                case 0 -> {
                    logWrite("Da thoat khoi chuong trinh.");
                    System.exit(0);
                }
                case 1 -> {
                    logWrite("Admin da truy cap.");
                    int adminChoice;
                    do {
                        System.out.print("""
                                -------------------------------------
                                |   1. Dang nhap                    |
                                |   2. Thoat                        |
                                -------------------------------------
                                """);
                        System.out.print("Moi nhap lua chon: ");
                        adminChoice = scanner.nextInt();

                        switch (adminChoice) {
                            case 1 -> {
                                System.out.print("Tai khoan: ");
                                scanner.nextLine();
                                String username = scanner.nextLine();
                                System.out.print("Mat khau: ");
                                String password = scanner.nextLine();
                                if (username.equals(accounts.get(0).getUsername()) &&
                                        password.equals(accounts.get(0).getPassword())) {
                                    System.out.println("Dang nhap thanh cong!");
                                    logWrite("Dang nhap thanh cong!");
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
                                            case 0 -> {
                                                System.exit(0);
                                                logWrite("Admin da thoat khoi chuong trinh.");
                                            }
                                            case 1 -> displayMenu();
                                            case 2 -> createNewEmployee();
                                            case 3 -> editEmployeeById();
                                            case 4 -> deleteById();
                                            case 5 -> findWithId();
                                            case 99 -> management.deleteAllStaff();
                                        }
                                    } while (success != 0);
                                } else {
                                    loginFailCount = loginFailAlert(loginFailCount);
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

    private static void logWrite(String message) {
        String log = now.toString() + ": " + message;
        logWrite.WriteLogFile(log);
    }

    private static int loginFailAlert(int loginFailCount) {
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
        return loginFailCount;
    }

    private static void findWithId() {
        System.out.print("Nhap vao ID can tim: ");
        scanner.nextLine();
        String id = scanner.nextLine();
        management.findWithId(id);
    }

    private static void editEmployeeById() {
        System.out.print("Nhap id nhan vien:");
        scanner.nextLine();
        String findById = scanner.nextLine();
        int changeCount = 0;

        for (Employee employee : employees) {
            if (findById.equals(employee.getId())) {
                changeCount++;
                System.out.print("Nhap ten: ");
                String name = scanner.nextLine();
                employee.setName(name);

                System.out.print("Nhap ngay sinh:");
                String dateOfBirth = scanner.nextLine();
                employee.setDateOfBirth(dateOfBirth);

                System.out.print("Nhap vao so dien thoai: ");
                String phoneNumber = scanner.nextLine();
                employee.setPhoneNumbers(phoneNumber);

                System.out.print("Nhap dia chi: ");
                String address = scanner.nextLine();
                employee.setAddress(address);

                System.out.print("Nhap email: ");
                String email = scanner.nextLine();
                employee.setEmail(email);
                if (employee instanceof FullTimeEmployee) {
                    System.out.print("Nhap luong:");
                    int basicSalary = scanner.nextInt();
                    ((FullTimeEmployee) employee).setBasicSalary(basicSalary);

                    System.out.print("Nhap luong thuong: ");
                    int bonus = scanner.nextInt();
                    ((FullTimeEmployee) employee).setBonus(bonus);

                    System.out.print("Nhap so tien bi phat: ");
                    int fine = scanner.nextInt();
                    ((FullTimeEmployee) employee).setFine(fine);
                    System.out.println("Nhan vien " + employee.getId() + " da duoc thay doi thong tin thanh cong!");
                    return;
                } else if (employee instanceof PartTimeEmployee) {
                    System.out.print("Nhap so gio lam: ");
                    double workTime = scanner.nextDouble();
                    ((PartTimeEmployee) employee).setWorkTimes(workTime);

                    System.out.println("Nhan vien co ID " + employee.getId() + " da duoc thay doi thong tin thanh cong!");
                    return;
                }
            }
        }
        if (changeCount == 0) {
            System.out.println("ID vua nhap khong ton tai.");
            editEmployeeById();
        }
    }

    private static void deleteById() {
        System.out.println("Xoa nhan vien theo id");
        System.out.print("Nhap id can xoa: ");
        scanner.nextLine();
        String id = scanner.nextLine();
        management.removeEmployee(id);
    }

    private static void displayMenu() {
        logWrite("Admin da truy cap vao hien thi");
        int displayChoice;
        do {
            System.out.println("""
                    ----------------------------------------------------
                    |   1. Hien thi toan bo nhan vien                  |
                    |   2. Hien thi toan bo nhan vien chinh thuc       |
                    |   3. Hien thi toan bo nhan vien part-time        |
                    |   4. Tro ve menu truoc                           |
                    |   5. Thoat khoi chuong trinh                     |
                    ----------------------------------------------------
                    """);
            System.out.print("Moi nhap lua chon: ");
            displayChoice = scanner.nextInt();
            switch (displayChoice) {
                case 1 -> {
                    if (management != null) {
                        management.display();
                        logWrite("Admin da chon hien thi tat ca nhan vien.");
                    } else {
                        System.out.println("Khong co nhan vien");
                        logWrite("Khong co nhan vien");
                    }
                }
                case 2 -> {
                    if (management != null) {
                        management.fulltimeEmployeeDisplay();
                        logWrite("Admin da chon hien thi tat ca nhan vien chinh thuc.");
                    } else {
                        System.out.println("Khong co nhan vien");
                        logWrite("Khong co nhan vien");
                    }
                }
                case 3 -> {
                    if (management != null) {
                        management.parttimeEmployeeDisplay();
                        logWrite("Admin da chon hien thi tat ca nhan vien ban thoi gian.");
                    } else {
                        System.out.println("Khong co nhan vien");
                        logWrite("Khong co nhan vien");
                    }
                }
                case 4 -> System.out.println("Tro ve memu truoc");
                case 5 -> {
                    logWrite("Da thoat khoi chuong trinh");
                    System.exit(0);
                }
            }
        } while (displayChoice != 0);
    }

    private static void createNewEmployee() {
        logWrite("Admin da truy cap vao menu tao nhan vien");
        int role = 0;
        do {
            System.out.println("Nhan vien chinh thuc/nhan vien part-time (1/2): ");
            role = scanner.nextInt();

            if (role != 1 && role != 2) {
                System.out.println("Gia tri khong hop le, moi nhap lai.");
                logWrite("Nhap role nhan vien sai");
            }
        } while (role != 1 && role != 2);
        scanner.nextLine();
        System.out.println("Nhap ma nhan vien: ");
        String id = scanner.nextLine();
        System.out.println("Nhap ten nhan vien: ");
        String name = scanner.nextLine();
        System.out.println("Ngay sinh (dd/mm/yyyy): ");
        String dateOfBirth = scanner.nextLine();
        System.out.println("Nhap so dien thoai: ");
        String phoneNumber = scanner.nextLine();
        System.out.println("Nhap dia chi: ");
        String address = scanner.nextLine();
        System.out.println("Nhap email: ");
        String email = scanner.nextLine();
        if (role == 1) {
            System.out.print("Nhap luong co ban: ");
            int basicSalary = scanner.nextInt();
            System.out.print("Nhap luong thuong thang nay: ");
            int bonus = scanner.nextInt();
            System.out.print("Nhap so tien phat: ");
            int fine = scanner.nextInt();
            Employee employeeInput = new FullTimeEmployee(id, name, dateOfBirth, phoneNumber, address, email, basicSalary, bonus, fine);
            management.addEmployee(employeeInput);
            System.out.println("Da them nhan vien " + name + " vao vi tri nhan vien chinh thuc.");
            logWrite("Admin da them nhan vien " + name + " vao vi tri nhan vien chinh thuc.");
            createUserAccount(id, name, dateOfBirth);
            System.out.println("Da tao thanh cong tai khoan nhan vien.");
            logWrite("Admin da them moi tai khoan nhan vien voi id: " + id);
        } else if (role == 2) {
            System.out.println("Nhap vao so gio lam ");
            double workedTime = scanner.nextDouble();
            Employee employeeInput = new PartTimeEmployee(id, name, dateOfBirth, phoneNumber, address, email, workedTime);
            management.addEmployee(employeeInput);
            System.out.println("Dan them nhan vien " + name + " vao vi tri nhan vien part-time");
            logWrite("Admin da them nhan vien " + name + " vao vi tri nhan vien part-time");
            createUserAccount(id, name, dateOfBirth);
            logWrite("Admin da them moi tai khoan nhan vien voi id: " + id);
        }
    }

    private static void createUserAccount(String id, String name, String dateOfBirth) {
        String username = user.employeeAccountGeneration(name, dateOfBirth);
        String password = dateOfBirth.replaceAll("/", "");
        UsersAccount userAccount = new UsersAccount(id, username, password);
        user.setUserAccount(userAccount);
        System.out.println("Da tao thanh cong tai khoan cho nhan vien co ID '" + id + "'");
    }

    private static void defaultData() {
        AdminAccount adminAccount = new AdminAccount("admin", "admin");
        admin.setAdmin(adminAccount);
        Employee test1 = new FullTimeEmployee("1", "test", "01/01/1991", "0xxxxxxxx", "test@gmail.com",
                "email@gmail.com", 10000000, 1000000, 500000);
        Employee test2 = new PartTimeEmployee("2", "test2", "02/02/1991", "012345xx", "test2@gmail.com",
                "email2@gmail.com", 15.5);
        management.addEmployee(test1);
        management.addEmployee(test2);
    }
}
