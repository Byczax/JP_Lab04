package data.gui.client;

import data.gui.results.ResultsGui;

import javax.swing.*;
import java.awt.*;

public class ClientGui {
    private JButton browseTheResultsButton;
    private JButton fillInTheFormButton;
    private JPanel mainPanel;

    String username;

    public ClientGui() {
        fillInTheFormButton.addActionListener(e -> {
            ClientFillGui clientFillGui = new ClientFillGui();
            JPanel root = clientFillGui.getMainFillPanel();
            JFrame frame = new JFrame();
            clientFillGui.createSurveyTable();
            clientFillGui.setUsername(username);
            frame.setTitle("Survey filling center");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setContentPane(root);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
        browseTheResultsButton.addActionListener(e -> {
            ResultsGui resultsGui = new ResultsGui();
            JPanel root = resultsGui.getMainPanel();
            JFrame frame = new JFrame();
            resultsGui.createTable();
            frame.setTitle("Surveys results");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setContentPane(root);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setMinimumSize(new Dimension(300, 300));
            frame.setVisible(true);
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
