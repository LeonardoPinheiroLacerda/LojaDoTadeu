package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import model.dao.FactoryDAO;
import model.entities.Funcionario;
import model.services.LogService;
import util.Alerts;
import util.Constructor;

public class HomeController implements Initializable {

	@FXML
	private TextField user;

	@FXML
	private PasswordField pass;

	@FXML
	private Button signIn;

	@FXML
	private Button logIn;

	@FXML
	private AnchorPane mainPane;
	
	private static Funcionario logged;

	@FXML
	public void onLogInMouseClick() {
		if (user.getText().length() > 0 && pass.getText().length() > 0) {
			logged = FactoryDAO.funcionarioDAO().login(user.getText(), pass.getText());
			if (logged != null) {
				System.out.println(logged.toString());

				user.setText("");
				pass.setText("");
				user.requestFocus();
				
				Main.getHomeStage().close();
				Constructor u = new Constructor();
				
				switch (logged.getCargo()) {

				case "Caixa":
					
					CaixaController.setCaixaStage(u.ContructStage("/view/gui/Caixa.fxml", logged.getNome() + " " + logged.getSobrenome(), new Image("view/resourses/USER.png")));
					
					break;
				case "Logistica":
					
					LogisticaController.setLogisticaStage(u.ContructStage("/view/gui/Logistica.fxml", logged.getNome() + " " + logged.getSobrenome(), new Image("view/resourses/USER.png")));
					
					break;
				case "Gerente":
					
					GerenteController.setGerenteStage(u.ContructStage("/view/gui/Gerente.fxml", logged.getNome() + " " + logged.getSobrenome(), new Image("view/resourses/USER.png")));
					
					break;
				case "ADM":
					
					ADMController.setADMStage(u.ContructStage("/view/gui/Adm.fxml", logged.getNome() + " " + logged.getSobrenome(), new Image("view/resourses/USER.png")));
					break;
				}
				
				LogService.entrou(logged);

			}else {
				Alerts.showAlert("Loja do Tadeu", null, "Login inválido.", AlertType.WARNING);
			}
		}else {
			Alerts.showAlert("Loja do Tadeu", null, "Entre com todos os dados solicitados para continuar.", AlertType.WARNING);
		}
	}

	@FXML
	public void onSignInMouseClick() {
		Constructor u = new Constructor();
		CadastroController.setCadastroStage(u.ContructStage("/view/gui/Cadastro.fxml", "Cadastro", new Image("view/resourses/USER.png")));
		Main.getHomeStage().close();
	}

	@FXML
	public void onTxtKeyPressed() {

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		EventHandler<KeyEvent> handler = e -> {
			if (e.getCode() == KeyCode.ENTER)
				onLogInMouseClick();
		};
		
		user.setOnKeyPressed(handler);
		pass.setOnKeyPressed(handler);
		
	}

	public static Funcionario getLogged() {
		return logged;
	}

	public static void setLogged(Funcionario logged) {
		HomeController.logged = logged;
	}
}
