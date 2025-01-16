class A {
	private static A a1 = null;
	private boolean a = false;
	A(){
		try{
			if(this instanceof B){a=true;}
			else if(this instanceof A && a1==null){a1=this;}
			else{throw new NullPointerException();}
		}
		catch(Exception e){
			System.out.println(e);
		}
	}
	public static A getA(){
		if(a1==null){a1=new A();}
		return a1;
	}
}

class B extends A{
	private static final B b1 = new B();
	private B(){
		//System.out.println("class B constructor called");
	}
	public static B getB(){
		return b1;
	}
}

public class Singleton{
	public static void main(String args[]){
		A a1 = A.getA();
		A a3 = new A();
		B b1 = B.getB();
		A a2 = A.getA();
		B b2 = B.getB();
		
		System.out.println(a1);	
		System.out.println(a2);	
		System.out.println(b1);	
		System.out.println(b2);		
	}
}