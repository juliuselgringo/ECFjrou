package training.afpa.sparadrap.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import training.afpa.sparadrap.ExceptionTracking.InputException;
import training.afpa.sparadrap.model.*;

import static org.junit.jupiter.api.Assertions.*;

class DoctorTest {

    DoctorTest() throws InputException {
    }


    @Test
    void constructor1_GetterAndSetter_ValidInput() throws InputException {
        Contact jeDup75 = new Contact("12 Rue de Paris", "75000", "Paris",
                "01 23 45 67 89", "jean.dupont@medecin.fr");
        Doctor jeDupParis = new Doctor("Jean", "Dupont", jeDup75,"12345678901");
        assertEquals("Jean", jeDupParis.getFirstName());
        assertEquals("Dupont", jeDupParis.getLastName());
        assertInstanceOf(Contact.class, jeDupParis.getContact());
        assertEquals("12345678901", jeDupParis.getAgreementId());

    }

    @Test
    void constructor2_ValidInput() throws InputException {
        Doctor jeDupParis = new Doctor();
        assertInstanceOf(Doctor.class, jeDupParis);

    }

    @ParameterizedTest(name="{0} le setter leve correctment l exception")
    @ValueSource(strings={"", "         ","aztuiy", ":;,"})
    public void setAgreementId_InvalidInput(String agreementId) throws InputException {
        Doctor testDoctor = new Doctor();
        Customer customerTest = new Customer();
        assertThrows(InputException.class, () -> {testDoctor.setAgreementId(agreementId);});
    }

    @Test
    public void setDoctorCustomersList_InvalidInput() throws InputException {
        Doctor testDoctor = new Doctor();
        Customer customerTest = new Customer();
        testDoctor.setDoctorCustomersList(customerTest);
        assertThrows(InputException.class, () -> {testDoctor.setDoctorCustomersList(customerTest);});
    }


    @Test
    public void setDoctorPrescriptionsList_ValidInput() throws InputException {
        Doctor testDoctor = new Doctor();
        Customer customerTest = new Customer();
        testDoctor.setLastName("Sean");
        customerTest.setLastName("Paul");
        Prescription prescription = new Prescription("12-12-2012", "Sean", "Paul");
        InputException thrown = assertThrows(InputException.class, () -> {
            testDoctor.setDoctorPrescriptionsList(prescription);
        });
        assertEquals("Cette prescription est déjà dans la liste du docteur", thrown.getMessage());
    }

    @Test
    public void toStringForDetails_ValidInput() throws InputException {
        Contact jeDup75 = new Contact("12 Rue de Paris", "75000", "Paris",
                "01 23 45 67 89", "jean.dupont@medecin.fr");
        Doctor jeDupParis = new Doctor("Jean", "Dupont", jeDup75,"12345678901");
        assertEquals("\nDocteur" +
                "\nPrénom: " + "Jean" +
                "\nNom: " + "Dupont" +
                "\n"  + "\nContact" +
                "\nAdresse: " + "12 Rue de Paris" +
                "\n Code postal: " + "75000" +
                "\nVille: " + "Paris" +
                "\nTéléphone: " + "01 23 45 67 89" +
                "\nEmail: " + "jean.dupont@medecin.fr" +
                "\nN° d'agréement: " + "12345678901", jeDupParis.toStringForDetails());
    }

    @Test
    public void toString_ValidInput() throws InputException {
        Contact jeDup75 = new Contact("12 Rue de Paris", "75000", "Paris",
                "01 23 45 67 89", "jean.dupont@medecin.fr");
        Doctor jeDupParis = new Doctor("Jean", "Dupont", jeDup75,"12345678901");
        assertEquals("\nPrénom: " + "Jean" +
                "\n Nom: " + "Dupont", jeDupParis.toString());
    }

    @Test
    public void createDoctorsMatrice_ValidInput() throws InputException {
        Doctor.doctorsList.clear();
        Contact jeDup75 = new Contact("12 Rue de Paris", "75000", "Paris",
                "01 23 45 67 89", "jean.dupont@medecin.fr");
        Doctor testDoctor = new Doctor("Jean", "Dupont", jeDup75,"12345678901");
        assertEquals("Jean", Doctor.createDoctorsMatrice()[0][0]);
    }
}