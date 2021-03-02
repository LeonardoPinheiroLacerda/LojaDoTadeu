package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class Connector {

	private Connection conn;
	
	//true = anbiente de teste, false = anbiente de produção
	private final boolean IDE = false;
	
	
	public void connect() {
		try {
			Class.forName("org.sqlite.JDBC");
			String url;		
			
			if(IDE) {
				 url = "jdbc:sqlite:src/model/files/dataBase.db";	
			}else {
				 url = "jdbc:sqlite:data/dataBase.db";	
			}
			
			setConn(DriverManager.getConnection(url));
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void disconnect() {
		try {
			if(!getConn().isClosed()) {
				getConn().close();
			}			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

}