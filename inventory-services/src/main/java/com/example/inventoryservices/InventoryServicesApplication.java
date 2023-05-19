package com.example.inventoryservices;

import com.example.inventoryservices.model.Inventory;
import com.example.inventoryservices.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InventoryServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServicesApplication.class, args);
	}

	public CommandLineRunner loadData(InventoryRepository repository) {
		return args -> {
			Inventory i1 = new Inventory();
			i1.setSkuCode("Iphone X");
			i1.setQuant(10);
            repository.save(i1);
			Inventory i2 = new Inventory();
			i2.setSkuCode("Iphone XS");
			i2.setQuant(20);
			repository.save(i2);
        };
	}
}
