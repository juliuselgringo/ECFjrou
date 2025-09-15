package training.afpa.sparadrap.view;

import training.afpa.sparadrap.ExceptionTracking.InputException;
import training.afpa.sparadrap.model.Contact;
import training.afpa.sparadrap.model.Doctor;
import training.afpa.sparadrap.model.Prescription;
import training.afpa.sparadrap.utility.Gui;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Comparator;

public class DoctorSwing {

    /**
     * PAGE MEDECIN
     */
    public static void doctorMenu() {
        JFrame frame = Gui.setFrame();
        JPanel panel = Gui.setPanel(frame);

        Gui.labelMaker(panel, "Sélectionner un médecin:",10,10);
        JComboBox doctorBox = getDoctorBox(panel,40);
        JButton detailButton = Gui.buttonMaker(panel,"Détails du médecin", 130);
        JButton modifyButton = Gui.buttonMaker(panel, "Modifier un médecin",160);
        JButton deleteButton = Gui.buttonMaker(panel, "Supprimer un médecin",190);
        JButton createButton = Gui.buttonMaker(panel,"Creer un médecin",220);
        JButton customersListButton = Gui.buttonMaker(panel, "Listes des patients", 250);
        JButton prescriptionsListButton = Gui.buttonMaker(panel, "Liste des prescriptions", 280);

        String[] header = new String[]{"Prénom","Nom","N° d'agréement","Téléphone","Email"};
        JTable table = Gui.tableMaker(panel,Doctor.createDoctorsMatrice(),header,800, 40,800,800);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        detailButton.addActionListener(e1 -> {
            Doctor doctor = (Doctor) doctorBox.getSelectedItem();
            displayDoctor(doctor);
        });

        modifyButton.addActionListener(e2 -> formDoctor((Doctor) doctorBox.getSelectedItem(),
                "modify", frame));

        deleteButton.addActionListener(e3 ->{
            Doctor doctor= (Doctor)doctorBox.getSelectedItem();
            try {
                deleteDoctor(doctor, frame);
            } catch (InputException e) {
                throw new RuntimeException(e);
            }
        });

        createButton.addActionListener(e4 -> {
            try {
                createDoctor(frame);
            } catch (InputException e) {
                throw new RuntimeException(e);
            }
        });

        customersListButton.addActionListener(e5 -> {
            Doctor doctor = (Doctor) doctorBox.getSelectedItem();
            displayDoctorCustomersList(doctor);
        });

        prescriptionsListButton.addActionListener(e6 -> {
            Doctor doctor = (Doctor) doctorBox.getSelectedItem();
            displayDoctorPrescriptionsList(doctor);
        });

        table.getSelectionModel().addListSelectionListener(e -> {
            if(e.getValueIsAdjusting()) {
                int selectedRow = table.getSelectedRow();
                if(selectedRow >= 0){
                    Doctor doctor = Doctor.doctorsList.get(selectedRow);
                    displayDoctor(doctor);
                }
            }
        });

        Gui.tableMaker(panel,Doctor.createDoctorsMatrice(),header,800, 40,800,800);

        JButton backButton = Gui.buttonMaker(panel,"Retour",490);
        backButton.addActionListener(ev -> frame.dispose());

        JButton exitButton = Gui.buttonMaker(panel, "Quitter", 520);
        exitButton.addActionListener(e -> System.exit(0));

    }

    /**
     * CREER UNE COMBOBOX MEDECIN
     * @param panel JPanel
     * @param y int
     * @return JComboBox
     */
    public static JComboBox getDoctorBox(JPanel panel,int y) {
        JComboBox doctorBox = Gui.comboBoxMaker(panel,10,y,400);
        for(Doctor doctor : Doctor.doctorsList){
            doctorBox.addItem(doctor);
        }
        return doctorBox;
    }

    public static void displayDoctor(Doctor doctor) {
        JFrame frame = Gui.setPopUpFrame(1200,500);
        JPanel panel = Gui.setPanel(frame);
        Gui.textAreaMaker(panel, doctor.toStringForDetails(),10,10,1200,300 );

        JButton back2Button = Gui.buttonMaker(panel,"Retour",340);
        back2Button.addActionListener(ev -> frame.dispose());

        JButton exitButton2 = Gui.buttonMaker(panel, "Quitter", 370);
        exitButton2.addActionListener(eve -> System.exit(0));
    }

