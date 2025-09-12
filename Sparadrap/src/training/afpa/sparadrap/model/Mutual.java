package training.afpa.sparadrap.model;

import training.afpa.sparadrap.ExceptionTracking.InputException;

import java.util.ArrayList;

public class Mutual {

    private String name;
    private Contact contact;
    private Double rate;

    public static ArrayList<Mutual> mutualsList = new ArrayList<>();
    
    public Mutual(String name, Contact contact, Double rate) throws InputException {
        setName(name);
        this.contact = contact;
        setRate(rate);
        mutualsList.add(this);
    }

    /**
     * GETTER name
     * @return String
     */
    public String getName() {
        return this.name;
    }

    /**
     * SETTER name
     * @param name String
     * @throws InputException
     */
    public void setName(String name) throws InputException {
        final String regexName = "[A-Z][a-z]+([\s][A-Z][a-z]+)?([\\s][0-9]+)?";
        name = name.trim();
        if(name.isEmpty()) {
            throw new InputException("Le nom de la mutuelle ne peut être vide");
        } else if (!name.matches(regexName)) {
            throw new InputException("Le prénom doit commencer par une majuscule et ne doit pas avoir d'accent ni trait d'union");
        }else {
            this.name = name;
        }
    }

    /**
     * GETTER contact
     * @return Contact
     */
    public Contact getContact() {
        return this.contact;
    }

    /**
     * GETTER rate
     * @return Double
     */
    public Double getRate() {
        return rate;
    }

    /**
     * SETTER rate
     * @param rate Double
     * @throws InputException
     */
    public void setRate(Double rate) throws InputException {
        if(rate <= 0 || rate >= 1) {
            throw new InputException("Le taux ne peut être négatif ou supérieur à 1");
        }else {
            this.rate = rate;
        }
    }

    /**
     * TO STRING
     * @return String
     */
    @Override
    public String toString() {
        return "\nNom: " + this.getName() +
                "\nCoordonnées:  " +
                this.getContact() +
                "\nTaux: " + this.getRate();
    }
}
