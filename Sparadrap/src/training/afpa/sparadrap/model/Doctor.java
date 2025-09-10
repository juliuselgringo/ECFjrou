package training.afpa.sparadrap.model;

import training.afpa.sparadrap.ExceptionTracking.InputException;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Doctor extends Person {

    private String agreementId;
    private ArrayList<Customer> doctorCustomersList = new ArrayList<>();
    private ArrayList<Prescription> doctorPrescriptionsList = new ArrayList<>();
    public static ArrayList<Doctor> doctorsList = new ArrayList<Doctor>();

    /**
     * CONSTRUCTOR
     * @param firstName String
     * @param lastName String
     * @param contact Contact
     * @param agreementId String
     * @throws InputException
     */
    public Doctor(String firstName, String lastName, Contact contact, String agreementId) throws InputException {
        super(firstName, lastName, contact);
        setAgreementId(agreementId);
        doctorsList.add(this);
    }

    /**
     * CONSTRUCTOR
     */
    public Doctor() {
        super();
        doctorsList.add(this);
    }

    /**
     * GETTER agreementId
     * @return String
     */
    public String getAgreementId() {
        return agreementId;
    }

    /**
     * SETTER agreementId
     * @param agreementId String
     * @throws InputException
     */
    public void setAgreementId(String agreementId) throws InputException {
        agreementId = agreementId.trim();
        if(agreementId == null || agreementId.isEmpty()) {
            throw new InputException("Agreement Id can't be null or empty");
        } else if (agreementId.length() != 11) {
            throw new InputException("Agreement Id invalide");
        }else {
            this.agreementId = agreementId;
        }
    }

    /**
     * GETTER doctorCustomersList
     * @return ArrayList
     */
    public ArrayList<Customer> getDoctorCustomersList() {
        return this.doctorCustomersList;
    }

    /**
     * SETTER doctorCustomersList
     * @param customer Customer
     * @throws InputException
     */
    public void setDoctorCustomersList(Customer customer) throws InputException {
        for (Customer c : doctorCustomersList) {
            if(customer.equals(c)){
                throw new InputException("Ce patient est déjà dans la liste du docteur");
            }
        }
        this.doctorCustomersList.add(customer);
    }

    /**
     * GETTER doctorPrescriptionsList
     * @return ArrayList
     */
    public ArrayList getDoctorPrescriptionsList(){
        return this.doctorPrescriptionsList;
    }

    public void setDoctorPrescriptionsList(Prescription prescription) throws InputException {
        for(Prescription p : doctorPrescriptionsList){
            if(p.equals(prescription)){
                throw new InputException("Cette prescription est déjà dans la liste du docteur");
            }
        }
        this.doctorPrescriptionsList.add(prescription);
    }

    /**
     * TO STRING
     * @return String
     */
    @Override
    public String toString() {
        return "\nDocteur" +
                "\nPrénom: " + this.getFirstName() +
                "\nNom: " + this.getLastName() +
                "\n" + this.getContact() +
                "\nN° d'agréement: " + this.getAgreementId();
    }

    /**
     * CREER UNE MATRICE DES MEDECINS
     * @return String[][]
     */
    public static String[][] createDoctorsMatrice(){
        String[][] matrices = new String[doctorsList.size()][5];
        int i = 0;
        for (Doctor doctor : doctorsList) {
            matrices[i][0] = doctor.getFirstName();
            matrices[i][1] = doctor.getLastName();
            matrices[i][2] = doctor.getAgreementId();
            matrices[i][3] = doctor.getContact().getPhone();
            matrices[i][4] = doctor.getContact().getEmail();
            i++;
        }
        return matrices;
    }

    /**
     * CREER UNE MATRICE DES PATIENTS
     * @return String[][]
     */
    public String[][] createCustomersMatrice(){
        String[][] matrices = new String[this.doctorCustomersList.size()][5];
        int i = 0;
        for (Customer customer : this.doctorCustomersList) {
            matrices[i][0] = customer.getFirstName();
            matrices[i][1] = customer.getLastName();
            matrices[i][2] = customer.getSocialSecurityId();
            matrices[i][3] = customer.getContact().getPhone();
            matrices[i][4] = customer.getContact().getEmail();
            i++;
        }
        return matrices;
    }

    /**
     * CREER UNE MATRICE DES PRESCRIPTIONS
     * @return String[][]
     */
    public String[][] createPrescriptionsMatrice(){
        String[][] matrices = new String[this.doctorPrescriptionsList.size()][2];
        int i = 0;
        for (Prescription prescription : this.doctorPrescriptionsList) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            matrices[i][0] = prescription.getPrescriptionDate().format(formatter);
            matrices[i][1] = prescription.getCustomerLastName();
            i++;
        }
        return matrices;
    }



}
