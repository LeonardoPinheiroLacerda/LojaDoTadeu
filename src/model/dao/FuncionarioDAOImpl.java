package model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import model.entities.Compra;
import model.entities.Funcionario;

public class FuncionarioDAOImpl extends Connector implements FuncionarioDAO {

	@Override
	public Funcionario login(String user, String pass) {
		
		Integer id = null;
		
		try {
			connect();
			
			PreparedStatement stmt = getConn().prepareStatement("select funcionario_id from cadastro where usuario = ? and senha = ?;");
			stmt.setString(1, user);
			stmt.setString(2, pass);
			
			ResultSet rs = stmt.executeQuery();
			id = rs.getInt("funcionario_id");
			
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		finally {
			disconnect();
		}
		
		return searchById(id);
	}

	@Override
	public Funcionario searchById(Integer id) {
		
		if(id == null)
			return null;
		
		String nome = null;
		String sobrenome = null;
		String ramal = null;
		Integer loja = null;
		String cargo = null;
		Double vendas = null;
		Integer gerenteId = null;
		
		try {
			connect();
			
			PreparedStatement stmt = getConn().prepareStatement("Select * from funcionarios where id = ?;");
			stmt.setInt(1, id);
			
			ResultSet rs = stmt.executeQuery();
			
			nome =  rs.getString("nome");
			sobrenome = rs.getString("sobrenome");
			ramal = rs.getString("ramal");
			loja = rs.getInt("loja_id");
			cargo = rs.getString("cargo");
			vendas = rs.getDouble("venda");
			gerenteId = rs.getInt("gerenteId");

		}catch(SQLException e) {
			System.out.println(e.getMessage() + " deu ruim");
		}finally {
			disconnect();
		}
		
		return new Funcionario(id, nome, sobrenome, ramal, loja, cargo, vendas, gerenteId);
				
	}

	@Override
	public boolean updateFuncionarioVenda(Funcionario funcionario, Compra compra) {
		boolean sucess = false;
		
		try {
			connect();
			
			PreparedStatement stmt = getConn().prepareStatement("update funcionarios set venda = venda + ? where id = ?");
			stmt.setDouble(1, compra.total());
			stmt.setInt(2, funcionario.getId());

			sucess = (stmt.executeUpdate() > 0) ? true: false;
			
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		finally {
			disconnect();
		}
		
		return sucess;
	}

	@Override
	public Funcionario[] getAllGerentes() {
		
		List<Funcionario> funcionarios = new ArrayList<>();
		try {
			connect();
			
			
			
			Statement stmt = getConn().createStatement();
			ResultSet rs = stmt.executeQuery("select * from funcionarios where id != 1 and gerenteId = 0");
			while(rs.next()) {
				funcionarios.add(searchById(rs.getInt(1)));
			}
						
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		finally {
			disconnect();
		}
		
		Funcionario[] funcs = new Funcionario[funcionarios.size()];
		for(int i = 0; i < funcs.length; i ++) {
			funcs[i] = funcionarios.get(i);
		}
		
		return funcs;
	}

	@Override
	public boolean insertFuncionario(Funcionario funcionario) {
		
		boolean success = false;
		
		try {
			connect();
			
			PreparedStatement stmt = getConn().prepareStatement("insert into funcionarios values(NULL, ?, ?, ?, ?, ?, 0, ?);");
			stmt.setString(1, funcionario.getNome());
			stmt.setString(2, funcionario.getSobrenome());
			stmt.setString(3, funcionario.getRamal());
			stmt.setInt(4, funcionario.getLoja().getId());
			stmt.setString(5, funcionario.getCargo());
			stmt.setInt(6, funcionario.getGerente_id());
			
			success = stmt.executeUpdate() > 0 ? true : false;
						
			
		}catch(SQLException e) {
			System.out.println(e.getMessage() + "Cod3");
		} finally {
			disconnect();
		}
		
		success = success ? FactoryDAO.cadastroDAO().insertCadastro(funcionario.getCadastro()) : false;
		
		System.out.println(success);
		
		if(!success) {
			Funcionario[] fs = getAllfunc();
			for(int i = 0; i < fs.length; i ++) {
				if(fs[i].getRamal().equals(funcionario.getRamal())) {
					try {
						connect();
						PreparedStatement pstmt = getConn().prepareStatement("delete from funcionarios where id = ?");
						pstmt.setInt(1, fs[i].getId());
						
						pstmt.executeUpdate();
					
						pstmt = getConn().prepareStatement("update sqlite_sequence set seq = seq - 1 where name = 'funcionarios';");						
						pstmt.executeUpdate();
						
					} catch (SQLException e) {
						e.printStackTrace();
					} finally {
						disconnect();
					}	
				}
			}
		}
		
		
		
		return success;
	}

	@Override
	public Collection<Funcionario> getAllfunc(Integer gerente, String cargo) {

		List<Funcionario> funcionarios = new ArrayList<>();
		try {
			connect();
			
			
			String sql = "select * from funcionarios where gerenteId = ? and cargo = ?";
			PreparedStatement stmt = getConn().prepareStatement(sql);
			stmt.setInt(1, gerente);
			stmt.setString(2, cargo);

			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				funcionarios.add(searchById(rs.getInt(1)));
			}
						
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		finally {
			disconnect();
		}
		
		return funcionarios;
	}

	@Override
	public boolean addToSolDesligamento(Funcionario funcionario) {
		
		boolean sucess = false;

		try {
			connect();
			
			
			String sql = "insert into sol_desligamento values(?)";
			PreparedStatement stmt = getConn().prepareStatement(sql);
			stmt.setInt(1, funcionario.getId());	

			sucess = (stmt.executeUpdate() > 0) ? true : false;
			
						
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		finally {
			disconnect();
		}
		
		
		return sucess;
	}

	@Override
	public Funcionario[] getAllSolDesligamento() {
	
		List<Funcionario> funcionarios = new ArrayList<>();
		try {
			connect();
			
			
			
			Statement stmt = getConn().createStatement();
			ResultSet rs = stmt.executeQuery("select * from sol_desligamento");
			while(rs.next()) {
				funcionarios.add(searchById(rs.getInt(1)));
			}
						
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		finally {
			disconnect();
		}
		
		Funcionario[] funcs = new Funcionario[funcionarios.size()];
		for(int i = 0; i < funcs.length; i ++) {
			funcs[i] = funcionarios.get(i);
		}
		
		return funcs;
		
	}

	@Override
	public boolean removeFuncionario(Funcionario funcionario) {

		boolean sucess = false;
		
		try {
			connect();
			
			String sql = "delete from funcionarios where id = ?;";
			PreparedStatement stmt = getConn().prepareStatement(sql);
			stmt.setInt(1, funcionario.getId());
			
			sucess = stmt.executeUpdate() > 0 ? true : false;
			
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}finally {
			disconnect();
		}
		
		if(sucess) {
			try {
				connect();
				
				String sql = "delete from sol_desligamento where id = ?;";
				PreparedStatement stmt = getConn().prepareStatement(sql);
				stmt.setInt(1, funcionario.getId());
				
				sucess = stmt.executeUpdate() > 0 ? true : false;
				
			}catch(SQLException e) {
				System.out.println(e.getMessage());
			}finally {
				disconnect();
			}
		}
		
		if(sucess) sucess = FactoryDAO.cadastroDAO().removeCadastro(FactoryDAO.cadastroDAO().getCadastroById(funcionario.getId()));
		
		return sucess;
	}

	@Override
	public boolean promoveFuncionario(Funcionario funcionario) {
		
		boolean sucess = false;
		
		try {
			connect();
			
			String sql = "update funcionarios set cargo = 'Gerente', gerenteId = 0 where id = ?";
			PreparedStatement stmt = getConn().prepareStatement(sql);
			stmt.setInt(1, funcionario.getId());
			
			sucess = stmt.executeUpdate() > 0 ? true : false;
			
		}catch(SQLException e) {
			
		}finally {
			disconnect();
		}
		
		
		return sucess;
		
	}

	@Override
	public boolean removeGerente(Funcionario gerente) {
		boolean sucess = false;
		
		try {
			connect();
			
			String sql = "delete from funcionarios where id = ?;";
			PreparedStatement stmt = getConn().prepareStatement(sql);
			stmt.setInt(1, gerente.getId());
			
			sucess = stmt.executeUpdate() > 0 ? true : false;
			
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}finally {
			disconnect();
		}
		
		if(sucess) return FactoryDAO.cadastroDAO().removeCadastro(gerente.getCadastro());
		
		return sucess;
	}

	@Override
	public Funcionario[] getAllfunc() {
		List<Funcionario> funcionarios = new ArrayList<>();
		try {
			connect();
			
			String sql = "select id from funcionarios where id <> 1";
			PreparedStatement stmt = getConn().prepareStatement(sql);

			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				funcionarios.add(searchById(rs.getInt("id")));
			}
						
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		finally {
			disconnect();
		}
		
		Funcionario[] funcs = new Funcionario[funcionarios.size()];
		for(int i = 0; i < funcs.length; i ++) {
			funcs[i] = funcionarios.get(i);
		}
		
		return funcs;
	}

	@Override
	public Funcionario[] getFunc() {
		List<Funcionario> funcionarios = new ArrayList<>();
		try {
			connect();
			
			String sql = "select id from funcionarios where gerenteId <> 0";
			PreparedStatement stmt = getConn().prepareStatement(sql);

			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				funcionarios.add(searchById(rs.getInt("id")));
			}
						
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		finally {
			disconnect();
		}
		
		Funcionario[] funcs = new Funcionario[funcionarios.size()];
		for(int i = 0; i < funcs.length; i ++) {
			funcs[i] = funcionarios.get(i);
		}
		
		return funcs;
	}

	@Override
	public boolean trocaGerente(Funcionario gerente, Funcionario funcionario) {
		boolean sucess = false;
		
		try {
			connect();
			
			PreparedStatement stmt = getConn().prepareStatement("update funcionarios set gerenteId = ? where id = ?;");
			stmt.setInt(1, gerente == null ? -1 : gerente.getId());
			stmt.setInt(2, funcionario.getId());
			
			sucess = (stmt.executeUpdate() > 0) ? true : false;
			
			
		}catch(SQLException e) {
			
		}finally {
			disconnect();
		}
		
		return sucess;
	}

}
