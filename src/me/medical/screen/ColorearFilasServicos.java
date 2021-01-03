package me.medical.screen;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

public class ColorearFilasServicos extends DefaultTableCellRenderer {

    private final int columna_patron;

    public ColorearFilasServicos(int Colpatron) {
        this.columna_patron = Colpatron;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean Selected, boolean hasFocus, int row, int col) {
        Font font = new Font("Courier", Font.BOLD, 16);
        switch (table.getValueAt(row, columna_patron).toString()) {

            case "Ativo":
                setForeground(Color.GREEN);
                setFont(font);
                break;
            case "Inativo":
                setForeground(Color.RED);
                setFont(font);
                break;

            case "Em manutenção":
                setForeground(Color.RED);
                setFont(font);
                break;
                
                case "Sem Atendimento":
                setForeground(Color.RED);
                setFont(font);
                break;

            case "Em Atendimento":
                setForeground(Color.GREEN);
                setFont(font);
                break;
            default:
        }
        super.getTableCellRendererComponent(table, value, Selected, hasFocus, row, col);
        return this;
    }

}


