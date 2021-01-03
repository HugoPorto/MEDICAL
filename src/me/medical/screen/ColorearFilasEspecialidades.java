
package me.medical.screen;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

public class ColorearFilasEspecialidades extends DefaultTableCellRenderer {

    private final int columna_patron;

    public ColorearFilasEspecialidades(int Colpatron) {
        this.columna_patron = Colpatron;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean Selected, boolean hasFocus, int row, int col) {
        Font font = new Font("Courier", Font.BOLD, 16);
        switch (table.getValueAt(row, columna_patron).toString()) {

             case "Em Atendimento":
                setForeground(Color.GREEN);
                setFont(font);
                break;
            case "Sem Atendimento":
                setForeground(Color.RED);
                setFont(font);
                break;
                default:
                break;
                
                
                        }
        super.getTableCellRendererComponent(table, value, Selected, hasFocus, row, col);
        return this;
    }

}