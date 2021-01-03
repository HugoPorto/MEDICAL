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

/**
 *
 * @author Adriano Zanette
 */
public class TelaCadastroLeitosUTI extends javax.swing.JFrame {

    // usando a variavel conexao do DAO
    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    /**
     * Creates new form TelaUsuario
     */
    public TelaCadastroLeitosUTI() {
        initComponents();
        // chama classe de letras maiusculas
        txtLeito.setDocument(new UpperCaseField(100));
        setIcon();
        //txtData.setText(horaAtual());
        ValidaEnter ve = new ValidaEnter();
        ve.ValidaEnterPainel(jPanel3);
        conexao = Conexao.conector();
        listarLeito();
//        alterarConvenio();
        letrasemNegrito();
    }

    //-----------------------------------METODOS------------------------------------------------------------------//
//    
//    public static String horaAtual(){
//        Date hora=new Date();
//        SimpleDateFormat formatoHora=new SimpleDateFormat("dd/MM/YYYY");
//        return formatoHora.format(hora);
//    }
//    
    private void habilitaCampos() {
        txtLeito.setEnabled(true);
        jTableLeitoUti.setEnabled(true);
        txtData.setEnabled(true);
        jCStatus.setEnabled(true);
       
    }

    private void desaabilitaCampos() {
        txtLeito.setEnabled(false);
        jTableLeitoUti.setEnabled(false);
        txtData.setEnabled(false);
        jCStatus.setEnabled(false);
        
    }

    // metodo para colocar a letra em negrito
    Font fonte = new Font("Arial", Font.BOLD, 12);

    private void letrasemNegrito() {
        txtData.setFont(fonte);
        txtLeito.setFont(fonte);
        txtData.setFont(fonte);
        jCStatus.setFont(fonte);
        

    }

