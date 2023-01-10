package com.web.bus.services;

import com.web.bus.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/*
 * @author: Daksh & Ashwin
 * Date: Jan. 2023
 * Description: Interface to use Jpa Repository to save and retrieve companies
 */
@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    // Create new query to find company by name
    Company findByName(String name);
}
