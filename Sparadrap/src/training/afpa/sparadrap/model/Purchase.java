package training.afpa.sparadrap.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Purchase {

    private LocalDate purchaseDate;
    private Boolean withPrescription;
    private Map<Drug, Integer> purchaseDrugsQuantity = new HashMap<>();
    private Prescription prescription;

    public static ArrayList<Purchase> purchasesHistory = new ArrayList<Purchase>();

    /**
     * CONSTRUCTOR
     * @param withPrescription
     */
    public Purchase(Boolean withPrescription) {
        this.purchaseDate = LocalDate.now();
        this.withPrescription = withPrescription;
        purchasesHistory.add(this);
    }


    /**
     * GETTER purchaseDate
     * @return LocalDate
     */
    public LocalDate getPurchaseDate() {
        return purchaseDate;
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
     * TO STRING
     * @return String
     */
    @Override
    public String toString() {
        if(withPrescription) {
            return "\nachat{ date: " + this.getPurchaseDate().toString() +
                    ",\n liste des médicaments: " + this.getPurchaseDrugsQuantity().toString() +
                    ",\n client: " + this.prescription.getCustomerLastName() +
                    ",\n docteur: " + this.prescription.getDoctorLastName() + " }";
        }else {
            return "\nachat{ date: " + this.getPurchaseDate().toString() +
                    ",\n liste des médicament: " + this.getPurchaseDrugsQuantity().toString() + " }";
        }
    }

    /**
     * CREER UNE MATRICE DES ACHATS
     * @return String[][]
     */
    public static String[][] createPurchaseMatrice(){
        String[][] purchaseMatrice = new String[purchasesHistory.size()][2];
        int i = 0;
        for (Purchase purchase : purchasesHistory) {
            purchaseMatrice[i][0] = purchase.getPurchaseDate().toString();
            purchaseMatrice[i][1] = purchase.getPurchaseDrugsQuantity().toString();
            i++;
        }
        return purchaseMatrice;
    }

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
}
