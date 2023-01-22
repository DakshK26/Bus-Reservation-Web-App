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
     * Method to find a company by email
     * @param email the email of the company to be found
     * @return the company with the given email
     */
    Optional<Company> findByEmail(String email);

    /**
     * Method to check if a company exists by email
     * @param email the email of the company to be checked
     * @return a boolean indicating whether the company exists
     */
    @Query("select (count(c) > 0) from Company c where c.email = ?1")
    boolean existsByEmail(String email);




}
