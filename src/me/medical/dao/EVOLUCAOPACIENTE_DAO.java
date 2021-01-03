/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.medical.dao;

import me.medical.connection.Conectar;
import me.medical.model.EVOLUCAOPACIENTE_MODEL;
import me.medical.model.FUNCOES_MODEL;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Adriano Zanette
 */
public class EVOLUCAOPACIENTE_DAO {
    
    
    public List<EVOLUCAOPACIENTE_MODEL> LISTA_COMPLETA() {
        List<EVOLUCAOPACIENTE_MODEL> list = new ArrayList<>();
        Conectar conec = new Conectar();
        String sql = "SELECT * FROM tb_evolucaopaciente;";
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            ps = conec.CONEXAO.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                EVOLUCAOPACIENTE_MODEL model = new EVOLUCAOPACIENTE_MODEL();
                model.setCodigo(rs.getInt("Codigo"));
                model.setNomePaciente(rs.getString("NomePaciente"));
                model.setNumeroLeito(rs.getString("LeitoPaciente"));
                model.setEvolucao(rs.getString("Evolucao"));
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
    
    
        public void GRAVAR(EVOLUCAOPACIENTE_MODEL model){
         
        Conectar conec = new Conectar(); 
        String sql = "INSERT INTO tb_evolucaopaciente(NomePaciente,LeitoPaciente,Evolucao) VALUES (?,?,?)";
        ResultSet rs = null;
        PreparedStatement ps = null;
        try{
            ps = conec.CONEXAO.prepareStatement(sql);
            ps.setString(1, model.getNomePaciente());
            ps.setString(2, model.getNumeroLeito());
            ps.setString(3, model.getEvolucao());
            ps.execute();
            JOptionPane.showMessageDialog(null, "GRAVAÇÃO CONCLUIDA COM SUCESSO");

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
    
      public void ALTERAR(EVOLUCAOPACIENTE_MODEL model){
        Conectar conec = new Conectar(); 
        String sql = "UPDATE tb_evolucaopaciente SET "
                + "NomePaciente=?, "
                + "LeitoPaciente=?, "
                + "Evolucao=? "
                + "WHERE codigo = ? ";
       // JOptionPane.showMessageDialog(null,model.getCodigo());
        ResultSet rs = null;
        PreparedStatement ps = null;
        try{
            ps = conec.CONEXAO.prepareStatement(sql);
            ps.setString(1, model.getNomePaciente());
            ps.setString(2, model.getNumeroLeito());
            ps.setString(3, model.getEvolucao());
            ps.setInt(4, model.getCodigo());
            ps.execute();
            JOptionPane.showMessageDialog(null, "ALTERAÇÃO CONCLUIDA COM SUCESSO");

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
    
    public void EXCLUIR (EVOLUCAOPACIENTE_MODEL model){
         
        Conectar conec = new Conectar(); 
        String sql = "DELETE FROM tb_evolucaopaciente "
                + "WHERE codigo = ? ";
        ResultSet rs = null;
        PreparedStatement ps = null;
        try{
            ps = conec.CONEXAO.prepareStatement(sql);            
            ps.setInt(1, model.getCodigo());
            ps.execute();
            JOptionPane.showMessageDialog(null, "EXCLUSÃO CONCLUIDA COM SUCESSO");

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
    
    public void IMPRIMIR_EVOLUCAO(int ID){         
        
        ResultSet rs = null;
        try {
            Conectar conec = new Conectar();
            
            if (conec.CONEXAO != null) {
                
                String sql = ""
                        + " SELECT tb_evolucaopaciente.*, "
                        + " tb_logomarca.Imagem1,"
                        + " tb_logomarca.Imagem2"
                        + " FROM tb_evolucaopaciente "
                        + " INNER JOIN tb_logomarca"                      
                        + " where tb_evolucaopaciente.codigo = ?";
                PreparedStatement stm;
                stm = conec.CONEXAO.prepareStatement(sql);
                stm.setInt(1, ID);
                rs = stm.executeQuery();
                
                JRResultSetDataSource jrRS = new JRResultSetDataSource(rs);
                String Endereco = "/RELATORIO/evolucao.jasper";
                InputStream Caminho = getClass().getResourceAsStream(Endereco);

                JasperPrint jasperPrint = JasperFillManager.fillReport(Caminho, new HashMap(), jrRS);
//              JasperPrint jasperPrint = JasperFillManager.fillReport("C:/teste02.jasper", new HashMap(), jrRS);

                JasperViewer.viewReport(jasperPrint, false);
                
                
//                stm.close();
            }
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, "Deu erro" + erro);
        }    
    }
  
    
    
    
    
    
    
    
    
}
