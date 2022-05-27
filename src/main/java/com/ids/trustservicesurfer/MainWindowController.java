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
    private ListView idResult;

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
            throw new RuntimeException(e);
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
    @FXML
    protected void onServiceProviderFilterAdd() {
        if (serviceProvidersList.getSelectionModel().getSelectedItem() == null || selectedServiceProvidersList.getItems().contains(typesList.getSelectionModel().getSelectedItem()))
            return;
        System.out.println("[+] New filter: " + serviceProvidersList.getSelectionModel().getSelectedItem());
        selectedServiceProvidersList.getItems().add(serviceProvidersList.getSelectionModel().getSelectedItem());
    }
    @FXML
    protected void onServiceTypeFilterAdd() {
        if (serviceStatesList.getSelectionModel().getSelectedItem() == null || selectedServiceStatesList.getItems().contains(typesList.getSelectionModel().getSelectedItem()))
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
    @FXML
    protected void onServiceProviderFilterRemove() {
        if (selectedServiceProvidersList.getSelectionModel().getSelectedItem() == null)
            return;
        System.out.println("[-] Remove filter: " + selectedServiceProvidersList.getSelectionModel().getSelectedItem());
        selectedServiceProvidersList.getItems().remove(selectedServiceProvidersList.getSelectionModel().getSelectedIndex());
    }
    @FXML
    protected void onServiceTypeFilterRemove() {
        if (selectedServiceStatesList.getSelectionModel().getSelectedItem() == null)
            return;
        System.out.println("[-] Remove filter: " + selectedServiceStatesList.getSelectionModel().getSelectedItem());
        selectedServiceStatesList.getItems().remove(selectedServiceStatesList.getSelectionModel().getSelectedIndex());
    }

    @FXML
    protected void onSearchStart() throws IOException {
        idResult.getItems().clear();
    	if((selectedTypesList.getItems().size() == 0) && (selectedCountriesList.getItems().size() == 0 ))
            searchHandler(countriesList, typesList);
        else if(selectedTypesList.getItems().size() == 0)
    		searchHandler(selectedCountriesList,typesList);
        else if(selectedCountriesList.getItems().size() == 0 )
    		searchHandler(countriesList, selectedTypesList);
        else
    		searchHandler(selectedCountriesList, selectedTypesList);
    }

    private void searchHandler(ListView _countries, ListView _types) {
    	String[] countries = new String[_countries.getItems().size()],
                 types = new String[_types.getItems().size()];

    	for(int i = 0; i < _countries.getItems().size(); i++)
			countries[i] =  _countries.getItems().get(i).toString();
    	for(int i = 0; i < _types.getItems().size(); i++)
			types[i] = _types.getItems().get(i).toString();

    	countries = extractCode(countries);

        try {
            String raw_json = connectionFactory.getServicesListJson(countries, types);
            String[] services = JsonProcess.serviceExtractorJson(raw_json);
            idResult.getItems().clear();
            for(String i:services) {
                System.out.println(i+"-");
                idResult.getItems().add(i);
            }
        } catch(IOException e) {
            System.out.println("[!] Error: " + e.toString());
        }
    }

    private void errorLauncher(Exception e) {
        //TODO

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

}
