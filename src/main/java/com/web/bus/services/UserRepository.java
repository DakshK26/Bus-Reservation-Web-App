package com.web.bus.services;

import com.web.bus.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/*
 * @author: Daksh & Ashwin
 * Date: Jan. 2023
 * Description: Interface to use Jpa Repository to save and retrieve customers
 */
public interface UserRepository extends JpaRepository<Customer, Long> {
    // Create new query to find customer by name
    @Query("SELECT c FROM Customer c WHERE c.name = :name")
    Customer findByName(String name);

}