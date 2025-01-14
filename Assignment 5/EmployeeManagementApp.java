package emp.assignment;

import java.util.regex.*;
import java.util.*;

class Age{
	public static int readAge(){
		while(true){
			System.out.print("Enter Age:");
			try{
				int age = new Scanner(System.in).nextInt();
				if(age<21 || age>60)
				{
					throw new AgeException("age is invalid,Enter between 21-60");
				}
				return age;
			}
			catch(AgeException e){
				System.out.println(e.getMessage());
			}
			catch(Exception e){
				System.out.println("Please enter number only");
			}
			
		}
	}

}

class AgeException extends RuntimeException{
	AgeException(){
		super();
	}
	AgeException(String msg){
		super(msg);
	}
}

class EmployeeName{
	public static String getName(){
		while(true){
			System.out.print("Enter Name:");
			try{	
				String name = new Scanner(System.in).nextLine();
				Pattern p1 = Pattern.compile("[A-Z][a-z]+\s[A-Z][a-z]*");
				Matcher m1 = p1.matcher(name);
				if(! m1.matches()){
					throw new EmployeeNameException("Enter correct name it should be two words And starting letter should be capital");
				}
				return name;
			}
			catch(InputMismatchException e){
				System.out.println("Please enter characters only");
			}
			catch(EmployeeNameException e){
				System.out.println(e.getMessage());
			}
		}
	}
}

class EmployeeNameException extends RuntimeException{
	EmployeeNameException(){
		super();
	}
	EmployeeNameException(String msg){
		super(msg);
	}
}

class Menu{
	private static int maxChoice;
	public static int readChoice(int max){
		maxChoice=max;
		while(true){
			System.out.print("Enter Choice:");
			try{
				int choice = new Scanner(System.in).nextInt();
				if(choice<1 || choice>maxChoice){
					throw new InvalidChoiceException();
				}
				return choice;
			}
			catch(InputMismatchException e){
				System.out.println("Please enter number only");
			}
			catch(InvalidChoiceException e){
				e.displayMessage(maxChoice);
			}
		}
	}
}

class InvalidChoiceException extends RuntimeException{
	InvalidChoiceException(){
		super();
	}
	InvalidChoiceException(String msg){
		super(msg);
	}
	public void displayMessage(int maxChoice){
		System.out.println("Please enter the choice between 1 to "+maxChoice);
	}
}

class EmployeeID{
	public static int getEid(Employee[] employees){
		while(true){
			System.out.print("Enter EmployeeID:");
			try{
				int employeeId = new Scanner(System.in).nextInt();
				for(int i=0;i<Employee.inCount;i++){
					if(employeeId==employees[i].getEid()){
						throw new EidException("EID Already Present-renter correct ID");
					}
				}
				return employeeId ;
			}
			catch(EidException e){
				System.out.println(e.getMessage());
			}
			catch(InputMismatchException e){
				System.out.println("please Enter numbers only");
			}
		}
	}
}

class EidException extends RuntimeException{
	EidException(){
		super();
	}
	EidException(String msg){
		super(msg);
	}
}

abstract class Employee
{
	private String name;
	private int age;
	private float salary;
	private String designation;
	private int eid=0;
	static int inCount=0;	
	//static int delCount=0;

	Employee(float salary,String designation,Employee[] employees)
	{
		int eid=EmployeeID.getEid(employees);		
		String name = EmployeeName.getName();
		int age=Age.readAge();
		
		this.eid=eid;
		this.name=name;
		this.age=age;
		this.salary=salary;
		this.designation=designation;
	}
	final public void display()
	{
		System.out.println("EmployeeId of Employee: "+this.eid);
		System.out.println("Name of Employee: "+this.name);
		System.out.println("Age of Employee: "+this.age);
		System.out.println("Salary of Employee: "+this.salary);
		System.out.println("Designation of Employee: "+this.designation);
	}
	abstract public void raiseSalary();

