package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.dao.FactoryDAO;
import model.entities.Cadastro;
import model.entities.Funcionario;
import model.entities.Loja;
import model.services.LogService;
import util.Alerts;

public class CadastroController implements Initializable {

	@FXML
	private TextField usuario;

	@FXML
	private TextField senha;

	@FXML
	private TextField nome;

	@FXML
	private TextField sobrenome;

	@FXML
	private TextField ramal;

	@FXML
	private ComboBox<String> cargo;

	@FXML
	private ComboBox<Loja> loja;

	@FXML
	private ComboBox<Funcionario> gerente;

	@FXML
	private Button cadastrar;
	
	private static Stage me;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
		gerente.getItems().addAll(FactoryDAO.funcionarioDAO().getAllGerentes());
		
		String[] cargos = new String[] { "Caixa", "Gerente",  "Logistica"};
		cargo.getItems().addAll(cargos);
		
		loja.getItems().addAll(FactoryDAO.lojaDAO().getAllLojas());
		
		cargo.setOnAction(e ->{
			
			if(cargo.getSelectionModel().getSelectedItem() == "Gerente") {
				gerente.setDisable(true);
			}else {
				gerente.setDisable(false);
			}
			
		});
		
		ramal.textProperty().addListener((obs, oldValue, newValue) ->{
			
			if(newValue != null && !newValue.matches("\\d*")) {
				ramal.setText(oldValue);
			}
			
		});
			
	}
	
	public void onCadastrarAction() {
		try {
			if(usuario.getText().length() > 0 && senha.getText().length() > 0 && nome.getText().length() > 0 && sobrenome.getText().length() > 0 && ramal.getText().length() > 0 && cargo.getSelectionModel().getSelectedItem().length() > 0 && loja.getSelectionModel().getSelectedItem() != null && (gerente.getSelectionModel().getSelectedItem() != null || gerente.isDisable() == true)) {
				
				Funcionario funcionario = new Funcionario(0, nome.getText(),sobrenome.getText(), ramal.getText(), loja.getSelectionModel().getSelectedItem().getId(), cargo.getSelectionModel().getSelectedItem(), 0.0, gerente.getSelectionModel().getSelectedItem() != null ? gerente.getSelectionModel().getSelectedItem().getId() : 0);
				funcionario.setCadastro(new Cadastro(0, usuario.getText(), senha.getText()));
//				
//				System.out.println("Nome: " + funcionario.getNome());
//				System.out.println("Sobrenome: " + funcionario.getSobrenome());
//				System.out.println("Ramal: " + funcionario.getRamal());
//				System.out.println("Loja: " + funcionario.getLoja());
//				System.out.println("Cargo: " + funcionario.getCargo());
//				//System.out.println("Gerente: " + funcionario.getGerente().getId() + " - " + funcionario.getGerente());
//				System.out.println("\nCadastro: ");
//				System.out.println("   Usuario: " + funcionario.getCadastro().getUsuario());
//				System.out.println("   Senha: " + funcionario.getCadastro().getSenha());
//				System.out.println("----------------------------");
				
				if(FactoryDAO.funcionarioDAO().insertFuncionario(funcionario)) {
					Alerts.showAlert("Loja do Tadeu", null, "Funcionario cadastrado.", AlertType.INFORMATION);
					
					//POG para receber o id do funcionario
					LogService.novoFuncionario(FactoryDAO.funcionarioDAO().login(usuario.getText(), senha.getText()));
					
				}else {
					Alerts.showAlert("Loja do Tadeu", null, "Confira se não há dados invalidos.", AlertType.ERROR);
				}
				
			}else {
				Alerts.showAlert("Loja do Tadeu", null, "Preencha todos os campos para continuar\ncod. 1", AlertType.WARNING);
			}
		}catch(NullPointerException e) {
			Alerts.showAlert("Loja do Tadeu", null, "Preencha todos os campos para continuar\\ncod. 2 - " + e.getMessage(), AlertType.WARNING);
			e.printStackTrace();
		}
	}
	
	public void onVoltarMouseClicked() {
		Main.getHomeStage().show();
		me.close();
	}

	public static Stage getCadastroStage() {
		return me;
	}

	public static void setCadastroStage(Stage me) {
		CadastroController.me = me;
	}

}
