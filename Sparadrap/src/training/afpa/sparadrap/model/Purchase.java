package training.afpa.sparadrap.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Purchase {

    private LocalDate purchaseDate;
    private Boolean withPrescription;
    private static Map<Drug, Integer> purchaseDrugsQuantity = new HashMap<>();
    private Prescription prescription;

    public static ArrayList<Purchase> purchasesHistory = new ArrayList<>();

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

    /**
     * GETTER purchaseDrugsQuantity
     * @return Map<Drug, Quantity>
     */
    public static Map<Drug, Integer>  getPurchaseDrugsQuantity() {
        return purchaseDrugsQuantity;
    }

    /**
     * SETTER purchaseDrugsList
     * @param drug
     */
    public void setPurchaseDrugsQuantity(Drug drug, int quantity) {
        if(withPrescription){
            this.prescription.setDrugsPrescriptionsList(drug,quantity);
            this.purchaseDrugsQuantity = this.prescription.getDrugsQuantityPrescriptionList();
        }else {
            this.purchaseDrugsQuantity.put(drug, quantity);
        }
    }


    @Override
    public String toString() {
        if(withPrescription) {
            return "achat{ date: " + this.getPurchaseDate().toString() +
                    ",\n liste des médicaments: " + this.getPurchaseDrugsQuantity().toString() +
                    ",\n client: " + this.prescription.getCustomerLastName() +
                    ",\n docteur: " + this.prescription.getDoctorLastName() + " }";
        }else {
            return "achat{ date: " + this.getPurchaseDate().toString() +
                    ",\n liste des médicament: " + this.getPurchaseDrugsQuantity().toString() + " }";
        }
    }

    /**
     * CREER UNE MATRICE DES ACHATS
     * @return String[][]
     */
    public static String[][] createPurchaseMatrice(){
        String[][] purchaseMatrice = new String[purchasesHistory.size()][3];
        int i = 0;
        for (Purchase purchase : purchasesHistory) {
            purchaseMatrice[i][0] = purchase.getPurchaseDate().toString();
            purchaseMatrice[i][1] = purchase.getPurchaseDrugsQuantity().toString();
            purchaseMatrice[i][2] = purchase.getPrescription().toString();
            i++;
        }
        return purchaseMatrice;
    }
}
