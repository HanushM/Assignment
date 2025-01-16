package emp.assignment;

import java.util.regex.*;
import java.util.*;

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
    public static int getEid(Employee[] employees) {
        while (true) {
            System.out.print("Enter EmployeeID:");
            try {
                int employeeId = new Scanner(System.in).nextInt();
                for (int i = 0; i < Employee.inCount; i++) {
                    if (employeeId == employees[i].getEid()) {
                        throw new EidException("EID Already Present, enter a correct ID");
                    }
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
    private int eid = 0;
    static int inCount = 0;
    static boolean ceoCreated = false; // Tracks if CEO is created

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

    public static Employee createEmployee(String designation, Employee[] employees) {
    while (true) {  // Keep prompting until valid data is entered
        try {
            // Ensure the CEO is created first
            if (designation.equals("Clerk") || designation.equals("Programmer") || designation.equals("Manager")) {
                if (!ceoCreated) {
                    throw new CeoNotCreatedException("First create the CEO before creating other employees.");
                }
            }

            // Ensure only one CEO is created
            if (designation.equals("Ceo") && ceoCreated) {
                throw new MultipleCeoCreationException("Only one instance of CEO can be created.");
            }

            int eid = EmployeeID.getEid(employees);  // Get Employee ID
            String name = EmployeeName.getName();    // Get Employee Name
            int age = Age.readAge();                 // Get Age

            Employee employee = null;
            // Create Employee based on the designation
            if (designation.equals("Ceo")) {
                employee = new Ceo();
                employee.setEid(eid);
                employee.setName(name);
                employee.setAge(age);
                employee.setSalary(200000);
                employee.setDesignation(designation);
                ceoCreated = true; // Set CEO creation flag to true
            } else if (designation.equals("Manager")) {
                employee = new Manager();
                employee.setEid(eid);
                employee.setName(name);
                employee.setAge(age);
                employee.setSalary(100000);
                employee.setDesignation(designation);
            } else if (designation.equals("Programmer")) {
                employee = new Programmer();
                employee.setEid(eid);
                employee.setName(name);
                employee.setAge(age);
                employee.setSalary(30000);
                employee.setDesignation(designation);
            } else if (designation.equals("Clerk")) {
                employee = new Clerk();
                employee.setEid(eid);
                employee.setName(name);
                employee.setAge(age);
                employee.setSalary(20000);
                employee.setDesignation(designation);
            }

            inCount++;  // Increment employee count
            return employee;  // Return the created employee

        } catch (CeoNotCreatedException | MultipleCeoCreationException e) {
            // Catch specific exceptions related to CEO creation
            System.out.println(e.getMessage());
	    return null;
        } catch (Exception e) {
            // Catch other general exceptions
            System.out.println("An error occurred: " + e.getMessage());
	    return null;
        }
    }
}


    final public void display() {
        System.out.println("EmployeeId of Employee: " + this.getEid());
        System.out.println("Name of Employee: " + this.getName());
        System.out.println("Age of Employee: " + this.getAge());
        System.out.println("Salary of Employee: " + this.getSalary());
        System.out.println("Designation of Employee: " + this.getDesignation());
    }

    abstract public void raiseSalary();

  public static Employee[] delete(Employee[] employees) {
        int delId = 0;
        Scanner scanner = new Scanner(System.in);
        while (delId == 0) {
            try {
                System.out.print("Enter Employee ID to Delete:");
                delId = scanner.nextInt();
            } catch (Exception e) {
                delId = 0;
                System.out.println(e);
                scanner.nextLine();
            }
        }
        boolean present = false;
        int index = 0;
        for (int i = 0; i < inCount; i++) {
            if (employees[i].getEid() == delId) {
                index = i;
                present = true;
            }
        }
        if (present) {
            employees[index].display();
            System.out.println("Do you really want to remove this employee record (y/n):");
            char deleteChoice = scanner.next(".").charAt(0);
            if (deleteChoice == 'y' || deleteChoice == 'Y') {
                // Shift remaining employees to fill the deleted one
                for (int j = index; j < inCount - 1; j++) {
                    employees[j] = employees[j + 1];
                }
                employees[inCount - 1] = null; // Nullify last employee
                inCount--; // Decrease employee count
            }
        } else {
            System.out.println("Invalid ID");
        }
        return employees;
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
    final public void raiseSalary() {
        this.setSalary(25000f);
    }
}

final class Manager extends Employee {
    final public void raiseSalary() {
        this.setSalary(15000f);
    }
}

final class Programmer extends Employee {
    final public void raiseSalary() {
        this.setSalary(5000f);
    }
}

final class Clerk extends Employee {
    final public void raiseSalary() {
        this.setSalary(2000f);
    }
}

public class EmployeeManagementApp {
    public static void main(String args[]) {
        int innerChoice = 0, outerChoice = 0;
        Employee employees[] = new Employee[50];

        do {
            outerChoice = 0;
            System.out.println("---------------------------------------------");
            System.out.println("1.Create");
            System.out.println("2.Display");
            System.out.println("3.Raise Salary");
            System.out.println("4.Remove");
            System.out.println("5.Exit");
            System.out.println("---------------------------------------------");
            Scanner scanner = new Scanner(System.in);
            outerChoice = Menu.readChoice(5);

            switch (outerChoice) {
                case (1): {
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
                            case (1): {
                            Employee createdEmployee = Employee.createEmployee("Ceo", employees);
                            if (createdEmployee != null) {
                                employees[Employee.inCount] = createdEmployee;
                            }
                            break;
                            }
                            case (2): {
                            Employee createdEmployee = Employee.createEmployee("Clerk", employees);
                            if (createdEmployee != null) {
                                employees[Employee.inCount] = createdEmployee;
                            }
                            break;
                            }
                            case (3): {
                            Employee createdEmployee = Employee.createEmployee("Programmer", employees);
                            if (createdEmployee != null) {
                                employees[Employee.inCount] = createdEmployee;
                            }
                            break;
                            }
                            case (4): {
                            Employee createdEmployee = Employee.createEmployee("Manager", employees);
                            if (createdEmployee != null) {
                                employees[Employee.inCount] = createdEmployee;
                            }
                            break;
                            }
                        }
                    } while (innerChoice != 5);
                    break;
                }
                case (2): {
                    for (int i = 0; i < Employee.inCount; i++) {
                        employees[i].display();
                    }
                    break;
                }
                case (3): {
                    for (int i = 0; i < Employee.inCount; i++) {
                        employees[i].raiseSalary();
                    }
                    break;
                }
                case (4): {
                    employees = Employee.delete(employees);
                    break;
                }
            }
        } while (outerChoice != 5);

        System.out.println("Total no.of employees created/added:" + Employee.inCount);
    }
}
