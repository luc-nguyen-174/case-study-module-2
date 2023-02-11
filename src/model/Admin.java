package model;

import storage.DocGhiAdmin;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Admin implements Serializable {
    private String username;
    private String password;

    public Admin() {
    }

    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public static void defaultAdmin(){
        List<StringBuilder> adminList = new ArrayList<>();
        StringBuilder adminAcc = new StringBuilder();
        adminAcc.append("admin").append("|").append("admin");
        adminList.add(adminAcc);
        new DocGhiAdmin().writeFile(adminList, "admin.bin");
    }

    public static void createAdmin(){
        List<StringBuilder> adminList = new ArrayList<>();
        StringBuilder adminAcc = new StringBuilder();
        String username ="admin";
        String password ="admin";
        adminAcc.append(username).append("|").append(password).append("\n");
        adminList.add(adminAcc);
        new DocGhiAdmin().writeFile(adminList,"admin.bin");
    }
}
