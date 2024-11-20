package com.example.serviceadmin.repository;

import com.example.serviceadmin.entity.admin;
import org.springframework.data.jpa.repository.JpaRepository;
@RepositoryRestResource
public interface adminrepository extends JpaRepository<admin,Long> {
}
