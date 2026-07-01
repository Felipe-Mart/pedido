package com.microservice.pedido.DTO;

import lombok.Data;


@Data
public class StockDTO {

    private Long idStock;
    private Integer cantDisponible;
    private Integer stockMinimo;
}
