/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.medical.screen;

import me.medical.controller.Conexao;
import me.medical.utils.Msg;
import me.medical.utils.Utils;
import java.awt.Color;
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

/**
 *
 * @author Adriano Zanette
 */
public class TelaGeralPacientesIternadosRecepcao extends javax.swing.JFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    /**
     * Creates new form TelaEntradaSaida
     */
    public TelaGeralPacientesIternadosRecepcao() {
        initComponents();
        jPanel1.setVisible(false);
        jPanel3.setVisible(false);
        jPanel6.setVisible(false);
        listarCadastro();
        pintarColumnaTabla();
        setIcon();
        conexao = Conexao.conector();
    }

    private void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("Receita.png")));
    }
    
    void pintarColumnaTabla() {
        ColorearFilasTroca color = new ColorearFilasTroca(7);
        tabelaPacientes.getColumnModel().getColumn(7).setCellRenderer(color);
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
        txtLeito.setEnabled(true);
        txtNumero.setEnabled(true);
        jCStatus.setEnabled(true);
    }

    public void desaabilitaCampos() {

        jCAcomodacao.setEnabled(false);
        txtLeito.setEnabled(false);
        txtNumero.setEnabled(false);
        jCStatus.setEnabled(false);
        
    }

    public void limpar() {
        
        
        jCAcomodacao.setSelectedIndex(0);
        txtNumero.setText("");
        txtLeito.setText("");
        txtProntuario.setText("");
        txtNomePaciente.setText("");
        txtDataInternacao.getEditor().setText("");
        jCStatus.setSelectedIndex(0);
    }
    