    private void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("Receita.png")));
    }
    
     // icone do joptionPane
    Icon icon = new javax.swing.ImageIcon(getClass().getResource("/Imagens/ok.png"));
    
    
    // icone do joptionPane
    Icon iconExcluir = new javax.swing.ImageIcon(getClass().getResource("/Imagens/Excluido.png"));
    
    

    public void listarLeito() {
        Conexao conec = new Conexao();
        DefaultTableModel model = (DefaultTableModel) jTableLeitoUti.getModel();
        model.setNumRows(0);
        String sql = "Select codigo,DataCadastro,leito,status from tb_leitosuti order by codigo";
        try {
            pst = Conexao.conector().prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("codigo"),
                    Utils.convertData(rs.getDate("DataCadastro")),
                    rs.getString("leito"),
                    rs.getString("status"),});
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

    public void SalvarLeitoUti() {
        Conexao conec = new Conexao();
        String sql = "insert into tb_leitosuti(DataCadastro,leito,status)values(?,?,?)";

        try {
            pst = Conexao.conector().prepareStatement(sql);
            pst.setDate(1, convertDateSalvar(txtData.getDate()));
            pst.setString(2, txtLeito.getText());
            pst.setString(3, jCStatus.getSelectedItem().toString());
            pst.execute();
            JOptionPane.showMessageDialog(rootPane, " Leito " + txtLeito.getText().toUpperCase() + " salvo com sucesso.", "Inclusão", JOptionPane.INFORMATION_MESSAGE,icon);
            listarLeito();
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

    public void alterarLeito() {
        Conexao conec = new Conexao();
        String sql = "update tb_leitosuti set DataCadastro=?,leito=?,status=? where codigo=?";

        try {
            pst = Conexao.conector().prepareStatement(sql);
            pst.setDate(1, convertDateSalvar(txtData.getDate()));
            pst.setString(2, txtLeito.getText());
            pst.setString(3, jCStatus.getSelectedItem().toString());
            pst.setInt(4, Integer.parseInt(txtCodigo.getText()));
            pst.executeUpdate();
            JOptionPane.showMessageDialog(rootPane, " Leito " + txtLeito.getText().toUpperCase() + " Atualizado com sucesso.", "Alteração", JOptionPane.INFORMATION_MESSAGE,icon);
            listarLeito();
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

    public void removerLeito() {
        Conexao conec = new Conexao();
        String sql = "delete from tb_leitosuti where codigo=?";

        try {
            pst = Conexao.conector().prepareStatement(sql);
            pst.setInt(1, Integer.parseInt(txtCodigo.getText()));
            pst.execute();
            JOptionPane.showMessageDialog(rootPane, " Leito " + txtLeito.getText().toUpperCase() + " Excluido com sucesso.", "Inclusão", JOptionPane.INFORMATION_MESSAGE,iconExcluir);
            listarLeito();
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

    public void mostrarItensLeitosUTI() {
        int seleciona = jTableLeitoUti.getSelectedRow();
        txtCodigo.setText(jTableLeitoUti.getModel().getValueAt(seleciona, 0).toString());
        txtData.setDate(Utils.convertData(jTableLeitoUti.getModel().getValueAt(seleciona, 1).toString()));
        txtLeito.setText(jTableLeitoUti.getModel().getValueAt(seleciona, 2).toString());
        jCStatus.setSelectedItem(jTableLeitoUti.getModel().getValueAt(seleciona, 3).toString());
    }

    private void limpar() {
        txtCodigo.setText("");
        txtLeito.setText("");
        txtData.getEditor().setText("");
        jCStatus.setSelectedIndex(0);
        
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnSair = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtLeito = new javax.swing.JTextField();
        txtData = new org.jdesktop.swingx.JXDatePicker();
        jCStatus = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        btnNovo = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableLeitoUti = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastro Leitos UTI");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setText("Cadastro de Leitos UTI");

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 190, Short.MAX_VALUE)
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

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 4, 500, -1));

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel24.setText("CÓDIGO:");

        txtCodigo.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtCodigo.setEnabled(false);

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel25.setText("DATA CADASTRO:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("NÚMERO DO LEITO:");

        txtLeito.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        txtLeito.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtLeito.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtLeito.setEnabled(false);
        txtLeito.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtLeitoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtLeitoFocusLost(evt);
            }
        });
        txtLeito.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtLeitoKeyReleased(evt);
            }
        });

        txtData.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtData.setEnabled(false);

        jCStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<Selecione>", "Disponivel" }));
        jCStatus.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jCStatus.setEnabled(false);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText("STATUS DO LEITO:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel24)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel25)
                    .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txtLeito, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jCStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 14, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 15, Short.MAX_VALUE)
                        .addGap(43, 43, 43))
                    .addComponent(jCStatus, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel24)
                            .addComponent(jLabel25))
                        .addGap(5, 5, 5)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtLeito, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {txtCodigo, txtLeito});

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 86, 500, -1));

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnNovo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnNovo.setForeground(new java.awt.Color(51, 51, 51));
        btnNovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagensTelas/Novo.png"))); // NOI18N
        btnNovo.setText("NOVO");
        btnNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoActionPerformed(evt);
            }
        });

        btnEditar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnEditar.setForeground(new java.awt.Color(51, 51, 51));
        btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagensTelas/Editar.png"))); // NOI18N
        btnEditar.setText("EDITAR");
        btnEditar.setEnabled(false);
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnExcluir.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnExcluir.setForeground(new java.awt.Color(51, 51, 51));
        btnExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagensTelas/excluir.png"))); // NOI18N
        btnExcluir.setText("EXCLUIR");
        btnExcluir.setEnabled(false);
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });

        btnSalvar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnSalvar.setForeground(new java.awt.Color(51, 51, 51));
        btnSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagensTelas/Salvar.png"))); // NOI18N
        btnSalvar.setText("SALVAR");
        btnSalvar.setEnabled(false);
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEditar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnExcluir)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 324, 500, -1));

        jTableLeitoUti.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Data Cadastro", "Número Leito", "Status Leito"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableLeitoUti.setEnabled(false);
        jTableLeitoUti.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableLeitoUtiMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTableLeitoUtiMouseReleased(evt);
            }
        });
        jTableLeitoUti.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTableLeitoUtiKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jTableLeitoUti);
        if (jTableLeitoUti.getColumnModel().getColumnCount() > 0) {
            jTableLeitoUti.getColumnModel().getColumn(0).setMinWidth(60);
            jTableLeitoUti.getColumnModel().getColumn(0).setPreferredWidth(60);
            jTableLeitoUti.getColumnModel().getColumn(0).setMaxWidth(60);
            jTableLeitoUti.getColumnModel().getColumn(1).setMinWidth(150);
            jTableLeitoUti.getColumnModel().getColumn(1).setPreferredWidth(150);
            jTableLeitoUti.getColumnModel().getColumn(1).setMaxWidth(150);
            jTableLeitoUti.getColumnModel().getColumn(2).setMinWidth(150);
            jTableLeitoUti.getColumnModel().getColumn(2).setPreferredWidth(150);
            jTableLeitoUti.getColumnModel().getColumn(2).setMaxWidth(150);
            jTableLeitoUti.getColumnModel().getColumn(3).setMinWidth(150);
            jTableLeitoUti.getColumnModel().getColumn(3).setPreferredWidth(150);
            jTableLeitoUti.getColumnModel().getColumn(3).setMaxWidth(150);
        }

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 176, 500, 142));

        jPanel4.setBackground(new java.awt.Color(51, 51, 51));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 8, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 72, 500, -1));

        setSize(new java.awt.Dimension(531, 439));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
        //Pergunta se o usuário deseja realmente sair do sistema
            dispose();
    }//GEN-LAST:event_btnSairActionPerformed

    private void jTableLeitoUtiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableLeitoUtiMouseClicked
        mostrarItensLeitosUTI();
        habilitaCampos();
        btnEditar.setEnabled(true);
        btnExcluir.setEnabled(true);
        btnSalvar.setEnabled(false);
        btnNovo.setEnabled(false);

    }//GEN-LAST:event_jTableLeitoUtiMouseClicked

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed
        txtData.requestFocus();
        btnEditar.setEnabled(false);
        btnExcluir.setEnabled(false);
        btnSalvar.setEnabled(true);
        habilitaCampos();
        limpar();
    }//GEN-LAST:event_btnNovoActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        int i = jTableLeitoUti.getSelectedRow();
        if (i < 0) {
            Msg.alerta(this, "Selecione um Registro!");
            return;
        }
        alterarLeito();
        listarLeito();
        desaabilitaCampos();
        limpar();
        btnNovo.setEnabled(true);
        btnEditar.setEnabled(false);
        btnExcluir.setEnabled(false);
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        int i = jTableLeitoUti.getSelectedRow();
        if (i < 0) {
            Msg.alerta(this, "Selecione um Registro!");
            return;
        }
        removerLeito();
        listarLeito();
        desaabilitaCampos();
        limpar();
        btnNovo.setEnabled(true);
        btnEditar.setEnabled(false);
        btnExcluir.setEnabled(false);
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed

        if (txtLeito.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Campo Leito é obrigatório", "Atenção", JOptionPane.WARNING_MESSAGE);
            txtLeito.requestFocus();
            return;
        }
        if (txtData.getDate() == null) {
            JOptionPane.showMessageDialog(rootPane, "Campo Data é Obrigatório", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        SalvarLeitoUti();
        listarLeito();
        limpar();
        desaabilitaCampos();
        btnSalvar.setEnabled(false);
        btnNovo.setEnabled(true);
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void jTableLeitoUtiMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableLeitoUtiMouseReleased
        jTableLeitoUti.setToolTipText(" Para habilitar o botao alterar e excluir clique no item que deseja!");
    }//GEN-LAST:event_jTableLeitoUtiMouseReleased

    private void txtLeitoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtLeitoFocusGained
        txtLeito.setBackground(new Color(252, 233, 163));
    }//GEN-LAST:event_txtLeitoFocusGained

    private void txtLeitoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtLeitoFocusLost
        txtLeito.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_txtLeitoFocusLost

    private void jTableLeitoUtiKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTableLeitoUtiKeyReleased
        mostrarItensLeitosUTI();
    }//GEN-LAST:event_jTableLeitoUtiKeyReleased

    private void txtLeitoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtLeitoKeyReleased

    }//GEN-LAST:event_txtLeitoKeyReleased

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
            java.util.logging.Logger.getLogger(TelaCadastroLeitosUTI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaCadastroLeitosUTI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaCadastroLeitosUTI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaCadastroLeitosUTI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new TelaCadastroLeitosUTI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnSair;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JComboBox<String> jCStatus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableLeitoUti;
    private javax.swing.JTextField txtCodigo;
    private org.jdesktop.swingx.JXDatePicker txtData;
    private javax.swing.JTextField txtLeito;
    // End of variables declaration//GEN-END:variables

    //metodo converte data
    private java.sql.Date convertDateSalvar(java.util.Date var) throws SQLException {
        int year = var.getDate();
        int month = var.getMonth();
        int day = var.getYear();
        return new java.sql.Date(day, month, year);
    }
}
