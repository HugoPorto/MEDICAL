package me.medical.screen;

import me.medical.controller.Conexao;
import me.medical.utils.Msg;
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
public class TelaPacientesAnamnese extends javax.swing.JFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    /**
     * Creates new form TelaPacientesClassificacao
     */
    public TelaPacientesAnamnese() {
        initComponents();
        pintarColumnaTabla();
        listarDadosPaciente();
        conexao = Conexao.conector();
        setIcon();
        atualizarTempoEmTempo();
    }

    private void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("Receita.png")));
    }

    void pintarColumnaTabla() {
        ColorearFilas color = new ColorearFilas(7);
        TabelaClassificacao.getColumnModel().getColumn(7).setCellRenderer(color);
    }

    private void atualizarTempoEmTempo() {
        try {
            final java.util.Timer t = new java.util.Timer();
            t.schedule(new TimerTask() {
                @Override
                public void run() {
                    listarDadosPaciente();
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
    public void listarDadosPaciente() {
        DefaultTableModel model = (DefaultTableModel) TabelaClassificacao.getModel();
        model.setNumRows(0);
        String sql = "Select NomePaciente,FrequenciaCardiaca,FrequenciaRespiratoria,Temperatura,Hgt,Saturacao,PressaoArterial,ClassificacaoClinica,QueixaPrincipal,Prontuario from tb_classificacao WHERE tb_classificacao.NomePaciente "
                + " not in (SELECT tb_anamnese.NomePaciente FROM tb_anamnese WHERE tb_anamnese.`Data`=NOW()) order by tb_classificacao.ClassificacaoClinica desc"
                + "";
        try {
            pst = Conexao.conector().prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                    //rs.getString("Codigo"),
                    //Utils.convertData(rs.getDate("DataCadastro")),
                    rs.getString("NomePaciente"),
                    rs.getString("FrequenciaCardiaca"),
                    rs.getString("FrequenciaRespiratoria"),
                    rs.getString("Temperatura"),
                    rs.getString("Hgt"),
                    rs.getString("Saturacao"),
                    rs.getString("PressaoArterial"),
                    rs.getString("ClassificacaoClinica"),
                    rs.getString("QueixaPrincipal"),
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
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        btnSair1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Tela Classificação Pacientes");
        setExtendedState(6);

        TabelaClassificacao.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "NOME DO PACIENTE", "FC", "FR", "HGT", "TEMP", "PRESSÃO", "SATURAÇÃO", "CLASSIFICAÇÃO CLÍNICA", "QUEIXA PRINCIPAL", "PRONTUÁRIO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
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
            TabelaClassificacao.getColumnModel().getColumn(0).setMinWidth(850);
            TabelaClassificacao.getColumnModel().getColumn(0).setPreferredWidth(850);
            TabelaClassificacao.getColumnModel().getColumn(0).setMaxWidth(850);
            TabelaClassificacao.getColumnModel().getColumn(1).setMinWidth(0);
            TabelaClassificacao.getColumnModel().getColumn(1).setPreferredWidth(0);
            TabelaClassificacao.getColumnModel().getColumn(1).setMaxWidth(0);
            TabelaClassificacao.getColumnModel().getColumn(2).setMinWidth(0);
            TabelaClassificacao.getColumnModel().getColumn(2).setPreferredWidth(0);
            TabelaClassificacao.getColumnModel().getColumn(2).setMaxWidth(0);
            TabelaClassificacao.getColumnModel().getColumn(3).setMinWidth(0);
            TabelaClassificacao.getColumnModel().getColumn(3).setPreferredWidth(0);
            TabelaClassificacao.getColumnModel().getColumn(3).setMaxWidth(0);
            TabelaClassificacao.getColumnModel().getColumn(4).setMinWidth(0);
            TabelaClassificacao.getColumnModel().getColumn(4).setPreferredWidth(0);
            TabelaClassificacao.getColumnModel().getColumn(4).setMaxWidth(0);
            TabelaClassificacao.getColumnModel().getColumn(5).setMinWidth(0);
            TabelaClassificacao.getColumnModel().getColumn(5).setPreferredWidth(0);
            TabelaClassificacao.getColumnModel().getColumn(5).setMaxWidth(0);
            TabelaClassificacao.getColumnModel().getColumn(6).setMinWidth(0);
            TabelaClassificacao.getColumnModel().getColumn(6).setPreferredWidth(0);
            TabelaClassificacao.getColumnModel().getColumn(6).setMaxWidth(0);
            TabelaClassificacao.getColumnModel().getColumn(8).setMinWidth(0);
            TabelaClassificacao.getColumnModel().getColumn(8).setPreferredWidth(0);
            TabelaClassificacao.getColumnModel().getColumn(8).setMaxWidth(0);
            TabelaClassificacao.getColumnModel().getColumn(9).setMinWidth(150);
            TabelaClassificacao.getColumnModel().getColumn(9).setPreferredWidth(150);
            TabelaClassificacao.getColumnModel().getColumn(9).setMaxWidth(150);
        }

        javax.swing.GroupLayout jXTitledPanel1Layout = new javax.swing.GroupLayout(jXTitledPanel1.getContentContainer());
        jXTitledPanel1.getContentContainer().setLayout(jXTitledPanel1Layout);
        jXTitledPanel1Layout.setHorizontalGroup(
            jXTitledPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        jXTitledPanel1Layout.setVerticalGroup(
            jXTitledPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 406, Short.MAX_VALUE)
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 51));
        jLabel2.setText("Pacientes Cadastrados para Atendimento");

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 523, Short.MAX_VALUE)
                .addComponent(btnSair1)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnSair1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jXTitledPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jXTitledPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        setSize(new java.awt.Dimension(1037, 571));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSair1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSair1ActionPerformed

        //Pergunta se o usuário deseja realmente sair do sistema
        int sair = JOptionPane.showConfirmDialog(null, "DESEJA SAIR DA TELA?", "ATENÇÃO", JOptionPane.YES_NO_OPTION);
        if (sair == JOptionPane.YES_OPTION) {
            dispose();
        }
    }//GEN-LAST:event_btnSair1ActionPerformed

    private void TabelaClassificacaoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabelaClassificacaoMouseClicked

        if (evt.getClickCount() == 1) {
            ChamaDados();
            ChamaDadosnaConsultorio();
        }
    }//GEN-LAST:event_TabelaClassificacaoMouseClicked

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
            java.util.logging.Logger.getLogger(TelaPacientesAnamnese.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaPacientesAnamnese.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaPacientesAnamnese.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaPacientesAnamnese.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaPacientesAnamnese().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TabelaClassificacao;
    private javax.swing.JButton btnSair1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private org.jdesktop.swingx.JXTitledPanel jXTitledPanel1;
    // End of variables declaration//GEN-END:variables

//chama a tela qque possui os dados e serao setados abaixo
    TelaAnamneseMedica tela = new TelaAnamneseMedica();

    //chamar metodo com os dados
    private void ChamaDados() {
        //chama a tela ja com os dados
        tela = new TelaAnamneseMedica(this);
        // chama a tela ja com os dados
        int index = TabelaClassificacao.getSelectedRow();
        TableModel model = TabelaClassificacao.getModel();
        // String codigo = model.getValueAt(index, 0).toString();
        String NomePaciente = model.getValueAt(index, 0).toString();
        String FC = model.getValueAt(index, 1).toString();
        String FR = model.getValueAt(index, 2).toString();
        String TEMP = model.getValueAt(index, 3).toString();
        String HGT = model.getValueAt(index, 4).toString();
        String SAT = model.getValueAt(index, 5).toString();
        String PRES = model.getValueAt(index, 6).toString();
        String QUEIXA = model.getValueAt(index, 8).toString();
        String PRONTUARIO = model.getValueAt(index, 9).toString();

        tela.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        tela.txtNomePaciente.setText(NomePaciente);
        tela.jtfFrequenciaCardiaca.setText(FC);
        tela.jtfFrequenciaRespiratoria.setText(FR);
        tela.jtfTemperatura.setText(TEMP);
        tela.jtfHGT.setText(HGT);
        tela.jtfSaturacao.setText(SAT);
        tela.jtfPressaoArterial.setText(PRES);
        tela.areaQueixaPrincipal.setText(QUEIXA);
        tela.txtProntuario.setText(PRONTUARIO);
        tela.setVisible(true);
        tela.txtData.requestFocus();
        tela.pack();
    }

// chama nome da na tela chamar senha 
    TelaChamarConsultorio telaConsultorio = new TelaChamarConsultorio();
//chamar metodo com os dados

    private void ChamaDadosnaConsultorio() {
        //chama a tela ja com os dados
        int index = TabelaClassificacao.getSelectedRow();
        TableModel model = TabelaClassificacao.getModel();
        String NomePaciente = model.getValueAt(index, 0).toString();
        telaConsultorio.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        telaConsultorio.txtNomePaciente.setText(NomePaciente);
        telaConsultorio.setVisible(true);
        telaConsultorio.pack();
    }

}