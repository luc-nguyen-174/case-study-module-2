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
//        System.out.println(user.getUsersAccountList());
        try {
            int rollChoice;
            int loginFailCount = 0;
            do {
                System.out.print("""
                        -------------------------------------
                        |  1. Admin                         |
                        |  2. Nhân viên                     |
                        |  0. Thoat                         |
                        -------------------------------------
                        """);
                System.out.print("Mời nhập lựa chọn: ");
                rollChoice = scanner.nextInt();
                switch (rollChoice) {
                    case 0 -> //Exit
                    {
                        logWrite("Đã thoát khỏi chương trình.");
                        System.exit(0);
                    }
                    case 1 -> //Menu Admin
                    {
                        logWrite("Admin đã truy cập.");
                        int adminChoice;
                        do {
                            System.out.print("""
                                    -------------------------------------
                                    |   1. Đăng nhập                    |
                                    |   2. Thoát                        |
                                    -------------------------------------
                                    """);
                            System.out.print("Mời nhập lựa chọn: ");
                            adminChoice = scanner.nextInt();

                            switch (adminChoice) {
                                case 1 -> {
                                    System.out.print("Tài khoản: ");
                                    scanner.nextLine();
                                    String username = scanner.nextLine();
                                    System.out.print("Mật khẩu: ");
                                    String password = scanner.nextLine();
                                    if (username.equals(accounts.get(0).getUsername()) &&
                                            password.equals(accounts.get(0).getPassword())) {
                                        adminChoiceMenu();
                                    } else {
                                        loginFailCount = loginFailAlert(loginFailCount);
                                    }
                                }
                                case 2 -> System.exit(adminChoice);
                                default -> management.inputValidateAlert();
                            }
                        } while (adminChoice != 0);
                    }
                    case 2 ->//Menu users
                    {
                        logWrite("Nhan vien da truy cap vao he thong.");
                        int usersChoice;
                        do {
                            System.out.println("""
                                    -------------------------------------
                                    |   1. Đăng nhập                    |
                                    |   2. Thoát                        |
                                    -------------------------------------
                                    """);
                            usersChoice = scanner.nextInt();
                            int loginSuccess;
                            switch (usersChoice) {
                                case 1 -> {
                                    loginSuccess = 0;
                                    System.out.print("Tài khoản: ");
                                    scanner.nextLine();
                                    String username = scanner.nextLine();
                                    System.out.print("Mật khẩu: ");
                                    String password = scanner.nextLine();
                                    int userIndex = 0;
                                    for (UsersAccount usersAccount : users) {
                                        if (username.equals(usersAccount.getUsername()) &&
                                                password.equals(usersAccount.getPassword())) {
                                            userIndex = users.indexOf(usersAccount);

                                            loginSuccess++;
                                        }
                                        if (loginSuccess == 1) {
                                            System.out.println("Dang nhap thanh cong.");
                                            delaySetting(1000);
                                            int userMenu;
                                            do {
                                                System.out.println("""
                                                        ---------------------------------
                                                        |   1. Hiển thị lương tháng này |
                                                        |   2. Đổi mật khẩu             |
                                                        ---------------------------------
                                                        """);
                                                System.out.println("Mời nhập lựa chọn");
                                                userMenu = scanner.nextInt();
                                                int salary;
                                                switch (userMenu) {
                                                    case 1 -> {
                                                        for (int i = 0; i < employees.size(); i++) {
                                                            if (employees.size() != 0) {
                                                                if (employees.get(userIndex) instanceof FullTimeEmployee) {
                                                                    salary = ((FullTimeEmployee) employees.get(userIndex)).salaryCount();
                                                                    System.out.println(salary);
                                                                    break;
                                                                } else if (employees.get(userIndex) instanceof PartTimeEmployee) {
                                                                    salary = ((PartTimeEmployee) employees.get(userIndex)).salaryCount();
                                                                    System.out.println(salary);
                                                                    break;
                                                                }
                                                            } else {
                                                                System.out.println("Khong co du lieu.");
                                                            }
                                                        }
                                                    }
                                                    case 2 -> System.out.println("2");
                                                    default -> {
                                                        management.inputValidateAlert();
                                                    }
                                                }
                                            } while (true);


                                        }
                                    }
                                }
                                case 2 -> System.exit(0);
                                default -> management.inputValidateAlert();
                            }
                        } while (usersChoice != 0);
                    }
                    default -> management.inputValidateAlert();
                }
            } while (rollChoice != 0);
        } catch (Exception e) {
            System.err.println("Nhập lỗi, mời khởi động lại chương trình");

        }
    }

    private static void delaySetting(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void adminChoiceMenu() {
        System.out.println("Đăng nhập thành công!");
        logWrite("Admin đã đăng nhập thành công vào hệ thống!");
        delaySetting(1000);
        int success = 0;
        do {
            System.out.print("""
                    -------------------------------------
                    |   1. Hiển thị danh sách nhân viên |
                    |   2. Thêm nhân viên               |
                    |   3. Sửa nhân viên theo ID        |
                    |   4. Xóa nhân viên theo ID        |
                    |   5. Tìm nhân viên theo ID        |
                    |   9. Trở về menu trước            |
                    |   99. Clear toàn bộ nhân viên     |
                    |   0. Thoát                        |
                    -------------------------------------
                                    """);
            System.out.print("Mời nhập lựa chọn: ");
            success = scanner.nextInt();
            switch (success) {
                case 0 -> {
                    System.exit(0);
                    logWrite("Admin đã thoát khỏi chương trình.");
                }
                case 1 -> displayMenu();
                case 2 -> createNewEmployee();
                case 3 -> editEmployeeById();
                case 4 -> deleteById();
                case 5 -> findWithId();
                case 9 -> System.out.println(1);//chua hoan thanh
                case 99 -> management.deleteAllStaff();
                default -> management.inputValidateAlert();
            }
        } while (true);
    }

    private static void logWrite(String message) {
        String log = now.toString() + ": " + message;
        logWrite.WriteLogFile(log);
    }

    private static int loginFailAlert(int loginFailCount) {
        loginFailCount++;
        System.out.println(loginFailCount);
        System.out.println("Đăng nhập thất bại, hãy kiểm tra lại tài khoản hoặc mật khẩu!");
        if (loginFailCount > 2) {
            System.out.println("Bạn đã nhập sai quá nhiều, chương trình sẽ tự động thoát sau 5s.");
            delaySetting(5000);
            System.exit(0);
        }
        return loginFailCount;
    }

    private static void findWithId() {
        System.out.print("Nhập vào ID cần truy suất: ");
        scanner.nextLine();
        String id = scanner.nextLine();
        management.findWithId(id);
    }

    private static void editEmployeeById() {
        System.out.print("Nhập vào ID nhân viên cần chỉnh sửa:");
        scanner.nextLine();
        String findById = scanner.nextLine();
        int changeCount = 0;

        for (Employee employee : employees) {
            if (findById.equals(employee.getId())) {
                changeCount++;
                System.out.print("Nhập tên mới: ");
                String name = scanner.nextLine();
                employee.setName(name);

                System.out.print("Nhập ngày sinh mới:");
                String dateOfBirth = scanner.nextLine();
                employee.setDateOfBirth(dateOfBirth);

                System.out.print("Nhập vào số điện thoại mới: ");
                String phoneNumber = scanner.nextLine();
                employee.setPhoneNumbers(phoneNumber);

                System.out.print("Nhập vào địa chỉ mới: ");
                String address = scanner.nextLine();
                employee.setAddress(address);

                System.out.print("Nhập email mới: ");
                String email = scanner.nextLine();
                employee.setEmail(email);
                if (employee instanceof FullTimeEmployee) {
                    System.out.print("Nhập lương cơ bản mới:");
                    int basicSalary = scanner.nextInt();
                    ((FullTimeEmployee) employee).setBasicSalary(basicSalary);

                    System.out.print("Nhập lương thưởng mới: ");
                    int bonus = scanner.nextInt();
                    ((FullTimeEmployee) employee).setBonus(bonus);

                    System.out.print("Nhập số tiền bị phạt: ");
                    int fine = scanner.nextInt();
                    ((FullTimeEmployee) employee).setFine(fine);
                    System.out.println("Nhân viên có ID là '" + employee.getId() + "' đã được thay đổi thông tin thành công!");
                    return;
                } else if (employee instanceof PartTimeEmployee) {
                    System.out.print("Nhập số giờ làm: ");
                    double workTime = scanner.nextDouble();
                    ((PartTimeEmployee) employee).setWorkTimes(workTime);

                    System.out.println("Nhân viên có ID là '" + employee.getId() + "' đã được thay đổi thông tin thành công!");
                    return;
                }
            }
        }
        if (changeCount == 0) {
            System.out.println("ID vừa nhập không tồn tại.");
            editEmployeeById();
        }
    }

    private static void deleteById() {
        System.out.println("Xóa nhân viên theo ID id.");
        System.out.print("Nhập ID nhân viên cần xóa: ");
        scanner.nextLine();
        String id = scanner.nextLine();
        management.removeEmployee(id);
    }

    private static void displayMenu() {
        logWrite("Admin đã truy cập vào menu hiển thị.");
        int displayChoice;
        do {
            System.out.println("""
                    ----------------------------------------------------
                    |   1. Hiển thị toàn bộ nhân viên                  |
                    |   2. Hiển thị toàn bộ nhân viên chính thức       |
                    |   3. Hiển thị toàn bộ nhân viên part-time        |
                    |   4. Trở về menu trước                           |
                    |   5. Thoát khỏi chương trình                     |
                    ----------------------------------------------------
                    """);
            System.out.print("Mời nhập lựa chọn: ");
            displayChoice = scanner.nextInt();
            switch (displayChoice) {
                case 1 -> {
                    if (management != null) {
                        management.display();
                        logWrite("Admin đã lựa chọn hiển thị toàn bộ nhân viên.");
                    } else {
                        System.out.println("Không có nhân viên nào trong danh sách");
                        logWrite("Không có nhân viên nào trong danh sách");
                    }
                }
                case 2 -> {
                    if (management != null) {
                        management.fulltimeEmployeeDisplay();
                        logWrite("Admin đã lựa chọn hiển thị toàn bộ nhân viên chính thức.");
                    } else {
                        System.out.println("Không có nhân viên nào trong danh sách");
                        logWrite("Không có nhân viên nào trong danh sách");
                    }
                }
                case 3 -> {
                    if (management != null) {
                        management.parttimeEmployeeDisplay();
                        logWrite("Admin đã lựa chọn hiển thị toàn bộ nhân viên bán thời gian.");
                    } else {
                        System.out.println("Không có nhân viên nào trong danh sách");
                        logWrite("Không có nhân viên nào trong danh sách");
                    }
                }
                case 4 -> adminChoiceMenu();
                case 5 -> {
                    logWrite("Admin đã thoát khỏi chương trình");
                    System.exit(0);
                }
                default -> management.inputValidateAlert();
            }
        } while (displayChoice != 0);
    }

    private static void createNewEmployee() {
        logWrite("Admin đã lựa chọn menu thêm mới nhân viên");
        int role = 0;
        do {
            System.out.println("Lựa chọn vai trò:");
            System.out.println("Nhân viên chính thức/part-time (1/2):");
            role = scanner.nextInt();

            if (role != 1 && role != 2) {
                System.out.println("Gia tri khong hop le, moi nhap lai.");
                logWrite("Nhap role nhan vien sai");
            }
        } while (role != 1 && role != 2);
        scanner.nextLine();
        System.out.println("Nhập mã nhân viên: ");
        String id = scanner.nextLine();
        System.out.println("Nhập tên nhân viên: ");
        String name = scanner.nextLine();
        System.out.println("Ngày sinh (dd/mm/yyyy): ");
        String dateOfBirth = scanner.nextLine();
        System.out.println("Nhập số điện thoại: ");
        String phoneNumber = scanner.nextLine();
        System.out.println("Nhập địa chỉ: ");
        String address = scanner.nextLine();
        System.out.println("Nhập email: ");
        String email = scanner.nextLine();
        if (role == 1) {
            System.out.print("Nhập lương cơ bản: ");
            int basicSalary = scanner.nextInt();
            System.out.print("Nhập lương thưởng tháng này: ");
            int bonus = scanner.nextInt();
            System.out.print("Nhập số tiền bị phạt tháng này: ");
            int fine = scanner.nextInt();
            Employee employeeInput = new FullTimeEmployee(id, name, dateOfBirth, phoneNumber, address, email, basicSalary, bonus, fine);
            management.addEmployee(employeeInput);
            System.out.println("Đã thêm nhân viên " + name + " vào vị trí nhân viên chính thức.");
            logWrite("Admin đã thêm mới nhân viên chính thức.");
            createUserAccount(id, name, dateOfBirth);

        } else if (role == 2) {
            System.out.println("Nhập số giờ làm: ");
            double workedTime = scanner.nextDouble();
            Employee employeeInput = new PartTimeEmployee(id, name, dateOfBirth, phoneNumber, address, email, workedTime);
            management.addEmployee(employeeInput);
            System.out.println("Đã thêm mới nhân viên " + name + " vào vị trí nhân viên part-time");
            logWrite("Admin đã thêm mới nhân viên part-time.");
            createUserAccount(id, name, dateOfBirth);

        }
    }

    private static void createUserAccount(String id, String name, String dateOfBirth) {
        String username = user.employeeAccountGeneration(name, dateOfBirth);
        String password = dateOfBirth.replaceAll("/", "");
        UsersAccount userAccount = new UsersAccount(id, username, password);
        user.setUserAccount(userAccount);
        System.out.println("Đã tạo thành công tài khoản cho nhân viên có ID '" + id + "'");
        logWrite("Admin đã tạo mới thành công tài khoản cho nhân viên có ID '" + id + "'");
    }

    private static void defaultData() {
        AdminAccount adminAccount = new AdminAccount("admin", "admin");
        admin.setAdmin(adminAccount);
        logWrite("Đã khởi tạo thành công tài khoản admin.");
        Employee test1 = new FullTimeEmployee("1", "test", "01/01/1991", "0xxxxxxxx", "test@gmail.com",
                "email@gmail.com", 10000000, 1000000, 500000);
        Employee test2 = new PartTimeEmployee("2", "test2", "02/02/1991", "012345xx", "test2@gmail.com",
                "email2@gmail.com", 15.5);
        management.addEmployee(test1);
        management.addEmployee(test2);
        logWrite("Đã khởi tạo thành công default data.");
    }
}
