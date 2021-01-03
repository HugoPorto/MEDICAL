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
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.Icon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author Adriano Zanette
 */
public class TelaCadastroMedicacao extends javax.swing.JFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    /**
     * Creates new form TelaUsuario
     */
    public TelaCadastroMedicacao() {
        initComponents();
        listarMedicacao();
        populaJComboBoxIntervalo();
        populaJComboBoxViasAcesso();
        // chama classe de letras maiusculas
        txtLaboratorio.setDocument(new UpperCaseField(100));
        setIcon();
        conexao = Conexao.conector();
        PreparedStatement ST = null;
        letrasemNegrito();
        mudarCorLinha();

    }
    
  // regra de mudar a cor da linha   
    private String regraData(Date dataFabricao, Date dataVencimento) {
        String data = "";
        try {
            long dias = Utils.calcularDias(dataFabricao, dataVencimento);
            if (dias > 15) {
                data = "VALIDADE OK";
            }
            
            
            if (dias <= 15) {
                data = "VENCERÁ EM "+dias +" DIAS";
            }
            
            if (dias <= 0) {
                data = "VENCIDO";
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
    
    // muda a cor
    private void mudarCorLinha(){
          tabelaMedicacao.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (table.getValueAt(row, 8).toString().contains("VALIDADE")) {
                    setForeground(Color.BLUE);
                } 
                
                 if (table.getValueAt(row, 8).toString().contains("VENCERÁ")) {
                    setForeground(Color.ORANGE);
                } 
                
                if (table.getValueAt(row, 8).toString().contains("VENCIDO")) {
                    setForeground(Color.red);
                } 
                return this;
            }
        });
    }
    
    
    
    
    

    //-----------------------------------METODOS------------------------------------------------------------------//
    public static String horaAtual() {
        Date hora = new Date();
        SimpleDateFormat formatoHora = new SimpleDateFormat("dd/MM/YYYY");
        return formatoHora.format(hora);
    }

    private void habilitaCampos() {

        txtNomeMedicação.setEnabled(true);
        jCIntervalo.setEnabled(true);
        txtPesquisar.setEnabled(true);
        jCViaAcesso.setEnabled(true);
        txtDataFabricacao.setEnabled(true);
        txtDataVencimento.setEnabled(true);
        txtLaboratorio.setEnabled(true);
        txtlote.setEnabled(true);
    }

    private void desaabilitaCampos() {

        txtNomeMedicação.setEnabled(false);
        jCIntervalo.setEnabled(false);
        txtPesquisar.setEnabled(false);
        jCViaAcesso.setEnabled(false);
        txtDataFabricacao.setEnabled(false);
        txtDataVencimento.setEnabled(false);
        txtLaboratorio.setEnabled(false);
        txtlote.setEnabled(false);

    }

    private void limpar() {

        txtNomeMedicação.setText("");
        jCIntervalo.setSelectedIndex(0);
        jCViaAcesso.setSelectedIndex(0);
        txtDataFabricacao.getEditor().setText("");
        txtDataVencimento.getEditor().setText("");
        txtLaboratorio.setText("");
        txtCodigo.setText("");
        txtlote.setText("");
    }

    // metodo para colocar a letra em negrito
    Font fonte = new Font("Arial", Font.BOLD, 12);

    private void letrasemNegrito() {

        txtNomeMedicação.setFont(fonte);
        txtNomeMedicação.setFont(fonte);
        jCIntervalo.setFont(fonte);
        jCViaAcesso.setFont(fonte);
        txtPesquisar.setFont(fonte);
        txtDataFabricacao.setFont(fonte);
        txtDataVencimento.setFont(fonte);
        txtLaboratorio.setFont(fonte);
        txtlote.setFont(fonte);

    }

    private void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("Receita.png")));
    }

    // icone do joptionPane
    Icon icon = new javax.swing.ImageIcon(getClass().getResource("/Imagens/ok.png"));

    // icone do joptionPane
    Icon iconExcluir = new javax.swing.ImageIcon(getClass().getResource("/Imagens/Excluido.png"));

    private void SalvarMedicacao() {
        Conexao conec = new Conexao();
        String sql = "insert into tb_medicacao(NomeMedicacao,ViaAcesso,Intervalo,DataFabricacao,DataVencimento,Lote,Laboratorio)values(?,?,?,?,?,?,?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtNomeMedicação.getText());
            pst.setString(2, jCViaAcesso.getSelectedItem().toString());
            pst.setString(3, jCIntervalo.getSelectedItem().toString());
            pst.setDate(4, convertDateSalvar(txtDataFabricacao.getDate()));
            pst.setDate(5, convertDateSalvar(txtDataVencimento.getDate()));
            pst.setString(6, txtlote.getText());
            pst.setString(7, txtLaboratorio.getText());
            //validação dos campos obrigatórios
            if (txtNomeMedicação.getText().trim().equals("") || txtNomeMedicação.getText().trim().equals("")) {

                JOptionPane.showMessageDialog(null, "Os dados devem ser preenchidos!", "Alerta", JOptionPane.WARNING_MESSAGE);

            } else {

                //A linha abaixo atualiza a tabela usuarios com os dados do formulario
                // a estrutura abaixo é usada para confirmar a inserção dos dados na tabela
                int adicionado = pst.executeUpdate();

                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(rootPane, "Medicação " + txtNomeMedicação.getText().toUpperCase() + " salvo com sucesso.", "Inclusão", JOptionPane.INFORMATION_MESSAGE, icon);

                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                rs.close();
                pst.close();
                conec.desconectar();
            } catch (Exception ex) {
            }
        }
    }

    public void alterarMedicacao() {
        Conexao conec = new Conexao();
        String sql = "Update tb_medicacao set NomeMedicacao=?,ViaAcesso=?,Intervalo=?,DataFabricacao=?,DataVencimento=?,Lote=?,Laboratorio=? where codigo = ? ";

        try {
            pst = Conexao.conector().prepareStatement(sql);
            pst.setString(1, txtNomeMedicação.getText());
            pst.setString(2, jCViaAcesso.getSelectedItem().toString());
            pst.setString(3, jCIntervalo.getSelectedItem().toString());
            pst.setDate(4, convertDateSalvar(txtDataFabricacao.getDate()));
            pst.setDate(5, convertDateSalvar(txtDataVencimento.getDate()));
            pst.setString(6, txtlote.getText());
            pst.setString(7, txtLaboratorio.getText());
            pst.setInt(8, Integer.parseInt(txtCodigo.getText()));
            pst.executeUpdate();
            JOptionPane.showMessageDialog(rootPane, " Medicação " + txtNomeMedicação.getText().toUpperCase() + " Atualizado com sucesso.", "Inclusão", JOptionPane.INFORMATION_MESSAGE, icon);
            listarMedicacao();
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

    public void removerMedicacao() {
        Conexao conec = new Conexao();
        String sql = "delete from tb_medicacao where codigo=?";

        try {
            pst = Conexao.conector().prepareStatement(sql);
            pst.setInt(1, Integer.parseInt(txtCodigo.getText()));
            pst.execute();
            JOptionPane.showMessageDialog(rootPane, " Convênio " + txtNomeMedicação.getText().toUpperCase() + " Excluido com sucesso.", "Inclusão", JOptionPane.INFORMATION_MESSAGE, iconExcluir);
            listarMedicacao();
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

    // lista na tabelinha 
    public void listarMedicacao() {
        Conexao conec = new Conexao();
        DefaultTableModel model = (DefaultTableModel) tabelaMedicacao.getModel();
        model.setNumRows(0);
        String sql = "Select codigo,NomeMedicacao,ViaAcesso,Intervalo,DataFabricacao,DataVencimento,Lote,Laboratorio from tb_medicacao"; 
        try {
            pst = Conexao.conector().prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("codigo"),
                    rs.getString("NomeMedicacao"),
                    rs.getString("ViaAcesso"),
                    rs.getString("Intervalo"),
                    Utils.convertData(rs.getDate("DataFabricacao")),
                    Utils.convertData(rs.getDate("DataVencimento")),
                    rs.getString("Lote"),
                    rs.getString("Laboratorio"),
                    regraData(rs.getDate("DataFabricacao"), rs.getDate("DataVencimento"))});
                            
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

    public void pesquisarMedicacao() {
        Conexao conec = new Conexao();
        DefaultTableModel model = (DefaultTableModel) tabelaMedicacao.getModel();
        model.setNumRows(0);
        //conexão com o banco de dados
        String sql = "Select *from tb_medicacao where NomeMedicacao like ?";

        try {
            pst = Conexao.conector().prepareStatement(sql);
            pst.setString(1, txtPesquisar.getText() + "%");
            rs = pst.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("codigo"),
                    rs.getString("NomeMedicacao"),
                    rs.getString("ViaAcesso"),
                    rs.getString("Intervalo"),
                    Utils.convertData(rs.getDate("DataFabricacao")),
                    Utils.convertData(rs.getDate("DataVencimento")),
                    rs.getString("Laboratorio"),
                    rs.getString("StatusVencimento"),
                    regraData(rs.getDate("DataFabricacao"), rs.getDate("DataVencimento"))});
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
    
//     public void pesquisarStatusMedicacao() {
//        Conexao conec = new Conexao();
//        DefaultTableModel model = (DefaultTableModel) tabelaMedicacao.getModel();
//        model.setNumRows(0);
//        conexão com o banco de dados
//        String sql = "Select * from tb_medicacao where StatusVencimento like ?";
//
//        try {
//            pst = Conexao.conector().prepareStatement(sql);
//            pst.setString(1, txtPesquisarStatusVencimento.getText() + "%");
//            rs = pst.executeQuery();
//            while (rs.next()) {
//                model.addRow(new Object[]{
//                    rs.getString("codigo"),
//                    rs.getString("NomeMedicacao"),
//                    rs.getString("ViaAcesso"),
//                    rs.getString("Intervalo"),
//                    Utils.convertData(rs.getDate("DataFabricacao")),
//                    Utils.convertData(rs.getDate("DataVencimento")),
//                    rs.getString("Laboratorio"),
//                    rs.getString("StatusVencimento"),
//                    });
//            }
//        } catch (SQLException error) {
//            JOptionPane.showMessageDialog(null, error);
//        } finally {
//            try {
//                rs.close();
//                pst.close();
//                conec.desconectar();
//            } catch (Exception ex) {
//            }
//        }
//    }
    
    
    
    
    
    

    public void mostrarItens() {
        int seleciona = tabelaMedicacao.getSelectedRow();
        txtCodigo.setText(tabelaMedicacao.getModel().getValueAt(seleciona, 0).toString());
        txtNomeMedicação.setText(tabelaMedicacao.getModel().getValueAt(seleciona, 1).toString());
        jCViaAcesso.setSelectedItem(tabelaMedicacao.getModel().getValueAt(seleciona, 2).toString());
        jCIntervalo.setSelectedItem(tabelaMedicacao.getModel().getValueAt(seleciona, 3).toString());
        txtDataFabricacao.getEditor().setText(tabelaMedicacao.getModel().getValueAt(seleciona, 4).toString());
        txtDataVencimento.getEditor().setText(tabelaMedicacao.getModel().getValueAt(seleciona, 5).toString());
        txtlote.setText(tabelaMedicacao.getModel().getValueAt(seleciona, 6).toString());
        txtLaboratorio.setText(tabelaMedicacao.getModel().getValueAt(seleciona, 7).toString());

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel8 = new javax.swing.JPanel();
        btnNovo = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        txtDataFabricacao = new org.jdesktop.swingx.JXDatePicker();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtDataVencimento = new org.jdesktop.swingx.JXDatePicker();
        txtLaboratorio = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtlote = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaMedicacao = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtNomeMedicação = new javax.swing.JTextField();
        jCViaAcesso = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jCIntervalo = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        txtPesquisar = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastro Medicação");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 574, Short.MAX_VALUE)
                .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel8Layout.createSequentialGroup()
                    .addGap(253, 253, 253)
                    .addComponent(btnExcluir)
                    .addContainerGap(578, Short.MAX_VALUE)))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(12, Short.MAX_VALUE))
            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel8Layout.createSequentialGroup()
                    .addGap(13, 13, 13)
                    .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(13, Short.MAX_VALUE)))
        );

        jPanel8Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnEditar, btnSalvar});

        getContentPane().add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 540, 940, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("Pesquisar Medicação por Nome:");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 480, -1, -1));

        txtCodigo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCodigo.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtCodigo.setEnabled(false);
        getContentPane().add(txtCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 500, 130, 31));

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel24.setText("CÓDIGO:");
        getContentPane().add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 480, -1, -1));

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel12.setText("DATA DE FABRICAÇÃO:");

        txtDataFabricacao.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtDataFabricacao.setEnabled(false);

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel13.setText("LABORATÓRIO");

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel14.setText("DATA DE VENCIMENTO:");

        txtDataVencimento.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtDataVencimento.setEnabled(false);

        txtLaboratorio.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtLaboratorio.setEnabled(false);

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel15.setText("LOTE:");

        txtlote.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtlote.setEnabled(false);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtDataFabricacao, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jLabel12)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(txtDataVencimento, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtlote, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 342, Short.MAX_VALUE))
                    .addComponent(txtLaboratorio))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13)
                    .addComponent(jLabel14)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtDataFabricacao, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtDataVencimento, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtlote)
                    .addComponent(txtLaboratorio))
                .addContainerGap())
        );

        getContentPane().add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 940, -1));

        tabelaMedicacao.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Código", "Descrição da Medicação ", "Via de Acesso", "Frequência", "Data Fabricação", "Data Vencimento", "Lote", "Laboratório", "Status Vencimento"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaMedicacao.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaMedicacaoMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelaMedicacao);
        if (tabelaMedicacao.getColumnModel().getColumnCount() > 0) {
            tabelaMedicacao.getColumnModel().getColumn(0).setMinWidth(50);
            tabelaMedicacao.getColumnModel().getColumn(0).setPreferredWidth(50);
            tabelaMedicacao.getColumnModel().getColumn(0).setMaxWidth(50);
            tabelaMedicacao.getColumnModel().getColumn(2).setMinWidth(0);
            tabelaMedicacao.getColumnModel().getColumn(2).setPreferredWidth(0);
            tabelaMedicacao.getColumnModel().getColumn(2).setMaxWidth(0);
            tabelaMedicacao.getColumnModel().getColumn(3).setMinWidth(0);
            tabelaMedicacao.getColumnModel().getColumn(3).setPreferredWidth(0);
            tabelaMedicacao.getColumnModel().getColumn(3).setMaxWidth(0);
            tabelaMedicacao.getColumnModel().getColumn(4).setMinWidth(110);
            tabelaMedicacao.getColumnModel().getColumn(4).setPreferredWidth(110);
            tabelaMedicacao.getColumnModel().getColumn(4).setMaxWidth(110);
            tabelaMedicacao.getColumnModel().getColumn(5).setMinWidth(110);
            tabelaMedicacao.getColumnModel().getColumn(5).setPreferredWidth(110);
            tabelaMedicacao.getColumnModel().getColumn(5).setMaxWidth(110);
            tabelaMedicacao.getColumnModel().getColumn(6).setMinWidth(80);
            tabelaMedicacao.getColumnModel().getColumn(6).setPreferredWidth(80);
            tabelaMedicacao.getColumnModel().getColumn(6).setMaxWidth(80);
            tabelaMedicacao.getColumnModel().getColumn(7).setMinWidth(0);
            tabelaMedicacao.getColumnModel().getColumn(7).setPreferredWidth(0);
            tabelaMedicacao.getColumnModel().getColumn(7).setMaxWidth(0);
            tabelaMedicacao.getColumnModel().getColumn(8).setMinWidth(150);
            tabelaMedicacao.getColumnModel().getColumn(8).setPreferredWidth(150);
            tabelaMedicacao.getColumnModel().getColumn(8).setMaxWidth(150);
        }

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, 940, 270));

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("Nome Medicamento:");

        txtNomeMedicação.setEnabled(false);

        jCViaAcesso.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "< Selecione >" }));
        jCViaAcesso.setEnabled(false);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setText("Via de Admininstração:");

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Create.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jCIntervalo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "< Selecione >" }));
        jCIntervalo.setEnabled(false);

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setText("Intervalo de Administração:");

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Create.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(0, 300, Short.MAX_VALUE))
                    .addComponent(txtNomeMedicação))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jCViaAcesso, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jCIntervalo, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel9))
                .addGap(20, 20, 20))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel8)
                        .addComponent(jLabel9)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNomeMedicação, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCViaAcesso, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCIntervalo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 940, -1));

        txtPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPesquisarKeyReleased(evt);
            }
        });
        getContentPane().add(txtPesquisar, new org.netbeans.lib.awtextra.AbsoluteConstraints(8, 500, 800, 31));

        setSize(new java.awt.Dimension(973, 657));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed
        txtNomeMedicação.requestFocus();
        btnSalvar.setEnabled(true);
        habilitaCampos();

    }//GEN-LAST:event_btnNovoActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        SalvarMedicacao();
        listarMedicacao();
        limpar();
        desaabilitaCampos();
        btnSalvar.setEnabled(false);
        btnNovo.setEnabled(true);
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        int i = tabelaMedicacao.getSelectedRow();
        if (i < 0) {
            Msg.alerta(this, "Selecione um Registro!");
            return;
        }
        alterarMedicacao();
        listarMedicacao();
        desaabilitaCampos();
        limpar();
        btnNovo.setEnabled(true);
        btnEditar.setEnabled(false);
        btnExcluir.setEnabled(false);
    }//GEN-LAST:event_btnEditarActionPerformed

    private void tabelaMedicacaoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaMedicacaoMouseClicked
        mostrarItens();
        btnEditar.setEnabled(true);
        btnExcluir.setEnabled(true);
        btnSalvar.setEnabled(false);
        btnNovo.setEnabled(false);
    }//GEN-LAST:event_tabelaMedicacaoMouseClicked

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        int i = tabelaMedicacao.getSelectedRow();
        if (i < 0) {
            Msg.alerta(this, "Selecione um Registro!");
            return;
        }
        removerMedicacao();
        listarMedicacao();
        desaabilitaCampos();
        limpar();
        btnNovo.setEnabled(true);
        btnEditar.setEnabled(false);
        btnExcluir.setEnabled(false);
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        TelaCadastroIntervaloMedicamentos tela = new TelaCadastroIntervaloMedicamentos();
        tela.setLocationRelativeTo(null);
        tela.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        TelaCadastroViasAdministracao tela = new TelaCadastroViasAdministracao();
        tela.setLocationRelativeTo(null);
        tela.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtPesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisarKeyReleased
       pesquisarMedicacao();
    }//GEN-LAST:event_txtPesquisarKeyReleased

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
            java.util.logging.Logger.getLogger(TelaCadastroMedicacao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaCadastroMedicacao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaCadastroMedicacao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaCadastroMedicacao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaCadastroMedicacao().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jCIntervalo;
    private javax.swing.JComboBox<String> jCViaAcesso;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabelaMedicacao;
    private javax.swing.JTextField txtCodigo;
    private org.jdesktop.swingx.JXDatePicker txtDataFabricacao;
    private org.jdesktop.swingx.JXDatePicker txtDataVencimento;
    private javax.swing.JTextField txtLaboratorio;
    private javax.swing.JTextField txtNomeMedicação;
    private javax.swing.JTextField txtPesquisar;
    private javax.swing.JTextField txtlote;
    // End of variables declaration//GEN-END:variables

    private void atualizarTabela() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void populaJComboBoxIntervalo() {
        String sql = "Select *from tb_intervalo_medicamentos";
        try {
            pst = Conexao.conector().prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                jCIntervalo.addItem(rs.getString("Intervalo"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    public void populaJComboBoxViasAcesso() {
        String sql = "Select *from tb_vias_acesso";
        try {
            pst = Conexao.conector().prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                jCViaAcesso.addItem(rs.getString("ViasAcesso"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    //metodo converte data
    private java.sql.Date convertDateSalvar(java.util.Date var) throws SQLException {
        int year = var.getDate();
        int month = var.getMonth();
        int day = var.getYear();
        return new java.sql.Date(day, month, year);
    }

}
