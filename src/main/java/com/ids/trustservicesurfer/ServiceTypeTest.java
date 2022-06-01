package com.ids.trustservicesurfer;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotEquals;

public class ServiceTypeTest {

    @Test
    public void getType() {
        ServiceType st1 = new ServiceType("Tsss"),
                st2 = new ServiceType("Tsss"),
                st3 = new ServiceType("TSSS");
        assertEquals(st1.toString(), st2.toString());
        assertNotEquals(st1.toString(),st3.toString());
        assertEquals(st1.getType(), "Tsss");
        assertEquals(st3.toString(), "TSSS");
    }

    @Test
    public void testToString() {
        ServiceType st1 = new ServiceType("Tsss");
        assertEquals(st1.toString(), "Tsss");
    }

    @Test
    public void compareTo() {
        ServiceType st1 = new ServiceType("Tsss"),
                st2 = new ServiceType("Tsss"),
                st3 = new ServiceType("TSSS");
        assertEquals(st1.compareTo(st1),0);
        assertEquals(st1.compareTo(st2), 0);
        assertNotEquals(st1.compareTo(st3), 0);
    }
}