package dao;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

public class DBConnection {

	private static Connection cn= null;
	private static  String user = "root";
	private static String password= "";
	private static String host = "jdbc:mysql://localhost/parking";

	public static Connection Connect() {

		try {

			Class.forName("com.mysql.jdbc.Driver");

			cn =(Connection) DriverManager.getConnection(host,user,password);
			System.out.println(cn.toString());
			return cn;

		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();


		}


		return null;


	}



	public static void Disconnect() {
		try {
			cn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



}