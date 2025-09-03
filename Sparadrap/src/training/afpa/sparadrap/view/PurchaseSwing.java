package training.afpa.sparadrap.view;

import training.afpa.sparadrap.ExceptionTracking.InputException;
import training.afpa.sparadrap.model.*;
import training.afpa.sparadrap.utility.Display;
import training.afpa.sparadrap.utility.Gui;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class PurchaseSwing {

    public static void purchase() {
        JFrame frame = Gui.setFrame();
        JPanel panel = Gui.setPanel(frame);

        int response = JOptionPane.showConfirmDialog(null, "Achat avec prescription?",
                "Confirmation",JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            Purchase newPrescriptionPurchase = new Purchase(true);
            createPrescriptionPurchase(newPrescriptionPurchase);
            frame.dispose();
        }else {
            Purchase newPurchase = new Purchase(false);
        }

    }

    public static void createPrescriptionPurchase(Purchase newPrescriptionPurchase) {
        JFrame frame = Gui.setFrame();
        JPanel panel = Gui.setPanel(frame);

        JLabel dateLabel = Gui.labelMaker(panel,"Saisissez la date de la prescription sous le format AAAA-MM-JJ:",
                10,10);
        JTextField dateField = Gui.textFieldMaker(panel,10,40);

        JLabel customerLabel = Gui.labelMaker(panel,"Sélectionner le client:",10,70);
        JComboBox customerBox = Gui.comboBoxMaker(panel,10,100);
        for(Customer customer : Customer.customersList){
            customerBox.addItem(customer);
        }

        JLabel doctorLabel = Gui.labelMaker(panel,"Sélectionner le médecin prescripteur:",10,130);
        JComboBox doctorBox = Gui.comboBoxMaker(panel,10,160);
        for(Doctor doctor : Doctor.doctorsList){
            doctorBox.addItem(doctor);
        }

        customerBox.addActionListener(e -> {
            Customer customer = (Customer) customerBox.getSelectedItem();

            doctorBox.addActionListener(ev ->{
                Doctor doctor = (Doctor) doctorBox.getSelectedItem();
                String prescriptionDate = dateField.getText();
                try {
                    Prescription newPrescription = new Prescription(prescriptionDate, doctor.getLastName(), customer.getLastName());
                    newPrescriptionPurchase.setPrescrition(newPrescription);
                    createPurchase(newPrescriptionPurchase);
                    frame.dispose();
                }catch (InputException ie){
                    JOptionPane.showMessageDialog(null, "Erreur de saisie: Impossible d'ajouter la prescription");
                }
            });
        });

    }

    public static void createPurchase(Purchase newPurchase) {
        JFrame frame = Gui.setFrame();
        JPanel panel = Gui.setPanel(frame);

        Gui.labelMaker(panel, "Sélectionner le médicament à ajouter: ",10,10);
        JComboBox drugBox =  Gui.comboBoxMaker(panel,10,40);
        for (Drug drug : Drug.drugsList){
            drugBox.addItem(drug);
        }
        Gui.labelMaker(panel, "Saisissez la quantité à ajouter à l'achat:",10,70);
        JTextField quantityField = Gui.textFieldMaker(panel,10,100);

        JButton addButton = Gui.buttonMaker(panel,"Ajouter",130);

        addButton.addActionListener(e -> {
            Drug drugToAdd = null;
            drugToAdd = (Drug)drugBox.getSelectedItem();
            int quantity = 0;
            quantity = Integer.parseInt(quantityField.getText());
            newPurchase.setPurchaseDrugsQuantity(drugToAdd, quantity);
            createDisplayPurchaseDrugs(newPurchase);
        });

        JButton backButton = Gui.buttonMaker(panel,"Retour",190);
        backButton.addActionListener(e -> frame.dispose());
    }

    public static void createDisplayPurchaseDrugs(Purchase newPurchase) {
        JFrame frame = Gui.setPopUpFrame(1000,800);
        JPanel panel = Gui.setPanel(frame);
        String display = "";
        display = newPurchase.purchaseDrugsQuantityToString();
        Gui.textAreaMaker(panel, display,10,10, 1000,400);
        Display.print(newPurchase.purchaseDrugsQuantityToString());

        JButton backButton = Gui.buttonMaker(panel,"Retour",440);
        backButton.addActionListener(e -> {
                frame.remove(frame.getContentPane());
                frame.dispose();
        });
    }

    public static void history() {
        JFrame frame = Gui.setFrame();
        JPanel panel = Gui.setPanel(frame);

        final String[] purchaseHistoryTableHeaders = new String []{"Date", "Liste des achats"};
        String[][] purchaseHistoryMatrice = Purchase.createPurchaseMatrice();

        JTable purchaseHistoryTable = Gui.tableMaker(panel, purchaseHistoryMatrice,
                purchaseHistoryTableHeaders,500,10,1000,400);

        JButton backButton = Gui.buttonMaker(panel,"Retour",190);
        backButton.addActionListener(e -> frame.dispose());
    }
}
