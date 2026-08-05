package com.tienda.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tienda.api.dto.CompraRequest;
import com.tienda.api.dto.CompraResponse;
import com.tienda.api.service.CompraService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/compras")
@RequiredArgsConstructor
public class CompraController {

    private final CompraService compraService;

    @PostMapping
    public ResponseEntity<CompraResponse> crear(@Valid @RequestBody CompraRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(compraService.crear(request));
    }

    @GetMapping
    public ResponseEntity<List<CompraResponse>> listar() {
        return ResponseEntity.ok(compraService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompraResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(compraService.obtenerPorId(id));
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<CompraResponse>> listarPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(compraService.listarPorCliente(clienteId));
    }
}
