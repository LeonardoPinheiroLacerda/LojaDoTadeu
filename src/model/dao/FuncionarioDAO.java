package model.dao;

import java.util.Collection;

import model.entities.Compra;
import model.entities.Funcionario;

public interface FuncionarioDAO{

	public Funcionario login(String user, String pass);
	public Funcionario searchById(Integer id);
	public boolean updateFuncionarioVenda(Funcionario funcionario, Compra compra);
	public Funcionario[] getAllGerentes();
	public boolean insertFuncionario(Funcionario funcionario);
	public Collection<Funcionario> getAllfunc(Integer gerente, String cargo);
	public Funcionario[] getAllfunc();
	public Funcionario[] getFunc();
	public boolean addToSolDesligamento(Funcionario funcionario);
	public Funcionario[] getAllSolDesligamento();
	public boolean removeFuncionario(Funcionario funcionario);
	public boolean promoveFuncionario(Funcionario funcionario);
	public boolean removeGerente(Funcionario gerente);
	public boolean trocaGerente(Funcionario gerente, Funcionario funcionario);
}
