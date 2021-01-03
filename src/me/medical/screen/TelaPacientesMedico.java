/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.medical.screen;

import me.medical.controller.Conexao;
import me.medical.model.ModeFoto;
import me.medical.utils.Msg;
import me.medical.utils.ValidaEnter;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author Adriano Zanette
 */
public class TelaPacientesMedico extends javax.swing.JFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    byte[] BYTES_FOTO = null;
    List<ModeFoto> lista_foto = new ArrayList<>();
    String Caminho = System.getProperty("user.home") + "\\Desktop";

    /**
     * Creates new form TelaPacientes
     */
    public TelaPacientesMedico() {
        initComponents();
        setIcon();
        populaJComboBoxConvenio();
        listarPacientes();
        ContarRegistros();
        ValidaEnter ve = new ValidaEnter();
        ve.ValidaEnterPainel(jPanel2);
        conexao = Conexao.conector();
        txtDataCadastro.setText(horaAtual());
        
    }

    protected void listarAnamnese(String nomePaciente) {
        //tratamento de erros
        try {
            DefaultTableModel model = (DefaultTableModel) tabela.getModel();//pegamos o model da tabela
            model.setNumRows(0);//limpamos caso tenha algum registro

            String sql = "SELECT * FROM tb_anamnese WHERE tb_anamnese.NomePaciente = '" + nomePaciente + "';";
            System.out.println(sql);
            pst = Conexao.conector().prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                //adiciona linha na tabela
                model.addRow(new Object[]{
                    me.medical.utils.Utils.convertData(rs.getDate("Data")),
                    rs.getString("HistoricoClinico"),
                    rs.getString("CondutaMedica"),
                    rs.getString("Medicacao"),
                    rs.getString("FrequenciaCardiaca"),
                    rs.getString("FrequenciaRespiratoria"),
                    rs.getString("Hgt"),
                    rs.getString("PressaoArterial"),
                    rs.getString("Temperatura"),
                    rs.getString("Saturacao"),});
            }

        } catch (Exception e) {
            e.printStackTrace();//imprime o erro no console do netbeans
            Msg.erro(this, "Erro ao Listar Histórico!\nErro: " + e.getMessage());
        }

    }

    // metodo para contar registros da tabela
    public void ContarRegistros() {

        int linhas = tblPacientes.getRowCount();
        txtRegistros.setText(" " + linhas);

    }

    public static String horaAtual() {
        Date hora = new Date();
        SimpleDateFormat formatoHora = new SimpleDateFormat("dd/MM/YYYY");
        return formatoHora.format(hora);
    }

    private void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("Receita.png")));
    }

    //--------------------------Métodos--------------------------------------
    private void habilitaCampos() {

        txtNomePaciente.setEnabled(false);
        txtCep.setEnabled(false);
        txtCidade.setEnabled(false);
        txtTipoAtendimento.setEnabled(false);
        txtEndereco.setEnabled(false);
        txtCidade.setEnabled(false);
        txtbairro.setEnabled(false);
        txtTelefoneCelular.setEnabled(false);
        txtDataNascimento.setEnabled(false);
        txtDocumento.setEnabled(false);
        JcUF.setEnabled(false);
        tblPacientes.setEnabled(true);
        btnCapturarFoto.setEnabled(false);
        btnEscolherFoto.setEnabled(false);
        JcTipoConvenio.setEnabled(false);
        jCStatus.setEnabled(false);
        txtIdade.setEnabled(false);
        lblFoto.setEnabled(false);
        jCSexo.setEnabled(false);

    }

    private void desaabilitaCampos() {

        txtNomePaciente.setEnabled(false);
        txtCep.setEnabled(false);
        txtCidade.setEnabled(false);
        txtTipoAtendimento.setEnabled(false);
        txtEndereco.setEnabled(false);
        txtbairro.setEnabled(false);
        txtCidade.setEnabled(false);
        txtTelefoneCelular.setEnabled(false);
        txtDataNascimento.setEnabled(false);
        txtDocumento.setEnabled(false);
        JcUF.setEnabled(false);
        tblPacientes.setEnabled(true);
        btnCapturarFoto.setEnabled(false);
        btnEscolherFoto.setEnabled(false);
        JcTipoConvenio.setEnabled(false);
        jCStatus.setEnabled(false);
        txtIdade.setEnabled(false);
        lblFoto.setEnabled(false);
        jCSexo.setEnabled(false);
    }

    private void limparCampos() {
        txtId.setText("");
        txtNomePaciente.setText("");
        txtCep.setText("");
        txtCidade.setText("");
        txtTipoAtendimento.setSelectedIndex(0);
        txtEndereco.setText("");
        txtbairro.setText("");
        txtCidade.setText("");
        txtTelefoneCelular.setText("");
        txtDataNascimento.setText("");
        txtDocumento.setText("");
        JcUF.setSelectedIndex(0);
        lblFoto.setText("");
        JcTipoConvenio.setSelectedIndex(0);
        jCStatus.setSelectedIndex(0);
        txtIdade.setText("");
        jCSexo.setSelectedIndex(0);

        try {

            InputStream IMPUT_IMG = getClass().getResourceAsStream("/Imagens/UserFoto.png");
            BufferedImage imagem = ImageIO.read(IMPUT_IMG);
            ByteArrayOutputStream buff = new ByteArrayOutputStream();
            ImageIO.write(imagem, "JPG", buff);
            BYTES_FOTO = buff.toByteArray();

            ImageIcon icon = new ImageIcon(BYTES_FOTO);
            icon.setImage(icon.getImage().getScaledInstance(150, 158, 100));
            lblFoto.setIcon(icon);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // metodo para colocar a letra em negrito
    Font fonte = new Font("Arial", Font.BOLD, 12);

    private void letrasemNegrito() {

        txtNomePaciente.setFont(fonte);
        txtCep.setFont(fonte);
        txtCidade.setFont(fonte);
        txtTipoAtendimento.setFont(fonte);
        txtEndereco.setFont(fonte);
        txtbairro.setFont(fonte);
        txtCidade.setFont(fonte);
        txtTelefoneCelular.setFont(fonte);
        txtDataNascimento.setFont(fonte);
        txtDocumento.setFont(fonte);
        JcUF.setFont(fonte);
        jCStatus.setFont(fonte);
        txtIdade.setFont(fonte);
        JcTipoConvenio.setFont(fonte);
        jCSexo.setFont(fonte);

    }

    //-------------------------------------------metodo listar,cadastrar,alterar,excluir------------------------------------
    public void listarPacientes() {
        lista_foto = new ArrayList<>();
        String sql = "Select IdPaciente,DataCadastro,TipoConvenio,documentoPaciente,NomePaciente, CepPaciente,EnderecoPaciente,Bairro,CidadePaciente,UfPaciente,dataNascimento,TelefoneCelular,TipoAtendimento,Status,Idade,Sexo from tbpaciente order by IdPaciente";
        try {
            pst = Conexao.conector().prepareStatement(sql);
            rs = pst.executeQuery();

            DefaultTableModel model = (DefaultTableModel) tblPacientes.getModel();
            model.setNumRows(0);

            //adiciona coluna por coluna tabela
            //para ocultar a coluna da tabela, clique na tabela->conteudo da tabela-> colunas-> e altere os valores largura preferencial, minima e maxima
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("IdPaciente"),
                    rs.getString("DataCadastro"),
                    rs.getString("TipoConvenio"),
                    rs.getString("documentoPaciente"),
                    rs.getString("NomePaciente"),
                    rs.getString("CepPaciente"),
                    rs.getString("EnderecoPaciente"),
                    rs.getString("Bairro"),
                    rs.getString("CidadePaciente"),
                    rs.getString("UfPaciente"),
                    rs.getString("dataNascimento"),
                    rs.getString("TelefoneCelular"),
                    rs.getString("TipoAtendimento"),
                    rs.getString("Status"),
                    rs.getString("Idade"),
                    rs.getString("Sexo")});
            }
            //se quiser voltar, so comentar o codigo acima e descomentar o codigo abaixo
            //tblPacientes.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException error) {
            JOptionPane.showMessageDialog(null, error);
        }
        listarfoto();
    }

    public void listarfoto() {
        lista_foto = new ArrayList<>();
        String sql = "Select IdPaciente,DataCadastro,TipoConvenio,documentoPaciente,NomePaciente, CepPaciente,EnderecoPaciente,Bairro,CidadePaciente,UfPaciente,dataNascimento,TelefoneCelular,TipoAtendimento,foto,Status,Idade,Sexo from tbpaciente order by IdPaciente";
        try {
            pst = Conexao.conector().prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                ModeFoto model = new ModeFoto();
                model.setImagem_foto(rs.getBytes("foto"));
                lista_foto.add(model);
            }

        } catch (SQLException error) {
            JOptionPane.showMessageDialog(null, error);
        }
    }

    public void cadastrarPacientes() {
        String sql = "Insert into tbpaciente(DataCadastro,TipoConvenio,documentoPaciente,NomePaciente,CepPaciente,EnderecoPaciente,Bairro,CidadePaciente,UfPaciente,dataNascimento,TelefoneCelular,TipoAtendimento,foto,Status,Idade,Sexo) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try {
            pst = Conexao.conector().prepareStatement(sql);
            pst.setString(1, txtDataCadastro.getText());
            pst.setString(2, JcTipoConvenio.getSelectedItem().toString());
            pst.setString(3, txtDocumento.getText());
            pst.setString(4, txtNomePaciente.getText());
            pst.setString(5, txtCep.getText());
            pst.setString(6, txtEndereco.getText());
            pst.setString(7, txtbairro.getText());
            pst.setString(8, txtCidade.getText());
            pst.setString(9, JcUF.getSelectedItem().toString());
            pst.setString(10, txtDataNascimento.getText());
            pst.setString(11, txtTelefoneCelular.getText());
            pst.setString(12, txtTipoAtendimento.getSelectedItem().toString());
            pst.setBytes(13, BYTES_FOTO);
            pst.setString(14, jCStatus.getSelectedItem().toString());
            pst.setString(15, txtIdade.getText());
            pst.setString(16, jCSexo.getSelectedItem().toString());
            pst.execute();
            JOptionPane.showMessageDialog(rootPane, " Paciente " + txtNomePaciente.getText().toUpperCase() + " salvo com sucesso.", "Inclusão", JOptionPane.PLAIN_MESSAGE);
            listarPacientes();

        } catch (SQLException error) {
            JOptionPane.showMessageDialog(null, error);
        }
    }

    public void pesquisarPacientes() {
        String sql = "Select *from tbpaciente where NomePaciente like ?";

        try {
            pst = Conexao.conector().prepareStatement(sql);
            pst.setString(1, txtPesquisar.getText() + "%");
            rs = pst.executeQuery();
            tblPacientes.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException error) {
            JOptionPane.showMessageDialog(null, error);
        }
    }

    public void mostraItens() {
        int seleciona = tblPacientes.getSelectedRow();

        //carrega historico
        listarAnamnese(tblPacientes.getModel().getValueAt(seleciona, 4).toString());

        txtId.setText(tblPacientes.getModel().getValueAt(seleciona, 0).toString());
        txtDataCadastro.setText(tblPacientes.getModel().getValueAt(seleciona, 1).toString());
        JcTipoConvenio.setSelectedItem(tblPacientes.getModel().getValueAt(seleciona, 2).toString());
        txtDocumento.setText(tblPacientes.getModel().getValueAt(seleciona, 3).toString());
        txtNomePaciente.setText(tblPacientes.getModel().getValueAt(seleciona, 4).toString());
        txtCep.setText(tblPacientes.getModel().getValueAt(seleciona, 5).toString());
        txtEndereco.setText(tblPacientes.getModel().getValueAt(seleciona, 6).toString());
        txtbairro.setText(tblPacientes.getModel().getValueAt(seleciona, 7).toString());
        txtCidade.setText(tblPacientes.getModel().getValueAt(seleciona, 8).toString());
        JcUF.setSelectedItem(tblPacientes.getModel().getValueAt(seleciona, 9).toString());
        txtDataNascimento.setText(tblPacientes.getModel().getValueAt(seleciona, 10).toString());
        txtTelefoneCelular.setText(tblPacientes.getModel().getValueAt(seleciona, 11).toString());
        txtTipoAtendimento.setSelectedItem(tblPacientes.getModel().getValueAt(seleciona, 12).toString());
        jCStatus.setSelectedItem(tblPacientes.getModel().getValueAt(seleciona, 13).toString());
        txtIdade.setText(tblPacientes.getModel().getValueAt(seleciona, 14).toString());
        jCSexo.setSelectedItem(tblPacientes.getModel().getValueAt(seleciona, 15).toString());
        try {
            BYTES_FOTO = lista_foto.get(seleciona).getImagem_foto();
            ImageIcon icon = new ImageIcon(BYTES_FOTO);
            icon.setImage(icon.getImage().getScaledInstance(213, 218, 100));
            lblFoto.setIcon(icon);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editarPacientes() {
        String sql = "Update tbpaciente set DataCadastro = ?,TipoConvenio=?,documentoPaciente = ?,NomePaciente = ?,CepPaciente = ?,EnderecoPaciente = ?,Bairro = ?,CidadePaciente = ?,UfPaciente = ?,dataNascimento = ?,TelefoneCelular = ?,TipoAtendimento = ?, foto=?, Status=?,Idade=?,Sexo=? where IdPaciente = ? ";

        try {
            pst = Conexao.conector().prepareStatement(sql);
            pst.setString(1, txtDataCadastro.getText());
            pst.setString(2, JcTipoConvenio.getSelectedItem().toString());
            pst.setString(3, txtDocumento.getText());
            pst.setString(4, txtNomePaciente.getText());
            pst.setString(5, txtCep.getText());
            pst.setString(6, txtEndereco.getText());
            pst.setString(7, txtbairro.getText());
            pst.setString(8, txtCidade.getText());
            pst.setString(9, JcUF.getSelectedItem().toString());
            pst.setString(10, txtDataNascimento.getText());
            pst.setString(11, txtTelefoneCelular.getText());
            pst.setString(12, txtTipoAtendimento.getSelectedItem().toString());
            pst.setBytes(13, BYTES_FOTO);
            pst.setString(14, jCStatus.getSelectedItem().toString());
            pst.setString(15, txtIdade.getText());
            pst.setString(16, jCSexo.getSelectedItem().toString());
            pst.setInt(17, Integer.parseInt(txtId.getText()));
            pst.executeUpdate();
            JOptionPane.showMessageDialog(rootPane, " Paciente " + txtNomePaciente.getText().toUpperCase() + " Atualizado com sucesso.", "Inclusão", JOptionPane.PLAIN_MESSAGE);
            listarPacientes();

        } catch (SQLException error) {
            JOptionPane.showMessageDialog(null, error);
        }
    }

    public void deletarPacientes() {
        String sql = "Delete from tbpaciente where IdPaciente = ?";

        try {
            pst = Conexao.conector().prepareStatement(sql);
            pst.setInt(1, Integer.parseInt(txtId.getText()));
            pst.execute();
            JOptionPane.showMessageDialog(rootPane, " Paciente " + txtNomePaciente.getText().toUpperCase() + " Excluido com sucesso.", "Inclusão", JOptionPane.PLAIN_MESSAGE);
            listarPacientes();
        } catch (SQLException error) {

            JOptionPane.showMessageDialog(null, error);
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel6 = new javax.swing.JPanel();
        btnNovo = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtNomePaciente = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtDataNascimento = new javax.swing.JFormattedTextField();
        jLabel7 = new javax.swing.JLabel();
        txtEndereco = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtCidade = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        JcUF = new javax.swing.JComboBox<>();
        txtDataCadastro = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtTelefoneCelular = new javax.swing.JFormattedTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtCep = new javax.swing.JFormattedTextField();
        documento = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtbairro = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        lblFoto = new javax.swing.JLabel();
        btnCapturarFoto = new javax.swing.JButton();
        btnEscolherFoto = new javax.swing.JButton();
        txtId = new javax.swing.JTextField();
        txtPesquisar = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        JcTipoConvenio = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPacientes = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jCStatus = new javax.swing.JComboBox<>();
        txtTipoAtendimento = new javax.swing.JComboBox<>();
        txtIdade = new javax.swing.JTextField();
        txtDocumento = new javax.swing.JFormattedTextField();
        jLabel16 = new javax.swing.JLabel();
        jCSexo = new javax.swing.JComboBox<>();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        txtRegistros = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastro de Pacientes");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel6.setBackground(new java.awt.Color(153, 153, 153));
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

        jButton1.setBackground(new java.awt.Color(102, 255, 204));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(51, 51, 51));
        jButton1.setText("EXAMES IMAGEM");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(0, 153, 102));
        jButton2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(51, 51, 51));
        jButton2.setText("ATESTADO MÉDICO");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(153, 153, 0));
        jButton3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton3.setForeground(new java.awt.Color(51, 51, 51));
        jButton3.setText("RECEITA SIMPLES");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(255, 153, 0));
        jButton4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton4.setForeground(new java.awt.Color(51, 51, 51));
        jButton4.setText("RECEITA ESPECIAL");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setBackground(new java.awt.Color(51, 153, 255));
        jButton5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton5.setForeground(new java.awt.Color(51, 51, 51));
        jButton5.setText("LAUDOS");
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

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(215, 215, 215)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton5)
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 620, 1270, -1));

        jTabbedPane1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTabbedPane1.setMinimumSize(new java.awt.Dimension(125, 128));
        jTabbedPane1.setPreferredSize(new java.awt.Dimension(120, 100));

        jPanel2.setBackground(new java.awt.Color(153, 153, 153));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("NOME PACIENTE:");

        txtNomePaciente.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtNomePaciente.setEnabled(false);
        txtNomePaciente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomePacienteActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("DATA CADASTRO:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("Data Nasc.");

        txtDataNascimento.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        try {
            txtDataNascimento.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtDataNascimento.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtDataNascimento.setEnabled(false);
        txtDataNascimento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDataNascimentoActionPerformed(evt);
            }
        });
        txtDataNascimento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDataNascimentoKeyReleased(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("ENDEREÇO:");

        txtEndereco.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtEndereco.setEnabled(false);
        txtEndereco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEnderecoActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setText("CIDADE:");

        txtCidade.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtCidade.setEnabled(false);
        txtCidade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCidadeActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setText("UF:");

        JcUF.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<Selecione>", "AC", "AL", "AM", "AP", "BA", "CE", "DF", "ES", "GO", "MA", "MG", "MS", "MT", "PA", "PB", "PE", "PI", "PR", "RJ", "RN", "RO", "RR", "RS", "SC", "SE", "SP", "TO" }));
        JcUF.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        JcUF.setEnabled(false);

        txtDataCadastro.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtDataCadastro.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtDataCadastro.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtDataCadastro.setEnabled(false);

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel13.setText("CELULAR:");

        txtTelefoneCelular.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        try {
            txtTelefoneCelular.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##)#####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtTelefoneCelular.setEnabled(false);
        txtTelefoneCelular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTelefoneCelularActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel14.setText("TIPO ATENDIMENTO:");

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel15.setText("CEP:");

        txtCep.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        try {
            txtCep.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("#####-###")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtCep.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCep.setEnabled(false);
        txtCep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCepActionPerformed(evt);
            }
        });
        txtCep.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCepKeyReleased(evt);
            }
        });

        documento.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        documento.setText("CPF:");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel12.setText("BAIRRO:");

        txtbairro.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtbairro.setEnabled(false);
        txtbairro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtbairroActionPerformed(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblFoto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblFoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/UserFoto.png"))); // NOI18N
        lblFoto.setEnabled(false);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnCapturarFoto.setBackground(new java.awt.Color(255, 255, 255));
        btnCapturarFoto.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnCapturarFoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/foto.png"))); // NOI18N
        btnCapturarFoto.setText("Capturar");
        btnCapturarFoto.setEnabled(false);
        btnCapturarFoto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapturarFotoActionPerformed(evt);
            }
        });

        btnEscolherFoto.setBackground(new java.awt.Color(255, 255, 255));
        btnEscolherFoto.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnEscolherFoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Archive_24x24.png"))); // NOI18N
        btnEscolherFoto.setText("Escolher");
        btnEscolherFoto.setToolTipText("Escolher Foto");
        btnEscolherFoto.setEnabled(false);
        btnEscolherFoto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEscolherFotoActionPerformed(evt);
            }
        });

        txtId.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtId.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtId.setEnabled(false);

        txtPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPesquisarKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPesquisarKeyReleased(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel17.setText("CONVÊNIO:");

        JcTipoConvenio.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<Selecionar>" }));
        JcTipoConvenio.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        JcTipoConvenio.setEnabled(false);

        tblPacientes.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        tblPacientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Data Cadastro", "Tipo Convenio", "Documento Paciente", "Nome", "CEP", "Endereco", "Bairro", "Cidade", "UF", "Nascimento", "Telefone", "Tipo", "Status", "Idade", "Sexo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblPacientes.setEnabled(false);
        tblPacientes.setRowHeight(28);
        tblPacientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPacientesMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblPacientesMouseReleased(evt);
            }
        });
        tblPacientes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblPacientesKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tblPacientes);
        if (tblPacientes.getColumnModel().getColumnCount() > 0) {
            tblPacientes.getColumnModel().getColumn(0).setMinWidth(70);
            tblPacientes.getColumnModel().getColumn(0).setPreferredWidth(70);
            tblPacientes.getColumnModel().getColumn(0).setMaxWidth(70);
            tblPacientes.getColumnModel().getColumn(1).setMinWidth(0);
            tblPacientes.getColumnModel().getColumn(1).setPreferredWidth(0);
            tblPacientes.getColumnModel().getColumn(1).setMaxWidth(0);
            tblPacientes.getColumnModel().getColumn(2).setMinWidth(0);
            tblPacientes.getColumnModel().getColumn(2).setPreferredWidth(0);
            tblPacientes.getColumnModel().getColumn(2).setMaxWidth(0);
            tblPacientes.getColumnModel().getColumn(3).setMinWidth(0);
            tblPacientes.getColumnModel().getColumn(3).setPreferredWidth(0);
            tblPacientes.getColumnModel().getColumn(3).setMaxWidth(0);
            tblPacientes.getColumnModel().getColumn(5).setMinWidth(0);
            tblPacientes.getColumnModel().getColumn(5).setPreferredWidth(0);
            tblPacientes.getColumnModel().getColumn(5).setMaxWidth(0);
            tblPacientes.getColumnModel().getColumn(6).setMinWidth(0);
            tblPacientes.getColumnModel().getColumn(6).setPreferredWidth(0);
            tblPacientes.getColumnModel().getColumn(6).setMaxWidth(0);
            tblPacientes.getColumnModel().getColumn(7).setMinWidth(0);
            tblPacientes.getColumnModel().getColumn(7).setPreferredWidth(0);
            tblPacientes.getColumnModel().getColumn(7).setMaxWidth(0);
            tblPacientes.getColumnModel().getColumn(8).setMinWidth(0);
            tblPacientes.getColumnModel().getColumn(8).setPreferredWidth(0);
            tblPacientes.getColumnModel().getColumn(8).setMaxWidth(0);
            tblPacientes.getColumnModel().getColumn(9).setMinWidth(0);
            tblPacientes.getColumnModel().getColumn(9).setPreferredWidth(0);
            tblPacientes.getColumnModel().getColumn(9).setMaxWidth(0);
            tblPacientes.getColumnModel().getColumn(10).setMinWidth(0);
            tblPacientes.getColumnModel().getColumn(10).setPreferredWidth(0);
            tblPacientes.getColumnModel().getColumn(10).setMaxWidth(0);
            tblPacientes.getColumnModel().getColumn(12).setMinWidth(0);
            tblPacientes.getColumnModel().getColumn(12).setPreferredWidth(0);
            tblPacientes.getColumnModel().getColumn(12).setMaxWidth(0);
            tblPacientes.getColumnModel().getColumn(13).setMinWidth(0);
            tblPacientes.getColumnModel().getColumn(13).setPreferredWidth(0);
            tblPacientes.getColumnModel().getColumn(13).setMaxWidth(0);
            tblPacientes.getColumnModel().getColumn(14).setMinWidth(0);
            tblPacientes.getColumnModel().getColumn(14).setPreferredWidth(0);
            tblPacientes.getColumnModel().getColumn(14).setMaxWidth(0);
            tblPacientes.getColumnModel().getColumn(15).setMinWidth(0);
            tblPacientes.getColumnModel().getColumn(15).setPreferredWidth(0);
            tblPacientes.getColumnModel().getColumn(15).setMaxWidth(0);
        }

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Search.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("STATUS");

        jCStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "< Selecione >", "Ativo", "Inativo" }));
        jCStatus.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jCStatus.setEnabled(false);

        txtTipoAtendimento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "< Selecione >", "CONSULTA", "AGENDAMENTO", "RETORNO" }));
        txtTipoAtendimento.setEnabled(false);

        txtIdade.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtIdade.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtIdade.setEnabled(false);

        try {
            txtDocumento.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.###-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtDocumento.setEnabled(false);

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel16.setText("Sexo:");

        jCSexo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "< Selecione >", "MASCULINO", "FEMININO" }));
        jCSexo.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jCSexo.setEnabled(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(txtDataCadastro, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(documento)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel9))
                                .addGap(26, 26, 26)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtNomePaciente, javax.swing.GroupLayout.DEFAULT_SIZE, 366, Short.MAX_VALUE)
                                    .addComponent(txtCidade)
                                    .addComponent(txtEndereco)
                                    .addComponent(txtDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel15)
                                            .addComponent(jLabel12)
                                            .addComponent(jLabel10))
                                        .addGap(23, 23, 23)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtbairro)
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                                        .addComponent(JcUF, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(jLabel13)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                        .addComponent(txtTelefoneCelular, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addComponent(txtCep, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(0, 0, Short.MAX_VALUE))))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel6)
                                            .addComponent(jLabel1))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(txtDataNascimento, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtIdade, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jCSexo, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(txtPesquisar))))
                                .addGap(14, 14, 14))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtTipoAtendimento, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(JcTipoConvenio, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jCStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(btnEscolherFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCapturarFoto))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addGap(44, 44, 44))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addComponent(jLabel1))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtDataCadastro, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtNomePaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel3)
                                .addComponent(jLabel6)
                                .addComponent(txtDataNascimento, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtIdade, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jCSexo))
                        .addGap(21, 21, 21)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(documento)
                                .addComponent(jLabel15)
                                .addComponent(txtCep, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtDocumento))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)
                            .addComponent(jLabel12)
                            .addComponent(txtbairro, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(JcUF, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10)
                            .addComponent(txtCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9)
                            .addComponent(jLabel13)
                            .addComponent(txtTelefoneCelular, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel14)
                        .addComponent(jLabel17)
                        .addComponent(JcTipoConvenio, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtTipoAtendimento, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnEscolherFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnCapturarFoto))
                    .addComponent(jCStatus)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE)
                .addGap(8, 8, 8))
        );

        jTabbedPane1.addTab("DADOS DO PACIENTE", jPanel2);

        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Data", "Histórico", "Conduta", "Medicação", "Frequência Cardíaca", "Frequência Respiratória", "HGT", "Pressão Arterial", "Temperatura", "Saturação"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabela.setRowHeight(28);
        tabela.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tabela);
        if (tabela.getColumnModel().getColumnCount() > 0) {
            tabela.getColumnModel().getColumn(6).setMinWidth(60);
            tabela.getColumnModel().getColumn(6).setPreferredWidth(60);
            tabela.getColumnModel().getColumn(6).setMaxWidth(60);
            tabela.getColumnModel().getColumn(9).setMinWidth(100);
            tabela.getColumnModel().getColumn(9).setPreferredWidth(100);
            tabela.getColumnModel().getColumn(9).setMaxWidth(100);
        }

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1265, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 29, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 510, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.addTab("HISTÓRICO CLÍNICO", jPanel1);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, 1270, 570));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("Total de Pacientes Cadastrados:");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 590, -1, 30));

        txtRegistros.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtRegistros.setForeground(new java.awt.Color(255, 0, 0));
        getContentPane().add(txtRegistros, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 590, 60, 30));

        setSize(new java.awt.Dimension(1368, 802));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtNomePacienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomePacienteActionPerformed
        txtNomePaciente.transferFocus();
    }//GEN-LAST:event_txtNomePacienteActionPerformed

    private void txtDataNascimentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDataNascimentoActionPerformed
        txtDataNascimento.transferFocus();
    }//GEN-LAST:event_txtDataNascimentoActionPerformed

    private void txtEnderecoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEnderecoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEnderecoActionPerformed

    private void txtCidadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCidadeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCidadeActionPerformed

    private void txtTelefoneCelularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTelefoneCelularActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTelefoneCelularActionPerformed

    private void txtCepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCepActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCepActionPerformed

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed
        txtNomePaciente.requestFocus();
        tblPacientes.setEnabled(true);
        habilitaCampos();
        limparCampos();
        letrasemNegrito();

    }//GEN-LAST:event_btnNovoActionPerformed
    TelaAnamneseMedica tela = new TelaAnamneseMedica();
    private void tblPacientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPacientesMouseClicked
        mostraItens();
        habilitaCampos();
        txtPesquisar.setText("");
        btnNovo.setEnabled(true);

