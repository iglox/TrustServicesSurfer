package com.ids.trustservicesurfer;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

public class JsonProcess {
    protected JsonProcess(){}



    public static JSONArray loadJsonArray(String raw_json) {
        return new JSONArray(raw_json);
    }


    public static Country[] countryExtractorJson(JSONArray j_array) {
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
    public static Country[] countryExtractorJson(String raw_json) {
        // Check input params
        if (raw_json == null || raw_json.length() == 0)
            throw new IllegalArgumentException("Json is null or empty");
        // Convert raw json => array of json obj
        JSONArray j_array = new JSONArray(raw_json);
        return countryExtractorJson(j_array);
    }


    public static ServiceType[] serviceTypesExtractorJson(JSONArray j_array) {
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
    public static ServiceType[] serviceTypesExtractorJson(String raw_json) {
        // Check input params
        if (raw_json == null || raw_json.length() == 0)
            throw new IllegalArgumentException("Json is null or empty");
        // Convert raw json => array of json obj
        JSONArray j_array = new JSONArray(raw_json);
        return serviceTypesExtractorJson(j_array);
    }

    public static String[] serviceProvidersExtractorJson(JSONArray j_array) {

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
    public static String[] serviceProvidersExtractorJson(String raw_json) {
        // Check input params
        if (raw_json == null || raw_json.length() == 0)
            throw new IllegalArgumentException("Json is null or empty");

        // Convert raw json => array of json obj
        JSONArray j_array = new JSONArray(raw_json);

        return serviceProvidersExtractorJson(j_array);
    }
    public static String[] serviceStatesExtractorJson(JSONArray j_array) {
        // Prepare output tmp destination list
        ArrayList<String> tmp_states = new ArrayList<String>();
        System.out.println("[!] : Loading service states");
        // For each obj in array: load obj, extract its name and save it in the destination array
        for (int i = 0; i < j_array.length(); i++) {
            System.out.println("[+] - Loading type " + i + " of " + (j_array.length() - 1));
            JSONObject tmp_obj = j_array.getJSONObject(i);
            JSONArray tmp_obj_services = tmp_obj.getJSONArray("services");
            for (int j = 0; j < tmp_obj_services.length(); j++) {
                if (!tmp_states.contains(tmp_obj_services.getJSONObject(j).get("currentStatus").toString()))
                    tmp_states.add(tmp_obj_services.getJSONObject(j).get("currentStatus").toString());
            }
        }
        // Sort it
        Collections.sort(tmp_states);
        String[] states = new String[tmp_states.size()];
        for(int i = 0; i < states.length; i++)
            states[i] = tmp_states.get(i).substring(tmp_states.get(i).lastIndexOf('/') + 1);
        return states;
    }
    public static String[] serviceStatesExtractorJson(String raw_json) {
        // Check input params
        if (raw_json == null || raw_json.length() == 0)
            throw new IllegalArgumentException("Json is null or empty");

        // Convert raw json => array of json obj
        JSONArray j_array = new JSONArray(raw_json);
        return serviceStatesExtractorJson(j_array);
    }

    public static String[][] availableFilterExtractorJson(JSONArray j_array, String[] _selected_countries, String[] _selected_type, String[] _selected_state, String[] _selected_providers) {
        List<String> selected_countries = Arrays.asList(_selected_countries),
                selected_state = Arrays.asList(_selected_state),
                selected_type = Arrays.asList(_selected_type),
                selected_providers = Arrays.asList(_selected_providers);

        Set<String> available_countries = new HashSet<String>(),
                available_type = new HashSet<String>(),
                available_state = new HashSet<String>(),
                available_providers = new HashSet<String>();

        System.out.println("[!] Available filters update started");

        for (int i = 0; i < j_array.length(); i++) {
            JSONObject tmp_obj = j_array.getJSONObject(i);
            // Providers
            if (!selected_providers.isEmpty())
                if (!selected_providers.contains(tmp_obj.getString("name")))
                    continue;
            // Countries
            if (!selected_countries.isEmpty())
                if (!selected_countries.contains(tmp_obj.getString("countryCode")))
                    continue;

            // Cycle inner array services
            JSONArray tmp_sevices_list = tmp_obj.getJSONArray("services");
            for (int j = 0; j < tmp_sevices_list.length(); j++) {
                JSONObject inner_tmp_obj = tmp_sevices_list.getJSONObject(j);
                // States
                if (!selected_state.isEmpty())
                    if (!selected_state.contains(inner_tmp_obj.getString("currentStatus").substring(inner_tmp_obj.getString("currentStatus").lastIndexOf('/') + 1)))
                        continue;
                JSONArray inner_tmp_obj_types = inner_tmp_obj.getJSONArray("qServiceTypes");
                // Types
                for (int k = 0; k < inner_tmp_obj_types.length(); k++) {
                    if (!selected_type.isEmpty()) {
                        if (selected_type.contains(inner_tmp_obj_types.getString(k))) {
                            available_providers.add(tmp_obj.getString("name"));
                            available_countries.add(tmp_obj.getString("countryCode"));
                            available_state.add(inner_tmp_obj.getString("currentStatus").substring(inner_tmp_obj.getString("currentStatus").lastIndexOf('/') + 1));
                            available_type.add(inner_tmp_obj_types.getString(k));
                        }
                    } else {
                        available_providers.add(tmp_obj.getString("name"));
                        available_countries.add(tmp_obj.getString("countryCode"));
                        available_state.add(inner_tmp_obj.getString("currentStatus").substring(inner_tmp_obj.getString("currentStatus").lastIndexOf('/') + 1));
                        available_type.add(inner_tmp_obj_types.getString(k));
                    }
                }
            }
        }

        System.out.println("[!] Available filters update ended");
        return new String[][]{available_countries.toArray(new String[0]),
                            available_type.toArray(new String[0]),
                            available_state.toArray(new String[0]),
                            available_providers.toArray(new String[0])};
    }

    public static String[] serviceExtractorJson (String raw_json, String[] _providers, String[] _states) {
        // Check input params
        if (raw_json == null || raw_json.length() == 0)
            throw new IllegalArgumentException("Json is null or empty");
        if (_providers.length == 0 || _states.length == 0)
            throw new IllegalArgumentException("One or more arg is null");

        System.out.println("[!] Starting the search");
        // Convert raw json => array of json obj
        JSONArray j_array = new JSONArray(raw_json);
        return serviceExtractorJson(j_array, _providers, _states);
    }

    public static String[] serviceExtractorJson (JSONArray j_array, String[] _providers, String[] _states) {
        // Check input params
        if (_providers.length == 0 || _states.length == 0)
            throw new IllegalArgumentException("One or more arg is null");
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
                        JSONArray tmp_obj_array = new JSONArray(tmp_obj.get("services").toString());
                        for (int j = 0; j < tmp_obj_array.length(); j++) {
                            // tmp save service`s status and extract the status (last part)
                            String tmp_service_status = tmp_obj_array.getJSONObject(j).get("currentStatus").toString();
                            String tmp_service_status_extracted = tmp_service_status.substring(tmp_service_status.lastIndexOf('/') + 1);
                            if (tmp_service_status_extracted.compareTo(s) == 0) {
                                // Found it, just add it to the array list building the string
                                services_tmp.add("SERVICE PROVIDER: "+p+"  ::  SERVICE ID: "+tmp_obj_array.getJSONObject(j).get("serviceId").toString() + "  ::  " + "SERVICE NAME: " +
                                         tmp_obj_array.getJSONObject(j).get("serviceName").toString() + "::" + " SERVICE STATUS: "+s
                                );
                            }
                        }
                    }
                    break;
                }
            }
        }
        System.out.println("[!] Search ended");
        String[] services =  new String[services_tmp.size()];

        Collections.sort(services_tmp);
        for(int i = 0; i < services.length; i++)
        	services[i] = services_tmp.get(i);
        return services;
    }
}
