package com.ids.trustservicesurfer;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.io.IOException;

public class MainWindowController {
    private ConnectionFactory connectionFactory = new ConnectionFactory();

    @FXML
    private ListView countriesList;
    @FXML
    private ListView typesList;
    
    @FXML
    private ListView idResult;

    @FXML
    private ListView selectedCountriesList;
    @FXML
    private ListView selectedTypesList;

    private Country[] countries;
    private ServiceType[] serviceTypes;
    @FXML
    protected void onLoadButtonClick() throws IOException {
        String raw_json;

        // Contries Load
        raw_json = connectionFactory.getCountriesListJson();
        countries =  JsonProcess.countryExtractorJson(raw_json);
        countriesList.getItems().clear();
        for (Country c : countries) {
            countriesList.getItems().add(c);
        }

        // Types Load
        raw_json = connectionFactory.getCompleteServicesListJson();
        serviceTypes =  JsonProcess.serviceTypesExtractorJson(raw_json);
        typesList.getItems().clear();
        for (ServiceType c : serviceTypes)
            typesList.getItems().add(c);
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
    protected void onSearchStart() throws IOException {
    	if((selectedTypesList.getItems().size() == 0) && (selectedCountriesList.getItems().size() == 0 ))
    	{
    		idResult.getItems().clear();
    		idResult.getItems().add("ERROR: PARAMETERS NOT FOUND" );
    		return;
    	}
    	
    	else if(selectedTypesList.getItems().size() == 0)
    		searchHandler(selectedCountriesList,typesList);
    	
    	else if(selectedCountriesList.getItems().size() == 0 )
    		searchHandler(countriesList,selectedTypesList);	
    	
    	else
    		searchHandler(selectedCountriesList,selectedTypesList);
    	
    	return;
    }
    
    private void searchHandler(ListView country, ListView type) throws IOException
    {
    	int n1 =country.getItems().size(); 
    	int n2 =type.getItems().size();
    	String[] countries = new String[n1];
    	String[] types = new String[n2];
    	for(int i=0; i<country.getItems().size();i++)
			countries[i] =  country.getItems().get(i).toString();
    	for(int i=0; i<type.getItems().size();i++)
			types[i] = type.getItems().get(i).toString();
		
    	countries = remake(countries);
    	
			String rawJson = connectionFactory.getServicesListJson(countries, types);
		String[] services = JsonProcess.serviceExtractorJson(rawJson);
		idResult.getItems().clear();
		for(String i:services)
		{
			System.out.println(i+"-");
			idResult.getItems().add(i);
		}
    }
    
    private String[] remake (String[] s)
    {
    	String[] tmp = new String[s.length];
    	for(int i=0;i<s.length;i++)
    	{
    		String str = s[i];
    		String str1 = Character.toString(str.charAt(0)) + Character.toString(str.charAt(1));
    		System.out.println(str1);
    		tmp[i] = str1;
    		
    	}
    	return tmp;
    }
   }
