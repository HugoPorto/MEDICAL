/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.medical.screen;

import me.medical.controller.Conexao;
import me.medical.model.ModeFoto;
import me.medical.utils.Msg;
import me.medical.utils.UpperCaseField;
import me.medical.utils.Utils;
import me.medical.utils.ValidaEnter;
import me.medical.utils.ValidarCPF;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author Adriano Zanette
 */
public class TelaPacientesRecepcao extends javax.swing.JFrame {
    private boolean inserir;
    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    private int codigoPaciente = 0;

    byte[] BYTES_FOTO = null;
    List<ModeFoto> lista_foto = new ArrayList<>();
    String Caminho = System.getProperty("user.home") + "\\Desktop";

    /**
     * Creates new form TelaPacientes
     */
    public TelaPacientesRecepcao() {
        initComponents();
        setIcon();
        txtId.setVisible(false);
        atalho();
        populaJComboBoxConvenio();
        listarPacientes();
        ContarRegistros();
        ValidaEnter ve = new ValidaEnter();
        ve.ValidaEnterPainel(jPanel2);
        conexao = Conexao.conector();
        AutoCompleteDecorator.decorate(txtTipoAtendimento);
        AutoCompleteDecorator.decorate(JcTipoConvenio);
        AutoCompleteDecorator.decorate(jCStatus);
        AutoCompleteDecorator.decorate(JCtipoSanguineo);
        AutoCompleteDecorator.decorate(jCSexo);
        Utils.considerarEnterComoTab(txtTipoAtendimento);
        Utils.considerarEnterComoTab(JcTipoConvenio);
        Utils.considerarEnterComoTab(jCStatus);
        Utils.considerarEnterComoTab(txtEndereco);
        Utils.considerarEnterComoTab(txtbairro);
        Utils.considerarEnterComoTab(txtCidade);
        Utils.considerarEnterComoTab(JcUF);
        Utils.considerarEnterComoTab(txtCep);
        // chama classe letra maiuscula
        txtNomePaciente.setDocument(new UpperCaseField(100));
        txtCep.setDocument(new UpperCaseField(100));
        txtCidade.setDocument(new UpperCaseField(100));
        txtEndereco.setDocument(new UpperCaseField(100));
        txtbairro.setDocument(new UpperCaseField(100));
        txtCidade.setDocument(new UpperCaseField(100));
        txtTelefoneCelular.setDocument(new UpperCaseField(100));
        txtDocumento.setDocument(new UpperCaseField(100));
        txtIdade.setDocument(new UpperCaseField(100));
        txtPeso.setDocument(new UpperCaseField(100));
        txtAltura.setDocument(new UpperCaseField(100));
        txtSus.setDocument(new UpperCaseField(100));
    }

    // metodo para contar registros da tabela
    public void ContarRegistros() {

        int linhas = tblPacientes.getRowCount();
        txtRegistros.setText(" " + linhas);

    }

