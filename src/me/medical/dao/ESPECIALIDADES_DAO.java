/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.medical.dao;

import me.medical.connection.Conectar;
import me.medical.model.LEITOS_MODEL;
import me.medical.model.ESPECIALIDADES_MODEL;
import me.medical.model.SERVICOS_MODEL;
import groovy.ui.text.FindReplaceUtility;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Icon;
import javax.swing.JOptionPane;


    
/**
 *
 * @author Adriano Zanette
 */
public class ESPECIALIDADES_DAO {
    
    
    public List<ESPECIALIDADES_MODEL> LISTA_COMPLETA() {
        List<ESPECIALIDADES_MODEL> list = new ArrayList<>();
        Conectar conec = new Conectar();
        String sql = "SELECT * FROM tb_atendimento_especialidade";
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            ps = conec.CONEXAO.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ESPECIALIDADES_MODEL model = new ESPECIALIDADES_MODEL();
                model.setCodigo(rs.getInt("Codigo"));
                model.setEspecialidades(rs.getString("especialidade"));
                model.setSigla(rs.getString("Sigla"));
                model.setNomeMedico(rs.getString("nomeMedico"));
                model.setTurnoEspecialidade(rs.getString("turnoEspecialidades"));
                model.setStatusEspecialidade(rs.getString("statusAtendimento"));
                list.add(model);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                rs.close();
                ps.close();
                conec.desconectar();
            } catch (Exception ex) {
            }
        }
        return list;
    }
   
    
     // icone do joptionPane
    Icon icon = new javax.swing.ImageIcon(getClass().getResource("/Imagens/ok.png"));
   
    // icone do joptionPane
   
    Icon iconExcluir = new javax.swing.ImageIcon(getClass().getResource("/Imagens/Excluido.png"));
    
    
    
    
        public void GRAVAR(ESPECIALIDADES_MODEL model){
         
        Conectar conec = new Conectar(); 
        String sql = "INSERT INTO tb_atendimento_especialidade(especialidade,Sigla,nomeMedico,turnoEspecialidades,statusAtendimento) VALUES (?,?,?,?,?)";
        ResultSet rs = null;
        PreparedStatement ps = null;
        try{
            ps = conec.CONEXAO.prepareStatement(sql);
            ps.setString(1, model.getEspecialidades());
            ps.setString(2, model.getSigla());
            ps.setString(3, model.getNomeMedico());
            ps.setString(4, model.getTurnoEspecialidade());
            ps.setString(5, model.getStatusEspecialidade());
            ps.execute();
           JOptionPane.showMessageDialog(null," Dados salvo com sucesso.", "Inclusão", JOptionPane.INFORMATION_MESSAGE,icon);
           
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }catch(Exception ex){
            System.out.println(ex.getMessage());
            
        }finally{
            try{
                rs.close();
                ps.close();                
                conec.desconectar();
            }catch(Exception ex){}
            
        }
         
        
    }
   
     public void ALTERAR(ESPECIALIDADES_MODEL model){
        Conectar conec = new Conectar(); 
        String sql = "UPDATE tb_atendimento_especialidade SET "
                + "especialidade=?, "
                + "Sigla=?, "
                + "nomeMedico=?, "
                + "turnoEspecialidades=?, "
                + "statusAtendimento=? "
                + "WHERE codigo = ? ";
       
        ResultSet rs = null;
        PreparedStatement ps = null;
        try{
            ps = conec.CONEXAO.prepareStatement(sql);
            ps.setString(1, model.getEspecialidades());
            ps.setString(2, model.getSigla());
            ps.setString(3, model.getNomeMedico());
            ps.setString(4, model.getTurnoEspecialidade());
            ps.setString(5, model.getStatusEspecialidade());
            ps.setInt(6, model.getCodigo());
            ps.execute();
           JOptionPane.showMessageDialog(null," Dados Alterados com sucesso.", "Alteração", JOptionPane.INFORMATION_MESSAGE,icon);

        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }finally{
            try{
                rs.close();
                ps.close();                
                conec.desconectar();
            }catch(Exception ex){}
        }
        
    }
    
    public void EXCLUIR (ESPECIALIDADES_MODEL model){
         
        Conectar conec = new Conectar(); 
        String sql = "DELETE FROM tb_atendimento_especialidade "
                + "WHERE codigo = ? ";
        ResultSet rs = null;
        PreparedStatement ps = null;
        try{
            ps = conec.CONEXAO.prepareStatement(sql);            
            ps.setInt(1, model.getCodigo());
            ps.execute();
            JOptionPane.showMessageDialog(null," Dados Excluidos com sucesso.", "Exclusão", JOptionPane.INFORMATION_MESSAGE,iconExcluir);

        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }finally{
            try{
                rs.close();
                ps.close();                
                conec.desconectar();
            }catch(Exception ex){}
        }
    }
    
}
