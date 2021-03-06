package com.ids.trustservicesurfer;

import org.json.JSONArray;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class JsonProcessTest {

    @Test
    public void countryExtractorJson() {
        // Avg test string
        String test_str = "[{\"countryCode\": \"AT\",\"countryName\": \"Austria\"},{\"countryCode\": \"BE\",\"countryName\": \"Be -= lgium\"},{\"countryCode\": \"TT\",\"countryName\": \"TTT t ttt\"}]";
        Country[] results = JsonProcess.countryExtractorJson(new JSONArray(test_str)),
                expected = new Country[]{new Country("Austria","AT"), new Country("Be -= lgium","BE"), new Country("TTT t ttt","TT")};
        assertEquals("Not same length", results.length, expected.length);
        Arrays.sort(results);
        Arrays.sort(expected);
        for (int i = 0; i < results.length; i++)
            assertEquals(expected[i].toString(), results[i].toString());
        // Empty string
        test_str = "[]";
        results = JsonProcess.countryExtractorJson(new JSONArray(test_str));
        assertEquals(0, results.length);
    }

    @Test
    public void serviceTypesExtractorJson() {
        String test_str = "[\n" +
                "  {\n" +
                "    \"countryCode\": \"string\",\n" +
                "    \"name\": \"string\",\n" +
                "    \"qServiceTypes\": [\n" +
                "      \"type1\",\n" +
                "      \"type2\",\n" +
                "      \"type2\"\n" +
                "    ],\n" +
                "    \"services\": [],\n" +
                "    \"trustmark\": \"string\",\n" +
                "    \"tspId\": 0\n" +
                "  }\n" +
                "]";
        ServiceType[] results = JsonProcess.serviceTypesExtractorJson(new JSONArray(test_str)),
                expected = new ServiceType[]{new ServiceType("type1"), new ServiceType("type2")};
        assertEquals("Not same length", results.length, expected.length);
        Arrays.sort(results);
        Arrays.sort(expected);
        for (int i = 0; i < results.length; i++)
            assertEquals(expected[i].toString(), results[i].toString());
        // Empty string .1
        test_str = "[]";
        results = JsonProcess.serviceTypesExtractorJson(new JSONArray(test_str));
        assertEquals(0, results.length);
        // Empty string .2
        test_str = "[\n" +
                "  {\n" +
                "    \"countryCode\": \"string\",\n" +
                "    \"name\": \"string\",\n" +
                "    \"qServiceTypes\": [],\n" +
                "    \"services\": [],\n" +
                "    \"trustmark\": \"string\",\n" +
                "    \"tspId\": 0\n" +
                "  }\n" +
                "]";
        results = JsonProcess.serviceTypesExtractorJson(new JSONArray(test_str));
        assertEquals(0, results.length);
    }

    @Test
    public void serviceProvidersExtractorJson() {
        String test_str = "[\n" +
                "  {\n" +
                "    \"countryCode\": \"string\",\n" +
                "    \"name\": \"test1\",\n" +
                "    \"qServiceTypes\": [],\n" +
                "    \"services\": [],\n" +
                "    \"trustmark\": \"string\",\n" +
                "    \"tspId\": 0\n" +
                "  },\n" +
                "  {\n" +
                "    \"countryCode\": \"string\",\n" +
                "    \"name\": \"test2\",\n" +
                "    \"qServiceTypes\": [],\n" +
                "    \"services\": [],\n" +
                "    \"trustmark\": \"string\",\n" +
                "    \"tspId\": 0\n" +
                "  },\n" +
                "  {\n" +
                "    \"countryCode\": \"string\",\n" +
                "    \"name\": \"test3\",\n" +
                "    \"qServiceTypes\": [],\n" +
                "    \"services\": [],\n" +
                "    \"trustmark\": \"string\",\n" +
                "    \"tspId\": 0\n" +
                "  }\n" +
                "]";
        String[] results = JsonProcess.serviceProvidersExtractorJson(new JSONArray(test_str)),
                expected = new String[]{"test1","test2", "test3"};
        assertEquals("Not same length", results.length, expected.length);
        Arrays.sort(results);
        Arrays.sort(expected);
        assertArrayEquals(expected, results);
        // Empty string
        test_str = "[]";
        results = JsonProcess.serviceProvidersExtractorJson(new JSONArray(test_str));
        assertEquals(results.length, 0);
        assertArrayEquals(new String[]{}, results);
    }

    @Test
    public void serviceStatesExtractorJson() {
        // Avg string test
        String test_str = "[\n" +
                "  {\n" +
                "    \"countryCode\": \"string\",\n" +
                "    \"name\": \"test1\",\n" +
                "    \"qServiceTypes\": [],\n" +
                "    \"services\": [\n" +
                "      {\n" +
                "        \"countryCode\": \"string\",\n" +
                "        \"currentStatus\": \"STATE1\",\n" +
                "        \"qServiceTypes\": [\n" +
                "          \"string\"\n" +
                "        ],\n" +
                "        \"serviceId\": 0,\n" +
                "        \"serviceName\": \"string\",\n" +
                "        \"tob\": \"string\",\n" +
                "        \"tspId\": 0,\n" +
                "        \"type\": \"string\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"countryCode\": \"string\",\n" +
                "        \"currentStatus\": \"STATE1\",\n" +
                "        \"qServiceTypes\": [\n" +
                "          \"string\"\n" +
                "        ],\n" +
                "        \"serviceId\": 0,\n" +
                "        \"serviceName\": \"string\",\n" +
                "        \"tob\": \"string\",\n" +
                "        \"tspId\": 0,\n" +
                "        \"type\": \"string\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"countryCode\": \"string\",\n" +
                "        \"currentStatus\": \"STATE2\",\n" +
                "        \"qServiceTypes\": [\n" +
                "          \"string\"\n" +
                "        ],\n" +
                "        \"serviceId\": 0,\n" +
                "        \"serviceName\": \"string\",\n" +
                "        \"tob\": \"string\",\n" +
                "        \"tspId\": 0,\n" +
                "        \"type\": \"string\"\n" +
                "      }\n" +
                "    ],\n" +
                "    \"trustmark\": \"string\",\n" +
                "    \"tspId\": 0\n" +
                "  },\n" +
                "  {\n" +
                "    \"countryCode\": \"string\",\n" +
                "    \"name\": \"test2\",\n" +
                "    \"qServiceTypes\": [],\n" +
                "    \"services\": [],\n" +
                "    \"trustmark\": \"string\",\n" +
                "    \"tspId\": 0\n" +
                "  }\n" +
                "]";
        String[] results = JsonProcess.serviceStatesExtractorJson(new JSONArray(test_str)),
                expected = new String[]{"STATE1","STATE2"};
        Arrays.sort(results);
        Arrays.sort(expected);
        assertEquals("Not same length", expected.length, results.length);
        assertArrayEquals(expected, results);
        // Empty string test
        test_str = "[]";
        results = JsonProcess.serviceStatesExtractorJson(new JSONArray(test_str));
        assertEquals("Not same length", 0, results.length);
    }

    @Test
    public void serviceExtractorJson() {
        // Avg string test
        String test_str = "[\n" +
                "  {\n" +
                "    \"countryCode\": \"IT\",\n" +
                "    \"name\": \"PROVIDER1\",\n" +
                "    \"qServiceTypes\": [],\n" +
                "    \"services\": [\n" +
                "      {\n" +
                "        \"countryCode\": \"string\",\n" +
                "        \"currentStatus\": \"STATE1\",\n" +
                "        \"qServiceTypes\": [\n" +
                "          \"TYPE1\"\n" +
                "        ],\n" +
                "        \"serviceId\": 0,\n" +
                "        \"serviceName\": \"string\",\n" +
                "        \"tob\": \"string\",\n" +
                "        \"tspId\": 0,\n" +
                "        \"type\": \"string\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"countryCode\": \"string\",\n" +
                "        \"currentStatus\": \"STATE1\",\n" +
                "        \"qServiceTypes\": [\n" +
                "          \"TYPE1\",\n" +
                "          \"TYPE2\"\n" +
                "        ],\n" +
                "        \"serviceId\": 0,\n" +
                "        \"serviceName\": \"string\",\n" +
                "        \"tob\": \"string\",\n" +
                "        \"tspId\": 0,\n" +
                "        \"type\": \"string\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"countryCode\": \"string\",\n" +
                "        \"currentStatus\": \"STATE2\",\n" +
                "        \"qServiceTypes\": [\n" +
                "          \"TYPE1\"\n" +
                "        ],\n" +
                "        \"serviceId\": 0,\n" +
                "        \"serviceName\": \"string\",\n" +
                "        \"tob\": \"string\",\n" +
                "        \"tspId\": 0,\n" +
                "        \"type\": \"string\"\n" +
                "      }\n" +
                "    ],\n" +
                "    \"trustmark\": \"string\",\n" +
                "    \"tspId\": 0\n" +
                "  },\n" +
                "  {\n" +
                "    \"countryCode\": \"string\",\n" +
                "    \"name\": \"PROVIDER2\",\n" +
                "    \"qServiceTypes\": [],\n" +
                "    \"services\": [],\n" +
                "    \"trustmark\": \"string\",\n" +
                "    \"tspId\": 0\n" +
                "  }\n" +
                "]";
        String[] results = JsonProcess.serviceExtractorJson(new JSONArray(test_str), new String[]{"TYPE1"}, new String[]{"STATE1"}, new String[]{"PROVIDER1"}),
                expected = new String[]{"SERVICE PROVIDER: PROVIDER1  ::  SERVICE ID: 0  ::  SERVICE NAME: string:: SERVICE STATUS: STATE1",
                        "SERVICE PROVIDER: PROVIDER1  ::  SERVICE ID: 0  ::  SERVICE NAME: string:: SERVICE STATUS: STATE1"};
        Arrays.sort(results);
        Arrays.sort(expected);
        assertEquals("Not same length", expected.length, results.length);
        assertArrayEquals(expected, results);
        // search .2
        results = JsonProcess.serviceExtractorJson(new JSONArray(test_str), new String[]{"TYPE1"}, new String[]{"STATE1"},new String[]{"PROVIDER1", "PROVIDER2"});
        assertEquals(2, results.length);
        // No results research
        results = JsonProcess.serviceExtractorJson(new JSONArray(test_str), new String[]{"TYPE2"}, new String[]{"STATE1"}, new String[]{"PROVIDER3"});
        assertEquals(0, results.length);
        // Empty string test
        test_str = "[]";
        results = JsonProcess.serviceExtractorJson(new JSONArray(test_str), new String[]{"TYPE2"}, new String[]{"STATE1"}, new String[]{"PROVIDER1"});
        assertEquals(0, results.length);
    }


    @Test
    public void availableFilterExtractorJson() {
        // Avg string test
        String test_str = "[\n" +
                "  {\n" +
                "    \"countryCode\": \"IT\",\n" +
                "    \"name\": \"PROVIDER1\",\n" +
                "    \"qServiceTypes\": [],\n" +
                "    \"services\": [\n" +
                "      {\n" +
                "        \"countryCode\": \"string\",\n" +
                "        \"currentStatus\": \"STATE1\",\n" +
                "        \"qServiceTypes\": [\n" +
                "          \"TYPE1\",\n" +
                "          \"TYPE2\"\n" +
                "        ],\n" +
                "        \"serviceId\": 0,\n" +
                "        \"serviceName\": \"string\",\n" +
                "        \"tob\": \"string\",\n" +
                "        \"tspId\": 0,\n" +
                "        \"type\": \"string\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"countryCode\": \"string\",\n" +
                "        \"currentStatus\": \"STATE1\",\n" +
                "        \"qServiceTypes\": [\n" +
                "          \"TYPE1\",\n" +
                "          \"TYPE2\"\n" +
                "        ],\n" +
                "        \"serviceId\": 0,\n" +
                "        \"serviceName\": \"string\",\n" +
                "        \"tob\": \"string\",\n" +
                "        \"tspId\": 0,\n" +
                "        \"type\": \"string\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"countryCode\": \"string\",\n" +
                "        \"currentStatus\": \"STATE2\",\n" +
                "        \"qServiceTypes\": [\n" +
                "          \"TYPE1\",\n" +
                "          \"TYPE2\"\n" +
                "        ],\n" +
                "        \"serviceId\": 0,\n" +
                "        \"serviceName\": \"string\",\n" +
                "        \"tob\": \"string\",\n" +
                "        \"tspId\": 0,\n" +
                "        \"type\": \"string\"\n" +
                "      }\n" +
                "    ],\n" +
                "    \"trustmark\": \"string\",\n" +
                "    \"tspId\": 0\n" +
                "  },\n" +
                "  {\n" +
                "    \"countryCode\": \"IT\",\n" +
                "    \"name\": \"PROVIDER2\",\n" +
                "    \"qServiceTypes\": [],\n" +
                "    \"services\": [],\n" +
                "    \"trustmark\": \"string\",\n" +
                "    \"tspId\": 0\n" +
                "  }\n" +
                "]";
        JSONArray j_arrray = new JSONArray(new JSONArray(test_str));
        String[][] results = JsonProcess.availableFilterExtractorJson(j_arrray,
                new String[]{"IT"},
                new String[]{"TYPE1"},
                new String[]{"STATE2"},
                new String[]{"PROVIDER1"}),
                expected = new String[][]{new String[]{"IT"},new String[]{"TYPE2","TYPE1"},new String[]{"STATE2"},new String[]{"PROVIDER1"}};

        for (int i =0; i < 4; i++) {
            assertEquals("Not same length", expected[i].length, results[i].length);
        }
        // search .2
        results = JsonProcess.availableFilterExtractorJson(j_arrray,
                new String[]{"IT"},
                new String[]{"TYPE1"},
                new String[]{"STATE1"},
                new String[]{"PROVIDER1"});
        expected = new String[][]{new String[]{"IT"},new String[]{"TYPE2","TYPE1"},new String[]{"STATE1"},new String[]{"PROVIDER1"}};
        for (int i =0; i < 4; i++) {
            assertEquals("Not same length", expected[i].length, results[i].length);
            assertArrayEquals(expected[i], results[i]);
        }
        // No results research
        results = JsonProcess.availableFilterExtractorJson(j_arrray,
                new String[]{"IT"},
                new String[]{"TYPE1"},
                new String[]{"STATE1"},
                new String[]{"PROVIDER2"});
        for (int i =0; i < 4; i++)
            assertEquals(0, results[i].length);
        //Empty string test
        results = JsonProcess.availableFilterExtractorJson(new JSONArray("[]"), new String[]{"IT"},new String[]{"TYPE1"},new String[]{"STATE2"},new String[]{"PROVIDER1"});
        for (int i =0; i < 4; i++)
            assertEquals(0, results[i].length);
    }

}