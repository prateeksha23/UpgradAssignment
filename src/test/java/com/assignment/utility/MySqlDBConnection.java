package com.assignment.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.testng.Reporter;

public class MySqlDBConnection {
	Connection con=null;
	ResultSet rs;
	
	/**
	 * initialize database connection
	 */
	public void createConnection(){
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con=DriverManager.getConnection( "jdbc:mysql://localhost:3306/imdb?useSSL=false","root","admin");   
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Insert the movies data in database
	 * and update the data if already exist
	 * @param name - name of movie
	 * @param year - release year of movie
	 * @param rating - rating of movie
	 */
	public void insertRow(int ranking, String name, String year, String rating){
		String stmt = " INSERT INTO popularmovies (ranking, movie_name, releaseyear, rating) values ("+ranking+",\""+name+"\",'"+year+"','"+rating+"' ) ON DUPLICATE KEY UPDATE rating='"+rating+"'";
		
	      try {
	    	  PreparedStatement preparedStmt = con.prepareStatement(stmt);
		      preparedStmt.execute();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}     
	}

	/**
	 * Closes the opened connection
	 */
	public void closeConnection() {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * @return the count of movies from db
	 */
	public int getCountOfMovies() {
		Statement stmt;
		int rowCount = 0;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT COUNT(*) FROM popularmovies");
		    // get the number of rows from the result set
		    rs.next();
		    rowCount = rs.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		
	    return rowCount;
	}

	public void printMoviesData() {
		String query = "SELECT * FROM popularmovies";
		Statement st;
		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			 while (rs.next())
		      {
		        int ranking = rs.getInt("ranking");
		        String movieName = rs.getString("movie_name");
		        String releaseDate = rs.getString("releasedate");
		        String rating = rs.getString("rating");
		        Reporter.log("Movie Name : "+movieName, true);
		        Reporter.log("Movie Rating : "+rating, true);
		        Reporter.log("Movie Release Year : "+ releaseDate, true);
		        Reporter.log("Movie Ranking : "+ ranking, true);      
		      }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	}
	
}
