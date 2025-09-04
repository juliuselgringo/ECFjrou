package training.afpa.sparadrap.view;

import training.afpa.sparadrap.ExceptionTracking.InputException;
import training.afpa.sparadrap.model.Contact;
import training.afpa.sparadrap.model.Customer;
import training.afpa.sparadrap.model.Doctor;
import training.afpa.sparadrap.utility.Gui;

import javax.swing.*;

public class DoctorSwing {

    /**
     * PAGE MEDECIN
     */
    public static void doctorCheckList() {
        JFrame frame = Gui.setFrame();
        JPanel panel = Gui.setPanel(frame);

        Gui.labelMaker(panel, "Sélectionner un médecin:",10,10);
        JComboBox doctorBox = getDoctorBox(panel,40);

        JButton detailButton = Gui.buttonMaker(panel,"Détails du médecin", 130);
        detailButton.addActionListener(e -> {
            Doctor doctor = (Doctor) doctorBox.getSelectedItem();
            displayDoctor(doctor);
        });

        JButton modifyButton = Gui.buttonMaker(panel, "Modifier un médecin",160);
        modifyButton.addActionListener(ev -> modifyDoctor((Doctor) doctorBox.getSelectedItem()));



        JButton deleteButton = Gui.buttonMaker(panel, "Supprimer un client",190);
        deleteButton.addActionListener(eve ->{
            Doctor doctor= (Doctor)doctorBox.getSelectedItem();
            int resp = JOptionPane.showConfirmDialog(null,
                    "Etes vous sur de vouloir supprimer ce médecin" + doctor.getLastName(),
                    "Confirmation", JOptionPane.YES_NO_OPTION);
            if(resp == JOptionPane.YES_OPTION) {
                Doctor.doctorsList.remove(doctor);
                JOptionPane.showMessageDialog(null, "Le médecin a été supprimé avec succès.",
                        "Succès",JOptionPane.INFORMATION_MESSAGE);
            }
        });

        JButton createButton = Gui.buttonMaker(panel,"Creer un médecin",220);
        createButton.addActionListener(ev -> {
            try {
                createDoctor();
            } catch (InputException e) {
                throw new RuntimeException(e);
            }
        });

        String[] header = new String[]{"Prénom","Nom","Agreement","Téléphone","email"};
        Gui.tableMaker(panel,Doctor.createDoctorsMatrice(),header,800, 40,800,800);

        JButton backButton = Gui.buttonMaker(panel,"Retour",490);
        backButton.addActionListener(ev -> frame.dispose());

        JButton exitButton = Gui.buttonMaker(panel, "Quitter", 520);
        exitButton.addActionListener(e -> System.exit(0));

        JButton updatePage = Gui.buttonMaker(panel,"Raffraichir la page",830);
        updatePage.addActionListener(ev -> {
            frame.dispose();
            doctorCheckList();
        });
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
        Gui.textAreaMaker(panel, doctor.toString(),10,10,1200,300 );

        JButton back2Button = Gui.buttonMaker(panel,"Retour",340);
        back2Button.addActionListener(ev -> frame.dispose());

        JButton exitButton2 = Gui.buttonMaker(panel, "Quitter", 370);
        exitButton2.addActionListener(eve -> System.exit(0));
    }

    public static void modifyDoctor(Doctor doctor) {
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

        JButton back2Button = Gui.buttonMaker(panel, "Retour", 480);
        back2Button.addActionListener(ev -> frame.dispose());

        save.addActionListener(ev -> {
            try {
                doctor.setFirstName(firstNameField.getText());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Prénom invalide" + e.getMessage(), "Erreur", JOptionPane.INFORMATION_MESSAGE);
            }
            try {
                doctor.setLastName(lastNameField.getText());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Nom invalide" + e.getMessage(), "Erreur", JOptionPane.INFORMATION_MESSAGE);
            }
            try {
                doctor.setAgreementId(agreementField.getText());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "N° agreement invalide" + e.getMessage(), "Erreur", JOptionPane.INFORMATION_MESSAGE);
            }
            try {
                contact.setTown(townField.getText());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Ville invalide" + e.getMessage(), "Erreur", JOptionPane.INFORMATION_MESSAGE);
            }
            try {
                contact.setPhone(phoneField.getText());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "telephone invalide" + e.getMessage(), "Erreur", JOptionPane.INFORMATION_MESSAGE);
            }
            try {
                contact.setEmail(emailField.getText());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "email invalide" + e.getMessage(), "Erreur", JOptionPane.INFORMATION_MESSAGE);
            }
            try {
                contact.setAddress(addressField.getText());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "adresse invalide" + e.getMessage(), "Erreur", JOptionPane.INFORMATION_MESSAGE);
            }
            try {
                contact.setPostalCode(postalField.getText());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "code postal invalide" + e.getMessage(), "Erreur", JOptionPane.INFORMATION_MESSAGE);
            }
            JOptionPane.showMessageDialog(null, "Vos modification ont bien été enregitré", "Success", JOptionPane.INFORMATION_MESSAGE);
            frame.dispose();
        });
    }

    public static void createDoctor() throws InputException {
        Contact contact = new Contact();
        Doctor doctor= new Doctor();
        doctor.setContact(contact);
        modifyDoctor(doctor);
    }

}