    private void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("Receita.png")));
    }

    //--------------------------Métodos--------------------------------------
    private void habilitaCampos() {

        txtNomePaciente.setEnabled(true);
        txtCep.setEnabled(true);
        txtCidade.setEnabled(true);
        txtTipoAtendimento.setEnabled(true);
        txtEndereco.setEnabled(true);
        txtCidade.setEnabled(true);
        txtbairro.setEnabled(true);
        txtTelefoneCelular.setEnabled(true);
        txtDataNascimento.setEnabled(true);
        txtDocumento.setEnabled(true);
        JcUF.setEnabled(true);
        tblPacientes.setEnabled(true);
        btnCapturarFoto.setEnabled(true);
        btnEscolherFoto.setEnabled(true);
        JcTipoConvenio.setEnabled(true);
        jCStatus.setEnabled(true);
        txtIdade.setEnabled(false);
        lblFoto.setEnabled(true);
        jCSexo.setEnabled(true);
        txtPeso.setEnabled(true);
        txtAltura.setEnabled(true);
        txtDataCadastro.setEnabled(true);
        txtSus.setEnabled(false);
        btnpesquisarPorNome.setEnabled(true);
        txtProntuario.setEnabled(true);
        JCtipoSanguineo.setEnabled(true);

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
        txtPeso.setEnabled(false);
        txtAltura.setEnabled(false);
        txtDataCadastro.setEnabled(false);
        txtSus.setEnabled(false);
        btnpesquisarPorNome.setEnabled(true);
        txtProntuario.setEnabled(false);
        JCtipoSanguineo.setEnabled(false);
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
        txtDataCadastro.getEditor().setText("");
        txtDocumento.setText("");
        JcUF.setSelectedIndex(0);
        lblFoto.setText("");
        JcTipoConvenio.setSelectedIndex(0);
        jCStatus.setSelectedIndex(0);
        txtIdade.setText("");
        jCSexo.setSelectedIndex(0);
        txtPeso.setText("");
        txtAltura.setText("");
        txtSus.setText("");
        txtProntuario.setText("");
        JCtipoSanguineo.setSelectedIndex(0);

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
        txtPeso.setFont(fonte);
        txtAltura.setFont(fonte);
        txtDataCadastro.setFont(fonte);
        txtSus.setFont(fonte);
        txtProntuario.setFont(fonte);
        JCtipoSanguineo.setFont(fonte);
    }
    
    
      // icone do joptionPane
    Icon icon = new javax.swing.ImageIcon(getClass().getResource("/Imagens/ok.png"));
    
    
    // icone do joptionPane
    Icon iconExcluir = new javax.swing.ImageIcon(getClass().getResource("/Imagens/Excluido.png"));
    
    
    // icone do joptionPane
    Icon iconStop = new javax.swing.ImageIcon(getClass().getResource("/Imagens/stop.png"));

    //-------------------------------------------metodo listar,cadastrar,alterar,excluir------------------------------------
    public void listarPacientes() {
        Conexao conec = new Conexao();
        lista_foto = new ArrayList<>();
        String sql = "Select codigo,DataCadastro,TipoConvenio,documentoPaciente,NomePaciente, CepPaciente,EnderecoPaciente,Bairro,CidadePaciente,UfPaciente,dataNascimento,TelefoneCelular,TipoAtendimento,Status,Idade,Sexo,Peso,Altura,CartaoSus,Prontuario,tipoSanguineo from tb_agenda order by codigo";
        try {
            pst = Conexao.conector().prepareStatement(sql);
            rs = pst.executeQuery();

            DefaultTableModel model = (DefaultTableModel) tblPacientes.getModel();
            model.setNumRows(0);

            //adiciona coluna por coluna tabela
            //para ocultar a coluna da tabela, clique na tabela->conteudo da tabela-> colunas-> e altere os valores largura preferencial, minima e maxima
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("codigo"),
                    Utils.convertData(rs.getDate("DataCadastro")),
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
                    rs.getString("Sexo"),
                    rs.getString("Peso"),
                    rs.getString("Altura"),
                    rs.getString("CartaoSus"),
                    rs.getString("Prontuario"),
                    rs.getString("tipoSanguineo"),
                });
            }
            //se quiser voltar, so comentar o codigo acima e descomentar o codigo abaixo
            //tblPacientes.setModel(DbUtils.rrioesultSetToTableModel(rs));
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
        listarfoto();
    }

    public void listarfoto() {
        Conexao conec = new Conexao();
        lista_foto = new ArrayList<>();
        String sql = "Select codigo,DataCadastro,TipoConvenio,documentoPaciente,NomePaciente, CepPaciente,EnderecoPaciente,Bairro,CidadePaciente,UfPaciente,dataNascimento,TelefoneCelular,TipoAtendimento,foto,Status,Idade,Sexo,Peso,Altura,CartaoSus from tb_agenda order by codigo";
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
        } finally {
            try {
                rs.close();
                pst.close();
                conec.desconectar();
            } catch (Exception ex) {
            }
        }
    }

    public void cadastrarPacientes() {
        Conexao conec = new Conexao();
        String sql = "Insert into tb_agenda(DataCadastro,TipoConvenio,documentoPaciente,NomePaciente,CepPaciente,EnderecoPaciente,Bairro,CidadePaciente,UfPaciente,dataNascimento,TelefoneCelular,TipoAtendimento,foto,Status,Idade,Sexo,Peso,Altura,CartaoSus,Prontuario,tipoSanguineo) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try {
            pst = Conexao.conector().prepareStatement(sql);
            pst.setDate(1, convertDateSalvar(txtDataCadastro.getDate()));
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
            pst.setString(17, txtPeso.getText());
            pst.setString(18, txtAltura.getText());
            pst.setString(19, txtSus.getText());
            pst.setString(20, txtProntuario.getText());
            pst.setString(21, JCtipoSanguineo.getSelectedItem().toString());
            pst.execute();
            JOptionPane.showMessageDialog(rootPane, " Paciente " + txtNomePaciente.getText().toUpperCase() + " salvo com sucesso.", "Inclusão", JOptionPane.INFORMATION_MESSAGE,icon);
            listarPacientes();

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

    public void pesquisarPacientes() {
        Conexao conec = new Conexao();
        String sql = "Select *from tb_agenda where NomePaciente like ?";

        try {
            pst = Conexao.conector().prepareStatement(sql);
            pst.setString(1, txtPesquisar.getText() + "%");
            rs = pst.executeQuery();
            tblPacientes.setModel(DbUtils.resultSetToTableModel(rs));
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

    public void mostraItens() {
        int seleciona = tblPacientes.getSelectedRow();
        //carrega historico

        txtId.setText(tblPacientes.getModel().getValueAt(seleciona, 0).toString());
        txtDataCadastro.setDate(Utils.convertData(tblPacientes.getModel().getValueAt(seleciona, 1).toString()));
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
        txtPeso.setText(tblPacientes.getModel().getValueAt(seleciona, 16).toString());
        txtAltura.setText(tblPacientes.getModel().getValueAt(seleciona, 17).toString());
        txtSus.setText(tblPacientes.getModel().getValueAt(seleciona, 18).toString());
        txtProntuario.setText(tblPacientes.getModel().getValueAt(seleciona, 19).toString());
        JCtipoSanguineo.setSelectedItem(tblPacientes.getModel().getValueAt(seleciona, 20).toString());
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
        Conexao conec = new Conexao();
        String sql = "Update tb_agenda set DataCadastro = ?,TipoConvenio=?,documentoPaciente = ?,NomePaciente = ?,CepPaciente = ?,EnderecoPaciente = ?,Bairro = ?,CidadePaciente = ?,UfPaciente = ?,dataNascimento = ?,TelefoneCelular = ?,TipoAtendimento = ?, foto=?, Status=?,Idade=?,Sexo=?,Peso=?,Altura=?,CartaoSus=?,Prontuario=?,tipoSanguineo=? where codigo = ? ";

        try {
            pst = Conexao.conector().prepareStatement(sql);
            pst.setDate(1, convertDateSalvar(txtDataCadastro.getDate()));
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
            pst.setString(17, txtPeso.getText());
            pst.setString(18, txtAltura.getText());
            pst.setString(19, txtSus.getText());
             pst.setString(20, txtProntuario.getText());
             pst.setString(21, JCtipoSanguineo.getSelectedItem().toString());
            pst.setInt(22, Integer.parseInt(txtId.getText()));
            pst.executeUpdate();
            JOptionPane.showMessageDialog(rootPane, " Paciente " + txtNomePaciente.getText().toUpperCase() + " Atualizado com sucesso.", "Inclusão", JOptionPane.INFORMATION_MESSAGE,icon);
            listarPacientes();

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

    public void deletarPacientes() {
        Conexao conec = new Conexao();
        String sql = "Delete from tb_agenda where codigo = ?";

        try {
            pst = Conexao.conector().prepareStatement(sql);
            pst.setInt(1, Integer.parseInt(txtId.getText()));
            pst.execute();
            JOptionPane.showMessageDialog(rootPane, " Paciente " + txtNomePaciente.getText().toUpperCase() + " Excluido com sucesso.", "Inclusão", JOptionPane.INFORMATION_MESSAGE,iconExcluir);
            listarPacientes();
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

  
    private void criaNumeroProntuario(){
        try {
            String sql = "SELECT IFNULL(Max(a.codigo),'0') AS id  FROM tb_agenda a";
            pst = Conexao.conector().prepareStatement(sql);
            rs = pst.executeQuery();
            rs.next();
            String dado = String.format("%03d", rs.getInt("id")+1);
            txtProntuario.setText(dado);
        } catch (Exception e) {
            e.printStackTrace();
            Msg.erro(this, "Erro ao Cliar Numero Prontuario!");
        }
    }
    
    
    public boolean jaCadastrado(String cpf) {
        PreparedStatement pst;
        ResultSet rs = null;
        boolean retorno = false;
        String sql = "select * from tb_agenda WHERE documentoPaciente = ?";
        try {
           pst = Conexao.conector().prepareStatement(sql);
            pst.setString(1, cpf);
            rs = pst.executeQuery();
            if (rs.next()) {
                retorno = true;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao pesquisar o paciente " + e.toString(), "Aviso", JOptionPane.WARNING_MESSAGE);
        }
        return retorno;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel6 = new javax.swing.JPanel();
        btnNovo = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnSair1 = new javax.swing.JButton();
        btnFicha = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtNomePaciente = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtEndereco = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtCidade = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        JcUF = new javax.swing.JComboBox<>();
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
        jLabel17 = new javax.swing.JLabel();
        JcTipoConvenio = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jCStatus = new javax.swing.JComboBox<>();
        txtTipoAtendimento = new javax.swing.JComboBox<>();
        txtIdade = new javax.swing.JTextField();
        txtDocumento = new javax.swing.JFormattedTextField();
        jLabel16 = new javax.swing.JLabel();
        jCSexo = new javax.swing.JComboBox<>();
        documento1 = new javax.swing.JLabel();
        txtPeso = new javax.swing.JTextField();
        documento2 = new javax.swing.JLabel();
        txtAltura = new javax.swing.JFormattedTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPacientes = new javax.swing.JTable();
        txtDataCadastro = new org.jdesktop.swingx.JXDatePicker();
        txtDataNascimento = new javax.swing.JFormattedTextField();
        jLabel18 = new javax.swing.JLabel();
        txtSus = new javax.swing.JTextField();
        btnpesquisarPorNome = new javax.swing.JButton();
        txtProntuario = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        LABELDATAcADASTRO = new javax.swing.JLabel();
        JCtipoSanguineo = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        txtRegistros = new javax.swing.JLabel();
        txtPesquisar = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastro de Pacientes");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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

        btnSair1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnSair1.setForeground(new java.awt.Color(51, 51, 51));
        btnSair1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagensTelas/Sair.png"))); // NOI18N
        btnSair1.setText("SAIR");
        btnSair1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSair1ActionPerformed(evt);
            }
        });

        btnFicha.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnFicha.setForeground(new java.awt.Color(51, 51, 51));
        btnFicha.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Atestado.png"))); // NOI18N
        btnFicha.setText("FICHA PACIENTE");
        btnFicha.setEnabled(false);
        btnFicha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFichaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 425, Short.MAX_VALUE)
                .addComponent(btnFicha, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSair1, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSair1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 3, Short.MAX_VALUE))
                    .addComponent(btnFicha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        getContentPane().add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 580, 1260, -1));

        jTabbedPane1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTabbedPane1.setMinimumSize(new java.awt.Dimension(125, 128));
        jTabbedPane1.setPreferredSize(new java.awt.Dimension(120, 100));

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setLayout(null);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("NOME PACIENTE:");
        jPanel2.add(jLabel3);
        jLabel3.setBounds(20, 82, 102, 15);

        txtNomePaciente.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtNomePaciente.setEnabled(false);
        txtNomePaciente.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomePacienteFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomePacienteFocusLost(evt);
            }
        });
        txtNomePaciente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomePacienteActionPerformed(evt);
            }
        });
        txtNomePaciente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNomePacienteKeyReleased(evt);
            }
        });
        jPanel2.add(txtNomePaciente);
        txtNomePaciente.setBounds(142, 72, 353, 37);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("TIPO SANGUINEO:");
        jPanel2.add(jLabel4);
        jLabel4.setBounds(510, 20, 110, 40);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("Data Nasc.");
        jPanel2.add(jLabel6);
        jLabel6.setBounds(513, 82, 64, 15);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("ENDEREÇO:");
        jPanel2.add(jLabel7);
        jLabel7.setBounds(20, 196, 68, 15);

        txtEndereco.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtEndereco.setEnabled(false);
        txtEndereco.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtEnderecoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtEnderecoFocusLost(evt);
            }
        });
        txtEndereco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEnderecoActionPerformed(evt);
            }
        });
        jPanel2.add(txtEndereco);
        txtEndereco.setBounds(142, 186, 353, 37);

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setText("CIDADE:");
        jPanel2.add(jLabel9);
        jLabel9.setBounds(20, 251, 51, 15);

        txtCidade.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtCidade.setEnabled(false);
        txtCidade.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCidadeFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCidadeFocusLost(evt);
            }
        });
        txtCidade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCidadeActionPerformed(evt);
            }
        });
        jPanel2.add(txtCidade);
        txtCidade.setBounds(142, 241, 353, 37);

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setText("UF:");
        jPanel2.add(jLabel10);
        jLabel10.setBounds(513, 251, 18, 15);

        JcUF.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<Selecione>", "AC", "AL", "AM", "AP", "BA", "CE", "DF", "ES", "GO", "MA", "MG", "MS", "MT", "PA", "PB", "PE", "PI", "PR", "RJ", "RN", "RO", "RR", "RS", "SC", "SE", "SP", "TO" }));
        JcUF.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        JcUF.setEnabled(false);
        jPanel2.add(JcUF);
        JcUF.setBounds(589, 241, 157, 37);

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel13.setText("CELULAR:");
        jPanel2.add(jLabel13);
        jLabel13.setBounds(750, 251, 59, 15);

        txtTelefoneCelular.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        try {
            txtTelefoneCelular.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##)#####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtTelefoneCelular.setEnabled(false);
        txtTelefoneCelular.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtTelefoneCelularFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTelefoneCelularFocusLost(evt);
            }
        });
        txtTelefoneCelular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTelefoneCelularActionPerformed(evt);
            }
        });
        jPanel2.add(txtTelefoneCelular);
        txtTelefoneCelular.setBounds(819, 241, 159, 37);

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel14.setText("TIPO ATENDIMENTO:");
        jPanel2.add(jLabel14);
        jLabel14.setBounds(12, 309, 124, 15);

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel15.setText("CEP:");
        jPanel2.add(jLabel15);
        jLabel15.setBounds(513, 137, 27, 15);

        txtCep.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        try {
            txtCep.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("#####-###")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtCep.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCep.setEnabled(false);
        txtCep.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCepFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCepFocusLost(evt);
            }
        });
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
        jPanel2.add(txtCep);
        txtCep.setBounds(589, 127, 158, 37);

        documento.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        documento.setText("CPF:");
        jPanel2.add(documento);
        documento.setBounds(20, 137, 26, 15);

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel12.setText("BAIRRO:");
        jPanel2.add(jLabel12);
        jLabel12.setBounds(513, 196, 53, 15);

        txtbairro.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtbairro.setEnabled(false);
        txtbairro.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtbairroFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtbairroFocusLost(evt);
            }
        });
        txtbairro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtbairroActionPerformed(evt);
            }
        });
        jPanel2.add(txtbairro);
        txtbairro.setBounds(589, 188, 389, 33);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        lblFoto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
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

        jPanel2.add(jPanel3);
        jPanel3.setBounds(992, 13, 250, 0);

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
        jPanel2.add(btnCapturarFoto);
        btnCapturarFoto.setBounds(1107, 296, 132, 41);

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
        jPanel2.add(btnEscolherFoto);
        btnEscolherFoto.setBounds(988, 296, 113, 41);

        txtId.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtId.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtId.setEnabled(false);
        jPanel2.add(txtId);
        txtId.setBounds(120, 20, 14, 36);

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel17.setText("CONVÊNIO:");
        jPanel2.add(jLabel17);
        jLabel17.setBounds(513, 309, 66, 15);

        JcTipoConvenio.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "< Digite Aqui >" }));
        JcTipoConvenio.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        JcTipoConvenio.setEnabled(false);
        JcTipoConvenio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JcTipoConvenioActionPerformed(evt);
            }
        });
        jPanel2.add(JcTipoConvenio);
        JcTipoConvenio.setBounds(589, 299, 156, 37);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("STATUS:");
        jPanel2.add(jLabel2);
        jLabel2.setBounds(755, 309, 59, 15);

        jCStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "< Digite Aqui >", "Ativo", "Inativo" }));
        jCStatus.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jCStatus.setEnabled(false);
        jPanel2.add(jCStatus);
        jCStatus.setBounds(818, 296, 157, 41);

        txtTipoAtendimento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "< Digite Aqui >", "CONSULTA", "AGENDAMENTO", "RETORNO" }));
        txtTipoAtendimento.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtTipoAtendimento.setEnabled(false);
        txtTipoAtendimento.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtTipoAtendimentoFocusGained(evt);
            }
        });
        jPanel2.add(txtTipoAtendimento);
        txtTipoAtendimento.setBounds(142, 297, 353, 40);

        txtIdade.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtIdade.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtIdade.setEnabled(false);
        txtIdade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdadeActionPerformed(evt);
            }
        });
        jPanel2.add(txtIdade);
        txtIdade.setBounds(697, 70, 50, 39);

        txtDocumento.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        try {
            txtDocumento.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.###-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtDocumento.setEnabled(false);
        txtDocumento.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDocumentoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDocumentoFocusLost(evt);
            }
        });
        txtDocumento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDocumentoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDocumentoKeyReleased(evt);
            }
        });
        jPanel2.add(txtDocumento);
        txtDocumento.setBounds(142, 127, 117, 37);

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel16.setText("Sexo:");
        jPanel2.add(jLabel16);
        jLabel16.setBounds(757, 70, 35, 39);

        jCSexo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "< Selecione >", "MASCULINO", "FEMININO" }));
        jCSexo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jCSexo.setEnabled(false);
        jPanel2.add(jCSexo);
        jCSexo.setBounds(796, 70, 182, 39);

        documento1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        documento1.setText("PESO:");
        jPanel2.add(documento1);
        documento1.setBounds(386, 137, 36, 15);

        txtPeso.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPeso.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtPeso.setEnabled(false);
        txtPeso.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPesoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPesoFocusLost(evt);
            }
        });
        txtPeso.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPesoKeyReleased(evt);
            }
        });
        jPanel2.add(txtPeso);
        txtPeso.setBounds(426, 127, 69, 37);

        documento2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        documento2.setText("ALTURA:");
        jPanel2.add(documento2);
        documento2.setBounds(269, 137, 53, 15);

        txtAltura.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        try {
            txtAltura.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("#.##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtAltura.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtAltura.setEnabled(false);
        txtAltura.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtAlturaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtAlturaFocusLost(evt);
            }
        });
        txtAltura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAlturaActionPerformed(evt);
            }
        });
        jPanel2.add(txtAltura);
        txtAltura.setBounds(326, 127, 56, 37);

        tblPacientes.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        tblPacientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Data Cadastro", "Tipo Convenio", "Documento Paciente", "Nome", "CEP", "Endereco", "Bairro", "Cidade", "UF", "Nascimento", "Telefone", "Tipo", "Status", "Idade", "Sexo", "Peso", "Altura", "Cartão SUS", "Prontuário", "Tipo Sanguineo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
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
            tblPacientes.getColumnModel().getColumn(0).setMinWidth(0);
            tblPacientes.getColumnModel().getColumn(0).setPreferredWidth(0);
            tblPacientes.getColumnModel().getColumn(0).setMaxWidth(0);
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
            tblPacientes.getColumnModel().getColumn(13).setMinWidth(0);
            tblPacientes.getColumnModel().getColumn(13).setPreferredWidth(0);
            tblPacientes.getColumnModel().getColumn(13).setMaxWidth(0);
            tblPacientes.getColumnModel().getColumn(14).setMinWidth(0);
            tblPacientes.getColumnModel().getColumn(14).setPreferredWidth(0);
            tblPacientes.getColumnModel().getColumn(14).setMaxWidth(0);
            tblPacientes.getColumnModel().getColumn(15).setMinWidth(0);
            tblPacientes.getColumnModel().getColumn(15).setPreferredWidth(0);
            tblPacientes.getColumnModel().getColumn(15).setMaxWidth(0);
            tblPacientes.getColumnModel().getColumn(16).setMinWidth(0);
            tblPacientes.getColumnModel().getColumn(16).setPreferredWidth(0);
            tblPacientes.getColumnModel().getColumn(16).setMaxWidth(0);
            tblPacientes.getColumnModel().getColumn(17).setMinWidth(0);
            tblPacientes.getColumnModel().getColumn(17).setPreferredWidth(0);
            tblPacientes.getColumnModel().getColumn(17).setMaxWidth(0);
            tblPacientes.getColumnModel().getColumn(18).setMinWidth(0);
            tblPacientes.getColumnModel().getColumn(18).setPreferredWidth(0);
            tblPacientes.getColumnModel().getColumn(18).setMaxWidth(0);
            tblPacientes.getColumnModel().getColumn(19).setMinWidth(80);
            tblPacientes.getColumnModel().getColumn(19).setPreferredWidth(80);
            tblPacientes.getColumnModel().getColumn(19).setMaxWidth(80);
            tblPacientes.getColumnModel().getColumn(20).setMinWidth(0);
            tblPacientes.getColumnModel().getColumn(20).setPreferredWidth(0);
            tblPacientes.getColumnModel().getColumn(20).setMaxWidth(0);
        }

        jPanel2.add(jScrollPane1);
        jScrollPane1.setBounds(12, 348, 1227, 141);

        txtDataCadastro.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtDataCadastro.setEnabled(false);
        txtDataCadastro.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDataCadastroFocusGained(evt);
            }
        });
        jPanel2.add(txtDataCadastro);
        txtDataCadastro.setBounds(353, 20, 140, 36);

        txtDataNascimento.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        try {
            txtDataNascimento.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtDataNascimento.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtDataNascimento.setEnabled(false);
        txtDataNascimento.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDataNascimentoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDataNascimentoFocusLost(evt);
            }
        });
        txtDataNascimento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDataNascimentoKeyReleased(evt);
            }
        });
        jPanel2.add(txtDataNascimento);
        txtDataNascimento.setBounds(589, 70, 102, 39);

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel18.setText("SUS:");
        jPanel2.add(jLabel18);
        jLabel18.setBounds(757, 137, 28, 15);

        txtSus.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtSus.setEnabled(false);
        jPanel2.add(txtSus);
        txtSus.setBounds(796, 127, 182, 37);

        btnpesquisarPorNome.setBackground(new java.awt.Color(255, 204, 0));
        btnpesquisarPorNome.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnpesquisarPorNome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Search.png"))); // NOI18N
        btnpesquisarPorNome.setText("Pesquisar Pacientes");
        btnpesquisarPorNome.setEnabled(false);
        btnpesquisarPorNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnpesquisarPorNomeActionPerformed(evt);
            }
        });
        jPanel2.add(btnpesquisarPorNome);
        btnpesquisarPorNome.setBounds(800, 20, 177, 36);

        txtProntuario.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtProntuario.setEnabled(false);
        txtProntuario.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtProntuarioFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtProntuarioFocusLost(evt);
            }
        });
        jPanel2.add(txtProntuario);
        txtProntuario.setBounds(140, 20, 90, 36);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setText("PRONTUÁRIO");
        jPanel2.add(jLabel8);
        jLabel8.setBounds(20, 19, 81, 40);

        LABELDATAcADASTRO.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        LABELDATAcADASTRO.setText("DATA CADASTRO:");
        jPanel2.add(LABELDATAcADASTRO);
        LABELDATAcADASTRO.setBounds(240, 20, 110, 40);

        JCtipoSanguineo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "< Digite Aqui >", "A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-" }));
        JCtipoSanguineo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        JCtipoSanguineo.setEnabled(false);
        JCtipoSanguineo.setMinimumSize(new java.awt.Dimension(36, 22));
        JCtipoSanguineo.setPreferredSize(new java.awt.Dimension(36, 22));
        JCtipoSanguineo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                JCtipoSanguineoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                JCtipoSanguineoFocusLost(evt);
            }
        });
        jPanel2.add(JCtipoSanguineo);
        JCtipoSanguineo.setBounds(620, 20, 130, 40);

        jTabbedPane1.addTab("DADOS DO PACIENTE", jPanel2);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 1260, 530));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("Total de Pacientes Cadastrados:");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(46, 550, 210, 20));

        txtRegistros.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtRegistros.setForeground(new java.awt.Color(255, 0, 0));
        getContentPane().add(txtRegistros, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 540, 60, 40));

        txtPesquisar.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPesquisarKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPesquisarKeyReleased(evt);
            }
        });
        getContentPane().add(txtPesquisar, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 544, 520, 30));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Search.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 550, -1, -1));

        setSize(new java.awt.Dimension(1323, 696));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtNomePacienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomePacienteActionPerformed
        txtNomePaciente.transferFocus();
    }//GEN-LAST:event_txtNomePacienteActionPerformed

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
        txtProntuario.requestFocus();
        btnSalvar.setEnabled(true);
        tblPacientes.setEnabled(true);
        btnFicha.setEnabled(false);
        btnEditar.setEnabled(false);
        btnExcluir.setEnabled(false);
        habilitaCampos();
        limparCampos();
        criaNumeroProntuario();
        letrasemNegrito();

    }//GEN-LAST:event_btnNovoActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
       
        if (codigoPaciente == 0) {
            if (jaCadastrado(txtDocumento.getText())) {
                String msgCPF = "O CPF ";
                msgCPF += txtDocumento.getText() + " CPF já está cadastrado no sistema!";
                JOptionPane.showMessageDialog(rootPane, msgCPF, "Atenção", JOptionPane.INFORMATION_MESSAGE,iconStop);
                txtDocumento.requestFocus();
                txtDocumento.setText("");
                return;
               
            }
        cadastrarPacientes();
        ContarRegistros();
        limparCampos();
        desaabilitaCampos();
        btnSalvar.setEnabled(false);
        btnNovo.setEnabled(true);
        txtSus.setBackground(Color.white);

        }                         
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        int i = tblPacientes.getSelectedRow();
        if (i < 0) {
            Msg.alerta(this, "Selecione um Registro!");
            return;
        }
        deletarPacientes();
        ContarRegistros();
        desaabilitaCampos();
        limparCampos();
        btnNovo.setEnabled(true);
        btnEditar.setEnabled(false);
        btnExcluir.setEnabled(false);

    }//GEN-LAST:event_btnExcluirActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        // TODO add your handling code here:
        int i = tblPacientes.getSelectedRow();
        if (i < 0) {
            Msg.alerta(this, "Selecione um Registro!");
            return;
        }
        editarPacientes();
        desaabilitaCampos();
        limparCampos();
        btnNovo.setEnabled(true);
        btnEditar.setEnabled(false);
        btnExcluir.setEnabled(false);
    }//GEN-LAST:event_btnEditarActionPerformed

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
            txtSus.requestFocus();
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

    private void btnSair1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSair1ActionPerformed

        //Pergunta se o usuário deseja realmente sair do sistema
        int sair = JOptionPane.showConfirmDialog(null, "DESEJA SAIR DA TELA PACIENTE ?", "ATENÇÃO", JOptionPane.YES_NO_OPTION);
        if (sair == JOptionPane.YES_OPTION) {
            dispose();
        }
    }//GEN-LAST:event_btnSair1ActionPerformed

    private void tblPacientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPacientesMouseClicked
        mostraItens();
        habilitaCampos();
        txtPesquisar.setText("");
        btnNovo.setEnabled(true);
        btnFicha.setEnabled(true);
        btnSalvar.setEnabled(false);
        btnEditar.setEnabled(true);
        btnExcluir.setEnabled(true);

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

    private void tblPacientesMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPacientesMouseReleased

    }//GEN-LAST:event_tblPacientesMouseReleased

    private void tblPacientesKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblPacientesKeyReleased
        mostraItens();
    }//GEN-LAST:event_tblPacientesKeyReleased

    private void txtIdadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdadeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdadeActionPerformed

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

    private void btnFichaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFichaActionPerformed

        final Aguarde carregando = new Aguarde();
        carregando.setVisible(true);
        Thread t = new Thread() {
            public void run() {

                try {
                    ImpressaoFicha imprime = new ImpressaoFicha();
                    List listade_dados = GetDados();
                    imprime.Imprime_Relatorio(listade_dados);
                    carregando.dispose();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }
                btnFicha.setEnabled(false);
            }
        };
        t.start();


    }//GEN-LAST:event_btnFichaActionPerformed

    private void JcTipoConvenioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JcTipoConvenioActionPerformed
        if (JcTipoConvenio.getSelectedItem().toString().equals("SUS")) {

            txtSus.setEnabled(true);
            txtSus.requestFocus();
            txtSus.setBackground(Color.yellow);
    }//GEN-LAST:event_JcTipoConvenioActionPerformed
    }
    private void txtNomePacienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNomePacienteKeyReleased

