package util.enums;

public enum TipoStatusPagamento {

    PAGO("Pagamento já foi efetuado"),
    ABERTO("Pagamento não foi efetuado"),
    EXCLUIDO("Status excluído");

    private final String descricao;

    TipoStatusPagamento(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
