package com.ids.trustservicesurfer;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainWindowController {
    private ConnectionFactory connectionFactory = new ConnectionFactory();

    @FXML
    private ListView countriesList;
    @FXML
    private ListView typesList;
    @FXML
    private ListView serviceProvidersList;
    @FXML
    private ListView serviceStatesList;
    
    @FXML
    private ListView resultsList;

    @FXML
    private ListView selectedCountriesList;
    @FXML
    private ListView selectedTypesList;
    @FXML
    private ListView selectedServiceProvidersList;
    @FXML
    private ListView selectedServiceStatesList;
    private Country[] countries;
    private ServiceType[] serviceTypes;
    private String[] serviceProviders;
    private String[] serviceStates;
    @FXML
    protected void onLoadButtonClick() {
        String raw_json;
        // Contries Load
        try {
            raw_json = connectionFactory.getCountriesListJson();
            countries = JsonProcess.countryExtractorJson(raw_json);
            countriesList.getItems().clear();
            for (Country c : countries) {
                countriesList.getItems().add(c);
            }
        } catch(IOException e) {
            errorLauncher(e);
        }

        // Types Load
        try {
            raw_json = connectionFactory.getCompleteServicesListJson();
            serviceTypes =  JsonProcess.serviceTypesExtractorJson(raw_json);
            typesList.getItems().clear();
            for (ServiceType c : serviceTypes)
                typesList.getItems().add(c);
        } catch (IOException e) {
            errorLauncher(e);
        }
        // TODO: try to reuse prev downloaded raw_json
        // Providers load
        try {
            raw_json = connectionFactory.getCompleteServicesListJson();
            serviceProviders =  JsonProcess.serviceProvidersExtractorJson(raw_json);
            serviceProvidersList.getItems().clear();
            for (String p : serviceProviders)
                serviceProvidersList.getItems().add(p);
        } catch (IOException e) {
            errorLauncher(e);
        }
        // TODO: try to reuse prev downloaded raw_json
        // States load
        try {
            raw_json = connectionFactory.getCompleteServicesListJson();
            serviceStates =  JsonProcess.serviceStatesExtractorJson(raw_json);
            serviceStatesList.getItems().clear();
            for (String p : serviceStates)
                serviceStatesList.getItems().add(p);
        } catch (IOException e) {
            errorLauncher(e);
        }
    }

    @FXML
    protected void onCountryFilterAdd() {
        if (countriesList.getSelectionModel().getSelectedItem() == null || selectedCountriesList.getItems().contains(countriesList.getSelectionModel().getSelectedItem()))
            return;
        System.out.println("[+] New filter: " + countriesList.getSelectionModel().getSelectedItem());
        selectedCountriesList.getItems().add(countriesList.getSelectionModel().getSelectedItem());
    }
    @FXML
    protected void onTypeFilterAdd() {
        if (typesList.getSelectionModel().getSelectedItem() == null || selectedTypesList.getItems().contains(typesList.getSelectionModel().getSelectedItem()))
            return;
        System.out.println("[+] New filter: " + typesList.getSelectionModel().getSelectedItem());
        selectedTypesList.getItems().add(typesList.getSelectionModel().getSelectedItem());
    }
    //TOCHECK
    @FXML
    protected void onServiceProviderFilterAdd() {
        if (serviceProvidersList.getSelectionModel().getSelectedItem() == null || selectedServiceProvidersList.getItems().contains(serviceProvidersList.getSelectionModel().getSelectedItem()))
            return;
        System.out.println("[+] New filter: " + serviceProvidersList.getSelectionModel().getSelectedItem());
        selectedServiceProvidersList.getItems().add(serviceProvidersList.getSelectionModel().getSelectedItem());
    }
    //TOCHECK
    @FXML
    protected void onServiceStateFilterAdd() {
        if (serviceStatesList.getSelectionModel().getSelectedItem() == null || selectedServiceStatesList.getItems().contains(serviceStatesList.getSelectionModel().getSelectedItem()))
            return;
        System.out.println("[+] New filter: " + serviceStatesList.getSelectionModel().getSelectedItem());
        selectedServiceStatesList.getItems().add(serviceStatesList.getSelectionModel().getSelectedItem());
    }


    @FXML
    protected void onCountryFilterRemove() {
        if (selectedCountriesList.getSelectionModel().getSelectedItem() == null)
            return;
        System.out.println("[-] Remove filter: " + selectedCountriesList.getSelectionModel().getSelectedItem());
        selectedCountriesList.getItems().remove(selectedCountriesList.getSelectionModel().getSelectedIndex());
    }

    @FXML
    protected void onTypeFilterRemove() {
        if (selectedTypesList.getSelectionModel().getSelectedItem() == null)
            return;
        System.out.println("[-] Remove filter: " + selectedTypesList.getSelectionModel().getSelectedItem());
        selectedTypesList.getItems().remove(selectedTypesList.getSelectionModel().getSelectedIndex());
    }
    //TOCHECK
    @FXML
    protected void onServiceProviderFilterRemove() {
        if (selectedServiceProvidersList.getSelectionModel().getSelectedItem() == null)
            return;
        System.out.println("[-] Remove filter: " + selectedServiceProvidersList.getSelectionModel().getSelectedItem());
        selectedServiceProvidersList.getItems().remove(selectedServiceProvidersList.getSelectionModel().getSelectedIndex());
    }
    //TOCHECK
    @FXML
    protected void onServiceStateFilterRemove() {
        if (selectedServiceStatesList.getSelectionModel().getSelectedItem() == null)
            return;
        System.out.println("[-] Remove filter: " + selectedServiceStatesList.getSelectionModel().getSelectedItem());
        selectedServiceStatesList.getItems().remove(selectedServiceStatesList.getSelectionModel().getSelectedIndex());
    }

    @FXML
    protected void onSearchStart() {
        resultsList.getItems().clear();

        if(countries == null || serviceTypes == null || serviceProviders == null || serviceStates == null) {
            // Try to reload
            this.onLoadButtonClick();
            if(countries == null || serviceTypes == null || serviceProviders == null || serviceStates == null) {
                errorLauncher(new Exception("Something went wrong, check internet connection"));
                return;
            }
        }
        String[] countryFilters,
                 typeFilters,
                 providerFilters,
                 stateFilters;

        // Copy country filters
        if (selectedCountriesList.getItems().size() == 0)
            countryFilters = extractCode(countries);
        else {
            countryFilters = new String[selectedCountriesList.getItems().size()];
            for (int i = 0; i < selectedCountriesList.getItems().size(); i++)
                countryFilters[i] = selectedCountriesList.getItems().get(i).toString();
            countryFilters = extractCode(countryFilters);
        }
        // Copy type filters
        if (selectedTypesList.getItems().size() == 0) {
            typeFilters = new String[serviceTypes.length];
            for (int i = 0; i < serviceTypes.length; i++)
                typeFilters[i] = serviceTypes[i].getType();
        } else {
            typeFilters = new String[selectedTypesList.getItems().size()];
            for (int i = 0; i < selectedTypesList.getItems().size(); i++)
                typeFilters[i] = selectedTypesList.getItems().get(i).toString();
        }
        // Copy provider filters
        if (selectedServiceProvidersList.getItems().size() == 0)
            providerFilters = serviceProviders;
        else {
            providerFilters = new String[selectedServiceProvidersList.getItems().size()];
            for (int i = 0; i < selectedServiceProvidersList.getItems().size(); i++)
                providerFilters[i] = selectedServiceProvidersList.getItems().get(i).toString();
        }
        // Copy state filters
        if (selectedServiceStatesList.getItems().size() == 0)
            stateFilters = serviceStates;
        else {
            stateFilters = new String[selectedServiceStatesList.getItems().size()];
            for (int i = 0; i < selectedServiceStatesList.getItems().size(); i++)
                stateFilters[i] = selectedServiceStatesList.getItems().get(i).toString();
        }

        searchHandler(countryFilters, typeFilters, providerFilters, stateFilters);
    }

    private void searchHandler(String[] _countries, String[] _types, String[] _providers, String[] _states) {
    	try {
            String raw_json = connectionFactory.getServicesListJson(_countries, _types);
            String[] services = JsonProcess.serviceExtractorJson(raw_json, _providers, _states);
            resultsList.getItems().clear();
            for(String i:services) {
                resultsList.getItems().add(i);
            }
        } catch(IOException e) {
            errorLauncher(e);
        }
    }

    //TODO: prepare error window
    private void errorLauncher(Exception e) {
        System.out.println(e);
    }

    // Init
    // TODO: transfer onLoadButtonClick into this method
    public void initialize() {
        this.onLoadButtonClick();
    }

    // Statics methods
    private static String[] extractCode(String[] _countries) {
        String code_extractor_pattern = "^[A-Z]{2}";
        Pattern code_extractor = Pattern.compile(code_extractor_pattern);

        for (int i = 0; i <  _countries.length; i++) {
            Matcher code_matcher = code_extractor.matcher(_countries[i]);
            if (code_matcher.find() && code_matcher.groupCount() >= 0)
                _countries[i] =  code_matcher.group(0).toString();
            else
                throw new RuntimeException("[!] Something went wrong: no pattern found");
        }
        return _countries;
    }
    private static String[] extractCode(Country[] _countries) {
        String[] countries = new String[_countries.length];
        for (int i = 0; i <  _countries.length; i++) {
            countries[i] = _countries[i].getCode();
        }
        return countries;
    }

}
