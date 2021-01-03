/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.medical.screen;

/**
 *
 * @author Adriano Zanette
 */
public class AuxiliarOsFarmacia {
    
private String codigo;
private String datapedido;
private String setor;
private String medicacao;
private String qtd;
private String tipo;
private String solicitante;
private String status;

    public AuxiliarOsFarmacia(String codigo, String datapedido, String setor, String medicacao, String qtd, String tipo, String solicitante, String status) {
        this.codigo = codigo;
        this.datapedido = datapedido;
        this.setor = setor;
        this.medicacao = medicacao;
        this.qtd = qtd;
        this.tipo = tipo;
        this.solicitante = solicitante;
        this.status = status;
    }
public AuxiliarOsFarmacia(){}

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDatapedido() {
        return datapedido;
    }

    public void setDatapedido(String datapedido) {
        this.datapedido = datapedido;
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

    public String getMedicacao() {
        return medicacao;
    }

    public void setMedicacao(String medicacao) {
        this.medicacao = medicacao;
    }

    public String getQtd() {
        return qtd;
    }

    public void setQtd(String qtd) {
        this.qtd = qtd;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(String solicitante) {
        this.solicitante = solicitante;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

   


    
    
}
