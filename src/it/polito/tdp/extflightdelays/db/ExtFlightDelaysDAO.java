package it.polito.tdp.extflightdelays.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.extflightdelays.model.Airline;
import it.polito.tdp.extflightdelays.model.Airport;
import it.polito.tdp.extflightdelays.model.Flight;
import it.polito.tdp.extflightdelays.model.Rotta;

public class ExtFlightDelaysDAO {

	public List<Airline> loadAllAirlines() {
		String sql = "SELECT * from airlines";
		List<Airline> result = new ArrayList<Airline>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				result.add(new Airline(rs.getInt("ID"), rs.getString("IATA_CODE"), rs.getString("AIRLINE")));
			}

			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public List<Airport> loadAllAirports() {
		String sql = "SELECT * FROM airports";
		List<Airport> result = new ArrayList<Airport>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Airport airport = new Airport(rs.getInt("ID"), rs.getString("IATA_CODE"), rs.getString("AIRPORT"),
						rs.getString("CITY"), rs.getString("STATE"), rs.getString("COUNTRY"), rs.getDouble("LATITUDE"),
						rs.getDouble("LONGITUDE"), rs.getDouble("TIMEZONE_OFFSET"));
				result.add(airport);
			}

			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
	public Map<Integer,Flight> mappaVoli(){
		Map<Integer,Flight> mappaVoli=new HashMap<Integer,Flight>();
		final String sql = "SELECT * FROM flights";
		try {
			Connection conn= DBConnect.getConnection();
			PreparedStatement st= conn.prepareStatement(sql);
			ResultSet rs= st.executeQuery();
			while(rs.next()) {
				//converto le date e l'ora da sql.date a time.localDateTime 
				
				LocalDateTime scheduledDeparture=LocalDateTime.of(rs.getDate(7).toLocalDate(),rs.getTime(7).toLocalTime());
				
				
				LocalDateTime arrivalDate=LocalDateTime.of(rs.getDate(11).toLocalDate(),rs.getTime(11).toLocalTime());
			
				
				
			
				mappaVoli.put(rs.getInt("ID"), new Flight(rs.getInt("ID"),rs.getInt("AIRLINE_ID") ,rs.getInt("FLIGHT_NUMBER"),rs.getString("TAIL_NUMBER"),rs.getInt("ORIGIN_AIRPORT_ID"), rs.getInt("DESTINATION_AIRPORT_ID"),scheduledDeparture,rs.getDouble(8),rs.getDouble(9),rs.getInt(10),arrivalDate,rs.getDouble(12)));
				
				
			}
		conn.close();
		
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	return mappaVoli;
	}
	public List<Rotta> getRotte(double distanzaMinima){
		List <Rotta> rotteRichieste= new LinkedList<Rotta>();
		final String sql = "SELECT f.ORIGIN_AIRPORT_ID,f.DESTINATION_AIRPORT_ID, AVG(f.DISTANCE) AS media from flights AS f  GROUP BY f.ORIGIN_AIRPORT_ID,f.DESTINATION_AIRPORT_ID HAVING (media>?)";
			try {Connection conn= DBConnect.getConnection();
		PreparedStatement st;
	
			st = conn.prepareStatement(sql);
		
		
		st.setDouble(1, distanzaMinima);
		ResultSet rs= st.executeQuery();
		while (rs.next()) {
			rotteRichieste.add(new Rotta(rs.getInt(1),rs.getInt(2),rs.getDouble(3)));
			
		}
		conn.close();
		} catch (SQLException e) {
			
			e.printStackTrace();
		
	}
	
	return rotteRichieste;
	}
}
