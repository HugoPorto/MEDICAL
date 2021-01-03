/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.medical.model;

import java.util.Date;

/**
 *
 * @author Adriano Zanette
 */
public class SenhaBean {
    
    private int idSenha;
    private String senha;
    private int status;
    private int prioridade;
    private String tipo;
    private Date data; private String DataHora;
    private String local;
    private String nomePaciente;
    

    /**
     * @return the idSenha
     */
    public int getIdSenha() {
        return idSenha;
    }

    /**
     * @param idSenha the idSenha to set
     */
    public void setIdSenha(int idSenha) {
        this.idSenha = idSenha;
    }

    /**
     * @return the senha
     */
    public String getSenha() {
        return senha;
    }

    /**
     * @param senha the senha to set
     */
    public void setSenha(String senha) {
        this.senha = senha;
    }

    /**
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the data
     */
    public Date getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(Date data) {
        this.data = data;
    }

    /**
     * @return the prioridade
     */
    public int getPrioridade() {
        return prioridade;
    }

    /**
     * @param prioridade the prioridade to set
     */
    public void setPrioridade(int prioridade) {
        this.prioridade = prioridade;
    }

    /**
     * @return the DataHora
     */
    public String getDataHora() {
        return DataHora;
    }

    /**
     * @param DataHora the DataHora to set
     */
    public void setDataHora(String DataHora) {
        this.DataHora = DataHora;
    }

    /**
     * @return the local
     */
    public String getLocal() {
        return local;
    }

    /**
     * @param local the local to set
     */
    public void setLocal(String local) {
        this.local = local;
    }

    /**
     * @return the nomePaciente
     */
    public String getNomePaciente() {
        return nomePaciente;
    }

    /**
     * @param nomePaciente the nomePaciente to set
     */
    public void setNomePaciente(String nomePaciente) {
        this.nomePaciente = nomePaciente;
    }
    
    
}
