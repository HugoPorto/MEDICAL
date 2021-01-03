/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.medical.screen;

import me.medical.controller.Conexao;
import me.medical.utils.Utils;
import java.awt.Font;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.Icon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import me.medical.utils.Msg;
import me.medical.utils.Singleton;
import me.medical.utils.ValidaEnter;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author Adriano Zanette
 */
public class TelaCadastroPedidoMedicacao extends javax.swing.JFrame {
    String setar_dados = "";
    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    /**
     * Creates new form TelaSolicitarMedicacao
     */
    public TelaCadastroPedidoMedicacao() {
        initComponents();
        txtUsuario.setText("" + Singleton.getUsuario());
        letrasemNegrito();
        setIcon();
        listarSolicitacao();
        populaJComboBoxSetorHospitalar();
        pintarColumnaTabla();
        ValidaEnter ve = new ValidaEnter();
        ve.ValidaEnterPainel(jPanel1);
        //panel2.setBackground(new Color(0,153,204,200));
        conexao = Conexao.conector();
        AutoCompleteDecorator.decorate(JcSetorPedido);
        AutoCompleteDecorator.decorate(JcStatus);
        areaPedidoMedicacao.setLineWrap(true);
        areaPedidoMedicacao.setWrapStyleWord(true);
    }

    void pintarColumnaTabla() {
        ColorearFilasSolicitacao color = new ColorearFilasSolicitacao(5);
        jTableSolicitacao.getColumnModel().getColumn(5).setCellRenderer(color);
    }

