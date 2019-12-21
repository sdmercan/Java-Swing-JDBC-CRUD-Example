package daoImplements;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import connection.DbConnection;
import data.Regions;

public class RegionsDAOImplements implements dao.AllDAO<Regions> {
	
	DbConnection dbConn=new DbConnection();
	@Override
	public ResultSet getData() throws SQLException {
		Statement myStatement=(Statement)dbConn.getMyConn().createStatement();
		ResultSet myResultSet=myStatement.executeQuery("Select * from Regions");
		return myResultSet;
	}

	@Override
	public int add(Regions r) throws SQLException{
		CallableStatement csInsert = dbConn.getMyConn().prepareCall("{call ADD_REGIONS(?,?,?)}");
		csInsert.setInt(1, 0);
		csInsert.setString(2, r.getRegionName());
		csInsert.registerOutParameter(3, Types.INTEGER);
		csInsert.executeUpdate();
		return csInsert.getInt(3);

		
	}

	@Override
	public int update(Regions r,int id) throws SQLException {
		String regionName=r.getRegionName();
		CallableStatement csUpdate = dbConn.getMyConn().prepareCall("{call UPDATE_REGIONS(?,?,?)}");
		csUpdate.setInt(1, id);
		csUpdate.setString(2, r.getRegionName());
		csUpdate.registerOutParameter(3, Types.INTEGER);
		csUpdate.executeUpdate();
		return csUpdate.getInt(3);
		
	}

	@Override
	public int delete(int id) throws SQLException {
		CallableStatement csDelete = dbConn.getMyConn().prepareCall("{call DELETE_EMPLOYEES(?,?)}");
		csDelete.setInt(1, id);
		csDelete.registerOutParameter(2, Types.INTEGER);
		csDelete.executeUpdate();
	
		return csDelete.getInt(2);
	}

	@Override
	public int update(Regions t, String id) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(String id) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}




}