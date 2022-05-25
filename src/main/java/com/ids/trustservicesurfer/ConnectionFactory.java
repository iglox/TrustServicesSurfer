package com.ids.trustservicesurfer;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ConnectionFactory {
    private final String type_url = "https://esignature.ec.europa.eu/efda/tl-browser/api/v1/search/find_by_type";
    private final String service_url = "https://esignature.ec.europa.eu/efda/tl-browser/api/v1/search/tsp_list";

    private String buildSearchCriteriaString(String[] countries, String[] types) {
        String search_criteria_string = "{";

        // Build countries section
        if (countries != null & countries.length > 0) {
            search_criteria_string = search_criteria_string.concat("\"countries\": [");
            for(int i = 0; i < countries.length; i++) {
                if (i > 0)
                    search_criteria_string = search_criteria_string.concat(", ");
                search_criteria_string = search_criteria_string.concat("\""+countries[i]+"\"");
            }
            search_criteria_string = search_criteria_string.concat("]");
        }

        // Build types section
        if (types != null & types.length > 0) {
            if (search_criteria_string.compareTo("{") != 0)
                search_criteria_string = search_criteria_string.concat(", ");
            search_criteria_string = search_criteria_string.concat("\"qServiceTypes\": [");
            for(int i = 0; i < types.length; i++) {
                if (i > 0)
                    search_criteria_string = search_criteria_string.concat(", ");
                search_criteria_string = search_criteria_string.concat("\""+types[i]+"\"");
            }
            search_criteria_string = search_criteria_string.concat("]");
        }
        System.out.println(search_criteria_string+"}");
        return search_criteria_string.concat("}");
    }



    public String getCountriesListJson() throws IOException {
        URL url = new URL("https://esignature.ec.europa.eu/efda/tl-browser/api/v1/search/countries_list");
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("GET");

        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");

        con.setDoOutput(true);

        int code = con.getResponseCode();
        if (code != 200) System.out.println(code);

        try(BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))){
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }

            return response.toString();
        }

    }

    // TODO
    public String getCompleteServicesListJson() throws IOException {
        URL url = new URL("https://esignature.ec.europa.eu/efda/tl-browser/api/v1/search/tsp_list");
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("GET");

        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");

        con.setDoOutput(true);

        int code = con.getResponseCode();
        if (code != 200) System.out.println(code);

        try(BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))){
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }

            return response.toString();
        }
    }

    // TODO
    public String getServicesListJson(String[] countries, String[] types) throws IOException {
        // TODO check input
    	
        URL url = new URL("https://esignature.ec.europa.eu/efda/tl-browser/api/v1/search/find_by_type");
        
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("POST");

        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");

        con.setDoOutput(true);

        String jsonInputString = buildSearchCriteriaString(countries, types);

        try(OutputStream os = con.getOutputStream()){
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int code = con.getResponseCode();
        System.out.println(code);
        
        try(BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))){
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            System.out.println(response.toString());
            return response.toString(); 
        }
    }
}
