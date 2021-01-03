/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.medical.screen;

import me.medical.controller.Conexao;
import me.medical.utils.Msg;
import me.medical.utils.UpperCaseField;
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
import net.proteanit.sql.DbUtils;

/**
 *
 * @author Adriano Zanette
 */
public class TelaAdcionarEvolucao extends javax.swing.JFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    /**
     * Creates new form TelaAdcionarEvolucao
     */
    public TelaAdcionarEvolucao() {
        initComponents();
        populaJComboBoxNome();
        setIcon();
        letrasemNegrito();
        conexao = Conexao.conector();
        ValidaEnter ve = new ValidaEnter();
        ve.ValidaEnterPainel(jPanel1);
        txtAreaEvolucao.setDocument(new UpperCaseField(2000));
    }

    private void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("Receita.png")));
    }

    // icone do joptionPane
    Icon icon = new javax.swing.ImageIcon(getClass().getResource("/Imagens/ok.png"));

    // icone do joptionPane
    Icon iconExcluir = new javax.swing.ImageIcon(getClass().getResource("/Imagens/Excluido.png"));

    private void limparCampos() {

        txtProntuario.setText("");
        txtNomePaciente.setText("");
        txtDataEvolucao.getEditor().setText("");
        txtAreaEvolucao.setText("");
        jcNomeResponsavel.setSelectedIndex(0);
        jtfFrequenciaCardiaca.setText("");
        jtfFrequenciaRespiratoria.setText("");
        jtfPressaoArterial.setText("");
        jtfTemperatura.setText("");
        jtfSaturacao.setText("");
        txtHGT.setText("");

    }

    // metodo para colocar a letra em negrito
    Font fonte = new Font("Arial", Font.BOLD, 12);

    private void letrasemNegrito() {

        txtNomePaciente.setFont(fonte);
        txtDataEvolucao.setFont(fonte);
        txtAreaEvolucao.setFont(fonte);
        jcNomeResponsavel.setFont(fonte);
        txtNomePaciente.setFont(fonte);
        jtfFrequenciaCardiaca.setFont(fonte);
        jtfFrequenciaRespiratoria.setFont(fonte);
        jtfPressaoArterial.setFont(fonte);
        jtfTemperatura.setFont(fonte);
        jtfSaturacao.setFont(fonte);
        txtHGT.setFont(fonte);

    }

    public void listarNomePaciente() {
        Conexao conec = new Conexao();
        String sql = "Select NomePaciente from tb_anamnese order by codigo Asc";
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

    public void SalvarEvolucao() {
        Conexao conec = new Conexao();
        String sql = "insert into tb_evolucao(Prontuario,NomePaciente,DataCadastro,Evolucao,FrequenciaCardiaca,FrequenciaRespiratoria,Hgt,PressaoArterial,Temperatura,Saturacao,Responsavel)values(?,?,?,?,?,?,?,?,?,?,?)";

        try {
            pst = Conexao.conector().prepareStatement(sql);
            pst.setString(1, txtProntuario.getText());
            pst.setString(2, txtNomePaciente.getText());
            pst.setDate(3, convertDateSalvar(txtDataEvolucao.getDate()));
            pst.setString(4, txtAreaEvolucao.getText());
            pst.setString(5, jtfFrequenciaCardiaca.getText());
            pst.setString(6, jtfFrequenciaRespiratoria.getText());
            pst.setString(7, txtHGT.getText());
            pst.setString(8, jtfPressaoArterial.getText());
            pst.setString(9, jtfTemperatura.getText());
            pst.setString(10, jtfSaturacao.getText());
            pst.setString(11, jcNomeResponsavel.getSelectedItem().toString());
            pst.execute();
            JOptionPane.showMessageDialog(rootPane, " Evolução " + txtNomePaciente.getText().toUpperCase() + " salvo com sucesso.", "Inclusão", JOptionPane.INFORMATION_MESSAGE, icon);
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
        jLabel11 = new javax.swing.JLabel();
        jtfHGT = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        txtNomePaciente = new javax.swing.JTextField();
        txtDataEvolucao = new org.jdesktop.swingx.JXDatePicker();
        jLabel4 = new javax.swing.JLabel();
        txtProntuario = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jtfFrequenciaCardiaca = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jtfFrequenciaRespiratoria = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jtfTemperatura = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtHGT = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jtfPressaoArterial = new javax.swing.JFormattedTextField();
        jLabel14 = new javax.swing.JLabel();
        jtfSaturacao = new javax.swing.JFormattedTextField();
        jXTitledPanel1 = new org.jdesktop.swingx.JXTitledPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAreaEvolucao = new javax.swing.JTextArea();
        jcNomeResponsavel = new javax.swing.JComboBox<>();

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

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel11.setText("HGT/Glicemia  :");

        jtfHGT.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jtfHGT.setEnabled(false);
        jtfHGT.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtfHGTFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtfHGTFocusLost(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Evolução");
        setResizable(false);

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel16.setText("NOME DO PACIENTE:");

        txtNomePaciente.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtNomePaciente.setEnabled(false);

        txtDataEvolucao.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("DATA DA EVOLUÇÃO:");

        txtProntuario.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtProntuario.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtProntuario.setEnabled(false);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setText("PRONTUÁRIO");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(txtProntuario, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtNomePaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 402, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtDataEvolucao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(0, 32, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 6, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDataEvolucao, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtProntuario, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNomePaciente))))
                .addContainerGap())
        );

        jButton1.setBackground(new java.awt.Color(102, 153, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Create.png"))); // NOI18N
        jButton1.setText("Gravar Evolução");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel17.setText("Responsável pela Evolução");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Sinais Vitais", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jtfFrequenciaCardiaca.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtfFrequenciaCardiaca.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jtfFrequenciaCardiaca.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtfFrequenciaCardiacaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtfFrequenciaCardiacaFocusLost(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setText("FC");

        jtfFrequenciaRespiratoria.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtfFrequenciaRespiratoria.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jtfFrequenciaRespiratoria.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtfFrequenciaRespiratoriaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtfFrequenciaRespiratoriaFocusLost(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("FR");

        jtfTemperatura.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtfTemperatura.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jtfTemperatura.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtfTemperaturaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtfTemperaturaFocusLost(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel10.setText("Temperatura");

        txtHGT.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtHGT.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtHGT.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtHGTFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtHGTFocusLost(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel12.setText("HGT ");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel13.setText("Saturação O² ");

        jtfPressaoArterial.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        try {
            jtfPressaoArterial.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###x##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jtfPressaoArterial.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtfPressaoArterial.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jtfPressaoArterial.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtfPressaoArterialFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtfPressaoArterialFocusLost(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel14.setText("Pressão Arterial");

        jtfSaturacao.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        try {
            jtfSaturacao.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("0##%")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jtfSaturacao.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtfSaturacao.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jtfSaturacao.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtfSaturacaoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtfSaturacaoFocusLost(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfFrequenciaCardiaca, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jtfFrequenciaRespiratoria, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)
                        .addComponent(jtfTemperatura, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21)
                        .addComponent(txtHGT, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jtfSaturacao, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jtfPressaoArterial, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(213, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel10)
                        .addComponent(jLabel2)
                        .addComponent(jLabel5))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel14)
                        .addComponent(jLabel13)
                        .addComponent(jLabel12)))
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtfFrequenciaCardiaca, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jtfTemperatura, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jtfFrequenciaRespiratoria, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtHGT, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jtfSaturacao, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jtfPressaoArterial, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jXTitledPanel1.setTitle("Descreva a Evolução");

        txtAreaEvolucao.setColumns(20);
        txtAreaEvolucao.setRows(5);
        txtAreaEvolucao.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtAreaEvolucaoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtAreaEvolucaoFocusLost(evt);
            }
        });
        jScrollPane1.setViewportView(txtAreaEvolucao);

        javax.swing.GroupLayout jXTitledPanel1Layout = new javax.swing.GroupLayout(jXTitledPanel1.getContentContainer());
        jXTitledPanel1.getContentContainer().setLayout(jXTitledPanel1Layout);
        jXTitledPanel1Layout.setHorizontalGroup(
            jXTitledPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTitledPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 680, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jXTitledPanel1Layout.setVerticalGroup(
            jXTitledPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
        );

        jcNomeResponsavel.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "< Selecione seu NOME >" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jXTitledPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jcNomeResponsavel, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(120, 120, 120)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jXTitledPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(jLabel17)
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jcNomeResponsavel, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(720, 600));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void TabelaNomePacienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabelaNomePacienteMouseClicked
        // seleciona a linha da tabela e seta nos campos
        int linha = TabelaNomePaciente.getSelectedRow();
        if (linha >= 0) {
            // numero da coluna da tabelinha
            txtNomePaciente.setText(TabelaNomePaciente.getValueAt(linha, 0).toString());
            LUPA_NOME_PACIENTE.setVisible(false);
        }
    }//GEN-LAST:event_TabelaNomePacienteMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        if (!verificaSalvar()) {
            SalvarEvolucao();
            limparCampos();
            dispose();
        }


    }//GEN-LAST:event_jButton1ActionPerformed

    private void jtfFrequenciaCardiacaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfFrequenciaCardiacaFocusGained
        jtfFrequenciaCardiaca.setBackground(new Color(252, 233, 163));
    }//GEN-LAST:event_jtfFrequenciaCardiacaFocusGained

    private void jtfFrequenciaCardiacaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfFrequenciaCardiacaFocusLost
        jtfFrequenciaCardiaca.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_jtfFrequenciaCardiacaFocusLost

    private void jtfFrequenciaRespiratoriaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfFrequenciaRespiratoriaFocusGained
        jtfFrequenciaRespiratoria.setBackground(new Color(252, 233, 163));
    }//GEN-LAST:event_jtfFrequenciaRespiratoriaFocusGained

    private void jtfFrequenciaRespiratoriaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfFrequenciaRespiratoriaFocusLost
        jtfFrequenciaRespiratoria.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_jtfFrequenciaRespiratoriaFocusLost

    private void jtfTemperaturaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfTemperaturaFocusGained
        jtfTemperatura.setBackground(new Color(252, 233, 163));
    }//GEN-LAST:event_jtfTemperaturaFocusGained

    private void jtfTemperaturaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfTemperaturaFocusLost
        jtfTemperatura.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_jtfTemperaturaFocusLost

    private void jtfHGTFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfHGTFocusGained
        jtfHGT.setBackground(new Color(252, 233, 163));
    }//GEN-LAST:event_jtfHGTFocusGained

    private void jtfHGTFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfHGTFocusLost
        jtfHGT.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_jtfHGTFocusLost

    private void txtHGTFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtHGTFocusGained
        jtfHGT.setBackground(new Color(252, 233, 163));
    }//GEN-LAST:event_txtHGTFocusGained

    private void txtHGTFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtHGTFocusLost
        jtfHGT.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_txtHGTFocusLost

    private void jtfPressaoArterialFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfPressaoArterialFocusGained
        jtfPressaoArterial.setBackground(new Color(252, 233, 163));
    }//GEN-LAST:event_jtfPressaoArterialFocusGained

    private void jtfPressaoArterialFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfPressaoArterialFocusLost
        jtfPressaoArterial.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_jtfPressaoArterialFocusLost

    private void txtAreaEvolucaoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtAreaEvolucaoFocusGained
        txtAreaEvolucao.setBackground(new Color(252, 233, 163));
    }//GEN-LAST:event_txtAreaEvolucaoFocusGained

    private void txtAreaEvolucaoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtAreaEvolucaoFocusLost
        txtAreaEvolucao.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_txtAreaEvolucaoFocusLost

    private void jtfSaturacaoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfSaturacaoFocusGained
        jtfSaturacao.setBackground(new Color(252, 233, 163));
    }//GEN-LAST:event_jtfSaturacaoFocusGained

    private void jtfSaturacaoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfSaturacaoFocusLost
        jtfSaturacao.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_jtfSaturacaoFocusLost

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
            java.util.logging.Logger.getLogger(TelaAdcionarEvolucao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaAdcionarEvolucao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaAdcionarEvolucao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaAdcionarEvolucao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaAdcionarEvolucao().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog LUPA_NOME_PACIENTE;
    private javax.swing.JTable TabelaNomePaciente;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private org.jdesktop.swingx.JXTitledPanel jXTitledPanel1;
    private javax.swing.JComboBox<String> jcNomeResponsavel;
    public javax.swing.JTextField jtfFrequenciaCardiaca;
    public javax.swing.JTextField jtfFrequenciaRespiratoria;
    public javax.swing.JTextField jtfHGT;
    public javax.swing.JFormattedTextField jtfPressaoArterial;
    private javax.swing.JFormattedTextField jtfSaturacao;
    public javax.swing.JTextField jtfTemperatura;
    private javax.swing.JTextArea txtAreaEvolucao;
    public org.jdesktop.swingx.JXDatePicker txtDataEvolucao;
    public javax.swing.JTextField txtHGT;
    public javax.swing.JTextField txtNomePaciente;
    public javax.swing.JTextField txtProntuario;
    // End of variables declaration//GEN-END:variables

    private boolean verificaSalvar() {
        boolean valor = false;
        String mensagem = "Campos Obrigatórios!\n";
        if (txtDataEvolucao.getDate() == null) {
            mensagem += "Data Evolução\n";
            valor = true;
        }

        if (txtAreaEvolucao.getText().equals("")) {
            mensagem += "Evolução\n";
            valor = true;
        }

        if (txtNomePaciente.getText().equals("")) {
            mensagem += "NomePaciente\n";
            valor = true;
        }

        if (jtfFrequenciaCardiaca.getText().equals("")) {
            mensagem += "FrequenciaCardiaca\n";
            valor = true;
        }

        if (jtfFrequenciaRespiratoria.getText().equals("")) {
            mensagem += "FrequenciaRespiratoria\n";
            valor = true;
        }

        if (txtHGT.getText().equals("")) {
            mensagem += "HGT\n";
            valor = true;
        }

        if (jtfPressaoArterial.getText().equals("")) {
            mensagem += "Pressão Aterial\n";
            valor = true;
        }

        if (jtfTemperatura.getText().equals("")) {
            mensagem += "Temperatura\n";
            valor = true;
        }

        if (jtfSaturacao.getText().equals("")) {
            mensagem += "Saturacao\n";
            valor = true;
        }
        
        if (jcNomeResponsavel.getSelectedItem().equals("")) {
            mensagem += "Responsavel Evolução\n";
            valor = true;
        }

        if (valor) {
            Msg.erro(this, mensagem);
        }
        return valor;
    }
    
    
    public void populaJComboBoxNome() {
        String sql = "Select *from usuarios";
        try {
            pst = Conexao.conector().prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                jcNomeResponsavel.addItem(rs.getString("Login"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }

    }
    

}
