package ch20;


public class ChatProtocol2 {


		//(C->S) C���� S��: ID:aaa ----> Ŭ���̾�Ʈ�� ������ ID �ѱ�°� ( : -> �����ϴ°�) 
		//(S->C) CHATLIST:aaa;bbb;ccc;ȫ�浿; ------> Ŭ���̾�Ʈ�� �����κ��� �޾Ƽ� �м��ϴ°�
	
		public final static String  /* ����� �빮�ڷ� ǥ���Ѵ�*/ ID="ID";//1��° ��������
		
		//(C->S) CHAT : �޴� ���̵�; ä�ó���
		//(S->C) CHAT : ���� ���̵�; ä�ó���
		public final static String CHAT="CHAT";//2��° ��������

		//(C->S) CHATALL : ä�ó���
		//(S->C) CHATALL : [���� ���̵�] ä�ó���
		public final static String CHATALL="CHATALL";//3��° ��������
		
		
		//(C->S) MESSAGE : �޴� ���̵�; ��������
		//(S->C) MESSAGE : �޴� ���̵�; ��������
		public final static String MESSAGE="MESSAGE";//4��° ��������
		
		//(S->C) CHATLIST :aaa;bbb;ccc;ȫ�浿;
		public final static String CHATLIST="CHATLIST";//5��° ��������
}
