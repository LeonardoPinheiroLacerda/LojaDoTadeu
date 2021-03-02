package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import model.dao.FactoryDAO;
import model.entities.Compra;
import model.entities.CompraProduto;
import model.entities.Funcionario;
import model.services.CaixaService;
import model.services.LogService;
import util.Alerts;

public class CaixaController extends CaixaService implements Initializable {

	private Funcionario logged;
	private Funcionario manager;
	private static Stage me;

	@FXML
	private Label nome;
	@FXML
	private Label ramal;
	@FXML
	private Label gerente;
	@FXML
	private Label loja;
	@FXML
	private Label preco;
	@FXML
	private Label sair;
	@FXML
	private Button add;
	@FXML
	private Button finalizar;
	@FXML
	private TextField prod;
	@FXML
	private TextField qtd;
	@FXML
	private ListView<CompraProduto> list;

	
	private Compra compra = new Compra();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		prod.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER)
				onAddAction();
		});

		qtd.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER)
				onAddAction();
		});

		list.setOnMouseClicked(t -> {
			if (t.getClickCount() == 2) {
				onListDoubleClick();
			}
		});

		logged = HomeController.getLogged();
		manager = HomeController.getLogged().getGerente();
		
		nome.setText("Nome: " + logged.getNome() + " " + logged.getSobrenome());
		ramal.setText("Ramal: " + logged.getRamal());
		gerente.setText(manager != null ? "Gerente: " + manager.getNome() + " " + manager.getSobrenome() : "Gerente: ");
		loja.setText("Loja: " + logged.getLoja().getNomeLoja());

	}

	@FXML
	public void onAddAction() {
		addItem(prod.getText(), qtd.getText(), list, preco, compra, logged);
		prod.setText("");
		qtd.setText("");
		prod.requestFocus();
	}

	@FXML
	public void onSairMouseClicked() {
		Main.getHomeStage().show();
		LogService.saiu(HomeController.getLogged());
		me.close();
	}

	@FXML
	public void onFinalizarAction() {
		if(!compra.getCompras().isEmpty()) {
		
			if(FactoryDAO.produtoDAO().updateEstoque(compra)) {
				if(FactoryDAO.compraDAO().insertCompra(compra, logged)) {
					if(FactoryDAO.funcionarioDAO().updateFuncionarioVenda(logged, compra)) {
						Alerts.showAlert("Sucesso", null, "Compra concluida com sucesso", AlertType.INFORMATION);
						
						LogService.vendeu(HomeController.getLogged(), compra.total());
						
					}else {
						Alerts.showAlert("Erro", null, "Erro de banco de dados. \nCod. 01", AlertType.ERROR);
					}
				}else {
					Alerts.showAlert("Erro", null, "Erro de banco de dados. \nCod. 02", AlertType.ERROR);
				}
			}else {
				Alerts.showAlert("Erro", null, "Erro de banco de dados. \nCod. 03", AlertType.ERROR);
			}	
			
			compra.getCompras().clear();
			list.getItems().clear();
			
			prod.setText("");
			qtd.setText("");
			prod.requestFocus();
			preco.setText("R$ 0,00");
			
		}else {
			
			Alerts.showAlert("Aviso", null, "Carrinho vazio.", AlertType.INFORMATION);
			
		}
	}
	
	public void onListDoubleClick() {
		removeItem(compra, list, preco);
	}

	public static Stage getCaixaStage() {
		return me;
	}

	public static void setCaixaStage(Stage ME) {
		me = ME;
	}
	
	

}
