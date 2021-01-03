/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.medical.screen;

import me.medical.controller.Conexao;
import me.medical.utils.Msg;
import me.medical.utils.UpperCaseField;
import me.medical.utils.Utils;
import me.medical.utils.ValidaEnter;
import java.awt.Color;
import java.awt.Font;
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
public class TelaAltaHospitalar extends javax.swing.JDialog {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    private TelaGeralPacientesIternadosMedico telaListagemMedico;

    /**
     * Creates new form TelaCadastro
     */
    public TelaAltaHospitalar(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        ValidaEnter ve = new ValidaEnter();
        ve.ValidaEnterPainel(jPanel6);
        setIcon();
        txtProntuario.requestFocus();
        letrasemNegrito();
        conexao = Conexao.conector();
       
    }

    public TelaAltaHospitalar(TelaGeralPacientesIternadosMedico parent, boolean modal) {
        super(parent, modal);
        initComponents();
        txtProntuario.requestFocus();
        ValidaEnter ve = new ValidaEnter();
        ve.ValidaEnterPainel(jPanel6);
        this.telaListagemMedico = parent;//implementado aqui para poder atualizar
        setIcon();
        letrasemNegrito();
        conexao = Conexao.conector();
       
    }

    private void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("Receita.png")));
    }

    // icone do joptionPane
    Icon icon = new javax.swing.ImageIcon(getClass().getResource("/Imagens/ok.png"));

    // icone do joptionPane
    Icon iconExcluir = new javax.swing.ImageIcon(getClass().getResource("/Imagens/Excluido.png"));

    

    // metodo para colocar a letra em negrito
    Font fonte = new Font("Arial", Font.BOLD, 12);

    private void letrasemNegrito() {

       
        jCStatus.setFont(fonte);
    }

    public void habilitaCampos() {

       
        jCStatus.setEnabled(true);
    }

    public void desaabilitaCampos() {

       
        jCStatus.setEnabled(false);

    }

    public void limpar() {

       
//        txtProntuario.setText("");
//        txtNomePaciente.setText("");
//        txtDataInternacao.getEditor().setText("");
        jCStatus.setSelectedIndex(0);
    }

    private void EditarCadastro() {
        Conexao conec = new Conexao();
        String sql = "update tb_internacao set Status=?,DataAlta=? where codigo=?";

        try {
            pst = Conexao.conector().prepareStatement(sql);
            pst.setString(1, jCStatus.getSelectedItem().toString());
            pst.setString(2, txtDataAltaHospitalar.getEditor().getText());
            pst.setInt(3, Integer.parseInt(txtProntuario.getText()));
            pst.execute();
            JOptionPane.showMessageDialog(rootPane, " Leito com código " + txtProntuario.getText()+ " teve a Alta Hospitalar registrada com sucesso.", "Alteração", JOptionPane.INFORMATION_MESSAGE, icon);
            
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

    public void listarNomePaciente() {
        Conexao conec = new Conexao();
        DefaultTableModel model = (DefaultTableModel) TabelaNomePaciente.getModel();
        model.setNumRows(0);
        String sql = "Select Codigo,Prontuario,DataInternacao,NomePaciente from tb_internacao order by codigo Asc";
        try {
            pst = Conexao.conector().prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("codigo"),
                    rs.getString("Prontuario"),
                    Utils.convertData(rs.getDate("DataInternacao")),
                    rs.getString("NomePaciente"),});
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
    
    
    
     public void listarLeitos() {
         Conexao conec = new Conexao();
         DefaultTableModel model = (DefaultTableModel) TabelaLeitos.getModel();
         model.setNumRows(0);
        String sql = "Select leito,status from tb_leitos order by codigo Asc";
        try {
            pst = Conexao.conector().prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("leito"),
                    rs.getString("status"),
                    });
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
        LUPA_LEITOS = new javax.swing.JDialog();
        jScrollPane1 = new javax.swing.JScrollPane();
        TabelaLeitos = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jCStatus = new javax.swing.JComboBox<>();
        jLabel27 = new javax.swing.JLabel();
        txtDataAltaHospitalar = new org.jdesktop.swingx.JXDatePicker();
        jLabel7 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        txtProntuario = new javax.swing.JTextField();

        LUPA_NOME_PACIENTE.setModal(true);

        TabelaNomePaciente.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        TabelaNomePaciente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Codigo", "Prontuario", "Data Internação", "Nome Paciente"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true
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
            TabelaNomePaciente.getColumnModel().getColumn(0).setMinWidth(0);
            TabelaNomePaciente.getColumnModel().getColumn(0).setPreferredWidth(0);
            TabelaNomePaciente.getColumnModel().getColumn(0).setMaxWidth(0);
            TabelaNomePaciente.getColumnModel().getColumn(1).setMinWidth(0);
            TabelaNomePaciente.getColumnModel().getColumn(1).setPreferredWidth(0);
            TabelaNomePaciente.getColumnModel().getColumn(1).setMaxWidth(0);
            TabelaNomePaciente.getColumnModel().getColumn(2).setMinWidth(0);
            TabelaNomePaciente.getColumnModel().getColumn(2).setPreferredWidth(0);
            TabelaNomePaciente.getColumnModel().getColumn(2).setMaxWidth(0);
        }

        javax.swing.GroupLayout LUPA_NOME_PACIENTELayout = new javax.swing.GroupLayout(LUPA_NOME_PACIENTE.getContentPane());
        LUPA_NOME_PACIENTE.getContentPane().setLayout(LUPA_NOME_PACIENTELayout);
        LUPA_NOME_PACIENTELayout.setHorizontalGroup(
            LUPA_NOME_PACIENTELayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LUPA_NOME_PACIENTELayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 491, Short.MAX_VALUE)
                .addContainerGap())
        );
        LUPA_NOME_PACIENTELayout.setVerticalGroup(
            LUPA_NOME_PACIENTELayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LUPA_NOME_PACIENTELayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
                .addContainerGap())
        );

        LUPA_LEITOS.setModal(true);

        TabelaLeitos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Leito", "Status"
            }
        ));
        TabelaLeitos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabelaLeitosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TabelaLeitos);

        javax.swing.GroupLayout LUPA_LEITOSLayout = new javax.swing.GroupLayout(LUPA_LEITOS.getContentPane());
        LUPA_LEITOS.getContentPane().setLayout(LUPA_LEITOSLayout);
        LUPA_LEITOSLayout.setHorizontalGroup(
            LUPA_LEITOSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LUPA_LEITOSLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 491, Short.MAX_VALUE)
                .addContainerGap())
        );
        LUPA_LEITOSLayout.setVerticalGroup(
            LUPA_LEITOSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LUPA_LEITOSLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Alta Hospitalar");
        getContentPane().setLayout(null);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagensTelas/excluir_1.png"))); // NOI18N
        jButton4.setText("Cancelar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton4);

        btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagensTelas/salvar_1.png"))); // NOI18N
        btnEditar.setText("Salvar");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });
        jPanel1.add(btnEditar);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(10, 200, 450, 39);

        jPanel4.setBackground(new java.awt.Color(51, 51, 51));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 450, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 8, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel4);
        jPanel4.setBounds(10, 70, 450, 0);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setText("Alta Hospitalar");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(304, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel1)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2);
        jPanel2.setBounds(10, 10, 450, 0);

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11), new java.awt.Color(255, 0, 0))); // NOI18N

        jCStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "< Selecione >", "Alta Hospitalar" }));
        jCStatus.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jCStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCStatusActionPerformed(evt);
            }
        });

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel27.setText("DATA ALTA HOSPITALAR:");

        txtDataAltaHospitalar.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtDataAltaHospitalar.setEnabled(false);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("STATUS :");

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel24.setText("PRONTUARIO:");

        txtProntuario.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtProntuario.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtProntuario.setEnabled(false);
        txtProntuario.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtProntuarioFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtProntuarioFocusLost(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtProntuario))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jCStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel27)
                    .addComponent(txtDataAltaHospitalar, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel27)
                                .addGap(40, 40, 40))
                            .addComponent(txtDataAltaHospitalar, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel6Layout.createSequentialGroup()
                            .addComponent(jLabel7)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jCStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel24)
                        .addGap(5, 5, 5)
                        .addComponent(txtProntuario, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel6);
        jPanel6.setBounds(10, 90, 450, 100);

        setSize(new java.awt.Dimension(481, 287));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void TabelaNomePacienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabelaNomePacienteMouseClicked
