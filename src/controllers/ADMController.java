package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.dao.FactoryDAO;
import model.entities.Funcionario;
import model.services.ADMService;
import model.services.LogService;
import util.Alerts;

public class ADMController extends ADMService implements Initializable{

	private static Stage me;
	
	@FXML
	private ComboBox<Funcionario> cb1;
	
	@FXML
	private ComboBox<Funcionario> cb2;
	
	@FXML
	private TextField txt1;
	
	@FXML
	private TextField txt2;
	
	@FXML
	private TextField txt3;
	
	@FXML
	private TextField txt4;
	
	@FXML
	private Button bt;
	
	@FXML
	private CheckBox checkbox;
	
	@FXML
	private ComboBox<String> acao;	
	
	@FXML
	private Label sair;
	
	
	@FXML
	public void acaoOnAction() {
	
		setAllInvisible();
		bt.setVisible(true);
		switch(acao.getSelectionModel().getSelectedIndex()) {
		case 0:
			
			cb1.setVisible(true);
			cb1.setPromptText("Funcionario");
			
			cb1.getItems().clear();
			cb1.getItems().addAll(FactoryDAO.funcionarioDAO().getAllSolDesligamento());
			
			bt.setVisible(true);
			bt.setText("Desligar");
			
			break;
		case 1:
			txt1.setVisible(true);
			txt2.setVisible(true);
			txt3.setVisible(true);
			txt4.setVisible(true);
			
			txt2.setDisable(true);
			txt3.setDisable(true);
			txt4.setDisable(true);
			
			txt1.setPromptText("Id");
			txt2.setPromptText("Nome");
			txt3.setPromptText("Sobrenome");
			txt4.setPromptText("Cargo");
			
			bt.setVisible(true);
			bt.setText("Promover para gerente");
			
			break;
		case 2:
			
			cb1.setVisible(true);
			cb1.setPromptText("Funcionario");
			
			cb1.getItems().clear();
			cb1.getItems().addAll(FactoryDAO.funcionarioDAO().getAllGerentes());
			
			checkbox.setSelected(false);
			checkbox.setVisible(true);
			checkbox.setText("Desligar o funcionario(a) selecionado"); 
			
			bt.setVisible(true);
			bt.setText("Desligar");
			
			break;
		case 3:
			
			cb1.setVisible(true);
			cb1.getItems().clear();
			cb1.getItems().addAll(FactoryDAO.funcionarioDAO().getAllfunc());
			
			txt2.setVisible(true);
			
			cb1.setPromptText("Funcionario");
			txt2.setPromptText("Nova Senha");
			
			bt.setVisible(true);
			bt.setText("Alterar senha");
			
			break;
		case 4:
			
			cb1.getItems().clear();
			cb2.getItems().clear();
			cb1.setVisible(true);
			cb2.setVisible(true);
			cb1.getItems().addAll(FactoryDAO.funcionarioDAO().getFunc());
			cb2.getItems().addAll(FactoryDAO.funcionarioDAO().getAllGerentes());
			
			cb1.setPromptText("Funcionario");
			cb2.setPromptText("Novo gerente");
			
			checkbox.setSelected(false);
			checkbox.setVisible(true);
			checkbox.setText("Mudar a gerência do funcionario(a) selecionado."); 
			
			bt.setVisible(true);
			bt.setText("Mudar gerencia");
			
			break;
		default:
			break;
		
		}
		
		
	}
	
	@FXML
	public void btOnAction() {
		
		switch(acao.getSelectionModel().getSelectedIndex()){
			
		case 0:	
		
			if(demiteFuncionario(cb1.getSelectionModel().getSelectedItem())) {
				
				LogService.desligamento(cb1.getSelectionModel().getSelectedItem());
				
				cb1.getItems().clear();
				cb1.getItems().addAll(FactoryDAO.funcionarioDAO().getAllSolDesligamento());
			}
			break;
			
		case 1:
			
			Funcionario func = FactoryDAO.funcionarioDAO().searchById(Integer.parseInt(txt1.getText()));
			
			if(promoveFuncionario(func)) {
				
				LogService.promocao(func);
				
				txt1.setText("");
				txt2.setText("");
				txt3.setText("");
				txt4.setText("");
			}
			
			break;
		case 2:
			
			if(checkbox.isSelected()) {
				if(demiteGerente(cb1.getSelectionModel().getSelectedItem())) {
					
					LogService.desligamento(cb1.getSelectionModel().getSelectedItem());
					
					cb1.getItems().clear();
					cb1.getItems().addAll(FactoryDAO.funcionarioDAO().getAllGerentes());
				}
			}else {
				Alerts.showAlert("Loja do tadeu", null, "Confirme o desligamento antes de efetua-lo.", AlertType.INFORMATION);
			}
			
			
			break;
		case 3:
			
			Funcionario fun = cb1.getSelectionModel().getSelectedIndex() >= 0 ? cb1.getSelectionModel().getSelectedItem() : null;
			
			if(fun != null && txt2.getText().length() > 0) {
				if(trocaSenha(txt2.getText(), fun)) {
					
					LogService.trocaSenha(fun);
					
					txt2.setText("");
				}
			}				
			else {
				Alerts.showAlert("Loja do Tadeu", null, "Preencha todos os dados para efetuar a ação.", AlertType.INFORMATION);
			}
			
			break;
		case 4:
			
			Funcionario funcionario = cb1.getSelectionModel().getSelectedIndex() >= 0 ? cb1.getSelectionModel().getSelectedItem() : null;
			Funcionario gerente =  cb2.getSelectionModel().getSelectedIndex() >= 0 ? cb2.getSelectionModel().getSelectedItem() : null;
			
			if(checkbox.isSelected()) {
				trocaGerente(funcionario, gerente);
				
				LogService.trocaGerencia(funcionario, gerente);
				
			}else {
				Alerts.showAlert("Loja do Tadeu", null, "Confirme a mudança para conclui-la", AlertType.INFORMATION);
			}
			
			break;
		default:
			break;
			
		}
	}
	
	public static Stage getADMStage() {
		return me;
	}

	public static void setADMStage(Stage me) {
		ADMController.me = me;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		acao.getItems().add("Atender solicitação de desligamento");
		acao.getItems().add("Promover funcionario para gerente");
		acao.getItems().add("Demetir gerente");
		acao.getItems().add("Alterar senha de funcionario");
		acao.getItems().add("Mudar funcionario de gerência");
		
		bt.setVisible(false);
		txt1.setOnKeyReleased(e ->{
			
			Funcionario func = null;
			
			try {
				func = FactoryDAO.funcionarioDAO().searchById(Integer.parseInt(txt1.getText()));
				
				txt2.setText(func.getNome());
				txt3.setText(func.getSobrenome());
				txt4.setText(func.getCargo());	
				
			}catch(Exception e1) {
				txt2.setText("");
				txt3.setText("");
				txt4.setText("");	
			}
			
		});
		
		sair.setOnMouseClicked(e ->{
			Main.getHomeStage().show();
			LogService.saiu(HomeController.getLogged());
			me.close();
		});
	}
	
	private void setAllInvisible() {
		txt1.setVisible(false);
		txt2.setVisible(false);
		txt3.setVisible(false);
		txt4.setVisible(false);
		cb1.setVisible(false);
		cb2.setVisible(false);
		bt.setVisible(false);
		checkbox.setVisible(false);
		txt2.setDisable(false);
	}
	
}
