package training.afpa.sparadrap.model;

import training.afpa.sparadrap.ExceptionTracking.InputException;

import java.io.Serializable;

public class Contact implements Serializable {

    private String address;
    private String postalCode;
    private String town;
    private String phone;
    private String email;

    private final String regexAddress = "\\d{1,5}(?:\\s(?:bis|ter|quater))?(?:[\\s,][a-zA-Z\\s\\,]{4,100})";
    private final String regexPostalCode = "\\d{5}";
    private final String regexTown = "[A-Z][a-z]*(['\\s][A-Z][a-z]+)*?";
    private final String regexPhone = "([0-9][0-9]\\s){4}([0-9][0-9])";
    public final String regexEmail = "[^\\s@]+@[^\\s@]+\\.[^\\s@]+";

    /**
     * CONSTRUCTOR
     * @param address String
     * @param postalCode String
     * @param town String
     * @param phone String
     * @param email String
     * @throws InputException
     */
    public Contact(String address, String postalCode, String town, String phone, String email) throws InputException {
        setAddress(address);
        setPostalCode(postalCode);
        setTown(town);
        setPhone(phone);
        setEmail(email);
    }

    /**
     * CONSTRUCTOR
     */
    public Contact() {}

    /**
     * GETTER address
     * @return
     */
    public String getAddress() {
        return address;
    }

    /**
     * SETTER address
     * @param address String
     * @throws InputException
     */
    public void setAddress(String address) throws InputException {
        address = address.trim();
        if(address == null || address.isEmpty()) {
            throw  new InputException("L'adresse ne peut être vide ou nulle");
        } else if (!address.matches(regexAddress)) {
            throw new InputException("L'adresse ne doit pas contenir d'accent, ni trait d'union");
        }else{
            this.address = address;
        }
    }

    /**
     * GETTER postalCode
     * @return String
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * SETTER postalCode
     * @param postalCode String
     * @throws InputException
     */
    public void setPostalCode(String postalCode) throws InputException {
        postalCode = postalCode.trim();
        if(postalCode == null || postalCode.isEmpty()) {
            throw  new InputException("Le code postal ne peut être  vide ou nul");
        }else if(!postalCode.matches(regexPostalCode)) {
            throw new InputException("Un code postal ne doit contenir que 5 chiffres");
        }else {
            this.postalCode = postalCode;
        }
    }

    /**
     * GETTER town
     * @return String
     */
    public String getTown() {
        return town;
    }

    /**
     * SETTER town
     * @param town String
     * @throws InputException
     */
    public void setTown(String town) throws InputException {
        town = town.trim();
        if(town == null || town.isEmpty()) {
            throw  new InputException("Le nom de la ville ne peut être vide ou nul");
        } else if (!town.matches(regexTown)) {
            throw new InputException("Le nom de la ville ne doit pas contenir d'accent mais doitavoir une majuscule à chaque particule pour les noms composés");
        }else{
            this.town = town;
        }
    }

    /**
     * GETTER phone
     * @return string
     */
    public String getPhone() {
        return phone;
    }

    /**
     * SETTER phone
     * @param phone
     * @throws InputException
     */
    public void setPhone(String phone) throws InputException {
        phone = phone.trim();
        if(phone == null || phone.isEmpty()) {
            throw  new InputException("Le n° de téléphone de peut être vide ou nul");
        } else if (!phone.matches(regexPhone)) {
            throw new InputException("Saisie invalide, séparé tout les 2 chiffres par un espace ex: xx xx xx xx xx");
        }else{
            this.phone = phone;
        }
    }

    /**
     * GETTER email
     * @return String
     */
    public String getEmail() {
        return email;
    }

    /**
     * SETTER email
     * @param email String
     * @throws InputException
     */
    public void setEmail(String email) throws InputException {
        email = email.trim();
        if(email == null || email.isEmpty()) {
            throw  new InputException("Email cannot be empty or null");
        }else if(!email.matches(regexEmail)) {
            throw  new InputException("Email invalide");
        }else {
            this.email = email;
        }
    }

    /**
     * TO STRING
     * @return String
     */
    @Override
    public String toString() {
        return "\nContact" +
                "\nAdresse: " + this.getAddress() +
                "\n Code postal: " + this.getPostalCode() +
                "\nVille: " + this.getTown() +
                "\nTéléphone: " + this.getPhone() +
                "\nEmail: " + this.getEmail();
    }


}