//        // seleciona a linha da tabela e seta nos campos
//        int linha = TabelaNomePaciente.getSelectedRow();
//        if (linha >= 0) {
//            // numero da coluna da tabelinha
//            txtCodigo.setText(TabelaNomePaciente.getValueAt(linha, 0).toString());
//            txtProntuario.setText(TabelaNomePaciente.getValueAt(linha, 1).toString());
//            txtDataInternacao.getEditor().setText(TabelaNomePaciente.getValueAt(linha, 2).toString());
//            txtNomePaciente.setText(TabelaNomePaciente.getValueAt(linha, 3).toString());
//            LUPA_NOME_PACIENTE.setVisible(false);
//        }
    }//GEN-LAST:event_TabelaNomePacienteMouseClicked

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        EditarCadastro();
//        listarCadastro();
        limpar();
        desaabilitaCampos();
        btnEditar.setEnabled(true);
        if (telaListagemMedico != null) {
            telaListagemMedico.listarDados();
        }
        dispose();
         
//        int sair = JOptionPane.showConfirmDialog(null, "Atenção DR. por favor mudar o STATUS do leito, deseja fazer agora ?", "ATENÇÃO", JOptionPane.YES_NO_OPTION);
//        if (sair == JOptionPane.YES_OPTION) {
//                    TelaGeralLeitos tela = new TelaGeralLeitos();
//                    tela.setVisible(true);  
//        } else{
//        
//             
//        }
    }//GEN-LAST:event_btnEditarActionPerformed
    
    private void TabelaLeitosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabelaLeitosMouseClicked
      
    }//GEN-LAST:event_TabelaLeitosMouseClicked

    private void txtProntuarioFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtProntuarioFocusLost
        txtProntuario.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_txtProntuarioFocusLost

    private void txtProntuarioFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtProntuarioFocusGained
           jCStatus.setEnabled(true);
           txtDataAltaHospitalar.setEnabled(true);
    }//GEN-LAST:event_txtProntuarioFocusGained

    private void jCStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCStatusActionPerformed
        if (jCStatus.getSelectedItem().toString().equals("Alta Hospitalar")) {

            txtDataAltaHospitalar.setEnabled(true);
    }//GEN-LAST:event_jCStatusActionPerformed
    }
   
    
    
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
            java.util.logging.Logger.getLogger(TelaAltaHospitalar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaAltaHospitalar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaAltaHospitalar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaAltaHospitalar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                TelaAltaHospitalar dialog = new TelaAltaHospitalar(new javax.swing.JFrame(), true);
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
    private javax.swing.JDialog LUPA_LEITOS;
    private javax.swing.JDialog LUPA_NOME_PACIENTE;
    private javax.swing.JTable TabelaLeitos;
    private javax.swing.JTable TabelaNomePaciente;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton jButton4;
    public javax.swing.JComboBox<String> jCStatus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    public org.jdesktop.swingx.JXDatePicker txtDataAltaHospitalar;
    public javax.swing.JTextField txtProntuario;
    // End of variables declaration//GEN-END:variables

    //metodo converte data
    private java.sql.Date convertDateSalvar(java.util.Date var) throws SQLException {
        int year = var.getDate();
        int month = var.getMonth();
        int day = var.getYear();
        return new java.sql.Date(day, month, year);
    }

}
