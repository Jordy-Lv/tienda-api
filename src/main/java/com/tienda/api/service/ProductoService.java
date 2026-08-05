package com.tienda.api.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tienda.api.dto.ProductoRequest;
import com.tienda.api.dto.ProductoResponse;
import com.tienda.api.entity.Producto;
import com.tienda.api.exception.ResourceNotFoundException;
import com.tienda.api.repository.ProductoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoResponse crear(ProductoRequest request) {
        Producto producto = Producto.builder()
                .nombre(request.getNombre())
                .descripcion(request.getDescripcion())
                .precio(request.getPrecio())
                .stock(request.getStock())
                .build();

        return toResponse(productoRepository.save(producto));
    }

    @Transactional(readOnly = true)
    public List<ProductoResponse> listar() {
        return productoRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProductoResponse obtenerPorId(Long id) {
        return toResponse(buscarProductoOLanzar(id));
    }

    public ProductoResponse actualizar(Long id, ProductoRequest request) {
        Producto producto = buscarProductoOLanzar(id);

        producto.setNombre(request.getNombre());
        producto.setDescripcion(request.getDescripcion());
        producto.setPrecio(request.getPrecio());
        producto.setStock(request.getStock());

        return toResponse(productoRepository.save(producto));
    }

    public void eliminar(Long id) {
        Producto producto = buscarProductoOLanzar(id);
        productoRepository.delete(producto);
    }

    Producto buscarProductoOLanzar(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + id));
    }

    private ProductoResponse toResponse(Producto producto) {
        return ProductoResponse.builder()
                .id(producto.getId())
                .nombre(producto.getNombre())
                .descripcion(producto.getDescripcion())
                .precio(producto.getPrecio())
                .stock(producto.getStock())
                .build();
    }
}
