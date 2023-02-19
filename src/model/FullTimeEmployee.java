package model;

import java.io.Serializable;

public class FullTimeEmployee extends Employee implements SalaryCount, Serializable {
    private int basicSalary;
    private int bonus;
    private int fine;

    public FullTimeEmployee() {
    }

    public FullTimeEmployee(String id,String name, String dateOfBirth, String phoneNumbers, String address, String email, int basicSalary, int bonus, int fine) {
        super(id,name, dateOfBirth, phoneNumbers, address, email);
        this.basicSalary = basicSalary;
        this.bonus = bonus;
        this.fine = fine;
    }

    public int getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(int basicSalary) {
        this.basicSalary = basicSalary;
    }

    public int getBonus() {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }

    public int getFine() {
        return fine;
    }

    public void setFine(int fine) {
        this.fine = fine;
    }

    @Override
    public String toString() {
        return super.toString() + ",\n vai tro: nhan vien chinh thuc" +
                ", luong cung: " + getBasicSalary() + "vnd" +
                ", luong thuong: " + getBonus() + "vnd" +
                ", phat " + getFine() + "vnd";
    }

    @Override
    public int salaryCount() {
        return getBasicSalary() + getBonus() - getFine();
    }
}
