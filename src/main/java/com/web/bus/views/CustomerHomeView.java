package com.web.bus.views;

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
import com.web.bus.entities.Bus;
import com.web.bus.entities.BusList;

import java.io.IOException;
import java.util.List;

@Route(value = "customerHomeView", layout = CustomerMainLayout.class)
@PageTitle("CustomerHome")
public class CustomerHomeView extends VerticalLayout {
    private Button search;
    private TextField searchbar;
    private Select<String> select;
    private Grid<Bus> table;
    private BusList busses;

    private List<Bus> busList;

    public CustomerHomeView () throws IOException {
        //busses = new BusList();
        //Bus bus = new Bus("Freeway", "Toronto", "Ottawa", "10");
        //Bus bus2 = new Bus("Parkway", "Vancouver", "Hamilton", "20");
       // Bus bus3 = new Bus("International", "Brampton", "Regina", "15");
        //Bus bus4 = new Bus("BooLoo", "Brampton", "Regina", "15");
       // Bus bus5 = new Bus("International", "Brampton", "Regina", "15");
       // Bus bus6 = new Bus("International", "Brampton", "Regina", "15");
        //Bus bus7 = new Bus("International", "Brampton", "Regina", "15");
      //  Bus bus8 = new Bus("International", "Brampton", "Regina", "15");
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
        table = new Grid<>();
        //table.setItems(busList);
      //  table.addColumn(Bus::getBusID).setHeader("Bus ID");
        table.addColumn(Bus::getStartDestination).setHeader("Start Destination");
        table.addColumn(Bus::getEndDestination).setHeader("End Destination");
        table.addColumn(Bus::getDistance).setHeader("Distance");
        table.addColumn(Bus::getTimeInMinutes).setHeader("Travel Time");

        table.addComponentColumn(item -> {
            Button btn = new Button("Book");
            return btn;
                }).setHeader("Book Bus");

        table.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);

        add (
                new H1("Welcome to the Bus Reservation System!"),
                new HorizontalLayout(new H4("Search: "), select, searchbar, search),
                table
                );
       search.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_CONTRAST);
    }
}
