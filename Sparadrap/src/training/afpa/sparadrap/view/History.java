package training.afpa.sparadrap.view;

import training.afpa.sparadrap.ExceptionTracking.InputException;
import training.afpa.sparadrap.model.Purchase;
import training.afpa.sparadrap.utility.Gui;

import javax.swing.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class History {

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

        Gui.labelMaker(panel, "Consulter les commandes d'une période:):",10,70);
        Gui.labelMaker(panel, "Saisissez une date de début (AAAA-MM-JJ):",10,100);
        JTextField startDateField = Gui.textFieldMaker(panel,10,130);
        Gui.labelMaker(panel, "Saisissez une date de fin (AAAA-MM-JJ):",10,160);
        JTextField endDateField = Gui.textFieldMaker(panel,10,190);
        JButton searchButton = Gui.buttonMaker(panel,"Rechercher par période",220);

        String[] headers = new String[] {"Date", "Number", "Nom du client"};
        JTable table = Gui.tableMaker(panel, Purchase.createpurchasesMatrice(),headers,500,100,800,300);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        historyBox.addActionListener(e -> {
            Purchase purchase = (Purchase)historyBox.getSelectedItem();
            PurchaseSwing.displayAPurchase(purchase);
        });

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

        table.getSelectionModel().addListSelectionListener(e -> {
            if(e.getValueIsAdjusting()){
                int selectedRow = table.getSelectedRow();
                if(selectedRow >= 0){
                    Purchase purchase = Purchase.purchasesHistory.get(selectedRow);
                    purchaseDetails(purchase);
                }
            }
        });

        JButton backButton = Gui.buttonMaker(panel,"Retour",280);
        backButton.addActionListener(ev -> frame.dispose());

        JButton exitButton = Gui.buttonMaker(panel, "Quitter", 310);
        exitButton.addActionListener(e -> {
            System.exit(0);
        });

    }

    /**
     * CONSULTER UNE COMMANDE PAR DATES
     * @param startDate LocalDate
     * @param endDate LocalDate
     */
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
                    PurchaseSwing.purchaseHistoryTableHeaders,10,40,1200,200);
        });

        JButton backButton2 = Gui.buttonMaker(panel2,"Retour",240);
        backButton2.addActionListener(ev -> frame2.dispose());
    }

    public static void purchaseDetails(Purchase purchase) {
        JFrame frame2 = Gui.setPopUpFrame(1400,800);
        JPanel panel2 = Gui.setPanel(frame2);
        PurchaseSwing.createDisplayPurchaseDrugs(panel2,purchase);

        JButton backButton = Gui.buttonMaker(panel2,"Retour",280);
        backButton.addActionListener(ev -> frame2.dispose());

        JButton exitButton = Gui.buttonMaker(panel2, "Quitter", 310);
        exitButton.addActionListener(e1 -> {
            System.exit(0);
        });
    }
}
