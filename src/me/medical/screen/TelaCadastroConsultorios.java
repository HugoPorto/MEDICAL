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
public class TelaCadastroConsultorios extends javax.swing.JFrame {

    // usando a variavel conexao do DAO
    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    /**
     * Creates new form TelaUsuario
     */
    public TelaCadastroConsultorios() {
        initComponents();
        // chama classe de letras maiusculas
        txtTipoConsultorio.setDocument(new UpperCaseField(100));
        setIcon();
        //txtData.setText(horaAtual());
        ValidaEnter ve = new ValidaEnter();
        ve.ValidaEnterPainel(jPanel3);
        conexao = Conexao.conector();
        listarConsultorios();
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
        txtTipoConsultorio.setEnabled(true);
        jTableConsultorios.setEnabled(true);
        txtData.setEnabled(true);
    }

    private void desaabilitaCampos() {
        txtTipoConsultorio.setEnabled(false);
        jTableConsultorios.setEnabled(false);
        txtData.setEnabled(false);
    }

    // metodo para colocar a letra em negrito
    Font fonte = new Font("Arial", Font.BOLD, 12);

    private void letrasemNegrito() {
        txtData.setFont(fonte);
        txtTipoConsultorio.setFont(fonte);
        txtData.setFont(fonte);

    }

