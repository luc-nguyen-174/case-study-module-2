package controller;

import model.AdminAccount;
import model.Employee;
import model.FullTimeEmployee;
import model.PartTimeEmployee;
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

    public void setEmployeeList(List<Employee> employees) {
        this.employeeList = employees;
    }
    public void addEmployee(Employee employee) {
        employeeList.add(employee);
        ReadAndWrite.getInstance().writeFile(employeeList,"src/database/management.bin");
    }

    public void removeEmployee(String id){
        for ( int i = 0; i < employeeList.size(); i++ )
            if( employeeList.get(i).getId().equals(id)){
                employeeList.remove(i);
                break;
            }else if(employeeList.size()==0){
                System.out.println("Không có thành viên nào để xóa.");
            }
        ReadAndWrite.getInstance().writeFile(employeeList,"src/database/management.bin");
    }
//    public void editEmployee(String id,){
//
//    }
    public void display(){
        for(Employee employee : employeeList){
            System.out.println(employee);
        }
    }
    public void findWithId(String id){
        for(Employee employee : employeeList){
            if ( employee.getId().equals(id)){
                System.out.println(employee);
                break;
            }
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
    public void deleteAllStaff(){
        employeeList.clear();
        ReadAndWrite.getInstance().writeFile(employeeList,"src/database/management.bin");
    }
    public void inputValidateAlert(){
        System.out.println("Bạn đã nhập sai, mời nhập lại!!");
    }

}
