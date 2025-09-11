package training.afpa.sparadrap.view;

import training.afpa.sparadrap.ExceptionTracking.InputException;
import training.afpa.sparadrap.model.*;
import training.afpa.sparadrap.utility.Display;
import training.afpa.sparadrap.utility.Gui;

import javax.swing.*;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

public class CustomerSwing {

    /**
     * PAGE CLIENT
     */
    public static void customerMenu() {
        JFrame frame = Gui.setFrame();
        JPanel panel = Gui.setPanel(frame);

        Gui.labelMaker(panel, "Sélectionner un client:",10,10);
        JComboBox customerBox = getCustomerBox(panel,40);

        JButton detailButton = Gui.buttonMaker(panel,"Détails du client", 130);
        JButton modifyButton = Gui.buttonMaker(panel, "Modifier un client",160);
        JButton deleteButton = Gui.buttonMaker(panel, "Supprimer un client",190);
        JButton createButton = Gui.buttonMaker(panel,"Creer un client",220);

        String[] header = new String[]{"Prénom","Nom","Date Naissance","Téléphone","Mutuelle","Medecin"};
        JTable table = Gui.tableMaker(panel,Customer.createCustomersMatrice(),header,800, 40,800,800);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        detailButton.addActionListener(e -> {
            Customer customer = (Customer) customerBox.getSelectedItem();
            displayCustomer(customer);
        });

        modifyButton.addActionListener(ev -> modifyCustomer(((Customer) customerBox.getSelectedItem()),frame));

        deleteButton.addActionListener(eve ->{
            Customer customer = (Customer)customerBox.getSelectedItem();
            try {
                deleteCustomer(customer);
            } catch (InputException e) {
                throw new RuntimeException(e);
            }
        });

        createButton.addActionListener(ev -> {
            try {
                createCustomer(frame);
            } catch (InputException e) {
                throw new RuntimeException(e);
            }
        });

        table.getSelectionModel().addListSelectionListener(e -> {
            if(e.getValueIsAdjusting()) {
                int selectedRow = table.getSelectedRow();
                if(selectedRow >= 0){
                    Customer customer = Customer.customersList.get(selectedRow);
                    displayCustomer(customer);
                }
            }
        });

        JButton updatePage = Gui.buttonMaker(panel,"Raffraichir la page",830);
        updatePage.addActionListener(ev -> {
           frame.dispose();
           customerMenu();
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
        JFrame customerFrame = Gui.setPopUpFrame(800,500);
        JPanel customerPanel = Gui.setPanel(customerFrame);
        Gui.textAreaMaker(customerPanel, customer.toStringForDetails(),10,10,700,300 );

        JButton mutualButton = Gui.buttonMaker(customerPanel, "Mutuelles",340);
        mutualButton.addActionListener(e -> {
            customerFrame.dispose();
            JFrame frame = Gui.setPopUpFrame(800,500);
            JPanel panel = Gui.setPanel(frame);

            Gui.textAreaMaker(panel, customer.getMutual().toString(),10,10,700,300 );

            JButton back2Button = Gui.buttonMaker(panel,"Retour",400);
            back2Button.addActionListener(ev -> frame.dispose());

            JButton exitButton2 = Gui.buttonMaker(panel, "Quitter", 430);
            exitButton2.addActionListener(eve -> System.exit(0));
        });

        JButton back2Button = Gui.buttonMaker(customerPanel,"Retour",400);
        back2Button.addActionListener(ev -> customerFrame.dispose());

        JButton exitButton2 = Gui.buttonMaker(customerPanel, "Quitter", 430);
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
    public static void modifyCustomer(Customer customer, JFrame frame1) {
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

        Gui.labelMaker(panel,"téléphone (XX XX XX XX XX): ",10,190);
        JTextField phoneField = Gui.textFieldMaker(panel,10,220);
        phoneField.setText(contact.getPhone());

        Gui.labelMaker(panel,"Email: ",400,190);
        JTextField emailField = Gui.textFieldMaker(panel,400,220);
        emailField.setText(contact.getEmail());

        Gui.labelMaker(panel,"date de naissance(JJ-MM-AAAA): ",10,250);
        JTextField birthField = Gui.textFieldMaker(panel,10,280);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        try {
            birthField.setText(customer.getDateOfBirth().format(formatter));
        }catch(NullPointerException npe) {
            Display.error(npe.getMessage());
        }

        Gui.labelMaker(panel,"N° de sécurité sociale (15 chiffres): ",400,250);
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
        back2Button.addActionListener(ev -> {
            Customer.customersList.remove(customer);
            frame.dispose();
        });

        JButton exitButton2 = Gui.buttonMaker(panel, "Quitter", 510);
        exitButton2.addActionListener(eve -> {
            Customer.customersList.remove(customer);
            System.exit(0);
        });

        save.addActionListener(ev -> {
            try {
                customer.setFirstName(firstNameField.getText());
                customer.setLastName(lastNameField.getText());
                customer.setDateOfBirth(birthField.getText());
                customer.setSocialSecurityId(secuField.getText());
                customer.setMutual((Mutual) mutualBox.getSelectedItem());
                customer.setDoctor((Doctor) docBox.getSelectedItem());
                contact.setTown(townField.getText());
                contact.setPhone(phoneField.getText());
                contact.setEmail(emailField.getText());
                contact.setAddress(addressField.getText());
                contact.setPostalCode(postalField.getText());
                JOptionPane.showMessageDialog(null,"Vos modification ont bien été enregitré",
                        "Success",JOptionPane.INFORMATION_MESSAGE);
                frame.dispose();
                frame1.dispose();
                Customer.customersList.sort(Comparator.comparing(Customer::getLastName));
                customerMenu();
            }catch(InputException ie) {
                JOptionPane.showMessageDialog(null, ie.getMessage(),"Erreur",JOptionPane.INFORMATION_MESSAGE);
            }catch(Exception e) {
                e.getStackTrace();
            }

        });
    }

    /**
     * CREER UN MEDECIN
     * @throws InputException
     */
    public static void createCustomer(JFrame frame) throws InputException {
        Contact contact = new Contact();
        Customer customer = new Customer();
        customer.setContact(contact);
        modifyCustomer(customer,frame);
    }

    /**
     * SUPPRIMER UN CLIENT
     * @param customer Customer
     * @throws InputException
     */
    public static void deleteCustomer(Customer customer) throws InputException {
        int resp = JOptionPane.showConfirmDialog(null,
                "Etes vous sur de vouloir supprimer ce client" + customer.getLastName(),
                "Confirmation", JOptionPane.YES_NO_OPTION);
        if(resp == JOptionPane.YES_OPTION) {

            customer.deleteCustomer();
            JOptionPane.showMessageDialog(null, "Le client a été supprimé avec succès.",
                    "Succès",JOptionPane.INFORMATION_MESSAGE);
        }
    }

}
