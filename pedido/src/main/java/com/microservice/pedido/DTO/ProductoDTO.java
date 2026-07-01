package com.microservice.pedido.DTO;

import lombok.Data;

@Data
public class ProductoDTO {

    private Long idProducto;
    private String nombProducto;
    private Double precio;

}
