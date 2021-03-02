package model.entities;

import model.dao.FactoryDAO;

public class Loja {

	private Integer id;
	private String nomeLoja;
	
	public Loja(Integer id) {
		this.id = id;
		this.nomeLoja = FactoryDAO.lojaDAO().getLojaNome(this.id);
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNomeLoja() {
		return nomeLoja;
	}
	public void setNomeLoja(String nomeLoja) {
		this.nomeLoja = nomeLoja;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nomeLoja == null) ? 0 : nomeLoja.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Loja other = (Loja) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nomeLoja == null) {
			if (other.nomeLoja != null)
				return false;
		} else if (!nomeLoja.equals(other.nomeLoja))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return nomeLoja;
	}
}
