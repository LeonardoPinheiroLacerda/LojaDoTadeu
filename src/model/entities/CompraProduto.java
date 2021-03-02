package model.entities;

public class CompraProduto {

	private Produto produto;
	private Integer quantidade;
	
	public Double total() {
		return produto.getPreco() * quantidade;
	}
	
	public CompraProduto(Produto produto, Integer quantidade) {
		
		if(produto.getQuantidade() >= quantidade) {
			this.produto = produto;
			this.quantidade = quantidade;
		}else {
			this.produto = null;
			this.quantidade = null;
		}
		
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public String toString() {
		return produto.getDescricao() + " X " + quantidade + " : " + String.format("%.2f", total());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((produto == null) ? 0 : produto.hashCode());
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
		CompraProduto other = (CompraProduto) obj;
		if (produto == null) {
			if (other.produto != null)
				return false;
		} else if (!produto.equals(other.produto))
			return false;
		return true;
	}	
}
