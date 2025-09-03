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

        JButton backButton = Gui.buttonMaker(panel,"Retour",190);
        backButton.addActionListener(ev -> frame.dispose());

        customerBox.addActionListener(e -> {
            Customer customer = (Customer) customerBox.getSelectedItem();
            JFrame customerFrame = Gui.setPopUpFrame(1200,400);
            JPanel customerPanel = Gui.setPanel(customerFrame);
            Gui.textAreaMaker(customerPanel, customer.toString(),10,10,1200,300 );

            JButton back2Button = Gui.buttonMaker(customerPanel,"Retour",340);
            back2Button.addActionListener(ev -> customerFrame.dispose());
        });

    }

    public static JComboBox getCustomerBox(JPanel panel,int y) {
        JComboBox customerBox = Gui.comboBoxMaker(panel,10,100,400);
        for(Customer customer : Customer.customersList){
            customerBox.addItem(customer);
        }
        return customerBox;
    }

}
