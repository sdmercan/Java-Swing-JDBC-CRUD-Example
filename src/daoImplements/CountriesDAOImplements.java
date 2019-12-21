package daoImplements;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import connection.DbConnection;
import data.Countries;

public class CountriesDAOImplements implements dao.AllDAO<Countries> {
	
	DbConnection dbConn=new DbConnection();
	@Override
	public ResultSet getData() throws SQLException {
		Statement myStatement=(Statement)dbConn.getMyConn().createStatement();
		ResultSet myResultSet=myStatement.executeQuery("Select * from countries");
		return myResultSet;
	}

	@Override
	public int add(Countries countries) throws SQLException {
		String countryId =countries.getCountryId();
		String countryName = countries.getCountryName();
		int regionID = countries.getRegionID();

		CallableStatement stmt = dbConn.getMyConn().prepareCall("{call ADD_COUNTRIES(to_char(?),?,?,?)}");
		stmt.setString(1, countryId);
		stmt.setString(2, countryName);
		if(regionID==0) {
			stmt.setString(3, null);
		}
		else {
			stmt.setInt(3, regionID);
		}
		stmt.registerOutParameter(4, Types.INTEGER);
		stmt.executeUpdate();
		return stmt.getInt(4);
		
	}

	@Override
	public int delete(int id) throws SQLException {
		return 0;
	}
	
	@Override
	public int update(Countries t, int id) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(Countries countries, String id) throws SQLException {
		
		String countryName = countries.getCountryName();
		int regionID = countries.getRegionID();

		CallableStatement stmt = dbConn.getMyConn().prepareCall("{call UPDATE_COUNTRIES(to_char(?),?,?,?)}");
		stmt.setString(1, id);
		stmt.setString(2, countryName);
		if(regionID==0) {
			stmt.setString(3, null);
		}
		else {
			stmt.setInt(3, regionID);
		}
		stmt.registerOutParameter(4, Types.INTEGER);
		stmt.executeUpdate();
		return stmt.getInt(4);
		
	}

	@Override
	public int delete(String id) throws SQLException {
		CallableStatement cs = dbConn.getMyConn().prepareCall("{call DELETE_COUNTRIES(to_char(?),?)} ");
		cs.setString(1, id);
		cs.registerOutParameter(2,Types.INTEGER);
		cs.executeUpdate();
		return cs.getInt(2);
		
	}

	



	
	


}
