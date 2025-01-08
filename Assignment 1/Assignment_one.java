import java.util.*;

abstract class Employee
{
	private String name;
	private int age;
	private float salary;
	private String designation;
	Employee(String name,int age,float salary,String designation)
	{
		this.name=name;
		this.age=age;
		this.salary=salary;
		this.designation=designation;
	}
	public void display()
	{
		System.out.println("Name of Employee: "+this.name);
		System.out.println("Age of Employee: "+this.age);
		System.out.println("Salary of Employee: "+this.salary);
		System.out.println("Designation of Employee: "+this.designation);
	}
	public void raisesalary(int amount)
	{
		this.salary = this.salary+amount;
		this.display();
	}
}
class Manager extends Employee
{
	Manager(String name,int age,float salary,String designation){
		super(name,age,salary,designation);
	}
	
}
class Programmer extends Employee
{
	Programmer(String name,int age,float salary,String designation){
		super(name,age,salary,designation);
	}
}
class Clerk extends Employee
{
	Clerk(String name,int age,float salary,String designation){
		super(name,age,salary,designation);
	}
}

class Assignment_one
{
	public static void main(String args[])
	{
		Scanner scanner=new Scanner(System.in);
		System.out.println("Enter raisesalary:");
		Integer amount=scanner.nextInt();
		Employee emp1 = new Manager("ajai",18,18000,"Manager");
		Employee emp2 = new Programmer("aniruth",18,18000,"Programmer");
		Employee emp3 = new Clerk("ram",18,18000,"Clerk");
		emp1.raisesalary(amount);
		emp2.raisesalary(amount);
		
	}
}
