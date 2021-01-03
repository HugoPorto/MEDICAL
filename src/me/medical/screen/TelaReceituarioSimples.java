/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.medical.screen;

//import Controle.Conexao;
import me.medical.controller.Conexao;
import me.medical.utils.ValidaEnter;
import java.awt.Font;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;
//import net.proteanit.sql.DbUtils;

/**
 *
 * @author Adriano Zanette
 */
public class TelaReceituarioSimples extends javax.swing.JFrame {

    String setar_dados = "";
    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    /**
     * Creates new form TelaRelatorio
     */
    public TelaReceituarioSimples() {
        initComponents();
        areaPrescricao.setLineWrap(true);
        areaPrescricao.setWrapStyleWord(true);
        ValidaEnter ve = new ValidaEnter();
        ve.ValidaEnterPainel(jPanel4);
        setIcon();
        conexao = Conexao.conector();
        // areaPrescricao.setDocument(new UpperCaseField(100));

    }

    private void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("Receita.png")));
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

    //--------------------------Métodos--------------------------------------
    private void habilitaCampos() {

        //txtCodigo.setEnabled(false);
        txtDataAtestado.setEnabled(true);
        txtNomePaciente.setEnabled(false);
        btnPesquisarPaciente.setEnabled(true);

    }

    private void desaabilitaCampos() {

        txtDataAtestado.setEnabled(false);
        txtNomePaciente.setEnabled(false);
        btnPesquisarPaciente.setEnabled(false);

    }

    private void limparCampos() {

        txtDataAtestado.getEditor().setText("");
        txtNomePaciente.setText("");
        btnPesquisarPaciente.setText("");
        areaPrescricao.setText("");

    }

    // metodo para colocar a letra em negrito
    Font fonte = new Font("Arial", Font.BOLD, 12);

    private void letrasemNegrito() {

        txtDataAtestado.setFont(fonte);
        txtNomePaciente.setFont(fonte);
        btnPesquisarPaciente.setFont(fonte);

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
        } finally {
            try {
                rs.close();
                pst.close();
                conec.desconectar();
            } catch (Exception ex) {
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

        LUPA_NOME_PACIENTE = new javax.swing.JDialog();
        jScrollPane3 = new javax.swing.JScrollPane();
        TabelaNomePaciente = new javax.swing.JTable();
        LUPA_NOME_MEDICACAO = new javax.swing.JDialog();
        txtPesquisar = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tabelaNomeMedicacao = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        btnSair1 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        btnImprimir = new javax.swing.JButton();
        btnNovo = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtNomePaciente = new javax.swing.JTextField();
        btnPesquisarPaciente = new javax.swing.JButton();
        txtDataAtestado = new org.jdesktop.swingx.JXDatePicker();
        jXTitledPanel1 = new org.jdesktop.swingx.JXTitledPanel();
        btnMedicacao = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        areaPrescricao = new javax.swing.JTextArea();

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
        if (tabelaNomeMedicacao.getColumnModel().getColumnCount() > 0) {
            tabelaNomeMedicacao.getColumnModel().getColumn(1).setMinWidth(150);
            tabelaNomeMedicacao.getColumnModel().getColumn(1).setPreferredWidth(150);
            tabelaNomeMedicacao.getColumnModel().getColumn(1).setMaxWidth(150);
            tabelaNomeMedicacao.getColumnModel().getColumn(2).setMinWidth(100);
            tabelaNomeMedicacao.getColumnModel().getColumn(2).setPreferredWidth(100);
            tabelaNomeMedicacao.getColumnModel().getColumn(2).setMaxWidth(100);
        }

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
        setTitle("Receituário Simples");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 51));
        jLabel2.setText("Receituário Simples");

        btnSair1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnSair1.setForeground(new java.awt.Color(51, 51, 51));
        btnSair1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagensTelas/Sair.png"))); // NOI18N
        btnSair1.setText("SAIR");
        btnSair1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSair1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 560, Short.MAX_VALUE)
                .addComponent(btnSair1)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSair1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 11, 800, 60));

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 539, Short.MAX_VALUE)
                .addComponent(btnImprimir)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17))
        );

        getContentPane().add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 500, 800, 90));

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 800, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 800, 10));

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel15.setText("Data do Atendimento:");

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel16.setText("Nome Paciente:");

        txtNomePaciente.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtNomePaciente.setEnabled(false);

        btnPesquisarPaciente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Search.png"))); // NOI18N
        btnPesquisarPaciente.setEnabled(false);
        btnPesquisarPaciente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesquisarPacienteActionPerformed(evt);
            }
        });

        txtDataAtestado.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtDataAtestado.setEnabled(false);

        jXTitledPanel1.setTitle("PRESCRIÇÃO                                                                                                                                   ADICIONAR MEDICAÇÃO");
        jXTitledPanel1.setTitleFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jXTitledPanel1.setTitleForeground(new java.awt.Color(51, 51, 51));

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

        areaPrescricao.setColumns(20);
        areaPrescricao.setRows(5);
        areaPrescricao.setEnabled(false);
        areaPrescricao.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                areaPrescricaoFocusGained(evt);
            }
        });
        areaPrescricao.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                areaPrescricaoMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(areaPrescricao);

        javax.swing.GroupLayout jXTitledPanel1Layout = new javax.swing.GroupLayout(jXTitledPanel1.getContentContainer());
        jXTitledPanel1.getContentContainer().setLayout(jXTitledPanel1Layout);
        jXTitledPanel1Layout.setHorizontalGroup(
            jXTitledPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jXTitledPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnMedicacao)
                .addContainerGap())
            .addComponent(jScrollPane1)
        );
        jXTitledPanel1Layout.setVerticalGroup(
            jXTitledPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jXTitledPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnMedicacao, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jLabel15))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(txtDataAtestado, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(txtNomePaciente)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnPesquisarPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addGap(0, 531, Short.MAX_VALUE))))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jXTitledPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnPesquisarPaciente, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(txtNomePaciente)
                    .addComponent(txtDataAtestado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jXTitledPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 800, 400));

        setSize(new java.awt.Dimension(837, 641));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSair1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSair1ActionPerformed

        //Pergunta se o usuário deseja realmente sair do sistema
        int sair = JOptionPane.showConfirmDialog(null, "DESEJA SAIR DA TELA RECEITA SIMPLES ?", "ATENÇÃO", JOptionPane.YES_NO_OPTION);
        if (sair == JOptionPane.YES_OPTION) {
            dispose();
        }
    }//GEN-LAST:event_btnSair1ActionPerformed

    private void btnPesquisarPacienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisarPacienteActionPerformed

        LUPA_NOME_PACIENTE.setSize(new java.awt.Dimension(400, 200));
        LUPA_NOME_PACIENTE.setLocationRelativeTo(null);
        listarNomePaciente();
        LUPA_NOME_PACIENTE.setVisible(true);
        txtDataAtestado.requestFocus();
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

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed
        letrasemNegrito();
        txtDataAtestado.requestFocus();
        areaPrescricao.setEnabled(true);
        areaPrescricao.setFont(fonte);
        habilitaCampos();
        limparCampos();
        letrasemNegrito();
    }//GEN-LAST:event_btnNovoActionPerformed

    private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed

        final Aguarde carregando = new Aguarde();
        carregando.setVisible(true);
        Thread t = new Thread() {
            public void run() {

                try {
                    ImpressaoReceitaSimples imprime = new ImpressaoReceitaSimples();
                    List listade_dados = GetDados();
                    imprime.Imprime_Relatorio(listade_dados);
                    carregando.dispose();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }
                desaabilitaCampos();
                limparCampos();
                areaPrescricao.setEnabled(false);
                dispose();
            }
        };
        t.start();


    }//GEN-LAST:event_btnImprimirActionPerformed

    private void areaPrescricaoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_areaPrescricaoMouseClicked

    }//GEN-LAST:event_areaPrescricaoMouseClicked

    private void areaPrescricaoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_areaPrescricaoFocusGained
        btnImprimir.setEnabled(true);
        btnMedicacao.setEnabled(true);
    }//GEN-LAST:event_areaPrescricaoFocusGained

    private void btnMedicacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMedicacaoActionPerformed
        LUPA_NOME_MEDICACAO.setSize(new java.awt.Dimension(600, 400));
        LUPA_NOME_MEDICACAO.setLocationRelativeTo(null);
        listarMedicacao();
        LUPA_NOME_MEDICACAO.setVisible(true);
        areaPrescricao.requestFocus();
    }//GEN-LAST:event_btnMedicacaoActionPerformed

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
            areaPrescricao.setText(setar_dados);
            LUPA_NOME_MEDICACAO.setVisible(false);
        }

    }//GEN-LAST:event_tabelaNomeMedicacaoMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Create and display the form */
 /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaReceituarioSimples().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog LUPA_NOME_MEDICACAO;
    private javax.swing.JDialog LUPA_NOME_PACIENTE;
    private javax.swing.JTable TabelaNomePaciente;
    private javax.swing.JTextArea areaPrescricao;
    private javax.swing.JButton btnImprimir;
    private javax.swing.JButton btnMedicacao;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnPesquisarPaciente;
    private javax.swing.JButton btnSair1;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private org.jdesktop.swingx.JXTitledPanel jXTitledPanel1;
    private javax.swing.JTable tabelaNomeMedicacao;
    private org.jdesktop.swingx.JXDatePicker txtDataAtestado;
    private javax.swing.JTextField txtNomePaciente;
    private javax.swing.JTextField txtPesquisar;
    // End of variables declaration//GEN-END:variables

    public List GetDados() {

        List lista = new ArrayList();

//for (int i = 0; i < 10; i++) {
        AuxiliarReceitaSimples print = new AuxiliarReceitaSimples();
//print.setCodigo("");
        print.setData(txtDataAtestado.getEditor().getText());
        print.setNome(txtNomePaciente.getText());
        print.setPrescricao(areaPrescricao.getText());

        lista.add(print);
        return lista;
    }

}
