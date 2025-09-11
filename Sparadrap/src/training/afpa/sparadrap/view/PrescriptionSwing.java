package training.afpa.sparadrap.view;

import training.afpa.sparadrap.model.Doctor;
import training.afpa.sparadrap.model.Prescription;
import training.afpa.sparadrap.utility.Gui;

import javax.swing.*;

public class PrescriptionSwing {

    public static void prescriptionMenu(){

        JFrame frame = Gui.setFrame();
        JPanel panel = Gui.setPanel(frame);

        Gui.labelMaker(panel, "Sélectionner un médecin:",10,10);
        JComboBox doctorBox = DoctorSwing.getDoctorBox(panel,40);
        JButton detailButton = Gui.buttonMaker(panel,"Afficher les prescriptions", 130);

        detailButton.addActionListener(e -> {
            Doctor doctor = (Doctor) doctorBox.getSelectedItem();
            DoctorSwing.displayDoctorPrescriptionsList(doctor);
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
}
