class A{
	public void abc(){
		for(int i=1;i<=10;i++){
			System.out.println("A:"+i);
		}
	}
}

class B{
	public void evenTill50(){
		for(int i=1;i<=50;i+=2){
			System.out.println("Even:"+i);
		}
	}
}
class C{
	public void fibonacciFrom1to1000(){
		long n1 =0 ,n2 =1 ,n3 =0;
		System.out.println("Fibonacci:"+n1);
		System.out.println("Fibonacci:"+n2);
		for(int i=0;i<1000;i++){
			n3=n1+n2;
			System.out.println("Fibonacci:"+n3);
			n1=n2;
			n2=n3;
		}
			
	}
}
public class Multithreading{
	public static void main(String args[]){
	A a1 = new A();
	B b1 = new B();
	C c1 = new C();
	new Thread(new Runnable(){
		public void run(){
			a1.abc();
		}
	}).start();
	new Thread(new Runnable(){
		public void run(){
			b1.evenTill50();
		}
	}).start();
	new Thread(new Runnable(){
		public void run(){
			c1.fibonacciFrom1to1000();
		}
	}).start();
		
	}


	new Thread(new A()::print1to10).start();
	new Thread(new B()::evenTill50).start();
	new Thread(new C()::fibonacciFrom1to1000).start();
}