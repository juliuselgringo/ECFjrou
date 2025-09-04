package training.afpa.sparadrap.model;

import training.afpa.sparadrap.ExceptionTracking.InputException;

import java.time.LocalDate;
import java.util.ArrayList;

public class Customer extends Person {

    private String socialSecurityId;
    private LocalDate dateOfBirth;
    private Mutual mutual;
    private Doctor doctor;

    private static final String regexSocialSecurityId = "[12]\\d{2}(0[1-9]|1[0-2])\\d{2}\\d{3}\\d{3}\\d{2}";
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
            throw new InputException("socialSecurityId cannot be empty or null");
        } else if (!socialSecurityId.matches(regexSocialSecurityId)) {
            throw new InputException("socialSecurityId is not valid");
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
        LocalDate localDateOfBirth = LocalDate.parse(dateOfBirth.trim());
        if(localDateOfBirth == null) {
            throw new InputException("dateOfBirth cannot be null");
        } else if (localDateOfBirth.isAfter(LocalDate.now())) {
            throw new InputException("dateOfBirth is after now");
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
     * GETTER Doctor
     * @return Doctor
     */
    public Doctor getDoctor() {
        return this.doctor;
    }

    /**
     * TO STRING
     * @return String
     */
    @Override
    public String toString() {
        return "Client \nPrénom: " + getFirstName() + ", Nom: " + getLastName() +
                ", Date de naissance: " + getDateOfBirth() + ", " + this.getContact() +
                "\nMutuelle: " + this.getMutual().getname() + " " + this.getMutual().getContact().getPostalCode() +
                ", \nDocteur: " + this.getDoctor().getLastName() +
                " Agreement: " + this.getDoctor().getAgreementId() + "\n";
    }
}
