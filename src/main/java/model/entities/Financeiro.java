package model.entities;

import java.math.BigDecimal;
import java.util.Date;

public class Financeiro {

    private Integer idFinanceiro;
    private Integer idPaciente;
    private BigDecimal valorSessao;
    private Integer quantidadeSessao;
    private Date dataVencimento;
    private String statusPagamento;
    private String mesStatusPagamento;

    public Financeiro() {
    }

    public Financeiro(Integer idFinanceiro, Integer idPaciente, BigDecimal valorSessao, Integer quantidadeSessao, Date dataVencimento, String statusPagamento, String mesStatusPagamento) {
        this.idFinanceiro = idFinanceiro;
        this.idPaciente = idPaciente;
        this.valorSessao = valorSessao;
        this.quantidadeSessao = quantidadeSessao;
        this.dataVencimento = dataVencimento;
        this.statusPagamento = statusPagamento;
        this.mesStatusPagamento = mesStatusPagamento;
    }

    public Integer getIdFinanceiro() {
        return idFinanceiro;
    }

    public void setIdFinanceiro(Integer idFinanceiro) {
        this.idFinanceiro = idFinanceiro;
    }

    public Integer getIdPaciente() {
        return this.idPaciente;
    }

    public void setIdPaciente(Integer idPaciente) {
        this.idPaciente = idPaciente;
    }

    public BigDecimal getValorSessao() {
        return this.valorSessao;
    }

    public void setValorSessao(BigDecimal valorSessao) {
        this.valorSessao = valorSessao;
    }

    public Integer getQuantidadeSessao() {
        return this.quantidadeSessao;
    }

    public void setQuantidadeSessao(Integer quantidadeSessao) {
        this.quantidadeSessao = quantidadeSessao;
    }

    public Date getDataVencimento() {
        return this.dataVencimento;
    }

    public void setDataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public String getStatusPagamento() {
        return this.statusPagamento;
    }

    public void setStatusPagamento(String statusPagamento) {
        this.statusPagamento = statusPagamento;
    }

    public String getMesStatusPagamento() {
        return this.mesStatusPagamento;
    }

    public void setMesStatusPagamento(String mesStatusPagamento) {
        this.mesStatusPagamento = mesStatusPagamento;
    }
}
