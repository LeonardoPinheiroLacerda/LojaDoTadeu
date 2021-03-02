package model.services;

import java.util.ArrayList;
import java.util.Collection;

import javafx.scene.control.Alert.AlertType;
import model.dao.FactoryDAO;
import model.entities.Funcionario;
import util.Alerts;

public class ADMService {

	public boolean demiteFuncionario(Funcionario funcionario) {

		if(FactoryDAO.funcionarioDAO().removeFuncionario(funcionario)) {
			Alerts.showAlert("Loja do tadeu", null, "Funcionario demitido.", AlertType.INFORMATION);
			return true;
		}else {
			Alerts.showAlert("Loja do tadeu", null, "Erro de banco de dados.", AlertType.ERROR);
		}
		return false;
		
	}
	
	public boolean demiteGerente(Funcionario gerente) {
		
		
		Collection<Funcionario> funcionarios = (ArrayList<Funcionario>) FactoryDAO.funcionarioDAO().getAllfunc(gerente.getId(), "Caixa");
		funcionarios.addAll(FactoryDAO.funcionarioDAO().getAllfunc(gerente.getId(),"Logistica"));
		
		if(FactoryDAO.funcionarioDAO().removeGerente(gerente) ) {
			Alerts.showAlert("Loja do tadeu", null, "Gerente demitido.", AlertType.INFORMATION);
			
			for(Funcionario f: funcionarios) {
				FactoryDAO.funcionarioDAO().trocaGerente(null, f);
			}
			
			return true;
		}else {
			Alerts.showAlert("Loja do tadeu", null, "Erro de banco de dados.", AlertType.ERROR);
		}
		return false;
	}
	
	public boolean promoveFuncionario(Funcionario funcionario) {
		
		if(funcionario.getCargo().equals("ADM")) {
			Alerts.showAlert("Loja do tadeu", null, "Um ADM não pode ser promovido para Gerente", AlertType.WARNING);
		}else if(funcionario.getCargo().equals("Gerente")) {
			Alerts.showAlert("Loja do tadeu", null, "O funcionarioionario já é gerente.", AlertType.WARNING);
		}else if(FactoryDAO.funcionarioDAO().promoveFuncionario(funcionario)) {
			Alerts.showAlert("Loja do tadeu", null, "Funcionario promovido.", AlertType.INFORMATION);
			return true;
		}else {
			Alerts.showAlert("Loja do tadeu", null, "Erro de banco de dados.", AlertType.ERROR);
		}
		
		return false;
	}
	
	public boolean trocaSenha(String senha, Funcionario funcionario) {
		if(FactoryDAO.cadastroDAO().updatePassword(senha, funcionario)){
			Alerts.showAlert("Loja do Tadeu", null, "Senha Alterada com sucesso!", AlertType.INFORMATION);
			return true;
		}else {
			Alerts.showAlert("Loja do Tadeu", null, "Erro de banco de dados.", AlertType.ERROR);
		}
		
		return false;
	}
	
	public boolean trocaGerente(Funcionario funcionario, Funcionario gerente) {
		
		if(funcionario != null && gerente != null) {
			if(FactoryDAO.funcionarioDAO().trocaGerente(gerente, funcionario)) {
				Alerts.showAlert("Loja do Tadeu", null, "Gerencia alterada.", AlertType.INFORMATION);
				return true;
			}else {
				Alerts.showAlert("Loja do Tadeu", null, "Erro de banco de dados.", AlertType.ERROR);
			}
		}else {
			Alerts.showAlert("Loja do Tadeu", null, "Preencha todas as informações para efetuar a ação.", AlertType.WARNING);
		}
		return false;
	}
	
}
