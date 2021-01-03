package me.medical.utils;

import java.awt.Component;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.swing.JOptionPane;

/**
 * 
 * @author Eleton Breke
 * @version 1.0
 * @since 2013-12-18
 * @email eletonbreke@yahoo.com.br
 * 
 */

public class Msg {
    
      public static String getErro(Throwable exception) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        exception.printStackTrace(pw);
        return (sw.toString());
    }

    public static String getMessage(Exception e) {
        if (e.getMessage().length() > 80) {
            return e.getMessage().substring(0, 80);
        } else {
            return e.getMessage();
        }
    }

    public static void alerta(Component frame, Object str) {
        JOptionPane.showMessageDialog(frame, str, "ALERTA", JOptionPane.WARNING_MESSAGE);
    }

    public static void erro(Component frame, Object str) {
        JOptionPane.showMessageDialog(frame, str, "ERRO", JOptionPane.ERROR_MESSAGE);
    }

    public static void erro(Object str) {
        JOptionPane.showMessageDialog(null, str, "ERRO", JOptionPane.ERROR_MESSAGE);
    }

    public static void informacao(Component frame, Object str) {
        JOptionPane.showMessageDialog(frame, str, "INFORMAÇÃO", JOptionPane.INFORMATION_MESSAGE);
    }

    public static boolean confirmar(Component frame, Object str) {
        Object[] options = {"Sim", "Não"};
        int option = JOptionPane.showOptionDialog(frame, str, "CONFIRMAR", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        if (option == JOptionPane.YES_OPTION) {
            return true;
        } else {
            return false;
        }
    }
}
