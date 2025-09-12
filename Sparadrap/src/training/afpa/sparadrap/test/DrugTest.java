package training.afpa.sparadrap.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import training.afpa.sparadrap.ExceptionTracking.InputException;
import training.afpa.sparadrap.model.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

public class DrugTest {

    Drug drugTest = new Drug("Jojo", "Stomatologie",10.00,"10-10-2020",10,true);

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public DrugTest() throws InputException {
    }


    @Test
    void constructor1_GetterAndSetter_ValidInput() throws InputException {
        Drug drug = new Drug("Jajo", "Stomatologie",10.00,"10-10-2020",10,true);
        assertEquals("Jajo", drug.getName());
        assertEquals("Stomatologie", drug.getCategoryName());
        assertEquals(10.00, drug.getPrice());
        assertEquals("10-10-2020", drug.getProductionDate().format(formatter));
        assertEquals(10, drug.getQuantity());
        assertEquals(true, drug.isUnderPrescription());
    }

    @ParameterizedTest(name="{0} le setter leve correctement l exception")
    @ValueSource(strings={"","       ", "1234", "/*<>", "2026-01-01"})
    void setterProductionDate_InvalidInput(String dateOf){
        assertThrows(InputException.class, () -> drugTest.setProductionDate(dateOf));
    }

    @ParameterizedTest(name="{0} le setter leve correctement l exception")
    @ValueSource(ints={-2,-100,-5/2})
    void setterQuantity_InvalidInput(int quantity) throws InputException {
        assertThrows(InputException.class, () -> drugTest.setQuantity(quantity));
    }


}