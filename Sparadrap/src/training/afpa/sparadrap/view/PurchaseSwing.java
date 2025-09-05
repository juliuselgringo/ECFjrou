package training.afpa.sparadrap.view;

import training.afpa.sparadrap.ExceptionTracking.InputException;
import training.afpa.sparadrap.model.*;
import training.afpa.sparadrap.utility.Gui;

import javax.swing.*;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

public class PurchaseSwing {

    public static final String[] purchaseHistoryTableHeaders = new String []{"Date","Numéro de commande", "Nom du clients", "Nom du médicament", "Quantite"};

    /**
     * FENETRE DE CREATION D ACHAT AVEC OU SANS PRESCRIPTION
     */
    public static void purchase() {
        JFrame frame = Gui.setFrame();
        Gui.setPanel(frame);

        int response = JOptionPane.showConfirmDialog(null, "Achat avec prescription?",
                "Confirmation",JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {

            createPrescriptionPurchase();
            frame.dispose();
        }else {
            Purchase newPurchase = new Purchase(false);
            createPurchase(newPurchase);
            frame.dispose();
        }

    }

    /**
     * CREER UN FORMULAIRE POUR UNE PRESCRIPTION
     */
    public static void createPrescriptionPurchase() {
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

        JButton exitButton = Gui.buttonMaker(panel, "Quitter", 220);
        exitButton.addActionListener(e -> System.exit(0));

        customerBox.addActionListener(e -> {
            Customer customer = (Customer) customerBox.getSelectedItem();

            doctorBox.addActionListener(ev ->{
                Purchase newPrescriptionPurchase = new Purchase(true);
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

    /**
     * CREER UNE FENETRE D ACHAT
     * @param newPurchase
     */
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
            try {
                quantity = Integer.parseInt(quantityField.getText());
                if (quantity <= 0) {
                    throw new InputException("La quantité est invalide.");
                }
                newPurchase.setPurchaseDrugsQuantity(drugToAdd, quantity);

                try {
                    Drug.stockUpdate(drugToAdd, quantity);
                    createDisplayPurchaseDrugs(newPurchase);
                }catch (InputException ie){
                    JOptionPane.showMessageDialog(null, "Erreur de saisie ou quantité indisponible: " + ie.getMessage(),
                            "Erreur",JOptionPane.ERROR_MESSAGE);
                }
            }catch (InputException ie){
                JOptionPane.showMessageDialog(null,ie.getMessage(),"Erreur",JOptionPane.ERROR_MESSAGE);
            }

        });

        saveButton.addActionListener(e -> {
            newPurchase.setPurchaseDetails();
            JOptionPane.showMessageDialog(null, "La commande a été enregistré avec succès");
            frame.dispose();
        });

        JButton cancelButton = Gui.buttonMaker(panel,"Annuler",220);
        cancelButton.addActionListener(e ->  {
            newPurchase.deletePurchaseFromHistory();
            frame.dispose();
        });

        JButton exitButton = Gui.buttonMaker(panel, "Quitter", 250);
        exitButton.addActionListener(e -> {
            newPurchase.deletePurchaseFromHistory();
            System.exit(0);
        });
    }

    /**
     * CREER UNE FENETRE QUI AFFICHE LE CONTENU D UN ACHAT
     * @param newPurchase
     */
    public static void createDisplayPurchaseDrugs(Purchase newPurchase) {
        JFrame frame = Gui.setPopUpFrame(1000,800);
        frame.setTitle("Commande en cours");
        JPanel panel = Gui.setPanel(frame);
        newPurchase.setPurchaseDetails();
        String[][] display = newPurchase.getPurchaseDetails();
        Gui.tableMaker(panel,display,purchaseHistoryTableHeaders,10,10,800,100);

        Double totalPrice = Purchase.totalPricePurchase(newPurchase);
        JTextField priceField = Gui.textFieldMaker(panel,10,250);
        priceField.setText("Prix Total" + totalPrice.toString());


        JButton backButton = Gui.buttonMaker(panel,"Retour",300);
        backButton.addActionListener(e -> {
                frame.remove(frame.getContentPane());
                frame.dispose();
        });
    }


    /**
     * PAGE HISTORIQUE DES ACHATS
     */
    public static void history() {
        JFrame frame = Gui.setFrame();
        JPanel panel = Gui.setPanel(frame);

        Gui.labelMaker(panel, "Sélectionner une commande à afficher:",10,10);
        JComboBox historyBox = Gui.comboBoxMaker(panel,10,40,1500);
        for (Purchase purchase : Purchase.purchasesHistory){
            historyBox.addItem(purchase);
        }

        historyBox.addActionListener(e -> {
            Purchase purchase = (Purchase)historyBox.getSelectedItem();
            consultAPurchase(purchase);
        });

        Gui.labelMaker(panel, "Consulter les commandes d'une période:):",10,70);
        Gui.labelMaker(panel, "Saisissez une date de début (AAAA-MM-JJ):",10,100);
        JTextField startDateField = Gui.textFieldMaker(panel,10,130);
        Gui.labelMaker(panel, "Saisissez une date de fin (AAAA-MM-JJ):",10,160);
        JTextField endDateField = Gui.textFieldMaker(panel,10,190);
        JButton searchButton = Gui.buttonMaker(panel,"Rechercher par période",220);

        searchButton.addActionListener(e -> {
            try{
                LocalDate startDate =  LocalDate.parse(startDateField.getText());
                LocalDate endDate =  LocalDate.parse(endDateField.getText());
                if(endDate.isBefore(startDate)){
                    throw new InputException("La période n'est pas valide");

                }
                consultPurchasesByPeriod(startDate, endDate);
            }catch(InputException ie){
                JOptionPane.showMessageDialog(null, ie.getMessage(),"Erreur",JOptionPane.ERROR_MESSAGE);
            }


        });

        JButton backButton = Gui.buttonMaker(panel,"Retour",280);
        backButton.addActionListener(ev -> frame.dispose());

        JButton exitButton = Gui.buttonMaker(panel, "Quitter", 310);
        exitButton.addActionListener(e -> {
            System.exit(0);
        });

    }

    public static void consultAPurchase(Purchase purchase) {
        JFrame frame2 = Gui.setPopUpFrame(1400,800);
        JPanel panel2 = Gui.setPanel(frame2);

        String[][] purchaseHistoryMatrice = purchase.getPurchaseDetails();

        Gui.tableMaker(panel2, purchaseHistoryMatrice,
                purchaseHistoryTableHeaders,10,40,1200,200);

        JButton backButton2 = Gui.buttonMaker(panel2,"Retour",240);
        backButton2.addActionListener(ev -> frame2.dispose());
    }

    public static void consultPurchasesByPeriod(LocalDate startDate, LocalDate endDate) {
        JFrame frame2 = Gui.setPopUpFrame(1600,800);
        JPanel panel2 = Gui.setPanel(frame2);

        ArrayList<Purchase> purchaseListToDisplay = Purchase.searchPurchaseByPeriod(startDate, endDate);
        Gui.labelMaker(panel2,"Sélectionner une commande:",10,10);
        JComboBox purchaseBox = Gui.comboBoxMaker(panel2,10,40,1500);
        for(Purchase purchase : purchaseListToDisplay){
            purchaseBox.addItem(purchase);
        }

        purchaseBox.addActionListener(e -> {
            Purchase purchase = (Purchase)purchaseBox.getSelectedItem();
            String[][] purchaseHistoryMatrice = purchase.getPurchaseDetails();

            Gui.tableMaker(panel2, purchaseHistoryMatrice,
                    purchaseHistoryTableHeaders,10,40,1200,200);
        });

        JButton backButton2 = Gui.buttonMaker(panel2,"Retour",240);
        backButton2.addActionListener(ev -> frame2.dispose());
    }



}
