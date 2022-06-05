package com.ids.trustservicesurfer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import org.json.JSONArray;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainWindowController {
    private ConnectionFactory connectionFactory = new ConnectionFactory();

    @FXML
    private ListView countriesList;
    @FXML
    private ListView selectedCountriesList;

    @FXML
    private ListView typesList;
    @FXML
    private ListView selectedTypesList;

    @FXML
    private ListView serviceProvidersList;
    @FXML
    private ListView selectedServiceProvidersList;

    @FXML
    private ListView serviceStatesList;
    @FXML
    private ListView selectedServiceStatesList;


    @FXML
    private ListView resultsList;

    @FXML
    private MenuItem quitButton;


    private Country[] countries;
    private ServiceType[] serviceTypes;
    private String[] serviceProviders;
    private String[] serviceStates;


    private JSONArray complete_list_jarray;

    //TODO delx2
    private enum Filter {TYPE, COUNTRY, PROVIDER, STATE}
    private Filter first_selection = null;

    // Init
    public void initialize() {
        String raw_json;

        // Try to get a json string containing the countries
        try {
            raw_json = connectionFactory.getCountriesListJson();
        } catch(IOException e) {
            errorLauncher(e);
            return;
        }
        // Countries Load
        countries = JsonProcess.countryExtractorJson(raw_json);
        countriesList.getItems().clear();
        for (Country country : countries)
            countriesList.getItems().add(country);

        // Try to get a json containing the complete list of services into a JsonArray
        try {
            complete_list_jarray = JsonProcess.loadJsonArray(connectionFactory.getCompleteServicesListJson());
        } catch (IOException e) {
            errorLauncher(e);
            return;
        }

        // Types Load
        serviceTypes = JsonProcess.serviceTypesExtractorJson(complete_list_jarray);
        typesList.getItems().clear();
        for (ServiceType serviceType : serviceTypes)
            typesList.getItems().add(serviceType);

        // Providers load
        serviceProviders = JsonProcess.serviceProvidersExtractorJson(complete_list_jarray);
        serviceProvidersList.getItems().clear();
        for (String p : serviceProviders)
            serviceProvidersList.getItems().add(p);

        // States load
        serviceStates = JsonProcess.serviceStatesExtractorJson(complete_list_jarray);
        serviceStatesList.getItems().clear();
        for (String p : serviceStates)
            serviceStatesList.getItems().add(p);
    }

    @FXML
    protected void onCountryFilterAdd() {
        if (countriesList.getSelectionModel().getSelectedItem() == null || selectedCountriesList.getItems().contains(countriesList.getSelectionModel().getSelectedItem()))
            return;
        System.out.println("[+] New filter: " + countriesList.getSelectionModel().getSelectedItem());
        selectedCountriesList.getItems().add(countriesList.getSelectionModel().getSelectedItem());
        if (first_selection == null)
            first_selection = Filter.COUNTRY;
        this.updateAvailableFiltersOnChange(Filter.COUNTRY);
    }
    @FXML
    protected void onTypeFilterAdd() {
        if (typesList.getSelectionModel().getSelectedItem() == null || selectedTypesList.getItems().contains(typesList.getSelectionModel().getSelectedItem()))
            return;
        System.out.println("[+] New filter: " + typesList.getSelectionModel().getSelectedItem());
        selectedTypesList.getItems().add(typesList.getSelectionModel().getSelectedItem());
        if (first_selection == null)
            first_selection = Filter.TYPE;
        this.updateAvailableFiltersOnChange(Filter.TYPE);
    }
    @FXML
    protected void onServiceProviderFilterAdd() {
        if (serviceProvidersList.getSelectionModel().getSelectedItem() == null || selectedServiceProvidersList.getItems().contains(serviceProvidersList.getSelectionModel().getSelectedItem()))
            return;
        System.out.println("[+] New filter: " + serviceProvidersList.getSelectionModel().getSelectedItem());
        selectedServiceProvidersList.getItems().add(serviceProvidersList.getSelectionModel().getSelectedItem());
        if (first_selection == null)
            first_selection = Filter.PROVIDER;
        this.updateAvailableFiltersOnChange(Filter.PROVIDER);
    }
    @FXML
    protected void onServiceStateFilterAdd() {
        if (serviceStatesList.getSelectionModel().getSelectedItem() == null || selectedServiceStatesList.getItems().contains(serviceStatesList.getSelectionModel().getSelectedItem()))
            return;
        System.out.println("[+] New filter: " + serviceStatesList.getSelectionModel().getSelectedItem());
        selectedServiceStatesList.getItems().add(serviceStatesList.getSelectionModel().getSelectedItem());
        if (first_selection == null)
            first_selection = Filter.STATE;
        this.updateAvailableFiltersOnChange(Filter.STATE);
    }


    @FXML
    protected void onCountryFilterRemove() {
        if (selectedCountriesList.getSelectionModel().getSelectedItem() == null)
            return;
        System.out.println("[-] Remove filter: " + selectedCountriesList.getSelectionModel().getSelectedItem());
        selectedCountriesList.getItems().remove(selectedCountriesList.getSelectionModel().getSelectedIndex());
        this.updateAvailableFiltersOnChange(Filter.COUNTRY);
    }

    @FXML
    protected void onTypeFilterRemove() {
        if (selectedTypesList.getSelectionModel().getSelectedItem() == null)
            return;
        System.out.println("[-] Remove filter: " + selectedTypesList.getSelectionModel().getSelectedItem());
        selectedTypesList.getItems().remove(selectedTypesList.getSelectionModel().getSelectedIndex());
        this.updateAvailableFiltersOnChange(Filter.TYPE);
    }
    @FXML
    protected void onServiceProviderFilterRemove() {
        if (selectedServiceProvidersList.getSelectionModel().getSelectedItem() == null)
            return;
        System.out.println("[-] Remove filter: " + selectedServiceProvidersList.getSelectionModel().getSelectedItem());
        selectedServiceProvidersList.getItems().remove(selectedServiceProvidersList.getSelectionModel().getSelectedIndex());
        this.updateAvailableFiltersOnChange(Filter.PROVIDER);
    }
    @FXML
    protected void onServiceStateFilterRemove() {
        if (selectedServiceStatesList.getSelectionModel().getSelectedItem() == null)
            return;
        System.out.println("[-] Remove filter: " + selectedServiceStatesList.getSelectionModel().getSelectedItem());
        selectedServiceStatesList.getItems().remove(selectedServiceStatesList.getSelectionModel().getSelectedIndex());
        this.updateAvailableFiltersOnChange(Filter.STATE);
    }

    //TODO del
    private void updateAvailableCountryFilters(String[] available_countries) {
        selectedCountriesList.getItems().clear();
        countriesList.getItems().clear();
        countriesList.getItems().addAll(available_countries);
    }

    private void updateAvailableStateFilters(String[] available_states) {
        selectedServiceStatesList.getItems().clear();
        serviceStatesList.getItems().clear();
        serviceStatesList.getItems().addAll(available_states);
    }

    private void updateAvailableProviderFilters(String[] available_providers) {
        selectedServiceProvidersList.getItems().clear();
        serviceProvidersList.getItems().clear();
        serviceProvidersList.getItems().addAll(available_providers);
    }

    private void updateAvailableTypeFilters(String[] available_types) {
        selectedTypesList.getItems().clear();
        typesList.getItems().clear();
        typesList.getItems().addAll(available_types);
    }


    // FIXME
    private void updateAvailableFiltersOnChange(Filter updated_filter) {
        String[] countryFilters,
                typeFilters,
                providerFilters,
                stateFilters;

        // Copy country filters
        countryFilters = new String[selectedCountriesList.getItems().size()];
        for (int i = 0; i < selectedCountriesList.getItems().size(); i++)
            countryFilters[i] = selectedCountriesList.getItems().get(i).toString();
        try { countryFilters = extractCode(countryFilters); } catch(RuntimeException e) { errorLauncher(e); }
        // Copy type filters
        typeFilters = new String[selectedTypesList.getItems().size()];
        for (int i = 0; i < selectedTypesList.getItems().size(); i++)
            typeFilters[i] = selectedTypesList.getItems().get(i).toString();
        // Copy provider filters
        providerFilters = new String[selectedServiceProvidersList.getItems().size()];
        for (int i = 0; i < selectedServiceProvidersList.getItems().size(); i++)
            providerFilters[i] = selectedServiceProvidersList.getItems().get(i).toString();
        // Copy state filters
        stateFilters = new String[selectedServiceStatesList.getItems().size()];
        for (int i = 0; i < selectedServiceStatesList.getItems().size(); i++)
            stateFilters[i] = selectedServiceStatesList.getItems().get(i).toString();

        String[][] s = JsonProcess.availableFilterExtractorJson(complete_list_jarray, countryFilters, typeFilters, stateFilters, providerFilters);
        System.out.println(s.length + " " + s[0].length + " " + s[1].length + " " + s[2].length + " " + s[3].length);

        // COUNTRY => PROVIDER => TYPE => STATE

        if (updated_filter == Filter.COUNTRY) {
            updateAvailableProviderFilters(s[3]);
            updateAvailableTypeFilters(s[1]);
            updateAvailableStateFilters(s[2]);
        } else if (updated_filter == Filter.PROVIDER) {
            updateAvailableTypeFilters(s[1]);
            updateAvailableStateFilters(s[2]);
        } else if (updated_filter == Filter.TYPE) {
            updateAvailableStateFilters(s[2]);
        } else if (updated_filter == Filter.STATE) {

        }
    }

    @FXML
    public void handleCloseButtonAction(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    public void handleResetAction(ActionEvent event) {
        System.out.println("[!] Resetting filters and results");
        selectedServiceStatesList.getItems().clear();
        selectedTypesList.getItems().clear();
        selectedServiceProvidersList.getItems().clear();
        selectedCountriesList.getItems().clear();
        resultsList.getItems().clear();
    }

    @FXML
    protected void onSearchStart() throws IOException {
        resultsList.getItems().clear();
        String[] countryFilters,
                typeFilters,
                providerFilters,
                stateFilters;

        // Error statement
        if(selectedCountriesList.getItems().size() == 0 && selectedTypesList.getItems().size() == 0 && selectedServiceProvidersList.getItems().size() == 0 && selectedServiceStatesList.getItems().size() == 0) {
            errorLauncher(new IOException("Error: no active filter detected, select at least one to continue."));
            return;
        }
        // Copy country filters
        if (selectedCountriesList.getItems().size() == 0)
            countryFilters = extractCode(countries);
        else {
            countryFilters = new String[selectedCountriesList.getItems().size()];
            for (int i = 0; i < selectedCountriesList.getItems().size(); i++)
                countryFilters[i] = selectedCountriesList.getItems().get(i).toString();
            try {
                extractCode(countryFilters);
            } catch(RuntimeException e) {
                errorLauncher(e);
            }
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
        //TODO update manual
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
        String raw_json;
        try {
            raw_json = connectionFactory.getServicesListJson(_countries, _types);
        } catch(IOException e) {
            errorLauncher(e);
            return;
        }
        String[] services = JsonProcess.serviceExtractorJson(raw_json, _providers, _states);
        resultsList.getItems().clear();
        for(String i:services) {
            resultsList.getItems().add(i);
        }
        //TODO try to use complete_list_jarray
        /*
        if (complete_list_jarray == null) {
            errorLauncher(new Exception("Something went wrong, check internet connection and restart"));
            return;
        }
        String[] services = JsonProcess.serviceExtractorJson(complete_list_jarray, _providers, _states);
        resultsList.getItems().clear();
        for(String i:services) {
            resultsList.getItems().add(i);
        }
         */
    }

    private void errorLauncher(Exception e) {
        //resultsList.getItems().add(e);
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("error-window.fxml"));
                fxmlLoader.getNamespace().put("labelText", e.toString());

            Scene scene = new Scene(fxmlLoader.load(), 300, 200);
            Stage stage = new Stage();
            stage.setTitle("Something went wrong");
            stage.setScene(scene);
            stage.show();
        } catch (IOException ee) {
            System.out.println(ee.toString());
        }
    }

    // Statics methods
    private static String[] extractCode(String[] _countries) throws RuntimeException {
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
