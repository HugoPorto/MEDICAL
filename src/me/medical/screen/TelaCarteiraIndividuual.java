/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.medical.screen;

import me.medical.controller.Conexao;
import me.medical.utils.Utils;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Adriano Zanette
 */
public class TelaCarteiraIndividuual extends javax.swing.JFrame {
    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    /**
     * Creates new form TelaCarteiraIndividuual
     */
    public TelaCarteiraIndividuual() {
        initComponents();
        setIcon();
        conexao = Conexao.conector();
        listarDadosCracha();
        PainelNome.setVisible(false);
    }

    private void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("Receita.png")));
    }
   
     public void listarDadosCracha() {
        Conexao conec = new Conexao();
        DefaultTableModel model = (DefaultTableModel) TabelaDadosCrcaha.getModel();
        model.setNumRows(0);
        String sql = "Select codigo,documentoPaciente,NomePaciente from tb_agenda order by codigo";
        try {
            pst = Conexao.conector().prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("codigo"),
                    rs.getString("NomePaciente"),
                    rs.getString("documentoPaciente"),});
            }
        } catch (SQLException error) {
            JOptionPane.showMessageDialog(null, error);
        } finally {
            try {
                rs.close();
                pst.close();
                conec.desconectar();
            } catch (Exception ex) {
            }
        }

    }
     
     
     
    public void mostrarItens() {
        int seleciona = TabelaDadosCrcaha.getSelectedRow();
        txtRegistro.setText(TabelaDadosCrcaha.getModel().getValueAt(seleciona, 0).toString());
        txtNomePaciente.setText(TabelaDadosCrcaha.getModel().getValueAt(seleciona, 1).toString());
        txtDocumento.setText(TabelaDadosCrcaha.getModel().getValueAt(seleciona, 2).toString());
    }
    
    
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jXTitledPanel1 = new org.jdesktop.swingx.JXTitledPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TabelaDadosCrcaha = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        btnImprimir = new javax.swing.JButton();
        btnSair1 = new javax.swing.JButton();
        PainelNome = new javax.swing.JPanel();
        txtDocumento = new javax.swing.JTextField();
        txtRegistro = new javax.swing.JTextField();
        txtNomePaciente = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Tela Identificação Individual");

        jXTitledPanel1.setTitle(" SELECIONE O NOME DO PACIENTE PARA IMPRESSÃO");
        jXTitledPanel1.setTitleFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jXTitledPanel1.setTitleForeground(new java.awt.Color(51, 51, 51));

        TabelaDadosCrcaha.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Codigo Paciente", "Nome Paciente", "Documento Paciente"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TabelaDadosCrcaha.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabelaDadosCrcahaMouseClicked(evt);
            }
        });
        TabelaDadosCrcaha.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TabelaDadosCrcahaKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(TabelaDadosCrcaha);
        if (TabelaDadosCrcaha.getColumnModel().getColumnCount() > 0) {
            TabelaDadosCrcaha.getColumnModel().getColumn(0).setMinWidth(110);
            TabelaDadosCrcaha.getColumnModel().getColumn(0).setPreferredWidth(110);
            TabelaDadosCrcaha.getColumnModel().getColumn(0).setMaxWidth(110);
            TabelaDadosCrcaha.getColumnModel().getColumn(1).setMinWidth(500);
            TabelaDadosCrcaha.getColumnModel().getColumn(1).setPreferredWidth(500);
            TabelaDadosCrcaha.getColumnModel().getColumn(1).setMaxWidth(500);
        }

        javax.swing.GroupLayout jXTitledPanel1Layout = new javax.swing.GroupLayout(jXTitledPanel1.getContentContainer());
        jXTitledPanel1.getContentContainer().setLayout(jXTitledPanel1Layout);
        jXTitledPanel1Layout.setHorizontalGroup(
            jXTitledPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 817, Short.MAX_VALUE)
        );
        jXTitledPanel1Layout.setVerticalGroup(
            jXTitledPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTitledPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnImprimir.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Print_24x24.png"))); // NOI18N
        btnImprimir.setText("IMPRIMIR");
        btnImprimir.setEnabled(false);
        btnImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirActionPerformed(evt);
            }
        });

        btnSair1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnSair1.setForeground(new java.awt.Color(51, 51, 51));
        btnSair1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagensTelas/Sair.png"))); // NOI18N
        btnSair1.setText("SAIR");
        btnSair1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSair1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnSair1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnImprimir)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnSair1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        txtDocumento.setEnabled(false);

        txtRegistro.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtRegistro.setEnabled(false);

        txtNomePaciente.setEnabled(false);

        javax.swing.GroupLayout PainelNomeLayout = new javax.swing.GroupLayout(PainelNome);
        PainelNome.setLayout(PainelNomeLayout);
        PainelNomeLayout.setHorizontalGroup(
            PainelNomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PainelNomeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtRegistro, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNomePaciente)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        PainelNomeLayout.setVerticalGroup(
            PainelNomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PainelNomeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PainelNomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtRegistro)
                    .addComponent(txtNomePaciente)
                    .addComponent(txtDocumento))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PainelNome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jXTitledPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(PainelNome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(6, 6, 6)
                .addComponent(jXTitledPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        setSize(new java.awt.Dimension(857, 420));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed
         
        final Aguarde carregando = new Aguarde();
        carregando.setVisible(true);
        Thread t = new Thread() {
            public void run() {

                try {
                    ImpressaoCarteiraIndividual imprime = new ImpressaoCarteiraIndividual();
                    List listade_dados = GetDados();
                    imprime.Imprime_Relatorio(listade_dados);
                    carregando.dispose();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }
                
            }
        };
        t.start();

    }//GEN-LAST:event_btnImprimirActionPerformed

    private void TabelaDadosCrcahaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabelaDadosCrcahaMouseClicked
       btnImprimir.setEnabled(true);
       mostrarItens();
    }//GEN-LAST:event_TabelaDadosCrcahaMouseClicked

    private void TabelaDadosCrcahaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TabelaDadosCrcahaKeyPressed
       mostrarItens();
    }//GEN-LAST:event_TabelaDadosCrcahaKeyPressed

    private void btnSair1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSair1ActionPerformed

        //Pergunta se o usuário deseja realmente sair do sistema
        int sair = JOptionPane.showConfirmDialog(null, "DESEJA SAIR DA TELA CRACHÁ ?", "ATENÇÃO", JOptionPane.YES_NO_OPTION);
        if (sair == JOptionPane.YES_OPTION) {
            dispose();
        }
    }//GEN-LAST:event_btnSair1ActionPerformed

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
            java.util.logging.Logger.getLogger(TelaCarteiraIndividuual.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaCarteiraIndividuual.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaCarteiraIndividuual.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaCarteiraIndividuual.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaCarteiraIndividuual().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PainelNome;
    private javax.swing.JTable TabelaDadosCrcaha;
    private javax.swing.JButton btnImprimir;
    private javax.swing.JButton btnSair1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private org.jdesktop.swingx.JXTitledPanel jXTitledPanel1;
    private javax.swing.JTextField txtDocumento;
    private javax.swing.JTextField txtNomePaciente;
    private javax.swing.JTextField txtRegistro;
    // End of variables declaration//GEN-END:variables

    
  public List GetDados() {

        List lista = new ArrayList();

        //for (int i = 0; i < 10; i++) {
        AuxiliarCarteiraIndividual print = new AuxiliarCarteiraIndividual();
        print.setRegistro(txtRegistro.getText());
        print.setNomepaciente(txtNomePaciente.getText());
        print.setDocumento(txtDocumento.getText());
        lista.add(print);
        return lista;
    }






}
