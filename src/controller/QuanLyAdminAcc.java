package controller;

import model.Admin;
import storage.DocGhiAdmin;

import java.io.Serializable;
import java.util.*;

public class QuanLyAdminAcc implements Serializable {
    List<String> adminAcc = new ArrayList<>();

    public QuanLyAdminAcc(List<String> adminAcc) {
        this.adminAcc = adminAcc;
    }

    public List<String> getAdminAcc() {
        return adminAcc;
    }

    public void setAdminAcc(List<String> adminAcc) {
        this.adminAcc = adminAcc;
    }

    public static void defaultAdmin() {
        List<String> adminList = new ArrayList<>();
        String username = "admin";
        String password = "admin";
        String adminAcc = username + "|" + password;
        adminList.add(adminAcc);
        new DocGhiAdmin().writeFile(adminList, "admin.bin");
    }

    public static void createNewAdmin(String username, String password) {
        List<String> adminList = new ArrayList<>();
        String adminAcc = username + "|" + password + "\n";
        adminList.add(adminAcc);
        new DocGhiAdmin().writeFile(adminList, "admin.bin");
    }
}
