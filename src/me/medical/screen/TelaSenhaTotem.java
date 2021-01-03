/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.medical.screen;

import me.medical.controller.Conexao;
import me.medical.printer.ImprimirSenha;
import me.medical.model.SenhaBean;
import me.medical.utils.Msg;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Adriano Zanette
 */
public class TelaSenhaTotem extends javax.swing.JFrame {

    private Connection conexao = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;

    public TelaSenhaTotem() {
        initComponents();
        conexao = Conexao.conector();
        setIcon();
        PreparedStatement ST = null;
    }
    
     private void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("Receita.png")));
    }

    private void gravaSenha(String tipo, int prioridade) {
        try {

            SenhaBean senha = new SenhaBean();
            senha.setStatus(1);
            senha.setTipo(tipo);
            senha.setPrioridade(prioridade);
            //pega o id da ultima senha gerada
            String sql = "SELECT IFNULL(Max(a.idSenha),'1') AS senha  FROM senha a";
            pst = Conexao.conector().prepareStatement(sql);
            rs = pst.executeQuery();
            rs.next();
            String dado = String.format("%03d", rs.getInt("senha"));
            senha.setSenha(tipo + dado.substring(dado.length() - 3));
            //chama o metodo salvar
            salvaSenha(senha);
            
            //imprimi a senha
            new ImprimirSenha().imprimirSenha(senha);
            TelaAguardeImpressao t = new TelaAguardeImpressao(this);
            t.setVisible(true);
            //
        } catch (Exception e) {
            e.printStackTrace();
            Msg.erro(this, "Erro ao Gerar a Senha!\nErro: " + e.getMessage());
        }
    }

    private void salvaSenha(SenhaBean senha) {
        try {
            String sql = "INSERT INTO `senha` (`senha`, `tipo`, `data`,`status`,`prioridade`) VALUES (?, ?, now(),?,?)";
            pst = conexao.prepareStatement(sql);
            pst.setString(1, senha.getSenha());
            pst.setString(2, senha.getTipo());
            pst.setInt(3, senha.getStatus());
            pst.setInt(4, senha.getPrioridade());

            pst.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            Msg.erro(this, "Erro ao Salvar a Senha!\nErro: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnNormal = new javax.swing.JButton();
        btnPrioritario = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        btnPrioritario1 = new javax.swing.JButton();
        btnPrioritario2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setExtendedState(MAXIMIZED_BOTH);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        btnNormal.setBackground(new java.awt.Color(51, 102, 255));
        btnNormal.setFont(new java.awt.Font("Tahoma", 0, 50)); // NOI18N
        btnNormal.setForeground(new java.awt.Color(255, 255, 255));
        btnNormal.setText("NORMAL");
        btnNormal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNormalActionPerformed(evt);
            }
        });

        btnPrioritario.setBackground(new java.awt.Color(255, 51, 51));
        btnPrioritario.setFont(new java.awt.Font("Tahoma", 0, 50)); // NOI18N
        btnPrioritario.setForeground(new java.awt.Color(255, 255, 255));
        btnPrioritario.setText("PREFERENCIAL");
        btnPrioritario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrioritarioActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 60)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("RETIRE SUA SENHA");

        btnPrioritario1.setBackground(new java.awt.Color(255, 102, 0));
        btnPrioritario1.setFont(new java.awt.Font("Tahoma", 0, 50)); // NOI18N
        btnPrioritario1.setForeground(new java.awt.Color(255, 255, 255));
        btnPrioritario1.setText("DOR NO PEITO");
        btnPrioritario1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrioritario1ActionPerformed(evt);
            }
        });

        btnPrioritario2.setBackground(new java.awt.Color(153, 0, 204));
        btnPrioritario2.setFont(new java.awt.Font("Tahoma", 0, 50)); // NOI18N
        btnPrioritario2.setForeground(new java.awt.Color(255, 255, 255));
        btnPrioritario2.setText("FEBRE E TOSSE");
        btnPrioritario2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrioritario2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnPrioritario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNormal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnPrioritario1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnPrioritario2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(63, 63, 63))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 846, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel1)
                .addGap(33, 33, 33)
                .addComponent(btnNormal, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPrioritario, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPrioritario1, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPrioritario2, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                .addGap(21, 21, 21))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNormalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNormalActionPerformed
        gravaSenha("NM",4);//NM ATENDIMENTO NORMAL
    }//GEN-LAST:event_btnNormalActionPerformed

    private void btnPrioritarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrioritarioActionPerformed
        gravaSenha("PR",1); //PR ATENDIMENTO PRIORITARIO
    }//GEN-LAST:event_btnPrioritarioActionPerformed

    private void btnPrioritario1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrioritario1ActionPerformed
        gravaSenha("DP",2); //DP ATENDIMENTO DOR NO PEITO
    }//GEN-LAST:event_btnPrioritario1ActionPerformed

    private void btnPrioritario2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrioritario2ActionPerformed
        gravaSenha("FT",3); //FT ATENDIMENTO FEBRE E TOSSE
    }//GEN-LAST:event_btnPrioritario2ActionPerformed

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
            java.util.logging.Logger.getLogger(TelaSenhaTotem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaSenhaTotem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaSenhaTotem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaSenhaTotem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaSenhaTotem().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnNormal;
    private javax.swing.JButton btnPrioritario;
    private javax.swing.JButton btnPrioritario1;
    private javax.swing.JButton btnPrioritario2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