//void pintarColumnaTabla() {
//        view.ColorearFilasTroca color = new view.ColorearFilasTroca(7);
//        tabelaPacientes.getColumnModel().getColumn(7).setCellRenderer(color);
//    }

    public void pesquisarPacienteNome() {
        DefaultTableModel model = (DefaultTableModel) tabelaPacientes.getModel();
        model.setNumRows(0);
        //conexão com o banco de dados
        String sql = "Select *from tb_internacao where NomePaciente like ?";

        try {
            pst = Conexao.conector().prepareStatement(sql);
            pst.setString(1, txtPesquisar.getText() + "%");
            rs = pst.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("codigo"),
                    rs.getString("Prontuario"),
                    Utils.convertData(rs.getDate("DataInternacao")),
                    rs.getString("NomePaciente"),
                    rs.getString("TipoAcomodacao"),
                    rs.getString("Numero"),
                    rs.getString("Leito"),
                    rs.getString("Status"),
                    rs.getString("DataAlta"),});
            }
        } catch (SQLException error) {
            JOptionPane.showMessageDialog(null, error);
        }
    }
    
    

    protected void listarCadastro() {
        Conexao conec = new Conexao();
        DefaultTableModel model = (DefaultTableModel) tabelaPacientes.getModel();
        model.setNumRows(0);
        String sql = "Select codigo,Prontuario,DataInternacao,NomePaciente,TipoAcomodacao,Numero,Leito,Status from tb_internacao order by codigo";
        try {
            pst = Conexao.conector().prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("codigo"),
                    rs.getString("Prontuario"),
                    Utils.convertData(rs.getDate("DataInternacao")),
                    rs.getString("NomePaciente"),
                    rs.getString("TipoAcomodacao"),
                    rs.getString("Numero"),
                    rs.getString("Leito"),
                    rs.getString("Status"),});
            }
        } catch (SQLException error) {
            JOptionPane.showMessageDialog(null, error);
        } finally {
            try {
                rs.close();
                pst.close();
                conec.desconectar();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }
    
    public void mostraItens() {
        int seleciona = tabelaPacientes.getSelectedRow();
        //carrega historico

        txtCodigo.setText(tabelaPacientes.getModel().getValueAt(seleciona, 0).toString());
        txtProntuario.setText(tabelaPacientes.getModel().getValueAt(seleciona, 1).toString());
        txtDataInternacao.setDate(Utils.convertData(tabelaPacientes.getModel().getValueAt(seleciona, 2).toString()));
        txtNomePaciente.setText(tabelaPacientes.getModel().getValueAt(seleciona, 3).toString());
        jCAcomodacao.setSelectedItem(tabelaPacientes.getModel().getValueAt(seleciona, 4).toString());
        txtNumero.setText(tabelaPacientes.getModel().getValueAt(seleciona, 5).toString());
        txtLeito.setText(tabelaPacientes.getModel().getValueAt(seleciona, 6).toString());
        jCStatus.setSelectedItem(tabelaPacientes.getModel().getValueAt(seleciona, 7).toString());
    }
    
    
    
//    
//    //chamar metodo alterar e tela alterar
//    private void Alterar() {
//        // chama a tela ja com os dados
//        int index = tabelaCadastro.getSelectedRow();
//        TableModel model = tabelaCadastro.getModel();
//        String codigo = model.getValueAt(index, 0).toString();
//        String DataCadastro = model.getValueAt(index, 1).toString();
//        String Turno = model.getValueAt(index, 2).toString();
//        String Base = model.getValueAt(index, 3).toString();
//        String Viatura = model.getValueAt(index, 4).toString();
//        String Enfermagem = model.getValueAt(index, 5).toString();
//        String Condutor = model.getValueAt(index, 6).toString();
//        String TelefoneVTR = model.getValueAt(index, 7).toString();
//        String TelefonePessoal = model.getValueAt(index, 8).toString();
//        //tela.listarCadastro(Placa);//aqui chamo o metodo atualiza 
//        tela.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        tela.txtCodigo.setText(codigo);
//        tela.txtDataCadastro.getEditor().setText(DataCadastro);
//        tela.jCTurno.setSelectedItem(Turno);
//        tela.jCBase.setSelectedItem(Base);
//        tela.jCViatura.setSelectedItem(Viatura);
//        tela.txtNomeEnfermeiro.setText(Enfermagem);
//        tela.txtNomeCondutor.setText(Condutor);
//        tela.txtTelefoneVTR.setText(TelefoneVTR);
//        tela.txtTelefonePessoal.setText(TelefonePessoal);
////      tela.txtCodigo.setEnabled(false);
//        
//        tela.jCTurno.setEnabled(true);
//        tela.jCBase.setEnabled(true);
//        tela.jCViatura.setEnabled(true);
//        tela.txtNomeEnfermeiro.setEnabled(true);
//        tela.txtNomeCondutor.setEnabled(true);
//        tela.txtTelefoneVTR.setEnabled(true);
//        tela.txtTelefonePessoal.setEnabled(true);
//        tela.setVisible(true);
//        
//        //   tela.pack();
//    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtPesquisar = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        btnCadastrar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnExcluir1 = new javax.swing.JButton();
        btnLegenda = new java.awt.Button();
        jLabel2 = new javax.swing.JLabel();
        jXTitledPanel1 = new org.jdesktop.swingx.JXTitledPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaPacientes = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        txtProntuario = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        txtDataInternacao = new org.jdesktop.swingx.JXDatePicker();
        jLabel25 = new javax.swing.JLabel();
        txtNomePaciente = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jCAcomodacao = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        txtLeito = new javax.swing.JTextField();
        txtNumero = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jCStatus = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        btnImpressao = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setExtendedState(MAXIMIZED_BOTH);

        jLabel1.setText("Pesquisar:");

        txtPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPesquisarKeyReleased(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnCadastrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Create.png"))); // NOI18N
        btnCadastrar.setText("Cadastrar");
        btnCadastrar.setEnabled(false);
        btnCadastrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadastrarActionPerformed(evt);
            }
        });
        jPanel1.add(btnCadastrar);

        btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagensTelas/editar_1.png"))); // NOI18N
        btnEditar.setText("Editar");
        btnEditar.setEnabled(false);
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });
        jPanel1.add(btnEditar);

        btnExcluir1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagensTelas/excluir_1.png"))); // NOI18N
        btnExcluir1.setText("Excluir");
        btnExcluir1.setEnabled(false);
        btnExcluir1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluir1ActionPerformed(evt);
            }
        });
        jPanel1.add(btnExcluir1);

        btnLegenda.setBackground(new java.awt.Color(51, 153, 255));
        btnLegenda.setForeground(new java.awt.Color(255, 255, 255));
        btnLegenda.setLabel("Atualizar TB");
        btnLegenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLegendaActionPerformed(evt);
            }
        });

        jLabel2.setText("Paciente:");

        jXTitledPanel1.setTitle("Clique no paciente desejado para impressão de etiqueta");
        jXTitledPanel1.setTitleFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jXTitledPanel1.setTitleForeground(new java.awt.Color(51, 51, 51));

        tabelaPacientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Codigo", "Prontuário", "Data Internação", "Nome do Paciente", "Tipo de Acomodação", "Número", "Leito", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaPacientes.setToolTipText("Clique sobre a linha desejada e no botão para impressão");
        tabelaPacientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaPacientesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelaPacientes);
        if (tabelaPacientes.getColumnModel().getColumnCount() > 0) {
            tabelaPacientes.getColumnModel().getColumn(0).setMinWidth(50);
            tabelaPacientes.getColumnModel().getColumn(0).setPreferredWidth(50);
            tabelaPacientes.getColumnModel().getColumn(0).setMaxWidth(50);
            tabelaPacientes.getColumnModel().getColumn(1).setMinWidth(70);
            tabelaPacientes.getColumnModel().getColumn(1).setPreferredWidth(70);
            tabelaPacientes.getColumnModel().getColumn(1).setMaxWidth(70);
            tabelaPacientes.getColumnModel().getColumn(2).setMinWidth(100);
            tabelaPacientes.getColumnModel().getColumn(2).setPreferredWidth(100);
            tabelaPacientes.getColumnModel().getColumn(2).setMaxWidth(100);
            tabelaPacientes.getColumnModel().getColumn(4).setMinWidth(150);
            tabelaPacientes.getColumnModel().getColumn(4).setPreferredWidth(150);
            tabelaPacientes.getColumnModel().getColumn(4).setMaxWidth(150);
            tabelaPacientes.getColumnModel().getColumn(5).setMinWidth(70);
            tabelaPacientes.getColumnModel().getColumn(5).setPreferredWidth(70);
            tabelaPacientes.getColumnModel().getColumn(5).setMaxWidth(70);
            tabelaPacientes.getColumnModel().getColumn(6).setMinWidth(70);
            tabelaPacientes.getColumnModel().getColumn(6).setPreferredWidth(70);
            tabelaPacientes.getColumnModel().getColumn(6).setMaxWidth(70);
            tabelaPacientes.getColumnModel().getColumn(7).setMinWidth(120);
            tabelaPacientes.getColumnModel().getColumn(7).setPreferredWidth(120);
            tabelaPacientes.getColumnModel().getColumn(7).setMaxWidth(120);
        }

        javax.swing.GroupLayout jXTitledPanel1Layout = new javax.swing.GroupLayout(jXTitledPanel1.getContentContainer());
        jXTitledPanel1.getContentContainer().setLayout(jXTitledPanel1Layout);
        jXTitledPanel1Layout.setHorizontalGroup(
            jXTitledPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 939, Short.MAX_VALUE)
        );
        jXTitledPanel1Layout.setVerticalGroup(
            jXTitledPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE)
        );

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
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(txtNomePaciente))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

        jCStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "< Selecione >", "Internado", "Higienizando", "Manutenção", " " }));
        jCStatus.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jCStatus.setEnabled(false);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("STATUS :");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
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
                    .addComponent(txtLeito, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jLabel5)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jCStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNumero))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jCAcomodacao, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                            .addComponent(txtLeito)
                            .addComponent(jCStatus))))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        btnImpressao.setBackground(new java.awt.Color(255, 153, 0));
        btnImpressao.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnImpressao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Atestado.png"))); // NOI18N
        btnImpressao.setText("Imprimir Etiqueta");
        btnImpressao.setEnabled(false);
        btnImpressao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImpressaoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jXTitledPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPesquisar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnImpressao, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLegenda, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 943, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnLegenda, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1)
                    .addComponent(txtPesquisar, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnImpressao, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jXTitledPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        setSize(new java.awt.Dimension(979, 635));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents


    private void txtPesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisarKeyReleased
       pesquisarPacienteNome();
       
    }//GEN-LAST:event_txtPesquisarKeyReleased

    private void excluir(int codigo) {
        Conexao conec = new Conexao();
        String sql = "DELETE FROM `consultorio`.`tb_internacao` WHERE  `codigo`=" + codigo + ";";

        try {
            pst = Conexao.conector().prepareStatement(sql);
            pst.executeUpdate();
            rs.close();
            pst.close();
            conec.desconectar();
//            listarCadastro();
            Msg.informacao(this, "Excluido com Sucesso!");
        } catch (Exception error) {
            JOptionPane.showMessageDialog(null, error);
        }
    }

    private void btnLegendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLegendaActionPerformed
         listarCadastro();
    }//GEN-LAST:event_btnLegendaActionPerformed

    private void btnExcluir1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluir1ActionPerformed
        int i = tabelaPacientes.getSelectedRow();
        if (i < 0) {
            Msg.alerta(this, "Selecione um Registro!");
            tabelaPacientes.setEnabled(true);
            return;
        } else {
            if (Msg.confirmar(this, "Deseja Realmente Excluir Este Registro?")) {
                excluir(Integer.parseInt(tabelaPacientes.getValueAt(i, 0).toString()));
            }
        }
    }//GEN-LAST:event_btnExcluir1ActionPerformed

   
    
    private void tabelaPacientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaPacientesMouseClicked
           mostraItens(); 
           btnImpressao.setEnabled(true);
    }//GEN-LAST:event_tabelaPacientesMouseClicked

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
       int i = tabelaPacientes.getSelectedRow();
        if (i < 0) {
            Msg.alerta(this, "Selecione um Registro!");
            tabelaPacientes.setEnabled(true);
            return;
//        } else {
//            alterar();
//        }
    }//GEN-LAST:event_btnEditarActionPerformed
    }
    private void btnCadastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadastrarActionPerformed
        TelaCadastroLeitosPacientesInternados tela = new TelaCadastroLeitosPacientesInternados();
        tela.setVisible(true);        
    }//GEN-LAST:event_btnCadastrarActionPerformed

    private void btnImpressaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImpressaoActionPerformed
       
        final Aguarde carregando = new Aguarde();
            carregando.setVisible(true);
            Thread t = new Thread(){
                public void run(){

                    try {
                        ImpressaoEtiqueta imprime =  new ImpressaoEtiqueta();
                        List listade_dados =  GetDados();
                        imprime.Imprime_Relatorio(listade_dados);
                        carregando.dispose();
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e);
                    }
                    desaabilitaCampos();
                    limpar();
                }
            };
            t.start();
            
    }//GEN-LAST:event_btnImpressaoActionPerformed

    private void txtNomePacienteFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomePacienteFocusGained
        txtNumero.setBackground(new Color(252, 233, 163));
    }//GEN-LAST:event_txtNomePacienteFocusGained

    private void txtNomePacienteFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomePacienteFocusLost
        txtNumero.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_txtNomePacienteFocusLost

    private void txtNomePacienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNomePacienteKeyReleased

    }//GEN-LAST:event_txtNomePacienteKeyReleased

    private void txtLeitoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtLeitoFocusGained
        txtLeito.setBackground(new Color(252, 233, 163));
    }//GEN-LAST:event_txtLeitoFocusGained

    private void txtLeitoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtLeitoFocusLost
        txtLeito.setBackground(new Color(255, 255, 255));

    }//GEN-LAST:event_txtLeitoFocusLost
    
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
            java.util.logging.Logger.getLogger(TelaGeralPacientesIternadosRecepcao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaGeralPacientesIternadosRecepcao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaGeralPacientesIternadosRecepcao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaGeralPacientesIternadosRecepcao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaGeralPacientesIternadosRecepcao().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCadastrar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnExcluir1;
    private javax.swing.JButton btnImpressao;
    private java.awt.Button btnLegenda;
    private javax.swing.JComboBox<String> jCAcomodacao;
    private javax.swing.JComboBox<String> jCStatus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private org.jdesktop.swingx.JXTitledPanel jXTitledPanel1;
    private javax.swing.JTable tabelaPacientes;
    public javax.swing.JTextField txtCodigo;
    public org.jdesktop.swingx.JXDatePicker txtDataInternacao;
    private javax.swing.JTextField txtLeito;
    public javax.swing.JTextField txtNomePaciente;
    private javax.swing.JTextField txtNumero;
    private javax.swing.JTextField txtPesquisar;
    public javax.swing.JTextField txtProntuario;
    // End of variables declaration//GEN-END:variables

public List GetDados(){
    
List lista = new ArrayList(); 

//for (int i = 0; i < 10; i++) {
AuxiliarEtiqueta print =  new AuxiliarEtiqueta(); 
//print.setCodigo("");
print.setCodigo(txtCodigo.getText());
print.setProntuario(txtProntuario.getText());
print.setData(txtDataInternacao.getEditor().getText());
print.setNomepaciente(txtNomePaciente.getText());
print.setTipoacomodacao(jCAcomodacao.getSelectedItem().toString());
print.setNumero(txtNumero.getText());
print.setLeito(txtLeito.getText());
lista.add(print);   
return lista;    
}


}
