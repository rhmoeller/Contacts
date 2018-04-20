package tests.models;

import com.hjortsholm.contacts.models.DataModel;
import com.hjortsholm.contacts.models.Field;
import com.hjortsholm.contacts.models.FieldType;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;


@DisplayName(value = "DataModel")
public class DataModelTest {

    private Field field;
    private DataModel dataModel;
    private int dataValue = 100;

    @BeforeAll
    static void beforeAll() {

    }

    @BeforeEach
    void beforeEach() {
        this.dataValue = 100;
        this.field = new Field(this.dataValue,100,FieldType.NAME,"first","Robert");
        this.dataModel = this.field;
    }

    @Test
    @DisplayName(value = "Get data from data model")
    void getData() {
        Object[] values = this.dataModel.getValues();
        assertAll(() -> {
            assertEquals(this.field.getId(),values[0]);
            assertEquals(this.field.getContact(),values[1]);
            assertEquals(this.field.getType().getIndex(),values[2]);
            assertEquals(this.field.getName(),values[3]);
            assertEquals(this.field.getValue(),values[4]);
        });
    }
}