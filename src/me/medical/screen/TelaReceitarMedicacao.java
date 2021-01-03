/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.medical.screen;

import me.medical.controller.Conexao;
import java.awt.Font;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Icon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author Adriano Zanette
 */
public class TelaReceitarMedicacao extends javax.swing.JFrame {

    String setar_dados = "";
    TelaCadastroMedicacao tela = new TelaCadastroMedicacao();
    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    /**
     * Creates new form TelaReceitarMedicacao
     */
    public TelaReceitarMedicacao() {
        initComponents();
        setIcon();
        conexao = Conexao.conector();
        txtPrescricao.setLineWrap(true);
        txtPrescricao.setWrapStyleWord(true);
    }

    private void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("Receita.png")));
    }

    // icone do joptionPane
    Icon icon = new javax.swing.ImageIcon(getClass().getResource("/Imagens/ok.png"));

    // icone do joptionPane
    Icon iconExcluir = new javax.swing.ImageIcon(getClass().getResource("/Imagens/Excluido.png"));

    
    
    public void pesquisarMedicacao() {
         Conexao conec = new Conexao();
        DefaultTableModel model = (DefaultTableModel) tabelaNomeMedicacao.getModel();
        model.setNumRows(0);
        //conexão com o banco de dados
        String sql = "Select *from tb_medicacao where UsoMedicacao like ?";

        try {
            pst = Conexao.conector().prepareStatement(sql);
            pst.setString(1, txtPesquisar.getText() + "%");
            rs = pst.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("UsoMedicacao"),});
            }
        } catch (SQLException error) {
            JOptionPane.showMessageDialog(null, error);
        }finally {
            try {
                rs.close();
                pst.close();
                conec.desconectar();
            } catch (Exception ex) {
            }
        }
    }
    
    
    public void listarNomePaciente() {
        Conexao conec = new Conexao();
        String sql = "Select NomePaciente from tb_agenda order by codigo Asc";
        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            TabelaNomePaciente.setModel(DbUtils.resultSetToTableModel(rs));

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

    // lista na tabelinha 
    public void listarMedicacao() {
        Conexao conec = new Conexao();
        DefaultTableModel model = (DefaultTableModel) tabelaNomeMedicacao.getModel();
        model.setNumRows(0);
        String sql = "Select NomeMedicacao,ViaAcesso,Intervalo from tb_medicacao";
        try {
            pst = Conexao.conector().prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("NomeMedicacao"),
                    rs.getString("ViaAcesso"),
                    rs.getString("Intervalo"),});
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

    //--------------------------Métodos--------------------------------------
    private void habilitaCampos() {

        //txtCodigo.setEnabled(false);
        txtData.setEnabled(true);
        txtNomePaciente.setEnabled(false);
        txtPrescricao.setEnabled(true);
        btnPesquisarPaciente.setEnabled(true);

    }

    private void desaabilitaCampos() {

        txtData.setEnabled(false);
        txtNomePaciente.setEnabled(false);
        txtPrescricao.setEnabled(false);
        btnPesquisarPaciente.setEnabled(false);
        btnImprimir.setEnabled(false);
        btnMedicacao.setEnabled(false);

    }

    private void limparCampos() {

        txtData.getEditor().setText("");
        txtNomePaciente.setText("");
        txtPrescricao.setText("");

    }

    // metodo para colocar a letra em negrito
    Font fonte = new Font("Arial", Font.BOLD, 12);

    private void letrasemNegrito() {

        txtData.setFont(fonte);
        txtNomePaciente.setFont(fonte);
        txtPrescricao.setFont(fonte);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        LUPA_NOME_PACIENTE = new javax.swing.JDialog();
        jScrollPane3 = new javax.swing.JScrollPane();
        TabelaNomePaciente = new javax.swing.JTable();
        LUPA_NOME_MEDICACAO = new javax.swing.JDialog();
        txtPesquisar = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tabelaNomeMedicacao = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnSair = new javax.swing.JButton();
        jXTitledPanel1 = new org.jdesktop.swingx.JXTitledPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtPrescricao = new javax.swing.JTextArea();
        btnMedicacao = new javax.swing.JButton();
        btnLimpar = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        btnImprimir = new javax.swing.JButton();
        btnNovo = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtData = new org.jdesktop.swingx.JXDatePicker();
        txtNomePaciente = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        btnPesquisarPaciente = new javax.swing.JButton();

        LUPA_NOME_PACIENTE.setModal(true);

        TabelaNomePaciente.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        TabelaNomePaciente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Nome Paciente"
            }
        ));
        TabelaNomePaciente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabelaNomePacienteMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(TabelaNomePaciente);

        javax.swing.GroupLayout LUPA_NOME_PACIENTELayout = new javax.swing.GroupLayout(LUPA_NOME_PACIENTE.getContentPane());
        LUPA_NOME_PACIENTE.getContentPane().setLayout(LUPA_NOME_PACIENTELayout);
        LUPA_NOME_PACIENTELayout.setHorizontalGroup(
            LUPA_NOME_PACIENTELayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LUPA_NOME_PACIENTELayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 330, Short.MAX_VALUE)
                .addContainerGap())
        );
        LUPA_NOME_PACIENTELayout.setVerticalGroup(
            LUPA_NOME_PACIENTELayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LUPA_NOME_PACIENTELayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
                .addContainerGap())
        );

        txtPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPesquisarKeyReleased(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("Pesquisar Medicação por Nome:");

        tabelaNomeMedicacao.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Nome Medicação ", "Via Acesso", "Intervalo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaNomeMedicacao.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaNomeMedicacaoMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tabelaNomeMedicacao);

        javax.swing.GroupLayout LUPA_NOME_MEDICACAOLayout = new javax.swing.GroupLayout(LUPA_NOME_MEDICACAO.getContentPane());
        LUPA_NOME_MEDICACAO.getContentPane().setLayout(LUPA_NOME_MEDICACAOLayout);
        LUPA_NOME_MEDICACAOLayout.setHorizontalGroup(
            LUPA_NOME_MEDICACAOLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LUPA_NOME_MEDICACAOLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(LUPA_NOME_MEDICACAOLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtPesquisar)
                    .addGroup(LUPA_NOME_MEDICACAOLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(0, 489, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(LUPA_NOME_MEDICACAOLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(LUPA_NOME_MEDICACAOLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 681, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        LUPA_NOME_MEDICACAOLayout.setVerticalGroup(
            LUPA_NOME_MEDICACAOLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, LUPA_NOME_MEDICACAOLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(297, 297, 297))
            .addGroup(LUPA_NOME_MEDICACAOLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(LUPA_NOME_MEDICACAOLayout.createSequentialGroup()
                    .addGap(67, 67, 67)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Prescrição de Medicação");
        getContentPane().setLayout(null);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setText("Prescrição de Medicação");

        btnSair.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnSair.setForeground(new java.awt.Color(51, 51, 51));
        btnSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagensTelas/Sair.png"))); // NOI18N
        btnSair.setText("SAIR");
        btnSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSairActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSair)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(btnSair, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1);
        jPanel1.setBounds(10, 11, 780, 0);

        jXTitledPanel1.setTitle("PRESCRIÇÃO                                                                                                                           ADICIONAR MEDICAÇÃO ");
        jXTitledPanel1.setTitleFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        txtPrescricao.setColumns(20);
        txtPrescricao.setLineWrap(true);
        txtPrescricao.setRows(5);
        txtPrescricao.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder()));
        txtPrescricao.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtPrescricao.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPrescricaoFocusGained(evt);
            }
        });
        txtPrescricao.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtPrescricaoMouseClicked(evt);
            }
        });
        txtPrescricao.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                txtPrescricaoComponentShown(evt);
            }
        });
        txtPrescricao.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPrescricaoKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(txtPrescricao);

        btnMedicacao.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnMedicacao.setForeground(new java.awt.Color(51, 51, 51));
        btnMedicacao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Create.png"))); // NOI18N
        btnMedicacao.setText("MEDICAÇÃO");
        btnMedicacao.setEnabled(false);
        btnMedicacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMedicacaoActionPerformed(evt);
            }
        });

        btnLimpar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnLimpar.setForeground(new java.awt.Color(51, 51, 51));
        btnLimpar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/limpar.png"))); // NOI18N
        btnLimpar.setText("LIMPAR TEXTO");
        btnLimpar.setEnabled(false);
        btnLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jXTitledPanel1Layout = new javax.swing.GroupLayout(jXTitledPanel1.getContentContainer());
        jXTitledPanel1.getContentContainer().setLayout(jXTitledPanel1Layout);
        jXTitledPanel1Layout.setHorizontalGroup(
            jXTitledPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 776, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jXTitledPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnLimpar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnMedicacao)
                .addContainerGap())
        );
        jXTitledPanel1Layout.setVerticalGroup(
            jXTitledPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jXTitledPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jXTitledPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnLimpar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnMedicacao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE))
        );

        getContentPane().add(jXTitledPanel1);
        jXTitledPanel1.setBounds(10, 156, 780, 290);

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnImprimir.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Print_24x24.png"))); // NOI18N
        btnImprimir.setText("IMPRIMIR");
        btnImprimir.setEnabled(false);
        btnImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirActionPerformed(evt);
            }
        });

        btnNovo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnNovo.setForeground(new java.awt.Color(51, 51, 51));
        btnNovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagensTelas/Novo.png"))); // NOI18N
        btnNovo.setText("NOVO");
        btnNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 519, Short.MAX_VALUE)
                .addComponent(btnImprimir)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17))
        );

        getContentPane().add(jPanel5);
        jPanel5.setBounds(10, 460, 0, 70);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("Data:");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(10, 84, 33, 15);

        txtData.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtData.setEnabled(false);
        getContentPane().add(txtData);
        txtData.setBounds(10, 108, 151, 37);

        txtNomePaciente.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtNomePaciente.setEnabled(false);
        getContentPane().add(txtNomePaciente);
        txtNomePaciente.setBounds(167, 108, 533, 37);

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel16.setText("Nome Paciente");
        getContentPane().add(jLabel16);
        jLabel16.setBounds(167, 84, 90, 15);

        btnPesquisarPaciente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Search.png"))); // NOI18N
        btnPesquisarPaciente.setEnabled(false);
        btnPesquisarPaciente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesquisarPacienteActionPerformed(evt);
            }
        });
        getContentPane().add(btnPesquisarPaciente);
        btnPesquisarPaciente.setBounds(706, 105, 80, 40);

        setSize(new java.awt.Dimension(816, 573));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed

        dispose();

    }//GEN-LAST:event_btnSairActionPerformed

    private void txtPrescricaoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPrescricaoFocusGained
        btnImprimir.setEnabled(true);
        btnMedicacao.setEnabled(true);
        btnNovo.setEnabled(false);
        btnLimpar.setEnabled(true);
    }//GEN-LAST:event_txtPrescricaoFocusGained

    private void txtPrescricaoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtPrescricaoMouseClicked
        // OTODO add your handling code here:

    }//GEN-LAST:event_txtPrescricaoMouseClicked

    private void txtPrescricaoComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_txtPrescricaoComponentShown

    }//GEN-LAST:event_txtPrescricaoComponentShown

    private void txtPrescricaoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrescricaoKeyPressed

    }//GEN-LAST:event_txtPrescricaoKeyPressed

    private void btnMedicacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMedicacaoActionPerformed
        LUPA_NOME_MEDICACAO.setSize(new java.awt.Dimension(600, 400));
        LUPA_NOME_MEDICACAO.setLocationRelativeTo(null);
        listarMedicacao();
        LUPA_NOME_MEDICACAO.setVisible(true);
        txtPrescricao.requestFocus();
    }//GEN-LAST:event_btnMedicacaoActionPerformed

    private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed

        final Aguarde carregando = new Aguarde();
        carregando.setVisible(true);
        Thread t = new Thread() {
            public void run() {

                try {
                    ImpressaoMedicacao imprime = new ImpressaoMedicacao();
                    List listade_dados = GetDados();
                    imprime.Imprime_Relatorio(listade_dados);
                    carregando.dispose();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }
                desaabilitaCampos();
                limparCampos();
                btnNovo.setEnabled(true);
            }
        };
        t.start();

    }//GEN-LAST:event_btnImprimirActionPerformed

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed
        letrasemNegrito();
        txtData.requestFocus();
        habilitaCampos();
        limparCampos();
        letrasemNegrito();
    }//GEN-LAST:event_btnNovoActionPerformed

    private void btnPesquisarPacienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisarPacienteActionPerformed

        LUPA_NOME_PACIENTE.setSize(new java.awt.Dimension(400, 200));
        LUPA_NOME_PACIENTE.setLocationRelativeTo(null);
        listarNomePaciente();
        LUPA_NOME_PACIENTE.setVisible(true);
        txtData.requestFocus();
    }//GEN-LAST:event_btnPesquisarPacienteActionPerformed

    private void TabelaNomePacienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabelaNomePacienteMouseClicked
        // seleciona a linha da tabela e seta nos campos
        int linha = TabelaNomePaciente.getSelectedRow();
        if (linha >= 0) {
            // numero da coluna da tabelinha
            txtNomePaciente.setText(TabelaNomePaciente.getValueAt(linha, 0).toString());
            LUPA_NOME_PACIENTE.setVisible(false);
        }
    }//GEN-LAST:event_TabelaNomePacienteMouseClicked

    private void btnLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparActionPerformed
       txtPrescricao.setText("");
    }//GEN-LAST:event_btnLimparActionPerformed

    private void txtPesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisarKeyReleased
        pesquisarMedicacao();
    }//GEN-LAST:event_txtPesquisarKeyReleased

    private void tabelaNomeMedicacaoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaNomeMedicacaoMouseClicked
        //        // seleciona a linha da tabela e seta nos campos com quebra de linha
        //        int linha = tabelaNomeMedicacao.getSelectedRow();
        //        if (linha >= 0) {
            //            // esse codigo faz com que as linhas selecionadas da tabela fiquem uma abaixo da outra
            //            String dados0 = (tabelaNomeMedicacao.getValueAt(linha, 0).toString());
            //            String dados1 = (tabelaNomeMedicacao.getValueAt(linha, 1).toString());
            //            String dados2 = (tabelaNomeMedicacao.getValueAt(linha, 2).toString());
            //            String dados3 = (tabelaNomeMedicacao.getValueAt(linha, 3).toString());
            //            setar_dados = setar_dados + "\n"+ dados0;
            //            setar_dados = setar_dados + "\n"+ dados1;
            //            setar_dados = setar_dados + "\n"+ dados2;
            //            setar_dados = setar_dados + "\n"+ dados3;
            //            areaMedicacao.setText(setar_dados);
            //            LUPA_NOME_MEDICACAO.setVisible(false);
            //        }

        // seleciona a linha da tabela e seta nos campos sem quebra de linha
        int linha = tabelaNomeMedicacao.getSelectedRow();
        if (linha >= 0) {
            // esse codigo faz com que as linhas selecionadas da tabela fiquem uma abaixo da outra
            String dados0 = (tabelaNomeMedicacao.getValueAt(linha, 0).toString());
            String dados1 = (tabelaNomeMedicacao.getValueAt(linha, 1).toString());
            String dados2 = (tabelaNomeMedicacao.getValueAt(linha, 2).toString());
            setar_dados = setar_dados + "\n" + dados0;
            setar_dados = setar_dados + "     -     " + dados1;
            setar_dados = setar_dados + "     -     " + dados2;
            txtPrescricao.setText(setar_dados);
            LUPA_NOME_MEDICACAO.setVisible(false);
        }
    }//GEN-LAST:event_tabelaNomeMedicacaoMouseClicked

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
            java.util.logging.Logger.getLogger(TelaReceitarMedicacao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaReceitarMedicacao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaReceitarMedicacao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaReceitarMedicacao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaReceitarMedicacao().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog LUPA_NOME_MEDICACAO;
    private javax.swing.JDialog LUPA_NOME_PACIENTE;
    private javax.swing.JTable TabelaNomePaciente;
    private javax.swing.JButton btnImprimir;
    private javax.swing.JButton btnLimpar;
    private javax.swing.JButton btnMedicacao;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnPesquisarPaciente;
    private javax.swing.JButton btnSair;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private org.jdesktop.swingx.JXTitledPanel jXTitledPanel1;
    private javax.swing.JTable tabelaNomeMedicacao;
    private org.jdesktop.swingx.JXDatePicker txtData;
    private javax.swing.JTextField txtNomePaciente;
    private javax.swing.JTextField txtPesquisar;
    private javax.swing.JTextArea txtPrescricao;
    // End of variables declaration//GEN-END:variables

 public List GetDados() {

        List lista = new ArrayList();

//for (int i = 0; i < 10; i++) {
        AuxiliarMedicacao print = new AuxiliarMedicacao();
//print.setCodigo("");
        print.setData(txtData.getEditor().getText());
        print.setNome(txtNomePaciente.getText());
        print.setPrescricao(txtPrescricao.getText());
        lista.add(print);
        return lista;
    }





}
