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
public class TelaAlterarLeitosPacientesInternados extends javax.swing.JDialog {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    private TelaGeralPacientesIternadosEnfermagem telaListagem;

    /**
     * Creates new form TelaCadastro
     */
    public TelaAlterarLeitosPacientesInternados(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
//        ValidaEnter ve = new ValidaEnter();
//        ve.ValidaEnterPainel(jPanel6);
        setIcon();
        txtProntuario.requestFocus();
        letrasemNegrito();
        conexao = Conexao.conector();
        letrasMaiuculas();
        btnleitosUti.setVisible(true);
        btnleitosEnfermaria.setVisible(false);
        btnleitosApartamentos.setVisible(false);
    }

    public TelaAlterarLeitosPacientesInternados(TelaGeralPacientesIternadosEnfermagem parent, boolean modal) {
        super(parent, modal);
        initComponents();
        txtProntuario.requestFocus();
//        ValidaEnter ve = new ValidaEnter();
//        ve.ValidaEnterPainel(jPanel6);
        this.telaListagem = parent;//implementado aqui para poder atualizar
        setIcon();
        letrasemNegrito();
        conexao = Conexao.conector();
        letrasMaiuculas();
    }
    
    
    void pintarColumnaTabla() {
        ColorearFilas_1 color = new ColorearFilas_1(1);
        TabelaLeitos.getColumnModel().getColumn(1).setCellRenderer(color);
        TabelaLeitosEnfermaria.getColumnModel().getColumn(1).setCellRenderer(color);
        TabelaLeitoApartamento.getColumnModel().getColumn(1).setCellRenderer(color);
    }
    
    
    
    

