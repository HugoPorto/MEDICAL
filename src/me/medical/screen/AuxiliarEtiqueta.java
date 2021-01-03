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
public class AuxiliarEtiqueta {
    
    private String codigo;
    private String prontuario;
    private String data;
    private String nomepaciente;
    private String tipoacomodacao;
    private String numero;
    private String leito;

    public AuxiliarEtiqueta(String codigo, String prontuario, String data, String nomepaciente, String tipoacomodacao, String numero, String leito) {
        this.codigo = codigo;
        this.prontuario = prontuario;
        this.data = data;
        this.nomepaciente = nomepaciente;
        this.tipoacomodacao = tipoacomodacao;
        this.numero = numero;
        this.leito = leito;
    }
    

    
    
    AuxiliarEtiqueta(){}

    public String getNomepaciente() {
        return nomepaciente;
    }

    public void setNomepaciente(String nomepaciente) {
        this.nomepaciente = nomepaciente;
    }

    public String getTipoacomodacao() {
        return tipoacomodacao;
    }

    public void setTipoacomodacao(String tipoacomodacao) {
        this.tipoacomodacao = tipoacomodacao;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getLeito() {
        return leito;
    }

    public void setLeito(String leito) {
        this.leito = leito;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getProntuario() {
        return prontuario;
    }

    public void setProntuario(String prontuario) {
        this.prontuario = prontuario;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
    
    
    
}
