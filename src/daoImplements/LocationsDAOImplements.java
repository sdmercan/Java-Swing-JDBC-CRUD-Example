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
import data.Departments;
import data.Employees;
import data.Locations;

public class LocationsDAOImplements implements dao.AllDAO<Locations> {
	
	DbConnection dbConn=new DbConnection();
	@Override
	public ResultSet getData() throws SQLException {
		Statement myStatement=(Statement)dbConn.getMyConn().createStatement();
		ResultSet myResultSet=myStatement.executeQuery("Select * from locations");
		return myResultSet;
	}

	@Override
	public int add(Locations l) throws SQLException {
		CallableStatement csInsert = dbConn.getMyConn().prepareCall("{call ADD_LOCATIONS(?,?,?,?,?,?,?)}");
		csInsert.setInt(1, 0);
		csInsert.setString(2, l.getStreetAddress());
		csInsert.setString(3, l.getPostalCode());
		csInsert.setString(4, l.getCity());
		csInsert.setString(5, l.getStateProvince());
		csInsert.setString(6, l.getCountryID());
		csInsert.registerOutParameter(7, Types.INTEGER);
		csInsert.executeUpdate();
		
		return csInsert.getInt(7);
	}

	@Override
	public int update(Locations l, int id) throws SQLException {
		CallableStatement csUpdate = dbConn.getMyConn().prepareCall("{call UPDATE_LOCATIONS(?,?,?,?,?,?,?)}");
		csUpdate.setInt(1, 0);
		csUpdate.setString(2, l.getStreetAddress());
		csUpdate.setString(3, l.getPostalCode());
		csUpdate.setString(4, l.getCity());
		csUpdate.setString(5, l.getStateProvince());
		csUpdate.setString(6, l.getCountryID());
	
		csUpdate.registerOutParameter(7, Types.INTEGER);
		csUpdate.executeUpdate();
		
		return csUpdate.getInt(7);

	}

	@Override
	public int delete(int id) throws SQLException {
		CallableStatement cs = dbConn.getMyConn().prepareCall("{call DELETE_LOCATIONS(?,?)} ");
		cs.setInt(1, id);
		cs.registerOutParameter(2,Types.INTEGER);
		cs.executeUpdate();
		return cs.getInt(2);
	}

	@Override
	public int update(Locations t, String id) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(String id) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}


	

}