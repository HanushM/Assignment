package emp.assignment;

import java.util.regex.*;
import java.util.*;
import java.io.*;
import java.sql.*;
import javax.sql.*;
import javax.sql.rowset.*;
class Age {
    public static int readAge() {
        while (true) {
            System.out.print("Enter Age:");
            try {
                int age = new Scanner(System.in).nextInt();
                if (age < 21 || age > 60) {
                    throw new AgeException("Age is invalid, Enter between 21-60");
                }
                return age;
            } catch (AgeException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("Please enter number only");
            }
        }
    }
}

class AgeException extends RuntimeException {
    AgeException() {
        super();
    }

    AgeException(String msg) {
        super(msg);
    }
}

class EmployeeName {
    public static String getName() {
        while (true) {
            System.out.print("Enter Name:");
            try {
                String name = new Scanner(System.in).nextLine();
                Pattern p1 = Pattern.compile("[A-Z][a-z]+\\s[A-Z][a-z]*");
                Matcher m1 = p1.matcher(name);
                if (!m1.matches()) {
                    throw new EmployeeNameException("Enter correct name, it should be two words and starting letter should be capital");
                }
                return name;
            } catch (InputMismatchException e) {
                System.out.println("Please enter characters only");
            } catch (EmployeeNameException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}

class EmployeeNameException extends RuntimeException {
    EmployeeNameException() {
        super();
    }

    EmployeeNameException(String msg) {
        super(msg);
    }
}

class Menu {
    private static int maxChoice;

    public static int readChoice(int max) {
        maxChoice = max;
        while (true) {
            System.out.print("Enter Choice:");
            try {
                int choice = new Scanner(System.in).nextInt();
                if (choice < 1 || choice > maxChoice) {
                    throw new InvalidChoiceException();
                }
                return choice;
            } catch (InputMismatchException e) {
                System.out.println("Please enter number only");
            } catch (InvalidChoiceException e) {
                e.displayMessage(maxChoice);
            }
        }
    }
}

class InvalidChoiceException extends RuntimeException {
    InvalidChoiceException() {
        super();
    }

    InvalidChoiceException(String msg) {
        super(msg);
    }

    public void displayMessage(int maxChoice) {
        System.out.println("Please enter the choice between 1 to " + maxChoice);
    }
}

class EmployeeID {
    public static int createEID(JdbcRowSet stmt) {
	JdbcRowSet rs = stmt;
        while (true) {
            System.out.print("Enter EmployeeID:");
            try {
                int employeeId = new Scanner(System.in).nextInt();
                stmt.setCommand("SELECT COUNT(*) FROM employees_assignment WHERE eid = ?");
		
		stmt.setInt(1, employeeId);
		stmt.execute();

                if (rs.next() && rs.getInt(1) > 0) {
                    throw new EidException("EID Already Present, please enter a correct ID.");
                }
                return employeeId;
            } catch (EidException e) {
                System.out.println(e.getMessage());
            } catch (InputMismatchException e) {
                System.out.println("Please enter numbers only");
            } catch(Exception e){
		System.out.println(e);
		}
        }
    }
}

class EidException extends RuntimeException {
    EidException() {
        super();
    }

    EidException(String msg) {
        super(msg);
    }
}

abstract class Employee implements java.io.Serializable {

    private String name;
    private int age;
    private float salary;
    private String designation;
    private String department;
    private int eid;

    static int inCount=0;
    static boolean ceoCreated=false; 

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public float getSalary() {
        return salary;
    }

    public String getDesignation() {
        return designation;
    }

    public String getDepartment() {
        return department;
    }

    public int getEid() {
        return eid;
    }

    protected void setEid(int eid) {
        this.eid = eid;
    }

    protected void setName(String name) {
        this.name = name;
    }

    protected void setAge(int age) {
        this.age = age;
    }

    protected void setSalary(float salary) {
        this.salary = salary;
    }

    protected void setDesignation(String designation) {
        this.designation = designation;
    }

    protected void setDepartment(String department) {
        this.department = department;
    }

    public static Employee createEmployee(String designation, JdbcRowSet rs ,boolean interactive) {
    while (true) {  
        try {
            if (designation.equals("Clerk") || designation.equals("Programmer") || designation.equals("Manager")) {
                if (!ceoCreated) {
                    throw new CeoNotCreatedException("First create the CEO before creating other employees.");
                }
            }
            if (designation.equals("Ceo") && ceoCreated) {
                throw new MultipleCeoCreationException("Only one instance of CEO can be created.");
            }
	
            rs.setCommand("INSERT INTO employees_assignment (eid,name, age, salary, designation, department) VALUES (?,?, ?, ?, ?, ?)");

            Employee employee = null;
            
            int eid = EmployeeID.createEID(rs);  
            String name = EmployeeName.getName();    
            int age = Age.readAge();
	    System.out.print("Enter Department: ");
	    String department = new Scanner(System.in).nextLine();

	    rs.setInt(1, eid);
            rs.setString(2, name);
            rs.setInt(3, age);
            rs.setString(6, department);

            if (designation.equals("Ceo")) {
                employee = Ceo.getInstance();
                employee.setEid(eid);
                employee.setName(name);
                employee.setAge(age);
                employee.setSalary(200000);
		employee.setDepartment(department);
                employee.setDesignation(designation);	

            	rs.setFloat(4, 200000f);
            	rs.setString(5, designation);

                ceoCreated = true; 
            } else if (designation.equals("Manager")) {
                employee = Manager.getInstance();
                employee.setEid(eid);
                employee.setName(name);
                employee.setAge(age);
                employee.setSalary(100000);
		employee.setDepartment(department);
                employee.setDesignation(designation);

            	rs.setFloat(4, 100000f);
            	rs.setString(5, designation);
            } else if (designation.equals("Programmer")) {
                employee = Programmer.getInstance();
                employee.setEid(eid);
                employee.setName(name);
                employee.setAge(age);
                employee.setSalary(30000);
		employee.setDepartment(department);
                employee.setDesignation(designation);

            	rs.setFloat(4, 30000f);
            	rs.setString(5, designation);
            } else if (designation.equals("Clerk")) {
                employee = Clerk.getInstance();
                employee.setEid(eid);
                employee.setName(name);
                employee.setAge(age);
                employee.setSalary(20000);
		employee.setDepartment(department);
                employee.setDesignation(designation);

            	rs.setFloat(4, 20000f);
            	rs.setString(5, designation);
            }else if (designation.equals("Others")) {
                employee = Others.getInstance();
                employee.setEid(eid);
                employee.setName(name);
                employee.setAge(age);
                employee.setSalary(20000);
		employee.setDepartment(department);
		System.out.print("Enter Designation : ");
		designation = new Scanner(System.in).nextLine();
                employee.setDesignation(designation);

            	rs.setFloat(4, 20000f);
            	rs.setString(5, designation);
            }
		
  	    rs.execute();
            inCount++;  
            return employee;  

        } catch (CeoNotCreatedException | MultipleCeoCreationException e) {
            System.out.println(e.getMessage());
	    return null;
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
	    return null;
        }
    }
}	
	public static void display(){}

	public static void delete(JdbcRowSet stmt){
		int delId = 0;
		Scanner scanner = new Scanner(System.in);
		
		while(delId==0){
			try{
				JdbcRowSet rs = stmt;
				stmt.setCommand("SELECT * FROM employees_assignment WHERE eid = ?");
				System.out.print("Enter Employee ID to Delete:");
				delId = scanner.nextInt();
				
                		stmt.setInt(1, delId);
				stmt.execute();

				 if (!stmt.next()) {
                			throw new EidException("EID not found, enter a correct ID");
           			 }

				System.out.print("Are you sure you want to delete this record? (yes/no): ");
                		String confirm = scanner.next();
                		if (confirm.equalsIgnoreCase("yes")) {
					stmt.setCommand("DELETE FROM employees_assignment WHERE eid = ?");
                    			stmt.setInt(1, delId);
                    			stmt.execute();
					inCount--;
                   		        System.out.println("Employee with ID " + delId + " deleted successfully.");
                		} else {
                    			System.out.println("Deletion canceled.");
                		}
			}
			catch(Exception e){
				delId = 0;
				System.out.println(e);
				scanner.nextLine();
			}
		}
	}

	public static void raise(JdbcRowSet stmt){
		int raiseId = 0;
		Scanner scanner = new Scanner(System.in);
		while(raiseId==0){
			try{
				JdbcRowSet rs = stmt;
				stmt.setCommand("SELECT * FROM employees_assignment WHERE eid = ?");
				System.out.print("Enter Employee ID to Raise Salary:");
				raiseId = scanner.nextInt();
				stmt.setInt(1, raiseId);
				stmt.execute();
				
            			if (!stmt.next()) {
                			throw new EidException("EID is not Present, enter a correct ID");
           			 }

                		System.out.print("Are you sure you want to raise the salary? (yes/no): ");
                		String confirm = scanner.next();
                		if (confirm.equalsIgnoreCase("yes")) {
						
					float currentSalary = stmt.getFloat("salary");
					System.out.print("Enter Amount to Raise Salary: ");
					float increment = scanner.nextFloat();
					float newSalary = currentSalary + increment;
					stmt.setCommand("UPDATE employees_assignment SET salary = ? WHERE eid = ?");
					
					stmt.setFloat(1, newSalary);
					stmt.setInt(2, raiseId);
					stmt.execute();
                            		
					
                		} else {
                    			System.out.println("Salary raise canceled.");
                		}
			}
			catch(Exception e){
				raiseId = 0;
				System.out.println(e);
				scanner.nextLine();
			}
		}
	}
}

class CeoNotCreatedException extends RuntimeException {
    CeoNotCreatedException(String msg) {
        super(msg);
    }
}

class MultipleCeoCreationException extends RuntimeException {
    MultipleCeoCreationException(String msg) {
        super(msg);
    }
}



final class Ceo extends Employee {
    private static final Ceo instance = new Ceo();
    private Ceo() {}
    public static Ceo getInstance() {
        return instance; 
    }
}



final class Manager extends Employee {
    private static final Manager instance = new Manager();

    private Manager() {} 

    public static Manager getInstance() {
        return instance; 
    }
}



final class Programmer extends Employee {
    private static final Programmer instance = new Programmer();
    private Programmer() {} 
    public static Programmer getInstance() {
        return instance; 
    }
}



final class Clerk extends Employee {
    private static final Clerk instance = new Clerk();
    private Clerk() {} 
    public static Clerk getInstance() {
        return instance;
    }
}



final class Others extends Employee{
    private static final Others instance = new Others();
    private Others() {} 
    public static Others getInstance() {
        return instance;
    }
}




public class EmployeeManagementApp {
    public static void main(String args[]) {

        int innerChoice = 0, outerChoice = 0;
        Scanner scanner = new Scanner(System.in);  
   
        try {
		//Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5433/empdb","postgres","tiger");
		RowSetFactory rsf = RowSetProvider.newFactory();
		JdbcRowSet rs = rsf.createJdbcRowSet();
		rs.setUrl("jdbc:postgresql://localhost:5433/empdb");
		rs.setUsername("postgres");
		rs.setPassword("tiger");
		

            do {
                outerChoice = 0;
                System.out.println("---------------------------------------------");
                System.out.println("1.Create");
                System.out.println("2.Display");
                System.out.println("3.Raise Salary");
                System.out.println("4.Remove");
                System.out.println("5.Search");
                System.out.println("6.Exit");
                System.out.println("---------------------------------------------");

          
                outerChoice = Menu.readChoice(6);

                switch (outerChoice) {
                    case 1: {
                        do {
                            innerChoice = 0;
                            System.out.println("---------------------------------------------");
                            System.out.println("1.CEO");
                            System.out.println("2.Clerk");
                            System.out.println("3.Programmer");
                            System.out.println("4.Manager");
                            System.out.println("5.Others");
                            System.out.println("6.Exit");
                            System.out.println("---------------------------------------------");

                            innerChoice = Menu.readChoice(6);

                            switch (innerChoice) {
                                case 1: {
                                    Employee createdEmployee = Employee.createEmployee("Ceo",rs,true);
                                    break;
                                }
                                case 2: {
                                    Employee createdEmployee = Employee.createEmployee("Clerk",rs,true);
                                    break;
                                }
                                case 3: {
                                    Employee createdEmployee = Employee.createEmployee("Programmer",rs,true);
                                    break;
                                }
                                case 4: {
                                    Employee createdEmployee = Employee.createEmployee("Manager",rs,true);
                                    break;
                                }
                                case 5: {
                                    Employee createdEmployee = Employee.createEmployee("Others",rs,true);
                                    break;
                                }
                            }
                        } while (innerChoice != 6);
                        break;
                    }
                    case 2: {
                        int displayChoice = 0;
                       
                        do {
                            System.out.println("---------------------------------------------");
                            System.out.println("1.Display By Id");
                            System.out.println("2.Display By Name");
                            System.out.println("3.Display By Age");
                            System.out.println("4.Display By Salary");
                            System.out.println("5.Display By Designation");
                            System.out.println("6.Exit");
                            System.out.println("---------------------------------------------");

                            displayChoice = Menu.readChoice(6);

                            switch (displayChoice) {
                                case 1: {
                                    DisplayDetails.display(rs,1);
                                    break;
                                }
                                case 2: {
                                    DisplayDetails.display(rs,2);
                                    break;
                                }
                                case 3: {
                                    DisplayDetails.display(rs,2);
                                    break;
                                }
                                case 4: {
                                    DisplayDetails.display(rs,3);
                                    break;
                                }
                                case 5: {
                                    DisplayDetails.display(rs,3);
                                    break;
                                }
                            }

                        } while (displayChoice != 6);
                        break;
                    }
                    case 3: {
                        Employee.raise(rs);
                        break;
                    }
                    case 4: {
                        Employee.delete(rs);
                        break;
                    }
                    case 5: {
                        int searchChoice = 0;
                        do {
                            System.out.println("---------------------------------------------");
                            System.out.println("1.Search By Id");
                            System.out.println("2.Search By Name");
                            System.out.println("3.Search By Designation");
                            System.out.println("4.Exit");
                            System.out.println("---------------------------------------------");
                            int index = 0;

                            searchChoice = Menu.readChoice(4);
                            switch (searchChoice) {
                                case 1: {
				    SearchDetails.search(rs,1);
                                    break;
                                }
                                case 2: {
				    SearchDetails.search(rs,2);
                                    break;
                                }
                                case 3: {
				    SearchDetails.search(rs,3);
                                    break;
                                }
                            }
                            
                        } while (searchChoice != 4);
                        break;
                    }
                }
            } while (outerChoice != 6);

            System.out.println("Total no.of employees created/added:" + Employee.inCount);
	    scanner.close();
	
        }catch(Exception e) {
            System.out.println(e);
        }
	
    }
}

	
class DisplayDetails{
	public static void display(JdbcRowSet stmt , int choice){
		try{
			JdbcRowSet rs = stmt;
			switch(choice){
				case(1):{
					stmt.setCommand("select * from employees_assignment order by eid");
					stmt.execute();
					boolean found = false;
					
					while(stmt.next()){
						System.out.println("ID: "+rs.getInt("eid")+"NAME: "+rs.getString("name")+"AGE: "+rs.getInt("age")+"SALARY: "+rs.getFloat("salary")+"DESIGNATION: "+rs.getString("designation")+"DEPARTMENT: "+rs.getString("department"));
						found=true;
					}
					if(!found){
						System.out.println("No employee found with ID ");
					}
					break;
				}
				case(2):{
					stmt.setCommand("select * from employees_assignment order by NAME");
					stmt.execute();
					boolean found = false;
					
					while(stmt.next()){
						System.out.println("ID: "+rs.getInt("eid")+"NAME: "+rs.getString("name")+"AGE: "+rs.getInt("age")+"SALARY: "+rs.getFloat("salary")+"DESIGNATION: "+rs.getString("designation")+"DEPARTMENT: "+rs.getString("department"));
						found =true;
					}
					if(!found){
						System.out.println("No employee found with name ");
					}
					break;
				}

				case(3):{
					stmt.setCommand("select * from employees_assignment order by SALARY");
					stmt.execute();
					boolean found = false;
					
					while(stmt.next()){
						System.out.println("ID: "+rs.getInt("eid")+"NAME: "+rs.getString("name")+"AGE: "+rs.getInt("age")+"SALARY: "+rs.getFloat("salary")+"DESIGNATION: "+rs.getString("designation")+"DEPARTMENT: "+rs.getString("department"));
						found =true;
					}
					if(!found){
						System.out.println("No employee found with salary");
					}
					break;
				}
				case(4):{
					stmt.setCommand("select * from employees_assignment order by AGE");
					stmt.execute();
					boolean found = false;
					while(stmt.next()){
						System.out.println("ID: "+rs.getInt("eid")+"NAME: "+rs.getString("name")+"AGE: "+rs.getInt("age")+"SALARY: "+rs.getFloat("salary")+"DESIGNATION: "+rs.getString("designation")+"DEPARTMENT: "+rs.getString("department"));
						found =true;
					}
					if(!found){
						System.out.println("No employee found with age");
					}
					break;
				}
				case(5):{
					stmt.setCommand("select * from employees_assignment order by DESIGNATION");
					stmt.execute();
					boolean found = false;
					
					while(stmt.next()){
						System.out.println("ID: "+rs.getInt("eid")+"NAME: "+rs.getString("name")+"AGE: "+rs.getInt("age")+"SALARY: "+rs.getFloat("salary")+"DESIGNATION: "+rs.getString("designation")+"DEPARTMENT: "+rs.getString("department"));
						found =true;
					}
					if(!found){
						System.out.println("No employee found with designation");
					}
					break;
				}
			}
		}catch(Exception e){
			System.out.println(e);
		}
	}
}

class SearchDetails{
	public static void search(JdbcRowSet stmt, int choice){
		try{
			Scanner scanner = new Scanner(System.in);
			JdbcRowSet rs = stmt;
			switch(choice){
				case(1):{
					stmt.setCommand("select * from EMPLOYEE where Eid =?");
					
					System.out.print("Enter employee ID to search: ");
                        	        int searchId = scanner.nextInt();
					stmt.setInt(1,searchId);
					stmt.execute();
					if(rs.next()){
						System.out.println("ID: "+rs.getInt("eid")+"NAME: "+rs.getString("name")+"AGE: "+rs.getInt("age")+"SALARY: "+rs.getFloat("salary")+"DESIGNATION: "+rs.getString("designation")+"DEPARTMENT: "+rs.getString("department"));
					}
					else{
						System.out.println("No employee found with ID " + searchId);
					}
					break;
				}
				case(2):{
					stmt.setCommand("select * from EMPLOYEE where NAME =?");
        	                        System.out.print("Enter employee Name to search: ");
					scanner.nextLine();
                	                String searchName = scanner.nextLine();	
					stmt.setString(1,searchName);
					stmt.execute();
					boolean found = false;
					while(rs.next()){
						System.out.println("ID: "+rs.getInt("eid")+"NAME: "+rs.getString("name")+"AGE: "+rs.getInt("age")+"SALARY: "+rs.getFloat("salary")+"DESIGNATION: "+rs.getString("designation")+"DEPARTMENT: "+rs.getString("department"));
						found =true;
					}
					if(!found){
						System.out.println("No employee found with name " + searchName);
					}
					break;
				}
				case(3):{
					stmt.setCommand("select * from EMPLOYEE where DESIGNATION =?");
                                	System.out.print("Enter employee Designation to search: ");
					scanner.nextLine();
	                                String searchDesignation = scanner.nextLine();
					stmt.setString(1,searchDesignation);
					boolean found = false;
					stmt.execute();
					while(rs.next()){
						System.out.println("ID: "+rs.getInt("eid")+"NAME: "+rs.getString("name")+"AGE: "+rs.getInt("age")+"SALARY: "+rs.getFloat("salary")+"DESIGNATION: "+rs.getString("designation")+"DEPARTMENT: "+rs.getString("department"));
						found =true;
					}
					if(!found){
						System.out.println("No employee found with designation " + searchDesignation);
					}
					break;
				}
			}
		}catch(Exception e){

		}
	}
}