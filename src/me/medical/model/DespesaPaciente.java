/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.medical.model;

/**
 *
 * @author Adriano Zanette
 */
public class DespesaPaciente {
    
    private int idDespesaPaciente;
    private ProdutoBean produto;
    private double quantidade;
    private double valor;

    /**
     * @return the idDespesaPaciente
     */
    public int getIdDespesaPaciente() {
        return idDespesaPaciente;
    }

    /**
     * @param idDespesaPaciente the idDespesaPaciente to set
     */
    public void setIdDespesaPaciente(int idDespesaPaciente) {
        this.idDespesaPaciente = idDespesaPaciente;
    }

    /**
     * @return the produto
     */
    public ProdutoBean getProduto() {
        return produto;
    }

    /**
     * @param produto the produto to set
     */
    public void setProduto(ProdutoBean produto) {
        this.produto = produto;
    }

    /**
     * @return the quantidade
     */
    public double getQuantidade() {
        return quantidade;
    }

    /**
     * @param quantidade the quantidade to set
     */
    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }

    /**
     * @return the valor
     */
    public double getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(double valor) {
        this.valor = valor;
    }
    
}
