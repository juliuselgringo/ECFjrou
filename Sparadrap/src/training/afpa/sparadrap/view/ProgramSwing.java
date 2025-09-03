package training.afpa.sparadrap.view;

import training.afpa.sparadrap.utility.Gui;
import javax.swing.*;

public class ProgramSwing {

    public static void generalMenu(){
        JFrame frame = Gui.setFrame();
        JPanel panel = Gui.setPanel(frame);

        JButton purchaseMenu = Gui.buttonMaker(panel, "Achat", 10);
        purchaseMenu.addActionListener(e -> {PurchaseSwing.purchase();});

        JButton purchaseHistory = Gui.buttonMaker(panel, "Historique des Achats", 40);
        purchaseHistory.addActionListener(e -> {PurchaseSwing.history();});

        JButton doctorCheck = Gui.buttonMaker(panel, "Liste des médecins", 70);
        doctorCheck.addActionListener(e -> {DoctorSwing.doctorCheckList();});

        JButton customerCheck = Gui.buttonMaker(panel, "Liste des clients",100);
        customerCheck.addActionListener(e -> {CustomerSwing.customerCheckList();});

        JButton exitButton = Gui.buttonMaker(panel, "Quitter", 190);
        exitButton.addActionListener(e -> System.exit(0));
    }
}
