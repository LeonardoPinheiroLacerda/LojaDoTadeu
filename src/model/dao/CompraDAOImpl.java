package model.dao;

import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import model.entities.Compra;
import model.entities.Funcionario;
import model.services.CaixaService;
import model.services.CompraService;

public class CompraDAOImpl extends Connector implements CompraDAO {

	@Override
	public boolean insertCompra(Compra compra, Funcionario funcionario) {
		
		boolean sucess = false;
		
		try {
			connect();			
			CaixaService cs = new CaixaService();
			
			String sql = "insert into compras values(NULL, ?, datetime('now', 'localtime'), ?, ?);";
			
			PreparedStatement stmt = getConn().prepareStatement(sql);
			stmt.setDouble(1, compra.total());
			stmt.setString(2, cs.generateProdString(compra));
			stmt.setInt(3, funcionario.getId());
			
			int AffectedLines = stmt.executeUpdate();
			
			sucess = (AffectedLines > 0) ? true : false;
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			disconnect();
		}
		
		return sucess;
	}

	@Override
	public Compra getCompraById(Integer id) {		
		String prodString = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date moment = null;
		
		try {
			connect();			
			
			PreparedStatement stmt = getConn().prepareStatement("select * from compras where id = ?");
			stmt.setInt(1, id);
			
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				prodString = rs.getString(4);
				moment = sdf.parse(rs.getString(3));
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			moment = null;
			e.printStackTrace();
		}finally {
			disconnect();
		}
		
		CompraService cs = new CompraService();
	
		return cs.toCompra(prodString, moment);
	}

}
