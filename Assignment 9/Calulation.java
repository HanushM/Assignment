import java.util.*;
import java.lang.*;
import java.lang.reflect.Method;
class Calculator
{
	Calculator(){
	
	}
	public int add(int a, int b)
	{
		return a+b;
	}
	public int sub(int a, int b)
	{

		return a-b;
	}
	public int mul(int a, int b)
	{
		return a*b;
	}
	public int div(int a, int b)
	{
		return a/b;
	}
}
 
public class Calulation{
	public static void main(String args[]){
		Scanner sc = new Scanner(System.in);
		while(true){
			System.out.println("Enter Operation:");
			String operation= sc.nextLine();
			System.out.println("Enter Parameter 1 :");
			int parameterOne = sc.nextInt();
			System.out.println("Enter Parameter 2 :");
			int parameterTwo = sc.nextInt();
			sc.nextLine();
			try{
				Class c2 = Class.forName("Calculator");
				Object obj = c2.getDeclaredConstructor().newInstance();
				Method[] methods = c2.getDeclaredMethods();
				for(Method method:methods){
					if(method.getName().equals(operation)){
						Object result = method.invoke(obj, parameterOne, parameterTwo);
	                    			System.out.println("Result: " + result);
						break;
						}
					}
			}
        		catch (Exception e) {
            			e.printStackTrace();
	        	}
		}
		sc.close();
	}
}
