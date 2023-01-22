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

@Route(value = "companyHomeView", layout = CompanyMainLayout.class)
@PageTitle("CompanyHome")
public class CompanyHomeView extends VerticalLayout {
    private Button search, clear;
    private TextField searchbar;
    private Select<String> select;
    private Grid<Bus> table;
    private BusList buses;

    private List<Bus> busList;

    public CompanyHomeView() throws IOException {
        // Get company from session data
        Company company = (Company) UI.getCurrent().getSession().getAttribute("company");

        buses = new BusList();
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
            busList = Arrays.asList(companyBuses.getList());
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

        table.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);



        add (
                new H1("All Currently Active Routes - All Companies"),
                new HorizontalLayout(new H4("Search: "), select, searchbar, search, clear),
                table
        );
        search.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_CONTRAST);
        clear.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
    }
}
