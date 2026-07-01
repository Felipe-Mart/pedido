package com.microservice.pedido.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.microservice.pedido.model.Pedido;
import com.microservice.pedido.service.PedidoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/pedidos")
@Validated
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<?> crearPedido(
            @Valid @RequestBody Pedido pedido) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(pedidoService.crearPedido(pedido));
    }

    @GetMapping
    public ResponseEntity<?> listarPedidos() {

        return ResponseEntity.ok(
                pedidoService.listarPedidos());
    }

    @GetMapping("buscar/{id}")
    public ResponseEntity<?> buscarPedido(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                pedidoService.buscarPedido(id));
    }

    @DeleteMapping("eliminar/{id}")
    public ResponseEntity<?> eliminarPedido(
            @PathVariable Long id) {

        pedidoService.eliminarPedido(id);

        return ResponseEntity.ok(
                "Pedido eliminado correctamente");
    }
}