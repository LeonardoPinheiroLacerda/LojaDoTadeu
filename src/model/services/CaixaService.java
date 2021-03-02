package model.services;

import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import model.dao.FactoryDAO;
import model.entities.Compra;
import model.entities.CompraProduto;
import model.entities.Funcionario;
import model.entities.Produto;
import util.Alerts;

public class CaixaService {

	public void addItem(String prod, String qtd, ListView<CompraProduto> list, Label preco, Compra compra, Funcionario funcionario) {
		
		if(prod.length() > 0 && qtd.length() > 0) {
			
			Produto produto = null;
			
			try{
				Integer id = Integer.parseInt(prod);
				produto = FactoryDAO.produtoDAO().getProdutoById(id);
				
			}catch(NumberFormatException e) {
				String nome = prod;
				produto = FactoryDAO.produtoDAO().getProdutoByName(nome);
			}
			
			if(produto != null && funcionario.getLoja().getId() == produto.getLoja().getId()) {
				
				try {
					
					Integer quantidade = Integer.parseInt(qtd);
					
					CompraProduto cp = new CompraProduto(produto, quantidade);
					
					if(compra.add(cp) && cp != null) {
						updateListView(compra, list);
						updatePrice(compra, preco);
						
						System.out.println(compra.toString());
						
					}else {
						Alerts.showAlert("Loja do Tadeu", null, "Quantidade não disponivel.", AlertType.INFORMATION);
					}
					
				}catch(NumberFormatException e) {
					Alerts.showAlert("Loja do Tadeu", null, "Quantidade invalida", AlertType.ERROR);
				}
				
			}else if(produto != null && funcionario.getLoja().getId() != produto.getLoja().getId()) {
			
				Alerts.showAlert("Loja do Tadeu", null, "Produto não localizado na unidade do(a) funcionario(a) " + funcionario.getNome() + " " + funcionario.getSobrenome() + ".", AlertType.INFORMATION);
				
			}else {
				Alerts.showAlert("Loja do Tadeu", null, "Produto não encontrado.", AlertType.INFORMATION);
			}
			
		}
		
	}
	
	public void removeItem(Compra compra, ListView<CompraProduto> list, Label preco) {
		compra.remove(list.getSelectionModel().getSelectedItem());
		updateListView(compra, list);
		updatePrice(compra, preco);
	}
	
	
	private void updateListView(Compra compra, ListView<CompraProduto> list) {
		list.getItems().clear();
		list.getItems().addAll(compra.toArray());
	}
	private void updatePrice(Compra compra, Label preco) {
		preco.setText("R$ " + String.format("%.2f", compra.total()));
	}
	
	public String generateProdString(Compra compra) {
		String str = "";
		for(CompraProduto c : compra.getCompras()) {
			str += c.getProduto().getId() + "x" + c.getQuantidade() + ";";
		}		
		return str;
	}
	
}
