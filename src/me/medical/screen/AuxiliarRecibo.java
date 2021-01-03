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
public class AuxiliarRecibo {
    
    private String data;
    private String nome;
    private String referente;
    private String valor;
    private String quantia;

    public AuxiliarRecibo(String data, String nome, String referente, String valor, String quantia) {
        this.data = data;
        this.nome = nome;
        this.referente = referente;
        this.valor = valor;
        this.quantia = quantia;
    }
    
    AuxiliarRecibo(){}

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getReferente() {
        return referente;
    }

    public void setReferente(String referente) {
        this.referente = referente;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getQuantia() {
        return quantia;
    }

    public void setQuantia(String quantia) {
        this.quantia = quantia;
    }
    
    
}
