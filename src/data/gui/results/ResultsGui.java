package data.gui.results;

import data.dao.*;
import data.models.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ResultsGui {
    private JTable resultsTable;
    private JPanel mainPanel;
    private JButton seeResultsButton;

    private ConnectDAO connectDao = new ConnectDAO();
    private ServiceDAO serviceDAO = new ServiceDAO();
    private SurveyDAO surveyDAO = new SurveyDAO();
    private FieldsDAO fieldsDao = new FieldsDAO();
    private ResultsDAO resultsDao = new ResultsDAO();


    List<List<String>> answersList = new ArrayList<>();

    List<Fields> fieldsList;

    public ResultsGui() {
        seeResultsButton.addActionListener(e -> {
            int id = resultsTable.getSelectedRow();

            String serviceName = resultsTable.getValueAt(id, 0).toString();
            String surveyName = resultsTable.getValueAt(id, 1).toString();

            UUID surveyId = findIdSurvey(surveyName).getUUID();
            UUID serviceId = findIdService(serviceName).getUUID();

            Survey ourSurvey = findNameSurvey(surveyId);
            Service ourService = findNameService(serviceId);
            answersList.clear();
            answersList = surveyAnswers(ourSurvey, ourService);

            openAnswers(fieldsList);

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

    public void openAnswers(List<Fields> answersFields) {
        ResultsAnswersGui resultsAnswersGui = new ResultsAnswersGui();
        JPanel root = resultsAnswersGui.getResultAnswerPanel();
        JFrame frame = new JFrame();
        resultsAnswersGui.createTable(answersFields);
        resultsAnswersGui.setAnswersList(answersList);
        frame.setTitle("Surveys results");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setContentPane(root);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setMinimumSize(new Dimension(300, 300));
        frame.setVisible(true);
    }

    public List<List<String>> surveyAnswers(Survey survey, Service service) {
        fieldsList = new ArrayList<>();
        for (Fields fields : fieldsDao.getAll()
        ) {
            if (fields.getUuid().equals(survey.getUUID()))
                fieldsList.add(fields);
        }

        List<List<String>> answersList = new ArrayList<>();
        var results = resultsDao.getAll();

        for (Results result : results
        ) {
            if (survey.getUUID().equals(result.getSurveyId()) && service.getUUID().equals(result.getServiceId())) {
                List<String> tempList = new ArrayList<>(result.getAnswers());
                answersList.add(tempList);
            }
        }
        return answersList;
    }
}
