
Bus Reservation Web App

Running the project:

To run the project, navigate to the "BusApplication" class and click "run". If you do not run the program
through this class, the project will fail to run as all the classes are interconnected and are routed from the initial
main route which is only available from running this class. The "BusApplication" class can be found in
"Bus-Reservation-Web-App/src/main/java/com.web.bus"

Running Application on LocalHost:
Once you have run the "BusApplication" class, if your web browser local host was not automatically initiated,
please go to your web browser and type in "localhost:8080" and hit enter. Note, please wait a for seconds before
hitting enter after you have typed in "localhost:8080" to give the application some time to compile the frontend and
backend of the application. Once the application had compiled onto the local lost, you will be at the login screen. Either
login as a customer or as a company.

NOTE: You must proceed through this login screen in order to access the other account related pages/views. If you attempt to
access these pages directly, the application will not function properly as the customer information is carried through the login page
into the GUIs/pages related to each customer/company account.

Few Program Notes:
1. You may notice some classes do not have a self testing main method. This is because these are vaadin web application views and hence, these
views cannot be run by themselves and must be viewed through navigating to the respective routes after logging in. Vaadin web application does
not function in the sense where individual route GUIs can be tested in a self testing main method and can only be accessed through the various routings
once the "BusApplication" class was run which starts up the entire program. Such classes that cannot be tested are located in the "views" folder located in
"Bus-Reservation-Web-App/src/main/java/com.web.bus/views" and similar case classes located in the "services" folder
("Bus-Reservation-Web-App/src/main/java/com.web.bus/services" and classes located in the "components" folder located at
"Bus-Reservation-Web-App/src/main/java/com.web.bus/components". These classes are meant simply or components and database data fetching and writing which, once again,
is not able to be tested through a main method self testing method in vaadin unlike other simplier applications.
2. Some other classes do have self testing main methods such as "Bus" and "BusList". This is because these are objects which are created purely containing data/information
and hence, different methods to read and write this data, calculations, formatting and reading/writing to a file is tested in the main method.
3. There are many auto-generated classes, mainly located outside of the "src" folder, which must be there for the functioning of this web application. Hence, please do not tamper
or edit any of these files.
4. There are components used within some GUI classes within the "views" folder (route specified before) which are provided by vaadin. These are similar to the default Java provided
classes however, this is, obviously, provided by vaadin. These components (mainly the GUIs extending from) is crucial for the web application functionality, hence, please do not try to tamper
with this or experiment this.

Hope you enjoy your time with experimenting/utilizing our web application which took a lot of blood, sweat and tears from our side, trying to make this a
memorable experience for you.

Sincerely,
Bus Reservation System Web App Developers
Ashwin Santhosh & Daksh Khanna

Sources:
Grid/Table: https://vaadin.com/docs/latest/components/grid
            https://vaadin.com/forum/thread/194428/table-column-formatting
Framework Development of Web App: https://www.youtube.com/watch?v=TGSDz-_dNhI&t=357s

Annotations:
# Annotations Used in Repository Classes
# @Repository is a Spring annotation that indicates that this interface is a repository. This annotation is used to mark the interface as a repository, allowing the Spring Framework to pick it up and create an implementation at runtime.
# @Transactional is used to indicate that a method or a class is transactional. This annotation is used to mark the methods that change the database, such as saveAndFlush, replace, and deleteByIdAndUsername methods.
# @Modifying is a Spring Data JPA annotation that is used in combination with the @Transactional annotation to indicate that a query modifies the database. It is used on the replace method because it modifies the customer data.
# @Query is a Spring Data JPA annotation that allows you to use a custom JPQL query for a repository method. It is used on the replace method to define the query that updates the customer.
# @Param is a Spring Data JPA annotation that allows you to bind a named parameter to a method query. It is used in the replace method to bind the newC and id parameters.



