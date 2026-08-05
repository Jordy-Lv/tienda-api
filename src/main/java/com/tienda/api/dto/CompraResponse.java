package com.tienda.api.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompraResponse {

    private Long id;
    private ClienteResponse cliente;
    private ProductoResponse producto;
    private Integer cantidad;
    private BigDecimal total;
    private LocalDateTime fechaCompra;
}
