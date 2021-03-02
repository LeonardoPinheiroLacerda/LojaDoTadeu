package model.dao;

import model.entities.Loja;

public interface LojaDAO {

	public String getLojaNome(Integer id);
	public Loja[] getAllLojas();
	
}
