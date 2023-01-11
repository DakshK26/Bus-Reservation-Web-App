package com.web.bus.services;

import com.web.bus.entities.Company;
import com.web.bus.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/*
 * @author: Daksh & Ashwin
 * Date: Jan. 2023
 * Description: Interface to use Jpa Repository to save and retrieve companies
 */
@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    /**
     * Custom save method that saves an entity and flush the changes to the database.
     * @param entity the entity to save
     * @param <S> the type of the entity
     * @return the saved entity
     */
   // @Transactional
   // <S extends Company> S saveAndFlush(S entity);

    /**
     * Custom replace method that updates a Company with a new Company
     * @param newCompany the new Company
     * @param id the id of the Company to be updated
     */
  //  @Transactional
   // @Modifying
   // @Query("update Company c set c = :newC where c.id = :id")
  //  void replace(@Param("newC") Company newCompany, @Param("id") Long id);

    /**
     * Custom delete method that deletes a customer by id and username
     * @param id the id of the customer to be deleted
     * @param username the username of the customer to be deleted
     */
  //  @Transactional
  //  void deleteByIdAndUsername(Long id, String username);

    /**
     * Custom find method that finds a customer by name
     * @param name the name of the customer
     * @return the customer
     */
 //   Optional<Company> findByName(String name);
}
