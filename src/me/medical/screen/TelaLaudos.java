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
public class TelaLaudos extends javax.swing.JFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    /**
     * /**
     * Creates new form TelaLaudos
     */
    public TelaLaudos() {
        initComponents();
        // muda de linha na caixa area texto
        txtConclusaoLaudo.setLineWrap(true);
        txtConclusaoLaudo.setWrapStyleWord(true);
        txtObservacaoLaudo.setLineWrap(true);
        txtConclusaoLaudo.setWrapStyleWord(true);
        listarLaudo();
        //txtCodigo.setVisible(false);
        setIcon();
        btnImprimirlaudo.setVisible(false);
        desaabilitaCampos();
        ValidaEnter ve = new ValidaEnter();
        ve.ValidaEnterPainel(jPanel3);
        ve.ValidaEnterPainel(jPanel3);
        conexao = Conexao.conector();
        // coloca letra maiuscula
        txtTituloLaudo.setDocument(new UpperCaseField(100));
        txtObservacaoLaudo.setDocument(new UpperCaseField(100));
        txtConclusaoLaudo.setDocument(new UpperCaseField(100));
        txtTipoExame.setDocument(new UpperCaseField(100));

    }
    
     // icone do joptionPane
    Icon icon = new javax.swing.ImageIcon(getClass().getResource("/Imagens/ok.png"));
    
    
    // icone do joptionPane
    Icon iconExcluir = new javax.swing.ImageIcon(getClass().getResource("/Imagens/Excluido.png"));
    

    private void habilitaCampos() {

        txtNomePaciente.setEnabled(false);
        //txtCodigo.setEnabled(true);
        txtTituloLaudo.setEnabled(true);
        txtObservacaoLaudo.setEnabled(true);
        txtConclusaoLaudo.setEnabled(true);
        txtTipoExame.setEnabled(true);
        txtNomeMedico.setEnabled(false);
        btnPesquisar.setEnabled(true);
        btnPesquisarMedico.setEnabled(true);
        tblLaudo.setEnabled(true);
        txtDataCadastro.setEnabled(true);
    }

    private void desaabilitaCampos() {

        txtNomePaciente.setEnabled(false);
        //txtCodigo.setEnabled(true);
        txtTituloLaudo.setEnabled(false);
        txtObservacaoLaudo.setEnabled(false);
        txtConclusaoLaudo.setEnabled(false);
        txtTipoExame.setEnabled(false);
        txtNomeMedico.setEnabled(false);
        btnPesquisar.setEnabled(false);
        btnPesquisarMedico.setEnabled(false);
        tblLaudo.setEnabled(false);
        txtDataCadastro.setEnabled(false);
    }

    private void limparCampos() {

        txtNomePaciente.setText("");
        txtCodigo.setText("");
        txtTituloLaudo.setText("");
        txtObservacaoLaudo.setText("");
        txtConclusaoLaudo.setText("");
        txtTipoExame.setText("");
        txtNomeMedico.setText("");
        txtDataCadastro.getEditor().setText("");
    }

    // metodo para colocar a letra em negrito
    Font fonte = new Font("Arial", Font.BOLD, 12);

    private void letrasemNegrito() {

        txtNomePaciente.setFont(fonte);
        txtCodigo.setFont(fonte);
        txtTituloLaudo.setFont(fonte);
        txtObservacaoLaudo.setFont(fonte);
        txtConclusaoLaudo.setFont(fonte);
        txtTipoExame.setFont(fonte);
        txtNomeMedico.setFont(fonte);
        txtNomeMedico.setFont(fonte);
        txtDataCadastro.setFont(fonte);
    }

    private void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("Receita.png")));
    }

    public void listarNomeMedico() {
        Conexao conec = new Conexao();
        String sql = "Select NomeMedico from tbmedicos order by idMedico Asc";
        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            TabelaNomeMedico.setModel(DbUtils.resultSetToTableModel(rs));

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

    public void listarNomePaciente() {
        Conexao conec = new Conexao();
        String sql = "Select NomePaciente from tb_agenda order by codigo Asc";
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

    public void listarLaudo() {
        Conexao conec = new Conexao();
        DefaultTableModel model = (DefaultTableModel) tblLaudo.getModel();
        model.setNumRows(0);
        String sql = "Select codigo,NomePaciente,NomeMedico,DataLaudo,TituloLaudo,TipoExame,ObsLaudo,ConclusaoLaudo from tblaudo order by codigo";
        try {
            pst = Conexao.conector().prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("codigo"),
                    rs.getString("NomePaciente"),
                    rs.getString("NomeMedico"),
                    Utils.convertData(rs.getDate("DataLaudo")),
                    rs.getString("TituloLaudo"),
                    rs.getString("TipoExame"),
                    rs.getString("ObsLaudo"),
                    rs.getString("ConclusaoLaudo"),});
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

    public void cadastrarLaudo() {
        Conexao conec = new Conexao();
        String sql = "Insert into tblaudo(NomePaciente,NomeMedico,DataLaudo,TituloLaudo,TipoExame,ObsLaudo,ConclusaoLaudo )values(?,?,?,?,?,?,?)";

        try {
            pst = Conexao.conector().prepareStatement(sql);
            pst.setString(1, txtNomePaciente.getText());
            pst.setString(2, txtNomeMedico.getText());
            pst.setDate(3, convertDateSalvar(txtDataCadastro.getDate()));
            pst.setString(4, txtTituloLaudo.getText());
            pst.setString(5, txtTipoExame.getText());
            pst.setString(6, txtObservacaoLaudo.getText());
            pst.setString(7, txtConclusaoLaudo.getText());
            pst.execute();
            JOptionPane.showMessageDialog(rootPane, " Laudo de " + txtNomePaciente.getText().toUpperCase() + " salvo com sucesso.", "Inclusão", JOptionPane.INFORMATION_MESSAGE,icon);
            listarLaudo();

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

    private void deletarlaudo() {
        Conexao conec = new Conexao();
        String sql = "Delete from tblaudo where codigo = ?";
        try {
            pst = Conexao.conector().prepareStatement(sql);
            pst.setInt(1, Integer.parseInt(txtCodigo.getText()));
            pst.execute();
            JOptionPane.showMessageDialog(rootPane, " Laudo de " + txtNomePaciente.getText() + " Excluido com sucesso.", "Inclusão", JOptionPane.INFORMATION_MESSAGE,iconExcluir);
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
        listarLaudo();
    }

    private void editarLaudo() {
        Conexao conec = new Conexao();
        String sql = "Update tblaudo set NomePaciente=?,NomeMedico=?,DataLaudo=?,TituloLaudo=?,TipoExame=?,ObsLaudo=?,ConclusaoLaudo=? where codigo = ? ";

        try {
            pst = Conexao.conector().prepareStatement(sql);
            pst.setString(1, txtNomePaciente.getText());
            pst.setString(2, txtNomeMedico.getText());
            pst.setDate(3, convertDateSalvar(txtDataCadastro.getDate()));
            pst.setString(4, txtTituloLaudo.getText());
            pst.setString(5, txtTipoExame.getText());
            pst.setString(6, txtObservacaoLaudo.getText());
            pst.setString(7, txtConclusaoLaudo.getText());
            pst.setInt(8, Integer.parseInt(txtCodigo.getText()));
            pst.executeUpdate();
            JOptionPane.showMessageDialog(rootPane, " Laudo de " + txtNomePaciente.getText() + " Editado com sucesso.", "Inclusão", JOptionPane.INFORMATION_MESSAGE,icon);
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
        listarLaudo();
    }

    public void mostraItens() {
        int seleciona = tblLaudo.getSelectedRow();
        txtCodigo.setText(tblLaudo.getModel().getValueAt(seleciona, 0).toString());
        txtNomePaciente.setText(tblLaudo.getModel().getValueAt(seleciona, 1).toString());
        txtNomeMedico.setText(tblLaudo.getModel().getValueAt(seleciona, 2).toString());
        txtDataCadastro.setDate(Utils.convertData(tblLaudo.getModel().getValueAt(seleciona, 3).toString()));
        txtTituloLaudo.setText(tblLaudo.getModel().getValueAt(seleciona, 4).toString());
        txtTipoExame.setText(tblLaudo.getModel().getValueAt(seleciona, 5).toString());
        txtObservacaoLaudo.setText(tblLaudo.getModel().getValueAt(seleciona, 6).toString());
        txtConclusaoLaudo.setText(tblLaudo.getModel().getValueAt(seleciona, 7).toString());
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
        jScrollPane4 = new javax.swing.JScrollPane();
        TabelaNomePaciente = new javax.swing.JTable();
        LUPA_NOME_MEDICO = new javax.swing.JDialog();
        jScrollPane5 = new javax.swing.JScrollPane();
        TabelaNomeMedico = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        btnSair1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtTituloLaudo = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtObservacaoLaudo = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtConclusaoLaudo = new javax.swing.JTextArea();
        jPanel6 = new javax.swing.JPanel();
        btnNovo = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnImprimirlaudo = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txtNomePaciente = new javax.swing.JTextField();
        btnPesquisar = new javax.swing.JButton();
        txtNomeMedico = new javax.swing.JTextField();
        btnPesquisarMedico = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txtTipoExame = new javax.swing.JTextField();
        txtCodigo = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        txtDataCadastro = new org.jdesktop.swingx.JXDatePicker();
        jLabel6 = new javax.swing.JLabel();
        jXTitledPanel1 = new org.jdesktop.swingx.JXTitledPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblLaudo = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();

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
        jScrollPane4.setViewportView(TabelaNomePaciente);

        javax.swing.GroupLayout LUPA_NOME_PACIENTELayout = new javax.swing.GroupLayout(LUPA_NOME_PACIENTE.getContentPane());
        LUPA_NOME_PACIENTE.getContentPane().setLayout(LUPA_NOME_PACIENTELayout);
        LUPA_NOME_PACIENTELayout.setHorizontalGroup(
            LUPA_NOME_PACIENTELayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LUPA_NOME_PACIENTELayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 330, Short.MAX_VALUE)
                .addContainerGap())
        );
        LUPA_NOME_PACIENTELayout.setVerticalGroup(
            LUPA_NOME_PACIENTELayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LUPA_NOME_PACIENTELayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
                .addContainerGap())
        );

        LUPA_NOME_MEDICO.setModal(true);

        TabelaNomeMedico.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        TabelaNomeMedico.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Nome Médico"
            }
        ));
        TabelaNomeMedico.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabelaNomeMedicoMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(TabelaNomeMedico);

        javax.swing.GroupLayout LUPA_NOME_MEDICOLayout = new javax.swing.GroupLayout(LUPA_NOME_MEDICO.getContentPane());
        LUPA_NOME_MEDICO.getContentPane().setLayout(LUPA_NOME_MEDICOLayout);
        LUPA_NOME_MEDICOLayout.setHorizontalGroup(
            LUPA_NOME_MEDICOLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LUPA_NOME_MEDICOLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 330, Short.MAX_VALUE)
                .addContainerGap())
        );
        LUPA_NOME_MEDICOLayout.setVerticalGroup(
            LUPA_NOME_MEDICOLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LUPA_NOME_MEDICOLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
                .addContainerGap())
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastro Laudos");
        setExtendedState(6);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 51));
        jLabel2.setText("Cadastro Laudos");

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSair1)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(btnSair1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("Nome Paciente:");

        txtTituloLaudo.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Obervações do Laudo:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

        txtObservacaoLaudo.setColumns(20);
        txtObservacaoLaudo.setRows(5);
        jScrollPane1.setViewportView(txtObservacaoLaudo);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 447, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("Tipo de Exame Realizado:");

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Conclusões do Laudo", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

        txtConclusaoLaudo.setColumns(20);
        txtConclusaoLaudo.setRows(5);
        jScrollPane2.setViewportView(txtConclusaoLaudo);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnNovo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnNovo.setForeground(new java.awt.Color(51, 51, 51));
        btnNovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagensTelas/Novo.png"))); // NOI18N
        btnNovo.setText("NOVO");
        btnNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoActionPerformed(evt);
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

        btnImprimirlaudo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnImprimirlaudo.setForeground(new java.awt.Color(51, 51, 51));
        btnImprimirlaudo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Print_24x24.png"))); // NOI18N
        btnImprimirlaudo.setText("Imprimir Laudo");
        btnImprimirlaudo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirlaudoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 260, Short.MAX_VALUE)
                .addComponent(btnImprimirlaudo)
                .addGap(20, 20, 20))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnImprimirlaudo, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("Titulo do Laudo:");

        txtNomePaciente.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtNomePaciente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomePacienteActionPerformed(evt);
            }
        });

        btnPesquisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Search.png"))); // NOI18N
        btnPesquisar.setEnabled(false);
        btnPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesquisarActionPerformed(evt);
            }
        });

        txtNomeMedico.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnPesquisarMedico.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Search.png"))); // NOI18N
        btnPesquisarMedico.setEnabled(false);
        btnPesquisarMedico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesquisarMedicoActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("Nome Médico:");

        txtTipoExame.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txtCodigo.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtCodigo.setEnabled(false);

        jPanel4.setBackground(new java.awt.Color(51, 51, 51));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 8, Short.MAX_VALUE)
        );

        txtDataCadastro.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtDataCadastro.setEnabled(false);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("Código");

        jXTitledPanel1.setTitle("SELECIONE ABAIXO O PACIENTE QUE DESEJA IMPRIMIR");
        jXTitledPanel1.setTitleFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        tblLaudo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        tblLaudo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Nome Paciente", "Nome Médico", "Data Laudo", "Título do Laudo", "Tipo de Exame", "Observação do Laudo", "Conclusão do Laudo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblLaudo.setEnabled(false);
        tblLaudo.setRowHeight(28);
        tblLaudo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblLaudoMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblLaudoMouseReleased(evt);
            }
        });
        tblLaudo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblLaudoKeyReleased(evt);
            }
        });
        jScrollPane3.setViewportView(tblLaudo);
        if (tblLaudo.getColumnModel().getColumnCount() > 0) {
            tblLaudo.getColumnModel().getColumn(0).setMinWidth(50);
            tblLaudo.getColumnModel().getColumn(0).setPreferredWidth(50);
            tblLaudo.getColumnModel().getColumn(0).setMaxWidth(50);
            tblLaudo.getColumnModel().getColumn(3).setMinWidth(80);
            tblLaudo.getColumnModel().getColumn(3).setPreferredWidth(80);
            tblLaudo.getColumnModel().getColumn(3).setMaxWidth(80);
        }

        javax.swing.GroupLayout jXTitledPanel1Layout = new javax.swing.GroupLayout(jXTitledPanel1.getContentContainer());
        jXTitledPanel1.getContentContainer().setLayout(jXTitledPanel1Layout);
        jXTitledPanel1Layout.setHorizontalGroup(
            jXTitledPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3)
        );
        jXTitledPanel1Layout.setVerticalGroup(
            jXTitledPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
        );

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("Data Atendimento:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtTituloLaudo)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel6))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(txtNomePaciente)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(jLabel1))))
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(txtTipoExame, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 405, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(txtNomeMedico, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnPesquisarMedico, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(jLabel5))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel7)
                                            .addComponent(txtDataCadastro, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(6, 6, 6))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jXTitledPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(jLabel5)
                        .addComponent(jLabel6))
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtDataCadastro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnPesquisar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnPesquisarMedico, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtNomePaciente)
                        .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtNomeMedico))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTituloLaudo, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTipoExame, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jXTitledPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {txtNomePaciente, txtTipoExame, txtTituloLaudo});

        setSize(new java.awt.Dimension(993, 645));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSair1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSair1ActionPerformed

        //Pergunta se o usuário deseja realmente sair do sistema
        int sair = JOptionPane.showConfirmDialog(null, "DESEJA SAIR DA TELA LAUDO?", "ATENÇÃO", JOptionPane.YES_NO_OPTION);
        if (sair == JOptionPane.YES_OPTION) {
            dispose();
        }
    }//GEN-LAST:event_btnSair1ActionPerformed

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed
        txtNomePaciente.requestFocus();
        btnEditar.setEnabled(false);
        btnExcluir.setEnabled(false);
        btnSalvar.setEnabled(true);
        habilitaCampos();
        letrasemNegrito();
    }//GEN-LAST:event_btnNovoActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        if (txtNomePaciente.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Campo Nome é obrigatório", "Atenção", JOptionPane.WARNING_MESSAGE);
            txtNomePaciente.requestFocus();
            return;
        }
        if (txtDataCadastro.getDate() == null) {
            JOptionPane.showMessageDialog(rootPane, "Campo Data é Obrigatório", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (txtNomeMedico.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Campo Nome do Médico é Obrigatório", "Atenção", JOptionPane.WARNING_MESSAGE);
            txtNomeMedico.requestFocus();
            return;
        }

        if (txtTituloLaudo.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Campo Título do Laudo é Obrigatório", "Atenção", JOptionPane.WARNING_MESSAGE);
            txtTituloLaudo.requestFocus();
            return;
        }

        if (txtTipoExame.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Campo Tipo de Exame é Obrigatório", "Atenção", JOptionPane.WARNING_MESSAGE);
            txtTipoExame.requestFocus();
            return;
        }

        if (txtObservacaoLaudo.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Campo Observação do Laudo é Obrigatório", "Atenção", JOptionPane.WARNING_MESSAGE);
            txtObservacaoLaudo.requestFocus();
            return;
        }

        if (txtConclusaoLaudo.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Campo Conclusão do Laudo é Obrigatório", "Atenção", JOptionPane.WARNING_MESSAGE);
            txtConclusaoLaudo.requestFocus();
            return;
        }

        cadastrarLaudo();
        limparCampos();
        desaabilitaCampos();
        btnSalvar.setEnabled(false);
        btnNovo.setEnabled(true);
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed

        int i = tblLaudo.getSelectedRow();
        if (i < 0) {
            Msg.alerta(this, "Selecione um Registro!");
            return;
        }
        deletarlaudo();
        desaabilitaCampos();
        limparCampos();
        btnNovo.setEnabled(true);
        btnEditar.setEnabled(false);
        btnExcluir.setEnabled(false);
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        // TODO add your handling code here:
        int i = tblLaudo.getSelectedRow();
        if (i < 0) {
            Msg.alerta(this, "Selecione um Registro!");
            return;
        }
        editarLaudo();
        desaabilitaCampos();
        limparCampos();
        btnNovo.setEnabled(true);
        btnEditar.setEnabled(false);
        btnExcluir.setEnabled(false);
    }//GEN-LAST:event_btnEditarActionPerformed

    private void TabelaNomePacienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabelaNomePacienteMouseClicked
        // seleciona a linha da tabela e seta nos campos
        int linha = TabelaNomePaciente.getSelectedRow();
        if (linha >= 0) {
            txtNomePaciente.setText(TabelaNomePaciente.getValueAt(linha, 0).toString());
            LUPA_NOME_PACIENTE.setVisible(false);
        }
    }//GEN-LAST:event_TabelaNomePacienteMouseClicked

    private void TabelaNomeMedicoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabelaNomeMedicoMouseClicked
        // seleciona a linha da tabela e seta nos campos
        int linha = TabelaNomeMedico.getSelectedRow();
        if (linha >= 0) {
            txtNomeMedico.setText(TabelaNomeMedico.getValueAt(linha, 0).toString());
            LUPA_NOME_MEDICO.setVisible(false);
        }
    }//GEN-LAST:event_TabelaNomeMedicoMouseClicked

    private void btnPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisarActionPerformed

        LUPA_NOME_PACIENTE.setSize(new java.awt.Dimension(400, 200));
        LUPA_NOME_PACIENTE.setLocationRelativeTo(null);
        listarNomePaciente();
        LUPA_NOME_PACIENTE.setVisible(true);
        txtNomePaciente.requestFocus();
    }//GEN-LAST:event_btnPesquisarActionPerformed

    private void btnPesquisarMedicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisarMedicoActionPerformed
        LUPA_NOME_MEDICO.setSize(new java.awt.Dimension(500, 200));
        LUPA_NOME_MEDICO.setLocationRelativeTo(null);
        listarNomeMedico();
        LUPA_NOME_MEDICO.setVisible(true);
        txtNomeMedico.requestFocus();
    }//GEN-LAST:event_btnPesquisarMedicoActionPerformed

    private void txtNomePacienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomePacienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomePacienteActionPerformed

    private void tblLaudoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblLaudoMouseClicked
        mostraItens();
        habilitaCampos();
        btnEditar.setEnabled(true);
        btnExcluir.setEnabled(true);
        btnNovo.setEnabled(false);
        btnSalvar.setEnabled(false);
        btnImprimirlaudo.setVisible(true);

    }//GEN-LAST:event_tblLaudoMouseClicked

    private void tblLaudoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblLaudoMouseReleased

    }//GEN-LAST:event_tblLaudoMouseReleased

    private void tblLaudoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblLaudoKeyReleased
        mostraItens();
    }//GEN-LAST:event_tblLaudoKeyReleased

    private void btnImprimirlaudoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirlaudoActionPerformed

        final Aguarde carregando = new Aguarde();
        carregando.setVisible(true);
        Thread t = new Thread() {
            public void run() {

                try {
                    ImpressaoLaudo imprime = new ImpressaoLaudo();
                    List listade_dados = GetDados();
                    imprime.Imprime_Relatorio(listade_dados);
                    carregando.dispose();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }
                desaabilitaCampos();
                limparCampos();
                btnNovo.setEnabled(true);
                btnSalvar.setEnabled(false);
                btnEditar.setEnabled(false);
                btnExcluir.setEnabled(false);
                tblLaudo.setEnabled(false);
                btnImprimirlaudo.setVisible(false);
            }
        };
        t.start();
    }//GEN-LAST:event_btnImprimirlaudoActionPerformed

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
            java.util.logging.Logger.getLogger(TelaLaudos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaLaudos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaLaudos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaLaudos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaLaudos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog LUPA_NOME_MEDICO;
    private javax.swing.JDialog LUPA_NOME_PACIENTE;
    private javax.swing.JTable TabelaNomeMedico;
    private javax.swing.JTable TabelaNomePaciente;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnImprimirlaudo;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnPesquisar;
    private javax.swing.JButton btnPesquisarMedico;
    private javax.swing.JButton btnSair1;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
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
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private org.jdesktop.swingx.JXTitledPanel jXTitledPanel1;
    private javax.swing.JTable tblLaudo;
    public javax.swing.JTextField txtCodigo;
    public javax.swing.JTextArea txtConclusaoLaudo;
    public org.jdesktop.swingx.JXDatePicker txtDataCadastro;
    public javax.swing.JTextField txtNomeMedico;
    public javax.swing.JTextField txtNomePaciente;
    public javax.swing.JTextArea txtObservacaoLaudo;
    public javax.swing.JTextField txtTipoExame;
    public javax.swing.JTextField txtTituloLaudo;
    // End of variables declaration//GEN-END:variables
//metodo converte data
    private java.sql.Date convertDateSalvar(java.util.Date var) throws SQLException {
        int year = var.getDate();
        int month = var.getMonth();
        int day = var.getYear();
        return new java.sql.Date(day, month, year);
    }

    public List GetDados() {

        List lista = new ArrayList();

//for (int i = 0; i < 10; i++) {
        AuxiliarLaudo print = new AuxiliarLaudo();

        print.setCodigo(txtCodigo.getText());
        print.setNomepaciente(txtNomePaciente.getText());
        print.setNomemedico(txtNomeMedico.getText());
        print.setDatalaudo(txtDataCadastro.getEditor().getText());
        print.setTitulolaudo(txtTituloLaudo.getText());
        print.setTipoexame(txtTipoExame.getText());
        print.setObslaudo(txtObservacaoLaudo.getText());
        print.setConclusaolaudo(txtConclusaoLaudo.getText());
        lista.add(print);
        return lista;
    }

}
