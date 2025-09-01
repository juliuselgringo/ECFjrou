package training.afpa.sparadrap.model;

import training.afpa.sparadrap.ExceptionTracking.InputException;

import java.util.ArrayList;

public class Doctor {

    private String agreementId;
    private static ArrayList<Customer> doctorCustomersList;

    public Doctor(String firstName, String lastName, String address, String postalCode,
                  String town, String phone, String email, String agreementId) throws InputException {
        super();
        setAgreementId(agreementId);

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
    public static ArrayList<Customer> getDoctorCustomersList() {
        return doctorCustomersList;
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
}
