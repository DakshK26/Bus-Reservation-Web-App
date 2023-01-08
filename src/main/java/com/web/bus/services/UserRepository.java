package com.web.bus.services;

import com.web.bus.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
/*
 * @author: Daksh & Ashwin
 * Date: Jan. 2023
 * Description: Interface to use Jpa Repository to save and retrieve customers
 */
public interface UserRepository extends JpaRepository<Customer, Long> {
}
