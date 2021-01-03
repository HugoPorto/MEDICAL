/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.medical.screen;

import me.medical.controller.Conexao;
import me.medical.utils.Msg;
import me.medical.utils.Utils;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author Adriano Zanette
 */
public class TelaGeralLeitos extends javax.swing.JFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    /**
     * Creates new form TelaEntradaSaida
     */
    public TelaGeralLeitos() {
        initComponents();
        listarCadastro();
        listarCadastroEnfermaria();
        listarCadastroApartamentos();
        setIcon();
        pintarColumnaTabla();
        conexao = Conexao.conector();
    }

    private void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("Receita.png")));
    }

    void pintarColumnaTabla() {
        ColorearFilas_1 color = new ColorearFilas_1(3);
        tabelaLeitos.getColumnModel().getColumn(3).setCellRenderer(color);
        tabelaLeitosEnfermarias.getColumnModel().getColumn(3).setCellRenderer(color);
        tabelaLeitosApatamentos.getColumnModel().getColumn(3).setCellRenderer(color);

    }

//    public void pesquisarLeitos() {
//        DefaultTableModel model = (DefaultTableModel) tabelaLeitos.getModel();
//        model.setNumRows(0);
//        //conexão com o banco de dados
//        String sql = "Select *from tb_leitosuti where leito like ?";
//
//        try {
//            pst = Conexao.conector().prepareStatement(sql);
//            pst.setString(1, txtPesquisar.getText() + "%");
//            rs = pst.executeQuery();
//            while (rs.next()) {
//                model.addRow(new Object[]{
//                    rs.getInt("codigo"),
//                    rs.getString("Prontuario"),
//                    Utils.convertData(rs.getDate("DataCadastro")),
//                    rs.getString("leito"),});
//            }
//        } catch (SQLException error) {
//            JOptionPane.showMessageDialog(null, error);
//        }
//    }
    protected void listarCadastro() {
        Conexao conec = new Conexao();
        DefaultTableModel model = (DefaultTableModel) tabelaLeitos.getModel();
        model.setNumRows(0);
        String sql = "Select codigo,DataCadastro,leito,status from tb_leitosuti order by codigo";
        try {
            pst = Conexao.conector().prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("codigo"),
                    Utils.convertData(rs.getDate("DataCadastro")),
                    rs.getString("leito"),
                    rs.getString("status"),});
            }
        } catch (SQLException error) {
            JOptionPane.showMessageDialog(null, error);
        } finally {
            try {
                rs.close();
                pst.close();
                conec.desconectar();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }

    TelaStatusLeitosUti tela1 = new TelaStatusLeitosUti(this, true);

    private void alterarLeitos() {
        //chama a tela ja com os dados
        //tela = new TelaInternacao(this);
        // chama a tela ja com os dados
        int index = tabelaLeitos.getSelectedRow();
        TableModel model = tabelaLeitos.getModel();
        String codigo = model.getValueAt(index, 0).toString();
        String leito = model.getValueAt(index, 2).toString();
        // String Status = model.getValueAt(index, 3).toString();  

        tela1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        tela1.txtcodigo.setText(codigo);
        tela1.jCLeitos.setSelectedItem(leito);
        tela1.jCStatusleito.setEnabled(true);
        tela1.btnSalvar.setEnabled(true);
        tela1.setVisible(true);
        tela1.pack();
    }

//**************************************ENFERMARIA**********************************************************
//    
//    
//
//    public void pesquisarLeitos() {
//        DefaultTableModel model = (DefaultTableModel) tabelaLeitos.getModel();
//        model.setNumRows(0);
//        //conexão com o banco de dados
//        String sql = "Select *from tb_leitosenfermaria where leito like ?";
//
//        try {
//            pst = Conexao.conector().prepareStatement(sql);
//            pst.setString(1, txtPesquisar.getText() + "%");
//            rs = pst.executeQuery();
//            while (rs.next()) {
//                model.addRow(new Object[]{
//                    rs.getInt("codigo"),
//                    rs.getString("Prontuario"),
//                    Utils.convertData(rs.getDate("DataCadastro")),
//                    rs.getString("leito"),});
//            }
//        } catch (SQLException error) {
//            JOptionPane.showMessageDialog(null, error);
//        }
//    }
    protected void listarCadastroEnfermaria() {
        Conexao conec = new Conexao();
        DefaultTableModel model = (DefaultTableModel) tabelaLeitosEnfermarias.getModel();
        model.setNumRows(0);
        String sql = "Select codigo,DataCadastro,leito,status from tb_leitosenfermaria order by codigo";
        try {
            pst = Conexao.conector().prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("codigo"),
                    Utils.convertData(rs.getDate("DataCadastro")),
                    rs.getString("leito"),
                    rs.getString("status"),});
            }
        } catch (SQLException error) {
            JOptionPane.showMessageDialog(null, error);
        } finally {
            try {
                rs.close();
                pst.close();
                conec.desconectar();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }

    TelaStatusLeitosEnfermagem tela = new TelaStatusLeitosEnfermagem(this, true);

    private void alterar() {
        //chama a tela ja com os dados
        //tela = new TelaInternacao(this);
        // chama a tela ja com os dados
        int index = tabelaLeitosEnfermarias.getSelectedRow();
        TableModel model = tabelaLeitosEnfermarias.getModel();
        String codigo = model.getValueAt(index, 0).toString();
        String leito = model.getValueAt(index, 2).toString();
        // String Status = model.getValueAt(index, 3).toString();  

        tela.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        tela.txtcodigo.setText(codigo);
        tela.jCLeitos.setSelectedItem(leito);
        tela.jCStatusleito.setEnabled(true);
        tela.btnSalvar.setEnabled(true);
        tela.setVisible(true);
        tela.pack();
    }

// ***************************************APARTAMENTOS*******************************************************   
    protected void listarCadastroApartamentos() {
        Conexao conec = new Conexao();
        DefaultTableModel model = (DefaultTableModel) tabelaLeitosApatamentos.getModel();
        model.setNumRows(0);
        String sql = "Select codigo,DataCadastro,leito,status from tb_leitosapartamento order by codigo";
        try {
            pst = Conexao.conector().prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("codigo"),
                    Utils.convertData(rs.getDate("DataCadastro")),
                    rs.getString("leito"),
                    rs.getString("status"),});
            }
        } catch (SQLException error) {
            JOptionPane.showMessageDialog(null, error);
        } finally {
            try {
                rs.close();
                pst.close();
                conec.desconectar();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }

    TelaStatusLeitosApartamentos tela2 = new TelaStatusLeitosApartamentos(this, true);

    private void alterarApartamentos() {
        //chama a tela ja com os dados
        //tela = new TelaInternacao(this);
        // chama a tela ja com os dados
        int index = tabelaLeitosApatamentos.getSelectedRow();
        TableModel model = tabelaLeitosApatamentos.getModel();
        String codigo = model.getValueAt(index, 0).toString();
        String leito = model.getValueAt(index, 2).toString();
        // String Status = model.getValueAt(index, 3).toString();  

        tela2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        tela2.txtcodigo.setText(codigo);
        tela2.jCLeitos.setSelectedItem(leito);
        tela2.jCStatusleito.setEnabled(true);
        tela2.btnSalvar.setEnabled(true);
        tela2.setVisible(true);
        tela2.pack();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jXTitledPanel1 = new org.jdesktop.swingx.JXTitledPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaLeitos = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelaLeitosEnfermarias = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabelaLeitosApatamentos = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Leitos");
        setExtendedState(MAXIMIZED_BOTH);

        jXTitledPanel1.setTitle("<<ATUALIZAR TABELA AQUI>>");
        jXTitledPanel1.setTitleFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jXTitledPanel1.setTitleForeground(new java.awt.Color(255, 0, 0));
        jXTitledPanel1.setMinimumSize(new java.awt.Dimension(297, 50));
        jXTitledPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jXTitledPanel1MouseClicked(evt);
            }
        });

        jTabbedPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane1MouseClicked(evt);
            }
        });

        tabelaLeitos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Codigo", "Data Cadastro", "Leito", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaLeitos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaLeitosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelaLeitos);
        if (tabelaLeitos.getColumnModel().getColumnCount() > 0) {
            tabelaLeitos.getColumnModel().getColumn(0).setMinWidth(0);
            tabelaLeitos.getColumnModel().getColumn(0).setPreferredWidth(0);
            tabelaLeitos.getColumnModel().getColumn(0).setMaxWidth(0);
            tabelaLeitos.getColumnModel().getColumn(1).setMinWidth(120);
            tabelaLeitos.getColumnModel().getColumn(1).setPreferredWidth(120);
            tabelaLeitos.getColumnModel().getColumn(1).setMaxWidth(120);
            tabelaLeitos.getColumnModel().getColumn(2).setMinWidth(70);
            tabelaLeitos.getColumnModel().getColumn(2).setPreferredWidth(70);
            tabelaLeitos.getColumnModel().getColumn(2).setMaxWidth(70);
        }

        jTabbedPane1.addTab("LEITOS UTI", jScrollPane1);

        tabelaLeitosEnfermarias.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Codigo", "Data Cadastro", "Leito", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaLeitosEnfermarias.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaLeitosEnfermariasMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tabelaLeitosEnfermarias);
        if (tabelaLeitosEnfermarias.getColumnModel().getColumnCount() > 0) {
            tabelaLeitosEnfermarias.getColumnModel().getColumn(0).setMinWidth(0);
            tabelaLeitosEnfermarias.getColumnModel().getColumn(0).setPreferredWidth(0);
            tabelaLeitosEnfermarias.getColumnModel().getColumn(0).setMaxWidth(0);
            tabelaLeitosEnfermarias.getColumnModel().getColumn(1).setMinWidth(120);
            tabelaLeitosEnfermarias.getColumnModel().getColumn(1).setPreferredWidth(120);
            tabelaLeitosEnfermarias.getColumnModel().getColumn(1).setMaxWidth(120);
            tabelaLeitosEnfermarias.getColumnModel().getColumn(2).setMinWidth(70);
            tabelaLeitosEnfermarias.getColumnModel().getColumn(2).setPreferredWidth(70);
            tabelaLeitosEnfermarias.getColumnModel().getColumn(2).setMaxWidth(70);
        }

        jTabbedPane1.addTab("LEITOS ENFERMARIA", jScrollPane2);

        tabelaLeitosApatamentos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Codigo", "Data Cadastro", "Leito", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaLeitosApatamentos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaLeitosApatamentosMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tabelaLeitosApatamentos);
        if (tabelaLeitosApatamentos.getColumnModel().getColumnCount() > 0) {
            tabelaLeitosApatamentos.getColumnModel().getColumn(0).setMinWidth(0);
            tabelaLeitosApatamentos.getColumnModel().getColumn(0).setPreferredWidth(0);
            tabelaLeitosApatamentos.getColumnModel().getColumn(0).setMaxWidth(0);
            tabelaLeitosApatamentos.getColumnModel().getColumn(1).setMinWidth(120);
            tabelaLeitosApatamentos.getColumnModel().getColumn(1).setPreferredWidth(120);
            tabelaLeitosApatamentos.getColumnModel().getColumn(1).setMaxWidth(120);
            tabelaLeitosApatamentos.getColumnModel().getColumn(2).setMinWidth(70);
            tabelaLeitosApatamentos.getColumnModel().getColumn(2).setPreferredWidth(70);
            tabelaLeitosApatamentos.getColumnModel().getColumn(2).setMaxWidth(70);
        }

        jTabbedPane1.addTab("LEITOS APARTAMENTOS", jScrollPane3);

        javax.swing.GroupLayout jXTitledPanel1Layout = new javax.swing.GroupLayout(jXTitledPanel1.getContentContainer());
        jXTitledPanel1.getContentContainer().setLayout(jXTitledPanel1Layout);
        jXTitledPanel1Layout.setHorizontalGroup(
            jXTitledPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 939, Short.MAX_VALUE)
        );
        jXTitledPanel1Layout.setVerticalGroup(
            jXTitledPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 503, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jXTitledPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jXTitledPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        setSize(new java.awt.Dimension(979, 600));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void excluir(int codigo) {
        Conexao conec = new Conexao();
        String sql = "DELETE FROM `consultorio`.`tb_internacao` WHERE  `codigo`=" + codigo + ";";

        try {
            pst = Conexao.conector().prepareStatement(sql);
            pst.executeUpdate();
            rs.close();
            pst.close();
            conec.desconectar();
//            listarCadastro();
            Msg.informacao(this, "Excluido com Sucesso!");
        } catch (Exception error) {
            JOptionPane.showMessageDialog(null, error);
        }
    }

    private void jTabbedPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane1MouseClicked
       
    }//GEN-LAST:event_jTabbedPane1MouseClicked

    private void tabelaLeitosApatamentosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaLeitosApatamentosMouseClicked

        if (evt.getClickCount() == 2) {
            // chama a tela ja com os dados
            alterarApartamentos();
        }
    }//GEN-LAST:event_tabelaLeitosApatamentosMouseClicked

    private void tabelaLeitosEnfermariasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaLeitosEnfermariasMouseClicked

        if (evt.getClickCount() == 2) {
            // chama a tela ja com os dados
            alterar();
        }
    }//GEN-LAST:event_tabelaLeitosEnfermariasMouseClicked

    private void tabelaLeitosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaLeitosMouseClicked

        if (evt.getClickCount() == 2) {
            // chama a tela ja com os dados
            alterarLeitos();
        }
    }//GEN-LAST:event_tabelaLeitosMouseClicked

    private void jXTitledPanel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jXTitledPanel1MouseClicked
        listarCadastro();
        listarCadastroApartamentos();
        listarCadastroEnfermaria();
    }//GEN-LAST:event_jXTitledPanel1MouseClicked


    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TelaGeralLeitos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaGeralLeitos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaGeralLeitos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaGeralLeitos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaGeralLeitos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private org.jdesktop.swingx.JXTitledPanel jXTitledPanel1;
    private javax.swing.JTable tabelaLeitos;
    private javax.swing.JTable tabelaLeitosApatamentos;
    private javax.swing.JTable tabelaLeitosEnfermarias;
    // End of variables declaration//GEN-END:variables
}
