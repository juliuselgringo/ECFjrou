package training.afpa.sparadrap.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import training.afpa.sparadrap.ExceptionTracking.InputException;
import training.afpa.sparadrap.model.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

    Customer test = new Customer();

    @Test
    void constructor1_GetterAndSetter_ValidInput() throws InputException {
        Contact mgen69 = new Contact("25 Av de la Mutualite","69000",
                "Lyon","04 56 78 90 12","contact@mgen.fr");
        Mutual mgenLyon = new Mutual("Mgen", mgen69, 0.80);
        Contact contact = new Contact("3, rue de la joie","54000","Nancy","00 00 00 00 00", "azer@azer.aze");
        Contact jeDup75 = new Contact("12 Rue de Paris", "75000", "Paris",
                "01 23 45 67 89", "jean.dupont@medecin.fr");
        Doctor jeDupParis = new Doctor("Jean", "Dupont", jeDup75,"12345678901");
        Customer customer = new Customer("Jojo", "Ducont",contact,"123456789012345","10-10-2000",mgenLyon,jeDupParis);
        assertEquals("Jojo", customer.getFirstName());
        assertEquals("Ducont", customer.getLastName());
        assertInstanceOf(Contact.class, customer.getContact());
        assertEquals("123456789012345", customer.getSocialSecurityId());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        assertEquals("10-10-2000", customer.getDateOfBirth().format(formatter));
        assertInstanceOf(Mutual.class, customer.getMutual());
        assertInstanceOf(Doctor.class, customer.getDoctor());
    }

    @Test
    void constructor2_GetterAndSetter_ValidInput() throws InputException {
        Contact mgen69 = new Contact("25 Av de la Mutualite","69000",
                "Lyon","04 56 78 90 12","contact@mgen.fr");
        Mutual mgenLyon = new Mutual("Mgen", mgen69, 0.80);
        Contact contact = new Contact("3, rue de la joie","54000","Nancy","00 00 00 00 00", "azer@azer.aze");
        Contact jeDup75 = new Contact("12 Rue de Paris", "75000", "Paris",
                "01 23 45 67 89", "jean.dupont@medecin.fr");
        Doctor jeDupParis = new Doctor("Jean", "Dupont", jeDup75,"12345678901");
        Customer customer = new Customer("Jojo", "Ducont",contact);
        assertEquals("Jojo", customer.getFirstName());
        assertEquals("Ducont", customer.getLastName());
        assertInstanceOf(Contact.class, customer.getContact());
    }

    @Test
    void constructor3_ValidInput() throws InputException {
        Customer customer = new Customer();
        assertInstanceOf(Customer.class, customer);
    }

    @ParameterizedTest(name="{0} le setter leve correctement l exception")
    @ValueSource(strings={"","       ", "1234", "/*<>"})
    void setterSocialSecurityId_InvalidInput(String socialSecurityId) throws InputException {
        assertThrows(InputException.class, () -> test.setSocialSecurityId(socialSecurityId));
    }

    @ParameterizedTest(name="{0} le setter leve correctement l exception")
    @ValueSource(strings={"","       ", "1234", "/*<>", "2026-01-01"})
    void setterDateOfBirth_InvalidInput(String dateOf) throws InputException {
        assertThrows(InputException.class, () -> test.setDateOfBirth(dateOf));
    }

    @Test
    public void setterDateOfBirth_invalidInput() throws InputException {
        InputException thrown = assertThrows(InputException.class, () -> test.setDateOfBirth("01-01-2026"));
        assertEquals("La date de naissance ne peut être postérieure à la date d'aujourd'hui", thrown.getMessage());
    }

    @Test
    public void setMutual_ValidInput() throws InputException {
        Mutual mutual = new Mutual();
        mutual.setName("Mutual");
        test.setMutual(mutual);
        assertEquals("Mutual", test.getMutual().getName());
    }

    @Test
    public void setAndGetCustomerPrescriptionList() throws InputException {
        Doctor doctor = new Doctor();
        doctor.setLastName("Jean");
        test.setLastName("Machine");
        Prescription prescription = new Prescription("01-09-2025","Jean","Machine");
        test.setCustomerPrescriptionsList(prescription);
        assertInstanceOf(Prescription.class, (Prescription)test.getCustomerPrescriptionsList().get(0));
    }

    @Test
    public void deleteCustomer_getCustomerByLastName_() throws InputException {
        Customer customerToDelete = new Customer();
        customerToDelete.setLastName("Papa");
        customerToDelete.deleteCustomer();
        InputException thrown = assertThrows(InputException.class, () -> Customer.getCustomerByLastName("Papa"));
        assertEquals("Ce client n'est pas enregistré",  thrown.getMessage());
    }

    @Test
    public void createCustomersMatrice() throws InputException {
        Customer.customersList.clear();
        Contact contact = new Contact("3 rue rue","54000","Ville","00 00 00 00 00","ju@ju.fr");
        Mutual mutual = new Mutual();
        mutual.setName("Mu");
        mutual.setContact(contact);
        Doctor doctor = new Doctor();
        doctor.setLastName("Jean");
        doctor.setContact(contact);
        Customer test = new Customer("Jul", "Ju", contact,"123456789012345","01-01-1995",mutual,doctor);
        assertEquals("00 00 00 00 00",Customer.createCustomersMatrice()[0][3]);
        assertEquals("Mu 54000",Customer.createCustomersMatrice()[0][4]);
        assertEquals("Jean 54000",Customer.createCustomersMatrice()[0][5]);
    }
}