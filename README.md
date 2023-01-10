# Bus Reservation Web App

# Annotations Used in Repository Classes
# @Repository is a Spring annotation that indicates that this interface is a repository. This annotation is used to mark the interface as a repository, allowing the Spring Framework to pick it up and create an implementation at runtime.
# @Transactional is used to indicate that a method or a class is transactional. This annotation is used to mark the methods that change the database, such as saveAndFlush, replace, and deleteByIdAndUsername methods.
# @Modifying is a Spring Data JPA annotation that is used in combination with the @Transactional annotation to indicate that a query modifies the database. It is used on the replace method because it modifies the customer data.
# @Query is a Spring Data JPA annotation that allows you to use a custom JPQL query for a repository method. It is used on the replace method to define the query that updates the customer.
# @Param is a Spring Data JPA annotation that allows you to bind a named parameter to a method query. It is used in the replace method to bind the newC and id parameters.

