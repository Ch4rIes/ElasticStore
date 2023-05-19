package com.example.inventoryservices.controller;


import com.example.inventoryservices.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private InventoryService inventoryService;

    @GetMapping("/{sku-code}")
    @ResponseStatus(HttpStatus.OK)
    public boolean checkInventory(@PathVariable("sku-code") String skuCode){
        return inventoryService.isInStock(skuCode);
    }
}
