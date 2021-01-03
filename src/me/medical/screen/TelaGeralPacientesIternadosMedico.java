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
public class TelaGeralPacientesIternadosMedico extends javax.swing.JFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    /**
     * Creates new form TelaEntradaSaida
     */
    public TelaGeralPacientesIternadosMedico() {
        initComponents();
        listarDados();
        pintarColumnaTabla();
        setIcon();
        conexao = Conexao.conector();
    }

    private void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("Receita.png")));
    }
    
    void pintarColumnaTabla() {
        ColorearFilasTroca color = new ColorearFilasTroca(7);
        tabelaPacientes.getColumnModel().getColumn(7).setCellRenderer(color);
    }

    public void pesquisarPacienteNome() {
        DefaultTableModel model = (DefaultTableModel) tabelaPacientes.getModel();
        model.setNumRows(0);
        //conexão com o banco de dados
        String sql = "Select *from tb_internacao where NomePaciente like ?";

        try {
            pst = Conexao.conector().prepareStatement(sql);
            pst.setString(1, txtPesquisar.getText() + "%");
            rs = pst.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("codigo"),
                    rs.getString("Prontuario"),
                    Utils.convertData(rs.getDate("DataInternacao")),
                    rs.getString("NomePaciente"),
                    rs.getString("TipoAcomodacao"),
                    rs.getString("Numero"),
                    rs.getString("Leito"),
                    rs.getString("Status"),
                    rs.getString("DataAlta"),});
            }
        } catch (SQLException error) {
            JOptionPane.showMessageDialog(null, error);
        }
    }
    
    

    protected void listarDados() {
         Conexao conec = new Conexao();
        DefaultTableModel model = (DefaultTableModel) tabelaPacientes.getModel();
        model.setNumRows(0);
        String sql = "Select codigo,Prontuario,DataInternacao,NomePaciente,TipoAcomodacao,Numero,Leito,Status,DataAlta from tb_internacao order by codigo";
        try {
            pst = Conexao.conector().prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("codigo"),
                    rs.getString("Prontuario"),
                    Utils.convertData(rs.getDate("DataInternacao")),
                    rs.getString("NomePaciente"),
                    rs.getString("TipoAcomodacao"),
                    rs.getString("Numero"),
                    rs.getString("Leito"),
                    rs.getString("Status"),
                    rs.getString("DataAlta"),});
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
        

    }}
    


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtPesquisar = new javax.swing.JTextField();
        btnLegenda = new java.awt.Button();
        jLabel2 = new javax.swing.JLabel();
        jXTitledPanel1 = new org.jdesktop.swingx.JXTitledPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaPacientes = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setExtendedState(MAXIMIZED_BOTH);

        jLabel1.setText("Pesquisar:");

        txtPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPesquisarKeyReleased(evt);
            }
        });

        btnLegenda.setBackground(new java.awt.Color(51, 153, 255));
        btnLegenda.setForeground(new java.awt.Color(255, 255, 255));
        btnLegenda.setLabel("Atualizar TB");
        btnLegenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLegendaActionPerformed(evt);
            }
        });

        jLabel2.setText("Paciente:");

        jXTitledPanel1.setTitle("CLIQUE SOBRE O PACIENTE DESEJADO ");
        jXTitledPanel1.setTitleFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jXTitledPanel1.setTitleForeground(new java.awt.Color(255, 0, 0));

        tabelaPacientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Codigo", "Prontuário", "Data Internação", "Nome do Paciente", "Tipo de Acomodação", "Número", "Leito", "Status", "Data Alta "
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaPacientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaPacientesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelaPacientes);
        if (tabelaPacientes.getColumnModel().getColumnCount() > 0) {
            tabelaPacientes.getColumnModel().getColumn(0).setMinWidth(70);
            tabelaPacientes.getColumnModel().getColumn(0).setPreferredWidth(70);
            tabelaPacientes.getColumnModel().getColumn(0).setMaxWidth(70);
            tabelaPacientes.getColumnModel().getColumn(1).setMinWidth(70);
            tabelaPacientes.getColumnModel().getColumn(1).setPreferredWidth(70);
            tabelaPacientes.getColumnModel().getColumn(1).setMaxWidth(70);
            tabelaPacientes.getColumnModel().getColumn(2).setMinWidth(100);
            tabelaPacientes.getColumnModel().getColumn(2).setPreferredWidth(100);
            tabelaPacientes.getColumnModel().getColumn(2).setMaxWidth(100);
            tabelaPacientes.getColumnModel().getColumn(4).setMinWidth(150);
            tabelaPacientes.getColumnModel().getColumn(4).setPreferredWidth(150);
            tabelaPacientes.getColumnModel().getColumn(4).setMaxWidth(150);
            tabelaPacientes.getColumnModel().getColumn(5).setMinWidth(70);
            tabelaPacientes.getColumnModel().getColumn(5).setPreferredWidth(70);
            tabelaPacientes.getColumnModel().getColumn(5).setMaxWidth(70);
            tabelaPacientes.getColumnModel().getColumn(6).setMinWidth(70);
            tabelaPacientes.getColumnModel().getColumn(6).setPreferredWidth(70);
            tabelaPacientes.getColumnModel().getColumn(6).setMaxWidth(70);
            tabelaPacientes.getColumnModel().getColumn(7).setMinWidth(110);
            tabelaPacientes.getColumnModel().getColumn(7).setPreferredWidth(110);
            tabelaPacientes.getColumnModel().getColumn(7).setMaxWidth(110);
            tabelaPacientes.getColumnModel().getColumn(8).setMinWidth(110);
            tabelaPacientes.getColumnModel().getColumn(8).setPreferredWidth(110);
            tabelaPacientes.getColumnModel().getColumn(8).setMaxWidth(110);
        }

        javax.swing.GroupLayout jXTitledPanel1Layout = new javax.swing.GroupLayout(jXTitledPanel1.getContentContainer());
        jXTitledPanel1.getContentContainer().setLayout(jXTitledPanel1Layout);
        jXTitledPanel1Layout.setHorizontalGroup(
            jXTitledPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 961, Short.MAX_VALUE)
        );
        jXTitledPanel1Layout.setVerticalGroup(
            jXTitledPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 467, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jXTitledPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPesquisar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLegenda, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(6, 6, 6)
                        .addComponent(jLabel2))
                    .addComponent(txtPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLegenda, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addComponent(jXTitledPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(1001, 588));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents


    private void txtPesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisarKeyReleased
       pesquisarPacienteNome();
       
    }//GEN-LAST:event_txtPesquisarKeyReleased

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

    private void btnLegendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLegendaActionPerformed
         listarDados();
    }//GEN-LAST:event_btnLegendaActionPerformed

    TelaAltaHospitalar tela = new TelaAltaHospitalar(this, true);
                private void alterar() {
                 //chama a tela ja com os dados
                 //tela = new TelaInternacao(this);
                // chama a tela ja com os dados
                int index = tabelaPacientes.getSelectedRow();
                TableModel model = tabelaPacientes.getModel();
                String Prontuario = model.getValueAt(index, 1).toString();
//                String Status = model.getValueAt(index, 1).toString();
                tela.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                tela.txtProntuario.setText(Prontuario);
//                tela.jCStatus.setSelectedItem(Status);
               // txtDataAltaHospitalar.getEditor().setText(DataAlta);
                
                tela.setVisible(true);
                tela.pack();
                }
    
    private void tabelaPacientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaPacientesMouseClicked
       
        if (evt.getClickCount() == 1) {
            // chama a tela ja com os dados
            alterar();
        }         
    }//GEN-LAST:event_tabelaPacientesMouseClicked
      
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
            java.util.logging.Logger.getLogger(TelaGeralPacientesIternadosMedico.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaGeralPacientesIternadosMedico.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaGeralPacientesIternadosMedico.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaGeralPacientesIternadosMedico.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaGeralPacientesIternadosMedico().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Button btnLegenda;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private org.jdesktop.swingx.JXTitledPanel jXTitledPanel1;
    private javax.swing.JTable tabelaPacientes;
    private javax.swing.JTextField txtPesquisar;
    // End of variables declaration//GEN-END:variables
}
