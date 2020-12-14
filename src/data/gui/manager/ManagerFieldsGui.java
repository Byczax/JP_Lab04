package data.gui.manager;

import data.dao.FieldsDAO;
import data.dao.SurveyDAO;
import data.models.FieldType;
import data.models.Fields;
import data.models.Survey;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.event.*;
import java.util.Objects;
import java.util.UUID;

import static data.gui.Gui.*;

public class ManagerFieldsGui {
    private JTextField fieldName;
    private JPanel fieldPanel;
    private JComboBox<FieldType> fieldType;
    private JButton confirmButton;
    private JTable fieldsTable;
    private JButton editButton;
    private JButton deleteButton;

    SurveyDAO surveyDAO = new SurveyDAO();
    FieldsDAO fieldsDao = new FieldsDAO();
    UUID activeSurveyUuid;


    ManagerFieldsGui() {
        fieldType.addItem(FieldType.INTEGER);
        fieldType.addItem(FieldType.STRING);
        confirmButton.addActionListener(e -> {
            FieldType type = FieldType.valueOf(Objects.requireNonNull(fieldType.getSelectedItem()).toString());
            String name = fieldName.getText();
            fieldName.setText("");
            fieldsDao.add(new Fields(activeSurveyUuid, name, type));
            addToTable(fieldsTable, name, String.valueOf(type));
            JOptionPane.showMessageDialog(null, "Field Added");


        });
        fieldPanel.addContainerListener(new ContainerAdapter() {
        });
        editButton.addActionListener(e -> {
            boolean exist = true;
            int id = fieldsTable.getSelectedRow();
            String oldName = fieldsTable.getValueAt(id, 0).toString();
            var newName =
                    JOptionPane.showInputDialog(null,
                            "Write new name",
                            "New name",
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            null,
                            fieldsTable.getValueAt(id, 0).toString()).toString();
            while (exist) {
                exist = existsInTable(fieldsTable, newName);
                if (exist) {
                    newName =
                            JOptionPane.showInputDialog(null,
                                    "Name exist, write new name",
                                    "New name",
                                    JOptionPane.PLAIN_MESSAGE,
                                    null,
                                    null,
                                    fieldsTable.getValueAt(id, 0).toString()).toString();
                }
            }
            String newType = fieldsTable.getValueAt(id, 1).toString();
            String[] newData = {newName, newType};

            editFieldTable(fieldsTable, newName, id);
            fieldsDao.update(oldName, newData);
            JOptionPane.showMessageDialog(null, "Data edited.");
        });
        deleteButton.addActionListener(e -> {
            int id = fieldsTable.getSelectedRow();
            removeFromTable(fieldsTable, id);
            surveyDAO.delete(fieldsTable.getValueAt(id, 0).toString());
            JOptionPane.showMessageDialog(null, "Removed from table");
        });
    }

    public JPanel getFieldPanel() {
        return fieldPanel;
    }

    public void createTable(String surveyName) {
        fieldsTable.setDefaultEditor(Object.class, null);
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new Object[]{"Name", "Type"});
        activeSurveyUuid = findUuid(surveyName).getUUID();
        for (Fields fields : fieldsDao.getAll()) {
            if (fields.getUuid().equals(activeSurveyUuid))
                tableModel.addRow(new Object[]{fields.getName(), fields.getType()});
        }
        fieldsTable.setModel(tableModel);
    }

    public Survey findUuid(String name) {
        var survey = surveyDAO.getAll().stream().filter(uuid -> uuid.getName().equals(name)).findFirst();
        return survey.orElse(null);
    }

    public static void editFieldTable(JTable table, String name, int id) {
        var myModel = table.getModel();
        myModel.setValueAt(name, id, 0);
    }
}
