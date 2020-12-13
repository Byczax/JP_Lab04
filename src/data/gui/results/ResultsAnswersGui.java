package data.gui.results;

import data.dao.FieldsDao;
import data.dao.ResultsDao;
import data.dao.SurveyDAO;
import data.models.*;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ResultsAnswersGui {
    private JPanel ResultAnswerPanel;
    private JTable surveyFieldTable;
    private JButton seeResultsButton;

    private final FieldsDao fieldsDao = new FieldsDao();
    private ResultsDao resultsDao = new ResultsDao();
    private SurveyDAO surveyDAO = new SurveyDAO();
    private ResultsGui resultsGui = new ResultsGui();

    List<List<String>> answersList;

    public void setAnswersList(List<List<String>> answersList) {
        this.answersList = answersList;
    }

    public ResultsAnswersGui() {
        seeResultsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int id = surveyFieldTable.getSelectedRow();
                String fieldName = surveyFieldTable.getValueAt(id,0).toString();
                FieldType fieldType = FieldType.valueOf( surveyFieldTable.getValueAt(id,1).toString());

                if (fieldType.equals(FieldType.STRING)){
                    List<String> answers = new ArrayList<>();
//                    answers = resultsGui.getAnswersList().stream().map(list -> list.get(id));
                    var tempList = resultsGui.getAnswersList();
                    for (var list: tempList
                         ) {
                        answers.add( list.get(id));
                    }
                    createStringAnswers(answers);
                }

//                var answersForField = answersList.get(id);
//                if (surveyFieldTable.getValueAt(id, 1).equals(FieldType.INTEGER)) {
//                    histChart(surveyFieldTable.getValueAt(id, 1).toString(), answersForField);
//
//                } else {
//                    ResultsAnswersStringGui resultsGui = new ResultsAnswersStringGui();
//                    JPanel root = resultsGui.getStringPanel();
//                    JFrame frame = new JFrame();
//                    resultsGui.createList(answersForField);
//                    frame.setTitle("Surveys results");
//                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//                    frame.setContentPane(root);
//                    frame.pack();
//                    frame.setLocationRelativeTo(null);
//                    frame.setMinimumSize(new Dimension(300, 300));
//                    frame.setVisible(true);
//                }
            }
        });
    }

    public JPanel getResultAnswerPanel() {
        return ResultAnswerPanel;
    }

//    public void createTable(UUID whichSurvey) {
//        surveyFieldTable.setDefaultEditor(Object.class, null);
//        DefaultTableModel tableModel = new DefaultTableModel();
//        tableModel.setColumnIdentifiers(new Object[]{"Service", "Survey"});
//        for (Fields fields : fieldsDao.getAll()) {
//            var name = fields.getName();
//            var type = fields.getType();
//            if (fields.getUuid().equals(whichSurvey))
//                tableModel.addRow(new Object[]{name, type});
//        }
//        surveyFieldTable.setModel(tableModel);
//    }
    public void createTable(List<Fields> answers){
        surveyFieldTable.setDefaultEditor(Object.class, null);
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new Object[]{"Service", "Survey"});
        for (Fields fields: answers
             ) {
            String fieldName = fields.getName();
            var fieldType = fields.getType();
        tableModel.addRow(new Object[]{fieldName,fieldType});
        }
        surveyFieldTable.setModel(tableModel);
    }

    private void histChart(String chartTitle, List<String> myResults) {
        List<Integer> intList = new ArrayList<>();
        for (String s : myResults) intList.add(Integer.valueOf(s));
        CategoryChart myChart = new CategoryChartBuilder().title(chartTitle).xAxisTitle("Value").yAxisTitle("Number of answers").build();
        myChart.getStyler().setPlotGridLinesVisible(true);
        List<Integer> categories = IntStream.range(0, 11).boxed().collect(Collectors.toList());
        List<Long> heights = categories.stream()
                .mapToLong(category -> intList.stream()
                        .filter(answer -> answer.equals(category))
                        .count())
                .boxed()
                .collect(Collectors.toList());
        myChart.addSeries("ResultSValues", categories, heights);
    }

    private void createStringAnswers(List<String> answers){
//        ResultsAnswersGui resultsAnswersGui = new ResultsAnswersGui();
        ResultsAnswersStringGui resultsAnswersStringGui = new ResultsAnswersStringGui();
        JPanel root = resultsAnswersStringGui.getStringPanel();
        JFrame frame = new JFrame();
        resultsAnswersStringGui.createList(answers);
        frame.setTitle("Surveys results");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setContentPane(root);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setMinimumSize(new Dimension(300,300));
        frame.setVisible(true);
    }





}
