package training.afpa.sparadrap.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import training.afpa.sparadrap.ExceptionTracking.InputException;
import training.afpa.sparadrap.model.Contact;
import training.afpa.sparadrap.model.Mutual;

import static org.junit.jupiter.api.Assertions.*;

class MutualTest {

    Contact contact = new Contact("3 rue machin","54000","Machin","00 00 00 00 00","ma@chin.fr");
    Mutual test = new Mutual("Machin",contact,0.4);

    MutualTest() throws InputException {
    }

    @Test
    public void constructor_GetterAndSetter_ValidInput() throws InputException {
        assertEquals("Machin", test.getName());
        assertEquals("3 rue machin", test.getContact().getAddress());
        assertEquals("54000", test.getContact().getPostalCode());
        assertEquals("Machin", test.getContact().getTown());
        assertEquals("00 00 00 00 00", test.getContact().getPhone());
        assertEquals("ma@chin.fr", test.getContact().getEmail());

    }

    @ParameterizedTest(name="{0} le setter lève correctemnt l exception")
    @ValueSource(strings={"","    ", "machin", "2000"})
    public void setterName_InvalidInput(String input) throws InputException {
        assertThrows(InputException.class, () -> test.setName(input));
    }

    @ParameterizedTest(name="{0} le setter lève correctemnt l exception")
    @ValueSource(doubles={ 2.00, 2000, -4})
    public void setter_InvalidInput(Double input) throws InputException {
        assertThrows(InputException.class, () -> test.setRate(input));
    }



}