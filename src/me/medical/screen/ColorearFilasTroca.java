package me.medical.screen;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

public class ColorearFilasTroca extends DefaultTableCellRenderer {

    private final int columna_patron;

    public ColorearFilasTroca(int Colpatron) {
        this.columna_patron = Colpatron;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean Selected, boolean hasFocus, int row, int col) {
        Font font = new Font("Courier", Font.BOLD, 16);
        switch (table.getValueAt(row, columna_patron).toString()) {

            case "Livre":
                setBackground(Color.green);
                setFont(font);
                break;
                case "Internado":
                setBackground(Color.red);
                setForeground(Color.white);
                setFont(font);
                break;
                case "Manutenção":
                setBackground(Color.ORANGE);
                setForeground(Color.black);
                break;
                case "Higienizando":
                setBackground(Color.YELLOW);
                setForeground(Color.black);
                break;
                case "Alta Hospitalar":
                setBackground(Color.blue);
                setForeground(Color.white);
                break;
                default:
                break;
                
                
                        }
        super.getTableCellRendererComponent(table, value, Selected, hasFocus, row, col);
        return this;
    }

}
