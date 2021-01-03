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
public class ESPECIALIDADES_MODEL {
    
    private int codigo;
    private String especialidades;
    private String sigla;
    private String nomeMedico;
    private String turnoEspecialidade;
    private String statusEspecialidade;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getEspecialidades() {
        return especialidades;
    }

    public void setEspecialidades(String especialidades) {
        this.especialidades = especialidades;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getNomeMedico() {
        return nomeMedico;
    }

    public void setNomeMedico(String nomeMedico) {
        this.nomeMedico = nomeMedico;
    }

    public String getTurnoEspecialidade() {
        return turnoEspecialidade;
    }

    public void setTurnoEspecialidade(String turnoEspecialidade) {
        this.turnoEspecialidade = turnoEspecialidade;
    }

    public String getStatusEspecialidade() {
        return statusEspecialidade;
    }

    public void setStatusEspecialidade(String statusEspecialidade) {
        this.statusEspecialidade = statusEspecialidade;
    }

    
    
}


