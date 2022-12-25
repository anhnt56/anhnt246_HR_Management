package Entity;

public class HourlyEmployee extends Employee {
	/**
	 * 
	 */
	private double rate;
	private double workingHours;

	

	public HourlyEmployee() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	public HourlyEmployee(int ssn, String firstName, String lastName, String birthDate, String phone, String email,double rate, double workingHours) {
		super(ssn, firstName, lastName, birthDate, phone, email);
		// TODO Auto-generated constructor stub		
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
	
	

	@Override
	public String toString() {
		return String.format("%s%s: %,.2f%s: %,.2f%s: $%,.2f",super.toString(),", hourlyRate",getRate(),
				", workingHours",getWorkingHours(),", totalMonthSalary",getPaymentAmount(),"").concat("};");

	}

	@Override
	void display() {
		// TODO Auto-generated method stub
                System.out.println(this.toString());
	}

	@Override
	public double getPaymentAmount() {
		return workingHours*rate;
	}


	@Override
	public int getEmpType() {
		// TODO Auto-generated method stub
		return 2;
	}




}
