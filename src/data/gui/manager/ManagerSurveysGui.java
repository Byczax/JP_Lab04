package data.gui.manager;

import data.dao.SurveyDAO;
import data.models.Survey;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import static data.gui.Gui.*;

public class ManagerSurveysGui {
    private JPanel mainSurveyPanel;
    private JButton editButton;
    private JButton deleteButton;
    private JTable surveyTable;
    private JTextArea surveyDescription;
    private JButton addButton;
    private JButton clearButton;
    private JTextField surveyName;
    private JButton addFieldsButton;
    private SurveyDAO surveyDAO = new SurveyDAO();

    JFrame FieldFrame = new JFrame();

    public ManagerSurveysGui() {
        clearButton.addActionListener(e -> {
            surveyName.setText("");
            surveyDescription.setText("");
        });
        addButton.addActionListener(e -> {
            boolean exist = existsInTable(surveyTable, surveyName.getText());
            if (exist) {
                JOptionPane.showMessageDialog(null, "Name exist, write new name");
                surveyName.setText("");
                return;
            }
            addToTable(surveyTable, surveyName.getText(), surveyDescription.getText());
            surveyDAO.add(new Survey(surveyName.getText(), surveyDescription.getText()));
            surveyName.setText("");
            surveyDescription.setText("");
            JOptionPane.showMessageDialog(null, "Added to table");
        });
        editButton.addActionListener(e -> {
            boolean exist = true;
            int id = surveyTable.getSelectedRow();
            String oldName = surveyTable.getValueAt(id, 0).toString();
            var newName =
                    JOptionPane.showInputDialog(null,
                            "Write new name",
                            "New name",
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            null,
                            surveyTable.getValueAt(id, 0).toString()).toString();
            while (exist) {
                exist = existsInTable(surveyTable, newName);
                if (exist) {
                    newName =
                            JOptionPane.showInputDialog(null,
                                    "Name exist, write new name",
                                    "New name",
                                    JOptionPane.PLAIN_MESSAGE,
                                    null,
                                    null,
                                    surveyTable.getValueAt(id, 0).toString()).toString();
                }
            }
            var newDescription = JOptionPane.showInputDialog(null,
                    "Write new description",
                    "New description",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    surveyTable.getValueAt(id, 1).toString()).toString();

            editTable(surveyTable, newName, newDescription, id);
            String[] newData = {newName, newDescription};
            surveyDAO.update(oldName, newData);
            JOptionPane.showMessageDialog(null, "Data edited.");
        });
        deleteButton.addActionListener(e -> {
            int id = surveyTable.getSelectedRow();
            removeFromTable(surveyTable, id);
            surveyDAO.delete(surveyTable.getValueAt(id, 0).toString());
            JOptionPane.showMessageDialog(null, "Removed from table");
        });

        addFieldsButton.addActionListener(e -> {
            ManagerFieldsGui managerGui = new ManagerFieldsGui();
            int selectedRow = surveyTable.getSelectedRow();
            String surveyName = surveyTable.getValueAt(selectedRow, 0).toString();
            JPanel root = managerGui.getFieldPanel();
            managerGui.createTable(surveyName);
            FieldFrame.setTitle("Manager Field Adding");
            FieldFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            FieldFrame.setContentPane(root);
            FieldFrame.pack();
            FieldFrame.setLocationRelativeTo(null);
            FieldFrame.setVisible(true);
        });
    }

    public JPanel getMainSurveyPanel() {
        return mainSurveyPanel;
    }

    public void createTable() {
        surveyTable.setDefaultEditor(Object.class, null);
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new Object[]{"Name", "Description"});
        for (Survey service : surveyDAO.getAll()) {
            tableModel.addRow(new Object[]{service.getName(), service.getDescription()});
        }
        surveyTable.setModel(tableModel);
    }
}
