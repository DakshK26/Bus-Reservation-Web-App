package com.web.bus.services;

import com.web.bus.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/*
 * @author: Daksh & Ashwin
 * Date: Jan. 2023
 * Description: Repository interface for managing {@link Company} entities
 */
@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    /**
     * Custom save method that saves an entity and flush the changes to the database.
     * @param entity the entity to save
     * @param <S> the type of the entity
     * @return the saved entity
     */
   @Transactional
   <S extends Company> S saveAndFlush(S entity);

    /**
     * Custom replace method that updates a Company with a new Company
     * @param newCompany the new Company
     */
    @Transactional
    @Modifying
    @Query("update Company c set c = :newC where c.company = :company")
    void replace(@Param("newC") Company newCompany, @Param("company") String company);

    /**
     * Custom delete method that deletes a company by id and username
     * @param company the username of the company to be deleted
     */
    @Transactional
    void deleteByCompany(String company);

    /**
     * Custom find method that finds a company by name
     * @param company the name of the company
     * @return the company
     */
    Optional<Company> findByCompany(String company);
}
