package model.services;

import model.dao.FactoryDAO;
import model.entities.Funcionario;
import model.entities.Log;
import model.entities.Produto;

public class LogService {

	public static boolean entrou(Funcionario func) {
		return FactoryDAO.logDAO().log(new Log("[Login] " + func.getId() + " : " + func.getNome() + " " + func.getSobrenome() + " entrou."));
	}
	
	public static boolean saiu(Funcionario func) {
		return FactoryDAO.logDAO().log(new Log("[Sign out] " + func.getId() + " : " + func.getNome() + " " + func.getSobrenome() + " saiu."));
	}
	
	public static boolean vendeu(Funcionario func, Double total) {
		return FactoryDAO.logDAO().log(new Log("[Venda] " + func.getId() + " : " + func.getNome() + " " + func.getSobrenome() + " fez uma venda de R$" + String.format("%.2f", total) + "."));
	}
	
	public static boolean addProd(Funcionario func, Produto prod) {
		return FactoryDAO.logDAO().log(new Log("[Add produto] " + func.getNome() + " " + func.getSobrenome() + " adicionou o produto " + prod.getId() + " : " + prod.getDescricao() + " - R$" + prod.getPreco() + "."));
	}
	
	public static boolean removeProd(Funcionario func, Produto prod) {
		return FactoryDAO.logDAO().log(new Log("[Remover produto] " + func.getId() + " : " + func.getNome() + " " + func.getSobrenome() + " removeu o produto " + prod.getId() + " : " + prod.getDescricao() + " - R$" + prod.getPreco() + "."));
	}
	
	public static boolean editProd(Funcionario func, Produto prod, Produto novo) {
		return FactoryDAO.logDAO().log(new Log("[Editar produto] " + func.getId() + " : " + func.getNome() + " " + func.getSobrenome() + " editou o produto " + prod.getId() + " : " + prod.getDescricao() + " - R$" + prod.getPreco() + " para " + novo.getId() + " : " + novo.getDescricao() + " - R$" + novo.getPreco() + "."));
	}
	
	public static boolean solDesl(Funcionario gerente, Funcionario funcionario) {
		return FactoryDAO.logDAO().log(new Log("[Sol desligamento] " + gerente.getId() + " : " + gerente.getNome() + " " + gerente.getSobrenome() + " solicitou o desligamento de " + funcionario.getId() + " : " + funcionario.getNome() + " " + funcionario.getSobrenome() + "."));
	}
	
	public static boolean desligamento(Funcionario funcionario) {
		return FactoryDAO.logDAO().log(new Log("[Desligamento] " + funcionario.getId() + " : " + funcionario.getNome() + " " + funcionario.getSobrenome() + " foi demitido(a)."));
	}
	
	public static boolean promocao(Funcionario funcionario) {
		return FactoryDAO.logDAO().log(new Log("[Promocao] " + funcionario.getId() + " : " + funcionario.getNome() + " " + funcionario.getSobrenome() + " foi promovido(a)."));
	}
	
	public static boolean trocaSenha(Funcionario funcionario) {
		return FactoryDAO.logDAO().log(new Log("[Troca senha] " + funcionario.getId() + " : " + funcionario.getNome() + " " + funcionario.getSobrenome() + " trocou sua senha."));
	}
	
	public static boolean trocaGerencia(Funcionario funcionario, Funcionario gerente) {
		return FactoryDAO.logDAO().log(new Log("[Troca gerencia] " + funcionario.getId() + " : " + funcionario.getNome() + " " + funcionario.getSobrenome() + " agora responde para " + gerente.getId() + " : " + gerente.getNome() + " " + gerente.getSobrenome() + "."));
	}
	
	public static boolean novoFuncionario(Funcionario funcionario) {
		return FactoryDAO.logDAO().log(new Log("[Novo funcionario] "  + funcionario.getId() + " : " + funcionario.getNome() + " " + funcionario.getSobrenome() + " foi inserido(a) no sistema."));
	}
}
