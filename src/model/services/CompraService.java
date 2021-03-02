package model.services;

import java.util.Date;

import model.dao.FactoryDAO;
import model.entities.Compra;
import model.entities.CompraProduto;

public class CompraService {

	public Compra toCompra(String prodString) {
		Compra compra = new Compra();
		
		if(prodString != "") {
			
			String[] prodVec = prodString.split(";");
			
			for(int i = 0; i < prodVec.length; i ++) {

				String[] ids = prodVec[i].split("x");

				compra.add(new CompraProduto(FactoryDAO.produtoDAO().getProdutoById(Integer.parseInt(ids[0])), Integer.parseInt(ids[1])));
			}
		}
		
		return compra;
	}
	
	
	public Compra toCompra(String prodString, Date moment) {
		
		Compra compra = new Compra();
		
		if(prodString != "") {
			
			String[] prodVec = prodString.split(";");
			
			for(int i = 0; i < prodVec.length; i ++) {
	
				String[] ids = prodVec[i].split("x");
	
				compra.add(new CompraProduto(FactoryDAO.produtoDAO().getProdutoById(Integer.parseInt(ids[0])), Integer.parseInt(ids[1])));
			}
			
			compra.setDate(moment);
		}
		return compra;
	}
	
}
