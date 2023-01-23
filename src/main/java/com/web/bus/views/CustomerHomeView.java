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
import com.vaadin.flow.data.renderer.NumberRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.web.bus.records.Bus;
import com.web.bus.records.BusList;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;
/**
 * @author: Aswin & Daksh
 * Date: Jan. 2023
 * Description: This class is the view for the customer to view all the routes
 * Method List: CustomerHomeView()
 */
@Route(value = "customerHomeView", layout = CustomerMainLayout.class)
@PageTitle("CustomerHome")
public class CustomerHomeView extends VerticalLayout {
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
    Constructor to build the Customer Home View/page
     */
    public CustomerHomeView () throws IOException {
        buses = new BusList(); // Create a new BusList object
        buses = buses.readFileMaster(); //initialize the BusList object to the already existing data

        busList = Arrays.asList(buses.getList()); //assign it as a list to be displayed in the table

        select = new Select<>(); // Create a new select object
        select.setItems("Company", "Start Destination", "End Destination");
        select.setValue("Company"); //set the initially selected value
        searchbar = new TextField();//searchbar
        searchbar.setPlaceholder("Search Criteria"); // Set the placeholder
        searchbar.setPrefixComponent(VaadinIcon.SEARCH.create()); //magnifying glass icon
        search = new Button("Search", event ->{ // Register action event
            //if company was selected to search for
            if (select.getValue().equalsIgnoreCase("Company")) {
                String companyName = searchbar.getValue();
                //create a new bus list with buses matching company name
                BusList companyBuses = new BusList();
                //search list by company name
                companyBuses = companyBuses.searchCompany(companyName, buses);
                companyBuses.quickSort(); //sort the list by distance
                busList = Arrays.asList(companyBuses.getList());
                table.setItems(busList); // Set the table to the new list
            }
            //if start destination was seleted to search for
            else if (select.getValue().equalsIgnoreCase("Start Destination")) { // Search by start destination
                //create a new bus list with buses matching start destinations
                String busStart = searchbar.getValue();
                BusList startDestinationBuses = new BusList();
                //search list by start destination
                startDestinationBuses = startDestinationBuses.searchByStartDestination(busStart, buses);
                startDestinationBuses.quickSort(); //sort the list by distance
                busList = Arrays.asList(startDestinationBuses.getList());
                table.setItems(busList); // Set the table to the new list
            }
            //if end destination was selected to search for
            else if (select.getValue().equalsIgnoreCase("End Destination")) { // Search by end destination
                //create a new bus list with buses matching end destinations
                String busEnd = searchbar.getValue();
                BusList endDestinationBuses = new BusList();
                //search list by end destination
                endDestinationBuses = endDestinationBuses.searchByEndDestination(busEnd, buses);
                endDestinationBuses.quickSort(); //sort the list by distance
                busList = Arrays.asList(endDestinationBuses.getList());
                table.setItems(busList); // Set the table to the new list
            }
        });
        //clear button to reset the table back to original
        clear = new Button("Clear Filters", event ->{ // Register action event
            busList = Arrays.asList(buses.getList());
            table.setItems(busList);
        });
        table = new Grid<>(); // Create a new table object
        table.setItems(busList);
        //add the table columns and bind to the bus object attributes
        table.addColumn(Bus::getBusID).setHeader("Company");
        table.addColumn(Bus::getStartDestination).setHeader("Start Destination");
        table.addColumn(Bus::getEndDestination).setHeader("End Destination");
        table.addColumn(Bus::getDistance).setHeader("Distance");
        table.addColumn(Bus::getTimeInMinutes).setHeader("Travel Time");
        //if a table column was clicked
        table.addItemClickListener(event -> {   // Register action event
            Bus selectedBus = event.getItem();
            UI.getCurrent().getSession().setAttribute("selectedBus", selectedBus);
            UI.getCurrent().navigate("customerPurchaseView");
        });
        table.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT); // Set the theme for tehe table

        add ( // Add the components to the layout
                new H1("Book A Bus"),
                new HorizontalLayout(new H4("Search: "), select, searchbar, search, clear),
                table
                );
        // Set the theme
       search.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_CONTRAST);
       clear.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
    }
}
