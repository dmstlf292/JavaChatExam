package ch20;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class ChatServer2 {

	Vector<CThread2> vc;
	ServerSocket server;
	int port = 8002;

	public ChatServer2() {
		try {
			server = new ServerSocket(port);
			vc = new Vector<CThread2>();
		} catch (Exception e) {
			System.err.println("Error in Server");
			e.printStackTrace();
			System.exit(1);// 비정상적인 종료
		}
		System.out.println("****************************");
		System.out.println("클라이언트의 접속을 기다리고 있습니다.");
		System.out.println("****************************");
		try {
			while (true) {
				Socket sock = server.accept();
				CThread2 ct = new CThread2(sock);
				ct.start();
				vc.add(ct);
			}
		} catch (Exception e) {
			System.err.println("Error in Socket");
			e.printStackTrace();
		}
	}

	// 접속된 모든 클라이언트에게 메세지 전송
	public void sendAllMessage(String msg) {
		for (int i = 0; i < vc.size(); i++) {
			CThread2 ct = vc.get(i);
			ct.sendMessage(msg);
		}
	}

	// Vector에 Client를 제거
	public void removeClient(CThread2 ct) {
		vc.remove(ct);
	}

	class CThread2 extends Thread {
		
		Socket sock;
		BufferedReader in;
		PrintWriter out;
		String id = "익명";
		
		public CThread2(Socket sock) {
			try {
				this.sock = sock;
				in = new BufferedReader(
						new InputStreamReader(
								sock.getInputStream()));
				out = new PrintWriter(
						sock.getOutputStream(),true);
				System.out.println(sock+" 접속됨....");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		@Override
		public void run() {
			try {
				out.println("사용하실 아이디를 입력하세요.");
				while(true) {
					String line = in.readLine();
					if(line==null) break;
					else routine(line);// 클라이언트부터 메시지 날라오면 프로토콜이 검색을 돌려서 클라이언트에게 적절한 대응을 하게끔 메소드 만다는것
				}
			} catch (Exception e) {
				//끊어진 Client는 Vector에서 제거한다.
				removeClient(this);
				System.err.println(sock+"["+id+"] 끊어짐...");
				//e.printStackTrace();
			}
			
		}
		//Client 로 부터 전송된 프로토콜 및 data 분석 메소드
		public void routine(String line) {
			//CHAT:bbb;밥먹자. //CHAT이 프로토콜, 뒤에가 데이터(프로토콜안에 들어가서 값들을 구분함)
			int idx = line.indexOf(':'); // ;가 아니라 : 이다!!!!!
			String cmd=line.substring(0,idx);//CHAT
			String data=line.substring(idx+1);//bbb;밥먹자.
			
			if(cmd.equals(ChatProtocol2.ID)) {
				if(data!=null&&data.length()>0) {
					//ID:홍길동
					id=data;
					//새로운 접속자가 추가가 되어서 -> List 재전송
					sendAllMessage(ChatProtocol2.CHATLIST+":"+getId());
					//새로운 접속자 환영 메세지
					sendAllMessage(ChatProtocol2.CHATALL+":"+"["+id+"] 님이 입장 하였습니다.");
				}
			}else if (cmd.equals(ChatProtocol2.CHAT)) {
				//CHAT:bbb;밥먹자.
				idx=data.indexOf(";");
				cmd=data.substring(0,idx);//bbb
				data=data.substring(idx+1);//밥먹자
				//bbb CThread2를 찾는다.
				CThread2 ct=findClient(cmd);
				if(ct!=null) {
					//bbb
					ct.sendMessage(ChatProtocol2.CHAT+":"+"["+id+"(S)]" + data);
					//aaa(중심 : 본인)
					sendMessage(ChatProtocol2.CHAT+":"+"["+id+"(S)]" + data);
				}else {
					//aaa 
					sendMessage(ChatProtocol2.CHAT+":"+"["+cmd+"] 접속자가 아닙니다." + data);
				}	
			}else if (cmd.equals(ChatProtocol2.MESSAGE)) {
				//MESSAGE:bbb;오늘은 짜장면OK?
				idx=data.indexOf(";");
				cmd=data.substring(0,idx);//bbb
				data=data.substring(idx+1);//오늘은 짜장면OK?
				CThread2 ct=findClient(cmd);
				if(ct!=null) {
					//bbb에게 쪽지를 보낸다.
					ct.sendMessage(ChatProtocol2.MESSAGE+":"+id+";"+data);
				}else {
					sendMessage(ChatProtocol2.CHAT+":"+"["+cmd+"] 접속자가 아닙니다." + data);
				}
			}else if (cmd.equals(ChatProtocol2.CHATALL)) {
				//CHATALL:[aaa] 모두 화이팅!!!
				sendAllMessage(ChatProtocol2.CHATALL+":"+"["+id+"]"+data);
			}
		}
		
		//매개변수로 받은 id값으로 CThread2를 찾기
		public CThread2 findClient(String id) {//내부클래스라서 가능함
			CThread2 ct = null;//CThread2가 bbb가 된다.
			for (int i = 0; i < vc.size(); i++) {
				ct=vc.get(i);
				//찾고자하는 id가 일치하면 뒤에 있는 것은 검색 불필요하다.
				if(ct.id.equals(id))
					break;
			}
			return ct;
		}
		
		//접속된 모든 id를 리턴(구분자는 ; ex)aaa;bbb;홍길동; ) 한다.
		///이부분 중요***************************************
		public String getIDs() {
			String ids="";
			for (int i = 0; i < vc.size(); i++) {
				CThread2 ct=vc.get(i);
				ids+=ct.id+";";
			}
			
			return ids;
		}
		
		public void sendMessage(String msg){
			out.println(msg);
		}
		
	}//---CThread2

	public static void main(String[] args) {
		new ChatServer2();
	}
}

