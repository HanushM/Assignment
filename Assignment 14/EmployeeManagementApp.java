package emp.assignment;

import java.util.*;
import java.sql.*;
import java.io.*;
import java.util.regex.*;

class Age{
	public static int readAge(){
		while (true) {
			try{
				System.out.print("Enter Age: ");
				int age = new Scanner(System.in).nextInt();
				if (age < 21 || age > 60){
					throw new AgeException("Age is invalid, Enter between 21-60");
				}	
				return age;
			}catch(AgeException e){
				System.out.println(e.getMessage());
			}catch (Exception e){
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
    public static int createEID() {
        while (true) {
            System.out.print("Enter EmployeeID:");
            try {
		Connection con = DBConnection.getConnection();
                int employeeId = new Scanner(System.in).nextInt();
                String query = "SELECT COUNT(*) FROM employees_assignment WHERE eid = ?";
		PreparedStatement stmt = con.prepareStatement(query);

		stmt.setInt(1, employeeId);
		ResultSet rs = stmt.executeQuery();

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

abstract class Employee{
	private int eID;
	private String name;
	private int age;
	private float salary;
	private String designation;
	private String department;

	static int inCount=0;

	protected Employee(){
	    System.out.print("Enter Department: ");
	    this.department = new Scanner(System.in).nextLine();		
	}

	public int getEID(){
		return this.eID;
	}
	public String getName(){
		return this.name;
	}
	public int getAge(){
		return this.age;
	}
	public float getSalary(){
		return this.salary;
	}
	public String getDesignation(){
		return this.designation;
	}
	public String getDepartment(){
		return this.department;
	}

	public void setEID(int eID){
		this.eID=eID;
	}
	public void setName(String name){
		this.name=name;
	}
	public void setAge(int age){
		this.age=age;
	}
	public void setSalary(float salary){
		this.salary=salary;
	}
	public void setDesignation(String designation){
		this.designation=designation;
	}
	public void setDepartment(String department){
		this.department=department;
	}
}

class EmployeeFactory{
	static boolean ceoCreated=false;
	static{
		System.out.println("Static");
		try{
			Connection con = DBConnection.getConnection();
			PreparedStatement pstmt = con.prepareStatement("select * from employees_assignment where designation='Ceo'");
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()){
				ceoCreated=true;
			}
		}catch(Exception e){
			System.out.println(e);
		}
	}
	public static Employee createEmployee(String designation){
		while(true){
			try{
				if (!ceoCreated && !(designation.equals("Ceo"))) {
    					throw new CeoNotCreatedException("First create the CEO before creating other employees.");
				}
            			else if (designation.equals("Ceo") && ceoCreated) {
                			throw new MultipleCeoCreationException("Only one instance of CEO can be created.");
	            		}
				
				Employee employee = null;
				int eid = EmployeeID.createEID();
				String name = EmployeeName.getName();
				int age = Age.readAge();
				
				if(designation.equals("Ceo")){
					employee = Ceo.getInstance();
					employee.setSalary(200000f);
					employee.setDesignation(designation);
					ceoCreated=true;
				}
				else if(designation.equals("Manager")){
					employee = Manager.getInstance();
					employee.setSalary(100000f);
					employee.setDesignation(designation);
				}
				else if(designation.equals("Programmer")){
					employee = Programmer.getInstance();
					employee.setSalary(30000f);
					employee.setDesignation(designation);
				}
				else if(designation.equals("Clerk")){
					employee = Clerk.getInstance();
					employee.setSalary(20000f);
					employee.setDesignation(designation);
				}
				else if(designation.equals("Others")){
					employee = Others.getInstance();
					employee.setSalary(20000f);
					System.out.print("Enter Designation : ");
					designation = new Scanner(System.in).nextLine();
        	        		employee.setDesignation(designation);
				}
	
		                employee.setEID(eid);
        	        	employee.setName(name);
                		employee.setAge(age);
	
				Employee.inCount++;
				return employee;
			}catch (CeoNotCreatedException | MultipleCeoCreationException e) {
	            		System.out.println(e.getMessage());
		    		return null;
	        	} catch (Exception e) {
	            		System.out.println("An error occurred: " + e.getMessage());
		    		return null;
	        	}
		}
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

public class EmployeeManagementApp{
	public static void main(String args[]){
		int innerChoice = 0, outerChoice = 0;
		Scanner scanner = new Scanner(System.in);
		EmpDAOImp employeeoperation = new EmpDAOImp();
		try{
			do{
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

				switch(outerChoice){
					case(1):{
						do{
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
								
							switch(innerChoice){
								case(1):{
									Employee createdEmployee = EmployeeFactory.createEmployee("Ceo");
									if (createdEmployee != null) {
    										employeeoperation.save(createdEmployee);
									}
									break;
								}
								case(2):{
									Employee createdEmployee = EmployeeFactory.createEmployee("Clerk");
									if (createdEmployee != null) {
    										employeeoperation.save(createdEmployee);
									}
									break;
								}
								case(3):{
									Employee createdEmployee = EmployeeFactory.createEmployee("Programmer");
									if (createdEmployee != null) {
    										employeeoperation.save(createdEmployee);
									}
									break;
								}
								case(4):{
									Employee createdEmployee = EmployeeFactory.createEmployee("Manager");
									if (createdEmployee != null) {
    										employeeoperation.save(createdEmployee);
									}
									break;
								}
								case(5):{
									Employee createdEmployee = EmployeeFactory.createEmployee("Others");						
									if (createdEmployee != null) {
    										employeeoperation.save(createdEmployee);
									}
									break;
								}
							}
						}while(innerChoice != 6);
						break;
					}
					case(2):{
						int displayChoice = 0;
						do{
							System.out.println("---------------------------------------------");
                            				System.out.println("1.Display By Id");
							System.out.println("2.Display By Name");
                           				System.out.println("3.Display By Age");
                            				System.out.println("4.Display By Salary");
                            				System.out.println("5.Display By Designation");
                            				System.out.println("6.Exit");
                            				System.out.println("---------------------------------------------");
							displayChoice = Menu.readChoice(6);
							employeeoperation.display(displayChoice);
						}while(displayChoice != 6);
						break;
					}
					case(3):{
						employeeoperation.update();
						break;
					}
					case(4):{
						employeeoperation.delete();
						break;
					}
					case(5):{
						int searchChoice = 0;
						do{
                            				System.out.println("---------------------------------------------");
                            				System.out.println("1.Search By Id");
                            				System.out.println("2.Search By Name");
                            				System.out.println("3.Search By Designation");
                            				System.out.println("4.Exit");
                            				System.out.println("---------------------------------------------");
							searchChoice = Menu.readChoice(4);
							employeeoperation.find(searchChoice);
						}while(searchChoice != 4);
						break;
					}
				}
			}while(outerChoice != 6);
		}catch(Exception e){
			System.out.println(e);
		}
		/*finally {
			System.out.println("Total no.of employees created/added:" + Employee.inCount);
    			if (pstmt != null) pstmt.close();
    			if (rs != null) rs.close();
    			if (con != null) con.close(); // Close the connection as well
		}*/
	}
}



class DBConnection{
	private static Connection con;
	private DBConnection(){}
	public static Connection getConnection(){
		if(con==null){
			try{
				con=DriverManager.getConnection("jdbc:postgresql://localhost:5433/empdb","postgres","tiger");
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return con;
	}
	public static void closeConnection(){
		con=null;
	}
}

interface EmpDAO{
	public void save(Employee entity);
	public void display(int choice);
	public void find(int choice);
	public void delete();
	public void update();
}

class EmpDAOImp implements EmpDAO{
	public void save(Employee emp){
		try{
			Connection con = DBConnection.getConnection();
        		PreparedStatement checkStmt = con.prepareStatement("SELECT COUNT(*) FROM employees_assignment WHERE eid = ?");
        		checkStmt.setInt(1, emp.getEID());
        		ResultSet rs = checkStmt.executeQuery();
        		rs.next();
        		int count = rs.getInt(1);
        		if (count > 0) {
            			System.out.println("Employee with ID " + emp.getEID() + " already exists!");
            			return; 
        		}
			PreparedStatement pstmt = con.prepareStatement("Insert into employees_assignment (eid,name, age, salary, designation, department) VALUES (?,?, ?, ?, ?, ?)");
			pstmt.setInt(1,emp.getEID());
			pstmt.setString(2,emp.getName());
			pstmt.setInt(3,emp.getAge());
			pstmt.setFloat(4,emp.getSalary());
			pstmt.setString(5,emp.getDesignation());
			pstmt.setString(6,emp.getDepartment());
			pstmt.executeUpdate();
		}catch(Exception e){
			System.out.println(e);
		}
	}
	public void display(int choice){
		try{
			Connection con = DBConnection.getConnection();
			PreparedStatement pstmt = null;
			switch(choice){
				case(1):{
					pstmt=con.prepareStatement("select * from employees_assignment order by eid");
					break;
				}
				case(2):{
					pstmt=con.prepareStatement("select * from employees_assignment order by NAME");
					break;
				}
				case(3):{
					pstmt=con.prepareStatement("select * from employees_assignment order by AGE");
					break;
				}
				case(4):{
					pstmt=con.prepareStatement("select * from employees_assignment order by SALARY");
					break;
				}
				case(5):{
					pstmt=con.prepareStatement("select * from employees_assignment order by DESIGNATION");
					break;
				}
			}
			ResultSet rs = pstmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columns = rsmd.getColumnCount();
			while(rs.next()){
				for (int i=1;i<=columns;i++){
					System.out.println(rsmd.getColumnName(i)+" : "+rs.getString(i));
				}
			}
		}catch(Exception e){
			System.out.println(e);
		}
	}
	public void find(int choice){
		try{
			Connection con = DBConnection.getConnection();
			PreparedStatement pstmt = null;
			Scanner scanner = new Scanner(System.in);
			boolean found = false;
			switch(choice){
				case(1):{
					pstmt=con.prepareStatement("select * from employees_assignment where Eid =?");
					System.out.print("Enter employee ID to search: ");
					int searchId = scanner.nextInt();
					pstmt.setInt(1,searchId);
					scanner.nextLine();
					break;
				}
				case(2):{
					pstmt=con.prepareStatement("select * from employees_assignment where NAME =?");
					System.out.print("Enter employee Name to search: ");
        	        	        String searchName = scanner.nextLine();	
					pstmt.setString(1,searchName);
					break;
				}
				case(3):{
					pstmt=con.prepareStatement("select * from employees_assignment where DESIGNATION =?");
					System.out.print("Enter employee Designation to search: ");
	                        	String searchDesignation = scanner.nextLine();
					pstmt.setString(1,searchDesignation);
					break;
				}
			}
			ResultSet rs = pstmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columns = rsmd.getColumnCount();
			while(rs.next()){
				for (int i=1;i<=columns;i++){
					System.out.println(rsmd.getColumnName(i)+" : "+rs.getString(i));
				}
				found=true;
			}
			if(!found){
				System.out.println("No employee found");
			}
		}catch(Exception e){
			System.out.println(e);
		}
	}
	public void delete(){
		try{
			Connection con = DBConnection.getConnection();
			PreparedStatement pstmt=con.prepareStatement("SELECT * FROM employees_assignment WHERE eid = ?");
			int delId=-1;
			Scanner scanner = new Scanner(System.in);
			while(delId==-1){
				System.out.print("Enter Employee ID to Delete:");
				delId = scanner.nextInt();
				pstmt.setInt(1, delId);
				ResultSet rs = pstmt.executeQuery();
				if (!rs.next()) {
					delId = 0;
					scanner.nextLine();
                			throw new EidException("EID not found, enter a correct ID");
           			}
				ResultSetMetaData rsmd = rs.getMetaData();
				int columns = rsmd.getColumnCount();
				
				for (int i=1;i<=columns;i++){
						System.out.println(rsmd.getColumnName(i)+" : "+rs.getString(i));
					}
				
				System.out.print("Are you sure you want to delete this record? (yes/no): ");
                		String confirm = scanner.next();
                		if (confirm.equalsIgnoreCase("yes")) {
					String deleteQuery = "DELETE FROM employees_assignment WHERE eid = ?";
                    			pstmt.setInt(1, delId);
                    			pstmt.executeUpdate();
					Employee.inCount--;
                   		        System.out.println("Employee with ID " + delId + " deleted successfully.");
                		} else {
                    			System.out.println("Deletion canceled.");
                		}
			}
		}catch(Exception e){
			System.out.println(e);
		}
	}
	public void update(){
		try{
			int raiseId=-1;
			Scanner scanner = new Scanner(System.in);
			Connection con = DBConnection.getConnection();
			PreparedStatement pstmt = con.prepareStatement("SELECT * FROM employees_assignment WHERE eid = ?");
			while(raiseId==-1){
				System.out.print("Enter Employee ID to Raise Salary:");
				raiseId = scanner.nextInt();
				pstmt.setInt(1, raiseId);
				ResultSet rs = pstmt.executeQuery();
				if (!rs.next()) {
					raiseId = 0;
					scanner.nextLine();
                			throw new EidException("EID not found, enter a correct ID");
           			}
				ResultSetMetaData rsmd = rs.getMetaData();
				int columns = rsmd.getColumnCount();
				
				for (int i=1;i<=columns;i++){
						System.out.println(rsmd.getColumnName(i)+" : "+rs.getString(i));
					}
				
				System.out.print("Are you sure you want to delete this record? (yes/no): ");
                		String confirm = scanner.next();
				if (confirm.equalsIgnoreCase("yes")) {
					float currentSalary = rs.getFloat("salary");
					System.out.print("Enter Amount to Raise Salary: ");
					float increment = scanner.nextFloat();
					float newSalary = currentSalary + increment;
					String updateQuery = "UPDATE employees_assignment SET salary = ? WHERE eid = ?";
					pstmt = con.prepareStatement(updateQuery);
					pstmt.setFloat(1, newSalary);
					pstmt.setInt(2, raiseId);
					int rowsAffected = pstmt.executeUpdate();
                            		if (rowsAffected > 0) {
                                		System.out.println("Employee with ID " + raiseId + " salary raised successfully.");
                            		} else {
                                		System.out.println("Failed to update salary.");
                            		}

				}else {
                    			System.out.println("updation canceled.");
                		}
			}
		}catch(Exception e){
			System.out.println(e);
		}
		
	}
}