package ch20;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketAddress;

public class EchoFrameClient extends MFrame
implements ActionListener{
	
	Button btn1, btn2;
	TextField tf1, tf2;
	TextArea ta;
	Panel p1, p2;
	BufferedReader in;
	PrintWriter out;
	int port = 8000;
	
	public EchoFrameClient() {
		super(350,400);
		setTitle("EchoFrameClient");
		p1 = new Panel();
		p1.add(new Label("HOST ",Label.CENTER));
		p1.add(tf1 = new TextField("127.0.0.1",25));
		p1.add(btn1 = new Button("Connect"));
		
		p2 = new Panel();
		p2.add(new Label("CHAT ",Label.CENTER));
		p2.add(tf2 = new TextField("",25));
		p2.add(btn2 = new Button("SEND"));	
		
		tf1.addActionListener(this);
		tf2.addActionListener(this);
		btn1.addActionListener(this);
		btn2.addActionListener(this);
		
		add(p1,BorderLayout.NORTH);
		add(ta=new TextArea());
		add(p2,BorderLayout.SOUTH);
		validate();//갱신
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj==tf1||obj==btn1) {//Connect버튼 눌려서 접속-----------------2번
			//서버접속
			connect(tf1.getText().trim());
			//비활성화 시키기
			tf1.setEnabled(false);
			btn1.setEnabled(false);
			tf2.requestFocus();
		}else if(obj==tf2||obj==btn2) {//send 버튼 눌려서 전송하기 -----------------3번
			//chat창에 입력해서 보내기
			try {
				//tf2에 입력된 문자를 서버로 보낸다.
				out.println(tf2.getText()); // out.println( ) 괄호안에 tf2.getText() 안적으면  입력했던 텍스트가 화면에 출력안되어서 나온다.
				//서버에서 에코문자열이 온다. 
				ta.append(in.readLine()+"\n");
				tf2.setText("");
				tf2.requestFocus();
			} catch (Exception e2) {
				e2.printStackTrace(); //위에있는 (ActionEvent e)의 e랑 충돌되서 e2라고 설정
				
			}
		}
	}
	//ServerSock에서 접속 시도하는 기능
	public void connect(String host){ // 접속하는 메소드 connect------------1번
		try {
			Socket sock = new Socket(host,port);// port번호는 800으로 만들어놨다
			in = new BufferedReader(new 
					InputStreamReader(sock.getInputStream()));
			out = new PrintWriter(
					sock.getOutputStream(),true/*auto flush*/);
			//서버 접속시 최초로 받는 메시지를 ta에 나타나게 하기, 출력하기
			ta.append(in.readLine()+"\n");
			tf2.requestFocus();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new EchoFrameClient();
	}
}
