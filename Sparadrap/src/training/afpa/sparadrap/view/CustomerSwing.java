package training.afpa.sparadrap.view;

import training.afpa.sparadrap.model.Customer;
import training.afpa.sparadrap.model.Doctor;
import training.afpa.sparadrap.utility.Gui;

import javax.swing.*;

public class CustomerSwing {

    public static void customerCheckList() {
        JFrame frame = Gui.setFrame();
        JPanel panel = Gui.setPanel(frame);

        Gui.labelMaker(panel, "Sélectionner un client:",10,10);
        JComboBox customerBox = getCustomerBox(panel,40);

        JButton detailButton = Gui.buttonMaker(panel,"Détails du client", 130);

        JButton modifyButton = Gui.buttonMaker(panel, "Modifier un client",160);
        modifyButton.addActionListener(ev -> modifyCustomer((Customer) customerBox.getSelectedItem()));

        JButton backButton = Gui.buttonMaker(panel,"Retour",490);
        backButton.addActionListener(ev -> frame.dispose());

        JButton exitButton = Gui.buttonMaker(panel, "Quitter", 520);
        exitButton.addActionListener(e -> System.exit(0));

        detailButton.addActionListener(e -> {
            Customer customer = (Customer) customerBox.getSelectedItem();
            displayCustomer(customer);
        });

    }

    public static void displayCustomer(Customer customer) {
        JFrame customerFrame = Gui.setPopUpFrame(1200,500);
        JPanel customerPanel = Gui.setPanel(customerFrame);
        Gui.textAreaMaker(customerPanel, customer.toString(),10,10,1200,300 );

        JButton back2Button = Gui.buttonMaker(customerPanel,"Retour",340);
        back2Button.addActionListener(ev -> customerFrame.dispose());

        JButton exitButton2 = Gui.buttonMaker(customerPanel, "Quitter", 370);
        exitButton2.addActionListener(eve -> System.exit(0));
    }

    public static JComboBox getCustomerBox(JPanel panel,int y) {
        JComboBox customerBox = Gui.comboBoxMaker(panel,10, y,400);
        for(Customer customer : Customer.customersList){
            customerBox.addItem(customer);
        }
        return customerBox;
    }

    public static void modifyCustomer(Customer customer) {
        JFrame frame = Gui.setPopUpFrame(1200,500);
        JPanel panel = Gui.setPanel(frame);

        Gui.labelMaker(panel,"Prénom: ",10,10);
        JTextField firstNameField = Gui.textFieldMaker(panel,10,40);
        firstNameField.setText(customer.getFirstName());

        Gui.labelMaker(panel,"Nom: ",10,70);
        JTextField lastNameField = Gui.textFieldMaker(panel,10,100);
        lastNameField.setText(customer.getLastName());

    }





}
