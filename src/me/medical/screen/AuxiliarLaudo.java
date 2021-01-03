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
public class AuxiliarLaudo {
    
    private String codigo;
    private String nomepaciente;
    private String nomemedico;
    private String datalaudo;
    private String titulolaudo;
    private String tipoexame;
    private String obslaudo;
    private String conclusaolaudo;

    public AuxiliarLaudo(String codigo, String nomepaciente, String nomemedico, String datalaudo, String titulolaudo, String tipoexame, String obslaudo, String conclusaolaudo) {
        this.codigo = codigo;
        this.nomepaciente = nomepaciente;
        this.nomemedico = nomemedico;
        this.datalaudo = datalaudo;
        this.titulolaudo = titulolaudo;
        this.tipoexame = tipoexame;
        this.obslaudo = obslaudo;
        this.conclusaolaudo = conclusaolaudo;
    }

    
   
    
    AuxiliarLaudo(){}

       public String getNomepaciente() {
        return nomepaciente;
    }

    public void setNomepaciente(String nomepaciente) {
        this.nomepaciente = nomepaciente;
    }

    public String getNomemedico() {
        return nomemedico;
    }

    public void setNomemedico(String nomemedico) {
        this.nomemedico = nomemedico;
    }

    public String getDatalaudo() {
        return datalaudo;
    }

    public void setDatalaudo(String datalaudo) {
        this.datalaudo = datalaudo;
    }

    public String getTitulolaudo() {
        return titulolaudo;
    }

    public void setTitulolaudo(String titulolaudo) {
        this.titulolaudo = titulolaudo;
    }

    public String getTipoexame() {
        return tipoexame;
    }

    public void setTipoexame(String tipoexame) {
        this.tipoexame = tipoexame;
    }

    public String getObslaudo() {
        return obslaudo;
    }

    public void setObslaudo(String obslaudo) {
        this.obslaudo = obslaudo;
    }

    public String getConclusaolaudo() {
        return conclusaolaudo;
    }

    public void setConclusaolaudo(String conclusaolaudo) {
        this.conclusaolaudo = conclusaolaudo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    
    
    
}
