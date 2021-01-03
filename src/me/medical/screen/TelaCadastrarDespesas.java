/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.medical.screen;

import me.medical.controller.Conexao;
import me.medical.model.Despesa;
import me.medical.model.DespesaPaciente;
import me.medical.model.ProdutoBean;
import me.medical.utils.Msg;
import me.medical.utils.Utils;
import me.medical.utils.ValidaEnter;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.Icon;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author Adriano Zanette
 */
public class TelaCadastrarDespesas extends javax.swing.JFrame {

    private boolean inserir;
    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    private List<ProdutoBean> listaProduto = new ArrayList<>();
    private Despesa despesa = new Despesa();

    /**
     * Creates new form TelaCadastrarDespesas
     */
    public TelaCadastrarDespesas() {
        initComponents();
        setIcon();
        atalho();
        lblcodigo.setVisible(false);
        txtCodigo.setVisible(false);
        populaJComboBoxProdutos();
        AutoCompleteDecorator.decorate(jCAcomodacao);
        Utils.considerarEnterComoTab(txtDataEntrada);
        Utils.considerarEnterComoTab(txtDataSaida);
        Utils.considerarEnterComoTab(cbProduto);
        Utils.considerarEnterComoTab(jCAcomodacao);
        Utils.considerarEnterComoTab(txQtde);
        Utils.considerarEnterComoTab(txtValorDiarias);
        Utils.considerarEnterComoTab(txTotalVenda);
        Utils.considerarEnterComoTab(txDinheiro);
        this.getRootPane().setDefaultButton(btnAdicionar);
        //chama a classe valida enter
        ValidaEnter ve = new ValidaEnter();
        ve.ValidaEnterPainel(jPanel3);
        conexao = Conexao.conector();
        letrasemNegrito();
        
    }

    // metodo para colocar a letra em negrito
    Font fonte = new Font("Arial", Font.BOLD, 12);

    private void letrasemNegrito() {

        txtCPF.setFont(fonte);
        txtTelefone.setFont(fonte);
        jCAcomodacao.setFont(fonte);
        txtDataEntrada.setFont(fonte);
        txtDataSaida.setFont(fonte);
        txtDiasEstadia.setFont(fonte);
        txtValorDiarias.setFont(fonte);
        txtEmail.setFont(fonte);
        txtDataCadastro.setFont(fonte);
    }

    private void limparcampos() {

//        txtCPF.setText("");
//        txtTelefone.setText("");
//        jCAcomodacao.setSelectedIndex(0);
//        txtDataEntrada.getEditor().setText("");
//        txtDataSaida.getEditor().setText("");
//        txtDiasEstadia.setText("");
//        txtValorDiarias.setText("");
        cbProduto.setSelectedIndex(0);
        txQtde.setText("1");
        cbProduto.requestFocus();
        txValorUn.setText("0,00");
    }

