package com.ids.trustservicesurfer;

import org.json.JSONObject;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ConnectionFactory {
    private final String type_url = "https://esignature.ec.europa.eu/efda/tl-browser/api/v1/search/find_by_type";
    private final String service_url = "https://esignature.ec.europa.eu/efda/tl-browser/api/v1/search/tsp_list";
    private final String countries_list_url = "https://esignature.ec.europa.eu/efda/tl-browser/api/v1/search/countries_list";

    protected static String buildSearchCriteriaString(String[] _countries, String[] _types) {
        JSONObject tmp_json_obj = new JSONObject();
        tmp_json_obj.put("countries", _countries);
        tmp_json_obj.put("qServiceTypes", _types);

        return tmp_json_obj.toString();
    }

    public String getCountriesListJson() throws IOException {
        URL url = new URL(countries_list_url);
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);

        if (con.getResponseCode() != 200) System.out.println("[!] HTTP Status code: " + con.getResponseCode());

        try(BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))){
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null)
                response.append(responseLine.trim());
            return response.toString();
        } catch (Exception e) {
            throw(e);
        }

    }

    public String getCompleteServicesListJson() throws IOException {
        URL url = new URL("https://esignature.ec.europa.eu/efda/tl-browser/api/v1/search/tsp_list");
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);

        if (con.getResponseCode() != 200) System.out.println("[!] HTTP Status code: " + con.getResponseCode());

        try(BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))){
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null)
                response.append(responseLine.trim());
            return response.toString();
        } catch (Exception e) {
            throw(e);
        }
    }

    public String getServicesListJson(String[] _countries, String[] _types) throws IOException {
        if (_countries == null || _countries.length == 0 || _types == null || _types.length == 0)
            throw new IllegalArgumentException("One or more args is null or empty");
    	
        URL url = new URL("https://esignature.ec.europa.eu/efda/tl-browser/api/v1/search/find_by_type");
        
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);

        String json_input_string = buildSearchCriteriaString(_countries, _types);

        try(OutputStream os = con.getOutputStream()){
            byte[] input = json_input_string.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }
        
        if (con.getResponseCode() != 200) System.out.println("[!] HTTP Status code: " + con.getResponseCode());
        
        try(BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))){
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            
            while ((responseLine = br.readLine()) != null)
                response.append(responseLine.trim());
            return response.toString(); 
        } catch (Exception e) {
            throw(e);
        }
    }
}
