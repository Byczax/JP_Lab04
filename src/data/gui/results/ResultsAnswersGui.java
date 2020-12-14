package data.gui.results;

import data.models.FieldType;
import data.models.Fields;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RefineryUtilities;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XChartPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ResultsAnswersGui {
    private JPanel ResultAnswerPanel;
    private JTable surveyFieldTable;
    private JButton seeResultsButton;

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
                histChart(fieldName, answers);
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

    private void histChart(String chartTitle, List<String> myResults) {
        List<Integer> intList = new ArrayList<>();
        for (String s : myResults) intList.add(Integer.valueOf(s));
        DefaultCategoryDataset chartData = new DefaultCategoryDataset();

        List<Integer> categories = IntStream.range(0, 11).boxed().collect(Collectors.toList());
        List<Integer> heights = categories.stream()
                .mapToInt(category -> Math.toIntExact(intList.stream()
                        .filter(answer -> answer.equals(category))
                        .count()))
                .boxed()
                .collect(Collectors.toList());
        int i = 0;
        for (int value : heights
        ) {
            chartData.addValue(value, "value", String.valueOf(i));
            i++;
        }

        JFreeChart barChart = ChartFactory.createBarChart(chartTitle, "Value", "Number of answers",
                chartData);
        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new Dimension(800, 600));
        JFrame frame = new JFrame();
        frame.setTitle("Surveys results");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setContentPane(chartPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setMinimumSize(new Dimension(800, 600));
        frame.setVisible(true);
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
