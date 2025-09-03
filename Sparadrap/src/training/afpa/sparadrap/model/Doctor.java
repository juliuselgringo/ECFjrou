package training.afpa.sparadrap.model;

import training.afpa.sparadrap.ExceptionTracking.InputException;

import java.util.ArrayList;

public class Doctor extends Person {

    private String agreementId;
    private ArrayList<Customer> doctorCustomersList = new ArrayList<>();

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
     * TO STRING
     * @return String
     */
    @Override
    public String toString() {
        return "\nDocteur{ Prénom: " + this.getFirstName() + ", Nom: " + this.getLastName() +
                ", \nCoordonnées: " + this.getContact() + ", N° d'agréement: " + this.getAgreementId() +
                " }";
    }

}
