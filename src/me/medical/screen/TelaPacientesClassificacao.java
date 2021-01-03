package me.medical.screen;

import me.medical.controller.Conexao;
import me.medical.utils.Msg;
import me.medical.utils.Utils;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.TimerTask;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Adriano Zanette
 */
public class TelaPacientesClassificacao extends javax.swing.JFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    /**
     * Creates new form TelaPacientesClassificacao
     */
    public TelaPacientesClassificacao() {
        initComponents();
        listarPacientesClassificacao();
        conexao = Conexao.conector();
        setIcon();
        atualizarTempoEmTempo();
    }

    private void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("Receita.png")));
    }

    private void atualizarTempoEmTempo() {
        try {
            final java.util.Timer t = new java.util.Timer();
            t.schedule(new TimerTask() {
                @Override
                public void run() {
                    listarPacientesClassificacao();
                    System.out.println("Atualizou a Tabela");
                }
            },
                    120000// 60000 //após 1 minuto ele irá iniciar a tarefa
                    ,
                     120000);    //após 1 minuto, executará novamente
        } catch (Exception e) {
            e.printStackTrace();
            Msg.alerta(this, "Erro ao Atualizar a Tabela!\nErro: " + e.getMessage());
        }
    }

    // lista na tabelinha 
    public void listarPacientesClassificacao() {
        DefaultTableModel model = (DefaultTableModel) TabelaClassificacao.getModel();
        model.setNumRows(0);
        String sql = "Select DataCadastro,NomePaciente,TipoAtendimento,Prontuario from tb_agenda order by DataCadastro";
        try {
            pst = Conexao.conector().prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                    //rs.getString("Codigo"),
                    Utils.convertData(rs.getDate("DataCadastro")),
                    rs.getString("NomePaciente"),
                    rs.getString("TipoAtendimento"),
                    rs.getString("Prontuario"),});
            }
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

        jXTitledPanel1 = new org.jdesktop.swingx.JXTitledPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TabelaClassificacao = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Tela Classificação Pacientes");
        setExtendedState(MAXIMIZED_BOTH);

        jXTitledPanel1.setTitle("Pacientes para Classificação");
        jXTitledPanel1.setTitleFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        TabelaClassificacao.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Data Cadastro", "Nome do Paciente", "Tipo Atendimento", "Prontuário"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TabelaClassificacao.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabelaClassificacaoMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TabelaClassificacao);
        if (TabelaClassificacao.getColumnModel().getColumnCount() > 0) {
            TabelaClassificacao.getColumnModel().getColumn(0).setMinWidth(130);
            TabelaClassificacao.getColumnModel().getColumn(0).setPreferredWidth(130);
            TabelaClassificacao.getColumnModel().getColumn(0).setMaxWidth(130);
            TabelaClassificacao.getColumnModel().getColumn(1).setMinWidth(700);
            TabelaClassificacao.getColumnModel().getColumn(1).setPreferredWidth(700);
            TabelaClassificacao.getColumnModel().getColumn(1).setMaxWidth(700);
        }

        javax.swing.GroupLayout jXTitledPanel1Layout = new javax.swing.GroupLayout(jXTitledPanel1.getContentContainer());
        jXTitledPanel1.getContentContainer().setLayout(jXTitledPanel1Layout);
        jXTitledPanel1Layout.setHorizontalGroup(
            jXTitledPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1174, Short.MAX_VALUE)
        );
        jXTitledPanel1Layout.setVerticalGroup(
            jXTitledPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 507, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jXTitledPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jXTitledPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        setSize(new java.awt.Dimension(1214, 571));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents


    private void TabelaClassificacaoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabelaClassificacaoMouseClicked

        if (evt.getClickCount() == 1) {
            ChamaDados();
            ChamaDadosnaTriagem();
    }//GEN-LAST:event_TabelaClassificacaoMouseClicked
    }

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
            java.util.logging.Logger.getLogger(TelaPacientesClassificacao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaPacientesClassificacao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaPacientesClassificacao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaPacientesClassificacao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaPacientesClassificacao().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TabelaClassificacao;
    private javax.swing.JScrollPane jScrollPane1;
    private org.jdesktop.swingx.JXTitledPanel jXTitledPanel1;
    // End of variables declaration//GEN-END:variables

// chama nome da na tela chamar senha 
    TelaChamarTriagem telaTriagem = new TelaChamarTriagem();
//chamar metodo com os dados

    private void ChamaDadosnaTriagem() {
        //chama a tela ja com os dados
        int index = TabelaClassificacao.getSelectedRow();
        TableModel model = TabelaClassificacao.getModel();
        // String codigo = model.getValueAt(index, 0).toString();
        String NomePaciente = model.getValueAt(index, 1).toString();
        telaTriagem.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        telaTriagem.txtNomePaciente.setText(NomePaciente);
        telaTriagem.setVisible(true);
        telaTriagem.pack();
    }

    // chama dados da tela triagem
    TelaEnfermagemClassificacao tela = new TelaEnfermagemClassificacao();

    //chamar metodo com os dados
    private void ChamaDados() {
        //chama a tela ja com os dados
        tela = new TelaEnfermagemClassificacao(this);//o segredo esta aqui
        int index = TabelaClassificacao.getSelectedRow();
        TableModel model = TabelaClassificacao.getModel();
        // String codigo = model.getValueAt(index, 0).toString();
        String NomePaciente = model.getValueAt(index, 1).toString();
        String TipoAtendimento = model.getValueAt(index, 2).toString();
        String Prontuario = model.getValueAt(index, 3).toString();

        tela.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        tela.txtNomePaciente.setText(NomePaciente);
        tela.txtTipoAtendimento.setText(TipoAtendimento);
        tela.txtProntuario.setText(Prontuario);
        //tela.labelData.setText(Data);
        tela.setVisible(true);
        tela.txtHorarioClassificacao.requestFocus();
        tela.pack();

    }

}
