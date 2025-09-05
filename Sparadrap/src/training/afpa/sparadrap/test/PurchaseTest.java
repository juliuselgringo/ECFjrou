package training.afpa.sparadrap.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import training.afpa.sparadrap.ExceptionTracking.InputException;
import training.afpa.sparadrap.model.*;

import static org.junit.jupiter.api.Assertions.*;

class PurchaseTest {

    Purchase testPurchase = new Purchase(true);
    Drug dafalgan = new Drug("Dafalgan","Analgesiques et Anti-inflammatoires",9.99,
            "2024-12-03", 50, false);
    Contact alLef75 = new Contact("12 Rue de Paris", "75000", "Paris",
            "01 23 45 67 89", "alice.lefevre@mail.fr");
    Contact harmo75 = new Contact("10 Rue de la Sante","75000",
            "Paris","01 23 45 67 89","contact@harmonie.fr");
    Mutual harmonie75 = new Mutual("Harmonie Mutuelle", harmo75, 0.75);
    Contact jeDup75 = new Contact("12 Rue de Paris", "75000", "Paris",
            "01 23 45 67 89", "jean.dupont@medecin.fr");
    Doctor jeDupParis = new Doctor("Jean", "Dupont", jeDup75,"12345678901");

    PurchaseTest() throws InputException {
    }

    @Test
    void constructor_ValidInput() {
        Purchase test = new Purchase(true);
        assertInstanceOf(Purchase.class, test);
        assertEquals(true, test.getWithPrescription());
    }

    @Test
    void constructor2_ValidInput() throws InputException {
        Purchase test = new Purchase("2025-08-01",true);
        assertInstanceOf(Purchase.class, test);
        assertEquals(true, test.getWithPrescription());
        assertEquals("2025-08-01",test.getPurchaseDate().toString());
    }

    @Test
    void setterPurchaseDrugsQuantity_ValidInput() throws InputException {
        Prescription testPresc = new Prescription();
        testPurchase.setPrescrition(testPresc);
        testPurchase.setPurchaseDrugsQuantity(dafalgan, 10);
        assertEquals(
                "{Medicament{ nom: Dafalgan, catégorie: Analgesiques et Anti-inflammatoires, " +
                "prix: 9.99, date de production: 2024-12-03, quantité: 50, underPrescription: false}=10}",
                testPurchase.getPurchaseDrugsQuantity().toString());
    }

    @Test
    void setterPurchaseDetails_ValidInput() throws InputException {
        Customer testCustomer = new Customer("Jul","Jul", alLef75,"123456789012345",
                "2000-10-10",harmonie75,jeDupParis);
        Prescription testPresc = new Prescription("2025-01-01","Dupont","Jul");
        testPurchase.setPrescrition(testPresc);
        testPurchase.setPurchaseDrugsQuantity(dafalgan, 10);
        testPurchase.setPurchaseDetails();
        assertEquals("2025-09-05", testPurchase.getPurchaseDetails()[0][0].toString());
    }


}