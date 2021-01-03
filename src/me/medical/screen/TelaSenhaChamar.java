/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.medical.screen;

import me.medical.controller.Conexao;
import me.medical.model.SenhaBean;
import me.medical.utils.Msg;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Adriano Zanette
 */
public class TelaSenhaChamar extends javax.swing.JDialog {

    private List<SenhaBean> listaSenha = new ArrayList<>();
    private Connection conexao = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;
    private Timer timer = new Timer();

    public TelaSenhaChamar(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.iniciar();
    }

    private void iniciar() {
        try {
            DefaultTableModel model = (DefaultTableModel) tabela.getModel();
            model.setNumRows(0);
            String sql = "SELECT *, DATE_FORMAT(senha.`data`,'%d/%m/%Y  %H:%i:%s') AS dataFormatada  FROM senha where senha.`status` = 1 and senha.podeChamar=0 ORDER BY senha.prioridade asc";
            pst = Conexao.conector().prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                SenhaBean s = new SenhaBean();
                s.setIdSenha(rs.getInt("idSenha"));
                s.setDataHora(rs.getString("dataFormatada"));
                s.setSenha(rs.getString("senha"));
                s.setTipo(rs.getString("tipo"));
                listaSenha.add(s);
            }
            for (SenhaBean ss : listaSenha) {
                model.addRow(new Object[]{
                    ss.getSenha(),
                    ss.getTipo().equals("NM") ? "NORMAL" : ss.getTipo().equals("PR") ? "PRIORITARIO" : ss.getTipo().equals("DP") ? "DOR NO PEITO" : ss.getTipo().equals("FT") ? "FREBE E TOSSE" : "NÃO CATALOGADO",
                    ss.getDataHora()
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
            Msg.erro(this, "Erro ao Atualizar Lista de Senhas!");
        }
    }

    private void chamarSenha() {
        try {
            int selecionada = tabela.getSelectedRow();
            if (selecionada < 0) {
                Msg.alerta(this, "SELECIONE A SENHA A SER CHAMADA!");
            } else {
                String sql = "UPDATE `senha` SET `podeChamar`='1', local=? WHERE  `idSenha`=?;";
                pst = Conexao.conector().prepareStatement(sql);
                pst.setString(1, "RECEPÇÃO");
                pst.setInt(2, listaSenha.get(selecionada).getIdSenha());
                pst.executeUpdate();
                Msg.informacao(this, "Senha "+listaSenha.get(selecionada).getSenha()+" Enviada Para o Telão!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Msg.erro(this, "Erro ao Chamar Senha!\nErro: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Senha", "Tipo", "Data"
            }
        ));
        jScrollPane1.setViewportView(tabela);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/dbnull.png"))); // NOI18N
        jButton1.setText("CANCELAR");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/dbok.png"))); // NOI18N
        jButton2.setText("CHAMAR SENHA");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 556, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(592, 385));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        chamarSenha();
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

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
            java.util.logging.Logger.getLogger(TelaSenhaChamar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaSenhaChamar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaSenhaChamar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaSenhaChamar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                TelaSenhaChamar dialog = new TelaSenhaChamar(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabela;
    // End of variables declaration//GEN-END:variables
}
