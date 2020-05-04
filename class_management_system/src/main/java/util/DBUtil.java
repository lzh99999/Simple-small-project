package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil {

	static{
		// 加载数据库驱动
				try {
					Class.forName("com.mysql.jdbc.Driver");
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
	}

	public static Connection getConnection() {
		Connection conn = null;
		try {
			// 获取数据库连接
			conn = DriverManager
					.getConnection("jdbc:mysql://localhost:3306/student_system?useUnicode=true&characterEncoding=UTF-8",
							"root", "");
	}catch (SQLException e) {
		e.printStackTrace();
	}
		return conn;
	}

	public static void release(Connection conn,Statement stmt ,ResultSet rs){
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(rs!=null){
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}