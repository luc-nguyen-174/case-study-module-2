package model;

import storage.ReadAndWrite;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AdminAccount implements Serializable {
    private String username;

    private String password;

    public AdminAccount() {
    }

    public AdminAccount(String username, String password) {
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


    @Override
    public String toString() {
        return username + "|" + password;
    }
}
