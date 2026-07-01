package com.microservice.pedido.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.microservice.pedido.DTO.ClienteDTO;
import com.microservice.pedido.DTO.ProductoDTO;
import com.microservice.pedido.DTO.StockDTO;
import com.microservice.pedido.model.Pedido;
import com.microservice.pedido.model.ProductoPedido;
import com.microservice.pedido.repository.PedidoRepository;



@Service
@Transactional
public class PedidoService {

    @Autowired
    private PedidoRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    private static final String URL_CLIENTES =
            "http://localhost:8081/api/v1/clientes/buscar/";

    private static final String URL_PRODUCTOS =
            "http://localhost:8086/api/v1/productos/buscar/";

    private static final String URL_STOCK =
            "http://localhost:8086/api/v1/stock/producto/buscar/";

    public Pedido crearPedido(Pedido pedido) {

    // 1. Verificar cliente
    ClienteDTO cliente = restTemplate.getForObject(
            URL_CLIENTES + pedido.getIdCliente(),
            ClienteDTO.class);

    if (cliente == null) {
        throw new RuntimeException("Cliente no encontrado");
    }

    // 2. Procesar productos
    for (ProductoPedido item : pedido.getProductos()) {

        // Obtener producto
        ProductoDTO producto = restTemplate.getForObject(
                URL_PRODUCTOS + item.getIdProducto(),
                ProductoDTO.class);

        if (producto == null) {
            throw new RuntimeException(
                    "Producto no encontrado: "
                    + item.getIdProducto());
        }

        // Obtener stock
        StockDTO stock = restTemplate.getForObject(
                URL_STOCK + item.getIdProducto(),
                StockDTO.class);

        if (stock == null) {
            throw new RuntimeException(
                    "Stock no encontrado para el producto "
                    + item.getIdProducto());
        }

        // Verificar cantidad disponible
        if (stock.getCantDisponible() < item.getCantidad()) {

            throw new RuntimeException(
                    "Stock insuficiente para el producto "
                    + producto.getNombProducto());
        }

        // Completar información del producto
        item.setNombProducto(
                producto.getNombProducto());

        item.setPrecio(
                producto.getPrecio());

        // Relacionar con el pedido
        item.setPedido(pedido);
    }

    // 3. Guardar pedido
    return repository.save(pedido);
}
    public List<Pedido> listarPedidos() {

        return repository.findAll();
    }

    public Pedido buscarPedido(Long idPedido) {

        return repository.findById(idPedido)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Pedido no encontrado"));
    }

    public void eliminarPedido(Long idPedido) {

        Pedido pedido = buscarPedido(idPedido);

        repository.delete(pedido);
    }
}