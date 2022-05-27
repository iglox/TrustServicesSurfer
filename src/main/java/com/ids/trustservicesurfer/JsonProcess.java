package com.ids.trustservicesurfer;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class JsonProcess {
    protected JsonProcess(){}

    public static Country[] countryExtractorJson(String raw_json) {
        // Check input params
        if (raw_json == null || raw_json.length() == 0)
            throw new IllegalArgumentException("Json is null or empty");

        // Convert raw json => array of json obj
        JSONArray j_array = new JSONArray(raw_json);
        System.out.println("[!] : Loading countries");
        // Prepare output destination array
        Country[] countries = new Country[j_array.length()];
        // For each obj in array: create Country obj and copy it in it
        for (int i = 0; i < j_array.length(); i++) {
            System.out.println("[+] - Loading country " + i + " of " + (j_array.length() - 1));
            JSONObject tmp_obj = j_array.getJSONObject(i);
            countries[i] =  new Country(tmp_obj.getString("countryName"),
                    tmp_obj.getString("countryCode"));
        }
        // Sort it
        Arrays.sort(countries);
        return countries;
    }

    public static ServiceType[] serviceTypesExtractorJson(String raw_json) {
        // Check input params
        if (raw_json == null || raw_json.length() == 0)
            throw new IllegalArgumentException("Json is null or empty");

        // Convert raw json => array of json obj
        JSONArray j_array = new JSONArray(raw_json);
        // Prepare output tmp destination list
        ArrayList<String> types = new ArrayList<String>();
        System.out.println("[!] : Loading types");
        // For each obj in array: load obj and copy its services` name in a list (which works
        // like a set, unique items)
        for (int i = 0; i < j_array.length(); i++) {
            System.out.println("[+] - Loading type " + i + " of " + (j_array.length() - 1));
            JSONObject tmp_obj = j_array.getJSONObject(i);
            JSONArray tmp_obj_types = (JSONArray) tmp_obj.get("qServiceTypes");
            for (int j = 0; j < tmp_obj_types.length(); j++) {
                String service_type = tmp_obj_types.getString(j);
                if (!types.contains(service_type))
                    types.add(service_type);
            }
        }
        // Prepare real destination array
        ServiceType[] serviceTypes =  new ServiceType[types.size()];
        // Sort it and copy its element in the array
        Collections.sort(types);
        for(int i = 0; i < types.size(); i++)
            serviceTypes[i] = new ServiceType(types.get(i));

        return serviceTypes;
    }
    public static String[] serviceProvidersExtractorJson(String raw_json) {
        // Check input params
        if (raw_json == null || raw_json.length() == 0)
            throw new IllegalArgumentException("Json is null or empty");

        // Convert raw json => array of json obj
        JSONArray j_array = new JSONArray(raw_json);
        // Prepare output tmp destination list
        String[] providers = new String[j_array.length()];
        System.out.println("[!] : Loading service providers");
        // For each obj in array: load obj, extract its name and save it in the destination array
        for (int i = 0; i < j_array.length(); i++) {
            System.out.println("[+] - Loading provider " + i + " of " + (j_array.length() - 1));
            providers[i] = j_array.getJSONObject(i).get("name").toString();
        }
        // Sort it
        Arrays.sort(providers);
        return providers;
    }

    //TODO: add comments
    public static String[] serviceExtractorJson (String raw_json) {
    	if (raw_json == null || raw_json.length() == 0)
            throw new IllegalArgumentException("Json is null or empty");

        JSONArray j_array = new JSONArray(raw_json);
        ArrayList<String> services_tmp = new ArrayList<String>();
        System.out.println("[!] : Loading services");
        for (int i = 0; i < j_array.length(); i++) {
            System.out.println("[+] - Loading service " + i + " of " + (j_array.length() - 1));
            JSONObject tmp_obj = j_array.getJSONObject(i);
            JSONArray tmp_arr_obj =  new JSONArray(tmp_obj.get("services"));
            String name = tmp_obj.getString("name");
            services_tmp.add(name);
        }

       String[] services =  new String[services_tmp.size()];

        Collections.sort(services_tmp);
        for(int i = 0; i < services.length; i++)
        	services[i] = services_tmp.get(i);
        return services;
    }
}
