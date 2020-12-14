package data.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Gui {
    public static boolean existsInTable(JTable table, String name) {

        int rowCount = table.getRowCount();

        String curEntry = name;

        for (int i = 0; i < rowCount; i++) {
            String rowEntry = table.getValueAt(i, 0).toString();
            if (rowEntry.equalsIgnoreCase(curEntry)) {
                return true;
            }
        }
        return false;
    }

    public static void addToTable(JTable table, String name, String description) {
        var myModel = (DefaultTableModel) table.getModel();
        myModel.addRow(new Object[]{name, description});
    }

    public static void removeFromTable(JTable table, int id) {
        var myModel = (DefaultTableModel) table.getModel();
        myModel.removeRow(id);

    }

    public static void editTable(JTable table, String name, String description, int id) {
        var myModel = table.getModel();
        myModel.setValueAt(name, id, 0);
        myModel.setValueAt(description, id, 1);
    }
}
