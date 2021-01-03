/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.medical.screen;

import me.medical.controller.Conexao;
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
public class TelaCadastroLeitosPacientesInternados extends javax.swing.JDialog {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    private TelaGeralPacientesIternadosEnfermagem telaListagem;

    /**
     * Creates new form TelaCadastro
     */
    public TelaCadastroLeitosPacientesInternados(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        ValidaEnter ve = new ValidaEnter();
        ve.ValidaEnterPainel(jPanel6);
        setIcon();
        letrasemNegrito();
        conexao = Conexao.conector();
        letrasMaiuculas();
    }

    public TelaCadastroLeitosPacientesInternados(TelaGeralPacientesIternadosEnfermagem parent, boolean modal) {
        super(parent, modal);
        initComponents();
        ValidaEnter ve = new ValidaEnter();
        ve.ValidaEnterPainel(jPanel6);
        this.telaListagem = parent;//implementado aqui para poder atualizar
        setIcon();
        pintarColumnaTabla();
        letrasemNegrito();
        conexao = Conexao.conector();
        letrasMaiuculas();
    }

    TelaCadastroLeitosPacientesInternados() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("Receita.png")));
    }
    
    
    void pintarColumnaTabla() {
        ColorearFilas_1 color = new ColorearFilas_1(2);
        TabelaLeitos.getColumnModel().getColumn(2).setCellRenderer(color);
    }

    // icone do joptionPane
    Icon icon = new javax.swing.ImageIcon(getClass().getResource("/Imagens/ok.png"));

    // icone do joptionPane
    Icon iconExcluir = new javax.swing.ImageIcon(getClass().getResource("/Imagens/Excluido.png"));

    private void letrasMaiuculas() {

        txtNumero.setDocument(new UpperCaseField(100));
        txtLeito.setDocument(new UpperCaseField(100));
        
    }

    // metodo para colocar a letra em negrito
    Font fonte = new Font("Arial", Font.BOLD, 12);

    private void letrasemNegrito() {

        
        jCAcomodacao.setFont(fonte);
        txtLeito.setFont(fonte);
        txtNumero.setFont(fonte);
        jCStatus.setFont(fonte);
    }

    public void habilitaCampos() {

        
        jCAcomodacao.setEnabled(true);
        txtNumero.setEnabled(true);
        jCStatus.setEnabled(true);
    }

    public void desaabilitaCampos() {

        jCAcomodacao.setEnabled(false);
        txtNumero.setEnabled(false);
        jCStatus.setEnabled(false);
        
    }

    public void limpar() {
        
        
        jCAcomodacao.setSelectedIndex(0);
        txtNumero.setText("");
        txtLeito.setText("");
        jCStatus.setSelectedIndex(0);
    }
    
    
     

    private void SalvarCadastro() {
        Conexao conec = new Conexao();
        String sql = "update tb_internacao set TipoAcomodacao=?,Numero=?,Leito=?,Status=? where codigo=?";

        try {
            pst = Conexao.conector().prepareStatement(sql);
            pst.setString(1, jCAcomodacao.getSelectedItem().toString());
            pst.setString(2, txtNumero.getText());
            pst.setString(3, txtLeito.getText());
            pst.setString(4, jCStatus.getSelectedItem().toString());
            pst.setInt(5, Integer.parseInt(txtCodigo.getText()));

            pst.execute();
            JOptionPane.showMessageDialog(rootPane, " Dados do Leito " + txtLeito.getText() + " salvo com sucesso.", "Inclusão", JOptionPane.INFORMATION_MESSAGE, icon);
//            listarCadastro();

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

        LUPA_LEITOS = new javax.swing.JDialog();
        jScrollPane1 = new javax.swing.JScrollPane();
        TabelaLeitos = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        btnNovo = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnSair1 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jCAcomodacao = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        txtLeito = new javax.swing.JTextField();
        txtNumero = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jCStatus = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        btnleitos = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        txtProntuario = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        txtDataInternacao = new org.jdesktop.swingx.JXDatePicker();
        jLabel25 = new javax.swing.JLabel();
        txtNomePaciente = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();

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

        jButton1.setText("jButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Tela Cadastro Acomodação");
        getContentPane().setLayout(null);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnNovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Create.png"))); // NOI18N
        btnNovo.setText("Novo");
        btnNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoActionPerformed(evt);
            }
        });
        jPanel1.add(btnNovo);

        btnSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagensTelas/salvar_1.png"))); // NOI18N
        btnSalvar.setText("Salvar");
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });
        jPanel1.add(btnSalvar);

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagensTelas/excluir_1.png"))); // NOI18N
        jButton4.setText("Cancelar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton4);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(10, 280, 880, 39);

        jPanel4.setBackground(new java.awt.Color(51, 51, 51));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 880, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 8, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel4);
        jPanel4.setBounds(10, 70, 880, 0);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setText("Cadastro Acomodação");

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
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 573, Short.MAX_VALUE)
                .addComponent(btnSair1)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(btnSair1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2);
        jPanel2.setBounds(10, 10, 880, 0);

        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("TIPO DE ACOMODAÇÃO:");

        jCAcomodacao.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "< Selecione >", "UTI", "Enfermaria", "Apartamento" }));
        jCAcomodacao.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jCAcomodacao.setEnabled(false);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("LEITO:");

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

        txtNumero.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtNumero.setEnabled(false);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("NÚMERO:");

        jCStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "< Selecione >", "Internado", " ", " " }));
        jCStatus.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jCStatus.setEnabled(false);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("STATUS :");

        btnleitos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Create.png"))); // NOI18N
        btnleitos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnleitosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jCAcomodacao, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jLabel6))
                    .addComponent(txtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(txtLeito, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnleitos, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jLabel5)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addContainerGap(212, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNumero, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jCAcomodacao, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                            .addComponent(txtLeito)
                            .addComponent(jCStatus)
                            .addComponent(btnleitos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel6);
        jPanel6.setBounds(10, 180, 880, 90);

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel24.setText("CÓDIGO:");

        txtCodigo.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtCodigo.setEnabled(false);

        txtProntuario.setEnabled(false);

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel26.setText("PRONTUÁRIO");

        txtDataInternacao.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtDataInternacao.setEnabled(false);

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel25.setText("DATA INTERNAÇÃO");

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

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("NOME PACIENTE");

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
                    .addComponent(jLabel26)
                    .addComponent(txtProntuario, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel25)
                    .addComponent(txtDataInternacao, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(0, 418, Short.MAX_VALUE))
                    .addComponent(txtNomePaciente))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNomePaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtDataInternacao, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel24)
                                .addGap(5, 5, 5)
                                .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel26)
                                .addComponent(jLabel25))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtProntuario))))
                .addGap(12, 12, 12))
        );

        getContentPane().add(jPanel3);
        jPanel3.setBounds(10, 90, 880, 0);

        setSize(new java.awt.Dimension(912, 366));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        if (jCAcomodacao.getSelectedItem().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Campo Acomodação é obrigatório", "Atenção", JOptionPane.WARNING_MESSAGE);
            jCAcomodacao.requestFocus();
            return;
        }

        if (txtNumero.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Campo Número é obrigatório", "Atenção", JOptionPane.WARNING_MESSAGE);
            txtNumero.requestFocus();
            return;
        }
        
        if (txtLeito.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Campo Leito é obrigatório", "Atenção", JOptionPane.WARNING_MESSAGE);
            txtLeito.requestFocus();
            return;
        }
        
        
        
        SalvarCadastro();
        limpar();
        desaabilitaCampos();
        btnSalvar.setEnabled(false);
        if(telaListagem!=null){
            telaListagem.listarCadastro();
        }
        dispose();
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void txtLeitoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtLeitoFocusGained
       txtLeito.setBackground(new Color(252, 233, 163));
    }//GEN-LAST:event_txtLeitoFocusGained

    private void txtLeitoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtLeitoFocusLost
       txtLeito.setBackground(new Color(255, 255, 255));
                                             
    }//GEN-LAST:event_txtLeitoFocusLost

    private void btnSair1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSair1ActionPerformed

            dispose();
        
    }//GEN-LAST:event_btnSair1ActionPerformed

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed
        habilitaCampos();
        btnSalvar.setEnabled(true);
        limpar();
    }//GEN-LAST:event_btnNovoActionPerformed

    private void btnleitosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnleitosActionPerformed
        LUPA_LEITOS.setSize(new java.awt.Dimension(400, 200));
        LUPA_LEITOS.setLocationRelativeTo(null);
        listarLeitos();
        LUPA_LEITOS.setVisible(true);
        
    }//GEN-LAST:event_btnleitosActionPerformed

    private void TabelaLeitosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabelaLeitosMouseClicked
       // seleciona a linha da tabela e seta nos campos
        int linha = TabelaLeitos.getSelectedRow();
        if (linha >= 0) {
            // numero da coluna da tabelinha
            txtLeito.setText(TabelaLeitos.getValueAt(linha, 0).toString());
            LUPA_LEITOS.setVisible(false);
        }
    }//GEN-LAST:event_TabelaLeitosMouseClicked

    private void txtNomePacienteFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomePacienteFocusGained
        txtNumero.setBackground(new Color(252, 233, 163));
    }//GEN-LAST:event_txtNomePacienteFocusGained

    private void txtNomePacienteFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomePacienteFocusLost
        txtNumero.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_txtNomePacienteFocusLost

    private void txtNomePacienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNomePacienteKeyReleased

    }//GEN-LAST:event_txtNomePacienteKeyReleased

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
            java.util.logging.Logger.getLogger(TelaCadastroLeitosPacientesInternados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaCadastroLeitosPacientesInternados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaCadastroLeitosPacientesInternados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaCadastroLeitosPacientesInternados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
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
                TelaCadastroLeitosPacientesInternados dialog = new TelaCadastroLeitosPacientesInternados(new javax.swing.JFrame(), true);
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
    private javax.swing.JTable TabelaLeitos;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnSair1;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JButton btnleitos;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton4;
    public javax.swing.JComboBox<String> jCAcomodacao;
    public javax.swing.JComboBox<String> jCStatus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTextField txtCodigo;
    public org.jdesktop.swingx.JXDatePicker txtDataInternacao;
    public javax.swing.JTextField txtLeito;
    public javax.swing.JTextField txtNomePaciente;
    public javax.swing.JTextField txtNumero;
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
