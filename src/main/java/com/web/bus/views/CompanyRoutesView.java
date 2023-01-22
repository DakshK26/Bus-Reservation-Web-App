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
import com.web.bus.records.Bus;
import com.web.bus.records.BusList;

import java.io.IOException;
import java.util.List;

@Route(value = "companyRoutesView", layout = CompanyMainLayout.class)
@PageTitle("CustomerRoutes")
public class CompanyRoutesView extends VerticalLayout {
    private Button search, addRoute, clear;
    private TextField searchbar;
    private Select<String> select;
    private Grid<Bus> table;
    private BusList busses;

    private List<Bus> busList;

    public CompanyRoutesView() throws IOException {
       // busses = new BusList();
        //Bus bus2 = new Bus("Parkway", "Vancouver", "Hamilton", "20");
       // Bus bus3 = new Bus("International", "Brampton", "Regina", "15");
        //Bus bus4 = new Bus("BooLoo", "Brampton", "Regina", "15");
       // Bus bus5 = new Bus("International", "Brampton", "Regina", "15");
       // Bus bus6 = new Bus("International", "Brampton", "Regina", "15");
        //Bus bus7 = new Bus("International", "Brampton", "Regina", "15");
         //Bus bus8 = new Bus("International", "Brampton", "Regina", "15");
        //Bus bus9 = new Bus("International", "Brampton", "Regina", "15");
       // Bus bus10 = new Bus("International", "Brampton", "Regina", "15");

        // busses.insert(bus);
      //  busses.insert(bus2);
      //  busses.insert(bus3);
       // busses.insert(bus4);
       // busses.insert(bus5);
       // busses.insert(bus6);
       // busses.insert(bus7);
        //busses.insert(bus8);
       // busses.insert(bus9);
        //busses.insert(bus10);

        //busList = Arrays.asList(busses.getList());

        select = new Select<>();
        select.setItems("Bus ID", "Start Destination", "End Destination");
        select.setValue("Bus ID");
        searchbar = new TextField();
        searchbar.setPlaceholder("Search Criteria");
        searchbar.setPrefixComponent(VaadinIcon.SEARCH.create());
        search = new Button("Search");
        clear = new Button("Clear Filters");
        addRoute = new Button ("Add a Route", event -> { // Company action event
            UI.getCurrent().navigate("companyAddRouteView"); // Send user to register route
        });
        table = new Grid<>();
        //table.setItems(busList);
      //  table.addColumn(Bus::getBusID).setHeader("Bus ID");
        table.addColumn(Bus::getStartDestination).setHeader("Start Destination");
        table.addColumn(Bus::getEndDestination).setHeader("End Destination");
        table.addColumn(Bus::getDistance).setHeader("Distance");
        table.addColumn(Bus::getTimeInMinutes).setHeader("Travel Time");
        table.addComponentColumn(item -> {
            Button btn = new Button("Cancel");
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
