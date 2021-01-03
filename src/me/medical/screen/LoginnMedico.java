package me.medical.screen;

import me.medical.controller.Conexao;
import me.medical.utils.BLDatas;
import me.medical.utils.Singleton;
import me.medical.utils.UpperCaseField;
import me.medical.utils.Utils;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JToolTip;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.Timer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Adriano Zanette
 */
public class LoginnMedico extends javax.swing.JFrame {

    Popup tooltip = null;
    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    String usuario;
    public Timer objetotimer;
    public int a;

    /**
     * Creates new form Adminlogin
     */
    public LoginnMedico() {
        initComponents();
        txtUsuario.setDocument(new UpperCaseField(100));
        Utils.considerarEnterComoTab(txtUsuario);
        //jPanel1.setBackground(new Color(255, 255, 255, 70));
        setIcon();
//        configurar();
        populaJComboBoxConsultorio();
        txtUsuario.requestFocus();
        conexao = Conexao.conector();
    }

//    //configurar data e hora
//    private void configurar() {
//
//        BLDatas bLDatas = new BLDatas();
//        jlData2.setText(bLDatas.retornarDataHora());
//
//    }

    private void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("Receita.png")));
    }

    private void verificaCapsLock() {
        boolean capsLigado = Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK);
        if (capsLigado) {
            showToolTip(txtSenha);
        } else {
            hideToolTip();
        }
    }

    private void showToolTip(JComponent component) {
        PopupFactory popupFactory = PopupFactory.getSharedInstance();
        JToolTip tooltip1 = component.createToolTip();
        String toolTipText = "<html><font color=red>Aviso</font><br><B>CAPS LOCK</B> está ativo</html>";
        tooltip1.setTipText(toolTipText);
        try {
            if (tooltip == null) {
                tooltip = popupFactory.getPopup(component, tooltip1, component.getLocationOnScreen().x, component.getLocationOnScreen().y + component.getHeight());
            }
            tooltip.show();
        } catch (java.lang.Error e) {
            tooltip = popupFactory.getPopup(component, tooltip1, component.getLocationOnScreen().x, component.getLocationOnScreen().y + component.getHeight());
            tooltip.show();
        }
    }

    private void hideToolTip() {
        if (tooltip != null) {
            tooltip.hide();
        }
    }

    public void Logar() {
        Conexao conec = new Conexao();
        String sql = "select * from usuarios where login=? and senha =?";
        {
            try {
                String user = txtUsuario.getText();
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtUsuario.getText());
                pst.setString(2, txtSenha.getText());
                rs = pst.executeQuery();    
                if (rs.next()) {
                    //salvando na memoria usuario e medico
                    Singleton.setSala(jCSala.getSelectedItem().toString());
                    Singleton.setUsuario(txtUsuario.getText());
                    TelaPrincipalMedico tela = new TelaPrincipalMedico();
                    tela.setVisible(true);
                    dispose();
                     // aqui chamo a opcao se sim imprime e salva ----------se escolher nao .....nao imprime e salva  
                     int sair = JOptionPane.showConfirmDialog(null, "FAVOR FAZER O CADASTRO A SEGUIR NO INÍCIO DO PLANTÃO \n"
                             + "OS DADOS SERÃO EXIBIDOS NO MONITOR DE ATENDIMENTO DA RECEPÇÃO"
                             , "ATENÇÃO DR(a)", JOptionPane.YES_OPTION);
                    if (sair == JOptionPane.YES_OPTION) {
                    TelaCadAtendimentosDiarios telas = new TelaCadAtendimentosDiarios();
                    telas.setVisible(true);  
          }  
                } else {
                    JOptionPane.showMessageDialog(null, "Usuário e senha inválidos!");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            } finally {
                try {
                    rs.close();
                    pst.close();
                    conec.desconectar();
                } catch (Exception ex) {
                }
            }
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

        jPanel2 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtSenha = new javax.swing.JPasswordField();
        btnEntrar = new javax.swing.JButton();
        jCSala = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Login Médico");
        setExtendedState(6);
        setResizable(false);

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("SIH - Sistema Integração Hospitalar");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 220, 340, 40));

        txtUsuario.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtUsuario.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtUsuario.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsuarioActionPerformed(evt);
            }
        });
        txtUsuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtUsuarioKeyReleased(evt);
            }
        });
        jPanel2.add(txtUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 290, 319, 40));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Seu Nome");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 270, -1, -1));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Sua Senha");
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 330, -1, 30));

        txtSenha.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtSenha.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtSenha.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtSenha.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtSenhaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtSenhaFocusLost(evt);
            }
        });
        txtSenha.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                txtSenhaMouseReleased(evt);
            }
        });
        txtSenha.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSenhaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSenhaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSenhaKeyTyped(evt);
            }
        });
        jPanel2.add(txtSenha, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 360, 319, 40));

        btnEntrar.setBackground(new java.awt.Color(255, 255, 255));
        btnEntrar.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        btnEntrar.setForeground(new java.awt.Color(51, 153, 255));
        btnEntrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagensTelas/Login_go.png"))); // NOI18N
        btnEntrar.setText("Acessar");
        btnEntrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEntrarMouseClicked(evt);
            }
        });
        btnEntrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEntrarActionPerformed(evt);
            }
        });
        jPanel2.add(btnEntrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 480, 320, 40));

        jCSala.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jCSala.setForeground(new java.awt.Color(51, 51, 51));
        jCSala.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "< Selecione Consultório >" }));
        jPanel2.add(jCSala, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 420, 319, 33));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagensTelas/LoginMedico.png"))); // NOI18N
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1370, 730));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 1370, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 731, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(1388, 759));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUsuarioActionPerformed
        txtSenha.requestFocus();        // TODO add your handling code here:
    }//GEN-LAST:event_txtUsuarioActionPerformed

    private void txtUsuarioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUsuarioKeyReleased

        // contagem do valor digitado no campo
        if (txtUsuario.getText().trim().length() > 0 && new String(txtSenha.getPassword()).length() > 0) {
            btnEntrar.setEnabled(true);
        } else {
            btnEntrar.setEnabled(false);
        }
    }//GEN-LAST:event_txtUsuarioKeyReleased

    private void txtSenhaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSenhaFocusGained
        verificaCapsLock();
    }//GEN-LAST:event_txtSenhaFocusGained

    private void txtSenhaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSenhaFocusLost
        hideToolTip();
    }//GEN-LAST:event_txtSenhaFocusLost

    private void txtSenhaMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSenhaMouseReleased

    }//GEN-LAST:event_txtSenhaMouseReleased

    private void txtSenhaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSenhaKeyPressed
        verificaCapsLock();
    }//GEN-LAST:event_txtSenhaKeyPressed

    private void txtSenhaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSenhaKeyReleased
        // contagem do valor digitado no campo
        if (txtUsuario.getText().trim().length() > 0 && new String(txtSenha.getPassword()).length() > 0) {
            btnEntrar.setEnabled(true);
        } else {
            btnEntrar.setEnabled(false);

        }
    }//GEN-LAST:event_txtSenhaKeyReleased

    private void txtSenhaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSenhaKeyTyped

    }//GEN-LAST:event_txtSenhaKeyTyped

    private void btnEntrarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEntrarMouseClicked

    }//GEN-LAST:event_btnEntrarMouseClicked

    private void btnEntrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEntrarActionPerformed
        // chamando o método logar
        Logar();
    }//GEN-LAST:event_btnEntrarActionPerformed

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
            java.util.logging.Logger.getLogger(LoginnMedico.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginnMedico.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginnMedico.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginnMedico.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new LoginnMedico().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEntrar;
    private javax.swing.JComboBox<String> jCSala;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPasswordField txtSenha;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables

    public void populaJComboBoxConsultorio() {
        String sql = "Select *from tb_consultorios";
        try {
            pst = Conexao.conector().prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                jCSala.addItem(rs.getString("tipoConsultorio"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

}
