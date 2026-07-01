package com.microservice.pedido.client;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.microservice.pedido.DTO.ProductoDTO;

@Component
@RequiredArgsConstructor
public class ProductoClient {

    private final RestTemplate restTemplate;

    private static final String URL_PRODUCTOS =
            "http://localhost:8081/api/v1/productos/";

    public ProductoDTO obtenerProducto(Long idProducto) {

        return restTemplate.getForObject(
                URL_PRODUCTOS + idProducto,
                ProductoDTO.class
        );
    }
}
