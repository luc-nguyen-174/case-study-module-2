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
        return "NhanVienParttime{" +
                "gioLam=" + workTimes +
                "} " + super.toString();
    }

    @Override
    public int salaryCount() {
        return (int) (workTimes * 100000);
    }
}
