package com.hjortsholm.contacts.tests;

import com.hjortsholm.contacts.util.Resource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName(value = "Resource")
class ResourceTest {

    @Test
    @DisplayName(value = "Create non-existent file")
    public void fileCreationTest() {
        Resource resource = null;
        try {
            resource = new Resource("foo");
        } catch (Exception ex) {}

        assertEquals(resource,null);
    }
}