package me.medical.utils;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.io.*;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.util.HashSet;
import java.awt.AWTKeyStroke;
import java.awt.Component;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;

/**
 *
 * @author Eleton Breke
 * @version 1.0
 * @since 2013-12-18
 * @email eletonbreke@yahoo.com.br
 *
 */
public class Utils {

    public static Object convertDouble;

    public static void copyFile(File source, File destination) throws IOException {

        FileChannel sourceChannel = null;
        FileChannel destinationChannel = null;
        try {
            sourceChannel = new FileInputStream(source).getChannel();
            destinationChannel = new FileOutputStream(destination).getChannel();
            sourceChannel.transferTo(0, sourceChannel.size(),
                    destinationChannel);
        } finally {
            if (sourceChannel != null && sourceChannel.isOpen()) {
                sourceChannel.close();
            }
            if (destinationChannel != null && destinationChannel.isOpen()) {
                destinationChannel.close();
            }
        }
    }

    public static byte[] converteFileToByteArray(String arquivo) throws FileNotFoundException, IOException {
        File f = new File(arquivo);
        FileInputStream fs = new FileInputStream(f);
        byte[] byt = new byte[(int) f.length()];
        for (int i = 0; i < f.length(); i++) {
            byt[i] = (byte) fs.read();
        }
        return byt;
    }

    public static void converteByteArrayToFile(byte[] bytes) throws FileNotFoundException, IOException {
        FileOutputStream fos = new FileOutputStream(new File("Definir pasta destino"));
        fos.write(bytes);
    }

    public static String getTempo() {
        Date data = new Date();
        int ano = data.getYear();
        int mes = data.getMonth();
        int dia = data.getDay();
        int hora = data.getHours();
        int minit = data.getMinutes();
        int segund = data.getSeconds();
        String nome = ano + "" + mes + "" + dia + "" + hora + "" + minit + "" + segund;
        return nome;
    }

    public static String longToMB(long size) {
        double kb = size / 1024;
        double mb = kb / 1024;
        String format = new DecimalFormat("#0.##").format(mb) + " Mb";
        return format;
    }

    public static String substituiCaracter(String s, int pos, char c) {
        return s.substring(0, pos) + c + s.substring(pos + 1);
    }

    public static String substituiCaracterCNPJ(String s, int pos, char c) {
        return s.substring(0, pos) + c + s.substring(pos - 1);
    }

    public static String convertDouble(double valor) {
        return new DecimalFormat("#0.00").format(valor);
    }

    public static String convertDinheiro(double valor) {
        return new DecimalFormat("#,##0.00").format(valor);
    }

    public static String convertDouble(double valor, String pattern) {
        return new DecimalFormat(pattern).format(valor);
    }

    public static void maximizar(JDialog janela) {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        screen = new Dimension((int) screen.getWidth(), (int) screen.getHeight() - 40);
        janela.setSize(screen);
        janela.setLocationRelativeTo(null);
    }

    public static void maximizar(JFrame janela) {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        screen = new Dimension((int) screen.getWidth(), (int) screen.getHeight() - 30);
        janela.setSize(screen);
        janela.setLocationRelativeTo(null);
    }

