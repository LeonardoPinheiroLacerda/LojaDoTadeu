package model.dao;

import model.entities.Compra;
import model.entities.Funcionario;

public interface CompraDAO {

	public boolean insertCompra(Compra compra, Funcionario funcionario);
	public Compra getCompraById(Integer id);
	
}
