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
	public void raiseSalary(int amount)
	{
		this.salary = this.salary+amount;
		this.display();
	}
	public String getDesignation()
	{
		return this.designation;
	}
	public float getSalary()
	{
		return (float) this.salary;
	}
	public void setSalary(float amount)
	{
		this.salary=this.salary+amount;	
	}	
}
class Manager extends Employee
{
	private static final float SALARY=100000.0f;
	private static final String DESIGNATION="Manager";
	Manager(String name,int age)
	{
		super(name,age,SALARY,DESIGNATION);
	}	
}
class Programmer extends Employee
{
	private static final float SALARY=30000.0f;
	private static final String DESIGNATION="Programmer";
	Programmer(String name,int age)
	{
		super(name,age,SALARY,DESIGNATION);
	}
}
class Clerk extends Employee
{
	private static final float SALARY=20000.0f;
	private static final String DESIGNATION="Clerk";
	Clerk(String name,int age)
	{
		super(name,age,SALARY,DESIGNATION);
	}
}

class EmployeeManagementApp
{
	public static void main(String args[])
	{
		boolean outerLoop=true;
		boolean innerLoop=true;
		Employee employees[]=new Employee[50];
		int counter=0;
		while(outerLoop)
		{
			System.out.println("---------------------------------------------");	
			System.out.println("1.Create");
			System.out.println("2.Display");
			System.out.println("3.Raise Salary");
			System.out.println("4.Exit");
			System.out.println("---------------------------------------------");
			System.out.println("Enter Choice:-");
			Scanner scanner = new Scanner(System.in);
			int outerChoice = scanner.nextInt();
			switch(outerChoice)
			{
				case(1):
				{
					while(innerLoop)
					{
						System.out.println("---------------------------------------------");
						System.out.println("1.Clerk");
						System.out.println("2.Programmer");
						System.out.println("3.Manager");
						System.out.println("4.Exit");
						System.out.println("---------------------------------------------");
						System.out.println("Enter Choice:-");
						int innerChoice = scanner.nextInt();
						scanner.nextLine();
						switch(innerChoice)
						{
							case(1):
							{	
								System.out.println("Enter Name:");
								String name=scanner.nextLine();
								System.out.println("Enter Age:");
								int age = scanner.nextInt();
								scanner.nextLine();
								employees[counter]=new Clerk(name,age);
								counter++;
								break;
							}
							case(2):
							{
								System.out.println("Enter Name:");
								String name=scanner.nextLine();
								System.out.println("Enter Age:");
								int age = scanner.nextInt();
								scanner.nextLine();
								employees[counter]=new Programmer(name,age);
								counter++;
								break;
							}
							case(3):
							{
								System.out.println("Enter Name:");
								String name=scanner.nextLine();
								System.out.println("Enter Age:");
								int age = scanner.nextInt();
								scanner.nextLine();
								employees[counter]=new Manager(name,age);
								counter++;
								break;	
							}
							case(4):
							{
								innerLoop=false;
								break;
							}
						}
					}
					break;
				}
				case(2):
				{
					for(int i=0;i<counter;i++){
						employees[i].display();
					}
					break;
				}
				case(3):
				{
					for(int i=0;i<counter;i++){
						String designation = employees[i].getDesignation();
						if(designation=="Manager"){
							employees[i].setSalary(15000);
						}
						else if(designation=="Programmer"){
							employees[i].setSalary(5000);
						}
						else if(designation=="Clerk"){
							employees[i].setSalary(2000);
						}
						else{
							System.out.println("Invalid Designation");
						}
					}
					break;
				}
				case(4):
				{
					outerLoop=false;
					break;
				}
			}
		}
		System.out.println("Total no.of employees created/added:"+counter);
	}
}
