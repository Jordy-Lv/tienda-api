package com.tienda.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tienda.api.entity.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
