package model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.entities.Cadastro;
import model.entities.Funcionario;

public class CadastroDAOImpl extends Connector implements CadastroDAO {

	@Override
	public Cadastro getCadastroById(Integer id) {
		
		String user = null;
		String pass = null;
		
		try {
			
			connect();
			
			PreparedStatement stmt = getConn().prepareStatement("select * from cadastro where funcionario_id = ?");
			stmt.setInt(1, id);
			
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				user = rs.getString(2);
				pass = rs.getString(3);
			}
			
		}catch(SQLException e) {
			
		}finally {
			disconnect();
		}
		
		return new Cadastro(id, user, pass);
	}

	@Override
	public boolean insertCadastro(Cadastro cadastro) {
		
		boolean success = false;
		
		try {	
			
			connect();
			
			PreparedStatement stmt = getConn().prepareStatement("insert into cadastro values(Null, ? , ?)");
			stmt.setString(1, cadastro.getUsuario());
			stmt.setString(2, cadastro.getSenha());
			
			success = (stmt.executeUpdate() > 0) ? true : false;			
			
		}catch(SQLException e) {
			
		}finally {
			disconnect();
		}
		
		return success;
	}

	@Override
	public boolean removeCadastro(Cadastro cadastro) {
		boolean success = false;
		
		try {	
			
			connect();
			
			PreparedStatement stmt = getConn().prepareStatement("delete from cadastro where funcionario_id = ?");
			stmt.setInt(1, cadastro.getId());
			
			success = (stmt.executeUpdate() > 0) ? true : false;			
			
		}catch(SQLException e) {
			
		}finally {
			disconnect();
		}
		
		return success;
	}

	@Override
	public boolean updatePassword(String pass, Funcionario func) {
		boolean sucess = false;
		
		try {
			connect();
			
			PreparedStatement stmt = getConn().prepareStatement("update cadastro set senha = ? where funcionario_id = ?");
			stmt.setString(1, pass);
			stmt.setInt(2, func.getId());
			
			sucess = stmt.executeUpdate() > 0 ? true : false;
			
		}catch(SQLException e) {
			
		}finally {
			disconnect();
		}
		
		return sucess;
	}

	
	
}
