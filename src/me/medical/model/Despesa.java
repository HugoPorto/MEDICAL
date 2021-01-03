/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.medical.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Adriano Zanette
 */
public class Despesa {
    
    private int idDespesa;
    private List<DespesaPaciente> listaDespesa;

    /**
     * @return the idDespesa
     */
    public int getIdDespesa() {
        return idDespesa;
    }

    /**
     * @param idDespesa the idDespesa to set
     */
    public void setIdDespesa(int idDespesa) {
        this.idDespesa = idDespesa;
    }

    /**
     * @return the listaDespesa
     */
    public List<DespesaPaciente> getListaDespesa() {
        if(listaDespesa==null){
            listaDespesa = new ArrayList<>();
        }
        return listaDespesa;
    }

    /**
     * @param listaDespesa the listaDespesa to set
     */
    public void setListaDespesa(List<DespesaPaciente> listaDespesa) {
        this.listaDespesa = listaDespesa;
    }
    
}