	protected void setSalary(float amount){
		this.salary=this.salary+amount;
	}

	protected int getEid(){
		return this.eid;
	}
	public static final Employee[] delete(Employee employees[]){
		int delId=0;
		Scanner scanner=new Scanner(System.in);
		while(delId==0){
			try{
				System.out.print("Enter Employee ID TO Delete:");
				delId=scanner.nextInt();
			}
			catch(Exception e){
				delId=0;
				System.out.println(e);
				scanner.nextLine();
			}
		}
		boolean present=false;
		int index=0;
		for(int i=0;i<inCount;i++){
			if(employees[i].getEid()==delId){
				index=i;
				present=true;
			}
		}
		if(present){
			employees[index].display();
			System.out.println("do yo really want to remove this employee record(y/n):");
			char deleteChoice = scanner.next(".").charAt(0);
			if(deleteChoice=='y' || deleteChoice=='Y'){
				for(int j=index;j<inCount-1;j++){
					employees[j]=employees[j+1];
				}
				employees[inCount - 1] = null;
				inCount-=1;
			}
				
		}
		else{
			System.out.println("Invalid ID");
		}
		return employees;
		
	}	
}
final class Manager extends Employee
{
	Manager(Employee[] employees)
	{
		super(100000,"Manager",employees);
	}
	final public void raiseSalary()
	{
		this.setSalary(15000f);
	}	
}
final class Programmer extends Employee
{
	Programmer(Employee[] employees)
	{
		super(30000,"Programmer",employees);
	}
	final public void raiseSalary()
	{
		this.setSalary(5000f);
	}
}
final class Clerk extends Employee
{
	Clerk(Employee[] employees)
	{
		super(20000,"Clerk",employees);
	}
	final public void raiseSalary()
	{
		this.setSalary(2000f);
	}
}

public class EmployeeManagementApp
{
	public static void main(String args[])
	{

		int innerChoice=0,outerChoice=0;
		Employee employees[]=new Employee[50];
		//int[] deleteId= new deleteId[50];

		do{
			outerChoice=0;
			System.out.println("---------------------------------------------");	
			System.out.println("1.Create");
			System.out.println("2.Display");
			System.out.println("3.Raise Salary");
			System.out.println("4.Remove");
			System.out.println("5.Exit");
			System.out.println("---------------------------------------------");
			Scanner scanner = new Scanner(System.in);
			//System.out.println("Enter Choice:-");
			outerChoice = Menu.readChoice(5);
			//scanner.nextLine();
			switch(outerChoice)
			{
				case(1):
				{
					do{
						innerChoice=0;
						System.out.println("---------------------------------------------");
						System.out.println("1.Clerk");
						System.out.println("2.Programmer");
						System.out.println("3.Manager");
						System.out.println("4.Exit");
						System.out.println("---------------------------------------------");
						//System.out.println("Enter Choice:-");
						innerChoice = Menu.readChoice(4);
						//scanner.nextLine();			
							
						switch(innerChoice)
						{
							case(1):
							{	
								employees[Employee.inCount]=new Clerk(employees);
								Employee.inCount+=1;
								break;
							}
							case(2):
							{	
								employees[Employee.inCount]=new Programmer(employees);
								Employee.inCount+=1;
								break;
							}
							case(3):
							{
								employees[Employee.inCount]=new Manager(employees);
								Employee.inCount+=1;
								break;	
							}
						}
					}while(innerChoice!=4);
					break;
				}
				case(2):
				{
					for(int i=0;i<Employee.inCount;i++){
						employees[i].display();
					}
					break;
				}
				case(3):
				{
					for(int i=0;i<Employee.inCount;i++){
						employees[i].raiseSalary();
					}
					break;
				}
				case(4):
				{
					employees=Employee.delete(employees);
					break;
				}
			}
		}while(outerChoice!=5);

		System.out.println("Total no.of employees created/added:"+Employee.inCount);
	}
}
