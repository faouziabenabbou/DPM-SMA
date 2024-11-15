package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;

import model.Parking;

public class parkingDAO {
	
	public static boolean insertParking(Parking p) {
		Connection con = DBConnection.Connect();

		try {

			String req="INSERT INTO `parking` (`idregion`, `prix`, `nbrplaces`, `nbrplacesdispo`) VALUES ('"+p.idRegion+"', '"+p.prix+"', '"+p.nbrplaces+"', '"+p.nbrplacesdispo+"')";
			Statement st = con.createStatement();
			int rs = st.executeUpdate(req);

			if (rs > 0)
			{
				DBConnection.Disconnect();
				return true;
			}

		} catch (Exception e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		DBConnection.Disconnect();
		return false;
	}
	
	public static Parking getParking(int id) {
		Connection con = DBConnection.Connect();
		Parking p = new Parking();
		System.out.println("parkingdao id: "+id);
		
		try {
			String req = "SELECT * from parking where id="+id+";";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(req);
			
			if ( rs.next()) {
				
				p.id=rs.getInt("id");
				p.idRegion = rs.getInt("idregion");
				p.prix = rs.getInt("prix");
				p.nbrplaces= rs.getInt("nbrplaces");
				p.nbrplacesdispo=rs.getInt("nbrplacesdispo");
				System.out.println("dao nbrplace :" +p.nbrplaces);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DBConnection.Disconnect();
		return p;
	}
	
	public static List<Parking> getParkingOfRegion(int idregion) {
		Connection con = DBConnection.Connect();
		List<Parking> listparking = new ArrayList<Parking>();
		
		try {
			String req = "SELECT * from parking where idregion="+idregion+";";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(req);
			
			while ( rs.next()) {
				Parking p = new Parking();
				p.id=rs.getInt("id");
				p.idRegion = rs.getInt("idregion");
				p.prix = rs.getInt("prix");
				p.nbrplaces= rs.getInt("nbrplaces");
				p.nbrplacesdispo=rs.getInt("nbrplacesdispo");
				listparking.add(p);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DBConnection.Disconnect();
		return listparking;
	}
	
	public static Boolean updatePlacesDispo(int id, int nb) {
		System.out.println("parking dao id: " +id + " nb "+nb);
		Connection con = DBConnection.Connect();
			try {
				
				String req = "UPDATE `parking`.`parking` SET `nbrplacesdispo`="+ nb +" WHERE `id`="+ id + ";";
				Statement st = con.createStatement();
				int rs = st.executeUpdate(req);
				System.out.println("result of update parking : "+rs);
				
				if (rs>0) {
					System.out.println("parking dao; update done!");
					DBConnection.Disconnect();
					return true;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			DBConnection.Disconnect();
			return false;
	}
}
