package com.web.bus.repository;

import com.web.bus.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    Optional<Company> findByEmail(String email);

    Optional<Company> findByName(String name);

    boolean existsByEmail(String email);

    boolean existsByName(String name);
}
