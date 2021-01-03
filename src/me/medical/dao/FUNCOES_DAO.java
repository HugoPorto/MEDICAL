/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.medical.dao;

import me.medical.connection.Conectar;
import me.medical.model.ESPECIALIDADES_MODEL;
import me.medical.model.FUNCOES_MODEL;
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
public class FUNCOES_DAO {
    
    
    public List<FUNCOES_MODEL> LISTA_COMPLETA() {
        List<FUNCOES_MODEL> list = new ArrayList<>();
        Conectar conec = new Conectar();
        String sql = "SELECT * FROM tb_Funcoes;";
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            ps = conec.CONEXAO.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                FUNCOES_MODEL model = new FUNCOES_MODEL();
                model.setCodigo(rs.getInt("Codigo"));
                model.setSigla(rs.getString("Sigla"));
                model.setNomeServidor(rs.getString("NomeServidor"));
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
    
    
    
    
        public void GRAVAR(FUNCOES_MODEL model){
         
        Conectar conec = new Conectar(); 
        String sql = "INSERT INTO tb_Funcoes(Sigla,NomeServidor) VALUES (?,?)";
        ResultSet rs = null;
        PreparedStatement ps = null;
        try{
            ps = conec.CONEXAO.prepareStatement(sql);
            ps.setString(1, model.getSigla());
            ps.setString(2, model.getNomeServidor());
            ps.execute();
            JOptionPane.showMessageDialog(null," Função salvo com sucesso.", "Inclusão", JOptionPane.INFORMATION_MESSAGE,icon);

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
    
      public void ALTERAR(FUNCOES_MODEL model){
        Conectar conec = new Conectar(); 
        String sql = "UPDATE tb_funcoes SET "
                + "Sigla=?, "
                + "NomeServidor=? "
                + "WHERE codigo = ? ";
       // JOptionPane.showMessageDialog(null,model.getCodigo());
        ResultSet rs = null;
        PreparedStatement ps = null;
        try{
            ps = conec.CONEXAO.prepareStatement(sql);
            ps.setString(1, model.getSigla());
            ps.setString(2, model.getNomeServidor());
            ps.setInt(3, model.getCodigo());
            ps.execute();
             JOptionPane.showMessageDialog(null," Função Alterada com sucesso.", "Alteração", JOptionPane.INFORMATION_MESSAGE,icon);

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
    
    public void EXCLUIR (FUNCOES_MODEL model){
         
        Conectar conec = new Conectar(); 
        String sql = "DELETE FROM tb_Funcoes "
                + "WHERE codigo = ? ";
        ResultSet rs = null;
        PreparedStatement ps = null;
        try{
            ps = conec.CONEXAO.prepareStatement(sql);            
            ps.setInt(1, model.getCodigo());
            ps.execute();
            JOptionPane.showMessageDialog(null," Função Excluida com sucesso.", "Exclusão", JOptionPane.INFORMATION_MESSAGE,iconExcluir);

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
