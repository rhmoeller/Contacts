package tests.database;

import com.hjortsholm.contacts.database.Database;
import com.hjortsholm.contacts.database.Query;
import com.hjortsholm.contacts.database.TableField;
import com.hjortsholm.contacts.models.DataModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


@DisplayName(value = "Database")
public class DatabaseTest {

    @BeforeAll
    static void beforeAll() {
        Database.configure("data/contacts.db");
    }

    @Test
    @DisplayName(value = "Create a new table")
    void createTable() {
        Database.createTable(TestDataModel.class);

        assertTrue(Database.verifyTable(TestDataModel.class));
        Database.dropTable(TestDataModel.class);
    }

    @Test
    @DisplayName(value = "Drop a table")
    void dropTable() {
        Database.createTable(TestDataModel.class);
        Database.dropTable(TestDataModel.class);
        assertFalse(Database.verifyTable(TestDataModel.class));
    }

    @Test
    @DisplayName(value = "Insert into a table")
    void insertIntoTable() {
        Database.createTable(TestDataModel.class);
        TestDataModel dataModel = new TestDataModel("bar");
        Database.insert(dataModel);

        assertEquals(dataModel.foo,
                Database.get(new Query().select().from(TestDataModel.class)).first().getColumn("foo")
        );
        Database.dropTable(TestDataModel.class);
    }


    @TableField(name = "foo", type = "VARCHAR", primaryKey = true)
    private class TestDataModel extends DataModel {

        private String foo;

        public TestDataModel(String foo) {
            this.foo = foo;
        }


        @Override
        public Object[] getValues() {
            return new Object[]{foo};
        }
    }
}