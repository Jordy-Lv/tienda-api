package com.tienda.api.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tienda.api.dto.ClienteRequest;
import com.tienda.api.dto.ClienteResponse;
import com.tienda.api.entity.Cliente;
import com.tienda.api.exception.EmailDuplicadoException;
import com.tienda.api.exception.ResourceNotFoundException;
import com.tienda.api.repository.ClienteRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteResponse crear(ClienteRequest request) {
        if (clienteRepository.existsByEmail(request.getEmail())) {
            throw new EmailDuplicadoException("Ya existe un cliente con el email: " + request.getEmail());
        }

        Cliente cliente = Cliente.builder()
                .nombre(request.getNombre())
                .email(request.getEmail())
                .telefono(request.getTelefono())
                .direccion(request.getDireccion())
                .build();

        return toResponse(clienteRepository.save(cliente));
    }

    @Transactional(readOnly = true)
    public List<ClienteResponse> listar() {
        return clienteRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public ClienteResponse obtenerPorId(Long id) {
        return toResponse(buscarClienteOLanzar(id));
    }

    public ClienteResponse actualizar(Long id, ClienteRequest request) {
        Cliente cliente = buscarClienteOLanzar(id);

        clienteRepository.findByEmail(request.getEmail())
                .filter(existente -> !existente.getId().equals(id))
                .ifPresent(existente -> {
                    throw new EmailDuplicadoException("Ya existe un cliente con el email: " + request.getEmail());
                });

        cliente.setNombre(request.getNombre());
        cliente.setEmail(request.getEmail());
        cliente.setTelefono(request.getTelefono());
        cliente.setDireccion(request.getDireccion());

        return toResponse(clienteRepository.save(cliente));
    }

    public void eliminar(Long id) {
        Cliente cliente = buscarClienteOLanzar(id);
        clienteRepository.delete(cliente);
    }

    Cliente buscarClienteOLanzar(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con id: " + id));
    }

    private ClienteResponse toResponse(Cliente cliente) {
        return ClienteResponse.builder()
                .id(cliente.getId())
                .nombre(cliente.getNombre())
                .email(cliente.getEmail())
                .telefono(cliente.getTelefono())
                .direccion(cliente.getDireccion())
                .build();
    }
}
