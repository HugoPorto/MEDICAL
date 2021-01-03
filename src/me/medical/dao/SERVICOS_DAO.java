/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.medical.dao;

import me.medical.connection.Conectar;
import me.medical.model.SERVICOS_MODEL;
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
public class SERVICOS_DAO {
    
    
    public List<SERVICOS_MODEL> LISTA_COMPLETA() {
        List<SERVICOS_MODEL> list = new ArrayList<>();
        Conectar conec = new Conectar();
        String sql = "SELECT * FROM tb_atendimento_servicos;";
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            ps = conec.CONEXAO.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                SERVICOS_MODEL model = new SERVICOS_MODEL();
                model.setCodigo(rs.getInt("Codigo"));
                model.setTipoServico(rs.getString("tipoServico"));
                model.setTurnoServico(rs.getString("turnoServico"));
                model.setStatusServico(rs.getString("statusServico"));
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
    
    
        public void GRAVAR(SERVICOS_MODEL model){
         
        Conectar conec = new Conectar(); 
        String sql = "INSERT INTO tb_atendimento_servicos(tipoServico,turnoServico,statusServico) VALUES (?,?,?)";
        ResultSet rs = null;
        PreparedStatement ps = null;
        try{
            ps = conec.CONEXAO.prepareStatement(sql);
            ps.setString(1, model.getTipoServico());
            ps.setString(2, model.getTurnoServico());
            ps.setString(3, model.getStatusServico());
            ps.execute();
            JOptionPane.showMessageDialog(null," Serviços salvo com sucesso.", "Inclusão", JOptionPane.INFORMATION_MESSAGE,icon);

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
   
     public void ALTERAR(SERVICOS_MODEL model){
         
        Conectar conec = new Conectar(); 
        String sql = "UPDATE tb_atendimento_servicos SET "
                + "tipoServico=?, "
                + "turnoServico=?, "
                + "statusServico=? "
                + "WHERE codigo = ? ";
       
        ResultSet rs = null;
        PreparedStatement ps = null;
        try{
            ps = conec.CONEXAO.prepareStatement(sql);
            ps.setString(1, model.getTipoServico());
            ps.setString(2, model.getTurnoServico());
            ps.setString(3, model.getStatusServico());
            ps.setInt(4, model.getCodigo());
            ps.execute();
             JOptionPane.showMessageDialog(null," Serviços Alterados com sucesso.", "Alteração", JOptionPane.INFORMATION_MESSAGE,icon);

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
    
    public void EXCLUIR (SERVICOS_MODEL model){
         
        Conectar conec = new Conectar(); 
        String sql = "DELETE FROM tb_atendimento_servicos "
                + "WHERE codigo = ? ";
        ResultSet rs = null;
        PreparedStatement ps = null;
        try{
            ps = conec.CONEXAO.prepareStatement(sql);            
            ps.setInt(1, model.getCodigo());
            ps.execute();
            JOptionPane.showMessageDialog(null," Serviços Excluidos com sucesso.", "Exclusão", JOptionPane.INFORMATION_MESSAGE,iconExcluir);

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
