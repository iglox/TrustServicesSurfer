package com.ids.trustservicesurfer;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class JsonProcessTest {

    @Test
    public void countryExtractorJson() {
        // Avg test string
        String test_str = "[{\"countryCode\": \"AT\",\"countryName\": \"Austria\"},{\"countryCode\": \"BE\",\"countryName\": \"Be -= lgium\"},{\"countryCode\": \"TT\",\"countryName\": \"TTT t ttt\"}]";
        Country[] results = JsonProcess.countryExtractorJson(test_str),
                expected = new Country[]{new Country("Austria","AT"), new Country("Be -= lgium","BE"), new Country("TTT t ttt","TT")};
        assertEquals("Not same length", results.length, expected.length);
        for (int i = 0; i < results.length; i++)
            assertEquals(expected[i].toString(), results[i].toString());
        // Empty string
        test_str = "[]";
        results = JsonProcess.countryExtractorJson(test_str);
        expected = new Country[]{};
        assertEquals("Not same length", results.length, expected.length);
        for (int i = 0; i < results.length; i++)
            assertEquals(expected[i].toString(), results[i].toString());
    }

    @Test
    public void serviceTypesExtractorJson() {
        //TODO
    }

    @Test
    public void serviceProvidersExtractorJson() {
        //TODO
    }

    @Test
    public void serviceStatesExtractorJson() {
        //TODO
    }

    @Test
    public void serviceExtractorJson() {
        //TODO
    }
}