    private void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("Receita.png")));
    }

    // icone do joptionPane
    Icon icon = new javax.swing.ImageIcon(getClass().getResource("/Imagens/ok.png"));

    // icone do joptionPane
    Icon iconExcluir = new javax.swing.ImageIcon(getClass().getResource("/Imagens/Excluido.png"));

    private void habilitaCampos() {
        JcSetorPedido.setEnabled(true);
        areaPedidoMedicacao.setEnabled(true);
        JcStatus.setEnabled(true);
        txtDataPedido.setEnabled(true);
        
    }

    private void desaabilitaCampos() {
        JcSetorPedido.setEnabled(false);
        areaPedidoMedicacao.setEnabled(false);
        JcStatus.setEnabled(false);
        txtDataPedido.setEnabled(false);
        txtUsuario.setEnabled(false);
        
    }

    private void limparCampos() {
        JcSetorPedido.setSelectedIndex(0);
        areaPedidoMedicacao.setText("");
        JcStatus.setSelectedIndex(0);
        //txtDataPedido.getEditor().setText("");
        

    }

    // metodo para colocar a letra em negrito
    Font fonte = new Font("Arial", Font.BOLD, 12);

    private void letrasemNegrito() {
        JcSetorPedido.setFont(fonte);
        areaPedidoMedicacao.setFont(fonte);
        JcStatus.setFont(fonte);
        txtDataPedido.setFont(fonte);
        txtUsuario.setFont(fonte);
       
    }
    
    
      // lista na tabelinha 
    public void listarMedicacao() {
         Conexao conec = new Conexao();
        DefaultTableModel model = (DefaultTableModel) tabelaNomeMedicacao.getModel();
        model.setNumRows(0);
        String sql = "Select NomeMedicacao,ViaAcesso,Intervalo from tb_medicacao";
        try {
            pst = Conexao.conector().prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("NomeMedicacao"),
                    rs.getString("ViaAcesso"),
                    rs.getString("Intervalo"),});
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
    
    public void pesquisarMedicacao() {
         Conexao conec = new Conexao();
        DefaultTableModel model = (DefaultTableModel) tabelaNomeMedicacao.getModel();
        model.setNumRows(0);
        //conexão com o banco de dados
        String sql = "Select *from tb_medicacao where UsoMedicacao like ?";

        try {
            pst = Conexao.conector().prepareStatement(sql);
            pst.setString(1, txtPesquisar.getText() + "%");
            rs = pst.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("UsoMedicacao"),});
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

    public void listarSolicitacao() {
        Conexao conec = new Conexao();
        DefaultTableModel model = (DefaultTableModel) jTableSolicitacao.getModel();
        model.setNumRows(0);
        String sql = "Select codigo,DataPedido,setorHospitalar,TipoMedicacao,Solicitante,StatusEntrega from tb_Solicitacao order by codigo";
        try {
            pst = Conexao.conector().prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("codigo"),
                    Utils.convertData(rs.getDate("DataPedido")),
                    rs.getString("setorHospitalar"),
                    rs.getString("TipoMedicacao"),
                     rs.getString("Solicitante"),
                    rs.getString("StatusEntrega"),});
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

    public void SalvarSolicitacao() {
        Conexao conec = new Conexao();
        String sql = "insert into tb_Solicitacao(DataPedido,setorHospitalar,StatusEntrega,TipoMedicacao,Solicitante)values(?,?,?,?,?)";

        try {
            pst = Conexao.conector().prepareStatement(sql);
            pst.setDate(1, convertDateSalvar(txtDataPedido.getDate()));
            pst.setString(2, JcSetorPedido.getSelectedItem().toString());
            pst.setString(3, JcStatus.getSelectedItem().toString());
            pst.setString(4, areaPedidoMedicacao.getText());
            pst.setString(5, txtUsuario.getText());
            pst.execute();
            JOptionPane.showMessageDialog(rootPane, " Solicitação do medicamento " + areaPedidoMedicacao.getText()+ " salvo com sucesso.", "Inclusão", JOptionPane.INFORMATION_MESSAGE, icon);
            listarSolicitacao();
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

    public void alterarSolicitacao() {
        Conexao conec = new Conexao();
        String sql = "update tb_Solicitacao set DataPedido=?,SetorHospitalar=?,StatusEntrega=?,TipoMedicacao=?,Solicitante=? where codigo=?";

        try {
            pst = Conexao.conector().prepareStatement(sql);
            pst.setDate(1, convertDateSalvar(txtDataPedido.getDate()));
            pst.setString(2, JcSetorPedido.getSelectedItem().toString());
            pst.setString(3, JcStatus.getSelectedItem().toString());
            pst.setString(4, areaPedidoMedicacao.getText());
            pst.setString(5, txtUsuario.getText());
            pst.setInt(6, Integer.parseInt(txtCodigo.getText()));
            pst.executeUpdate();
            JOptionPane.showMessageDialog(rootPane, " Solicitação do medicamento " + areaPedidoMedicacao.getText() + " Alterado com sucesso.", "Alteração", JOptionPane.INFORMATION_MESSAGE, icon);
            listarSolicitacao();
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

    public void removerSolicitacao() {
        Conexao conec = new Conexao();
        String sql = "delete from tb_Solicitacao where codigo=?";

        try {
            pst = Conexao.conector().prepareStatement(sql);
            pst.setInt(1, Integer.parseInt(txtCodigo.getText()));
            pst.execute();
            JOptionPane.showMessageDialog(rootPane, " Solicitação do medicamento " + areaPedidoMedicacao.getText()+ " Excluido com sucesso.", "Exclusão", JOptionPane.INFORMATION_MESSAGE, iconExcluir);
            listarSolicitacao();
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

    public void mostrarItens() {
        int seleciona = jTableSolicitacao.getSelectedRow();
        txtCodigo.setText(jTableSolicitacao.getModel().getValueAt(seleciona, 0).toString());
        txtDataPedido.setDate(Utils.convertData(jTableSolicitacao.getModel().getValueAt(seleciona, 1).toString()));
        JcSetorPedido.setSelectedItem(jTableSolicitacao.getModel().getValueAt(seleciona, 2).toString());
        areaPedidoMedicacao.setText(jTableSolicitacao.getModel().getValueAt(seleciona, 3).toString());
        txtUsuario.setText(jTableSolicitacao.getModel().getValueAt(seleciona, 4).toString());
        JcStatus.setSelectedItem(jTableSolicitacao.getModel().getValueAt(seleciona, 5).toString());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        LUPA_NOME_MEDICACAO = new javax.swing.JDialog();
        txtPesquisar = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tabelaNomeMedicacao = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        labelCodigo = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        txtDataPedido = new org.jdesktop.swingx.JXDatePicker();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        JcStatus = new javax.swing.JComboBox<>();
        jLabel27 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        JcSetorPedido = new javax.swing.JComboBox<>();
        jLabel28 = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        areaPedidoMedicacao = new javax.swing.JTextArea();
        btnMedicacao = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        btnNovo = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableSolicitacao = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnSair = new javax.swing.JButton();

        LUPA_NOME_MEDICACAO.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPesquisarKeyReleased(evt);
            }
        });
        LUPA_NOME_MEDICACAO.getContentPane().add(txtPesquisar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 33, 560, 37));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("Pesquisar Medicação por Nome:");
        LUPA_NOME_MEDICACAO.getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 11, -1, -1));

        tabelaNomeMedicacao.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Nome Medicação ", "Via Acesso", "Intervalo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
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
        jScrollPane6.setViewportView(tabelaNomeMedicacao);

        LUPA_NOME_MEDICACAO.getContentPane().add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 81, 560, 260));

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Tela Solicitação de Insumos e Medicações");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        labelCodigo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        labelCodigo.setText("CÓDIGO");

        txtCodigo.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtCodigo.setEnabled(false);

        txtDataPedido.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtDataPedido.setEnabled(false);

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel25.setText("DATA PEDIDO:");

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel26.setText("SETOR DO PEDIDO:");

        JcStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<Digite Aqui>", "Solicitado" }));
        JcStatus.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        JcStatus.setEnabled(false);

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel27.setText("STATUS:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("MEDICAÇÃO:");

        JcSetorPedido.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<Digite Aqui>" }));
        JcSetorPedido.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        JcSetorPedido.setEnabled(false);

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel28.setText("SOLICITANTE:");

        txtUsuario.setBackground(new java.awt.Color(102, 153, 255));
        txtUsuario.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtUsuario.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtUsuario.setEnabled(false);

        areaPedidoMedicacao.setColumns(20);
        areaPedidoMedicacao.setRows(5);
        areaPedidoMedicacao.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                areaPedidoMedicacaoFocusGained(evt);
            }
        });
        jScrollPane2.setViewportView(areaPedidoMedicacao);

        btnMedicacao.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnMedicacao.setForeground(new java.awt.Color(51, 51, 51));
        btnMedicacao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Create.png"))); // NOI18N
        btnMedicacao.setText("MEDICAÇÃO");
        btnMedicacao.setEnabled(false);
        btnMedicacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMedicacaoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(labelCodigo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtCodigo))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel25)
                                    .addComponent(txtDataPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(JcSetorPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel26))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(JcStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel27))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel28)
                                    .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 358, Short.MAX_VALUE)
                        .addComponent(btnMedicacao)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnMedicacao, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 2, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel27)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(JcStatus))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(jLabel26)
                                            .addGap(5, 5, 5)
                                            .addComponent(JcSetorPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(labelCodigo)
                                                .addComponent(jLabel25))
                                            .addGap(5, 5, 5)
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(txtDataPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel28)
                                        .addGap(5, 5, 5)
                                        .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addGap(4, 4, 4)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 79, 1150, 190));

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 703, Short.MAX_VALUE)
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

        getContentPane().add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 500, 1150, -1));

        jTableSolicitacao.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Data Pedido", "Setor", "Medicação Solicitada", "Solicitante", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableSolicitacao.setFocusable(false);
        jTableSolicitacao.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableSolicitacaoMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTableSolicitacaoMouseReleased(evt);
            }
        });
        jTableSolicitacao.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTableSolicitacaoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTableSolicitacaoKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jTableSolicitacao);
        if (jTableSolicitacao.getColumnModel().getColumnCount() > 0) {
            jTableSolicitacao.getColumnModel().getColumn(0).setMinWidth(50);
            jTableSolicitacao.getColumnModel().getColumn(0).setPreferredWidth(50);
            jTableSolicitacao.getColumnModel().getColumn(0).setMaxWidth(50);
            jTableSolicitacao.getColumnModel().getColumn(1).setMinWidth(80);
            jTableSolicitacao.getColumnModel().getColumn(1).setPreferredWidth(80);
            jTableSolicitacao.getColumnModel().getColumn(1).setMaxWidth(80);
            jTableSolicitacao.getColumnModel().getColumn(2).setMinWidth(100);
            jTableSolicitacao.getColumnModel().getColumn(2).setPreferredWidth(100);
            jTableSolicitacao.getColumnModel().getColumn(2).setMaxWidth(100);
            jTableSolicitacao.getColumnModel().getColumn(4).setMinWidth(150);
            jTableSolicitacao.getColumnModel().getColumn(4).setPreferredWidth(150);
            jTableSolicitacao.getColumnModel().getColumn(4).setMaxWidth(150);
            jTableSolicitacao.getColumnModel().getColumn(5).setMinWidth(100);
            jTableSolicitacao.getColumnModel().getColumn(5).setPreferredWidth(100);
            jTableSolicitacao.getColumnModel().getColumn(5).setMaxWidth(100);
        }

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 280, 1150, 210));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setText("Solicitação de Insumos e Medicações");

        btnSair.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnSair.setForeground(new java.awt.Color(51, 51, 51));
        btnSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagensTelas/Sair.png"))); // NOI18N
        btnSair.setText("SAIR");
        btnSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSairActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 709, Short.MAX_VALUE)
                .addComponent(btnSair)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(btnSair, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 11, 1150, -1));

        setSize(new java.awt.Dimension(1185, 615));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed
        txtDataPedido.requestFocus();
        btnEditar.setEnabled(false);
        btnExcluir.setEnabled(false);
        btnSalvar.setEnabled(true);
        habilitaCampos();
        limparCampos();
    }//GEN-LAST:event_btnNovoActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        alterarSolicitacao();
        listarSolicitacao();
        desaabilitaCampos();
        limparCampos();
        btnNovo.setEnabled(true);
        btnEditar.setEnabled(false);
        btnExcluir.setEnabled(false);
        txtUsuario.setText("");
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        int i = jTableSolicitacao.getSelectedRow();
        if (i < 0) {
            Msg.alerta(this, "Selecione um Registro!");
            return;
        }
        removerSolicitacao();
        listarSolicitacao();
        desaabilitaCampos();
        limparCampos();
        btnNovo.setEnabled(true);
        btnEditar.setEnabled(false);
        btnExcluir.setEnabled(false);
        txtUsuario.setText("");
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        if (txtDataPedido.getDate() == null) {
            JOptionPane.showMessageDialog(rootPane, "Campo Data Pedido é Obrigatório", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (JcSetorPedido.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(rootPane, "O Setor do pedido é obrigatório", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (JcStatus.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(rootPane, "O Status é obrigatório", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (areaPedidoMedicacao.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "O Pedido é obrigatório", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        SalvarSolicitacao();
        listarSolicitacao();
        limparCampos();
        desaabilitaCampos();
        btnSalvar.setEnabled(false);
        btnNovo.setEnabled(true);
        txtUsuario.setText("");
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void jTableSolicitacaoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableSolicitacaoMouseClicked
        mostrarItens();
        habilitaCampos();
        btnEditar.setEnabled(true);
        btnExcluir.setEnabled(true);
        btnSalvar.setEnabled(false);
        btnNovo.setEnabled(false);
    }//GEN-LAST:event_jTableSolicitacaoMouseClicked

    private void jTableSolicitacaoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableSolicitacaoMouseReleased

    }//GEN-LAST:event_jTableSolicitacaoMouseReleased

    private void jTableSolicitacaoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTableSolicitacaoKeyReleased
        mostrarItens();
    }//GEN-LAST:event_jTableSolicitacaoKeyReleased

    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed

        dispose();

    }//GEN-LAST:event_btnSairActionPerformed

    private void jTableSolicitacaoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTableSolicitacaoKeyPressed
        mostrarItens();
    }//GEN-LAST:event_jTableSolicitacaoKeyPressed

    private void btnMedicacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMedicacaoActionPerformed
        LUPA_NOME_MEDICACAO.setSize(new java.awt.Dimension(600, 400));
        LUPA_NOME_MEDICACAO.setLocationRelativeTo(null);
        listarMedicacao();
        LUPA_NOME_MEDICACAO.setVisible(true);
        areaPedidoMedicacao.requestFocus();
    }//GEN-LAST:event_btnMedicacaoActionPerformed

    private void areaPedidoMedicacaoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_areaPedidoMedicacaoFocusGained
        btnMedicacao.setEnabled(true);
    }//GEN-LAST:event_areaPedidoMedicacaoFocusGained

    private void txtPesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisarKeyReleased
        pesquisarMedicacao();
    }//GEN-LAST:event_txtPesquisarKeyReleased

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

        // seleciona a linha da tabela e seta nos campos sem quebra de linha
        int linha = tabelaNomeMedicacao.getSelectedRow();
        if (linha >= 0) {
            // esse codigo faz com que as linhas selecionadas da tabela fiquem uma abaixo da outra
            String dados0 = (tabelaNomeMedicacao.getValueAt(linha, 0).toString());
            String dados1 = (tabelaNomeMedicacao.getValueAt(linha, 1).toString());
            String dados2 = (tabelaNomeMedicacao.getValueAt(linha, 2).toString());
            setar_dados = setar_dados + "\n" +  dados0;
            setar_dados = setar_dados + "     -     " + dados1 ;
            setar_dados = setar_dados + "     -     " + dados2 ;
            areaPedidoMedicacao.setText(setar_dados);
            LUPA_NOME_MEDICACAO.setVisible(false);
        }
    }//GEN-LAST:event_tabelaNomeMedicacaoMouseClicked

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
            java.util.logging.Logger.getLogger(TelaCadastroPedidoMedicacao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaCadastroPedidoMedicacao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaCadastroPedidoMedicacao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaCadastroPedidoMedicacao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaCadastroPedidoMedicacao().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JComboBox<String> JcSetorPedido;
    public javax.swing.JComboBox<String> JcStatus;
    private javax.swing.JDialog LUPA_NOME_MEDICACAO;
    private javax.swing.JTextArea areaPedidoMedicacao;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnMedicacao;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnSair;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTable jTableSolicitacao;
    private javax.swing.JLabel labelCodigo;
    private javax.swing.JTable tabelaNomeMedicacao;
    private javax.swing.JTextField txtCodigo;
    private org.jdesktop.swingx.JXDatePicker txtDataPedido;
    private javax.swing.JTextField txtPesquisar;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables

    //metodo converte data
    private java.sql.Date convertDateSalvar(java.util.Date var) throws SQLException {
        int year = var.getDate();
        int month = var.getMonth();
        int day = var.getYear();
        return new java.sql.Date(day, month, year);
    }

    public void populaJComboBoxSetorHospitalar() {
        String sql = "Select *from tb_setorhospitalar";
        try {
            pst = Conexao.conector().prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                JcSetorPedido.addItem(rs.getString("setorHospitalar"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

}
