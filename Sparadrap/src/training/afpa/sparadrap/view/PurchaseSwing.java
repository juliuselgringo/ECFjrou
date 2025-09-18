package training.afpa.sparadrap.view;

import training.afpa.sparadrap.ExceptionTracking.InputException;
import training.afpa.sparadrap.model.*;
import training.afpa.sparadrap.utility.Gui;

import javax.swing.*;
import java.io.IOException;
import java.text.DecimalFormat;
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

        Gui.labelMaker(panel,"Saisissez la date de la prescription sous le format JJ-MM-AAAA:",
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
                    newPrescription.purchaseNumber = newPrescriptionPurchase.getPurchaseNumber();
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
                if (!drugToAdd.isUnderPrescription() || ((drugToAdd.isUnderPrescription() && newPurchase.getWithPrescription()))) {
                    quantity = Integer.parseInt(quantityField.getText());
                    if (quantity <= 0) {
                        throw new InputException("La quantité est invalide.");
                    }
                    newPurchase.setPurchaseDrugsQuantity(drugToAdd, quantity);

                    try {
                        Drug.stockUpdate(drugToAdd, -quantity);
                        createDisplayPurchaseDrugs(panel, newPurchase);
                    } catch (InputException ie) {
                        JOptionPane.showMessageDialog(null, "Erreur de saisie ou quantité indisponible: " + ie.getMessage(),
                                "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                }else{
                    JOptionPane.showMessageDialog(null,"Ce médicament est sous ordonnance!",
                            "Erreur",JOptionPane.ERROR_MESSAGE);
                }
            }catch (InputException ie){
                JOptionPane.showMessageDialog(null,ie.getMessage(),"Erreur",JOptionPane.ERROR_MESSAGE);
            }

        });

        saveButton.addActionListener(e -> {
            newPurchase.setPurchaseDetails();
            if(newPurchase.getWithPrescription()){
                Customer customer = null;
                try {
                    customer = Customer.getCustomerByLastName(newPurchase.getPrescription().getCustomerLastName());
                } catch (InputException ex) {
                    throw new RuntimeException(ex);
                }
                customer.setCustomerPrescriptionsList(newPurchase.getPrescription());
                try {
                    newPurchase.getPrescription().savePrescriptionAsPdf();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
            JOptionPane.showMessageDialog(null, "La commande a été enregistré avec succès");
            try {
                DataSave.serialization();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            frame.dispose();
        });

        JButton cancelButton = Gui.buttonMaker(panel,"Annuler",220);
        cancelButton.addActionListener(e ->  {
            cancelPurchase(newPurchase);
            //suppression de la prescription de la liste du médecin
            if(newPurchase.getWithPrescription()){
                Doctor doctor = Doctor.searchDoctorByName(newPurchase.getPrescription().getDoctorLastName());
                doctor.getDoctorPrescriptionsList().remove(newPurchase.getPrescription());
            }
            frame.dispose();
        });

        JButton exitButton = Gui.buttonMaker(panel, "Quitter", 250);
        exitButton.addActionListener(e -> {
            cancelPurchase(newPurchase);
            //suppression de la prescription de la liste du médecin
            if(newPurchase.getWithPrescription()){
                Doctor doctor = Doctor.searchDoctorByName(newPurchase.getPrescription().getDoctorLastName());
                doctor.getDoctorPrescriptionsList().remove(newPurchase.getPrescription());
            }
            System.exit(0);
        });
    }

    /**
     * MET A JOUR LE STOCK ET L HISTORIQUE APRES ANNULATION
     * @param newPurchase Purchase
     */
    public static void cancelPurchase(Purchase newPurchase){
        Map<Drug, Integer> map = newPurchase.getPurchaseDrugsQuantity();
        for(Map.Entry<Drug, Integer> entry : map.entrySet()){
            Drug drug = entry.getKey();
            int value = entry.getValue();
            try {
                Drug.stockUpdate(drug, value);
            } catch (InputException ex) {
                throw new RuntimeException(ex);
            }
        }
        newPurchase.deletePurchaseFromHistory();
    }

    /**
     * CREER UNE FENETRE QUI AFFICHE LE CONTENU D UN ACHAT
     * @param newPurchase
     */
    public static void createDisplayPurchaseDrugs(JPanel panel, Purchase newPurchase) {
        newPurchase.setPurchaseDetails();
        String[][] display = newPurchase.getPurchaseDetails();
        Gui.tableMaker(panel,display,purchaseHistoryTableHeaders,500,100,800,300);

        newPurchase.setTotalPrice();
        JTextField priceField = Gui.textFieldMaker(panel,500,450);
        priceField.setText("Prix Total : " +newPurchase.getTotalPrice() + " €");
        priceField.setEditable(false);

        if(newPurchase.getWithPrescription()) {
            Double pricePostMutualRate = newPurchase.getTotalPrice() * (1 - getMutualRateByPrescription(newPurchase.getPrescription()));
            DecimalFormat decimalFormat = new DecimalFormat("#.##");

            JTextField priceFieldPostMutualRate = Gui.textFieldMaker(panel, 500, 480);
            priceFieldPostMutualRate.setText("Prix Total après déduction mutuelle : " +
                    decimalFormat.format(pricePostMutualRate) + " €");
            priceFieldPostMutualRate.setEditable(false);
        }
    }

    /**
     * CONSULTER UNE COMMANDE
     * @param purchase Purchase
     */
    public static void displayAPurchase(Purchase purchase) {
        JFrame frame2 = Gui.setPopUpFrame(1400,800);
        JPanel panel2 = Gui.setPanel(frame2);

        String[][] purchaseHistoryMatrice = purchase.getPurchaseDetails();

        Gui.tableMaker(panel2, purchaseHistoryMatrice,
                purchaseHistoryTableHeaders,10,40,1200,200);

        JButton backButton2 = Gui.buttonMaker(panel2,"Retour",300);
        backButton2.addActionListener(ev -> frame2.dispose());

        JButton exitButton = Gui.buttonMaker(panel2, "Quitter", 330);
        exitButton.addActionListener(e -> {
            System.exit(0);
        });
    }

    /**
     * RECUPERER TAUX MUTUELLE A PARTIR DE PRESCRIPTION
     * @param prescription Prescription
     * @return Double
     */
    public static Double getMutualRateByPrescription(Prescription prescription) {
        String customerLastName = prescription.getCustomerLastName();
        Mutual mutual = null;
        for(Customer customer : Customer.customersList){
            if(customer.getLastName().equals(customerLastName)){
                mutual = customer.getMutual();
            }
        }
        return mutual.getRate();
    }

}
