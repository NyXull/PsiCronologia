package util.enums;

public enum TipoCancelamento {
	
	PONTUAL("Cancelar apenas esse dia"),
	RECORRENTE("Cancelar toda a recorrência"),
	CANCELADO("Ação de cancelamento não confirmada");
	
	private final String descricao;
	
	TipoCancelamento(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	@Override
	public String toString() {
		return descricao;
	}
}