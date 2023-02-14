package model;

import java.io.Serializable;

public abstract class Employee implements Serializable {
    private String id;
    private String name;
    private String phoneNumbers;
    private String address;
    private String email;

    public Employee() {
    }

    public Employee(String id, String name, String phoneNumbers, String address, String email) {
        this.id = id;
        this.name = name;
        this.phoneNumbers = phoneNumbers;
        this.address = address;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(String phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "NhanVien{" +
                "maNv='" + id + '\'' +
                ", ten='" + name + '\'' +
                ", sdt='" + phoneNumbers + '\'' +
                ", diaChi='" + address + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