//         // chama a tela ja com os dados
//        int index = tblPacientes.getSelectedRow();
//        TableModel model = tblPacientes.getModel();
//        String codigo = model.getValueAt(index, 0).toString();
//        String NomePaciente = model.getValueAt(index, 4).toString();
//        String Idade = model.getValueAt(index, 14).toString();
//        String TipoAtendimento = model.getValueAt(index, 12).toString();
//        
//        
//        tela.setVisible(true);
//        tela.pack();
//        tela.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        tela.txtId.setText(codigo);
//        tela.txtNomePaciente.setText(NomePaciente);
//        tela.txtIdade.setText(Idade);
//        tela.txtTipoAtendimento.setText(TipoAtendimento);
//        
//        

    }//GEN-LAST:event_tblPacientesMouseClicked

    private void txtCepKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCepKeyReleased

        // CHAMA O BUSCA CEP QUANDO DIGITADO NA CAIXA CEP   
        String cp = txtCep.getText();
        cp = cp.replaceAll("\\D*", ""); //ignora qualquer coisa que não seja numero.  
        int cont = cp.length();

        if (cont == 8) {
            try {
                correio();
            } catch (Error e) {
                JOptionPane.showMessageDialog(null, e);

    }//GEN-LAST:event_txtCepKeyReleased
            txtTelefoneCelular.requestFocus();
        }
    }
    private void txtbairroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtbairroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtbairroActionPerformed

    private void txtPesquisarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisarKeyPressed

    }//GEN-LAST:event_txtPesquisarKeyPressed

    private void txtPesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisarKeyReleased

        pesquisarPacientes();
    }//GEN-LAST:event_txtPesquisarKeyReleased

    private void tblPacientesMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPacientesMouseReleased

    }//GEN-LAST:event_tblPacientesMouseReleased

    private void btnCapturarFotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapturarFotoActionPerformed
        WEBCAM dialog = new WEBCAM(new javax.swing.JFrame(), true);
        dialog.setVisible(true);

        try {
            if (dialog.LEVA_BYTES != null) {
                BYTES_FOTO = dialog.LEVA_BYTES;
            }
            ImageIcon icon = new ImageIcon(BYTES_FOTO);
            //icon.setImage(icon.getImage().getScaledInstance(lblFoto.getWidth(), lblFoto.getHeight(), 100));
            icon.setImage(icon.getImage().getScaledInstance(213, 218, 100));
            lblFoto.setIcon(icon);
        } catch (Exception e) {
        }

    }//GEN-LAST:event_btnCapturarFotoActionPerformed

    private void btnEscolherFotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEscolherFotoActionPerformed
        LOCALIZAR_ARQUIVO();
    }//GEN-LAST:event_btnEscolherFotoActionPerformed

    private void tblPacientesKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblPacientesKeyReleased
        mostraItens();
    }//GEN-LAST:event_tblPacientesKeyReleased

    private void txtDataNascimentoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDataNascimentoKeyReleased

        try {
            if (txtDataNascimento.getText().length() >= 10) {

                DateTimeFormatter date = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate dataNascimento = LocalDate.parse(txtDataNascimento.getText(), date);
                LocalDate dataAtual = LocalDate.now();
                Period periodo = Period.between(dataNascimento, dataAtual);
                String resultado = "" + periodo.getYears();
                txtIdade.setText(resultado);

//        LocalDate aniversario = LocalDate.parse(txtDataNascimento.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
//        LocalDate hoje = LocalDate.now();
//        Period periodo = Period.between(aniversario, hoje);       
//        txtIdade.setText(periodo.getYears()+"");
            } else {
                txtIdade.setText("0");
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_txtDataNascimentoKeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        TelaRealizarExames tela = new TelaRealizarExames();
        tela.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        TelaAtestadoMedico tela = new TelaAtestadoMedico();
        tela.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        TelaReceituarioSimples tela = new TelaReceituarioSimples();
        tela.setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        TelaReceituarioEspecial tela = new TelaReceituarioEspecial();
        tela.setVisible(true);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        TelaLaudos tela = new TelaLaudos();
        tela.setVisible(true);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        TelaAtestadoComparecimento tela = new TelaAtestadoComparecimento();
        tela.setVisible(true);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void tabelaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaMouseClicked

    }//GEN-LAST:event_tabelaMouseClicked

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
            java.util.logging.Logger.getLogger(TelaPacientesMedico.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaPacientesMedico.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaPacientesMedico.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaPacientesMedico.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new TelaPacientesMedico().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JComboBox<String> JcTipoConvenio;
    public javax.swing.JComboBox<String> JcUF;
    private javax.swing.JButton btnCapturarFoto;
    private javax.swing.JButton btnEscolherFoto;
    private javax.swing.JButton btnNovo;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel documento;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JComboBox<String> jCSexo;
    private javax.swing.JComboBox<String> jCStatus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblFoto;
    private javax.swing.JTable tabela;
    public javax.swing.JTable tblPacientes;
    public javax.swing.JFormattedTextField txtCep;
    public javax.swing.JTextField txtCidade;
    public javax.swing.JTextField txtDataCadastro;
    public javax.swing.JFormattedTextField txtDataNascimento;
    private javax.swing.JFormattedTextField txtDocumento;
    public javax.swing.JTextField txtEndereco;
    public javax.swing.JTextField txtId;
    private javax.swing.JTextField txtIdade;
    public javax.swing.JTextField txtNomePaciente;
    private javax.swing.JTextField txtPesquisar;
    private javax.swing.JLabel txtRegistros;
    public javax.swing.JFormattedTextField txtTelefoneCelular;
    public javax.swing.JComboBox<String> txtTipoAtendimento;
    public javax.swing.JTextField txtbairro;
    // End of variables declaration//GEN-END:variables

    public void correio() {

        String cep = txtCep.getText();

        me.medical.utils.WebServiceCep webServiceCep = me.medical.utils.WebServiceCep.searchCep(cep);

        if (webServiceCep.wasSuccessful()) {

            txtEndereco.setText(webServiceCep.getLogradouroFull());
            txtbairro.setText(webServiceCep.getBairro());
            txtCidade.setText(webServiceCep.getCidade());
            JcUF.setSelectedItem(webServiceCep.getUf());

        } else {
            JOptionPane.showMessageDialog(null, webServiceCep.getResultText());

        }
    }

    private void LOCALIZAR_ARQUIVO() {
        try {
            JFileChooser ImagemSelecionada = null;
            FileNameExtensionFilter filtro = new FileNameExtensionFilter("Imagem em JPEG e PNG", "jpg", "png");
            ImagemSelecionada = new JFileChooser();
            ImagemSelecionada.setCurrentDirectory(new File(Caminho));
            ImagemSelecionada.addChoosableFileFilter(filtro);
            ImagemSelecionada.setFileFilter(filtro);
            ImagemSelecionada.setAcceptAllFileFilterUsed(true);
            ImagemSelecionada.setDialogType(JFileChooser.OPEN_DIALOG);

            if (ImagemSelecionada.showOpenDialog(ImagemSelecionada) == 0) {

//            BufferedImage imagem = ImageIO.read(ImagemSelecionada.getSelectedFile());      
//            ByteArrayOutputStream buff = new ByteArrayOutputStream();
//            ImageIO.write(imagem, "JPG", buff);
//            BYTES_IMAGEM1 = buff.toByteArray();
                BufferedImage imagem = ImageIO.read(ImagemSelecionada.getSelectedFile());
                int Nova_Largura = 150, Nova_Altura = 150; //Aqui eu escolho qual será a altura e largura de px da imagem a ser salva
                BufferedImage new_img = new BufferedImage(Nova_Largura, Nova_Altura, BufferedImage.TYPE_4BYTE_ABGR);
                Graphics2D g = new_img.createGraphics();
                g.drawImage(imagem, 0, 0, Nova_Largura, Nova_Altura, null);
                ByteArrayOutputStream buff2 = new ByteArrayOutputStream();
                ImageIO.write(new_img, "JPG", buff2);
                BYTES_FOTO = buff2.toByteArray();

                ImageIcon icon = new ImageIcon(BYTES_FOTO);
                icon.setImage(icon.getImage().getScaledInstance(213, 218, 100));
                lblFoto.setIcon(icon);

            }

        } catch (Exception e) {
        }
    }

    public void populaJComboBoxConvenio() {
        String sql = "Select *from tb_convenios";
        try {
            pst = Conexao.conector().prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                JcTipoConvenio.addItem(rs.getString("tipoConvenio"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

}
