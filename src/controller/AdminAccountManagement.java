package controller;


import model.AdminAccount;
import storage.ReadAndWrite;

import java.util.*;


public class AdminAccountManagement{
    List<AdminAccount> adminAccounts = new ArrayList<AdminAccount>();

    public AdminAccountManagement(List<AdminAccount> accounts) {
        this.adminAccounts = accounts;
    }

    public List<AdminAccount> getAdminAccounts() {
        return adminAccounts;
    }

    public void setAdminAccounts(List<AdminAccount> adminAccounts) {
        this.adminAccounts = adminAccounts;
    }
    public void setAdmin(AdminAccount accounts) {
        adminAccounts.add(accounts);
        ReadAndWrite.getInstance().writeFile(adminAccounts,"admin.bin");
    }
}
