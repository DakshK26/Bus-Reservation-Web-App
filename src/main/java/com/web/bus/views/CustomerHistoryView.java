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

@Route(value = "customerHistoryView", layout = CustomerMainLayout.class)
@PageTitle("CustomerHistory")
public class CustomerHistoryView extends VerticalLayout {
    private Button search, clear;
    private TextField searchbar;
    private Select<String> select;
    private Grid<Bus> table;
    private BusList buses;

    private List<Bus> busList;

    public CustomerHistoryView() throws IOException {
        // Get customer from session data
        Customer customer = (Customer) UI.getCurrent().getSession().getAttribute("customer");

            BusList temp = new BusList();
            String [] tempData = temp.readFilePurchases();
            String [] usernames = new String[tempData.length];
            for (int i = 0; i < tempData.length; i++) {
                String[] words = tempData[i].split("/");
                usernames[i] = words[0];
                Bus tempBus = new Bus(words[1], words[2], Integer.parseInt(words[3]), words[4]);
                temp.increaseSize();
                temp.insert(tempBus);
            }

            BusList myBookedBuses = new BusList();
            for (int i = 0; i < usernames.length; i++) {
                if(usernames[i].equalsIgnoreCase(customer.getUsername())) {
                   myBookedBuses.increaseSize();
                   myBookedBuses.insert(temp.getList()[i]);
                }
            }
        busList = Arrays.asList(myBookedBuses.getList());

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
                companyBuses = companyBuses.searchCompany(companyName, myBookedBuses);
                companyBuses.quickSort();
                busList = Arrays.asList(companyBuses.getList());
                table.setItems(busList);
            }
            else if (select.getValue().equalsIgnoreCase("Start Destination")) {
                String busStart = searchbar.getValue();
                BusList startDestinationBuses = new BusList();
                startDestinationBuses = startDestinationBuses.searchByStartDestination(busStart, myBookedBuses);
                startDestinationBuses.quickSort();
                busList = Arrays.asList(startDestinationBuses.getList());
                table.setItems(busList);
            }
            else if (select.getValue().equalsIgnoreCase("End Destination")) {
                String busEnd = searchbar.getValue();
                BusList endDestinationBuses = new BusList();
                endDestinationBuses = endDestinationBuses.searchByEndDestination(busEnd, myBookedBuses);
                endDestinationBuses.quickSort();
                busList = Arrays.asList(endDestinationBuses.getList());
                table.setItems(busList);
            }
        });
        clear = new Button("Clear Filters", event ->{ // Register action event
            busList = Arrays.asList(myBookedBuses.getList());
            table.setItems(busList);
        });
        table = new Grid<>();
        table.setItems(busList);
        table.addColumn(Bus::getBusID).setHeader("Company");
        table.addColumn(Bus::getStartDestination).setHeader("Start Destination");
        table.addColumn(Bus::getEndDestination).setHeader("End Destination");
        table.addColumn(Bus::getDistance).setHeader("Distance");
        table.addColumn(Bus::getTimeInMinutes).setHeader("Travel Time");

        table.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);

        add (
                new H1("Past Purchases"),
                new HorizontalLayout(new H4("Search: "), select, searchbar, search, clear),
                table
                );
        search.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_CONTRAST);
        clear.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
    }
}
