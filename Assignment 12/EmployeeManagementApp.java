package emp.assignment;

import java.util.regex.*;
import java.util.*;
import java.io.*;

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
    public static int createEID(HashMap employees) {
        while (true) {
            System.out.print("Enter EmployeeID:");
            try {
                int employeeId = new Scanner(System.in).nextInt();
                if (employees.containsKey(employeeId)) {
                        throw new EidException("EID Already Present, enter a correct ID");
                   }
                return employeeId;
            } catch (EidException e) {
                System.out.println(e.getMessage());
            } catch (InputMismatchException e) {
                System.out.println("Please enter numbers only");
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

abstract class Employee {
    private String name;
    private int age;
    private float salary;
    private String designation;
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

    public static Employee createEmployee(String designation, HashMap employees,boolean interactive) {
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
	if (!interactive) {
                Employee employee = null;
                int eid = 0;
                String name = "Unknown"; 
                int age = 30;  
                float salary = 0.0f;  

                if (designation.equals("Ceo")) {
                    employee = Ceo.getInstance();
                    employee.setEid(eid);
                    employee.setName(name);
                    employee.setAge(age);
                    employee.setSalary(200000);
                    employee.setDesignation(designation);
                    ceoCreated = true;
                } else if (designation.equals("Manager")) {
                    employee = Manager.getInstance();
                    employee.setEid(eid);
                    employee.setName(name);
                    employee.setAge(age);
                    employee.setSalary(100000);
                    employee.setDesignation(designation);
                } else if (designation.equals("Programmer")) {
                    employee = Programmer.getInstance();
                    employee.setEid(eid);
                    employee.setName(name);
                    employee.setAge(age);
                    employee.setSalary(30000);
                    employee.setDesignation(designation);
                } else if (designation.equals("Clerk")) {
                    employee = Clerk.getInstance();
                    employee.setEid(eid);
                    employee.setName(name);
                    employee.setAge(age);
                    employee.setSalary(20000);
                    employee.setDesignation(designation);
                }

                inCount++;
                return employee;
            }

            Employee employee = null;
            
            int eid = EmployeeID.createEID(employees);  
            String name = EmployeeName.getName();    
            int age = Age.readAge();

            if (designation.equals("Ceo")) {
                employee = Ceo.getInstance();
                employee.setEid(eid);
                employee.setName(name);
                employee.setAge(age);
                employee.setSalary(200000);
                employee.setDesignation(designation);
                ceoCreated = true; 
            } else if (designation.equals("Manager")) {
                employee = Manager.getInstance();
                employee.setEid(eid);
                employee.setName(name);
                employee.setAge(age);
                employee.setSalary(100000);
                employee.setDesignation(designation);
            } else if (designation.equals("Programmer")) {
                employee = Programmer.getInstance();
                employee.setEid(eid);
                employee.setName(name);
                employee.setAge(age);
                employee.setSalary(30000);
                employee.setDesignation(designation);
            } else if (designation.equals("Clerk")) {
                employee = Clerk.getInstance();
                employee.setEid(eid);
                employee.setName(name);
                employee.setAge(age);
                employee.setSalary(20000);
                employee.setDesignation(designation);
            }

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
    final public static void display(HashMap employees) {
	Set displayValues = employees.entrySet();
	Iterator displayIterator = displayValues.iterator();
	while(displayIterator.hasNext()){
		Map.Entry mapValues = (Map.Entry)displayIterator.next();
		Employee emp =  (Employee) mapValues.getValue();
		System.out.println("EmployeeId of Employee: " + mapValues.getKey());
        	System.out.println("Name of Employee: " + emp.getName());
        	System.out.println("Age of Employee: " + emp.getAge());
        	System.out.println("Salary of Employee: " + emp.getSalary());
        	System.out.println("Designation of Employee: " + emp.getDesignation());
		System.out.println("--------------------------------------");
	}
    }
	abstract public void raiseSalary();
	public static void delete(HashMap employees){
		int delId = 0;
		Scanner scanner = new Scanner(System.in);
		while(delId==0){
			try{
				System.out.print("Enter Employee ID to Delete:");
				delId = scanner.nextInt();
				if(! employees.containsKey(delId)){
					throw new EidException("EID Already Present, enter a correct ID");
				}
				employees.remove(delId);
				Employee.inCount--;
				System.out.println("Employee with ID " + delId + " deleted successfully.");
			}
			catch(Exception e){
				delId = 0;
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
    final public void raiseSalary() {
        this.setSalary(this.getSalary()+25000f);
    }
}

final class Manager extends Employee {
    private static final Manager instance = new Manager();

    private Manager() {} 

    public static Manager getInstance() {
        return instance; 
    }
    final public void raiseSalary() {
        this.setSalary(this.getSalary()+15000f);
    }
}

final class Programmer extends Employee {
    private static final Programmer instance = new Programmer();
    private Programmer() {} 
    public static Programmer getInstance() {
        return instance; 
    }
    final public void raiseSalary() {
        this.setSalary(this.getSalary()+5000f);
    }
}

final class Clerk extends Employee {
    private static final Clerk instance = new Clerk();
    private Clerk() {} 
    public static Clerk getInstance() {
        return instance;
    }
    final public void raiseSalary() {
        this.setSalary(2000f);
    }
}

class CSVFileStorageRetrival{
    public static void loadEmployeeDataFromFile(RandomAccessFile raf, HashMap<Integer, Employee> employees) {
        try {
            String line;
            while ((line = raf.readLine()) != null) {
                String[] data = line.split(" , ");  
                if (data.length >= 5) {
                    int eid = Integer.parseInt(data[0].trim());
                    String name = data[1].trim();
                    int age = Integer.parseInt(data[2].trim());
                    String designation = data[3].trim();
                    float salary = Float.parseFloat(data[4].trim());
                    Employee employee = Employee.createEmployee(designation, employees,false);
                    if (employee != null) {
                        employee.setEid(eid);
                        employee.setName(name);
                        employee.setAge(age);
                        employee.setSalary(salary);
                        employee.setDesignation(designation);
                        employees.put(eid, employee);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading employee data: " + e.getMessage());
        }
    }

    public static void updateEmployeeDataToFile(RandomAccessFile raf, HashMap<Integer, Employee> employees) {
        try {
            raf.setLength(0);
            for (Employee emp : employees.values()) {
                raf.writeBytes(emp.getEid() + " , " + emp.getName() + " , " +emp.getAge() + " , " +emp.getDesignation() + " , " +emp.getSalary() + " , \n");
            }
            System.out.println("Employee data updated successfully!");
        } catch (IOException e) {
            System.out.println("Error writing to employee data file: " + e.getMessage());
        }
    }

}


public class EmployeeManagementApp {
    public static void main(String args[]) {

        int innerChoice = 0, outerChoice = 0;
        HashMap<Integer, Employee> employees = new HashMap<>();
        Scanner scanner = new Scanner(System.in);  
   
        try {
            RandomAccessFile raf = new RandomAccessFile("EmployeeData.csv", "rw");
            CSVFileStorageRetrival.loadEmployeeDataFromFile(raf, employees);
	    raf.close();
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
                            System.out.println("5.Exit");
                            System.out.println("---------------------------------------------");

                            innerChoice = Menu.readChoice(5);

                            switch (innerChoice) {
                                case 1: {
                                    Employee createdEmployee = Employee.createEmployee("Ceo", employees,true);
                                    if (createdEmployee != null) {
                                        employees.put(createdEmployee.getEid(), createdEmployee);
                                    }
                                    break;
                                }
                                case 2: {
                                    Employee createdEmployee = Employee.createEmployee("Clerk", employees,true);
                                    if (createdEmployee != null) {
                                        employees.put(createdEmployee.getEid(), createdEmployee);
                                    }
                                    break;
                                }
                                case 3: {
                                    Employee createdEmployee = Employee.createEmployee("Programmer", employees,true);
                                    if (createdEmployee != null) {
                                        employees.put(createdEmployee.getEid(), createdEmployee);
                                    }
                                    break;
                                }
                                case 4: {
                                    Employee createdEmployee = Employee.createEmployee("Manager", employees,true);
                                    if (createdEmployee != null) {
                                        employees.put(createdEmployee.getEid(), createdEmployee);
                                    }
                                    break;
                                }
                            }
                        } while (innerChoice != 5);
                        break;
                    }
                    case 2: {
                        int displayChoice = 0;
                        Collection values = employees.values();
                        ArrayList<Employee> employeeList = new ArrayList<>(values);
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
                                    Collections.sort(employeeList, new IdSorter());
                                    break;
                                }
                                case 2: {
                                    Collections.sort(employeeList, new NameSorter());
                                    break;
                                }
                                case 3: {
                                    Collections.sort(employeeList, new AgeSorter());
                                    break;
                                }
                                case 4: {
                                    Collections.sort(employeeList, new SalarySorter());
                                    break;
                                }
                                case 5: {
                                    Collections.sort(employeeList, new DesignationSorter());
                                    break;
                                }
                            }

                            Iterator<Employee> iterator = employeeList.iterator();
                            while (iterator.hasNext()) {
                                Employee emp = iterator.next();
                                System.out.println("Employee ID: " + emp.getEid());
                                System.out.println("Employee Name: " + emp.getName());
                                System.out.println("Age: " + emp.getAge());
                                System.out.println("Salary: " + emp.getSalary());
                                System.out.println("Designation: " + emp.getDesignation());
                                System.out.println("---------------------------------------------");
                            }
                        } while (displayChoice != 6);
                        break;
                    }
                    case 3: {
                        Set raiseSalarySet = employees.entrySet();
                        Iterator raiseSalaryIterator = raiseSalarySet.iterator();
                        while (raiseSalaryIterator.hasNext()) {
                            Map.Entry mapValues = (Map.Entry) raiseSalaryIterator.next();
                            Employee emp = (Employee) mapValues.getValue();
                            emp.raiseSalary();
                        }
                        break;
                    }
                    case 4: {
                        Employee.delete(employees);
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

                            Collection values = employees.values();
                            ArrayList<Employee> employeeList = new ArrayList<>(values);
                            int index = 0;

                            searchChoice = Menu.readChoice(4);
                            switch (searchChoice) {
                                case 1: {
                                    System.out.print("Enter employee ID to search: ");
                                    int searchId = scanner.nextInt();
                                    scanner.nextLine(); 
                                    Collections.sort(employeeList, new IdSorter());
                                    for (int i = 0; i < employeeList.size(); i++) {
                                        Employee emp = employeeList.get(i);
                                        if (emp.getEid() == searchId) {
                                            index = i;
                                            break;
                                        }
                                    }
                                    break;
                                }
                                case 2: {
                                    System.out.print("Enter employee Name to search: ");
                                    String searchName = scanner.nextLine();
                                    Collections.sort(employeeList, new NameSorter());
                                    for (int i = 0; i < employeeList.size(); i++) {
                                        Employee emp = employeeList.get(i);
                                        if (emp.getName().equalsIgnoreCase(searchName)) {
                                            index = i;
                                            break;
                                        }
                                    }
                                    break;
                                }
                                case 3: {
                                    System.out.print("Enter employee Designation to search: ");
                                    String searchDesignation = scanner.nextLine();
                                    Collections.sort(employeeList, new DesignationSorter());
                                    for (int i = 0; i < employeeList.size(); i++) {
                                        Employee emp = employeeList.get(i);
                                        if (emp.getDesignation().equalsIgnoreCase(searchDesignation)) {
                                            index = i;
                                            break;
                                        }
                                    }
                                    break;
                                }
                            }
                            if (index >= 0) {
                                Employee emp = employeeList.get(index);
                                System.out.println("Employee Found: ");
                                System.out.println("Employee ID: " + emp.getEid());
                                System.out.println("Employee Name: " + emp.getName());
                                System.out.println("Age: " + emp.getAge());
                                System.out.println("Salary: " + emp.getSalary());
                                System.out.println("Designation: " + emp.getDesignation());
                            } else {
                                System.out.println("Employee not found.");
                            }
                        } while (searchChoice != 4);
                        break;
                    }
                }
            } while (outerChoice != 6);

            System.out.println("Total no.of employees created/added:" + Employee.inCount);
	    raf = new RandomAccessFile("EmployeeData.csv", "rw");
            CSVFileStorageRetrival.updateEmployeeDataToFile(raf, employees);
	    raf.close();
	    scanner.close();
	
        }34catch (Exception e) {
            System.out.println(e);
        }
	
    }
}

	
class IdSorter implements Comparator<Employee>{
	public int compare(Employee e1 , Employee e2){
		return Integer.compare(e1.getEid(),e2.getEid());
	}
}
class NameSorter implements Comparator<Employee>{
	public int compare(Employee e1 , Employee e2){
		return e1.getName().compareTo(e2.getName());
	}
}
class AgeSorter implements Comparator<Employee>{
	public int compare(Employee e1 , Employee e2){
		return Integer.compare(e1.getAge(),e2.getAge());
	}
}
class SalarySorter implements Comparator<Employee>{
	public int compare(Employee e1, Employee e2){
		return Double.compare(e1.getSalary(),e2.getSalary());
	}
}
class DesignationSorter implements Comparator<Employee>{
	public int compare(Employee e1 , Employee e2){
		return e1.getDesignation().compareTo(e2.getDesignation());
	}
}


