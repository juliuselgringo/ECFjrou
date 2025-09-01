package training.afpa.sparadrap.model;

import training.afpa.sparadrap.ExceptionTracking.InputException;

import java.time.LocalDate;
import java.util.Date;

public class Customer extends Person {

    private String socialSecurityId;
    private LocalDate dateOfBirth;
    private Mutual mutual;
    private Doctor doctor;

    private static final String regexSocialSecurityId = "^[12]\\d{2}(0[1-9]|1[0-2])\\d{2}\\d{3}\\d{3}\\d{3}$\n";

    /**
     * CONSTRUCTOR
     * @param socialSecurityId String
     * @param dateOfBirth Date
     * @param mutual Mutual
     * @param doctor Doctor
     * @throws InputException
     */
    public Customer(String firstName, String lastName, Contact contact,String socialSecurityId,
                    LocalDate dateOfBirth, Mutual mutual, Doctor doctor) throws InputException {
        super(firstName, lastName, contact);
        setSocialSecurityId(socialSecurityId);
        setDateOfBirth(dateOfBirth);
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
    public void setDateOfBirth(LocalDate dateOfBirth) throws InputException {
        if(dateOfBirth == null) {
            throw new InputException("dateOfBirth cannot be null");
        } else if (dateOfBirth.isAfter(LocalDate.now())) {
            throw new InputException("dateOfBirth is after now");
        }else{
            this.dateOfBirth = dateOfBirth;
        }
    }



}
