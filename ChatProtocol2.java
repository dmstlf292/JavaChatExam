package ch20;


public class ChatProtocol2 {


		//(C->S) C에서 S로: ID:aaa ----> 클라이언트가 서버로 ID 넘기는것 ( : -> 구분하는것) 
		//(S->C) CHATLIST:aaa;bbb;ccc;홍길동; ------> 클라이언트가 서버로부터 받아서 분석하는것
	
		public final static String  /* 상수는 대문자로 표기한다*/ ID="ID";//1번째 프로토콜
		
		//(C->S) CHAT : 받는 아이디; 채팅내용
		//(S->C) CHAT : 보낸 아이디; 채팅내용
		public final static String CHAT="CHAT";//2번째 프로토콜

		//(C->S) CHATALL : 채팅내용
		//(S->C) CHATALL : [보낸 아이디] 채팅내용
		public final static String CHATALL="CHATALL";//3번째 프로토콜
		
		
		//(C->S) MESSAGE : 받는 아이디; 쪽지내용
		//(S->C) MESSAGE : 받는 아이디; 쪽지내용
		public final static String MESSAGE="MESSAGE";//4번째 프로토콜
		
		//(S->C) CHATLIST :aaa;bbb;ccc;홍길동;
		public final static String CHATLIST="CHATLIST";//5번째 프로토콜
}
