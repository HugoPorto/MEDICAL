/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.medical.screen;

import me.medical.controller.Conexao;
import me.medical.model.SenhaBean;
import me.medical.utils.AePlayWave;
import me.medical.utils.Msg;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author Adriano Zanette
 */
public class TelaSenhaTelao extends javax.swing.JFrame {

    private List<SenhaBean> listaSenha = new ArrayList<>();
    private Connection conexao = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;
    private Timer timer = new Timer();

    public TelaSenhaTelao() {
        initComponents();
        setIcon();
        conexao = Conexao.conector();
        this.sincroniza();
    }
    
     private void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("Receita.png")));
    }

    private void atualizaLista() {
        try {
            String sql = "SELECT senha.senha, senha.idSenha, senha.local, senha.nomePaciente FROM senha where senha.`status` = 1 and senha.podeChamar=1";
            pst = Conexao.conector().prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                SenhaBean senha = new SenhaBean();
                senha.setSenha(rs.getString("senha"));
                senha.setNomePaciente(rs.getString("nomePaciente"));
                //se senha for igual a zero chamo paciente pelo nome
                if (senha.getSenha().equals("0")) {
                    labelSenha.setText("NOME");
                    txSenha.setFont(new java.awt.Font("Clarendon BT", 1, 40));
                    senha.setSenha(senha.getNomePaciente());
                } else {
                    //SENÃO CHAMO PELA SENHA
                    labelSenha.setText("SENHA");
                    txSenha.setFont(new java.awt.Font("Clarendon BT", 1, 120));
                    senha.setSenha(senha.getSenha());
                }

                senha.setLocal(rs.getString("local"));
                new AePlayWave("misc038.wav").start();//toca o audio
                txSenha.setText(senha.getSenha());//seta a senha no Telão
                txLocal.setText(senha.getLocal());//seta o local no Telão
                piscandoLabel(1);//pisca a Senha
                atualizaStatusSenha(rs.getInt("idSenha"));
                listaSenha.add(senha);
                atualizaListaChamados();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Msg.erro(this, "Erro ao Buscar Dados no Banco de Dados!\nErro: " + e.getMessage());
        }
    }

    private void atualizaStatusSenha(int idSenha) {
        try {
            String sql = "UPDATE `senha` SET `status`='0' WHERE  `idSenha`=?;";
            pst = Conexao.conector().prepareStatement(sql);
            pst.setInt(1, idSenha);
            pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            Msg.erro(this, "Erro ao Alterar Status Senha!");
        }
    }

    private void sincroniza() {
        try {

            final java.util.Timer t = new java.util.Timer();
            t.schedule(new TimerTask() {
                @Override
                public void run() {
                    atualizaLista();
                    System.out.println("Atualizou o Sistema");
                }
            },
                    15000// 60000 //após 30 segundos ele irá iniciar a tarefa
                    ,
                     10000);    //após 5 segundos, executará novamente
        } catch (Exception e) {
            e.printStackTrace();
            Msg.alerta(this, "Erro ao Sincronizar o Sistema!\nErro: " + e.getMessage());
        }
    }

    private void atualizaListaChamados() {
        try {
            int cont = 0;
            painelPrincipal.removeAll();
            for (int i = listaSenha.size() - 1; i >= 0; i--) {
                if (cont < 3) {
                    painelPrincipal.add(new PainelAtendido(listaSenha.get(i), this));
                    cont++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Msg.erro(this, "Erro ao Atualizar A Lista\n Erro:" + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        painelPrincipal = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        txLocal = new javax.swing.JLabel();
        txLocal1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        txSenha = new javax.swing.JLabel();
        labelSenha = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setExtendedState(MAXIMIZED_BOTH);

        painelPrincipal.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 4, true));

        txLocal.setFont(new java.awt.Font("Clarendon BT", 1, 60)); // NOI18N
        txLocal.setForeground(new java.awt.Color(0, 102, 0));
        txLocal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txLocal.setText(" ");

        txLocal1.setFont(new java.awt.Font("Clarendon BT", 1, 60)); // NOI18N
        txLocal1.setForeground(new java.awt.Color(255, 0, 0));
        txLocal1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txLocal1.setText("LOCAL");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txLocal1, javax.swing.GroupLayout.DEFAULT_SIZE, 532, Short.MAX_VALUE)
                    .addComponent(txLocal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(txLocal1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txLocal)
                .addContainerGap(55, Short.MAX_VALUE))
        );

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 4, true));

        txSenha.setFont(new java.awt.Font("Clarendon BT", 1, 140)); // NOI18N
        txSenha.setForeground(new java.awt.Color(0, 102, 0));
        txSenha.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txSenha.setText("00000");

        labelSenha.setFont(new java.awt.Font("Clarendon BT", 1, 60)); // NOI18N
        labelSenha.setForeground(new java.awt.Color(255, 0, 0));
        labelSenha.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelSenha.setText("SENHA");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txSenha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(labelSenha, javax.swing.GroupLayout.DEFAULT_SIZE, 532, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addComponent(txSenha, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)
                .addGap(21, 21, 21))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(31, 31, 31)
                    .addComponent(labelSenha)
                    .addContainerGap(166, Short.MAX_VALUE)))
        );

        jLabel3.setFont(new java.awt.Font("Clarendon BT", 1, 40)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("SENHA CHAMADA");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 472, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(582, Short.MAX_VALUE)
                    .addComponent(painelPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addGap(60, 60, 60)
                    .addComponent(painelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, 439, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(TelaSenhaTelao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaSenhaTelao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaSenhaTelao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaSenhaTelao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaSenhaTelao().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel labelSenha;
    private javax.swing.JPanel painelPrincipal;
    private javax.swing.JLabel txLocal;
    private javax.swing.JLabel txLocal1;
    private javax.swing.JLabel txSenha;
    // End of variables declaration//GEN-END:variables

    //pisca senha
    public void piscandoLabel(int seconds) {
        timer = new Timer();
        timer.schedule(new RemindTask(), 10, seconds * 500);
    }

    class RemindTask extends TimerTask {

        int i = 0;

        public void run() {
            if (true) {
                txSenha.setVisible(!txSenha.isVisible());
                i++;
            }
            if (i > 6) {
                timerStop();
            }
        }
    }

    public void timerStop() {
        timer.cancel();
        txSenha.setVisible(true);
    }
}
