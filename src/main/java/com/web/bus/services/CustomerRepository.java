package com.web.bus.services;

import com.web.bus.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author: Daksh & Ashwin
 * Date: Jan. 2023
 * Description: Repository interface for managing {@link Customer} entities
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    /**
     * Custom save method that saves an entity and flush the changes to the database.
     * @param entity the entity to save
     * @param <S> the type of the entity
     * @return the saved entity
     */
    @Transactional
    <S extends Customer> S saveAndFlush(S entity);

    /**
     * Custom replace method that updates a customer with a new customer
     * @param newCustomer the new customer
     * @param username the username of the customer to be updated
     */
    @Transactional
    @Modifying
    @Query("update Customer c set c = :newC where c.username = :username")
    void replaceByUsername(@Param("newC") Customer newCustomer, @Param("username") String username);

    /**
    * Custom delete method that deletes a customer by id and username
     * @param username the id of the customer to be deleted
     */
    @Transactional
    void deleteByUsername(String username);

    /**
     * Custom find method that finds a customer by username
     * @param username the username of the customer
     * @return the customer
     */
    Optional<Customer> findByUsername(String username);

    /**
     * Check if a customer with the given username exists in the repository.
     *
     * @param username the username of the customer to search for
     * @return true if a customer with the given username exists, false otherwise
     */
    boolean existsByUsername(String username);
}
