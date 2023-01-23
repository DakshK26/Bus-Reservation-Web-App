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
 * date: Jan. 2023
 * Description: This class is the layout for the drawer and navbar for the customer
 * Method List: public CustomerMainLayout()
 *             private void createNavbar()
 *             private void createDrawer()
 */
@Route("customerMainView")
public class CustomerMainLayout extends AppLayout {

    /*
    Private Instance Data
     */
    private RouterLink customerMainLink, customerHistoryLink;
    private  HorizontalLayout header;
    private Button logout;

    /*
    Constructor to build the navigation menu and header
     */
    public CustomerMainLayout() {
        createNavbar();
        createDrawer();
    }

    /*
    Constructor to build the Navbar
     */
    private void createNavbar() {
        //title
        H1 title = new H1("Bus Reservation Web App");  // Title of the page
        title.getStyle().set("font-size", "var(--lumo-font-size-l)")
                .set("margin", "0");
        //format header
        header = new HorizontalLayout(new DrawerToggle()); // Drawer toggle button
        header.addClassName("header");
        header.setWidth("3%");
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        //logout button
        logout = new Button("Logout"); // Logout button
        logout.getStyle().set("margin-left", "20px");
        logout.addClickListener(event -> { // When logout button is clicked, close the session and navigate to the login page
            UI.getCurrent().navigate("");
        });
        //add components to navbar
        addToNavbar(header, title, logout); // Add the title and logout button to the navbar
    }

    /*
    Constructor to build the toggle menu
     */
    private void createDrawer() { // Create the drawer
        // add the alternate routes links
       customerMainLink = new RouterLink("Book A Bus", CustomerHomeView.class);
        customerMainLink.setHighlightCondition(HighlightConditions.sameLocation());
        customerHistoryLink = new RouterLink("View Past Purchases", CustomerHistoryView.class);
        customerHistoryLink.setHighlightCondition(HighlightConditions.sameLocation());
        //add components to the toggle menu
        addToDrawer(new VerticalLayout( // Add the links to the drawer
                customerMainLink,
                customerHistoryLink
        ));
    }

}
