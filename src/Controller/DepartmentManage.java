/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.Validation;
import DAO.EmployeeDAO;
import Entity.Department;
import Entity.EmpDeptDTO;
import Entity.Employee;
import Entity.HourlyEmployee;
import Entity.SalariedEmployee;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DepartmentManage {

    private static Validation validate = new Validation();
    private static List<Employee> empList = new ArrayList<>();
    private static EmployeeDAO empDAO = new EmployeeDAO();
    private static List<EmpDeptDTO> edDTOList = new ArrayList<>();
    private static List<String> deptNameList = new ArrayList<>();

    public static void main(String[] args) {
        // load employee from file
        loadEmployee();
        try {
            while (true) {
                // main menu
                switch (mainMenuOption()) {
                    // add new employee
                    case 1:
                        addNewEmployee();// display employees list    
                        break;
                    case 2: {
                        System.out.println("List of All Employee");
                        System.out.println("--------------");
                        List<Employee> emps = empDAO.getAllEmployee();
                        displayList(emps);
                        break;
                    }
                    // Classify employees
                    case 3: {
                        classifyEmployee();
                        break;
                    }
                    // Employee search
                    case 4: {
                        // small menu contain search name and student name
                        search();
                        break;
                    }

                    // Report
                    case 5: {
                        // display Map and number of employee;
                        report();
                        break;
                    }

                    // Exit
                    case 6:
                        System.out.println("Program closed !!");
                        return;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static void loadEmployee() {
        try {
            empList = empDAO.getAllEmployee();
            edDTOList = empDAO.getAllEmpDept();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    private static int checkDupSSN() {
        int existed;
        Integer SSN = 0;
        do {
            existed = 0;
            System.out.print("Input employee SSN :");
            SSN = validate.checkInputInteger("SSN must be number!!");
            for (Employee e : empList) {
                if (e.getSsn() == SSN) {
                    System.err.println("SSN already exist !!");
                    existed++;
                }
            }
        } while (existed != 0);
        return SSN;
    }

    private static String checkDupPhone() {
        int existed = 0;
        String phone = "";
        do {
            phone = validate.checkInputPhone("Phone number must be all digit  !!");
            for (Employee e : empList) {
                if (e.getPhone().equalsIgnoreCase(phone)) {
                    System.err.println("Phone already exist !!");
                    existed++;
                }
            }
        } while (existed != 0);
        return phone;
    }

    private static String checkDupEmail() {
        int existed = 0;
        String email = "";
        do {
            email = validate.checkInputEmail();
            for (Employee e : empList) {
                if (e.getEmail().equalsIgnoreCase(email)) {
                    System.out.println("Email already exist !!");
                    existed++;
                }
            }
        } while (existed != 0);
        return email;
    }

    private static int mainMenuOption() {
        System.out.println("******MENU*******");
        System.out.println("1.Add new Employee");
        System.out.println("2.Display Employee");
        System.out.println("3.Classify Employee");
        System.out.println("4.Employee Search");
        System.out.println("5.Report");
        System.out.println("6.Exit Program");
        System.out.println("******************");
        System.out.print("Please select an option:");
        int option = validate.checkIntegerLimit(1, 6, "Choice must be from 1-6 !!");
        return option;
    }

    // 1.add new employee
    private static void addNewEmployee() {
        EmpDeptDTO emp;
        System.out.println("====ADD NEW EMPLOYEE====");
        int SSN = checkDupSSN();
        System.out.print("Input employee first name:");
        String firstName = validate.checkInputEmpty();
        System.out.print("Input employee last name:");
        String lastName = validate.checkInputEmpty();
        String birthDate = validate.checkInputBirthDate("Birth date must be dd-MM-yyyy");
        String phoneNumb = checkDupPhone();
        String email = checkDupEmail();
        System.out.print("Input SalariedEmployee Type (Y/N):");
        boolean salaryType = validate.checkYNOption();
        // input hourly employee
        if (!salaryType) {
            System.out.print("Input rate :");
            double hourlyRate = validate.checkInputDouble("Rate must be a number !!");
            System.out.print("Input working hours:");
            double workingHours = validate.checkInputDouble("Working hours must be a number !!");
            emp = new EmpDeptDTO(SSN, firstName, lastName, birthDate, phoneNumb, email, hourlyRate, workingHours);
            emp.setEmpType(2);
        } else {
            // input salary employee
            System.out.print("Input commissionRate:");
            double commissionRate = validate.checkInputDouble("Commission rate must be number !!");
            System.out.print("Input gross sale:");
            double grossSale = validate.checkInputDouble("Gross sale must be a number !!");
            System.out.print("Input basic salary:");
            double basicSalary = validate.checkInputDouble("Salary must be a number !!");
            emp = new EmpDeptDTO(SSN, firstName, lastName, birthDate, phoneNumb, email, commissionRate, grossSale, basicSalary);
            emp.setEmpType(1);
        }
        // input department
        System.out.print("Input employee department name:");
        String deptName = validate.checkInputEmpty();
        if (addEmployee(emp, deptName)) {
            System.out.println("ADD SUCESSFUL !!!");
        } else {
            System.out.println("Exception when insert !!");
        }

    }

    // add new employee
    private static boolean addEmployee(EmpDeptDTO e, String deptName) {
            boolean deptExist = false;
            int deptID=1;
            try {
            List<EmpDeptDTO> deptList = empDAO.getAllEmpDept();
                for (EmpDeptDTO ed : deptList) {
                    if (ed.getDepartmentName().equalsIgnoreCase(deptName)) {
                        deptExist = true;
                        break;
                    }
                }
                if (!deptExist) {
                    if (empDAO.addNewDept(deptName)) {
                        deptID = empDAO.getNumbOfDepartment();
                        deptID++;
                        System.out.println("New department added !!");
                    } else {
                        System.out.println("Department add failed !!");
                    }
                }
                deptID  = empDAO.getDepartmentID(deptName);
                e.setDepartmentID(deptID);            
                e.setDepartmentName(deptName);
                //add emp
                if (e.getEmpType() == 1) {
                    empDAO.insertSalariedEmployee(e);
                } else {
                    empDAO.insertHourlyEmployee(e);
                }
                return true;
            } catch (SQLException ex) {
                System.out.println("SQL Exception : Insert Employee Fail !!" + ex);
            }
            return false;
        
    }

    // 2. display employee in list
    private static void displayList(List<Employee> eList) {
        for (Employee e : eList) {
            System.out.println(e.toString());
        }
    }



    // 3.Classify employee
    // The last for loop illustrates how to find out specific class for each object
    private static void classifyEmployee() {
        while (true) {
            try {
                System.out.println("\n====ClassifyEmployee===");
                switch (optionDeptEmp()) {
                    case 1:
                        List<EmpDeptDTO> empDept = empDAO.getAllEmpDept();
                        Map<String, List<EmpDeptDTO>> empByDept = groupEmptByDeptName(empDept);
                        empByDept.forEach((k, v) -> {
                            System.out.println("Department " + k + ":");
                            v.forEach(System.out::println);
                            System.out.println("------------------\n");
                        });
                        break;
                    case 2:
                        List<Employee> hourlyList = empDAO.getHourlyEmployee();
                        List<Employee> salariedList = empDAO.getSalariedEmployee();
                        System.out.println("Hourly Employee");
                        System.out.println("--------------");
                        displayList(hourlyList);
                        System.out.println("\nSalaried Employee");
                        System.out.println("--------------");
                        displayList(salariedList);
                        break;
                    case 3:
                        System.out.println("--------------");
                        System.out.println("Classify Closed!");
                        return;
                }

            } catch (SQLException ex) {
                System.out.println("Can't get data !!");
            }
        }
    }

    public static Map<String, List<EmpDeptDTO>> groupEmptByDeptName(List<EmpDeptDTO> empDeptDTOs) {
        // TODO: Implementation
        String deptName = "";
        Map<String, List<EmpDeptDTO>> deptGroup = new HashMap<>();
        for (int i = 0; i < empDeptDTOs.size(); i++) {
            deptName = empDeptDTOs.get(i).getDepartmentName();
            List<EmpDeptDTO> empDeptList = deptGroup.get(deptName);
            if (empDeptList == null) {
                empDeptList = new ArrayList<>();

            }
            empDeptList.add(empDeptDTOs.get(i));
            deptGroup.put(deptName, empDeptList);
        }
        return deptGroup;
    }

    // 4.Search by deptName
    // search by employee, dept name
    private static void search() {
        while (true) {
            System.out.println("====Search===");
            switch (optionDeptEmp()) {
                case 1:
                    runDepartmentSeacrch();
                    break;
                case 2:
                    runEmpSearch();
                    break;
                case 3:
                    System.out.println("--------------");
                    System.out.println("Search Closed!");
                    return;
            }
        }

    }

    private static int optionDeptEmp() {
        System.out.println("1.Department");
        System.out.println("2.Employee");
        System.out.println("3.Exit");
        System.out.print("Select one option :");
        int option = validate.checkIntegerLimit(1, 3, "Please choose 1-3 :");
        return option;
    }

    // Employee Search
    private static void runEmpSearch() {
        try {
            System.out.println("-------Employee Search--------");
            System.out.print("Enter Employee Name :");
            String empName = validate.checkInputEmpty();
            List<Employee> foundedEmp = empDAO.getEmployeeByName(empName);
            if (!foundedEmp.isEmpty()) {
                for (Employee e : foundedEmp) {
                    System.out.println(e.toString());
                }
            } else {
                System.out.println("EMPLOYEE NOT FOUND !!");
            }
        } catch (SQLException ex) {
            System.out.println("SQL error !!");
        }
    }

    // Department Search
    private static void runDepartmentSeacrch() {
        System.out.println("-------Department Search--------");
        System.out.print("Enter Deparment Name :");
        try {
            String deptName = validate.checkInputEmpty();
            List<Employee> departmentList = empDAO.getDeptByName(deptName);
            if (!departmentList.isEmpty()) {
                System.out.println("Department Name contains " + deptName + ":");
                for (Employee e : departmentList) {
                    System.out.println(e.toString());
                }
            } else {
                System.out.println("DEPARTMENT NOT FOUND !");
            }
            System.out.println("--------------");
        } catch (SQLException ex) {
            System.err.println("Department search can't exe");
        }
    }

    // 5.Report
    private static void report() {
        System.out.println("=====Report=====");
        try {
            List<EmpDeptDTO> empDept = empDAO.getAllEmpDept();
            Map<String, List<EmpDeptDTO>> empByDept = groupEmptByDeptName(empDept);
            empByDept.forEach((k, v) -> {
                System.out.println(k);
                System.out.println("Department " + k + ":");
                v.forEach(System.out::println);
                System.out.println("Total " + k + " Employee:" + v.size());
                System.out.println("------------------\n");
            });
            System.out.println("=================");
        } catch (SQLException ex) {
            System.out.println("SQL Error on Report !");
        }
    }
}
