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
public class EVOLUCAOPACIENTE_MODEL {
    
    private int codigo;
    private String NomePaciente;
    private String NumeroLeito;
    private String Evolucao;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNomePaciente() {
        return NomePaciente;
    }

    public void setNomePaciente(String NomePaciente) {
        this.NomePaciente = NomePaciente;
    }

    public String getNumeroLeito() {
        return NumeroLeito;
    }

    public void setNumeroLeito(String NumeroLeito) {
        this.NumeroLeito = NumeroLeito;
    }

    public String getEvolucao() {
        return Evolucao;
    }

    public void setEvolucao(String Evolucao) {
        this.Evolucao = Evolucao;
    }
    
    
    
    
    
}
