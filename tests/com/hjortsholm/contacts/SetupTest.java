package com.hjortsholm.contacts;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SetupTest {

    @Test
    @DisplayName(value = "Test 1")
    public void myFirstTest() {



        assertEquals("foo", "foo");
    }

    @Test
    @DisplayName(value = "Test 2")
    public void mySeccondTest() {
        assertEquals(0, 1 - 1);
    }
}