    /**
     * FORMULAIRE POUR MODIFIER OU CREER UN MEDECIN
     * String type "create" ou "momdify"
     * @param doctor Doctor
     * @param type String
     * @param frame1 JFrame
     */
    public static void formDoctor(Doctor doctor, String type, JFrame frame1) {
        JFrame frame = Gui.setPopUpFrame(800, 1000);
        JPanel panel = Gui.setPanel(frame);
        Contact contact = doctor.getContact();

        Gui.labelMaker(panel, "Prénom: ", 10, 10);
        JTextField firstNameField = Gui.textFieldMaker(panel, 10, 40);
        firstNameField.setText(doctor.getFirstName());

        Gui.labelMaker(panel, "Nom: ", 400, 10);
        JTextField lastNameField = Gui.textFieldMaker(panel, 400, 40);
        lastNameField.setText(doctor.getLastName());

        Gui.labelMaker(panel, "Adresse: ", 10, 70);
        JTextField addressField = Gui.textFieldMaker(panel, 10, 100);
        addressField.setText(contact.getAddress());


        Gui.labelMaker(panel, "code postal: ", 10, 130);
        JTextField postalField = Gui.textFieldMaker(panel, 10, 160);
        postalField.setText(contact.getPostalCode());

        Gui.labelMaker(panel, "Ville: ", 400, 130);
        JTextField townField = Gui.textFieldMaker(panel, 400, 160);
        townField.setText(contact.getTown());

        Gui.labelMaker(panel, "téléphone: ", 10, 190);
        JTextField phoneField = Gui.textFieldMaker(panel, 10, 220);
        phoneField.setText(contact.getPhone());

        Gui.labelMaker(panel, "Email: ", 400, 190);
        JTextField emailField = Gui.textFieldMaker(panel, 400, 220);
        emailField.setText(contact.getEmail());

        Gui.labelMaker(panel, "N° agreement: ", 10, 250);
        JTextField agreementField = Gui.textFieldMaker(panel, 10, 280);
        agreementField.setText(doctor.getAgreementId());

        JButton save = Gui.buttonMaker(panel, "Enregistrer", 450);

        JButton back2Button = Gui.buttonMaker(panel, "Annuler", 480);
        back2Button.addActionListener(ev -> {
            if (type.equals("create")) {
                Doctor.doctorsList.remove(doctor);
            }
            frame1.dispose();
            frame.dispose();
            doctorMenu();
        });

        JButton exitButton2 = Gui.buttonMaker(panel, "Quitter", 510);
        exitButton2.addActionListener(eve -> {
            if (type.equals("create")) {
                Doctor.doctorsList.remove(doctor);
            }
            System.exit(0);
        });

        save.addActionListener(ev -> {
            try {
                doctor.setFirstName(firstNameField.getText());
                doctor.setLastName(lastNameField.getText());
                doctor.setAgreementId(agreementField.getText());
                contact.setTown(townField.getText());
                contact.setPhone(phoneField.getText());
                contact.setEmail(emailField.getText());
                contact.setAddress(addressField.getText());
                contact.setPostalCode(postalField.getText());
                Doctor.doctorsList.sort(Comparator.comparing(Doctor::getLastName));
                JOptionPane.showMessageDialog(null, "Vos modification ont bien été enregitré",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                frame.dispose();
                frame1.dispose();
                doctorMenu();
            } catch (InputException ie) {
                JOptionPane.showMessageDialog(null, ie.getMessage(), "Erreur", JOptionPane.INFORMATION_MESSAGE);
            }

        });
    }

    /**
     * CRER UN MEDECIN
     * @param frame JFrame
     * @throws InputException
     */
    public static void createDoctor(JFrame frame) throws InputException {
        Contact contact = new Contact();
        Doctor doctor= new Doctor();
        doctor.setContact(contact);
        formDoctor(doctor, "create",frame);
    }

    /**
     * SUPPRIMER UN MEDECIN
     * @param doctor Doctor
     * @throws InputException
     */
    public static void deleteDoctor(Doctor doctor, JFrame frame1) throws InputException {
        int resp = JOptionPane.showConfirmDialog(null,
                "Etes vous sur de vouloir supprimer ce médecin" + doctor.getLastName(),
                "Confirmation", JOptionPane.YES_NO_OPTION);
        if(resp == JOptionPane.YES_OPTION) {
            Doctor.doctorsList.remove(doctor);
            JOptionPane.showMessageDialog(null, "Le médecin a été supprimé avec succès.",
                    "Succès",JOptionPane.INFORMATION_MESSAGE);
        }
        frame1.dispose();
        doctorMenu();
    }

    /**
     * AFFICHER LA LISTE DES PATIENTS D UN MEDECIN
     * @param doctor Doctor
     */
    public static void displayDoctorCustomersList(Doctor doctor){
        JFrame frame2 = Gui.setPopUpFrame(800,500);
        frame2.setTitle("Liste des patients");
        JPanel panel2 = Gui.setPanel(frame2);
        String[] header = new String[]{"Prénom", "Nom", "N° Secu", "Téléphone", "Email"};

        Gui.tableMaker(panel2, doctor.createCustomersMatrice(), header,10,10,700,300);
        JButton backButton = Gui.buttonMaker(panel2,"Retour",400);
        backButton.addActionListener(ev -> frame2.dispose());

        JButton exitButton = Gui.buttonMaker(panel2, "Quitter", 430);
        exitButton.addActionListener(e -> System.exit(0));
    }

    /**
     * AFFICHER LE LISTE DES PRESCRIPTION D UN MEDECIN
     * @param doctor Doctor
     */
    public static void displayDoctorPrescriptionsList(Doctor doctor){
        JFrame frame = Gui.setPopUpFrame(800,500);
        JPanel panel = Gui.setPanel(frame);
        frame.setTitle("Liste des prescriptions");
        String[] header = new String[]{"Date","Nom du patient","Numéro de commande"};

        JTable table = Gui.tableMaker(panel, doctor.createPrescriptionsMatrice(), header,10,10,700,300);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(e -> {
            if(e.getValueIsAdjusting()) {
                int selectedRow = table.getSelectedRow();
                ArrayList list = doctor.getDoctorPrescriptionsList();
                if(selectedRow >= 0) {
                    Prescription prescription = (Prescription)list.get(selectedRow);
                    PrescriptionSwing.displayPrescription(prescription);
                    frame.dispose();
                }
            }
        });
        Gui.labelMaker(panel,"Cliquez dans le tableau pour avoir les détails de la prescription",
                10,330);

        JButton backButton = Gui.buttonMaker(panel,"Retour",400);
        backButton.addActionListener(ev -> frame.dispose());

        JButton exitButton = Gui.buttonMaker(panel, "Quitter", 430);
        exitButton.addActionListener(e -> System.exit(0));
    }

}
