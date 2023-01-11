package com.web.bus.services;

import com.web.bus.entities.Customer;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
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
public interface UserRepository extends CrudRepository<Customer, Long> {

    /**
     * Custom save method that saves an entity and flush the changes to the database.
     * @param entity the entity to save
     * @param <S> the type of the entity
     * @return the saved entity
     */
  //  @Transactional
  //  <S extends Customer> S saveAndFlush(S entity);

    /**
     * Custom replace method that updates a customer with a new customer
     * @param newCustomer the new customer
     * @param id the id of the customer to be updated
     */
 //   @Transactional
 //   @Modifying
 //   @Query("update Customer c set c = :newC where c.id = :id")
 //   void replace(@Param("newC") Customer newCustomer, @Param("id") Long id);

    /**
    * Custom delete method that deletes a customer by id and username
     * @param id the id of the customer to be deleted
     * @param name the username of the customer to be deleted
     */
//    @Transactional
//    void deleteByIdAndUsername(Long id, String name);

    /**
     * Custom find method that finds a customer by name
     * @param name the name of the customer
     * @return the customer
     */
//    Optional<Customer> findByName(String name);
}
