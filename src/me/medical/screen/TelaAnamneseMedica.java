/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.medical.screen;

import me.medical.controller.Conexao;
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
import java.util.ArrayList;
import java.util.List;
import javax.swing.Icon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author Adriano Zanette
 */
public class TelaAnamneseMedica extends javax.swing.JFrame {

    String setar_dados = "";
    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    TelaPrincipalRecepcao pai;
    private TelaPacientesAnamnese telaAnamnese;

    /**
     * Creates new form TelaAgendaConsulta
     */
    public TelaAnamneseMedica() {
        initComponents();
        this.iniciar();
    }

    //crio outro metodo construtor para a classe, mas como parametro o nome da classe
    public TelaAnamneseMedica(TelaPacientesAnamnese pai) {
        initComponents();
        this.iniciar();
        this.telaAnamnese = pai;
    }

    private void iniciar() {
        
        labelSala.setText("Sala: "+Singleton.getSala());
        labelUsuario.setText("Usuário: "+Singleton.getUsuario());
        
        letrasemNegrito();
        btnSalvar.setEnabled(true);
//        listarAnamnese();
        //PULAR DE LINHA NO AREA TEXT
        areaHistoricoClinico.setLineWrap(true);
        areaHistoricoClinico.setWrapStyleWord(true);
        areaCondutaMedica.setLineWrap(true);
        areaCondutaMedica.setWrapStyleWord(true);
        areaQueixaPrincipal.setLineWrap(true);
        areaQueixaPrincipal.setWrapStyleWord(true);
        areaPrescricao.setLineWrap(true);
        areaPrescricao.setWrapStyleWord(true);

        //..................................
        areaHistoricoClinico.setDocument(new UpperCaseField(2000));
        areaCondutaMedica.setDocument(new UpperCaseField(2000));
        areaQueixaPrincipal.setDocument(new UpperCaseField(2000));
        areaPrescricao.setDocument(new UpperCaseField(2000));

        txtNomePaciente.setEnabled(false);
        setIcon();
        ValidaEnter ve = new ValidaEnter();
        ve.ValidaEnterPainel(jPanel4);
        conexao = Conexao.conector();
    }

