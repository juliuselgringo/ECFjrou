package training.afpa.sparadrap.model;

import training.afpa.sparadrap.ExceptionTracking.InputException;

import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

public class Prescription {

    private LocalDate prescriptionDate;
    private String doctorLastName;
    private String customerLastName;
    private Map<Drug, Integer> drugsQuantityPrescriptionsList = new HashMap<>();
    public Integer purchaseNumber;

    /**
     * CONSTRUCTOR
     * @param prescriptionDateIn String
     * @param doctorLastNameIn String
     * @param customerLastNameIn String
     * @throws InputException
     */
    public Prescription(String prescriptionDateIn, String doctorLastNameIn, String customerLastNameIn) throws InputException {
        this.setPrescriptionDate(prescriptionDateIn);
        this.setDoctorLastName(doctorLastNameIn);
        this.setCustomerLastName(customerLastNameIn);
    }

    /**
     * CONSTRUCTOR
     */
    public Prescription(){};

    /**
     * GETTER prescriptionDate
     * @return LocalDate
     */
    public LocalDate getPrescriptionDate() {
        return this.prescriptionDate;
    }

    /**
     * SETTER prescriptionDate
     * @param prescriptionDateIn String
     * @throws InputException
     */
    public void setPrescriptionDate(String prescriptionDateIn) throws InputException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate prescriptionDateInLD = null;
        try {
            prescriptionDateInLD = LocalDate.parse(prescriptionDateIn, formatter);

        }catch(DateTimeParseException dtpe){
            JOptionPane.showMessageDialog(null, "Date de prescription invalide.",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        }

        if (prescriptionDateInLD.isAfter(LocalDate.now())) {
            throw new InputException("La date de prescription ne peut être après la date d'aujourd'hui!");
        } else {
            this.prescriptionDate = prescriptionDateInLD;
        }

    }

    /**
     * GETTER doctorLastName
     * @return String
     */
    public String getDoctorLastName() {
        return this.doctorLastName;
    }

    /**
     * SETTER doctorLastName
     * @param doctorLastNameIn String
     * @throws InputException
     */
    public void setDoctorLastName(String doctorLastNameIn) throws InputException {
        String doctorLastNameInSt = doctorLastNameIn.trim();
        for (Doctor doctor : Doctor.doctorsList){
            try {
                if (doctor.getLastName().equals(doctorLastNameInSt)) {
                    this.doctorLastName = doctorLastNameInSt;
                    doctor.setDoctorPrescriptionsList(this);
                    return;
                }
            }catch(NullPointerException npe){}
        }
        if(this.doctorLastName == null){
            throw new InputException("Ce medecin n'est pas enregistré.");
        }
    }

    /**
     * GETTER custormerLastName
     * @return String
     */
    public String getCustomerLastName() {
        return this.customerLastName;
    }

    /**
     * SETTER customerLastName
     * @param customerLastNameIn String
     * @throws InputException
     */
    public void setCustomerLastName(String customerLastNameIn) throws InputException {
        String customerLastNameInTrimed = customerLastNameIn.trim();
        for (Customer customer : Customer.customersList){
            try {
                if (customer.getLastName().equals(customerLastNameInTrimed)) {
                    this.customerLastName = customerLastNameInTrimed;
                    return;
                }
            }catch(NullPointerException npe){}
        }
        if(this.customerLastName == null){
            throw new InputException("Ce client n'est pas enregistré.");
        }
    }

    /**
     * GETTER drugsPrescriptionList
     * @return Map<Drug, Integer>
     */
    public Map<Drug, Integer> getDrugsQuantityPrescriptionList() {
        return this.drugsQuantityPrescriptionsList;
    }

    /**
     * SETTER drugsPrescritionList
     * @param drug Drug
     */
    public void setDrugsQuantityPrescriptionsList(Drug drug, int quantity) {
        this.drugsQuantityPrescriptionsList.put(drug,quantity);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return "Date: " + this.getPrescriptionDate().format(formatter) +
                "\nNom du medecin: " + this.getDoctorLastName() +
                "\nNom du client:  " + this.getCustomerLastName() +
                "\nListe des médicament: " + this.getDrugsQuantityPrescriptionList().toString() + '}';
    }

}
