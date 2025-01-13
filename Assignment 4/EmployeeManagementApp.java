package emp.assignment;

import java.util.*;

class AgeException extends RuntimeException{
	AgeException(){
		super();
	}
	AgeException(String msg){
		super(msg);
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
		int age=0;
		int eid=0;
		Scanner sc=new Scanner(System.in);
		while(eid==0){
			try{
				System.out.println("Enter Employee ID:");
				eid = sc.nextInt();	
				sc.nextLine();	
				for(int i=0;i<inCount;i++){
					if(eid== employees[i].getEid()){
						throw new EidException("EID Already Present-renter correct ID");
					}
				}
			}
			catch(EidException e){
				eid=0;
				System.out.println(e);
				
			}
			catch(Exception e){
				eid=0;
				System.out.println("please Enter correct ID");
				
			}
		}

		System.out.println("Enter name:");
		String name = sc.nextLine();
		
		while(age==0){
			try{
				System.out.println("Enter age:");
				age = sc.nextInt();
				if(age<21 || age>60)
				{
					age=0;
					throw new AgeException("age is invalid,Enter between 21-60");
				}
				sc.nextLine();
			}
			catch(AgeException e){
				age=0;
				System.out.println(e);
				sc.nextLine();
			}
			catch(Exception e){
				age=0;
				System.out.println(e);
				sc.nextLine();
			}
		}

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
		Scanner scanner=new Scanner(System.in);
		System.out.println("Enter Employee ID TO Delete:");
		int delId=scanner.nextInt();
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
			while(outerChoice==0){
				try{
					System.out.println("Enter Choice:-");
					outerChoice = scanner.nextInt();
					scanner.nextLine();
				}
				catch(Exception e){
					outerChoice=0;
					System.out.println("Choose correct choice between 1-5");
					scanner.nextLine();
				}
			}

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
						while(innerChoice==0){
							try{
								System.out.println("Enter Choice:-");
								innerChoice = scanner.nextInt();
								scanner.nextLine();			
							}
							catch(Exception e){
								innerChoice=0;
								System.out.println("Choose correct choice between 1-4");
								scanner.nextLine();
							}
						}
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