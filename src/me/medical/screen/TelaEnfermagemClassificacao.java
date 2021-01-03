/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.medical.screen;

import me.medical.controller.Conexao;
import me.medical.utils.Msg;
import me.medical.utils.Singleton;
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
public class TelaEnfermagemClassificacao extends javax.swing.JFrame {

    String Radio_Buton = "";
    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    private TelaPacientesClassificacao pai;

    /**
     * Creates new form TelaPrincipalEnfermagem
     */
    public TelaEnfermagemClassificacao() {
        initComponents();
        this.iniciar();

    }

    public TelaEnfermagemClassificacao(TelaPacientesClassificacao pai) {
        initComponents();
        this.iniciar();
        this.pai = pai;
    }

    private void iniciar() {

        labelSala.setText("Sala: " + Singleton.getSala());
        labelUsuario.setText("Usuário: " + Singleton.getUsuario());

        conexao = Conexao.conector();
        ValidaEnter ve = new ValidaEnter();
        ve.ValidaEnterPainel(box3);
        ve.ValidaEnterPainel(jXTitledPanel1);
        setIcon();
        letrasemNegrito();
        // pular linha
        areaObs.setLineWrap(true);
        areaObs.setWrapStyleWord(true);
        areaQueixa.setLineWrap(true);
        areaQueixa.setWrapStyleWord(true);
        // caixa texto letras maiscula
        areaQueixa.setDocument(new UpperCaseField(2000));
        areaObs.setDocument(new UpperCaseField(2000));
//        
//        
//        box1.setForeground(new Color(255, 255, 255));
//        box1.setForeground(new Color(255, 255, 255));
//        box1.setForeground(new Color(255, 255, 255));
    }

    private void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("Receita.png")));
    }

    // icone do joptionPane
    Icon icon = new javax.swing.ImageIcon(getClass().getResource("/ImagensTelas/ok.png"));

    // icone do joptionPane
    Icon iconExcluir = new javax.swing.ImageIcon(getClass().getResource("/ImagensTelas/Excluido.png"));

    //--------------------------Métodos--------------------------------------
