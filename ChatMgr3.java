package ch20;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ChatMgr3 {

	private DBConnectionMgr pool;
	public ChatMgr3() {
		pool = DBConnectionMgr.getInstance();
	}
		
	//�α���
	public boolean loginChk (String id, String pwd) {
		//db1 �Է��� ctrl+space
		Connection con = null;//DB ���� ��ü (�ܿ�����)
		PreparedStatement pstmt = null; //sql �� ����� ��ü
		ResultSet rs = null;//select �� ���� ����� ���� ��ü
		String sql = null; // �������� �� sql�� �ߴ°�?
		boolean flag=false;
		try {
			con = pool.getConnection();
			sql = "select id from tblChat where id=? and pwd=?";
			pstmt = con.prepareStatement(sql);
			//? ����ǥ ����
			pstmt.setString(1, id);
			pstmt.setString(2, pwd);
			rs = pstmt.executeQuery();
			flag=rs.next();//���ǿ� �´� ������� ������ true, ������ false
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return flag; // ����� flag
	}
}
