package com.web.bus.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.web.bus.entities.Company;
import com.web.bus.entities.Customer;
import com.web.bus.records.Bus;
import com.web.bus.records.BusList;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author: Daksh & Ashwin
 * Date: Jan. 2023
 * Description: GUI for the company home view
 * Method List:
 * public CompanyHomeView() throws IOException
 **/
@Route(value = "companyHomeView", layout = CompanyMainLayout.class)
@PageTitle("CompanyHome")
public class CompanyHomeView extends VerticalLayout {
    /*
    Private Instance Data
     */
    private Button search, clear;
    private TextField searchbar;
    private Select<String> select;
    private Grid<Bus> table;
    private BusList buses;

    private List<Bus> busList;

    /*
    Constructor to build Company Home View
     */
    public CompanyHomeView() throws IOException {
        // Get company from session data
        Company company = (Company) UI.getCurrent().getSession().getAttribute("company");

        //create a new bus list
        buses = new BusList();
        buses = buses.readFileMaster(); // Read data from master file and initialize it to the list

        busList = Arrays.asList(buses.getList()); //create a list of the bus list to display in the table

        select = new Select<>(); // Create select
        select.setItems("Company", "Start Destination", "End Destination");
        select.setValue("Company"); //default option
        searchbar = new TextField(); //search bar
        searchbar.setPlaceholder("Search Criteria"); //placeholder
        searchbar.setPrefixComponent(VaadinIcon.SEARCH.create()); // Add search icon
        search = new Button("Search", event ->{ // Register action event
        if (select.getValue().equalsIgnoreCase("Company")) { //search by company
            //create a bus list for company name buses
            String companyName = searchbar.getValue();
            BusList companyBuses = new BusList(); // Create new bus list
            companyBuses = companyBuses.searchCompany(companyName, buses); //search by the company buses and add it to the company bus list
            companyBuses.quickSort(); // Sort list by distance
            busList = Arrays.asList(companyBuses.getList()); //set list as the company bus list to display in the table
            table.setItems(busList);
        }
        else if (select.getValue().equalsIgnoreCase("Start Destination")) { // Search by start destination
            //create a bus list for start destination buses
            String busStart = searchbar.getValue();
            BusList startDestinationBuses = new BusList(); // Create new bus list
            startDestinationBuses = startDestinationBuses.searchByStartDestination(busStart, buses); //search by the start destination and add it to the company bus list
            startDestinationBuses.quickSort(); //sort the list by distance
            busList = Arrays.asList(startDestinationBuses.getList()); // Convert to list
            table.setItems(busList);
        }
        else if (select.getValue().equalsIgnoreCase("End Destination")) { // Search by end destination
            //create a bus list for end destination buses
            String busEnd = searchbar.getValue();
            BusList endDestinationBuses = new BusList(); // Create new bus list
            endDestinationBuses = endDestinationBuses.searchByEndDestination(busEnd, buses); //search by the end destination and add it to the company bus list
            endDestinationBuses.quickSort(); //sort the list by distance
            busList = Arrays.asList(endDestinationBuses.getList()); // Convert to list
            table.setItems(busList);
        }
        });
        clear = new Button("Clear Filters", event ->{ // Register action event
            busList = Arrays.asList(buses.getList()); //reset the table back to the original list with all buses
            table.setItems(busList);
        });
        table = new Grid<>(); // Create table
        table.setItems(busList);
        // Add table columns
        table.addColumn(Bus::getBusID).setHeader("Company");
        table.addColumn(Bus::getStartDestination).setHeader("Start Destination");
        table.addColumn(Bus::getEndDestination).setHeader("End Destination");
        table.addColumn(Bus::getDistance).setHeader("Distance");
        table.addColumn(Bus::getTimeInMinutes).setHeader("Travel Time");

        table.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT); // Add theme variants



        add ( // Add components to layout
                new H1("All Currently Active Routes - All Companies"),
                new HorizontalLayout(new H4("Search: "), select, searchbar, search, clear),
                table
        );
        // Add theme variants
        // Add theme variants
        search.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_CONTRAST);
        clear.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
    }
}
