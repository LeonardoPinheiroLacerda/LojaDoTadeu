package controllers;

import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.dao.FactoryDAO;
import model.entities.Loja;
import model.entities.Produto;

public class TabelaProdutoController implements Initializable {

	@FXML
	private TableView<Produto> tabela;
	
	@FXML
	private TableColumn<Produto, Integer> ids;
	
	@FXML
	private TableColumn<Produto, String> produtos;
	
	@FXML
	private TableColumn<Produto, Double> precos;
	
	@FXML
	private TableColumn<Produto, Integer> estoque;
	
	@FXML
	private TableColumn<Produto, Loja> loja;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
		Collection<Produto> array = FactoryDAO.produtoDAO().getAllProdutos();		
		
		ObservableList<Produto> prods = FXCollections.observableArrayList(array);
		
		ids.setCellValueFactory(new PropertyValueFactory<Produto, Integer>("id"));
		produtos.setCellValueFactory(new PropertyValueFactory<Produto, String>("descricao"));
		precos.setCellValueFactory(new PropertyValueFactory<Produto, Double>("preco"));
		estoque.setCellValueFactory(new PropertyValueFactory<Produto, Integer>("quantidade"));
		loja.setCellValueFactory(new PropertyValueFactory<Produto, Loja>("loja"));
		
		tabela.setItems(prods);
		
	}
	
}
