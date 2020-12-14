package data.gui.results;

import javax.swing.*;
import java.util.List;

public class ResultsAnswersStringGui {
    private JList<String> stringList;
    private JPanel stringPanel;

    public JPanel getStringPanel() {
        return stringPanel;
    }

    void createList(List<String> answersList) {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        listModel.addAll(answersList);
        stringList.setModel(listModel);
    }
}
