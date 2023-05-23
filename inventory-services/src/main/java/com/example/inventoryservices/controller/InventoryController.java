package com.example.inventoryservices.controller;


import com.example.inventoryservices.dto.InventoryResponse;
import com.example.inventoryservices.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private InventoryService inventoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> checkInventory(@RequestParam List<String> skuCode){
        return inventoryService.isInStock(skuCode);
    }
}
