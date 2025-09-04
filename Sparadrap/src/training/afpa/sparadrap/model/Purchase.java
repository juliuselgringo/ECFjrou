package training.afpa.sparadrap.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Purchase {

    private LocalDate purchaseDate;
    private Integer purchaseNumber;
    private Boolean withPrescription;
    private Map<Drug, Integer> purchaseDrugsQuantity = new HashMap<>();
    private String[][] purchaseDetails;
    private Prescription prescription;

    public static ArrayList<Purchase> purchasesHistory = new ArrayList<Purchase>();
    public static int incrementPurchaseNumber = 1;

    /**
     * CONSTRUCTOR
     * @param withPrescription
     */
    public Purchase(Boolean withPrescription) {
        this.purchaseDate = LocalDate.now();
        this.withPrescription = withPrescription;
        purchasesHistory.add(this);
        this.purchaseNumber = incrementPurchaseNumber;
        incrementPurchaseNumber++;
    }

    /**
     * CONSTRUCTOR
     * @param withPrescription
     */
    public Purchase(String purchaseDate, Boolean withPrescription) {
        setPurchaseDate(purchaseDate);
        this.withPrescription = withPrescription;
        purchasesHistory.add(this);
        this.purchaseNumber = incrementPurchaseNumber;
        incrementPurchaseNumber++;
    }


    /**
     * GETTER purchaseDate
     * @return LocalDate
     */
    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public  void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = LocalDate.parse(purchaseDate);
    }

    /**
     * GETTER purchaseNumber
     * @return Integer
     */
    public Integer getPurchaseNumber() {
        return this.purchaseNumber;
    }

    /**
     * GETTER withPrescription
     * @return Boolean
     */
    public Boolean getWithPrescription() {
        return withPrescription;
    }

    /**
     * GETTER prescription
     * @return Prescription
     */
    public Prescription getPrescription() {
        return prescription;
    }
    
    public void setPrescrition(Prescription prescription){
        this.prescription = prescription;
    }

    /**
     * GETTER purchaseDrugsQuantity
     * @return Map<Drug, Quantity>
     */
    public Map<Drug, Integer>  getPurchaseDrugsQuantity() {
        return this.purchaseDrugsQuantity;
    }

    /**
     * SETTER purchaseDrugsQuantity
     * @param drug Drug
     * @param quantity int
     */
    public void setPurchaseDrugsQuantity(Drug drug, int quantity) {
        if(this.withPrescription){
            this.prescription.setDrugsQuantityPrescriptionsList(drug,quantity);
            this.purchaseDrugsQuantity = this.prescription.getDrugsQuantityPrescriptionList();
        }else {
            this.purchaseDrugsQuantity.put(drug, quantity);
        }
    }

    /**
     * GETTER purchaseDetails
     * @return String[][]
     */
    public String[][] getPurchaseDetails() {
        return this.purchaseDetails;
    }

    /**
     * SETTER purchaseDateDrugsQuantities
     * @return String[][]
     */
    public void setPurchaseDetails(){
        String[][] purchaseDetails = new String[this.purchaseDrugsQuantity.size()][5];
        int i = 0;
        for (Drug drug : this.purchaseDrugsQuantity.keySet()) {
            purchaseDetails[i][0] = this.getPurchaseDate().toString();
            purchaseDetails[i][1] = this.getPurchaseNumber().toString();
            if(this.withPrescription) {
                purchaseDetails[i][2] = this.getPrescription().getCustomerLastName();
            }else {
                purchaseDetails[i][2] = "Anonyme (achat sans prescription)";
            }
            purchaseDetails[i][3] = drug.getName();
            i++;
        }
        i = 0;
        for (Integer quantity : this.purchaseDrugsQuantity.values()) {
            purchaseDetails[i][4] = quantity.toString();
            i++;
        }

        this.purchaseDetails = purchaseDetails;
    }

    /**
     * TO STRING
     * @return String
     */
    @Override
    public String toString() {
        String stringToReturn = "\nAchat{ Date: " + this.getPurchaseDate().toString() +
                ", Numéro de commande: " + this.getPurchaseNumber();
        if(withPrescription) {
            stringToReturn += ",\n Client: " + this.prescription.getCustomerLastName() +
                    ",\n Docteur: " + this.prescription.getDoctorLastName() + " }";
        }else {
            stringToReturn += ",\n Achat sans prescription }";
        }
        return stringToReturn;
    }

    /**
     * CREER UNE CHAINE STRING DE MEDICAMENTS AVEC LES QUANTITES
     * @return String
     */
    public String purchaseDrugsQuantityToString(){
        String[] drugList = new String[purchaseDrugsQuantity.size()];
        int i = 0;
        for(Drug drug :  purchaseDrugsQuantity.keySet()){
            drugList[i] = drug.toString();
            i++;
        }
        String[] quantityList = new String[purchaseDrugsQuantity.size()];
        int j = 0;
        for(Integer quantity : purchaseDrugsQuantity.values()){
            quantityList[j] = quantity.toString();
            j++;
        }

        String[] drugsQuantitiesList = new String[purchaseDrugsQuantity.size()];
        for (int z = 0; z < quantityList.length; z++){
            drugsQuantitiesList[z] = drugList[z] + " : " + quantityList[z] + "\n";
        }
        return Arrays.toString(drugsQuantitiesList);
    }

    /**
     * CREER UNE LISTE DE COMMANDE PAR PERIODE
     * @param startDate LocalDate
     * @param endDate LocalDate
     * @return
     */
    public static ArrayList searchPurchaseByPeriod(LocalDate startDate, LocalDate endDate) {
        ArrayList purchaseByPeriod = new ArrayList();
        for(Purchase purchase : purchasesHistory){
            LocalDate date = purchase.getPurchaseDate();
            if(date.isAfter(startDate) && date.isBefore(endDate)){
                purchaseByPeriod.add(purchase);
            } else if (date.isEqual(startDate) || date.isEqual(endDate)) {
                purchaseByPeriod.add(purchase);
            }
        }
        return purchaseByPeriod;
    }

    /**
     * SUPPRIMER UNE COMMANDE L HISTORIQUE
     */
    public void deletePurchaseFromHistory() {
        purchasesHistory.remove(this);
    }

}
