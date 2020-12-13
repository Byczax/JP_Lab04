import data.gui.client.ClientGui;

import javax.swing.*;
import java.awt.*;

public class Client {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Client::createGUI);
    }

    private static void createGUI(){
        ClientGui clientGui = new ClientGui();
        JPanel root = clientGui.getMainPanel();
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Manager panel");
        frame.setContentPane(root);
        frame.pack();
        frame.setMinimumSize(new Dimension(300,200));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        clientGui.setUsername(JOptionPane.showInputDialog("Sign in"));
    }

}
