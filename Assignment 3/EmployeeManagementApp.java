package emp.assignment;

import java.util.*;

abstract class Employee
{
	private String name;
	private int age;
	private float salary;
	private String designation;
	private int eid;
	static int count=0;	

	Employee(float salary,String designation)
	{
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter Employee ID:");
		int eid = sc.nextInt();	
		sc.nextLine();	
		System.out.println("Enter name:");
		String name = sc.nextLine();
		System.out.println("Enter age:");
		int age = sc.nextInt();
		sc.nextLine();

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
		for(int i=0;i<count;i++){
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
				for(int j=index;j<count-1;j++){
					employees[j]=employees[j+1];
				}
				employees[count - 1] = null;
				count-=1;
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
	Manager()
	{
		super(100000,"Manager");
	}
	final public void raiseSalary()
	{
		this.setSalary(15000f);
	}	
}
final class Programmer extends Employee
{
	Programmer()
	{
		super(30000,"Programmer");
	}
	final public void raiseSalary()
	{
		this.setSalary(5000f);
	}
}
final class Clerk extends Employee
{
	Clerk()
	{
		super(20000,"Clerk");
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
		do{
			System.out.println("---------------------------------------------");	
			System.out.println("1.Create");
			System.out.println("2.Display");
			System.out.println("3.Raise Salary");
			System.out.println("4.Remove");
			System.out.println("5.Exit");
			System.out.println("---------------------------------------------");
			System.out.println("Enter Choice:-");
			Scanner scanner = new Scanner(System.in);
			outerChoice = scanner.nextInt();
			scanner.nextLine();
			switch(outerChoice)
			{
				case(1):
				{
					do{
						System.out.println("---------------------------------------------");
						System.out.println("1.Clerk");
						System.out.println("2.Programmer");
						System.out.println("3.Manager");
						System.out.println("4.Exit");
						System.out.println("---------------------------------------------");
						System.out.println("Enter Choice:-");
						innerChoice = scanner.nextInt();
						scanner.nextLine();
						switch(innerChoice)
						{
							case(1):
							{	
								employees[Employee.count]=new Clerk();
								Employee.count+=1;
								break;
							}
							case(2):
							{	
								employees[Employee.count]=new Programmer();
								Employee.count+=1;
								break;
							}
							case(3):
							{
								employees[Employee.count]=new Manager();
								Employee.count+=1;
								break;	
							}
						}
					}while(innerChoice!=4);
					break;
				}
				case(2):
				{
					for(int i=0;i<Employee.count;i++){
						employees[i].display();
					}
					break;
				}
				case(3):
				{
					for(int i=0;i<Employee.count;i++){
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

		System.out.println("Total no.of employees created/added:"+Employee.count);
	}
}