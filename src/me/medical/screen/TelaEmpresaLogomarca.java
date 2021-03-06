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
import java.awt.Color;
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
import java.util.ArrayList;
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
public class TelaEmpresaLogomarca extends javax.swing.JFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    byte[] BYTES_FOTO = null;
    List<ModeFoto> lista_foto = new ArrayList<>();
    String Caminho = System.getProperty("user.home") + "\\Desktop";

    /**
     * Creates new form TelaPacientes
     */
    public TelaEmpresaLogomarca() {
        initComponents();
        setIcon();
        conexao = Conexao.conector();
    }

    private void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("Receita.png")));
    }

    private void limparCampos() {
        lblFoto.setText("");
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

    public void cadastrarFoto() {
        Conexao conec = new Conexao();
        String sql = "Insert into tb_logomarca(foto) values(?)";

        try {
            pst = Conexao.conector().prepareStatement(sql);
            pst.setBytes(1, BYTES_FOTO);
            pst.execute();
            JOptionPane.showMessageDialog(rootPane, " Foto salvo com sucesso ");
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel6 = new javax.swing.JPanel();
        btnSalvar = new javax.swing.JButton();
        btnSair1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        lblFoto = new javax.swing.JLabel();
        btnCapturarFoto = new javax.swing.JButton();
        btnEscolherFoto = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastrar Logo Marca");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel6.setBackground(new java.awt.Color(153, 153, 153));
        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());

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

        btnSair1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnSair1.setForeground(new java.awt.Color(51, 51, 51));
        btnSair1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImagensTelas/Sair.png"))); // NOI18N
        btnSair1.setText("SAIR");
        btnSair1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSair1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnSair1, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 298, Short.MAX_VALUE)
                .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSair1, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                    .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        getContentPane().add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 280, 520, -1));

        jPanel2.setBackground(new java.awt.Color(153, 153, 153));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblFoto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblFoto.setEnabled(false);
        lblFoto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                lblFotoFocusGained(evt);
            }
        });
        lblFoto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblFotoMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblFoto, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 477, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblFoto, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE)
        );

        btnCapturarFoto.setBackground(new java.awt.Color(255, 255, 255));
        btnCapturarFoto.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnCapturarFoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/foto.png"))); // NOI18N
        btnCapturarFoto.setText("Capturar");
        btnCapturarFoto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCapturarFotoMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnCapturarFotoMousePressed(evt);
            }
        });
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
        btnEscolherFoto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEscolherFotoMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnEscolherFotoMousePressed(evt);
            }
        });
        btnEscolherFoto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEscolherFotoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(btnEscolherFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCapturarFoto)))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEscolherFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCapturarFoto))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 520, 260));

        setSize(new java.awt.Dimension(555, 397));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        cadastrarFoto();
        limparCampos();
    }//GEN-LAST:event_btnSalvarActionPerformed


    private void btnSair1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSair1ActionPerformed

        //Pergunta se o usuário deseja realmente sair do sistema
        int sair = JOptionPane.showConfirmDialog(null, "DESEJA SAIR DA TELA LOGOMARCA ?", "ATENÇÃO", JOptionPane.YES_NO_OPTION);
        if (sair == JOptionPane.YES_OPTION) {
            dispose();
        }
    }//GEN-LAST:event_btnSair1ActionPerformed

    private void btnEscolherFotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEscolherFotoActionPerformed
        LOCALIZAR_ARQUIVO();
    }//GEN-LAST:event_btnEscolherFotoActionPerformed

    private void btnCapturarFotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapturarFotoActionPerformed
        WEBCAM dialog = new WEBCAM(new javax.swing.JFrame(), true);
        dialog.setVisible(true);

        try {
            if (dialog.LEVA_BYTES != null) {
                BYTES_FOTO = dialog.LEVA_BYTES;
            }
            ImageIcon icon = new ImageIcon(BYTES_FOTO);
            //icon.setImage(icon.getImage().getScaledInstance(lblFoto.getWidth(), lblFoto.getHeight(), 100));
//            icon.setImage(icon.getImage().getScaledInstance(213, 218, 100));
            icon.setImage(icon.getImage().getScaledInstance(404, 105, 100));
            lblFoto.setIcon(icon);
        } catch (Exception e) {
        }
    }//GEN-LAST:event_btnCapturarFotoActionPerformed

    private void btnEscolherFotoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEscolherFotoMouseClicked
        
    }//GEN-LAST:event_btnEscolherFotoMouseClicked

    private void btnCapturarFotoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCapturarFotoMouseClicked
    
    }//GEN-LAST:event_btnCapturarFotoMouseClicked

    private void lblFotoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_lblFotoFocusGained
      
    }//GEN-LAST:event_lblFotoFocusGained

    private void lblFotoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblFotoMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_lblFotoMouseClicked

    private void btnEscolherFotoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEscolherFotoMousePressed
       btnSalvar.setEnabled(true);
    }//GEN-LAST:event_btnEscolherFotoMousePressed

    private void btnCapturarFotoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCapturarFotoMousePressed
        btnSalvar.setEnabled(true);
    }//GEN-LAST:event_btnCapturarFotoMousePressed

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
            java.util.logging.Logger.getLogger(TelaEmpresaLogomarca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaEmpresaLogomarca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaEmpresaLogomarca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaEmpresaLogomarca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new TelaEmpresaLogomarca().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCapturarFoto;
    private javax.swing.JButton btnEscolherFoto;
    private javax.swing.JButton btnSair1;
    private javax.swing.JButton btnSalvar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblFoto;
    // End of variables declaration//GEN-END:variables

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
                int Nova_Largura = 404, Nova_Altura = 105; //Aqui eu escolho qual será a altura e largura de px da imagem a ser salva
                BufferedImage new_img = new BufferedImage(Nova_Largura, Nova_Altura, BufferedImage.TYPE_4BYTE_ABGR);
                Graphics2D g = new_img.createGraphics();
                g.drawImage(imagem, 0, 0, Nova_Largura, Nova_Altura, null);
                ByteArrayOutputStream buff2 = new ByteArrayOutputStream();
                ImageIO.write(new_img, "JPG", buff2);
                BYTES_FOTO = buff2.toByteArray();

                ImageIcon icon = new ImageIcon(BYTES_FOTO);
                icon.setImage(icon.getImage().getScaledInstance(404, 105, 100));
                lblFoto.setIcon(icon);

            }

        } catch (Exception e) {
        }
    }

}
