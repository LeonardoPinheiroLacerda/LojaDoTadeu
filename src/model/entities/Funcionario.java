package model.entities;

import model.dao.FactoryDAO;

public class Funcionario {
	
	private Integer id;
	private String nome;
	private String sobrenome;
	private String ramal;
	private Loja loja;
	private String cargo;
	private Double vendas;
	private Integer gerente_id;
	private Funcionario gerente;
	
	private Cadastro cadastro;

	public Funcionario(Integer id, String nome, String sobrenome, String ramal, Integer loja_id, String cargo, Double vendas,
			Integer gerente_id) {
		this.id = id;
		this.nome = nome;
		this.sobrenome = sobrenome;
		this.ramal = ramal;
		this.loja = new Loja(loja_id);		
		this.cargo = cargo;
		this.vendas = vendas;
		this.gerente_id = gerente_id;
		this.gerente = (gerente_id != 0 && gerente_id != -1) ? FactoryDAO.funcionarioDAO().searchById(gerente_id) : null;
		this.cadastro = FactoryDAO.cadastroDAO().getCadastroById(id);
	}

	public Funcionario() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public String getRamal() {
		return ramal;
	}

	public void setRamal(String ramal) {
		this.ramal = ramal;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public Double getVendas() {
		return vendas;
	}

	public void setVendas(Double vendas) {
		this.vendas = vendas;
	}

	public Integer getGerente_id() {
		return gerente_id;
	}

	public void setGerente_id(Integer gerente_id) {
		this.gerente_id = gerente_id;
	}

	public Loja getLoja() {
		return loja;
	}

	public Funcionario getGerente() {
		return gerente;
	}

	
	
	public Cadastro getCadastro() {
		return cadastro;
	}

	public void setCadastro(Cadastro cadastro) {
		this.cadastro = cadastro;
	}

	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cadastro == null) ? 0 : cadastro.hashCode());
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
		Funcionario other = (Funcionario) obj;
		if (cadastro == null) {
			if (other.cadastro != null)
				return false;
		} else if (!cadastro.equals(other.cadastro))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return nome + " " + sobrenome;
	}
}
