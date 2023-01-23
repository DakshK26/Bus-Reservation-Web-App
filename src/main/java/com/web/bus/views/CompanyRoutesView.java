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
import com.web.bus.records.Bus;
import com.web.bus.records.BusList;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author: Daksh & Ashwin
 * date: Jan. 2023
 * Description: This class is the view for the company to view their routes
 * Method List: CompanyRoutesView()
 */
@Route(value = "companyRoutesView", layout = CompanyMainLayout.class)
@PageTitle("CustomerRoutes")
public class CompanyRoutesView extends VerticalLayout {
    /*
    Private Instance Data
     */
    private Button search, addRoute, clear;
    private TextField searchbar;
    private Select<String> select;
    private Grid<Bus> table;
    private BusList buses, companyBuses;

    private List<Bus> busList;

    /*
    Constructor to build the company routes view
     */
    public CompanyRoutesView() throws IOException {
        // Get company from session data
        Company company = (Company) UI.getCurrent().getSession().getAttribute("company");

        //create a bus list
        buses = new BusList();
        //initialize bus list to already existing data
        buses = buses.readFileMaster();
        //create bus list for company specific buses
        companyBuses = new BusList();
        companyBuses = companyBuses.searchCompany(company.getName(), buses);
        //create a list of the company buses to display in the table
        busList = Arrays.asList(companyBuses.getList());
        select = new Select<>();
        select.setItems("Start Destination", "End Destination");
        select.setValue("Start Destination"); //default option
        searchbar = new TextField(); //searchbar
        searchbar.setPlaceholder("Search Criteria"); //placeholder
        searchbar.setPrefixComponent(VaadinIcon.SEARCH.create()); //magnifying glass icon in searchbar
        search = new Button("Search", event ->{ // Register action event
           //search by company name
            if (select.getValue().equalsIgnoreCase("Company")) {
                //create a bus list for company buses
                String companyName = searchbar.getValue();
                BusList companyBuses = new BusList();
                //search by company name
                companyBuses = companyBuses.searchCompany(companyName, buses);
                companyBuses.quickSort(); //sort by distance
                //set the list to the company bus list
                busList = Arrays.asList(companyBuses.getList());
                table.setItems(busList);
            }
            //search by company name
            else if (select.getValue().equalsIgnoreCase("Start Destination")) {
                //create a bus list for start location buses
                String busStart = searchbar.getValue();
                BusList startDestinationBuses = new BusList();
                //search by start location
                startDestinationBuses = startDestinationBuses.searchByStartDestination(busStart, buses);
                startDestinationBuses.quickSort(); //sort by distance
                //set the list to the company bus list
                busList = Arrays.asList(startDestinationBuses.getList());
                table.setItems(busList);
            }
            //search by company name
            else if (select.getValue().equalsIgnoreCase("End Destination")) {
                //create a bus list for end location buses
                String busEnd = searchbar.getValue();
                BusList endDestinationBuses = new BusList();
                //search by end location
                endDestinationBuses = endDestinationBuses.searchByEndDestination(busEnd, buses);
                endDestinationBuses.quickSort(); //sort by distance
                //set the list to the company bus list
                busList = Arrays.asList(endDestinationBuses.getList());
                table.setItems(busList);
            }
        });
        //clear button
        clear = new Button("Clear Filters", event ->{ // Register action event
            //reset table back to original view with all company buses
            busList = Arrays.asList(buses.getList());
            table.setItems(busList);
        });
        //button to add a bus route
        addRoute = new Button ("Add a Route", event -> { // Company action event
            UI.getCurrent().navigate("companyAddRouteView"); // Send user to company add route view
        });
        //create table
        table = new Grid<>();
        table.setItems(busList);
        //create columns for table
        table.addColumn(Bus::getBusID).setHeader("CompanyName");
        table.addColumn(Bus::getStartDestination).setHeader("Start Destination");
        table.addColumn(Bus::getEndDestination).setHeader("End Destination");
        table.addColumn(Bus::getDistance).setHeader("Distance");
        table.addColumn(Bus::getTimeInMinutes).setHeader("Travel Time");
        table.addComponentColumn(item -> {
            Button btn = new Button("Cancel");
            btn.addClickListener(event -> {
                // Cancel route
                buses.delete(item);
                try {
                    buses.writeFileMaster(buses);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                busList = Arrays.asList(buses.getList());
                table.setItems(busList);
            });
            return btn;
        }).setHeader("Cancel Routes");


        table.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);


        table.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);



        add (
                new H1("Company Active Routes"),
                new HorizontalLayout(new H4("Search: "), select, searchbar, search, clear),
                addRoute,
                table
                );
        search.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_CONTRAST);
        clear.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
    }
}
