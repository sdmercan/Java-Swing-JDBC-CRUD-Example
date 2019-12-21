package daoImplements;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import connection.DbConnection;
import data.Departments;

public class DepartmentsDAOImplements implements dao.AllDAO<Departments> {
	
	DbConnection dbConn=new DbConnection();
	@Override
	public ResultSet getData() throws SQLException {
		Statement myStatement=(Statement)dbConn.getMyConn().createStatement();
		ResultSet myResultSet=myStatement.executeQuery("Select * from Departments");
		return myResultSet;
	}
	@Override
	public int add(Departments d) throws SQLException {
		
		CallableStatement csInsert = dbConn.getMyConn().prepareCall("{call ADD_DEPARTMENTS(?,?,?,?,?)}");
		
		csInsert.setInt(1, 0);
		csInsert.setString(2, d.getDepartmentName());
		csInsert.setInt(3, d.getManagerID());
		csInsert.setInt(4, d.getLocationID());
		
		csInsert.registerOutParameter(5, Types.INTEGER);
		csInsert.executeUpdate();
		
		return csInsert.getInt(5);	
	}

	
	@Override
	public int delete(int id) throws SQLException {
		CallableStatement cs = dbConn.getMyConn().prepareCall("{call DELETE_DEPARTMENTS(?,?)} ");
		cs.setInt(1, id);
		cs.registerOutParameter(2,Types.INTEGER);
		cs.executeUpdate();
		return cs.getInt(2);
	}
	@Override
	public int update(Departments d, int id) throws SQLException {
		
		CallableStatement csUpdate = dbConn.getMyConn().prepareCall("{call UPDATE_DEPARTMENTS(?,?,?,?,?)}");
		csUpdate.setInt(1, id);
		csUpdate.setString(2, d.getDepartmentName());
		csUpdate.setInt(3, d.getManagerID());
		csUpdate.setInt(4, d.getLocationID());
		csUpdate.registerOutParameter(5, Types.INTEGER);
		csUpdate.executeUpdate();
		
		return csUpdate.getInt(5);
	}
	@Override
	public int update(Departments t, String id) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int delete(String id) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}






}
