package training.afpa.sparadrap.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import training.afpa.sparadrap.ExceptionTracking.InputException;
import training.afpa.sparadrap.model.*;

import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class PurchaseTest {

    Purchase testPurchase = new Purchase(true);
    Drug dafalgan = new Drug("Dafalgan","Analgesiques et Anti-inflammatoires",9.99,
            "03-12-2005", 50, false);
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        Purchase test = new Purchase("01-08-2025",true);
        assertInstanceOf(Purchase.class, test);
        assertEquals(true, test.getWithPrescription());
        assertEquals("01-08-2025",test.getPurchaseDate().format(formatter));
    }

    @Test
    void setterPurchaseDrugsQuantity_ValidInput() throws InputException {
        Prescription testPresc = new Prescription();
        testPurchase.setPrescrition(testPresc);
        testPurchase.setPurchaseDrugsQuantity(dafalgan, 10);
        assertEquals("{\n" +
                        "\n" +
                        "Nom: Dafalgan\n" +
                        "Catégorie: Analgesiques et Anti-inflammatoires\n" +
                        "Prix: 9.99\n" +
                        "Date de production: 03-12-2005\n" +
                        "Quantité en stock: 50\n" +
                        "UnderPrescription: false=10}"
                ,
                testPurchase.getPurchaseDrugsQuantity().toString());
    }

    @Test
    void setterPurchaseDetails_ValidInput() throws InputException {
        Customer testCustomer = new Customer("Jul","Jul", alLef75,"123456789012345",
                "10-10-2000",harmonie75,jeDupParis);
        Prescription testPresc = new Prescription("01-01-2025","Dupont","Jul");
        testPurchase.setPrescrition(testPresc);
        testPurchase.setPurchaseDrugsQuantity(dafalgan, 10);
        testPurchase.setPurchaseDetails();
        assertEquals("12-09-2025", testPurchase.getPurchaseDetails()[0][0].toString());
    }


}