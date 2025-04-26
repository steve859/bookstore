package com.bookstore.repository;

import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookstore.entity.Roles;

@Repository
public interface RoleRepository extends JpaRepository<Roles, String> {

}
