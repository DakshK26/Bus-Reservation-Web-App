package com.web.bus.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

/**
 * @author: Daksh & Ashwin
 * Date: Jan. 2023
 * Description: This class is the main layout for the company user. It contains the navigation bar and the drawer.
 * Method List:
 * public CompanyMainLayout()
 * private void createNavbar()
 * private void createDrawer()
 */
@Route("companyMainView")
public class CompanyMainLayout extends AppLayout {
    /*
    Private Instance Data
     */
    HorizontalLayout header;
    RouterLink companyMainLink, companyRoutesLink;
    private Button logout;
    /*
    Constructor to build the toggle menu and the header
     */
    public CompanyMainLayout() {
        createNavbar();
        createDrawer();
    }

    /*
    Constructor to create the header/navbar
     */
    private void createNavbar() {
        //title
        H1 title = new H1("Bus Reservation Web App");
        title.getStyle().set("font-size", "var(--lumo-font-size-l)")
                .set("margin", "0");
        //format header
        header = new HorizontalLayout(new DrawerToggle());
        header.addClassName("header");
        header.setWidth("3%");
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        //logout button
        logout = new Button("Logout");
        logout.getStyle().set("margin-left", "20px");
        logout.addClickListener(event -> {
            UI.getCurrent().getSession().close();
            UI.getCurrent().navigate(""); //navigate back to main route
        });
        //add components to navbar
        addToNavbar(header, title, logout);
    }

    /*
    Create the toggle menu
     */
    private void createDrawer() {
        //add the alternate routes links
        companyMainLink = new RouterLink("View All Buses", CompanyHomeView.class);
        companyMainLink.setHighlightCondition(HighlightConditions.sameLocation());
        companyRoutesLink = new RouterLink("View Company Active Routes", CompanyRoutesView.class);
        companyRoutesLink.setHighlightCondition(HighlightConditions.sameLocation());
        //add components to the menu
        addToDrawer(new VerticalLayout(
                companyMainLink,
                companyRoutesLink
        ));
    }

}
