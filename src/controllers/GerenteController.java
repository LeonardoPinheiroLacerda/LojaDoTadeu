package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.dao.FactoryDAO;
import model.entities.Funcionario;
import model.entities.Loja;
import model.services.LogService;
import util.Alerts;

public class GerenteController implements Initializable{

	@FXML
	private ComboBox<String> cargo;
	
	@FXML
	private TableView<Funcionario> tabela;
	
	@FXML
	private ComboBox<Funcionario> func;
	
	@FXML
	private Button sol;
	
	@FXML
	private Label voltar;
	

	@FXML
	public void cargoOnAction() {
		
		TableColumn<Funcionario, Integer> matricula = new TableColumn<>("Matricula");
		TableColumn<Funcionario, String> nome = new TableColumn<>("Nome");
		TableColumn<Funcionario, String> sobrenome = new TableColumn<>("Sobrenome");
		TableColumn<Funcionario, Loja> loja = new TableColumn<>("Loja");
		
		matricula.setCellValueFactory(new PropertyValueFactory<Funcionario, Integer>("id"));
		nome.setCellValueFactory(new PropertyValueFactory<Funcionario, String>("nome"));
		sobrenome.setCellValueFactory(new PropertyValueFactory<Funcionario, String>("sobrenome"));
		loja.setCellValueFactory(new PropertyValueFactory<Funcionario, Loja>("loja"));
		
		tabela.setVisible(true);
		tabela.getColumns().clear();
		
		tabela.getColumns().add(matricula);
		tabela.getColumns().add(nome);
		tabela.getColumns().add(sobrenome);
		tabela.getColumns().add(loja);
			
		if(cargo.getSelectionModel().getSelectedItem() == "Caixa") {
			
			TableColumn<Funcionario, Double> lucro = new TableColumn<>("Lucro");
			
			lucro.setCellValueFactory(new PropertyValueFactory<Funcionario, Double>("vendas"));
			
			tabela.getColumns().add(lucro);
			
		}
		
		ObservableList<Funcionario> funcs = FXCollections.observableArrayList(FactoryDAO.funcionarioDAO().getAllfunc(HomeController.getLogged().getId(), cargo.getSelectionModel().getSelectedItem()));
		tabela.setItems(funcs);
		
		func.getItems().clear();
		func.getItems().addAll(funcs);
			
	}
	
	@FXML
	public void funcOnAction() {
		
	}
	
	@FXML
	public void solOnAction() {
		
		if(func.getSelectionModel().getSelectedItem() != null) {

			Funcionario desl = func.getSelectionModel().getSelectedItem();
			if(FactoryDAO.funcionarioDAO().addToSolDesligamento(desl)) {
				Alerts.showAlert("Loja do Tadeu", null, "Solicitação de desligamento concluida, funcionario será desligado do sistema assim que o ADM do sistema confirmar o deligamento.", AlertType.INFORMATION);
				LogService.solDesl(HomeController.getLogged(), desl);
			}else{
				Alerts.showAlert("Loja do Tadeu", null, "Solicitação ja efetivada", AlertType.ERROR);
			}
			
		}
		
	}
	
	@FXML
	public void voltarOnMouseClicked() {
		Main.getHomeStage().show();
		LogService.saiu(HomeController.getLogged());
		me.close();
	}
	
	private static Stage me;
	
	public static Stage getGerenteStage() {
		return me;
	}

	public static void setGerenteStage(Stage me) {
		GerenteController.me = me;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {

		cargo.getItems().add("Caixa");
		cargo.getItems().add("Logistica");
		
	}
	
	
	
}
