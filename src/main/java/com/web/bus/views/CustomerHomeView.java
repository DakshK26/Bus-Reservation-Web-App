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

    public CustomerHomeView () throws IOException {
        buses = new BusList(); // Create a new BusList object
        buses = buses.readFileMaster();

        busList = Arrays.asList(buses.getList());

        select = new Select<>();
        select.setItems("Company", "Start Destination", "End Destination");
        select.setValue("Company");
        searchbar = new TextField();
        searchbar.setPlaceholder("Search Criteria");
        searchbar.setPrefixComponent(VaadinIcon.SEARCH.create());
        search = new Button("Search", event ->{ // Register action event
            if (select.getValue().equalsIgnoreCase("Company")) {
                String companyName = searchbar.getValue();
                BusList companyBuses = new BusList();
                companyBuses = companyBuses.searchCompany(companyName, buses);
                companyBuses.quickSort();
                busList = Arrays.asList(companyBuses.getList());
                table.setItems(busList);
            }
            else if (select.getValue().equalsIgnoreCase("Start Destination")) {
                String busStart = searchbar.getValue();
                BusList startDestinationBuses = new BusList();
                startDestinationBuses = startDestinationBuses.searchByStartDestination(busStart, buses);
                startDestinationBuses.quickSort();
                busList = Arrays.asList(startDestinationBuses.getList());
                table.setItems(busList);
            }
            else if (select.getValue().equalsIgnoreCase("End Destination")) {
                String busEnd = searchbar.getValue();
                BusList endDestinationBuses = new BusList();
                endDestinationBuses = endDestinationBuses.searchByEndDestination(busEnd, buses);
                endDestinationBuses.quickSort();
                busList = Arrays.asList(endDestinationBuses.getList());
                table.setItems(busList);
            }
        });
        clear = new Button("Clear Filters", event ->{ // Register action event
            busList = Arrays.asList(buses.getList());
            table.setItems(busList);
        });
        table = new Grid<>();
        table.setItems(busList);
        table.addColumn(Bus::getBusID).setHeader("Company");
        table.addColumn(Bus::getStartDestination).setHeader("Start Destination");
        table.addColumn(Bus::getEndDestination).setHeader("End Destination");
        table.addColumn(Bus::getDistance).setHeader("Distance");
        table.addColumn(Bus::getTimeInMinutes).setHeader("Travel Time");
        table.addItemClickListener(event -> {
            Bus selectedBus = event.getItem();
            UI.getCurrent().getSession().setAttribute("selectedBus", selectedBus);
            UI.getCurrent().navigate("customerPurchaseView");
        });
       

        table.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);

        add (
                new H1("Book A Bus"),
                new HorizontalLayout(new H4("Search: "), select, searchbar, search, clear),
                table
                );
       search.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_CONTRAST);
       clear.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
    }
}