//    private void habilitaCampos() {
//
//        txtNomePaciente.setEnabled(false);
//        areaObs.setEnabled(true);
//        areaQueixa.setEnabled(true);
//        btnPesquisarPaciente.setEnabled(true);
//        txtData.setEnabled(true);
//        jtfFrequenciaCardiaca.setEnabled(true);
//        jtfFrequenciaRespiratoria.setEnabled(true);
//        jtfHGT.setEnabled(true);
//        jtfPressaoArterial.setEnabled(true);
//        jtfTemperatura.setEnabled(true);
//        jtfSaturacao.setEnabled(true);
//        txtHorarioClassificacao.setEnabled(true);
//
//    }
//
//    private void desaabilitaCampos() {
//
//        txtNomePaciente.setEnabled(false);
//        areaObs.setEnabled(false);
//        areaQueixa.setEnabled(false);
//        btnPesquisarPaciente.setEnabled(false);
//        txtData.setEnabled(false);
//        jtfFrequenciaCardiaca.setEnabled(false);
//        jtfFrequenciaRespiratoria.setEnabled(false);
//        jtfHGT.setEnabled(false);
//        jtfPressaoArterial.setEnabled(false);
//        jtfTemperatura.setEnabled(false);
//        jtfSaturacao.setEnabled(false);
//        txtHorarioClassificacao.setEnabled(false);
//
//    }
    private void limparCampos() {

        areaObs.setText("");
        areaQueixa.setText("");
        txtNomePaciente.setText("");
        txtNomePaciente.setText("");
        txtData.getEditor().setText("");
        jtfFrequenciaCardiaca.setText("");
        jtfFrequenciaRespiratoria.setText("");
        jtfHGT.setText("");
        jtfPressaoArterial.setText("");
        jtfTemperatura.setText("");
        jtfSaturacao.setText("");
        txtHorarioClassificacao.setText("");
        vermelho.setSelected(false);
        laranja.setSelected(false);
        amarelo.setSelected(false);
        verde.setSelected(false);
        azul.setSelected(false);
    }

    // metodo para colocar a letra em negrito
    Font fonte = new Font("Arial", Font.BOLD, 12);

    private void letrasemNegrito() {

        txtNomePaciente.setFont(fonte);
        areaObs.setFont(fonte);
        areaQueixa.setFont(fonte);
        txtNomePaciente.setFont(fonte);
        txtData.setFont(fonte);
        jtfFrequenciaCardiaca.setFont(fonte);
        jtfFrequenciaRespiratoria.setFont(fonte);
        jtfHGT.setFont(fonte);
        jtfPressaoArterial.setFont(fonte);
        jtfTemperatura.setFont(fonte);
        jtfSaturacao.setFont(fonte);
        txtHorarioClassificacao.setFont(fonte);
    }

    public void listarNomePaciente() {
        String sql = "Select NomePaciente from tbpaciente order by idPaciente Asc";
        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            TabelaNomePaciente.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (SQLException error) {
            JOptionPane.showMessageDialog(null, error);

        }
    }

    private void SalvarClassificacao() {
        if (vermelho.isSelected()) {
            Radio_Buton = "Vermelho - Emergência";
        }

        if (laranja.isSelected()) {
            Radio_Buton = "Laranja - Muito Urgente";
        }

        if (amarelo.isSelected()) {
            Radio_Buton = "Amarelo - Urgente";
        }

        if (verde.isSelected()) {
            Radio_Buton = "Verde - Pouco Urgente";
        }

        if (azul.isSelected()) {
            Radio_Buton = "Azul - Não Urgente";
        }

       String sql = "Insert into tb_classificacao(Data,HorarioClassificacao,NomePaciente,QueixaPrincipal,Observacao,FrequenciaCardiaca,FrequenciaRespiratoria,Hgt,PressaoArterial,Temperatura,Saturacao,ClassificacaoClinica,Prontuario)values(?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try {
            pst = Conexao.conector().prepareStatement(sql);

            pst.setDate(1, convertDateSalvar(txtData.getDate()));
            pst.setString(2, txtHorarioClassificacao.getText());
            pst.setString(3, txtNomePaciente.getText());
            pst.setString(4, areaQueixa.getText());
            pst.setString(5, areaObs.getText());
            pst.setString(6, jtfFrequenciaCardiaca.getText());
            pst.setString(7, jtfFrequenciaRespiratoria.getText());
            pst.setString(8, jtfHGT.getText());
            pst.setString(9, jtfPressaoArterial.getText());
            pst.setString(10, jtfTemperatura.getText());
            pst.setString(11, jtfSaturacao.getText());
            pst.setString(12, Radio_Buton);
            pst.setString(13, txtProntuario.getText());
            pst.execute();
            JOptionPane.showMessageDialog(rootPane, " Classificação de " + txtNomePaciente.getText().toUpperCase() + " salvo com sucesso.", "Inclusão", JOptionPane.INFORMATION_MESSAGE,icon);
        } catch (SQLException error) {
            JOptionPane.showMessageDialog(null, error);
        }
    }

    private boolean verificaSalvar() {
        boolean valor = false;
        String mensagem = "Campos Obrigatórios!\n";
        if (txtData.getDate() == null) {
            mensagem += "Data\n";
            valor = true;
        }

        if (txtHorarioClassificacao.getText().equals("")) {
            mensagem += "HorarioClassificacao\n";
            valor = true;
        }

        if (txtNomePaciente.getText().equals("")) {
            mensagem += "NomePaciente\n";
            valor = true;
        }

        if (areaQueixa.getText().equals("")) {
            mensagem += "Queixa\n";
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

        if (jtfHGT.getText().equals("")) {
            mensagem += "HGT\n";
            valor = true;
        }

        if (jtfPressaoArterial.getText().equals("")) {
            mensagem += "Data\n";
            valor = true;
        }

        if (jtfTemperatura.getText().equals("")) {
            mensagem += "PressaoArterial\n";
            valor = true;
        }

        if (jtfSaturacao.getText().equals("")) {
            mensagem += "Saturacao\n";
            valor = true;
        }

        if (valor) {
            Msg.erro(this, mensagem);
        }
        return valor;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        LUPA_NOME_PACIENTE = new javax.swing.JDialog();
        jScrollPane3 = new javax.swing.JScrollPane();
        TabelaNomePaciente = new javax.swing.JTable();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        btnSair1 = new javax.swing.JButton();
        jXTitledPanel1 = new org.jdesktop.swingx.JXTitledPanel();
        jLabel1 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        txtNomePaciente = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        btnPesquisarPaciente = new javax.swing.JButton();
        box4 = new javax.swing.JTabbedPane();
        box1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        areaQueixa = new javax.swing.JTextArea();
        box2 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jtfFrequenciaCardiaca = new javax.swing.JTextField();
        jtfFrequenciaRespiratoria = new javax.swing.JTextField();
        jtfTemperatura = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jtfPressaoArterial = new javax.swing.JFormattedTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jtfHGT = new javax.swing.JTextField();
        jtfSaturacao = new javax.swing.JFormattedTextField();
        box3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        areaObs = new javax.swing.JTextArea();
        txtHorarioClassificacao = new javax.swing.JFormattedTextField();
        txtData = new org.jdesktop.swingx.JXDatePicker();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtTipoAtendimento = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtProntuario = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        btnSalvar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        azul = new javax.swing.JRadioButton();
        verde = new javax.swing.JRadioButton();
        vermelho = new javax.swing.JRadioButton();
        amarelo = new javax.swing.JRadioButton();
        laranja = new javax.swing.JRadioButton();
        jPanel3 = new javax.swing.JPanel();
        labelUsuario = new javax.swing.JLabel();
        labelSala = new javax.swing.JLabel();

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

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 51));
        jLabel2.setText("Classificação de Risco");

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 729, Short.MAX_VALUE)
                .addComponent(btnSair1)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(btnSair1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 11, 1032, -1));

        jXTitledPanel1.setTitle("REGISTRO DA CLASSIFICAÇÃO DE RISCO");
        jXTitledPanel1.setTitleFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jXTitledPanel1.setTitleForeground(new java.awt.Color(51, 51, 51));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("Código:");

        txtCodigo.setEnabled(false);

        txtNomePaciente.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtNomePaciente.setEnabled(false);

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel16.setText("Nome Paciente:");

        btnPesquisarPaciente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Search.png"))); // NOI18N
        btnPesquisarPaciente.setEnabled(false);
        btnPesquisarPaciente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesquisarPacienteActionPerformed(evt);
            }
        });

        areaQueixa.setColumns(20);
        areaQueixa.setRows(5);
        areaQueixa.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                areaQueixaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                areaQueixaFocusLost(evt);
            }
        });
        jScrollPane1.setViewportView(areaQueixa);

        javax.swing.GroupLayout box1Layout = new javax.swing.GroupLayout(box1);
        box1.setLayout(box1Layout);
        box1Layout.setHorizontalGroup(
            box1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(box1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 981, Short.MAX_VALUE)
                .addContainerGap())
        );
        box1Layout.setVerticalGroup(
            box1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(box1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
                .addContainerGap())
        );

        box4.addTab("QUEIXA PRINCIPAL", box1);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setText("Temperatura - TEMP       :");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText("Frequência Respiratória :");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setText("Frequência Cardiaca:");

        jtfFrequenciaCardiaca.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
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
        jtfFrequenciaCardiaca.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtfFrequenciaCardiacaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtfFrequenciaCardiacaKeyReleased(evt);
            }
        });

        jtfFrequenciaRespiratoria.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
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
        jtfFrequenciaRespiratoria.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtfFrequenciaRespiratoriaKeyReleased(evt);
            }
        });

        jtfTemperatura.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
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
        jtfTemperatura.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtfTemperaturaKeyReleased(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel13.setText("Pressão Arterial :");

        jtfPressaoArterial.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        try {
            jtfPressaoArterial.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###x##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jtfPressaoArterial.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtfPressaoArterial.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jtfPressaoArterial.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtfPressaoArterialFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtfPressaoArterialFocusLost(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel12.setText("Saturação O²  :");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel9.setText("HGT/Glicemia  :");

        jtfHGT.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jtfHGT.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtfHGT.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jtfHGT.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtfHGTFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtfHGTFocusLost(evt);
            }
        });
        jtfHGT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtfHGTKeyReleased(evt);
            }
        });

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

        javax.swing.GroupLayout box2Layout = new javax.swing.GroupLayout(box2);
        box2.setLayout(box2Layout);
        box2Layout.setHorizontalGroup(
            box2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(box2Layout.createSequentialGroup()
                .addGroup(box2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(box2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel6))
                    .addGroup(box2Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jtfFrequenciaCardiaca, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(box2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(box2Layout.createSequentialGroup()
                        .addGap(74, 74, 74)
                        .addComponent(jtfFrequenciaRespiratoria, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(box2Layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addComponent(jLabel4)))
                .addGroup(box2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(box2Layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(jLabel8)
                        .addGap(47, 47, 47))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, box2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtfTemperatura, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(94, 94, 94)))
                .addGroup(box2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(jtfHGT, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 63, Short.MAX_VALUE)
                .addGroup(box2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(jtfSaturacao, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(box2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(box2Layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addComponent(jLabel13))
                    .addGroup(box2Layout.createSequentialGroup()
                        .addGap(67, 67, 67)
                        .addComponent(jtfPressaoArterial, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(73, 73, 73))
        );

        box2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jtfFrequenciaCardiaca, jtfFrequenciaRespiratoria, jtfHGT, jtfPressaoArterial, jtfTemperatura});

        box2Layout.setVerticalGroup(
            box2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, box2Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(box2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jtfFrequenciaCardiaca, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfFrequenciaRespiratoria, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(box2Layout.createSequentialGroup()
                        .addGroup(box2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9)
                            .addComponent(jLabel12)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel6))
                        .addGap(18, 18, 18)
                        .addGroup(box2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jtfHGT, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtfTemperatura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtfSaturacao, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtfPressaoArterial, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(191, 191, 191))
        );

        box2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jtfFrequenciaCardiaca, jtfFrequenciaRespiratoria, jtfHGT, jtfPressaoArterial, jtfTemperatura});

        box4.addTab("SINAIS VITAIS", box2);

        areaObs.setColumns(20);
        areaObs.setRows(5);
        areaObs.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                areaObsFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                areaObsFocusLost(evt);
            }
        });
        jScrollPane2.setViewportView(areaObs);

        javax.swing.GroupLayout box3Layout = new javax.swing.GroupLayout(box3);
        box3.setLayout(box3Layout);
        box3Layout.setHorizontalGroup(
            box3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, box3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 981, Short.MAX_VALUE)
                .addContainerGap())
        );
        box3Layout.setVerticalGroup(
            box3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(box3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        box4.addTab("OBSERVAÇÕES", box3);

        try {
            txtHorarioClassificacao.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##:##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtHorarioClassificacao.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtHorarioClassificacao.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtHorarioClassificacaoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtHorarioClassificacaoFocusLost(evt);
            }
        });
        txtHorarioClassificacao.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtHorarioClassificacaoKeyReleased(evt);
            }
        });

        txtData.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtData.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDataFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDataFocusLost(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("Horário da Classificação:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("Data Classificação:");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setText("Tipo de Atendimento:");

        txtTipoAtendimento.setBackground(new java.awt.Color(255, 255, 156));
        txtTipoAtendimento.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtTipoAtendimento.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setText("Prontuário");

        txtProntuario.setBackground(new java.awt.Color(102, 153, 255));
        txtProntuario.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtProntuario.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtProntuario.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jXTitledPanel1Layout = new javax.swing.GroupLayout(jXTitledPanel1.getContentContainer());
        jXTitledPanel1.getContentContainer().setLayout(jXTitledPanel1Layout);
        jXTitledPanel1Layout.setHorizontalGroup(
            jXTitledPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTitledPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jXTitledPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jXTitledPanel1Layout.createSequentialGroup()
                        .addComponent(box4)
                        .addContainerGap())
                    .addGroup(jXTitledPanel1Layout.createSequentialGroup()
                        .addGroup(jXTitledPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jXTitledPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jXTitledPanel1Layout.createSequentialGroup()
                                .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtNomePaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnPesquisarPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jXTitledPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtHorarioClassificacao)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jXTitledPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jXTitledPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jXTitledPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtTipoAtendimento))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jXTitledPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jXTitledPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addGap(34, 34, 34))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jXTitledPanel1Layout.createSequentialGroup()
                                .addComponent(txtProntuario, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())))))
        );
        jXTitledPanel1Layout.setVerticalGroup(
            jXTitledPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTitledPanel1Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jXTitledPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jXTitledPanel1Layout.createSequentialGroup()
                        .addGroup(jXTitledPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel16))
                        .addGap(9, 9, 9)
                        .addGroup(jXTitledPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btnPesquisarPaciente, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                            .addComponent(txtCodigo, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNomePaciente, javax.swing.GroupLayout.Alignment.LEADING)))
                    .addGroup(jXTitledPanel1Layout.createSequentialGroup()
                        .addGroup(jXTitledPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel5)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jXTitledPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtHorarioClassificacao, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtData, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                            .addComponent(txtTipoAtendimento, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtProntuario, javax.swing.GroupLayout.Alignment.LEADING))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(box4, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jXTitledPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 85, 1030, 360));

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnSalvar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnSalvar.setForeground(new java.awt.Color(51, 51, 51));
        btnSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagensTelas/Salvar.png"))); // NOI18N
        btnSalvar.setText("SALVAR");
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });
        jPanel8.add(btnSalvar, new org.netbeans.lib.awtextra.AbsoluteConstraints(896, 12, 114, 40));

        getContentPane().add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 530, 1030, 60));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Classificação Clínica", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        buttonGroup1.add(azul);
        azul.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        azul.setForeground(new java.awt.Color(51, 102, 255));
        azul.setText("Azul - Não Urgente");

        buttonGroup1.add(verde);
        verde.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        verde.setForeground(new java.awt.Color(0, 153, 0));
        verde.setText("Verde - Pouco Urgente");

        buttonGroup1.add(vermelho);
        vermelho.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        vermelho.setForeground(new java.awt.Color(255, 0, 0));
        vermelho.setText("Vermelho - Emergência");

        buttonGroup1.add(amarelo);
        amarelo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        amarelo.setForeground(new java.awt.Color(255, 255, 0));
        amarelo.setText("Amarelo - Urgente");

        buttonGroup1.add(laranja);
        laranja.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        laranja.setForeground(new java.awt.Color(255, 204, 0));
        laranja.setText("Laranja - Muito Urgente");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(vermelho)
                .addGap(34, 34, 34)
                .addComponent(laranja)
                .addGap(34, 34, 34)
                .addComponent(amarelo)
                .addGap(33, 33, 33)
                .addComponent(verde)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addComponent(azul)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(vermelho)
                    .addComponent(laranja)
                    .addComponent(amarelo)
                    .addComponent(verde)
                    .addComponent(azul))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 450, 1030, 70));

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        labelUsuario.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        labelUsuario.setForeground(new java.awt.Color(51, 51, 51));
        labelUsuario.setText("Usuário: ");

        labelSala.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        labelSala.setForeground(new java.awt.Color(51, 51, 51));
        labelSala.setText("Sala:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(labelSala, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(446, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(labelUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                .addComponent(labelSala, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 600, 1030, -1));

        setSize(new java.awt.Dimension(1064, 686));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSair1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSair1ActionPerformed

        //Pergunta se o usuário deseja realmente sair do sistema
        int sair = JOptionPane.showConfirmDialog(null, "DESEJA SAIR DA TELA?", "ATENÇÃO", JOptionPane.YES_NO_OPTION);
        if (sair == JOptionPane.YES_OPTION) {
            dispose();
        }
    }//GEN-LAST:event_btnSair1ActionPerformed

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

    private void jtfPressaoArterialFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfPressaoArterialFocusGained
        jtfPressaoArterial.setBackground(new Color(252, 233, 163));
    }//GEN-LAST:event_jtfPressaoArterialFocusGained

    private void jtfPressaoArterialFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfPressaoArterialFocusLost
        jtfPressaoArterial.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_jtfPressaoArterialFocusLost

    private void jtfHGTFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfHGTFocusGained

        jtfHGT.setBackground(new Color(252, 233, 163));


    }//GEN-LAST:event_jtfHGTFocusGained

    private void jtfHGTFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfHGTFocusLost

        jtfHGT.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_jtfHGTFocusLost

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        if (!verificaSalvar()) {
            SalvarClassificacao();
            dispose();
            limparCampos();
            if (pai != null) {
                pai.listarPacientesClassificacao();
            }
        }
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void TabelaNomePacienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabelaNomePacienteMouseClicked
        // seleciona a linha da tabela e seta nos campos
        int linha = TabelaNomePaciente.getSelectedRow();
        if (linha >= 0) {
            // numero da coluna da tabelinha
            txtNomePaciente.setText(TabelaNomePaciente.getValueAt(linha, 0).toString());
            LUPA_NOME_PACIENTE.setVisible(false);

        }
    }//GEN-LAST:event_TabelaNomePacienteMouseClicked

    private void areaObsFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_areaObsFocusGained

        areaObs.setBackground(new Color(252, 233, 163));
    }//GEN-LAST:event_areaObsFocusGained

    private void areaObsFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_areaObsFocusLost
        areaObs.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_areaObsFocusLost

    private void btnPesquisarPacienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisarPacienteActionPerformed

        LUPA_NOME_PACIENTE.setSize(new java.awt.Dimension(400, 200));
        LUPA_NOME_PACIENTE.setLocationRelativeTo(null);
        listarNomePaciente();
        LUPA_NOME_PACIENTE.setVisible(true);
        txtData.requestFocus();
    }//GEN-LAST:event_btnPesquisarPacienteActionPerformed

    private void jtfSaturacaoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfSaturacaoFocusGained
        jtfSaturacao.setBackground(new Color(252, 233, 163));
    }//GEN-LAST:event_jtfSaturacaoFocusGained

    private void jtfSaturacaoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfSaturacaoFocusLost
        jtfSaturacao.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_jtfSaturacaoFocusLost

    private void jtfFrequenciaCardiacaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfFrequenciaCardiacaKeyPressed

    }//GEN-LAST:event_jtfFrequenciaCardiacaKeyPressed

    private void jtfFrequenciaCardiacaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfFrequenciaCardiacaKeyReleased
        //campo aceita somente numeros 
        jtfFrequenciaCardiaca.setText(jtfFrequenciaCardiaca.getText().replaceAll("[^0-9]", ""));
    }//GEN-LAST:event_jtfFrequenciaCardiacaKeyReleased

    private void jtfFrequenciaRespiratoriaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfFrequenciaRespiratoriaKeyReleased
        //campo aceita somente numeros 
        jtfFrequenciaRespiratoria.setText(jtfFrequenciaRespiratoria.getText().replaceAll("[^0-9]", ""));
    }//GEN-LAST:event_jtfFrequenciaRespiratoriaKeyReleased

    private void jtfTemperaturaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfTemperaturaKeyReleased
        //campo aceita somente numeros 
        //jtfTemperatura.setText(jtfTemperatura.getText().replaceAll("[^0-9]", ""));
    }//GEN-LAST:event_jtfTemperaturaKeyReleased

    private void jtfHGTKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfHGTKeyReleased
        jtfHGT.setText(jtfHGT.getText().replaceAll("[^0-9]", ""));
    }//GEN-LAST:event_jtfHGTKeyReleased

    private void txtHorarioClassificacaoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtHorarioClassificacaoKeyReleased
        //campo aceita somente numeros 
        txtHorarioClassificacao.setText(txtHorarioClassificacao.getText().replaceAll("[^0-9]", ""));
    }//GEN-LAST:event_txtHorarioClassificacaoKeyReleased

    private void txtHorarioClassificacaoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtHorarioClassificacaoFocusGained
        txtHorarioClassificacao.setBackground(new Color(252, 233, 163));
    }//GEN-LAST:event_txtHorarioClassificacaoFocusGained

    private void txtHorarioClassificacaoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtHorarioClassificacaoFocusLost
        txtHorarioClassificacao.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_txtHorarioClassificacaoFocusLost

    private void txtDataFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDataFocusGained
        txtData.setBackground(new Color(252, 233, 163));
    }//GEN-LAST:event_txtDataFocusGained

    private void areaQueixaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_areaQueixaFocusGained
        areaQueixa.setBackground(new Color(252, 233, 163));
    }//GEN-LAST:event_areaQueixaFocusGained

    private void txtDataFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDataFocusLost
        txtData.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_txtDataFocusLost

    private void areaQueixaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_areaQueixaFocusLost
        areaQueixa.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_areaQueixaFocusLost

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
            java.util.logging.Logger.getLogger(TelaEnfermagemClassificacao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaEnfermagemClassificacao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaEnfermagemClassificacao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaEnfermagemClassificacao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaEnfermagemClassificacao().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog LUPA_NOME_PACIENTE;
    private javax.swing.JTable TabelaNomePaciente;
    private javax.swing.JRadioButton amarelo;
    private javax.swing.JTextArea areaObs;
    private javax.swing.JTextArea areaQueixa;
    private javax.swing.JRadioButton azul;
    private javax.swing.JPanel box1;
    private javax.swing.JPanel box2;
    private javax.swing.JPanel box3;
    private javax.swing.JTabbedPane box4;
    private javax.swing.JButton btnPesquisarPaciente;
    private javax.swing.JButton btnSair1;
    private javax.swing.JButton btnSalvar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private org.jdesktop.swingx.JXTitledPanel jXTitledPanel1;
    private javax.swing.JTextField jtfFrequenciaCardiaca;
    private javax.swing.JTextField jtfFrequenciaRespiratoria;
    private javax.swing.JTextField jtfHGT;
    private javax.swing.JFormattedTextField jtfPressaoArterial;
    private javax.swing.JFormattedTextField jtfSaturacao;
    private javax.swing.JTextField jtfTemperatura;
    private javax.swing.JLabel labelSala;
    private javax.swing.JLabel labelUsuario;
    private javax.swing.JRadioButton laranja;
    private javax.swing.JTextField txtCodigo;
    public org.jdesktop.swingx.JXDatePicker txtData;
    public javax.swing.JFormattedTextField txtHorarioClassificacao;
    public javax.swing.JTextField txtNomePaciente;
    public javax.swing.JTextField txtProntuario;
    public javax.swing.JTextField txtTipoAtendimento;
    private javax.swing.JRadioButton verde;
    private javax.swing.JRadioButton vermelho;
    // End of variables declaration//GEN-END:variables

//metodo converte data
    private java.sql.Date convertDateSalvar(java.util.Date var) throws SQLException {
        int year = var.getDate();
        int month = var.getMonth();
        int day = var.getYear();
        return new java.sql.Date(day, month, year);
    }

}
