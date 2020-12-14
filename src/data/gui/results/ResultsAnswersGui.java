package data.gui.results;

import data.dao.FieldsDao;
import data.dao.ResultsDao;
import data.dao.SurveyDAO;
import data.models.*;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XChartPanel;

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

    List<List<String>> answersList = new ArrayList<>();

    public void setAnswersList(List<List<String>> answersList) {
        this.answersList = new ArrayList<>();
        this.answersList = answersList;
    }

    public ResultsAnswersGui() {
        seeResultsButton.addActionListener(e -> {
            int id = surveyFieldTable.getSelectedRow();
            String fieldName = surveyFieldTable.getValueAt(id, 0).toString();
            FieldType fieldType = FieldType.valueOf(surveyFieldTable.getValueAt(id, 1).toString());

            List<String> answers = new ArrayList<>();
            for (var list : answersList
            ) {
                answers.add(list.get(id));
            }
            if (fieldType.equals(FieldType.STRING)) {
                createStringAnswers(answers);
            } else if (fieldType.equals(FieldType.INTEGER)) {

                CategoryChart chart = histChart(fieldName, answers);

//                    new SwingWrapper<CategoryChart>(chart).displayChart();
            }
        });
    }

    public JPanel getResultAnswerPanel() {
        return ResultAnswerPanel;
    }

    public void createTable(List<Fields> answers) {
        surveyFieldTable.setDefaultEditor(Object.class, null);
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new Object[]{"Service", "Survey"});
        for (Fields fields : answers
        ) {
            String fieldName = fields.getName();
            var fieldType = fields.getType();
            tableModel.addRow(new Object[]{fieldName, fieldType});
        }
        surveyFieldTable.setModel(tableModel);
    }

    private CategoryChart histChart(String chartTitle, List<String> myResults) {
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
        new XChartPanel<>(myChart);
        return myChart;
    }

    private void createStringAnswers(List<String> answers) {

        ResultsAnswersStringGui resultsAnswersStringGui = new ResultsAnswersStringGui();
        JPanel root = resultsAnswersStringGui.getStringPanel();
        JFrame frame = new JFrame();
        resultsAnswersStringGui.createList(answers);
        frame.setTitle("Surveys results");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setContentPane(root);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setMinimumSize(new Dimension(300, 300));
        frame.setVisible(true);
    }


}
