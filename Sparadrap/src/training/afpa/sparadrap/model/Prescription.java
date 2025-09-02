package training.afpa.sparadrap.model;

import training.afpa.sparadrap.ExceptionTracking.InputException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Prescription {

    private LocalDate prescriptionDate;
    private String doctorLastName;
    private String customerLastName;
    private Map<Drug, Integer> drugsQuantityPrescriptionsList = new HashMap<>();

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
        this.setCustomerLastName(doctorLastNameIn);
    }

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
        prescriptionDate = LocalDate.parse(prescriptionDateIn);
        if (prescriptionDate.isAfter(LocalDate.now())) {
            throw new InputException("La date de prescription ne peut être après la date d'aujourd'hui!");
        }else {
            this.prescriptionDate = prescriptionDate;
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
        doctorLastName = doctorLastNameIn.trim();
        for (Doctor doctor : Doctor.doctorsList){
            if (doctor.getLastName().equals(doctorLastNameIn)){
                doctorLastName = doctor.getLastName();
            }else {
                throw new InputException("Ce medecin n'est pas enregistré.");
            }
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
        customerLastName = customerLastNameIn.trim();
        for (Customer customer : Customer.customersList){
            if (customer.getLastName().equals(customerLastNameIn)){
                customerLastName = customer.getLastName();
            }else {
                throw new InputException("Ce client n'est pas enregistré.");
            }
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
    public void setDrugsPrescriptionsList(Drug drug, int quantity) {
        this.drugsQuantityPrescriptionsList.put(drug,quantity);
    }

    @Override
    public String toString() {
        return "Prescription{ prescriptionDate: " + this.getPrescriptionDate() +
                ", nom du medecin: " + this.getDoctorLastName() + ", nom du client:  " + this.getCustomerLastName() +
                ", liste des médicament: " + this.getDrugsQuantityPrescriptionList().toString() + '}';
    }
}