    private void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("Receita.png")));
    }
    
     // icone do joptionPane
    Icon icon = new javax.swing.ImageIcon(getClass().getResource("/Imagens/ok.png"));
    
    
    // icone do joptionPane
    Icon iconExcluir = new javax.swing.ImageIcon(getClass().getResource("/Imagens/Excluido.png"));

    //--------------------------Métodos--------------------------------------
    private void habilitaCampos() {

        txtNomePaciente.setEnabled(false);
        areaHistoricoClinico.setEnabled(true);
        areaCondutaMedica.setEnabled(true);
        txtData.setEnabled(true);
        jtfFrequenciaCardiaca.setEnabled(true);
        jtfFrequenciaRespiratoria.setEnabled(true);
        jtfHGT.setEnabled(true);
        jtfPressaoArterial.setEnabled(true);
        jtfTemperatura.setEnabled(true);
        jtfSaturacao.setEnabled(true);

    }

    private void desaabilitaCampos() {

        txtNomePaciente.setEnabled(false);
        areaHistoricoClinico.setEnabled(false);
        areaCondutaMedica.setEnabled(false);
        txtData.setEnabled(false);
        jtfFrequenciaCardiaca.setEnabled(false);
        jtfFrequenciaRespiratoria.setEnabled(false);
        jtfHGT.setEnabled(false);
        jtfPressaoArterial.setEnabled(false);
        jtfTemperatura.setEnabled(false);
        jtfSaturacao.setEnabled(false);

    }

    private void limparCampos() {

        areaHistoricoClinico.setText("");
        areaCondutaMedica.setText("");
        txtNomePaciente.setText("");
        txtNomePaciente.setText("");
        txtData.getEditor().setText("");
        jtfFrequenciaCardiaca.setText("");
        jtfFrequenciaRespiratoria.setText("");
        jtfHGT.setText("");
        jtfPressaoArterial.setText("");
        jtfTemperatura.setText("");
        jtfSaturacao.setText("");
        txtProntuario.setText("");
        areaQueixaPrincipal.setText("");

    }

    // metodo para colocar a letra em negrito
    Font fonte = new Font("Arial", Font.BOLD, 12);

    private void letrasemNegrito() {

        txtNomePaciente.setFont(fonte);
        areaHistoricoClinico.setFont(fonte);
        areaCondutaMedica.setFont(fonte);
        txtNomePaciente.setFont(fonte);
        txtData.setFont(fonte);
        jtfFrequenciaCardiaca.setFont(fonte);
        jtfFrequenciaRespiratoria.setFont(fonte);
        jtfHGT.setFont(fonte);
        jtfPressaoArterial.setFont(fonte);
        jtfTemperatura.setFont(fonte);
        jtfSaturacao.setFont(fonte);
    }

    public void listarNomePaciente() {
        Conexao conec = new Conexao();
        String sql = "Select NomePaciente from tbpaciente order by idPaciente Asc";
        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            TabelaNomePaciente.setModel(DbUtils.resultSetToTableModel(rs));

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

    // lista na tabelinha 
    public void listarMedicacao() {
        Conexao conec = new Conexao();
        DefaultTableModel model = (DefaultTableModel) tabelaNomeMedicacao.getModel();
        model.setNumRows(0);
        String sql = "Select NomeMedicacao,ViaAcesso,FrequenciaUso,Quantidade from tb_medicacao";
        try {
            pst = Conexao.conector().prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("NomeMedicacao"),
                    rs.getString("ViaAcesso"),
                    rs.getString("FrequenciaUso"),
                    rs.getString("Quantidade"),});
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

    private void cadastrarAnamnese() {
        Conexao conec = new Conexao();
        String sql = "Insert into tb_anamnese(NomePaciente,Data,HistoricoClinico,CondutaMedica,FrequenciaCardiaca,FrequenciaRespiratoria,Hgt,PressaoArterial,Temperatura,Saturacao,Prontuario)values(?,?,?,?,?,?,?,?,?,?,?)";
        try {
            pst = Conexao.conector().prepareStatement(sql);

            pst.setString(1, txtNomePaciente.getText());
            pst.setDate(2, convertDateSalvar(txtData.getDate()));
            pst.setString(3, areaHistoricoClinico.getText());
            pst.setString(4, areaCondutaMedica.getText());
            pst.setString(5, jtfFrequenciaCardiaca.getText());
            pst.setString(6, jtfFrequenciaRespiratoria.getText());
            pst.setString(7, jtfHGT.getText());
            pst.setString(8, jtfPressaoArterial.getText());
            pst.setString(9, jtfTemperatura.getText());
            pst.setString(10, jtfSaturacao.getText());
            pst.setString(11, txtProntuario.getText());
            pst.execute();
            JOptionPane.showMessageDialog(rootPane, " Anamnese de " + txtNomePaciente.getText().toUpperCase() + " salvo com sucesso.", "Inclusão", JOptionPane.INFORMATION_MESSAGE,icon);
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

    public void pesquisarMedicacao() {
        Conexao conec = new Conexao();
        DefaultTableModel model = (DefaultTableModel) tabelaNomeMedicacao.getModel();
        model.setNumRows(0);
        //conexão com o banco de dados
        String sql = "Select *from tb_medicacao where NomeMedicacao like ?";

        try {
            pst = Conexao.conector().prepareStatement(sql);
            pst.setString(1, txtPesquisar.getText() + "%");
            rs = pst.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("NomeMedicacao"),});
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
        LUPA_NOME_MEDICACAO = new javax.swing.JDialog();
        jScrollPane5 = new javax.swing.JScrollPane();
        tabelaNomeMedicacao = new javax.swing.JTable();
        txtPesquisar = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        LUPA_MEDICACAO = new javax.swing.JDialog();
        jXTitledPanel1 = new org.jdesktop.swingx.JXTitledPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        areaPrescricao = new javax.swing.JTextArea();
        jPanel6 = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        areaHistoricoClinico = new javax.swing.JTextArea();
        jLabel10 = new javax.swing.JLabel();
        txtNomePaciente = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtData = new org.jdesktop.swingx.JXDatePicker();
        jLabel2 = new javax.swing.JLabel();
        jtfFrequenciaRespiratoria = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jtfFrequenciaCardiaca = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jtfTemperatura = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jtfHGT = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jtfSaturacao = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jtfPressaoArterial = new javax.swing.JFormattedTextField();
        jPanel9 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        areaQueixaPrincipal = new javax.swing.JTextArea();
        jLabel16 = new javax.swing.JLabel();
        txtProntuario = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        areaCondutaMedica = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        btnSalvar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        btnMedicao = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
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

        tabelaNomeMedicacao.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Nome Medicação ", "Via Acesso", "Frequência", "Quantidade"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
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
            tabelaNomeMedicacao.getColumnModel().getColumn(1).setMinWidth(80);
            tabelaNomeMedicacao.getColumnModel().getColumn(1).setPreferredWidth(80);
            tabelaNomeMedicacao.getColumnModel().getColumn(1).setMaxWidth(80);
            tabelaNomeMedicacao.getColumnModel().getColumn(2).setMinWidth(80);
            tabelaNomeMedicacao.getColumnModel().getColumn(2).setPreferredWidth(80);
            tabelaNomeMedicacao.getColumnModel().getColumn(2).setMaxWidth(80);
            tabelaNomeMedicacao.getColumnModel().getColumn(3).setMinWidth(130);
            tabelaNomeMedicacao.getColumnModel().getColumn(3).setPreferredWidth(130);
            tabelaNomeMedicacao.getColumnModel().getColumn(3).setMaxWidth(130);
        }

        txtPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPesquisarKeyReleased(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("Pesquisar Medicação por Nome:");

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(51, 51, 51));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Create.png"))); // NOI18N
        jButton2.setText("CADASTRAR MEDICAÇÃO");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton3.setForeground(new java.awt.Color(51, 51, 51));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/show.png"))); // NOI18N
        jButton3.setText("ATUALIZAR TABELA");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout LUPA_NOME_MEDICACAOLayout = new javax.swing.GroupLayout(LUPA_NOME_MEDICACAO.getContentPane());
        LUPA_NOME_MEDICACAO.getContentPane().setLayout(LUPA_NOME_MEDICACAOLayout);
        LUPA_NOME_MEDICACAOLayout.setHorizontalGroup(
            LUPA_NOME_MEDICACAOLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LUPA_NOME_MEDICACAOLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(LUPA_NOME_MEDICACAOLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 681, Short.MAX_VALUE)
                    .addComponent(txtPesquisar)
                    .addGroup(LUPA_NOME_MEDICACAOLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, LUPA_NOME_MEDICACAOLayout.createSequentialGroup()
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        LUPA_NOME_MEDICACAOLayout.setVerticalGroup(
            LUPA_NOME_MEDICACAOLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, LUPA_NOME_MEDICACAOLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(LUPA_NOME_MEDICACAOLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18))
        );

        jXTitledPanel1.setTitle("DESCREVER A PRESCRIÇÃO");
        jXTitledPanel1.setTitleFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jXTitledPanel1.setTitleForeground(new java.awt.Color(51, 51, 51));

        areaPrescricao.setColumns(20);
        areaPrescricao.setRows(5);
        areaPrescricao.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                areaPrescricaoKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(areaPrescricao);

        javax.swing.GroupLayout jXTitledPanel1Layout = new javax.swing.GroupLayout(jXTitledPanel1.getContentContainer());
        jXTitledPanel1.getContentContainer().setLayout(jXTitledPanel1Layout);
        jXTitledPanel1Layout.setHorizontalGroup(
            jXTitledPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 574, Short.MAX_VALUE)
        );
        jXTitledPanel1Layout.setVerticalGroup(
            jXTitledPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 268, Short.MAX_VALUE)
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jButton4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton4.setForeground(new java.awt.Color(51, 51, 51));
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagensTelas/SETA.png"))); // NOI18N
        jButton4.setText("ENVIAR");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout LUPA_MEDICACAOLayout = new javax.swing.GroupLayout(LUPA_MEDICACAO.getContentPane());
        LUPA_MEDICACAO.getContentPane().setLayout(LUPA_MEDICACAOLayout);
        LUPA_MEDICACAOLayout.setHorizontalGroup(
            LUPA_MEDICACAOLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, LUPA_MEDICACAOLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(LUPA_MEDICACAOLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jXTitledPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        LUPA_MEDICACAOLayout.setVerticalGroup(
            LUPA_MEDICACAOLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LUPA_MEDICACAOLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jXTitledPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Anamnese do Paciente");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(51, 51, 51));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Histórico Clínico");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(133, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(148, 148, 148))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        jPanel4.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 390, 30));

        areaHistoricoClinico.setColumns(20);
        areaHistoricoClinico.setRows(5);
        areaHistoricoClinico.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        areaHistoricoClinico.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                areaHistoricoClinicoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                areaHistoricoClinicoFocusLost(evt);
            }
        });
        jScrollPane2.setViewportView(areaHistoricoClinico);

        jPanel4.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 390, 340));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setText("Data Cadastro:");
        jPanel4.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 20, 100, 20));

        txtNomePaciente.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtNomePaciente.setEnabled(false);
        jPanel4.add(txtNomePaciente, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 10, 330, 40));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setText("Nome Paciente:");
        jPanel4.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 20, 100, 20));

        txtData.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel4.add(txtData, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 10, 180, 40));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("Frequência Respiratória :");
        jPanel4.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 370, -1, -1));

        jtfFrequenciaRespiratoria.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jtfFrequenciaRespiratoria.setEnabled(false);
        jtfFrequenciaRespiratoria.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtfFrequenciaRespiratoriaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtfFrequenciaRespiratoriaFocusLost(evt);
            }
        });
        jPanel4.add(jtfFrequenciaRespiratoria, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 360, 70, 30));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setText("Frequência Cardiaca        :");
        jPanel4.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 320, -1, -1));

        jtfFrequenciaCardiaca.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jtfFrequenciaCardiaca.setEnabled(false);
        jtfFrequenciaCardiaca.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtfFrequenciaCardiacaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtfFrequenciaCardiacaFocusLost(evt);
            }
        });
        jPanel4.add(jtfFrequenciaCardiaca, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 310, 70, 30));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setText("Temperatura - TEMP       :");
        jPanel4.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 420, -1, -1));

        jtfTemperatura.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jtfTemperatura.setEnabled(false);
        jtfTemperatura.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtfTemperaturaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtfTemperaturaFocusLost(evt);
            }
        });
        jPanel4.add(jtfTemperatura, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 410, 70, 30));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel9.setText("HGT/Glicemia  :");
        jPanel4.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(1040, 320, -1, -1));

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
        jPanel4.add(jtfHGT, new org.netbeans.lib.awtextra.AbsoluteConstraints(1140, 310, 70, 30));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel12.setText("Saturação O²  :");
        jPanel4.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(1040, 370, 100, -1));

        jtfSaturacao.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jtfSaturacao.setEnabled(false);
        jtfSaturacao.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtfSaturacaoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtfSaturacaoFocusLost(evt);
            }
        });
        jPanel4.add(jtfSaturacao, new org.netbeans.lib.awtextra.AbsoluteConstraints(1140, 360, 70, 30));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel13.setText("Pressão Arterial :");
        jPanel4.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(1040, 410, -1, 30));

        jtfPressaoArterial.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        try {
            jtfPressaoArterial.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###x##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jtfPressaoArterial.setEnabled(false);
        jtfPressaoArterial.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtfPressaoArterialFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtfPressaoArterialFocusLost(evt);
            }
        });
        jPanel4.add(jtfPressaoArterial, new org.netbeans.lib.awtextra.AbsoluteConstraints(1140, 410, 70, 30));

        jPanel9.setBackground(new java.awt.Color(51, 51, 51));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Sinais Vitais Aferidos");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(153, 153, 153)
                .addComponent(jLabel14)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        jPanel4.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 270, 400, -1));

        jPanel10.setBackground(new java.awt.Color(255, 0, 0));

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Queixa Principal");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(153, 153, 153)
                .addComponent(jLabel15)
                .addContainerGap(129, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        jPanel4.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 270, 390, -1));

        areaQueixaPrincipal.setColumns(20);
        areaQueixaPrincipal.setRows(5);
        areaQueixaPrincipal.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        areaQueixaPrincipal.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        areaQueixaPrincipal.setEnabled(false);
        areaQueixaPrincipal.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                areaQueixaPrincipalFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                areaQueixaPrincipalFocusLost(evt);
            }
        });
        jScrollPane7.setViewportView(areaQueixaPrincipal);

        jPanel4.add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 310, 390, 130));

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel16.setText("Prontuário:");
        jPanel4.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, 20));

        txtProntuario.setBackground(new java.awt.Color(102, 153, 255));
        txtProntuario.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtProntuario.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtProntuario.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel4.add(txtProntuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 10, 110, 40));

        jPanel5.setBackground(new java.awt.Color(51, 51, 51));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Conduta Médica");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(358, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(331, 331, 331))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        jPanel4.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 60, 800, -1));

        areaCondutaMedica.setColumns(20);
        areaCondutaMedica.setRows(5);
        areaCondutaMedica.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        areaCondutaMedica.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                areaCondutaMedicaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                areaCondutaMedicaFocusLost(evt);
            }
        });
        jScrollPane8.setViewportView(areaCondutaMedica);

        jPanel4.add(jScrollPane8, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 100, 800, 160));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 51, 51));
        jLabel3.setText("Registro Anamnese do Paciente");

        btnSalvar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnSalvar.setForeground(new java.awt.Color(51, 51, 51));
        btnSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagensTelas/Salvar.png"))); // NOI18N
        btnSalvar.setText("SALVAR");
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jLabel3))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Menú Acesso Rápido", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jButton1.setBackground(new java.awt.Color(102, 255, 204));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(51, 51, 51));
        jButton1.setText("EXAMES IMAGEM");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton5.setBackground(new java.awt.Color(0, 153, 102));
        jButton5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton5.setForeground(new java.awt.Color(51, 51, 51));
        jButton5.setText("ATESTADO MÉDICO");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setBackground(new java.awt.Color(0, 204, 204));
        jButton6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton6.setForeground(new java.awt.Color(51, 51, 51));
        jButton6.setText("ATESTADO COMPARECIMENTO");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setBackground(new java.awt.Color(153, 153, 0));
        jButton7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton7.setForeground(new java.awt.Color(51, 51, 51));
        jButton7.setText("RECEITA SIMPLES");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setBackground(new java.awt.Color(255, 153, 0));
        jButton8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton8.setForeground(new java.awt.Color(51, 51, 51));
        jButton8.setText("RECEITA ESPECIAL");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton9.setBackground(new java.awt.Color(51, 153, 255));
        jButton9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton9.setForeground(new java.awt.Color(51, 51, 51));
        jButton9.setText("LAUDOS");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        btnMedicao.setBackground(new java.awt.Color(255, 102, 51));
        btnMedicao.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnMedicao.setForeground(new java.awt.Color(51, 51, 51));
        btnMedicao.setText("MEDICAÇÃO");
        btnMedicao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMedicaoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnMedicao, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jButton8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(btnMedicao, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel11Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnMedicao, jButton5, jButton6, jButton7, jButton8, jButton9});

        jPanel12.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        labelUsuario.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        labelUsuario.setForeground(new java.awt.Color(51, 51, 51));
        labelUsuario.setText("Usuário: ");

        labelSala.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        labelSala.setForeground(new java.awt.Color(51, 51, 51));
        labelSala.setText("Sala:");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addComponent(labelUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(labelSala, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
            .addComponent(labelSala, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 1229, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 448, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        setSize(new java.awt.Dimension(1265, 729));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents


    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        if (txtNomePaciente.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Campo Nome é obrigatório", "Atenção", JOptionPane.WARNING_MESSAGE);
            txtNomePaciente.requestFocus();
            return;
        }
        if (txtData.getDate() == null) {
            JOptionPane.showMessageDialog(rootPane, "Campo Data é Obrigatório", "Atenção", JOptionPane.WARNING_MESSAGE);
            txtData.requestFocus();
            return;
        }

        if (areaHistoricoClinico.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Campo Histórico Clínico é Obrigatório", "Atenção", JOptionPane.WARNING_MESSAGE);
            areaHistoricoClinico.requestFocus();
            return;
        }

        if (areaCondutaMedica.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Campo Conduta Médica é Obrigatório", "Atenção", JOptionPane.WARNING_MESSAGE);
            areaCondutaMedica.requestFocus();
            return;
        }

        if (jtfFrequenciaCardiaca.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Campo Frequência Cardíaca é Obrigatório", "Atenção", JOptionPane.WARNING_MESSAGE);
            jtfFrequenciaCardiaca.requestFocus();
            return;
        }

        if (jtfFrequenciaRespiratoria.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Campo Frequência Respiratória é Obrigatório", "Atenção", JOptionPane.WARNING_MESSAGE);
            jtfFrequenciaRespiratoria.requestFocus();
            return;
        }

        if (jtfHGT.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Campo HGT é Obrigatório", "Atenção", JOptionPane.WARNING_MESSAGE);
            jtfHGT.requestFocus();
            return;
        }

        if (jtfPressaoArterial.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Campo Pressão Arterial é Obrigatório", "Atenção", JOptionPane.WARNING_MESSAGE);
            jtfPressaoArterial.requestFocus();
            return;
        }

        if (jtfTemperatura.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Campo Temperatura é Obrigatório", "Atenção", JOptionPane.WARNING_MESSAGE);
            jtfTemperatura.requestFocus();
            return;
        }

        if (jtfSaturacao.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Campo Saturação é Obrigatório", "Atenção", JOptionPane.WARNING_MESSAGE);
            jtfSaturacao.requestFocus();
            return;
    }
                    cadastrarAnamnese();
                    if (telaAnamnese != null) {
                        telaAnamnese.listarDadosPaciente();
                    }
                    limparCampos();
                    desaabilitaCampos();
                    btnSalvar.setEnabled(false);
                    
        // aqui chamo a opcao se sim imprime e salva ----------se escolher nao .....nao imprime e salva  
        int sair = JOptionPane.showConfirmDialog(null, "DR. O PACIENTE EXAMINADO SERÁ INTERNADO ?", "ATENÇÃO", JOptionPane.YES_NO_OPTION);
        if (sair == JOptionPane.YES_OPTION) {
                    TelaInternacao tela = new TelaInternacao();
                    tela.setVisible(true);  
        } else{
        
             
        }
                    
    }//GEN-LAST:event_btnSalvarActionPerformed
    
    
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        
    }//GEN-LAST:event_formWindowClosing

    private void TabelaNomePacienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabelaNomePacienteMouseClicked
        // seleciona a linha da tabela e seta nos campos
        int linha = TabelaNomePaciente.getSelectedRow();
        if (linha >= 0) {
            // numero da coluna da tabelinha
            txtNomePaciente.setText(TabelaNomePaciente.getValueAt(linha, 0).toString());
            LUPA_NOME_PACIENTE.setVisible(false);

        }
    }//GEN-LAST:event_TabelaNomePacienteMouseClicked

    private void areaHistoricoClinicoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_areaHistoricoClinicoFocusGained
        areaHistoricoClinico.setBackground(new Color(252, 233, 163));

        if (txtData.getDate() == null) {
            JOptionPane.showMessageDialog(rootPane, "Campo Data é Obrigatório", "Atenção", JOptionPane.WARNING_MESSAGE);
            txtData.requestFocus();
            return;
        }
    }//GEN-LAST:event_areaHistoricoClinicoFocusGained

    private void areaHistoricoClinicoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_areaHistoricoClinicoFocusLost
        areaHistoricoClinico.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_areaHistoricoClinicoFocusLost

    private void jtfFrequenciaRespiratoriaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfFrequenciaRespiratoriaFocusGained
        jtfFrequenciaRespiratoria.setBackground(new Color(252, 233, 163));
    }//GEN-LAST:event_jtfFrequenciaRespiratoriaFocusGained

    private void jtfFrequenciaCardiacaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfFrequenciaCardiacaFocusGained
        jtfFrequenciaCardiaca.setBackground(new Color(252, 233, 163));
    }//GEN-LAST:event_jtfFrequenciaCardiacaFocusGained

    private void jtfTemperaturaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfTemperaturaFocusGained
        jtfTemperatura.setBackground(new Color(252, 233, 163));
    }//GEN-LAST:event_jtfTemperaturaFocusGained

    private void jtfHGTFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfHGTFocusGained
        jtfHGT.setBackground(new Color(252, 233, 163));
    }//GEN-LAST:event_jtfHGTFocusGained

    private void jtfSaturacaoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfSaturacaoFocusGained
        jtfSaturacao.setBackground(new Color(252, 233, 163));
    }//GEN-LAST:event_jtfSaturacaoFocusGained

    private void jtfPressaoArterialFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfPressaoArterialFocusGained
        jtfPressaoArterial.setBackground(new Color(252, 233, 163));
    }//GEN-LAST:event_jtfPressaoArterialFocusGained

    private void jtfFrequenciaRespiratoriaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfFrequenciaRespiratoriaFocusLost
        jtfFrequenciaRespiratoria.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_jtfFrequenciaRespiratoriaFocusLost

    private void jtfFrequenciaCardiacaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfFrequenciaCardiacaFocusLost
        jtfFrequenciaCardiaca.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_jtfFrequenciaCardiacaFocusLost

    private void jtfTemperaturaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfTemperaturaFocusLost
        jtfTemperatura.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_jtfTemperaturaFocusLost

    private void jtfHGTFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfHGTFocusLost
        jtfHGT.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_jtfHGTFocusLost

    private void jtfSaturacaoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfSaturacaoFocusLost
        jtfSaturacao.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_jtfSaturacaoFocusLost

    private void jtfPressaoArterialFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfPressaoArterialFocusLost
        jtfPressaoArterial.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_jtfPressaoArterialFocusLost

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

