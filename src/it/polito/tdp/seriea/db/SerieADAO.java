package it.polito.tdp.seriea.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.seriea.model.Match;
import it.polito.tdp.seriea.model.Season;
import it.polito.tdp.seriea.model.Team;
import it.polito.tdp.seriea.model.TeamPair;

public class SerieADAO {
	
	public List<Season> listSeasons() {
		String sql = "SELECT season, description FROM seasons" ;
		
		List<Season> result = new ArrayList<>() ;
		
		Connection conn = DBConnect.getConnection() ;
		
		try {
			PreparedStatement st = conn.prepareStatement(sql) ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				result.add( new Season(res.getInt("season"), res.getString("description"))) ;
			}
			
			conn.close();
			return result ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Team> listTeams() {
		String sql = "SELECT team FROM teams" ;
		
		List<Team> result = new ArrayList<>() ;
		
		Connection conn = DBConnect.getConnection() ;
		
		try {
			PreparedStatement st = conn.prepareStatement(sql) ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				result.add( new Team(res.getString("team"))) ;
			}
			
			conn.close();
			return result ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}

	public List<Team> getTeamForSeason(Season season) {
		
		String sql = "select distinct HomeTeam " + 
				"from matches " + 
				"where season = ?";
		
		List<Team> result = new ArrayList<>() ;

		Connection conn = DBConnect.getConnection() ;
		
		try {
			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, season.getSeason());
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				result.add( new Team(res.getString("HomeTeam"))) ;
			}
			
			conn.close();
			return result ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
		
	}

	public List<TeamPair> getTeamPair(Season season) {
		String sql = "select distinct HomeTeam, AwayTeam, FTR  " + 
				"from matches " + 
				"where season = ?";
		
		List<TeamPair> result = new ArrayList<>() ;

		Connection conn = DBConnect.getConnection() ;
		
		try {
			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, season.getSeason());
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				result.add( new TeamPair(res.getString("HomeTeam"), res.getString("AwayTeam"), res.getString("FTR"))) ;
			}
			
			conn.close();
			return result ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
		
		
	}
	
	public List<Match> getMatch(Season season, Map<String, Team> mappaTeam ){
		
		String sql ="select distinct * " + 
				"from matches " + 
				"where Season = ?";
		
		List<Match> result = new ArrayList<>() ;

		Connection conn = DBConnect.getConnection() ;
		
		try {
			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, season.getSeason());
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				Team homeTeam = mappaTeam.get(res.getString("HomeTeam"));
				Team awayTeam = mappaTeam.get(res.getString("AwayTeam"));
						
				Match match = new Match (res.getInt("match_id"), season, res.getString("Div"), res.getDate("Date").toLocalDate(), homeTeam, awayTeam, res.getInt("FTHG"), res.getInt("FTAG"), res.getString("FTR"));
				
				result.add(match);
			}
			
			conn.close();
			return result ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
		
	}


}