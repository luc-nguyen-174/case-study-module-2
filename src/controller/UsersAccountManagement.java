package controller;

import model.UsersAccount;
import storage.ReadAndWrite;

import java.util.List;

public class UsersAccountManagement {
    List<UsersAccount> usersAccountList;

    public UsersAccountManagement(List<UsersAccount> usersAccountList) {
        this.usersAccountList = usersAccountList;
    }

    public List<UsersAccount> getUsersAccountList() {
        return usersAccountList;
    }

    public void setUsersAccountList(List<UsersAccount> usersAccountList) {
        this.usersAccountList = usersAccountList;
    }

    public String employeeAccountGeneration(String name, String dateOfBirth) {
        name = name.toLowerCase().replaceAll("\\s", "");
        dateOfBirth = dateOfBirth.replaceAll("/", "");
        String[] arr = {"á", "à", "ả", "ã", "ạ", "â", "ấ", "ầ", "ẩ", "ẫ", "ậ", "ă", "ắ", "ằ", "ẳ", "ẵ", "ặ",
                "đ", "é", "è", "ẻ", "ẽ", "ẹ", "ê", "ế", "ề", "ể", "ễ", "ệ",
                "í", "ì", "ỉ", "ĩ", "ị",
                "ó", "ò", "ỏ", "õ", "ọ", "ô", "ố", "ồ", "ổ", "ỗ", "ộ", "ơ", "ớ", "ờ", "ở", "ỡ", "ợ",
                "ú", "ù", "ủ", "ũ", "ụ", "ư", "ứ", "ừ", "ử", "ữ", "ự",
                "ý", "ỳ", "ỷ", "ỹ", "ỵ"};
        String[] arrReplace = {"a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a",
                "d", "e", "e", "e", "e", "e", "e", "e", "e", "e", "e", "e",
                "i", "i", "i", "i", "i",
                "o", "o", "o", "o", "o", "o", "o", "o", "o", "o", "o", "o", "o", "o", "o", "o", "o",
                "u", "u", "u", "u", "u", "u", "u", "u", "u", "u", "u",
                "y", "y", "y", "y", "y"};
        for (int i = 0; i < arr.length; i++) {
            name = name.replaceAll(arr[i], arrReplace[i]);
        }
        return name + dateOfBirth;
    }

    public void setUserAccount(UsersAccount account) {
        usersAccountList.add(account);
        ReadAndWrite.getInstance().writeFile(usersAccountList, "src/database/users.bin");
    }

    public void removeUserAccount(String id) {
        for (int i = 0; i < usersAccountList.size(); i++) {
            if (usersAccountList.get(i).getId().equals(id)) {
                usersAccountList.remove(i);
            }
        }
        ReadAndWrite.getInstance().writeFile(usersAccountList,"src/database/users.bin");
    }
    public void deleteAllAccount(){
        usersAccountList.clear();
        ReadAndWrite.getInstance().writeFile(usersAccountList,"src/database/users.bin");
    }
    public void editAccount(UsersAccount account,String newPassword){
        account.setPassword(newPassword);
        ReadAndWrite.getInstance().writeFile(usersAccountList,"src/database/users.bin");
    }
}
