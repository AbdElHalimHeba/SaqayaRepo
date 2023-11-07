package com.saqaya.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saqaya.entity.User;

/**
 * 
 * <h1>UserRepository Class</h1> 
 * <p>JPA CRUD operations for User.</p>
 *
 * @author Heba Abd El-Halim
 *
 */
public interface UserRepository extends JpaRepository<User, String> {

}