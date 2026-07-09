package com.microservice.pedido.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.microservice.pedido.model.Pedido;
import com.microservice.pedido.service.PedidoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/pedidos")
@Validated
@Tag(name = "Pedidos", description = "Operaciones relacionadas con la creación, consulta y gestión de pedidos.")
public class PedidoController {

        @Autowired
        private PedidoService pedidoService;

        @PostMapping
        @Operation(summary = "Crear pedido", description = "Registra un nuevo pedido en el sistema.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Pedido creado correctamente"),
                        @ApiResponse(responseCode = "400", description = "Datos del pedido inválidos o incompletos")
        })
        public ResponseEntity<?> crearPedido(
                        @Valid @RequestBody Pedido pedido) {

                return ResponseEntity
                                .status(HttpStatus.CREATED)
                                .body(pedidoService.crearPedido(pedido));
        }

        @GetMapping
        @Operation(summary = "Listar pedidos", description = "Obtiene una lista de todos los pedidos registrados en el sistema.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Lista de pedidos obtenida correctamente")
        })
        public ResponseEntity<?> listarPedidos() {

                return ResponseEntity.ok(
                                pedidoService.listarPedidos());
        }

        @GetMapping("buscar/{id}")
        @Operation(summary = "Buscar pedido por ID", description = "Obtiene los detalles de un pedido específico mediante su identificador.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Pedido encontrado exitosamente"),
                        @ApiResponse(responseCode = "404", description = "Pedido no encontrado")
        })
        public ResponseEntity<?> buscarPedido(
                        @PathVariable Long id) {

                return ResponseEntity.ok(
                                pedidoService.buscarPedido(id));
        }

        @DeleteMapping("eliminar/{id}")
        @Operation(summary = "Eliminar pedido", description = "Elimina de forma permanente un pedido del sistema mediante su identificador.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Pedido eliminado correctamente"),
                        @ApiResponse(responseCode = "404", description = "Pedido no encontrado para eliminar")
        })
        public ResponseEntity<?> eliminarPedido(
                        @PathVariable Long id) {

                pedidoService.eliminarPedido(id);

                return ResponseEntity.ok(
                                "Pedido eliminado correctamente");
        }
}