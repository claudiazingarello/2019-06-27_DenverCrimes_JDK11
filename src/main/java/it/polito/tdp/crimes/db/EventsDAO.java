package it.polito.tdp.crimes.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.crimes.model.Adiacenza;
import it.polito.tdp.crimes.model.Event;


public class EventsDAO {

	public List<Event> listAllEvents(){
		String sql = "SELECT * FROM events" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;

			List<Event> list = new ArrayList<>() ;

			ResultSet res = st.executeQuery() ;

			while(res.next()) {
				try {
					list.add(new Event(res.getLong("incident_id"),
							res.getInt("offense_code"),
							res.getInt("offense_code_extension"), 
							res.getString("offense_type_id"), 
							res.getString("offense_category_id"),
							res.getTimestamp("reported_date").toLocalDateTime(),
							res.getString("incident_address"),
							res.getDouble("geo_lon"),
							res.getDouble("geo_lat"),
							res.getInt("district_id"),
							res.getInt("precinct_id"), 
							res.getString("neighborhood_id"),
							res.getInt("is_crime"),
							res.getInt("is_traffic")));
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id"));
				}
			}

			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}

	public List<String> listAllCategories(){
		String sql = "SELECT DISTINCT e.offense_category_id AS cat FROM `events` AS e ORDER BY cat ASC" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;

			List<String> list = new ArrayList<>() ;

			ResultSet res = st.executeQuery() ;

			while(res.next()) {
				try {
					list.add(res.getString("cat"));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}

			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}

	public List<Integer> listAllMonth(){
		String sql = "SELECT DISTINCT MONTH(e.reported_date) AS mese FROM `events` AS e ORDER BY mese ASC" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;

			List<Integer> list = new ArrayList<>() ;

			ResultSet res = st.executeQuery() ;

			while(res.next()) {
				try {
					list.add((res.getInt("mese")));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}

			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}

	public List<String> getTipiReato(String categoria, Integer mese) {
		String sql = "SELECT DISTINCT e.offense_type_id AS tid " + 
				"FROM  `events` AS e " + 
				"WHERE e.offense_category_id = ? AND MONTH(e.reported_date) = ?" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setString(1, categoria);
			st.setInt(2, mese);

			List<String> list = new ArrayList<>() ;

			ResultSet res = st.executeQuery() ;

			while(res.next()) {
				try {
					list.add(res.getString("tid"));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}

			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}

	public List<Adiacenza> getAdiacenze(String categoria, Integer mese) {
		String sql = "SELECT e1.offense_type_id AS ot1, e2.offense_type_id AS ot2, COUNT(DISTINCT (e1.neighborhood_id)) AS peso " + 
				"FROM `events` AS e1, `events` AS e2 " + 
				"WHERE e1.offense_category_id = ? AND e2.offense_category_id = ? " + 
				"AND MONTH(e1.reported_date) = ? AND MONTH (e2.reported_date) = ? " + 
				"AND e1.offense_type_id < e2.offense_type_id AND e1.neighborhood_id = e2.neighborhood_id " + 
				"GROUP BY ot1, ot2" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setString(1, categoria);
			st.setString(2, categoria);
			st.setInt(3, mese);
			st.setInt(4, mese);

			List<Adiacenza> list = new ArrayList<>() ;

			ResultSet res = st.executeQuery() ;

			while(res.next()) {
				try {
					list.add(new Adiacenza(res.getString("ot1"), res.getString("ot2"), res.getInt("peso")));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}

			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
}
