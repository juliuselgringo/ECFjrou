package training.afpa.sparadrap.model;

import training.afpa.sparadrap.ExceptionTracking.InputException;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Drug implements Serializable {

    private String name;
    private String categoryName;
    private Double price;
    private LocalDate productionDate;
    private int quantity;
    private Boolean underPrescription;
    private Integer totalOut = 0;

    private final String regexName = "[A-Z][a-z]+([\\s][A-Z][a-z]+)?([\\s][0-9]+)?";

    public static ArrayList<Drug> drugsList =  new ArrayList<>();

    public static String[] categoriesNamesList = new String[] {
            "Analgesiques et Anti-inflammatoires",
            "Antibiotiques et Antibacteriens",
            "Antituberculeux et Antilepreux",
            "Antimycosiques",
            "Antiviraux",
            "Cardiologie",
            "Dermatologie",
            "Dietetique et Nutrition",
            "Endocrinologie",
            "Gastro-enterologie et hepatologie",
            "Gynecologie obstetrique et contraception",
            "Hematologie",
            "Immunologie et Allergologie",
            "Medicaments des troubles metaboliques",
            "Neurologie",
            "Ophtalmologie",
            "Oto-rhino-laryngologie",
            "Parasitologie",
            "Pneumologie",
            "Psychiatrie",
            "Reanimation et toxicologie",
            "Rhumatologie",
            "Stomatologie",
    };

    /**
     * CONSTRUCTOR
     * @param name String
     * @param categoryName String
     * @param price Double
     * @param productDate String
     * @param quantity int
     * @param underPrescription Boolean
     */
    public Drug(String name, String categoryName, Double price, String productDate,
                int quantity, Boolean underPrescription) throws InputException {
        this.setName(name);
        this.setCategoryName(categoryName);
        this.setPrice(price);
        this.setProductionDate(productDate);
        this.setQuantity(quantity);
        this.setUnderPrescription(underPrescription);
        drugsList.add(this);
        drugsList.sort(Comparator.comparing(Drug::getName));
    }

    /**
     * CONSTRUCTOR
     */
    public Drug(){
        drugsList.add(this);
    }

    /**
     * GETTER name
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * SETTER name
     * @param name String
     */
    public void setName(String name) throws InputException {
        name = name.trim();
        if (name.isEmpty() || name == null) {
            throw new InputException("First name cannot be empty or null");
        }else if (!name.matches(regexName)) {
            throw new InputException("Le nom du médicament doit commencer par une majuscule et ne doit pas avoir d'accent ni trait d'union");
        }else{
            this.name = name;
        }
    }

    /**
     * GETTER price
     * @return Double
     */
    public Double getPrice() {
        return price;
    }

    /**
     * SETTER price
     * @param price Double
     * @throws InputException
     */
    public void setPrice(Double price) throws InputException {
        if(price == null || price <= 0){
            throw new InputException("Le prix ne peux être inférieur ou égal à 0");
        }else{
            this.price = price;
        }
    }

    /**
     * GETTER productionDate
     * @return LocaleDate
     */
    public LocalDate getProductionDate() {
        return productionDate;
    }

    /**
     * SETTER productionDate
     * @param productDate String
     * @throws InputException
     */
    public void setProductionDate(String productDate) throws InputException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String productionDate = productDate.trim();
        String regexDate = "(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[0-2])-(19|20)\\d{2}";
        LocalDate productionDateP =  null;
            if(productionDate == null ||productionDate.isEmpty() || !productionDate.matches(regexDate)) {
                throw new InputException("La date de production est invalide (jj-mm-aaaa).");
            }
            productionDateP = LocalDate.parse(productDate.trim(), formatter);

            if (productionDateP.isAfter(LocalDate.now())) {
                throw new InputException("La date de production doit être antérieure à la date d'aujourd'hui.");
            } else {
                this.productionDate = productionDateP;
            }

    }

    /**
     * GETTER quantity
     * @return int
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * SETTER quantity
     * @param quantity int
     * @throws InputException
     */
    public void setQuantity(int quantity) throws InputException {
        if(quantity < 0){
            throw new InputException("La quantité ne peut être inférier à 0");
        }else {
            this.quantity = quantity;
        }
    }

    /**
     * GETTER underPrescription
     * @return Boolean
     */
    public Boolean isUnderPrescription() {
        return this.underPrescription;
    }

    /**
     * SETTER underPrescription
     * @param underPrescription Boolean
     */
    public void setUnderPrescription(Boolean underPrescription) {
        this.underPrescription = underPrescription;
    }

    /**
     * GETTER name
     * @return String
     */
    public String getCategoryName() {
        return this.categoryName;
    }

    /**
     * SETTER name
     * @param name String
     * @throws InputException
     */
    public void setCategoryName(String name) throws InputException {
        for(String c : categoriesNamesList){
            if(name.equals(c)){
                this.categoryName = name;
            }
        }
        if(this.categoryName == null){
            throw new InputException("Cette catégorie de médicament n'existe pas");
        }
    }

    /**
     * GETTER totalOut
     * @return Integer
     */
    public Integer getTotalOut(){
        return this.totalOut;
    }

    /**
     * SETTER totalOut
     * @param totalOut Integer
     * @throws InputException
     */
    public void setTotalOut(Integer totalOut) throws InputException {
        if(totalOut == null || totalOut < 0){
            throw new InputException("La quantité ne peux être négative ou nul");
        }else{
            this.totalOut = totalOut;
        }
    }

    /**
     * TO STRING
     * @return String
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return "\n\nNom: " + this.getName() +
                "\nCatégorie: " + this.getCategoryName() +
                "\nPrix: " + this.getPrice() +
                "\nDate de production: " + this.getProductionDate().format(formatter) +
                "\nQuantité en stock: " + this.getQuantity() +
                "\nUnderPrescription: " + this.isUnderPrescription();
    }

    /**
     * TO STRING SANS RETOUR CHARIOT POUR LE PDF
     * @return String
     */
    public String toStringForPdf(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return " // " + this.getName();
    }

    /**
     * MISE A JOUR DU STOCK
     * @param drugToUpdate Drug
     * @param quantity int
     * @throws InputException
     */
    public static void stockUpdate(Drug drugToUpdate, int quantity) throws InputException {
        for(Drug drug : drugsList){
            if(drug.getName().equals(drugToUpdate.getName())){
                drug.setQuantity(drug.getQuantity() + quantity);
                return;
            }
        }
        throw new InputException("Médicament introuvable");
    }

    /**
     * CREER UNE MATRICE DES MEDICAMENTS
     * @return String[][]
     */
    public static String[][] createDrugsMatrice(){
        String[][] matrices = new String[drugsList.size()][6];
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        int i = 0;
        for (Drug drug : drugsList) {
            matrices[i][0] = drug.getName();
            matrices[i][1] = drug.getCategoryName();
            matrices[i][2] = drug.getPrice().toString();
            matrices[i][3] = drug.getProductionDate().format(formatter);
            matrices[i][4] = ((Integer)drug.getQuantity()).toString();
            matrices[i][5] = drug.isUnderPrescription().toString();
            i++;
        }
        return matrices;
    }

}