    public static void configTabela(JTable tabela, int[] largura) {
        tabela.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < largura.length; i++) {
            tabela.getColumnModel().getColumn(i).setPreferredWidth(largura[i]);

            if (largura[i] <= 0) {
                tabela.getColumnModel().getColumn(i).setMinWidth(0);
                tabela.getColumnModel().getColumn(i).setMaxWidth(0);
            }
        }
    }

    public static Date convertData(String date) {
        Date data = null;
        try {
            data = new SimpleDateFormat("dd/MM/yyyy").parse(date);
        } catch (ParseException ex) {
            //Msg.erro(null, "Erro ao converter data!!!\n\n" + ex.getMessage());
        }
        return data;
    }
    

    public static Date convertDataSalvar(String date) {
        Date data = null;
        try {
            data = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException ex) {
            //Msg.erro(null, "Erro ao converter data!!!\n\n" + ex.getMessage());
        }
        return data;
    }

    public static Date convertDataTime(String date) {
        Date data = null;
        try {
            data = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(date);
        } catch (ParseException ex) {
            ex.printStackTrace();  //Msg.erro(null, "Erro ao converter data!!!\n\n" + ex.getMessage());
        }
        return data;
    }

    public static String convertDateTime(Date date) {
        return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(date);
    }

    public static String convertData(Date date) {
        return new SimpleDateFormat("dd/MM/yyyy").format(date);
    }

    public static String convertData(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }

    public static String getExtFile(String file) {
        String arr[] = file.split("[\\.]");

        if (arr.length < 2) {
            return null;
        }
        return arr[arr.length - 1].trim().toLowerCase();
    }

    public static void deleteFile(String srcFile) {
        File file = new File(srcFile);

        if (file.isFile()) {
            file.delete();
        }
    }

    public static void deleteTree(String srcFile) {
        File file = new File(srcFile);

        if (file.isFile()) {
            file.delete();
        } else {
            File files[] = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                deleteTree(files[i].getAbsolutePath());
            }
        }
    }

    public static boolean isEmail(String email) {
        String regexp = "^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{1,3})+$";
        return email.matches(regexp);
    }

    public static void setButtonCursor(JButton button) {
        button.setCursor(Cursor.getPredefinedCursor(12));
    }

    public static void setShortTable(JTable tabela) {
        DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();
        TableRowSorter<TableModel> sorter;
        sorter = new TableRowSorter<TableModel>(modelo);
        tabela.setRowSorter(sorter);
    }

    public static void setTableCursor(JTable tabela) {
        tabela.setCursor(Cursor.getPredefinedCursor(12));
    }

    public static boolean isCPF(String cpf) {
        boolean retorno = true;

        if (cpf.matches("^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$")) {
            try {
                cpf = cpf.replace(".", "");
                cpf = cpf.replace("-", "");

                String cpfV = "";
                for (int i = 0; i <= 9; i++) {
                    cpfV = "";
                    for (int j = 1; j <= 11; j++) {
                        cpfV += i;
                    }
                    if (cpfV.equals(cpf)) {
                        return false;
                    }
                }

                int digito[] = new int[11];
                int dvInformado = Integer.parseInt(cpf.substring(9, 11));

                for (int i = 0; i <= 8; i++) {
                    digito[i] = Integer.parseInt(cpf.substring(i, i + 1));
                }

                int posicao = 10;
                int soma = 0;

                for (int i = 0; i <= 8; i++) {
                    soma = (soma + digito[i] * posicao);
                    posicao = (posicao - 1);
                }

                digito[9] = (soma % 11);

                if (digito[9] < 2) {
                    digito[9] = 0;
                } else {
                    digito[9] = (11 - digito[9]);
                }

                posicao = 11;
                soma = 0;

                for (int i = 0; i <= 9; i++) {
                    soma = (soma + digito[i] * posicao);
                    posicao = (posicao - 1);
                }

                digito[10] = (soma % 11);

                if (digito[10] < 2) {
                    digito[10] = 0;
                } else {
                    digito[10] = (11 - digito[10]);
                }

                int dv = (digito[9] * 10 + digito[10]);

                if (dv != dvInformado) {
                    retorno = false;
                }
            } catch (Exception e) {
                retorno = false;
            }
        } else {
            retorno = false;
        }
        return retorno;
    }

    public static boolean isCNPJ(String cnpj) {
        boolean retorno = true;

        if (!cnpj.matches("\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}")) {
            return false;
        }

        cnpj = cnpj.replace(".", "");
        cnpj = cnpj.replace(".", "");
        cnpj = cnpj.replace("-", "");
        cnpj = cnpj.replace("/", "");

        int a[] = new int[14];
        int x = 0, b = 0;
        int c[] = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

        for (int i = 0; i < 12; i++) {
            a[i] = Integer.parseInt(cnpj.substring(i, i + 1));
            b += (a[i] * c[i + 1]);
        }

        if ((x = b % 11) < 2) {
            a[12] = 0;
        } else {
            a[12] = (11 - x);
        }

        b = 0;
        for (int y = 0; y < 13; y++) {
            b += (a[y] * c[y]);
        }

        if ((float) (x = b % 11) < 2) {
            a[13] = 0;
        } else {
            a[13] = (11 - x);
        }

        int pos12 = Integer.parseInt(cnpj.substring(12, 13));
        int pos13 = Integer.parseInt(cnpj.substring(13, 14));

        if ((pos12 != a[12]) || (pos13 != a[13])) {
            retorno = false;
        }

        return retorno;
    }
    private GraphicsDevice device;

    public void setFullScreen() {
        GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        device = environment.getDefaultScreenDevice();

        final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setUndecorated(true);
        frame.setIgnoreRepaint(true);
        frame.setResizable(false);

        device.setFullScreenWindow(frame);

//       if (displayMode != null && device.isDisplayChangeSupported()) {
//          setDisplayMode(displayMode);
//          // fix for mac os x
//          frame.setSize(displayMode.getWidth(), displayMode.getHeight());
//       }
        frame.createBufferStrategy(2);
//       sleep(500);
    }

    public static boolean isCodigoBarra(String barCode) {
        int digit;
        int calculated;
        String ean;
        String checkSum = "131313131313";
        int sum = 0;

        if (barCode.length() == 8 || barCode.length() == 13) {
            digit = Integer.parseInt("" + barCode.charAt(barCode.length() - 1));
            ean = barCode.substring(0, barCode.length() - 1);
            for (int i = 0; i <= ean.length() - 1; i++) {
                sum += (Integer.parseInt("" + ean.charAt(i))) * (Integer.parseInt("" + checkSum.charAt(i)));
            }
            calculated = 10 - (sum % 10);
            return (digit == calculated);
        } else {
            return false;
        }
    }

    public static void maximumAndMinimumColumnSizeTable(JTable e, int column, int max, int min) {
        e.getColumnModel().getColumn(column).setMinWidth(min);
        e.getColumnModel().getColumn(column).setMaxWidth(max);
    }

    public static String getNome() {
        Date data = new Date();
        int year = data.getYear();
        int mount = data.getMonth();
        int day = data.getDay();
        int hours = data.getHours();
        int minutes = data.getMinutes();
        int secundes = data.getSeconds();
        return "" + year + "" + mount + "" + day + "" + hours + "" + minutes + "" + secundes;
    }

    public static String getErro(Throwable exception) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        exception.printStackTrace(pw);
        return (sw.toString());
    }

    public static String center(String s, int size) {
        return center(s, size, ' ');
    }

    private static String center(String s, int size, char pad) {
        if (s == null || size <= s.length()) {
            return s;
        }

        StringBuilder sb = new StringBuilder(size);
        for (int i = 0; i < (size - s.length()) / 2; i++) {
            sb.append(pad);
        }
        sb.append(s);
        while (sb.length() < size) {
            sb.append(pad);
        }
        return sb.toString();
    }

    public static void considerarEnterComoTab(Component comp) {
        // Colocando enter para pular de campo  
        HashSet conj = new HashSet(comp.getFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS));
        conj.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_ENTER, 0));
        comp.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, conj);

    }

    public static void considerarSetaComoTab(JButton comp) {
        // Colocando enter para pular de campo  
        HashSet conj = new HashSet(comp.getFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS));
        conj.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_DOWN, 0));
        conj.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_KP_DOWN, 0));
        comp.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, conj);

        conj = new HashSet(comp.getFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS));
        conj.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_UP, 0));
        conj.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_KP_UP, 0));
        comp.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, conj);

    }
    
    //esse metodo calcula os dias entre duas datas
    public static long calcularDias(Date inicio, Date fim) throws ParseException {
        Date dtInicial = inicio;
        Date dtFinal = fim;
        return (dtFinal.getTime() - dtInicial.getTime() + 3600000L) / 86400000L;
    }
}
