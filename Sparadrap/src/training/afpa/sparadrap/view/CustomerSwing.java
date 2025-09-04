package training.afpa.sparadrap.view;

import training.afpa.sparadrap.ExceptionTracking.InputException;
import training.afpa.sparadrap.model.Contact;
import training.afpa.sparadrap.model.Customer;
import training.afpa.sparadrap.model.Doctor;
import training.afpa.sparadrap.model.Mutual;
import training.afpa.sparadrap.utility.Display;
import training.afpa.sparadrap.utility.Gui;

import javax.swing.*;

public class CustomerSwing {

    /**
     * PAGE CLIENT
     */
    public static void customerCheckList() {
        JFrame frame = Gui.setFrame();
        JPanel panel = Gui.setPanel(frame);

        Gui.labelMaker(panel, "Sélectionner un client:",10,10);
        JComboBox customerBox = getCustomerBox(panel,40);

        JButton detailButton = Gui.buttonMaker(panel,"Détails du client", 130);
        detailButton.addActionListener(e -> {
            Customer customer = (Customer) customerBox.getSelectedItem();
            displayCustomer(customer);
        });

        JButton modifyButton = Gui.buttonMaker(panel, "Modifier un client",160);
        modifyButton.addActionListener(ev -> modifyCustomer((Customer) customerBox.getSelectedItem()));

        JButton deleteButton = Gui.buttonMaker(panel, "Supprimer un client",190);
        deleteButton.addActionListener(eve ->{
            Customer customer = (Customer)customerBox.getSelectedItem();
            int resp = JOptionPane.showConfirmDialog(null,
                    "Etes vous sur de vouloir supprimer ce client" + customer.getLastName(),
                    "Confirmation", JOptionPane.YES_NO_OPTION);
            if(resp == JOptionPane.YES_OPTION) {

                customer.deleteCustomer();
                JOptionPane.showMessageDialog(null, "Le client a été supprimé avec succès.",
                        "Succès",JOptionPane.INFORMATION_MESSAGE);
            }
        });

        JButton createButton = Gui.buttonMaker(panel,"Creer un client",220);
        createButton.addActionListener(ev -> {
            try {
                createCustomer();
            } catch (InputException e) {
                throw new RuntimeException(e);
            }
        });

        String[] header = new String[]{"Prénom","Nom","Date Naissance","Téléphone","Mutuelle","Medecin"};
        Gui.tableMaker(panel,Customer.createCustomersMatrice(),header,800, 40,800,800);

        JButton updatePage = Gui.buttonMaker(panel,"Raffraichir la page",830);
        updatePage.addActionListener(ev -> {
           frame.dispose();
           customerCheckList();
        });

        JButton backButton = Gui.buttonMaker(panel,"Retour",490);
        backButton.addActionListener(ev -> frame.dispose());

        JButton exitButton = Gui.buttonMaker(panel, "Quitter", 520);
        exitButton.addActionListener(e -> System.exit(0));



    }

    /**
     * AFFICHER LES DETAILS D UN CLIENT
     * @param customer
     */
    public static void displayCustomer(Customer customer) {
        JFrame customerFrame = Gui.setPopUpFrame(1200,500);
        JPanel customerPanel = Gui.setPanel(customerFrame);
        Gui.textAreaMaker(customerPanel, customer.toString(),10,10,1200,300 );

        JButton back2Button = Gui.buttonMaker(customerPanel,"Retour",340);
        back2Button.addActionListener(ev -> customerFrame.dispose());

        JButton exitButton2 = Gui.buttonMaker(customerPanel, "Quitter", 370);
        exitButton2.addActionListener(eve -> System.exit(0));
    }

    /**
     * CREER UN COMBO BOX CLIENT
     * @param panel JPanel
     * @param y int
     * @return JComboBox
     */
    public static JComboBox getCustomerBox(JPanel panel,int y) {
        JComboBox customerBox = Gui.comboBoxMaker(panel,10, y,300);
        for(Customer customer : Customer.customersList){
            customerBox.addItem(customer);
        }
        return customerBox;
    }

