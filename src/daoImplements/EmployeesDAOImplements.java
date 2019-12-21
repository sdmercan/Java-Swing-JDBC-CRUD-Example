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
import data.Employees;

public class EmployeesDAOImplements implements dao.AllDAO<Employees> {

	DbConnection dbConn=new DbConnection();
	
	@Override
	public ResultSet getData() throws SQLException {
		Statement myStatement=(Statement)dbConn.getMyConn().createStatement();
		ResultSet myResultSet=myStatement.executeQuery("Select employee_id,first_name,last_name,email,phone_number,to_char(hire_date,'dd/mm/yyyy') hire_date,job_id,salary,commission_pct,manager_id,department_id from employees");
		return myResultSet;
	}
	
	@Override
	public int add(Employees e) throws SQLException {
		int salary=e.getSalary();
		float commission=e.getCommission();
		int managerId=e.getManagerId();
		int departmentId= e.getDepartmentId();
		
		CallableStatement csInsert = dbConn.getMyConn().prepareCall("{call ADD_EMPLOYEES(?,?,?,?,?,to_date(?,'dd/mm/yyyy'),?,?,?,?,?,?)}");
		csInsert.setInt(1, 0);
		csInsert.setString(2, e.getFirstName());
		csInsert.setString(3, e.getLastName());
		csInsert.setString(4, e.getEmail());
		csInsert.setString(5, e.getPhoneNumber());
		csInsert.setString(6, e.getHireDate());
		csInsert.setString(7, e.getJobId());
		if(salary==0) {
			csInsert.setString(8, null);
		}
		else {
			csInsert.setInt(8, salary);
		}
		if(commission==0.0) {
			csInsert.setString(9, null);
		}
		else {
			csInsert.setFloat(9, commission);
		}
		if(managerId==0) {
			csInsert.setString(10, null);
		}
		else {
			csInsert.setInt(10, managerId);
		}
		if(departmentId==0) {
			csInsert.setString(11, null);

		}
		else {
			csInsert.setInt(11, departmentId);
		}
		csInsert.registerOutParameter(12, Types.INTEGER);
		csInsert.executeUpdate();
		
		return csInsert.getInt(12);	
	}

	@Override
	public int update(Employees e,int id) throws SQLException {
		int salary=e.getSalary();
		float commission=e.getCommission();
		int managerId=e.getManagerId();
		int departmentId= e.getDepartmentId();
	
		CallableStatement csUpdate = dbConn.getMyConn().prepareCall("{call UPDATE_EMPLOYEES(?,?,?,?,?,to_date(?,'dd/mm/yyyy'),?,?,?,?,?,?)}");
		csUpdate.setInt(1, id);
		csUpdate.setString(2, e.getFirstName());
		csUpdate.setString(3, e.getLastName());
		csUpdate.setString(4, e.getEmail());
		csUpdate.setString(5, e.getPhoneNumber());
		csUpdate.setString(6, e.getHireDate());
		csUpdate.setString(7, e.getJobId());
		if(salary==0) {
			csUpdate.setString(8, null);
		}
		else {
			csUpdate.setInt(8, salary);
		}
		if(commission==0.0) {
			csUpdate.setString(9, null);
		}
		else {
			csUpdate.setFloat(9, commission);
		}
		if(managerId==0) {
			csUpdate.setString(10, null);
		}
		else {
			csUpdate.setInt(10, managerId);
		}
		if(departmentId==0) {
			csUpdate.setString(11, null);

		}
		else {
			csUpdate.setInt(11, departmentId);
		}
		csUpdate.registerOutParameter(12, Types.INTEGER);
		csUpdate.executeUpdate();
		
		return csUpdate.getInt(12);
		
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
	public int update(Employees t, String id) throws SQLException {
		return 0;
	}

	@Override
	public int delete(String id) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

}
