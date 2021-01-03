/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.medical.screen;

import me.medical.controller.Conexao;
import me.medical.utils.Utils;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.Icon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Adriano Zanette
 */
public class TelaGeralDespesasPagas extends javax.swing.JFrame {

    String setar_dados = "";
    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    /**
     * Creates new form TelaGeralLeitosDisponiveis
     */
    public TelaGeralDespesasPagas() {
        initComponents();
        listarDespesas();
        conexao = Conexao.conector();
        setIcon();
    }

    private void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("Receita.png")));
    }

    // icone do joptionPane
    Icon icon = new javax.swing.ImageIcon(getClass().getResource("/Imagens/ok.png"));

    // icone do joptionPane
    Icon iconExcluir = new javax.swing.ImageIcon(getClass().getResource("/Imagens/Excluido.png"));

    public void listarDespesas() {
        Conexao conec = new Conexao();
        DefaultTableModel model = (DefaultTableModel) TabelaDespesas.getModel();
        model.setNumRows(0);
      
        String sql = "SELECT tb_cad_despesas.*, SUM(despesaspaciente.valorTotal) AS "
                + "totalDespesas FROM tb_cad_despesas, despesaspaciente WHERE tb_cad_despesas.codigo = despesaspaciente.idDespesa GROUP BY tb_cad_despesas.codigo";

                try {
            pst = Conexao.conector().prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("codigo"),
                    Utils.convertData(rs.getDate("DataCadastro")),
                    rs.getString("Prontuario"),
                    rs.getString("NomePaciente"),
                    rs.getString("Cpf"),
                    rs.getString("Telefone"),
                    rs.getString("TipoAcomodacao"),
                    Utils.convertData(rs.getDate("DataEntrada")),
                    Utils.convertData(rs.getDate("DataSaida")),
                    rs.getDouble("qtdDias"),
                    rs.getDouble("ValorDiarias"),
                    rs.getDouble("ValorTotalDiarias"),
                    rs.getDouble("totalDespesas"),
                    rs.getDouble("valorTotal"),
                    rs.getDouble("Dinheiro"),
                    rs.getDouble("Troco"),
                    rs.getString("Email"),
                });
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
        jXTitledPanel1 = new org.jdesktop.swingx.JXTitledPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TabelaDespesas = new javax.swing.JTable();

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
        ) {
            boolean[] canEdit = new boolean [] {
                false, true
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
            TabelaNomePaciente.getColumnModel().getColumn(0).setMinWidth(100);
            TabelaNomePaciente.getColumnModel().getColumn(0).setPreferredWidth(100);
            TabelaNomePaciente.getColumnModel().getColumn(0).setMaxWidth(100);
        }

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
        setTitle("Tela Despesas Pagas");
        setExtendedState(MAXIMIZED_BOTH);

        jXTitledPanel1.setInheritAlpha(false);
        jXTitledPanel1.setTitle("Demonstrativo de despesas");
        jXTitledPanel1.setTitleFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        TabelaDespesas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Código", "Data Cadastro", "Prontuário", "Nome do Paciente", "Cpf", "Telefone", "Tipo Acomodação", "Data Entrada", "Data Saída", "Dias Estadia", "Valor das Diarias", "Valor Total Estadias", "Valor Despesas", "Valor Total", "Dinheiro", "Troco", "Email Envio"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(TabelaDespesas);
        if (TabelaDespesas.getColumnModel().getColumnCount() > 0) {
            TabelaDespesas.getColumnModel().getColumn(0).setMinWidth(0);
            TabelaDespesas.getColumnModel().getColumn(0).setPreferredWidth(0);
            TabelaDespesas.getColumnModel().getColumn(0).setMaxWidth(0);
            TabelaDespesas.getColumnModel().getColumn(1).setMinWidth(90);
            TabelaDespesas.getColumnModel().getColumn(1).setPreferredWidth(90);
            TabelaDespesas.getColumnModel().getColumn(1).setMaxWidth(90);
            TabelaDespesas.getColumnModel().getColumn(2).setMinWidth(0);
            TabelaDespesas.getColumnModel().getColumn(2).setPreferredWidth(0);
            TabelaDespesas.getColumnModel().getColumn(2).setMaxWidth(0);
            TabelaDespesas.getColumnModel().getColumn(4).setMinWidth(0);
            TabelaDespesas.getColumnModel().getColumn(4).setPreferredWidth(0);
            TabelaDespesas.getColumnModel().getColumn(4).setMaxWidth(0);
            TabelaDespesas.getColumnModel().getColumn(5).setMinWidth(0);
            TabelaDespesas.getColumnModel().getColumn(5).setPreferredWidth(0);
            TabelaDespesas.getColumnModel().getColumn(5).setMaxWidth(0);
            TabelaDespesas.getColumnModel().getColumn(6).setMinWidth(110);
            TabelaDespesas.getColumnModel().getColumn(6).setPreferredWidth(110);
            TabelaDespesas.getColumnModel().getColumn(6).setMaxWidth(110);
            TabelaDespesas.getColumnModel().getColumn(7).setMinWidth(90);
            TabelaDespesas.getColumnModel().getColumn(7).setPreferredWidth(90);
            TabelaDespesas.getColumnModel().getColumn(7).setMaxWidth(90);
            TabelaDespesas.getColumnModel().getColumn(8).setMinWidth(80);
            TabelaDespesas.getColumnModel().getColumn(8).setPreferredWidth(80);
            TabelaDespesas.getColumnModel().getColumn(8).setMaxWidth(80);
            TabelaDespesas.getColumnModel().getColumn(9).setMinWidth(80);
            TabelaDespesas.getColumnModel().getColumn(9).setPreferredWidth(80);
            TabelaDespesas.getColumnModel().getColumn(9).setMaxWidth(80);
            TabelaDespesas.getColumnModel().getColumn(10).setMinWidth(100);
            TabelaDespesas.getColumnModel().getColumn(10).setPreferredWidth(100);
            TabelaDespesas.getColumnModel().getColumn(10).setMaxWidth(100);
            TabelaDespesas.getColumnModel().getColumn(11).setMinWidth(120);
            TabelaDespesas.getColumnModel().getColumn(11).setPreferredWidth(120);
            TabelaDespesas.getColumnModel().getColumn(11).setMaxWidth(120);
            TabelaDespesas.getColumnModel().getColumn(12).setMinWidth(100);
            TabelaDespesas.getColumnModel().getColumn(12).setPreferredWidth(100);
            TabelaDespesas.getColumnModel().getColumn(12).setMaxWidth(100);
            TabelaDespesas.getColumnModel().getColumn(13).setMinWidth(70);
            TabelaDespesas.getColumnModel().getColumn(13).setPreferredWidth(70);
            TabelaDespesas.getColumnModel().getColumn(13).setMaxWidth(70);
            TabelaDespesas.getColumnModel().getColumn(14).setMinWidth(0);
            TabelaDespesas.getColumnModel().getColumn(14).setPreferredWidth(0);
            TabelaDespesas.getColumnModel().getColumn(14).setMaxWidth(0);
            TabelaDespesas.getColumnModel().getColumn(15).setMinWidth(0);
            TabelaDespesas.getColumnModel().getColumn(15).setPreferredWidth(0);
            TabelaDespesas.getColumnModel().getColumn(15).setMaxWidth(0);
            TabelaDespesas.getColumnModel().getColumn(16).setMinWidth(200);
            TabelaDespesas.getColumnModel().getColumn(16).setPreferredWidth(200);
            TabelaDespesas.getColumnModel().getColumn(16).setMaxWidth(200);
        }

        javax.swing.GroupLayout jXTitledPanel1Layout = new javax.swing.GroupLayout(jXTitledPanel1.getContentContainer());
        jXTitledPanel1.getContentContainer().setLayout(jXTitledPanel1Layout);
        jXTitledPanel1Layout.setHorizontalGroup(
            jXTitledPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 839, Short.MAX_VALUE)
        );
        jXTitledPanel1Layout.setVerticalGroup(
            jXTitledPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 495, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jXTitledPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jXTitledPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(859, 559));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void TabelaNomePacienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabelaNomePacienteMouseClicked
