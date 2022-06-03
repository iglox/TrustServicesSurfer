package com.ids.trustservicesurfer;

import com.ids.trustservicesurfer.Country;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class CountryTest {

    @Test
    public void getCode() {
        Country c1 = new Country("test", "tt"),
                c2 = new Country("test", "tT"),
                c3 = new Country("Test", "TT"),
                c4 = new Country("Test", "TY");
        assertEquals(c1.getCode(),"TT");
        assertEquals(c2.getCode(),"TT");
        assertEquals(c3.getCode(),"TT");
        assertEquals(c4.getCode(),"TY");
    }

    @Test
    public void getName() {
        Country c1 = new Country("test", "tt"),
                c2 = new Country("test", "tT"),
                c3 = new Country("Test", "TT"),
                c4 = new Country("TesT", "TY");
        assertEquals(c1.getName(),"test");
        assertEquals(c2.getName(),"test");
        assertEquals(c3.getName(),"Test");
        assertEquals(c4.getName(),"TesT");
    }

    @Test
    public void testToString() {
        Country c1 = new Country("test", "tt"),
                c2 = new Country("test", "tT"),
                c3 = new Country("Test", "TT"),
                c4 = new Country("TesT", "TY");
        assertEquals(c1.toString(),"TT : test");
        assertEquals(c2.toString(),"TT : test");
        assertEquals(c3.toString(),"TT : Test");
        assertEquals(c4.toString(),"TY : TesT");
    }

    @Test
    public void compareTo() {
        Country c1 = new Country("test", "tt"),
                c2 = new Country("test", "tT"),
                c3 = new Country("Test", "TT"),
                c4 = new Country("Test", "TY");
        assertEquals(c1.compareTo(c1),0);
        assertEquals(c1.compareTo(c2),0);
        assertEquals(c1.compareTo(c3),0);
        assertNotEquals(c1.compareTo(c4),0);
    }
}