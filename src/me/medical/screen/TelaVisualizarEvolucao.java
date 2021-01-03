package me.medical.screen;

import me.medical.controller.Conexao;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 
 */
public class TelaVisualizarEvolucao extends javax.swing.JFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

  

    /**
     * Creates new form history
     */
    public TelaVisualizarEvolucao() {
        initComponents();
        setIcon();
        //panel2.setBackground(new Color(0,153,204,200));
        conexao = Conexao.conector();
        listarDadosEvolucao();

    }

    private void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("Receita.png")));
    }

    
   public void listarDadosEvolucao() {
        DefaultTableModel model = (DefaultTableModel) TabelaVisualizarEvolucao.getModel();
        model.setNumRows(0);
        String sql  = "Select NomePaciente,DataCadastro,Responsavel from tb_evolucao group by NomePaciente order by NomePaciente";
        try {
            pst = Conexao.conector().prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("NomePaciente"),
                    me.medical.utils.Utils.convertData(rs.getDate("DataCadastro")),
                    rs.getString("Responsavel"),
                });
            }
        } catch (SQLException error) {
            JOptionPane.showMessageDialog(null, error);
        }

    } 
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jXTitledPanel1 = new org.jdesktop.swingx.JXTitledPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        TabelaVisualizarEvolucao = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Pacientes Evoluidos");
        setExtendedState(MAXIMIZED_BOTH);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TabelaVisualizarEvolucao.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Nome Paciente", "Data Evolução"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TabelaVisualizarEvolucao.setRowHeight(28);
        TabelaVisualizarEvolucao.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabelaVisualizarEvolucaoMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(TabelaVisualizarEvolucao);
        if (TabelaVisualizarEvolucao.getColumnModel().getColumnCount() > 0) {
            TabelaVisualizarEvolucao.getColumnModel().getColumn(1).setMinWidth(100);
            TabelaVisualizarEvolucao.getColumnModel().getColumn(1).setPreferredWidth(100);
            TabelaVisualizarEvolucao.getColumnModel().getColumn(1).setMaxWidth(100);
        }

        javax.swing.GroupLayout jXTitledPanel1Layout = new javax.swing.GroupLayout(jXTitledPanel1.getContentContainer());
        jXTitledPanel1.getContentContainer().setLayout(jXTitledPanel1Layout);
        jXTitledPanel1Layout.setHorizontalGroup(
            jXTitledPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1344, Short.MAX_VALUE)
        );
        jXTitledPanel1Layout.setVerticalGroup(
            jXTitledPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 644, Short.MAX_VALUE)
        );

        getContentPane().add(jXTitledPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 11, -1, 670));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    
    //chama a tela qque possui os dados e serao setados abaixo
    TelaDadosEvolucao tela = new TelaDadosEvolucao();

    private void TabelaVisualizarEvolucaoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabelaVisualizarEvolucaoMouseClicked

        // chama a tela ja com os dados
        int index = TabelaVisualizarEvolucao.getSelectedRow();
        TableModel model = TabelaVisualizarEvolucao.getModel();
        // String codigo = model.getValueAt(index, 0).toString();
        String NomePaciente = model.getValueAt(index, 0).toString();
        tela.listarEvolucao(NomePaciente);//aqui chamo o metodo atualiza Anamnese
        tela.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        tela.labelNome.setText(NomePaciente);
//        tela.labelData.setText(Data);
        tela.setVisible(true);
        tela.pack();

    }//GEN-LAST:event_TabelaVisualizarEvolucaoMouseClicked

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
            java.util.logging.Logger.getLogger(TelaVisualizarEvolucao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaVisualizarEvolucao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaVisualizarEvolucao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaVisualizarEvolucao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
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
                new TelaVisualizarEvolucao().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TabelaVisualizarEvolucao;
    private javax.swing.JScrollPane jScrollPane2;
    private org.jdesktop.swingx.JXTitledPanel jXTitledPanel1;
    // End of variables declaration//GEN-END:variables


}