    private void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("Receita.png")));
    }

    // icone do joptionPane
    Icon icon = new javax.swing.ImageIcon(getClass().getResource("/Imagens/ok.png"));

    // icone do joptionPane
    Icon iconExcluir = new javax.swing.ImageIcon(getClass().getResource("/Imagens/Excluido.png"));

    public void listarNomePaciente() {
        Conexao conec = new Conexao();
        String sql = "Select Prontuario,NomePaciente from tb_agenda order by codigo Asc";
        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            tabelaNomePaciente.setModel(DbUtils.resultSetToTableModel(rs));

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

    public void listarProdutos() {
        Conexao conec = new Conexao();
        DefaultTableModel model = (DefaultTableModel) tabelaProdutos.getModel();
        model.setNumRows(0);
        String sql = "Select codigo,Produto,Valor from tb_produtos order by codigo";
        try {
            pst = Conexao.conector().prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {

                model.addRow(new Object[]{
                    rs.getInt("codigo"),
                    rs.getString("Produto"),
                    rs.getString("Valor"),});
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

    private void atualizaTabela() {
        try {
            DefaultTableModel model = (DefaultTableModel) tabelaDespesas.getModel();
            model.setNumRows(0);
            for (DespesaPaciente dp : despesa.getListaDespesa()) {
                model.addRow(new Object[]{
                    dp.getProduto().getIdProduto(),
                    dp.getProduto().getProduto(),
                    Utils.convertDouble(dp.getQuantidade()),
                    Utils.convertDouble(dp.getValor()),
                    Utils.convertDouble(dp.getValor() * dp.getQuantidade())
                });
            }
            calculaTroco();
        } catch (Exception e) {
            e.printStackTrace();
            Msg.erro(this, "Erro ao Atualizar Tabela!");
        }
    }

    private void calculaTroco() {
        calculaValorTotal();
        try {
            double valorTotal = Double.parseDouble(txTotalVenda.getText().replace("R$ ", "").replace(",", "."));
            double dinheiro = Double.parseDouble(txDinheiro.getText().replace("R$ ", "").replace(",", "."));
            txTroco.setText(Utils.convertDouble(dinheiro - valorTotal));
        } catch (Exception e) {
        }
    }

    private void calculaValorDiarias() {
        try {
            double diasEstadia = Double.parseDouble(txtDiasEstadia.getText());
            double valorDiaria = Double.parseDouble(txtValorDiarias.getText().replace("R$ ", "").replace(",", "."));
            txtValorTotalDiarias.setText(Utils.convertDouble(diasEstadia * valorDiaria));
        } catch (Exception e) {
        }
    }

    private void calculaDias() {
        try {
            txtDiasEstadia.setText(Utils.calcularDias(txtDataEntrada.getDate(), txtDataSaida.getDate()) + "");
        } catch (Exception e) {
        }
    }

    private void calculaValorTotal() {
        try {
            double total = 0.0;
            for (int i = 0; i < tabelaDespesas.getRowCount(); i++) {
                double valor = Double.parseDouble(tabelaDespesas.getValueAt(i, 4).toString().replace(",", "."));
                total += valor;
            }
            double valorTotalDiarias = Double.parseDouble(txtValorTotalDiarias.getText().replace("R$ ", "").replace(",", "."));
            txTotalVenda.setText(Utils.convertDouble(total + valorTotalDiarias));
        } catch (Exception e) {
        }
    }

    private void adiciona() {
        try {
            if (cbProduto.getSelectedIndex() == 0) {
                Msg.alerta(this, "Selecione Um Produto!");
                return;
            } else {
                DespesaPaciente dp = new DespesaPaciente();
                ProdutoBean pt = listaProduto.get(cbProduto.getSelectedIndex() - 1);
                dp.setProduto(pt);//seta qual o produto
                dp.setQuantidade(Double.parseDouble(txQtde.getText().replace(",", ".")));//seta a quantidade
                dp.setValor(Double.parseDouble(txValorUn.getText().replace(",", ".")));//seta o valor
                despesa.getListaDespesa().add(dp);//adiciona na Lista

                atualizaTabela();
                limparcampos();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Msg.erro(this, "Erro ao Adicionar o Produto!");
        }
    }

    private void salvarItens(int idDespesa) {
        System.out.println("Ultimo Id: "+idDespesa);
        try {
            for (int i = 0; i < tabelaDespesas.getRowCount(); i++) {
                Conexao conec = new Conexao();
                String sql = "INSERT INTO `despesaspaciente` (`produto`, `valor`, `quantidade`, `valorTotal`, `idDespesa`) VALUES (?, ?, ?, ?, ?);";
                pst = Conexao.conector().prepareStatement(sql);
                pst.setString(1, tabelaDespesas.getValueAt(i, 1).toString());
                pst.setDouble(2, Double.parseDouble(tabelaDespesas.getValueAt(i, 3).toString().replace(",", ".")));
                pst.setDouble(3, Double.parseDouble(tabelaDespesas.getValueAt(i, 2).toString().replace(",", ".")));
                pst.setDouble(4, Double.parseDouble(tabelaDespesas.getValueAt(i, 4).toString().replace(",", ".")));
                pst.setInt(5, idDespesa);
                pst.execute();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Msg.erro(this, "Erro ao Slvar Itens");
        }
    }

    private void btnSalvar() {
        // if (isInserir()) {
        //
        Conexao conec = new Conexao();
        String sql = "INSERT INTO `tb_cad_despesas` (`NomePaciente`,`Prontuario`, `Cpf`, `Telefone`, "
                + "`TipoAcomodacao`, `DataEntrada`, `DataSaida`, `qtdDias`, `ValorDiarias`, `ValorTotalDiarias`, "
                + "`ValorTotal`, `Dinheiro`, `Troco`, `Email`,`DataCadastro`) VALUES (?,?,?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?,?,?);";
        try {
           
            pst = Conexao.conector().prepareStatement(sql);
            pst.setString(1, txtNomePaciente.getText());
            pst.setString(2, txtProntuario.getText());
            pst.setString(3, txtCPF.getText());
            pst.setString(4, txtTelefone.getText());
            pst.setString(5, jCAcomodacao.getSelectedItem().toString());
            pst.setDate(6, convertDateSalvar(txtDataEntrada.getDate()));
            pst.setDate(7, convertDateSalvar(txtDataSaida.getDate()));
            pst.setDouble(8, Double.parseDouble(txtDiasEstadia.getText()));
            pst.setDouble(9, Double.parseDouble(txtValorDiarias.getText().replace(",", ".")));
            pst.setDouble(10, Double.parseDouble(txtValorTotalDiarias.getText().replace(",", ".")));
            pst.setDouble(11, Double.parseDouble(txTotalVenda.getText().replace(",", ".")));
            pst.setDouble(12, Double.parseDouble(txDinheiro.getText().replace(",", ".")));
            pst.setDouble(13, Double.parseDouble(txTroco.getText().replace(",", ".")));
            pst.setString(14, txtEmail.getText());
            pst.setDate(15, convertDateSalvar(txtDataCadastro.getDate()));
            pst.execute();
            //pega o id da despesa inserida no banco de dados
            String sql1 = "SELECT IFNULL(MAX(tb_cad_despesas.codigo),1) AS ID FROM tb_cad_despesas";
            pst = Conexao.conector().prepareStatement(sql1);
            rs = pst.executeQuery();
            rs.next();
            salvarItens(rs.getInt("ID"));//salva os itens da tabela
            JOptionPane.showMessageDialog(rootPane, " Despesas do Paciente " + txtNomePaciente.getText().toUpperCase() + " foram salvo com sucesso.", "Inclusão", JOptionPane.INFORMATION_MESSAGE,icon);
//            Msg.informacao(this, "Salvo Com Sucesso!");
            this.dispose();
        } catch (SQLException error) {
            JOptionPane.showMessageDialog(null, error);
        }
//        } else {
//
//            this.dispose();
//        }
    }

    //metodo converte data
    private java.sql.Date convertDateSalvar(java.util.Date var) throws SQLException {
        int year = var.getDate();
        int month = var.getMonth();
        int day = var.getYear();
        return new java.sql.Date(day, month, year);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        LUPA_NOME_PACIENTE = new javax.swing.JDialog();
        jScrollPane6 = new javax.swing.JScrollPane();
        tabelaNomePaciente = new javax.swing.JTable();
        LUPA_PRODUTOS = new javax.swing.JDialog();
        jScrollPane7 = new javax.swing.JScrollPane();
        tabelaProdutos = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtNomePaciente = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        txtProntuario = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        lblcodigo = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtCPF = new javax.swing.JFormattedTextField();
        jLabel4 = new javax.swing.JLabel();
        txtTelefone = new javax.swing.JFormattedTextField();
        txtEmail = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtDataEntrada = new org.jdesktop.swingx.JXDatePicker();
        jLabel8 = new javax.swing.JLabel();
        txtDataSaida = new org.jdesktop.swingx.JXDatePicker();
        jLabel10 = new javax.swing.JLabel();
        txtDiasEstadia = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtValorTotalDiarias = new javax.swing.JTextField();
        txtValorDiarias = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jCAcomodacao = new javax.swing.JComboBox<>();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaDespesas = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jPanel7 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        txTotalVenda = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        txTroco = new javax.swing.JTextField();
        txDinheiro = new javax.swing.JFormattedTextField();
        jPanel8 = new javax.swing.JPanel();
        txQtde = new javax.swing.JFormattedTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        btnAdicionar = new javax.swing.JButton();
        cbProduto = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        txValorUn = new javax.swing.JFormattedTextField();
        jPanel2 = new javax.swing.JPanel();
        txtDataCadastro = new org.jdesktop.swingx.JXDatePicker();

        LUPA_NOME_PACIENTE.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tabelaNomePaciente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Prontuario", "Nome do Paciente"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaNomePaciente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaNomePacienteMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(tabelaNomePaciente);
        if (tabelaNomePaciente.getColumnModel().getColumnCount() > 0) {
            tabelaNomePaciente.getColumnModel().getColumn(0).setMinWidth(60);
            tabelaNomePaciente.getColumnModel().getColumn(0).setPreferredWidth(60);
            tabelaNomePaciente.getColumnModel().getColumn(0).setMaxWidth(60);
        }

        LUPA_NOME_PACIENTE.getContentPane().add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 1, 550, 320));

        LUPA_PRODUTOS.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tabelaProdutos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Codigo", "Descrição", "Valor"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaProdutos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaProdutosMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(tabelaProdutos);
        if (tabelaProdutos.getColumnModel().getColumnCount() > 0) {
            tabelaProdutos.getColumnModel().getColumn(0).setMinWidth(60);
            tabelaProdutos.getColumnModel().getColumn(0).setPreferredWidth(60);
            tabelaProdutos.getColumnModel().getColumn(0).setMaxWidth(60);
            tabelaProdutos.getColumnModel().getColumn(2).setMinWidth(100);
            tabelaProdutos.getColumnModel().getColumn(2).setPreferredWidth(100);
            tabelaProdutos.getColumnModel().getColumn(2).setMaxWidth(100);
        }

        LUPA_PRODUTOS.getContentPane().add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 1, 550, 320));

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Tela Contas a Pagar");

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("Nome Paciente:");

        txtNomePaciente.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtNomePaciente.setEnabled(false);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Search.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        txtProntuario.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtProntuario.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtProntuario.setEnabled(false);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setText("Prontuário:");

        lblcodigo.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblcodigo.setText("Código:");

        txtCodigo.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtCodigo.setEnabled(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblcodigo)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtProntuario, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNomePaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 712, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(40, 40, 40)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblcodigo)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtCodigo)
                    .addComponent(txtProntuario)
                    .addComponent(txtNomePaciente))
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Responsável Legal"));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("CPF:");

        txtCPF.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        try {
            txtCPF.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.###-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtCPF.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCPF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCPFFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCPFFocusLost(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText("Telefone:");

        txtTelefone.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        try {
            txtTelefone.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##)9####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtTelefone.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtTelefoneFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTelefoneFocusLost(evt);
            }
        });

        txtEmail.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtEmail.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtEmailFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtEmailFocusLost(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel13.setText("Telefone:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCPF, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtTelefone)
                        .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel13))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(jLabel4)
                        .addComponent(txtCPF, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {txtCPF, txtEmail, txtTelefone});

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Gastos com Diária de internação"));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setText("Data Internação:");

        txtDataEntrada.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtDataEntrada.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDataEntradaFocusGained(evt);
            }
        });
        txtDataEntrada.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txtDataEntradaPropertyChange(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setText("Data Saída:");

        txtDataSaida.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtDataSaida.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txtDataSaidaPropertyChange(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel10.setText("Qtd Dias:");

        txtDiasEstadia.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtDiasEstadia.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtDiasEstadia.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtDiasEstadia.setEnabled(false);
        txtDiasEstadia.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDiasEstadiaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDiasEstadiaFocusLost(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel11.setText("R$Valor Total Diarias:");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel12.setText("R$Valor da Diaria :");

        txtValorTotalDiarias.setBackground(new java.awt.Color(153, 204, 255));
        txtValorTotalDiarias.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtValorTotalDiarias.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtValorTotalDiarias.setText("0,00");
        txtValorTotalDiarias.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txtValorDiarias.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtValorDiarias.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtValorDiarias.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtValorDiarias.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtValorDiariasFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtValorDiariasFocusLost(evt);
            }
        });
        txtValorDiarias.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtValorDiariasKeyReleased(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel9.setText("Tipo de Acomodação:");

        jCAcomodacao.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "< Selecione >", "UTI", "Enfermaria", "Apartamento" }));
        jCAcomodacao.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jCAcomodacao.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jCAcomodacaoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jCAcomodacaoFocusLost(evt);
            }
        });
        jCAcomodacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCAcomodacaoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCAcomodacao, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(10, 10, 10)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(txtDataEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtDataSaida, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(txtDiasEstadia, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(txtValorDiarias))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(txtValorTotalDiarias, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(0, 1, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel7)
                                        .addComponent(jLabel9)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jCAcomodacao, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtDataEntrada, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                                    .addComponent(txtDataSaida, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(1, 1, 1))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDiasEstadia))))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtValorTotalDiarias)
                            .addComponent(txtValorDiarias, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE))))
                .addContainerGap())
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tabelaDespesas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código Produto", "Descrição Produto", "Quantidade", "Valor Unitário", "Valor Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaDespesas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaDespesasMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelaDespesas);
        if (tabelaDespesas.getColumnModel().getColumnCount() > 0) {
            tabelaDespesas.getColumnModel().getColumn(0).setMinWidth(100);
            tabelaDespesas.getColumnModel().getColumn(0).setPreferredWidth(100);
            tabelaDespesas.getColumnModel().getColumn(0).setMaxWidth(100);
            tabelaDespesas.getColumnModel().getColumn(2).setMinWidth(100);
            tabelaDespesas.getColumnModel().getColumn(2).setPreferredWidth(100);
            tabelaDespesas.getColumnModel().getColumn(2).setMaxWidth(100);
            tabelaDespesas.getColumnModel().getColumn(3).setMinWidth(100);
            tabelaDespesas.getColumnModel().getColumn(3).setPreferredWidth(100);
            tabelaDespesas.getColumnModel().getColumn(3).setMaxWidth(100);
            tabelaDespesas.getColumnModel().getColumn(4).setMinWidth(100);
            tabelaDespesas.getColumnModel().getColumn(4).setPreferredWidth(100);
            tabelaDespesas.getColumnModel().getColumn(4).setMaxWidth(100);
        }

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 925, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Serviços e Insumos", jPanel5);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 929, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Observações", jPanel6);

        jPanel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/cancelar.png"))); // NOI18N
        jButton3.setText("Cancelar (ESC)");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel7.add(jButton3);

        btnSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagensTelas/Editar.png"))); // NOI18N
        btnSalvar.setText("Salvar (F6)");
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });
        jPanel7.add(btnSalvar);

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel17.setText("TOTAL:");

        txTotalVenda.setBackground(new java.awt.Color(255, 255, 153));
        txTotalVenda.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txTotalVenda.setText("0,00");

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel18.setText("DINHEIRO:");

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel19.setText("TROCO:");

        txTroco.setBackground(new java.awt.Color(102, 153, 255));
        txTroco.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txTroco.setText("0,00");

        txDinheiro.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txDinheiro.setText("0,00");
        txDinheiro.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txDinheiro.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txDinheiroFocusGained(evt);
            }
        });
        txDinheiro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txDinheiroKeyReleased(evt);
            }
        });

        jPanel8.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txQtde.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txQtde.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        txQtde.setText("1");
        txQtde.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txQtde.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txQtdeFocusGained(evt);
            }
        });
        txQtde.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txQtdeMouseClicked(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel16.setText("Qtde:");

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel15.setText("Produtos");

        btnAdicionar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnAdicionar.setText("+ Adicionar");
        btnAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarActionPerformed(evt);
            }
        });

        cbProduto.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        cbProduto.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "<Selecione Produtos>" }));
        cbProduto.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        cbProduto.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbProdutoItemStateChanged(evt);
            }
        });
        cbProduto.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cbProdutoPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        cbProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbProdutoActionPerformed(evt);
            }
        });
        cbProduto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                cbProdutoKeyTyped(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("Valor Un:");

        txValorUn.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txValorUn.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        txValorUn.setText("0,00");
        txValorUn.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txValorUn.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txValorUnFocusGained(evt);
            }
        });
        txValorUn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txValorUnMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 508, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txQtde, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txValorUn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnAdicionar)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txValorUn, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAdicionar)
                    .addComponent(jLabel6)
                    .addComponent(txQtde)
                    .addComponent(jLabel16)
                    .addComponent(cbProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addContainerGap())
        );

        jPanel8Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnAdicionar, cbProduto, txQtde, txValorUn});

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Data Cadastro"));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtDataCadastro, javax.swing.GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtDataCadastro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel18)
                                    .addComponent(jLabel17))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txTotalVenda)
                                    .addComponent(txDinheiro, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel19)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txTroco, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txTotalVenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(txDinheiro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(txTroco))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        setSize(new java.awt.Dimension(982, 682));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jCAcomodacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCAcomodacaoActionPerformed

    }//GEN-LAST:event_jCAcomodacaoActionPerformed

    private void tabelaDespesasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaDespesasMouseClicked

    }//GEN-LAST:event_tabelaDespesasMouseClicked

    private void tabelaNomePacienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaNomePacienteMouseClicked
        // seleciona a linha da tabela e seta nos campos
        int linha = tabelaNomePaciente.getSelectedRow();
        if (linha >= 0) {
            // numero da coluna da tabelinha
            txtProntuario.setText(tabelaNomePaciente.getValueAt(linha, 0).toString());
            txtNomePaciente.setText(tabelaNomePaciente.getValueAt(linha, 1).toString());
            txtCPF.requestFocus();
            LUPA_NOME_PACIENTE.setVisible(false);
        }
    }//GEN-LAST:event_tabelaNomePacienteMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        LUPA_NOME_PACIENTE.setSize(new java.awt.Dimension(565, 300));
        LUPA_NOME_PACIENTE.setLocationRelativeTo(null);
        listarNomePaciente();
        LUPA_NOME_PACIENTE.setVisible(true);

    }//GEN-LAST:event_jButton1ActionPerformed

    private void tabelaProdutosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaProdutosMouseClicked

    }//GEN-LAST:event_tabelaProdutosMouseClicked

    private void cbProdutoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbProdutoItemStateChanged
        if (cbProduto.getSelectedIndex() > 0) {
            txValorUn.setText(Utils.convertDouble(listaProduto.get(cbProduto.getSelectedIndex() - 1).getValor()));
        }
    }//GEN-LAST:event_cbProdutoItemStateChanged

    private void cbProdutoPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cbProdutoPopupMenuWillBecomeInvisible

    }//GEN-LAST:event_cbProdutoPopupMenuWillBecomeInvisible

    private void cbProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbProdutoActionPerformed

    }//GEN-LAST:event_cbProdutoActionPerformed

    private void cbProdutoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbProdutoKeyTyped

    }//GEN-LAST:event_cbProdutoKeyTyped

    private void txQtdeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txQtdeFocusGained

    }//GEN-LAST:event_txQtdeFocusGained

    private void txQtdeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txQtdeMouseClicked

    }//GEN-LAST:event_txQtdeMouseClicked

    private void btnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarActionPerformed
        this.adiciona();
    }//GEN-LAST:event_btnAdicionarActionPerformed

    private void txDinheiroFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txDinheiroFocusGained
        txDinheiro.selectAll();
    }//GEN-LAST:event_txDinheiroFocusGained

    private void txDinheiroKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txDinheiroKeyReleased
        calculaTroco();
    }//GEN-LAST:event_txDinheiroKeyReleased

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        btnSalvar();
        // aqui chamo a tela de recibo
        int sair = JOptionPane.showConfirmDialog(null, "IMPRIMIR RECIBO AGORA ?", "ATENÇÃO", JOptionPane.YES_NO_OPTION);
        if (sair == JOptionPane.YES_OPTION) {
                    TelaRecibo tela = new TelaRecibo();
                    tela.setVisible(true);
        } else{
            
            this.dispose();
    }//GEN-LAST:event_btnSalvarActionPerformed
    }
    private void txValorUnFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txValorUnFocusGained
        txValorUn.selectAll();
    }//GEN-LAST:event_txValorUnFocusGained

    private void txValorUnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txValorUnMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txValorUnMouseClicked

    private void txtCPFFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCPFFocusGained
        txtCPF.setBackground(new Color(255, 255, 0));
    }//GEN-LAST:event_txtCPFFocusGained

    private void txtTelefoneFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTelefoneFocusGained
        txtTelefone.setBackground(new Color(255, 255, 0));
    }//GEN-LAST:event_txtTelefoneFocusGained

    private void txtDiasEstadiaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDiasEstadiaFocusGained
        txtDiasEstadia.setBackground(new Color(255, 255, 0));
    }//GEN-LAST:event_txtDiasEstadiaFocusGained

    private void txtValorDiariasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtValorDiariasFocusGained
        txtValorDiarias.setBackground(new Color(255, 255, 0));
    }//GEN-LAST:event_txtValorDiariasFocusGained

    private void txtCPFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCPFFocusLost
        txtCPF.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_txtCPFFocusLost

    private void txtTelefoneFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTelefoneFocusLost
        txtTelefone.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_txtTelefoneFocusLost

    private void txtDiasEstadiaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDiasEstadiaFocusLost
        txtDiasEstadia.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_txtDiasEstadiaFocusLost

    private void txtValorDiariasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtValorDiariasFocusLost
        txtValorDiarias.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_txtValorDiariasFocusLost

    private void jCAcomodacaoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jCAcomodacaoFocusGained

    }//GEN-LAST:event_jCAcomodacaoFocusGained

    private void jCAcomodacaoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jCAcomodacaoFocusLost

    }//GEN-LAST:event_jCAcomodacaoFocusLost

    private void txtDataEntradaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDataEntradaFocusGained
        txtDataEntrada.setBackground(new Color(255, 255, 0));
    }//GEN-LAST:event_txtDataEntradaFocusGained

    private void txtDataSaidaPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtDataSaidaPropertyChange
        calculaDias();
        calculaValorDiarias();
        calculaTroco();
    }//GEN-LAST:event_txtDataSaidaPropertyChange

    private void txtDataEntradaPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtDataEntradaPropertyChange
        calculaDias();
    }//GEN-LAST:event_txtDataEntradaPropertyChange

    private void txtValorDiariasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtValorDiariasKeyReleased
        calculaValorDiarias();
        calculaTroco();
    }//GEN-LAST:event_txtValorDiariasKeyReleased

    private void txtEmailFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtEmailFocusGained
       txtEmail.setBackground(new Color(255, 255, 0));
    }//GEN-LAST:event_txtEmailFocusGained

    private void txtEmailFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtEmailFocusLost
        // TODO add your handling code here:
        txtEmail.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_txtEmailFocusLost

    /**
     * tabelaNomePacientethe command line arguments
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
            java.util.logging.Logger.getLogger(TelaCadastrarDespesas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaCadastrarDespesas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaCadastrarDespesas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaCadastrarDespesas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaCadastrarDespesas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog LUPA_NOME_PACIENTE;
    private javax.swing.JDialog LUPA_PRODUTOS;
    private javax.swing.JButton btnAdicionar;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JComboBox cbProduto;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    public javax.swing.JComboBox<String> jCAcomodacao;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel lblcodigo;
    private javax.swing.JTable tabelaDespesas;
    private javax.swing.JTable tabelaNomePaciente;
    private javax.swing.JTable tabelaProdutos;
    private javax.swing.JFormattedTextField txDinheiro;
    private javax.swing.JFormattedTextField txQtde;
    private javax.swing.JTextField txTotalVenda;
    private javax.swing.JTextField txTroco;
    private javax.swing.JFormattedTextField txValorUn;
    private javax.swing.JFormattedTextField txtCPF;
    private javax.swing.JTextField txtCodigo;
    private org.jdesktop.swingx.JXDatePicker txtDataCadastro;
    private org.jdesktop.swingx.JXDatePicker txtDataEntrada;
    private org.jdesktop.swingx.JXDatePicker txtDataSaida;
    private javax.swing.JTextField txtDiasEstadia;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtNomePaciente;
    private javax.swing.JTextField txtProntuario;
    private javax.swing.JFormattedTextField txtTelefone;
    private javax.swing.JTextField txtValorDiarias;
    private javax.swing.JTextField txtValorTotalDiarias;
    // End of variables declaration//GEN-END:variables

    public void populaJComboBoxProdutos() {
        String sql = "Select *from tb_produtos";
        try {
            pst = Conexao.conector().prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                //criamos o objeto e alimentamos a lista
                ProdutoBean pt = new ProdutoBean();
                pt.setIdProduto(rs.getInt("codigo"));
                pt.setProduto(rs.getString("Produto"));
                pt.setValor(rs.getDouble("Valor"));
                listaProduto.add(pt);

                cbProduto.addItem(rs.getString("Produto"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    public void atalho() {
        //opção numero 2
        InputMap im = jPanel7.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0), "v03");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_F6, 0), "v06");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "vesc");

        ActionMap am = jPanel7.getActionMap();
        am.put("v03", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        am.put("v06", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnSalvar();
            }
        });

        am.put("vesc", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    /**
     * @return the inserir
     */
    public boolean isInserir() {
        return inserir;
    }

    /**
     * @param inserir the inserir to set
     */
    public void setInserir(boolean inserir) {
        this.inserir = inserir;
    }

}
