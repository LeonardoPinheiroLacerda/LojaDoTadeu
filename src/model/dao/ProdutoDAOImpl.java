package model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import model.entities.Compra;
import model.entities.CompraProduto;
import model.entities.Produto;

public class ProdutoDAOImpl extends Connector implements ProdutoDAO {

	@Override
	public Produto getProdutoById(Integer id) {
		
		Produto prod = null;
		String descricao = null;
		Double preco = null;
		Integer qtd = null;
		Integer loja = null;

		try {
			connect();
			
			String sql = "select * from estoque where id_prod = ?;";
			
			PreparedStatement stmt = getConn().prepareStatement(sql);
			stmt.setInt(1, id);
			
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				descricao = rs.getString(2);
				preco = rs.getDouble(3);
				qtd = rs.getInt(4);
				loja = rs.getInt(5);
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			disconnect();
		}
		
		if(descricao != null) {
			prod = new Produto(id, descricao, preco, qtd, loja);
		}
		
		return prod;
	}

	@Override
	public Produto getProdutoByName(String name) {
		
		Produto prod = null;
		String descricao = null;
		Double preco = null;
		Integer qtd = null;
		Integer loja = null;
		Integer id = null;
		
		try {
			connect();
			
			String sql = "select * from estoque where upper(descricao) = ?;";
			
			PreparedStatement stmt = getConn().prepareStatement(sql);
			stmt.setString(1, name.toUpperCase());
			
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				id = rs.getInt(1);
				descricao = rs.getString(2);
				preco = rs.getDouble(3);
				qtd = rs.getInt(4);
				loja = rs.getInt(5);
			}
			
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			disconnect();
		}
		
		if(descricao != null) {
			prod = new Produto(id, descricao, preco, qtd, loja);
		}
		
		return prod;
	}

	@Override
	public boolean updateEstoque(Compra compra) {
		boolean sucess = false;
		
		try {
			connect();
			
			for(CompraProduto c: compra.getCompras()) {
				
				PreparedStatement stmt = getConn().prepareStatement("update estoque set qtd =  qtd - ? where id_prod = ?;");
				stmt.setInt(1, c.getQuantidade());
				stmt.setInt(2, c.getProduto().getId());
				
				sucess = (stmt.executeUpdate() > 0) ? true : false;
				
				if(!sucess) throw new SQLException("Deu ruim no arrebatamento do estoque");
				
			}
			
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}finally {
			disconnect();
		}
		
		return sucess;
	}

	@Override
	public boolean insertOnDelEstoque(Produto p) {
		
		boolean sucess = false;
		
		try {
			connect();
			
			PreparedStatement stmt = getConn().prepareStatement("insert into del_estoque values(?, ?, ?, ?);");
			stmt.setInt(1, p.getId());
			stmt.setString(2, p.getDescricao());
			stmt.setDouble(3, p.getPreco());
			stmt.setInt(4, p.getLoja().getId());
			
			sucess = stmt.executeUpdate() > 0 ? true: false; 
			
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}finally {
			disconnect();
		}
		
		return sucess;
	}
	
	@Override
	public boolean deleteProduto(Integer id) {
		
		boolean sucess = false;
		
		try {
			connect();
			
			PreparedStatement stmt = getConn().prepareStatement("delete from estoque where id_prod = ?");
			stmt.setInt(1, id);
			
			sucess = stmt.executeUpdate() > 0 ? true: false; 
			
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}finally {
			disconnect();
		}
		
		return sucess;
	}
	
	@Override
	public boolean moveToDelEstoque(Integer id) {
		
		boolean sucess = false;
			
		Produto p = getProdutoById(id);
		
		if(insertOnDelEstoque(p)) {
			sucess = deleteProduto(id);
		}
			
		return sucess;
	}

	@Override
	public boolean editProduto(Integer id, Double preco, Integer entEstoque) {

		boolean sucess = false;
		
		try {
			connect();
			
			PreparedStatement stmt = getConn().prepareStatement("update estoque set preco = ?, qtd = qtd + ? where id_prod = ?");
			stmt.setDouble(1, preco);
			stmt.setInt(2, entEstoque);
			stmt.setInt(3, id);
			
			sucess = stmt.executeUpdate() > 0 ? true: false; 
			
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}finally {
			disconnect();
		}
				
		return sucess;
	}

	

	@Override
	public boolean checkDelProdutos(Integer id) {
		
		boolean sucess = false;
		
		try {
			connect();
			
			PreparedStatement stmt = getConn().prepareStatement("select * from del_estoque where id_prod = ?");
			stmt.setInt(1, id);
			
			ResultSet rs = stmt.executeQuery();
			
			sucess = rs.next();			
			
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}finally {
			disconnect();
		}
				
		return sucess;
	}



	@Override
	public Produto getProdutoFromDelProduto(Integer id) {
		
		Produto p = null;
		
		try {
			connect();
			
			PreparedStatement stmt = getConn().prepareStatement("select * from del_estoque where id_prod = ?");
			stmt.setInt(1, id);
			
			ResultSet rs = stmt.executeQuery();
						
			if(rs.next()) {
				String desc = rs.getString(2);
				Double preco = rs.getDouble(3);
				Integer loja_id = rs.getInt(4);
				
				p = new Produto(id, desc, preco, 0, loja_id);
			}			
			
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}finally {
			disconnect();
		}
				
		return p;
	}

	@Override
	public boolean deleteFromDelProduto(Integer id) {
		
		boolean sucess = false;
		
		try {
			connect();
			
			PreparedStatement stmt = getConn().prepareStatement("delete from del_estoque where id_prod = ?");
			stmt.setInt(1, id);
			
			sucess = stmt.executeUpdate() > 0 ? true: false; 
			
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}finally {
			disconnect();
		}
		
		return sucess;
		
	}

	@Override
	public boolean addProduto(Produto p) {

		String id = (p.getId() == 0) ? "null" : p.getId().toString();
		boolean sucess = false;
		
		try {
			connect();
			
			PreparedStatement stmt = getConn().prepareStatement("insert into estoque values(" + id + ", ?, ?, ?, ?);");
			stmt.setString(1, p.getDescricao());
			stmt.setDouble(2, p.getPreco());
			stmt.setInt(3, p.getQuantidade());
			stmt.setInt(4, p.getLoja().getId());
			
			sucess = stmt.executeUpdate() > 0 ? true: false; 
			
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}finally {
			disconnect();
		}
		
		if(id != "null") {
			deleteFromDelProduto(Integer.parseInt(id));
		}
		
		return sucess;
	}

	@Override
	public Produto getDelProduto(String nome) {
		
		Produto prod = null;
		
		String descricao = null;
		Double preco = null;
		Integer loja = null;
		Integer id = null;
		
		try {
			connect();
			
			String sql = "select * from del_estoque where upper(descricao) = ?;";
			
			PreparedStatement stmt = getConn().prepareStatement(sql);
			stmt.setString(1, nome.toUpperCase());
			
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				id = rs.getInt(1);
				descricao = rs.getString(2);
				preco = rs.getDouble(3);
				loja = rs.getInt(4);
			}
			
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			disconnect();
		}
		
		if(descricao != null) {
			prod = new Produto(id, descricao, preco, 0, loja);
		}
		
		return prod;
	}

	@Override
	public Collection<Produto> getAllProdutos() {
		
		Collection<Produto> prods = new ArrayList<>();
		
		try {
			
			connect();
			
			String sql = "select * from estoque;";
			Statement stmt = getConn().createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				prods.add(new Produto(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getInt(4), rs.getInt(5)));
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			disconnect();
		}
		
		return prods;
	}
	
}
