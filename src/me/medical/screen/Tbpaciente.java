/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.medical.screen;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Adriano Zanette
 */
@Entity
@Table(name = "tbpaciente", catalog = "consultorio", schema = "")
@NamedQueries({
    @NamedQuery(name = "Tbpaciente.findAll", query = "SELECT t FROM Tbpaciente t")
    , @NamedQuery(name = "Tbpaciente.findByIdPaciente", query = "SELECT t FROM Tbpaciente t WHERE t.idPaciente = :idPaciente")
    , @NamedQuery(name = "Tbpaciente.findByDataCadastro", query = "SELECT t FROM Tbpaciente t WHERE t.dataCadastro = :dataCadastro")
    , @NamedQuery(name = "Tbpaciente.findByTipoConvenio", query = "SELECT t FROM Tbpaciente t WHERE t.tipoConvenio = :tipoConvenio")
    , @NamedQuery(name = "Tbpaciente.findByDocumentoPaciente", query = "SELECT t FROM Tbpaciente t WHERE t.documentoPaciente = :documentoPaciente")
    , @NamedQuery(name = "Tbpaciente.findByNomePaciente", query = "SELECT t FROM Tbpaciente t WHERE t.nomePaciente = :nomePaciente")
    , @NamedQuery(name = "Tbpaciente.findByCepPaciente", query = "SELECT t FROM Tbpaciente t WHERE t.cepPaciente = :cepPaciente")
    , @NamedQuery(name = "Tbpaciente.findByEnderecoPaciente", query = "SELECT t FROM Tbpaciente t WHERE t.enderecoPaciente = :enderecoPaciente")
    , @NamedQuery(name = "Tbpaciente.findByBairro", query = "SELECT t FROM Tbpaciente t WHERE t.bairro = :bairro")
    , @NamedQuery(name = "Tbpaciente.findByCidadePaciente", query = "SELECT t FROM Tbpaciente t WHERE t.cidadePaciente = :cidadePaciente")
    , @NamedQuery(name = "Tbpaciente.findByUfPaciente", query = "SELECT t FROM Tbpaciente t WHERE t.ufPaciente = :ufPaciente")
    , @NamedQuery(name = "Tbpaciente.findByDataNascimento", query = "SELECT t FROM Tbpaciente t WHERE t.dataNascimento = :dataNascimento")
    , @NamedQuery(name = "Tbpaciente.findByTelefoneCelular", query = "SELECT t FROM Tbpaciente t WHERE t.telefoneCelular = :telefoneCelular")
    , @NamedQuery(name = "Tbpaciente.findByTipoAtendimento", query = "SELECT t FROM Tbpaciente t WHERE t.tipoAtendimento = :tipoAtendimento")
    , @NamedQuery(name = "Tbpaciente.findByStatus", query = "SELECT t FROM Tbpaciente t WHERE t.status = :status")
    , @NamedQuery(name = "Tbpaciente.findByIdade", query = "SELECT t FROM Tbpaciente t WHERE t.idade = :idade")
    , @NamedQuery(name = "Tbpaciente.findBySexo", query = "SELECT t FROM Tbpaciente t WHERE t.sexo = :sexo")
    , @NamedQuery(name = "Tbpaciente.findByPeso", query = "SELECT t FROM Tbpaciente t WHERE t.peso = :peso")
    , @NamedQuery(name = "Tbpaciente.findByAltura", query = "SELECT t FROM Tbpaciente t WHERE t.altura = :altura")
    , @NamedQuery(name = "Tbpaciente.findByCartaoSus", query = "SELECT t FROM Tbpaciente t WHERE t.cartaoSus = :cartaoSus")})
public class Tbpaciente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IdPaciente")
    private Integer idPaciente;
    @Column(name = "DataCadastro")
    @Temporal(TemporalType.DATE)
    private Date dataCadastro;
    @Column(name = "TipoConvenio")
    private String tipoConvenio;
    @Column(name = "documentoPaciente")
    private String documentoPaciente;
    @Column(name = "NomePaciente")
    private String nomePaciente;
    @Column(name = "CepPaciente")
    private String cepPaciente;
    @Column(name = "EnderecoPaciente")
    private String enderecoPaciente;
    @Column(name = "Bairro")
    private String bairro;
    @Column(name = "CidadePaciente")
    private String cidadePaciente;
    @Column(name = "UfPaciente")
    private String ufPaciente;
    @Column(name = "dataNascimento")
    private String dataNascimento;
    @Column(name = "TelefoneCelular")
    private String telefoneCelular;
    @Column(name = "TipoAtendimento")
    private String tipoAtendimento;
    @Lob
    @Column(name = "foto")
    private byte[] foto;
    @Column(name = "Status")
    private String status;
    @Column(name = "Idade")
    private String idade;
    @Column(name = "Sexo")
    private String sexo;
    @Column(name = "Peso")
    private String peso;
    @Column(name = "Altura")
    private String altura;
    @Column(name = "CartaoSus")
    private String cartaoSus;

    public Tbpaciente() {
    }

    public Tbpaciente(Integer idPaciente) {
        this.idPaciente = idPaciente;
    }

    public Integer getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Integer idPaciente) {
        this.idPaciente = idPaciente;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public String getTipoConvenio() {
        return tipoConvenio;
    }

    public void setTipoConvenio(String tipoConvenio) {
        this.tipoConvenio = tipoConvenio;
    }

    public String getDocumentoPaciente() {
        return documentoPaciente;
    }

    public void setDocumentoPaciente(String documentoPaciente) {
        this.documentoPaciente = documentoPaciente;
    }

    public String getNomePaciente() {
        return nomePaciente;
    }

    public void setNomePaciente(String nomePaciente) {
        this.nomePaciente = nomePaciente;
    }

    public String getCepPaciente() {
        return cepPaciente;
    }

    public void setCepPaciente(String cepPaciente) {
        this.cepPaciente = cepPaciente;
    }

    public String getEnderecoPaciente() {
        return enderecoPaciente;
    }

    public void setEnderecoPaciente(String enderecoPaciente) {
        this.enderecoPaciente = enderecoPaciente;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidadePaciente() {
        return cidadePaciente;
    }

    public void setCidadePaciente(String cidadePaciente) {
        this.cidadePaciente = cidadePaciente;
    }

    public String getUfPaciente() {
        return ufPaciente;
    }

    public void setUfPaciente(String ufPaciente) {
        this.ufPaciente = ufPaciente;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getTelefoneCelular() {
        return telefoneCelular;
    }

    public void setTelefoneCelular(String telefoneCelular) {
        this.telefoneCelular = telefoneCelular;
    }

    public String getTipoAtendimento() {
        return tipoAtendimento;
    }

    public void setTipoAtendimento(String tipoAtendimento) {
        this.tipoAtendimento = tipoAtendimento;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIdade() {
        return idade;
    }

    public void setIdade(String idade) {
        this.idade = idade;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getAltura() {
        return altura;
    }

    public void setAltura(String altura) {
        this.altura = altura;
    }

    public String getCartaoSus() {
        return cartaoSus;
    }

    public void setCartaoSus(String cartaoSus) {
        this.cartaoSus = cartaoSus;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPaciente != null ? idPaciente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tbpaciente)) {
            return false;
        }
        Tbpaciente other = (Tbpaciente) object;
        if ((this.idPaciente == null && other.idPaciente != null) || (this.idPaciente != null && !this.idPaciente.equals(other.idPaciente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Telas.Tbpaciente[ idPaciente=" + idPaciente + " ]";
    }
    
}
