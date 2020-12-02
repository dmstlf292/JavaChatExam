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
				Thread.sleep(500);//0.5�ʴ� �����
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
				Thread.sleep(500);//0.5�ʴ� �����
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
		//t1.start();//���������� run() ȣ�� & ���������� run() ȣ��, ��Ƽ ��� thread �� �ȵȴ�.
		//t2.start();//start�żҵ�� thread �����ٷ����� ����� �ϴ°� ->����� �����ٷ��� run �޼ҵ� ȣ���� �Ѵ�.
		////////////////////////////////
		NoThread1 n1 = new NoThread1("ù��°");
		NoThread1 n2 = new NoThread1("�ι�°");
		n1.start();
		n2.start();
	}
}



