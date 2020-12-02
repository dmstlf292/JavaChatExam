package ch20;

class Thread1 extends Thread{
	
	String name;
	
	public Thread1(String name) {
		this.name = name;
	}
	
	@Override
	public void run() {
		try {
			for (int i = 0; i < 10; i++) {
				System.out.println(name + " : " + i);
				Thread.sleep(500);//0.5초당 쉬어라
			}
		} catch (Exception e) {}
	}
	
}

class NoThread1{
	String name;
	
	public NoThread1(String name) {
		this.name = name;
	}
	public void run() {
		try {
			for (int i = 0; i < 10; i++) {
				System.out.println(name + " : " + i);
				Thread.sleep(500);//0.5초당 쉬어라
			}
		} catch (Exception e) {}
	}
	public void start() {
		run();
	}
}

public class ThreadEx1 {
	public static void main(String[] args) {
		Thread1 t1 = new Thread1("Firsrt");
		Thread1 t2 = new Thread1("Second");
		//t1.start();//내부적으로 run() 호출 & 내부적으로 run() 호출, 멀티 기능 thread 가 안된다.
		//t2.start();//start매소드는 thread 스케줄러에게 등록을 하는것 ->등록후 스케줄러가 run 메소드 호출을 한다.
		////////////////////////////////
		NoThread1 n1 = new NoThread1("첫번째");
		NoThread1 n2 = new NoThread1("두번째");
		n1.start();
		n2.start();
	}
}



