package DAO;

import Entity.EmpDeptDTO;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;

import Util.ConnectionUtil;
import Entity.Employee;
import Entity.HourlyEmployee;
import Entity.SalariedEmployee;
import java.sql.SQLException;

public class EmployeeDAO {

    static final String SELECT_ALL_EMP = "SELECT * FROM Employee";
    static final String SELECT_ALL_EMPDEPT = "SELECT * FROM Employee e INNER JOIN Department d ON e.departmentID = d.departmentID ";
    static final String SELECT_DEPT_BY_NAME = "SELECT * FROM Employee e INNER JOIN Department d ON e.departmentID = d.departmentID WHERE DepartmentName LIKE ? ";
    static final String SELECT_EMP_BY_NAME = "SELECT * FROM Employee WHERE fullName LIKE ? ";
    static final String SELECT_TYPE_EMP = "SELECT * FROM Employee WHERE empType = ?";
    static final String SELECT_DEPTID_BY_NAME = "SELECT DepartmentID FROM Department WHERE DepartmentName = ?";
    static final String INSERT_SALARY_EMP = "Insert into Employee(SSN,FirstName,LastName,Birthdate,Phone,Email,EmpType,CommissionRate,GrossSales,BasicSalary,DepartmentID,fullName)VALUES(?,?,?,?,?,?,?,?,?,?,?,?);";
    static final String INSERT_HOURLY_EMP = "Insert into Employee(SSN,FirstName,LastName,Birthdate,Phone,Email,EmpType,Rate,WorkingHours,DepartmentID,fullName)VALUES(?,?,?,?,?,?,?,?,?,?,?);";
    static final String INSERT_DEPT = "INSERT INTO Department(DepartmentName) VALUES (?)";
    static final String COUNT_DEPT = "SELECT count(*) as numbOfDept FROM Department";