// // seleciona a linha da tabela e seta nos campos sem quebra de linha
//        int linha = tabelaNomeMedicacao.getSelectedRow();
//        if (linha >= 0) {
//            // esse codigo faz com que as linhas selecionadas da tabela fiquem uma abaixo da outra
//            String dados0 = (tabelaNomeMedicacao.getValueAt(linha, 0).toString());
//            String dados1 = (tabelaNomeMedicacao.getValueAt(linha, 1).toString());
//            String dados2 = (tabelaNomeMedicacao.getValueAt(linha, 2).toString());
//            String dados3 = (tabelaNomeMedicacao.getValueAt(linha, 3).toString());
//            setar_dados = setar_dados + "\n" +  dados0;
//            setar_dados = setar_dados + "     -     " + dados1 ;
//            setar_dados = setar_dados + "     -     " + dados2 ;
//            setar_dados = setar_dados + "     -     " + dados3 ;
//            areaMedicacao.setText(setar_dados);
//            LUPA_NOME_MEDICACAO.setVisible(false);
//        }

    }//GEN-LAST:event_tabelaNomeMedicacaoMouseClicked

    private void txtPesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisarKeyReleased
        pesquisarMedicacao();
    }//GEN-LAST:event_txtPesquisarKeyReleased

    private void areaQueixaPrincipalFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_areaQueixaPrincipalFocusGained
        areaQueixaPrincipal.setBackground(new Color(252, 233, 163));
        btnSalvar.setEnabled(true);
    }//GEN-LAST:event_areaQueixaPrincipalFocusGained

    private void areaQueixaPrincipalFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_areaQueixaPrincipalFocusLost
        areaQueixaPrincipal.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_areaQueixaPrincipalFocusLost

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        TelaCadastroMedicacao tela = new TelaCadastroMedicacao();
        tela.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        listarMedicacao();
    }//GEN-LAST:event_jButton3ActionPerformed


    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed

        // codigo faz com que ao clicar no botao da tela abre outra tela escreve e seta os dados nessa outra tela 
        String dados0 = (areaPrescricao.getText().toString());
        setar_dados = setar_dados + "\n" + dados0;
        LUPA_MEDICACAO.setVisible(true);
        areaPrescricao.setText("");
    }//GEN-LAST:event_jButton4ActionPerformed

    private void areaPrescricaoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_areaPrescricaoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_areaPrescricaoKeyPressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        TelaRealizarExames tela = new TelaRealizarExames();
        tela.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        TelaAtestadoMedico tela = new TelaAtestadoMedico();
        tela.setVisible(true);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        TelaAtestadoComparecimento tela = new TelaAtestadoComparecimento();
        tela.setVisible(true);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        TelaReceituarioSimples tela = new TelaReceituarioSimples();
        tela.setVisible(true);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        TelaReceituarioEspecial tela = new TelaReceituarioEspecial();
        tela.setVisible(true);
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        TelaLaudos tela = new TelaLaudos();
        tela.setVisible(true);
    }//GEN-LAST:event_jButton9ActionPerformed

    private void btnMedicaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMedicaoActionPerformed
        TelaReceitarMedicacao tela = new TelaReceitarMedicacao();
        tela.setVisible(true);
    }//GEN-LAST:event_btnMedicaoActionPerformed

    private void areaCondutaMedicaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_areaCondutaMedicaFocusGained
        areaCondutaMedica.setBackground(new Color(252, 233, 163));
    }//GEN-LAST:event_areaCondutaMedicaFocusGained

    private void areaCondutaMedicaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_areaCondutaMedicaFocusLost
        areaCondutaMedica.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_areaCondutaMedicaFocusLost

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
            java.util.logging.Logger.getLogger(TelaAnamneseMedica.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaAnamneseMedica.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaAnamneseMedica.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaAnamneseMedica.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaAnamneseMedica().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog LUPA_MEDICACAO;
    private javax.swing.JDialog LUPA_NOME_MEDICACAO;
    private javax.swing.JDialog LUPA_NOME_PACIENTE;
    private javax.swing.JTable TabelaNomePaciente;
    private javax.swing.JTextArea areaCondutaMedica;
    private javax.swing.JTextArea areaHistoricoClinico;
    private javax.swing.JTextArea areaPrescricao;
    public javax.swing.JTextArea areaQueixaPrincipal;
    private javax.swing.JButton btnMedicao;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private org.jdesktop.swingx.JXTitledPanel jXTitledPanel1;
    public javax.swing.JTextField jtfFrequenciaCardiaca;
    public javax.swing.JTextField jtfFrequenciaRespiratoria;
    public javax.swing.JTextField jtfHGT;
    public javax.swing.JFormattedTextField jtfPressaoArterial;
    public javax.swing.JTextField jtfSaturacao;
    public javax.swing.JTextField jtfTemperatura;
    private javax.swing.JLabel labelSala;
    private javax.swing.JLabel labelUsuario;
    private javax.swing.JTable tabelaNomeMedicacao;
    public org.jdesktop.swingx.JXDatePicker txtData;
    public javax.swing.JTextField txtNomePaciente;
    private javax.swing.JTextField txtPesquisar;
    public javax.swing.JTextField txtProntuario;
    // End of variables declaration//GEN-END:variables

    public List GetDados() {

        List lista = new ArrayList();

//for (int i = 0; i < 10; i++) {
        AuxiliarAnamnese print = new AuxiliarAnamnese();

        print.setData(txtData.getEditor().getText());
        print.setNomepaciente(txtNomePaciente.getText());
        lista.add(print);
        return lista;
    }

    //metodo converte data
    private java.sql.Date convertDateSalvar(java.util.Date var) throws SQLException {
        int year = var.getDate();
        int month = var.getMonth();
        int day = var.getYear();
        return new java.sql.Date(day, month, year);
    }
//    
//    private void listarAnamnese() {
//        //tratamento de erros
//        try {
//            DefaultTableModel model = (DefaultTableModel) tabelaHistorico.getModel();//pegamos o model da tabela
//            model.setNumRows(0);//limpamos caso tenha algum registro
//
//            String sql = "SELECT * FROM tb_anamnese" ;
//            System.out.println(sql);
//            pst = Conexao.conector().prepareStatement(sql);
//            rs = pst.executeQuery();
//            while (rs.next()) {
//                //adiciona linha na tabela
//                model.addRow(new Object[]{
//                    //                    rs.getString("Data"),
//                    Utilitarios.Utils.convertData(rs.getDate("Data")),
//                    rs.getString("NomePaciente"),
//                    rs.getString("HistoricoClinico"),
//                    rs.getString("CondutaMedica"),
//                    rs.getString("Medicacao"),
//                    rs.getString("FrequenciaCardiaca"),
//                    rs.getString("FrequenciaRespiratoria"),
//                    rs.getString("Hgt"),
//                    rs.getString("PressaoArterial"),
//                    rs.getString("Temperatura"),
//                    rs.getString("Saturacao"),});
//            }
//        } catch (Exception e) {
//            e.printStackTrace();//imprime o erro no console do netbeans
//            Msg.erro(this, "Erro ao Listar Anamnese!\nErro: " + e.getMessage());
//        }
//   } 

}
