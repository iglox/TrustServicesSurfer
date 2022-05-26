package com.ids.trustservicesurfer;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class JsonProcess {
    protected JsonProcess(){}

    public static Country[] countryExtractorJson(String raw_json) {
        if (raw_json == null || raw_json.length() == 0)
            throw new IllegalArgumentException("Json is null or empty");

        JSONArray j_array = new JSONArray(raw_json);
        System.out.println("[!] : Loading countries");
        Country[] countries = new Country[j_array.length()];
        for (int i = 0; i < j_array.length(); i++) {
            System.out.println("[+] - Loading country " + i + " of " + (j_array.length() - 1));
            JSONObject tmp_obj = j_array.getJSONObject(i);
            countries[i] =  new Country(tmp_obj.getString("countryName"),
                    tmp_obj.getString("countryCode"));
        }
        Arrays.sort(countries);
        return countries;
    }

    public static ServiceType[] serviceTypesExtractorJson(String raw_json) {
        if (raw_json == null || raw_json.length() == 0)
            throw new IllegalArgumentException("Json is null or empty");

        JSONArray j_array = new JSONArray(raw_json);
        ArrayList<String> types = new ArrayList<String>();
        System.out.println("[!] : Loading types");
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

        ServiceType[] serviceTypes =  new ServiceType[types.size()];

        Collections.sort(types);
        for(int i = 0; i < types.size(); i++)
            serviceTypes[i] = new ServiceType(types.get(i));

        return serviceTypes;
    }
    
    public static String[] serviceExtractorJson ( String raw_json) {
    	if (raw_json == null || raw_json.length() == 0)
            throw new IllegalArgumentException("Json is null or empty");

        JSONArray j_array = new JSONArray(raw_json);
        ArrayList<String> services_tmp = new ArrayList<String>();
        System.out.println("[!] : Loading services");
        for (int i = 0; i < j_array.length(); i++) {
            System.out.println("[+] - Loading service " + i + " of " + (j_array.length() - 1));
            JSONObject tmp_obj = j_array.getJSONObject(i);
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
