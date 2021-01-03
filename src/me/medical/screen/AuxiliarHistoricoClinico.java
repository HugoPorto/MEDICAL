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
public class AuxiliarHistoricoClinico {
    
    private String data;
    private String nomepaciente;
    private String historicoclinico;
    private String condutamedica;
    private String medicacao;
    private String frequenciarespiratoria;
    private String frequenciacardiaca;
    private String temperatura;
    private String hgt;
    private String saturacao;
    private String pressao;

    public AuxiliarHistoricoClinico(String data, String nomepaciente, String historicoclinico, String condutamedica, String medicacao, String frequenciarespiratoria, String frequenciacardiaca, String temperatura, String hgt, String saturacao, String pressao) {
        this.data = data;
        this.nomepaciente = nomepaciente;
        this.historicoclinico = historicoclinico;
        this.condutamedica = condutamedica;
        this.medicacao = medicacao;
        this.frequenciarespiratoria = frequenciarespiratoria;
        this.frequenciacardiaca = frequenciacardiaca;
        this.temperatura = temperatura;
        this.hgt = hgt;
        this.saturacao = saturacao;
        this.pressao = pressao;
    }

    AuxiliarHistoricoClinico(){}

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getNomepaciente() {
        return nomepaciente;
    }

    public void setNomepaciente(String nomepaciente) {
        this.nomepaciente = nomepaciente;
    }

    public String getHistoricoclinico() {
        return historicoclinico;
    }

    public void setHistoricoclinico(String historicoclinico) {
        this.historicoclinico = historicoclinico;
    }

    public String getCondutamedica() {
        return condutamedica;
    }

    public void setCondutamedica(String condutamedica) {
        this.condutamedica = condutamedica;
    }

    public String getMedicacao() {
        return medicacao;
    }

    public void setMedicacao(String medicacao) {
        this.medicacao = medicacao;
    }

    public String getFrequenciarespiratoria() {
        return frequenciarespiratoria;
    }

    public void setFrequenciarespiratoria(String frequenciarespiratoria) {
        this.frequenciarespiratoria = frequenciarespiratoria;
    }

    public String getFrequenciacardiaca() {
        return frequenciacardiaca;
    }

    public void setFrequenciacardiaca(String frequenciacardiaca) {
        this.frequenciacardiaca = frequenciacardiaca;
    }

    public String getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(String temperatura) {
        this.temperatura = temperatura;
    }

    public String getHgt() {
        return hgt;
    }

    public void setHgt(String hgt) {
        this.hgt = hgt;
    }

    public String getSaturacao() {
        return saturacao;
    }

    public void setSaturacao(String saturacao) {
        this.saturacao = saturacao;
    }

    public String getPressao() {
        return pressao;
    }

    public void setPressao(String pressao) {
        this.pressao = pressao;
    }
    
    
    
    
    
}