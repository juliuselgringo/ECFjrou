package training.afpa.sparadrap.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import training.afpa.sparadrap.ExceptionTracking.InputException;
import training.afpa.sparadrap.model.Contact;

import static org.junit.jupiter.api.Assertions.*;

class ContactTest {

    Contact contactTest = new Contact("3, rue de la peur","55000","Reims","99 99 99 99 99", "cvbn@cvbn.cvb");

    ContactTest() throws InputException {
    }

    @Test
    void constructor_GetterAndSetter_ValidInput() throws InputException {
        Contact contact = new Contact("3, rue de la joie","54000","Nancy","00 00 00 00 00", "azer@azer.aze");
        assertEquals("3, rue de la joie", contact.getAddress());
        assertEquals("54000",contact.getPostalCode());
        assertEquals("Nancy", contact.getTown());
        assertEquals("00 00 00 00 00", contact.getPhone());
        assertEquals("azer@azer.aze", contact.getEmail());
    }

    @ParameterizedTest(name="{0} le setter leve correctement l'exception")
    @ValueSource(strings={"","    ", "1234"})
    void setterAddress_InvalidInput(String address) throws InputException {
        assertThrows(InputException.class, () -> contactTest.setAddress(address));
    }

    @ParameterizedTest(name="{0} le setter leve correctement l'exception")
    @ValueSource(strings={"","    ", "</NrT", "00 15"})
    void setterPostalcode_InvalidInput(String postalCode) throws InputException {
        assertThrows(InputException.class, () -> contactTest.setPostalCode(postalCode));
    }

    @ParameterizedTest(name="{0} le setter leve correctement l'exception")
    @ValueSource(strings={"","    ", "</NrT", "nancy"})
    void setterTown_InvalidInput(String town) throws InputException {
        assertThrows(InputException.class, () -> contactTest.setTown(town));
    }

    @ParameterizedTest(name="{0} le setter leve correctement l'exception")
    @ValueSource(strings={"","    ", "</NrT", "nancy", "00 00", "88-88-88-88-88"})
    void setterPhone_InvalidInput(String phone) throws InputException {
        assertThrows(InputException.class, () -> contactTest.setPhone(phone));
    }

    @ParameterizedTest(name="{0} le setter leve correctement l'exception")
    @ValueSource(strings={"","    ", "</NrT", "nancy", "00 00", "88-88-88-88-88", "@azer", "azer.azer@"})
    void setterEmail_InvalidInput(String email) throws InputException {
        assertThrows(InputException.class, () -> contactTest.setEmail(email));
    }

}