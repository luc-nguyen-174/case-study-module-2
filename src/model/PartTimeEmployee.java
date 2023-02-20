package model;

import java.io.Serializable;

public class PartTimeEmployee extends Employee implements SalaryCount, Serializable {
    private double workTimes;

    public PartTimeEmployee() {
    }

    public PartTimeEmployee(String id, String name, String dateOfBirth, String phoneNumbers, String address, String email, double workTimes) {
        super(id, name, dateOfBirth, phoneNumbers, address, email);
        this.workTimes = workTimes;
    }

    public double getWorkTimes() {
        return workTimes;
    }

    public void setWorkTimes(double workTimes) {
        this.workTimes = workTimes;
    }

    @Override
    public String toString() {
        return super.toString() + "\nvai trò: nhân viên bán thời gian" +
                ", giờ làm: " + workTimes + " giờ.";
    }

    @Override
    public int salaryCount() {
        return (int) (getWorkTimes() * 100000);
    }
}
