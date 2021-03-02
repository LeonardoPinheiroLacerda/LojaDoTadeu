package model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.entities.Loja;

public class LojaDAOImpl extends Connector implements LojaDAO{

	@Override
	public String getLojaNome(Integer id) {
		
		String cidade = null;
		
		try {
			connect();
			
			String sql = "select cidade from lojas where id = ?";
			
			PreparedStatement stmt = getConn().prepareStatement(sql);
			stmt.setInt(1, id);		
			
			ResultSet rs = stmt.executeQuery();
			
			cidade = rs.getString("cidade");
			
		}catch(SQLException e) {
			System.out.println(e.getMessage());
			
		}finally {
			disconnect();
		}
		
		return cidade;
	}

	@Override
	public Loja[] getAllLojas() {

		List<Loja> lojas = new ArrayList<>();
		
		try {
			connect();
			
			Statement stmt = getConn().createStatement();
			ResultSet rs = stmt.executeQuery("select * from lojas");
			
			while(rs.next()) {
				lojas.add(new Loja(rs.getInt(1)));
			}
			
		}catch(SQLException e) {
			System.out.println(e.getMessage());
			
		}finally {
			disconnect();
		}
		
		Loja[] loj = new Loja[lojas.size()];
		for(int i = 0; i < loj.length; i ++) {
			loj[i] = lojas.get(i);
		}
		
		return loj;
	}

}
