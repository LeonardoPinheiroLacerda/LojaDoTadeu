package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.dao.FactoryDAO;
import model.entities.Loja;
import model.entities.Produto;
import model.services.LogService;
import util.Alerts;
import util.Constructor;

public class LogisticaController implements Initializable {

	private String[] vecAcoes = new String[] { "Adicionar novo produto", "Editar produto existente", "Deletar produto",
			"Vizualizar produtos" };

	private static Stage me;

	@FXML
	private Accordion accordion;

	@FXML
	private ComboBox<String> acoes;

	@FXML
	private TextField txt1;

	@FXML
	private TextField txt2;

	@FXML
	private TextField txt3;

	@FXML
	private TextField txt4;
	
	@FXML
	private ComboBox<Loja> lojas;

	@FXML
	private Button bt;
	
	@FXML
	private Label sair;
	
	private Integer estadoAtual;

	private EventHandler<MouseEvent> adicionar = e -> {
		
		try {			
			if(txt1.getText().length() > 0 && txt2.getText().length() > 0 && txt3.getText().length() > 0 && lojas.getSelectionModel().getSelectedIndex() >= 0) {
											
				Produto p = null;
				
				if(txt2.isDisabled()) {
					p = FactoryDAO.produtoDAO().getDelProduto(txt1.getText());
					p.setQuantidade(Integer.parseInt(txt3.getText()));
				}else {
					p = new Produto(0, txt1.getText(), Double.parseDouble(txt2.getText()), Integer.parseInt(txt3.getText()), lojas.getSelectionModel().getSelectedIndex() + 1);
				}
				
				txt1.setText("");
				txt2.setText("");
				txt3.setText("");
				lojas.getSelectionModel().clearSelection();
				
				txt2.setDisable(false);
				lojas.setDisable(false);
				
				if(FactoryDAO.produtoDAO().addProduto(p)) {
					Alerts.showAlert("Loja do Tadeu", null, "Produto registrado.", AlertType.INFORMATION);
					p = FactoryDAO.produtoDAO().getProdutoByName(p.getDescricao());
					LogService.addProd(HomeController.getLogged(), p);
				}else {
					Alerts.showAlert("Loja do Tadeu", null, "Erro ao adicionar produto no banco de dados.", AlertType.ERROR);
				}
				
				
			}
					
		}catch(NumberFormatException ex) {
			//Alerts.showAlert("Loja do Tadeu", null, "Entre com valores valídos.", AlertType.ERROR);
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
		
		
	};
	
	private EventHandler<MouseEvent> editar = e -> {
		Integer id = (txt1.getText().length() > 0) ? Integer.parseInt(txt1.getText()) : 0;
		Double preco = (txt2.getText().length() > 0) ? Double.parseDouble(txt2.getText()) : 0.0;
		Integer ent = (txt3.getText().length() > 0) ? Integer.parseInt(txt3.getText()) : 0;
		
		Produto antigo = FactoryDAO.produtoDAO().getProdutoById(id);
		
		if(id != 0) {
			if(FactoryDAO.produtoDAO().editProduto(id, preco, ent)) {
				Produto novo = FactoryDAO.produtoDAO().getProdutoById(id);
				Alerts.showAlert("Loja do Tadeu", null, "Produto editado com sucesso", AlertType.INFORMATION);
				
				LogService.editProd(HomeController.getLogged(), antigo, novo);
				txt1.setText("");
				txt2.setText("");
				txt3.setText("");
			}else {
				Alerts.showAlert("Loja do Tadeu", null, "Erro ao editar o produto", AlertType.ERROR);
			}
		}
	};
	private EventHandler<MouseEvent> remover = e -> {
		
		Produto prod = FactoryDAO.produtoDAO().getProdutoById(Integer.parseInt(txt1.getText()));
		
		FactoryDAO.produtoDAO().moveToDelEstoque(Integer.parseInt(txt1.getText()));
		Alerts.showAlert("Loja do Tadeu", null, "Produto movido para 'Produtos deletados'", AlertType.INFORMATION);
		
		LogService.removeProd(HomeController.getLogged(), prod);
		
		txt1.setText("");
		txt2.setText("");
		txt3.setText("");
		txt4.setText("");
	};

	@FXML
	private void onAcoesAction() {
		estadoAtual = acoes.getSelectionModel().getSelectedIndex();
		
		setAllVisible(true);
		
		switch (estadoAtual) {

		case 0:	
			
			txt1.setPromptText("Descriçao");
			txt2.setPromptText("Preço");
			txt3.setPromptText("Estoque");
			
			txt4.setVisible(false);
			lojas.setVisible(true);
			lojas.setPromptText("Loja");
			
			lojas.getItems().clear();
			lojas.getItems().addAll(FactoryDAO.lojaDAO().getAllLojas());
			
			bt.setText("Adicionar");
			bt.setOnMouseClicked(adicionar);

			break;

		case 1:
			
			txt4.setVisible(false);
			
			txt1.setPromptText("Id");
			txt2.setPromptText("Preço");
			txt3.setPromptText("Entrada em estoque");
			
			bt.setText("Editar");
			bt.setOnMouseClicked(editar);
			
			break;
		case 2:
			
			txt1.setPromptText("Id");
			txt2.setPromptText("Descrição");
			txt3.setPromptText("Preço");
			txt4.setPromptText("Estoque");
			
			txt2.setDisable(true);
			txt3.setDisable(true);
			txt4.setDisable(true);
		
			bt.setOnMouseClicked(remover);
			bt.setText("Apagar");
			break;
			
		case 3:
			setAllVisible(false);
			lojas.setVisible(false);
			
			Constructor u = new Constructor();
			
			u.ContructStage("/view/gui/TabelaProdutos.fxml", "Produtos", new Image("view/resourses/USER.png"));
			
			break;
		case 4:
			break;

		default:
			break;
		}
	}

	public static Stage getLogisticaStage() {
		return me;
	}

	public static void setLogisticaStage(Stage me) {
		LogisticaController.me = me;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		acoes.setPromptText("Ações...");
		acoes.getItems().addAll(vecAcoes);
		
		txt1.addEventHandler(KeyEvent.KEY_RELEASED, e ->{
			if(estadoAtual == 0){ // ADICIONAR
			
				try {
					Produto p = FactoryDAO.produtoDAO().getDelProduto(txt1.getText());
					if(p != null) {
						
						txt2.setText(p.getPreco().toString());		
						lojas.getSelectionModel().select(p.getLoja().getId() - 1);
						
						txt2.setDisable(true);
						lojas.setDisable(true);
						
					}else {
						txt2.setText("");		
						txt3.setText("");
						lojas.getSelectionModel().clearSelection();
						
						txt2.setDisable(false);
						lojas.setDisable(false);
					}
				}catch(NullPointerException | NumberFormatException exc) {
					txt2.setText("");
					System.out.println(exc.getMessage());
				}
				
			}else if(estadoAtual == 1) {	//EDITAR
				try {
					Produto p = FactoryDAO.produtoDAO().getProdutoById(Integer.parseInt(txt1.getText()));
					if(p != null) {
						txt2.setText(p.getPreco().toString());
					}
				}catch(NullPointerException | NumberFormatException exc) {
					txt2.setText("");
					System.out.println(exc.getMessage());
				}
				
				
				
			}else if(estadoAtual == 2) {	//DELETAR
				try {
					Produto p = FactoryDAO.produtoDAO().getProdutoById(Integer.parseInt(txt1.getText()));
					if(p != null) {
						txt2.setText(p.getDescricao());
						txt3.setText("R$" + p.getPreco());
						txt4.setText(p.getQuantidade().toString());
					}
				}catch(NullPointerException | NumberFormatException exc) {
					txt2.setText("");
					txt3.setText("");
					txt4.setText("");
					System.out.println(exc.getMessage());
				}
			}
		});
		
		sair.setOnMouseClicked(e ->{
			
			Main.getHomeStage().show();
			LogService.saiu(HomeController.getLogged());
			me.close();
			
		});
	}
	
	private void setAllVisible(boolean b) {
		txt1.setVisible(b);
		txt2.setVisible(b);
		txt3.setVisible(b);
		txt4.setVisible(b);
		bt.setVisible(b);
		lojas.setVisible(!b);
		
		txt2.setDisable(false);
		txt3.setDisable(false);
		txt4.setDisable(false);
		
		txt1.setText("");
		txt2.setText("");
		txt3.setText("");
		txt4.setText("");
	}

}
