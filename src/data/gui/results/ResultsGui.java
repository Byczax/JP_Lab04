package data.gui.results;

import data.dao.*;
import data.models.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ResultsGui {
    private JTable resultsTable;
    private JPanel mainPanel;
    private JButton seeResultsButton;

    //    ConnectDao connectDao = new ConnectDao();
    private ConnectDao connectDao = new ConnectDao();
    private ServiceDAO serviceDAO = new ServiceDAO();
    private SurveyDAO surveyDAO = new SurveyDAO();
    private FieldsDao fieldsDao = new FieldsDao();
    private ResultsDao resultsDao = new ResultsDao();
    private ResultsAnswersGui resultsAnswersGui = new ResultsAnswersGui();

    List<List<String>> answersList = new ArrayList<>();

    public List<List<String>> getAnswersList() {
        return answersList;
    }

    public ResultsGui() {
        seeResultsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = resultsTable.getSelectedRow();
//                String serviceName = resultsTable.getValueAt(id, 0).toString();
                String surveyName = resultsTable.getValueAt(id, 1).toString();
                UUID surveyId = findIdSurvey(surveyName).getUUID();
//                UUID serviceId = findIdService(serviceName).getUUID();
                List<Fields> fieldsList = new ArrayList<>();



                var ourSurvey = findNameSurvey(surveyId);
                for (Fields fields : fieldsDao.getAll()) {
                    if (fields.getUuid().equals(ourSurvey.getUUID())) {
                        fieldsList.add(fields);
                    }
                }
                var resultsList = resultsDao.getAll();
                for (int i = 0; i < fieldsList.size(); i++) {
                    ArrayList<String> tempList = new ArrayList<>();
                    for (Results result : resultsList
                    ) {
                        var resultList = result.getAnswers();
                        tempList.add(resultList.get(i));
                    }
                    answersList.add(tempList);
                }
                openAnswers(surveyId,answersList);
                resultsDao.setAnswersList(answersList);
//                resultsAnswersGui.setAnswersList(answersList);
            }
        });
    }

    public void createTable() {
        resultsTable.setDefaultEditor(Object.class, null);
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new Object[]{"Service", "Survey"});
        for (Connection connection : connectDao.getAll()) {
            var serviceID = connection.getServiceUUID();
            var surveyID = connection.getSurveyUUID();

            tableModel.addRow(new Object[]{findNameService(serviceID).getName(), findNameSurvey(surveyID).getName()});
        }
        resultsTable.setModel(tableModel);
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

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void openAnswers(UUID whichSurvey,List<List<String>> answersList) {
        ResultsAnswersGui resultsAnswersGui = new ResultsAnswersGui();
        JPanel root = resultsAnswersGui.getResultAnswerPanel();
        JFrame frame = new JFrame();
        resultsAnswersGui.createTable(whichSurvey);
        frame.setTitle("Surveys results");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setContentPane(root);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setMinimumSize(new Dimension(300, 300));
        frame.setVisible(true);
    }
}
