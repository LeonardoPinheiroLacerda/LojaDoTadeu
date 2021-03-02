package model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import model.entities.Log;

public class LogDAOImpl extends Connector implements LogDAO {

	@Override
	public Log getById(Integer id) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Log log = new Log();;
		
		try {
			connect();
			PreparedStatement stmt = getConn().prepareStatement("select * from logs where id = ?;");
			stmt.setInt(1, id);
			
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				log.setLog(rs.getString("atividade"));
				log.setMoment(sdf.parse(rs.getString("momento")));
			}
			
		}catch(SQLException | ParseException e) {
			System.out.println(e.getMessage());
		}finally {
			disconnect();
		}
		
		return log;
	}

	@Override
	public boolean log(Log log) {
		String sql = "INSERT INTO logs(id, atividade, momento)" + 
		"VALUES (null, ?, datetime('now', 'localtime'))";
		
		boolean success = false;
		
		try {
			connect();
			
			PreparedStatement stmt = getConn().prepareStatement(sql);
			stmt.setString(1, log.getLog());
			
			success = (stmt.executeUpdate() > 0) ? true : false;
			
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}finally {
			disconnect();
		}
		
		return success;
	}

}
