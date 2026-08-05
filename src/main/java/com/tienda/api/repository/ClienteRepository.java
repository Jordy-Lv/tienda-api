package com.tienda.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tienda.api.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByEmail(String email);

    boolean existsByEmail(String email);
}
