/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.medical.screen;

import me.medical.utils.BLDatas;
import me.medical.utils.Singleton;
import me.medical.utils.Utils;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Adriano Zanette
 */
public class TelaPrincipalEnfermagemGeral extends javax.swing.JFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public TelaPrincipalEnfermagemGeral() {
        initComponents();
        labelSala.setText("Sala: " + Singleton.getSala());
        labelUsuario1.setText("Usuário: " + Singleton.getUsuario());
        setIcon();
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        configurar();

    }

    private void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("Receita.png")));
    }

    //configurar data e hora
    private void configurar() {
        BLDatas bLDatas = new BLDatas();
        jlData2.setText(bLDatas.retornarDataHora());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanelbotoes = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        labelUsuario = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jlData2 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        labelUsuario1 = new javax.swing.JLabel();
        labelSala = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenucadastros = new javax.swing.JMenu();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem14 = new javax.swing.JMenuItem();
        jMenuItem15 = new javax.swing.JMenuItem();
        jMenu10 = new javax.swing.JMenu();
        jMenuItem18 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem19 = new javax.swing.JMenuItem();
        jMenucadastros3 = new javax.swing.JMenu();
        menu1 = new javax.swing.JMenuItem();
        menu2 = new javax.swing.JMenuItem();
        menu7 = new javax.swing.JMenuItem();
        jMenucadastros2 = new javax.swing.JMenu();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenuItem13 = new javax.swing.JMenuItem();
        jMenuSobre1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Sys Hospital");
        setExtendedState(6);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanelbotoes.setBackground(new java.awt.Color(255, 255, 255));
        jPanelbotoes.setToolTipText("Tela Recepção");
        jPanelbotoes.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Arial Black", 1, 36)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 102, 255));
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/símbolo-enfermagem-png-.png"))); // NOI18N
        jPanelbotoes.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 180, 540, 260));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Tela_Principal_img.png"))); // NOI18N
        jPanelbotoes.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 0, 1410, 610));

        labelUsuario.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        labelUsuario.setForeground(new java.awt.Color(255, 255, 255));
        labelUsuario.setText("Usuário");
        jPanelbotoes.add(labelUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 140, 220, -1));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanelbotoes, javax.swing.GroupLayout.PREFERRED_SIZE, 1388, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 10, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelbotoes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jlData2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jlData2.setForeground(new java.awt.Color(255, 0, 0));
        jlData2.setText("data");

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/clock.png"))); // NOI18N

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("Data/Hora de login: - ");

        labelUsuario1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        labelUsuario1.setForeground(new java.awt.Color(51, 51, 51));
        labelUsuario1.setText("Usuário: ");

        labelSala.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        labelSala.setForeground(new java.awt.Color(51, 51, 51));
        labelSala.setText("Sala:");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelUsuario1, javax.swing.GroupLayout.PREFERRED_SIZE, 462, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(labelSala, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jlData2)
                .addGap(42, 42, 42))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jlData2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(labelUsuario1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(labelSala, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jMenuBar1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jMenuBar1.setPreferredSize(new java.awt.Dimension(653, 40));

        jMenucadastros.setBackground(new java.awt.Color(255, 255, 255));
        jMenucadastros.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jMenucadastros.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Archive_24x24.png"))); // NOI18N
        jMenucadastros.setText("Acesso Rápido");
        jMenucadastros.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jMenucadastros.setFont(new java.awt.Font("Gulim", 1, 14)); // NOI18N
        jMenucadastros.setMaximumSize(new java.awt.Dimension(180, 32767));
        jMenucadastros.setName(""); // NOI18N
        jMenucadastros.setPreferredSize(new java.awt.Dimension(180, 30));
        jMenucadastros.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenucadastrosMouseClicked(evt);
            }
        });
        jMenucadastros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenucadastrosActionPerformed(evt);
            }
        });

        jMenuItem6.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        jMenuItem6.setText("Classificaçao de Risco");
        jMenuItem6.setPreferredSize(new java.awt.Dimension(200, 27));
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenucadastros.add(jMenuItem6);

        jMenuItem14.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, 0));
        jMenuItem14.setText("Solicitar Medicação");
        jMenuItem14.setPreferredSize(new java.awt.Dimension(200, 27));
        jMenuItem14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem14ActionPerformed(evt);
            }
        });
        jMenucadastros.add(jMenuItem14);

        jMenuItem15.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F5, 0));
        jMenuItem15.setText("Cadastrar Efetivos");
        jMenuItem15.setPreferredSize(new java.awt.Dimension(200, 27));
        jMenuItem15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem15ActionPerformed(evt);
            }
        });
        jMenucadastros.add(jMenuItem15);

        jMenu10.setText("Cadastrar de Leitos");
        jMenu10.setPreferredSize(new java.awt.Dimension(140, 26));

        jMenuItem18.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem18.setText("Leitos UTI");
        jMenuItem18.setPreferredSize(new java.awt.Dimension(175, 22));
        jMenuItem18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem18ActionPerformed(evt);
            }
        });
        jMenu10.add(jMenuItem18);

        jMenuItem5.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_B, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem5.setText("Leitos Enfermaria");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu10.add(jMenuItem5);

        jMenuItem19.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem19.setText("Leitos Apartamentos");
        jMenuItem19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem19ActionPerformed(evt);
            }
        });
        jMenu10.add(jMenuItem19);

        jMenucadastros.add(jMenu10);

        jMenuBar1.add(jMenucadastros);

        jMenucadastros3.setBackground(new java.awt.Color(255, 255, 255));
        jMenucadastros3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jMenucadastros3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagensTelas/camaHospital.png"))); // NOI18N
        jMenucadastros3.setText("Internação do Paciente");
        jMenucadastros3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jMenucadastros3.setFont(new java.awt.Font("Gulim", 1, 14)); // NOI18N
        jMenucadastros3.setMaximumSize(new java.awt.Dimension(230, 32767));
        jMenucadastros3.setName(""); // NOI18N
        jMenucadastros3.setPreferredSize(new java.awt.Dimension(180, 30));
        jMenucadastros3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenucadastros3MouseClicked(evt);
            }
        });
        jMenucadastros3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenucadastros3ActionPerformed(evt);
            }
        });

        menu1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F8, 0));
        menu1.setText("Cadastrar Acomodação da Internação");
        menu1.setPreferredSize(new java.awt.Dimension(270, 27));
        menu1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menu1MouseClicked(evt);
            }
        });
        menu1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu1ActionPerformed(evt);
            }
        });
        jMenucadastros3.add(menu1);

        menu2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F9, 0));
        menu2.setText("Verificar Alta Hospitalar");
        menu2.setPreferredSize(new java.awt.Dimension(200, 27));
        menu2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu2ActionPerformed(evt);
            }
        });
        jMenucadastros3.add(menu2);

        menu7.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F10, 0));
        menu7.setText("Alterar Status dos Leitos");
        menu7.setPreferredSize(new java.awt.Dimension(200, 27));
        menu7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu7ActionPerformed(evt);
            }
        });
        jMenucadastros3.add(menu7);

        jMenuBar1.add(jMenucadastros3);

        jMenucadastros2.setBackground(new java.awt.Color(255, 255, 255));
        jMenucadastros2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jMenucadastros2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Evolucao.png"))); // NOI18N
        jMenucadastros2.setText("Evolução Paciente");
        jMenucadastros2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jMenucadastros2.setFont(new java.awt.Font("Gulim", 1, 14)); // NOI18N
        jMenucadastros2.setMaximumSize(new java.awt.Dimension(210, 32767));
        jMenucadastros2.setName(""); // NOI18N
        jMenucadastros2.setPreferredSize(new java.awt.Dimension(180, 30));
        jMenucadastros2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenucadastros2MouseClicked(evt);
            }
        });
        jMenucadastros2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenucadastros2ActionPerformed(evt);
            }
        });

        jMenuItem8.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F7, 0));
        jMenuItem8.setText("Cadastrar Evolução");
        jMenuItem8.setPreferredSize(new java.awt.Dimension(200, 27));
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenucadastros2.add(jMenuItem8);

        jMenuItem13.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F6, 0));
        jMenuItem13.setText("Visualizar Evolução dos Pacientes");
        jMenuItem13.setPreferredSize(new java.awt.Dimension(230, 27));
        jMenuItem13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem13ActionPerformed(evt);
            }
        });
        jMenucadastros2.add(jMenuItem13);

        jMenuBar1.add(jMenucadastros2);

        jMenuSobre1.setBackground(new java.awt.Color(255, 255, 255));
        jMenuSobre1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jMenuSobre1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/home.png"))); // NOI18N
        jMenuSobre1.setText("Sair");
        jMenuSobre1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jMenuSobre1.setFont(new java.awt.Font("Gulim", 1, 14)); // NOI18N
        jMenuSobre1.setMaximumSize(new java.awt.Dimension(90, 32767));
        jMenuSobre1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenuSobre1MouseClicked(evt);
            }
        });

        jMenuItem1.setText("LogOut");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenuSobre1.add(jMenuItem1);

        jMenuBar1.add(jMenuSobre1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuSobre1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuSobre1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuSobre1MouseClicked

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed

        dispose();

    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenucadastrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenucadastrosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenucadastrosActionPerformed

    private void jMenucadastrosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenucadastrosMouseClicked

    }//GEN-LAST:event_jMenucadastrosMouseClicked

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        TelaSenhaClassificacaoRisco tela = new TelaSenhaClassificacaoRisco();
        tela.setVisible(true);
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem14ActionPerformed
        TelaSenhaPedirmedicacao tela = new TelaSenhaPedirmedicacao();
        tela.setLocationRelativeTo(null);
        tela.setVisible(true);
    }//GEN-LAST:event_jMenuItem14ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        TelaGeralPacientesEvolucao tela = new TelaGeralPacientesEvolucao();
        tela.setLocationRelativeTo(null);
        tela.setVisible(true);
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jMenuItem13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem13ActionPerformed
        TelaVisualizarEvolucao tela = new TelaVisualizarEvolucao();
        tela.setLocationRelativeTo(null);
        tela.setVisible(true);
    }//GEN-LAST:event_jMenuItem13ActionPerformed

    private void jMenucadastros2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenucadastros2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenucadastros2MouseClicked

    private void jMenucadastros2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenucadastros2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenucadastros2ActionPerformed

    private void menu1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu1ActionPerformed

        TelaSenhaInternacao tela = new TelaSenhaInternacao();
        tela.setVisible(true);


    }//GEN-LAST:event_menu1ActionPerformed

    private void jMenucadastros3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenucadastros3MouseClicked

    }//GEN-LAST:event_jMenucadastros3MouseClicked

    private void jMenucadastros3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenucadastros3ActionPerformed

    }//GEN-LAST:event_jMenucadastros3ActionPerformed

    private void jMenuItem15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem15ActionPerformed
        TelaCadastroEfetivos tela = new TelaCadastroEfetivos();
        tela.setLocationRelativeTo(null);
        tela.setVisible(true);
    }//GEN-LAST:event_jMenuItem15ActionPerformed

    private void menu2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu2ActionPerformed
        TelaGeralPacientesAltaHospitalar tela = new TelaGeralPacientesAltaHospitalar();
        tela.setLocationRelativeTo(null);
        tela.setVisible(true);
    }//GEN-LAST:event_menu2ActionPerformed

    private void jMenuItem18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem18ActionPerformed
        TelaCadastroLeitosUTI tela = new TelaCadastroLeitosUTI();
        tela.setLocationRelativeTo(null);
        tela.setVisible(true);
    }//GEN-LAST:event_jMenuItem18ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        TelaCadastroLeitosEnfermaria tela = new TelaCadastroLeitosEnfermaria();
        tela.setVisible(true);
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem19ActionPerformed
        TelaCadastroLeitosApartamentos tela = new TelaCadastroLeitosApartamentos();
        tela.setVisible(true);
    }//GEN-LAST:event_jMenuItem19ActionPerformed

    private void menu1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menu1MouseClicked

    }//GEN-LAST:event_menu1MouseClicked

    private void menu7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu7ActionPerformed
       TelaGeralLeitos tela = new TelaGeralLeitos();
        tela.setVisible(true);
    }//GEN-LAST:event_menu7ActionPerformed

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
            java.util.logging.Logger.getLogger(TelaPrincipalEnfermagemGeral.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipalEnfermagemGeral.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipalEnfermagemGeral.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipalEnfermagemGeral.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new TelaPrincipalEnfermagemGeral().setVisible(true);
            }
        });

    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JMenu jMenu10;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JMenuItem jMenuItem14;
    private javax.swing.JMenuItem jMenuItem15;
    private javax.swing.JMenuItem jMenuItem18;
    private javax.swing.JMenuItem jMenuItem19;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenu jMenuSobre1;
    private javax.swing.JMenu jMenucadastros;
    private javax.swing.JMenu jMenucadastros2;
    private javax.swing.JMenu jMenucadastros3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanelbotoes;
    private javax.swing.JLabel jlData2;
    private javax.swing.JLabel labelSala;
    private javax.swing.JLabel labelUsuario;
    private javax.swing.JLabel labelUsuario1;
    public javax.swing.JMenuItem menu1;
    public javax.swing.JMenuItem menu2;
    public javax.swing.JMenuItem menu7;
    // End of variables declaration//GEN-END:variables
}