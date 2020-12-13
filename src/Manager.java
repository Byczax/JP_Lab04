import data.gui.manager.*;

import javax.swing.*;
import java.awt.*;
public class Manager {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createGUI());
    }

    private static void createGUI(){
        ManagerGui managerGui = new ManagerGui();
        JPanel root = managerGui.getMainPanel();
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Manager panel");
        frame.setContentPane(root);
        frame.pack();
        frame.setMinimumSize(new Dimension(300,200));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
