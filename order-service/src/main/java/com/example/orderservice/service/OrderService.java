package com.example.orderservice.service;


import com.example.orderservice.controller.OrderLineItemsDto;
import com.example.orderservice.dto.InventoryResponse;
import com.example.orderservice.dto.OrderRequest;
import com.example.orderservice.model.Order;
import com.example.orderservice.model.OrderLineItems;
import com.example.orderservice.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient webClient;
    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItemsList = orderRequest.getOrderLineItemsDTOList().stream().map(orderLineItemsDto -> mapToDto(orderLineItemsDto)).toList();
        order.setOrderLineItemsList(orderLineItemsList);

        //call inventory service to check if we have sufficient inventory

        List<String> skuCode = order.getOrderLineItemsList().stream().map(OrderLineItems::getSkuCode).toList();

        InventoryResponse[] inventoryResponses = webClient.get()
                .uri("http://localhost:8082/api/inventory" ,
                uriBuilder -> uriBuilder.queryParam("skuCode", skuCode).build()
                )
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();


        Boolean allProductsInStock = Arrays.stream(inventoryResponses).allMatch(InventoryResponse::isInStock);


        if(!allProductsInStock){
            throw new IllegalArgumentException("Inventory is not sufficient");
        }else{
            orderRepository.save(order);
            System.out.println("Inventory is sufficient");
        }

    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuant(orderLineItemsDto.getQuant());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }
}
