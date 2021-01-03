package me.medical.screen;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

public class ColorearFilas_1 extends DefaultTableCellRenderer {

    private final int columna_patron;

    public ColorearFilas_1(int Colpatron) {
        this.columna_patron = Colpatron;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean Selected, boolean hasFocus, int row, int col) {
        Font font = new Font("Courier", Font.BOLD, 16);
        switch (table.getValueAt(row, columna_patron).toString()) {

            case "Internado":
                setForeground(Color.RED);
                setFont(font);
                break;
                
                 case "Disponivel":
                setForeground(Color.green);
                setFont(font);
                break;

            case "Higiênizando":
                setForeground(Color.blue);
                setFont(font);
                break;

            case "Em Manutenção":
                setForeground(Color.ORANGE);
                setFont(font);
                break;

            default:
                break;
        }
        super.getTableCellRendererComponent(table, value, Selected, hasFocus, row, col);
        return this;
    }
    
}
