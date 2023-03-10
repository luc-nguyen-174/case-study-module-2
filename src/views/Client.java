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
                        |  2. Nh??n vi??n                     |
                        |  0. Tho??t                         |
                        -------------------------------------
                        """);
                System.out.print("M???i nh???p l???a ch???n: ");
                roleChoice = scanner.nextInt();
                switch (roleChoice) {
                    case 0 -> //Exit
                    {
                        logWrite("???? tho??t kh???i ch????ng tr??nh.");
                        System.exit(0);
                    }
                    case 1 -> //Menu Admin
                    {
                        logWrite("Admin ???? truy c???p.");
                        int adminChoice;
                        do {
                            System.out.print("""
                                    -------------------------------------
                                    |   1. ????ng nh???p                    |
                                    |   2. Tr??? v???                       |
                                    |   3. Tho??t                        |
                                    -------------------------------------
                                    """);
                            System.out.print("M???i nh???p l???a ch???n: ");
                            adminChoice = scanner.nextInt();

                            switch (adminChoice) {
                                case 1 -> {
                                    System.out.print("T??i kho???n: ");
                                    scanner.nextLine();
                                    String username = scanner.nextLine();
                                    System.out.print("M???t kh???u: ");
                                    String password = scanner.nextLine();
                                    for (AdminAccount account : adminAccounts) {
                                        if (username.equals(account.getUsername()) &&
                                                password.equals(account.getPassword())) {
                                            loginSuccess = true;
                                        }
                                        if (loginSuccess) {
                                            System.out.println("????ng nh???p th??nh c??ng.");
                                            adminChoiceMenu();
                                        }
                                    }
                                    loginFail = loginFailAlert(loginSuccess, loginFail);
                                }
                                case 2 -> {
                                    logWrite("Admin ???? l???a ch???n tr??? v??? menu ch???n vai tr??.");
                                    menu();
                                }
                                case 3 -> {
                                    logWrite("Admin ???? tho??t");
                                    System.exit(adminChoice);
                                }
                                default -> management.inputValidateAlert();
                            }
                        } while (adminChoice != 0);
                    }
                    case 2 ->//Menu users
                    {
                        logWrite("Nh??n vi??n ???? truy c???p v??o h??? th???ng.");
                        int usersChoice;

                        do {
                            System.out.println("""
                                    -------------------------------------
                                    |   1. ????ng nh???p                    |
                                    |   2. Tr??? v???                       |
                                    |   3. Tho??t                        |
                                    -------------------------------------
                                    """);
                            System.out.print("M???i nh???p l???a ch???n: ");
                            usersChoice = scanner.nextInt();

                            String id = "";
                            switch (usersChoice) {
                                case 1 -> {
                                    System.out.print("T??i kho???n: ");
                                    scanner.nextLine();
                                    String username = scanner.nextLine();
                                    System.out.print("M???t kh???u: ");
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
                                            System.out.println("????ng nh???p th??nh c??ng.");
                                            logWrite(usersAccount.getUsername() + " ???? ????ng nh???p th??nh c??ng.");
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
            System.err.println("Nh???p l???i, m???i kh???i ?????ng l???i ch????ng tr??nh.");
        }
    }

    private static void adminChoiceMenu() {
        logWrite("Admin ???? ????ng nh???p th??nh c??ng v??o h??? th???ng!");
        delaySetting(1000);
        int success = 0;
        do {
            System.out.print("""
                    -------------------------------------
                    |   1. Hi???n th??? danh s??ch nh??n vi??n |
                    |   2. Th??m nh??n vi??n               |
                    |   3. S???a nh??n vi??n theo ID        |
                    |   4. X??a nh??n vi??n theo ID        |
                    |   5. T??m nh??n vi??n theo ID        |
                    |   9. ????ng xu???t                    |
                    |   99. Clear to??n b??? nh??n vi??n     |
                    |   0. Tho??t                        |
                    -------------------------------------
                                    """);
            System.out.print("M???i nh???p l???a ch???n: ");
            success = scanner.nextInt();
            switch (success) {
                case 0 -> {
                    System.exit(0);
                    logWrite("Admin ???? tho??t kh???i ch????ng tr??nh.");
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
                    System.out.println("??ang ????ng xu???t.");
                    logWrite("Admin ???? ????ng xu???t.");
                    delaySetting(500);
                    menu();
                }
                case 99 -> {
                    System.err.println("C???nh b??o: ");
                    System.out.print("B???n c?? ?????ng ?? x??a t???t c??? th??nh vi??n (1/2): ");
                    scanner.nextLine();
                    int confirm = scanner.nextInt();
                    if (confirm == 1) {
                        management.deleteAllStaff();
                        logWrite("???? x??a t???t c??? th??nh vi??n.");
                        user.deleteAllAccount();
                        logWrite("???? x??a t???t c??? t??i kho???n th??nh vi??n");
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
                    |   1. Hi???n th??? l????ng th??ng n??y |
                    |   2. ?????i m???t kh???u             |
                    |   3. ????ng xu???t                |
                    ---------------------------------
                    """);
            System.out.print("M???i nh???p l???a ch???n: ");
            userMenu = scanner.nextInt();
            switch (userMenu) {
                case 1 -> //display salary
                {
                    logWrite(usersAccount.getUsername() + " ???? ch???n hi???n th??? l????ng.");
                    salaryDisplay(id);
                }
                case 2 -> //change password
                {
                    logWrite(usersAccount.getUsername() + " ???? ch???n thay ?????i m???t kh???u.");
                    changePassword(usersAccount);
                }
                case 3 -> {
                    logWrite("???? ????ng xu???t.");
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
        System.out.print("Nh???p m???t kh???u hi???n t???i: ");
        String nowPassword = scanner.nextLine();
        if (nowPassword.equals(usersAccount.getPassword())) {
            System.out.print("Nh???p m???t kh???u m???i: ");
            String newPassword = scanner.nextLine();
            System.out.print("Nh???p l???i m???t kh???u m???i: ");
            String confirmPassword = scanner.nextLine();
            if (newPassword.equals(confirmPassword)) {
                user.editAccount(usersAccount, newPassword);
                System.out.println("???? thay ?????i m???t kh???u th??nh c??ng.");
                logWrite(usersAccount.getUsername() + " ???? thay ?????i m???t kh???u.");
            }
        }
    }

    private static void salaryDisplay(String id) {
        int salary;
        for (Employee employee : employees) {
            if (employee instanceof FullTimeEmployee) {
                if (employee.getId().equals(id)) {
                    salary = ((FullTimeEmployee) employee).salaryCount();
                    System.out.println("Ti???n l????ng th???c l??nh: " + salary);
                    break;
                }
            } else if (employee instanceof PartTimeEmployee) {
                if (employee.getId().equals(id)) {
                    salary = ((PartTimeEmployee) employee).salaryCount();
                    System.out.println("Ti???n l????ng th???c l??nh: " + salary);
                    break;
                }
            }
        }
    }

    private static int loginFailAlert(boolean loginSuccess, int loginFail) throws InterruptedException {
        if (!loginSuccess) {
            loginFail++;
            logWrite("????ng nh???p th???t b???i.");
            System.out.println("Sai t??i kho???n ho???c m???t kh???u.");
            System.out.println("B???n ???? nh???p sai " + loginFail + "/3");
            if (loginFail == 3) {
                for (int i = 3; i > 0; i--) {
                    System.out.println("B???n ???? nh???p sai qu?? nhi???u, ch????ng tr??nh s??? t??? ?????ng tho??t sau " + i + "s.");
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
        System.out.print("Nh???p v??o ID c???n truy su???t: ");
        scanner.nextLine();
        String id = scanner.nextLine();
        management.findWithId(id);
        System.out.print("B???n c?? mu???n ti???p t???c t??m (1/2):");
        int confirm = scanner.nextInt();
        if (confirm == 1) {
            findWithId();
        } else if (confirm == 2) {
            adminChoiceMenu();
        }
    }

    private static void editEmployeeById() {
        System.out.print("Nh???p v??o ID nh??n vi??n c???n ch???nh s???a:");
        String findById = scanner.nextLine();
        int changeCount = 0;

        for (Employee employee : employees) {
            if (findById.equals(employee.getId())) {
                changeCount++;
                String id = findById;
                management.removeEmployee(id);          //remove old employee
                user.removeUserAccount(id);             // remove old user account

                System.out.println("L???a ch???n vai tr??:");
                System.out.println("Nh??n vi??n ch??nh th???c/part-time (1/2):");
                int role = getRole();

                System.out.print("Nh???p t??n m???i: ");
                String name = scanner.nextLine();


                System.out.print("Nh???p ng??y sinh m???i:");
                String dateOfBirth = scanner.nextLine();


                System.out.print("Nh???p v??o s??? ??i???n tho???i m???i: ");
                String phoneNumber = scanner.nextLine();


                System.out.print("Nh???p v??o ?????a ch??? m???i: ");
                String address = scanner.nextLine();


                System.out.print("Nh???p email m???i: ");
                String email = scanner.nextLine();

                if (role == 1) {
                    System.out.print("Nh???p l????ng c?? b???n m???i:");
                    int basicSalary = scanner.nextInt();


                    System.out.print("Nh???p l????ng th?????ng m???i: ");
                    int bonus = scanner.nextInt();


                    System.out.print("Nh???p s??? ti???n b??? ph???t: ");
                    int fine = scanner.nextInt();

                    createNewFulltimeEmployee(id, name, dateOfBirth, phoneNumber, address, email, basicSalary, bonus, fine);

                    createUserAccount(id, name, dateOfBirth); //create new user account
                    System.out.println("Nh??n vi??n c?? ID l?? '" + employee.getId() + "' ???? ???????c thay ?????i th??ng tin th??nh c??ng!");
                    return;
                } else if (role == 2) {
                    System.out.print("Nh???p s??? gi??? l??m: ");
                    double workTime = scanner.nextDouble();

                    createNewParttimeEmployee(id, name, dateOfBirth, phoneNumber, address, email, workTime);

                    createUserAccount(id, name, dateOfBirth); // create new user account
                    System.out.println("Nh??n vi??n c?? ID l?? '" + employee.getId() + "' ???? ???????c thay ?????i th??ng tin th??nh c??ng!");
                    return;
                }
            }
        }
        if (changeCount == 0) {
            System.out.println("ID v???a nh???p kh??ng t???n t???i.");
            editEmployeeById();
        }
        System.out.print("B???n c?? mu???n ti???p t???c s???a (1/2):");
        int confirm = scanner.nextInt();
        if (confirm == 1) {
            editEmployeeById();
        } else if (confirm == 2) {
            adminChoiceMenu();
        }
    }

    private static void deleteById() //remove the employee and remove employee's account
    {
        System.out.println("X??a nh??n vi??n theo ID id.");
        System.out.print("Nh???p ID nh??n vi??n c???n x??a: ");
        scanner.nextLine();
        String id = scanner.nextLine();
        management.removeEmployee(id);
        user.removeUserAccount(id);
        System.out.println("Nh??n vi??n c?? " + id + " ???? ???????c x??a th??nh c??ng.");
        System.out.print("B???n c?? mu???n ti???p t???c x??a (1/2):");
        int confirm = scanner.nextInt();
        if (confirm == 1) {
            deleteById();
        } else if (confirm == 2) {
            adminChoiceMenu();
        }
    }

    private static void createNewEmployee() {
        logWrite("Admin ???? l???a ch???n menu th??m m???i nh??n vi??n");
        System.out.println("L???a ch???n vai tr??:");
        System.out.println("Nh??n vi??n ch??nh th???c/part-time (1/2):");
        int role = getRole();

        scanner.nextLine();
        System.out.println("Nh???p m?? nh??n vi??n: ");
        String id = getId();

        System.out.println("Nh???p t??n nh??n vi??n: ");
        String name = scanner.nextLine();

        System.out.println("Ng??y sinh (dd/mm/yyyy): ");
        String dateOfBirth = checkDateValidate();

        System.out.println("Nh???p s??? ??i???n tho???i: ");
        String phoneNumber = checkPhoneValidate();

        System.out.println("Nh???p ?????a ch???: ");
        String address = scanner.nextLine();

        System.out.println("Nh???p email: ");
        String email = checkEmailValidate();
        if (role == 1) {
            System.out.print("Nh???p l????ng c?? b???n: ");
            int basicSalary = scanner.nextInt();
            System.out.print("Nh???p l????ng th?????ng th??ng n??y: ");
            int bonus = scanner.nextInt();
            System.out.print("Nh???p s??? ti???n b??? ph???t th??ng n??y: ");
            int fine = scanner.nextInt();
            createNewFulltimeEmployee(id, name, dateOfBirth, phoneNumber, address, email, basicSalary, bonus, fine);
            createUserAccount(id, name, dateOfBirth);
        } else if (role == 2) {
            System.out.println("Nh???p s??? gi??? l??m: ");
            double workedTime = scanner.nextDouble();
            createNewParttimeEmployee(id, name, dateOfBirth, phoneNumber, address, email, workedTime);
            createUserAccount(id, name, dateOfBirth);
        }
        System.out.print("B???n c?? mu???n th??m nh??n vi??n m???i (1/2):");
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
                System.out.println("ID n??y ???? t???n t???i, m???i nh???p l???i ID kh??c.");

            }
        } while (checkIdValid != 0);
        return id;
    }

    private static int getRole() {
        int role;
        do {
            role = scanner.nextInt();

            if (role != 1 && role != 2) {
                System.out.println("Gi?? tr??? kh??ng h???p l???, m???i nh???p l???i:");
                logWrite("Nh???p vai tr?? sai.");
            }
        } while (role != 1 && role != 2);
        return role;
    }

    private static void createNewParttimeEmployee(String id, String name, String dateOfBirth, String phoneNumber, String address, String email, double workedTime) {
        Employee employeeInput = new PartTimeEmployee(id, name, dateOfBirth, phoneNumber, address, email, workedTime);
        management.addEmployee(employeeInput);
        System.out.println("???? th??m m???i nh??n vi??n " + name + " v??o v??? tr?? nh??n vi??n part-time");
        logWrite("Admin ???? th??m m???i nh??n vi??n part-time.");
    }

    private static void createNewFulltimeEmployee(String id, String name, String dateOfBirth, String phoneNumber, String address, String email, int basicSalary, int bonus, int fine) {
        Employee employeeInput = new FullTimeEmployee(id, name, dateOfBirth, phoneNumber, address, email, basicSalary, bonus, fine);
        management.addEmployee(employeeInput);
        System.out.println("???? th??m nh??n vi??n " + name + " v??o v??? tr?? nh??n vi??n ch??nh th???c.");
        logWrite("Admin ???? th??m m???i nh??n vi??n ch??nh th???c.");
    }

    private static void createUserAccount(String id, String name, String dateOfBirth) //create new user account
    {
        String username = user.employeeAccountGeneration(name, dateOfBirth);
        String password = dateOfBirth.replaceAll("/", "");
        UsersAccount userAccount = new UsersAccount(id, username, password);
        user.setUserAccount(userAccount);
        System.out.println("???? t???o th??nh c??ng t??i kho???n cho nh??n vi??n c?? ID '" + id + "'");
        logWrite("Admin ???? t???o m???i th??nh c??ng t??i kho???n cho nh??n vi??n c?? ID '" + id + "'");
    }

    private static void displayMenu() //display menu
    {
        logWrite("Admin ???? truy c???p v??o menu hi???n th???.");
        int displayChoice;
        do {
            System.out.println("""
                    ----------------------------------------------------
                    |   1. Hi???n th??? to??n b??? nh??n vi??n                  |
                    |   2. Hi???n th??? to??n b??? nh??n vi??n ch??nh th???c       |
                    |   3. Hi???n th??? to??n b??? nh??n vi??n part-time        |
                    |   4. Tr??? v??? menu tr?????c                           |
                    |   5. Tho??t kh???i ch????ng tr??nh                     |
                    ----------------------------------------------------
                    """);
            System.out.print("M???i nh???p l???a ch???n: ");
            displayChoice = scanner.nextInt();
            switch (displayChoice) {
                case 1 -> //display all
                {
                    if (management != null) {
                        management.display();
                        logWrite("Admin ???? l???a ch???n hi???n th??? to??n b??? nh??n vi??n.");
                    } else {
                        System.out.println("Kh??ng c?? nh??n vi??n n??o trong danh s??ch");
                        logWrite("Kh??ng c?? nh??n vi??n n??o trong danh s??ch");
                    }
                }
                case 2 -> //display fulltime employee
                {
                    if (management != null) {
                        management.fulltimeEmployeeDisplay();
                        logWrite("Admin ???? l???a ch???n hi???n th??? to??n b??? nh??n vi??n ch??nh th???c.");
                    } else {
                        System.out.println("Kh??ng c?? nh??n vi??n n??o trong danh s??ch");
                        logWrite("Kh??ng c?? nh??n vi??n n??o trong danh s??ch");
                    }
                }
                case 3 -> //display parttime employee
                {
                    if (management != null) {
                        management.parttimeEmployeeDisplay();
                        logWrite("Admin ???? l???a ch???n hi???n th??? to??n b??? nh??n vi??n b??n th???i gian.");
                    } else {
                        System.out.println("Kh??ng c?? nh??n vi??n n??o trong danh s??ch");
                        logWrite("Kh??ng c?? nh??n vi??n n??o trong danh s??ch");
                    }
                }
                case 4 -> adminChoiceMenu();//return admin choice menu
                case 5 -> //exit
                {
                    logWrite("Admin ???? tho??t kh???i ch????ng tr??nh");
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
            logWrite("Nh???p v??o ng??y sinh kh??ng h???p l???.");
            System.out.println("Ng??y sinh kh??ng h???p l???, m???i nh???p l???i (dd/mm/yyyy): ");
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
            logWrite("Nh???p v??o s??? ??i???n tho???i kh??ng h???p l???.");
            System.out.println("S??? ??i???n tho???i kh??ng h???p l???, m???i nh???p l???i: ");
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
            logWrite("Nh???p v??o ?????a ch??? email kh??ng h???p l???.");
            System.out.println("Email kh??ng h???p l???, m???i nh???p l???i (xxx@xxx.com): ");
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
        logWrite("???? kh???i t???o th??nh c??ng t??i kho???n admin.");
        Employee test1 = new FullTimeEmployee("1", "test", "01/01/1991", "0xxxxxxxx", "test@gmail.com",
                "email@gmail.com", 10000000, 1000000, 500000);
        Employee test2 = new PartTimeEmployee("2", "test2", "02/02/1991", "012345xx", "test2@gmail.com",
                "email2@gmail.com", 15.5);
        management.addEmployee(test1);
        management.addEmployee(test2);
        createUserAccount("1", "test", "01/01/1991");
        createUserAccount("2", "test2", "02/02/1991");
        logWrite("???? kh???i t???o th??nh c??ng default data.");
    }//create default data
}