    private void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("Receita.png")));
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
        txtProntuario.setFont(fonte);
    }

    public void habilitaCampos() {

        jCAcomodacao.setEnabled(true);
        txtLeito.setEnabled(true);
        txtNumero.setEnabled(true);

    }

    public void desaabilitaCampos() {

        jCAcomodacao.setEnabled(false);
        txtLeito.setEnabled(false);
        txtNumero.setEnabled(false);

    }

    public void limpar() {

        jCAcomodacao.setSelectedIndex(0);
        txtNumero.setText("");
        txtLeito.setText("");
//        txtProntuario.setText("");
//        txtNomePaciente.setText("");
//        txtDataInternacao.getEditor().setText("");

    }

    private void EditarCadastro() {
        Conexao conec = new Conexao();
        String sql = "update tb_internacao set TipoAcomodacao=?,Numero=?,Leito=? where Prontuario=?";

        try {
            pst = Conexao.conector().prepareStatement(sql);
            pst.setString(1, jCAcomodacao.getSelectedItem().toString());
            pst.setString(2, txtNumero.getText());
            pst.setString(3, txtLeito.getText());
            pst.setInt(4, Integer.parseInt(txtProntuario.getText()));
            pst.execute();
            JOptionPane.showMessageDialog(rootPane, " Dados do Leito " + txtLeito.getText() + " Alterado com sucesso.", "Inclusão", JOptionPane.INFORMATION_MESSAGE, icon);
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

    public void listarLeitosUTI() {
        Conexao conec = new Conexao();
        DefaultTableModel model = (DefaultTableModel) TabelaLeitos.getModel();
        model.setNumRows(0);
        String sql = "Select leito,status from tb_leitosUti order by codigo Asc";
        try {
            pst = Conexao.conector().prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("leito"),
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

    public void listarLeitosEnfermaria() {
        Conexao conec = new Conexao();
        DefaultTableModel model = (DefaultTableModel) TabelaLeitosEnfermaria.getModel();
        model.setNumRows(0);
        String sql = "Select leito,status from tb_leitosenfermaria order by codigo Asc";
        try {
            pst = Conexao.conector().prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("leito"),
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

    public void listarLeitosApartamentos() {
        Conexao conec = new Conexao();
        DefaultTableModel model = (DefaultTableModel) TabelaLeitoApartamento.getModel();
        model.setNumRows(0);
        String sql = "Select leito,status from tb_leitosapartamento order by codigo Asc";
        try {
            pst = Conexao.conector().prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("leito"),
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
        LUPA_LEITOS_UTI = new javax.swing.JDialog();
        jScrollPane1 = new javax.swing.JScrollPane();
        TabelaLeitos = new javax.swing.JTable();
        LUPA_LEITOS_ENFERMARIA = new javax.swing.JDialog();
        jScrollPane2 = new javax.swing.JScrollPane();
        TabelaLeitosEnfermaria = new javax.swing.JTable();
        LUPA_LEITOS_APARTAMENTOS = new javax.swing.JDialog();
        jScrollPane4 = new javax.swing.JScrollPane();
        TabelaLeitoApartamento = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        txtProntuario = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jCAcomodacao = new javax.swing.JComboBox<>();
        lblAcomodacao = new javax.swing.JLabel();
        txtNumero = new javax.swing.JTextField();
        lblNumero = new javax.swing.JLabel();
        txtLeito = new javax.swing.JTextField();
        lblLeito = new javax.swing.JLabel();
        btnleitosUti = new javax.swing.JButton();
        btnleitosEnfermaria = new javax.swing.JButton();
        btnleitosApartamentos = new javax.swing.JButton();

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

        LUPA_LEITOS_UTI.setTitle("Leitos UTI");
        LUPA_LEITOS_UTI.setModal(true);

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

        javax.swing.GroupLayout LUPA_LEITOS_UTILayout = new javax.swing.GroupLayout(LUPA_LEITOS_UTI.getContentPane());
        LUPA_LEITOS_UTI.getContentPane().setLayout(LUPA_LEITOS_UTILayout);
        LUPA_LEITOS_UTILayout.setHorizontalGroup(
            LUPA_LEITOS_UTILayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LUPA_LEITOS_UTILayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 491, Short.MAX_VALUE)
                .addContainerGap())
        );
        LUPA_LEITOS_UTILayout.setVerticalGroup(
            LUPA_LEITOS_UTILayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LUPA_LEITOS_UTILayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        LUPA_LEITOS_ENFERMARIA.setTitle("Leitos de Enfermaria");
        LUPA_LEITOS_ENFERMARIA.setModal(true);

        TabelaLeitosEnfermaria.setModel(new javax.swing.table.DefaultTableModel(
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
        TabelaLeitosEnfermaria.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabelaLeitosEnfermariaMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(TabelaLeitosEnfermaria);

        javax.swing.GroupLayout LUPA_LEITOS_ENFERMARIALayout = new javax.swing.GroupLayout(LUPA_LEITOS_ENFERMARIA.getContentPane());
        LUPA_LEITOS_ENFERMARIA.getContentPane().setLayout(LUPA_LEITOS_ENFERMARIALayout);
        LUPA_LEITOS_ENFERMARIALayout.setHorizontalGroup(
            LUPA_LEITOS_ENFERMARIALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LUPA_LEITOS_ENFERMARIALayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 491, Short.MAX_VALUE)
                .addContainerGap())
        );
        LUPA_LEITOS_ENFERMARIALayout.setVerticalGroup(
            LUPA_LEITOS_ENFERMARIALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LUPA_LEITOS_ENFERMARIALayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        LUPA_LEITOS_APARTAMENTOS.setTitle("Leitos de Apartamentos");
        LUPA_LEITOS_APARTAMENTOS.setModal(true);

        TabelaLeitoApartamento.setModel(new javax.swing.table.DefaultTableModel(
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
        TabelaLeitoApartamento.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabelaLeitoApartamentoMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(TabelaLeitoApartamento);

        javax.swing.GroupLayout LUPA_LEITOS_APARTAMENTOSLayout = new javax.swing.GroupLayout(LUPA_LEITOS_APARTAMENTOS.getContentPane());
        LUPA_LEITOS_APARTAMENTOS.getContentPane().setLayout(LUPA_LEITOS_APARTAMENTOSLayout);
        LUPA_LEITOS_APARTAMENTOSLayout.setHorizontalGroup(
            LUPA_LEITOS_APARTAMENTOSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LUPA_LEITOS_APARTAMENTOSLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 491, Short.MAX_VALUE)
                .addContainerGap())
        );
        LUPA_LEITOS_APARTAMENTOSLayout.setVerticalGroup(
            LUPA_LEITOS_APARTAMENTOSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LUPA_LEITOS_APARTAMENTOSLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Tela Cadastrar Acomodação/Alta Hospitalar");

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

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel24.setText("PRONTUARIO");

        jCAcomodacao.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "< Selecione >", "UTI", "Enfermaria", "Apartamento" }));
        jCAcomodacao.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jCAcomodacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCAcomodacaoActionPerformed(evt);
            }
        });

        lblAcomodacao.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblAcomodacao.setText("TIPO DE ACOMODAÇÃO");

        txtNumero.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNumero.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtNumero.setEnabled(false);
        txtNumero.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNumeroFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNumeroFocusLost(evt);
            }
        });

        lblNumero.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblNumero.setText("NÚMERO ");

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

        lblLeito.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblLeito.setText("LEITO");

        btnleitosUti.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Create.png"))); // NOI18N
        btnleitosUti.setEnabled(false);
        btnleitosUti.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnleitosUtiActionPerformed(evt);
            }
        });

        btnleitosEnfermaria.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Create.png"))); // NOI18N
        btnleitosEnfermaria.setEnabled(false);
        btnleitosEnfermaria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnleitosEnfermariaActionPerformed(evt);
            }
        });

        btnleitosApartamentos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Create.png"))); // NOI18N
        btnleitosApartamentos.setEnabled(false);
        btnleitosApartamentos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnleitosApartamentosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 513, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtProntuario, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(jCAcomodacao, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(txtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel24)
                                .addGap(18, 18, 18)
                                .addComponent(lblAcomodacao, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtLeito)
                            .addComponent(lblLeito, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnleitosUti, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(btnleitosEnfermaria, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnleitosApartamentos, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel24)
                    .addComponent(lblAcomodacao, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblNumero)
                    .addComponent(lblLeito))
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtProntuario, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jCAcomodacao, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtLeito, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnleitosUti, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnleitosEnfermaria, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnleitosApartamentos, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(6, 6, 6)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        setSize(new java.awt.Dimension(541, 162));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtLeitoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtLeitoFocusGained
        txtLeito.setBackground(new Color(252, 233, 163));
    }//GEN-LAST:event_txtLeitoFocusGained

    private void txtLeitoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtLeitoFocusLost
        txtLeito.setBackground(new Color(255, 255, 255));

    }//GEN-LAST:event_txtLeitoFocusLost

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
        if (telaListagem != null) {
            telaListagem.listarCadastro();
        }
        dispose();
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnleitosUtiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnleitosUtiActionPerformed
            LUPA_LEITOS_UTI.setSize(new java.awt.Dimension(400, 200));
            LUPA_LEITOS_UTI.setLocationRelativeTo(null);
            listarLeitosUTI();
            pintarColumnaTabla();
            LUPA_LEITOS_UTI.setVisible(true);

    }//GEN-LAST:event_btnleitosUtiActionPerformed

    private void TabelaLeitosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabelaLeitosMouseClicked
        // seleciona a linha da tabela e seta nos campos
        int linha = TabelaLeitos.getSelectedRow();
        if (linha >= 0) {
            // numero da coluna da tabelinha
            txtLeito.setText(TabelaLeitos.getValueAt(linha, 0).toString());
            LUPA_LEITOS_UTI.setVisible(false);
        }
    }//GEN-LAST:event_TabelaLeitosMouseClicked

    private void txtNumeroFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumeroFocusGained
        txtNumero.setBackground(new Color(252, 233, 163));
    }//GEN-LAST:event_txtNumeroFocusGained

    private void txtNumeroFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumeroFocusLost
        txtNumero.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_txtNumeroFocusLost

    private void TabelaLeitosEnfermariaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabelaLeitosEnfermariaMouseClicked
        // seleciona a linha da tabela e seta nos campos
        int linha = TabelaLeitosEnfermaria.getSelectedRow();
        if (linha >= 0) {
            // numero da coluna da tabelinha
            txtLeito.setText(TabelaLeitosEnfermaria.getValueAt(linha, 0).toString());
            LUPA_LEITOS_ENFERMARIA.setVisible(false);
        }
    }//GEN-LAST:event_TabelaLeitosEnfermariaMouseClicked

    private void TabelaLeitoApartamentoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabelaLeitoApartamentoMouseClicked
        // seleciona a linha da tabela e seta nos campos
        int linha = TabelaLeitoApartamento.getSelectedRow();
        if (linha >= 0) {
            // numero da coluna da tabelinha
            txtLeito.setText(TabelaLeitoApartamento.getValueAt(linha, 0).toString());
            LUPA_LEITOS_APARTAMENTOS.setVisible(false);
        }
    }//GEN-LAST:event_TabelaLeitoApartamentoMouseClicked

    private void btnleitosApartamentosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnleitosApartamentosActionPerformed
        LUPA_LEITOS_APARTAMENTOS.setSize(new java.awt.Dimension(400, 200));
        LUPA_LEITOS_APARTAMENTOS.setLocationRelativeTo(null);
        listarLeitosApartamentos();
        pintarColumnaTabla();
        LUPA_LEITOS_APARTAMENTOS.setVisible(true);
    }//GEN-LAST:event_btnleitosApartamentosActionPerformed

    private void btnleitosEnfermariaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnleitosEnfermariaActionPerformed
        LUPA_LEITOS_ENFERMARIA.setSize(new java.awt.Dimension(400, 200));
        LUPA_LEITOS_ENFERMARIA.setLocationRelativeTo(null);
        listarLeitosEnfermaria();
        pintarColumnaTabla();
        LUPA_LEITOS_ENFERMARIA.setVisible(true);
    }//GEN-LAST:event_btnleitosEnfermariaActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jCAcomodacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCAcomodacaoActionPerformed
        if (jCAcomodacao.getSelectedItem().toString().equals("UTI")) {

            txtNumero.requestFocus();
            lblAcomodacao.setText("UTI");
            lblNumero.setText("NÚMERO UTI");
            lblLeito.setText("LEITO UTI");
            txtNumero.setEnabled(true);
            btnleitosUti.setEnabled(true);
            btnleitosUti.setVisible(true);
            btnleitosEnfermaria.setVisible(false);
            btnleitosApartamentos.setVisible(false);

        } else if (jCAcomodacao.getSelectedItem().toString().equals("Enfermaria")) {

            txtNumero.requestFocus();
            lblAcomodacao.setText("ENFERMARIA");
            lblNumero.setText("NÚMERO ENF");
            lblLeito.setText("LEITO ENF");
            txtNumero.setEnabled(true);
            btnleitosEnfermaria.setEnabled(true);
            btnleitosEnfermaria.setVisible(true);
            btnleitosUti.setVisible(false);
            btnleitosApartamentos.setVisible(false);

        } else if (jCAcomodacao.getSelectedItem().toString().equals("Apartamento")) {

            txtNumero.requestFocus();
            lblAcomodacao.setText("APARTAMENTO");
            lblNumero.setText("NÚMERO APTO");
            lblLeito.setText("LEITO APTO");
            txtNumero.setEnabled(true);
            btnleitosApartamentos.setEnabled(true);
            btnleitosApartamentos.setVisible(true);
            btnleitosUti.setVisible(false);
            btnleitosEnfermaria.setVisible(false);

        }
    }//GEN-LAST:event_jCAcomodacaoActionPerformed

    private void txtProntuarioFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtProntuarioFocusLost
        txtProntuario.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_txtProntuarioFocusLost

    private void txtProntuarioFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtProntuarioFocusGained
        txtProntuario.setBackground(new Color(252, 233, 163));
        txtNumero.setEnabled(true);
        jCAcomodacao.setEnabled(true);
    }//GEN-LAST:event_txtProntuarioFocusGained

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
            java.util.logging.Logger.getLogger(TelaAlterarLeitosPacientesInternados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaAlterarLeitosPacientesInternados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaAlterarLeitosPacientesInternados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaAlterarLeitosPacientesInternados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                TelaAlterarLeitosPacientesInternados dialog = new TelaAlterarLeitosPacientesInternados(new javax.swing.JFrame(), true);
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
    private javax.swing.JDialog LUPA_LEITOS_APARTAMENTOS;
    private javax.swing.JDialog LUPA_LEITOS_ENFERMARIA;
    private javax.swing.JDialog LUPA_LEITOS_UTI;
    private javax.swing.JDialog LUPA_NOME_PACIENTE;
    private javax.swing.JTable TabelaLeitoApartamento;
    private javax.swing.JTable TabelaLeitos;
    private javax.swing.JTable TabelaLeitosEnfermaria;
    private javax.swing.JTable TabelaNomePaciente;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnleitosApartamentos;
    private javax.swing.JButton btnleitosEnfermaria;
    private javax.swing.JButton btnleitosUti;
    private javax.swing.JButton jButton4;
    public javax.swing.JComboBox<String> jCAcomodacao;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lblAcomodacao;
    private javax.swing.JLabel lblLeito;
    private javax.swing.JLabel lblNumero;
    public javax.swing.JTextField txtLeito;
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
