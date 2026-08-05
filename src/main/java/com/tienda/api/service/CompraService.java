package com.tienda.api.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tienda.api.dto.ClienteResponse;
import com.tienda.api.dto.CompraRequest;
import com.tienda.api.dto.CompraResponse;
import com.tienda.api.dto.ProductoResponse;
import com.tienda.api.entity.Cliente;
import com.tienda.api.entity.Compra;
import com.tienda.api.entity.Producto;
import com.tienda.api.exception.ResourceNotFoundException;
import com.tienda.api.exception.StockInsuficienteException;
import com.tienda.api.repository.CompraRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CompraService {

    private final CompraRepository compraRepository;
    private final ClienteService clienteService;
    private final ProductoService productoService;

    public CompraResponse crear(CompraRequest request) {
        Cliente cliente = clienteService.buscarClienteOLanzar(request.getClienteId());
        Producto producto = productoService.buscarProductoOLanzar(request.getProductoId());

        if (producto.getStock() < request.getCantidad()) {
            throw new StockInsuficienteException(
                    "Stock insuficiente para el producto: " + producto.getNombre()
                            + ". Disponible: " + producto.getStock());
        }

        producto.setStock(producto.getStock() - request.getCantidad());

        BigDecimal total = producto.getPrecio().multiply(BigDecimal.valueOf(request.getCantidad()));

        Compra compra = Compra.builder()
                .cliente(cliente)
                .producto(producto)
                .cantidad(request.getCantidad())
                .total(total)
                .fechaCompra(LocalDateTime.now())
                .build();

        return toResponse(compraRepository.save(compra));
    }

    @Transactional(readOnly = true)
    public List<CompraResponse> listar() {
        return compraRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public CompraResponse obtenerPorId(Long id) {
        Compra compra = compraRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Compra no encontrada con id: " + id));
        return toResponse(compra);
    }

    @Transactional(readOnly = true)
    public List<CompraResponse> listarPorCliente(Long clienteId) {
        clienteService.buscarClienteOLanzar(clienteId);
        return compraRepository.findByClienteId(clienteId).stream()
                .map(this::toResponse)
                .toList();
    }

    private CompraResponse toResponse(Compra compra) {
        ClienteResponse clienteResponse = ClienteResponse.builder()
                .id(compra.getCliente().getId())
                .nombre(compra.getCliente().getNombre())
                .email(compra.getCliente().getEmail())
                .telefono(compra.getCliente().getTelefono())
                .direccion(compra.getCliente().getDireccion())
                .build();

        ProductoResponse productoResponse = ProductoResponse.builder()
                .id(compra.getProducto().getId())
                .nombre(compra.getProducto().getNombre())
                .descripcion(compra.getProducto().getDescripcion())
                .precio(compra.getProducto().getPrecio())
                .stock(compra.getProducto().getStock())
                .build();

        return CompraResponse.builder()
                .id(compra.getId())
                .cliente(clienteResponse)
                .producto(productoResponse)
                .cantidad(compra.getCantidad())
                .total(compra.getTotal())
                .fechaCompra(compra.getFechaCompra())
                .build();
    }
}
