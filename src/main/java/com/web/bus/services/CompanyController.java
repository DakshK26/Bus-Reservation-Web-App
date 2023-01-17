package com.web.bus.services;
import com.web.bus.entities.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    @Autowired
    private CompanyRepository companyRepository;

    /**
     * Save a new company to the repository
     * @param company the company to be saved
     * @return the saved company
     */
    @PostMapping
    public Company saveCompany(@RequestBody Company company){
        return companyRepository.saveAndFlush(company);
    }

    /**
     * Update an existing company in the repository
     * @param newCompany the new company information
     * @param company the name of the company to be updated
     */
    @PutMapping("/{company}")
    public void updateCompany(@RequestBody Company newCompany, @PathVariable String company){
        companyRepository.replace(newCompany, company);
    }

    /**
     * Delete a company from the repository
     * @param company the name of the company to be deleted
     */
    @DeleteMapping("/{company}")
    public void deleteCompany(@PathVariable String company){
        companyRepository.deleteByCompany(company);
    }

    /**
     * Find a company by name
     * @param company the name of the company
     * @return the found company
     */
    @GetMapping("/{company}")
    public Optional<Company> findCompany(@PathVariable String company){
        return companyRepository.findByCompany(company);
    }
}

