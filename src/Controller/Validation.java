package Controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Validation {
    private static final Scanner sc = new Scanner(System.in);
    private static final String PHONE_VALID = "^[0-9]{7,12}$";
    private static final String EMAIL_VALID = "^[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}$";
    
    //validate integer 
    public int checkInputInteger(String mess) {
        while (true) {
            try {
                int result = Integer.parseInt(sc.nextLine());
                return result;
            } catch (NumberFormatException ex) {
                System.err.println(mess);
                System.out.print("Input again:");
            }
        }
    }
    
    //check input double
    public double checkInputDouble(String mess) {
        while (true) {
            try {
                double result = Double.parseDouble(sc.nextLine());
                return result;
            } catch (NumberFormatException ex) {
                System.err.println(mess);
                System.out.print("Input again:");
            }
        }
    }
    
    // validate limit
    public  int checkIntegerLimit(int min, int max,String mess) {
        while (true) {
            try {
                int n = Integer.parseInt(sc.nextLine());
                if (n < min || n > max) {
                    throw new NumberFormatException();
                }
                return n;
            } catch (NumberFormatException e) {
                System.err.println(mess);
                System.out.print("Input again:");
            }
        }
    }
    
    // validate input empty
    public String checkInputEmpty() {
        //loop until user input true value
        while (true) {
            String result = sc.nextLine().trim();
            if (result.isEmpty()) {
                System.err.println("Input can't be empty !!");
            } else {
                return result;
            }
        }
    }
    
    // validate input date with format
	public String checkInputBirthDate(String mess) {
        while (true) {
            try {
            	System.out.print("Input employee birthdate:");
                String result = sc.nextLine().trim();
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                Date date = format.parse(result);
                if (result.equalsIgnoreCase(format.format(date))) {
                    return result;
                } else {
                    System.out.print("Input again:");
                }
            } catch (NumberFormatException | ParseException ex) {
                System.err.println(mess);
            }
        }
    }
	
	//validate input phone number
	 public String checkInputPhone(String mess) {
	        while (true) {
	            try {
	            	System.out.print("Phone number:");
	                int phoneCheck = Integer.parseInt(sc.nextLine().trim());
	                String resultCheck = String.valueOf(phoneCheck);
	                if (!resultCheck.matches(PHONE_VALID)) {
	                    System.err.println("Phone number must contain 7-12 digits");
	                } else {
	                    return resultCheck;
	                }
	            } catch (NumberFormatException ex) {
	                System.err.println(mess);
	            }
	        }
	    }
	 
	 //validate input email 
	 public String checkInputEmail() {
	        while (true) {
	        	System.out.print("Input employee email:");
	            String emailCheck = checkInputEmpty();
	            if (!emailCheck.matches(EMAIL_VALID)) {
	                System.err.println("Email must be format ***@***.*** !!");
	                System.out.print("Email: ");
	            } else {
	                return emailCheck;
	            }
	        }
	    }
	 
	 public boolean checkYNOption() {
	        //loop until user input correct
	        while (true) {
	            String result = checkInputEmpty();
	            //return true if user input s/S
	            if (result.equalsIgnoreCase("Y")) {
	                return true;
	            }
	            //return false if user input h/H
	            if (result.equalsIgnoreCase("N")) {
	                return false;
	            }
	            System.err.println("Please input Y or N.");
	            System.out.print("Enter again: ");
	        }
	    }

	
	
}