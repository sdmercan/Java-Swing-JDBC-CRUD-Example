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
import data.Jobs;

public class JobsDAOImplements implements dao.AllDAO<Jobs> {
	
	DbConnection dbConn=new DbConnection();
	@Override
	public ResultSet getData() throws SQLException {
		Statement myStatement=(Statement)dbConn.getMyConn().createStatement();
		ResultSet myResultSet=myStatement.executeQuery("select * from jobs");
		return myResultSet;
	}

	@Override
	public int add(Jobs j) throws SQLException {
		String jobId =j.getJobId();
		String jobTitle = j.getJobTitle();
		int minSalary = j.getMinSalary();
		int maxSalary =j.getMaxSalary();

		CallableStatement csInsert = dbConn.getMyConn().prepareCall("call ADD_JOBS(?,?,?,?,?)");
		csInsert.setString(1, jobId);
		csInsert.setString(2, jobTitle);
		if(minSalary==0) {
			csInsert.setString(3, null);
		}
		else {
			csInsert.setInt(3, minSalary);
		}
		if(maxSalary==0) {
			csInsert.setString(4, null);
		}
		else {
			csInsert.setInt(4, maxSalary);
		}
		csInsert.registerOutParameter(5, Types.INTEGER);
		csInsert.executeUpdate();
		
		return csInsert.getInt(5);	

	}

	@Override
	public int update(Jobs j,int id) throws SQLException {
		return 0;

	}

	@Override
	public int delete(int id) throws SQLException {
		return 0;

	}

	@Override
	public int update(Jobs j, String id) throws SQLException {
		String jobId =j.getJobId();
		String jobTitle = j.getJobTitle();
		int minSalary = j.getMinSalary();
		int maxSalary =j.getMaxSalary();
	
		CallableStatement csUpdate = dbConn.getMyConn().prepareCall("call UPDATE_JOBS(?,?,?,?,?)");
		csUpdate.setString(1, jobId);
		csUpdate.setString(2, jobTitle);
		if(minSalary==0) {
			csUpdate.setString(3, null);
		}
		else {
			csUpdate.setInt(3, minSalary);
		}
		if(maxSalary==0) {
			csUpdate.setString(4, null);
		}
		else {
			csUpdate.setInt(4, maxSalary);
		}
		csUpdate.registerOutParameter(5, Types.INTEGER);
		csUpdate.executeUpdate();
		
		return csUpdate.getInt(5);
	}

	@Override
	public int delete(String id) throws SQLException {
		CallableStatement csDelete = dbConn.getMyConn().prepareCall("{call DELETE_JOBS(?,?)}");
		csDelete.setString(1, id);
		csDelete.registerOutParameter(2, Types.INTEGER);
		csDelete.executeUpdate();
	
		return csDelete.getInt(2);
	}


}