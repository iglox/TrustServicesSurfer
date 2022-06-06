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
                if (!selected_providers.contains(tmp_obj.getString("name"))) // Check if provider`s name is correct
                    continue;
            // Countries
            if (!selected_countries.isEmpty())
                if (!selected_countries.contains(tmp_obj.getString("countryCode"))) // Check if countryCode is correct
                    continue;

            // Cycle inner array services
            JSONArray tmp_sevices_list = tmp_obj.getJSONArray("services");
            for (int j = 0; j < tmp_sevices_list.length(); j++) {
                JSONObject inner_tmp_obj = tmp_sevices_list.getJSONObject(j);
                // States
                if (!selected_state.isEmpty())
                    if (!selected_state.contains(inner_tmp_obj.getString("currentStatus").substring(inner_tmp_obj.getString("currentStatus").lastIndexOf('/') + 1))) // Check if currentStatus is correct
                        continue;
                JSONArray inner_tmp_obj_types = inner_tmp_obj.getJSONArray("qServiceTypes");
                // Types
                if (!selected_type.isEmpty()) {
                    String[] tmp_types = new String[inner_tmp_obj_types.length()];
                    boolean found = false;
                    for (int k = 0; k < inner_tmp_obj_types.length(); k++) {
                        if (selected_type.contains(inner_tmp_obj_types.getString(k))) found = true;
                        tmp_types[k] = inner_tmp_obj_types.getString(k);
                    }
                    if (found) {
                        available_providers.add(tmp_obj.getString("name"));
                        available_countries.add(tmp_obj.getString("countryCode"));
                        available_state.add(inner_tmp_obj.getString("currentStatus").substring(inner_tmp_obj.getString("currentStatus").lastIndexOf('/') + 1));
                        available_type.addAll(Arrays.asList(tmp_types));
                    }
                } else {
                    for (int k = 0; k < inner_tmp_obj_types.length(); k++) // No serviceType selected => add all
                        available_type.add(inner_tmp_obj_types.getString(k));
                    available_providers.add(tmp_obj.getString("name"));
                    available_countries.add(tmp_obj.getString("countryCode"));
                    available_state.add(inner_tmp_obj.getString("currentStatus").substring(inner_tmp_obj.getString("currentStatus").lastIndexOf('/') + 1));
                }
            }
        }

        System.out.println("[!] Available filters update ended");
        return new String[][]{available_countries.toArray(new String[0]),
                            available_type.toArray(new String[0]),
                            available_state.toArray(new String[0]),
                            available_providers.toArray(new String[0])};
    }


    public static String[] serviceExtractorJson (JSONArray j_array, String[] _types, String[] _states, String[] _providers) {
        // Check input params
        if (_providers.length == 0 || _states.length == 0)
            throw new IllegalArgumentException("One or more arg is null");
        ArrayList<String> services_tmp = new ArrayList<String>();

        for (int i = 0; i < j_array.length(); i++) {
            JSONObject tmp_obj = j_array.getJSONObject(i);
            // Check if provider matches
            for(String p : _providers) {
                if (p.compareTo(tmp_obj.get("name").toString()) == 0) {
                    // Check if state matches
                    for (String s : _states) {
                        // For each service provided by the matched service provider
                        JSONArray services_array = new JSONArray(tmp_obj.get("services").toString());
                        for (int j = 0; j < services_array.length(); j++) {
                            // tmp save service`s status and extract the status (last part)
                            String service_status_j = services_array.getJSONObject(j).get("currentStatus").toString();
                            String service_status_j_extracted = service_status_j.substring(service_status_j.lastIndexOf('/') + 1);
                            if (service_status_j_extracted.compareTo(s) == 0) {
                                // Found it, check if it offers the type needed and add it to the array list building the string
                                JSONArray service_types_array = services_array.getJSONObject(j).getJSONArray("qServiceTypes");
                                boolean found = false;
                                for(int k = 0; k < service_types_array.length() && !found; k++)
                                    if (Arrays.asList(_types).contains(service_types_array.getString(k))) {
                                        services_tmp.add("SERVICE PROVIDER: " + p + "  ::  SERVICE ID: " + services_array.getJSONObject(j).get("serviceId").toString() + "  ::  " + "SERVICE NAME: " +
                                                services_array.getJSONObject(j).get("serviceName").toString() + "::" + " SERVICE STATUS: " + s);
                                        found = true;
                                    }
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
