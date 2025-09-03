package training.afpa.sparadrap.view;

import training.afpa.sparadrap.ExceptionTracking.InputException;
import training.afpa.sparadrap.model.*;
import training.afpa.sparadrap.utility.Gui;

import javax.swing.*;

public class PurchaseSwing {

    public static void purchase() {
        JFrame frame = Gui.setFrame();
        Gui.setPanel(frame);

        int response = JOptionPane.showConfirmDialog(null, "Achat avec prescription?",
                "Confirmation",JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            Purchase newPrescriptionPurchase = new Purchase(true);
            createPrescriptionPurchase(newPrescriptionPurchase);
            frame.dispose();
        }else {
            Purchase newPurchase = new Purchase(false);
            createPurchase(newPurchase);
            frame.dispose();
        }

    }

    public static void createPrescriptionPurchase(Purchase newPrescriptionPurchase) {
        JFrame frame = Gui.setFrame();
        JPanel panel = Gui.setPanel(frame);

        Gui.labelMaker(panel,"Saisissez la date de la prescription sous le format AAAA-MM-JJ:",
                10,10);
        JTextField dateField = Gui.textFieldMaker(panel,10,40);

        Gui.labelMaker(panel,"Sélectionner le client:",10,70);
        JComboBox customerBox = CustomerSwing.getCustomerBox(panel,100);

        Gui.labelMaker(panel,"Sélectionner le médecin prescripteur:",10,130);
        JComboBox doctorBox = DoctorSwing.getDoctorBox(panel,160);

        JButton backButton = Gui.buttonMaker(panel,"Retour",190);
        backButton.addActionListener(e -> frame.dispose());

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
        JComboBox drugBox =  Gui.comboBoxMaker(panel,10,40,1000);
        for (Drug drug : Drug.drugsList){
            drugBox.addItem(drug);
        }
        Gui.labelMaker(panel, "Saisissez la quantité à ajouter à l'achat:",10,70);
        JTextField quantityField = Gui.textFieldMaker(panel,10,100);

        JButton addButton = Gui.buttonMaker(panel,"Ajouter",130);

        JButton saveButton = Gui.buttonMaker(panel,"Valider la commande",190);

        addButton.addActionListener(e -> {
            Drug drugToAdd;
            drugToAdd = (Drug)drugBox.getSelectedItem();
            int quantity = 0;
            quantity = Integer.parseInt(quantityField.getText());
            newPurchase.setPurchaseDrugsQuantity(drugToAdd, quantity);
            createDisplayPurchaseDrugs(newPurchase);
            try {
                Drug.stockUpdate(drugToAdd, quantity);
            }catch (InputException ie){
                JOptionPane.showMessageDialog(null, "Erreur de saisie ou quantité indisponible: " + ie.getMessage(),
                        "Erreur",JOptionPane.ERROR_MESSAGE);
            }
        });

        saveButton.addActionListener(e -> {
            newPurchase.setPurchaseDateDrugsQuantities();
            JOptionPane.showMessageDialog(null, "La commande a été enregistré avec succès");
            frame.dispose();
        });

        JButton backButton = Gui.buttonMaker(panel,"Retour",220);
        backButton.addActionListener(e -> frame.dispose());
    }

    public static void createDisplayPurchaseDrugs(Purchase newPurchase) {
        JFrame frame = Gui.setPopUpFrame(1000,800);
        JPanel panel = Gui.setPanel(frame);
        String display = "";
        display = newPurchase.purchaseDrugsQuantityToString();
        Gui.textAreaMaker(panel, display,10,10, 1000,400);

        JButton backButton = Gui.buttonMaker(panel,"Retour",440);
        backButton.addActionListener(e -> {
                frame.remove(frame.getContentPane());
                frame.dispose();
        });
    }


    public static void history() {
        JFrame frame = Gui.setFrame();
        JPanel panel = Gui.setPanel(frame);

        final String[] purchaseHistoryTableHeaders = new String []{"Date", "Nom du clients", "Nom du médicament", "Quantite"};

        JComboBox historyBox = Gui.comboBoxMaker(panel,10,10,1500);
        for (Purchase purchase : Purchase.purchasesHistory){
            historyBox.addItem(purchase);
        }

        JButton backButton = Gui.buttonMaker(panel,"Retour",190);
        backButton.addActionListener(ev -> frame.dispose());

        historyBox.addActionListener(e -> {
            JFrame frame2 = Gui.setPopUpFrame(1400,800);
            JPanel panel2 = Gui.setPanel(frame2);
            Purchase purchase = (Purchase)historyBox.getSelectedItem();
            String[][] purchaseHistoryMatrice = purchase.getPurchaseDateDrugsQuantities();

            Gui.tableMaker(panel2, purchaseHistoryMatrice,
                    purchaseHistoryTableHeaders,10,40,1200,200);

            JButton backButton2 = Gui.buttonMaker(panel2,"Retour",240);
            backButton2.addActionListener(ev -> frame2.dispose());
        });

    }


}
