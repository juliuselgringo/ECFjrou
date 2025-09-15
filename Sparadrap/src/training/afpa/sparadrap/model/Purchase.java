package training.afpa.sparadrap.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Purchase {

    private LocalDate purchaseDate;
    private Integer purchaseNumber;
    private Boolean withPrescription;
    private Map<Drug, Integer> purchaseDrugsQuantity = new HashMap<>();
    private Double totalPrice;
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        this.purchaseDate = LocalDate.parse(purchaseDate, formatter);
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
     * GETTER totalPrice
     * @return Double
     */
    public Double getTotalPrice(){
        return this.totalPrice;
    }

    /**
     * SETTER totalPrice
     */
    public void setTotalPrice() {
        this.totalPrice = 0.00;
        Map<Drug, Integer> purchaseMap = this.getPurchaseDrugsQuantity();
        for(Map.Entry<Drug, Integer> entity : purchaseMap.entrySet()) {
            Drug drug = entity.getKey();
            int quantity = entity.getValue();
            this.totalPrice += drug.getPrice() * quantity;
        }
    }

    /**
     * SETTER purchaseDrugsQuantity
     * @param drug Drug
     * @param quantity int
     */
    public void setPurchaseDrugsQuantity(Drug drug, int quantity) {
        if(this.withPrescription){
            this.prescription.setDrugsQuantityPrescriptionList(drug,quantity);
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
     * SETTER purchaseDetails
     */
    public void setPurchaseDetails(){
        String[][] purchaseDetails = new String[this.purchaseDrugsQuantity.size()][5];
        int i = 0;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        for (Drug drug : this.purchaseDrugsQuantity.keySet()) {
            purchaseDetails[i][0] = this.getPurchaseDate().format(formatter);
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String stringToReturn = "\nDate: " + this.getPurchaseDate().format(formatter) +
                ", Numéro de commande: " + this.getPurchaseNumber();
        return stringToReturn;
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

    /**
     * CREER UNE MATRICE DES ACHATS
     * @return
     */
    public static String[][] createpurchasesMatrice(){
        String[][] purchaseMatrice = new String[purchasesHistory.size()][5];
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        int i = 0;
        try {
            for (Purchase purchase : purchasesHistory) {
                purchaseMatrice[i][0] = purchase.getPurchaseDate().format(formatter);
                purchaseMatrice[i][1] = purchase.getPurchaseNumber().toString();
                if(!purchase.getWithPrescription()){
                    purchaseMatrice[i][2] = "sans ordonnance";
                }else {
                    purchaseMatrice[i][2] = purchase.getPrescription().getCustomerLastName();
                }
                i++;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return purchaseMatrice;
    }

}
