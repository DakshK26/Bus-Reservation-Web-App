package com.web.bus.services;

import com.web.bus.entities.Customer;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
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
     * Method to check if a customer exists by username
     * @param username the username of the customer to be checked
     * @return a boolean indicating whether the customer exists
     */
    @Query("select (count(c) > 0) from Customer c where c.username = ?1")
    boolean existsByUsername(String username);

    /**
     * Method to find a customer by username
     * @param username the username of the customer to be found
     * @return the customer with the given username
     */
    @Query("select c from Customer c where c.username = ?1")
    Optional<Customer> findByUsername(String username);


}
