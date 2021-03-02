package model.dao;

import model.entities.Cadastro;
import model.entities.Funcionario;

public interface CadastroDAO {

	public Cadastro getCadastroById(Integer id);
	public boolean insertCadastro(Cadastro cadastro);
	public boolean removeCadastro(Cadastro cadastro);
	public boolean updatePassword(String pass, Funcionario func);
	
}
