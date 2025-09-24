# Bus Reservation Web App

Running the project:

Recommended IDE: IntelliJ IDEA
Recommended Plugin For Eclipse: Maven Integration for Eclipse (M2E) 1.17.0 - https://www.eclipse.org/m2e/ (Not needed at all, just improves project structure formatting to make project more readable)
Recommended Java Version (JDK): 11.0.8


To run the project, navigate to the "BusApplication" class and click "run". If you do not run the program
through this class, the project will fail to run as all the classes are interconnected and are routed from the initial
main route which is only available from running this class. The "BusApplication" class can be found in
"Bus-Reservation-Web-App/src/main/java/com.web.bus"

Running Application on LocalHost:
(Local Host will automatically initiate aslong as you are using a windows devicve and have the latest version of Java installed)
Once you have run the "BusApplication" class, if your web browser local host was not automatically initiated,
please go to your web browser and type in "localhost:8080" and hit enter. Note, please wait a for seconds before
hitting enter after you have typed in "localhost:8080" to give the application some time to compile the frontend and
backend of the application. Once the application had compiled onto the local lost, you will be at the login screen. Either
login as a customer or as a company.

NOTE: You must proceed through this login screen in order to access the other account related pages/views. If you attempt to
access these pages directly, the application will not function properly as the customer information is carried through the login page
into the GUIs/pages related to each customer/company account.

Booking A Bus Through Company Home View:
TO BOOK A BUS AS A CUSTOMER FROM THE HOME PAGE, click on the row with th desired bus route and it will redirect you to the purchase
screen. NOTE, THERE IS NO BOOK BUTTON, simply click on the row with the bus you desire to book.

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
5. There are some classes which are not used in the web application but are used for testing purposes. These classes are located in the "test" folder located at
"Bus-Reservation-Web-App/src/test/java/com.web.bus/test". These classes are used to test the functionality of the "Bus" and "BusList" classes which are located in the "main" folder

Hope you enjoy your time with experimenting/utilizing our web application which took a lot of blood, sweat and tears from our side, trying to make this a
memorable experience for you.

Sincerely,
Bus Reservation System Web App Developers
Ashwin Santhosh & Daksh Khanna

Sources:
Grid/Table: https://vaadin.com/docs/latest/components/grid
            https://vaadin.com/forum/thread/194428/table-column-formatting
Framework Development of Web App: https://www.youtube.com/watch?v=TGSDz-_dNhI&t=357s
Understanding Vaadin: https://www.youtube.com/watch?v=Z8Y2BZxhNvM&t=1s
Vaadin Components: https://vaadin.com/components
Springboot Security: https://www.youtube.com/watch?v=her_7pa0vrg
Springboot: https://www.youtube.com/watch?v=9SGDpanrc8U


Annotations:
Annotations Used in Repository Classes
@Repository is a Spring annotation that indicates that this interface is a repository. This annotation is used to mark the interface as a repository, allowing the Spring Framework to pick it up and create an implementation at runtime.
@Transactional is used to indicate that a method or a class is transactional. This annotation is used to mark the methods that change the database, such as saveAndFlush, replace, and deleteByIdAndUsername methods.
@Modifying is a Spring Data JPA annotation that is used in combination with the @Transactional annotation to indicate that a query modifies the database. It is used on the replace method because it modifies the customer data.
@Query is a Spring Data JPA annotation that allows you to use a custom JPQL query for a repository method. It is used on the replace method to define the query that updates the customer.
@Param is a Spring Data JPA annotation that allows you to bind a named parameter to a method query. It is used in the replace method to bind the newC and id parameters.
@Entity is a JPA annotation that indicates that this class is an entity. It is used to mark the Customer class as an entity.
@Id is a JPA annotation that indicates that this field is the primary key of the entity. It is used to mark the id field as the primary key.
@GeneratedValue is a JPA annotation that indicates that the primary key is generated automatically. It is used to mark the id field as auto-generated.
@Column is a JPA annotation that indicates that this field is a column in the database. It is used to mark the username, password, and email fields as columns in the database.
@Table is a JPA annotation that indicates that this class is a table in the database. It is used to mark the Customer class as a table in the database.
@Component is a Spring annotation that indicates that this class is a component. It is used to mark the CustomerService class as a component.
@Autowired is a Spring annotation that allows you to inject a bean into another bean. It is used to inject the CustomerRepository bean into the CustomerService class.
@Service is a Spring annotation that indicates that this class is a service. It is used to mark the CustomerService class as a service.
@RestController is a Spring annotation that indicates that this class is a REST controller. It is used to mark the CustomerController class as a REST controller.
@RequestMapping is a Spring annotation that maps HTTP requests to handler methods of MVC and REST controllers. It is used to map the HTTP requests to the controller methods.
@GetMapping is a Spring annotation that maps HTTP GET requests onto specific handler methods. It is used to map the HTTP GET requests to the controller methods.
@PostMapping is a Spring annotation that maps HTTP POST requests onto specific handler methods. It is used to map the HTTP POST requests to the controller methods.
@PutMapping is a Spring annotation that maps HTTP PUT requests onto specific handler methods. It is used to map the HTTP PUT requests to the controller methods.
@DeleteMapping is a Spring annotation that maps HTTP DELETE requests onto specific handler methods. It is used to map the HTTP DELETE requests to the controller methods.
@EnableJpaRepositories(basePackages = "com.web.bus.services") is a Spring annotation that enables JPA repositories. It is used to enable the JPA repositories.
@EntityScan(basePackages = "com.web.bus.entities") is a Spring annotation that enables scanning of JPA entities. It is used to enable the scanning of JPA entities.

