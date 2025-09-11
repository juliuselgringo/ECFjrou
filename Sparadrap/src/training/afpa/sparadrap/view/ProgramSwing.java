package training.afpa.sparadrap.view;

import training.afpa.sparadrap.utility.Gui;
import javax.swing.*;

public class ProgramSwing {

    public static void generalMenu(){
        JFrame frame = Gui.setFrame();
        JPanel panel = Gui.setPanel(frame);

        JButton purchaseMenu = Gui.buttonMaker(panel, "Achat", 10);
        purchaseMenu.addActionListener(e -> PurchaseSwing.purchase());

        JButton purchaseHistory = Gui.buttonMaker(panel, "Historique des Achats", 40);
        purchaseHistory.addActionListener(e -> History.history());

        JButton doctor = Gui.buttonMaker(panel, "Médecins", 70);
        doctor.addActionListener(e -> DoctorSwing.doctorMenu());

        JButton customer = Gui.buttonMaker(panel, "Clients",100);
        customer.addActionListener(e -> {CustomerSwing.customerMenu();});

        JButton drug = Gui.buttonMaker(panel, "Médicaments",130);
        drug.addActionListener(e -> DrugSwing.drugMenu());

        JButton prescription = Gui.buttonMaker(panel, "Prescriptions",160);
        prescription.addActionListener(e -> PrescriptionSwing.prescriptionMenu());

        JButton mutual = Gui.buttonMaker(panel, "Mutuelles",190);
        mutual.addActionListener(e -> MutualSwing.mutualMenu());


        JButton exitButton = Gui.buttonMaker(panel, "Quitter", 400);
        exitButton.addActionListener(e -> System.exit(0));
    }
}