//        //campo aceita somente letras
//        txtNomePaciente.setText(txtNomePaciente.getText().replaceAll("[^A-Z]", ""));
    }//GEN-LAST:event_txtNomePacienteKeyReleased

    private void txtPesoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesoKeyReleased
        //campo aceita somente numeros
        txtPeso.setText(txtPeso.getText().replaceAll("[^0-9]", ""));
    }//GEN-LAST:event_txtPesoKeyReleased

    private void txtAlturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAlturaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAlturaActionPerformed

    private void btnpesquisarPorNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnpesquisarPorNomeActionPerformed
        String pesquisarNome = JOptionPane.showInputDialog("Digite o Nome Completo para Pesquisar");
        if (pesquisarNome.length() > 0) {
            for (int i = 0; i < tblPacientes.getRowCount(); i++) {
                if (pesquisarNome.equals(tblPacientes.getValueAt(i, 4))) {
                    txtDataCadastro.setDate(Utils.convertData(tblPacientes.getValueAt(i, 1).toString()));
                    JcTipoConvenio.setSelectedItem(tblPacientes.getValueAt(i, 2).toString());
                    txtDocumento.setText(tblPacientes.getValueAt(i, 3).toString());
                    txtNomePaciente.setText(tblPacientes.getValueAt(i, 4).toString());
                    txtCep.setText(tblPacientes.getValueAt(i, 5).toString());
                    txtEndereco.setText(tblPacientes.getValueAt(i, 6).toString());
                    txtbairro.setText(tblPacientes.getValueAt(i, 7).toString());
                    txtCidade.setText(tblPacientes.getValueAt(i, 8).toString());
                    JcUF.setSelectedItem(tblPacientes.getValueAt(i, 9).toString());
                    txtDataNascimento.setText(tblPacientes.getValueAt(i, 10).toString());
                    txtTelefoneCelular.setText(tblPacientes.getValueAt(i, 11).toString());
                    txtTipoAtendimento.setSelectedItem(tblPacientes.getValueAt(i, 12).toString());
                    jCStatus.setSelectedItem(tblPacientes.getValueAt(i, 13).toString());
                    txtIdade.setText(tblPacientes.getValueAt(i, 14).toString());
                    jCSexo.setSelectedItem(tblPacientes.getValueAt(i, 15).toString());
                    txtPeso.setText(tblPacientes.getValueAt(i, 16).toString());
                    txtAltura.setText(tblPacientes.getValueAt(i, 17).toString());
                    txtSus.setText(tblPacientes.getValueAt(i, 18).toString());
                    txtProntuario.setText(tblPacientes.getValueAt(i, 19).toString());
                    JCtipoSanguineo.setSelectedItem(tblPacientes.getModel().getValueAt(i, 20).toString());
                    
                    try {
                        BYTES_FOTO = lista_foto.get(i).getImagem_foto();
                        ImageIcon icon = new ImageIcon(BYTES_FOTO);
                        icon.setImage(icon.getImage().getScaledInstance(213, 218, 100));
                        lblFoto.setIcon(icon);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            }

        }
    }//GEN-LAST:event_btnpesquisarPorNomeActionPerformed

    private void txtNomePacienteFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomePacienteFocusGained
        txtNomePaciente.setBackground(new Color(255, 255, 0));
    }//GEN-LAST:event_txtNomePacienteFocusGained

    private void txtProntuarioFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtProntuarioFocusGained
        txtProntuario.setBackground(new Color(255, 255, 0));
    }//GEN-LAST:event_txtProntuarioFocusGained

    private void txtDataNascimentoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDataNascimentoFocusGained
       txtDataNascimento.setBackground(new Color(255, 255, 0));
    }//GEN-LAST:event_txtDataNascimentoFocusGained

    private void txtDocumentoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDocumentoFocusGained
         txtDocumento.setBackground(new Color(255, 255, 0));
    }//GEN-LAST:event_txtDocumentoFocusGained

    private void txtAlturaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtAlturaFocusGained
        txtAltura.setBackground(new Color(255, 255, 0));
    }//GEN-LAST:event_txtAlturaFocusGained

    private void txtPesoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPesoFocusGained
        txtPeso.setBackground(new Color(255, 255, 0));
    }//GEN-LAST:event_txtPesoFocusGained

    private void txtCepFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCepFocusLost
         txtCep.setBackground(new Color(255, 255,255));
    }//GEN-LAST:event_txtCepFocusLost

    private void txtTelefoneCelularFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTelefoneCelularFocusGained
         txtTelefoneCelular.setBackground(new Color(255, 255, 0));
    }//GEN-LAST:event_txtTelefoneCelularFocusGained

    private void txtProntuarioFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtProntuarioFocusLost
       txtProntuario.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_txtProntuarioFocusLost

    private void txtNomePacienteFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomePacienteFocusLost
       txtNomePaciente.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_txtNomePacienteFocusLost

    private void txtDataNascimentoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDataNascimentoFocusLost
        txtDataNascimento.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_txtDataNascimentoFocusLost

    private void txtDocumentoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDocumentoFocusLost
       txtDocumento.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_txtDocumentoFocusLost

    private void txtAlturaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtAlturaFocusLost
        txtAltura.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_txtAlturaFocusLost

    private void txtPesoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPesoFocusLost
       txtPeso.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_txtPesoFocusLost

    private void txtCepFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCepFocusGained
         txtCep.setBackground(new Color(255, 255, 0));
    }//GEN-LAST:event_txtCepFocusGained

    private void txtTelefoneCelularFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTelefoneCelularFocusLost
         txtTelefoneCelular.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_txtTelefoneCelularFocusLost

    private void txtEnderecoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtEnderecoFocusLost
        txtEndereco.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_txtEnderecoFocusLost

    private void txtbairroFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtbairroFocusLost
        txtbairro.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_txtbairroFocusLost

    private void txtCidadeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCidadeFocusLost
        txtCidade.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_txtCidadeFocusLost

    private void txtEnderecoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtEnderecoFocusGained
       txtEndereco.setBackground(new Color(255, 255, 0));
    }//GEN-LAST:event_txtEnderecoFocusGained

    private void txtbairroFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtbairroFocusGained
       txtbairro.setBackground(new Color(255, 255, 0));
    }//GEN-LAST:event_txtbairroFocusGained

    private void txtCidadeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCidadeFocusGained
       txtCidade.setBackground(new Color(255, 255, 0));
    }//GEN-LAST:event_txtCidadeFocusGained

    private void txtDataCadastroFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDataCadastroFocusGained
       txtDataCadastro.setBackground(new Color(255, 255, 0));
    }//GEN-LAST:event_txtDataCadastroFocusGained

    private void txtTipoAtendimentoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTipoAtendimentoFocusGained
     
    }//GEN-LAST:event_txtTipoAtendimentoFocusGained

    private void JCtipoSanguineoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_JCtipoSanguineoFocusGained
       JCtipoSanguineo.setBackground(new Color(255, 255, 0));
    }//GEN-LAST:event_JCtipoSanguineoFocusGained

    private void JCtipoSanguineoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_JCtipoSanguineoFocusLost
       JCtipoSanguineo.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_JCtipoSanguineoFocusLost

    private void txtDocumentoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDocumentoKeyReleased
       
        if (codigoPaciente == 0) {
            if (jaCadastrado(txtDocumento.getText())) {
                String msgCPF = "O CPF ";
                msgCPF += txtDocumento.getText() + " CPF já está cadastrado no sistema!";
                JOptionPane.showMessageDialog(rootPane, msgCPF, "Atenção", JOptionPane.INFORMATION_MESSAGE,iconStop);
                txtDocumento.requestFocus();
                txtDocumento.setText("");
                return;
               
            }

        }            
    }//GEN-LAST:event_txtDocumentoKeyReleased

    private void txtDocumentoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDocumentoKeyPressed
        
    }//GEN-LAST:event_txtDocumentoKeyPressed

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
            java.util.logging.Logger.getLogger(TelaPacientesRecepcao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaPacientesRecepcao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaPacientesRecepcao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaPacientesRecepcao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaPacientesRecepcao().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> JCtipoSanguineo;
    public javax.swing.JComboBox<String> JcTipoConvenio;
    public javax.swing.JComboBox<String> JcUF;
    private javax.swing.JLabel LABELDATAcADASTRO;
    private javax.swing.JButton btnCapturarFoto;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEscolherFoto;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnFicha;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnSair1;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JButton btnpesquisarPorNome;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel documento;
    private javax.swing.JLabel documento1;
    private javax.swing.JLabel documento2;
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
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblFoto;
    public javax.swing.JTable tblPacientes;
    private javax.swing.JFormattedTextField txtAltura;
    public javax.swing.JFormattedTextField txtCep;
    public javax.swing.JTextField txtCidade;
    private org.jdesktop.swingx.JXDatePicker txtDataCadastro;
    private javax.swing.JFormattedTextField txtDataNascimento;
    private javax.swing.JFormattedTextField txtDocumento;
    public javax.swing.JTextField txtEndereco;
    public javax.swing.JTextField txtId;
    private javax.swing.JTextField txtIdade;
    public javax.swing.JTextField txtNomePaciente;
    private javax.swing.JTextField txtPeso;
    private javax.swing.JTextField txtPesquisar;
    private javax.swing.JTextField txtProntuario;
    private javax.swing.JLabel txtRegistros;
    private javax.swing.JTextField txtSus;
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
        AuxiliarFicha print = new AuxiliarFicha();
        print.setIdpaciente(txtId.getText());
        print.setDatacadastro(txtDataCadastro.getEditor().getText());
        print.setTipoconvenio(JcTipoConvenio.getSelectedItem().toString());
        print.setDocumentopaciente(txtDocumento.getText());
        print.setNomepaciente(txtNomePaciente.getText());
        print.setCeppaciente(txtCep.getText());
        print.setEnderecopaciente(txtEndereco.getText());
        print.setBairro(txtbairro.getText());
        print.setCidadepaciente(txtCidade.getText());
        print.setUfpaciente(JcUF.getSelectedItem().toString());
        print.setDatanascimento(txtDataNascimento.getText());
        print.setTelefonecelular(txtTelefoneCelular.getText());
        print.setTipoatendimento(txtTipoAtendimento.getSelectedItem().toString());
        print.setStatus(jCStatus.getSelectedItem().toString());
        print.setIdade(txtIdade.getText());
        print.setSexo(jCSexo.getSelectedItem().toString());
        print.setPeso(txtPeso.getText());
        print.setAltura(txtAltura.getText());
        print.setProntuario(txtProntuario.getText());
        lista.add(print);
        return lista;
    }
    
    
    public void atalho() {
        //opção numero 2
        InputMap im = jPanel6.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0), "v03");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_F6, 0), "v06");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "vesc");

        ActionMap am = jPanel6.getActionMap();
        am.put("v03", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
            }
        });

        am.put("v06", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarPacientes();
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
