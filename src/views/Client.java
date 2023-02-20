package views;

import com.sun.security.jgss.GSSUtil;
import controller.Management;

import controller.AdminAccountManagement;
import controller.UsersAccountManagement;
import model.*;
import org.jetbrains.annotations.NotNull;
import storage.IReadAndWrite;
import storage.IWriteLog;
import storage.ReadAndWrite;
import storage.WriteLogFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Client {
    public static Scanner scanner = new Scanner(System.in);
    public static IWriteLog logWrite = WriteLogFile.getInstance();
    public static IReadAndWrite<List<Employee>> employeeIReadAndWrite = ReadAndWrite.getInstance();
    public static List<Employee> employees = employeeIReadAndWrite.readFile("src/database/management.bin");
    public static IReadAndWrite<List<AdminAccount>> adminAccountIReadAndWrite = ReadAndWrite.getInstance();
    public static List<AdminAccount> adminAccounts = adminAccountIReadAndWrite.readFile("src/database/admin.bin");
    public static IReadAndWrite<List<UsersAccount>> usersIReadAndWrite = ReadAndWrite.getInstance();
    public static List<UsersAccount> users = usersIReadAndWrite.readFile("src/database/users.bin");
    public static Management management = new Management(employees);
    public static AdminAccountManagement admin = new AdminAccountManagement(adminAccounts);
    public static UsersAccountManagement user = new UsersAccountManagement(users);
    public static LocalDateTime now = LocalDateTime.now();
    private static final String DATEOFBIRTH_VALIDATION = "(^(((0[1-9]|1[0-9]|2[0-8])[\\/](0[1-9]|1[012]))|((29|30|31)[\\/](0[13578]|1[02]))|((29|30)[\\/](0[4,6,9]|11)))[\\/](19|[2-9][0-9])\\d\\d$)|(^29[\\/]02[\\/](19|[2-9][0-9])(00|04|08|12|16|20|24|28|32|36|40|44|48|52|56|60|64|68|72|76|80|84|88|92|96)$)";
    private static final String PHONE_NUMBER = "(((\\+|)84)|0)(3|5|7|8|9)+([0-9]{8})\\b";
    private static final String EMAIL_VALIDATION = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

    public static void main(String[] args) {

//        defaultData();
//        management.display();
        System.out.println(user.getUsersAccountList());
        menu();
    }

    private static void menu() {
        try {
            int roleChoice;
            int loginFail = 0;
            boolean loginSuccess = false;
            do {
                System.out.print("""
                        -------------------------------------
                        |  1. Admin                         |
                        |  2. Nhân viên                     |
                        |  0. Thoát                         |
                        -------------------------------------
                        """);
                System.out.print("Mời nhập lựa chọn: ");
                roleChoice = scanner.nextInt();
                switch (roleChoice) {
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
                                    |   2. Trở về                       |
                                    |   3. Thoát                        |
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
                                    for (AdminAccount account : adminAccounts) {
                                        if (username.equals(account.getUsername()) &&
                                                password.equals(account.getPassword())) {
                                            loginSuccess = true;
                                        }
                                        if (loginSuccess) {
                                            System.out.println("Đăng nhập thành công.");
                                            adminChoiceMenu();
                                        }
                                    }
                                    loginFail = loginFailAlert(loginSuccess, loginFail);
                                }
                                case 2 -> {
                                    logWrite("Admin đã lựa chọn trở về menu chọn vai trò.");
                                    menu();
                                }
                                case 3 -> {
                                    logWrite("Admin đã thoát");
                                    System.exit(adminChoice);
                                }
                                default -> management.inputValidateAlert();
                            }
                        } while (adminChoice != 0);
                    }
                    case 2 ->//Menu users
                    {
                        logWrite("Nhân viên đã truy cập vào hệ thống.");
                        int usersChoice;

                        do {
                            System.out.println("""
                                    -------------------------------------
                                    |   1. Đăng nhập                    |
                                    |   2. Trở về                       |
                                    |   3. Thoát                        |
                                    -------------------------------------
                                    """);
                            System.out.print("Mời nhập lựa chọn: ");
                            usersChoice = scanner.nextInt();

                            String id = "";
                            switch (usersChoice) {
                                case 1 -> {
                                    System.out.print("Tài khoản: ");
                                    scanner.nextLine();
                                    String username = scanner.nextLine();
                                    System.out.print("Mật khẩu: ");
                                    String password = scanner.nextLine();
                                    int userIndex;

                                    for (UsersAccount usersAccount : users) {
                                        if (username.equals(usersAccount.getUsername()) &&
                                                password.equals(usersAccount.getPassword())) {
                                            userIndex = users.indexOf(usersAccount);
                                            id = users.get(userIndex).getId();
                                            loginSuccess = true;
                                        }
                                        if (loginSuccess) {
                                            System.out.println("Đăng nhập thành công.");
                                            logWrite(usersAccount.getUsername() + " đã đăng nhập thành công.");
                                            userMenu(id, usersAccount);
                                        }
                                    }
                                    loginFail = loginFailAlert(loginSuccess, loginFail);
                                }
                                case 2 -> {
                                    menu();
                                }
                                case 3 -> {
                                    System.exit(0);
                                }
                                default -> management.inputValidateAlert();
                            }
                        } while (usersChoice != 0);
                    }
                    default -> management.inputValidateAlert();
                }
            } while (true);
        } catch (Exception e) {
            System.err.println("Nhập lỗi, mời khởi động lại chương trình.");
        }
    }

    private static void adminChoiceMenu() {
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
                    |   9. Đăng xuất                    |
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
                case 3 -> {
                    scanner.nextLine();
                    editEmployeeById();
                }
                case 4 -> deleteById();
                case 5 -> findWithId();
                case 9 -> {
                    System.out.println("Đang đăng xuất.");
                    logWrite("Admin đã đăng xuất.");
                    delaySetting(500);
                    menu();
                }
                case 99 -> {
                    System.err.println("Cảnh báo: ");
                    System.out.print("Bạn có đồng ý xóa tất cả thành viên (1/2): ");
                    scanner.nextLine();
                    int confirm = scanner.nextInt();
                    if (confirm == 1) {
                        management.deleteAllStaff();
                        logWrite("Đã xóa tất cả thành viên.");
                        user.deleteAllAccount();
                        logWrite("Đã xóa tất cả tài khoản thành viên");
                    } else if (confirm == 2) {
                        adminChoiceMenu();
                    }
                }
                default -> management.inputValidateAlert();
            }
        } while (true);
    }

    private static void userMenu(String id, UsersAccount usersAccount) {
        delaySetting(1000);
        int userMenu;
        do {
            System.out.println("""
                    ---------------------------------
                    |   1. Hiển thị lương tháng này |
                    |   2. Đổi mật khẩu             |
                    |   3. Đăng xuất                |
                    ---------------------------------
                    """);
            System.out.print("Mời nhập lựa chọn: ");
            userMenu = scanner.nextInt();
            switch (userMenu) {
                case 1 -> //display salary
                {
                    logWrite(usersAccount.getUsername() + " đã chọn hiển thị lương.");
                    salaryDisplay(id);
                }
                case 2 -> //change password
                {
                    logWrite(usersAccount.getUsername() + " đã chọn thay đổi mật khẩu.");
                    changePassword(usersAccount);
                }
                case 3 -> {
                    logWrite("Đã đăng xuất.");
                    menu();
                }
                default -> {
                    management.inputValidateAlert();
                }
            }
        } while (true);
    }

    private static void changePassword(@NotNull UsersAccount usersAccount) {
        scanner.nextLine();
        System.out.print("Nhập mật khẩu hiện tại: ");
        String nowPassword = scanner.nextLine();
        if (nowPassword.equals(usersAccount.getPassword())) {
            System.out.print("Nhập mật khẩu mới: ");
            String newPassword = scanner.nextLine();
            System.out.print("Nhập lại mật khẩu mới: ");
            String confirmPassword = scanner.nextLine();
            if (newPassword.equals(confirmPassword)) {
                user.editAccount(usersAccount, newPassword);
                System.out.println("Đã thay đổi mật khẩu thành công.");
                logWrite(usersAccount.getUsername() + " đã thay đổi mật khẩu.");
            }
        }
    }

    private static void salaryDisplay(String id) {
        int salary;
        for (Employee employee : employees) {
            if (employee instanceof FullTimeEmployee) {
                if (employee.getId().equals(id)) {
                    salary = ((FullTimeEmployee) employee).salaryCount();
                    System.out.println("Tiền lương thực lĩnh: " + salary);
                    break;
                }
            } else if (employee instanceof PartTimeEmployee) {
                if (employee.getId().equals(id)) {
                    salary = ((PartTimeEmployee) employee).salaryCount();
                    System.out.println("Tiền lương thực lĩnh: " + salary);
                    break;
                }
            }
        }
    }

    private static int loginFailAlert(boolean loginSuccess, int loginFail) throws InterruptedException {
        if (!loginSuccess) {
            loginFail++;
            logWrite("Đăng nhập thất bại.");
            System.out.println("Sai tài khoản hoặc mật khẩu.");
            System.out.println("Bạn đã nhập sai " + loginFail + "/3");
            if (loginFail == 3) {
                for (int i = 3; i > 0; i--) {
                    System.out.println("Bạn đã nhập sai quá nhiều, chương trình sẽ tự động thoát sau " + i + "s.");
                    Thread.sleep(1000);
                }
                System.exit(0);
            }
        }
        return loginFail;
    }

    private static void delaySetting(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void findWithId() {
        System.out.print("Nhập vào ID cần truy suất: ");
        scanner.nextLine();
        String id = scanner.nextLine();
        management.findWithId(id);
        System.out.print("Bạn có muốn tiếp tục tìm (1/2):");
        int confirm = scanner.nextInt();
        if (confirm == 1) {
            findWithId();
        } else if (confirm == 2) {
            adminChoiceMenu();
        }
    }

    private static void editEmployeeById() {
        System.out.print("Nhập vào ID nhân viên cần chỉnh sửa:");
        String findById = scanner.nextLine();
        int changeCount = 0;

        for (Employee employee : employees) {
            if (findById.equals(employee.getId())) {
                changeCount++;
                String id = findById;
                management.removeEmployee(id);          //remove old employee
                user.removeUserAccount(id);             // remove old user account

                System.out.println("Lựa chọn vai trò:");
                System.out.println("Nhân viên chính thức/part-time (1/2):");
                int role = getRole();

                System.out.print("Nhập tên mới: ");
                String name = scanner.nextLine();


                System.out.print("Nhập ngày sinh mới:");
                String dateOfBirth = scanner.nextLine();


                System.out.print("Nhập vào số điện thoại mới: ");
                String phoneNumber = scanner.nextLine();


                System.out.print("Nhập vào địa chỉ mới: ");
                String address = scanner.nextLine();


                System.out.print("Nhập email mới: ");
                String email = scanner.nextLine();

                if (role == 1) {
                    System.out.print("Nhập lương cơ bản mới:");
                    int basicSalary = scanner.nextInt();


                    System.out.print("Nhập lương thưởng mới: ");
                    int bonus = scanner.nextInt();


                    System.out.print("Nhập số tiền bị phạt: ");
                    int fine = scanner.nextInt();

                    createNewFulltimeEmployee(id, name, dateOfBirth, phoneNumber, address, email, basicSalary, bonus, fine);

                    createUserAccount(id, name, dateOfBirth); //create new user account
                    System.out.println("Nhân viên có ID là '" + employee.getId() + "' đã được thay đổi thông tin thành công!");
                    return;
                } else if (role == 2) {
                    System.out.print("Nhập số giờ làm: ");
                    double workTime = scanner.nextDouble();

                    createNewParttimeEmployee(id, name, dateOfBirth, phoneNumber, address, email, workTime);

                    createUserAccount(id, name, dateOfBirth); // create new user account
                    System.out.println("Nhân viên có ID là '" + employee.getId() + "' đã được thay đổi thông tin thành công!");
                    return;
                }
            }
        }
        if (changeCount == 0) {
            System.out.println("ID vừa nhập không tồn tại.");
            editEmployeeById();
        }
        System.out.print("Bạn có muốn tiếp tục sửa (1/2):");
        int confirm = scanner.nextInt();
        if (confirm == 1) {
            editEmployeeById();
        } else if (confirm == 2) {
            adminChoiceMenu();
        }
    }

    private static void deleteById() //remove the employee and remove employee's account
    {
        System.out.println("Xóa nhân viên theo ID id.");
        System.out.print("Nhập ID nhân viên cần xóa: ");
        scanner.nextLine();
        String id = scanner.nextLine();
        management.removeEmployee(id);
        user.removeUserAccount(id);
        System.out.println("Nhân viên có " + id + " đã được xóa thành công.");
        System.out.print("Bạn có muốn tiếp tục xóa (1/2):");
        int confirm = scanner.nextInt();
        if (confirm == 1) {
            deleteById();
        } else if (confirm == 2) {
            adminChoiceMenu();
        }
    }

    private static void createNewEmployee() {
        logWrite("Admin đã lựa chọn menu thêm mới nhân viên");
        System.out.println("Lựa chọn vai trò:");
        System.out.println("Nhân viên chính thức/part-time (1/2):");
        int role = getRole();

        scanner.nextLine();
        System.out.println("Nhập mã nhân viên: ");
        String id = getId();

        System.out.println("Nhập tên nhân viên: ");
        String name = scanner.nextLine();

        System.out.println("Ngày sinh (dd/mm/yyyy): ");
        String dateOfBirth = checkDateValidate();

        System.out.println("Nhập số điện thoại: ");
        String phoneNumber = checkPhoneValidate();

        System.out.println("Nhập địa chỉ: ");
        String address = scanner.nextLine();

        System.out.println("Nhập email: ");
        String email = checkEmailValidate();
        if (role == 1) {
            System.out.print("Nhập lương cơ bản: ");
            int basicSalary = scanner.nextInt();
            System.out.print("Nhập lương thưởng tháng này: ");
            int bonus = scanner.nextInt();
            System.out.print("Nhập số tiền bị phạt tháng này: ");
            int fine = scanner.nextInt();
            createNewFulltimeEmployee(id, name, dateOfBirth, phoneNumber, address, email, basicSalary, bonus, fine);
            createUserAccount(id, name, dateOfBirth);
        } else if (role == 2) {
            System.out.println("Nhập số giờ làm: ");
            double workedTime = scanner.nextDouble();
            createNewParttimeEmployee(id, name, dateOfBirth, phoneNumber, address, email, workedTime);
            createUserAccount(id, name, dateOfBirth);
        }
        System.out.print("Bạn có muốn thêm nhân viên mới (1/2):");
        int confirm = scanner.nextInt();
        if (confirm == 1) {
            createNewEmployee();
        } else if (confirm == 2) {
            adminChoiceMenu();
        }
    }

    private static String getId() {
        String id = "";
        int checkIdValid;
        do {
            checkIdValid = 0;
            id = scanner.nextLine();
            for (Employee employee : employees) {
                if (employee.getId().equals(id)) {
                    checkIdValid++;
                }
            }
            if (checkIdValid != 0) {
                System.out.println("ID này đã tồn tại, mời nhập lại ID khác.");

            }
        } while (checkIdValid != 0);
        return id;
    }

    private static int getRole() {
        int role;
        do {
            role = scanner.nextInt();

            if (role != 1 && role != 2) {
                System.out.println("Giá trị không hợp lệ, mời nhập lại:");
                logWrite("Nhập vai trò sai.");
            }
        } while (role != 1 && role != 2);
        return role;
    }

    private static void createNewParttimeEmployee(String id, String name, String dateOfBirth, String phoneNumber, String address, String email, double workedTime) {
        Employee employeeInput = new PartTimeEmployee(id, name, dateOfBirth, phoneNumber, address, email, workedTime);
        management.addEmployee(employeeInput);
        System.out.println("Đã thêm mới nhân viên " + name + " vào vị trí nhân viên part-time");
        logWrite("Admin đã thêm mới nhân viên part-time.");
    }

    private static void createNewFulltimeEmployee(String id, String name, String dateOfBirth, String phoneNumber, String address, String email, int basicSalary, int bonus, int fine) {
        Employee employeeInput = new FullTimeEmployee(id, name, dateOfBirth, phoneNumber, address, email, basicSalary, bonus, fine);
        management.addEmployee(employeeInput);
        System.out.println("Đã thêm nhân viên " + name + " vào vị trí nhân viên chính thức.");
        logWrite("Admin đã thêm mới nhân viên chính thức.");
    }

    private static void createUserAccount(String id, String name, String dateOfBirth) //create new user account
    {
        String username = user.employeeAccountGeneration(name, dateOfBirth);
        String password = dateOfBirth.replaceAll("/", "");
        UsersAccount userAccount = new UsersAccount(id, username, password);
        user.setUserAccount(userAccount);
        System.out.println("Đã tạo thành công tài khoản cho nhân viên có ID '" + id + "'");
        logWrite("Admin đã tạo mới thành công tài khoản cho nhân viên có ID '" + id + "'");
    }

    private static void displayMenu() //display menu
    {
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
                case 1 -> //display all
                {
                    if (management != null) {
                        management.display();
                        logWrite("Admin đã lựa chọn hiển thị toàn bộ nhân viên.");
                    } else {
                        System.out.println("Không có nhân viên nào trong danh sách");
                        logWrite("Không có nhân viên nào trong danh sách");
                    }
                }
                case 2 -> //display fulltime employee
                {
                    if (management != null) {
                        management.fulltimeEmployeeDisplay();
                        logWrite("Admin đã lựa chọn hiển thị toàn bộ nhân viên chính thức.");
                    } else {
                        System.out.println("Không có nhân viên nào trong danh sách");
                        logWrite("Không có nhân viên nào trong danh sách");
                    }
                }
                case 3 -> //display parttime employee
                {
                    if (management != null) {
                        management.parttimeEmployeeDisplay();
                        logWrite("Admin đã lựa chọn hiển thị toàn bộ nhân viên bán thời gian.");
                    } else {
                        System.out.println("Không có nhân viên nào trong danh sách");
                        logWrite("Không có nhân viên nào trong danh sách");
                    }
                }
                case 4 -> adminChoiceMenu();//return admin choice menu
                case 5 -> //exit
                {
                    logWrite("Admin đã thoát khỏi chương trình");
                    System.exit(0);
                }
                default -> management.inputValidateAlert();
            }
        } while (displayChoice != 0);
    }

    private static String checkDateValidate() {
        String dateOfBirth = scanner.nextLine();
        Pattern pattern = Pattern.compile(DATEOFBIRTH_VALIDATION);
        Matcher matcher = pattern.matcher(dateOfBirth);
        if (matcher.find()) {
            return dateOfBirth;
        } else {
            logWrite("Nhập vào ngày sinh không hợp lệ.");
            System.out.println("Ngày sinh không hợp lệ, mời nhập lại (dd/mm/yyyy): ");
            return checkDateValidate();
        }
    }

    private static String checkPhoneValidate() {
        String phoneNumber = scanner.nextLine();
        Pattern pattern = Pattern.compile(PHONE_NUMBER);
        Matcher matcher = pattern.matcher(phoneNumber);
        if (matcher.find()) {
            return phoneNumber;
        } else {
            logWrite("Nhập vào số điện thoại không hợp lệ.");
            System.out.println("Số điện thoại không hợp lệ, mời nhập lại: ");
            return checkPhoneValidate();
        }
    }

    private static String checkEmailValidate() {
        String email = scanner.nextLine();
        Pattern pattern = Pattern.compile(EMAIL_VALIDATION);
        Matcher matcher = pattern.matcher(email);
        if (matcher.find()) {
            return email;
        } else {
            logWrite("Nhập vào địa chỉ email không hợp lệ.");
            System.out.println("Email không hợp lệ, mời nhập lại (xxx@xxx.com): ");
            return checkEmailValidate();
        }
    }

    private static void logWrite(String message) {
        String log = now.toString() + ": " + message;
        logWrite.WriteLogFile(log);
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
        createUserAccount("1", "test", "01/01/1991");
        createUserAccount("2", "test2", "02/02/1991");
        logWrite("Đã khởi tạo thành công default data.");
    }//create default data
}