    /**
     * FORMULAIRE DE MODIFICATION DES INFOS CLIENTS
     * @param customer Customer
     */
    public static void modifyCustomer(Customer customer) {
        JFrame frame = Gui.setPopUpFrame(800,1000);
        JPanel panel = Gui.setPanel(frame);
        Contact contact = customer.getContact();
        Gui.labelMaker(panel,"Prénom: ",10,10);
        JTextField firstNameField = Gui.textFieldMaker(panel,10,40);
        firstNameField.setText(customer.getFirstName());

        Gui.labelMaker(panel,"Nom: ",400,10);
        JTextField lastNameField = Gui.textFieldMaker(panel,400,40);
        lastNameField.setText(customer.getLastName());

        Gui.labelMaker(panel,"Adresse: ",10,70);
        JTextField addressField = Gui.textFieldMaker(panel,10,100);
        addressField.setText(contact.getAddress());

        Gui.labelMaker(panel,"code postal: ",10,130);
        JTextField postalField = Gui.textFieldMaker(panel,10,160);
        postalField.setText(contact.getPostalCode());

        Gui.labelMaker(panel,"Ville: ",400,130);
        JTextField townField = Gui.textFieldMaker(panel,400,160);
        townField.setText(contact.getTown());

        Gui.labelMaker(panel,"téléphone: ",10,190);
        JTextField phoneField = Gui.textFieldMaker(panel,10,220);
        phoneField.setText(contact.getPhone());

        Gui.labelMaker(panel,"Email: ",400,190);
        JTextField emailField = Gui.textFieldMaker(panel,400,220);
        emailField.setText(contact.getEmail());

        Gui.labelMaker(panel,"date de naissance: ",10,250);
        JTextField birthField = Gui.textFieldMaker(panel,10,280);
        try {
            birthField.setText(customer.getDateOfBirth().toString());
        }catch(NullPointerException npe) {
            Display.error(npe.getMessage());
        }

        Gui.labelMaker(panel,"N° de sécurité sociale: ",400,250);
        JTextField secuField = Gui.textFieldMaker(panel,400,280);
        secuField.setText(customer.getSocialSecurityId());

        Gui.labelMaker(panel,"Mutuelle: ",10,310);
        JComboBox mutualBox = Gui.comboBoxMaker(panel,10,340,700);
        for(Mutual mutual : Mutual.mutualsList){
            mutualBox.addItem(mutual);
        }

        Gui.labelMaker(panel,"Médecin: ",10,380);
        JComboBox docBox = Gui.comboBoxMaker(panel,10,410,700);
        for(Doctor doc : Doctor.doctorsList){
            docBox.addItem(doc);
        }

        JButton save =  Gui.buttonMaker(panel,"Enregistrer",450);

        JButton back2Button = Gui.buttonMaker(panel,"Retour",480);
        back2Button.addActionListener(ev -> frame.dispose());

        save.addActionListener(ev -> {
            try {
                customer.setFirstName(firstNameField.getText());
            }catch(Exception e) {
                JOptionPane.showMessageDialog(null,"Prénom invalide"+e.getMessage(),"Erreur",JOptionPane.INFORMATION_MESSAGE);
            }
            try {
                customer.setLastName(lastNameField.getText());
            }catch(Exception e) {
                JOptionPane.showMessageDialog(null,"Nom invalide"+e.getMessage(),"Erreur",JOptionPane.INFORMATION_MESSAGE);
            }
            try {
                customer.setDateOfBirth(birthField.getText());
            }catch(Exception e) {
                JOptionPane.showMessageDialog(null,"date de naissance invalide"+e.getMessage(),"Erreur",JOptionPane.INFORMATION_MESSAGE);
            }
            try {
                customer.setSocialSecurityId(secuField.getText());
            }catch(Exception e) {
                JOptionPane.showMessageDialog(null,"N° secu invalide"+e.getMessage(),"Erreur",JOptionPane.INFORMATION_MESSAGE);
            }
            try {
                customer.setMutual((Mutual) mutualBox.getSelectedItem());
            }catch(Exception e) {
                JOptionPane.showMessageDialog(null,"mutuelle invalide"+e.getMessage(),"Erreur",JOptionPane.INFORMATION_MESSAGE);
            }
            try {
                customer.setDoctor((Doctor) docBox.getSelectedItem());
            }catch(Exception e) {
                JOptionPane.showMessageDialog(null,"medecin invalide"+e.getMessage(),"Erreur",JOptionPane.INFORMATION_MESSAGE);
            }
            try {
                contact.setTown(townField.getText());
            }catch(Exception e) {
                JOptionPane.showMessageDialog(null,"Ville invalide"+e.getMessage(),"Erreur",JOptionPane.INFORMATION_MESSAGE);
            }
            try {
                contact.setPhone(phoneField.getText());
            }catch(Exception e) {
                JOptionPane.showMessageDialog(null,"telephone invalide"+e.getMessage(),"Erreur",JOptionPane.INFORMATION_MESSAGE);
            }
            try {
                contact.setEmail(emailField.getText());
            }catch(Exception e) {
                JOptionPane.showMessageDialog(null,"email invalide"+e.getMessage(),"Erreur",JOptionPane.INFORMATION_MESSAGE);
            }
            try {
                contact.setAddress(addressField.getText());
            }catch(Exception e) {
                JOptionPane.showMessageDialog(null,"adresse invalide"+e.getMessage(),"Erreur",JOptionPane.INFORMATION_MESSAGE);
            }
            try {
                contact.setPostalCode(postalField.getText());
            }catch(Exception e) {
                JOptionPane.showMessageDialog(null,"code postal invalide"+e.getMessage(),"Erreur",JOptionPane.INFORMATION_MESSAGE);
            }
            JOptionPane.showMessageDialog(null,"Vos modification ont bien été enregitré","Success",JOptionPane.INFORMATION_MESSAGE);
            frame.dispose();

        });
    }

    /**
     * CREER UN MEDECIN
     * @throws InputException
     */
    public static void createCustomer() throws InputException {
        Contact contact = new Contact();
        Customer customer = new Customer();
        customer.setContact(contact);
        modifyCustomer(customer);
    }



}
