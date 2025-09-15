package training.afpa.sparadrap.view;

import training.afpa.sparadrap.model.Customer;
import training.afpa.sparadrap.model.Doctor;
import training.afpa.sparadrap.model.Prescription;
import training.afpa.sparadrap.utility.Gui;

import javax.swing.*;
import java.util.List;

public class PrescriptionSwing {

    public static void prescriptionMenu(){

        JFrame frame = Gui.setFrame();
        JPanel panel = Gui.setPanel(frame);

        Gui.labelMaker(panel, "Sélectionner un médecin:",10,10);
        JComboBox doctorBox = DoctorSwing.getDoctorBox(panel,40);
        JButton detailButton = Gui.buttonMaker(panel,"Afficher les prescriptions", 130);
        JButton historyButton = Gui.buttonMaker(panel,"Historique des prescriptions/client",160);

        detailButton.addActionListener(e -> {
            Doctor doctor = (Doctor) doctorBox.getSelectedItem();
            DoctorSwing.displayDoctorPrescriptionsList(doctor);
        });

        historyButton.addActionListener(e2 -> {
            displayPrescriptionList();
        });

        JButton backButton = Gui.buttonMaker(panel,"Retour",490);
        backButton.addActionListener(ev -> frame.dispose());

        JButton exitButton = Gui.buttonMaker(panel, "Quitter", 520);
        exitButton.addActionListener(e -> System.exit(0));
    }

    public static void displayPrescription(Prescription prescription) {
        JFrame frame = Gui.setPopUpFrame(800,500);
        frame.setTitle("Détails de la prescription");
        JPanel panel = Gui.setPanel(frame);

        Gui.textAreaMakerScroll(panel, prescription.toString(),10,10,500,300);

        JButton backButton = Gui.buttonMaker(panel,"Retour",350);
        backButton.addActionListener(ev -> frame.dispose());

        JButton exitButton = Gui.buttonMaker(panel, "Quitter", 380);
        exitButton.addActionListener(e -> System.exit(0));
    }

    public static void displayPrescriptionList() {
        JFrame frame = Gui.setPopUpFrame(800,700);
        frame.setTitle("Détails de la prescription");
        JPanel panel = Gui.setPanel(frame);

        Gui.labelMaker(panel, "Sélectionner un client",10,10);
        JComboBox customerBox = Gui.comboBoxMaker(panel, 10,40,300);
        for(Customer customer : Customer.customersList){
            customerBox.addItem(customer);
        }

        JButton backButton = Gui.buttonMaker(panel,"Retour",490);
        backButton.addActionListener(e3 -> frame.dispose());

        JButton exitButton = Gui.buttonMaker(panel, "Quitter", 520);
        exitButton.addActionListener(e4 -> System.exit(0));

        customerBox.addActionListener(e -> {
           Customer customer = (Customer)customerBox.getSelectedItem();
            String[][] customerPrescriptionsMatrice = customer.createCustomerPrescriptionsMatrice();
           String[] header = {"Date", "Nom du client", "Nom du médecin"};

           Gui.labelMaker(panel, "Sélctionner une prescription dans le tableau pour en afficher le détail",10,70);
           JTable table = Gui.tableMaker(panel, customerPrescriptionsMatrice, header,10,100, 700,300);
           table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

           table.getSelectionModel().addListSelectionListener(e2 ->{
               frame.dispose();
               if(e2.getValueIsAdjusting()){
                   int selectedRow = table.getSelectedRow();
                   if(selectedRow >= 0){
                       Prescription prescription = (Prescription) customer.getCustomerPrescriptionsList().get(selectedRow);
                       displayPrescription(prescription);
                   }
               }
           });
        });
    }
}
