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
public class AuxiliarCarteiraIndividual {
    
    private String nomepaciente;
    private String documento;
    private String registro;

    public AuxiliarCarteiraIndividual(String nomepaciente, String documento, String registro) {
        this.nomepaciente = nomepaciente;
        this.documento = documento;
        this.registro = registro;
    }
    
    public AuxiliarCarteiraIndividual(){}

    public String getNomepaciente() {
        return nomepaciente;
    }

    public void setNomepaciente(String nomepaciente) {
        this.nomepaciente = nomepaciente;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getRegistro() {
        return registro;
    }

    public void setRegistro(String registro) {
        this.registro = registro;
    }
    
    
    
    
}
