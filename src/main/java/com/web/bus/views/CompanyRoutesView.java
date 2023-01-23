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

@Route(value = "companyRoutesView", layout = CompanyMainLayout.class)
@PageTitle("CustomerRoutes")
public class CompanyRoutesView extends VerticalLayout {
    private Button search, addRoute, clear;
    private TextField searchbar;
    private Select<String> select;
    private Grid<Bus> table;
    private BusList buses, companyBuses;

    private List<Bus> busList;

    public CompanyRoutesView() throws IOException {
        // Get company from session data
        Company company = (Company) UI.getCurrent().getSession().getAttribute("company");

        buses = new BusList();
        buses = buses.readFileMaster();
        companyBuses = new BusList();
        companyBuses = companyBuses.searchCompany(company.getName(), buses);


        busList = Arrays.asList(companyBuses.getList());
        select = new Select<>();
        select.setItems("Start Destination", "End Destination");
        select.setValue("Start Destination");
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
        addRoute = new Button ("Add a Route", event -> { // Company action event
            UI.getCurrent().navigate("companyAddRouteView"); // Send user to register route
        });
        table = new Grid<>();
        table.setItems(busList);
        table.addColumn(Bus::getBusID).setHeader("CompanyName");
        table.addColumn(Bus::getStartDestination).setHeader("Start Destination");
        table.addColumn(Bus::getEndDestination).setHeader("End Destination");
        table.addColumn(Bus::getDistance).setHeader("Distance");
        table.addColumn(Bus::getTimeInMinutes).setHeader("Travel Time");
        table.addComponentColumn(item -> {
            Button btn = new Button("Cancel");
            btn.addClickListener(event -> {
                // Cancel route
                BusList busList = new BusList();
                try {
                    busList = busList.readFileMaster();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                busList.delete(item);
                try {
                    busList.writeFileMaster(busList);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                UI.getCurrent().navigate("companyRoutesView");
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
