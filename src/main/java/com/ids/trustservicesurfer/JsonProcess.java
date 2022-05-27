package com.ids.trustservicesurfer;

import javafx.print.Collation;
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
    public static String[] serviceStatesExtractorJson(String raw_json) {
        // Check input params
        if (raw_json == null || raw_json.length() == 0)
            throw new IllegalArgumentException("Json is null or empty");

        // Convert raw json => array of json obj
        JSONArray j_array = new JSONArray(raw_json);
        // Prepare output tmp destination list
        ArrayList<String> tmp_states = new ArrayList<String>();
        System.out.println("[!] : Loading service states");
        // For each obj in array: load obj, extract its name and save it in the destination array
        for (int i = 0; i < j_array.length(); i++) {
            System.out.println("[+] - Loading type " + i + " of " + (j_array.length() - 1));
            JSONObject tmp_obj = j_array.getJSONObject(i);
            JSONArray tmp_obj_services = (JSONArray) tmp_obj.get("services");
            for (int j = 0; j < tmp_obj_services.length(); j++) {
                if (!tmp_states.contains(tmp_obj_services.getJSONObject(j).get("currentStatus").toString()))
                    tmp_states.add(tmp_obj_services.getJSONObject(j).get("currentStatus").toString());
            }
        }
        // Sort it

        // TODO: CLEAN IT
        Collections.sort(tmp_states);
        String[] states = new String[tmp_states.size()];
        for(int i = 0; i < states.length; i++)
            states[i] = tmp_states.get(i);
        return states;
    }

    //TODO: add comments
    //TODO: not tested
    public static String[] serviceExtractorJson (String raw_json, String[] _providers, String[] _states) {
        // Check input params
    	if (raw_json == null || raw_json.length() == 0)
            throw new IllegalArgumentException("Json is null or empty");
        if (_providers == null || _states == null)
            throw new IllegalArgumentException("One or more arg is null");

        // Convert raw json => array of json obj
        JSONArray j_array = new JSONArray(raw_json);
        ArrayList<String> services_tmp = new ArrayList<String>();
        // TODO create Service OBJ ??
        for (int i = 0; i < j_array.length(); i++) {
            JSONObject tmp_obj = j_array.getJSONObject(i);
            // Check if provider matches
            for(String p : _providers) {
                if (p.compareTo(tmp_obj.get("name").toString()) == 0) {
                    // Check if state matches
                    for (String s : _states) {
                        // For each service provided by the matched service provider
                        JSONArray tmp_obj_array = new JSONArray(tmp_obj.get("services"));
                        for (int j = 0; j < tmp_obj_array.length(); j++) {
                            // tmp save service`s status and extract the status (last part)
                            String tmp_service_status = tmp_obj_array.getJSONObject(j).get("currentStatus").toString();
                            String tmp_service_status_extracted = tmp_service_status.substring(tmp_service_status.lastIndexOf('/'));
                            if (tmp_service_status_extracted.compareTo(s) == 0) {
                                // Found it, just add it to the array list building the string
                                services_tmp.add(tmp_obj_array.getJSONObject(j).get("serviceId").toString() + "::" +
                                        p + "::" + tmp_obj_array.getJSONObject(j).get("serviceName").toString() + "::" + s
                                );
                            }
                        }
                    }
                    break;
                }
            }
        }

       String[] services =  new String[services_tmp.size()];

        Collections.sort(services_tmp);
        for(int i = 0; i < services.length; i++)
        	services[i] = services_tmp.get(i);
        return services;
    }
}
