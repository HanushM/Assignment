package emp.assignment;

import java.util.regex.*;
import java.util.*;
import java.io.*;
import java.sql.*;
import javax.sql.*;
import javax.sql.rowset.*;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Updates;

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
        	//System.out.print("Entered choice menu");
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
            }catch(Exception e) {
            	System.out.println(e);
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
            	MongoCollection<Document> collection = DBConnection.getConnection();
                int employeeId = new Scanner(System.in).nextInt();
                Bson filter = Filters.eq("eid", employeeId);
                FindIterable<Document> i = collection.find(filter);
                if (i.first()!=null) {
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
			MongoCollection<Document> collection = DBConnection.getConnection();
			Bson filter = Filters.eq("designation", "ceo");
			FindIterable<Document> i = collection.find(filter);
			if(i.first() != null){
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
		EmpDAOImp employeeoperation = new EmpDAOImp();
		Scanner scanner = new Scanner(System.in);
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
	}
}



class DBConnection{
	public static MongoClient mongoclient = null;
	public static MongoDatabase database = null;
	public static MongoCollection<Document> collection = null;
	private DBConnection(){}
	public static MongoCollection<Document> getConnection(){
		if(mongoclient==null){
			try{
				mongoclient=MongoClients.create("mongodb://localhost:27017");
				database = mongoclient.getDatabase("employee_assignment");
				collection = database.getCollection("Employee");
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return collection;
	}
	public static void closeConnection(){
        	try {
	            if (mongoclient != null) {
	            	mongoclient.close();;
	            }
        	} catch (Exception e) {
	            e.printStackTrace();
	        }
        	mongoclient = null;
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
				MongoCollection<Document>collection = DBConnection.getConnection();
				Bson filter = Filters.eq("eid",emp.getEID());
				FindIterable<Document> i=collection.find(filter);
        		if (i.first() != null) {
            			System.out.println("Employee with ID " + emp.getEID() + " already exists!");
            			return; 
        		}
        		
        		collection.insertOne(new Document().append("eid",emp.getEID()).append("name",emp.getName()).append("age",emp.getAge()).append("salary",emp.getSalary()).append("designation",emp.getDesignation()).append("department",emp.getDepartment()));
        		
		}catch(Exception e){
			System.out.println(e);
		}
	}
	public void display(int choice){
		try{
			MongoCollection<Document> collection = DBConnection.getConnection();
			Bson sort=null;
			switch(choice){
				case(1):{
					sort = Sorts.ascending("eid");
					break;
				}
				case(2):{
					sort = Sorts.ascending("name");
					break;
				}
				case(3):{
					sort = Sorts.ascending("age");
					break;
				}
				case(4):{
					sort = Sorts.ascending("salary");
					break;
				}
				case(5):{
					sort = Sorts.ascending("designation");
					break;
				}
			}
			FindIterable<Document> i=collection.find().sort(sort);	
			for(Document d:i) {
				System.out.println(d.toJson());
			}
		}catch(Exception e){
			System.out.println(e);
		}
	}
	public void find(int choice){
		try{
			Scanner scanner = new Scanner(System.in);
			MongoCollection<Document> collection = DBConnection.getConnection();
			boolean found = false;
			Bson filter = null;
			switch(choice){
				case(1):{
					System.out.print("Enter employee ID to search: ");
					int searchId = scanner.nextInt();
					filter = Filters.eq("eid",searchId);
					scanner.nextLine();
					break;
				}
				case(2):{
					System.out.print("Enter employee Name to search: ");
        	        String searchName = scanner.nextLine();	
					filter = Filters.eq("name",searchName);
					break;
				}
				case(3):{
					System.out.print("Enter employee Designation to search: ");
	                String searchDesignation = scanner.nextLine();
					filter = Filters.eq("designation",searchDesignation);
					break;
				}
			}
			FindIterable<Document> i = collection.find(filter);
			for(Document d:i) {
				System.out.println(d.toJson());
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
			Scanner scanner = new Scanner(System.in);
			MongoCollection<Document> collection = DBConnection.getConnection();
			int delId=-1;
			while(delId==-1){
				System.out.print("Enter Employee ID to Delete:");
				delId = scanner.nextInt();
				scanner.nextLine();
				Bson filter = Filters.eq("eid",delId);
				FindIterable<Document> i = collection.find(filter);
				if (i.first() == null){
					delId = -1;
					scanner.nextLine();
                			throw new EidException("EID not found, enter a correct ID");
           		}
				for(Document d:i) {
					System.out.println(d.toJson());
				}
				
				System.out.print("Are you sure you want to delete this record? (yes/no): ");
                String confirm = scanner.next();
                if (confirm.equalsIgnoreCase("yes")) {
					collection.deleteOne(filter);
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
			Scanner scanner = new Scanner(System.in);
			int raiseId=-1;
			MongoCollection<Document> collection = DBConnection.getConnection();
			while(raiseId==-1){
				System.out.print("Enter Employee ID to Raise Salary:");
				raiseId = scanner.nextInt();
				scanner.nextLine();
				Bson filter = Filters.eq("eid",raiseId);
				FindIterable<Document> i = collection.find(filter);
				if (i.first() == null) {
					raiseId = -1;
					scanner.nextLine();
                	throw new EidException("EID not found, enter a correct ID");
           		}
				for (Document d:i){
						System.out.println(d.toJson());
					}
				System.out.print("Are you sure you want to delete this record? (yes/no): ");
                String confirm = scanner.next();
				if (confirm.equalsIgnoreCase("yes")) {
					Document employee = i.first();
					float currentSalary = employee.getDouble("salary").floatValue();
					System.out.print("Enter Amount to Raise Salary: ");
					float increment = scanner.nextFloat();
					float newSalary = currentSalary + increment;
					Bson update = Updates.set("salary",newSalary);
                	collection.updateOne(filter, update);
                	System.out.println("Employee with ID " + raiseId + " salary raised successfully.");

				}else {
                    			System.out.println("updation canceled.");
                		}
			}
		}catch(Exception e){
			System.out.println(e);
		}
		
	}
}