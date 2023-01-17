package com.web.bus.security;

import java.util.Collection;

import com.web.bus.entities.Customer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/*
 * @author: Daksh & Ashwin
 * Date: Jan. 2023
 * Description: Class to represent the authenticated user's information
 */
public class CustomerPrincipal implements UserDetails {
    private Customer customer;

    public CustomerPrincipal(Customer customer) {
        this.customer = customer;
    }

    /*
     * Method to get the customer's username
     * @return the customer's username
     */
    @Override
    public String getUsername() {
        return customer.getUsername();
    }

    /*
     * Method to get the customer's password
     * @return the customer's password
     */
    @Override
    public String getPassword() {
        return customer.getPassword();
    }

    /*
     * Method to get the customer's authorities
     * @return the customer's authorities
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    /*
     * Method to check if the customer's account is expired
     * @return true if the customer's account is expired, false otherwise
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /*
     * Method to check if the customer's account is locked
     * @return true if the customer's account is locked, false otherwise
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /*
     * Method to check if the customer's credentials are expired
     * @return true if the customer's credentials are expired, false otherwise
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /*
     * Method to check if the customer's account is enabled
     * @return true if the customer's account is enabled, false otherwise
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
