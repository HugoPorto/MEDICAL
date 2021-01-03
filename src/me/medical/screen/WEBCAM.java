/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * WEBCAM.java
 *
 * Created on 23/01/2018, 18:13:09
 */
package me.medical.screen;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
/**
 *
 * @author Giovane
 */
public class WEBCAM extends javax.swing.JDialog {
    private Dimension dimensao_default;
    private Webcam WEBCAM;
    boolean executando = true;
    
    public byte[] LEVA_BYTES =null;
    
    /** Creates new form WEBCAM */
    public WEBCAM(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setIcon();
        INICIALIZA();
    }
    
    
    private void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("Receita.png")));
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLayeredPane1 = new javax.swing.JLayeredPane();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        FotoWebcam = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLayeredPane1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/OK_1.png"))); // NOI18N
        jButton1.setText("START");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jLayeredPane1.add(jButton1);
        jButton1.setBounds(20, 20, 120, 40);

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/cancelar.png"))); // NOI18N
        jButton2.setText("STOP");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jLayeredPane1.add(jButton2);
        jButton2.setBounds(20, 70, 120, 40);

        jButton3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/maquina foto_1.png"))); // NOI18N
        jButton3.setText("CAPTURA");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jLayeredPane1.add(jButton3);
        jButton3.setBounds(20, 120, 120, 40);

        FotoWebcam.setBackground(new java.awt.Color(0, 0, 0));
        FotoWebcam.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        FotoWebcam.setForeground(new java.awt.Color(51, 255, 0));
        FotoWebcam.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        FotoWebcam.setText("          ");
        FotoWebcam.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        FotoWebcam.setOpaque(true);
        jLayeredPane1.add(FotoWebcam);
        FotoWebcam.setBounds(170, 20, 190, 140);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
                .addContainerGap())
        );

        setSize(new java.awt.Dimension(414, 242));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

    new Thread(){
    public void run(){
    executando = true;
    FotoWebcam.setText("Iniciando...");     
    WEBCAM.open();     
    FotoWebcam.setText("");    
    INICIALIZA_VIDEO();
    }
    }.start();

}//GEN-LAST:event_jButton1ActionPerformed

private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
    
    new Thread(){
    public void run(){  
    executando = false;
    WEBCAM.close();    
    FotoWebcam.setIcon(null);
     }
    }.start();
    
}//GEN-LAST:event_jButton2ActionPerformed

private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

    
     try{
            executando = false;
            ByteArrayOutputStream buff = new ByteArrayOutputStream();
            ImageIO.write(WEBCAM.getImage(), "JPG", buff);
            byte[] bytes = buff.toByteArray();
//            LEVA_BYTES = bytes; // AQUI A IMAGEM ESTÁ CONVERTIDA EM BYTES NO TAMANHO ORIGINAL

            ByteArrayInputStream is = new ByteArrayInputStream(bytes);
            BufferedImage imagem = ImageIO.read(is);
            
            int Nova_Largura = 500, Nova_Altura = 500; //AQUI EU DEFINO QUAL SERÁ O NOVO TAMANHO PARA MINHA IMAGEM
            BufferedImage new_img = new BufferedImage(Nova_Largura, Nova_Altura, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = new_img.createGraphics();
            g.drawImage(imagem, 0, 0, Nova_Largura, Nova_Altura, null);
//            ImageIO.write(new_img, "JPG", new File("C:\\fototeste.jpg"));
            
            ByteArrayOutputStream buff2 = new ByteArrayOutputStream();
            ImageIO.write(new_img, "JPG", buff2);
            LEVA_BYTES = buff2.toByteArray(); // AQUI A IMAGEM ESTÁ CONVERTIDA EM BYTES NO TAMANHO PERSONALIZADO
            
            
            WEBCAM.close();
            FotoWebcam.setIcon(null);
            dispose();
            
            
//            ImageIO.write(imagem, "JPG", new File("C:\\fototeste.jpg"));
            
    }catch(IOException e){
        System.out.println(e);
    }catch(Exception e){
        System.out.println(e);
    }  
    
}//GEN-LAST:event_jButton3ActionPerformed

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
            java.util.logging.Logger.getLogger(WEBCAM.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(WEBCAM.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(WEBCAM.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(WEBCAM.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                WEBCAM dialog = new WEBCAM(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel FotoWebcam;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLayeredPane jLayeredPane1;
    // End of variables declaration//GEN-END:variables


private void INICIALIZA() {
        try {
//          dimensao_default = new Dimension(176, 144); //UTILIZO A DIMENSÃO DE MINHA PREFERÊNCIA
            dimensao_default = WebcamResolution.VGA.getSize();//UTILIZA A DIMENSÃO PADRÃO DA WEBCAM
            WEBCAM = Webcam.getDefault(); //UTILIZA A WEBCAM PADRÃO DO COMPUTADOR            
//          List list = Webcam.getWebcams();            
//          for (int i = 0; i < list.size(); i++) {
//          WEBCAM =  (Webcam) list.get(i); //UTILIZA A ÚLTIMA WEBCAM ENCONTRADA, SE EXISTIR MAIS DE UMA PEGA A ÚLTIMA
//          }
            WEBCAM.setViewSize(dimensao_default); 
                        
            for(Dimension dimensao : WEBCAM.getViewSizes()){ // AQUI EU CONSIGO SABER QUAIS SÃO RESOLUÇÕES SUPORTADA PELA WEBCAM
            System.out.println("Largura: "+dimensao.getWidth()+"  Altura: "+dimensao.getHeight());                       
            }
            
        } catch (Exception e) {
//            e.printStackTrace();            
        }
    }

    private void INICIALIZA_VIDEO(){
       new Thread() {
        @Override
        public void run() {
            while (true&&executando) {
                try {
                    Image imagem = WEBCAM.getImage();
                    ImageIcon icon = new ImageIcon(imagem);
                    icon.setImage(icon.getImage().getScaledInstance(FotoWebcam.getWidth(), FotoWebcam.getHeight(), 100));
                    FotoWebcam.setIcon(icon);
                    Thread.sleep(50);
                } catch (Exception e) {
                     System.out.println(e);                 
                }
            }
        }
    }.start();
    }

}