//         seleciona a linha da tabela e seta nos campos
//        int linha = TabelaNomePaciente.getSelectedRow();
//        if (linha >= 0) {
//             numero da coluna da tabelinha
//            BtnLeitoUti01.setText(TabelaNomePaciente.getValueAt(linha, 0).toString());
//            BtnLeitoUti01.setText(TabelaNomePaciente.getValueAt(linha, 1).toString());
//             String dados0 = (TabelaNomePaciente.getValueAt(linha, 0).toString());
//            String dados1 = (TabelaNomePaciente.getValueAt(linha, 1).toString());
//            setar_dados = setar_dados +"Prontuario" + "\n" + ""  +  dados0;
//            setar_dados = setar_dados + "Paciente" + "  -  " + dados1;
//            BtnLeitoUti01.setText(setar_dados);
//            LUPA_NOME_PACIENTE.setVisible(false);
//            listarDados();
//           
//        }
    }//GEN-LAST:event_TabelaNomePacienteMouseClicked

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
            java.util.logging.Logger.getLogger(TelaGeralDespesasPagas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaGeralDespesasPagas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaGeralDespesasPagas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaGeralDespesasPagas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaGeralDespesasPagas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog LUPA_NOME_PACIENTE;
    private javax.swing.JTable TabelaDespesas;
    private javax.swing.JTable TabelaNomePaciente;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private org.jdesktop.swingx.JXTitledPanel jXTitledPanel1;
    // End of variables declaration//GEN-END:variables

}
