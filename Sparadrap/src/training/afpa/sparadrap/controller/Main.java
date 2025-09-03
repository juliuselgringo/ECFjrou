package training.afpa.sparadrap.controller;


import training.afpa.sparadrap.ExceptionTracking.InputException;
import training.afpa.sparadrap.model.*;
import training.afpa.sparadrap.view.ProgramSwing;
import training.afpa.sparadrap.utility.Display;

public class Main {

    public static void main(String[] args) throws InputException {
        Main.developpmentDataInput();
        //Main.fuctionnalTestObjectCreation();
        ProgramSwing.generalMenu();
        /*
        String test = Purchase.createPurchaseMatrice()[0][1];
        Display.print("test matrice :" + test);
        Display.print(Purchase.purchasesHistory.toString());
        */
    }

    private static void fuctionnalTestObjectCreation() throws InputException {


    }

    private static void developpmentDataInput() throws InputException {

        //________________________MUTUELLES______________________________________
        Contact harmo75 = new Contact("10 Rue de la Sante","75000",
                "Paris","01 23 45 67 89","contact@harmonie.fr");
        Mutual harmonie75 = new Mutual("Harmonie Mutuelle", harmo75, 0.75);

        Contact mgen69 = new Contact("25 Av de la Mutualite","69000",
                "Lyon","04 56 78 90 12","contact@mgen.fr");
        Mutual mgenLyon = new Mutual("Mgen", mgen69, 0.80);

        //__________________________DOCTEURS____________________________________
        Contact jeDup75 = new Contact("12 Rue de Paris", "75000", "Paris",
                "01 23 45 67 89", "jean.dupont@medecin.fr");
        Doctor jeDupParis = new Doctor("Jean", "Dupont", jeDup75,"12345678901");

        Contact maMar69 = new Contact("45 Av des Champs", "69000", "Lyon",
                "04 56 78 90 12", "marie.martin@medecin.fr");
        Doctor maMarLyon = new Doctor("Marie","Martin", maMar69, "23456789012");
        //________________________CLIENTS__________________________________________
        Contact alLef75 = new Contact("12 Rue de Paris", "75000", "Paris",
                "01 23 45 67 89", "alice.lefevre@mail.fr");
        Customer alLefParis = new Customer("Alice", "Lefevre",alLef75,"275126432109848",
                "1985-07-15", harmonie75, jeDupParis );

        Contact maPet69 = new Contact("34 Av des Camps", "69000", "Lyon",
                "04 56 78 90 12", "marc.petit@mail.fr");
        Customer maPetLyon = new Customer("Marc", "Petit", maPet69,"185076432109818",
                "1975-12-20", mgenLyon, maMarLyon);

        //_______________________MEDICAMENTS___________________________
        Drug dafalgan = new Drug("Dafalgan","Analgesiques et Anti-inflammatoires",9.99, "2024-12-03", 50, false);
        Drug amoxicilline = new Drug("Amoxicilline", "Antibiotiques et Antibacteriens", 12.50, "2025-01-15", 30, true);
        Drug ventoline = new Drug("Ventoline", "Immunologie et Allergologie", 15.75, "2025-01-20", 20, true);
        Drug levothyrox = new Drug("Levothyrox", "Endocrinologie", 8.90, "2025-02-05", 90, false);
        Drug dolirhume = new Drug("Dolirhume", "Analgesiques et Anti-inflammatoires", 6.49, "2025-06-30", 40, false);
        Drug seroplex = new Drug("Seroplex", "Neurologie", 22.30, "2025-08-10", 28, true);
        Drug smecta = new Drug("Smecta", "Gastro-enterologie et hepatologie", 4.20, "2025-02-01", 60, false);
        Drug lisinopril = new Drug("Lisinopril", "Cardiologie", 7.80, "2025-07-18", 30, true);
        Drug doliprane = new Drug("Doliprane", "Analgesiques et Anti-inflammatoires", 5.99, "2025-02-10", 50, false);
        Drug zyrtec = new Drug("Zyrtec", "Immunologie et Allergologie", 10.25, "2025-06-25", 15, false);

        //_______________________________ACHATS_________________________________________
        Purchase purchase1 = new Purchase(false);
        purchase1.setPurchaseDrugsQuantity(Drug.drugsList.get(0),1);
        purchase1.setPurchaseDrugsQuantity(Drug.drugsList.get(3),1);
        purchase1.setPurchaseDrugsQuantity(Drug.drugsList.get(6),1);
        purchase1.setPurchaseDateDrugsQuantities();


        Purchase purchase2 = new Purchase(true);
        Prescription prescription2 = new Prescription("2025-08-29", "Martin","Petit");
        purchase2.setPrescrition(prescription2);
        purchase2.setPurchaseDrugsQuantity(Drug.drugsList.get(2),1);
        purchase2.setPurchaseDrugsQuantity(Drug.drugsList.get(4),1);
        purchase2.setPurchaseDrugsQuantity(Drug.drugsList.get(8),1);
        purchase2.setPurchaseDateDrugsQuantities();

    }
}
