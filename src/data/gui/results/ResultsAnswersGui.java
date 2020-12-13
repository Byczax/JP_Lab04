package data.gui.results;

import data.dao.FieldsDao;
import data.dao.ResultsDao;
import data.models.FieldType;
import data.models.Fields;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.XChartPanel;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ResultsAnswersGui {
    private JPanel ResultAnswerPanel;
    private JTable surveyFieldTable;
    private JButton seeResultsButton;

    private final FieldsDao fieldsDao = new FieldsDao();

    List<List<String>> answersList;

    public void setAnswersList(List<List<String>> answersList) {
        this.answersList = answersList;
    }

//    ResultsGui resultsGui = new ResultsGui();
        ResultsDao resultsDao = new ResultsDao();

    public ResultsAnswersGui() {
        seeResultsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = surveyFieldTable.getSelectedRow();

                var answersForField = resultsDao.getAnswersList().get(id);
                if (surveyFieldTable.getValueAt(id, 1).equals(FieldType.INTEGER)) {
                    histChart(surveyFieldTable.getValueAt(id, 1).toString(), answersForField);

                } else {
                    ResultsAnswersStringGui resultsGui = new ResultsAnswersStringGui();
                    JPanel root = resultsGui.getStringPanel();
                    JFrame frame = new JFrame();
                    resultsGui.createList(answersForField);
                    frame.setTitle("Surveys results");
                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    frame.setContentPane(root);
                    frame.pack();
                    frame.setLocationRelativeTo(null);
                    frame.setMinimumSize(new Dimension(300, 300));
                    frame.setVisible(true);
                }
            }
        });
    }

    public JPanel getResultAnswerPanel() {
        return ResultAnswerPanel;
    }

    public void createTable(UUID whichSurvey) {
        surveyFieldTable.setDefaultEditor(Object.class, null);
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new Object[]{"Service", "Survey"});
        for (Fields fields : fieldsDao.getAll()) {
            var name = fields.getName();
            var type = fields.getType();
            if (fields.getUuid().equals(whichSurvey))
                tableModel.addRow(new Object[]{name, type});
        }
        surveyFieldTable.setModel(tableModel);
    }

    private void histChart(String chartTitle, List<String> myResults) {
        List<Integer> intList = new ArrayList<>();
        for (String s : myResults) intList.add(Integer.valueOf(s));
        CategoryChart myChart = new CategoryChartBuilder().title(chartTitle).xAxisTitle("Value").yAxisTitle("Number of answers").build();
        myChart.getStyler().setPlotGridLinesVisible(true);
//        myChart.getStyler().setDatePattern("yyyy");
        List<Integer> categories = IntStream.range(0, 11).boxed().collect(Collectors.toList());
        List<Long> heights = categories.stream()
                .mapToLong(category -> intList.stream()
                        .filter(answer -> answer.equals(category))
                        .count())
                .boxed()
                .collect(Collectors.toList());
        myChart.addSeries("ResultSValues", categories, heights);


    }

}
