/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.medical.dao;

import me.medical.connection.Conectar;
import me.medical.model.LEITOS_MODEL;
import me.medical.model.ESPECIALIDADES_MEDICAS_MODEL;
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
public class ESPECIALIDADES_MEDICAS_DAO {
    
    public List<ESPECIALIDADES_MEDICAS_MODEL> LISTA_COMPLETA() {
        List<ESPECIALIDADES_MEDICAS_MODEL> list = new ArrayList<>();
        Conectar conec = new Conectar();
        String sql = "SELECT * FROM tb_Especialidades_Medicas order by codigo Asc ;";
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            ps = conec.CONEXAO.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ESPECIALIDADES_MEDICAS_MODEL model = new ESPECIALIDADES_MEDICAS_MODEL();
                model.setCodigo(rs.getInt("Codigo"));
                model.setEspecialidades(rs.getString("especialidades"));
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
    
    
    
    
    
        public void GRAVAR(ESPECIALIDADES_MEDICAS_MODEL model){
         
        Conectar conec = new Conectar(); 
        String sql = "INSERT INTO tb_especialidades_medicas(especialidades) VALUES (?)";
        ResultSet rs = null;
        PreparedStatement ps = null;
        try{
            ps = conec.CONEXAO.prepareStatement(sql);
            ps.setString(1, model.getEspecialidades());
            ps.execute();
            JOptionPane.showMessageDialog(null," Especialidade salvo com sucesso.", "Inclusão", JOptionPane.INFORMATION_MESSAGE,icon);

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
    
     public void ALTERAR(ESPECIALIDADES_MEDICAS_MODEL model){
         
        Conectar conec = new Conectar(); 
        String sql = "UPDATE tb_especialidades_medicas SET "
                + "especialidades=? "
                + "WHERE codigo = ? ";
        ResultSet rs = null;
        PreparedStatement ps = null;
        try{
            ps = conec.CONEXAO.prepareStatement(sql);
            ps.setString(1, model.getEspecialidades());
            ps.setInt(2, model.getCodigo());
            ps.execute();
             JOptionPane.showMessageDialog(null," Especialidade Alterados com sucesso.", "Alteração", JOptionPane.INFORMATION_MESSAGE,icon);

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
    
    public void EXCLUIR (ESPECIALIDADES_MEDICAS_MODEL model){
         
        Conectar conec = new Conectar(); 
        String sql = "DELETE FROM tb_especialidades_medicas "
                + "WHERE codigo = ? ";
        ResultSet rs = null;
        PreparedStatement ps = null;
        try{
            ps = conec.CONEXAO.prepareStatement(sql);            
            ps.setInt(1, model.getCodigo());
            ps.execute();
             JOptionPane.showMessageDialog(null," Especialidade Excluida com sucesso.", "Exclusão", JOptionPane.INFORMATION_MESSAGE,iconExcluir);

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
