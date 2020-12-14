package data.gui.manager;

import data.dao.ServiceDAO;
import data.models.Service;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import static data.gui.Gui.*;

public class ManagerServiceGui extends JFrame {
    private JTextField serviceName;
    private JTextArea serviceDescription;
    private JButton clearButton;
    private JButton addButton;
    private JButton deleteButton;
    private JButton editButton;
    private JTable serviceTable;
    private JPanel serviceMainPanel;

    private final ServiceDAO serviceDAO = new ServiceDAO();

    ManagerServiceGui() {

        addButton.addActionListener(e -> {
            boolean exist = existsInTable(serviceTable, serviceName.getText());
            if (exist) {
                JOptionPane.showMessageDialog(null, "Name exist, write new name");
                serviceName.setText("");
                return;
            }
            addToTable(serviceTable, serviceName.getText(), serviceDescription.getText());
            serviceDAO.add(new Service(serviceName.getText(), serviceDescription.getText()));
            serviceName.setText("");
            serviceDescription.setText("");
            JOptionPane.showMessageDialog(null, "Added to table");
        });
        clearButton.addActionListener(e -> {
            serviceName.setText("");
            serviceDescription.setText("");
        });
        deleteButton.addActionListener(e -> {
            int id = serviceTable.getSelectedRow();
            removeFromTable(serviceTable, id);
            serviceDAO.delete(serviceTable.getValueAt(id, 0).toString());
            JOptionPane.showMessageDialog(null, "Removed from table");

        });
        editButton.addActionListener(e -> {
            boolean exist = true;
            int id = serviceTable.getSelectedRow();
            String oldName = serviceTable.getValueAt(id, 0).toString();
            var newName =
                    JOptionPane.showInputDialog(null,
                            "Write new name",
                            "New name",
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            null,
                            serviceTable.getValueAt(id, 0).toString()).toString();
            while (exist) {
                exist = existsInTable(serviceTable, newName);
                if (exist) {
                    newName =
                            JOptionPane.showInputDialog(null,
                                    "Name exist, write new name",
                                    "New name",
                                    JOptionPane.PLAIN_MESSAGE,
                                    null,
                                    null,
                                    serviceTable.getValueAt(id, 0).toString()).toString();
                }
            }
            var newDescription = JOptionPane.showInputDialog(null,
                    "Write new description",
                    "New description",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    serviceTable.getValueAt(id, 1).toString()).toString();

            editTable(serviceTable, newName, newDescription, id);
            String[] newData = {newName, newDescription};
            serviceDAO.update(oldName, newData);
            JOptionPane.showMessageDialog(null, "Data edited.");
        });
    }

    public JPanel getServiceMainPanel() {
        return serviceMainPanel;
    }

    public void createTable() {
        serviceTable.setDefaultEditor(Object.class, null);
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new Object[]{"Name", "Description"});
        for (Service service : serviceDAO.getAll()) {
            tableModel.addRow(new Object[]{service.getName(), service.getDescription()});
        }
        serviceTable.setModel(tableModel);
    }
}