    public List<Employee> getAllEmployee() throws SQLException {
        List<Employee> empList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(SELECT_ALL_EMP);
            while (rs.next()) { // When rs.next() == FALSE => End loop
                int SSN = Integer.parseInt(rs.getString("SSN"));
                String firstName = rs.getString("FirstName");
                String lastName = rs.getString("LastName");
                String birthDate = rs.getString("BirthDate");
                String phone = rs.getString("Phone");
                String email = rs.getString("Email");
                if (rs.getInt("empType") == 1) {
                    double commissionRate = rs.getFloat("CommissionRate");
                    double grossSales = rs.getFloat("GrossSales");
                    double basicSalary = rs.getFloat("BasicSalary");
                    empList.add(new SalariedEmployee(SSN, firstName, lastName, birthDate, phone, email, commissionRate, grossSales, basicSalary));
                } else {
                    double rate = rs.getFloat("Rate");
                    double workingHours = rs.getFloat("WorkingHours");
                    empList.add(new HourlyEmployee(SSN, firstName, lastName, birthDate, phone, email, rate, workingHours));
                }
            }
        }
        return empList;
    }

    public List<EmpDeptDTO> getAllEmpDept() throws SQLException {
        List<EmpDeptDTO> edDTOList = new ArrayList<>();
        EmpDeptDTO e;
        try (Connection connection = ConnectionUtil.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(SELECT_ALL_EMPDEPT);
            while (rs.next()) { // When rs.next() == FALSE => End loop
                int SSN = Integer.parseInt(rs.getString("SSN"));
                String firstName = rs.getString("FirstName");
                String lastName = rs.getString("LastName");
                String birthDate = rs.getString("BirthDate");
                String phone = rs.getString("Phone");
                String email = rs.getString("Email");
                String deptName = rs.getString("DepartmentName");
                if (rs.getInt("empType") == 1) {
                    double commissionRate = rs.getFloat("CommissionRate");
                    double grossSales = rs.getFloat("GrossSales");
                    double basicSalary = rs.getFloat("BasicSalary");
                    e = new EmpDeptDTO(SSN, firstName, lastName, birthDate, phone, email, commissionRate, grossSales, basicSalary);
                    e.setEmpType(1);
                } else {
                    double rate = rs.getFloat("Rate");
                    double workingHours = rs.getFloat("WorkingHours");
                    e = new EmpDeptDTO(SSN, firstName, lastName, birthDate, phone, email, rate, workingHours);
                    e.setEmpType(2);

                }
                e.setDepartmentName(deptName);
                edDTOList.add(e);
                
            }
        }
        return edDTOList;
    }

    public List<Employee> getHourlyEmployee() throws SQLException {
        List<Employee> empHourlyList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SELECT_TYPE_EMP);
            statement.setInt(1, 2);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) { // When rs.next() == FALSE => End loop
                int SSN = rs.getInt("SSN");
                String firstName = rs.getString("FirstName");
                String lastName = rs.getString("LastName");
                String birthDate = rs.getString("BirthDate");
                String phone = rs.getString("Phone");
                String email = rs.getString("Email");
                double rate = rs.getFloat("Rate");
                double workingHours = rs.getFloat("WorkingHours");
                empHourlyList.add(new HourlyEmployee(SSN, firstName, lastName, birthDate, phone, email, rate, workingHours));
            }
        }
        return empHourlyList;
    }

    public List<Employee> getSalariedEmployee() throws SQLException {
        List<Employee> empSalaryList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SELECT_TYPE_EMP);
            statement.setInt(1, 1);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) { // When rs.next() == FALSE => End loop
                int SSN = Integer.parseInt(rs.getString("SSN"));
                String firstName = rs.getString("FirstName");
                String lastName = rs.getString("LastName");
                String birthDate = rs.getString("BirthDate");
                String phone = rs.getString("Phone");
                String email = rs.getString("Email");
                double commissionRate = rs.getFloat("CommissionRate");
                double grossSales = rs.getFloat("GrossSales");
                double basicSalary = rs.getFloat("BasicSalary");
                empSalaryList.add(new SalariedEmployee(SSN, firstName, lastName, birthDate, phone, email, commissionRate, grossSales, basicSalary));
            }
        }
        return empSalaryList;
    }

    public List<Employee> getEmployeeByName(String name) throws SQLException {
        List<Employee> empList = new ArrayList<>();
        Employee e;
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SELECT_EMP_BY_NAME);
            statement.setString(1, "%" + name + "%");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) { // When rs.next() == FALSE => End loop
                int SSN = rs.getInt("SSN");
                String firstName = rs.getString("FirstName");
                String lastName = rs.getString("LastName");
                String birthDate = rs.getString("BirthDate");
                String phone = rs.getString("Phone");
                String email = rs.getString("Email");
                if (rs.getInt("empType") == 1) {
                    double commissionRate = rs.getFloat("CommissionRate");
                    double grossSales = rs.getFloat("GrossSales");
                    double basicSalary = rs.getFloat("BasicSalary");
                    e = new SalariedEmployee(SSN, firstName, lastName, birthDate, phone, email, commissionRate, grossSales, basicSalary);
                } else {
                    double rate = rs.getFloat("Rate");
                    double workingHours = rs.getFloat("WorkingHours");
                    e = new HourlyEmployee(SSN, firstName, lastName, birthDate, phone, email, rate, workingHours);
                }
                empList.add(e);
            }
        }
        return empList;
    }

    public List<Employee> getDeptByName(String deptName) throws SQLException {
        List<Employee> empList = new ArrayList<>();
        Employee e;
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SELECT_DEPT_BY_NAME);
            statement.setString(1, "%" + deptName + "%");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) { // When rs.next() == FALSE => End loop
                int SSN = rs.getInt("SSN");
                String firstName = rs.getString("FirstName");
                String lastName = rs.getString("LastName");
                String birthDate = rs.getString("BirthDate");
                String phone = rs.getString("Phone");
                String email = rs.getString("Email");
                if (rs.getInt("empType") == 1) {
                    double commissionRate = rs.getFloat("CommissionRate");
                    double grossSales = rs.getFloat("GrossSales");
                    double basicSalary = rs.getFloat("BasicSalary");
                    e = new SalariedEmployee(SSN, firstName, lastName, birthDate, phone, email, commissionRate, grossSales, basicSalary);
                } else {
                    double rate = rs.getFloat("Rate");
                    double workingHours = rs.getFloat("WorkingHours");
                    e = new HourlyEmployee(SSN, firstName, lastName, birthDate, phone, email, rate, workingHours);
                }
                empList.add(e);
            }
        }
        return empList;
    }

    public boolean insertSalariedEmployee(EmpDeptDTO emp) throws SQLException {
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(INSERT_SALARY_EMP);
            statement.setInt(1, emp.getSsn());
            statement.setString(2, emp.getFirstName());
            statement.setString(3, emp.getLastName());
            statement.setString(4, emp.getBirthDate());
            statement.setString(5, emp.getPhone());
            statement.setString(6, emp.getEmail());
            statement.setInt(7, emp.getEmpType());
            statement.setFloat(8, (float) emp.getCommissionRate());
            statement.setFloat(9, (float) emp.getGrossSale());
            statement.setFloat(10, (float) emp.getBasicSalary());
            statement.setInt(11, emp.getDepartmentID());
            statement.setString(12, emp.getFullName());
            int rowAffected = statement.executeUpdate();
            return rowAffected == 1;
        }
    }

    public int getDepartmentID(String deptName) throws SQLException {
        try (Connection connection = ConnectionUtil.getConnection()) {
            int deptID = 0;
            PreparedStatement statement = connection.prepareStatement(SELECT_DEPTID_BY_NAME);
            statement.setString(1,deptName);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) { // When rs.next() == FALSE => End loop
                deptID = rs.getInt("DepartmentID");
            }
            return deptID;
        }
    }
    public int getNumbOfDepartment() throws SQLException {
        int countDept = 1;
        try (Connection connection = ConnectionUtil.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(COUNT_DEPT);
            while (rs.next()) { // When rs.next() == FALSE => End loop
                countDept = rs.getInt("numbOfDept");
            }
        }
        return countDept;
    }
    
    public boolean insertHourlyEmployee(EmpDeptDTO emp) throws SQLException {
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(INSERT_HOURLY_EMP);
            statement.setInt(1, emp.getSsn());
            statement.setString(2, emp.getFirstName());
            statement.setString(3, emp.getLastName());
            statement.setString(4, emp.getBirthDate());
            statement.setString(5, emp.getPhone());
            statement.setString(6, emp.getEmail());
            statement.setInt(7, emp.getEmpType());
            statement.setFloat(8, (float) emp.getRate());
            statement.setFloat(9, (float) emp.getWorkingHours());
            statement.setInt(10, emp.getDepartmentID());
            statement.setString(11, emp.getFullName());
            int rowAffected = statement.executeUpdate();
            return rowAffected == 1;
        }
    }
    
    public boolean addNewDept(String departmentName) throws SQLException {
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(INSERT_DEPT);
            statement.setString(1, departmentName);
            int rowAffected = statement.executeUpdate();
            return rowAffected == 1;
        }
    }

}
