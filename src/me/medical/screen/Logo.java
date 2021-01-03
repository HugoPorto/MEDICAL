/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.medical.screen;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Adriano Zanette
 */
@Entity
@Table(name = "logo", catalog = "consultorio", schema = "")
@NamedQueries({
    @NamedQuery(name = "Logo.findAll", query = "SELECT l FROM Logo l")
    , @NamedQuery(name = "Logo.findByIdLogo", query = "SELECT l FROM Logo l WHERE l.idLogo = :idLogo")})
public class Logo implements Serializable {

    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IdLogo")
    private Integer idLogo;
    @Lob
    @Column(name = "imagem")
    private byte[] imagem;

    public Logo() {
    }

    public Logo(Integer idLogo) {
        this.idLogo = idLogo;
    }

    public Integer getIdLogo() {
        return idLogo;
    }

    public void setIdLogo(Integer idLogo) {
        Integer oldIdLogo = this.idLogo;
        this.idLogo = idLogo;
        changeSupport.firePropertyChange("idLogo", oldIdLogo, idLogo);
    }

    public byte[] getImagem() {
        return imagem;
    }

    public void setImagem(byte[] imagem) {
        byte[] oldImagem = this.imagem;
        this.imagem = imagem;
        changeSupport.firePropertyChange("imagem", oldImagem, imagem);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idLogo != null ? idLogo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Logo)) {
            return false;
        }
        Logo other = (Logo) object;
        if ((this.idLogo == null && other.idLogo != null) || (this.idLogo != null && !this.idLogo.equals(other.idLogo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Telas.Logo[ idLogo=" + idLogo + " ]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
}
