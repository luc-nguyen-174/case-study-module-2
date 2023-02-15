package controller;

import model.Employee;
import model.FullTimeEmployee;
import model.PartTimeEmployee;
import storage.IReadAndWrite;
import storage.ReadAndWrite;

import java.io.Serializable;
import java.util.List;

public class Management implements Serializable {
    List<Employee> employeeList;

    public Management(List<Employee> employeesManagement) {
        this.employeeList = employeesManagement;
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Employee> nhanViens) {
        this.employeeList = nhanViens;
    }
    public void addEmployee(Employee nhanVien) {
        employeeList.add(nhanVien);
        ReadAndWrite.getInstance().writeFile(employeeList,"management.bin");
    }
    public void removeEmployee(String id){
        employeeList.remove(Integer.parseInt(id));
        ReadAndWrite.getInstance().writeFile(employeeList,"management.bin");
    }
    public void display(){
        for(Employee employee : employeeList){
            System.out.println(employee);
        }
    }
    public void fulltimeEmployeeDisplay(){
        for (Employee employee : employeeList){
            if (employee instanceof FullTimeEmployee){
                System.out.println(employee);
            }
        }
    }
    public void parttimeEmployeeDisplay(){
        for (Employee employee : employeeList){
            if (employee instanceof PartTimeEmployee){
                System.out.println(employee);
            }
        }
    }
    public void xoaTatCaNhanVien(){
        employeeList.clear();
        ReadAndWrite.getInstance().writeFile(employeeList,"management.bin");
    }

}
