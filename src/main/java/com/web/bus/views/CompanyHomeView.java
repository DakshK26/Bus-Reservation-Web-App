package com.web.bus.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "companyHomeView", layout = CustomerMainLayout.class)
@PageTitle("CompanyHome")
public class CompanyHomeView extends VerticalLayout {
    private Button search;
    private TextField searchbar;
    private Select<String> select;

    public CompanyHomeView() {
        select = new Select<>();
        select.setItems("Bus ID", "Start Destination", "End Destination");
        select.setValue("Bus ID");
        searchbar = new TextField();
        searchbar.setPlaceholder("Search Criteria");
        searchbar.setPrefixComponent(VaadinIcon.SEARCH.create());
        search = new Button("Search");

        add (
                new H1("Welcome to the Bus Reservation System!"),
                new HorizontalLayout(new H4("Search: "), select, searchbar, search)
                );
       search.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_CONTRAST);
    }
}
