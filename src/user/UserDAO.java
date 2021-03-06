package user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import util.DatabaseUtil;

public class UserDAO {
	//로그인함수
	public int login(String userID, String userPassword) {

		String SQL = "SELECT userPassword FROM user WHERE userID = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userID);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				
				if(rs.getString(1).contentEquals(userPassword)) {
					return 1; // 로그인성공시 1반환
				}else {
					return 0; // 비밀번호 불일치시 0반환
				}
			}
			return -1;//아이디없음 -1반환
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {if(conn != null) conn.close();}catch(Exception e) {}
			try {if(pstmt != null) pstmt.close();}catch(Exception e) {}
			try {if(rs != null) rs.close();}catch(Exception e) {}
		}
		return -2; //데이터베이스오류
	}
	//회원가입 함수
	public int join(UserDTO user) {
		String SQL = "INSERT INTO user(userNick, userID, userPassword, userBirth, userGender, userEmail) VALUES(?, ?, ?, ?, ?, ?);";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, user.getUserNick().replaceAll("<", "&lt;").replaceAll(">", "&gt").replaceAll("\r\n", "<br>"));
			pstmt.setString(2, user.getUserID().replaceAll("<", "&lt;").replaceAll(">", "&gt").replaceAll("\r\n", "<br>"));
			pstmt.setString(3, user.getUserPassword().replaceAll("<", "&lt;").replaceAll(">", "&gt").replaceAll("\r\n", "<br>"));
			pstmt.setString(4, user.getUserBirth().replaceAll("<", "&lt;").replaceAll(">", "&gt").replaceAll("\r\n", "<br>"));
			pstmt.setString(5, user.getUserGender().replaceAll("<", "&lt;").replaceAll(">", "&gt").replaceAll("\r\n", "<br>"));
			pstmt.setString(6, user.getUserEmail().replaceAll("<", "&lt;").replaceAll(">", "&gt").replaceAll("\r\n", "<br>"));
			return pstmt.executeUpdate();
		}catch(Exception e) {
			e.getStackTrace();
		}finally {
			try {if(conn != null) conn.close();}catch(Exception e) {}
			try {if(pstmt != null) pstmt.close();}catch(Exception e) {}
			try {if(rs != null) rs.close();}catch(Exception e) {}
		}
		return -1; //데이터베이스 삽입오류
	}
	//id를 이용해 email인증 여부 확인함수
	public boolean getUserEmailChecked(String userID) {
		String SQL = "SELECT userEmailChecked FROM user WHERE userID = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userID);
			rs = pstmt.executeQuery();
			System.out.println(rs.next());
			return rs.getBoolean(1);
		}catch(Exception e) {
			e.getStackTrace();
		}finally {
				try {
					if(conn != null)
					conn.close();
				}catch(Exception e) {}
				try {
					if(rs != null) 
					rs.close();
				}catch(Exception e) {}
				try {
					if(pstmt != null) 
					pstmt.close();
				}catch(Exception e) {}
		}return false;
	}
	//id를 이용해 email 확인함수
	public String getUserEmail(String userID) {
		String SQL = "SELECT userEmail FROM user WHERE userID = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userID);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getString(1);
			}return null;
		}catch(Exception e) {
			e.getStackTrace();
		}finally {
				try {
					if(conn != null)
					conn.close();
				}catch(Exception e) {}
				try {
					if(rs != null) 
					rs.close();
				}catch(Exception e) {}
				try {
					if(pstmt != null) 
					pstmt.close();
				}catch(Exception e) {}
		}return null;
	}
	//이메일 인증시 체크함수
	public boolean setUserEmailChecked(String userID) {
		String SQL = "UPDATE user SET userEmailChecked = true where userID= ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1,  userID);
			pstmt.executeUpdate();
			return true;//성공
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try{if(conn != null) conn.close();}catch(Exception e) {}
			try{if(pstmt != null) pstmt.close();}catch(Exception e) {}
			try{if(rs!= null) rs.close();}catch(Exception e) {}
		}
		return false;
	}
	//닉네임으로 아이디얻기
	public String getUserID(String userNick) {
		String SQL = "SELECT userID FROM user WHERE userNick=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1,  Integer.parseInt(userNick));
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getString(1);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try{if(conn != null) conn.close();}catch(Exception e) {}
			try{if(pstmt != null) pstmt.close();}catch(Exception e) {}
			try{if(rs!= null) rs.close();}catch(Exception e) {}
		}
		return null;//데이터베이스오류
	}
	//아이디로 닉네임얻기
	public String getUserNick(String userID) {
		String SQL = "SELECT userNick FROM user WHERE userID=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1,  userID);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getString(1);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try{if(conn != null) conn.close();}catch(Exception e) {}
			try{if(pstmt != null) pstmt.close();}catch(Exception e) {}
			try{if(rs!= null) rs.close();}catch(Exception e) {}
		}
		return null;//데이터베이스오류
	}
	//user정보변경하기
	public int statusModify(String userID, String newUserID, String newUserEmail, String newUserNick){
		String SQL = "UPDATE user SET userID = ?, userEmail = ?, userNick = ? where userID= ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, newUserID);
			pstmt.setString(2, newUserEmail);
			pstmt.setString(3, newUserNick);
			pstmt.setString(4, userID);
			pstmt.executeUpdate();
			return 1;//성공
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try{if(conn != null) conn.close();}catch(Exception e) {}
			try{if(pstmt != null) pstmt.close();}catch(Exception e) {}
			try{if(rs!= null) rs.close();}catch(Exception e) {}
		}
		return -1;//중복이메일
	}
}
