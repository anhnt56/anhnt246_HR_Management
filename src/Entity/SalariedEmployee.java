package Entity;

public class SalariedEmployee extends Employee {
	/**
	 * 
	 */
	private double commissionRate;
	private double grossSale;
	private double basicSalary;

	
	
	public SalariedEmployee() {
		// TODO Auto-generated constructor stub
	}
	


	public SalariedEmployee(int ssn, String firstName, String lastName, String birthDate, String phone, String email,double commissionRate, double grossSale, double basicSalary) {
		super(ssn, firstName, lastName, birthDate, phone, email);
		// TODO Auto-generated constructor stub	
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
	@Override
	public String toString() {
		        return String.format("%s%s: %,.2f%s: %.2f%s: $%,.2f%s: $%,.2f",
		            super.toString(),
		           ", grossSales", getGrossSale(),
		          ", commissionRate", getCommissionRate(),
		          ", baseSalary", getBasicSalary(),
		          ", totalSalary", getPaymentAmount(),""
		          ).concat("};");
	}
	
	@Override
	void display() {
		// TODO Auto-generated method stub
		System.out.println(this.toString());
	}
	@Override
	public double getPaymentAmount() {
		return (commissionRate*grossSale) + basicSalary;
	}

	@Override
	public int getEmpType() {
		// TODO Auto-generated method stub
		return 1;
	}
	
	
	
}
