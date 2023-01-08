package com.web.bus.services;

import com.web.bus.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;

/*
 * @author: Daksh & Ashwin
 * Date: Jan. 2023
 * Description: Interface to use Jpa Repository to save and retrieve companies
 */
public interface CompanyRepository extends JpaRepository<Company, Long> {

}
