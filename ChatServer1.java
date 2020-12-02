package ch20;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class ChatServer1 {
	
	//������ü�� ��� �ָӴ� Ŭ���� : Vector
	ServerSocket server;
	//CThread1�� �����ϴ� Vector
	Vector<CThread1> vc;
	
	//������
	public ChatServer1() {
		//�̸� try catch
		try {
			server = new ServerSocket(8001);//port ��ȣ 8001������
			vc=new Vector<CThread1>();
		} catch (Exception e) {
			System.err.println("Error in Server");
			e.printStackTrace();
			System.exit(1);//���������� ���� : ����1
		}
		System.out.println("***********************************");
		System.out.println("Ŭ���̾�Ʈ ������ ��ٸ��� �ֽ��ϴ�.");
		System.out.println("***********************************");
		
		try {
			while(true) {
				Socket sock = server.accept();
				CThread1 ct= new CThread1(sock); //���� ������ CThread1 !!!! �̴�.
				ct.start();
				//�������� ���� �Ŀ� CThread1 ��ü�� Vector�� ���� 
				vc.addElement(ct);
			}
		} catch (Exception e) {
			System.err.println("Error in Socket");
			e.printStackTrace();
		}
	}//---CThread1	
		//���ӵ� ��� Client(Vector �� ����� )���� �޼��� ����
		public void sendAllMessage(String msg) {
			for (int i = 0; i < vc.size(); i++) {
				//Vector�� ����� CThread1 �� �ϳ��� ���������� ���Ϲ޾Ƽ� 
				CThread1 ct = vc.get(i);
				ct.sendMessage(msg);
			}
		}

	
	class CThread1 extends Thread{
		
		Socket sock;
		BufferedReader in;
		PrintWriter out;
		String id;
		
		public CThread1(Socket sock) {
			try {
				this.sock = sock;
				in = new BufferedReader(new 
						InputStreamReader(sock.getInputStream()));
				out = new PrintWriter(
						sock.getOutputStream(),true/*auto flush*/);
				System.out.println(sock + " ���ӵ�...");// ���� �̺κ� �߰���
			} catch (Exception e) {
				e.printStackTrace();
			}
		}//---CThread1
		
		@Override
		public void run() {
			try {
				//Ŭ���̾�Ʈ�� �����Ŀ� ���ʷ� �޴� �޼���
				out.println("����Ͻ� ���̵� �Է��ϼ���");
				id = in.readLine();
				//��� ����� = Ŭ���̾�Ʈ = Client ���� �޼��� ����
				sendAllMessage("[" + id + "]"+" ���� �����Ͽ����ϴ�.");
				String data="";
				while(true) {
					data=in.readLine();
					if(data==null) break;
					sendAllMessage("[" + id + "]" + data);
				}
				in.close();
				out.close();
			} catch (Exception e) {
				//������ ���� ������ �������� Vector���� ���� ��ü�� �����ؾ��Ѵ�. 
				vc.remove(this); 
				System.err.println(sock+"������...");
				e.printStackTrace();
			}
		}
		//��ġ���� ��������� Ŭ�������� �ٲٸ� �ȴ�. ��ġ���� ������
		
		//Ŭ���̾�Ʈ���� ������ �޼ҵ� �߰�, ����� Client���� �޼��� ������ ���
		public void sendMessage(String msg) {
			out.println(msg);
		}
	}
	
	public static void main(String[] args) {
		new ChatServer1();
	}
}


















