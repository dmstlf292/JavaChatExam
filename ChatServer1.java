package ch20;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class ChatServer1 {
	
	//여러객체를 담는 주머니 클래스 : Vector
	ServerSocket server;
	//CThread1을 저장하는 Vector
	Vector<CThread1> vc;
	
	//생성자
	public ChatServer1() {
		//미리 try catch
		try {
			server = new ServerSocket(8001);//port 번호 8001고정됨
			vc=new Vector<CThread1>();
		} catch (Exception e) {
			System.err.println("Error in Server");
			e.printStackTrace();
			System.exit(1);//비정상적인 종료 : 숫자1
		}
		System.out.println("***********************************");
		System.out.println("클라이언트 접속을 기다리고 있습니다.");
		System.out.println("***********************************");
		
		try {
			while(true) {
				Socket sock = server.accept();
				CThread1 ct= new CThread1(sock); //묶는 단위가 CThread1 !!!! 이다.
				ct.start();
				//정상적인 접속 후에 CThread1 객체는 Vector에 저장 
				vc.addElement(ct);
			}
		} catch (Exception e) {
			System.err.println("Error in Socket");
			e.printStackTrace();
		}
	}//---CThread1	
		//접속된 모든 Client(Vector 에 저장됨 )에게 메세지 전송
		public void sendAllMessage(String msg) {
			for (int i = 0; i < vc.size(); i++) {
				//Vector에 저장된 CThread1 를 하나씩 순차적으로 리턴받아서 
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
				System.out.println(sock + " 접속됨...");// 여기 이부분 추가함
			} catch (Exception e) {
				e.printStackTrace();
			}
		}//---CThread1
		
		@Override
		public void run() {
			try {
				//클라이언트가 접속후에 최초로 받는 메세지
				out.println("사용하실 아이디를 입력하세요");
				id = in.readLine();
				//모든 사용자 = 클라이언트 = Client 에게 메세지 전달
				sendAllMessage("[" + id + "]"+" 님이 입장하였습니다.");
				String data="";
				while(true) {
					data=in.readLine();
					if(data==null) break;
					sendAllMessage("[" + id + "]" + data);
				}
				in.close();
				out.close();
			} catch (Exception e) {
				//문제가 생겨 연결이 끊어지면 Vector에게 현재 객체를 제거해야한다. 
				vc.remove(this); 
				System.err.println(sock+"끊어짐...");
				e.printStackTrace();
			}
		}
		//배치파일 만들었던거 클래스명만 바꾸면 된다. 배치파일 만들어보기
		
		//클라이언트에게 보내는 메소드 추가, 연결된 Client에게 메세지 보내는 기능
		public void sendMessage(String msg) {
			out.println(msg);
		}
	}
	
	public static void main(String[] args) {
		new ChatServer1();
	}
}



















