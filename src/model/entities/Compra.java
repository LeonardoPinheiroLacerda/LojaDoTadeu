package model.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class Compra {

	private Collection<CompraProduto> compras = new ArrayList<>();
	private Date date = new Date();

	public Collection<CompraProduto> getCompras() {
		return compras;
	}
	
	public boolean add(CompraProduto cp) {
		
		if (cp.getProduto() != null && cp.getQuantidade() != null) {
			
			if(compras.contains(cp)) {
				for(CompraProduto c : compras){
					
					if(c.equals(cp)) {

						if(c.getQuantidade() + cp.getQuantidade() <= c.getProduto().getQuantidade()) {
							c.setQuantidade(c.getQuantidade() + cp.getQuantidade());
							return true;
						}else {
							return false;
						}
					}
					
				};
			}else if(cp != null){
				compras.add(cp);
				
				return true;
			}
		}
		
		return false;
	}
	
	public void remove(CompraProduto cp) {
		
		compras.remove(cp);
		
	}
	
	public CompraProduto[] toArray() {
		
		CompraProduto[] cp = new CompraProduto[compras.size()];
		
		int i = 0;
		for(CompraProduto c : compras) {
			cp[i] = c;
			i ++;
		}
		
		return cp;
		
	}
	
	public Double total() {

		Double total = 0.0;
		
		for(CompraProduto c : compras) {
			total += c.total();
		}
		
		return total;
	}
	

	@Override
	public String toString() {
		return compras.toString();
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