Explanations of each folder:
.idea - This folder contains the project settings and configurations for the IntelliJ IDEA IDE. It is not necessary to open the project in IntelliJ IDEA, but it is recommended.
.mvn - This folder contains the Maven wrapper files. It is not necessary to use the Maven wrapper, but it is recommended.
.settings - This folder contains the project settings and configurations for the Eclipse IDE. It is not necessary to open the project in Eclipse, but it is recommended.
frontend - This folder contains the frontend code for the application. It has the css, html, and js files. It also has the node_modules folder that contains the frontend dependencies.
node_modules - This folder contains the frontend dependencies. It is not necessary to use the frontend dependencies, but it is recommended.
src - This folder contains the source code for the application. It has the main and test folders. The main folder has the java and resources folders. The java folder has the com.web.bus package. The resources folder has the static and templates folders. The static folder has the css, images, and js folders. The templates folder has the html files.
target - This folder contains the compiled code for the application. It is not necessary to compile the code, but it is recommended.
out - This folder contains the compiled code for the application. It is not necessary to compile the code, but it is recommended.


Explanations of each file:
pom.xml - This file contains the project dependencies and configurations for the Maven build tool. It is not necessary to use the Maven build tool, but it is recommended.
package.json - This file contains the project dependencies and configurations for the Node.js build tool. It is not necessary to use the Node.js build tool, but it is recommended.
package-lock.json - This file contains the project dependencies and configurations for the Node.js build tool. It is not necessary to use the Node.js build tool, but it is recommended.
.gitignore - This file contains the files and folders that should be ignored by Git. It is not necessary to use Git, but it is recommended.
mydb.mv.db - This file contains the database for the application. It is not necessary to use the database, but it is recommended.
application.properties - This file contains the project configurations for the Spring Boot framework.
allBuses.txt - This file contains the data for the buses in the database.
.classpath - This file contains the project settings and configurations for the Eclipse IDE. It is not necessary to open the project in Eclipse, but it is recommended.
tsconfig.json - This file contains the project configurations for the TypeScript build tool. It is not necessary to use the TypeScript build tool, but it is recommended.
tslint.json - This file contains the project configurations for the TypeScript build tool. It is not necessary to use the TypeScript build tool, but it is recommended.
webpack.config.js - This file contains the project configurations for the Webpack build tool. It is not necessary to use the Webpack build tool, but it is recommended.
.iml - This file contains the project settings and configurations for the IntelliJ IDEA IDE. It is not necessary to open the project in IntelliJ IDEA, but it is recommended.
mvnw - This file contains the Maven wrapper. It is not necessary to use the Maven wrapper, but it is recommended.
vite.config.ts - This file contains the project configurations for the Vite build tool. It is not necessary to use the Vite build tool, but it is recommended.
vite.generated.d.ts - This file contains the project configurations for the Vite build tool. It is not necessary to use the Vite build tool, but it is recommended.

All Dependencies Used in the Project:
Google Gson - This dependency is used to convert Java objects to JSON and vice versa. It uses the google maps api library to get the coordinates and travel time between locations
Vaadin - This dependency is used to create the frontend for the application. It uses the vaadin library to create the frontend.
Spring Boot - This dependency is used to create the backend for the application. It uses the spring boot library to create the backend.
Spring Data JPA - This dependency is used to create the database for the application. It uses the spring data jpa library to create the database.
H2 Database - Creates a in-memory database for the application. To be used with Spring Data JPA.
Spring Web - This dependency is used to create the REST API for the application. It uses the spring web library to create the REST API.
Spring Boot DevTools - Creates tools like live reload for the application. To be used with Spring Boot.
Spring Boot Starter Test - This dependency is used to create the unit tests for the application. It uses the spring boot starter test library to create the unit tests.
