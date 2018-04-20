package tests.models;

import com.hjortsholm.contacts.models.FieldType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName(value = "FieldType")
public class FieldTypeTest {

    private FieldType fieldType;

    @BeforeEach
    void beforeEach() {
        this.fieldType = FieldType.NUMBER;
    }

    @Test
    @DisplayName(value = "Get default name")
    void getDefaultName() {
        assertEquals("mobile", this.fieldType.getDefaultName());
    }

    @Test
    @DisplayName(value = "All types has correct indexes")
    void correctIndexing() {
        assertAll(() -> {
            for (int i = 0; i < FieldType.values().length; i++) {
                assertEquals(i, FieldType.values()[i].getIndex());
            }
        });
    }

    @Test
    @DisplayName(value = "ValueOf gets correct types")
    void correctReverseIndexing() {
        assertAll(() -> {
            for (int i = 0; i < FieldType.values().length; i++) {
                assertEquals(FieldType.values()[i], FieldType.valueOf(i));
            }
        });
    }
}