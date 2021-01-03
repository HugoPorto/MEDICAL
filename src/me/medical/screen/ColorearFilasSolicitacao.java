package me.medical.screen;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

public class ColorearFilasSolicitacao extends DefaultTableCellRenderer {

    private final int columna_patron;

    public ColorearFilasSolicitacao(int Colpatron) {
        this.columna_patron = Colpatron;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean Selected, boolean hasFocus, int row, int col) {
        Font font = new Font("Courier", Font.BOLD, 16);
        switch (table.getValueAt(row, columna_patron).toString()) {

            case "Solicitado":
                setBackground(Color.red);
                setForeground(Color.white);
                break;
                case "Entregue":
                setBackground(Color.green);
                setForeground(Color.black);
                setFont(font);
                break;
                case "Em falta":
                setBackground(Color.ORANGE);
                setForeground(Color.black);
                break;
                
                default:
                break;
                
                
                        }
        super.getTableCellRendererComponent(table, value, Selected, hasFocus, row, col);
        return this;
    }

}
