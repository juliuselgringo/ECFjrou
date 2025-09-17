package training.afpa.sparadrap.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import training.afpa.sparadrap.ExceptionTracking.InputException;
import training.afpa.sparadrap.model.Customer;
import training.afpa.sparadrap.model.Doctor;
import training.afpa.sparadrap.model.Prescription;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class PrescriptionTest {

    @Test
    public void toString_Test() throws InputException {
        Customer customer = new Customer();
        customer.setLastName("Dupont");
        Doctor doctor = new Doctor();
        doctor.setLastName("Ducoin");
        Prescription prescription = new Prescription("01-09-2025", "Ducoin","Dupont");
        assertEquals("Date: " + "01-09-2025" +
                "\nNom du medecin: " + "Ducoin" +
                "\nNom du client:  " + "Dupont" +
                "\nListe des médicament: ", prescription.toString());
    }

    @Test
    public void toStringForPdf_Test() throws InputException {
        Customer customer = new Customer();
        customer.setLastName("Dupont");
        Doctor doctor = new Doctor();
        doctor.setLastName("Ducoin");
        Prescription prescription = new Prescription("01-09-2025", "Ducoin","Dupont");
        assertEquals(" // " + "01-09-2025" +
                " / Medecin: " + "Ducoin" +
                " / Client:  " + "Dupont" +
                " / Médicaments: ", prescription.toStringForPdf());
    }

    @ParameterizedTest(name="{0} L exception se lève correctement")
    @ValueSource(strings = {"", "    ", "Dy"})
    public void setDoctorLastName_InvalidInput() throws NullPointerException {
        Prescription prescription = new Prescription();
        assertThrows(InputException.class, () -> prescription.setDoctorLastName(""));
    }

    @Test
    public void savePrescriptionAsPdf_ValidInput() throws InputException, IOException {
        Customer customer = new Customer();
        customer.setLastName("Dupont");
        Doctor doctor = new Doctor();
        doctor.setLastName("Ducoin");
        Prescription prescription = new Prescription("01-09-2025", "Ducoin","Dupont");
        prescription.savePrescriptionAsPdf();
        assertEquals("C:\\Users\\DEV01\\OneDrive - AFPA\\Documents\\ECFjrou\\Sparadrap\\src\\historic\\Dupont2025-09-01.pdf",
                prescription.getPathPdf());
    }


}