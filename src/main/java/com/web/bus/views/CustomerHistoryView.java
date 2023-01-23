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
import com.web.bus.entities.Customer;
import com.web.bus.records.Bus;
import com.web.bus.records.BusList;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author: Daksh & Ashwin
 * Date: Jan. 2023
 * Description: This class is the view for the customer to view their history
 * Method List: public CustomerHistoryView()
 */
@Route(value = "customerHistoryView", layout = CustomerMainLayout.class)
@PageTitle("CustomerHistory")
public class CustomerHistoryView extends VerticalLayout {
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
Constructor to build the Customer Purchased Tickets View
 */
    public CustomerHistoryView() throws IOException {
        // Get customer from session data
        Customer customer = (Customer) UI.getCurrent().getSession().getAttribute("customer");


            // Read the file and store the data in a list
            BusList temp = new BusList();
            //array for data read from file
            String [] tempData = temp.readFilePurchases();
            //array to store usernames
            String [] usernames = new String[tempData.length];
            //loop through each element of the tempData array
            for (int i = 0; i < tempData.length; i++) {
                String[] words = tempData[i].split("/");
                //first word is the username
                usernames[i] = words[0];
                //remaining words used to create bus object
                Bus tempBus = new Bus(words[1], words[2], Integer.parseInt(words[3]), words[4]);
                //insert bus into the list
                temp.increaseSize();
                temp.insert(tempBus);
            }

            BusList myBookedBuses = new BusList(); // List of buses that the customer has booked
           //loop through usernames array
            for (int i = 0; i < usernames.length; i++) {
                //if username is equal to the logged in customer username
                if(usernames[i].equalsIgnoreCase(customer.getUsername())) {
                    //insert the bus at index [i] into the booked busses list
                   myBookedBuses.increaseSize();
                   myBookedBuses.insert(temp.getList()[i]);
                }
            }
        busList = Arrays.asList(myBookedBuses.getList()); // List of buses that the customer has booked

        select = new Select<>(); // Create a select component
        select.setItems("Company", "Start Destination", "End Destination");
        select.setValue("Company"); //default option
        searchbar = new TextField(); //search bar
        searchbar.setPlaceholder("Search Criteria"); //placeholder
        searchbar.setPrefixComponent(VaadinIcon.SEARCH.create()); //magnifying glass icon
        search = new Button("Search", event ->{ // Register action event
            if (select.getValue().equalsIgnoreCase("Company")) { // Search by company
               //create a bus list for the company name buses
                String companyName = searchbar.getValue();
                BusList companyBuses = new BusList();
                //search for the company names from the list
                companyBuses = companyBuses.searchCompany(companyName, myBookedBuses);
                companyBuses.quickSort(); //sort the list by distance
                busList = Arrays.asList(companyBuses.getList()); //convert into a list to be displayed in table
                table.setItems(busList); //set the items in the table
            }
            else if (select.getValue().equalsIgnoreCase("Start Destination")) { // Search by start destination
                //create a bus list for the start destination buses
                String busStart = searchbar.getValue();
                BusList startDestinationBuses = new BusList();
                //search for the start destinations from the list
                startDestinationBuses = startDestinationBuses.searchByStartDestination(busStart, myBookedBuses);
                startDestinationBuses.quickSort(); //sort the list by distance
                busList = Arrays.asList(startDestinationBuses.getList()); //convert into a list to be displayed in table
                table.setItems(busList); //set the items in the table
            }
            else if (select.getValue().equalsIgnoreCase("End Destination")) { // Search by end destination
                //create a bus list for the end destination buses
                String busEnd = searchbar.getValue();
                BusList endDestinationBuses = new BusList();
                //search for the end destinations from the list
                endDestinationBuses = endDestinationBuses.searchByEndDestination(busEnd, myBookedBuses);
                endDestinationBuses.quickSort(); //sort the list by distance
                busList = Arrays.asList(endDestinationBuses.getList()); //convert into a list to be displayed in table
                table.setItems(busList); //set the items in the table
            }
        });
        //clear button
        clear = new Button("Clear Filters", event ->{ // Register action event
            //reset the table back to the original table with all booked routes visible
            busList = Arrays.asList(myBookedBuses.getList());
            table.setItems(busList);
        });

        table = new Grid<>(); // Create a grid component
        table.setItems(busList);
        //add the table columns
        table.addColumn(Bus::getBusID).setHeader("Company");
        table.addColumn(Bus::getStartDestination).setHeader("Start Destination");
        table.addColumn(Bus::getEndDestination).setHeader("End Destination");
        table.addColumn(Bus::getDistance).setHeader("Distance");
        table.addColumn(Bus::getTimeInMinutes).setHeader("Travel Time");

        table.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT); //set table theme

        add ( // Add components to the layout
                new H1("Past Purchases"),
                new HorizontalLayout(new H4("Search: "), select, searchbar, search, clear),
                table
                );
        // Add theme variants to the buttons
        search.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_CONTRAST);
        clear.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
    }
}
