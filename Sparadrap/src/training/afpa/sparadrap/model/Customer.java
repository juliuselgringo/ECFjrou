package training.afpa.sparadrap.model;

import training.afpa.sparadrap.ExceptionTracking.InputException;
import training.afpa.sparadrap.utility.Display;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class Customer extends Person {

    private String socialSecurityId;
    private LocalDate dateOfBirth;
    private Mutual mutual;
    private Doctor doctor;

    private static final String regexSocialSecurityId = "\\d{15}";
    public static ArrayList<Customer> customersList = new ArrayList<>();

    /**
     * CONSTRUCTOR
     * @param socialSecurityId String
     * @param dateOfBirth String
     * @param mutual Mutual
     * @param doctor Doctor
     * @throws InputException
     */
    public Customer(String firstName, String lastName, Contact contact,String socialSecurityId,
                    String dateOfBirth, Mutual mutual, Doctor doctor) throws InputException {
        super(firstName, lastName, contact);
        setSocialSecurityId(socialSecurityId);
        setDateOfBirth(dateOfBirth);
        this.mutual =  mutual;
        this.doctor = doctor;
        customersList.add(this);
        this.doctor.setDoctorCustomersList(this);
    }

    /**
     * CONSTRUCTOR
     * @param firstName String
     * @param lastName String
     * @param contact Contact
     * @throws InputException
     */
    public Customer(String firstName, String lastName, Contact contact) throws InputException {
        super(firstName, lastName, contact);
        customersList.add(this);
    }

    /**
     * CONSTRUCTOR
     */
    public Customer(){
        super();
        customersList.add(this);
    }

    /**
     * GETTER socialSecurityId
     * @return String
     */
    public String getSocialSecurityId() {
        return socialSecurityId;
    }

    /**
     * SETTER socialSecurityId
     * @param socialSecurityId String
     * @throws InputException
     */
    public void setSocialSecurityId(String socialSecurityId) throws InputException {
        socialSecurityId = socialSecurityId.trim();
        if (socialSecurityId.isEmpty() || socialSecurityId == null) {
            throw new InputException("Le n° de sécurité sociale ne peut être vide ou nul");
        } else if (!socialSecurityId.matches(regexSocialSecurityId)) {
            throw new InputException("Le n° de sécurité sociale est invalide");
        } else {
            this.socialSecurityId = socialSecurityId;
        }
    }

    /**
     * GETTER dateOfBirth
     * @return LocalDate
     */
    public LocalDate getDateOfBirth() {
        return this.dateOfBirth;
    }

    /**
     * SETTER dateOfBirth
     * @param dateOfBirth LocalDate
     * @throws InputException
     */
    public void setDateOfBirth(String dateOfBirth) throws InputException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDateOfBirth = null;
        try {
            localDateOfBirth = LocalDate.parse(dateOfBirth.trim(), formatter);
        }catch(DateTimeParseException dtpe){
            Display.error(dtpe.getMessage());
            throw new InputException("Saisie date de naissance invalide");
        }
        if(localDateOfBirth == null) {
            throw new InputException("La date de naissance ne peut être nulle");
        } else if (localDateOfBirth.isAfter(LocalDate.now())) {
            throw new InputException("La date de naissance ne peut être postérieure à la date d'aujourd'hui");
        }else{
            this.dateOfBirth = localDateOfBirth;
        }
    }

    /**
     * GETTER Mutual
     * @return Mutual
     */
    public Mutual getMutual() {
        return this.mutual;
    }

    /**
     * SETTER mutual
     * @param mutual Mutual
     * @throws InputException
     */
    public void setMutual(Mutual mutual) throws InputException {
        this.mutual = mutual;
    }

    /**
     * GETTER Doctor
     * @return Doctor
     */
    public Doctor getDoctor() {
        return this.doctor;
    }

    /**
     * SETTER doctor
     * @param doctor Doctor
     * @throws InputException
     */
    public void setDoctor(Doctor doctor) throws InputException {
        this.doctor = doctor;
    }

    /**
     * TO STRING
     * @return String
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return "Client \nPrénom: " + this.getFirstName() + ", Nom: " + this.getLastName() +
                ", Date de naissance: " + this.getDateOfBirth().format(formatter) + ", tel:" + this.getContact().getPhone() +
                "\nMutuelle: " + this.getMutual().getname() + " " + this.getMutual().getContact().getPostalCode() +
                ", \nDocteur: " + this.getDoctor().getLastName() + " " + this.getDoctor().getContact().getPostalCode() + "\n";
    }

    /**
     * SUPPRIMER UN CLIENT
     */
    public void deleteCustomer(){
        customersList.remove(this);
    }

    public static String[][] createCustomersMatrice(){
        String[][] matrices = new String[customersList.size()][6];
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        int i = 0;
        for (Customer customer : customersList) {
            matrices[i][0] = customer.getFirstName();
            matrices[i][1] = customer.getLastName();
            matrices[i][2] = customer.getDateOfBirth().format(formatter);
            matrices[i][3] = customer.getContact().getPhone();
            matrices[i][4] = customer.getMutual().getname() + " " + customer.getMutual().getContact().getPostalCode();
            matrices[i][5] = customer.getDoctor().getLastName() + " " + customer.getDoctor().getContact().getPostalCode();
            i++;
        }
        return matrices;
    }

}
