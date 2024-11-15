package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;

import model.Region;

public class regionDAO {
	public static List<Region> getRegions() {
		Connection con = DBConnection.Connect();
		List<Region> listRegion = new ArrayList<Region>();
		
		try {
			String req = "Select * from region;";
			
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(req);
			
			while (rs.next()) {
				Region r = new Region();
				r.id=rs.getInt("id");
				r.nom=rs.getString("nom");
				r.nbrparking= rs.getInt("nbrparking");
				listRegion.add(r);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DBConnection.Disconnect();
		return listRegion;
	}
	
	public static int getIdregion(String name) {
		Connection con = DBConnection.Connect();
		int id = 0;
		
		try {
			String req = "Select id from region where nom='"+name+"';";

			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(req);
			
			if ( rs.next()) {
				 id=rs.getInt("id");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 DBConnection.Disconnect();
		 return id;
	}
	
	public static Region getregion(String name) {
		Connection con = DBConnection.Connect();
		Region r = new Region();
		
		try {
			String req = "Select * from region where nom='"+name+"';";

			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(req);
			
			if ( rs.next()) {
				r.id=rs.getInt("id");
				r.nom=rs.getString("nom");
				r.nbrparking= rs.getInt("nbrparking");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 DBConnection.Disconnect();
		 return r;
	}
	
}
