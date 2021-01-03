/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.medical.screen;

import me.medical.controller.Conexao;
import me.medical.utils.UpperCaseField;
import me.medical.utils.ValidaEnter;
import java.awt.Color;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.Icon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author Adriano Zanette
 */
public class TelaInternacao extends javax.swing.JFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
   // private TelaPrincipalRecepcao pai;
    

    /**
     * Creates new form TelaInternacao
     */
    public TelaInternacao() {
        initComponents();
        this.iniciar();
    }
    
    
    
    
    private void iniciar() {
        conexao = Conexao.conector();
        setIcon();
        jcAcomodacao.setVisible(false);
        lblMensagem.setVisible((false));
        
    }
    
    
    

    private void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("Receita.png")));
    }

    // icone do joptionPane
    Icon icon = new javax.swing.ImageIcon(getClass().getResource("/Imagens/ok.png"));

    // icone do joptionPane
    Icon iconExcluir = new javax.swing.ImageIcon(getClass().getResource("/Imagens/Excluido.png"));

    public void SalvarInternacao() {
        Conexao conec = new Conexao();
        String sql = "insert into tb_internacao(DataInternacao,NomePaciente,Prontuario,Status,TipoAcomodacao)values(?,?,?,?,?)";

        try {
            pst = Conexao.conector().prepareStatement(sql);
            pst.setDate(1, convertDateSalvar(txtDataInternacao.getDate()));
            pst.setString(2, txtNomePaciente.getText());
            pst.setString(3, txtProntuario.getText());
            pst.setString(4, jCStatus.getSelectedItem().toString());
            pst.setString(5, jcAcomodacao.getSelectedItem().toString());
            pst.execute();
            JOptionPane.showMessageDialog(rootPane, " Internação " + txtNomePaciente.getText().toUpperCase() + " salvo com sucesso.", "Inclusão", JOptionPane.INFORMATION_MESSAGE, icon);
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
    
    //metodo converte data
    private java.sql.Date convertDateSalvar(java.util.Date var) throws SQLException {
        int year = var.getDate();
        int month = var.getMonth();
        int day = var.getYear();
        return new java.sql.Date(day, month, year);
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
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnSalvar = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        txtNomePaciente = new javax.swing.JTextField();
        txtProntuario = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        txtDataInternacao = new org.jdesktop.swingx.JXDatePicker();
        jCStatus = new javax.swing.JComboBox<>();
        jcAcomodacao = new javax.swing.JComboBox<>();
        lblMensagem = new javax.swing.JLabel();
        btnPesquisarPaciente = new javax.swing.JButton();

        LUPA_NOME_PACIENTE.setModal(true);

        TabelaNomePaciente.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        TabelaNomePaciente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Prontuário", "Nome Paciente"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TabelaNomePaciente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabelaNomePacienteMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(TabelaNomePaciente);
        if (TabelaNomePaciente.getColumnModel().getColumnCount() > 0) {
            TabelaNomePaciente.getColumnModel().getColumn(0).setMinWidth(70);
            TabelaNomePaciente.getColumnModel().getColumn(0).setPreferredWidth(70);
            TabelaNomePaciente.getColumnModel().getColumn(0).setMaxWidth(70);
        }

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

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Tela Internação");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setText("Cadastrar Internação");

        btnSalvar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnSalvar.setForeground(new java.awt.Color(51, 51, 51));
        btnSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagensTelas/Salvar.png"))); // NOI18N
        btnSalvar.setText("SALVAR");
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 477, Short.MAX_VALUE)
                .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 11, 810, 70));

        jPanel4.setBackground(new java.awt.Color(51, 51, 51));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 810, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 8, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 84, 810, -1));

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("NOME PACIENTE");

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel26.setText("PRONTUÁRIO");

        txtNomePaciente.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        txtNomePaciente.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtNomePaciente.setEnabled(false);
        txtNomePaciente.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomePacienteFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomePacienteFocusLost(evt);
            }
        });
        txtNomePaciente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNomePacienteKeyReleased(evt);
            }
        });

        txtProntuario.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtProntuario.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtProntuario.setEnabled(false);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText("STATUS:");

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel25.setText("DATA INTERNAÇÃO");

        txtDataInternacao.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jCStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "< Selecione >", "Internado" }));
        jCStatus.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jCStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCStatusActionPerformed(evt);
            }
        });

        jcAcomodacao.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<Selecione>", "UTI", "Enfermaria", "Apartamento" }));
        jcAcomodacao.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblMensagem.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblMensagem.setForeground(new java.awt.Color(255, 0, 0));
        lblMensagem.setText("Escolha o setor de Internação");

        btnPesquisarPaciente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Search.png"))); // NOI18N
        btnPesquisarPaciente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesquisarPacienteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel26)
                    .addComponent(txtProntuario, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtNomePaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPesquisarPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(8, 8, 8)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel25)
                    .addComponent(txtDataInternacao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblMensagem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCStatus, 0, 129, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jcAcomodacao, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel26)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtProntuario))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtNomePaciente, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                            .addComponent(btnPesquisarPaciente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel25)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtDataInternacao, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jCStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcAcomodacao, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMensagem))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 103, 810, 130));

        setSize(new java.awt.Dimension(848, 287));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtNomePacienteFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomePacienteFocusGained
        txtNomePaciente.setBackground(new Color(252, 233, 163));
    }//GEN-LAST:event_txtNomePacienteFocusGained

    private void txtNomePacienteFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomePacienteFocusLost
        txtNomePaciente.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_txtNomePacienteFocusLost

    private void txtNomePacienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNomePacienteKeyReleased

    }//GEN-LAST:event_txtNomePacienteKeyReleased

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
         if (txtNomePaciente.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Campo Nome é obrigatório", "Atenção", JOptionPane.WARNING_MESSAGE);
            txtNomePaciente.requestFocus();
            return;
        }
        if (txtDataInternacao.getDate() == null) {
            JOptionPane.showMessageDialog(rootPane, "Campo Data internação é Obrigatório", "Atenção", JOptionPane.WARNING_MESSAGE);
            txtDataInternacao.requestFocus();
            return;
        }
        
         
        if (jCStatus.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(rootPane, "O Status é obrigatório", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
         if (jcAcomodacao.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(rootPane, "O setor de internação é obrigatório", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        SalvarInternacao();
        dispose();
        
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void jCStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCStatusActionPerformed
        if (jCStatus.getSelectedItem().toString().equals("Internado")) {
            jcAcomodacao.setVisible(true); 
            jCStatus.setVisible(true);
            lblMensagem.setVisible((true));
            
            
    }//GEN-LAST:event_jCStatusActionPerformed
    }
    private void TabelaNomePacienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabelaNomePacienteMouseClicked
        // seleciona a linha da tabela e seta nos campos
        int linha = TabelaNomePaciente.getSelectedRow();
        if (linha >= 0) {
            // numero da coluna da tabelinha
            txtProntuario.setText(TabelaNomePaciente.getValueAt(linha, 0).toString());
            txtNomePaciente.setText(TabelaNomePaciente.getValueAt(linha, 1).toString());
            LUPA_NOME_PACIENTE.setVisible(false);
        }
    }//GEN-LAST:event_TabelaNomePacienteMouseClicked

    private void btnPesquisarPacienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisarPacienteActionPerformed

        LUPA_NOME_PACIENTE.setSize(new java.awt.Dimension(500, 300));
        LUPA_NOME_PACIENTE.setLocationRelativeTo(null);
        listarNomePaciente();
        LUPA_NOME_PACIENTE.setVisible(true);
        
    }//GEN-LAST:event_btnPesquisarPacienteActionPerformed
    
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
            java.util.logging.Logger.getLogger(TelaInternacao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaInternacao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaInternacao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaInternacao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaInternacao().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog LUPA_NOME_PACIENTE;
    private javax.swing.JTable TabelaNomePaciente;
    private javax.swing.JButton btnPesquisarPaciente;
    private javax.swing.JButton btnSalvar;
    public javax.swing.JComboBox<String> jCStatus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JComboBox<String> jcAcomodacao;
    private javax.swing.JLabel lblMensagem;
    public org.jdesktop.swingx.JXDatePicker txtDataInternacao;
    public javax.swing.JTextField txtNomePaciente;
    public javax.swing.JTextField txtProntuario;
    // End of variables declaration//GEN-END:variables

 public void listarNomePaciente() {
        Conexao conec = new Conexao();
        String sql = "Select Prontuario,NomePaciente from tb_agenda order by codigo Asc";
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
    
}