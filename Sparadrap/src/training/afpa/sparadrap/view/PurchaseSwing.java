package training.afpa.sparadrap.view;

import training.afpa.sparadrap.model.Purchase;
import training.afpa.sparadrap.utility.Gui;

import javax.swing.*;
import java.awt.*;

public class PurchaseSwing {

    public static void purchase() {
        JFrame frame = Gui.setFrame();
        JPanel panel = Gui.setPanel(frame);

        Purchase newPurchase;
        int response = JOptionPane.showConfirmDialog(null, "Achat avec prescription?",
                "Confirmation",JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            newPurchase = new Purchase(true);

        }else {
            newPurchase = new Purchase(false);
        }





    }

    public static void history() {
        JFrame frame = Gui.setFrame();
        JPanel panel = Gui.setPanel(frame);

        final String[] purchaseHistoryTableHeaders = new String []{"Date", "Liste des achats",
                "Détails de la prescription"};
        String[][] purchaseHistoryMatrice = Purchase.createPurchaseMatrice();

        JTable purchaseHistoryTable = Gui.tableMaker(panel, purchaseHistoryMatrice,
                purchaseHistoryTableHeaders,500,10,700,400);

    }
}
