package training.afpa.vue.swingGui;

import javax.swing.*;
import java.awt.*;

public class Gui {

    /**
     * CREER UN FULL SCREEN
     * @return JFrame
     */
    public static JFrame setFrame(){
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        Dimension minDim = new Dimension(700,300);
        frame.setMinimumSize(minDim);
        frame.setVisible(true);

        return frame;
    }

    /**
     * CREER UN POP UP
     * @return JFrame
     */
    public static JFrame setPopUpFrame(){
        JFrame frame = new JFrame();
        frame.setSize(800,600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        return frame;
    }

    /**
     * JPANEL
     * @param frame JFrame
     * @return JPanel
     */
    public static JPanel setPanel(JFrame frame){
        JPanel panel = new JPanel();
        panel.setBackground(new Color(119,103,176));
        frame.add(panel);
        panel.setLayout(null);

        return panel;
    }

    /**
     *
     * @param panel JPanel
     * @param sentence String
     * @param positionY int
     * @return JLabel
     */
    public static JLabel labelMaker(JPanel panel, String sentence,int positionX, int positionY){
        JLabel label = new JLabel(sentence);
        label.setBounds(positionX, positionY, 500, 20);
        label.setForeground(Color.WHITE);
        panel.add(label);

        return label;
    }

    /**
     *
     * @param panel JPanel
     * @param sentence String
     * @param positionY int
     * @param height int
     * @return JTextArea
     */
    public static JTextArea textAreaMaker(JPanel panel, String sentence,int positionX, int positionY,int height){
        JTextArea textArea = new JTextArea(sentence);
        textArea.setBounds(positionX, positionY, 400, height);
        textArea.setEditable(false);
        panel.add(textArea);

        return textArea;
    }

    /**
     *
     * @param panel JPanel
     * @param positionY int
     * @return JTextField
     */
    public static JTextField textFieldMaker(JPanel panel,int positionX, int positionY){
        JTextField textField = new JTextField();
        textField.setBounds(positionX, positionY, 300, 20);
        panel.add(textField);

        return textField;
    }

    /**
     *
     * @param panel JPanel
     * @param sentence String
     * @param positionY int
     * @return JButton
     */
    public static JButton buttonMaker(JPanel panel, String sentence, int positionY){
        JButton button = new JButton(sentence);
        button.setBounds(10, positionY, 300, 20);
        panel.add(button);
        return button;
    }

    /**
     *
     * @param panel JPanel
     * @param matrice String[][]
     * @param tableHeaders String[]
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     * @return JTable
     */
    public static JTable tableMaker(JPanel panel, String[][] matrice, String[] tableHeaders,int x,int y,int width, int height){

        JTable table = new JTable(matrice, tableHeaders);

        table.setBackground(new Color(68, 71, 90));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        table.setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(x,y, width,height);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panel.add(scrollPane);

        return table;

    }

    public static JComboBox<String> comboBoxMaker(JPanel panel, String[] list, int positionX, int positionY){
        JComboBox<String> comboBox = new JComboBox<>(list);
        comboBox.setBounds(positionX, positionY, 300, 20);
        panel.add(comboBox);

        return comboBox;
    }

    public static JComboBox comboBoxMaker(JPanel panel, int positionX, int positionY){
        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.setBounds(positionX, positionY, 900, 20);
        panel.add(comboBox);

        return comboBox;
    }

}