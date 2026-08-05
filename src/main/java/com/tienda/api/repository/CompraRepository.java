package com.tienda.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tienda.api.entity.Compra;

public interface CompraRepository extends JpaRepository<Compra, Long> {

    List<Compra> findByClienteId(Long clienteId);
}
