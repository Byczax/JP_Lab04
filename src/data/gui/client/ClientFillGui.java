package data.gui.client;

import data.dao.*;
import data.models.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ClientFillGui {
    private JPanel mainFillPanel;
    private JTable surveysTable;
    private JButton fillChoosenSurveyButton;

    private ConnectDao connectDao = new ConnectDao();
    private ServiceDAO serviceDAO = new ServiceDAO();
    private SurveyDAO surveyDAO = new SurveyDAO();
    private FieldsDao fieldsDao = new FieldsDao();
    private ResultsDao resultsDao = new ResultsDao();

    public ClientFillGui() {
        fillChoosenSurveyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = surveysTable.getSelectedRow();
                String serviceName = surveysTable.getValueAt(id, 0).toString();
                String surveyName = surveysTable.getValueAt(id, 1).toString();
                UUID surveyId = findIdSurvey(surveyName).getUUID();
                UUID serviceId = findIdService(serviceName).getUUID();
                List<Fields> fieldsList = new ArrayList<>();
                for (Fields field : fieldsDao.getAll()
                ) {
                    if (field.getUuid().equals(surveyId)) {
                        fieldsList.add(field);
                    }
                }
                int i = 0;
                List<String> answers = new ArrayList<>();
                String filledField;
                for (Fields field : fieldsList
                ) {
                    boolean correct = false;
                    if (field.getType().equals(FieldType.INTEGER)) {

                        filledField = JOptionPane.showInputDialog(null,
                                field.getName(),
                                "Question " + i,
                                JOptionPane.PLAIN_MESSAGE
                        );
                        while (!correct) {
                            if (Integer.parseInt(filledField) < 0 || Integer.parseInt(filledField) > 10) {

                                filledField = JOptionPane.showInputDialog(null,
                                        field.getName(),
                                        "Wrong value, enter value again, numer 0-10",
                                        JOptionPane.PLAIN_MESSAGE
                                );
                            } else {
                                correct = true;
                                answers.add(filledField);
                            }
                        }
                    } else {
                        answers.add(JOptionPane.showInputDialog(null,
                                field.getName(),
                                "Wrong value, enter value again, numer 0-10",
                                JOptionPane.PLAIN_MESSAGE));
                    }
                    i++;
                }
                resultsDao.add(new Results(username, serviceId, surveyId, answers));
            }
        });
    }

    public void setUsername(String username) {
        this.username = username;
    }

    String username;

    public JPanel getMainFillPanel() {
        return mainFillPanel;
    }

    public void createSurveyTable() {
        surveysTable.setDefaultEditor(Object.class, null);
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new Object[]{"Service", "Survey"});
        for (Connection connection : connectDao.getAll()) {
            var serviceID = connection.getServiceUUID();
            var surveyID = connection.getSurveyUUID();

            tableModel.addRow(new Object[]{findNameService(serviceID).getName(), findNameSurvey(surveyID).getName()});
        }
        surveysTable.setModel(tableModel);
    }

    private Service findNameService(UUID uuid) {
        var service = serviceDAO.getAll().stream().filter(name -> name.getUUID().equals(uuid)).findFirst();
        return service.orElse(null);
    }

    private Survey findNameSurvey(UUID uuid) {
        var survey = surveyDAO.getAll().stream().filter(name -> name.getUUID().equals(uuid)).findFirst();
        return survey.orElse(null);
    }

    private Survey findIdSurvey(String name) {
        var survey = surveyDAO.getAll().stream().filter(uuid -> uuid.getName().equals(name)).findFirst();
        return survey.orElse(null);
    }

    private Service findIdService(String name) {
        var service = serviceDAO.getAll().stream().filter(uuid -> uuid.getName().equals(name)).findFirst();
        return service.orElse(null);
    }
}