    private void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("Receita.png")));
    }
    
     // icone do joptionPane
    Icon icon = new javax.swing.ImageIcon(getClass().getResource("/Imagens/ok.png"));
    
    
    // icone do joptionPane
    Icon iconExcluir = new javax.swing.ImageIcon(getClass().getResource("/Imagens/Excluido.png"));
    
    
    
    
    

    public void listarConsultorios() {
        Conexao conec = new Conexao();
        DefaultTableModel model = (DefaultTableModel) jTableConsultorios.getModel();
        model.setNumRows(0);
        String sql = "Select codigo,DataCadastro,tipoConsultorio from tb_consultorios order by codigo";
        try {
            pst = Conexao.conector().prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("codigo"),
                    Utils.convertData(rs.getDate("DataCadastro")),
                    rs.getString("tipoConsultorio"),});
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

    public void SalvarConvenio() {
        Conexao conec = new Conexao();
        String sql = "insert into tb_consultorios(DataCadastro,tipoConsultorio)values(?,?)";

        try {
            pst = Conexao.conector().prepareStatement(sql);
            pst.setDate(1, convertDateSalvar(txtData.getDate()));
            pst.setString(2, txtTipoConsultorio.getText());
            pst.execute();
            JOptionPane.showMessageDialog(rootPane, " Consultório " + txtTipoConsultorio.getText().toUpperCase() + " salvo com sucesso.", "Inclusão", JOptionPane.INFORMATION_MESSAGE,icon);
            listarConsultorios();
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

    public void alterarConvenio() {
        Conexao conec = new Conexao();
        String sql = "update tb_consultorios set DataCadastro=?,tipoConsultorio=? where codigo=?";

        try {
            pst = Conexao.conector().prepareStatement(sql);
            pst.setDate(1, convertDateSalvar(txtData.getDate()));
            pst.setString(2, txtTipoConsultorio.getText());
            pst.setInt(3, Integer.parseInt(txtCodigo.getText()));
            pst.executeUpdate();
            JOptionPane.showMessageDialog(rootPane, " Consultório " + txtTipoConsultorio.getText().toUpperCase() + " Atualizado com sucesso.", "Alteração", JOptionPane.INFORMATION_MESSAGE,icon);
            listarConsultorios();
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

    public void removerConsultorio() {
        Conexao conec = new Conexao();
        String sql = "delete from tb_consultorios where codigo=?";

        try {
            pst = Conexao.conector().prepareStatement(sql);
            pst.setInt(1, Integer.parseInt(txtCodigo.getText()));
            pst.execute();
            JOptionPane.showMessageDialog(rootPane, " Consultório " + txtTipoConsultorio.getText().toUpperCase() + " Excluido com sucesso.", "Exclusão", JOptionPane.INFORMATION_MESSAGE,iconExcluir);
            listarConsultorios();
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

    public void mostrarItens() {
        int seleciona = jTableConsultorios.getSelectedRow();
        txtCodigo.setText(jTableConsultorios.getModel().getValueAt(seleciona, 0).toString());
        txtData.setDate(Utils.convertData(jTableConsultorios.getModel().getValueAt(seleciona, 1).toString()));
        txtTipoConsultorio.setText(jTableConsultorios.getModel().getValueAt(seleciona, 2).toString());
    }

    private void limpar() {
        txtCodigo.setText("");
        txtTipoConsultorio.setText("");
        txtData.getEditor().setText("");
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
        txtTipoConsultorio = new javax.swing.JTextField();
        txtData = new org.jdesktop.swingx.JXDatePicker();
        jPanel8 = new javax.swing.JPanel();
        btnNovo = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableConsultorios = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastro Consultorios");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setText("Cadastro de Consultórios");

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

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel24.setText("ID:");

        txtCodigo.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtCodigo.setEnabled(false);

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel25.setText("DATA CADASTRO:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("TIPO CONSULTÓRIO:");

        txtTipoConsultorio.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        txtTipoConsultorio.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtTipoConsultorio.setEnabled(false);
        txtTipoConsultorio.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtTipoConsultorioFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTipoConsultorioFocusLost(evt);
            }
        });
        txtTipoConsultorio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTipoConsultorioKeyReleased(evt);
            }
        });

        txtData.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtData.setEnabled(false);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTipoConsultorio)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel24)
                                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel25)
                                    .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(jLabel25))
                .addGap(5, 5, 5)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTipoConsultorio, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {txtCodigo, txtTipoConsultorio});

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

        jTableConsultorios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Data Cadastro", "Tipo Consultorio"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableConsultorios.setEnabled(false);
        jTableConsultorios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableConsultoriosMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTableConsultoriosMouseReleased(evt);
            }
        });
        jTableConsultorios.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTableConsultoriosKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jTableConsultorios);

        jPanel4.setBackground(new java.awt.Color(51, 51, 51));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 615, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 8, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        setSize(new java.awt.Dimension(651, 534));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
        //Pergunta se o usuário deseja realmente sair do sistema
        int sair = JOptionPane.showConfirmDialog(null, "DESEJA SAIR DA TELA CONSULTÓRIOS ?", "ATENÇÃO", JOptionPane.YES_NO_OPTION);
        if (sair == JOptionPane.YES_OPTION) {
            dispose();
        }
    }//GEN-LAST:event_btnSairActionPerformed

    private void jTableConsultoriosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableConsultoriosMouseClicked
        mostrarItens();
        habilitaCampos();
        btnEditar.setEnabled(true);
        btnExcluir.setEnabled(true);
        btnSalvar.setEnabled(false);
        btnNovo.setEnabled(false);

    }//GEN-LAST:event_jTableConsultoriosMouseClicked

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed
        txtData.requestFocus();
        btnEditar.setEnabled(false);
        btnExcluir.setEnabled(false);
        btnSalvar.setEnabled(true);
        habilitaCampos();
        limpar();
    }//GEN-LAST:event_btnNovoActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        int i = jTableConsultorios.getSelectedRow();
        if (i < 0) {
            Msg.alerta(this, "Selecione um Registro!");
            return;
        }
        alterarConvenio();
        listarConsultorios();
        desaabilitaCampos();
        limpar();
        btnNovo.setEnabled(true);
        btnEditar.setEnabled(false);
        btnExcluir.setEnabled(false);
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        int i = jTableConsultorios.getSelectedRow();
        if (i < 0) {
            Msg.alerta(this, "Selecione um Registro!");
            return;
        }
        removerConsultorio();
        listarConsultorios();
        desaabilitaCampos();
        limpar();
        btnNovo.setEnabled(true);
        btnEditar.setEnabled(false);
        btnExcluir.setEnabled(false);
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        if (txtTipoConsultorio.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Campo Tipo Consultório é obrigatório", "Atenção", JOptionPane.WARNING_MESSAGE);
            txtTipoConsultorio.requestFocus();
            return;
        }
        if (txtData.getDate() == null) {
            JOptionPane.showMessageDialog(rootPane, "Campo Data é Obrigatório", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        SalvarConvenio();
        listarConsultorios();
        limpar();
        desaabilitaCampos();
        btnSalvar.setEnabled(false);
        btnNovo.setEnabled(true);
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void jTableConsultoriosMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableConsultoriosMouseReleased
        jTableConsultorios.setToolTipText(" Para habilitar o botao alterar e excluir clique no item que deseja!");
    }//GEN-LAST:event_jTableConsultoriosMouseReleased

    private void txtTipoConsultorioFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTipoConsultorioFocusGained
        txtTipoConsultorio.setBackground(new Color(252, 233, 163));
    }//GEN-LAST:event_txtTipoConsultorioFocusGained

    private void txtTipoConsultorioFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTipoConsultorioFocusLost
        txtTipoConsultorio.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_txtTipoConsultorioFocusLost

    private void jTableConsultoriosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTableConsultoriosKeyReleased
        mostrarItens();
    }//GEN-LAST:event_jTableConsultoriosKeyReleased

    private void txtTipoConsultorioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTipoConsultorioKeyReleased
//        //campo aceita somente letras
//        txtTipoConsultorio.setText(txtTipoConsultorio.getText().replaceAll("[^A-Z]", ""));
    }//GEN-LAST:event_txtTipoConsultorioKeyReleased

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
            java.util.logging.Logger.getLogger(TelaCadastroConsultorios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaCadastroConsultorios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaCadastroConsultorios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaCadastroConsultorios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new TelaCadastroConsultorios().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnSair;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableConsultorios;
    private javax.swing.JTextField txtCodigo;
    private org.jdesktop.swingx.JXDatePicker txtData;
    private javax.swing.JTextField txtTipoConsultorio;
    // End of variables declaration//GEN-END:variables

    //metodo converte data
    private java.sql.Date convertDateSalvar(java.util.Date var) throws SQLException {
        int year = var.getDate();
        int month = var.getMonth();
        int day = var.getYear();
        return new java.sql.Date(day, month, year);
    }
}
