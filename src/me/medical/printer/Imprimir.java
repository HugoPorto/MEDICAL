/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.medical.printer;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.ServiceUI;
import javax.print.SimpleDoc;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.DocumentName;
import javax.print.attribute.standard.Sides;
import javax.swing.JOptionPane;

/**
 *
 * @author Breke
 */
public class Imprimir {

    PrintService impressora;

    public void imprimirArquivo(boolean mostrarDialogo) throws FileNotFoundException {
        System.out.println(mostrarDialogo);
        try {
            PrintService[] servicosImpressao = PrintServiceLookup.lookupPrintServices(DocFlavor.INPUT_STREAM.AUTOSENSE, null);
            System.out.println("Impressoras com suporte: " + servicosImpressao.length); // Localiza a impressora padrÃ£o 
            impressora = PrintServiceLookup.lookupDefaultPrintService();
            System.out.println("Impressora: " + impressora.getName());
            System.out.println("Imprimindo arquivo-texto"); // DefiniÃ§Ã£o de atributos do conteÃºdo a ser impresso: 
            DocFlavor docFlavor = DocFlavor.INPUT_STREAM.AUTOSENSE; // Atributos de impressÃ£o do documento 
            HashDocAttributeSet attributes = new HashDocAttributeSet(); // InputStream apontando para o conteÃºdo a ser impresso 
            FileInputStream fi = new FileInputStream("C:\\java\\java.txt"); // Cria um Doc para impressÃ£o a partir do arquivo exemplo.txt 
            Doc documentoTexto = new SimpleDoc(fi, docFlavor, attributes); // Configura o conjunto de parametros para a impressora 
            PrintRequestAttributeSet printerAttributes = new HashPrintRequestAttributeSet();

            // exibe um dialogo de configuracoes de impressao 
            if (mostrarDialogo) {
                PrintService servico = ServiceUI.printDialog(null, 320, 240, servicosImpressao, impressora, docFlavor, printerAttributes);
                if (servico != null) {
                    DocPrintJob printJob = servico.createPrintJob();
                    printJob.print(documentoTexto, printerAttributes);
                }
            } else { // Cria uma tarefa de impressÃ£o 
                DocPrintJob printJob = impressora.createPrintJob(); // Adiciona propriedade de impressÃ£o: imprimir duas cÃ³pias 
                printerAttributes.add(new Copies(1)); // Imprime o documento sem exibir uma tela de dialogo 
                printJob.print(documentoTexto, printerAttributes);
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            System.out.println("Arquivo file.txt não encontrado!");
        } catch (PrintException ex2) {
            System.out.println("Erro de impressão: " + ex2.getMessage());
        }
    }

    public void italicOn(String textos) {
        String texto = "\u001B" + "M";
        PrintService impressoras = PrintServiceLookup.lookupDefaultPrintService();
        if (impressoras == null) {
            JOptionPane.showMessageDialog(null, "Nennhuma impressora foi encontrada. Instale uma impressora padrÃ£o \r\n(Generic Text Only) e reinicie o programa.");
        } else {
            try {
                DocPrintJob dpj = impressoras.createPrintJob();
                InputStream stream = new ByteArrayInputStream((texto + "\n").getBytes());
                DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
                Doc doc = new SimpleDoc(stream, flavor, null);
                dpj.print(doc, null);
            } catch (PrintException e) {
                e.printStackTrace();
            }
        }
    }

    public void italicOFF() {
        String texto = "" + (char) 53;
        PrintService impressoras = PrintServiceLookup.lookupDefaultPrintService();
        if (impressoras == null) {
            JOptionPane.showMessageDialog(null, "Nennhuma impressora foi encontrada. Instale uma impressora padrÃ£o \r\n(Generic Text Only) e reinicie o programa.");
        } else {
            try {
                DocPrintJob dpj = impressoras.createPrintJob();
                InputStream stream = new ByteArrayInputStream((texto + "\n").getBytes());
                DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
                Doc doc = new SimpleDoc(stream, flavor, null);
                dpj.print(doc, null);
            } catch (PrintException e) {
                e.printStackTrace();
            }
        }
    }

    public void cortar() {
        String texto = "" + (char) 27 + (char) 109;
        
        
        PrintService[] printServices = PrintServiceLookup.lookupPrintServices(DocFlavor.SERVICE_FORMATTED.PRINTABLE, null);

        PrintRequestAttributeSet attr_set = new HashPrintRequestAttributeSet();

        attr_set.add(new Copies(1));
        attr_set.add(Sides.ONE_SIDED);

        for (int i = 0; i < printServices.length; i++) {
            System.out.println(printServices[i].getName());
            if (printServices[i].getName().equals("IMPRESSORA SENHA")) {
                PrintService impressoras = printServices[i];
                String utf8;
                try {
                    utf8 = new String(texto.getBytes("UTF-8"), "UTF-8");
                    DocPrintJob dpj = impressoras.createPrintJob();
                    InputStream stream = new ByteArrayInputStream((utf8).getBytes());
                    DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
                    Doc doc = new SimpleDoc(stream, flavor, null);
                    dpj.print(doc, null);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    
        
        
//        PrintService impressoras = PrintServiceLookup.lookupDefaultPrintService();
//        if (impressoras == null) {
//            JOptionPane.showMessageDialog(null, "Nennhuma impressora foi encontrada. Instale uma impressora padrÃ£o \r\n(Generic Text Only) e reinicie o programa.");
//        } else {
//            try {
//                DocPrintJob dpj = impressoras.createPrintJob();
//                InputStream stream = new ByteArrayInputStream((texto + "\n").getBytes());
//                DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
//                Doc doc = new SimpleDoc(stream, flavor, null);
//                dpj.print(doc, null);
//            } catch (PrintException e) {
//                e.printStackTrace();
//            }
//        }
    }

    public boolean imprime(String texto) {
//        PrintService impressoras = PrintServiceLookup.lookupDefaultPrintService();
//        if (impressoras == null) {
//            JOptionPane.showMessageDialog(null, "Nennhuma impressora foi encontrada. Instale uma impressora padrão \r\n(Generic Text Only) e reinicie o programa.");
//        } else {
//            try {
//             
//                String utf8 = new String( texto.getBytes( "UTF-8" ) , "UTF-8" );
//                DocPrintJob dpj = impressoras.createPrintJob();
//                InputStream stream = new ByteArrayInputStream((utf8).getBytes());
//                DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
//                Doc doc = new SimpleDoc(stream, flavor, null);
//                dpj.print(doc, null);
//                return true;
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        return false;

        PrintService[] printServices = PrintServiceLookup.lookupPrintServices(DocFlavor.SERVICE_FORMATTED.PRINTABLE, null);

        PrintRequestAttributeSet attr_set = new HashPrintRequestAttributeSet();

        attr_set.add(new Copies(1));
        attr_set.add(Sides.ONE_SIDED);

        for (int i = 0; i < printServices.length; i++) {
           // System.out.println(printServices[i].getName());
            if (printServices[i].getName().equals("IMPRESSORA SENHA")) {
                PrintService impressoras = printServices[i];
                String utf8;
                try {
                    utf8 = new String(texto.getBytes("UTF-8"), "UTF-8");
                    DocPrintJob dpj = impressoras.createPrintJob();
                    InputStream stream = new ByteArrayInputStream((utf8).getBytes());
                    DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
                    Doc doc = new SimpleDoc(stream, flavor, null);
                    dpj.print(doc, null);
                    return true;
                } catch (Exception ex) {
                    Logger.getLogger(Imprimir.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return false;
    }
}
