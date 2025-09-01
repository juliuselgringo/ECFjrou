package training.afpa.sparadrap.model;

import training.afpa.sparadrap.ExceptionTracking.InputException;

public class Person {

    private String firstName;
    private String lastName;
    private Contact contact;

    private final String regexFirstName = "[A-Z][a-z]+([\s][A-Z][a-z]+)?";
    private final String regexLastName = "[A-Z][a-z]+([\s][A-Z][a-z]+)?";

    /**
     * CONSTURCTOR
     * @param firstName String
     * @param lastName String
     * @throws InputException 
     */
    public Person(String firstName, String lastName, Contact contact) throws InputException {
        setFirstName(firstName);
        setLastName(lastName);
        this.contact = contact;
    }

    /**
     * GETTER firstName
     * @return String
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * SETTER firstName
     * @param firstName String
     * @throws InputException
     */
    public void setFirstName(String firstName) throws InputException {
        firstName = firstName.trim();
        if(firstName == null || firstName.isEmpty()) {
            throw new InputException("First name cannot be empty or null");
        } else if (!firstName.matches(regexFirstName)) {
            throw new InputException("Le prénom doit commencer par une majuscule et ne doit pas avoir d'accent ni trait d'union");
        }else {
            this.firstName = firstName;
        }
    }

    /**
     * GETTER lastName
     * @return String
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * SETTER lastName
     * @param lastName String
     * @throws InputException
     */
    public void setLastName(String lastName) throws InputException {
        lastName = lastName.trim();
        if(lastName == null || lastName.isEmpty()) {
            throw  new InputException("Last name cannot be empty or null");
        } else if (!lastName.matches(regexLastName)) {
            throw new InputException("Le nom doit commencer par une majuscule et ne doit pas avoir d'accent ni trait d'union");
        }else{
            this.lastName = lastName;
        }
    }




}
