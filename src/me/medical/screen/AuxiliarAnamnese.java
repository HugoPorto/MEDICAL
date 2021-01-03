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
public class AuxiliarAnamnese {
    
    private String nomepaciente;
    private String data;
    private String medicacao;

    public AuxiliarAnamnese(String nomepaciente, String data, String medicacao) {
        this.nomepaciente = nomepaciente;
        this.data = data;
        this.medicacao = medicacao;
    }
    
     public AuxiliarAnamnese(){}

    public String getNomepaciente() {
        return nomepaciente;
    }

    public void setNomepaciente(String nomepaciente) {
        this.nomepaciente = nomepaciente;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMedicacao() {
        return medicacao;
    }

    public void setMedicacao(String medicacao) {
        this.medicacao = medicacao;
    }
     
     
     
    
}
