package data.gui.manager;

import data.dao.ConnectDAO;
import data.dao.ServiceDAO;
import data.dao.SurveyDAO;
import data.models.Connection;
import data.models.Service;
import data.models.Survey;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ManagerConnectGui {
    private JButton linkButton;
    private JTable servicesTable;
    private JTable surveysTable;
    private JPanel mainConnectPanel;

    ServiceDAO serviceDAO = new ServiceDAO();
    SurveyDAO surveyDAO = new SurveyDAO();
    ConnectDAO connectDao = new ConnectDAO();

    public ManagerConnectGui() {
        linkButton.addActionListener(e -> {
            int idSurvey = surveysTable.getSelectedRow();
            int idService = servicesTable.getSelectedRow();
            String surveyName = surveysTable.getValueAt(idSurvey, 0).toString();
            String serviceName = servicesTable.getValueAt(idService, 0).toString();
            connectDao.add(new Connection(findNameService(serviceName).getUUID(), findNameSurvey(surveyName).getUUID()));
            JOptionPane.showMessageDialog(null, "Connection established!");
        });
    }

    public JPanel getMainConnectPanel() {
        return mainConnectPanel;
    }

    public void createServiceTable() {
        servicesTable.setDefaultEditor(Object.class, null);
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new Object[]{"Name", "Description"});
        for (Service service : serviceDAO.getAll()) {
            tableModel.addRow(new Object[]{service.getName(), service.getDescription()});
        }
        servicesTable.setModel(tableModel);
    }

    public void createSurveyTable() {
        surveysTable.setDefaultEditor(Object.class, null);
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new Object[]{"Name", "Description"});
        for (Survey survey : surveyDAO.getAll()) {
            tableModel.addRow(new Object[]{survey.getName(), survey.getDescription()});
        }
        surveysTable.setModel(tableModel);
    }

    private Service findNameService(String sName) {
        var service = serviceDAO.getAll().stream().filter(name -> name.getName().equals(sName)).findFirst();
        return service.orElse(null);
    }

    private Survey findNameSurvey(String sName) {
        var survey = surveyDAO.getAll().stream().filter(name -> name.getName().equals(sName)).findFirst();
        return survey.orElse(null);
    }

}
