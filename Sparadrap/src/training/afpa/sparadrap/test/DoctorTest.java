package training.afpa.sparadrap.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import training.afpa.sparadrap.ExceptionTracking.InputException;
import training.afpa.sparadrap.model.Contact;
import training.afpa.sparadrap.model.Customer;
import training.afpa.sparadrap.model.Doctor;
import training.afpa.sparadrap.model.Mutual;

import static org.junit.jupiter.api.Assertions.*;

class DoctorTest {

    Doctor testDoctor = new Doctor();
    Customer customerTest = new Customer();

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
        assertThrows(InputException.class, () -> {testDoctor.setAgreementId(agreementId);});
    }

    @Test
    public void setDoctorCustomersList_InvalidInput() throws InputException {
        testDoctor.setDoctorCustomersList(customerTest);
        assertThrows(InputException.class, () -> {testDoctor.setDoctorCustomersList(customerTest);});
    }


}