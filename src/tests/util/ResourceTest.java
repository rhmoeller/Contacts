package tests.util;

import com.hjortsholm.contacts.util.Resource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@DisplayName(value = "Resource")
class ResourceTest {

    @Test
    @DisplayName(value = "Create a resource from a non-existent file")
    public void nullFileCreationTest() {
        Class exception = null;
        try {
            new Resource("foo");
        } catch (NullPointerException ex) {
            exception = ex.getClass();
        }
        assertEquals(NullPointerException.class, exception);
    }

    @Test
    @DisplayName(value = "Create a resource from a existing file")
    public void fileCreationTest() {
        Resource resource = null;
        try {
            resource = new Resource("gui/style/Window.css");
        } catch (NullPointerException ex) {
            fail(ex.getMessage());
        }
        assertEquals(false, resource.exists());
    }
}