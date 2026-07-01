package com.microservice.pedido.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservice.pedido.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    
}
