package model.dao;

public class FactoryDAO {

	public static LojaDAO lojaDAO() {
		return new LojaDAOImpl();
	}
	
	public static FuncionarioDAO funcionarioDAO() {
		return new FuncionarioDAOImpl();
	}
	
	public static ProdutoDAO produtoDAO() {
		return new ProdutoDAOImpl();
	}
	
	public static CompraDAO compraDAO() {
		return new CompraDAOImpl();
	}
	
	public static CadastroDAO cadastroDAO() {
		return new CadastroDAOImpl();
	}
	
	public static LogDAO logDAO() {
		return new LogDAOImpl();
	}
}
