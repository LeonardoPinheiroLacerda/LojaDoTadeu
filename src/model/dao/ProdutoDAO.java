package model.dao;

import java.util.Collection;

import model.entities.Compra;
import model.entities.Produto;

public interface ProdutoDAO {

	public Produto getProdutoById(Integer id);
	public Produto getProdutoByName(String name);
	public boolean updateEstoque(Compra compra);
	public boolean moveToDelEstoque(Integer id);
	public boolean insertOnDelEstoque(Produto p);
	public boolean deleteProduto(Integer id);
	public boolean deleteFromDelProduto(Integer id);
	public boolean editProduto(Integer id, Double preco, Integer entEstoque);
	public boolean addProduto(Produto p);
	public boolean checkDelProdutos(Integer id);
	public Produto getProdutoFromDelProduto(Integer id);
	public Produto getDelProduto(String nome);
	public Collection<Produto> getAllProdutos();
	
}
