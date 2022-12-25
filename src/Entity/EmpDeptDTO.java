/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

/**
 *
 * @author tunav
 */
public class EmpDeptDTO extends  Employee{

    private double rate;
    private double workingHours;
    private double commissionRate;
    private double grossSale;
    private double basicSalary;
    private String departmentName;
    private int departmentID;
    private int empType;

    public EmpDeptDTO() {
       super();
    }
    
    public EmpDeptDTO(int ssn, String firstName, String lastName, String birthDate, String phone, String email, double commissionRate, double grossSale, double basicSalary) {
        super(ssn, firstName, lastName, birthDate, phone, email);
         if (commissionRate < 0)
			 commissionRate= 0;
		 else 
			 this.commissionRate = commissionRate;
		 
		 if (grossSale < 0)
			 grossSale = 0;
		 else 
			 this.grossSale = grossSale;
		 
		 if (basicSalary < 0)
			 basicSalary = 0;
		 else 
			 this.basicSalary = basicSalary;
    }
    
    public EmpDeptDTO(int ssn, String firstName, String lastName, String birthDate, String phone, String email, double rate, double workingHours) {
        super(ssn, firstName, lastName, birthDate, phone, email);
        if (rate < 0)
			 rate= 0;
		 else 
			 this.rate = rate;
		 
		 if (workingHours < 0)
			 workingHours = 0;
		 else 
			 this.workingHours = workingHours;
    }


    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(double workingHours) {
        this.workingHours = workingHours;
    }

    public double getCommissionRate() {
        return commissionRate;
    }

    public void setCommissionRate(double commissionRate) {
        this.commissionRate = commissionRate;
    }

    public double getGrossSale() {
        return grossSale;
    }

    public void setGrossSale(double grossSale) {
        this.grossSale = grossSale;
    }

    public double getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(double basicSalary) {
        this.basicSalary = basicSalary;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public int getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(int departmentID) {
        this.departmentID = departmentID;
    }
    
    
    @Override
    public int getEmpType() {
        return this.empType; //To change body of generated methods, choose Tools | Templates.
    }

    public void setEmpType(int empType) {
        this.empType = empType;
    }

   
    

    @Override
    public String toString() {
        String display="";
        if(empType==1){
            display= String.format("%s%s: %,.2f%s: %.2f%s: $%,.2f%s: $%,.2f",
		            super.toString(),
		           ", grossSales", getGrossSale(),
		          ", commissionRate", getCommissionRate(),
		          ", baseSalary", getBasicSalary(),
		          ", totalSalary", getPaymentAmount()
		          ).concat("};");
        }else{
            display=String.format("%s%s: %,.2f%s: %,.2f%s: $%,.2f",super.toString(),", hourlyRate",getRate(),
				", workingHours",getWorkingHours(),", totalMonthSalary",getPaymentAmount()
                          ).concat("};");
        }
        return display;
    }

    @Override
    void display() {  
    }

    @Override
    public double getPaymentAmount() {
        double salary=0;
        if(this.empType==1){
            salary = (commissionRate*grossSale)+basicSalary;
        }else{
            salary = rate*workingHours;
        }
        return salary;
    }
    
    
}
