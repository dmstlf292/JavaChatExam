package ch20;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
	
	int cnt = 0;//Client의 접속갯수
	ServerSocket server;
	int port = 8000;//포트 번호
	
	public EchoServer() {
		try {
			server = new ServerSocket(port);
			System.out.println("ServerSocket Start**************");
			while(true) {
				//Client가 접속 할때까지 대기상태
				Socket sock = server.accept();//wait
				EchoThread et = new EchoThread(sock);
				et.start();//run메소드 호출
				cnt++;
				System.out.println("Client " + cnt + " Socket");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//접속된 Client를 대응하기 위해 Thread 상속 받은 클래스
	class EchoThread extends Thread{
		
		Socket sock;
		BufferedReader in;//Client가 보내는 메세지 받는 스트림
		PrintWriter out;//Client로 메세지 보내는 스트림
		
		public EchoThread(Socket sock) {
			try {
				this.sock = sock;
				in = new BufferedReader(new 
						InputStreamReader(sock.getInputStream()));
				out = new PrintWriter(
						sock.getOutputStream(),true/*auto flush*/);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		@Override
		public void run() {
			try {
				//Client가 첫메세지
				out.println("Hello Enter BYB to exit");
				while(true) {
					//Client에서 메세지가 들어 올때까지 대기 상태
					String line = in.readLine();
					if(line==null) { 
						break;//Client에서 접속을 끊으면 연결해제.
					}else {
						out.println("Echo : " + line);
						if(line.equals("BYB"))
							break;
					}
				}
				out.close();
				in.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public static void main(String[] args) {
		new EchoServer();
	}
}

